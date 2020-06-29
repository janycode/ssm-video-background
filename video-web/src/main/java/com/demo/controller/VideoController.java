package com.demo.controller;

import com.demo.pojo.Video;
import com.demo.service.CourseService;
import com.demo.service.SpeakerService;
import com.demo.service.VideoService;
import com.demo.utils.QueryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CourseService courseService;

    /**
     * 视频管理列表展示页
     * @param pageNum
     * @param pageSize
     * @param session
     * @param queryVo
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView findAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                //HttpSession session,
                                QueryVo queryVo) {
        // 如果用户有输入条件，以用户输入条件优先；用户输入为空时，取 session 中的内容查询。
        //queryVo = checkQueryVo(queryVo, session);
        //session.setAttribute("queryVo", queryVo);

        ModelAndView mav = new ModelAndView();

        List<Video> videoList = videoService.findAll(pageNum, pageSize, queryVo);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        mav.addObject("pageInfo", pageInfo);

        // 获取两个下拉菜单的内容
        getComboBox(mav);

        mav.setViewName("/behind/videoList.jsp");
        return mav;
    }

    /**
     * 通过 id 删除 1 条记录
     * @param id
     * @return
     */
    @RequestMapping("/videoDel")
    public String delVideoById(Integer id) {
        return videoService.delVideoById(id) == 1 ? "success" : "error";
    }

    /**
     * 通过一组 id 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delBatchVideos")
    public String delVideoByIds(Integer[] ids) {
        // System.out.println(Arrays.toString(ids));
        videoService.delVideoByIds(ids);
        return "forward:/video/list";
    }

    /**
     * 新增 和 修改 共用一个接口和页面
     * @param id 通过 id 查找出要修改的信息
     * @return
     */
    @RequestMapping("/addAndUpdVideo")
    public ModelAndView addAndUpdVideo(Integer id) {
        ModelAndView mav = new ModelAndView();
        // id为空时为添加、id不空时为修改
        if (null != id) {
            Video video = videoService.findById(id);
            // 获取两个下拉菜单的内容
            mav.addObject("video", video);
        }
        getComboBox(mav);
        mav.setViewName("/behind/addVideo.jsp");
        return mav;
    }

    /**
     * 新增和修改使用公共接口
     * @param video
     * @return
     * @throws IOException
     */
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Video video) {
        System.out.println(video);
        boolean flag;
        // id 不空时为修改、id 为空时为添加
        if (null != video.getId()) {
            flag = videoService.updateById(video);
        } else {
            flag = videoService.add(video);
        }
        return "redirect:" + (flag ? "/video/list" : "/video/saveOrUpdate");
    }

    /**
     * 获取下拉菜单内容存域
     * @param m
     */
    private void getComboBox(ModelAndView m) {
        // 查询显示的下拉菜单内容
        m.addObject("speakerList", speakerService.findAll());
        // 查询显示的下拉菜单内容
        m.addObject("courseList", courseService.findAll());
    }

    /**
     * 用户输入的查询条件只要不为空，优先查询
     *
     * @param q
     * @param session
     * @return
     */
    private QueryVo checkQueryVo(QueryVo q, HttpSession session) {
        if (null != q.getTitle() && null != q.getSpeakerId() && null != q.getCourseId()) {
            return q;
        }
        return (QueryVo) session.getAttribute("queryVo");
    }
}
