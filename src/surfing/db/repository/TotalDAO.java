package surfing.db.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.Total;
import surfing.db.util.DBManager;

public class TotalDAO {
	private DBManager dbManager = DBManager.getInstance();
	
	//매출형태별 총 누적합을 반환
	public List selectSaleNum() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<>(); 
		
		String sql = "select sum(board) as board, sum(food) as food, sum(etc) as etc from total";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(rs.getInt("board"));
				list.add(rs.getInt("food"));
				list.add(rs.getInt("etc"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	//강습형태별 총 누적합을 반환
	public List selectTrainingNum() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<>(); 
		
		String sql = "select sum(nomal) as nomal, sum(couple) as couple, sum(single) as single from total";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(rs.getInt("nomal"));
				list.add(rs.getInt("couple"));
				list.add(rs.getInt("single"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 연간 테이블 전체 조회
	public List selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Total> list = new ArrayList<>();

		String sql = "select * from total order by total_idx asc";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				Total total = new Total();
				
				total.setTotal_idx(rs.getInt("total_idx"));
				total.setName(rs.getString("name"));
				total.setNomal(rs.getInt("nomal"));
				total.setCouple(rs.getInt("couple"));
				total.setSingle(rs.getInt("single"));
				total.setBoard(rs.getInt("board"));
				total.setFood(rs.getInt("food"));
				total.setEtc(rs.getInt("etc"));
				
				list.add(total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
