<%@page import="java.util.HashMap"%>
<%@page import="com.jspshop.util.PageManager"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.domain.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.jspshop.repository.ProductDAO"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	ProductDAO productDAO = new ProductDAO();
	PageManager pm = new PageManager();
%>

<%
	SqlSession sqlSession = mybatisConfig.getSqlSession();
	productDAO.setSession(sqlSession);
	
	//검색 카테고리 및 키워드 정보 저장
	String category = request.getParameter("category");
	String keyword = request.getParameter("keyword");
	HashMap<String, String> map = new HashMap();
	map.put("category", category);
	map.put("keyword", keyword);
	
	List<Product> list = productDAO.selectAll(map);
	pm.init(list, request);
%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AdminLTE 3 | Dashboard</title>
<%@ include file="/admin/inc/header_link.jsp"%>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">

		<!-- Preloader -->
		<%@ include file="/admin/inc/preloader.jsp"%>

		<!-- Navbar -->
		<%@ include file="/admin/inc/navbar.jsp"%>

		<!-- /.navbar -->

		<!-- mian sidbar -->
		<%@ include file="/admin/inc/sidebar_left.jsp"%>


		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0">상품목록</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item active">Dashboard v1</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<!-- Small boxes (Stat box) -->
					<div class="col">
						<!-- 테이블 영역 -->
							<div class="card">
              <div class="card-header">
                <h3 class="card-title">전체 상품수 : <%=list.size() %>개</h3>
								
								<!-- 검색 영역 -->
                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 300px;">
                  	<select class="form-control float-right" id="category">
                  		<option value="0">선택</option>
                  		<option value="product_name">상품명</option>
                  		<option value="brand">브랜드</option>
                  	</select>
                    <input type="text" id="keyword" class="form-control float-right" placeholder="Search">

                    <div class="input-group-append">
                      <button type="button" class="btn btn-default" id="search">
                        <i class="fas fa-search"></i>
                      </button>
                    </div>
                  </div>
                </div>
                <!-- /.검색 영역 -->
              </div>
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
                <table class="table table-hover text-nowrap">
                  <thead>
                    <tr>
                      <th>No</th>
                      <th>카테고리</th>
                      <th>상품명</th>
                      <th>브랜드</th>
                      <th>가격</th>
                      <th>할인가</th>
                    </tr>
                  </thead>
                  <!-- 리스트 생성영역 -->
                  <tbody>
                  	<%
                  	int num = pm.getNum();
                  	int curPos = pm.getCurPos();
                  	for(int i = 0; i < pm.getPageSize(); i++){
                  	if(num < 1) break;
                  	Product product = list.get(curPos++);
                  	%>
                  	<tr>
                  		<td><%=num-- %></td>
                  		<td><%=product.getCategory().getCategory_name() %></td>
                  		<td><%=product.getProduct_name() %></td>
                  		<td><%=product.getBrand() %></td>
                  		<td><%=product.getPrice() %></td>
                  		<td><%=product.getDiscount() %></td>
                  	</tr>
										<%}%>
                  </tbody>
                  <!-- /.리스트 생성영역 -->
                </table>
                <!-- paging 영역 -->
                <div class="card-footer clearfix">
                <ul class="pagination pagination-sm m-0 float-right">
                  <li class="page-item"><a class="page-link" href="#">«</a></li>
                  <%
                  	for(int i = pm.getFirstPage(); i < pm.getLastPage(); i++){ 
                  	if(i > pm.getTotalPage()) break;
                  %>
                  	<li class="page-item">
                  		<a class="page-link" href="/admin/product/list.jsp?currentPage=<%=i %>"><%=i %></a>
                  	</li>
                  <%} %>
                  <li class="page-item"><a class="page-link" href="#">»</a></li>
                </ul>
            	  </div>
                <!-- /.paging 영역 -->
              </div>
              <!-- /.card-body -->
            </div>
						<!-- /.테이블 영역 -->
					</div>
				</div>
		<!-- /.container-fluid -->
		</section>
		<!-- /.content -->
	</div>

	<!-- /.content-wrapper -->

	<!-- Footer -->
	<%@ include file="/admin/inc/footer.jsp"%>

	<!-- Control Sidebar -->
	<%@ include file="/admin/inc/sidebar_right.jsp"%>

	<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->

	<!-- Footer Link -->
	<%@ include file="/admin/inc/footer_link.jsp"%>
</body>
<script type="text/javascript">
	function search(){
		$.ajax({
			type: "GET",
			url: "/admin/product/list.jsp?category="+ $("#category").val() + "&keyword=" + $("#keyword").val(),
			success: function(result, status, xhr){
				//여기에 어떤 동작을 해야하는지 고민 필요
			}
		});
	}

	$(function(){
		$("#search").on("click", function(){
			search();
		});
	});
</script>
</html>
