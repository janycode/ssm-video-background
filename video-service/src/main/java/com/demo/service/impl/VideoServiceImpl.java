package com.demo.service.impl;

import com.demo.dao.VideoMapper;
import com.demo.pojo.Video;
import com.demo.pojo.VideoExample;
import com.demo.service.VideoService;
import com.demo.utils.QueryVo;
import com.github.pagehelper.PageHelper;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll(Integer pageNum, Integer pageSize, QueryVo queryVo) {
        PageHelper.startPage(pageNum, pageSize);
        return videoMapper.findAll(queryVo);
    }

    @Override
    public int delVideoById(Integer id) {
        return videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delVideoByIds(List<Integer> asList) {
        int count = 0;
        for (Integer id : asList) {
            count += videoMapper.deleteByPrimaryKey(id);
        }
        return count;
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
}
