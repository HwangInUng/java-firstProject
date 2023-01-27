package surfing.gui.login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import surfing.db.domain.ShopAdmin;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class LoginPanel extends Page implements ActionListener {
	// 로그인 화면 구성
	private JPanel p_loginInfo;
	private JLabel la_logo, la_id, la_pass;
	private JTextField t_id;
	private JPasswordField t_pass;
	private JButton bt_login, bt_edit, bt_regist;

	// 로고 이미지
	private Image logo;
	private String dir = "/Users/inung/Desktop/project/back/SurfingProject/src/res/logo/";
	private String logoName = "logo.png";

	// 로그인정보 수정 시 패널변경을 위해 멤버로 보유
	private EditPanel editPanel;

	// 로그인한 관리자의 데이터를 담을 DTO멤버 보유
	public ShopAdmin accessAdmin;

	public LoginPanel(SurfingApp app) {
		super(app);
		editPanel = new EditPanel(app, this);

		la_logo = new JLabel("Logo", Label.WIDTH / 2) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				
				// 로고 이미지생성
				logo = createLogo();
				
				g2.drawImage(logo, 0, 0, la_logo.getWidth(), la_logo.getHeight(), app);
			}
		};
		p_loginInfo = new JPanel();
		la_id = new JLabel("ID : ");
		t_id = new JTextField(15);
		la_pass = new JLabel("PASS : ");
		t_pass = new JPasswordField(15);
		bt_login = new JButton("로그인");
		bt_edit = new JButton("정보수정");
		bt_regist = new JButton("관리자등록");
		

		Dimension d = new Dimension(50, 25);
		
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 100));
		container.setBackground(NONCLICK_COLOR);
		la_logo.setPreferredSize(new Dimension(500, 100));
		la_logo.setBorder(Page.PANEL_LINEBORDER);
		p_loginInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
		p_loginInfo.setPreferredSize(new Dimension(300, 200));
		p_loginInfo.setBorder(Page.PANEL_LINEBORDER);
		la_id.setPreferredSize(d);
		la_pass.setPreferredSize(d);

		p_loginInfo.add(la_id);
		p_loginInfo.add(t_id);
		p_loginInfo.add(la_pass);
		p_loginInfo.add(t_pass);
		p_loginInfo.add(bt_login);
		p_loginInfo.add(bt_edit);
		p_loginInfo.add(bt_regist);

		container.add(la_logo);
		container.add(p_loginInfo);

		add(container);
		add(editPanel);

		bt_login.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_regist.addActionListener(this);
	}

	// login을 위한 id, pass 검증
	public void loginCheck() {
		ShopAdmin shopAdmin = new ShopAdmin(); // 매개값으로 전달할 Empty DTO 생성
		shopAdmin.setId(t_id.getText());
		String pass = new String(t_pass.getPassword());
		shopAdmin.setPass(pass);

		// select문의 결과로 전달받은 DTO 멤버에 저장(차후 사용)
		accessAdmin = app.shopAdminDAO.select(shopAdmin);
		System.out.println("현재 접속자의 아이디 : " + accessAdmin.getId());
		if (accessAdmin != null) {
			JOptionPane.showMessageDialog(this, accessAdmin.getId() + "님 환영합니다.");

			// 로그인 성공 시 menuPanel의 로그인정보 동적으로 생성
			app.menuPanel.la_id.setText("접속한 ID : " + accessAdmin.getId());
			app.menuPanel.la_accessTime.setText("접속한시간 : " + app.dbManager.getAccessTime());
			// 화면 전환
			app.showHide(SurfingApp.MAINPAGE);
			reset(); // 입력필드 초기화
		} else {
			JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다.");
		}
	}

	// 관리자 등록
	public void regist() {
		ShopAdmin shopAdmin = new ShopAdmin(); // 입력한 정보를 담을 empty DTO 생성

		shopAdmin.setId(t_id.getText());
		String pass = new String(t_pass.getPassword());
		shopAdmin.setPass(pass);

		int result = app.shopAdminDAO.insert(shopAdmin); // 쿼리문의 매개값으로 DTO 전달
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "관리자가 등록되었습니다.");
			System.out.println(shopAdmin.getId() + " 등록");
		} else {
			JOptionPane.showMessageDialog(this, "관리자가 등록되었습니다.");
		}
		reset(); // 입력필드 초기화
	}

	// 로그인 정보 입력 필드의 공백을 방지
	public boolean checkInput(JTextField id_input, JPasswordField pass_input) { // 매개값으로 재활용
		boolean result = false;

		if (id_input.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
		} else if (pass_input.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.");
		} else {
			result = true;
		}
		return result;
	}

	// 입력 후 필드 초기화를 위한 메서드
	public void reset() {
		t_id.setText("");
		t_pass.setText("");
	}

	// 로고 이미지 생성
	public Image createLogo() {
		File file = new File(dir + logoName);
		
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_login) {
			if (checkInput(t_id, t_pass)) {
				loginCheck();
			}
		} else if (e.getSource() == bt_edit) {
			container.setVisible(false);
			editPanel.setVisible(true);
		} else if (e.getSource() == bt_regist) {
			if (checkInput(t_id, t_pass)) {
				regist();
			}
		}
	}
}
