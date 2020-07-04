package com.demo.service;

import com.demo.pojo.Video;
import com.demo.utils.QueryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface VideoService {

    PageInfo<Video> findAll(Integer pageNum, Integer pageSize, QueryVo queryVo);

    int delVideoById(Integer id);

    int delVideoByIds(Integer[] ids);

    Video findById(Integer id);

    boolean updateById(Video video);

    boolean add(Video video);

    Video findVideoById(Integer id);
}
