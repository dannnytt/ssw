package com.sibsutis.study.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AuthorPageController {

    @GetMapping
    public String getAboutAuthorInfo() {
        return "about-author";
    }
}
