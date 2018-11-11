
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * class for graphing Polynoms
 *
 * @author Elisha
 */
public class Grapher
{
	/**
	 * Graph a single Polynom between {@code x0} and {@code x1}, with {@code eps} size steps.
	 *
	 * @param polynom the Polynom to graph.
	 * @param x0 starting point.
	 * @param x1 ending point.
	 * @param eps step size.
	 */
	public static void graph(Polynom polynom, double x0, double x1, double eps)
	{
		if (x0 > x1)
		{
			double d = x0;
			x0 = x1;
			x1 = d;
		}

		List<Double> xData = new LinkedList<>();
		List<Double> yData = new LinkedList<>();

		for (double num = x0; num <= x1; num += eps)
		{
			xData.add(num);
			yData.add(polynom.f(num));
		}

		// Create Chart
		XYChart chart = QuickChart.getChart("Polynom Graphing", "X", "Y", polynom.toString(), xData, yData);

		// Show it
		new SwingWrapper<>(chart).displayChart();
	}
}
