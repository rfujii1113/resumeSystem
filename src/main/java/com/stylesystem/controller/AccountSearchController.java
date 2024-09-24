package com.stylesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountSearchController {
	
	@GetMapping("/accountSearch")
    public String showAccountSearchPage() {
        return "accountSearch";
    }
}
