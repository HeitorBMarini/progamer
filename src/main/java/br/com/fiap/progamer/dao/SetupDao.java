package br.com.fiap.progamer.dao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.fiap.progamer.model.SetupModel;

@Named
public class SetupDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SetupDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public void save(SetupModel setupModel) {
		this.entityManager.merge(setupModel);
	}
	
}
