package com.versionone.demo1server;


import com.versionone.demo1server.threads.BootList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo1ServerApplication.class, args);
        BootList.start();
    }

}
