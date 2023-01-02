package test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

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

public class DualTest extends JFrame {
	public DualTest() {
		// 차트에 사용될 카테고리 dataset 선언
		CategoryDataset dataset1 = createDataset1();
		CategoryDataset dataset2 = createDataset2();

		// 차트 생성
		JFreeChart chart = createChart(dataset1, dataset2);
		
		// 차트패널 생성
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 300));
		
		add(chartPanel);
		
		setSize(550, 350);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new DualTest();
	}

	// 첫번째 차트에 대한 dataset 생성 메서드
	public CategoryDataset createDataset1() {
		// row keys 선언
		String series1 = "Series 1"; // 좌측 y축 value에 대한 세팅
		String series2 = "Dummy 1";

		// colum keys 선언(x축)
		String category1 = "Category1";
		String category2 = "Category2";
		String category3 = "Category3";
		String category4 = "Category4";

		// default dataset 선언
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// dataset value 세팅
		dataset.addValue(1.0, series1, category1);
		dataset.addValue(4.0, series1, category2);
		dataset.addValue(3.0, series1, category3);
		dataset.addValue(5.0, series1, category4);

		dataset.addValue(null, series2, category1);
		dataset.addValue(null, series2, category2);
		dataset.addValue(null, series2, category3);
		dataset.addValue(null, series2, category4);

		return dataset;
	}

	// 두번째 차트에 대한 dataset 생성 메서드
	public CategoryDataset createDataset2() {
		// row keys 선언
		String series1 = "Dummy 2"; // 공백 값
		String series2 = "Series 2"; // 우측 y축 value에 대한 세팅

		// colum keys 선언(x축)
		String category1 = "Category1";
		String category2 = "Category2";
		String category3 = "Category3";
		String category4 = "Category4";

		// default dataset 선언
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// dataset value 세팅(y축의 데이터 대상을 어느곳으로 할지에 따라서 적용 벨류가달라짐)
		dataset.addValue(null, series1, category1);
		dataset.addValue(null, series1, category2);
		dataset.addValue(null, series1, category3);
		dataset.addValue(null, series1, category4);

		dataset.addValue(75.0, series2, category1);
		dataset.addValue(87.0, series2, category2);
		dataset.addValue(96.0, series2, category3);
		dataset.addValue(68.0, series2, category4);

		return dataset;
	}

	// 차트 생성 메서드(dataset1, 2사용)
	public JFreeChart createChart(CategoryDataset dataset1, CategoryDataset dataset2) { // 매개값 전달
		// x축에 쓰여질 라벨
		CategoryAxis domainAxis = new CategoryAxis("Category");

		// 좌측 y축에 쓰여지는 라벨
		NumberAxis rangeAxis = new NumberAxis("Value");

		// bar 차트를 읽어드리는 객체
		LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
		
		// plot익명객체 기법으로 선언 및 정의
		CategoryPlot plot = new CategoryPlot(dataset1, domainAxis, rangeAxis, renderer1) {

			public LegendItemCollection getLegendItems() {

				LegendItemCollection result = new LegendItemCollection();

				CategoryDataset data = getDataset();
				if (data != null) {
					CategoryItemRenderer r = getRenderer();
					if (r != null) {
						LegendItem item = r.getLegendItem(0, 0);
						result.add(item);
					}
				}
				
				CategoryDataset data2 = getDataset(1);
				if(data2 != null) {
					CategoryItemRenderer r2 = getRenderer(1);
					if(r2 != null) {
						LegendItem item = r2.getLegendItem(1, 1);
						result.add(item);
					}
				}
				
				return result;
			}

		};
		
		JFreeChart chart = new JFreeChart("Dual Axis Bar Chart", plot);
		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.orange);
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setDataset(1, dataset2);
		plot.mapDatasetToRangeAxis(1, 1);
		
		ValueAxis axis2 = new NumberAxis("Secondary");
		plot.setRangeAxis(1, axis2);
		plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
		
		BarRenderer renderer2 = new BarRenderer();
		plot.setRenderer(1, renderer2);

		return chart;
	}
}
