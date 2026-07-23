package com.bank.amendments.handler;

public class CoreBankingException extends RuntimeException {
    public CoreBankingException(String message) { super(message); }
    public CoreBankingException(String message, Throwable cause) { super(message, cause); }
}
