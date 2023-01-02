package surfing.db.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import surfing.db.domain.ShopAdmin;
import surfing.db.util.DBManager;
import surfing.gui.SurfingApp;
import surfing.util.StringUtil;

public class ShopAdminDAO {
	DBManager dbManager = DBManager.getInstance();
	SurfingApp app;

	public ShopAdminDAO(SurfingApp app) {
		this.app = app;
	}
	//관리자 등록 메서드
	public int insert(ShopAdmin shopAdmin) {
		PreparedStatement pstmt = null;

		String sql = "insert into shopadmin(admin_idx, id, pass)";
		sql += " values(seq_shopadmin.nextval, ?, ?)";

		int result = 0;
		try { // jpa , mybatis 추천 
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, shopAdmin.getId());
			String pass = shopAdmin.getPass();
			pstmt.setString(2, StringUtil.getCovertedPassword(pass));

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(app, "중복된 ID가 있습니다.");
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	//로그인 대상에 대한 정보를 반환하는 메서드
	public ShopAdmin select(ShopAdmin shopAdmin) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ShopAdmin accessAdmin = null; //접속한 유저의 정보를 나타내는 DTO
		
		String sql = "select * from shopadmin where id=? and pass=?";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, shopAdmin.getId());
			String pass = shopAdmin.getPass();  //암호화하여 비교
			pstmt.setString(2, StringUtil.getCovertedPassword(pass));
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				accessAdmin = new ShopAdmin();
				accessAdmin.setAdmin_idx(rs.getInt("admin_idx"));
				accessAdmin.setId(rs.getString("id"));
				accessAdmin.setPass(rs.getString("pass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return accessAdmin;
	}

	//관리자 정보 수정(id, pass 만)
	public int update(String currentId, ShopAdmin shopAdmin) {
		PreparedStatement pstmt = null;
		
		String sql = "update shopadmin set id=?, pass=?";
		sql += " where id=?";
		
		int result = 0;
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			//변경할 정보를 담은 DTO
			pstmt.setString(1, shopAdmin.getId());
			String pass = shopAdmin.getPass();
			pstmt.setString(2, StringUtil.getCovertedPassword(pass));
			
			//변경할 대상이 되는 id
			pstmt.setString(3, currentId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
