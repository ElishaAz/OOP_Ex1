import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Elisha
 */
class PolynomTest
{
	@Test
	void ctorString()
	{
		Polynom p = new Polynom();
		p.add(new Monom(2.3, 5));
		p.add(new Monom(500.1, 2));
		p.add(new Monom(24, 0));
		p.add(new Monom(123, 100));
		Polynom p2 = new Polynom(p.toString());
		Polynom p3 = new Polynom(" 2.3x^5 + 500.1x^2 +24x ^0+ 123 x^ 100 ");
		if (!p.equals(p2))
		{
			fail("Creating a Polynom from toString");
		}
		if (!p.equals(p3))
		{
			fail("Creating a Polynom from String");
		}
		String[] bad = {"$", "23*-", "*3", "x^2x", "2xx"};
		String[] good = {"1x^2", "1x^0", "x^12345", "1234x^5678"};

		for (String s : bad)
		{
			try
			{

				new Polynom(s);

				fail("init from bad String " + s);
			} catch (Exception e)
			{

			}
		}
		for (String s : good)
		{
			try
			{

				new Polynom(s);

			} catch (Exception e)
			{
				fail("init from good String " + s);
			}
		}
	}

	@Test
	void ctorOther()
	{
		Polynom p1 = new Polynom();
		p1.add(new Monom(0, 1));
		p1.add(new Monom(5.5, 4));
		Polynom p2 = new Polynom(p1);
		if (!p1.equals(p2))
		{
			fail("not equal");
		}
		p2.add(new Monom(3.4, 1));
		if (p1.equals(p2))
		{
			fail("editing original");
		}
	}

	@Test
	void add()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		p1.add(new Monom(1, 2));
		p1.add(new Monom(3, 5));
		p2.add(new Monom(7, 15));
		p2.add(new Monom(100, 5));
		Polynom p12 = new Polynom(p1);
		p12.add(p2);
		Polynom p21 = new Polynom(p2);
		p21.add(p1);

		if (!p12.equals(p21))
		{
			fail("add is order specific");
		}

	}

	@Test
	void substract()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		p1.add(new Monom(1, 2));
		p1.add(new Monom(3, 5));
		p2.add(new Monom(7, 15));
		p2.add(new Monom(100, 5));
		Polynom p12 = new Polynom(p1);
		p12.substract(p2);
		p12.add(p2);

		if (!p12.equals(p1))
		{
			fail("subtract and then add is not original");
		}
	}

	@Test
	void multiply()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		p1.add(new Monom(1, 2));
		p1.add(new Monom(3, 5));
		p2.add(new Monom(7, 15));
		p2.add(new Monom(100, 5));
		Polynom p12 = new Polynom(p1);
		p12.multiply(p2);
		Polynom p21 = new Polynom(p2);
		p21.multiply(p1);

		if (!p12.equals(p12))
		{
			fail("multiply is order specific");
		}
	}

	@Test
	void equals()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		p1.add(new Monom(1, 2));
		p2.add(new Monom(1, 2));
		if (!p1.equals(p2))
		{
			fail("separate add not equal");
		}
		if (p1.equals(p3))
		{
			fail("non new Polynom is equal to new Polynom");
		}
	}

	@Test
	void isZero()
	{
		Polynom p1 = new Polynom();
		if (!p1.isZero())
			fail("new Polynom is not zero");
		p1.add(new Monom(0, 102));
		if (!p1.isZero())
			fail("zero Polynom is not zero");
		p1.add(new Monom(1, 1));
		if (p1.isZero())
			fail("non-zero Polynom is zero");
	}

	@Test
	void root()
	{
		Polynom p1 = new Polynom();
		p1.add(new Monom(1, 3));
		if (p1.f(p1.root(-10, 100.1, 0.001)) > 0.001)
		{
			fail("root is bigger than eps");
		}
	}

	@Test
	void copy()
	{
		ctorOther();
	}

	@Test
	void derivative()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		p1.add(new Monom(4, 2));
		p2.add(new Monom(4, 2));
		p3.add(new Monom(5, 2));
		if (!p1.derivative().equals(p1.derivative()))
		{
			fail("derivative is inconsistent");
		}
		if (!p1.derivative().equals(p2.derivative()))
		{
			fail("derivative is object-specific");
		}
		if (p1.derivative().equals(p3.derivative()))
		{
			fail("derivative is same for non-equal objects");
		}
	}

	@Test
	void area()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		p1.add(new Monom(4, 2));
		p2.add(new Monom(4, 2));
		p3.add(new Monom(5, 2));
		if (p1.area(0, 1, 0.001) != (p1.area(0, 1, 0.001)))
		{
			fail("area is inconsistent");
		}
		if (p1.area(0, 1, 0.001) != (p2.area(0, 1, 0.001)))
		{
			fail("area is object-specific");
		}
		if (p1.area(0, 1, 0.001) == (p3.area(0, 1, 0.001)))
		{
			fail("area is same for non-equal objects");
		}
	}

	@Test
	void f()
	{
		Polynom p1 = new Polynom();
		Polynom p2 = new Polynom();
		Polynom p3 = new Polynom();
		p1.add(new Monom(4, 2));
		p2.add(new Monom(4, 2));
		p3.add(new Monom(5, 2));
		if (p1.f(5) != (p1.f(5)))
		{
			fail("f is inconsistent");
		}
		if (p1.f(5) != (p2.f(5)))
		{
			fail("f is object-specific");
		}
		if (p1.f(5) == (p3.f(5)))
		{
			fail("f is the same for non-equal objects");
		}
		if (p1.f(5) == (p3.f(6)))
		{
			fail("f is the same in two different points");
		}
	}
}