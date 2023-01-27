package surfing.db.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import surfing.db.domain.product.Goods;

public class GoodsModel extends AbstractTableModel {
	private String[] columnName = { "상품번호", "상위구분", "하위구분", "상품명", "브랜드", "가격", "재고" };
	public List<Goods> goodsList = new ArrayList<Goods>();

	@Override
	public int getRowCount() {
		return goodsList.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		String value = null;
		Goods goods = goodsList.get(row);

		switch (col) {
		case 0:
			value = Integer.toString(goods.getGoods_idx());
			break;
		case 1:
			value = goods.getSubItem().getTopItem().getTopitem_name();
			break;
		case 2:
			value = goods.getSubItem().getSubitem_name();
			break;
		case 3:
			value = goods.getGoods_name();
			break;
		case 4:
			value = goods.getGoods_brand();
			break;
		case 5:
			NumberFormat formatter = NumberFormat.getNumberInstance();
			value = formatter.format(goods.getGoods_price()).toString();
			break;
		case 6:
			value = Integer.toString(goods.getGoods_stock());
			break;
		}
		return value;
	}

	@Override
	public String getColumnName(int col) {
		return columnName[col];
	}
}
