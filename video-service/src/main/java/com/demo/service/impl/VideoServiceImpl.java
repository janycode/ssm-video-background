package com.demo.service.impl;

import com.demo.dao.VideoMapper;
import com.demo.pojo.Video;
import com.demo.pojo.VideoExample;
import com.demo.service.VideoService;
import com.demo.utils.QueryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public PageInfo<Video> findAll(Integer pageNum, Integer pageSize, QueryVo queryVo) {
        PageHelper.startPage(pageNum, pageSize);
        List<Video> videoList = videoMapper.findAll(queryVo);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        return pageInfo;
    }

    @Override
    public int delVideoById(Integer id) {
        return videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delVideoByIds(Integer[] ids) {
        int count = 0;
        for (Integer id : ids) {
            count += videoMapper.deleteByPrimaryKey(id);
        }
        return count;
//        VideoExample example = new VideoExample();
//        VideoExample.Criteria criteria = example.createCriteria();
//        // DELETE FROM `table` WHERE id IN(1, 2, 3); IN 效率较低不如遍历
//        criteria.andIdIn(Arrays.asList(ids));
//        return videoMapper.deleteByExample(example);
    }

    @Override
    public Video findById(Integer id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateById(Video video) {
        return videoMapper.updateByPrimaryKeyWithBLOBs(video) == 1;
    }

    @Override
    public boolean add(Video video) {
        return videoMapper.insert(video) == 1;
    }

    @Override
    public Video findVideoById(Integer id) {
        return videoMapper.findVideoById(id);
    }
}
