<%@page import="com.jspshop.util.Msg"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.repository.AdminDAO"%>
<%@page import="com.jspshop.domain.Admin"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%!
	AdminDAO adminDAO = new AdminDAO();
%>

<%
	String admin_id = request.getParameter("adminId");
	String admin_pass = request.getParameter("adminPass");
	
	Admin admin = new Admin();
	admin.setAdmin_id(admin_id);
	admin.setAdmin_pass(admin_pass);
	
	Admin result = adminDAO.select(admin);
	
	Msg msg = new Msg();
	Gson gson = new Gson();
	
	if(result != null){
		//세션에 접속정보 저장
		session.setAttribute("admin", result);
		msg.setCode(1);
		msg.setMsg("인증성공");
	} else{
		msg.setCode(0);
		msg.setMsg("로그인 정보가 올바르지 않습니다.");
	}
		String data = gson.toJson(msg);
		
		out.print(data);
%>