package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class CommandesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commandes.class);
        Commandes commandes1 = new Commandes();
        commandes1.setId(1L);
        Commandes commandes2 = new Commandes();
        commandes2.setId(commandes1.getId());
        assertThat(commandes1).isEqualTo(commandes2);
        commandes2.setId(2L);
        assertThat(commandes1).isNotEqualTo(commandes2);
        commandes1.setId(null);
        assertThat(commandes1).isNotEqualTo(commandes2);
    }
}
