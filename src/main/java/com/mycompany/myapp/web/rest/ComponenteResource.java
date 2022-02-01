package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ComponenteRepository;
import com.mycompany.myapp.service.ComponenteService;
import com.mycompany.myapp.service.dto.ComponenteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Componente}.
 */
@RestController
@RequestMapping("/api")
public class ComponenteResource {

    private final Logger log = LoggerFactory.getLogger(ComponenteResource.class);

    private static final String ENTITY_NAME = "componente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComponenteService componenteService;

    private final ComponenteRepository componenteRepository;

    public ComponenteResource(ComponenteService componenteService, ComponenteRepository componenteRepository) {
        this.componenteService = componenteService;
        this.componenteRepository = componenteRepository;
    }

    /**
     * {@code POST  /componentes} : Create a new componente.
     *
     * @param componenteDTO the componenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new componenteDTO, or with status {@code 400 (Bad Request)} if the componente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/componentes")
    public ResponseEntity<ComponenteDTO> createComponente(@Valid @RequestBody ComponenteDTO componenteDTO) throws URISyntaxException {
        log.debug("REST request to save Componente : {}", componenteDTO);
        if (componenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new componente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComponenteDTO result = componenteService.save(componenteDTO);
        return ResponseEntity
            .created(new URI("/api/componentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /componentes/:id} : Updates an existing componente.
     *
     * @param id the id of the componenteDTO to save.
     * @param componenteDTO the componenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated componenteDTO,
     * or with status {@code 400 (Bad Request)} if the componenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the componenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/componentes/{id}")
    public ResponseEntity<ComponenteDTO> updateComponente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComponenteDTO componenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Componente : {}, {}", id, componenteDTO);
        if (componenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, componenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!componenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComponenteDTO result = componenteService.save(componenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, componenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /componentes/:id} : Partial updates given fields of an existing componente, field will ignore if it is null
     *
     * @param id the id of the componenteDTO to save.
     * @param componenteDTO the componenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated componenteDTO,
     * or with status {@code 400 (Bad Request)} if the componenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the componenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the componenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/componentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComponenteDTO> partialUpdateComponente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComponenteDTO componenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Componente partially : {}, {}", id, componenteDTO);
        if (componenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, componenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!componenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComponenteDTO> result = componenteService.partialUpdate(componenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, componenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /componentes} : get all the componentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of componentes in body.
     */
    @GetMapping("/componentes")
    public ResponseEntity<List<ComponenteDTO>> getAllComponentes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Componentes");
        Page<ComponenteDTO> page = componenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /componentes/:id} : get the "id" componente.
     *
     * @param id the id of the componenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the componenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/componentes/{id}")
    public ResponseEntity<ComponenteDTO> getComponente(@PathVariable Long id) {
        log.debug("REST request to get Componente : {}", id);
        Optional<ComponenteDTO> componenteDTO = componenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(componenteDTO);
    }

    /**
     * {@code DELETE  /componentes/:id} : delete the "id" componente.
     *
     * @param id the id of the componenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/componentes/{id}")
    public ResponseEntity<Void> deleteComponente(@PathVariable Long id) {
        log.debug("REST request to delete Componente : {}", id);
        componenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
