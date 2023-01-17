package com.example.artizen.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MemberController {
    @GetMapping("/")
    public String sample() {
        return "hello";
    }
}
