package br.com.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.fiap.model.Profile;

@Component
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
