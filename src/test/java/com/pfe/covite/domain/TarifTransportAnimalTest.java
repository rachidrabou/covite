package com.pfe.covite.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pfe.covite.web.rest.TestUtil;

public class TarifTransportAnimalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifTransportAnimal.class);
        TarifTransportAnimal tarifTransportAnimal1 = new TarifTransportAnimal();
        tarifTransportAnimal1.setId(1L);
        TarifTransportAnimal tarifTransportAnimal2 = new TarifTransportAnimal();
        tarifTransportAnimal2.setId(tarifTransportAnimal1.getId());
        assertThat(tarifTransportAnimal1).isEqualTo(tarifTransportAnimal2);
        tarifTransportAnimal2.setId(2L);
        assertThat(tarifTransportAnimal1).isNotEqualTo(tarifTransportAnimal2);
        tarifTransportAnimal1.setId(null);
        assertThat(tarifTransportAnimal1).isNotEqualTo(tarifTransportAnimal2);
    }
}
