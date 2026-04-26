package com.renovar.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.renovar.dao.DeviceDAO;
import com.renovar.dao.IndicatorDAO;
import com.renovar.dao.ReadingDAO;
import com.renovar.dao.UserDAO;
import com.renovar.domain.Coordinate;
import com.renovar.domain.Device;
import com.renovar.domain.Indicator;
import com.renovar.domain.Reading;
import com.renovar.domain.User;
import com.renovar.domain.enums.Unit;

import jakarta.annotation.PostConstruct;

@Service
public class DBService {

    private final Logger log = LoggerFactory.getLogger(DBService.class);

    private final DeviceDAO deviceDAO;
    private final IndicatorDAO indicatorDAO;
    private final ReadingDAO readingDAO;
    private final UserDAO userDAO;
    private final Environment environment;

    public DBService(DeviceDAO deviceDAO, IndicatorDAO indicatorDAO, ReadingDAO readingDAO,
                     UserDAO userDAO, Environment environment) {
        this.deviceDAO = deviceDAO;
        this.indicatorDAO = indicatorDAO;
        this.readingDAO = readingDAO;
        this.userDAO = userDAO;
        this.environment = environment;
    }

    // ── Seed inicial (somente perfil dev, executa uma única vez) ──────────────

    @PostConstruct
    public void seedInitialData() {
        boolean isDev = Arrays.asList(environment.getActiveProfiles()).contains("dev");
        if (!isDev) {
            return;
        }

        if (indicatorDAO.count() > 0) {
            log.info("Database already contains data — skipping initial seed.");
            return;
        }

        log.info("Seeding initial dev data...");

        Indicator temperature = new Indicator(null, "Temperature", Unit.TEMPERATURE, 40.0);
        indicatorDAO.save(temperature);

        User admin = new User(null, "admin@renovar.com");
        userDAO.save(admin);

        Coordinate coordinate = new Coordinate(-27.5969, -48.5495);
        Device sensor = new Device(null, "Sensor 01", null, coordinate, admin);
        sensor.getIndicators().add(temperature);
        deviceDAO.save(sensor);

        log.info("Seed completed: 1 indicator, 1 user, 1 device.");
    }

    // ── Mock data (chamado por TestConfig / DevConfig via @Bean) ──────────────

    public void initializeDatabase() throws InterruptedException {
        log.info("Creating mock objects");
        User user = new User(null, "franciscosft@gmail.com");
        userDAO.save(user);

        Coordinate coordinate1 = new Coordinate(-27.599645, -48.518083);
        Coordinate coordinate2 = new Coordinate(-27.6001426, -48.5182837);

        Device device1 = new Device(null, "Device 1", "abc", coordinate1, user);
        Device device2 = new Device(null, "Device 2", "cdf", coordinate2, user);

        Indicator co = new Indicator(null, "CO", Unit.CONCENTRATION, 0.8);
        Indicator mockTemperature = new Indicator(null, "Thermometer", Unit.TEMPERATURE);
        Indicator pressure = new Indicator(null, "Atmospheric Pressure", Unit.PRESSURE);

        device1.getIndicators().addAll(Arrays.asList(co, mockTemperature, pressure));
        device2.getIndicators().addAll(Arrays.asList(mockTemperature, pressure));

        indicatorDAO.saveAll(Arrays.asList(co, mockTemperature, pressure));
        deviceDAO.saveAll(Arrays.asList(device1, device2));

        log.info("Adding readings");
        ArrayList<Reading> readings = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, co));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, mockTemperature));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, pressure));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device2, mockTemperature));
            Thread.sleep(1000);
        }
        log.info("Readings saved");
        readingDAO.saveAll(readings);
    }

    public Date randomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2017, 2018);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

}