package dev.samuel.PersonalFinance.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String email) {
        super("Email " + email +  " já cadastrado");
    }
}
