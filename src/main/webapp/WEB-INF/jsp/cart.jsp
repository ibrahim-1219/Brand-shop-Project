<%@page import="com.global.shop.projection.ProductDto"%>
<%@ page import="com.global.shop.entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>
    <style>
        /* Add some CSS styling here to enhance the appearance of the cart */
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
        }

        h2 {
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .message {
            color: #ff0000;
        }

        .total-price {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>My Cart</h2>
    <% if (request.getAttribute("Msg") != null) { %>
        <p class="message"><%= request.getAttribute("Msg") %></p>
    <% } 
    else 
    { %>
        <% List<ProductDto> products = (List<ProductDto>) request.getAttribute("products"); %>
        <% if (products != null && !products.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <% double totalPrice = 0.0; %>
                    <% for (ProductDto product : products) { %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td><%= product.getName() %></td>
                            <td><%= product.getPrice() %></td>
                        </tr>
                        <% totalPrice += product.getPrice(); %>
                    <% } %>
                    <tr>
                        <td colspan="2"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td class="total-price">Total : <%= totalPrice %></td>
                    </tr>
                </tbody>
            </table>
        <% } else { %>
            <p>Your cart is empty.</p>
        <% } %>
         <div class="options">
        <a href="/products/all">View All Products</a>
    </div>
    <% } %>
    
</body>
</html>