package com.lq.learn2026.controller;

import com.lq.learn2026.bean.Student;
import com.lq.learn2026.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/student")
    public List<Student> queryAll() {
        return studentService.queryAll();
    }
}
