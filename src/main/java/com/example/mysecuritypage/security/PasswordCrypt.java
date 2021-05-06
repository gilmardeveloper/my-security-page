package com.example.mysecuritypage.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Classe para criptografia de dados
 * 
 * @author Gilmar Carlos
 *
 */
@Component
public class PasswordCrypt extends BCryptPasswordEncoder{
	

}
