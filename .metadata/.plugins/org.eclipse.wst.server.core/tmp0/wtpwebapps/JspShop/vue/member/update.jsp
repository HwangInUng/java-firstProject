<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.util.Msg"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="com.jspshop.domain.Member"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	MemberDAO memberDAO = new MemberDAO();
%>

<%
	request.setCharacterEncoding("utf-8");
	SqlSession sqlSession = mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	//empty DTO 생성
	Member member = new Member();
	member.setMember_idx(Integer.parseInt(request.getParameter("member_idx")));
	member.setMember_id(request.getParameter("member_id2"));
	member.setMember_pass(request.getParameter("member_pass2"));
	member.setMember_name(request.getParameter("member_name2"));
	member.setEmail(request.getParameter("email2"));
	
	Msg msg = new Msg();
	try{
		memberDAO.update(member);
		
		msg.setCode(1);
		msg.setMsg("수정성공");
	} catch(MemberException e){
		msg.setCode(0);
		msg.setMsg(e.getMessage());
	}
	
	mybatisConfig.release(sqlSession);
	out.print(new Gson().toJson(msg));
%>