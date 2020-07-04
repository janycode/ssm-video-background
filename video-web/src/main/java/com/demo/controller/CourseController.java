package com.demo.controller;


import com.demo.pojo.Course;
import com.demo.pojo.Subject;
import com.demo.service.CourseService;
import com.demo.service.SpeakerService;
import com.demo.service.SubjectService;
import com.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private SpeakerService speakerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/findAll")
    public List<Course> findAll() {
        return courseService.findAll();
    }

    /**
     * 显示不同的高级课程内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/course/{subjectId}")
    public ModelAndView toCourseIdPath(@PathVariable(name = "subjectId") String id) {
        ModelAndView mav = new ModelAndView();

        // 导航栏下拉菜单
        List<Subject> subjectList = subjectService.findAll();
        mav.addObject("subjectList", subjectList);

        // 根据 ID 查询单个 subject，包含了多个课程，单个课程包含了多个视频信息
        Subject subject = subjectService.findSubjectById(Integer.parseInt(id));
        mav.addObject("subject", subject);

        mav.setViewName("/before/course.jsp");
        return mav;
    }

}
