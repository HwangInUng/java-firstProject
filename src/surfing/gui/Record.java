package surfing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import surfing.db.domain.member.RidingRecord;

//라이딩 기록 1건에 대한 정보를 담을 패널
public class Record extends JPanel {
	SurfingApp app;
	RidingPanel ridingPanel;

	JLabel la_ridingImage;
	Image image;
	JPanel p_ridingInfo;

	// RidingInfo 필드
	String[] r_labelName = { "장소 : ", "일자 : ", "보드스펙 : ", "파고 : " };
	JLabel[] r_labelList = new JLabel[4];

	// 각 컴포넌트의 크기 지정 상수
	public static final int WIDTH = 200;
	public static final int P_HEIGHT = 310;
	public static final int IMAGE_HEIGHT = 130;
	public static final int INFO_HEIGHT = 170;
	public static final int VGAP = 0;

	// record_idx 보유
	int record_idx;

	public Record(SurfingApp app, RidingPanel ridingPanel, Image image, RidingRecord ridingRecord) {
		this.app = app;
		this.image = image;
		this.record_idx = ridingRecord.getRecord_idx();
		this.ridingPanel = ridingPanel;
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, VGAP));
		setPreferredSize(new Dimension(WIDTH, P_HEIGHT));

		la_ridingImage = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		p_ridingInfo = new JPanel();

		la_ridingImage.setPreferredSize(new Dimension(WIDTH, IMAGE_HEIGHT));
		la_ridingImage.setBorder(Page.PANEL_LINEBORDER);
		p_ridingInfo.setPreferredSize(new Dimension(WIDTH, INFO_HEIGHT));
		p_ridingInfo.setBorder(Page.PANEL_LINEBORDER);
		p_ridingInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

		// RidingInfo 필드 생성 및 부착
		createRidingInfoField(ridingRecord);

		add(la_ridingImage, BorderLayout.NORTH);
		add(p_ridingInfo);
		
		la_ridingImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ridingPanel.showDetail(record_idx);
			}
		});
	}

	// 정보 출력 필드 생성 메서드
	public void createRidingInfoField(RidingRecord ridingRecord) {
		// 생성 시 넘겨받은 DTO의 정보를 배열에 저장 후 반복문을 통해 순차적으로 추출
		String[] labelData = { ridingRecord.getRiding_spot(), ridingRecord.getRegdate(),
				ridingRecord.getBoard_spec(), ridingRecord.getPado() };
		
		for (int i = 0; i < r_labelList.length; i++) {
			r_labelList[i] = new JLabel(r_labelName[i] + labelData[i]);
			r_labelList[i].setPreferredSize(new Dimension(180, 25));
			p_ridingInfo.add(r_labelList[i]);
		}
	}
	
}
