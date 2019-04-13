package com.bushemi.controller;

import com.bushemi.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final EncryptionService encryptionService;

    @Autowired
    public MainController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PostMapping("/encode")
    @ResponseBody
    String encode(@RequestBody String text) {
        return encryptionService.encode(text);
    }

    @PostMapping("/decode")
    @ResponseBody
    String decode(@RequestBody String text) {
        return encryptionService.decode(text);
    }
}
