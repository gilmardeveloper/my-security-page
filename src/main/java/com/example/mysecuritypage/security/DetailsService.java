package com.example.mysecuritypage.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mysecuritypage.model.Auth;
import com.example.mysecuritypage.model.User;
import com.example.mysecuritypage.repository.UserRepository;

/**
 * Classe com configurações de segurança do spring security para usuário
 * 
 * @author Gilmar Carlos
 *
 */
@Service("userDetailsService")
@Transactional
public class DetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messages;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new AuthenticationCredentialsNotFoundException("User not found");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswd(),
				user.isEnable(), true, true, true, getAuthorities(user.getAuths()));
	}

	public MessageSource getMessage() {
		return messages;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Auth> auths) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		auths.forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getRoleName())));
		return authorities;
	}

}
