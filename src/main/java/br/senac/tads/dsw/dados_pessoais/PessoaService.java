package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class PessoaService {

	private AtomicInteger contador = new AtomicInteger(0);
	private Map<String, PessoaDto> mapPessoas = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		mapPessoas.put("fulano", new PessoaDto(contador.incrementAndGet(),
			"fulano", "Fulano da Silva", "fulano@email.com", "(11) 99999-1234", LocalDate.parse("2000-10-20")));
		mapPessoas.put("ciclano", new PessoaDto(contador.incrementAndGet(),
			"ciclano", "Ciclano de Souza", "ciclano@email.com", "(11) 98888-5678", LocalDate.parse("1999-05-10")));
		mapPessoas.put("beltrana", new PessoaDto(contador.incrementAndGet(),
			"beltrana", "Beltrana dos Santos", "beltrana@email.com", "(11) 97777-9012", LocalDate.parse("2001-02-23")));
	}

	public List<PessoaDto> obterPessoas() {
		return new ArrayList<>(mapPessoas.values());
	}

	public Optional<PessoaDto> obterPessoa(String userName) {
		return Optional.ofNullable(mapPessoas.get(userName));
	}

	public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
		pessoa.setId(contador.incrementAndGet());
		mapPessoas.put(pessoa.getUserName(), pessoa);
		return pessoa;
	}

	public PessoaDto alterarPessoa(String userName, PessoaAlteracaoDto pessoaAlteracaoDto) {
		if(!mapPessoas.containsKey(userName)) {
			throw new NaoEncontradoException("Pessoa" + userName + " não encontrada");
		}
		PessoaDto pessoaOriginal = mapPessoas.get(userName);
		pessoaOriginal.setNome(pessoaAlteracaoDto.getNome());
		pessoaOriginal.setEmail(pessoaAlteracaoDto.getEmail());
		pessoaOriginal.setTelefone(pessoaAlteracaoDto.getTelefone());
		pessoaOriginal.setDataNascimento(pessoaAlteracaoDto.getDataNascimento());
		pessoaOriginal.setConhecimentos(pessoaAlteracaoDto.getConhecimentos());
		return pessoaOriginal;
	}

	public void removerPessoa(String userName) {
		if(!mapPessoas.containsKey(userName)) {
			throw new NaoEncontradoException("Pessoa" + userName + " não encontrada");
		}
		mapPessoas.remove(userName);
	}
}

