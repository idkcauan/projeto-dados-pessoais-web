package br.senac.tads.dsw.dados_pessoais.entidade;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_usuarios_sistema")
public class UsuarioSistemaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "tb_usuarios_roles",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<RoleEntity> roles = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
}
