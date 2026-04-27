package com.renovar.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renovar.dao.UserDAO;
import com.renovar.domain.User;
import com.renovar.services.exceptions.DataIntegrityException;
import com.renovar.services.exceptions.ObjectNotFoundException;
import com.renovar.services.exceptions.UserAlreadyExistsException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO dao;

    public User findById(Integer id) {
        Optional<User> result = dao.findById(id);
        return result.orElseThrow(() -> new ObjectNotFoundException(
                "User not found: " + id + ", Type: " + User.class.getName()));
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public User save(User user) {
        user.setId(null);
        if (dao.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("E-mail already exists");
        }
        return dao.save(user);
    }

    public User update(User user) {
        User existing = findById(user.getId());
        User userWithEmail = dao.findByEmail(user.getEmail());
        if (userWithEmail != null && !userWithEmail.getId().equals(existing.getId())) {
            throw new UserAlreadyExistsException("E-mail already exists");
        }
        existing.setEmail(user.getEmail());
        return dao.save(existing);
    }

    public Page<User> findPage(Integer page, Integer pageSize, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findAll(pageRequest);
    }

}