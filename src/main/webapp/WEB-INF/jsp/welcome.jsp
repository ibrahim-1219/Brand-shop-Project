<%-- <%@ page import="org.json.simple.JSONObject"%> --%>
<%@ page import="com.global.shop.entity.Customer"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Welcome</title>
<style>
/* Add some CSS styling here to enhance the appearance of the page */
body {
	font-family: Arial, sans-serif;
	background-color: #f1f1f1;
	display: flex;
	align-items: center;
	justify-content: center;
	height: 100vh;
	margin: 0;
	padding: 0;
	flex-direction: column; /* Added flex-direction property */
}

h2 {
	color: red;
	text-align: center;
}

a {
	color: blue;
	text-decoration: none;
}

a.highlight {
	color: blue;
}

.options {
	margin-top: 20px;
	text-align: center;
}

.options a {
	margin-right: 20px;
}
</style>
</head>
<body>
	<h2>
		Welcome
<%-- 		<%=t( ((Customer) request.getAttribute("customer")).getName(%> --%>
		to our site
	</h2>

	<div class="options">
		<a href="/products/all">View All Products </a> 
<%-- 		<a href="/users/cart?userId=<%=t( ((Customer) request.getAttribute("customer")).getId(%>"> View My Cart </a>  --%>
		<a href="/contact"> Contact Us </a>
	</div>

<%-- 	<%   User user = (User) request.getAttribute("user"); --%>
<!-- // // 	        JSONObject userJson = new JSONObject(); -->
<!-- // 	        userJson.put("id", user.getId()); -->
<!-- // 	        userJson.put("name", user.getName()); -->
<!-- // 	        userJson.put("email", user.getEmail()); -->
<!-- // 	        String userStr = userJson.toString();  -->
	 %> 
	 <script src="cookies.js"></script>
</body>
</html>