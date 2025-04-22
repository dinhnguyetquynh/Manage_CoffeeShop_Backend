package com.example.manage_coffeeshop_dataservice;

import com.example.manage_coffeeshop_dataservice.model.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageCoffeeshopDataserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(ManageCoffeeshopDataserviceApplication.class, args);
		Category cate = new Category(1,"Cà phe","KKK");
		System.out.println(cate.toString());

	}

}
