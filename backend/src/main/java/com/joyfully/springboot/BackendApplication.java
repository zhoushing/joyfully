package com.joyfully.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot应用程序
 *
 * @author marx
 * @date 2021/07/31
 */
@SpringBootApplication
@MapperScan("com.joyfully.springboot.mapper")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
