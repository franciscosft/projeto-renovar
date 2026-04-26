package com.renovar;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.renovar.dao.UserDAO;
import com.renovar.domain.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class RenovarApplicationTests {

    @Autowired
    UserDAO dao;

    @MockBean
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void updateDiagnostic() {
        Optional<User> findById = dao.findById(1);
        User user = findById.get();

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