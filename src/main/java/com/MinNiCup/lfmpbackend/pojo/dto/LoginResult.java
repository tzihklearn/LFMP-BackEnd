package com.MinNiCup.lfmpbackend.pojo.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LoginResult {

    private String message;
    private String code;
    private Result data;
    @Data
    public static class Result{
        private ArrayList<Object> bookOrderList;
        private String loginid;
        private Student student;
        private String succeed;
        private String token;

        @Data
        public static class Student{
            private String id;
            private String code;
            private String name;
            private String password;
            private String sex;
            private String email;
            private String phone;
            private String cardNo;
            private String role;
            private String state;
        }
    }
}
