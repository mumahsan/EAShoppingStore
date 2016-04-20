package edu.mum.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mum.domain.GenderLine;
import edu.mum.domain.Product;
import edu.mum.domain.ShoppingCart;
import edu.mum.service.ProductService;

@Controller
@RequestMapping(value="/product")
@SessionAttributes("checkoutProducts")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private List<Product> checkoutProducts = new ArrayList();
	
	//Add Product Show Form
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/addProduct", method=RequestMethod.GET)	
    public String showForm(@ModelAttribute("productAdd") Product product, Model model)
	{
//    	model.addAttribute("productLines",productService.getProductLines());
		model.addAttribute("genderEnum", GenderLine.values());
        return "newProduct";
    }

	//Add Product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
    public String processForm(@Valid @ModelAttribute("productAdd") Product product, BindingResult bindingResult, Model model, 
    		RedirectAttributes redirectAttributes, HttpServletRequest request)
    {
    	if (bindingResult.hasErrors()) {
			return "newProduct";
		}
 
    	String[] suppressedFields = bindingResult.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): " + StringUtils.addStringToArray(suppressedFields, ", "));
		}
		
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		
		if (productImage!=null && !productImage.isEmpty()) {
		       try {
		    	   productImage.transferTo(new File(rootDirectory+"\\resources\\images\\"+ product.getName() + ".jpg"));
		       } catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
		   }
		}
		
		productService.save(product);
		redirectAttributes.addFlashAttribute("product", product);
//		redirectAttributes.addFlashAttribute("rootDirectory", rootDirectory);
		return "redirect:/product/productSaved";
	
	}
	
	//Product Saved
	@RequestMapping(value="/productSaved", method=RequestMethod.GET)	
    public String ownerSaved(Model model)
    {		
		return "saveProduct";
	}
	
	//Show All Products
	@RequestMapping(value="/showProducts", method=RequestMethod.GET)
    public String showAllProducts(Model model)
	{
    	model.addAttribute("products", productService.getAllProducts());
        return "showProducts";
    }

	//Get Product Details
	@RequestMapping(value="/getProductDetail", method=RequestMethod.GET)	
    public String getProductDetail(@RequestParam("product_id") long id, Model model)
	{
		model.addAttribute("product",productService.getProduct(id));
        return "productDetail";
    }
	
	//Product Checkout
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/addToCart", method=RequestMethod.POST)	
    public String addToCart(@ModelAttribute("cartAdd") ShoppingCart shoppingCart, BindingResult bindingResult, Model model, 
    		RedirectAttributes redirectAttributes, HttpServletRequest request)
    {
    	if (bindingResult.hasErrors()) {
			return "showProducts";
		}
 
    	String[] suppressedFields = bindingResult.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): " + StringUtils.addStringToArray(suppressedFields, ", "));
		}
		
//		Product product = (Product)model.asMap().get("product");
//		checkoutProducts.add(productService.getProduct(id));
		checkoutProducts.add((Product)model.asMap().get("product"));
		return "redirect:/product/showProducts";
	
	}
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
 //       binder.setDisallowedFields(new String[]{"firstName"});
      }
}
