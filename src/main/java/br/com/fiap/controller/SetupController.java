package br.com.fiap.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dao.SetupDAO;
import br.com.fiap.model.Setup;
import br.com.fiap.repository.SetupRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/setups")
@Api(value = "Progamer API")
public class SetupController {

	@Inject
	private SetupDAO setupDao;
	
	@Inject
	private SetupRepository repository;

	@GetMapping()
	@ApiOperation("Obter todos os setups")
	public ResponseEntity<List<Setup>> index() {
		return ResponseEntity.ok(setupDao.findAllSetups());
	}

	@GetMapping("{id}")
	@ApiOperation("Obter setup por ID")
	public ResponseEntity<Setup> show(@PathVariable("id") long id) {

		Setup setup = setupDao.buscarPorId(id);

		if (setup == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(setup);
	}

	@PostMapping()
	@ApiOperation("Criar setup novo")
	public ResponseEntity<String> create(@RequestBody Setup setupRequest) {
		Setup setup = new Setup();
		try {
			if (setupRequest.getName() == null || setupRequest.getDescription() == null || setupRequest.getPrice() == 0
					|| setupRequest.getFile() == null) {

				System.out.println("===== ERROR =====");
				System.out.println("Parâmetros insuficientes");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

			}

			setup.setName(setupRequest.getName());
			setup.setDescription(setupRequest.getDescription());
			setup.setPrice(setupRequest.getPrice());
			setup.setFile(setupRequest.getFile());

			setupDao.salvar(setup);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("{id}") //ATUALIZA O RECURSO POR COMPLETO
	@ApiOperation("Atualização de Setup")
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Setup setupRequest) {

		try {
			Setup setup = setupDao.buscarPorId(id);

			if (setup == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			setup.setName(setupRequest.getName());
			setup.setDescription(setupRequest.getDescription());
			setup.setPrice(setupRequest.getPrice());
			setup.setFile(setupRequest.getFile());

			setupDao.salvar(setup);

			return ResponseEntity.ok("Setup atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("{id}") //MODIFICACOES PARCIAIS
	@ApiOperation("Atualização parcial de Setups")
	public ResponseEntity<String> patch(@PathVariable("id") long id, @RequestBody Setup setupRequest) {

		try {
			Setup setup = setupDao.buscarPorId(id);

			if (setup == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			if (setupRequest.getName() != null)
				setup.setName(setupRequest.getName());
			if (setupRequest.getDescription() != null)
				setup.setDescription(setupRequest.getDescription());
			if (setupRequest.getPrice() > 0)
				setup.setPrice(setupRequest.getPrice());
			if (setupRequest.getFile() != null)
				setup.setFile(setupRequest.getFile());

			setupDao.salvar(setup);

			return ResponseEntity.ok("Setup atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("{id}")
	@ApiOperation("Excluir Setup")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			Setup setup = setupDao.buscarPorId(id);
			if (setup == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			setupDao.deletar(setup);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("delete/{id}")
	@ApiOperation("Excluindo Setup com JPA")
	public ResponseEntity<String> delete2(@PathVariable("id") long id){
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
