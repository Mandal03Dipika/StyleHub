package com.wish.style.service;

import java.util.List;
import java.util.Optional;
import com.wish.style.dto.DocumentDto;
import com.wish.style.dto.DocumentFolderDto;
import com.wish.style.dto.DocumentResponseDto;
import com.wish.style.entities.Document;

public interface IDocumentService {
	public String delete(Long id);
	public List<Document> getAll();
	public Optional<Document> getFileById(Long id);
	public List<Document> getFileByUserId(Long id);
	public List<DocumentDto> getFilesByUser(Long userId);
	public Document create(DocumentResponseDto documentDto);
	public Document createFolder(DocumentFolderDto documentDto);
}
