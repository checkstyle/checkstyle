////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
header {
package com.puppycrawl.tools.checkstyle;
}

/**
 * Java 1.2 AST Recognizer Grammar
 *
 * Based heavily on the Grammer example that comes with ANTLR. See
 * http://www.antlr.org.
 *
 */

class GeneratedJavaTreeParser extends TreeParser;

options {
	importVocab = GeneratedJava;
	ASTLabelType=MyCommonAST;
}

{
   Verifier ver = VerifierSingleton.getInstance();
   // Used in primaryExpression
   MyCommonAST firstExprIdent = null;
}

compilationUnit
	:	(packageDefinition)?
		(importDefinition)*
		(typeDefinition)*
	;

packageDefinition
{ LineText nm; }
   : #( PACKAGE_DEF nm=identifier )
   ;

importDefinition
	:	#( IMPORT identifierStar )
	;

typeDefinition
{
   MyModifierSet mods;
}
   :  #(CLASS_DEF mods=modifiers IDENT extendsClause implementsClause objBlock)
   |  #(INTERFACE_DEF mods=modifiers IDENT extendsClause interfaceBlock)
   ;

typeSpec
	:	#(TYPE typeSpecArray)
	;

typeSpecArray
	:	#( ARRAY_DECLARATOR typeSpecArray )
	|	type
	;

type
{ Object ignore;}
  	:	ignore=identifier
	|	builtInType
	;

builtInType
    :   "void"
    |   "boolean"
    |   "byte"
    |   "char"
    |   "short"
    |   "int"
    |   "float"
    |   "long"
    |   "double"
    ;

modifiers returns [MyModifierSet r]
{
   r = new MyModifierSet();
}
   :  #( MODIFIERS (m:modifier { r.addModifier(m);} )* )
   ;

modifier
    :   "private"
    |   "public"
    |   "protected"
    |   "static"
    |   "transient"
    |   "final"
    |   "abstract"
    |   "native"
    |   "threadsafe"
    |   "synchronized"
    |   "const"
    |   "volatile"
	|	"strictfp"
    ;

extendsClause
{ Object ignore;}
	:	#(EXTENDS_CLAUSE (ignore=identifier)* )
	;

implementsClause
{ Object ignore;}
	:	#(IMPLEMENTS_CLAUSE (ignore=identifier)* )
	;


interfaceBlock
{
   MyVariable var;
}
   :  #(OBJBLOCK
         (  methodDecl
         |  var=variableDef
         |  typeDefinition    // OJB ADDED THIS RULE
         )*
      )
   ;

objBlock
{
   MyVariable var;
}

   :  #(OBJBLOCK
         (ctorDef
         |  methodDef
         |  var=variableDef
         |  typeDefinition
         |  #(STATIC_INIT slist)
         |  #(INSTANCE_INIT slist)
         )*
       )
   ;

ctorDef
{
   MyModifierSet mods;
   MethodSignature ms;
}
   :  #(CTOR_DEF mods=modifiers ms=methodHead ctorSList )
   ;

methodDecl
{
   MyModifierSet mods;
   MethodSignature ms;
}
   :  #(METHOD_DEF mods=modifiers ts:typeSpec ms=methodHead)
   ;

methodDef
{
   MyModifierSet mods;
   MethodSignature ms;
}
   :  #(METHOD_DEF mods=modifiers ts:typeSpec ms=methodHead (slist)? )
   ;

variableDef returns [MyVariable r]
{
   MyModifierSet mods;
   LineText name;
   r = null;
}
   :  #(VARIABLE_DEF mods=modifiers typeSpec name=variableDeclarator varInitializer)
      {
         r = new MyVariable(name, mods);
      }
   ;

parameterDef returns [LineText r]
{
   r = null;
   Object ignore;
}
   :  #(PARAMETER_DEF ignore=modifiers typeSpec ii:IDENT )
      {
         r = new LineText(ii.getLineNo(), ii.getText());
      }
   ;

objectinitializer
	:	#(INSTANCE_INIT slist)
	;

variableDeclarator returns [LineText r]
{
   Object ignore;
   r = null;
}
   :  ii:IDENT
      {
         r = new LineText(ii.getLineNo(), ii.getText());
      }
   |  LBRACK ignore=variableDeclarator
      {
         // Not sure how to handle this?
         System.out.println("Report this breakage to Oliver. " +
                            "Remember the input file!");
         System.exit(1);
      }
   ;

varInitializer
   :  #(ASSIGN initializer)
   |
   ;

initializer
	:	expression
	|	arrayInitializer
	;

arrayInitializer
	:	#(ARRAY_INIT (initializer)*)
	;

methodHead returns [MethodSignature r]
{
   r = new MethodSignature();
   java.util.List throwsList = new java.util.ArrayList(); // emtpy list
   LineText p = null;
}
   :  ii:IDENT #( PARAMETERS (p=parameterDef )* ) (throwsList=throwsClause)?
      {
         r.setLineNo(ii.getLineNo());
         r.setThrows(throwsList);
      }
   ;

throwsClause returns [java.util.List r]
{
   LineText name;
   r = new java.util.ArrayList();
}
	:	#( "throws" (name=identifier { r.add(name); } )* )
	;

identifier returns [LineText r]
{
   r = null;
   LineText a;
}
   :  i:IDENT
      {
         r = new LineText(i.getLineNo(), i.getText());
      }
   |  #( DOT a=identifier b:IDENT )
      {
         r = new LineText(a);
         r.appendText("." + b.getText());
      }
   ;

identifierStar
{ Object ignore; }
   :  IDENT
   |  #( DOT ignore=identifier (STAR|IDENT) )
   ;

ctorSList
	:	#( SLIST (ctorCall)? (stat)* )
	;

slist
	:	#( SLIST (stat)* )
	;

stat
{
   Object ignore;
}
   :  typeDefinition
   |  ignore=variableDef
   |  expression
   |  #(LABELED_STAT IDENT stat)
   |  #(ii:"if" expression is1:stat (is2:stat)? )
      {
         ver.verifyLeftCurly(is1.getText(), false, "if", ii.getLineNo());
         ver.verifySurroundingWS(is1);
         if (is2 != null) {
            ver.verifyLeftCurly(is2.getText(), true, "else", ii.getLineNo());
            ver.verifySurroundingWS(is2);
         }
      }
   |  #(ff:"for"
           #(FOR_INIT (ignore=variableDef | elist)?)
           #(FOR_CONDITION (expression)?)
           #(FOR_ITERATOR (elist)?)
           fs:stat
           {
              ver.verifyLeftCurly(fs.getText(), false, "for", ff.getLineNo());
              ver.verifySurroundingWS(fs);
           }
       )
   |  #(ww:"while" expression ws:stat)
      {
         ver.verifyLeftCurly(ws.getText(), false, "while", ww.getLineNo());
         ver.verifySurroundingWS(ws);
      }
   |  #(dd:"do" ds:stat expression)
      {
         ver.verifyLeftCurly(ds.getText(), false, "do", dd.getLineNo());
         ver.verifySurroundingWS(ds);
      }
   |  #("break" (IDENT)? )
   |  #("continue" (IDENT)? )
   |  #(rr:"return" (ee:expression)? )
   |  #("switch" expression (caseGroup)*)
   |  #("throw" expression)
   |  #(ss:"synchronized" expression stat)
   |  tryBlock
   |  slist // nested SLIST
   |  EMPTY_STAT
   ;

caseGroup
	:	#(CASE_GROUP (#("case" expression) | "default")+ slist)
	;

tryBlock
	:	#( "try" slist (handler)* (#("finally" slist))? )
	;

handler
{ Object ignore;}
	:	#( cc:"catch" ignore=parameterDef slist )
	;

elist
	:	#( ELIST (expression)* )
	;

expression
	:	#(EXPR expr)
	;

expr
   :  #(QUESTION expr expr COLON expr)
   |  #(ASSIGN expr expr)
   |  #(PLUS_ASSIGN expr expr)
   |  #(MINUS_ASSIGN expr expr)
   |  #(STAR_ASSIGN expr expr)
   |  #(DIV_ASSIGN expr expr)
   |  #(MOD_ASSIGN expr expr)
   |  #(SR_ASSIGN expr expr)
   |  #(BSR_ASSIGN expr expr)
   |  #(SL_ASSIGN expr expr)
   |  #(BAND_ASSIGN expr expr)
   |  #(BXOR_ASSIGN expr expr)
   |  #(BOR_ASSIGN expr expr)
   |  #(LOR expr expr)
   |  #(LAND expr expr)
   |  #(BOR expr expr)
   |  #(BXOR expr expr)
   |  #(BAND expr expr)
   |  #(NOT_EQUAL expr expr)
   |  #(EQUAL expr expr)
   |  #(LT expr expr)
   |  #(GT expr expr)
   |  #(LE expr expr)
   |  #(GE expr expr)
   |  #(SL expr expr)
   |  #(SR expr expr)
   |  #(BSR expr expr)
   |  #(PLUS expr expr)
   |  #(MINUS expr expr)
   |  #(DIV expr expr)
   |  #(MOD expr expr)
   |  #(STAR expr expr)
   |  #(INC expr) { ver.verifyNoWSAfter(#INC); }
   |  #(DEC expr) { ver.verifyNoWSAfter(#DEC); }
   |  #(POST_INC expr) { ver.verifyNoWSBefore(#POST_INC); }
   |  #(POST_DEC expr) { ver.verifyNoWSBefore(#POST_DEC); }
   |  #(BNOT expr) { ver.verifyNoWSAfter(#BNOT); }
   |  #(LNOT expr) { ver.verifyNoWSAfter(#LNOT); }
   |  #("instanceof" expr expr) // Java ensures surrounded by WS!
   |  #(UNARY_MINUS expr) { ver.verifyNoWSAfter(#UNARY_MINUS); }
   |  #(UNARY_PLUS expr) { ver.verifyNoWSAfter(#UNARY_PLUS); }
   |  primaryExpression
   ;

primaryExpression
    :   IDENT { firstExprIdent = #IDENT; ver.reportIdentifier(#IDENT); }
    |   #( DOT
            (expr
                (i2:IDENT { firstExprIdent = null;}
                | arrayIndex
                | "this"
                | "class" { if (firstExprIdent != null) { ver.reportReference(firstExprIdent.getText()); } }
                |  #( "new" IDENT elist )
				|   "super"
                )
            | #(ARRAY_DECLARATOR typeSpecArray)
            | builtInType ("class")?
            )
        )
    | arrayIndex
    | #(METHOD_CALL primaryExpression elist)
    | #(TYPECAST typeSpec expr)
    | newExpression
    | constant
    | "super"
    | "true"
    | "false"
    | "this"
    | "null"
    | typeSpec // type name used with instanceof
    ;

ctorCall
	:	#( CTOR_CALL elist )
	|	#( SUPER_CTOR_CALL
			(	elist
			|	primaryExpression elist
			)
		 )
	;

arrayIndex
	:	#(INDEX_OP primaryExpression expression)
	;

constant
    :   NUM_INT
    |   CHAR_LITERAL
    |   STRING_LITERAL
    |   NUM_FLOAT
    |   NUM_DOUBLE
    |   NUM_LONG
    ;

newExpression
	:	#(	"new" type
			(	newArrayDeclarator (arrayInitializer)?
			|	elist ({ver.reportStartTypeBlock(Scope.ANONINNER, false);} objBlock {ver.reportEndTypeBlock();})?
			)
		)
			
	;

newArrayDeclarator
	:	#( ARRAY_DECLARATOR (newArrayDeclarator)? (expression)? )
	;
