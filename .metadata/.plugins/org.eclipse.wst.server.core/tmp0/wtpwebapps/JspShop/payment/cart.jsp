<%@page import="com.jspshop.domain.Member"%>
<%@page import="com.jspshop.domain.Cart"%>
<%@page import="com.jspshop.domain.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	//클라이언트의 장바구니 등록요청을 처리
	
	//요청이 최초 요청이라면 웹 컨테이넌은 세션 객체 및 ID할당
	//클라이언트의 브라우저 쿠키ID 존재여부 판단
	String sid = session.getId();
	System.out.println("현재 세션아이디 : " + sid);
	
	//장바구니에 등록한 상품을 표현하기 위한 List 준비
	List<Cart> cartList = new ArrayList();
	
	//세션에 저장
	//application 내장객체에 담을 경우 tomcat이 종료될 때까지 유지가능
	//초기화가 되지않도록 최초 등록만 허용
	if(session.getAttribute("cartList") == null){
		session.setAttribute("cartList", cartList);
	}
	
	//로그인한 유저를 대상으로 session.getAttribute()로 얻음
	Member member = new Member();
	member.setMember_idx(1);
	member.setMember_id("test");
	
	//해당 상품 받기
	String product_idx = request.getParameter("product_idx");

	Cart cart = new Cart(); //empty
	cart.setMember(member);
	cart.setProduct_idx(Integer.parseInt(product_idx));
	cart.setEa(1);
	
	//한건 장바구니에 담기
	//세션 내에 저장된 리스트에 접근하여 DTO(상품)를 저장
	List sessionCartList = (List)session.getAttribute("cartList");
	sessionCartList.add(cart);
	System.out.println(sessionCartList.size() + "건이 담겨있음");
	
	out.print("장바구니에 1건 담기완료");
%>