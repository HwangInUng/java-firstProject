package com.jspshop.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jspshop.domain.Product;
import com.jspshop.exception.ProductException;

public class ProductDAO {
	private SqlSession session;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	//예외처리가 아닌 호출자에게 전달
	public void insert(Product product) throws ProductException{
		int result = 0;
		result = session.insert("Product.insert", product);
		if(result < 1) {
			throw new ProductException("상품 등록실패");
		}
	}
	
	public List selectAll() {
		return session.selectList("Product.selectAll");
	}
}
