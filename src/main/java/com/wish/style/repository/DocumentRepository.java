package com.wish.style.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wish.style.entities.Document;
import com.wish.style.entities.User;

public interface DocumentRepository extends JpaRepository<Document, Long>{
	List<Document> findByUser(User user);
}
