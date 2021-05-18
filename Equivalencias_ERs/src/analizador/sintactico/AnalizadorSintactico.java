
//----------------------------------------------------
// The following code was generated by CUP v0.11b beta 20140220
// Tue May 18 21:02:34 CEST 2021
//----------------------------------------------------

package analizador.sintactico;

import java_cup.runtime.*;
import objects.*;
import analizador.lexico.AnalizadorLexico;
import analizador.lexico.UnidadLexica;
import control.ElementoLista;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import analizador.errores.ErrorAnalizador;
import analizador.lexico.StringLocalizado;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** CUP v0.11b beta 20140220 generated parser.
  * @version Tue May 18 21:02:34 CEST 2021
  */
public class AnalizadorSintactico extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public AnalizadorSintactico() {super();}

  /** Constructor which sets the default scanner. */
  public AnalizadorSintactico(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public AnalizadorSintactico(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\036\000\002\002\004\000\002\002\004\000\002\003" +
    "\003\000\002\003\002\000\002\004\004\000\002\004\003" +
    "\000\002\005\006\000\002\006\003\000\002\006\002\000" +
    "\002\007\004\000\002\007\003\000\002\010\006\000\002" +
    "\011\005\000\002\011\003\000\002\012\004\000\002\012" +
    "\003\000\002\013\004\000\002\013\004\000\002\013\004" +
    "\000\002\013\003\000\002\014\003\000\002\014\003\000" +
    "\002\014\003\000\002\014\003\000\002\014\003\000\002" +
    "\014\005\000\002\014\005\000\002\015\005\000\002\015" +
    "\003\000\002\016\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\055\000\010\002\ufffe\004\010\006\ufffe\001\002\000" +
    "\010\002\uffff\004\010\006\uffff\001\002\000\004\002\056" +
    "\001\002\000\006\002\ufff9\006\050\001\002\000\010\002" +
    "\ufffc\004\ufffc\006\ufffc\001\002\000\004\024\011\001\002" +
    "\000\004\005\012\001\002\000\020\014\015\016\014\021" +
    "\020\022\013\023\016\024\021\025\017\001\002\000\040" +
    "\002\uffea\004\uffea\006\uffea\007\uffea\010\uffea\011\uffea\012" +
    "\uffea\014\uffea\015\uffea\016\uffea\021\uffea\022\uffea\023\uffea" +
    "\024\uffea\025\uffea\001\002\000\004\023\036\001\002\000" +
    "\020\014\015\016\014\021\020\022\013\023\016\024\021" +
    "\025\017\001\002\000\040\002\uffed\004\uffed\006\uffed\007" +
    "\uffed\010\uffed\011\uffed\012\uffed\014\uffed\015\uffed\016\uffed" +
    "\021\uffed\022\uffed\023\uffed\024\uffed\025\uffed\001\002\000" +
    "\040\002\uffe9\004\uffe9\006\uffe9\007\uffe9\010\uffe9\011\uffe9" +
    "\012\uffe9\014\uffe9\015\uffe9\016\uffe9\021\uffe9\022\uffe9\023" +
    "\uffe9\024\uffe9\025\uffe9\001\002\000\040\002\uffeb\004\uffeb" +
    "\006\uffeb\007\uffeb\010\uffeb\011\uffeb\012\uffeb\014\uffeb\015" +
    "\uffeb\016\uffeb\021\uffeb\022\uffeb\023\uffeb\024\uffeb\025\uffeb" +
    "\001\002\000\040\002\uffec\004\uffec\006\uffec\007\uffec\010" +
    "\uffec\011\uffec\012\uffec\014\uffec\015\uffec\016\uffec\021\uffec" +
    "\022\uffec\023\uffec\024\uffec\025\uffec\001\002\000\040\002" +
    "\uffee\004\uffee\006\uffee\007\uffee\010\uffee\011\uffee\012\uffee" +
    "\014\uffee\015\uffee\016\uffee\021\uffee\022\uffee\023\uffee\024" +
    "\uffee\025\uffee\001\002\000\040\002\ufff2\004\ufff2\006\ufff2" +
    "\007\ufff2\010\031\011\032\012\033\014\ufff2\015\ufff2\016" +
    "\ufff2\021\ufff2\022\ufff2\023\ufff2\024\ufff2\025\ufff2\001\002" +
    "\000\032\002\ufff4\004\ufff4\006\ufff4\007\ufff4\014\015\015" +
    "\ufff4\016\014\021\020\022\013\023\016\024\021\025\017" +
    "\001\002\000\012\002\ufffb\004\ufffb\006\ufffb\007\026\001" +
    "\002\000\020\014\015\016\014\021\020\022\013\023\016" +
    "\024\021\025\017\001\002\000\032\002\ufff5\004\ufff5\006" +
    "\ufff5\007\ufff5\014\015\015\ufff5\016\014\021\020\022\013" +
    "\023\016\024\021\025\017\001\002\000\040\002\ufff3\004" +
    "\ufff3\006\ufff3\007\ufff3\010\031\011\032\012\033\014\ufff3" +
    "\015\ufff3\016\ufff3\021\ufff3\022\ufff3\023\ufff3\024\ufff3\025" +
    "\ufff3\001\002\000\040\002\ufff1\004\ufff1\006\ufff1\007\ufff1" +
    "\010\ufff1\011\ufff1\012\ufff1\014\ufff1\015\ufff1\016\ufff1\021" +
    "\ufff1\022\ufff1\023\ufff1\024\ufff1\025\ufff1\001\002\000\040" +
    "\002\ufff0\004\ufff0\006\ufff0\007\ufff0\010\ufff0\011\ufff0\012" +
    "\ufff0\014\ufff0\015\ufff0\016\ufff0\021\ufff0\022\ufff0\023\ufff0" +
    "\024\ufff0\025\ufff0\001\002\000\040\002\uffef\004\uffef\006" +
    "\uffef\007\uffef\010\uffef\011\uffef\012\uffef\014\uffef\015\uffef" +
    "\016\uffef\021\uffef\022\uffef\023\uffef\024\uffef\025\uffef\001" +
    "\002\000\006\007\026\015\035\001\002\000\040\002\uffe8" +
    "\004\uffe8\006\uffe8\007\uffe8\010\uffe8\011\uffe8\012\uffe8\014" +
    "\uffe8\015\uffe8\016\uffe8\021\uffe8\022\uffe8\023\uffe8\024\uffe8" +
    "\025\uffe8\001\002\000\004\013\044\001\002\000\006\017" +
    "\uffe5\020\uffe5\001\002\000\006\017\042\020\041\001\002" +
    "\000\004\023\036\001\002\000\040\002\uffe7\004\uffe7\006" +
    "\uffe7\007\uffe7\010\uffe7\011\uffe7\012\uffe7\014\uffe7\015\uffe7" +
    "\016\uffe7\021\uffe7\022\uffe7\023\uffe7\024\uffe7\025\uffe7\001" +
    "\002\000\006\017\uffe6\020\uffe6\001\002\000\004\023\045" +
    "\001\002\000\006\017\uffe4\020\uffe4\001\002\000\004\002" +
    "\001\001\002\000\006\002\ufff7\006\ufff7\001\002\000\004" +
    "\024\053\001\002\000\006\002\ufffa\006\050\001\002\000" +
    "\006\002\ufff8\006\ufff8\001\002\000\004\005\054\001\002" +
    "\000\020\014\015\016\014\021\020\022\013\023\016\024" +
    "\021\025\017\001\002\000\010\002\ufff6\006\ufff6\007\026" +
    "\001\002\000\004\002\000\001\002\000\010\002\ufffd\004" +
    "\ufffd\006\ufffd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\055\000\012\002\004\003\005\004\003\005\006\001" +
    "\001\000\004\005\056\001\001\000\002\001\001\000\010" +
    "\006\045\007\050\010\046\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\012\011\024\012\023" +
    "\013\022\014\021\001\001\000\002\001\001\000\006\015" +
    "\037\016\036\001\001\000\012\011\033\012\023\013\022" +
    "\014\021\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\013\027\014\021\001\001\000\002\001" +
    "\001\000\010\012\026\013\022\014\021\001\001\000\006" +
    "\013\027\014\021\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\016\042\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\010\051\001\001\000\002\001\001\000\002\001\001\000" +
    "\012\011\054\012\023\013\022\014\021\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$AnalizadorSintactico$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$AnalizadorSintactico$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$AnalizadorSintactico$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** User initialization code. */
  public void user_init() throws java.lang.Exception
    {
 
   errores = new ErrorAnalizador();
   AnalizadorLexico alex = (AnalizadorLexico)getScanner();
   alex.fijaGestionErrores(errores);

    }

  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {
 return getScanner().next_token(); 
    }

 
   private ErrorAnalizador errores;
   public void syntax_error(Symbol unidadLexica){
     errores.errorSintactico((UnidadLexica)unidadLexica);
   }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$AnalizadorSintactico$actions {


   private boolean mapPerm = true;
   private List<ElementoLista> parselist = new ArrayList<>();
   private Map<String, UnidadParse> auxmap = new HashMap<>();
   public void write(String msg){
   		System.out.println("Nodo: " + msg);
   }

  private final AnalizadorSintactico parser;

  /** Constructor */
  CUP$AnalizadorSintactico$actions(AnalizadorSintactico parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$AnalizadorSintactico$do_action_part00000000(
    int                        CUP$AnalizadorSintactico$act_num,
    java_cup.runtime.lr_parser CUP$AnalizadorSintactico$parser,
    java.util.Stack            CUP$AnalizadorSintactico$stack,
    int                        CUP$AnalizadorSintactico$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$AnalizadorSintactico$result;

      /* select the action based on the action number */
      switch (CUP$AnalizadorSintactico$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // Pre ::= LAuxs LDefs 
            {
              Object RESULT =null;
		 RESULT=parselist; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Pre",0, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= Pre EOF 
            {
              Object RESULT =null;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		RESULT = start_val;
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
          /* ACCEPT */
          CUP$AnalizadorSintactico$parser.done_parsing();
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // LAuxs ::= Auxs 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("LAuxs",1, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // LAuxs ::= 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("LAuxs",1, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // Auxs ::= Auxs Aux 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Auxs",2, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // Auxs ::= Aux 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Auxs",2, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // Aux ::= AUX VAR IGUAL E0 
            {
              Object RESULT =null;
		StringLocalizado v = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-2)).value;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  auxmap.put(v.toString(), e); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Aux",3, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // LDefs ::= Defs 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("LDefs",4, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // LDefs ::= 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("LDefs",4, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // Defs ::= Defs Def 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Defs",5, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // Defs ::= Def 
            {
              Object RESULT =null;

              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Defs",5, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // Def ::= DEF VAR IGUAL E0 
            {
              Object RESULT =null;
		StringLocalizado v = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-2)).value;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  parselist.add(new ElementoLista(v.toString(), e.er, e.str)); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Def",6, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // E0 ::= E0 ALTER E1 
            {
              UnidadParse RESULT =null;
		UnidadParse e1 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-2)).value;
		UnidadParse e2 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  ExpressionBase union;
			if (e1.er.getType() == Tipo.VACIO)
				union = e2.er;
			else if (e2.er.getType() == Tipo.VACIO)
				union = e1.er;
			else
				union = new Union(e1.er, e2.er);
			RESULT= new UnidadParse(e1.str+"|"+e2.str, union); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E0",7, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // E0 ::= E1 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT=e; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E0",7, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // E1 ::= E1 E2 
            {
              UnidadParse RESULT =null;
		UnidadParse e1 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		UnidadParse e2 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  if (e1.er.getType() == Tipo.VACIO || e2.er.getType() == Tipo.VACIO)
				RESULT = new UnidadParse(e1.str+e2.str, new Vacio());
			else{
				ExpressionBase concat;
				if (e1.er.getType() == Tipo.LAMBDA)
					concat = e2.er;
				else if (e2.er.getType() == Tipo.LAMBDA)
					concat = e1.er;
				else
					concat = new Concat(e1.er, e2.er);
				 RESULT = new UnidadParse(e1.str+e2.str, concat);
			} 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E1",8, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // E1 ::= E2 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = e; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E1",8, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // E2 ::= E2 ASTERISCO 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		  if (e.er.getType() == Tipo.KLEEN)
				RESULT = e;
			else
				RESULT = new UnidadParse(e.str+"*", new Kleen(e.er)); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E2",9, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // E2 ::= E2 MAS 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		  RESULT = new UnidadParse(e.str+"+", new Concat(e.er, new Kleen(e.er))); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E2",9, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // E2 ::= E2 INTERR 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		 RESULT = new UnidadParse(e.str+"?", new Union(e.er, new Lambdaa())); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E2",9, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // E2 ::= E3 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = e; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E2",9, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // E3 ::= SIM 
            {
              UnidadParse RESULT =null;
		StringLocalizado s = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = new UnidadParse(s.toString(), new Simbolo(s.toString())); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // E3 ::= VAR 
            {
              UnidadParse RESULT =null;
		StringLocalizado v = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  if (auxmap.containsKey(v.toString()))
				RESULT = auxmap.get(v.toString());
			else{
				throw new Exception("line " + v.fila() + ", colum " + v.col() + ":Error sint�ctico al leer el archivo: no hay ning�n auxiliar previo con el nombre \""+ v.toString() +"\".");
			} 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // E3 ::= LAMBDA 
            {
              UnidadParse RESULT =null;
		StringLocalizado l = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = new UnidadParse(l.toString(), new Lambdaa()); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // E3 ::= VACIO 
            {
              UnidadParse RESULT =null;
		StringLocalizado va = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = new UnidadParse(va.toString(), new Vacio()); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // E3 ::= ESCAPESIM 
            {
              UnidadParse RESULT =null;
		StringLocalizado esc = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		 RESULT = new UnidadParse("\\" + esc, new Simbolo(esc.toString())); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // E3 ::= PAP E0 PCIE 
            {
              UnidadParse RESULT =null;
		UnidadParse e = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		  e.str = "(" + e.str +")"; 
			RESULT = e; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // E3 ::= CORAP Rangos CORCIE 
            {
              UnidadParse RESULT =null;
		UnidadParse r = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-1)).value;
		  r.str = "[" + r.str + "]";
			RESULT = r; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("E3",10, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // Rangos ::= Rangos COMA Rango 
            {
              UnidadParse RESULT =null;
		UnidadParse e1 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-2)).value;
		UnidadParse e2 = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  RESULT = new UnidadParse(e1.getStr()+","+e2.getStr(), new Union(e1.getER(), e2.getER())); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Rangos",11, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // Rangos ::= Rango 
            {
              UnidadParse RESULT =null;
		UnidadParse r = (UnidadParse)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  RESULT = r; 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Rangos",11, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // Rango ::= SIM GUION SIM 
            {
              UnidadParse RESULT =null;
		StringLocalizado s1 = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.elementAt(CUP$AnalizadorSintactico$top-2)).value;
		StringLocalizado s2 = (StringLocalizado)((java_cup.runtime.Symbol) CUP$AnalizadorSintactico$stack.peek()).value;
		  char c1 = s1.toString().charAt(0);
			char c2 = s2.toString().charAt(0);
			if (c1 > c2) {
				throw new Exception("line " + s1.fila() + ", colum " + s1.col() + ": Syntax Range error: " + s1 + " > " + s2);
			}  
			RESULT= new UnidadParse(s1+"-"+s2, new UnionRangos(new RangoCharacter(c1, c2))); 
              CUP$AnalizadorSintactico$result = parser.getSymbolFactory().newSymbol("Rango",12, RESULT);
            }
          return CUP$AnalizadorSintactico$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$AnalizadorSintactico$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$AnalizadorSintactico$do_action(
    int                        CUP$AnalizadorSintactico$act_num,
    java_cup.runtime.lr_parser CUP$AnalizadorSintactico$parser,
    java.util.Stack            CUP$AnalizadorSintactico$stack,
    int                        CUP$AnalizadorSintactico$top)
    throws java.lang.Exception
    {
              return CUP$AnalizadorSintactico$do_action_part00000000(
                               CUP$AnalizadorSintactico$act_num,
                               CUP$AnalizadorSintactico$parser,
                               CUP$AnalizadorSintactico$stack,
                               CUP$AnalizadorSintactico$top);
    }
}

