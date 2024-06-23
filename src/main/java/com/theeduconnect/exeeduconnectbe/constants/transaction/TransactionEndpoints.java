package com.theeduconnect.exeeduconnectbe.constants.transaction;

public class TransactionEndpoints {
    public static final String BASE = "/api/transactions";
    public static final String GET_ALL = BASE;
    public static final String GET_BY_ID = "/{id}";
    public static final String CREATE = BASE;
    public static final String UPDATE = "/{id}";
    public static final String DELETE = "/{id}";
    public static final String GET_BY_USERID = "/user/{userid}";
    public static final String ALL_TRANSACTION_FEATURE = "/api/transactions/**";
}
