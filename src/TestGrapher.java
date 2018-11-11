/**
 * @author Elisha
 */
public class TestGrapher
{
	public static void main(String[] args)
	{
		Polynom p = new Polynom();
		p.add(new Monom(2,0));
		p.add(new Monom(1,2));
		Polynom p2 = new Polynom("0.2x^4 + -1.5x^3 + 3.0x^2 + -x + -5");
		Grapher.graph(new Polynom[]{p,p2},-2,6,0.01);
	}
}
