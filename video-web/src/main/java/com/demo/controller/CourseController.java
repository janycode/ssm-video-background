package com.demo.controller;


import com.demo.pojo.Course;
import com.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/findAll")
    public List<Course> findAll() {
        return courseService.findAll();
    }

}
