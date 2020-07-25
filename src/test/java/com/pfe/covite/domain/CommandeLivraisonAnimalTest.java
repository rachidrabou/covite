package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class CommandeLivraisonAnimalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeLivraisonAnimal.class);
        CommandeLivraisonAnimal commandeLivraisonAnimal1 = new CommandeLivraisonAnimal();
        commandeLivraisonAnimal1.setId(1L);
        CommandeLivraisonAnimal commandeLivraisonAnimal2 = new CommandeLivraisonAnimal();
        commandeLivraisonAnimal2.setId(commandeLivraisonAnimal1.getId());
        assertThat(commandeLivraisonAnimal1).isEqualTo(commandeLivraisonAnimal2);
        commandeLivraisonAnimal2.setId(2L);
        assertThat(commandeLivraisonAnimal1).isNotEqualTo(commandeLivraisonAnimal2);
        commandeLivraisonAnimal1.setId(null);
        assertThat(commandeLivraisonAnimal1).isNotEqualTo(commandeLivraisonAnimal2);
    }
}
