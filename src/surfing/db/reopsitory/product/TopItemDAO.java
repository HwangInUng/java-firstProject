package surfing.db.reopsitory.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import surfing.db.util.DBManager;

public class TopItemDAO {
	DBManager dbManager = DBManager.getInstance();
	
	// name을 매개로 받아 idx 값 반환
	public int getIdx(String name) {
		int idx = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select topitem_idx from topitem where topitem_name=?";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				idx = rs.getInt("topitem_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return idx;
	}
}
