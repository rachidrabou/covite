package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class CommandeTransportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeTransport.class);
        CommandeTransport commandeTransport1 = new CommandeTransport();
        commandeTransport1.setId(1L);
        CommandeTransport commandeTransport2 = new CommandeTransport();
        commandeTransport2.setId(commandeTransport1.getId());
        assertThat(commandeTransport1).isEqualTo(commandeTransport2);
        commandeTransport2.setId(2L);
        assertThat(commandeTransport1).isNotEqualTo(commandeTransport2);
        commandeTransport1.setId(null);
        assertThat(commandeTransport1).isNotEqualTo(commandeTransport2);
    }
}
