package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.repository.ProductoRepository;
import com.mycompany.myapp.service.dto.ProductoDTO;
import com.mycompany.myapp.service.mapper.ProductoMapper;
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
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoResourceIT {

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

    private static final LocalDate DEFAULT_FECHA_VENTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENTA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRECIO_COMPRA = 1D;
    private static final Double UPDATED_PRECIO_COMPRA = 2D;

    private static final Double DEFAULT_PRECIO_BRUTO = 1D;
    private static final Double UPDATED_PRECIO_BRUTO = 2D;

    private static final Double DEFAULT_PRECIO_IVA = 1D;
    private static final Double UPDATED_PRECIO_IVA = 2D;

    private static final Boolean DEFAULT_VENDIDO = false;
    private static final Boolean UPDATED_VENDIDO = true;

    private static final String ENTITY_API_URL = "/api/productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMockMvc;

    private Producto producto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(DEFAULT_NOMBRE)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .descripcion(DEFAULT_DESCRIPCION)
            .peso(DEFAULT_PESO)
            .fechaVenta(DEFAULT_FECHA_VENTA)
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .precioBruto(DEFAULT_PRECIO_BRUTO)
            .precioIva(DEFAULT_PRECIO_IVA)
            .vendido(DEFAULT_VENDIDO);
        return producto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO)
            .fechaVenta(UPDATED_FECHA_VENTA)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioBruto(UPDATED_PRECIO_BRUTO)
            .precioIva(UPDATED_PRECIO_IVA)
            .vendido(UPDATED_VENDIDO);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();
        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProducto.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testProducto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testProducto.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testProducto.getFechaVenta()).isEqualTo(DEFAULT_FECHA_VENTA);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioBruto()).isEqualTo(DEFAULT_PRECIO_BRUTO);
        assertThat(testProducto.getPrecioIva()).isEqualTo(DEFAULT_PRECIO_IVA);
        assertThat(testProducto.getVendido()).isEqualTo(DEFAULT_VENDIDO);
    }

    @Test
    @Transactional
    void createProductoWithExistingId() throws Exception {
        // Create the Producto with an existing ID
        producto.setId(1L);
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setNombre(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setNumeroSerie(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setMarca(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setModelo(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setDescripcion(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPeso(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioCompraIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioCompra(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioBrutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioBruto(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioIva(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setVendido(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaVenta").value(hasItem(DEFAULT_FECHA_VENTA.toString())))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(DEFAULT_PRECIO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioBruto").value(hasItem(DEFAULT_PRECIO_BRUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].precioIva").value(hasItem(DEFAULT_PRECIO_IVA.doubleValue())))
            .andExpect(jsonPath("$.[*].vendido").value(hasItem(DEFAULT_VENDIDO.booleanValue())));
    }

    @Test
    @Transactional
    void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.fechaVenta").value(DEFAULT_FECHA_VENTA.toString()))
            .andExpect(jsonPath("$.precioCompra").value(DEFAULT_PRECIO_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.precioBruto").value(DEFAULT_PRECIO_BRUTO.doubleValue()))
            .andExpect(jsonPath("$.precioIva").value(DEFAULT_PRECIO_IVA.doubleValue()))
            .andExpect(jsonPath("$.vendido").value(DEFAULT_VENDIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO)
            .fechaVenta(UPDATED_FECHA_VENTA)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioBruto(UPDATED_PRECIO_BRUTO)
            .precioIva(UPDATED_PRECIO_IVA)
            .vendido(UPDATED_VENDIDO);
        ProductoDTO productoDTO = productoMapper.toDto(updatedProducto);

        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testProducto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProducto.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testProducto.getFechaVenta()).isEqualTo(UPDATED_FECHA_VENTA);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioBruto()).isEqualTo(UPDATED_PRECIO_BRUTO);
        assertThat(testProducto.getPrecioIva()).isEqualTo(UPDATED_PRECIO_IVA);
        assertThat(testProducto.getVendido()).isEqualTo(UPDATED_VENDIDO);
    }

    @Test
    @Transactional
    void putNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaVenta(UPDATED_FECHA_VENTA)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioIva(UPDATED_PRECIO_IVA)
            .vendido(UPDATED_VENDIDO);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testProducto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testProducto.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testProducto.getFechaVenta()).isEqualTo(UPDATED_FECHA_VENTA);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioBruto()).isEqualTo(DEFAULT_PRECIO_BRUTO);
        assertThat(testProducto.getPrecioIva()).isEqualTo(UPDATED_PRECIO_IVA);
        assertThat(testProducto.getVendido()).isEqualTo(UPDATED_VENDIDO);
    }

    @Test
    @Transactional
    void fullUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .descripcion(UPDATED_DESCRIPCION)
            .peso(UPDATED_PESO)
            .fechaVenta(UPDATED_FECHA_VENTA)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioBruto(UPDATED_PRECIO_BRUTO)
            .precioIva(UPDATED_PRECIO_IVA)
            .vendido(UPDATED_VENDIDO);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testProducto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProducto.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testProducto.getFechaVenta()).isEqualTo(UPDATED_FECHA_VENTA);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioBruto()).isEqualTo(UPDATED_PRECIO_BRUTO);
        assertThat(testProducto.getPrecioIva()).isEqualTo(UPDATED_PRECIO_IVA);
        assertThat(testProducto.getVendido()).isEqualTo(UPDATED_VENDIDO);
    }

    @Test
    @Transactional
    void patchNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, producto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
