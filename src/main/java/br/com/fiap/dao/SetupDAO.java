package br.com.fiap.dao;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import br.com.fiap.model.Setup;

@Named
@ViewScoped
public class SetupDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public SetupDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void salvar(Setup setup) {
		entityManager.merge(setup);
	}

	public Setup buscarPorId(Long id) {
		return entityManager.find(Setup.class, id);
	}

	public List<Setup> findAllSetups() {
		@SuppressWarnings("unchecked")
		TypedQuery<Setup> query = (TypedQuery<Setup>) entityManager.createQuery("SELECT e FROM Setup e");
		return query.getResultList();
	}

	public List<Setup> findAllSetups2() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Setup> cq = cb.createQuery(Setup.class);
		Root<Setup> rootEntry = cq.from(Setup.class);
		CriteriaQuery<Setup> all = cq.select(rootEntry);
		TypedQuery<Setup> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}
}