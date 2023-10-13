package com.maikon.hdcommonslib.models.responses;


import com.maikon.hdcommonslib.models.enums.ProfileEnum;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Builder
public record UserResponse(//record substitui o Lombok
    String id,
    String name,
    String email,
    String password,
    Set<ProfileEnum> profiles
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}