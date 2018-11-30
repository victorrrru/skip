package com.muyuan.platform.skip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.muyuan.platform")
@MapperScan("com.muyuan.platform.skip.mapper")
@EnableScheduling
public class SkipApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SkipApplication.class, args);
	}

}
