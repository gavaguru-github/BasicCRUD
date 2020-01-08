package com.ig.crud.web.rest;

import com.ig.crud.BasicCrudApp;
import com.ig.crud.domain.BreadCrumb;
import com.ig.crud.repository.BreadCrumbRepository;
import com.ig.crud.service.BreadCrumbService;
import com.ig.crud.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ig.crud.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BreadCrumbResource} REST controller.
 */
@SpringBootTest(classes = BasicCrudApp.class)
public class BreadCrumbResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BreadCrumbRepository breadCrumbRepository;

    @Autowired
    private BreadCrumbService breadCrumbService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBreadCrumbMockMvc;

    private BreadCrumb breadCrumb;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreadCrumbResource breadCrumbResource = new BreadCrumbResource(breadCrumbService);
        this.restBreadCrumbMockMvc = MockMvcBuilders.standaloneSetup(breadCrumbResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BreadCrumb createEntity(EntityManager em) {
        BreadCrumb breadCrumb = new BreadCrumb()
            .path(DEFAULT_PATH)
            .name(DEFAULT_NAME);
        return breadCrumb;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BreadCrumb createUpdatedEntity(EntityManager em) {
        BreadCrumb breadCrumb = new BreadCrumb()
            .path(UPDATED_PATH)
            .name(UPDATED_NAME);
        return breadCrumb;
    }

    @BeforeEach
    public void initTest() {
        breadCrumb = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreadCrumb() throws Exception {
        int databaseSizeBeforeCreate = breadCrumbRepository.findAll().size();

        // Create the BreadCrumb
        restBreadCrumbMockMvc.perform(post("/api/bread-crumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breadCrumb)))
            .andExpect(status().isCreated());

        // Validate the BreadCrumb in the database
        List<BreadCrumb> breadCrumbList = breadCrumbRepository.findAll();
        assertThat(breadCrumbList).hasSize(databaseSizeBeforeCreate + 1);
        BreadCrumb testBreadCrumb = breadCrumbList.get(breadCrumbList.size() - 1);
        assertThat(testBreadCrumb.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testBreadCrumb.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBreadCrumbWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breadCrumbRepository.findAll().size();

        // Create the BreadCrumb with an existing ID
        breadCrumb.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreadCrumbMockMvc.perform(post("/api/bread-crumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breadCrumb)))
            .andExpect(status().isBadRequest());

        // Validate the BreadCrumb in the database
        List<BreadCrumb> breadCrumbList = breadCrumbRepository.findAll();
        assertThat(breadCrumbList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBreadCrumbs() throws Exception {
        // Initialize the database
        breadCrumbRepository.saveAndFlush(breadCrumb);

        // Get all the breadCrumbList
        restBreadCrumbMockMvc.perform(get("/api/bread-crumbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breadCrumb.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBreadCrumb() throws Exception {
        // Initialize the database
        breadCrumbRepository.saveAndFlush(breadCrumb);

        // Get the breadCrumb
        restBreadCrumbMockMvc.perform(get("/api/bread-crumbs/{id}", breadCrumb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breadCrumb.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBreadCrumb() throws Exception {
        // Get the breadCrumb
        restBreadCrumbMockMvc.perform(get("/api/bread-crumbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreadCrumb() throws Exception {
        // Initialize the database
        breadCrumbService.save(breadCrumb);

        int databaseSizeBeforeUpdate = breadCrumbRepository.findAll().size();

        // Update the breadCrumb
        BreadCrumb updatedBreadCrumb = breadCrumbRepository.findById(breadCrumb.getId()).get();
        // Disconnect from session so that the updates on updatedBreadCrumb are not directly saved in db
        em.detach(updatedBreadCrumb);
        updatedBreadCrumb
            .path(UPDATED_PATH)
            .name(UPDATED_NAME);

        restBreadCrumbMockMvc.perform(put("/api/bread-crumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBreadCrumb)))
            .andExpect(status().isOk());

        // Validate the BreadCrumb in the database
        List<BreadCrumb> breadCrumbList = breadCrumbRepository.findAll();
        assertThat(breadCrumbList).hasSize(databaseSizeBeforeUpdate);
        BreadCrumb testBreadCrumb = breadCrumbList.get(breadCrumbList.size() - 1);
        assertThat(testBreadCrumb.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testBreadCrumb.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBreadCrumb() throws Exception {
        int databaseSizeBeforeUpdate = breadCrumbRepository.findAll().size();

        // Create the BreadCrumb

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreadCrumbMockMvc.perform(put("/api/bread-crumbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breadCrumb)))
            .andExpect(status().isBadRequest());

        // Validate the BreadCrumb in the database
        List<BreadCrumb> breadCrumbList = breadCrumbRepository.findAll();
        assertThat(breadCrumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBreadCrumb() throws Exception {
        // Initialize the database
        breadCrumbService.save(breadCrumb);

        int databaseSizeBeforeDelete = breadCrumbRepository.findAll().size();

        // Delete the breadCrumb
        restBreadCrumbMockMvc.perform(delete("/api/bread-crumbs/{id}", breadCrumb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BreadCrumb> breadCrumbList = breadCrumbRepository.findAll();
        assertThat(breadCrumbList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
