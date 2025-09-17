package com.AddressProject.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.AddressProject.Entity.ProductResponse;

@FeignClient(name="ProductProject" ,path="/product-app")
//@RibbonClient(name="ProductProject") //## No Need to mention this LoadBalancer BCZ Spring Eureka Itself will take care of the loadbalancer ..No External Configuration Nothing...
public interface ProductServiceFeignClient {

	@GetMapping("/product")
	public ProductResponse getproductResponse();
}
