package org.uniplore.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class KylinAuthentication {

	public KylinAuthentication() {
		
	}
	
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
        String ecode = "ky^_usr#2021";
        try {
            String encodedPassword = encoder.encode(ecode);
            System.out.println("原始密码:");
            System.out.println(ecode);
            System.out.println("加密后:");
            System.out.println(encodedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}
