<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.domain.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.jspshop.repository.CategoryDAO"%>
<%@ page contentType="application/json;charset=utf-8"%>

<%!
	CategoryDAO categoryDAO = new CategoryDAO();
%>

<%
	List<Category> categoryList = categoryDAO.selectAll();

	Gson gson = new Gson();
	String result = gson.toJson(categoryList);
	
	out.print(result);
%>