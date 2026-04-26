package com.renovar;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.renovar.dao.UserDAO;
import com.renovar.domain.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RenovarApplicationTests {

    @Autowired
    UserDAO dao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void updateDiagnostic() {
        Optional<User> findById = dao.findById(1);
        User user = findById.get();
        user.setName("OtherName");

        List<User> findAll = dao.findAll();
        for (User u : findAll) {
            System.out.println(u);
        }

        dao.save(user);

        System.out.println("-----------------------------------------------");

        findAll = dao.findAll();
        for (User u : findAll) {
            System.out.println(u);
        }
    }

    @Test
    public void testEmail() {
        User foundUser = dao.findByEmail("franciscosft@gmail.com");
        assertNotNull(foundUser);
    }

}