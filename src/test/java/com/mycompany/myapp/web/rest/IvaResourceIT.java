package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Iva;
import com.mycompany.myapp.repository.IvaRepository;
import com.mycompany.myapp.service.dto.IvaDTO;
import com.mycompany.myapp.service.mapper.IvaMapper;
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
 * Integration tests for the {@link IvaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IvaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final String ENTITY_API_URL = "/api/ivas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IvaRepository ivaRepository;

    @Autowired
    private IvaMapper ivaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIvaMockMvc;

    private Iva iva;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Iva createEntity(EntityManager em) {
        Iva iva = new Iva().nombre(DEFAULT_NOMBRE).tipo(DEFAULT_TIPO).valor(DEFAULT_VALOR);
        return iva;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Iva createUpdatedEntity(EntityManager em) {
        Iva iva = new Iva().nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).valor(UPDATED_VALOR);
        return iva;
    }

    @BeforeEach
    public void initTest() {
        iva = createEntity(em);
    }

    @Test
    @Transactional
    void createIva() throws Exception {
        int databaseSizeBeforeCreate = ivaRepository.findAll().size();
        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);
        restIvaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ivaDTO)))
            .andExpect(status().isCreated());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeCreate + 1);
        Iva testIva = ivaList.get(ivaList.size() - 1);
        assertThat(testIva.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIva.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testIva.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createIvaWithExistingId() throws Exception {
        // Create the Iva with an existing ID
        iva.setId(1L);
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        int databaseSizeBeforeCreate = ivaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIvaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ivaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIvas() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        // Get all the ivaList
        restIvaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @Test
    @Transactional
    void getIva() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        // Get the iva
        restIvaMockMvc
            .perform(get(ENTITY_API_URL_ID, iva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(iva.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingIva() throws Exception {
        // Get the iva
        restIvaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIva() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();

        // Update the iva
        Iva updatedIva = ivaRepository.findById(iva.getId()).get();
        // Disconnect from session so that the updates on updatedIva are not directly saved in db
        em.detach(updatedIva);
        updatedIva.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).valor(UPDATED_VALOR);
        IvaDTO ivaDTO = ivaMapper.toDto(updatedIva);

        restIvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ivaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ivaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
        Iva testIva = ivaList.get(ivaList.size() - 1);
        assertThat(testIva.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIva.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testIva.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ivaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ivaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ivaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ivaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIvaWithPatch() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();

        // Update the iva using partial update
        Iva partialUpdatedIva = new Iva();
        partialUpdatedIva.setId(iva.getId());

        restIvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIva.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIva))
            )
            .andExpect(status().isOk());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
        Iva testIva = ivaList.get(ivaList.size() - 1);
        assertThat(testIva.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIva.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testIva.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateIvaWithPatch() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();

        // Update the iva using partial update
        Iva partialUpdatedIva = new Iva();
        partialUpdatedIva.setId(iva.getId());

        partialUpdatedIva.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).valor(UPDATED_VALOR);

        restIvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIva.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIva))
            )
            .andExpect(status().isOk());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
        Iva testIva = ivaList.get(ivaList.size() - 1);
        assertThat(testIva.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIva.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testIva.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ivaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ivaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ivaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIva() throws Exception {
        int databaseSizeBeforeUpdate = ivaRepository.findAll().size();
        iva.setId(count.incrementAndGet());

        // Create the Iva
        IvaDTO ivaDTO = ivaMapper.toDto(iva);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIvaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ivaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Iva in the database
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIva() throws Exception {
        // Initialize the database
        ivaRepository.saveAndFlush(iva);

        int databaseSizeBeforeDelete = ivaRepository.findAll().size();

        // Delete the iva
        restIvaMockMvc.perform(delete(ENTITY_API_URL_ID, iva.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Iva> ivaList = ivaRepository.findAll();
        assertThat(ivaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
