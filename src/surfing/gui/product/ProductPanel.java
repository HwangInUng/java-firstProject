package surfing.gui.product;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import surfing.db.domain.product.Goods;
import surfing.db.model.GoodsModel;
import surfing.db.reopsitory.product.GoodsDAO;
import surfing.db.reopsitory.product.SubItemDAO;
import surfing.db.reopsitory.product.TopItemDAO;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class ProductPanel extends Page implements ActionListener {
	private ProductRegist productRegist;
	public TopItemDAO topItemDAO;
	public SubItemDAO subItemDAO;
	public GoodsDAO goodsDAO;

	// 상단 검색필드
	private JComboBox<String> box_productCategory;
	private String[] categoryName = { "상위구분", "하위구분", "상품" };
	private JTextField t_productSearch;
	private JButton bt_productSearch;
	private JButton bt_allList;

	// 중앙 테이블 필드
	private JTable productTable;
	private GoodsModel goodsModel;
	private JScrollPane productScroll;

	// 하단 수정 및 등록 필드
	private JTextField t_productName, t_productBrand, t_productPrice, t_stock;
	private JButton bt_productRegist, bt_productEdit, bt_productDel;

	// 중복되는 값을 위한 변수 선언
	public static final int TEXTFIELDSIZE = 8;

	// 현재 선택한 상품 멤버로 보유
	private Goods currentGoods;

	public ProductPanel(SurfingApp app) {
		super(app);
		productRegist = new ProductRegist(app, this);
		topItemDAO = new TopItemDAO();
		subItemDAO = new SubItemDAO();
		goodsDAO = new GoodsDAO();

		container.setLayout(new BorderLayout());

		// 상단 필드
		box_productCategory = new JComboBox<String>();
		t_productSearch = new JTextField();
		bt_productSearch = new JButton("검색");
		bt_allList = new JButton("전체목록");

		t_productSearch.setPreferredSize(new Dimension(300, 25));

		app.addBoxItem(categoryName, box_productCategory);

		p_north.add(box_productCategory);
		p_north.add(t_productSearch);
		p_north.add(bt_productSearch);
		p_north.add(bt_allList);

		// 중앙 필드
		productTable = new JTable(goodsModel = new GoodsModel());
		// 재고가 0일 경우 셀의 색상 변경을위한 renderer 생성
		GoodsCellRenderer render = new GoodsCellRenderer();
		try {
			productTable.setDefaultRenderer(Class.forName("java.lang.Object"), render);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		productScroll = new JScrollPane(productTable);

		productScroll.setPreferredSize(new Dimension(795, 650));

		// 하단 필드
		t_productName = new JTextField(TEXTFIELDSIZE);
		t_productBrand = new JTextField(TEXTFIELDSIZE);
		t_productPrice = new JTextField(TEXTFIELDSIZE);
		t_stock = new JTextField(TEXTFIELDSIZE);
		bt_productRegist = new JButton("상품등록");
		bt_productEdit = new JButton("수정");
		bt_productDel = new JButton("삭제");

		p_south.add(t_productName);
		p_south.add(t_productBrand);
		p_south.add(t_productPrice);
		p_south.add(t_stock);
		p_south.add(bt_productRegist);
		p_south.add(bt_productEdit);
		p_south.add(bt_productDel);

		container.add(p_north, BorderLayout.NORTH);
		container.add(productScroll);
		container.add(p_south, BorderLayout.SOUTH);

		// 리스트 호출
		getGoodsList();

		add(container);
		add(productRegist);

		bt_productRegist.addActionListener(this);
		bt_productEdit.addActionListener(this);
		bt_productDel.addActionListener(this);
		bt_productSearch.addActionListener(this);
		bt_allList.addActionListener(this);

		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = productTable.getSelectedRow();
				getGoodsInfo(row);
			}
		});
	}

	// 선택된 레코드 1건 삭제
	public void delGoods() {
		if (currentGoods != null) {
			int result = goodsDAO.delete(currentGoods.getGoods_idx());
			if (result > 0) {
				JOptionPane.showMessageDialog(app, currentGoods.getGoods_name() + " 삭제완료");
				getGoodsList(); // 테이블 갱신
				resetField(); // 입력필드 초기화
			} else {
				JOptionPane.showMessageDialog(app, "삭제실패");
			}
		} else {
			JOptionPane.showMessageDialog(app, "선택된 상품이 없습니다.");
		}
	}

	// 선택된 레코드 1건 수정
	public void editGoods() {
		if (currentGoods != null) {
			currentGoods.setGoods_name(t_productName.getText());
			currentGoods.setGoods_brand(t_productBrand.getText());
			// 콤마(,) 삭제 수행
			int price = Integer.parseInt(t_productPrice.getText().replace(",", ""));
			currentGoods.setGoods_price(price);
			currentGoods.setGoods_stock(Integer.parseInt(t_stock.getText()));

			int result = goodsDAO.update(currentGoods);
			if (result > 0) {
				JOptionPane.showMessageDialog(app, currentGoods.getGoods_name() + " 수정완료");
				getGoodsList(); // 테이블 갱신
				resetField(); // 입력필드 초기화
			} else {
				JOptionPane.showMessageDialog(app, "수정실패");
			}
		} else {
			JOptionPane.showMessageDialog(app, "선택된 상품이 없습니다.");
		}
	}

	// 검색한 정보에 따라 테이블을 반환
	public void searchGoods() {
		int index = box_productCategory.getSelectedIndex();
		String input = t_productSearch.getText();

		if (index == 0) {
			JOptionPane.showMessageDialog(app, "카테고리를 선택하세요.");
		} else {
			goodsModel.goodsList = goodsDAO.selectSearch(index, input);
			productTable.updateUI();
		}
	}

	// 선택한 상품의 정보를 하단 필드에 출력
	public void getGoodsInfo(int row) {
		System.out.println("현재 row : " + row);
		currentGoods = goodsModel.goodsList.get(row);

		t_productName.setText(currentGoods.getGoods_name());
		t_productBrand.setText(currentGoods.getGoods_brand());
		NumberFormat formatter = NumberFormat.getNumberInstance();
		t_productPrice.setText(formatter.format(currentGoods.getGoods_price()).toString());
		t_stock.setText(Integer.toString(currentGoods.getGoods_stock()));
	}

	// 생성과 동시에 리스트 호출
	public void getGoodsList() {
		goodsModel.goodsList = goodsDAO.selectAll();
		productTable.updateUI();
	}

	// 입력필드 초기화
	public void resetField() {
		t_productName.setText("");
		t_productBrand.setText("");
		t_productPrice.setText("");
		t_stock.setText("");
	}

	// 패널 전환
	public void showRegist() {
		container.setVisible(false);
		productRegist.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bt_productRegist)) {
			showRegist();
		} else if (e.getSource().equals(bt_productEdit)) {
			if (JOptionPane.showConfirmDialog(app, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {
				editGoods();
			}
		} else if (e.getSource().equals(bt_productDel)) {
			if (JOptionPane.showConfirmDialog(app, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
				delGoods();
			}
		} else if (e.getSource().equals(bt_productSearch)) {
			searchGoods();
		} else if (e.getSource().equals(bt_allList)) {
			getGoodsList();
			t_productSearch.setText("");
		}

	}

}
