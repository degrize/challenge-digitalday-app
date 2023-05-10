package com.challenge.digitaldayapp.service.impl;

import com.challenge.digitaldayapp.domain.Commander;
import com.challenge.digitaldayapp.repository.CommanderRepository;
import com.challenge.digitaldayapp.service.CommanderService;
import com.challenge.digitaldayapp.service.dto.CommanderDTO;
import com.challenge.digitaldayapp.service.mapper.CommanderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commander}.
 */
@Service
@Transactional
public class CommanderServiceImpl implements CommanderService {

    private final Logger log = LoggerFactory.getLogger(CommanderServiceImpl.class);

    private final CommanderRepository commanderRepository;

    private final CommanderMapper commanderMapper;

    public CommanderServiceImpl(CommanderRepository commanderRepository, CommanderMapper commanderMapper) {
        this.commanderRepository = commanderRepository;
        this.commanderMapper = commanderMapper;
    }

    @Override
    public CommanderDTO save(CommanderDTO commanderDTO) {
        log.debug("Request to save Commander : {}", commanderDTO);
        Commander commander = commanderMapper.toEntity(commanderDTO);
        commander = commanderRepository.save(commander);
        return commanderMapper.toDto(commander);
    }

    @Override
    public CommanderDTO update(CommanderDTO commanderDTO) {
        log.debug("Request to update Commander : {}", commanderDTO);
        Commander commander = commanderMapper.toEntity(commanderDTO);
        commander = commanderRepository.save(commander);
        return commanderMapper.toDto(commander);
    }

    @Override
    public Optional<CommanderDTO> partialUpdate(CommanderDTO commanderDTO) {
        log.debug("Request to partially update Commander : {}", commanderDTO);

        return commanderRepository
            .findById(commanderDTO.getId())
            .map(existingCommander -> {
                commanderMapper.partialUpdate(existingCommander, commanderDTO);

                return existingCommander;
            })
            .map(commanderRepository::save)
            .map(commanderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommanderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commanders");
        return commanderRepository.findAll(pageable).map(commanderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommanderDTO> findOne(Long id) {
        log.debug("Request to get Commander : {}", id);
        return commanderRepository.findById(id).map(commanderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commander : {}", id);
        commanderRepository.deleteById(id);
    }
}
