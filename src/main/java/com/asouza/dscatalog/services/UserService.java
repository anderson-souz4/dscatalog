package com.asouza.dscatalog.services;

import com.asouza.dscatalog.dto.*;
import com.asouza.dscatalog.entities.Category;
import com.asouza.dscatalog.entities.Product;
import com.asouza.dscatalog.entities.Role;
import com.asouza.dscatalog.entities.User;
import com.asouza.dscatalog.repositories.CategoryRepository;
import com.asouza.dscatalog.repositories.ProductRepository;
import com.asouza.dscatalog.repositories.RoleRepository;
import com.asouza.dscatalog.repositories.UserRepository;
import com.asouza.dscatalog.services.exceptions.DataBaseException;
import com.asouza.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return all.map(product -> new UserDTO(product));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        User entity = byId.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO rolDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(rolDto.getId());
            entity.getRoles().add(role);
        }
    }
}
