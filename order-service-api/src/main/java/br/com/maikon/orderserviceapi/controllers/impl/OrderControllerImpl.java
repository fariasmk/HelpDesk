package br.com.maikon.orderserviceapi.controllers.impl;


import br.com.maikon.orderserviceapi.controllers.OrderController;
import br.com.maikon.orderserviceapi.services.OrderService;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;
import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }
}