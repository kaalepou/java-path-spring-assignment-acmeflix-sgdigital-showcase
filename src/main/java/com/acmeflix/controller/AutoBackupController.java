package com.acmeflix.controller;

import java.io.IOException;
import java.util.Date;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author kalepou
 */
@Configuration
@EnableScheduling
public class AutoBackupController {

  @Scheduled(cron = "0 30 1 * * ?")
  public void schedule() {
    System.out.println("Backup Started at " + new Date());

    // Logic for backup to be added here

    // execute command;
    //TODO
    String executeCmd = "";


    Process runtimeProcess = null;
    try {
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    int processComplete = 0;
    try {
      processComplete = runtimeProcess.waitFor();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (processComplete == 0) {
      System.out.println("Backup Complete at " + new Date());
    } else {
      System.out.println("Backup Failure");
    }
  }

}
