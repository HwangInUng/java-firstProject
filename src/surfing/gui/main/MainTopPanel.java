package surfing.gui.main;

import java.awt.Dimension;

import javax.swing.JPanel;

import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class MainTopPanel extends JPanel{
	private SurfingApp app;
	
	public MainTopPanel(SurfingApp app, int width, int height) {
		this.app = app;
		
		setBorder(Page.PANEL_LINEBORDER);
		setPreferredSize(new Dimension(width, height));
		setBackground(Page.BG_COLOR);
	}
}
