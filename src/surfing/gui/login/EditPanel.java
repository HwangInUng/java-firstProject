package surfing.gui.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import surfing.db.domain.ShopAdmin;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class EditPanel extends Page implements ActionListener {
	// 정보수정 화면 구성
	private JPanel p_loginInfo;
	private JLabel la_logo, la_currentId, la_editId, la_editPass;
	private JTextField t_currentId, t_editId;
	private JPasswordField t_editPass;
	private JButton bt_login, bt_edit;
	private Image logo;

	// 공통기능의 메서드 호출을 위해 멤버보유
	LoginPanel loginPanel;

	public EditPanel(SurfingApp app, LoginPanel loginPanel) {
		super(app);
		this.loginPanel = loginPanel;

		setLayout(new BorderLayout());

		la_logo = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				
				//login패널의 메서드 재사용
				logo = loginPanel.createLogo();
				g2.drawImage(logo, 0, 0, la_logo.getWidth(), la_logo.getHeight(), app);
			}
		};
		p_loginInfo = new JPanel();
		la_currentId = new JLabel("현재 ID : ");
		t_currentId = new JTextField(15);
		la_editId = new JLabel("변경 ID : ");
		t_editId = new JTextField(15);
		la_editPass = new JLabel("변경 PASS : ");
		t_editPass = new JPasswordField(15);
		bt_login = new JButton("로그인");
		bt_edit = new JButton("정보수정");

		Dimension d = new Dimension(70, 25);

		container.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 100));
		container.setBackground(NONCLICK_COLOR);
		la_logo.setPreferredSize(new Dimension(500, 100));
		la_logo.setBorder(Page.PANEL_LINEBORDER);
		p_loginInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		p_loginInfo.setPreferredSize(new Dimension(300, 200));
		p_loginInfo.setBorder(Page.PANEL_LINEBORDER);
		la_currentId.setPreferredSize(d);
		la_editId.setPreferredSize(d);
		la_editPass.setPreferredSize(d);

		p_loginInfo.add(la_currentId);
		p_loginInfo.add(t_currentId);
		p_loginInfo.add(la_editId);
		p_loginInfo.add(t_editId);
		p_loginInfo.add(la_editPass);
		p_loginInfo.add(t_editPass);
		p_loginInfo.add(bt_login);
		p_loginInfo.add(bt_edit);

		container.add(la_logo);
		container.add(p_loginInfo);

		add(container);

		bt_login.addActionListener(this);
		bt_edit.addActionListener(this);

		setVisible(false);
	}

	// 관리자 로그인 정보 변경 메서드
	public void edit() {
		ShopAdmin shopAdmin = new ShopAdmin();
		shopAdmin.setId(t_editId.getText());
		// char[]형 자료를 String형으로 전환하기 위한 생성자 사용
		String pass = new String(t_editPass.getPassword());
		shopAdmin.setPass(pass);

		int result = app.shopAdminDAO.update(t_currentId.getText(), shopAdmin);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, shopAdmin.getId() + "님의 정보 수정완료");
			reset(); // 입력필드 초기화
		} else {
			JOptionPane.showMessageDialog(this, "정보수정 실패");
		}

	}

	// 입력필드 초기화
	public void reset() {
		t_currentId.setText("");
		t_editId.setText("");
		t_editPass.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_login) {
			setVisible(false);
			LoginPanel loginPanel = (LoginPanel) app.panelList[SurfingApp.LOGINPAGE];
			loginPanel.container.setVisible(true);
		} else if (e.getSource() == bt_edit) {
			//즉시적인 수정을 방지하기 위하여 조건설정
			if (JOptionPane.showConfirmDialog(EditPanel.this, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {
				//현재 아이디가 입력되지 않으면 경고문 출력
				if (t_currentId.getText().length() == 0) {
					JOptionPane.showMessageDialog(EditPanel.this, "변경대상을 입력하세요.");
				} else if (loginPanel.checkInput(t_editId, t_editPass) && t_currentId.getText().length() != 0) {
					edit();
				}
			}
		}
	}
}
