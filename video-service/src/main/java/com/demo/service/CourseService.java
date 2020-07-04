package com.demo.service;

import com.demo.pojo.Course;
import com.demo.pojo.Video;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findVideoListById(Integer courseId);
}
