package br.com.gilson.saveserver.api.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.gilson.saveserver.api.service.MyFileService;


@Service
public class MyFileServiceImpl implements MyFileService{

	@Override
	public File getDirectory(String rootDirectory, String resourceDirectory) {
		File directory = new File(rootDirectory, resourceDirectory);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

	@Override
	public File buildFile(File directory, String fileName) throws IOException {
		File file = new File(directory, fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	@Override
	public void writeFile(File fileResource, MultipartFile fileForSave) throws IOException {
		FileOutputStream out = new FileOutputStream(fileResource);
		out.write(fileForSave.getBytes());
		out.flush();
		out.close();
	}

	@Override
	public String buildUrlFileSaved(HttpServletRequest request, String pathResource, String originalFilename) {
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String server = url.substring(0, url.length() - uri.length());
		return String.format("%s/%s/%s", server, pathResource, originalFilename);
	}
}
