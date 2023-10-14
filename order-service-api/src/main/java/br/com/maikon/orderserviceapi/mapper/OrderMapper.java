package br.com.maikon.orderserviceapi.mapper;


import br.com.maikon.orderserviceapi.entities.Order;
import com.maikon.hdcommonslib.models.enums.OrderStatusEnum;
import com.maikon.hdcommonslib.models.requests.CreateOrderRequest;

import com.maikon.hdcommonslib.models.requests.UpdateOrderRequest;
import com.maikon.hdcommonslib.models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;


import java.time.LocalDateTime;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    @Mapping(target = "createdAt", expression = "java(mapCreatedAt())")
    Order fromRequest(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)//Ignora o ID
    @Mapping(target = "createdAt", ignore = true)//Ignora a data de criação
    @Mapping(target = "status", source = "request.status", qualifiedByName = "mapStatus")
    Order fromRequest(@MappingTarget Order entity, UpdateOrderRequest request);

    OrderResponse fromEntity(Order save);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }

    default LocalDateTime mapCreatedAt() {
        return LocalDateTime.now();
    }
}