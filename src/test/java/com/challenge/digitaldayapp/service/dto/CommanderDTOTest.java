package com.challenge.digitaldayapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.digitaldayapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommanderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommanderDTO.class);
        CommanderDTO commanderDTO1 = new CommanderDTO();
        commanderDTO1.setId(1L);
        CommanderDTO commanderDTO2 = new CommanderDTO();
        assertThat(commanderDTO1).isNotEqualTo(commanderDTO2);
        commanderDTO2.setId(commanderDTO1.getId());
        assertThat(commanderDTO1).isEqualTo(commanderDTO2);
        commanderDTO2.setId(2L);
        assertThat(commanderDTO1).isNotEqualTo(commanderDTO2);
        commanderDTO1.setId(null);
        assertThat(commanderDTO1).isNotEqualTo(commanderDTO2);
    }
}
