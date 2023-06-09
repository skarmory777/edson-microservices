package br.com.api.testeedson.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.api.testeedson.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
