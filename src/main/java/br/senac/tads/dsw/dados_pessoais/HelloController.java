package br.senac.tads.dsw.dados_pessoais;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping(value = "/hello-manual", produces = MediaType.APPLICATION_JSON_VALUE)
	public String helloManual() throws JacksonException {
		Mensagem mensagem = new Mensagem("Cauan Lima Fernandes", "Json gerado manualmente com JsonMapper");
		JsonMapper mapper = new JsonMapper();
		return mapper.writeValueAsString(mensagem);
	}
}
