package com.meesmb.iprwc;

import com.meesmb.iprwc.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class IprwcApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprwcApplication.class, args);
	}

}
