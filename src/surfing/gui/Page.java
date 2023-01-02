package surfing.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Page extends JPanel{
	SurfingApp app;
	
	//최상위 객체가 보유하여 필요 시 부착하여 사용할 수 있도록 보유
	JPanel container, p_north, p_south;
	
	//각 패널에서 사용할 Border 객체 보유
	public static final Border PANEL_LINEBORDER = new LineBorder(new Color(31, 140, 255), 2, true);
	public static final Border LABEL_LINEBORDER = new LineBorder(Color.GRAY, 2, true);
	public static final Border MAINLABEL_LINEBORDER = new LineBorder(Color.GRAY, 2, true);
	
	//각 패널에서 사용할 컬러 객체 보유
	public static final Color BG_COLOR = new Color(237, 241, 239);
	public static final Color CLICK_COLOR = new Color(102, 178, 255);
	public static final Color NONCLICK_COLOR = Color.white;
	public static final Color RSVCELL_COLOR = new Color(102, 178, 255);
	
	public Page(SurfingApp app) {
		this.app = app;
		
		container = new JPanel();
		p_north = new JPanel();
		p_south = new JPanel();
		
		//중앙 영역 담당
		container.setPreferredSize(new Dimension(790, 755));
		
		//상단 영역 담당
		p_north.setPreferredSize(new Dimension(795, 50));
		p_north.setBorder(Page.PANEL_LINEBORDER);
		
		//하단 영역 담당
		p_south.setPreferredSize(new Dimension(795, 50));
		p_south.setBorder(Page.PANEL_LINEBORDER);
	}
}
