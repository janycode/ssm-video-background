package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    private Integer id;

    private String speakerName;

    private String speakerJob;

    private String headImgUrl;

    private String speakerDesc;
}