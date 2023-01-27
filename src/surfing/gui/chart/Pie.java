package surfing.gui.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Pie extends JPanel{
	private PieDataset dataset;
	private JFreeChart pie;
	private PiePlot plot;
	private PieSectionLabelGenerator labelGenerator;
	
	public Pie() {
		
		setPreferredSize(new Dimension(200, 210));
		
		//인터페이스로 메서드를 통한 인스턴스화 실행
		dataset = createDataset();
		
		//차트 생성
		pie = ChartFactory.createPieChart("", dataset, false, true, false);
		
		//차트라벨 포맷 생성 및 정의
		labelGenerator = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot = (PiePlot)pie.getPlot();
		plot.setLabelGenerator(labelGenerator);
		
		
		//차트 패널 생성
		ChartPanel piePanel = new ChartPanel(pie);
		piePanel.setPreferredSize(new Dimension(200, 200));
		
		//스타일 적용 호출
		setDesign();
		
		add(piePanel);
		
	}
	
	public void setDesign() {
		//Color 세팅
		Paint paint0 = new Color(237, 241, 239);  //기본배경, 테두리 색
		Paint paint1 = new Color(102, 102, 255);
	    Paint paint2 = new Color(102, 178, 255);
	    Paint paint3 = new Color(102, 240, 255);
		
		//차트타이틀
		pie.setBackgroundPaint(paint0);
		pie.getTitle().setPaint(Color.black);
		
		//섹션배경 디자인
	    plot.setOutlinePaint(paint0);  //섹션 외부 사격형의 테두리
	    plot.setBackgroundPaint(paint0);
	    plot.setExplodePercent("입문", 0.02);  //섹션 간 간격
	    plot.setExplodePercent("커플", 0.02);  //섹션 간 간격
	    plot.setExplodePercent("퍼스널", 0.02);  //섹션 간 간격
	    
	    //라벨 디자인
	    plot.setLabelBackgroundPaint(paint0);
	    plot.setLabelOutlinePaint(paint0);
	    plot.setLabelFont(new Font("Verdana", Font.BOLD, 10));
	    
	    //섹션디자인
	    plot.setSectionPaint("입문", paint1);
	    plot.setSectionPaint("커플", paint2);
	    plot.setSectionPaint("퍼스널", paint3);
		
	}
	
	//차트의 dataset을 생성하는 메서드
	public PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		dataset.setValue("입문", 20);
		dataset.setValue("커플", 30);
		dataset.setValue("퍼스널", 30);
		
		return dataset;
	}
}
