package surfing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//Page 상속을 통해 멤버로 MainApp 보유
public class MenuPanel extends JPanel {
	SurfingApp app;
	// 로그인 정보 출력필드
	JPanel loginInfo;
	JLabel la_id;
	JLabel la_accessTime;
	JButton bt_logout;

	// 메뉴필드
	JPanel menuInfo;
	MenuLabel[] labelList = new MenuLabel[5];

	public MenuPanel(SurfingApp app) {
		this.app = app;
		// 로그인 정보 필드
		loginInfo = new JPanel();
		la_id = new JLabel(); // 로그인 완료 시 동적으로 생성
		la_accessTime = new JLabel(); // 위와 동일
		bt_logout = new JButton("로그아웃");

		loginInfo.setPreferredSize(new Dimension(190, 100));
		loginInfo.setBackground(Page.BG_COLOR);
		loginInfo.setBorder(Page.PANEL_LINEBORDER);

		loginInfo.add(la_id);
		loginInfo.add(la_accessTime);
		loginInfo.add(bt_logout);

		add(loginInfo);

		// 메뉴 필드정의
		menuInfo = new JPanel();
		createMenuLabel();

		// 컴포넌트 간 간격 조정을 위한 FlowLayout 생성자 활용
		menuInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 60));
		menuInfo.setPreferredSize(new Dimension(190, 650));
		menuInfo.setBorder(Page.PANEL_LINEBORDER);

		add(menuInfo);

		setPreferredSize(new Dimension(200, 900));
		setVisible(false);

		bt_logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 로그아웃 시 접속중인 관리자 null값으로 초기화
				LoginPanel loginPanel = (LoginPanel) app.panelList[SurfingApp.LOGINPAGE];
				loginPanel.accessAdmin = null;
				System.out.println("현재 접속중인 ID : " + loginPanel.accessAdmin);
				app.showHide(SurfingApp.LOGINPAGE);
			}
		});
	}

	// 클릭 시 라벨의 배경색 변경
	public void changeColor(int index) {
		for (int i = 0; i < labelList.length; i++) {
			if (i == index) {
				labelList[i].setBackground(Page.CLICK_COLOR);
			} else {
				labelList[i].setBackground(Page.NONCLICK_COLOR);
			}
		}
	}

	// menuLabel 리스트 생성
	public void createMenuLabel() {
		String[] menuName = { "메인", "회원관리", "라이딩 기록", "상품관리", "예약관리" };

		for (int i = 0; i < menuName.length; i++) {
			labelList[i] = new MenuLabel(menuName[i], Label.WIDTH / 2, this, i);
			menuInfo.add(labelList[i]);
		}
	}
}
