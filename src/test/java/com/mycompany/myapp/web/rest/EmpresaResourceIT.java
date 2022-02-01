package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.repository.EmpresaRepository;
import com.mycompany.myapp.service.dto.EmpresaDTO;
import com.mycompany.myapp.service.mapper.EmpresaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpresaResourceIT {

    private static final String DEFAULT_NOMBRE_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CIF = "AAAAAAAAAA";
    private static final String UPDATED_CIF = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

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

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nombreSocial(DEFAULT_NOMBRE_SOCIAL)
            .cif(DEFAULT_CIF)
            .telefono(DEFAULT_TELEFONO)
            .mail(DEFAULT_MAIL)
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .sucursal(DEFAULT_SUCURSAL)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .calle(DEFAULT_CALLE)
            .categoria(DEFAULT_CATEGORIA)
            .fechaCreacion(DEFAULT_FECHA_CREACION);
        return empresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nombreSocial(UPDATED_NOMBRE_SOCIAL)
            .cif(UPDATED_CIF)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .categoria(UPDATED_CATEGORIA)
            .fechaCreacion(UPDATED_FECHA_CREACION);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreSocial()).isEqualTo(DEFAULT_NOMBRE_SOCIAL);
        assertThat(testEmpresa.getCif()).isEqualTo(DEFAULT_CIF);
        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpresa.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testEmpresa.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEmpresa.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testEmpresa.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testEmpresa.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testEmpresa.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testEmpresa.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    }

    @Test
    @Transactional
    void createEmpresaWithExistingId() throws Exception {
        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNombreSocial(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCifIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCif(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setTelefono(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setMail(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setPais(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setProvincia(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSucursalIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setSucursal(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCodigoPostal(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCalle(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setCategoria(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setFechaCreacion(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreSocial").value(hasItem(DEFAULT_NOMBRE_SOCIAL)))
            .andExpect(jsonPath("$.[*].cif").value(hasItem(DEFAULT_CIF)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].sucursal").value(hasItem(DEFAULT_SUCURSAL)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())));
    }

    @Test
    @Transactional
    void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nombreSocial").value(DEFAULT_NOMBRE_SOCIAL))
            .andExpect(jsonPath("$.cif").value(DEFAULT_CIF))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.sucursal").value(DEFAULT_SUCURSAL))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nombreSocial(UPDATED_NOMBRE_SOCIAL)
            .cif(UPDATED_CIF)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .categoria(UPDATED_CATEGORIA)
            .fechaCreacion(UPDATED_FECHA_CREACION);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreSocial()).isEqualTo(UPDATED_NOMBRE_SOCIAL);
        assertThat(testEmpresa.getCif()).isEqualTo(UPDATED_CIF);
        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpresa.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEmpresa.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpresa.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpresa.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testEmpresa.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testEmpresa.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void putNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .nombreSocial(UPDATED_NOMBRE_SOCIAL)
            .cif(UPDATED_CIF)
            .mail(UPDATED_MAIL)
            .provincia(UPDATED_PROVINCIA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreSocial()).isEqualTo(UPDATED_NOMBRE_SOCIAL);
        assertThat(testEmpresa.getCif()).isEqualTo(UPDATED_CIF);
        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpresa.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEmpresa.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpresa.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpresa.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testEmpresa.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testEmpresa.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    }

    @Test
    @Transactional
    void fullUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .nombreSocial(UPDATED_NOMBRE_SOCIAL)
            .cif(UPDATED_CIF)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .sucursal(UPDATED_SUCURSAL)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .categoria(UPDATED_CATEGORIA)
            .fechaCreacion(UPDATED_FECHA_CREACION);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreSocial()).isEqualTo(UPDATED_NOMBRE_SOCIAL);
        assertThat(testEmpresa.getCif()).isEqualTo(UPDATED_CIF);
        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpresa.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEmpresa.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpresa.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpresa.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testEmpresa.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testEmpresa.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void patchNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
