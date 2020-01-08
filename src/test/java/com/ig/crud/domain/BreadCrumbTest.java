package com.ig.crud.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ig.crud.web.rest.TestUtil;

public class BreadCrumbTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreadCrumb.class);
        BreadCrumb breadCrumb1 = new BreadCrumb();
        breadCrumb1.setId(1L);
        BreadCrumb breadCrumb2 = new BreadCrumb();
        breadCrumb2.setId(breadCrumb1.getId());
        assertThat(breadCrumb1).isEqualTo(breadCrumb2);
        breadCrumb2.setId(2L);
        assertThat(breadCrumb1).isNotEqualTo(breadCrumb2);
        breadCrumb1.setId(null);
        assertThat(breadCrumb1).isNotEqualTo(breadCrumb2);
    }
}
