package test;
import objects.*;
import parser.ParserER;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		ParserER parser = new ParserER(new String_ref(str));
		ExpressionBase er = parser.parse();
		System.out.println(er.toString());
	}

}
