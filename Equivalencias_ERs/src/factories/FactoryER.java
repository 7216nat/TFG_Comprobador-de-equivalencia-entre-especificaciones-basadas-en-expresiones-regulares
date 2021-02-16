package factories;

import objects.*;

public class FactoryER {
	
	private static ExpressionBase[] availableERs = {
			new Simbolo(),
			new Vacio(),
			new Lambdaa()
	};
	
	/**
	 * parser, devuelve el tipo de expresion
	 * @param er
	 * @return
	 */
	public static ExpressionBase parseER(String er) {
		ExpressionBase _er = null;
		for (ExpressionBase eb: availableERs) {
			_er = (ExpressionBase) eb.parse(er);
			if (_er != null) {
				break;
			}
		}
		return _er;
	}
}
