package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private Integer id;

    private String subjectName;

    private List<Course> courseList; // 1的一方
}