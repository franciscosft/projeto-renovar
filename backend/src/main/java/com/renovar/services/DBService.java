package com.renovar.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class DBService {

    private final Logger log = LoggerFactory.getLogger(DBService.class);

    private final DeviceDAO deviceDAO;
    private final IndicatorDAO indicatorDAO;
    private final ReadingDAO readingDAO;
    private final UserDAO userDAO;

    public DBService(DeviceDAO deviceDAO, IndicatorDAO indicatorDAO, ReadingDAO readingDAO, UserDAO userDAO) {
        this.deviceDAO = deviceDAO;
        this.indicatorDAO = indicatorDAO;
        this.readingDAO = readingDAO;
        this.userDAO = userDAO;
    }

    public void initializeDatabase() throws InterruptedException {
        log.info("Creating mock objects");
        User user = new User(null, "Francisco", "Teixeira", "franciscosft@gmail.com", "123");
        userDAO.save(user);

        Coordinate coordinate1 = new Coordinate(-27.599645, -48.518083);
        Coordinate coordinate2 = new Coordinate(-27.6001426, -48.5182837);

        Device device1 = new Device(null, "Device 1", "abc", coordinate1, user);
        Device device2 = new Device(null, "Device 2", "cdf", coordinate2, user);

        Indicator co = new Indicator(null, "CO", Unit.CONCENTRATION, 0.8);
        Indicator temperature = new Indicator(null, "Thermometer", Unit.TEMPERATURE);
        Indicator pressure = new Indicator(null, "Atmospheric Pressure", Unit.PRESSURE);

        device1.getIndicators().addAll(Arrays.asList(co, temperature, pressure));
        device2.getIndicators().addAll(Arrays.asList(temperature, pressure));

        indicatorDAO.saveAll(Arrays.asList(co, temperature, pressure));
        deviceDAO.saveAll(Arrays.asList(device1, device2));

        log.info("Adding readings");
        ArrayList<Reading> readings = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, co));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, temperature));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device1, pressure));
            readings.add(new Reading(null, Math.random(), randomDate(), coordinate1, device2, temperature));
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
