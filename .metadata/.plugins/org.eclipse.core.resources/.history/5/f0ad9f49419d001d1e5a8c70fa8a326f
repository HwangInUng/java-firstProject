package com.jspshop.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class PageManager {
	private int totalRecord; // 총 레코드 수
	private int pageSize = 10; // 한 페이지의 게시물 사이즈
	private int totalPage; // 총 페이지 수
	private int blockSize = 10; //

	private int currentPage = 1;
	private int firstPage;
	private int lastPage;

	private int curPos; // 페이지 당 시작 index
	private int num;

	public void init(List list, HttpServletRequest request) {
		totalRecord = list.size();
		totalPage = (int) Math.ceil((float) totalRecord / pageSize);

		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		firstPage = currentPage - (currentPage - 1) % blockSize;
		lastPage = firstPage + (blockSize - 1);
		curPos = (currentPage - 1) * pageSize;
		num = totalRecord - curPos;
	}
}
