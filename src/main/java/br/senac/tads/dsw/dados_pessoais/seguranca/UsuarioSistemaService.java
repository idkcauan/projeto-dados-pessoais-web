package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.UsuarioSistemaRepository;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UsuarioSistemaService implements UserDetailsService {

	private final UsuarioSistemaRepository usuarioSistemaRepository;

	private final Map<String, UserDetails> usuarios;

	public UsuarioSistemaService(PasswordEncoder passwordEncoder, UsuarioSistemaRepository usuarioSistemaRepository) {
		this.usuarioSistemaRepository = usuarioSistemaRepository;

		this.usuarios = new HashMap<>();

		this.usuarios.put("admin", new UsuarioSistema(
			"admin",
			passwordEncoder.encode("Abcd%12345"), List.of(
				new SimpleGrantedAuthority("ROLE_ADMIN"),
				new SimpleGrantedAuthority("ROLE_GERENTE"),
				new SimpleGrantedAuthority("ROLE_USER"))
		));

		this.usuarios.put("user1", new UsuarioSistema(
			"user1",
			passwordEncoder.encode("Abcd%12345"), List.of(
				new SimpleGrantedAuthority("ROLE_GERENTE"),
				new SimpleGrantedAuthority("ROLE_USER"))
		));

		this.usuarios.put("user2", new UsuarioSistema("user2", passwordEncoder.
			encode("Abcd%12345"), List.of(new SimpleGrantedAuthority("ROLE_USER"))
		));
	}

	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {

		UsuarioSistemaEntity usuario = usuarioSistemaRepository.findByUsername(username)
			.orElseThrow(() ->
				new UsernameNotFoundException("Usuário não encontrado"));

	List<GrantedAuthority> authorities =
		usuario.getRoles().stream().<GrantedAuthority>map(role ->
			new SimpleGrantedAuthority(role.getNome())).toList();
			return new UsuarioSistema(
				usuario.getUsername(),
				usuario.getSenha(),
				authorities
			);
		}

}

