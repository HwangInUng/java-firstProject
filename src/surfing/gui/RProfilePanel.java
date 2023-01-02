package surfing.gui;

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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import surfing.db.domain.member.RidingRecord;

public class RProfilePanel extends Page {
	RidingPanel ridingPanel;

	// 상단
	JLabel la_user;

	// 중앙
	JPanel p_center, p_info;
	JLabel la_image;
	Image image;
	String[] r_labelName = { "장소 : ", "일자 : ", "보드스펙 : ", "파고 : " };
	JLabel[] r_labelList = new JLabel[4];

	// 하단
	JButton bt_back;

	public RProfilePanel(SurfingApp app, RidingPanel ridingPanel) {
		super(app);
		this.ridingPanel = ridingPanel;

		container.setLayout(new BorderLayout());

		la_user = new JLabel("00번 게시물", Label.WIDTH / 2);
		p_center = new JPanel();
		p_info = new JPanel();
		la_image = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		bt_back = new JButton("이전");
		// 필드 생성
		createInfoField();
		
		//스타일
		la_user.setFont(new Font("배달의민족 연성", Font.BOLD, 25));
		p_center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		p_info.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		p_center.setPreferredSize(new Dimension(790, 500));
		la_image.setPreferredSize(new Dimension(400, 200));
		la_image.setBorder(Page.PANEL_LINEBORDER);
		p_info.setPreferredSize(new Dimension(400, 350));
		p_info.setBorder(Page.PANEL_LINEBORDER);

		p_north.add(la_user);
		p_center.add(la_image);
		p_center.add(p_info);
		p_south.add(bt_back);

		container.add(p_north, BorderLayout.NORTH);
		container.add(p_center);
		container.add(p_south, BorderLayout.SOUTH);

		add(container);
		setVisible(false);

		bt_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRidingPanel();
			}
		});
	}

	// 패널 전환 메서드
	public void showRidingPanel() {
		ridingPanel.container.setVisible(true);
		setVisible(false);
	}

	// 정보 필드 생성 메서드
	public void createInfoField() {
		for (int i = 0; i < r_labelList.length; i++) {
			r_labelList[i] = new JLabel(r_labelName[i]);
			r_labelList[i].setFont(new Font("배달의민족 연성", Font.BOLD, 20));
			r_labelList[i].setPreferredSize(new Dimension(300, 25));
			p_info.add(r_labelList[i]);
		}
	}

	// 선택한 1건에 대한 정보 출력 메서드
	public void getProfileInfo(RidingRecord ridingRecord) {
		// DTO세팅을 위한 변수 정의
		String fileName = ridingPanel.dir + ridingRecord.getImage_name();
		String[] labelData = { ridingRecord.getRiding_spot(), ridingRecord.getRegdate(), ridingRecord.getBoard_spec(),
				ridingRecord.getPado() };
		
		// 이미지 생성 및 교체
		createImage(fileName);
		
		// 각 필드 내용 수정
		for(int i = 0; i < r_labelList.length; i++) {
			r_labelList[i].setText(r_labelName[i] + labelData[i]);
		}
		// 프로필 타이틀 수정
		la_user.setText(ridingRecord.getRecord_idx() + "번 게시물 상세보기");
		
		container.updateUI(); //시각정보 갱신
	}

	// 선택한 레코드 파일을 생성하는 메서드
	public void createImage(String fileName) {
		File file = new File(fileName);

		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		la_image.repaint();
	}

}
