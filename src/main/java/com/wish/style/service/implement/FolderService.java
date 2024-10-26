package com.wish.style.service.implement;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wish.style.entities.Document;
import com.wish.style.entities.Folder;
import com.wish.style.repository.DocumentRepository;
import com.wish.style.repository.FolderRepository;
import com.wish.style.service.IFolderService;

@Service
public class FolderService implements IFolderService{
	
	@Autowired
	private FolderRepository folderRepo;

	@Autowired
	private DocumentRepository docRepo;
	
	@Override
	public List<Folder> getAll() {
		return folderRepo.findAll();
	}

	@Override
	public Folder addToFolder(Folder folder) {
		Folder fold=new Folder();
		fold.setDocument_id(folder.getDocument_id());
		fold.setFolderId(folder.getFolderId());
		fold.setUser_id(folder.getUser_id());
		folderRepo.save(fold);
		return fold;
	}

	@Override
	public String delete(Long id) {
		folderRepo.deleteById(id);
		return "Deleted Successfully";
	}

	@Override
	public Optional<Folder> getFolderById(Long id) {
		return folderRepo.findById(id);
	}
	
	@Override
	public List<Folder> getFileByFolderId(Long id) {
		Document doc=docRepo.findById(id).orElseThrow();
		List<Folder> folders=folderRepo.findByFolderId(doc.getId());
		return folders;
	}

	@Override
	public String moveToOtherFolder(Long docId, Long fromId, Long toId) {
		Folder fold=new Folder();
		fold.setDocument_id(docId);
		fold.setFolderId(toId);
		folderRepo.save(fold);
		folderRepo.deleteById(fromId);
		return "Move Completed";
	}
	
}
