package com.auditdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface BookRepository extends RevisionRepository<Book, Long, Integer>, JpaRepository<Book, Long> {
}
