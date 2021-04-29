package lexico;

import errores.ErrorAnalizador;

%%
%cup
%line
%column
%class AnalizadorLexico
%type  UnidadLexica
%public
%unicode

%{
  private AnOperations ops;
  private ErrorAnalizador errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(ErrorAnalizador errores){
    this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new AnOperations(this);
%init}

letra = ([A-Z]|[a-z])
ignorada = [ \t\r\b\n]
def = def
aux = aux
var = \<{letra}{letra}*\>
PAp = \(
PCie = \)
CorAp = \[
CorCie = \]
Kleen = \*
KleenPos = \+
Union = \|
Posibilidad = \?
Igual = \=
Guion = \-
Coma = \,
PuntoComa = \;
SimEscape = \\[^]
Simbolo = [^]

%%
{ignorada}                {}
{def}                     {return ops.unidadDef();}
{aux}                     {return ops.unidadAux();}
{var}                     {return ops.unidadVar();}
{PAp}                     {return ops.unidadPAP();}
{PCie}                    {return ops.unidadPCie();}
{CorAp}                   {return ops.unidadCorAp();}
{CorCie}                  {return ops.unidadCorCie();}
{Kleen}                   {return ops.unidadKleen();}
{KleenPos}                {return ops.unidadKleenPos();}
{Union}                   {return ops.unidadUnion();}
{Posibilidad}             {return ops.unidadPosibilidad();}
{Igual}                   {return ops.unidadIgual();}
{Guion}                   {return ops.unidadGuion();}
{Coma}                    {return ops.unidadComa();}
{PuntoComa}               {return ops.unidadPuntoComa();}
{SimEscape}               {return ops.unidadSimEscape();}
{Simbolo}                 {return ops.unidadSimbolo();}
[^]                       {ops.errorLexico(fila(),columna(),lexema());}