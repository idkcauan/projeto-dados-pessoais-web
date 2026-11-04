package br.senac.tads.dsw.dados_pessoais.repositorio;

import br.senac.tads.dsw.dados_pessoais.entidade.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

	Optional<RoleEntity> findByNome(String nome);
}
