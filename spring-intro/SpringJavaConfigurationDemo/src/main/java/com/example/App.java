package com.example;

import com.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Alien alien = context.getBean(Alien.class);
        alien.code();

//        Desktop dt1 = context.getBean("desktop",Desktop.class);
//        dt1.compile();
//
//        Desktop dt2 = context.getBean("desktop",Desktop.class);
//        dt2.compile();
    }
}
