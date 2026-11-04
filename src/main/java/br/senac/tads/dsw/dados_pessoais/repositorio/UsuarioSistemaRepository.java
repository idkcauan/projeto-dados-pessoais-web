package br.senac.tads.dsw.dados_pessoais.repositorio;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistemaEntity, Integer> {

	Optional<UsuarioSistemaEntity> findByUsername(String username);
}
