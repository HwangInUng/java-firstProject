package surfing.gui.product;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GoodsCellRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected && !hasFocus) {
			if (value.equals("0")) {
				cell.setBackground(Color.orange);
			} else {
				cell.setBackground(Color.white);
			}
		}
		return cell;
	}
}
