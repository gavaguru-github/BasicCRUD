package com.ig.crud.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ig.crud.web.rest.TestUtil;

public class ApplicationMenuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationMenu.class);
        ApplicationMenu applicationMenu1 = new ApplicationMenu();
        applicationMenu1.setId(1L);
        ApplicationMenu applicationMenu2 = new ApplicationMenu();
        applicationMenu2.setId(applicationMenu1.getId());
        assertThat(applicationMenu1).isEqualTo(applicationMenu2);
        applicationMenu2.setId(2L);
        assertThat(applicationMenu1).isNotEqualTo(applicationMenu2);
        applicationMenu1.setId(null);
        assertThat(applicationMenu1).isNotEqualTo(applicationMenu2);
    }
}
