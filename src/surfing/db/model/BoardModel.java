package surfing.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import surfing.db.domain.reservation.Reservation;

public class BoardModel extends AbstractTableModel {
	private final String[] columnName = { "예약번호", "예약자명", "예약인원", "예약시간", "강습형태" };
	public List<Reservation> list = new ArrayList<Reservation>();

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		String value = null;
		Reservation rsv = list.get(row);

		switch (col) {
		case 0:
			value = Integer.toString(rsv.getRsv_idx());
			break;
		case 1:
			value = rsv.getRsv_name();
			break;
		case 2:
			value = Integer.toString(rsv.getRsv_number());
			break;
		case 3:
			value = Integer.toString(rsv.getRsv_time());
			break;
		case 4:
			value = rsv.getStep().getStep_name();
			break;
		}
		return value;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnName[col];
	}
}
