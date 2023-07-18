package com.stonebridge.jenkinstest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JenkinsController {
    @GetMapping("/test")
    public String test() {
        return "hello jenkins->v3.0.0";
    }
}
