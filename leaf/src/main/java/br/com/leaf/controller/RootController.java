package br.com.leaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/") // Maps to the root URL ("/")
    public String home() {
        return "index"; // Logical view name (Thymeleaf will look for index.html)
    }

    @GetMapping("/about") // Example: Maps to the /about URL
    public String about() {
        return "about"; // Logical view name (Thymeleaf will look for about.html)
    }

    // Add other mappings for your root-level pages (e.g., /contact, etc.)

}