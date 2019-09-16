package com.sasip;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword
{
    public static void main(String arga[])
    {
        PasswordEncoder pe = new BCryptPasswordEncoder();

        System.out.println(pe.encode("sasi"));
        System.out.println(pe.encode("peri"));

        System.out.println(Integer.toBinaryString(1 + 1));

    }
}
