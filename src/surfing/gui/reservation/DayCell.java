package surfing.gui.reservation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DayCell extends Cell{

	public DayCell(ReservationPanel reservationPanel, String title, Color color, Color color2) {
		super(reservationPanel, title, color, color2);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(color);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setColor(color2);
		g2.drawString(title, this.getWidth()/3, this.getHeight()/2);
	}

}
