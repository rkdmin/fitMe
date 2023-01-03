package com.zerobase.fitme;

import com.zerobase.fitme.type.ColorType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
//@EnableCaching
//@SpringBootApplication
public class FitMeApplication {
    public static void main(String[] args) {
//        SpringApplication.run(FitMeApplication.class, args);
        List list = new ArrayList();
        list.add(ColorType.WHITE);
        ColorType color = null;
        if(!list.contains(color) || !list.contains("123")){
            System.out.println(1);
        }
    }
}
