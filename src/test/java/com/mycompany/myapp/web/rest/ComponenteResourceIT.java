package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Componente;
import com.mycompany.myapp.repository.ComponenteRepository;
import com.mycompany.myapp.service.dto.ComponenteDTO;
import com.mycompany.myapp.service.mapper.ComponenteMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComponenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComponenteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final String ENTITY_API_URL = "/api/componentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComponenteRepository componenteRepository;

    @Autowired
    private ComponenteMapper componenteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComponenteMockMvc;

    private Componente componente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Componente createEntity(EntityManager em) {
        Componente componente = new Componente()
            .nombre(DEFAULT_NOMBRE)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .descripcion(DEFAULT_DESCRIPCION)
            .peso(DEFAULT_PESO);
        return componente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Componente createUpdatedEntity(EntityManager em) {
        Componente componente = new Componente()
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO);
        return componente;
    }

    @BeforeEach
    public void initTest() {
        componente = createEntity(em);
    }

    @Test
    @Transactional
    void createComponente() throws Exception {
        int databaseSizeBeforeCreate = componenteRepository.findAll().size();
        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);
        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeCreate + 1);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComponente.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testComponente.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testComponente.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testComponente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testComponente.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void createComponenteWithExistingId() throws Exception {
        // Create the Componente with an existing ID
        componente.setId(1L);
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        int databaseSizeBeforeCreate = componenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setNombre(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setNumeroSerie(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setMarca(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setModelo(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setDescripcion(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = componenteRepository.findAll().size();
        // set the field null
        componente.setPeso(null);

        // Create the Componente, which fails.
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        restComponenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isBadRequest());

        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComponentes() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        // Get all the componenteList
        restComponenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(componente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())));
    }

    @Test
    @Transactional
    void getComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        // Get the componente
        restComponenteMockMvc
            .perform(get(ENTITY_API_URL_ID, componente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(componente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingComponente() throws Exception {
        // Get the componente
        restComponenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();

        // Update the componente
        Componente updatedComponente = componenteRepository.findById(componente.getId()).get();
        // Disconnect from session so that the updates on updatedComponente are not directly saved in db
        em.detach(updatedComponente);
        updatedComponente
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO);
        ComponenteDTO componenteDTO = componenteMapper.toDto(updatedComponente);

        restComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, componenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComponente.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testComponente.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testComponente.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testComponente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponente.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void putNonExistingComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, componenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(componenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComponenteWithPatch() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();

        // Update the componente using partial update
        Componente partialUpdatedComponente = new Componente();
        partialUpdatedComponente.setId(componente.getId());

        partialUpdatedComponente.numeroSerie(UPDATED_NUMERO_SERIE).marca(UPDATED_MARCA).descripcion(UPDATED_DESCRIPCION).peso(UPDATED_PESO);

        restComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComponente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComponente))
            )
            .andExpect(status().isOk());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComponente.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testComponente.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testComponente.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testComponente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponente.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void fullUpdateComponenteWithPatch() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();

        // Update the componente using partial update
        Componente partialUpdatedComponente = new Componente();
        partialUpdatedComponente.setId(componente.getId());

        partialUpdatedComponente
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO);

        restComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComponente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComponente))
            )
            .andExpect(status().isOk());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComponente.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testComponente.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testComponente.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testComponente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComponente.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void patchNonExistingComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, componenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();
        componente.setId(count.incrementAndGet());

        // Create the Componente
        ComponenteDTO componenteDTO = componenteMapper.toDto(componente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComponenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(componenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        int databaseSizeBeforeDelete = componenteRepository.findAll().size();

        // Delete the componente
        restComponenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, componente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
