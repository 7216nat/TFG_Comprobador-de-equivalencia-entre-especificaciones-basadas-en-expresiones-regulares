import java.util.ArrayList;
import java.util.List;
import java.util.regex.*; 
public class Hello_world {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String regexConcat = "[(\\*\\w)(\\)\\w)(\\)\\()][\\w\\+\\*\\(\\)]+";
		String regexParenthesis = "\\(.+";
		String regexLambda = "\\\\e";
		String words = ")(da";
		String regex = "[(]";
		List<String> matchList = new ArrayList<String>();
		System.out.println(Pattern.matches(regexConcat,words));
		Pattern p = Pattern.compile(regexConcat);
		Matcher m = p.matcher(words);
		m.find();
		System.out.println(m.group());
		
		String hola = "hola";
		System.out.println(hola.substring(2));
		
	}
	
}
