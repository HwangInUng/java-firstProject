package surfing.gui.reservation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import surfing.gui.Page;

public class Cell extends JPanel{
	private ReservationPanel reservationPanel;
	public String title;
	public Color color, color2;
	
	public Cell(ReservationPanel reservationPanel, String title, Color color, Color color2){
		this.reservationPanel = reservationPanel;
		this.title = title;
		this.color = color;
		this.color2 = color2;
		
		setBorder(Page.LABEL_LINEBORDER);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(color);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setColor(color2);
		g2.drawString(title, this.getWidth()/3, this.getHeight()/3);
	}
}
