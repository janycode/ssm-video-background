package com.demo.controller;

import com.demo.pojo.Subject;
import com.demo.service.SubjectService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/selectAll")
    public ModelAndView findAll() {
        List<Subject> subjectList = subjectService.findAll();

        ModelAndView mav = new ModelAndView();
        mav.addObject("subjectList", subjectList);
        mav.setViewName("/before/index.jsp");
        return mav;
    }

}
