package com.demo.service.impl;

import com.demo.dao.SubjectMapper;
import com.demo.pojo.Course;
import com.demo.pojo.Subject;
import com.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> findAll() {
        return subjectMapper.selectByExample(null);
    }

    @Override
    public Subject findSubjectById(Integer id) {
        return subjectMapper.findSubjectById(id);
    }

    @Override
    public Subject findSubjectNameByVideoId(Integer id) {
        return subjectMapper.findSubjectNameByVideoId(id);
    }
}
