package br.com.maikon.orderserviceapi.services;


import br.com.maikon.orderserviceapi.entities.Order;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;
import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(final Long id, UpdateOrderRequest request);
}
