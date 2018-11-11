
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * class for graphing Polynoms using XChart: <a href = "https://github.com/knowm/XChart">https://github.com/knowm/XChart</a>.
 *
 * @author Elisha
 */
public class Grapher
{
	/**
	 * Graph multiple Polynoms between {@code x0} and {@code x1}, with {@code eps} size steps.
	 *
	 * @param polynoms the Polynoms to graph.
	 * @param x0      starting point.
	 * @param x1      ending point.
	 * @param eps     step size.
	 */
	public static void graph(Polynom[] polynoms, double x0, double x1, double eps)
	{
		if (x0 > x1)
		{
			double d = x0;
			x0 = x1;
			x1 = d;
		}

		List<Double> xData = new LinkedList<>();
		List<Double>[] yData = new List[polynoms.length];

		for (double num = x0; num <= x1; num += eps)
		{
			xData.add(num);
			for (int i = 0; i < polynoms.length; i++)
			{
				if (yData[i] == null)
				{
					yData[i] = new LinkedList<>();
				}
				Polynom p = polynoms[i];
				yData[i].add(p.f(num));
			}
		}
		XYChart chart = new XYChart(600, 400);
		chart.setTitle("Polynom Graphing");
		chart.setXAxisTitle("X");
		chart.setYAxisTitle("Y");
		for (int i = 0; i < polynoms.length; i++)
		{
			Polynom p = polynoms[i];
			chart.addSeries(polynoms[i].toString(), xData, yData[i]).setMarker(SeriesMarkers.NONE);
		}
		// Show it
		new SwingWrapper<>(chart).displayChart();
	}

	/**
	 * Graph a single Polynom between {@code x0} and {@code x1}, with {@code eps} size steps.
	 *
	 * @param polynom the Polynom to graph.
	 * @param x0      starting point.
	 * @param x1      ending point.
	 * @param eps     step size.
	 */
	public static void graph(Polynom polynom, double x0, double x1, double eps)
	{
		graph(new Polynom[]{polynom}, x0,x1,eps);
	}
}
