package com.challenge.digitaldayapp.repository;

import com.challenge.digitaldayapp.domain.CategorieArticle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategorieArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieArticleRepository extends JpaRepository<CategorieArticle, Long> {}
