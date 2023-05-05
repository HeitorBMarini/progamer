package br.com.fiap.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.file.UploadedFile;

import br.com.fiap.dao.SetupDAO;
import br.com.fiap.model.Setup;
import br.com.fiap.service.UploadService;

@Named
@RequestScoped
public class SetupBean {
	Setup setup = new Setup();

	@Inject
	private SetupDAO setupDao;
	
    private Setup selectedSetup;

    private UploadedFile file;
    
	public Setup getSetup() {
		return setup;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}
	
	@Transactional
	public void save() {
		
		UploadService upload = new UploadService();
		
		if(setup.getName()!="" && setup.getDescription()!="") {
			
			upload.salvarArquivo(getFile());
			setup.setFile(getFile().getFileName());
			
			setupDao.salvar(setup);
			this.setup = new Setup();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "As informações foram salvas com sucesso.", "INFO"));
		} else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha todos os campos", "ERROR"));
		}
	}
	
	public List<Setup> findAll(){
		return setupDao.findAllSetups();
	}

	public Setup getSelectedSetup() {
		return selectedSetup;
	}

	public void setSelectedSetup(Setup selectedSetup) {
		this.selectedSetup = selectedSetup;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
