package edu.mum.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	
	private List<Product> products = new ArrayList();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product)
	{
		products.add(product);
	}
	
	public boolean remove(Product product)
	{
		return products.remove(product);
	}
}
