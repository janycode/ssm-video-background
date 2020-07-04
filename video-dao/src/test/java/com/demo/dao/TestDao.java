package com.demo.dao;

import com.demo.pojo.Course;
import com.demo.pojo.Subject;
import com.demo.pojo.Video;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestDao {

    private SubjectMapper subjectMapper;
    private VideoMapper videoMapper;
    private CourseMapper courseMapper;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
        subjectMapper = (SubjectMapper) context.getBean("subjectMapper");
        videoMapper = (VideoMapper) context.getBean("videoMapper");
        courseMapper = (CourseMapper) context.getBean("courseMapper");
    }

    @Test
    public void testFindSubjectById() {
        // 1,WEB前端  6,UI  10,Python  11,PHP
        Subject subject = subjectMapper.findSubjectById(6);
        System.out.println(subject);
    }

    @Test
    public void testFindVideoById() {
        // 226-415
        Video video = videoMapper.findVideoById(226);
        System.out.println(video);
    }

    @Test
    public void testFindVideoListById() {
        Course course = courseMapper.findVideoListById(9);
        System.out.println(course);
    }

}
