package com.parking.administration.domain.email;

import com.parking.administration.infra.exception.EmailException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.parking.administration.util.Utility.LOGGER;

@AllArgsConstructor
@Service
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;

    /* TODO use queue instead @Async */
    @Override
    @Async
    public void send(String email, String content) throws EmailException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            message.setText(content, true);
            message.setTo(email);
            message.setSubject("Confirme o seu email.");
            message.setFrom("gusdev.testcode@gmail.com");
            javaMailSender.send(mimeMessage);
            LOGGER.info("The email has been sent successfully");
        } catch (MessagingException e) {
            LOGGER.error("Failed to send the email - EmailService", e);
            throw new EmailException(ErrorCode.NO0001.getMessage(), ErrorCode.NO0001.getCode());
        }
    }
}
