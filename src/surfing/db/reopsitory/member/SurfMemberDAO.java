package surfing.db.reopsitory.member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.member.SurfMember;
import surfing.db.util.DBManager;

public class SurfMemberDAO {
	DBManager dbManager = DBManager.getInstance();

	// 현재 레코드 정보를 수정(트랜잭션 수행 적용메서드)
	public int updateCurrentRecord(SurfMember member) throws Exception {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "update surfmember set name=?, snsid=?, career=?";
		sql += " where surfmember_idx=?";

		pstmt = dbManager.getConnection().prepareStatement(sql);
		pstmt.setString(1, member.getName());
		pstmt.setString(2, member.getSnsId());
		pstmt.setInt(3, member.getCareer());
		pstmt.setInt(4, member.getSurfmember_idx());

		result = pstmt.executeUpdate();

		dbManager.release(pstmt);
		return result;
	}

	// 테이블에서 선택된 레코드를 반환
	public SurfMember selectCurrentRecord(int current_idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SurfMember member = null;

		String sql = "select * from surfmember where surfmember_idx=?";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, current_idx);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new SurfMember();
				member.setSurfmember_idx(rs.getInt("surfmember_idx"));
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setPhoneno(rs.getString("phoneno"));
				member.setSnsId(rs.getString("snsid"));
				member.setCareer(rs.getInt("career"));
				member.setRegdate(rs.getString("regdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return member;
	}

	// 선택된 멤버의 레코드를 삭제
	public int delete(int surfmember_idx) {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "delete from surfmember where surfmember_idx=?";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, surfmember_idx);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// Member 검색 시 해당 테이블 전체를 조회
	public List select(int index, String input) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SurfMember> list = new ArrayList<SurfMember>();
		String sql = null;
		
		switch(index) {
		case 1: 
			sql = "select * from surfmember where surfmember_idx like ?";
			break;
		case 2:
			sql = "select * from surfmember where id like ?";
			break;
		case 3:
			sql = "select * from surfmember where name like ?";
			break;
		}

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				SurfMember member = new SurfMember();
				member.setSurfmember_idx(rs.getInt("surfmember_idx"));
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setPhoneno(rs.getString("phoneno"));
				member.setSnsId(rs.getString("snsid"));
				member.setCareer(rs.getInt("career"));
				member.setRegdate(rs.getString("regdate"));

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// MemberPanel 로드 시 테이블 데이터 전체를 조회
	public List selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SurfMember> list = new ArrayList<SurfMember>();

		String sql = "select * from surfmember order by surfmember_idx desc"; // 내림차순(가장 최근에 등록한) 정렬

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SurfMember member = new SurfMember();
				member.setSurfmember_idx(rs.getInt("surfmember_idx"));
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setPhoneno(rs.getString("phoneno"));
				member.setSnsId(rs.getString("snsid"));
				member.setCareer(rs.getInt("career"));
				member.setRegdate(rs.getString("regdate"));

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
