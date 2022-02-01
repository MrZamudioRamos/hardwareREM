package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.repository.EmpleadoRepository;
import com.mycompany.myapp.service.dto.EmpleadoDTO;
import com.mycompany.myapp.service.mapper.EmpleadoMapper;
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
 * Integration tests for the {@link EmpleadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpleadoResourceIT {

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PEDIDOS = 1;
    private static final Integer UPDATED_NUMERO_PEDIDOS = 2;

    private static final String DEFAULT_TIPO_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONTRATO = "BBBBBBBBBB";

    private static final Double DEFAULT_COMISION = 1D;
    private static final Double UPDATED_COMISION = 2D;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_CONTRASENA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRASENA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empleados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpleadoMockMvc;

    private Empleado empleado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleado createEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .dni(DEFAULT_DNI)
            .nombre(DEFAULT_NOMBRE)
            .apellidos(DEFAULT_APELLIDOS)
            .telefono(DEFAULT_TELEFONO)
            .mail(DEFAULT_MAIL)
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .calle(DEFAULT_CALLE)
            .numeroPedidos(DEFAULT_NUMERO_PEDIDOS)
            .tipoContrato(DEFAULT_TIPO_CONTRATO)
            .comision(DEFAULT_COMISION)
            .activo(DEFAULT_ACTIVO)
            .contrasena(DEFAULT_CONTRASENA);
        return empleado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleado createUpdatedEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .dni(UPDATED_DNI)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .numeroPedidos(UPDATED_NUMERO_PEDIDOS)
            .tipoContrato(UPDATED_TIPO_CONTRATO)
            .comision(UPDATED_COMISION)
            .activo(UPDATED_ACTIVO)
            .contrasena(UPDATED_CONTRASENA);
        return empleado;
    }

    @BeforeEach
    public void initTest() {
        empleado = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpleado() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();
        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);
        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate + 1);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testEmpleado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEmpleado.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testEmpleado.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpleado.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testEmpleado.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEmpleado.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testEmpleado.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testEmpleado.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testEmpleado.getNumeroPedidos()).isEqualTo(DEFAULT_NUMERO_PEDIDOS);
        assertThat(testEmpleado.getTipoContrato()).isEqualTo(DEFAULT_TIPO_CONTRATO);
        assertThat(testEmpleado.getComision()).isEqualTo(DEFAULT_COMISION);
        assertThat(testEmpleado.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testEmpleado.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
    }

    @Test
    @Transactional
    void createEmpleadoWithExistingId() throws Exception {
        // Create the Empleado with an existing ID
        empleado.setId(1L);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setDni(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setNombre(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setApellidos(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setTelefono(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setMail(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setPais(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setProvincia(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setCodigoPostal(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setCalle(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoContratoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setTipoContrato(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setActivo(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContrasenaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setContrasena(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpleados() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get all the empleadoList
        restEmpleadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].numeroPedidos").value(hasItem(DEFAULT_NUMERO_PEDIDOS)))
            .andExpect(jsonPath("$.[*].tipoContrato").value(hasItem(DEFAULT_TIPO_CONTRATO)))
            .andExpect(jsonPath("$.[*].comision").value(hasItem(DEFAULT_COMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].contrasena").value(hasItem(DEFAULT_CONTRASENA)));
    }

    @Test
    @Transactional
    void getEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get the empleado
        restEmpleadoMockMvc
            .perform(get(ENTITY_API_URL_ID, empleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empleado.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.numeroPedidos").value(DEFAULT_NUMERO_PEDIDOS))
            .andExpect(jsonPath("$.tipoContrato").value(DEFAULT_TIPO_CONTRATO))
            .andExpect(jsonPath("$.comision").value(DEFAULT_COMISION.doubleValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.contrasena").value(DEFAULT_CONTRASENA));
    }

    @Test
    @Transactional
    void getNonExistingEmpleado() throws Exception {
        // Get the empleado
        restEmpleadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado
        Empleado updatedEmpleado = empleadoRepository.findById(empleado.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleado are not directly saved in db
        em.detach(updatedEmpleado);
        updatedEmpleado
            .dni(UPDATED_DNI)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .numeroPedidos(UPDATED_NUMERO_PEDIDOS)
            .tipoContrato(UPDATED_TIPO_CONTRATO)
            .comision(UPDATED_COMISION)
            .activo(UPDATED_ACTIVO)
            .contrasena(UPDATED_CONTRASENA);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(updatedEmpleado);

        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmpleado.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testEmpleado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpleado.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEmpleado.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEmpleado.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpleado.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpleado.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testEmpleado.getNumeroPedidos()).isEqualTo(UPDATED_NUMERO_PEDIDOS);
        assertThat(testEmpleado.getTipoContrato()).isEqualTo(UPDATED_TIPO_CONTRATO);
        assertThat(testEmpleado.getComision()).isEqualTo(UPDATED_COMISION);
        assertThat(testEmpleado.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEmpleado.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
    }

    @Test
    @Transactional
    void putNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpleadoWithPatch() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado using partial update
        Empleado partialUpdatedEmpleado = new Empleado();
        partialUpdatedEmpleado.setId(empleado.getId());

        partialUpdatedEmpleado
            .dni(UPDATED_DNI)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .numeroPedidos(UPDATED_NUMERO_PEDIDOS)
            .tipoContrato(UPDATED_TIPO_CONTRATO)
            .comision(UPDATED_COMISION)
            .activo(UPDATED_ACTIVO);

        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmpleado.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testEmpleado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpleado.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testEmpleado.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEmpleado.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testEmpleado.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testEmpleado.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testEmpleado.getNumeroPedidos()).isEqualTo(UPDATED_NUMERO_PEDIDOS);
        assertThat(testEmpleado.getTipoContrato()).isEqualTo(UPDATED_TIPO_CONTRATO);
        assertThat(testEmpleado.getComision()).isEqualTo(UPDATED_COMISION);
        assertThat(testEmpleado.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEmpleado.getContrasena()).isEqualTo(DEFAULT_CONTRASENA);
    }

    @Test
    @Transactional
    void fullUpdateEmpleadoWithPatch() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado using partial update
        Empleado partialUpdatedEmpleado = new Empleado();
        partialUpdatedEmpleado.setId(empleado.getId());

        partialUpdatedEmpleado
            .dni(UPDATED_DNI)
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .telefono(UPDATED_TELEFONO)
            .mail(UPDATED_MAIL)
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .calle(UPDATED_CALLE)
            .numeroPedidos(UPDATED_NUMERO_PEDIDOS)
            .tipoContrato(UPDATED_TIPO_CONTRATO)
            .comision(UPDATED_COMISION)
            .activo(UPDATED_ACTIVO)
            .contrasena(UPDATED_CONTRASENA);

        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmpleado.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testEmpleado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpleado.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEmpleado.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEmpleado.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpleado.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpleado.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testEmpleado.getNumeroPedidos()).isEqualTo(UPDATED_NUMERO_PEDIDOS);
        assertThat(testEmpleado.getTipoContrato()).isEqualTo(UPDATED_TIPO_CONTRATO);
        assertThat(testEmpleado.getComision()).isEqualTo(UPDATED_COMISION);
        assertThat(testEmpleado.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testEmpleado.getContrasena()).isEqualTo(UPDATED_CONTRASENA);
    }

    @Test
    @Transactional
    void patchNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empleadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empleadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeDelete = empleadoRepository.findAll().size();

        // Delete the empleado
        restEmpleadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, empleado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
