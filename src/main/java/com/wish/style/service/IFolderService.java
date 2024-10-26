package com.wish.style.service;

import java.util.List;
import java.util.Optional;
import com.wish.style.entities.Folder;

public interface IFolderService {
	public Folder addToFolder(Folder folder);
	public List<Folder> getAll();
	public String delete(Long id);
	public Optional<Folder> getFolderById(Long id);
	public List<Folder> getFileByFolderId(Long id);
	public String moveToOtherFolder(Long docId,Long fromId, Long toId);
}
