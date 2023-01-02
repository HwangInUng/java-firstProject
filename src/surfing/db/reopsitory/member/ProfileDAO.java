package surfing.db.reopsitory.member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import surfing.db.domain.member.Profile;
import surfing.db.domain.member.SurfMember;
import surfing.db.util.DBManager;

public class ProfileDAO {
	DBManager dbManager = DBManager.getInstance();

	// 현재 레코드 정보를 수정(트랜잭션 수행 적용메서드)
	public int updateCurrentRecord(Profile member) throws Exception {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "update profile set spot=?, board_name=?, board_price=?, name=?, snsid=?, career=?";
		sql += " where surfmember_idx=?";

		pstmt = dbManager.getConnection().prepareStatement(sql);
		pstmt.setString(1, member.getSpot());
		pstmt.setString(2, member.getBoard_name());
		pstmt.setInt(3, member.getBoard_price());
		pstmt.setString(4, member.getSurfMember().getName());
		pstmt.setString(5, member.getSurfMember().getSnsId());
		pstmt.setInt(6, member.getSurfMember().getCareer());
		pstmt.setInt(7, member.getSurfMember().getSurfmember_idx());

		result = pstmt.executeUpdate();
		dbManager.release(pstmt);
		
		return result;
	}

	// 선택한 회원의 프로필 레코드 조회
	public Profile selectRecord(int currentmember_idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Profile profile = null;

		String sql = "select * from profile where surfmember_idx=?";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, currentmember_idx);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				profile = new Profile();
				SurfMember surfMember = new SurfMember();
				profile.setSurfMember(surfMember);

				profile.getSurfMember().setSurfmember_idx(rs.getInt("surfmember_idx"));
				profile.setProfile_idx(rs.getInt("profile_idx"));
				profile.setSpot(rs.getString("spot"));
				profile.setBoard_name(rs.getString("board_name"));
				profile.setBoard_price(rs.getInt("board_price"));
				profile.getSurfMember().setName(rs.getString("name"));
				profile.getSurfMember().setSnsId(rs.getString("snsid"));
				profile.getSurfMember().setCareer(rs.getInt("career"));
				profile.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return profile;
	}
}
