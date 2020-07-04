package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String email;

    private String phonenum;

    private String password;

    private String code;

    private String nickname;

    private String sex;

    private String birthday;

    private String address;

    private String imgurl;

    private Date createtime;
}