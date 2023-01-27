package surfing.gui.product;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import surfing.db.domain.product.Goods;
import surfing.db.domain.product.SubItem;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class ProductRegist extends Page implements ActionListener {
	private ProductPanel productPanel;

	// 상단 필드
	private JLabel la_title;

	// 중앙 필드
	private JPanel p_center, p_registInfo;
	private String[] categoryName = { "서핑보드", "식품", "서핑소모품" };
	private JLabel la_topItem, la_subItem;
	private JLabel la_registName, la_registBrand, la_registPrice, la_registCount;
	private JComboBox<String> box_topItem, box_subItem;
	private JTextField t_registName, t_registBrand, t_registPrice, t_registCount;

	// 하단 필드
	private JButton bt_back, bt_registAll, bt_regist;

	// combobox 아이템과 연계된 idx값 보유
	private List<Integer> subItemIdx = new ArrayList<Integer>();
	private int currentIndex;

	// 파일 일괄등록을 위한 멤버 보유
	private JFileChooser chooser;

	public ProductRegist(SurfingApp app, ProductPanel productPanel) {
		super(app);
		this.productPanel = productPanel;
		container.setLayout(new BorderLayout());

		// 상단필드
		la_title = new JLabel("상품등록", Label.WIDTH / 2);
		la_title.setFont(new Font("배달의민족 연성", Font.BOLD, 25));

		p_north.add(la_title);

		// 중앙필드
		p_center = new JPanel();
		p_registInfo = new JPanel();
		la_topItem = new JLabel("상위구분 : ");
		la_subItem = new JLabel("하위구분 : ");
		la_registName = new JLabel("상품명 : ");
		la_registBrand = new JLabel("상품브랜드 : ");
		la_registPrice = new JLabel("상품가격 : ");
		la_registCount = new JLabel("상품수량 : ");
		box_topItem = new JComboBox<String>(); // 카테고리 입력 시 오타방지를 위해 콤보박스 제공
		box_subItem = new JComboBox<String>();
		t_registName = new JTextField(15);
		t_registBrand = new JTextField(15);
		t_registPrice = new JTextField(15);
		t_registCount = new JTextField(15);

		p_center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		p_registInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
		p_registInfo.setPreferredSize(new Dimension(300, 500));
		p_registInfo.setBorder(Page.PANEL_LINEBORDER);
		box_topItem.setPreferredSize(new Dimension(200, 25));
		box_subItem.setPreferredSize(new Dimension(200, 25));

		app.addBoxItem(categoryName, box_topItem);

		p_registInfo.add(la_topItem);
		p_registInfo.add(box_topItem);
		p_registInfo.add(la_subItem);
		p_registInfo.add(box_subItem);
		p_registInfo.add(la_registName);
		p_registInfo.add(t_registName);
		p_registInfo.add(la_registBrand);
		p_registInfo.add(t_registBrand);
		p_registInfo.add(la_registPrice);
		p_registInfo.add(t_registPrice);
		p_registInfo.add(la_registCount);
		p_registInfo.add(t_registCount);
		p_center.add(p_registInfo);

		// 하단 필드
		bt_back = new JButton("이전");
		bt_registAll = new JButton("일괄등록");
		bt_regist = new JButton("건별등록");

		p_south.add(bt_back);
		p_south.add(bt_registAll);
		p_south.add(bt_regist);

		container.add(p_north, BorderLayout.NORTH);
		container.add(p_center);
		container.add(p_south, BorderLayout.SOUTH);

		add(container);

		setVisible(false);

		bt_regist.addActionListener(this);
		bt_registAll.addActionListener(this);
		bt_back.addActionListener(this);

		box_topItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String item = (String) box_topItem.getSelectedItem();
				createSubItem(item);
			}
		});

		box_subItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentIndex = box_subItem.getSelectedIndex() - 1;
			}
		});
	}

	// 일괄등록을 위한 List 생성 메서드
	public void registAll() {
		List<Goods> list = new ArrayList<Goods>();
		chooser = new JFileChooser("/Users/inung/Desktop/project/back/SurfingProject/src/res");

		if (chooser.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			//엑셀파일 전환
			parseExcel(file, list);
		}
		System.out.println("엑셀에서 전환된 DTO 수는? " + list.size());
		//전환된 엑셀파일의 subitem_name과 대응하는 idx 추가
		int result = addSubItemIdx(list);
		if(result > 0) {
			JOptionPane.showMessageDialog(app, list.size() + "개의 상품이 모두 등록되었습니다.");
		} else {
			JOptionPane.showMessageDialog(app, "상품 등록이 실패되었습니다.");
		}
	}
	
	// list 내 각 DTO에 subitem_idx값 연결 후 DB등록 메서드
	public int addSubItemIdx(List<Goods> list) {
		int result = 0;
		
		for(int i = 0; i < list.size(); i++) {
			Goods goods = list.get(i);
			int idx = productPanel.subItemDAO.selectSubItemIdx(goods.getSubItem().getSubitem_name());
			goods.getSubItem().setSubitem_idx(idx);
			
			//result값을 계속 재정의하면서 오류 체크
			result = productPanel.goodsDAO.insert(goods);
			System.out.println("현재입력된 상품은? " + goods.getGoods_name());
		}
		return result;
	}

	// 엑셀파일을 변환하기 위한 메서드
	public void parseExcel(File file, List<Goods> list) {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			XSSFWorkbook book = new XSSFWorkbook(fis);

			XSSFSheet sheet = book.getSheetAt(0); // 테이블이 1개이므로 0호출
			int lastRow = sheet.getLastRowNum();

			for (int i = 1; i <= lastRow; i++) {
				XSSFRow row = sheet.getRow(i);
				// 각 row와 1:1 대응하는 비어있는 DTO를 생성
				Goods goods = new Goods();
				SubItem subItem = new SubItem();
				goods.setSubItem(subItem);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);

					switch (j) {
					case 0:
						goods.getSubItem().setSubitem_name(cell.getStringCellValue());
						break;
					case 1:
						goods.setGoods_name(cell.getStringCellValue());
						break;
					case 2:
						goods.setGoods_brand(cell.getStringCellValue());
						break;
					case 3:
						goods.setGoods_price((int)cell.getNumericCellValue());
						break;
					case 4:
						goods.setGoods_stock((int)cell.getNumericCellValue());
						break;
					}
				}
				list.add(goods);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 건별 등록 메서드
	public void registGoods() {
		if (checkInput() == false) {
			return;
		}
		Goods goods = new Goods();
		SubItem subItem = new SubItem();
		goods.setSubItem(subItem);

		goods.getSubItem().setSubitem_idx(subItemIdx.get(currentIndex));
		goods.setGoods_name(t_registName.getText());
		goods.setGoods_brand(t_registBrand.getText());
		goods.setGoods_price(Integer.parseInt(t_registPrice.getText()));
		goods.setGoods_stock(Integer.parseInt(t_registCount.getText()));

		int result = productPanel.goodsDAO.insert(goods);
		if (result > 0) {
			JOptionPane.showMessageDialog(app, "등록 성공");
			productPanel.getGoodsList();
		} else {
			JOptionPane.showMessageDialog(app, "등록 실패");
		}
	}

	// 등록 전 입력사항 체크 메서드
	public boolean checkInput() {
		boolean flag = false;
		if (box_topItem.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(app, "상위 구분을 선택하세요");
		} else if (box_subItem.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(app, "하위 구분을 선택하세요");
		} else if (t_registName.getText().length() == 0) {
			JOptionPane.showMessageDialog(app, "상품명을 입력하세요");
		} else if (t_registBrand.getText().length() == 0) {
			JOptionPane.showMessageDialog(app, "브랜드를 입력하세요");
		} else if (t_registPrice.getText().length() == 0) {
			JOptionPane.showMessageDialog(app, "가격을 입력하세요");
		} else if (t_registCount.getText().length() == 0) {
			JOptionPane.showMessageDialog(app, "수량을 입력하세요");
		} else {
			flag = true;
		}
		return flag;
	}

	// 서브아이템 생성 메서드
	public void createSubItem(String item) {
		box_subItem.removeAllItems(); // 생성 전 초기화
		subItemIdx.removeAll(subItemIdx);

		int topitem_idx = productPanel.topItemDAO.getIdx(item);
		List<SubItem> list = productPanel.subItemDAO.selectByTopItem(topitem_idx);
		box_subItem.addItem("카테고리 선택");

		for (SubItem subitem : list) {
			box_subItem.addItem(subitem.getSubitem_name());
			subItemIdx.add(subitem.getSubitem_idx());
		}
	}

	// 패널 전환
	public void showPanel() {
		setVisible(false);
		productPanel.container.setVisible(true);

		productPanel.getGoodsList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bt_registAll)) {
			registAll();
		} else if (e.getSource().equals(bt_regist)) {
			if (JOptionPane.showConfirmDialog(app, "입력하신 상품을 등록하시겠습니까?") == JOptionPane.OK_OPTION) {
				registGoods();
			}
		} else if (e.getSource().equals(bt_back)) {
			showPanel();
		}

	}

}
