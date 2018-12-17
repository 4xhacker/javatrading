package com.compica.javatrading;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

import com.jfx.net.JFXServer;
@ComponentScan("com.compica.javatrading")
@SpringBootApplication
public class JavatradingApplication implements ApplicationRunner {
	@Autowired
	ServerBridge serverBridge;
	
	@Autowired
	ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(JavatradingApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	System.out.println("Your application started with option names :");
	List<String> port = new ArrayList<String>();
	if(StringUtils.isEmpty(args.getOptionValues("port"))) {
		port.add("8080");
	}
	else{
			port = args.getOptionValues("port");
	}
	
	System.out.println("port " + port.get(0));


	}
}
