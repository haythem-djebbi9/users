package com.example.usersmicroservice.service;



import mail.Mail;

public interface MailService {
    void sendMail(String email, Mail mail);
}