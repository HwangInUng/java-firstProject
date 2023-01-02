package surfing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import surfing.db.domain.member.SurfMember;
import surfing.db.model.MemberModel;
import surfing.db.reopsitory.member.ProfileDAO;
import surfing.db.reopsitory.member.SurfMemberDAO;

public class MemberPanel extends Page implements ActionListener {
	// db연동
	SurfMemberDAO surfMemberDAO;

	// 상단 검색필드
	String[] categoryName = { "회원번호", "ID", "이름" };
	JComboBox<String> box_memberCategory;
	JTextField t_memberSearch;
	JButton bt_memberSearch;
	JButton bt_allList;

	// 중앙 테이블 필드
	JTable memberTable;
	MemberModel memberModel;
	JScrollPane memberScroll;

	// 하단 버튼 필드
	JButton bt_profile, bt_memberDel;

	// 상세보기 페이지 구현을 위하여 멤버로 보유
	MProfilePanel mProfilePanel;

	// 현재 선택한 DTO를 멤버로 보유
	SurfMember currentMember;

	public MemberPanel(SurfingApp app) {
		super(app);
		surfMemberDAO = new SurfMemberDAO();

		container.setLayout(new BorderLayout());

		// 상단 검색
		box_memberCategory = new JComboBox<String>();
		t_memberSearch = new JTextField();
		bt_memberSearch = new JButton("검색");
		bt_allList = new JButton("전체목록");

		t_memberSearch.setPreferredSize(new Dimension(300, 25));

		app.addBoxItem(categoryName, box_memberCategory);
		p_north.add(box_memberCategory);
		p_north.add(t_memberSearch);
		p_north.add(bt_memberSearch);
		p_north.add(bt_allList);

		container.add(p_north, BorderLayout.NORTH);

		// 중앙 테이블
		memberTable = new JTable(memberModel = new MemberModel());
		memberScroll = new JScrollPane(memberTable);
		mProfilePanel = new MProfilePanel(app, this);

		memberScroll.setPreferredSize(new Dimension(795, 650));

		container.add(memberScroll);

		// 하단 버튼
		bt_profile = new JButton("프로필");
		bt_memberDel = new JButton("삭제");

		p_south.add(bt_profile);
		p_south.add(bt_memberDel);

		// 테이블 리스트 호출
		getMemberList();

		container.add(p_south, BorderLayout.SOUTH);
		add(container);
		add(mProfilePanel); // default visible을 false로 유지

		bt_memberSearch.addActionListener(this);
		bt_allList.addActionListener(this);
		bt_profile.addActionListener(this);
		bt_memberDel.addActionListener(this);

		memberTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				getCurrentMember();
			}
		});
	}
	
	// 검색결과에 대한 멤버를 조회하는 메서드
	public void searchMember() {
		int index = box_memberCategory.getSelectedIndex(); // "카테고리 선택"을 제외한 index 계산
		String input = t_memberSearch.getText();
		if(index == 0) {
			//카테고리 미선택 시 알림
			JOptionPane.showMessageDialog(app, "카테고리를 선택하세요");
		} else {
			memberModel.memberList = surfMemberDAO.select(index, input);
			memberTable.updateUI(); // 테이블 갱신
		}
		
	}

	// 테이블에서 선택한 row의 surfmember를 멤버변수에 저장하는 메서드
	public void getCurrentMember() {
		int row = memberTable.getSelectedRow();
		String current_idx = (String) memberTable.getValueAt(row, 0);
		currentMember = surfMemberDAO.selectCurrentRecord(Integer.parseInt(current_idx));

		System.out.println("선택한 회원의 번호는 : " + current_idx);
	}

	// 선택된 레코드 삭제
	public void deleteRecord() {
		System.out.println("현재 선택한 회원번호는 ? " + currentMember.getSurfmember_idx());

		int result = surfMemberDAO.delete(currentMember.getSurfmember_idx());
		if (result > 0) {
			JOptionPane.showMessageDialog(app, "회원번호 : " + currentMember.getSurfmember_idx() + " 삭제완료");
			getMemberList(); // 테이블 갱신
		} else {
			JOptionPane.showMessageDialog(app, "삭제 실패");
		}
	}

	// Profile 패널 전환 메서드
	public void showProfile() {
		container.setVisible(false);
		mProfilePanel.setVisible(true);
	}

	// model의 list를 대입하는 메서드
	public void getMemberList() {
		// 패키지의 경로로 인해 접근제한자를 public으로 선언
		memberModel.memberList = surfMemberDAO.selectAll();
		memberTable.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 검색하면 해당 조건의 레코드 반환
		if (e.getSource().equals(bt_memberSearch)) {
			searchMember();
		}
		//전체목록 보기
		else if (e.getSource().equals(bt_allList)) {
			getMemberList();
			t_memberSearch.setText(""); //텍스트 필드 초기화
		}
		// 선택된 레코드 정보를 1건 출력
		else if (e.getSource().equals(bt_profile)) {
			mProfilePanel.getProfileInfo(currentMember.getSurfmember_idx());
			showProfile();
		}
		// 선택된 레코드 1건 삭제
		else if (e.getSource().equals(bt_memberDel)) {
			if (JOptionPane.showConfirmDialog(app, "정말 삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
				deleteRecord();
			}
		}
	}

}
