package br.com.fiap.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.core.env.Profiles;
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

import br.com.fiap.dao.ProfileDAO;
import br.com.fiap.model.Profile;
import br.com.fiap.repository.ProfileRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/profiles")
@Api(value = "MyProfile API")
public class ProfileController {

	@Inject
	private ProfileDAO profileDao;
	
	@Inject
	private ProfileRepository repository;

   
    @GetMapping("{id}")
    
	@ApiOperation("Obter profile por ID")
	public ResponseEntity<Profile> show(@PathVariable("id") long id) {

		Profile profile = profileDao.buscarPorId(id);

		if (profile == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(profile);
	}

    @GetMapping()    
	@ApiOperation("Obter todos os profiles")
	public ResponseEntity<List<Profile>> show() {

		List<Profile> profiles = profileDao.findAll();

		if (profiles == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(profiles);
	}
    @PostMapping()
	@ApiOperation("Criar setup novo")
	public ResponseEntity<String> create(@RequestBody Profile setupRequest) {
		Profile setup = new Profile();
		try {
			if (setupRequest.getName() == null || setupRequest.getEmail() == null || setupRequest.getPassword() == null
					|| setupRequest.getProfile() == null) {

				System.out.println("===== ERROR MY FRIEND =====");
				System.out.println("Parâmetros insuficientes para criar");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

			}

			setup.setName(setupRequest.getName());
			setup.setEmail(setupRequest.getEmail());
			setup.setPassword(setupRequest.getPassword());
			

			profileDao.salvar(setup);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

    @PutMapping("{id}") //ATUALIZA O RECURSO POR COMPLETO
	@ApiOperation("Atualização de Setup")
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Profile setupRequest) {

		try {
			Profile profile = profileDao.buscarPorId(id);

			if (profile== null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			profile.setName(setupRequest.getName());
			profile.setEmail(setupRequest.getEmail());
			profile.setProfile(setupRequest.getProfile());
			profile.setPassword(setupRequest.getPassword());

			profileDao.salvar(profile);

			return ResponseEntity.ok("Setup atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * @param id
	 * @param profileRequest
	 * @return
	 */
	@PatchMapping("{id}") //MODIFICACOES PARCIAIS
	@ApiOperation("Atualização parcial de Profile")
	public ResponseEntity<String> patch(@PathVariable("id") long id, @RequestBody Profile profileRequest) {

		try {
			Profile profile = profileDao.buscarPorId(id);

			if (profile == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			if (profileRequest.getName() != null)
				profile.setName(profileRequest.getName());
			if (profileRequest.getEmail() != null)
				profile.setEmail(profileRequest.getEmail());
			if (profileRequest.getPassword() != null)
            profile.setPassword(profileRequest.getPassword());

			if (profileRequest.getProfile() != null)
				profile.setProfile(profileRequest.getProfile());

			profileDao.salvar(profile);

			return ResponseEntity.ok("profile atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
        @DeleteMapping("{id}")
        @ApiOperation("Remover perfil do profile")
        public ResponseEntity<String> delete(@PathVariable("id") long id) {
            try {
                Profile profile = profileDao.buscarPorId(id);
    
                if (profile == null)
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    
                profileDao.remover(id);
    
                return ResponseEntity.ok("Perfil removido com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

		@DeleteMapping("delete/{id}")
	@ApiOperation("Excluindo Profile com JPA")
	public ResponseEntity<String> delete2(@PathVariable("id") long id){
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
    