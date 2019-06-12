package br.com.gilson.saveserver.api.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MyFileService {

	File getDirectory(String rootDirectory, String resourceDirectory);
	
	File buildFile(File directory, String fileName) throws IOException;
	
	void writeFile(File fileResource, MultipartFile fileForSave) throws IOException;
	
	String buildUrlFileSaved(HttpServletRequest request, String pathResource, String originalFilename);
	
}
