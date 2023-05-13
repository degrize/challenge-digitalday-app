package com.challenge.digitaldayapp.service.impl;

import com.challenge.digitaldayapp.domain.Client;
import com.challenge.digitaldayapp.domain.Vente;
import com.challenge.digitaldayapp.repository.ClientRepository;
import com.challenge.digitaldayapp.repository.VenteRepository;
import com.challenge.digitaldayapp.service.VenteService;
import com.challenge.digitaldayapp.service.dto.VenteDTO;
import com.challenge.digitaldayapp.service.mapper.VenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vente}.
 */
@Service
@Transactional
public class VenteServiceImpl implements VenteService {

    private final Logger log = LoggerFactory.getLogger(VenteServiceImpl.class);

    private final VenteRepository venteRepository;

    @Autowired
    private ClientRepository clientRepository;

    private final VenteMapper venteMapper;

    public VenteServiceImpl(VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }

    @Override
    public VenteDTO save(VenteDTO venteDTO) {
        log.debug("Request to save Vente : {}", venteDTO);
        Vente vente = venteMapper.toEntity(venteDTO);

        vente = venteRepository.save(vente);

        if (vente.getClient().getId() != null) {
            Client client = clientRepository.getReferenceById(vente.getClient().getId());
            if (client.getFidelite() == null) {
                client.setFidelite(0);
            }
            client.setFidelite(client.getFidelite() + 1);

            clientRepository.save(client);
        }

        return venteMapper.toDto(vente);
    }

    @Override
    public VenteDTO update(VenteDTO venteDTO) {
        log.debug("Request to update Vente : {}", venteDTO);
        Vente vente = venteMapper.toEntity(venteDTO);
        vente = venteRepository.save(vente);
        return venteMapper.toDto(vente);
    }

    @Override
    public Optional<VenteDTO> partialUpdate(VenteDTO venteDTO) {
        log.debug("Request to partially update Vente : {}", venteDTO);

        return venteRepository
            .findById(venteDTO.getId())
            .map(existingVente -> {
                venteMapper.partialUpdate(existingVente, venteDTO);

                return existingVente;
            })
            .map(venteRepository::save)
            .map(venteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventes");
        return venteRepository.findAll(pageable).map(venteMapper::toDto);
    }

    public Page<VenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return venteRepository.findAllWithEagerRelationships(pageable).map(venteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VenteDTO> findOne(Long id) {
        log.debug("Request to get Vente : {}", id);
        return venteRepository.findOneWithEagerRelationships(id).map(venteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vente : {}", id);
        venteRepository.deleteById(id);
    }
}
