package com.wish.style.service.implement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wish.style.dto.DocumentDto;
import com.wish.style.dto.DocumentFolderDto;
import com.wish.style.dto.DocumentResponseDto;
import com.wish.style.entities.Document;
import com.wish.style.entities.Folder;
import com.wish.style.entities.User;
import com.wish.style.repository.DocumentRepository;
import com.wish.style.repository.UserRepository;
import com.wish.style.service.IDocumentService;

@Service
public class DocumentService implements IDocumentService{
	
	@Autowired
	private DocumentRepository docRepo;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ModelMapper mapper;
	@Autowired
    private UserRepository userRepo;
	
	
	@Override
	public String delete(Long id) {
		docRepo.deleteById(id);
		return "Deleted Successfully";
	}

	@Override
	public List<Document> getAll() {
		return docRepo.findAll();
	}

	@Override
	public Optional<Document> getFileById(Long id) {
		return docRepo.findById(id);
	}

	@Override
	public List<Document> getFileByUserId(Long id) {
		User user=userRepo.findById(id).orElseThrow();
		List<Document> documents = docRepo.findByUser(user);
		return documents;
	}

	@Override
	public List<DocumentDto> getFilesByUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow();
        List<Document> documents = docRepo.findByUser(user);
        List<DocumentDto> documentDTOs = documents.stream()
                .map(document -> mapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
        return documentDTOs;
	}

	@Override
	public Document create(DocumentResponseDto documentDto) {
		MultipartFile file=documentDto.getFile();
		if(file.isEmpty())
		{
			return null;
		}
		try
		{
			String path1 = "src/main/resources/static/documents/";
			String time = String.valueOf(System.currentTimeMillis());
			String fileName = time + file.getOriginalFilename();
			Path path = Paths.get(path1 + fileName);
			Files.createDirectories(path.getParent());
			Files.copy(file.getInputStream(), path);
//			String path1="src/main/resources/static/documents/";
//			String time=String.valueOf(System.currentTimeMillis());
//			Path path=Paths.get(path1+time+file.getOriginalFilename());
//			Files.copy(file.getInputStream(),path);
			User user=userRepo.findById(documentDto.getUserId()).orElse(null);
			Document doc=new Document();
			doc.setCreated_at(new Date(System.currentTimeMillis()));
			doc.setOriginal_name(documentDto.getFile().getOriginalFilename());
			doc.setPath(path1.concat(fileName)); 
//			doc.setPath(path1+time+file.getOriginalFilename());
			doc.setUser(user);
			String extension=documentDto.getFile()
					.getOriginalFilename()
					.substring(documentDto.getFile()
							.getOriginalFilename()
							.lastIndexOf(".")+1);
			doc.setExtension(extension);
			doc.setFile_name(time+file.getOriginalFilename());
			doc.setUpdated_at(new Date(System.currentTimeMillis()));
			doc.setMime_type(file.getContentType());
			if(extension.equals("jpg") ||
					extension.equals("jpeg") || 
					extension.equals("png"))
			{
				doc.setDocument_type("image");
			}
			else
			{
				doc.setDocument_type("doc");
			}
			docRepo.save(doc);
			Folder folder=new Folder();
			folder.setDocument_id(doc.getId());
			folder.setFolderId(user.getId());
			folder.setUser_id(user.getId());
			folderService.addToFolder(folder);
			return doc;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Document createFolder(DocumentFolderDto documentDto) {
		String path="src/main/resources/static/documents/";
		User user=userRepo.findById(documentDto.getUserId()).orElse(null);
		Document doc=new Document();
		doc.setCreated_at(new Date(System.currentTimeMillis()));
		doc.setExtension("folder");
		doc.setDocument_type("folder");
		doc.setMime_type("folder");
		path=path.concat("/");
		doc.setPath(path.concat(documentDto.getFile_name()));
		String randomId=UUID.randomUUID().toString();
		String name=randomId
				.concat(documentDto.getFile_name());
		doc.setFile_name(name);
		doc.setOriginal_name(documentDto.getOriginal_name());
		doc.setUser(user);
		doc.setUpdated_at(new Date(System.currentTimeMillis()));
		docRepo.save(doc);
		return doc;
		
	}

}
