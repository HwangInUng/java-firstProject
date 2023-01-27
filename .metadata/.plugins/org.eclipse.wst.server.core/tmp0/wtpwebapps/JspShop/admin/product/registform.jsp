<%@ page contentType="text/html;charset=UTF-8"%>
<%
String[] sizeList = {"XS", "S", "M", "L", "XL", "XXL"};
String[] colorList = {"베이지", "네이비", "브라운", "블랙"};
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
							<h1 class="m-0">상품 개별등록</h1>
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
						<!-- general form elements -->
						<div class="card card-dark">
							<div class="card-header">
								<h3 class="card-title">상품등록</h3>
							</div>
							<!-- /.card-header -->
							<!-- form start -->
							<form id="registForm">
								<div class="card-body">
									<div class="form-group">
										<select class="form-control" id="categorySelect"></select>
									</div>
									<div class="form-group">
										<label for="product_name">상품명</label> <input type="text" class="form-control" id="product_name" placeholder="상품명">
									</div>
									<div class="form-group">
										<label for="brand">브랜드</label> <input type="text" class="form-control" id="brand" placeholder="브랜드">
									</div>
									<div class="form-group">
										<label for="price">가격</label> <input type="text" class="form-control" id="price" placeholder="가격">
									</div>
									<div class="form-group">
										<label for="discount">할인가</label> <input type="text" class="form-control" id="discount" placeholder="할인가">
									</div>

									<div class="card card-secondary">
									
										<div class="card-header">
											<h3 class="card-title">사이즈 및 색상</h3>
										</div>
											
										<div class="card-body">
											<!-- Minimal style -->
											<div class="row">
												<div class="col">
												<!-- checkbox -->
												<div class="form-group clearfix">
													<!-- size area -->
													<div class="row">
														<div class="col">
															<!-- checkbox -->
															<div class="form-group clearfix">
																<%for(int i = 0; i < sizeList.length; i++){%>
																<div class="icheck-dark d-inline">
																	<input type="checkbox" id="size<%=i %>" value="<%=sizeList[i] %>" name="size">
																	<label for="size<%=i %>"> <%=sizeList[i] %> </label>
																</div>
																<%}%>
															</div>
														</div>
													</div>
													<!-- color area -->
													<div class="row mt-3">
														<div class="col">
															<!-- checkbox -->
															<div class="form-group clearfix">
																<%for(int i = 0; i < colorList.length; i++){%>
																<div class="icheck-dark d-inline">
																	<input type="checkbox" id="color<%=i %>" value="<%=colorList[i] %>" name="color">
																	<label for="color<%=i %>"> <%=colorList[i] %> </label>
																</div>
																<%}%>
															</div>
														</div>
													</div>
													
													</div>
												</div>
											</div>
											</div>
											<!-- /.card-body -->
											
											<div class="card-footer">
												Many more skins available. <a href="https://bantikyan.github.io/icheck-bootstrap/">Documentation</a>
											</div>
										</div>
											
											<div class="form-group">
												<label for="exampleInputPassword1">상세내용</label>
												<textarea class="form-control" id="detail"></textarea>
											</div>
											
											<div class="form-group">
												<label for="exampleInputFile">이미지 등록</label>

												<div class="custom-file">
													<span class="btn btn-success col fileinput-button dz-clickable" onclick="triggerFile()">
														<i class="fas fa-plus"></i>
														<span>Add files</span>
													</span>
													<input type="file" class="custom-file-input" id="file" multiple/>
												</div>

											</div>
											
											<div class="row form-group" id="preview"></div>

									</div>
									<!-- /.card-body -->
									
										<div class="card-footer">
											<button type="button" class="btn btn-dark" id="regist">등록</button>
											<button type="button" class="btn btn-dark" id="list">목록</button>
										</div>
									</form>
									</div>
									<!-- /.card -->

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
	
	<script type="text/babel">
		let tag = [];
		let root;
		let fileList = [];

		function triggerFile(){
			$("#file").trigger("click");
		}

		// 등록
		function regist(){
			let formData = new FormData();

			formData.append("category_idx", $("#categorySelect").val());
			formData.append("product_name", $("#product_name").val());
			formData.append("brand", $("#brand").val());
			formData.append("price", $("#price").val());
			formData.append("discount", $("#discount").val());
			formData.append("detail", $("#detail").val());

			// 사이즈 데이터 쌓기

			//for(let i = 0; i < $("input[name='size']").length; i++){
			//	if($("input[name='size']")[i].checked == true){
			//		formData.append("size", $($("input[name='size']")[i]).val());
			//	}
			//}
			let sizeCheckedArray = [];
			$.each($("input[name='size']:checked"), function(){
				//$(this)는 each문 내의 현재 객체 자신을가르킴
				sizeCheckedArray.push($(this).val());
			});
			formData.append("size[]", sizeCheckedArray);

			// 색상 데이터 쌓기
			//for(let i = 0; i < $("input[name='color']").length; i++){
			//	if($("input[name='color']")[i].checked == true){
			//		formData.append("color", $($("input[name='color']")[i]).val());
			//	}
			//}
			let colorCheckedArray = [];
			$.each($("input[name='color']:checked"), function(){
				//$(this)는 each문 내의 현재 객체 자신을가르킴
				colorCheckedArray.push($(this).val());
			});
			formData.append("color[]", colorCheckedArray);

			for(let i = 0; i < fileList.length; i++){
				formData.append("file", fileList[i]);
			}

			$.ajax({
				url: "/admin/product/regist.jsp",
				type: "POST",
				processData: false,
				contentType: false,
				data: formData,
				success: function(result, status, xhr){
					alert(result);
				}				
			});
		}

		// 이미지 삭제
		function removeImg(e, file){
			$(e.target).parent().parent().remove();

			let index = fileList.indexOf(file); //객체(인스턴스)의 주소값을 비교
			fileList.splice(index ,1);
			tag.splice(index, 1);
			root.render(tag);
		}

		// 선택한 이미지 생성(리액트 컴포넌트)
		function ImgBox(props){
			return (
					<div className={"col-sm-2"}>
						<div>
							<a href="#" onClick={(e) => removeImg(e, props.file)}>X</a>
						</div>
						<img src={props.src} width="100px"/>
					</div>
			)
		}

		// 선택한 파일 미리보기
		function previewImg(){
			//리액트 컴포넌트 저장 배열 초기화
			tag = [];

			for(let i = 0; i < fileList.length; i++){
				let reader = new FileReader();
				//파일을 읽어드리기 시작
				reader.onload = function(e){
					console.log(fileList[i]);
					//리액트 컴포넌트 누적
					tag.push(<ImgBox key={i} src={e.target.result} file={fileList[i]}/>); //파일 객체를 넘김
					if(i == fileList.length-1){
						//루트에 랜더링 해주는 메서드(쌓여있는 배열 전체를 한번만 넘겨야함)
						root.render(tag);
					}
				}
				reader.readAsDataURL(fileList[i]);
			}
		}

		// 카테고리 옵션 생성
		function createCategoryOption(list) {

			let option = "<option value='0'>카테고리 선택</option>";
			for (let i = 0; i < list.length; i++) {
				let category = list[i];

				option += "<option value='" + category.category_idx + "'>"
						+ category.category_name + "</option>";
			}

			$("#categorySelect").html(option);
		}

		// 카테고리 리스트 조회
		function getCategoryList() {
			$.ajax({
				type : "GET",
				url : "/admin/category/category.jsp",
				success : function(result, status, xhr) {
					//응답받은 리스트 매개변수로 전달
					createCategoryOption(result);
				}
			});
		}

		// onload
		$(function() {
			$('#detail').summernote({
				tabsize : 2,
				height : 400
			});

			$("#regist").on("click", function(){
				regist();
			});
			$("#list").on("click", function(){
				location.href="/admin/product/list.jsp";
			});
			
			$("#file").on("change", function(){
				fileList = [];
				
				for(let i = 0; i < this.files.length; i++){
					//readonly 배열에서 객체(파일)가 하나씩 저장
					fileList.push(this.files[i]);
				}
				previewImg();
			});

			// 카테고리 리스트 호출
			getCategoryList();
			// root 선언
			root = ReactDOM.createRoot(document.getElementById("preview"));
			console.log("현재 root", root);
		});
	</script>
</body>
</html>
