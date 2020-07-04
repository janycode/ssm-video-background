package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;

    private String courseTitle;

    private Integer subjectId;

    private String courseDesc;

    private List<Video> videoList;

}