package surfing.gui.member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import surfing.db.domain.member.Profile;
import surfing.db.reopsitory.member.ProfileDAO;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class MProfilePanel extends Page implements ActionListener {
	private ProfileDAO profileDAO;
	private MemberPanel memberPanel;
	private String dir = "/Users/inung/Desktop/project/back/SurfingProject/src/res/profile/";

	// 상단 유저이름 필드
	private JLabel la_user;

	// 프로필 상세정보 필드
	private JPanel p_profile, p_info;
	private JLabel la_image;
	private Image image;
	private String[] m_labelName = { "회원 이름 : ", "SNS 아이디 : ", "서핑 베이스 : ", "보드 브랜드 : ", "보드 가격 : ", "서핑 경력 : " };
	private JLabel[] m_labelList = new JLabel[6];
	private JTextField[] m_textFieldList = new JTextField[6];

	// 하단 버튼필드
	private JButton bt_save, bt_back;

	// 현재 DTO 멤버로 보유
	private Profile currentProfile;

	public MProfilePanel(SurfingApp app, MemberPanel memberPanel) {
		super(app);
		this.memberPanel = memberPanel;
		profileDAO = new ProfileDAO();

		container.setLayout(new BorderLayout());

		la_user = new JLabel("??님의 프로필"); // user자리에 변수 필요
		la_user.setFont(new Font("배달의민족 연성", Font.BOLD, 25));
		
		p_profile = new JPanel();
		la_image = new JLabel("이미지 등록 위치", Label.WIDTH / 2) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		p_info = new JPanel();
		bt_save = new JButton("저장");
		bt_back = new JButton("이전");

		// 스타일 적용
		p_profile.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 70));
		la_image.setPreferredSize(new Dimension(300, 200));
		p_info.setPreferredSize(new Dimension(300, 400));
		p_info.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		la_image.setBorder(Page.PANEL_LINEBORDER);
		p_info.setBorder(Page.PANEL_LINEBORDER);

		// 상단 회원 ID 패널
		p_north.add(la_user);

		// 프로필 상세내용 패널
		createInfoField();

		// 프로필 이미지 패널
		p_profile.add(la_image);
		p_profile.add(p_info);

		// 하단 버튼 패널
		p_south.add(bt_save);
		p_south.add(bt_back);

		container.add(p_north, BorderLayout.NORTH);
		container.add(p_profile);
		container.add(p_south, BorderLayout.SOUTH);

		add(container);

		setVisible(false); // MemberPage의 레코드 클릭 시 해당 내용 상세보기 간 true로 전환

		bt_save.addActionListener(this);
		bt_back.addActionListener(this);
	}

	// memberPanel이 보유한 현재 멤버의 정보를 세팅하는 메서드
	public void setCurrentInfo() {
		for (int i = 0; i < m_textFieldList.length; i++) {
			switch (i) {
			case 0:
				memberPanel.currentMember.setName(m_textFieldList[0].getText());
				currentProfile.getSurfMember().setName(m_textFieldList[0].getText());
				break;
			case 1:
				memberPanel.currentMember.setSnsId(m_textFieldList[1].getText());
				currentProfile.getSurfMember().setSnsId(m_textFieldList[1].getText());
				break;
			case 2:
				currentProfile.setSpot(m_textFieldList[2].getText());
				break;
			case 3:
				currentProfile.setBoard_name(m_textFieldList[3].getText());
				break;
			case 4:
				String price = m_textFieldList[4].getText();
				currentProfile.setBoard_price(Integer.parseInt(price.replace(",", "")));
				break;
			case 5:
				memberPanel.currentMember.setCareer(Integer.parseInt(m_textFieldList[5].getText()));
				currentProfile.getSurfMember().setCareer(Integer.parseInt(m_textFieldList[5].getText()));
				break;
			}
		}
	}

	// 프로필 정보를 수정하는 메서드
	public void updateProfile() {
		// 쿼리문 수행 전 현재 DTO들의 데이터를 세팅
		setCurrentInfo();

		try {
			// 커넥션 트랜잭션 수동 전환
			app.dbManager.getConnection().setAutoCommit(false);

			// surfmember 테이블의 업데이트 수행
			int result = memberPanel.surfMemberDAO.updateCurrentRecord(memberPanel.currentMember);

			// profile 테이블의 업데이트 수행
			int result2 = profileDAO.updateCurrentRecord(currentProfile);
			if (result > 0 && result2 > 0) {
				app.dbManager.getConnection().commit();
				// commit 후 트랜잭션 자동 전환
				app.dbManager.getConnection().setAutoCommit(true);
				JOptionPane.showMessageDialog(app, "수정 완료");
				System.out.println("정보 수정완료");
			}
		} catch (Exception e) {
			try {
				app.dbManager.getConnection().rollback();
				JOptionPane.showMessageDialog(app, "수정 실패");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	// 상세보기 화면전환 전 입력필드 정보 세팅 메서드
	public void getProfileInfo(int currentMemeber_idx) {
		Profile profile = profileDAO.selectRecord(currentMemeber_idx);
		currentProfile = profile; // 쿼리 수행결과의 DTO 대입

		la_user.setText(profile.getSurfMember().getName()); // 프로필 타이틀 데이터 세팅
		createImage(); //이미지 세팅

		for (int index = 0; index < m_textFieldList.length; index++) {
			// index 조건으로 해당하는 TextField에 DTO의 각 컬럼 데이터를 세팅
			switch (index) {
			case 0:
				m_textFieldList[index].setText(profile.getSurfMember().getName());
				break;
			case 1:
				m_textFieldList[index].setText(profile.getSurfMember().getSnsId());
				break;
			case 2:
				m_textFieldList[index].setText(profile.getSpot());
				break;
			case 3:
				m_textFieldList[index].setText(profile.getBoard_name());
				break;
			case 4:
				NumberFormat formatter = NumberFormat.getNumberInstance();
				m_textFieldList[index].setText(formatter.format(profile.getBoard_price()).toString());
				break;
			case 5:
				m_textFieldList[index].setText(Integer.toString(profile.getSurfMember().getCareer()));
				break;
			}
		}
		p_info.updateUI(); // 갱신
	}

	// 선택된 멤버의 프로필 이미지 생성 메서드
	public void createImage() {
		File file = new File(dir + currentProfile.getFilename());
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		la_image.repaint();
	}

	// 멤버 리스트로 패널을 전환하는 메서드
	public void showMemberPanel() {
		MProfilePanel.this.setVisible(false);
		MemberPanel memberPanel = (MemberPanel) app.panelList[SurfingApp.MEMBERPAGE];
		memberPanel.container.setVisible(true);
	}

	// 반복되는 대상인 상세보기 필드를 생성 및 부착하는 메서드
	public void createInfoField() {
		for (int i = 0; i < m_labelList.length; i++) {
			m_labelList[i] = new JLabel(m_labelName[i]);
			m_textFieldList[i] = new JTextField(15);
			p_info.add(m_labelList[i]);
			p_info.add(m_textFieldList[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bt_save)) {
			if (JOptionPane.showConfirmDialog(app, "정말로 수정하시겠습니까?") == JOptionPane.OK_OPTION) {
				updateProfile();
			}
		} else if (e.getSource().equals(bt_back)) {
			memberPanel.getMemberList();
			showMemberPanel();
		}

	}

}
