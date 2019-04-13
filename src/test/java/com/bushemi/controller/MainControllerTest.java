package com.bushemi.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/mock-mvc-dispatcher-servlet.xml")
public class MainControllerTest {

    @Autowired
    MainController controller;

    @Test
    public void encode() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Igor");
        json.put("age", 14);
        String string = json.toString();

        JSONObject object = new JSONObject(string);
        String name = object.get("name").toString();
        System.out.println("name = " + name);
        String age = object.get("age").toString();
        System.out.println("age = " + age);

        String encode = controller.encode(string);
        System.out.println("encode = " + encode);
    }

    @Test
    public void decode() throws Exception {
        String decode = controller.decode("n!u|C\u0004LT\u0004]\u0004.; \u0007fPqk3gXeYMXzJSyKEIFkF3qFa9t+LO0B6k6rrt8WQoTA=");
        System.out.println("decode = " + decode);
    }

}