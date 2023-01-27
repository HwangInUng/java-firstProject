<%@page import="com.jspshop.domain.Admin"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
// 현재 접속시도한 클라이언트와 연결된 세션
Admin admin = (Admin) session.getAttribute("admin");

out.print("<script>");
if (admin == null) {
	out.print("alert('로그인이 필요한 서비스입니다.');");
	out.print("history.back();");
}
out.print("</script>");

%>