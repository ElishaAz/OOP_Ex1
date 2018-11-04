import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom>
{

	// ******** add your code below *********

	/**
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return a negative integer, zero, or a positive integer as the
	 * first argument is less than, equal to, or greater than the
	 * second.
	 * @throws NullPointerException if an argument is null and this
	 *                              comparator does not permit null arguments
	 * @throws ClassCastException   if the arguments' types prevent them from
	 *                              being compared by this comparator.
	 */
	@Override
	public int compare(Monom o1, Monom o2)
	{
		if (o1.get_power() < o2.get_power())
			return -1;

		if (o1.get_power() == o2.get_power())
		{
			if (o1.get_coefficient() < o2.get_coefficient())
				return -1;

			if (o1.get_coefficient() == o2.get_coefficient())
				return 0;

			if (o1.get_coefficient() > o2.get_coefficient())
				return 1;
		}

		if (o1.get_power() > o2.get_power())
			return 1;

		return 0;
	}
}
