package com.wish.style.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wish.style.dto.DocumentDto;
import com.wish.style.dto.DocumentFolderDto;
import com.wish.style.dto.DocumentResponseDto;
import com.wish.style.entities.Document;
import com.wish.style.service.IDocumentService;
import io.jsonwebtoken.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/doc")
public class DocumentController {

	@Autowired
	IDocumentService docService;
	
	@PostMapping("/upload")
	public String fileUpload(@ModelAttribute DocumentResponseDto documentDto)
	{
		try
		{
			docService.create(documentDto);
			return "Uploaded Successfully";
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "Error Found";
		}
	}
	
	@GetMapping("/files")
	public List<Document> get() {
		try {
			List<Document> files = docService.getAll();
			return files;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable String id) {
		try {
			long id1 = Long.parseLong(id);
			return docService.delete(id1);

		} catch (IOException e) {
			e.printStackTrace();
			return "Error";
		}

	}

	@GetMapping("/get/{id}")
	public Optional<Document> getById(@PathVariable String id) {
		long id1 = Long.parseLong(id);
		return docService.getFileById(id1);
	}

	@GetMapping("/get/user/{id}")
	public List<Document> getByUserId(@PathVariable String id) {
		long id1 = Long.parseLong(id);
		return docService.getFileByUserId(id1);
	}

	@GetMapping("/get/file/{id}")
	public List<DocumentDto> getFilesByUser(@PathVariable String id) {
		long id1 = Long.parseLong(id);
		return docService.getFilesByUser(id1);
	}

	@PostMapping("/folder")
	public String folderCreate(@RequestBody DocumentFolderDto documentDto)
	{
		try
		{
			docService.createFolder(documentDto);
			return "Uploaded Successfully";
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "Error Found";
		}
	}
	
}
