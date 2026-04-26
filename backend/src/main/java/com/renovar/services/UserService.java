package com.renovar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.renovar.dao.UserDAO;
import com.renovar.domain.User;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserDAO dao;

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
        return dao.save(user);
    }

    public User update(User user) {
        User existing = findById(user.getId());
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return dao.save(existing);
    }

    public Page<User> findPage(Integer page, Integer pageSize, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), orderBy);
        return dao.findAll(pageRequest);
    }

}