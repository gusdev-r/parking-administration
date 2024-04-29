package com.parking.administration.service;

import com.parking.administration.customValidator.EmailValidator;
import com.parking.administration.customValidator.PasswordValidator;
import com.parking.administration.domain.User;
import com.parking.administration.domain.email.EmailSender;
import com.parking.administration.domain.token.ConfirmationToken;
import com.parking.administration.dto.request.UserRegistrationRequest;
import com.parking.administration.infra.exception.EmailException;
import com.parking.administration.infra.exception.EmailNotValidException;
import com.parking.administration.infra.exception.PasswordNotValidException;
import com.parking.administration.infra.exception.TokenException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.parking.administration.util.Utility.LOGGER;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class RegistrationService {

    private UserDetailsService userDetailsService;
    private EmailValidator emailValidator;
    private EmailSender emailSender;
    private ConfirmationTokenService confirmationTokenService;
    private PasswordValidator passwordValidator;

    public String register(UserRegistrationRequest requestUser) {
        boolean isValidEmail = emailValidator.test(requestUser.email());
        if (!isValidEmail) {
            LOGGER.warn("The email format is not valid - RegistrationService");
            throw new EmailNotValidException(ErrorCode.ON0003.getMessage(), ErrorCode.ON0003.getCode());
        }

        boolean isValidPassword = passwordValidator.test(requestUser.password());
        if (!isValidPassword) {
            LOGGER.warn("The password format is not valid - RegistrationService");
            throw new PasswordNotValidException(ErrorCode.ON0005.getMessage(), ErrorCode.ON0005.getCode());
        }

        String accountConfirmationToken = userDetailsService.signUpUser(
                new User (
                        requestUser.userRole(),
                        requestUser.fullName(),
                        requestUser.email(),
                        requestUser.password(),
                        requestUser.document(),
                        requestUser.username())
        );

        String link = "http://localhost:8080/v1/api/registration/confirm?token=" + accountConfirmationToken;
        emailSender.send(requestUser.email(),
                buildEmail(requestUser.fullName(), link));
        return accountConfirmationToken;
    }

    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new TokenException(ErrorCode.ON0004.getMessage(), ErrorCode.ON0004.getCode()));

        if (confirmationToken.getConfirmedAt() != null) {
            LOGGER.warn("The e-mail has already been confirmed - RegistrationService");
            throw new EmailException(ErrorCode.EM0001.getMessage(), ErrorCode.EM0001.getCode());
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            LOGGER.warn("Token already expired - RegistrationService");
            throw new TokenException(ErrorCode.EM0001.getMessage(), ErrorCode.EM0001.getCode());
        }
        confirmationTokenService.setConfirmedAt(token);
        LOGGER.info("Email confirmed by token - RegistrationService");
        userDetailsService.enableUser(
                confirmationToken.getUser().getEmail());
        LOGGER.info("Enabling the user - Registration Service");
        return "The token was confirmed successfully!";
    }
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +

                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +

                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +

                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +

                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +

                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +

                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +

                "                  <tbody><tr>\n" +

                "                    <td style=\"padding-left:10px\">\n" +

                "                  \n" +

                "                    </td>\n" +

                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +

                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +

                "                    </td>\n" +

                "                  </tr>\n" +

                "                </tbody></table>\n" +

                "              </a>\n" +

                "            </td>\n" +

                "          </tr>\n" +

                "        </tbody></table>\n" +

                "        \n" +

                "      </td>\n" +

                "    </tr>\n" +

                "  </tbody></table>\n" +

                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +

                "    <tbody><tr>\n" +

                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +

                "      <td>\n" +

                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +

                "                  <tbody><tr>\n" +

                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +

                "                  </tr>\n" +

                "                </tbody></table>\n" +

                "        \n" +

                "      </td>\n" +

                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +

                "    </tr>\n" +

                "  </tbody></table>\n" +

                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +

                "    <tbody><tr>\n" +

                "      <td height=\"30\"><br></td>\n" +

                "    </tr>\n" +
                "    <tr>\n" +

                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +

                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering with us. Please click on the link below to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 20 minutes. <p>See you soon</p>" +

                "        \n" +

                "      </td>\n" +

                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +

                "    </tr>\n" +

                "    <tr>\n" +

                "      <td height=\"30\"><br></td>\n" +

                "    </tr>\n" +

                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +

                "\n" +
                "</div></div>";
    }
}
