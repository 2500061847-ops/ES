package com.lq.learn2026.controller;

import com.lq.learn2026.service.impl.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ESController {
    @Autowired
    private ESService esService;

    @RequestMapping("/createIndex")
    public String createIndex() {
        try {
            Boolean suc = esService.createIndex("student");
            if (!suc) {
                return "index already exists";
            }
            return "success";
        }catch (Exception ex){
            System.out.println("ESController.createIndex error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/queryIndex")
    public String queryIndex() {
        try {
            String result = esService.queryIndex("student");
            return result;
        }catch (Exception ex){
            System.out.println("ESController.queryIndex error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/deleteIndex")
    public String deleteIndex() {
        try {
            Boolean suc = esService.deleteIndex("student");
            if (!suc) {
                return "index not exists";
            }
            return "success";
        }catch (Exception ex){
            System.out.println("ESController.deleteIndex error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/createDocument")
    public String createDocument() {
        try {
            Boolean suc = esService.createDocument();
            if (!suc) {
                return "failed";
            }
            return "success";
        }catch (Exception ex){
            System.out.println("ESController.createDocument error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/queryDocument")
    public String queryDocument() {
        try {
            String result = esService.queryDocument();
            return result;
        }catch (Exception ex){
            System.out.println("ESController.queryDocument error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/updateDocument")
    public String updateDocument() {
        try {
            Boolean suc = esService.updateDocument();
            if (!suc) {
                return "failed";
            }
            return "success";
        }catch (Exception ex){
            System.out.println("ESController.updateDocument error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/deleteDocument")
    public String deleteDocument() {
        try {
            Boolean suc = esService.deleteDocument();
            if (!suc) {
                return "failed";
            }
            return "success";
        }catch (Exception ex){
            System.out.println("ESController.deleteDocument error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/batchCreateDoc")
    public String batchCreateDoc() {
        try {
            String result = esService.batchCreateDoc();
            return result;
        }catch (Exception ex){
            System.out.println("ESController.batchCreateDoc error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/batchDeleteDoc")
    public String batchDeleteDoc() {
        try {
            String result = esService.batchDeleteDoc();
            return result;
        }catch (Exception ex){
            System.out.println("ESController.batchDeleteDoc error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/queryAllDocument")
    public String queryAllDocument() {
        try {
            Object result = esService.queryAllDocument();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.queryAllDocument error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/pageQuery")
    public String pageQuery() {
        try {
            Object result = esService.pageQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.pageQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/sortQuery")
    public String sortQuery() {
        try {
            Object result = esService.sortQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.sortQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/conditionQuery")
    public String conditionQuery() {
        try {
            Object result = esService.conditionQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.conditionQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/comMustQuery")
    public String comMustQuery() {
        try {
            Object result = esService.comMustQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.comMustQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/comShouldQuery")
    public String comShouldQuery() {
        try {
            Object result = esService.comShouldQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.comShouldQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/rangeQuery")
    public String rangeQuery() {
        try {
            Object result = esService.rangeQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.rangeQuery error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/highlight")
    public String highlight() {
        try {
            Object result = esService.highlight();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.highlight error: " + ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/fuzzyQuery")
    public String fuzzyQuery() {
        try {
            Object result = esService.fuzzyQuery();
            return result.toString();
        }catch (Exception ex){
            System.out.println("ESController.fuzzyQuery error: " + ex.getMessage());
            return "error";
        }
    }

}
