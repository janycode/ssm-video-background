package com.demo.service;

import com.demo.pojo.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> findAll();

    Subject findSubjectById(Integer id);

    Subject findSubjectNameByVideoId(Integer id);
}
