/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.70
 * Generated at: 2023-01-24 13:12:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.admin.product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.google.gson.Gson;
import com.jspshop.util.Msg;
import com.jspshop.exception.PimgException;
import com.jspshop.repository.PimgDAO;
import java.util.ArrayList;
import com.jspshop.domain.Pimg;
import org.apache.ibatis.reflection.SystemMetaObject;
import com.jspshop.exception.PsizeException;
import com.jspshop.exception.ColorException;
import com.jspshop.exception.ProductException;
import org.apache.ibatis.session.SqlSession;
import com.jspshop.mybatis.MybatisConfig;
import com.jspshop.domain.Color;
import com.jspshop.domain.Psize;
import com.jspshop.domain.Category;
import com.jspshop.domain.Product;
import com.jspshop.util.FileManager;
import org.apache.commons.fileupload.FileItem;
import java.util.List;
import java.io.File;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.jspshop.repository.PsizeDAO;
import com.jspshop.repository.ColorDAO;
import com.jspshop.repository.ProductDAO;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

public final class regist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


	MybatisConfig mybatisConfig = MybatisConfig.getInstance();


	ProductDAO productDAO = new ProductDAO();
	PsizeDAO psizeDAO = new PsizeDAO();
	ColorDAO colorDAO = new ColorDAO();
	PimgDAO pimgDAO = new PimgDAO();

	int maxSize = 5 * (1024 * 1024);
	String path = "/data/";
  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("org.apache.ibatis.reflection.SystemMetaObject");
    _jspx_imports_classes.add("com.jspshop.mybatis.MybatisConfig");
    _jspx_imports_classes.add("com.jspshop.repository.ProductDAO");
    _jspx_imports_classes.add("com.jspshop.repository.PsizeDAO");
    _jspx_imports_classes.add("org.apache.commons.fileupload.servlet.ServletFileUpload");
    _jspx_imports_classes.add("com.jspshop.domain.Category");
    _jspx_imports_classes.add("com.jspshop.util.FileManager");
    _jspx_imports_classes.add("java.util.ArrayList");
    _jspx_imports_classes.add("com.jspshop.domain.Psize");
    _jspx_imports_classes.add("com.jspshop.repository.ColorDAO");
    _jspx_imports_classes.add("com.jspshop.exception.ColorException");
    _jspx_imports_classes.add("com.jspshop.exception.PsizeException");
    _jspx_imports_classes.add("com.google.gson.Gson");
    _jspx_imports_classes.add("com.jspshop.exception.ProductException");
    _jspx_imports_classes.add("org.apache.ibatis.session.SqlSession");
    _jspx_imports_classes.add("com.jspshop.repository.PimgDAO");
    _jspx_imports_classes.add("com.jspshop.domain.Product");
    _jspx_imports_classes.add("org.apache.commons.fileupload.FileItem");
    _jspx_imports_classes.add("com.jspshop.domain.Color");
    _jspx_imports_classes.add("com.jspshop.exception.PimgException");
    _jspx_imports_classes.add("java.io.File");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.jspshop.domain.Pimg");
    _jspx_imports_classes.add("com.jspshop.util.Msg");
    _jspx_imports_classes.add("org.apache.commons.fileupload.disk.DiskFileItemFactory");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP?????? ?????? GET, POST ?????? HEAD ??????????????? ???????????????. Jasper??? OPTIONS ????????? ?????? ???????????????.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("application/json;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');
      out.write('\n');

	DiskFileItemFactory factory = new DiskFileItemFactory();
	//?????? ????????? ??????
	path = application.getRealPath(path);
	
	factory.setDefaultCharset("utf-8");
	factory.setRepository(new File(path));
	factory.setSizeThreshold(maxSize);
	
	ServletFileUpload upload = new ServletFileUpload(factory);
	List<FileItem> itemList = upload.parseRequest(request);
	
	// ????????? DTO ??????
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
	
		//????????? ??????????????? ????????? ?????? ??????
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		
		//????????????
		productDAO.setSession(sqlSession);
		colorDAO.setSession(sqlSession);
		psizeDAO.setSession(sqlSession);
		pimgDAO.setSession(sqlSession);
		
		//???????????? ????????? ?????????
		Msg msg = new Msg();
		
		
		try{
			//insert ??? ????????? idx??? ??????
			productDAO.insert(product);
			System.out.println("insert??? product_idx :" + product.getProduct_idx());
			
			//color insert
			colorDAO.insert(colorList);
			//size insert
			psizeDAO.insert(psizeList);
			//img insert
			pimgDAO.insert(pimgList);
			
			sqlSession.commit();
			msg.setCode(1);
			msg.setMsg("?????? 1??? ????????????");
			System.out.println("?????? 1??? ????????????");
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
		
		//???????????? ?????????
		Gson gson = new Gson();
		String result = gson.toJson(msg);
		
		out.print(msg);

      out.write('\n');
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
