<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="src/main/webapp/resources/css/collapsiblePanel.css" />
<link type="text/css" rel="stylesheet"
	href="src/main/webapp/resources/css/checkout.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>U-Store Checkout</title>
<script>
	//$(document).ready(function()){

	// }
</script>
</head>
<body>
	<div style="float: left;">
		<h3 style="color: #c45500 !important">1. Choose a shipping
			address</h3>
		<div>
			<span style="font-weight: 700 !important"> Your addresses </span>
			<table>
				<c:if test="${!empty shippingAdress}">
					<tr>
						<td>${shippingAdress}</td>
					</tr>
				</c:if>
				<tr>
					<td><a href="<spring:url value='/addShippingAddress' />">
							Add New Address </a></td>
				</tr>
			</table>
		</div>
		<h3 style="color: #c45500 !important">2. Choose a payment method</h3>
		<div class="list">
			<span style="font-weight: 700 !important"> Your credit and
				debit cards </span>
			<table>
				<tr>
					<td>${billingInfo}</td>
				</tr>
				<tr>
					<td><a href="<spring:url value='/addBillingInfo' />"> Add
							Billing Information </a></td>
				</tr>
			</table>
		</div>
	</div>
	<div style="float: right; margin: 5% 50% 0 0;">
		<div>
			<h3 style="font-weight: 700 !important;">Order Summary</h3>
			<table>
				<c:if test="${!empty customer.orders}">
					<tr>
						<td>Items:</td>
						<td>${items}</td>
					</tr>
					<tr>
						<td>Total Price:</td>
						<td>${totalPrice}</td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>
	<div style="float: right; margin: 5% 50% 0 0;">
		<input id="submit" type="submit" value="Place Your Order">
	</div>

</body>
</html>
