package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
public class InfoController {

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getPortNumber() {
        return serverPort;
    }

    @GetMapping("/sum")
    public int getSum() {
        long timeStart = System.currentTimeMillis();


        Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);

//        LongStream.rangeClosed(1, 1_000_000)
//                .parallel()
//                .reduce(0, Long::sum);

        long timeFinish = System.currentTimeMillis() - timeStart;
        System.out.printf("timeStart %d \n", timeFinish);
        return (int) timeFinish;
    }
}
