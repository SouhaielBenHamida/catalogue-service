package com.sbh;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sbh.dao.CategoryRepository;
import com.sbh.dao.ProductRepository;
import com.sbh.entities.Category;
import com.sbh.entities.Product;

@SpringBootApplication
public class CatalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args ->{
			categoryRepository.deleteAll();
			Stream.of("C1 ordinateur","C2 Imprimentes").forEach(
					c -> categoryRepository.save(new Category(c.split(" ").clone()[0],c.split(" ").clone()[1],new ArrayList<>())) 
					);
			categoryRepository.findAll().forEach(System.out::println);
			
			
			productRepository.deleteAll();
			Category category = categoryRepository.findById("C1").get();
			Stream.of("P1","P2","P3","P4").forEach(
					name ->{
						Product p = productRepository.save(new Product(null, name, Math.random()*1000, category));
						category.getProducts().add(p);
						categoryRepository.save(category);
					}
					);
			
			Category category2 = categoryRepository.findById("C2").get();
			Stream.of("P5","P6","P7","P8").forEach(
					name -> {
						Product p = productRepository.save( new Product(null, name, Math.random()*1000, category2));
						category2.getProducts().add(p);
						categoryRepository.save(category2);
						}
					);
			
			productRepository.findAll().forEach(System.out::println);
		};
	}
}
