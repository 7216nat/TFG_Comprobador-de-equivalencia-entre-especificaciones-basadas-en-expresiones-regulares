package factories;

import objects.*;

public class FactoryER {
	
	private static Lenguaje[] availableERs = {
			new Simbolo(),
			new Vacio(),
			new Lambdaa()
	};
	
	/**
	 * parser, devuelve el tipo de expresion
	 * @param er
	 * @return
	 */
	public static Lenguaje parseER(String er) {
		Lenguaje _er = null;
		for (Lenguaje eb: availableERs) {
			_er = (Lenguaje) eb.parse(er);
			if (_er != null) {
				break;
			}
		}
		return _er;
	}
}
