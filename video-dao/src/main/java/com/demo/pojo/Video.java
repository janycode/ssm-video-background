package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private Integer id;

    private String title;

    private Integer time;

    private Integer spearkerId;

    private Integer courseId;

    private String videoUrl;

    private String imageUrl;

    private Integer playNum;

    private String detail;

    private Speaker speaker; // 关联映射

    private Course course; // 关联映射
}