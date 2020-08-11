package com.cloudmusic;

import com.cloudmusic.utils.CloudMusicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 程序启动类
 */
@SpringBootApplication
public class ApiApplication {




	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


}

