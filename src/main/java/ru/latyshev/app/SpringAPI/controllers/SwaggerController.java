package ru.latyshev.app.SpringAPI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

        @GetMapping("/")
        public String getApiDocumentation() {
            return "redirect:swagger-ui.html";
        }

}

