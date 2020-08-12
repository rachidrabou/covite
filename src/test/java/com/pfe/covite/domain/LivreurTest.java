package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class LivreurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livreur.class);
        Livreur livreur1 = new Livreur();
        livreur1.setId(1L);
        Livreur livreur2 = new Livreur();
        livreur2.setId(livreur1.getId());
        assertThat(livreur1).isEqualTo(livreur2);
        livreur2.setId(2L);
        assertThat(livreur1).isNotEqualTo(livreur2);
        livreur1.setId(null);
        assertThat(livreur1).isNotEqualTo(livreur2);
    }
}
