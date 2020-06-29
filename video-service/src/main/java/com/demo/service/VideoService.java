package com.demo.service;

import com.demo.pojo.Video;
import com.demo.utils.QueryVo;

import java.util.List;

public interface VideoService {

    List<Video> findAll(Integer pageNum, Integer pageSize, QueryVo queryVo);

    int delVideoById(Integer id);

    int delVideoByIds(Integer[] ids);

    Video findById(Integer id);

    boolean updateById(Video video);

    boolean add(Video video);
}
