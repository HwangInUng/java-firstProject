package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieTest{
	public static void main(String[] args) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		dataset.setValue("남자", 30);
		dataset.setValue("여자", 30);
		
		System.setProperty("java.awt.headless", "true");;
		
		JFreeChart pie = ChartFactory.createPieChart("성별분포", dataset, false, true, false);
		
		PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} ({2})",
		new DecimalFormat("0"), new DecimalFormat("0%"));
		((PiePlot)pie.getPlot()).setLabelGenerator(labelGenerator);
		
		pie.setBackgroundPaint(Color.white);
		pie.getTitle().setPaint(Color.black);
		
		PiePlot plot = (PiePlot)pie.getPlot();
		
		//RGB 컬러
		Paint paint0 = Color.white;
		Paint paint1 = new Color(102, 102, 255);
	    Paint paint2 = new Color(102, 178, 255);
	    
		//범위 컬러 지정
	    plot.setOutlinePaint(paint0);  //섹션 외부 사격형의 테두리
	    plot.setExplodePercent("남자", 0.05);  //섹션 간 간격
	    plot.setBackgroundPaint(paint0);
	    plot.setLabelBackgroundPaint(paint0);
	    plot.setLabelOutlinePaint(paint0);
	    plot.setLabelFont(new Font("Verdana", Font.BOLD, 20));
	    plot.setSectionPaint("남자", paint1);
	    plot.setSectionPaint("여자", paint2);
	    
	    
	    File chartFile = new File("/Users/inung/Desktop/test.jpg");
	    
	    try {
			FileOutputStream fos = new FileOutputStream(chartFile);
			ChartUtilities.writeChartAsJPEG(fos, pie, 600, 600);
			
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
