package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.repository.PedidoRepository;
import com.mycompany.myapp.service.dto.PedidoDTO;
import com.mycompany.myapp.service.mapper.PedidoMapper;
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
 * Integration tests for the {@link PedidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PedidoResourceIT {

    private static final String DEFAULT_DIRECCION_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_ENTREGA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_PEDIDO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PEDIDO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_DESCUENTO = 1D;
    private static final Double UPDATED_DESCUENTO = 2D;

    private static final String DEFAULT_TIPO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PAGO = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_TOTAL = 1D;
    private static final Double UPDATED_PRECIO_TOTAL = 2D;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENTREGADO = false;
    private static final Boolean UPDATED_ENTREGADO = true;

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final String ENTITY_API_URL = "/api/pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .direccionEntrega(DEFAULT_DIRECCION_ENTREGA)
            .fechaPedido(DEFAULT_FECHA_PEDIDO)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .descuento(DEFAULT_DESCUENTO)
            .tipoPago(DEFAULT_TIPO_PAGO)
            .precioTotal(DEFAULT_PRECIO_TOTAL)
            .observaciones(DEFAULT_OBSERVACIONES)
            .entregado(DEFAULT_ENTREGADO)
            .enviado(DEFAULT_ENVIADO);
        return pedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pedido createUpdatedEntity(EntityManager em) {
        Pedido pedido = new Pedido()
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .descuento(UPDATED_DESCUENTO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .observaciones(UPDATED_OBSERVACIONES)
            .entregado(UPDATED_ENTREGADO)
            .enviado(UPDATED_ENVIADO);
        return pedido;
    }

    @BeforeEach
    public void initTest() {
        pedido = createEntity(em);
    }

    @Test
    @Transactional
    void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();
        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);
        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getDireccionEntrega()).isEqualTo(DEFAULT_DIRECCION_ENTREGA);
        assertThat(testPedido.getFechaPedido()).isEqualTo(DEFAULT_FECHA_PEDIDO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testPedido.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testPedido.getTipoPago()).isEqualTo(DEFAULT_TIPO_PAGO);
        assertThat(testPedido.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
        assertThat(testPedido.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testPedido.getEntregado()).isEqualTo(DEFAULT_ENTREGADO);
        assertThat(testPedido.getEnviado()).isEqualTo(DEFAULT_ENVIADO);
    }

    @Test
    @Transactional
    void createPedidoWithExistingId() throws Exception {
        // Create the Pedido with an existing ID
        pedido.setId(1L);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDireccionEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setDireccionEntrega(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaPedidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setFechaPedido(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setFechaEntrega(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setTipoPago(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setPrecioTotal(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntregadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setEntregado(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnviadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pedidoRepository.findAll().size();
        // set the field null
        pedido.setEnviado(null);

        // Create the Pedido, which fails.
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        restPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isBadRequest());

        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get all the pedidoList
        restPedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccionEntrega").value(hasItem(DEFAULT_DIRECCION_ENTREGA)))
            .andExpect(jsonPath("$.[*].fechaPedido").value(hasItem(DEFAULT_FECHA_PEDIDO.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].tipoPago").value(hasItem(DEFAULT_TIPO_PAGO)))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].entregado").value(hasItem(DEFAULT_ENTREGADO.booleanValue())))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())));
    }

    @Test
    @Transactional
    void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        // Get the pedido
        restPedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId().intValue()))
            .andExpect(jsonPath("$.direccionEntrega").value(DEFAULT_DIRECCION_ENTREGA))
            .andExpect(jsonPath("$.fechaPedido").value(DEFAULT_FECHA_PEDIDO.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO.doubleValue()))
            .andExpect(jsonPath("$.tipoPago").value(DEFAULT_TIPO_PAGO))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.entregado").value(DEFAULT_ENTREGADO.booleanValue()))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        // Disconnect from session so that the updates on updatedPedido are not directly saved in db
        em.detach(updatedPedido);
        updatedPedido
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .descuento(UPDATED_DESCUENTO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .observaciones(UPDATED_OBSERVACIONES)
            .entregado(UPDATED_ENTREGADO)
            .enviado(UPDATED_ENVIADO);
        PedidoDTO pedidoDTO = pedidoMapper.toDto(updatedPedido);

        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getDireccionEntrega()).isEqualTo(UPDATED_DIRECCION_ENTREGA);
        assertThat(testPedido.getFechaPedido()).isEqualTo(UPDATED_FECHA_PEDIDO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testPedido.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testPedido.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testPedido.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
        assertThat(testPedido.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPedido.getEntregado()).isEqualTo(UPDATED_ENTREGADO);
        assertThat(testPedido.getEnviado()).isEqualTo(UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    void putNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pedidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePedidoWithPatch() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido using partial update
        Pedido partialUpdatedPedido = new Pedido();
        partialUpdatedPedido.setId(pedido.getId());

        partialUpdatedPedido
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .tipoPago(UPDATED_TIPO_PAGO)
            .observaciones(UPDATED_OBSERVACIONES);

        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPedido))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getDireccionEntrega()).isEqualTo(UPDATED_DIRECCION_ENTREGA);
        assertThat(testPedido.getFechaPedido()).isEqualTo(DEFAULT_FECHA_PEDIDO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testPedido.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testPedido.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testPedido.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
        assertThat(testPedido.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPedido.getEntregado()).isEqualTo(DEFAULT_ENTREGADO);
        assertThat(testPedido.getEnviado()).isEqualTo(DEFAULT_ENVIADO);
    }

    @Test
    @Transactional
    void fullUpdatePedidoWithPatch() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido using partial update
        Pedido partialUpdatedPedido = new Pedido();
        partialUpdatedPedido.setId(pedido.getId());

        partialUpdatedPedido
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .fechaPedido(UPDATED_FECHA_PEDIDO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .descuento(UPDATED_DESCUENTO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .observaciones(UPDATED_OBSERVACIONES)
            .entregado(UPDATED_ENTREGADO)
            .enviado(UPDATED_ENVIADO);

        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPedido))
            )
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getDireccionEntrega()).isEqualTo(UPDATED_DIRECCION_ENTREGA);
        assertThat(testPedido.getFechaPedido()).isEqualTo(UPDATED_FECHA_PEDIDO);
        assertThat(testPedido.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testPedido.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testPedido.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testPedido.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
        assertThat(testPedido.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPedido.getEntregado()).isEqualTo(UPDATED_ENTREGADO);
        assertThat(testPedido.getEnviado()).isEqualTo(UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    void patchNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pedidoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();
        pedido.setId(count.incrementAndGet());

        // Create the Pedido
        PedidoDTO pedidoDTO = pedidoMapper.toDto(pedido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pedidoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.saveAndFlush(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Delete the pedido
        restPedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pedido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
