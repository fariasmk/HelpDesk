package com.maikon.userserviceapi.mapper;

import com.maikon.hdcommonslib.models.requests.CreateUserRequest;
import com.maikon.hdcommonslib.models.requests.UpdateUserRequest;
import com.maikon.hdcommonslib.models.responses.UserResponse;
import com.maikon.userserviceapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,//Estrategia de mapeamento de propriedades para valores que estao nulos.
        nullValueCheckStrategy = ALWAYS//Estrategia de checagem de nulos sera para todos os contratos.
)
public interface UserMapper {
    UserResponse fromEntity(final User entity);

    @Mapping(target = "id", ignore = true)
    User fromRequest(CreateUserRequest createUserRequest);

    @Mapping(target = "id", ignore = true)//Nao mapeia o id, pois nunca e modificado
    User update(UpdateUserRequest updateUserRequest, @MappingTarget User entity);//Copia as informaçoes de updateUserRequest para entity
}