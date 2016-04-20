<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>

	<div style="height: 100px;"></div>
	<div
		style="font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 16px;">
		<table width=100%">
			<tr>
				<td align="center">
					<table width="40%" border="0" cellspacing="3" cellpadding="1">
						<tr>
							<td>
								<h1>
									<spring:message text="Your Order number : " + ${orderNo} />
								</h1>


							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</div>
</body>

</html>