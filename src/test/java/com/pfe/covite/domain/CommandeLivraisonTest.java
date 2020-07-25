package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class CommandeLivraisonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeLivraison.class);
        CommandeLivraison commandeLivraison1 = new CommandeLivraison();
        commandeLivraison1.setId(1L);
        CommandeLivraison commandeLivraison2 = new CommandeLivraison();
        commandeLivraison2.setId(commandeLivraison1.getId());
        assertThat(commandeLivraison1).isEqualTo(commandeLivraison2);
        commandeLivraison2.setId(2L);
        assertThat(commandeLivraison1).isNotEqualTo(commandeLivraison2);
        commandeLivraison1.setId(null);
        assertThat(commandeLivraison1).isNotEqualTo(commandeLivraison2);
    }
}
