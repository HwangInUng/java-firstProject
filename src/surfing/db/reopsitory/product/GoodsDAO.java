package surfing.db.reopsitory.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import surfing.db.domain.product.Goods;
import surfing.db.domain.product.SubItem;
import surfing.db.domain.product.TopItem;
import surfing.db.util.DBManager;

public class GoodsDAO {
	DBManager dbManager = DBManager.getInstance();

	// 레코드 1건 추가
	public int insert(Goods goods) {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "insert into goods(goods_idx, subitem_idx, goods_name, goods_brand, goods_price, goods_stock)";
		sql += " values(seq_goods.nextval, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, goods.getSubItem().getSubitem_idx());
			pstmt.setString(2, goods.getGoods_name());
			pstmt.setString(3, goods.getGoods_brand());
			pstmt.setInt(4, goods.getGoods_price());
			pstmt.setInt(5, goods.getGoods_stock());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 레코드 1건 삭제
	public int delete(int goods_idx) {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "delete from goods where goods_idx=?";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, goods_idx);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 레코드 1건 수정
	public int update(Goods goods) {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "update goods set goods_name=?, goods_brand=?, goods_price=?, goods_stock=?";
		sql += " where goods_idx=?";

		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setString(1, goods.getGoods_name());
			pstmt.setString(2, goods.getGoods_brand());
			pstmt.setInt(3, goods.getGoods_price());
			pstmt.setInt(4, goods.getGoods_stock());
			pstmt.setInt(5, goods.getGoods_idx());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 검색한 테이블 데이터 조회
	public List selectSearch(int index, String input) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Goods> list = new ArrayList<Goods>();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.topitem_idx as topitem_idx, topitem_name, s.subitem_idx as subitem_idx, subitem_name,");
		sb.append(" goods_idx, goods_name, goods_brand, goods_price, goods_stock");
		sb.append(" from topitem t, subitem s, goods g");
		sb.append(" where  t.topitem_idx = s.topitem_idx and s.subitem_idx = g.subitem_idx");

		switch (index) {
		case 1:
			sb.append(" and topitem_name like ?");
			break;
		case 2:
			sb.append(" and subitem_name like ?");
			break;
		case 3:
			sb.append(" and goods_name like ?");
			break;
		}

		try {
			pstmt = dbManager.getConnection().prepareStatement(sb.toString());
			pstmt.setString(1, "%" + input + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods goods = new Goods();
				// 각 카테고리 멤버로 보유
				SubItem subItem = new SubItem();
				TopItem topItem = new TopItem();
				subItem.setTopItem(topItem);
				goods.setSubItem(subItem);

				// topitem 세팅
				goods.getSubItem().getTopItem().setTopitem_idx(rs.getInt("topitem_idx"));
				goods.getSubItem().getTopItem().setTopitem_name(rs.getString("topitem_name"));
				// subitem 세팅
				goods.getSubItem().setSubitem_idx(rs.getInt("subitem_idx"));
				goods.getSubItem().setSubitem_name(rs.getString("subitem_name"));
				// goods 세팅
				goods.setGoods_idx(rs.getInt("goods_idx"));
				goods.setGoods_name(rs.getString("goods_name"));
				goods.setGoods_brand(rs.getString("goods_brand"));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_stock(rs.getInt("goods_stock"));

				list.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 전체 테이블데이터 조회
	public List selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Goods> list = new ArrayList<Goods>();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.topitem_idx as topitem_idx, topitem_name, s.subitem_idx as subitem_idx, subitem_name,");
		sb.append(" goods_idx, goods_name, goods_brand, goods_price, goods_stock");
		sb.append(" from topitem t, subitem s, goods g");
		sb.append(" where  t.topitem_idx = s.topitem_idx and s.subitem_idx = g.subitem_idx");
		sb.append(" order by t.topitem_idx, s.subitem_idx, goods_idx asc");

		try {
			pstmt = dbManager.getConnection().prepareStatement(sb.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods goods = new Goods();
				// 각 카테고리 멤버로 보유
				SubItem subItem = new SubItem();
				TopItem topItem = new TopItem();
				subItem.setTopItem(topItem);
				goods.setSubItem(subItem);

				// topitem 세팅
				goods.getSubItem().getTopItem().setTopitem_idx(rs.getInt("topitem_idx"));
				goods.getSubItem().getTopItem().setTopitem_name(rs.getString("topitem_name"));
				// subitem 세팅
				goods.getSubItem().setSubitem_idx(rs.getInt("subitem_idx"));
				goods.getSubItem().setSubitem_name(rs.getString("subitem_name"));
				// goods 세팅
				goods.setGoods_idx(rs.getInt("goods_idx"));
				goods.setGoods_name(rs.getString("goods_name"));
				goods.setGoods_brand(rs.getString("goods_brand"));
				goods.setGoods_price(rs.getInt("goods_price"));
				goods.setGoods_stock(rs.getInt("goods_stock"));

				list.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
