package com.example.focus_group.mail;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
@PropertySource("application.yml")
public class MailService{
    @Value("${spring.mail.username}")
    private String senderEmail;
    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);
    public JavaMailSender emailSender;

    public ResponseEntity<String> sendEmail(String email) {

        try {
            sendSimpleEmail(email, "Привет", "Черный");
        } catch (MailException mailException) {
            // LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            // LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }


    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendEmail(String toEmail, String subject, String body) {
//
////        try {
////            SimpleMailMessage message = new SimpleMailMessage();
////            message.setFrom("arjungautam8877@gmail.com");
////            message.setTo(mailInfo.getRecipientEmail());
////            message.setSubject(mailInfo.getEmailType().name());
////            message.setText(mailInfo.getContext().toString());
////            javaMailSender.send(message);
////            System.out.println("Email Sent Successfully...");
////        } catch (MailException e) {
////            e.printStackTrace();
////        }
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(toEmail);
//            message.setSubject(subject);
//            message.setText(body);
//            javaMailSender.send(message);
//            System.out.println("Email Sent Successfully...");
//        } catch (MailException e) {
//            e.printStackTrace();
//        }
//    }

}