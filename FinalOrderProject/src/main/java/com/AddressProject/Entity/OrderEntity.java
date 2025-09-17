package com.AddressProject.Entity;

public class OrderEntity {
	private ProductResponse product;
	private int orderno;
	
	public ProductResponse getProduct() {
		return product;
	}
	public void setProduct(ProductResponse product) {
		this.product = product;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
}