package com.AddressProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AddressProject.Entity.OrderEntity;
import com.AddressProject.Entity.ProductResponse;
import com.AddressProject.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/order")
	public OrderEntity getOrderDetails() {
		OrderEntity order = new OrderEntity();
		order.setOrderno(100);
//		## this will connect to ProductService and give the response..
		ProductResponse productResponse = orderService.getproductDetails();
		order.setProduct(productResponse);
		return order;
	}

}
