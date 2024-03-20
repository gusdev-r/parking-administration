package com.parking.administration.demo.customValidator.annotations;

import com.parking.administration.demo.customValidator.annotations.ValidateCPF;
import com.parking.administration.demo.infra.exception.CpfNotValidException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidateCPF, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.replaceAll("[^0-9]", "").length() == 11
                || s.replaceAll("[^0-9]", "").length() == 14) {
            if (s.length() == 11) {
                return cpfValidation(s);
            } else {
                return cnpjValidation(s);
            }
        } else {
            throw new CpfNotValidException(ErrorCode.ON0002.getMessage(), ErrorCode.ON0002.getCode());
        }
    }
    private Boolean cpfValidation(String cpf) {
        int sum = 0;
        for (int index = 0; index < 9; index++) {
            sum += (cpf.charAt(index) - '0') * (10 - index);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }
        if (cpf.charAt(9) - '0' != firstDigit) {
            return false;
        }
        sum = 0;
        for (int index = 0; index < 10; index++) {
            sum += (cpf.charAt(index) - '0') * (11 - index);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }
        return cpf.charAt(10) - '0' == secondDigit;
    }
    private Boolean cnpjValidation (String cnpj) {
        int[] weights0 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int index = 0; index < 12; index++) {
            sum += (cnpj.charAt(index) - '0') * weights0[index];
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }
        int[] weights1 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int index = 0; index < 13; index++) {
            sum += (cnpj.charAt(index) - '0') * weights1[index];
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }
        return cnpj.charAt(12) - '0' == secondDigit;
    }
}
