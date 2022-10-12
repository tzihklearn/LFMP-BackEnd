package com.MinNiCup.lfmpbackend.utils.larkRobot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LarkRobotFinish implements CommandLineRunner {

    @Resource
    LarkRobot larkRobot;

    @Override
    public void run(String... args) throws Exception {
        larkRobot.finish();
    }
}
