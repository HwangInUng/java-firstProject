package surfing.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import surfing.gui.chart.Pie;

public class MainTopPanel extends JPanel{
	SurfingApp app;
	
	public MainTopPanel(SurfingApp app, int width, int height) {
		this.app = app;
		
		setBorder(Page.PANEL_LINEBORDER);
		setPreferredSize(new Dimension(width, height));
		setBackground(Page.BG_COLOR);
	}
}
