package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioSistema implements UserDetails {
	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public UsuarioSistema(String username, String password, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonProperty("senhaCodificada")
	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonProperty("roles")
	public List<String> getRoles() {
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

}
