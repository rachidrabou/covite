package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class TarifLivraisonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifLivraison.class);
        TarifLivraison tarifLivraison1 = new TarifLivraison();
        tarifLivraison1.setId(1L);
        TarifLivraison tarifLivraison2 = new TarifLivraison();
        tarifLivraison2.setId(tarifLivraison1.getId());
        assertThat(tarifLivraison1).isEqualTo(tarifLivraison2);
        tarifLivraison2.setId(2L);
        assertThat(tarifLivraison1).isNotEqualTo(tarifLivraison2);
        tarifLivraison1.setId(null);
        assertThat(tarifLivraison1).isNotEqualTo(tarifLivraison2);
    }
}
