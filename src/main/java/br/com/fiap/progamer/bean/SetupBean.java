package br.com.fiap.progamer.bean;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fiap.progamer.dao.SetupDao;
import br.com.fiap.progamer.model.SetupModel;

@Named
@RequestScoped
public class SetupBean {

	SetupModel setupModel = new SetupModel();
	
	@Inject
	private SetupDao setupDao;
	
	
	public SetupModel getSetupModel() {
		return setupModel;
	}

	public void setSetupModel(SetupModel setupModel) {
		this.setupModel = setupModel;
	}

	public void save() {
		this.setupDao.salvar(this.setupModel);
		this.setupDao.listarSetups();
	}
}
