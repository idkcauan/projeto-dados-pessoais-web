package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class  PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping
	public List<PessoaDto> obterPessoas() {
		return pessoaService.obterPessoas();
	}

	@GetMapping("/busca")
	public ResponseEntity<List<PessoaDto>> buscarPessoas(@RequestParam("q") String termo) {
		return ResponseEntity.ok(pessoaService.buscarPessoas(termo));
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

	@PostMapping
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
