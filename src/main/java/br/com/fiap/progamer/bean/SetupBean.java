package br.com.fiap.progamer.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

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

	@Transactional
	public void save() {
		if(this.setupModel.getName()!="" && this.setupModel.getDescription()!="") {
			setupDao.save(this.setupModel);
			this.setupModel = new SetupModel();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"As informações foram salvas com sucesso!","INFO"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar salvar!","ERROR"));
		}
	}
}
