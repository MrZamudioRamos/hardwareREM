package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.repository.PedidoRepository;
import com.mycompany.myapp.repository.ProductoRepository;
import com.mycompany.myapp.repository.specification.PedidoSpecification;
import com.mycompany.myapp.service.PedidoService;
import com.mycompany.myapp.service.ProductoService;
import com.mycompany.myapp.service.dto.PedidoDTO;
import com.mycompany.myapp.service.mapper.PedidoMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    private final ProductoRepository productoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoRepository productoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        log.debug("Request to save Pedido : {}", pedidoDTO);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        double total = 0;

        if (null != pedido.getProductos()) {
            for (Producto producto : pedido.getProductos()) {
                producto.setPedido(pedido);
                producto.setVendido(true);
                total = producto.getPrecioIva() + total;
                productoRepository.save(producto);
            }
            pedido.setPrecioTotal(total);
        }
        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toDto(pedido);
    }

    @Override
    public Optional<PedidoDTO> partialUpdate(PedidoDTO pedidoDTO) {
        log.debug("Request to partially update Pedido : {}", pedidoDTO);

        return pedidoRepository
            .findById(pedidoDTO.getId())
            .map(existingPedido -> {
                pedidoMapper.partialUpdate(existingPedido, pedidoDTO);

                return existingPedido;
            })
            .map(pedidoRepository::save)
            .map(pedidoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable).map(pedidoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDTO> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findById(id).map(pedidoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }

    public Page<PedidoDTO> findAllBySearchingParam(String filtro, Pageable pageable) {
        log.debug("Filtro");
        return pedidoRepository.findAll(PedidoSpecification.searchingParam(filtro), pageable).map(pedidoMapper::toDto);
    }

    public List<PedidoDTO> findAllByEmpleado(Empleado empleado) {
        log.debug("Encontrar pedidos de un empleado");
        return pedidoRepository
            .findAllByEmpleado(empleado)
            .stream()
            .filter(Objects::nonNull)
            .map(pedidoMapper::toDto)
            .collect(Collectors.toList());
    }
}
