package edu.mum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.domain.Product;
import edu.mum.domain.ProductLine;
import edu.mum.repository.ProductLineRepository;
import edu.mum.repository.ProductRepository;
import edu.mum.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductRepository productRepository;
	
	/*@Autowired
	private ProductLineRepository productLineRepository;*/
	
	public void save(Product product)
	{
		productRepository.save(product);
		
	}

	public Iterable<Product> getAllProducts()
	{
		return productRepository.findAll();
		
	}

	public Product getProduct(long id)
	{
		return productRepository.findOne(id);
		
	}

	/*@Override
	public Iterable<ProductLine> getProductLines()
	{
		return productLineRepository.findAll();
	}*/

	/*@Override
	public List<String> getProductCategories(String productLine)
	{
		return productRepository.getProductCategories(productLine);
	}*/

	
}
