package br.com.maikon.orderserviceapi.services;


import br.com.maikon.orderserviceapi.entities.Order;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;
import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(final Long id, UpdateOrderRequest request);

    void deleteById(final Long id);

    List<Order> findAll();

    Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy);
}
