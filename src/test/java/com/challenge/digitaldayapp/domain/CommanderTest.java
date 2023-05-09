package com.challenge.digitaldayapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.digitaldayapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommanderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commander.class);
        Commander commander1 = new Commander();
        commander1.setId(1L);
        Commander commander2 = new Commander();
        commander2.setId(commander1.getId());
        assertThat(commander1).isEqualTo(commander2);
        commander2.setId(2L);
        assertThat(commander1).isNotEqualTo(commander2);
        commander1.setId(null);
        assertThat(commander1).isNotEqualTo(commander2);
    }
}
