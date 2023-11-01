package br.com.maikon.orderserviceapi.controllers.impl;


import br.com.maikon.orderserviceapi.controllers.OrderController;
import br.com.maikon.orderserviceapi.mapper.OrderMapper;
import br.com.maikon.orderserviceapi.services.OrderService;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;
import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok().body(
                mapper.fromEntity(service.findById(id))
        );
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(
                mapper.fromEntities(service.findAll())
        );
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        return ResponseEntity.ok().body(
                service.findAllPaginated(page, linesPerPage, direction, orderBy).map(mapper::fromEntity)
        );
    }
}