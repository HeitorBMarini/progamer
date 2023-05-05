package br.com.fiap.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.model.file.UploadedFile;

public class UploadService {

	private static final String IMAGE_FOLDER = "D:\\AULAS-FIAP\\TDS - Digital Business Enablement\\progamer\\src\\main\\webapp\\resources\\images";
	
	public void salvarArquivo(UploadedFile file) {
		
		InputStream input = null;
		OutputStream output = null;
		
		try {
			input = file.getInputStream();
			output = new FileOutputStream( 
						new File(IMAGE_FOLDER, file.getFileName())
					);
			IOUtils.copy(input, output);
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fazer upload do arquivo.", "Error")
					);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
}
