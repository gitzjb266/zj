package com.example.custom.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @ClassName UserController
 * @Description TODO
 * @Date 2019-06-05 10:17
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "customer")
public class UserController {
    @GetMapping(value = "user")
    public String user(){
        return "user";
    }
}