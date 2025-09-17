package com.AddressProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.AddressProject.Entity.ProductEntity;

@RestController
public class ProductController {
	
	@GetMapping("/product")
	public ProductEntity getProductDetails() {
		ProductEntity product = new ProductEntity("Tv" , 2, "20000" );
		return product;
	}

}
