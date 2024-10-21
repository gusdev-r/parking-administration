package com.parking.administration.util;

import com.parking.administration.infra.exception.EmailNotValidException;
import com.parking.administration.infra.exception.PasswordNotValidException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class Utility {
    public static final Logger LOGGER = LoggerFactory.getLogger("parking-administration");
    private final JavaMailSender mailSender;

    public void verifyEmail(String email) {
        String regexEmail = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
        if (!Pattern.matches(regexEmail, email)) {
            LOGGER.warn("The email format is not valid - RegistrationService");
            throw new EmailNotValidException(ErrorCode.EM0003.getMessage(), ErrorCode.EM0003.getCode());
        }
    }
    public void verifyPassword(String password) {
        String regexPassword = "^(?=.*[!@#$%^&*()-+=])(?=\\S+$).{8,}$";
        if (!Pattern.matches(regexPassword, password)) {
            LOGGER.warn("The password format is not valid - RegistrationService");
            throw new PasswordNotValidException(ErrorCode.ON0005.getMessage(), ErrorCode.ON0005.getCode());
        }
    }

    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mimeMsg, "utf-8");
        try {
            if (!Boolean.parseBoolean(Dotenv.load().get("DEV"))) {
            msg.setText(body, true);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setFrom("gusdev.testcode@gmail.com");
            mailSender.send(mimeMsg);
            } else {
                LOGGER.info(body);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
