package com.app.controller;

import com.app.model.Person;
import com.app.service.MongoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MongoTransactionalController {
    @Autowired
    private MongoTransactionalService mongoTransactionalService;

    @GetMapping("/save")
    public void save(HttpServletRequest request){
        String name1 = request.getParameter("name1");
        int age1 = Integer.parseInt(request.getParameter("age1"));
        String name2 = request.getParameter("name2");
        int age2 = Integer.parseInt(request.getParameter("age2"));

        Person p1 = new Person();
        p1.setName(name1);
        p1.setAge(age1);

        Person p2 = new Person();
        p2.setName(name2);
        p2.setAge(age2);

        mongoTransactionalService.save(p1,p2);
    }

    @GetMapping("/savewitherror")
    public void saveWithError(HttpServletRequest request){
        String name1 = request.getParameter("name1");
        int age1 = Integer.parseInt(request.getParameter("age1"));
        String name2 = request.getParameter("name2");
        int age2 = Integer.parseInt(request.getParameter("age2"));

        Person p1 = new Person();
        p1.setName(name1);
        p1.setAge(age1);

        Person p2 = new Person();
        p2.setName(name2);
        p2.setAge(age2);
        mongoTransactionalService.saveWithError(p1,p2);
    }

    @GetMapping("/savewitherror2")
    public void saveWithError2(HttpServletRequest request){
        String name1 = request.getParameter("name1");
        int age1 = Integer.parseInt(request.getParameter("age1"));
        String name2 = request.getParameter("name2");
        int age2 = Integer.parseInt(request.getParameter("age2"));

        Person p1 = new Person();
        p1.setName(name1);
        p1.setAge(age1);

        Person p2 = new Person();
        p2.setName(name2);
        p2.setAge(age2);

        mongoTransactionalService.saveWithError2(p1,p2);

    }

}
