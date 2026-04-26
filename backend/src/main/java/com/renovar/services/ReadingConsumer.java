package com.renovar.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renovar.config.RabbitMQConfig;
import com.renovar.domain.Reading;
import com.renovar.dto.ReadingRequestDTO;

@Service
public class ReadingConsumer {

    private final Logger log = LoggerFactory.getLogger(ReadingConsumer.class);

    @Autowired
    private ReadingService readingService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(ReadingRequestDTO dto) {
        log.info("Received reading from queue — device: {}, indicator: {}", dto.deviceId(), dto.indicatorId());
        Reading reading = readingService.toReading(dto);
        readingService.save(reading);
        log.info("Reading persisted with id: {}", reading.getId());
    }

}