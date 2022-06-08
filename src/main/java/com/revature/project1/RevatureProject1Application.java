package com.revature.project1;

import com.revature.project1.annotations.Author;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Author(authorName = "Leo Schaffner",
		description = "Application Start")
@SpringBootApplication
@EnableAspectJAutoProxy
public class RevatureProject1Application {
	public static void main(String[] args) { SpringApplication.run(RevatureProject1Application.class, args); }
}
