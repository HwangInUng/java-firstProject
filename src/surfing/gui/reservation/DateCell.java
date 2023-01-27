package surfing.gui.reservation;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class DateCell extends Cell {
	private int index;

	public DateCell(ReservationPanel reservationPanel, String title, Color color, Color color2, int index) {
		super(reservationPanel, title, color, color2);
		this.index = index;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (DateCell.this.color.equals(new Color(102, 178, 255))) {
					reservationPanel.getRsvList(DateCell.this.title);
					reservationPanel.showBoard();
				} else {
					JOptionPane.showMessageDialog(reservationPanel.app, "예약현황이 없습니다.");
				}
			}
		});
	}
}
