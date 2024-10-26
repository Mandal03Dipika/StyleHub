package com.wish.style.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wish.style.entities.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long>{
	List<Folder> findByFolderId(Long folderId);
}
