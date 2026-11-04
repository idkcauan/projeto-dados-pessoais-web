package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.senac.tads.dsw.dados_pessoais.entidade.ConhecimentoEntity;
import br.senac.tads.dsw.dados_pessoais.entidade.PessoaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.ConhecimentoRepository;
import br.senac.tads.dsw.dados_pessoais.repositorio.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import br.senac.tads.dsw.dados_pessoais.entidade.RoleEntity;
import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.RoleRepository;
import br.senac.tads.dsw.dados_pessoais.repositorio.UsuarioSistemaRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.Data;

@Component
public class DataInitializer implements CommandLineRunner {

	private final PessoaRepository pessoaRepository;
	private final ConhecimentoRepository conhecimentoRepository;
	private final UsuarioSistemaRepository usuarioSistemaRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;


	public DataInitializer(PessoaRepository pessoaRepository, ConhecimentoRepository conhecimentoRepository,
						   UsuarioSistemaRepository usuarioSistemaRepository, RoleRepository roleRepository,
						   PasswordEncoder passwordEncoder) {

		this.pessoaRepository = pessoaRepository;
		this.conhecimentoRepository = conhecimentoRepository;
		this.usuarioSistemaRepository = usuarioSistemaRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		List<String> conhecimentos = List.of("Java", "Javascript", "SQL", "HTML", "CSS", "Docker", "Angular", "React");
		Map<String, ConhecimentoEntity> mapConhecimentos = new HashMap<>();

		if(pessoaRepository.count() == 0) {
			PessoaEntity p1 = new PessoaEntity();
			p1.setUsername("jpinkman");
			p1.setNome("Jesse Pinkman");
			p1.setEmail("jpinkman@email.com");
			p1.setTelefone("(11) 99000-9999");
			p1.setDataNascimento(LocalDate.parse("2000-10-20"));
			p1.setSenha("Abcd%12345");
			p1.getConhecimentos().add(mapConhecimentos.get("Java"));
			p1.getConhecimentos().add(mapConhecimentos.get("SQL"));
			p1.getConhecimentos().add(mapConhecimentos.get("Docker"));
			pessoaRepository.save(p1);

			PessoaEntity p2 = new PessoaEntity();
			p2.setUsername("ironman");
			p2.setNome("Tony Stark");
			p2.setEmail("ironman@email.com");
			p2.setTelefone("(21) 98000-8888");
			p2.setDataNascimento(LocalDate.parse("1999-05-10"));
			p2.setSenha("Abcd%12345");
			p2.getConhecimentos().add(mapConhecimentos.get("Angular"));
			p2.getConhecimentos().add(mapConhecimentos.get("Javascript"));
			p2.getConhecimentos().add(mapConhecimentos.get("HTML"));
			p2.getConhecimentos().add(mapConhecimentos.get("CSS"));
			pessoaRepository.save(p2);

			PessoaEntity p3 = new PessoaEntity();
			p3.setUsername("eleven");
			p3.setNome("Eleven Hopper");
			p3.setEmail("eleven@email.com");
			p3.setTelefone("(31) 97000-7777");
			p3.setDataNascimento(LocalDate.parse("2001-02-23"));
			p3.setSenha("Abcd%12345");
			p3.getConhecimentos().add(mapConhecimentos.get("Java"));
			p3.getConhecimentos().add(mapConhecimentos.get("React"));
			p3.getConhecimentos().add(mapConhecimentos.get("SQL"));
			pessoaRepository.save(p3);
		}

		if (usuarioSistemaRepository.count() == 0) {

			// Criando as permissões
			RoleEntity admin = criarRole("ROLE_ADMIN");
			RoleEntity gerente = criarRole("ROLE_GERENTE");
			RoleEntity user = criarRole("ROLE_USER");

			// Criando os usuários
			criarUsuario("admin", Set.of(admin, gerente, user));

			criarUsuario("user1", Set.of(gerente, user));

			criarUsuario("user2", Set.of(user));
		}
	}

	private RoleEntity criarRole(String nome) {

		return roleRepository.findByNome(nome)
			.orElseGet(() -> {
				RoleEntity role = new RoleEntity();
				role.setNome(nome);
				return roleRepository.save(role);
			});
	}

	private void criarUsuario(String username, Set<RoleEntity> roles) {

		UsuarioSistemaEntity usuario = new UsuarioSistemaEntity();

		usuario.setUsername(username);
		usuario.setSenha(passwordEncoder.encode("Abcd%12345"));
		usuario.setRoles(roles);

		usuarioSistemaRepository.save(usuario);
	}

}
