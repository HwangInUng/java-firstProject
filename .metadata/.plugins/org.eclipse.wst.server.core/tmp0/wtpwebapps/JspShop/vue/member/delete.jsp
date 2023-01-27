<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.util.Msg"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	MemberDAO memberDAO = new MemberDAO();
%>

<%
	Msg msg = new Msg();
	SqlSession sqlSession = mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);

	try{
		memberDAO.delete(Integer.parseInt(request.getParameter("member_idx")));
		msg.setCode(1);
		msg.setMsg("삭제 성공");
	} catch(MemberException e){
		msg.setCode(0);
		msg.setMsg(e.getMessage());
	}
	
	mybatisConfig.release(sqlSession);
	out.print(new Gson().toJson(msg));
%>