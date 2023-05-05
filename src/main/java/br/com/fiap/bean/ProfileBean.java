package br.com.fiap.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import br.com.fiap.dao.ProfileDAO;
import br.com.fiap.model.Profile;

@Named
@RequestScoped
public class ProfileBean {

	Profile profile = new Profile();

	private Profile selectedProfile;
	
	@Inject
	private ProfileDAO profileDAO;
	
	@Transactional
	public void save() {
		if(this.profile.getName() != null) {
			profileDAO.salvar(this.profile);
			this.profile = new Profile();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile salvo com sucesso.", "INFO"));
		}
		
        PrimeFaces.current().ajax().update("form:messages", "form:profileTable");
	}
	
	public List<Profile> findAll(){
		return this.profileDAO.findAll();
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getSelectedProfile() {
		return selectedProfile;
	}

	public void openNew() {
        this.selectedProfile = new Profile();
    }
	
	public void setSelectedProfile(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

}
