package com.example.usersmicroservice.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.usersmicroservice.service.MailService;

import mail.Mail;

@RestController
@CrossOrigin(origins = "*")
public class MailRestController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send/{email}")
    public String sendMail (@PathVariable String email, @RequestBody Mail mail) {
        mailService.sendMail(email, mail);
        return "Mail sent successfully";
    }
}