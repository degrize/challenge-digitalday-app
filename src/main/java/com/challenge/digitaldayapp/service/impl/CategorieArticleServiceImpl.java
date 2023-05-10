package com.challenge.digitaldayapp.service.impl;

import com.challenge.digitaldayapp.domain.CategorieArticle;
import com.challenge.digitaldayapp.repository.CategorieArticleRepository;
import com.challenge.digitaldayapp.service.CategorieArticleService;
import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import com.challenge.digitaldayapp.service.mapper.CategorieArticleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieArticle}.
 */
@Service
@Transactional
public class CategorieArticleServiceImpl implements CategorieArticleService {

    private final Logger log = LoggerFactory.getLogger(CategorieArticleServiceImpl.class);

    private final CategorieArticleRepository categorieArticleRepository;

    private final CategorieArticleMapper categorieArticleMapper;

    public CategorieArticleServiceImpl(
        CategorieArticleRepository categorieArticleRepository,
        CategorieArticleMapper categorieArticleMapper
    ) {
        this.categorieArticleRepository = categorieArticleRepository;
        this.categorieArticleMapper = categorieArticleMapper;
    }

    @Override
    public CategorieArticleDTO save(CategorieArticleDTO categorieArticleDTO) {
        log.debug("Request to save CategorieArticle : {}", categorieArticleDTO);
        CategorieArticle categorieArticle = categorieArticleMapper.toEntity(categorieArticleDTO);
        categorieArticle = categorieArticleRepository.save(categorieArticle);
        return categorieArticleMapper.toDto(categorieArticle);
    }

    @Override
    public CategorieArticleDTO update(CategorieArticleDTO categorieArticleDTO) {
        log.debug("Request to update CategorieArticle : {}", categorieArticleDTO);
        CategorieArticle categorieArticle = categorieArticleMapper.toEntity(categorieArticleDTO);
        categorieArticle = categorieArticleRepository.save(categorieArticle);
        return categorieArticleMapper.toDto(categorieArticle);
    }

    @Override
    public Optional<CategorieArticleDTO> partialUpdate(CategorieArticleDTO categorieArticleDTO) {
        log.debug("Request to partially update CategorieArticle : {}", categorieArticleDTO);

        return categorieArticleRepository
            .findById(categorieArticleDTO.getId())
            .map(existingCategorieArticle -> {
                categorieArticleMapper.partialUpdate(existingCategorieArticle, categorieArticleDTO);

                return existingCategorieArticle;
            })
            .map(categorieArticleRepository::save)
            .map(categorieArticleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieArticles");
        return categorieArticleRepository.findAll(pageable).map(categorieArticleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieArticleDTO> findOne(Long id) {
        log.debug("Request to get CategorieArticle : {}", id);
        return categorieArticleRepository.findById(id).map(categorieArticleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieArticle : {}", id);
        categorieArticleRepository.deleteById(id);
    }
}
