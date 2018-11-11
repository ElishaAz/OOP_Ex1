/**
 * This class represents a simple "Monom" of the form ax^b, where a is a real number and a is an integer,
 * <a href = https://en.wikipedia.org/wiki/Monomial>see: https://en.wikipedia.org/wiki/Monomial</a>
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply.
 *
 * @author Elisha
 */
public class Monom implements function
{

	public static final boolean exceptNegativePowers = false;

	/**
	 * Creats a new Monom.
	 *
	 * @param coefficient a coefficient for this Monom.
	 * @param power       a power for this Monom.
	 */
	public Monom(double coefficient, int power)
	{
		this.set_coefficient(coefficient);
		this.set_power(power);
	}

	/**
	 * Copy constructor.
	 *
	 * @param other the Monom to copy.
	 */
	public Monom(Monom other)
	{
		this.set_coefficient(other.get_coefficient());
		this.set_power(other.get_power());
	}

	/**
	 * Constructs a Monom from a string.
	 *
	 * @param exp a String of the form {@code ax^b}, where a is a double and b is an int ({@code a} or/and {@code ^b} can be excluded).
	 * @throws NumberFormatException    if {@code a} is not a double or if {@code b} is not an int.
	 * @throws IllegalArgumentException if the expression is not of the form {@code ax^b}.
	 */
	public Monom(String exp)
	{
		if (exp == null || exp.isEmpty())
		{
			this.set_coefficient(0);
			this.set_power(0);
		} else
		{
			exp = exp.trim();
			exp = exp.toLowerCase();

			int xIndex = exp.indexOf('x');
			int powIndex = exp.indexOf('^');

			double coefficient;
			int power;

			if (xIndex == -1) // no 'x'
			{
				if (powIndex == -1)
				{
					coefficient = Double.parseDouble(exp);
				} else
				{
					throw new IllegalArgumentException("String " + exp + " is not of the form ax^b");
				}
				power = 0;
			} else
			{
				if (xIndex == 0) // if there is nothing before the x
				{
					coefficient = 1;
				} else if (exp.substring(0, xIndex).trim().equals("-"))
				{
					coefficient = -1;
				}else
				{
					coefficient = Double.parseDouble(exp.substring(0, xIndex).trim());
				}

				if (powIndex == -1) // no '^'
				{
					if (!exp.substring(xIndex + 1).trim().isEmpty()) // oif there is something after the x
					{
						throw new IllegalArgumentException("String " + exp + " is not of the form ax^b");
					}
					power = 1;
				} else
				{
					power = Integer.parseInt(exp.substring(powIndex + 1).trim());
				}
			}
			this.set_coefficient(coefficient);
			this.set_power(power);
		}
	}

	/**
	 * Calculates value at x.
	 *
	 * @return the value of this function at x.
	 */
	@Override
	public double f(double x)
	{
		return get_coefficient() * Math.pow(x, get_power());
	}

	/**
	 * Add a Monom with same power to this one.
	 *
	 * @param m Monom to add.
	 * @throws IllegalArgumentException if powers are not equal.
	 */
	public void add(Monom m)
	{
		if (get_power() != m.get_power())
		{
			throw new IllegalArgumentException("powers have to be equal!");
		} else
		{
			set_coefficient(get_coefficient() + m.get_coefficient());
		}
	}

	/**
	 * Subtract a Monom with the same power from this one.
	 *
	 * @param m Monom to subtract.
	 * @throws IllegalArgumentException if powers are not equal.
	 */
	public void subtract(Monom m)
	{
		if (get_power() != m.get_power())
		{
			throw new IllegalArgumentException("powers have to be equal!");
		} else
		{
			set_coefficient(get_coefficient() - m.get_coefficient());
		}

	}

	/**
	 * Multiply this Monom by {@code m}.
	 *
	 * @param m Monom to multiply by.
	 */
	public void multiply(Monom m)
	{
		set_power(get_power() + m.get_power());

		set_coefficient(get_coefficient() * m.get_coefficient());
	}

	/**
	 * Computes the derivative of this Monom.
	 *
	 * @return the derivative of this Monom.
	 */
	public Monom derivative()
	{
		Monom m = new Monom(this);

		if (m.get_power() >= 0)
		{
			m.set_coefficient(m.get_coefficient() * m.get_power());
			m.set_power(m.get_power() - 1);
		}

		return m;
	}

	/**
	 * Checks if this Monom is the zero Monom.
	 *
	 * @return true if and only if this Monom returns zero for every x.
	 */
	public boolean isZero()
	{
		return get_coefficient() == 0;
	}

	@Override
	public String toString()
	{
		String coefficient;
		if ((int) get_coefficient() == get_coefficient()) // if coefficient does not have a floating point
		{
			coefficient = (int)get_coefficient() + "";
		}else
		{
			coefficient = get_coefficient() + "";
		}
		if (get_coefficient() == 0)
		{
			return "0"; // 0
		}
		if (get_power() == 0)
		{
			return coefficient + ""; // a
		}
		if (get_power() == 1)
		{
			if (get_coefficient() == 1)
				return "x"; // x
			else if (get_coefficient() == -1)
				return "-x"; // -x
			else
				return coefficient + "x"; // ax
		}
		if (get_coefficient() == 1)
		{
			return "x^" + get_power(); // x^b
		}

		return coefficient + "x^" + get_power(); // ax^b
	}

	public int get_power()
	{
		return _power;
	}

	public double get_coefficient()
	{
		return _coefficient;
	}
	//****************** Private Methods and Data *****************

	private void set_coefficient(double a)
	{
		this._coefficient = a;
	}

	private void set_power(int p)
	{
		if (exceptNegativePowers && p < 0)
		{
			this._power = 0;
		}else
		{
			this._power = p;
		}
	}


	private double _coefficient;
	private int _power;


}
