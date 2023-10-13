package com.maikon.userserviceapi.service;

import com.maikon.hdcommonslib.models.exceptions.ResourceNotFoundException;
import com.maikon.hdcommonslib.models.requests.CreateUserRequest;
import com.maikon.hdcommonslib.models.requests.UpdateUserRequest;
import com.maikon.hdcommonslib.models.responses.UserResponse;
import com.maikon.userserviceapi.entity.User;
import com.maikon.userserviceapi.mapper.UserMapper;
import com.maikon.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor//Com essa anotaçao nao preciso do @Autowired
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public UserResponse findById(final String id) {
        return mapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailAlreadyExists(request.email(), null);
        repository.save(
                mapper.fromRequest(request)
                        .withPassword(encoder.encode(request.password()))
        );
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::fromEntity)
                .toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest request) {
        User entity = find(id);
        verifyIfEmailAlreadyExists(request.email(), id);
        return mapper.fromEntity(
                repository.save(
                        mapper.update(request, entity)
                                .withPassword(request.password() != null ? encoder.encode(request.password()) : entity.getPassword())
                )
        );
    }

    private User find(final String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
                ));
    }

    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        repository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))//Verifica se o id do user e diferente ao id passado, se for diferente,
                // verifica se email ja existe.
                .ifPresent(user -> {//Se existir, cria uma exception
                    throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
                });
    }
}