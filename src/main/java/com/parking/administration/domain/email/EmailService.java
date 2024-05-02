package com.parking.administration.domain.email;

import com.parking.administration.infra.exception.EmailException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.parking.administration.util.Utility.LOGGER;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class EmailService implements EmailSender {
    private JavaMailSender javaMailSender;

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
            helper.setFrom("g.hen.moreira@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send the email - EmailService", e);
            throw new EmailException(ErrorCode.NO0001.getMessage(), ErrorCode.NO0001.getCode());
        }
    }
}
