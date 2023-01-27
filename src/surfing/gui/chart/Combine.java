package surfing.gui.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Combine extends JPanel {
	private CategoryDataset dataset1, dataset2;
	private JFreeChart chart;
	private CategoryPlot plot;

	public Combine() {
		setPreferredSize(new Dimension(720, 370));
		
		//dataset 생성
		dataset1 = createDataset1();  //강습 현황에 대한 값을 매개변수로 넣어줘야함
		dataset2 = createDataset2();  //매출 현황에 대한 값을 매개변수로 넣어줘야함
		
		//차트 생성
		chart = createChart(dataset1, dataset2);
		
		//차트 패널 생성
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(720, 360));
		
		//스타일 적용
		setDesign();
		
		add(panel);
		
		}
	
	public void setDesign() {
		Paint paint0 = new Color(237, 241, 239);  //기본배경, 테두리 색
		Paint paint1 = new Color(102, 102, 255);
	    Paint paint2 = new Color(102, 178, 255);
		
		chart.setBackgroundPaint(paint0);
		chart.setBorderPaint(paint0);
		plot.setBackgroundPaint(paint0);
		
		//각 차트의 색상 변경
		plot.getRenderer(0).setSeriesPaint(0, paint2);
		plot.getRenderer(1).setSeriesPaint(1, paint1);
	}

	// 강습 현황 dataset 메서드 정의
	public CategoryDataset createDataset1() {
		// Row key 선언
		String series1 = "강습"; // 실제 데이터가 들어갈 key
		String series2 = "Dummy 1";

		// Coloumn key 선언
		String[] monthList = new String[12];
		for (int i = 0; i < 12; i++) {
			monthList[i] = i+1 + "월";
		}

		// 각 월의 현황을 담을 배열 선언
		int[] monthValue = new int[12];
		for (int i = 0; i < 12; i++) {
			monthValue[i] = i+1 * 10;
		}

		// default dataset 선언
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// value 세팅
		for (int i = 0; i < monthList.length; i++) {
			dataset.addValue(monthValue[i], series1, monthList[i]);
			dataset.addValue(null, series2, monthList[i]);
		}

		return dataset;
	}

	// 매출 현황 dataset 메서드 정의
	public CategoryDataset createDataset2() {
		// Row key 선언
		String series1 = "Dummy 2"; // 실제 데이터가 들어갈 key
		String series2 = "매출";

		// Coloumn key 선언
		String[] monthList = new String[12];
		for (int i = 0; i < 12; i++) {
			monthList[i] = i+1 + "월";
		}

		// 각 월의 현황을 담을 배열 선언
		int[] monthValue = new int[12];
		for (int i = 0; i < 12; i++) {
			monthValue[i] = i+1 * 40;
		}

		// default dataset 선언
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// value 세팅
		for (int i = 0; i < monthList.length; i++) {
			dataset.addValue(null, series1, monthList[i]);
			dataset.addValue(monthValue[i], series2, monthList[i]);
		}
		return dataset;
	}

	// 차트 생성 메서드
	public JFreeChart createChart(CategoryDataset dataset1, CategoryDataset dataset2) {
		// x축 중앙에 라벨 생성
		CategoryAxis domainAxis = new CategoryAxis("월 구분");

		// 좌측 y축 라벨
		NumberAxis rangeAxis = new NumberAxis("강습생 수");

		// Line차트 생성 객체
		LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();

		// 두가지 차트를 담을 plot 익명객체 기법 선언
		plot = new CategoryPlot(dataset1, domainAxis, rangeAxis, renderer1) {
			
			//비어있을 수 있는 컬렉션을 반환
			public LegendItemCollection getLegendItems() {
				//비어있는 새 범례 항목 컬렉션을 구성
				LegendItemCollection result = new LegendItemCollection();
				
				//dataset1 내부 범례를 지정하여 추가
				CategoryDataset data = getDataset();
				if (data != null) {
					CategoryItemRenderer r = getRenderer();
					if (r != null) {
						LegendItem item = r.getLegendItem(0, 0);
						result.add(item);
					}
				}

				//dataset1 내부 범례를 지정하여 추가
				CategoryDataset data2 = getDataset(1);
				if (data2 != null) {
					CategoryItemRenderer r2 = getRenderer(1);
					if (r2 != null) {
						LegendItem item = r2.getLegendItem(1, 1);
						result.add(item);
					}
				}
				return result;
			}
		};
		
		//반환값으로 사용할 차트 생성 및 dataset1,2 조합
		JFreeChart chart = new JFreeChart(plot); //스크롤과 비슷한 개념
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setDataset(1, dataset2); //0번째에는 1이 이미 들어간 상태
		plot.mapDatasetToRangeAxis(1, 1);
		
		ValueAxis axis2 = new NumberAxis("매출액");
		plot.setRangeAxis(1, axis2);
		plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
		
		//bar차트를 읽어드리는 객체를 plot에 세팅
		BarRenderer renderer2 = new BarRenderer();
		plot.setRenderer(1, renderer2);
		
		return chart;
	}
}
