<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.util.Msg"%>
<%@page import="com.jspshop.exception.PimgException"%>
<%@page import="com.jspshop.repository.PimgDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jspshop.domain.Pimg"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="com.jspshop.exception.PsizeException"%>
<%@page import="com.jspshop.exception.ColorException"%>
<%@page import="com.jspshop.exception.ProductException"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.domain.Color"%>
<%@page import="com.jspshop.domain.Psize"%>
<%@page import="com.jspshop.domain.Category"%>
<%@page import="com.jspshop.domain.Product"%>
<%@page import="com.jspshop.util.FileManager"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="com.jspshop.repository.PsizeDAO"%>
<%@page import="com.jspshop.repository.ColorDAO"%>
<%@page import="com.jspshop.repository.ProductDAO"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();


	ProductDAO productDAO = new ProductDAO();
	PsizeDAO psizeDAO = new PsizeDAO();
	ColorDAO colorDAO = new ColorDAO();
	PimgDAO pimgDAO = new PimgDAO();

	int maxSize = 5 * (1024 * 1024);
	String path = "/data/";%>

<%
	DiskFileItemFactory factory = new DiskFileItemFactory();
	//실제 경로로 대체
	path = application.getRealPath(path);
	
	factory.setDefaultCharset("utf-8");
	factory.setRepository(new File(path));
	factory.setSizeThreshold(maxSize);
	
	ServletFileUpload upload = new ServletFileUpload(factory);
	List<FileItem> itemList = upload.parseRequest(request);
	
	// 등록할 DTO 선언
	Product product = new Product();
	List<Psize> psizeList = new ArrayList();
	List<Color> colorList = new ArrayList();
	List<Pimg> pimgList = new ArrayList();
	
	product.setPsizeList(psizeList);
	product.setColorList(colorList);
	product.setPimgList(pimgList);
	
	for (FileItem item : itemList) {
		if (item.isFormField()) {
			switch (item.getFieldName()) {
			case "category_idx":
				Category category = new Category();
				category.setCategory_idx(Integer.parseInt(item.getString()));
				product.setCategory(category);
				break;
			case "product_name":
				product.setProduct_name(item.getString());
				break;
			case "brand":
				product.setBrand(item.getString());
				break;
			case "price":
				product.setPrice(Integer.parseInt(item.getString()));
				break;
			case "discount":
				product.setDiscount(Integer.parseInt(item.getString()));
				break;
			case "size[]":
				String[] sizeData = item.getString().split(",");
				for(int i = 0; i < sizeData.length; i++){
					Psize psize = new Psize();
					
					psize.setProduct(product);
					psize.setPsize_name(sizeData[i]);
					psizeList.add(psize);					
				}
				break;
			case "color[]":
				String[] colorData = item.getString().split(",");
				for(int i = 0; i < colorData.length; i++){
					Color color = new Color();
					
					color.setProduct(product);
					color.setColor_name(colorData[i]);
					colorList.add(color);						
				}
				break;
			case "detail":
				product.setDetail(item.getString());
				break;
			}
		} else {
			long time = System.currentTimeMillis();
			String ext = FileManager.getExt(item.getName());
	
			item.write(new File(path + time + "." + ext));
			Pimg pimg = new Pimg();
			
			pimg.setProduct(product);
			pimg.setFilename(time + "." + ext);
			pimgList.add(pimg);
		}
	}
	
		//한개의 등록과정을 담당할 세션 보유
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		
		//세션주입
		productDAO.setSession(sqlSession);
		colorDAO.setSession(sqlSession);
		psizeDAO.setSession(sqlSession);
		pimgDAO.setSession(sqlSession);
		
		//응답으로 보내줄 메세지
		Msg msg = new Msg();
		
		
		try{
			//insert 후 등록된 idx값 반환
			productDAO.insert(product);
			System.out.println("insert된 product_idx :" + product.getProduct_idx());
			
			//color insert
			colorDAO.insert(colorList);
			//size insert
			psizeDAO.insert(psizeList);
			//img insert
			pimgDAO.insert(pimgList);
			
			sqlSession.commit();
			msg.setCode(1);
			msg.setMsg("상품 1건 등록완료");
			System.out.println("상품 1건 등록완료");
		}	catch(ProductException e){
			sqlSession.rollback();
			msg.setCode(0);
			msg.setMsg(e.getMessage());
		}	catch(ColorException e){
			sqlSession.rollback();
			msg.setCode(0);
			msg.setMsg(e.getMessage());
		}	catch(PsizeException e){
			sqlSession.rollback();
			msg.setCode(0);
			msg.setMsg(e.getMessage());
		}	catch(PimgException e){
			sqlSession.rollback();
			msg.setCode(0);
			msg.setMsg(e.getMessage());
		} finally{
			mybatisConfig.release(sqlSession);
		}
		
		//응답정보 보내기
		Gson gson = new Gson();
		String result = gson.toJson(msg);
%>
