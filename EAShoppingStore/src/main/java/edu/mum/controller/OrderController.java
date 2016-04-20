package edu.mum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mum.domain.Address;
import edu.mum.domain.BillingInfo;
import edu.mum.domain.Customer;
import edu.mum.domain.Product;
import edu.mum.domain.ShoppingOrder;
import edu.mum.service.AddressService;
import edu.mum.service.BillingInfoService;

@Controller
// @RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private BillingInfoService billingInfoService;

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String showForm(@ModelAttribute("checkout") Customer customer, Model model) {
		// model.addAttribute("productLines",productService.getProductLines());
		model.addAttribute("shippingAdress", customer.getShippingAddress());
		model.addAttribute("billingInfo", customer.getBillingInfo());
		model.addAttribute("items", customer.getOrders().size());
//		model.addAttribute("totalPrice", customer.totoalPrice());
		return "checkout";
	}

	@RequestMapping(value = "/addShippingAddress", method = RequestMethod.GET)
	public String showFormShippingAddress(@ModelAttribute("shippingAddressAdd") Address address, Model model) {
		return "addShippingAddress";
	}

	@RequestMapping(value = "/addShippingAddress", method = RequestMethod.POST)
	public String processShippingAddress(@Valid @ModelAttribute("shippingAddressAdd") Address address,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "addShippingAddress";
		}
		String[] suppressedFields = bindingResult.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): "
					+ StringUtils.addStringToArray(suppressedFields, ", "));
		}
		Customer customer = (Customer) model.asMap().get("customerSession");

		addressService.save(address);
		customer.setShippingAddress(address);
		return "checkout";
	}

	@RequestMapping(value = "/addBillingInfo", method = RequestMethod.GET)
	public String showFormBillingInfo(@ModelAttribute("billingInfoAdd") BillingInfo billingInfo, Model model) {
		return "addBillingInfo";
	}

	@RequestMapping(value = "/addBillingInfo", method = RequestMethod.POST)
	public String processBillingInfo(@Valid @ModelAttribute("billingInfoAdd") BillingInfo billingInfo,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "addBillingInfo";
		}
		String[] suppressedFields = bindingResult.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): "
					+ StringUtils.addStringToArray(suppressedFields, ", "));
		}
		
		billingInfoService.save(billingInfo);
		Customer customer = (Customer) model.asMap().get("customerSession");
		customer.setBillingInfo(billingInfo);
		return "checkout";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String processPlaceOrder(@Valid @ModelAttribute("placeOrderAdd") ShoppingOrder shoppingOrder,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "checkout";
		}
		String[] suppressedFields = bindingResult.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): "
					+ StringUtils.addStringToArray(suppressedFields, ", "));
		}

		model.addAttribute("orderNo", shoppingOrder.getOrderNo());
		return "orderConfirmation";
	}
}