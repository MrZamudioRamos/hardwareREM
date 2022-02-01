package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Almacen;
import com.mycompany.myapp.repository.AlmacenRepository;
import com.mycompany.myapp.service.dto.AlmacenDTO;
import com.mycompany.myapp.service.mapper.AlmacenMapper;
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
 * Integration tests for the {@link AlmacenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlmacenResourceIT {

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_SUCURSAL = "AAAAAAAAAA";
    private static final String UPDATED_SUCURSAL = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALBARAN = "AAAAAAAAAA";
    private static final String UPDATED_ALBARAN = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK_PRODUCTOS = 1;
    private static final Integer UPDATED_STOCK_PRODUCTOS = 2;

    private static final String ENTITY_API_URL = "/api/almacens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private AlmacenMapper almacenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlmacenMockMvc;

    private Almacen almacen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Almacen createEntity(EntityManager em) {
        Almacen almacen = new Almacen()
            .telefono(DEFAULT_TELEFONO)
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .sucursal(DEFAULT_SUCURSAL)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .calle(DEFAULT_CALLE)
            .albaran(DEFAULT_ALBARAN)
            .stockProductos(DEFAULT_STOCK_PRODUCTOS);
        return almacen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Almacen createUpdatedEntity(EntityManager em) {
        Almacen almacen = new Almacen()
            .telefono(UPDATED_TELEFONO)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .albaran(UPDATED_ALBARAN)
            .stockProductos(UPDATED_STOCK_PRODUCTOS);
        return almacen;
    }

    @BeforeEach
    public void initTest() {
        almacen = createEntity(em);
    }

    @Test
    @Transactional
    void createAlmacen() throws Exception {
        int databaseSizeBeforeCreate = almacenRepository.findAll().size();
        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);
        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isCreated());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeCreate + 1);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testAlmacen.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testAlmacen.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testAlmacen.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testAlmacen.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testAlmacen.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testAlmacen.getAlbaran()).isEqualTo(DEFAULT_ALBARAN);
        assertThat(testAlmacen.getStockProductos()).isEqualTo(DEFAULT_STOCK_PRODUCTOS);
    }

    @Test
    @Transactional
    void createAlmacenWithExistingId() throws Exception {
        // Create the Almacen with an existing ID
        almacen.setId(1L);
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        int databaseSizeBeforeCreate = almacenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setTelefono(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setPais(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setProvincia(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSucursalIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setSucursal(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setCodigoPostal(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setCalle(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlbaranIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setAlbaran(null);

        // Create the Almacen, which fails.
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        restAlmacenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlmacens() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        // Get all the almacenList
        restAlmacenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(almacen.getId().intValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].sucursal").value(hasItem(DEFAULT_SUCURSAL)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].albaran").value(hasItem(DEFAULT_ALBARAN)))
            .andExpect(jsonPath("$.[*].stockProductos").value(hasItem(DEFAULT_STOCK_PRODUCTOS)));
    }

    @Test
    @Transactional
    void getAlmacen() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        // Get the almacen
        restAlmacenMockMvc
            .perform(get(ENTITY_API_URL_ID, almacen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(almacen.getId().intValue()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.sucursal").value(DEFAULT_SUCURSAL))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.albaran").value(DEFAULT_ALBARAN))
            .andExpect(jsonPath("$.stockProductos").value(DEFAULT_STOCK_PRODUCTOS));
    }

    @Test
    @Transactional
    void getNonExistingAlmacen() throws Exception {
        // Get the almacen
        restAlmacenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlmacen() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();

        // Update the almacen
        Almacen updatedAlmacen = almacenRepository.findById(almacen.getId()).get();
        // Disconnect from session so that the updates on updatedAlmacen are not directly saved in db
        em.detach(updatedAlmacen);
        updatedAlmacen
            .telefono(UPDATED_TELEFONO)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .albaran(UPDATED_ALBARAN)
            .stockProductos(UPDATED_STOCK_PRODUCTOS);
        AlmacenDTO almacenDTO = almacenMapper.toDto(updatedAlmacen);

        restAlmacenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, almacenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testAlmacen.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testAlmacen.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testAlmacen.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testAlmacen.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testAlmacen.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testAlmacen.getAlbaran()).isEqualTo(UPDATED_ALBARAN);
        assertThat(testAlmacen.getStockProductos()).isEqualTo(UPDATED_STOCK_PRODUCTOS);
    }

    @Test
    @Transactional
    void putNonExistingAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, almacenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(almacenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlmacenWithPatch() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();

        // Update the almacen using partial update
        Almacen partialUpdatedAlmacen = new Almacen();
        partialUpdatedAlmacen.setId(almacen.getId());

        partialUpdatedAlmacen.provincia(UPDATED_PROVINCIA).codigoPostal(UPDATED_CODIGO_POSTAL);

        restAlmacenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlmacen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlmacen))
            )
            .andExpect(status().isOk());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testAlmacen.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testAlmacen.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testAlmacen.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testAlmacen.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testAlmacen.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testAlmacen.getAlbaran()).isEqualTo(DEFAULT_ALBARAN);
        assertThat(testAlmacen.getStockProductos()).isEqualTo(DEFAULT_STOCK_PRODUCTOS);
    }

    @Test
    @Transactional
    void fullUpdateAlmacenWithPatch() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();

        // Update the almacen using partial update
        Almacen partialUpdatedAlmacen = new Almacen();
        partialUpdatedAlmacen.setId(almacen.getId());

        partialUpdatedAlmacen
            .telefono(UPDATED_TELEFONO)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .albaran(UPDATED_ALBARAN)
            .stockProductos(UPDATED_STOCK_PRODUCTOS);

        restAlmacenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlmacen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlmacen))
            )
            .andExpect(status().isOk());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testAlmacen.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testAlmacen.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testAlmacen.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testAlmacen.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testAlmacen.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testAlmacen.getAlbaran()).isEqualTo(UPDATED_ALBARAN);
        assertThat(testAlmacen.getStockProductos()).isEqualTo(UPDATED_STOCK_PRODUCTOS);
    }

    @Test
    @Transactional
    void patchNonExistingAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, almacenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();
        almacen.setId(count.incrementAndGet());

        // Create the Almacen
        AlmacenDTO almacenDTO = almacenMapper.toDto(almacen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlmacenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(almacenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlmacen() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        int databaseSizeBeforeDelete = almacenRepository.findAll().size();

        // Delete the almacen
        restAlmacenMockMvc
            .perform(delete(ENTITY_API_URL_ID, almacen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
