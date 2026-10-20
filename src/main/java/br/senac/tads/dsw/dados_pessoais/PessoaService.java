package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

	List <PessoaDto> obterPessoas();
	Optional<PessoaDto> obterPessoa(String userName);
	PessoaDto incluirNovaPessoa(PessoaDto pessoa);
	PessoaDto alterarPessoa(String userName, PessoaAlteracaoDto pessoaAlteracao);
	void removerPessoa(String userName);
	List<PessoaDto> buscarPessoas(String termo);
}
