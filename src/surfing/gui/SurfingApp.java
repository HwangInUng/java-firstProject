package surfing.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import surfing.db.repository.ShopAdminDAO;
import surfing.db.util.DBManager;
import surfing.gui.login.LoginPanel;
import surfing.gui.main.MainPanel;
import surfing.gui.member.MemberPanel;
import surfing.gui.member.RidingPanel;
import surfing.gui.product.ProductPanel;
import surfing.gui.reservation.ReservationPanel;

public class SurfingApp extends JFrame {
	public DBManager dbManager = DBManager.getInstance();
	public ShopAdminDAO shopAdminDAO;

	// 프레임 필드
	public JPanel container;  //JFrame의 센터 영역을 담당할 패널
	public MenuPanel menuPanel; //JFrame의 서측 영역을 담당할 패널
	public Page[] panelList = new Page[6];

	// 기능별 페이지 상수
	public static final int LOGINPAGE = 0;
	public static final int MAINPAGE = 1;
	public static final int MEMBERPAGE = 2;
	public static final int RIDINGPAGE = 3;
	public static final int PRODUCTPAGE = 4;
	public static final int RESERVATIONPAGE = 5;

	public SurfingApp() {
		setTitle("서핑샵 관리프로그램");
		shopAdminDAO = new ShopAdminDAO(this);
		
		container = new JPanel();
		menuPanel = new MenuPanel(this);
		
		//기능별 페이지를 배열내 정의
		panelList[0] = new LoginPanel(this);
		panelList[1] = new MainPanel(this);
		panelList[2] = new MemberPanel(this);
		panelList[3] = new RidingPanel(this);
		panelList[4] = new ProductPanel(this);
		panelList[5] = new ReservationPanel(this);

		addPanelList();
		
		add(menuPanel, BorderLayout.WEST);
		add(container);

		setSize(1000, 800);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// DB드라이버 및 접속 종료
				dbManager.release(dbManager.getConnection());
				// 시스템 종료
				System.exit(0);
			}
		});
		showHide(LOGINPAGE);
	}
	
	public void addPanelList() {
		for (int i = 0; i < panelList.length; i++) {
			container.add(panelList[i]);
		}
	}
	
	//페이지의 시각적 효과만을 담당하는 메서드
	public void showHide(int index) {
		for (int i = 0; i < panelList.length; i++) {
			if (i == index) {
				if (index == 0) { //0일 경우 LoginPanel출력 시 서측 menuPanel숨김
					panelList[i].setVisible(true);
					panelList[i].updateUI();  //각 Panel의 시각정보를 최신화
					menuPanel.setVisible(false);
				} else { //LoginPanel을 제외한 모든 Panel은 서측 영역과 동반
					panelList[i].setVisible(true);
					panelList[i].updateUI();
					menuPanel.setVisible(true);
					menuPanel.updateUI();
				}
			} else {
				panelList[i].setVisible(false);
			}
		}
	}
	
	//각 페이지 콤보박스의 아이템 추가를 위한 공통메서드
	public void addBoxItem(String[] itemName, JComboBox<String> box) {
		box.addItem("카테고리 선택");
		for (int i = 0; i < itemName.length; i++) {
			box.addItem(itemName[i]);
		}
	}

	public static void main(String[] args) {
		new SurfingApp();
	}
}
