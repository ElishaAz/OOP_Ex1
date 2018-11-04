import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Elisha
 */
class MonomTest
{

	@Test
	void f()
	{
		Monom m1 = new Monom(3,1);
		Monom m2 = new Monom(3,1);
		Monom m3 = new Monom(12,37);
		if (m1.f(5) != (m1.f(5)))
		{
			fail("f is inconsistent");
		}
		if (m1.f(5) != (m2.f(5)))
		{
			fail("f is object-specific");
		}
		if (m1.f(5) == (m3.f(5)))
		{
			fail("f is same for non-equal objects");
		}
		if (m1.f(5) == (m3.f(6)))
		{
			fail("f is the same in two different points");
		}
	}

	@Test
	void add()
	{
		Monom m1 = new Monom(3,1);
		Monom m2 = new Monom(3,1);
		Monom m3 = new Monom(12,1);
		m2.add(m3);
		if (m1.equals(m2))
			fail("no change");
	}

	@Test
	void subtract()
	{
		Monom m1 = new Monom(3,1);
		Monom m2 = new Monom(3,1);
		Monom m3 = new Monom(12,1);
		m2.subtract(m3);
		if (m1.equals(m2))
			fail("no change");
	}

	@Test
	void multiply()
	{
		Monom m1 = new Monom(3,1);
		Monom m2 = new Monom(3,1);
		Monom m3 = new Monom(12,37);
		m2.multiply(m3);
		if (m1.equals(m2))
			fail("no change");
	}

	@Test
	void derivative()
	{
		Monom m1 = new Monom(3,1);
		Monom m2 = new Monom(3,1);
		Monom m3 = new Monom(12,37);
		if (m1.derivative() != (m1.derivative()))
		{
			fail("derivative is inconsistent");
		}
		if (m1.derivative() != (m2.derivative()))
		{
			fail("derivative is object-specific");
		}
		if (m1.derivative() == (m3.derivative()))
		{
			fail("derivative is same for non-equal objects");
		}
		if (m1.derivative() == (m3.derivative()))
		{
			fail("derivative is the same in two different points");
		}
	}

	@Test
	void isZero()
	{
		Monom m1 = new Monom(0,102);
		if (!m1.isZero())
			fail("new Polynom is not zero");
		m1.add(new Monom(0, 102));
		if (!m1.isZero())
			fail("zero Polynom is not zero");
		m1.add(new Monom(1, 102));
		if (m1.isZero())
			fail("non-zero Polynom is zero");
	}
}