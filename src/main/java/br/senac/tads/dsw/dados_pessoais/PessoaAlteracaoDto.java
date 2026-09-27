package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.*;

public class PessoaAlteracaoDto {

	@NotBlank(message = "O nome completo é obrigatório")
	@Size(max = 100)
	private String nome;

	@NotBlank
	@Size(max = 100)
	private String email;

	@Size(max = 20)
	private String telefone;

	@NotNull
	@PastOrPresent
	private LocalDate dataNascimento;

	private List<String> conhecimentos;

}
