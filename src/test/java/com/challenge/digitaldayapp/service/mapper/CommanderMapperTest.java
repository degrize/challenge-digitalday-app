package com.challenge.digitaldayapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommanderMapperTest {

    private CommanderMapper commanderMapper;

    @BeforeEach
    public void setUp() {
        commanderMapper = new CommanderMapperImpl();
    }
}
