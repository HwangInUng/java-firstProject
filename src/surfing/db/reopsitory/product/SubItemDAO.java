package surfing.db.reopsitory.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.product.SubItem;
import surfing.db.domain.product.TopItem;
import surfing.db.util.DBManager;

public class SubItemDAO {
	private DBManager dbManager = DBManager.getInstance();
	
	// item명과 1:1대응하는 idx값 반환
	public int selectSubItemIdx(String name) {
		int idx = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select subitem_idx from subitem where subitem_name=?";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				idx = rs.getInt("subitem_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return idx;
	}
	
	//테이블 조회
	public List selectByTopItem(int topitem_idx) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SubItem> list = new ArrayList<SubItem>();
		
		String sql = "select * from subitem where topitem_idx=?";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, topitem_idx);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SubItem item = new SubItem();
				TopItem topItem = new TopItem();
				item.setTopItem(topItem);
				
				item.getTopItem().setTopitem_idx(rs.getInt("topitem_idx"));
				item.setSubitem_idx(rs.getInt("subitem_idx"));
				item.setSubitem_name(rs.getString("subitem_name"));
				
				list.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
