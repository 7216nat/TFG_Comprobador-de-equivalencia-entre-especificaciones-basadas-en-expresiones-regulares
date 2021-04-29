package lexico;

import errores.ErrorAnalizador;

%%
%cup
%line
%column
%class AnalizadorLexico
%type  UnidadLexica
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

ignorada = [ \t\r\b\n]
def = def
aux = aux
var = \< [^>]* \>
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

PApSim = \\\(
PCieSim = \\\)
AnApSim = \\\<
AnCieSim = \\\>
CorApSim = \\\[
CorCieSim = \\\]
Asterisco = \\\*
Mas = \\\+
Barra = \\\|
Interr = \\\?
IgualSim = \\\=
GuionSim = \\\- 
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

{PApSim}                  {return ops.unidadPApSim();}
{PCieSim}                 {return ops.unidadPCieSim();}
{AnApSim}                 {return ops.unidadAnApSim();}
{AnCieSim}                {return ops.unidadAnCieSim();}
{CorApSim}                {return ops.unidadCorcApSim();}
{CorCieSim}               {return ops.unidadCorCieSim();}
{Asterisco}               {return ops.unidadAsterisco();}
{Mas}                     {return ops.unidadMas();}
{Barra}                   {return ops.unidadBarra();}
{Interr}                  {return ops.unidadInterr();}
{IgualSim}                {return ops.unidadIgualSim();}
{GuionSim}                {return ops.unidadGuionSim();}
{SimEscape}               {return ops.unidadSimEscape();}
{Simbolo}                 {return ops.unidadSimbolo();}
[^]                       {ops.error();}