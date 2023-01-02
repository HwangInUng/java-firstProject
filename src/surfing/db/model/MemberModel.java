package surfing.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import surfing.db.domain.member.SurfMember;

public class MemberModel extends AbstractTableModel {
	String[] columnName = { "회원번호", "회원ID", "회원명", "성별", "전화번호", "SNS아이디", "경력", "등록일" };
	// 생성 후 MemberPanel에서 메서드를 통해 정의
	public List<SurfMember> memberList = new ArrayList<SurfMember>();

	@Override
	public int getRowCount() {
		return memberList.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// 리스트 내의 DTO를 하나의 변수로 저장
		SurfMember member = memberList.get(row);

		String value = null;

		switch (col) {
		case 0:
			value = Integer.toString(member.getSurfmember_idx());
			break;
		case 1:
			value = member.getId();
			break;
		case 2:
			value = member.getName();
			break;
		case 3:
			value = member.getGender();
			break;
		case 4:
			value = member.getPhoneno();
			break;
		case 5:
			value = member.getSnsId();
			break;
		case 6:
			value = Integer.toString(member.getCareer());
			break;
		case 7:
			value = member.getRegdate();
			break;
		}
		return value;
	}

	@Override
	public String getColumnName(int col) {
		return columnName[col];
	}
}
