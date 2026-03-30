package com.lq.learn2026.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lq.learn2026.bean.Student;
import com.lq.learn2026.dao.StudentMapper;
import com.lq.learn2026.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> queryAll() {
        List<Student> students = studentMapper.selectList(null);
        System.out.println("students = " + JSON.toJSONString(students));
        System.out.println("students = " + students);
        return students;
    }
}

