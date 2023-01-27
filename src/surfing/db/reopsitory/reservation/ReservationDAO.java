package surfing.db.reopsitory.reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.reservation.Reservation;
import surfing.db.domain.reservation.Step;
import surfing.db.util.DBManager;

public class ReservationDAO {
	private DBManager dbManager = DBManager.getInstance();
	
	// 해당 일의 데이터 조회
	public List selectDay(String currentDate) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reservation> list = new ArrayList<Reservation>();
		
		String sql = "select * from step s, reservation r where s.step_idx = r.step_idx";
		sql += " and to_char(regdate, 'yyyymmdd')=? order by rsv_time desc";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, currentDate);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Reservation rsv = new Reservation();
				Step step = new Step();
				rsv.setStep(step);
				
				rsv.getStep().setStep_name(rs.getString("step_name"));
				rsv.setRsv_idx(rs.getInt("rsv_idx"));
				rsv.setRsv_name(rs.getString("rsv_name"));
				rsv.setRsv_number(rs.getInt("rsv_number"));
				rsv.setRsv_time(rs.getInt("rsv_time"));
				
				list.add(rsv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 전체 테이블 조회
	public List selectAll(String date) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reservation> list = new ArrayList<Reservation>();

		// 해당 연월과 동일한 레코드만 수집
		StringBuffer sb = new StringBuffer();
		sb.append("select rsv_idx, step_idx, rsv_name, rsv_number, rsv_time, to_char(regdate, 'dd') AS day");
		sb.append(" from reservation where to_char(regdate, 'yyyymm')=?");
		sb.append(" order by regdate asc");

		try {
			pstmt = dbManager.getConnection().prepareStatement(sb.toString());
			pstmt.setString(1, date);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Reservation rsv = new Reservation();
				Step step = new Step();
				rsv.setStep(step);

				rsv.setRsv_idx(rs.getInt("rsv_idx"));
				rsv.getStep().setStep_idx(rs.getInt("step_idx"));
				rsv.setRsv_name(rs.getString("rsv_name"));
				rsv.setRsv_number(rs.getInt("rsv_number"));
				rsv.setRsv_time(rs.getInt("rsv_time"));
				rsv.setRegdate(rs.getString("day"));

				list.add(rsv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
