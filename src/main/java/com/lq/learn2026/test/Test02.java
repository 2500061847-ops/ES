package com.lq.learn2026.test;

import com.lq.learn2026.bean.Student;

import java.io.*;

public class Test02 {
    public static void main(String[] args) throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("张三");
        // 序列化
        // FileOutputStream output = new FileOutputStream("D:/test.txt");
        // ObjectOutputStream outputStream = new ObjectOutputStream(output);
        // outputStream.writeObject(student);

        // 反序列化
        FileInputStream input = new FileInputStream("D:/test.txt");
        ObjectInputStream inputStream = new ObjectInputStream(input);
        Student studentFan = (Student) inputStream.readObject();
        System.out.println(studentFan);


    }
}
