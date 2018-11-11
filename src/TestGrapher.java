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
		Grapher.graph(p,-10,10,1);
	}
}
