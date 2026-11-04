package br.senac.tads.dsw.dados_pessoais.entidade;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String nome;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
