package com.demo.service.impl;

import com.demo.dao.CourseMapper;
import com.demo.pojo.Course;
import com.demo.pojo.Video;
import com.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.selectByExample(null);
    }

    @Override
    public Course findVideoListById(Integer courseId) {
        return courseMapper.findVideoListById(courseId);
    }
}
