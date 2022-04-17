package com.example.springboot.AutoID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcAutoConfig implements WebMvcConfigurer {

    //Jei norit, kad butu is dar kazkokios kitos vietos, reikia nurodyti pilna kelia
    // String myExternalFilePath = "file:C:/xampp/tomcat/webapps/ROOT/images/";//System.getProperty("catalina.base"); // end your path with a /
    //Sitas eina i tomcat home ir tada patikslinam kur guli img, po tokio config pasieksite per http://localhost:8080/images/cat.jpg
    String myExternalFilePath = "file:" + System.getProperty("catalina.base") + "/images/";//System.getProperty("catalina.base"); // end your path with a /

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(myExternalFilePath);
    }
}
