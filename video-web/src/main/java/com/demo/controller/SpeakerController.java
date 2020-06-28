package com.demo.controller;

import com.demo.pojo.Speaker;
import com.demo.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @RequestMapping("/showSpeakerList")
    public List<Speaker> findAll() {
        return speakerService.findAll();
    }

}
