package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.IvaRepository;
import com.mycompany.myapp.service.IvaService;
import com.mycompany.myapp.service.dto.IvaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Iva}.
 */
@RestController
@RequestMapping("/api")
public class IvaResource {

    private final Logger log = LoggerFactory.getLogger(IvaResource.class);

    private static final String ENTITY_NAME = "iva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IvaService ivaService;

    private final IvaRepository ivaRepository;

    public IvaResource(IvaService ivaService, IvaRepository ivaRepository) {
        this.ivaService = ivaService;
        this.ivaRepository = ivaRepository;
    }

    /**
     * {@code POST  /ivas} : Create a new iva.
     *
     * @param ivaDTO the ivaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ivaDTO, or with status {@code 400 (Bad Request)} if the iva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ivas")
    public ResponseEntity<IvaDTO> createIva(@RequestBody IvaDTO ivaDTO) throws URISyntaxException {
        log.debug("REST request to save Iva : {}", ivaDTO);
        if (ivaDTO.getId() != null) {
            throw new BadRequestAlertException("A new iva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IvaDTO result = ivaService.save(ivaDTO);
        return ResponseEntity
            .created(new URI("/api/ivas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ivas/:id} : Updates an existing iva.
     *
     * @param id the id of the ivaDTO to save.
     * @param ivaDTO the ivaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ivaDTO,
     * or with status {@code 400 (Bad Request)} if the ivaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ivaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ivas/{id}")
    public ResponseEntity<IvaDTO> updateIva(@PathVariable(value = "id", required = false) final Long id, @RequestBody IvaDTO ivaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Iva : {}, {}", id, ivaDTO);
        if (ivaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ivaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ivaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IvaDTO result = ivaService.save(ivaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ivaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ivas/:id} : Partial updates given fields of an existing iva, field will ignore if it is null
     *
     * @param id the id of the ivaDTO to save.
     * @param ivaDTO the ivaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ivaDTO,
     * or with status {@code 400 (Bad Request)} if the ivaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ivaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ivaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ivas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IvaDTO> partialUpdateIva(@PathVariable(value = "id", required = false) final Long id, @RequestBody IvaDTO ivaDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Iva partially : {}, {}", id, ivaDTO);
        if (ivaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ivaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ivaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IvaDTO> result = ivaService.partialUpdate(ivaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ivaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ivas} : get all the ivas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ivas in body.
     */
    @GetMapping("/ivas")
    public ResponseEntity<List<IvaDTO>> getAllIvas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ivas");
        Page<IvaDTO> page = ivaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ivas/:id} : get the "id" iva.
     *
     * @param id the id of the ivaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ivaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ivas/{id}")
    public ResponseEntity<IvaDTO> getIva(@PathVariable Long id) {
        log.debug("REST request to get Iva : {}", id);
        Optional<IvaDTO> ivaDTO = ivaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ivaDTO);
    }

    /**
     * {@code DELETE  /ivas/:id} : delete the "id" iva.
     *
     * @param id the id of the ivaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ivas/{id}")
    public ResponseEntity<Void> deleteIva(@PathVariable Long id) {
        log.debug("REST request to delete Iva : {}", id);
        ivaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
