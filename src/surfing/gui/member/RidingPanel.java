package surfing.gui.member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import surfing.db.domain.member.RidingRecord;
import surfing.db.reopsitory.member.RidingRecordDAO;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class RidingPanel extends Page {
	private RidingRecordDAO ridingRecordDAO;

	// 상단 필드
	private JComboBox<String> box_ridingCategory;
	private JTextField t_ridingSearch;
	private JButton bt_ridingSearch;
	private JButton bt_allList;

	// 중앙 필드
	private JPanel p_record;
	private JScrollPane ridingScroll;
	private List<RidingRecord> ridingList = new ArrayList<RidingRecord>();
	public String dir = "/Users/inung/Desktop/project/back/SurfingProject/src/res/riding/";

	// 상세보기를 위해 멤버로 보유
	private RProfilePanel rProfilePanel;

	public RidingPanel(SurfingApp app) {
		super(app);
		ridingRecordDAO = new RidingRecordDAO();

		container.setLayout(new BorderLayout());

		// 상단 검색 필드
		box_ridingCategory = new JComboBox<String>();
		t_ridingSearch = new JTextField();
		bt_ridingSearch = new JButton("검색");
		bt_allList = new JButton("전체목록");

		t_ridingSearch.setPreferredSize(new Dimension(300, 25));

		box_ridingCategory.addItem("카테고리 선택");
		box_ridingCategory.addItem("ID");
		box_ridingCategory.addItem("회원명");

		p_north.add(box_ridingCategory);
		p_north.add(t_ridingSearch);
		p_north.add(bt_ridingSearch);
		p_north.add(bt_allList);

		// 중간 기록 필드
		p_record = new JPanel();
		ridingScroll = new JScrollPane(p_record);
		rProfilePanel = new RProfilePanel(app, this);

		getAllRecord();

		p_record.setLayout(new GridLayout(0, 3));
		ridingScroll.setPreferredSize(new Dimension(790, 600));

		container.add(p_north, BorderLayout.NORTH);
		container.add(ridingScroll);

		add(container);
		add(rProfilePanel); // default visible을 false로 설정하여 상세보기 클릭 시 true로 전환

		bt_ridingSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchRecord();
			}
		});

		bt_allList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 리스트에 담겨진 DTO객체를 삭제 후 비우기
				resetRecord();

				// 리스트에 전체 목록을 추가 후 출력
				getAllRecord();
				t_ridingSearch.setText("");
				p_record.updateUI();
			}
		});
	}

	// 검색한 대상에 대한 record만 출력하는 메서드
	public void searchRecord() {
		int index = box_ridingCategory.getSelectedIndex();
		String input = t_ridingSearch.getText();

		if (index == 0) {
			JOptionPane.showMessageDialog(app, "카테고리를 선택하세요");
		} else {
			// 출력 정보를 최신화하기 위한 메서드 호출
			getMemberRecord(index, input);
		}
	}

	// 생성 시 정의된 데이터 삭제 후 검색된 데이터로 재정의
	public void resetRecord() {
		ridingList.removeAll(ridingList);
		p_record.removeAll();
	}

	// 검색한 회원 정보의 기록을 출력하는 메서드
	public void getMemberRecord(int index, String input) {
		resetRecord();

		// 매개값을 select 조건으로하여 해당 회원의 기록을 반환
		int idx = ridingRecordDAO.getIdx(index, input);
		ridingList = ridingRecordDAO.selectAll(idx);

		for (int i = 0; i < ridingList.size(); i++) {
			createRecord(i);
		}
		p_record.updateUI(); // 시각 정보 갱신
	}

	// 선택한 건수에 대한 상세보기 메서드
	public void showDetail(int record_idx) {
		RidingRecord record = ridingRecordDAO.select(record_idx);
		rProfilePanel.getProfileInfo(record);

		showRidingProfile();
	}

	// 패널 전환 메서드
	public void showRidingProfile() {
		rProfilePanel.setVisible(true);
		container.setVisible(false);
	}

	// 생성과 동시에 각 테이블 정보의 패널 출력
	public void getAllRecord() {
		ridingList = ridingRecordDAO.selectAll();
		
		for (int i = 0; i < ridingList.size(); i++) {
			createRecord(i);
		}
	}

	// Record 생성
	public void createRecord(int index) {
		// List는 멤버변수이기 때문에 select문의 성질에 따라 객체 보유현황 변경
		RidingRecord ridingRecord = ridingList.get(index);
		// 이미지 생성
		Image image = createImage(ridingRecord.getImage_name());

		Record record = new Record(app, this, image, ridingRecord);
		p_record.add(record);
	}

	// 반복문에 사용될 이미지 생성 메서드
	public Image createImage(String imageName) {
		// 메서드 호출 시 DTO가 보유한 이미지명 매개로 전달받음
		File file = new File(dir + imageName);

		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
