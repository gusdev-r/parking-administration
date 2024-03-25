package com.parking.administration.demo.email;

import com.parking.administration.demo.infra.exception.EmailException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static com.parking.administration.demo.utils.Utility.LOGGER;
@Service
public class EmailService implements EmailSender {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /* TODO use queue instead @Async */
    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("devgus@academy.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send the email - EmailService", e);
            throw new EmailException(ErrorCode.NO0001.getMessage(), ErrorCode.NO0001.getCode());
        }
    }
}
