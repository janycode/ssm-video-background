package com.demo.service.impl;

import com.demo.dao.SpeakerMapper;
import com.demo.pojo.Speaker;
import com.demo.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper speakerMapper;

    @Override
    public List<Speaker> findAll() {
        return speakerMapper.selectByExample(null);
    }
}
