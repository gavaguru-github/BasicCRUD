package com.ig.crud.web.rest;

import com.ig.crud.BasicCrudApp;
import com.ig.crud.domain.ApplicationMenu;
import com.ig.crud.repository.ApplicationMenuRepository;
import com.ig.crud.service.ApplicationMenuService;
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
 * Integration tests for the {@link ApplicationMenuResource} REST controller.
 */
@SpringBootTest(classes = BasicCrudApp.class)
public class ApplicationMenuResourceIT {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FRENCH_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FRENCH_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_ROLE = 1L;
    private static final Long UPDATED_ROLE = 2L;

    private static final Long DEFAULT_ORDER = 1L;
    private static final Long UPDATED_ORDER = 2L;

    @Autowired
    private ApplicationMenuRepository applicationMenuRepository;

    @Autowired
    private ApplicationMenuService applicationMenuService;

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

    private MockMvc restApplicationMenuMockMvc;

    private ApplicationMenu applicationMenu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationMenuResource applicationMenuResource = new ApplicationMenuResource(applicationMenuService);
        this.restApplicationMenuMockMvc = MockMvcBuilders.standaloneSetup(applicationMenuResource)
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
    public static ApplicationMenu createEntity(EntityManager em) {
        ApplicationMenu applicationMenu = new ApplicationMenu()
            .parentId(DEFAULT_PARENT_ID)
            .name(DEFAULT_NAME)
            .englishText(DEFAULT_ENGLISH_TEXT)
            .frenchPath(DEFAULT_FRENCH_PATH)
            .role(DEFAULT_ROLE)
            .order(DEFAULT_ORDER);
        return applicationMenu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationMenu createUpdatedEntity(EntityManager em) {
        ApplicationMenu applicationMenu = new ApplicationMenu()
            .parentId(UPDATED_PARENT_ID)
            .name(UPDATED_NAME)
            .englishText(UPDATED_ENGLISH_TEXT)
            .frenchPath(UPDATED_FRENCH_PATH)
            .role(UPDATED_ROLE)
            .order(UPDATED_ORDER);
        return applicationMenu;
    }

    @BeforeEach
    public void initTest() {
        applicationMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationMenu() throws Exception {
        int databaseSizeBeforeCreate = applicationMenuRepository.findAll().size();

        // Create the ApplicationMenu
        restApplicationMenuMockMvc.perform(post("/api/application-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationMenu)))
            .andExpect(status().isCreated());

        // Validate the ApplicationMenu in the database
        List<ApplicationMenu> applicationMenuList = applicationMenuRepository.findAll();
        assertThat(applicationMenuList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationMenu testApplicationMenu = applicationMenuList.get(applicationMenuList.size() - 1);
        assertThat(testApplicationMenu.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testApplicationMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationMenu.getEnglishText()).isEqualTo(DEFAULT_ENGLISH_TEXT);
        assertThat(testApplicationMenu.getFrenchPath()).isEqualTo(DEFAULT_FRENCH_PATH);
        assertThat(testApplicationMenu.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testApplicationMenu.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createApplicationMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationMenuRepository.findAll().size();

        // Create the ApplicationMenu with an existing ID
        applicationMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMenuMockMvc.perform(post("/api/application-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationMenu)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationMenu in the database
        List<ApplicationMenu> applicationMenuList = applicationMenuRepository.findAll();
        assertThat(applicationMenuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplicationMenus() throws Exception {
        // Initialize the database
        applicationMenuRepository.saveAndFlush(applicationMenu);

        // Get all the applicationMenuList
        restApplicationMenuMockMvc.perform(get("/api/application-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].englishText").value(hasItem(DEFAULT_ENGLISH_TEXT)))
            .andExpect(jsonPath("$.[*].frenchPath").value(hasItem(DEFAULT_FRENCH_PATH)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER.intValue())));
    }
    
    @Test
    @Transactional
    public void getApplicationMenu() throws Exception {
        // Initialize the database
        applicationMenuRepository.saveAndFlush(applicationMenu);

        // Get the applicationMenu
        restApplicationMenuMockMvc.perform(get("/api/application-menus/{id}", applicationMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationMenu.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.englishText").value(DEFAULT_ENGLISH_TEXT))
            .andExpect(jsonPath("$.frenchPath").value(DEFAULT_FRENCH_PATH))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationMenu() throws Exception {
        // Get the applicationMenu
        restApplicationMenuMockMvc.perform(get("/api/application-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationMenu() throws Exception {
        // Initialize the database
        applicationMenuService.save(applicationMenu);

        int databaseSizeBeforeUpdate = applicationMenuRepository.findAll().size();

        // Update the applicationMenu
        ApplicationMenu updatedApplicationMenu = applicationMenuRepository.findById(applicationMenu.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationMenu are not directly saved in db
        em.detach(updatedApplicationMenu);
        updatedApplicationMenu
            .parentId(UPDATED_PARENT_ID)
            .name(UPDATED_NAME)
            .englishText(UPDATED_ENGLISH_TEXT)
            .frenchPath(UPDATED_FRENCH_PATH)
            .role(UPDATED_ROLE)
            .order(UPDATED_ORDER);

        restApplicationMenuMockMvc.perform(put("/api/application-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationMenu)))
            .andExpect(status().isOk());

        // Validate the ApplicationMenu in the database
        List<ApplicationMenu> applicationMenuList = applicationMenuRepository.findAll();
        assertThat(applicationMenuList).hasSize(databaseSizeBeforeUpdate);
        ApplicationMenu testApplicationMenu = applicationMenuList.get(applicationMenuList.size() - 1);
        assertThat(testApplicationMenu.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testApplicationMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationMenu.getEnglishText()).isEqualTo(UPDATED_ENGLISH_TEXT);
        assertThat(testApplicationMenu.getFrenchPath()).isEqualTo(UPDATED_FRENCH_PATH);
        assertThat(testApplicationMenu.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testApplicationMenu.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationMenu() throws Exception {
        int databaseSizeBeforeUpdate = applicationMenuRepository.findAll().size();

        // Create the ApplicationMenu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMenuMockMvc.perform(put("/api/application-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationMenu)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationMenu in the database
        List<ApplicationMenu> applicationMenuList = applicationMenuRepository.findAll();
        assertThat(applicationMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationMenu() throws Exception {
        // Initialize the database
        applicationMenuService.save(applicationMenu);

        int databaseSizeBeforeDelete = applicationMenuRepository.findAll().size();

        // Delete the applicationMenu
        restApplicationMenuMockMvc.perform(delete("/api/application-menus/{id}", applicationMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationMenu> applicationMenuList = applicationMenuRepository.findAll();
        assertThat(applicationMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
