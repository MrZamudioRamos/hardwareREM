package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AlmacenRepository;
import com.mycompany.myapp.service.AlmacenService;
import com.mycompany.myapp.service.dto.AlmacenDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Almacen}.
 */
@RestController
@RequestMapping("/api")
public class AlmacenResource {

    private final Logger log = LoggerFactory.getLogger(AlmacenResource.class);

    private static final String ENTITY_NAME = "almacen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlmacenService almacenService;

    private final AlmacenRepository almacenRepository;

    public AlmacenResource(AlmacenService almacenService, AlmacenRepository almacenRepository) {
        this.almacenService = almacenService;
        this.almacenRepository = almacenRepository;
    }

    /**
     * {@code POST  /almacens} : Create a new almacen.
     *
     * @param almacenDTO the almacenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new almacenDTO, or with status {@code 400 (Bad Request)} if the almacen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/almacens")
    public ResponseEntity<AlmacenDTO> createAlmacen(@Valid @RequestBody AlmacenDTO almacenDTO) throws URISyntaxException {
        log.debug("REST request to save Almacen : {}", almacenDTO);
        if (almacenDTO.getId() != null) {
            throw new BadRequestAlertException("A new almacen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlmacenDTO result = almacenService.save(almacenDTO);
        return ResponseEntity
            .created(new URI("/api/almacens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /almacens/:id} : Updates an existing almacen.
     *
     * @param id the id of the almacenDTO to save.
     * @param almacenDTO the almacenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated almacenDTO,
     * or with status {@code 400 (Bad Request)} if the almacenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the almacenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/almacens/{id}")
    public ResponseEntity<AlmacenDTO> updateAlmacen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlmacenDTO almacenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Almacen : {}, {}", id, almacenDTO);
        if (almacenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, almacenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!almacenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlmacenDTO result = almacenService.save(almacenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, almacenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /almacens/:id} : Partial updates given fields of an existing almacen, field will ignore if it is null
     *
     * @param id the id of the almacenDTO to save.
     * @param almacenDTO the almacenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated almacenDTO,
     * or with status {@code 400 (Bad Request)} if the almacenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the almacenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the almacenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/almacens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlmacenDTO> partialUpdateAlmacen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlmacenDTO almacenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Almacen partially : {}, {}", id, almacenDTO);
        if (almacenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, almacenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!almacenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlmacenDTO> result = almacenService.partialUpdate(almacenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, almacenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /almacens} : get all the almacens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of almacens in body.
     */
    @GetMapping("/almacens")
    public ResponseEntity<List<AlmacenDTO>> getAllAlmacens(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Almacens");
        Page<AlmacenDTO> page = almacenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /almacens/:id} : get the "id" almacen.
     *
     * @param id the id of the almacenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the almacenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/almacens/{id}")
    public ResponseEntity<AlmacenDTO> getAlmacen(@PathVariable Long id) {
        log.debug("REST request to get Almacen : {}", id);
        Optional<AlmacenDTO> almacenDTO = almacenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(almacenDTO);
    }

    /**
     * {@code DELETE  /almacens/:id} : delete the "id" almacen.
     *
     * @param id the id of the almacenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/almacens/{id}")
    public ResponseEntity<Void> deleteAlmacen(@PathVariable Long id) {
        log.debug("REST request to delete Almacen : {}", id);
        almacenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
