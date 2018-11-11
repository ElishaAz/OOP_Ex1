import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 *
 * @author Elisha
 */
public class Polynom implements Polynom_able
{

	// ********** Change if you change database ***********

	// Constructors

	/**
	 * Creates a new Polynom.
	 */
	public Polynom()
	{
		data = new LinkedList<>();
	}

	/**
	 * Database.
	 */
	private List<Monom> data;

	/**
	 * Add m1 to this Polynom
	 *
	 * @param m1 Monom to add.
	 */
	@Override
	public void add(Monom m1)
	{
		data.add(new Monom(m1));
		sort();
	}

	/**
	 * @return An Iterator of the database.
	 */

	@Override
	public Iterator<Monom> iteretor()
	{
		return data.iterator();
	}

	/**
	 * Sort the database according to {@code Monom_Comperator}.
	 */
	private void sort()
	{
		this.data.sort(new Monom_Comperator().reversed());
	}

	@Override
	public String toString()
	{
		StringBuilder ans = new StringBuilder();
		Iterator<Monom> it = iteretor();
		if (!it.hasNext() || isZero())
			return "0";
		Monom m = it.next();
		while (it.hasNext())
		{
			if (!m.toString().trim().equals("0"))
			{
				ans.append(m.toString());
				ans.append(" + ");
			}
			m = it.next();
		}
		ans.append(m.toString());
		return ans.toString();
	}

	// ********** Until here ***********

	/**
	 * Copy constructor.
	 *
	 * @param other Polynom to copy.
	 */
	public Polynom(Polynom other)
	{
		this();
		Iterator<Monom> it = other.iteretor();
		while (it.hasNext())
		{
			add(new Monom(it.next()));
		}
		sort();
	}

	/**
	 * Constructs a Polynom from the String {@code exp}.
	 *
	 * @param exp a String of the form: a<sub>0</sub>x^b<sub>0</sub> + a<sub>1</sub>x^b<sub>1</sub> + a<sub>2</sub>x^b<sub>2</sub> + ...  + a<sub>n</sub>x^b<sub>n</sub>,
	 *            where a<sub>0 .. n</sub> are doubles, and b<sub>0 .. n</sub> are ints.
	 */
	public Polynom(String exp)
	{
		this();
		if (exp == null || exp.isEmpty())
		{
			return;
		}
		String[] arr = exp.split("\\+");
		for (String s : arr)
		{
			add(new Monom(s));
		}
	}

	/**
	 * Add p1 to this Polynom
	 *
	 * @param p1 {@code Polynom_able} to add.
	 */
	@Override
	public void add(Polynom_able p1)
	{
		Iterator<Monom> it1 = p1.iteretor();
		while (it1.hasNext())
		{
			Monom m = it1.next();
			Monom m2 = getPow(m.get_power());

			if (m2 == null)
			{
				add(m);
			} else
			{
				m2.add(m);
			}
		}
	}


	/**
	 * Subtract p1 from this Polynom
	 *
	 * @param p1 {@code Polynom_able} to subtract.
	 */
	@Override
	public void substract(Polynom_able p1)
	{
		Iterator<Monom> it = p1.iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			Monom m2 = getPow(m.get_power());

			if (m2 == null)
			{
				add(new Monom(-m.get_coefficient(), m.get_power()));
			} else
			{
				m2.subtract(m);
				// removeZeros();
			}
		}
	}

	/**
	 * Multiply this Polynom by p1
	 *
	 * @param p1 {@code Polynom_able} to multiply by.
	 */
	@Override
	public void multiply(Polynom_able p1)
	{
		Iterator<Monom> it = p1.iteretor();
		Polynom original = new Polynom(this);
		Polynom_able p = copy();
		if (it.hasNext())
		{
			Monom m = it.next();
			multiply(this, m);
		}
		while (it.hasNext())
		{
			Monom m = it.next();
			multiply(p, m);
			add(p);
			p = original.copy();
		}
		simplify();
	}

	/**
	 * Multiply {@code p} by {@code m}.
	 *
	 * @param p {@code Polynom_able} to multiply.
	 * @param m {@code Monom} to multiply by.
	 */
	public void multiply(Polynom_able p, Monom m)
	{
		Iterator<Monom> it = p.iteretor();
		while (it.hasNext())
		{
			Monom m2 = it.next();
			m2.multiply(m);
		}
	}

	/**
	 * Test if this Polynom is logically equal to p1.
	 *
	 * @param p1 {@code Polynom_able} to test against.
	 * @return true if and only if this Polynom represents the same function ans p1.
	 */
	@Override
	public boolean equals(Polynom_able p1)
	{
		boolean ans = true;
		Iterator<Monom> it = p1.iteretor();
		if (p1.isZero())
		{
			return isZero();
		}
		while (it.hasNext())
		{
			Monom m = it.next();

			if ((!m.isZero()) && (!contains(m)))
			{
				ans = false;
				break;
			}

		}
		return ans;
	}

	@Override
	public boolean equals(Object other)
	{
		return other instanceof Polynom_able && equals((Polynom_able) other);

	}

	/**
	 * Test if this is the Zero Polynom
	 *
	 * @return true if and only if for all of the Monoms {@code monom.isZero()} is true.
	 */
	@Override
	public boolean isZero()
	{
		boolean ans = true;
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			if (!m.isZero())
			{
				ans = false;
				break;
			}
		}
		return ans;
	}

	/**
	 * Compute a value x' (x0 <= x' <= x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1) <= 0, returns f(x2) such that:
	 * *	(i) x0 <= x2 <= x1 && (ii) f(x2) < eps
	 *
	 * @param x0  starting point
	 * @param x1  end point
	 * @param eps step (positive) value
	 * @return the first x such that x0 <= x <= x1 and |f(x)| < eps.
	 */
	@Override
	public double root(double x0, double x1, double eps)
	{
		if (f(x0) * f(x1) > 0)
		{
			throw new IllegalArgumentException("Exactly one argument must be negative!");
		} else if (f(x1) == 0)
			return x1;
		else if (f(x0) == 0)
			return x0;

		if (x0 > x1)
		{
			double d = x1;
			x1 = x0;
			x0 = d;
		}

		double x = 0;
		double xVal;
		while (Math.abs(x0 - x1) >= eps)
		{
			x = (x1 + x0) / 2;
			xVal = f(x);

			if (Math.abs(xVal) <= eps)
			{
				return x;
			} else
			{
				if (f(x0) * xVal <= 0)
				{
					x1 = x;
				} else if (xVal * f(x1) <= 0)
				{
					x0 = x;
				} else
				{
					System.err.println("ERROR");
					return 0;
				}
			}
		}
		System.out.println("x:" + x + ", x0:" + x0 + ",x1:" + x1);
		System.err.println("ERROR");
		return 0;
	}

	/**
	 * create a deep copy of this Polynum
	 *
	 * @return a deep copy of this Polynum.
	 */
	@Override
	public Polynom_able copy()
	{
		return new Polynom(this);
	}

	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 *
	 * @return A Polynom that is the derivative of this Polynom.
	 */
	@Override
	public Polynom_able derivative()
	{
		Polynom p = new Polynom();
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			p.add(m.derivative());
		}
		p.sort();

		return p;
	}

	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps,
	 * see: https://en.wikipedia.org/wiki/Riemann_integral. Only positive values!
	 *
	 * @param x0  Start of range.
	 * @param x1  End of range.
	 * @param eps Size of steps.
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.
	 */
	@Override
	public double area(double x0, double x1, double eps)
	{
		double ans = 0;
		double current = x0;
		while (current + eps < x1)
		{
			if (f(current) < 0 || f(current + eps) < 0)
			{
				throw new IllegalArgumentException("Only positive values!");
			}
			ans += ((f(current) + f(current + eps)) * eps) / 2;
			current += eps;
		}
		ans += ((f(current) + f(x1)) * (current - x1)) / 2;
		return ans;
	}


	/**
	 * Calculates the function for x.
	 *
	 * @param x the x to calculate for.
	 * @return the function calculated for x.
	 */
	@Override
	public double f(double x)
	{
		double ans = 0;
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			ans += m.f(x);
		}
		return ans;
	}


	/**
	 * Checks if there is a Monom that equals to {@code mon} according to {@code Monom_Comperator.compare}.
	 *
	 * @param mon Monom to find.
	 * @return true if there is a Monom equaling to {@code mon} and false otherwise.
	 */
	private boolean contains(Monom mon)
	{
		Monom_Comperator mc = new Monom_Comperator();
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			if (mc.compare(mon, m) == 0)
			{
				return true;
			}
		}
		return false;
	}

	public void removeZeros()
	{
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			if (it.next().isZero())
				it.remove();
		}
	}

	/**
	 * Finds the first Monom that has the power {@code pow}.
	 *
	 * @param pow power of the Monom to find.
	 * @return first Monom that has the power {@code pow}. null if no Monom found.
	 */
	private Monom getPow(int pow)
	{
		Iterator<Monom> it = iteretor();
		while (it.hasNext())
		{
			Monom m = it.next();
			if (m.get_power() == pow)
			{
				return m;
			}
		}
		return null;
	}


	/**
	 * Simplifies the database so there are'nt any multiple Monoms with the same power.
	 */
	private void simplify()
	{
		sort();
		Iterator<Monom> it = iteretor();
		if (!it.hasNext())
			return;
		Monom m1 = it.next();
		while (it.hasNext())
		{
			Monom m2 = it.next();
			if (m1.get_power() == m2.get_power())
			{
				m1.add(m2);
				it.remove();
			}
			m1 = m2;
		}
	}

}
