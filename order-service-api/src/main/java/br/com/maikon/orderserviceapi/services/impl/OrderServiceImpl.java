package br.com.maikon.orderserviceapi.services.impl;

import br.com.maikon.orderserviceapi.clients.UserServiceFeignClient;
import br.com.maikon.orderserviceapi.entities.Order;
import br.com.maikon.orderserviceapi.mapper.OrderMapper;
import br.com.maikon.orderserviceapi.repositories.OrderRepository;
import br.com.maikon.orderserviceapi.services.OrderService;
import com.maikon.hdcommonslib.models.exceptions.ResourceNotFoundException;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;
import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;
import com.maikon.hdcommonslib.models.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.maikon.hdcommonslib.models.enums.OrderStatusEnum.CLOSED;
import static java.time.LocalDateTime.now;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public Order findById(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                ));
    }

    @Override
    public void save(CreateOrderRequest request) {
        final var requester = validateUserId(request.requesterId());
        final var customer = validateUserId(request.customerId());

        log.info("Requester: {}", requester);
        log.info("Customer: {}", customer);

        final var entity = repository.save(mapper.fromRequest(request));
        log.info("Order created: {}", entity);
    }

    @Override
    public OrderResponse update(final Long id, UpdateOrderRequest request) {
        validateUsers(request);

        Order entity = findById(id);
        entity = mapper.fromRequest(entity, request);

        if (entity.getStatus().equals(CLOSED)) {
            entity.setClosedAt(now());
        }

        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public void deleteById(final Long id) {
        repository.delete(findById(id));
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                org.springframework.data.domain.Sort.Direction.valueOf(direction),
                orderBy
        );

        return repository.findAll(pageRequest);
    }

    UserResponse validateUserId(final String userId) {
        return userServiceFeignClient.findById(userId).getBody();//getBody pega o conteudo do corpo da requisição
    }

    private void validateUsers(UpdateOrderRequest request) {
        if(request.requesterId() != null) validateUserId(request.requesterId());
        if(request.customerId() != null) validateUserId(request.customerId());
    }
}