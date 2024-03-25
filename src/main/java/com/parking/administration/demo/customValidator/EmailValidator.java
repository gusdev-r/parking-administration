package com.parking.administration.demo.customValidator;

import com.parking.administration.demo.infra.exception.EmailNotValidException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import static com.parking.administration.demo.utils.Utility.LOGGER;
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        if (!isValidEmail(email)) {
            LOGGER.error("Email format not valid - EmailValidator");
            throw new EmailNotValidException(ErrorCode.ON0003.getMessage(), ErrorCode.ON0003.getCode());
        }
        return true;
    }
    public static boolean isValidEmail(String email) {
        String regexEmail = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
        return Pattern.matches(regexEmail, email);
    }
}
