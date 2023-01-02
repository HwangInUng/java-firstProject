package surfing.db.reopsitory.member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.member.RidingRecord;
import surfing.db.domain.member.SurfMember;
import surfing.db.util.DBManager;

public class RidingRecordDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// 선택한 레코드 1건을 조회
	public RidingRecord select(int record_idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RidingRecord record = null;
		
		String sql = "select * from ridingrecord where record_idx=?";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, record_idx);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				record = new RidingRecord();
				SurfMember surfMember = new SurfMember();
				record.setSurfMember(surfMember);
				
				record.setRecord_idx(rs.getInt("record_idx"));
				record.getSurfMember().setSurfmember_idx(rs.getInt("surfmember_idx"));
				record.setRiding_spot(rs.getString("riding_spot"));
				record.setBoard_spec(rs.getString("board_spec"));
				record.setPado(rs.getString("pado"));
				record.setImage_name(rs.getString("image_name"));
				record.setRegdate(rs.getString("regdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return record;
	}
	
	//검색한 회원의 기록을 전체 조회
	public int getIdx(int index, String input) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = null;
		int idx = 0; //검색결과로 얻어진 member_idx를 담을 변수
		
		switch(index) {
		case 1: 
			sql = "select * from surfmember where id like ?";
			break;
		case 2:
			sql = "select * from surfmember where name like ?";
			break;
		}
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				idx = rs.getInt("surfmember_idx"); //조회된 idx를 저장
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return idx;
	}
	
	// getIdx로 얻어온 surfmember_idx값으로 해당 기록 전체를 조회
	public List selectAll(int surfmember_idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RidingRecord> list = new ArrayList<RidingRecord>();
		
		String sql = "select * from ridingrecord where surfmember_idx=? order by record_idx desc";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, surfmember_idx);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RidingRecord record = new RidingRecord();
				SurfMember surfMember = new SurfMember();
				record.setSurfMember(surfMember);
				
				record.setRecord_idx(rs.getInt("record_idx"));
				record.getSurfMember().setSurfmember_idx(rs.getInt("surfmember_idx"));
				record.setRiding_spot(rs.getString("riding_spot"));
				record.setBoard_spec(rs.getString("board_spec"));
				record.setPado(rs.getString("pado"));
				record.setImage_name(rs.getString("image_name"));
				record.setRegdate(rs.getString("regdate"));
				
				list.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	// 라이딩 기록테이블 전체 조회
	public List selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RidingRecord> list = new ArrayList<RidingRecord>();
		
		String sql = "select * from ridingrecord order by record_idx desc";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RidingRecord record = new RidingRecord();
				SurfMember surfMember = new SurfMember();
				record.setSurfMember(surfMember);
				
				record.setRecord_idx(rs.getInt("record_idx"));
				record.getSurfMember().setSurfmember_idx(rs.getInt("surfmember_idx"));
				record.setRiding_spot(rs.getString("riding_spot"));
				record.setBoard_spec(rs.getString("board_spec"));
				record.setPado(rs.getString("pado"));
				record.setImage_name(rs.getString("image_name"));
				record.setRegdate(rs.getString("regdate"));
				
				list.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
