package com.challenge.digitaldayapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenteMapperTest {

    private VenteMapper venteMapper;

    @BeforeEach
    public void setUp() {
        venteMapper = new VenteMapperImpl();
    }
}
