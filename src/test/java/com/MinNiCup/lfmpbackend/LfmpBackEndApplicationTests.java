package com.MinNiCup.lfmpbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LfmpBackEndApplicationTests {

    @Test
    void contextLoads() {
        String s1 = "SIPC115";
        String s2 = "SIPC115";
        String s3 = "SIPC113";
        System.out.println("s1.equals(s2) = " + s1.equals(s2));
        System.out.println("s1.equals(s3) = " + s1.equals(s3));
        StringBuilder sb = new StringBuilder(s1);
        System.out.println("sb.charAt(6) = " + sb.charAt(6));
        sb.setCharAt(6, '5');
        System.out.println("sb = " + sb);
        System.out.println("sb.equals(s2) = " + sb.equals(s2));
        System.out.println("sb.equals(s3) = " + sb.equals(s3));
        System.out.println("s2.equals(sb) = " + s2.equals(sb));
        System.out.println("s3.equals(sb) = " + s3.equals(sb));
        System.out.println("sb.toString().equals(s2) = " + sb.toString().equals(s2));
        System.out.println("sb.toString().equals(s3) = " + sb.toString().equals(s3));
        System.out.println("s2.equals(sb.toString()) = " + s2.equals(sb.toString()));
        System.out.println("s3.equals(sb.toString()) = " + s3.equals(sb.toString()));
    }

}
