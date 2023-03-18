package br.com.fiap.progamer.dao;

import java.util.ArrayList;

import javax.inject.Named;

import br.com.fiap.progamer.model.SetupModel;

@Named
public class SetupDao {
	
	ArrayList<SetupModel> setups = new ArrayList<>();
	
	public void salvar(SetupModel setupModel) {
		setups.add(setupModel);
	}
	
	public void listarSetups() {
		setups.forEach(System.out::println);
	}
}
