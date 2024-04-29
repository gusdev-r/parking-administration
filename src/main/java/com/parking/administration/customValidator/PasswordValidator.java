package com.parking.administration.customValidator;

import com.parking.administration.infra.exception.EmailNotValidException;
import com.parking.administration.infra.exception.enums.ErrorCode;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.parking.administration.util.Utility.LOGGER;

public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String password) {
        if (!isValidPassword(password)) {
            LOGGER.error("Password format not valid - EmailValidator");
            throw new EmailNotValidException(ErrorCode.ON0003.getMessage(), ErrorCode.ON0003.getCode());
        }
        return true;
    }
    public static boolean isValidPassword(String password) {
        String regexPassword = "^(?=.*[!@#$%^&*()-+=])(?=\\S+$).{8,}$";
        return Pattern.matches(regexPassword, password);
    }
}
