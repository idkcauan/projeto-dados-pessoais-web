package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping
	public List<PessoaDto> obterPessoas() {
		return pessoaService.obterPessoas();
	}

	@GetMapping("/{userName}")
	public PessoaDto obterPessoa(@PathVariable("userName") String userName) {
		Optional<PessoaDto> optionalPessoa = pessoaService.obterPessoa(userName);
		if(optionalPessoa.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return optionalPessoa.get();
	}

	@PostMapping("/sem-validacao")
	public ResponseEntity<?> incluirNovo(@RequestBody PessoaDto pessoa) {
		pessoaService.incluirNovaPessoa(pessoa);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pessoas/{userName}")
			.buildAndExpand(pessoa.getUserName()).toUri();
		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<?> incluirNovoComValidacao(@RequestBody @Valid PessoaDto pessoa) {
		pessoaService.incluirNovaPessoa(pessoa);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pessoas/{userName}")
			.buildAndExpand(pessoa.getUserName()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{userName}")
	public ResponseEntity<?> atualizar(@PathVariable("userName") String userName, @RequestBody @Valid PessoaAlteracaoDto pessoa) {
		PessoaDto pessoaAlterada = pessoaService.alterarPessoa(userName, pessoa);
		return ResponseEntity.ok().body(pessoaAlterada);
	}

	@DeleteMapping("/{userName}")
	public ResponseEntity<?> remover(@PathVariable("userName") String userName) {
		pessoaService.removerPessoa(userName);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<ProblemDetail> tratarExcecao(NaoEncontradoException ex) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
		return ResponseEntity.of(pd).build();
	}

}
