package surfing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MenuLabel extends JLabel{
	MenuPanel menuPanel;
	int index;
	
	public MenuLabel(String menuName, int n, MenuPanel menuPanel, int index) {
		super(menuName, n);
		this.menuPanel = menuPanel;
		this.index = index;
		
		setBorder(Page.LABEL_LINEBORDER);
		setFont(new Font("배달의민족 연성", Font.BOLD, 20));
		setPreferredSize(new Dimension(150, 50));
		setOpaque(true);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
					//생성 시 부여받은 인덱스를 이용 각 페이지로 이동
					menuPanel.app.showHide(index+1);
					menuPanel.changeColor(index);;
			}
		});
	}
}
