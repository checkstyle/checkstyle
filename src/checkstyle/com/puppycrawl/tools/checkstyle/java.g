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


/** Java 1.2 Recognizer
 *
 * Based heavily on the Grammer example that comes with ANTLR. See
 * http://www.antlr.org.
 *
 * BUG:
 * 		Doesn't like boolean.class!
 *
 * class Test {
 *   public static void main( String args[] ) {
 *     if (boolean.class.equals(boolean.class)) {
 *       System.out.println("works");
 *     }
 *   }
 * }
 */
class GeneratedJavaRecognizer extends Parser;

options {
	ASTLabelType=MyCommonAST;
	k = 2;                           // two token lookahead
	exportVocab=GeneratedJava;       // Call its vocabulary "GeneratedJava"
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

tokens {
	BLOCK; MODIFIERS; OBJBLOCK; SLIST; CTOR_DEF; METHOD_DEF; VARIABLE_DEF;
	INSTANCE_INIT; STATIC_INIT; TYPE; CLASS_DEF; INTERFACE_DEF;
	PACKAGE_DEF; ARRAY_DECLARATOR; EXTENDS_CLAUSE; IMPLEMENTS_CLAUSE;
	PARAMETERS; PARAMETER_DEF; LABELED_STAT; TYPECAST; INDEX_OP;
	POST_INC; POST_DEC; METHOD_CALL; EXPR; ARRAY_INIT;
	IMPORT; UNARY_MINUS; UNARY_PLUS; CASE_GROUP; ELIST; FOR_INIT; FOR_CONDITION;
	FOR_ITERATOR; EMPTY_STAT; FINAL="final"; ABSTRACT="abstract";
	STRICTFP="strictfp"; SUPER_CTOR_CALL; CTOR_CALL;
}

{
    final Verifier ver = VerifierSingleton.getInstance();
    private static String sFirstIdent = "";
    private static final MyCommonAST[] sIgnoreAST = new MyCommonAST[2];
    private static LineText sLastIdentifier;
    private static final int[] sIgnoreType = new int[1];
    private static final boolean[] sIgnoreIsEmpty = new boolean[1];

    // Constants to indicate statement type. Numbers are delibrately weird
    private static int STMT_OTHER = 0;
    private static int STMT_COMPOUND = 6;
    private static int STMT_IF = 8;
}

// Compilation Unit: In Java, this is a single file.  This is the start
//   rule for this parser
compilationUnit
	:	// A compilation unit starts with an optional package definition
		(	packageDefinition
		|	/* nothing */
		)

		// Next we have a series of zero or more import statements
		( importDefinition )*

		// Wrapping things up with any number of class or interface
		//    definitions
		( typeDefinition )*

		EOF!
	;


// Package statement: "package" followed by an identifier.
packageDefinition
	options {defaultErrorHandler = true;} // let ANTLR handle errors
	:	p:"package"^ {#p.setType(PACKAGE_DEF);} identifier SEMI!
        { ver.reportPackageName(sLastIdentifier); }
	;


// Import statement: import followed by a package or class name
importDefinition
	options {defaultErrorHandler = true;}
	:	i:"import"^ {#i.setType(IMPORT);} identifierStar SEMI!
	;

// A type definition in a file is either a class or interface definition.
typeDefinition
	options {defaultErrorHandler = true;}
{ MyModifierSet modSet = new MyModifierSet(); }
	:	m:modifiers[modSet]!
		( classDefinition[#m, modSet]
		| interfaceDefinition[#m, modSet]
		)
	|	SEMI!
	;

/** A declaration is the creation of a reference or primitive-type variable
 *  Create a separate Type/Var tree for each var in the var list.
 */
declaration!
{ MyModifierSet modSet = new MyModifierSet(); }
	:	m:modifiers[modSet] t:typeSpec[false] v:variableDefinitions[#m,#t, modSet]
		{#declaration = #v;}
	;

// A type specification is a type name with possible brackets afterwards
//   (which would make it an array type).
typeSpec[boolean addImagNode]
	: classTypeSpec[addImagNode]
	| builtInTypeSpec[addImagNode]
	;

// A class type specification is a class type with possible brackets afterwards
//   (which would make it an array type).
classTypeSpec[boolean addImagNode]
	:	identifier (lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)*
		{
			if ( addImagNode ) {
				#classTypeSpec = #(#[TYPE,"TYPE"], #classTypeSpec);
			}
		}
	;

// A builtin type specification is a builtin type with possible brackets
// afterwards (which would make it an array type).
builtInTypeSpec[boolean addImagNode]
	:	builtInType (lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)*
		{
			if ( addImagNode ) {
				#builtInTypeSpec = #(#[TYPE,"TYPE"], #builtInTypeSpec);
			}
		}
	;

// A type name. which is either a (possibly qualified) class name or
//   a primitive (builtin) type
type
	:	identifier
	|	builtInType
	;

// The primitive types.
builtInType
	:	"void"
	|	"boolean"
	|	"byte"
	|	"char"
	|	"short"
	|	"int"
	|	"float"
	|	"long"
	|	"double"
	;

// A (possibly-qualified) java identifier.  We start with the first IDENT
//   and expand its name by adding dots and following IDENTS
identifier
	:	i1:IDENT
        {
            sLastIdentifier = new LineText(i1.getLine(), i1.getColumn(), i1.getText());
        }
        ( DOT^ i2:IDENT {sLastIdentifier.appendText("." + i2.getText());} )*
        {
        }
	;

identifierStar
{ int ln = 0; String str = ""; boolean star = false; }
	:	i1:IDENT {ln = i1.getLine(); str = i1.getText();}
		( DOT^ i2:IDENT {str += "." + i2.getText(); } )*
		( DOT^ i3:STAR {str += ".*"; star = true; } )?
{
 if (star) {
  ver.reportStarImport(ln, str);
 } else {
  ver.reportImport(ln, str);
 }
}
	;

// A list of zero or more modifiers.  We could have used (modifier)* in
//   place of a call to modifiers, but I thought it was a good idea to keep
//   this rule separate so they can easily be collected in a Vector if
//   someone so desires
modifiers[MyModifierSet mms]
	:	( m:modifier {mms.addModifier(#m);} )*
		{#modifiers = #([MODIFIERS, "MODIFIERS"], #modifiers);}
	;

// modifiers for Java classes, interfaces, class/instance vars and methods
modifier
	:	"private"
	|	"public"
	|	"protected"
	|	"static"
	|	"transient"
	|	"final"
	|	"abstract"
	|	"native"
	|	"threadsafe"
	|	"synchronized"
//	|	"const"			// reserved word, but not valid
	|	"volatile"
	|	"strictfp"
	;

// Definition of a Java class
classDefinition![MyCommonAST modifiers, MyModifierSet modSet]
	:	cc:"class" IDENT
		// it _might_ have a superclass...
		sc:superClassClause
		// it might implement some interfaces...
		ic:implementsClause
		// now parse the body of the class
        {
            ver.reportStartTypeBlock(modSet.getVisibilityScope(), false, #IDENT);
        }
		cb:classBlock[(modSet.size() == 0) ? #cc.getLineNo() : modSet.getFirstLineNo()]
		{#classDefinition = #(#[CLASS_DEF,"CLASS_DEF"],
							   modifiers,IDENT,sc,ic,cb);}
        {
            ver.reportEndTypeBlock(true);
        }
	;

superClassClause!
	:	( "extends" id:identifier )?
		{#superClassClause = #(#[EXTENDS_CLAUSE,"EXTENDS_CLAUSE"],id);}
	;

// Definition of a Java Interface
interfaceDefinition![MyCommonAST modifiers, MyModifierSet modSet]
	:	ii:"interface" IDENT
		// it might extend some other interfaces
		ie:interfaceExtends
		// now parse the body of the interface (looks like a class...)
        {
            ver.reportStartTypeBlock(modSet.getVisibilityScope(), true, #IDENT);
        }
		cb:classBlock[(modSet.size() == 0) ? #ii.getLineNo() : modSet.getFirstLineNo()]
		{#interfaceDefinition = #(#[INTERFACE_DEF,"INTERFACE_DEF"],
									modifiers,IDENT,ie,cb);}
        {
            ver.reportEndTypeBlock(true);
        }
	;


// This is the body of a class.  You can have fields and extra semicolons,
// That's about it (until you see what a field is...)
classBlock[int aLine]
	:	lc:LCURLY!
			( field | SEMI! )*
		RCURLY!
		{#classBlock = #([OBJBLOCK, "OBJBLOCK"], #classBlock);}
	;

// An interface can extend several other interfaces...
interfaceExtends
	:	(
		e:"extends"!
		identifier ( COMMA! identifier )*
		)?
		{#interfaceExtends = #(#[EXTENDS_CLAUSE,"EXTENDS_CLAUSE"],
							#interfaceExtends);}
	;

// A class can implement several interfaces...
implementsClause
	:	(
			i:"implements"! identifier ( COMMA! identifier )*
		)?
		{#implementsClause = #(#[IMPLEMENTS_CLAUSE,"IMPLEMENTS_CLAUSE"],
								 #implementsClause);}
	;

// Now the various things that can be defined inside a class or interface...
// Note that not all of these are really valid in an interface (constructors,
//   for example), and if this grammar were used for a compiler there would
//   need to be some semantic checks to make sure we're doing the right thing...
field!
{
    java.util.List exs = new java.util.ArrayList();
    MethodSignature msig = new MethodSignature();
    final MyCommonAST[] stmtBraces = new MyCommonAST[2];
}
	:	// method, constructor, or variable declaration
		mods:modifiers[msig.getModSet()]
		(	h:ctorHead[exs, msig]
            {
                msig.setThrows(exs);
                ver.verifyMethod(msig);
                ver.reportStartMethodBlock();
            }
            s:constructorBody[msig.getFirstLineNo()] // constructor
			{#field = #(#[CTOR_DEF,"CTOR_DEF"], mods, h, s);}
            {ver.reportEndMethodBlock();}

		|	cd:classDefinition[#mods, msig.getModSet()]       // inner class
			{#field = #cd;}

		|	id:interfaceDefinition[#mods, msig.getModSet()]   // inner interface
			{#field = #id;}

		|	t:typeSpec[false]  // method or variable declaration(s)
			(	IDENT  // the name of the method

				// parse the formal parameter declarations.
				LPAREN! param:parameterDeclarationList[msig] RPAREN!

				rt:declaratorBrackets[#t]

				// get the list of exceptions that this method is
				// declared to throw
				(tc:throwsClause[exs])?

                {
                    msig.setName(#IDENT);
                    msig.setThrows(exs);
                    msig.setReturnType(#t);
                    ver.verifyMethod(msig);
                    ver.reportStartMethodBlock();
                }
				(
                    s2:compoundStatement[stmtBraces, sIgnoreIsEmpty]
                    {
                    }
                |
                    SEMI
                )
				{#field = #(#[METHOD_DEF,"METHOD_DEF"],
						     mods,
							 #(#[TYPE,"TYPE"],rt),
							 IDENT,
							 param,
							 tc,
							 s2); }
                {ver.reportEndMethodBlock();}
			|	v:variableDefinitions[#mods,#t, msig.getModSet()] SEMI
				{#field = #v;}
			)
		)

    // "static { ... }" class initializer
	|	st:"static" {ver.reportStartMethodBlock();} s3:compoundStatement[stmtBraces, sIgnoreIsEmpty]
        {
            #field = #(#[STATIC_INIT,"STATIC_INIT"], s3);
            ver.reportEndMethodBlock();
        }

    // "{ ... }" instance initializer
	|	{ver.reportStartMethodBlock();} s4:compoundStatement[sIgnoreAST, sIgnoreIsEmpty] {ver.reportEndMethodBlock();}
		{#field = #(#[INSTANCE_INIT,"INSTANCE_INIT"], s4);}
	;

constructorBody[int aLineNo]
    :   lc:LCURLY^ {#lc.setType(SLIST); }
		// Predicate might be slow but only checked once per constructor def
		// not for general methods.
		(	(explicitConstructorInvocation) => explicitConstructorInvocation
		|
		)
        (statement[sIgnoreType, sIgnoreAST])*
        rc:RCURLY!
    ;

explicitConstructorInvocation
    :   (	options {
				// this/super can begin a primaryExpression too; with finite
				// lookahead ANTLR will think the 3rd alternative conflicts
				// with 1, 2.  I am shutting off warning since ANTLR resolves
				// the nondeterminism by correctly matching alts 1 or 2 when
				// it sees this( or super(
				generateAmbigWarnings=false;
			}
		:	"this"! lp1:LPAREN^ argList RPAREN! SEMI!
			{#lp1.setType(CTOR_CALL);}

	    |   "super"! lp2:LPAREN^ argList RPAREN! SEMI!
			{#lp2.setType(SUPER_CTOR_CALL);}

			// (new Outer()).super()  (create enclosing instance)
		|	primaryExpression DOT! "super"! lp3:LPAREN^ argList RPAREN! SEMI!
			{#lp3.setType(SUPER_CTOR_CALL);}
		)
    ;

variableDefinitions[MyCommonAST mods, MyCommonAST t, MyModifierSet modSet]
	:	variableDeclarator[(MyCommonAST) getASTFactory().dupTree(mods),
						   (MyCommonAST) getASTFactory().dupTree(t),
                           modSet]
		(	COMMA!
			variableDeclarator[(MyCommonAST) getASTFactory().dupTree(mods),
							   (MyCommonAST) getASTFactory().dupTree(t),
                               modSet]
		)*
	;

/** Declaration of a variable.  This can be a class/instance variable,
 *   or a local variable in a method
 * It can also include possible initialization.
 */
variableDeclarator![MyCommonAST mods, MyCommonAST t, MyModifierSet modSet]
	:	id:IDENT d:declaratorBrackets[t] v:varInitializer
		{#variableDeclarator = #(#[VARIABLE_DEF,"VARIABLE_DEF"], mods, #(#[TYPE,"TYPE"],d), id, v);}
        {
            ver.verifyVariable(new MyVariable(new LineText(id.getLine(), id.getColumn(), id.getText()), modSet));
        }
	;

declaratorBrackets[MyCommonAST typ]
	:	{#declaratorBrackets=typ;}
		(lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)*
	;

varInitializer
	:	( ASSIGN^ initializer )?
	;

// This is an initializer used to set up an array.
arrayInitializer
	:	lc:LCURLY^ {#lc.setType(ARRAY_INIT);}
			(	initializer
				(
					// CONFLICT: does a COMMA after an initializer start a new
					//           initializer or start the option ',' at end?
					//           ANTLR generates proper code by matching
					//			 the comma as soon as possible.
					options {
						warnWhenFollowAmbig = false;
					}
				:
					COMMA! initializer
				)*
				(COMMA!)?
			)?
		RCURLY!
	;


// The two "things" that can initialize an array element are an expression
//   and another (nested) array initializer.
initializer
	:	expression
	|	arrayInitializer
	;

// This is the header of a method.  It includes the name and parameters
//   for the method.
//   This also watches for a list of exception classes in a "throws" clause.
ctorHead[java.util.List exs, MethodSignature msig]
	:	IDENT  { msig.setName(#IDENT); } // the name of the method

		// parse the formal parameter declarations.
		LPAREN! parameterDeclarationList[msig] RPAREN!

		// get the list of exceptions that this method is declared to throw
		(throwsClause[exs])?
	;

// This is a list of exception classes that the method is declared to throw
throwsClause[java.util.List exs]
	:	"throws"^ i1:identifier { exs.add(sLastIdentifier); }
        ( COMMA! i2:identifier { exs.add(sLastIdentifier); } )*
	;


// A list of formal parameters
parameterDeclarationList[MethodSignature msig]
	:	( parameterDeclaration[msig] ( COMMA! parameterDeclaration[msig] )* )?
		{#parameterDeclarationList = #(#[PARAMETERS,"PARAMETERS"],
									#parameterDeclarationList);}
	;

// A formal parameter.
parameterDeclaration![MethodSignature msig]
	:	pm:parameterModifier t:typeSpec[false] id:IDENT
		pd:declaratorBrackets[#t]
		{#parameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"],
									pm, #([TYPE,"TYPE"],pd), id);}
        {
            msig.addParam(new LineText(#id.getLineNo(), #id.getColumnNo(), #id.getText()));
        }
	;

parameterModifier
	:	(f:"final")?
		{#parameterModifier = #(#[MODIFIERS,"MODIFIERS"], f);}
	;

// Compound statement.  This is used in many contexts:
//   Inside a class definition prefixed with "static":
//      it is a class initializer
//   Inside a class definition without "static":
//      it is an instance initializer
//   As the body of a method
//   As a completely indepdent braced block of code inside a method
//      it starts a new scope for variable definitions

compoundStatement[MyCommonAST[] aCurlies, boolean[] aIsEmpty]
{
    aIsEmpty[0] = true;
}
	:	lc:LCURLY^  {#lc.setType(SLIST); aCurlies[0] = #lc;}
			// include the (possibly-empty) list of statements
			(statement[sIgnoreType, sIgnoreAST] {aIsEmpty[0] = false;})*
		rc:RCURLY!  { aCurlies[1] = #rc; }
	;


// This production provides a slot for adding additional statement productions.
// It is used to simplify an inherited grammar that includes assert statements
// (new Java language feature in JDK 1.4)
statement[int[] aType, MyCommonAST[] aCurlies]
	:	traditionalStatement[aType, aCurlies]
	;

// a traditional (JDK < 1.4) java statement, assert keyword is not allowed
traditionalStatement[int[] aType, MyCommonAST[] aCurlies]
{
    final MyModifierSet modSet = new MyModifierSet();
    final int[] stmtType = new int[1];
    final MyCommonAST[] stmtBraces = new MyCommonAST[2];
    stmtType[0] = STMT_OTHER;
}
	// A list of statements in curly braces -- start a new scope!
	:	compoundStatement[aCurlies, sIgnoreIsEmpty] { aType[0] = STMT_COMPOUND; }

	// declarations are ambiguous with "ID DOT" relative to expression
	// statements.  Must backtrack to be sure.  Could use a semantic
	// predicate to test symbol table to see what the type was coming
	// up, but that's pretty hard without a symbol table ;)
	|	(declaration)=> declaration SEMI!

	// An expression statement.  This could be a method call,
	// assignment statement, or any other expression evaluated for
	// side-effects.
	|	expression SEMI!

	// class definition
	|	m:modifiers[modSet]! classDefinition[#m, modSet]

	// Attach a label to the front of a statement
	|	IDENT c:COLON^ {#c.setType(LABELED_STAT);} statement[sIgnoreType, sIgnoreAST]

    // If-else statement
	|	ii:"if"^ LPAREN! expression RPAREN! statement[stmtType, stmtBraces]
        {
            aType[0] = STMT_IF;
        }
		(
			// CONFLICT: the old "dangling-else" problem...
			//           ANTLR generates proper code matching
			//			 as soon as possible.  Hush warning.
			options {
				warnWhenFollowAmbig = false;
			}
		:
			ee:"else"!
            {
                stmtType[0] = STMT_OTHER;
            }
            statement[stmtType, stmtBraces]
            {
            }
		)?

	// For statement
	|	ff:"for"^
        LPAREN!
        forInit s1:SEMI! // initializer
        forCond	s2:SEMI! // condition test
        forIter         // updater
        RPAREN!
        statement[stmtType, stmtBraces] // statement to loop over
        {
            ver.verifyWSAfter(s1.getLine(), s1.getColumn(), MyToken.SEMI_COLON);
            ver.verifyWSAfter(s2.getLine(), s2.getColumn(), MyToken.SEMI_COLON, ")");
        }

	// While statement
	|	ww:"while"^ LPAREN! expression RPAREN! statement[stmtType, stmtBraces]
        {
        }

	// do-while statement
	|	dd:"do"^ statement[stmtType, stmtBraces] dw:"while"! LPAREN! expression RPAREN! SEMI!
        {
        }

	// get out of a loop (or switch)
	|	"break"^ (IDENT)? SEMI!

	// do next iteration of a loop
	|	"continue"^ (IDENT)? SEMI!

	// Return an expression
	|	rr:"return"^ (expression  )? SEMI!

	// switch/case statement
	|	ss2:"switch"^ LPAREN! expression RPAREN! lc:LCURLY!
			( casesGroup )*
		RCURLY!

	// exception try-catch block
	|	tryBlock

	// throw an exception
	|	"throw"^ expression SEMI!

	// synchronize a statement
	|	ss:"synchronized"^ LPAREN! expression RPAREN! compoundStatement[stmtBraces, sIgnoreIsEmpty]
        {
        }

	// empty statement
	|	s:SEMI {#s.setType(EMPTY_STAT);}
	;


casesGroup
	:	(	// CONFLICT: to which case group do the statements bind?
			//           ANTLR generates proper code: it groups the
			//           many "case"/"default" labels together then
			//           follows them with the statements
			options {
				warnWhenFollowAmbig = false;
			}
			:
			aCase
		)+
		caseSList
		{#casesGroup = #([CASE_GROUP, "CASE_GROUP"], #casesGroup);}
	;

aCase
	:	("case"^ expression | "default") COLON!
	;

caseSList
	:	(statement[sIgnoreType, sIgnoreAST])*
		{#caseSList = #(#[SLIST,"SLIST"],#caseSList);}
	;

// The initializer for a for loop
forInit
		// if it looks like a declaration, it is
	:	(	(declaration)=> declaration
		// otherwise it could be an expression list...
		|	expressionList
		)?
		{#forInit = #(#[FOR_INIT,"FOR_INIT"],#forInit);}
	;

forCond
	:	(expression)?
		{#forCond = #(#[FOR_CONDITION,"FOR_CONDITION"],#forCond);}
	;

forIter
	:	(expressionList)?
		{#forIter = #(#[FOR_ITERATOR,"FOR_ITERATOR"],#forIter);}
	;

// an exception handler try/catch block
tryBlock
{
    final MyCommonAST[] stmtBraces = new MyCommonAST[2];
    final MethodSignature ignoreMS = new MethodSignature();
    final boolean[] isEmpty = new boolean[1];
}
	:	t:"try"^ compoundStatement[stmtBraces, isEmpty]
        {
//            ver.reportTryBlock(stmtBraces, isEmpty[0]);
        }

        (
            c:"catch"^
            LPAREN! parameterDeclaration[ignoreMS] RPAREN! compoundStatement[stmtBraces, isEmpty]
            {
//                ver.reportCatchBlock(stmtBraces, isEmpty[0]);
            }
        )*

        (
            f:"finally"^
            compoundStatement[stmtBraces, isEmpty]
            {
//                ver.reportFinallyBlock(stmtBraces, isEmpty[0]);
            }
        )?
	;


// expressions
// Note that most of these expressions follow the pattern
//   thisLevelExpression :
//       nextHigherPrecedenceExpression
//           (OPERATOR nextHigherPrecedenceExpression)*
// which is a standard recursive definition for a parsing an expression.
// The operators in java have the following precedences:
//    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
//            (12)  ?:
//            (11)  ||
//            (10)  &&
//            ( 9)  |
//            ( 8)  ^
//            ( 7)  &
//            ( 6)  == !=
//            ( 5)  < <= > >=
//            ( 4)  << >>
//            ( 3)  +(binary) -(binary)
//            ( 2)  * / %
//            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
//                  []   () (method call)  . (dot -- identifier qualification)
//                  new   ()  (explicit parenthesis)
//
// the last two are not usually on a precedence chart; I put them in
// to point out that new has a higher precedence than '.', so you
// can validy use
//     new Frame().show()
//
// Note that the above precedence levels map to the rules below...
// Once you have a precedence chart, writing the appropriate rules as below
//   is usually very straightfoward



// the mother of all expressions
expression
	:	assignmentExpression
		{#expression = #(#[EXPR,"EXPR"],#expression);}
	;


// This is a list of expressions.
expressionList
	:	expression (COMMA! expression)*
		{#expressionList = #(#[ELIST,"ELIST"], expressionList);}
	;


// assignment expression (level 13)
assignmentExpression
	:	conditionalExpression
		(	(	ASSIGN^
            |   PLUS_ASSIGN^
            |   MINUS_ASSIGN^
            |   STAR_ASSIGN^
            |   DIV_ASSIGN^
            |   MOD_ASSIGN^
            |   SR_ASSIGN^
            |   BSR_ASSIGN^
            |   SL_ASSIGN^
            |   BAND_ASSIGN^
            |   BXOR_ASSIGN^
            |   BOR_ASSIGN^
            )
			assignmentExpression
		)?
	;


// conditional test (level 12)
conditionalExpression
	:	logicalOrExpression
		( QUESTION^ assignmentExpression c:COLON {ver.verifyOpBegin(c.getLine(), c.getColumn(), c.getText());} conditionalExpression )?
	;


// logical or (||)  (level 11)
logicalOrExpression
	:	logicalAndExpression (LOR^ logicalAndExpression)*
	;


// logical and (&&)  (level 10)
logicalAndExpression
	:	inclusiveOrExpression (LAND^ inclusiveOrExpression)*
	;


// bitwise or non-short-circuiting or (|)  (level 9)
inclusiveOrExpression
	:	exclusiveOrExpression (BOR^ exclusiveOrExpression)*
	;


// exclusive or (^)  (level 8)
exclusiveOrExpression
	:	andExpression (BXOR^ andExpression)*
	;


// bitwise or non-short-circuiting and (&)  (level 7)
andExpression
	:	equalityExpression (BAND^ equalityExpression)*
	;


// equality/inequality (==/!=) (level 6)
equalityExpression
	:	relationalExpression ((NOT_EQUAL^ | EQUAL^) relationalExpression)*
	;


// boolean relational expressions (level 5)
relationalExpression
	:	shiftExpression
		(	(	(	LT^
				|	GT^
				|	LE^
				|	GE^
				)
				shiftExpression
			)*
		|	"instanceof"^ typeSpec[true]
		)
	;


// bit shift expressions (level 4)
shiftExpression
	:	additiveExpression ((SL^ | SR^ | BSR^) additiveExpression)*
	;


// binary addition/subtraction (level 3)
additiveExpression
	:	multiplicativeExpression
        ((p:PLUS^ {ver.verifyOpBegin(p.getLine(), p.getColumn(), p.getText());}| m:MINUS^ {ver.verifyOpBegin(m.getLine(), m.getColumn(), m.getText());} )
            multiplicativeExpression)*
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression
	:	unaryExpression ((s:STAR^ {ver.verifyOpBegin(s.getLine(), s.getColumn(), s.getText());} | DIV^ | MOD^ )  unaryExpression)*
	;

unaryExpression
	:	INC^ unaryExpression {ver.verifyNoWSAfter(#INC);}
	|	DEC^ unaryExpression {ver.verifyNoWSAfter(#DEC);}
	|	MINUS^ {#MINUS.setType(UNARY_MINUS); ver.verifyNoWSAfter(#MINUS);} unaryExpression
	|	PLUS^  {#PLUS.setType(UNARY_PLUS); ver.verifyNoWSAfter(#PLUS);} unaryExpression
	|	unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus
	:	BNOT^ unaryExpression {ver.verifyNoWSAfter(#BNOT);}
	|	LNOT^ unaryExpression {ver.verifyNoWSAfter(#LNOT);}

	|	(	// subrule allows option to shut off warnings
			options {
				// "(int" ambig with postfixExpr due to lack of sequence
				// info in linear approximate LL(k).  It's ok.  Shut up.
				generateAmbigWarnings=false;
			}
		:	// If typecast is built in type, must be numeric operand
			// Also, no reason to backtrack if type keyword like int, float...
			lpb:LPAREN^ {#lpb.setType(TYPECAST);} builtInTypeSpec[true] rpb:RPAREN! {ver.verifyWSAfter(rpb.getLine(), rpb.getColumn(), MyToken.CAST);}
			unaryExpression

			// Have to backtrack to see if operator follows.  If no operator
			// follows, it's a typecast.  No semantic checking needed to parse.
			// if it _looks_ like a cast, it _is_ a cast; else it's a "(expr)"
		|	(LPAREN classTypeSpec[true] RPAREN unaryExpressionNotPlusMinus)=>
			lp:LPAREN^ {#lp.setType(TYPECAST);} classTypeSpec[true] rpb2:RPAREN! {ver.verifyWSAfter(rpb2.getLine(), rpb2.getColumn(), MyToken.CAST);}
			unaryExpressionNotPlusMinus

		|	postfixExpression
		)
	;

// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
	:	primaryExpression // start with a primary

		(	// qualified id (id.id.id.id...) -- build the name
			DOT^ ( i1:IDENT {sLastIdentifier.appendText("." + i1.getText()); }
				| "this"
				| "class"
				| newExpression
				| "super" // ClassName.super.field
				)
			// the above line needs a semantic check to make sure "class"
			// is the _last_ qualifier.

			// allow ClassName[].class
		|	( lbc:LBRACK^ {#lbc.setType(ARRAY_DECLARATOR); } RBRACK! )+
			DOT^ "class"

			// an array indexing operation
		|	lb:LBRACK^ {#lb.setType(INDEX_OP);} expression RBRACK!

			// method invocation
			// The next line is not strictly proper; it allows x(3)(4) or
			//  x[2](4) which are not valid in Java.  If this grammar were used
			//  to validate a Java program a semantic check would be needed, or
			//   this rule would get really ugly...
			// It also allows ctor invocation like super(3) which is now
			// handled by the explicit constructor rule, but it would
			// be hard to syntactically prevent ctor calls here
		|	lp:LPAREN^ {#lp.setType(METHOD_CALL);}
				argList
			RPAREN!
		)*

		// possibly add on a post-increment or post-decrement.
		// allows INC/DEC on too much, but semantics can check
		(	in:INC^ {#in.setType(POST_INC); ver.verifyNoWSBefore(#in);}
	 	|	de:DEC^ {#de.setType(POST_DEC); ver.verifyNoWSBefore(#de);}
		|	// nothing
		)
	;

// the basic element of an expression
primaryExpression
	:	i1:IDENT
        {
            sFirstIdent = i1.getText();
            sLastIdentifier = new LineText(i1.getLine(), i1.getColumn(), i1.getText());
        }
	|	constant
	|	"true"
	|	"false"
	|	"this"
	|	"null"
	|	newExpression
	|	LPAREN! assignmentExpression RPAREN!
	|	"super"
		// look for int.class and int[].class
	|	builtInType
		( lbt:LBRACK^ {#lbt.setType(ARRAY_DECLARATOR);} RBRACK! )*
		DOT^ "class"
	;

/** object instantiation.
 *  Trees are built as illustrated by the following input/tree pairs:
 *
 *  new T()
 *
 *  new
 *   |
 *   T --  ELIST
 *           |
 *          arg1 -- arg2 -- .. -- argn
 *
 *  new int[]
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *
 *  new int[] {1,2}
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR -- ARRAY_INIT
 *                                  |
 *                                EXPR -- EXPR
 *                                  |      |
 *                                  1      2
 *
 *  new int[3]
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *                |
 *              EXPR
 *                |
 *                3
 *
 *  new int[1][2]
 *
 *  new
 *   |
 *  int -- ARRAY_DECLARATOR
 *               |
 *         ARRAY_DECLARATOR -- EXPR
 *               |              |
 *             EXPR             1
 *               |
 *               2
 *
 */
newExpression
	:	n:"new"^ t:type
		(	LPAREN! argList RPAREN! ({ver.reportStartTypeBlock(Scope.ANONINNER, false, null);}  classBlock[-1]  {ver.reportEndTypeBlock(false);})?

			//java 1.1
			// Note: This will allow bad constructs like
			//    new int[4][][3] {exp,exp}.
			//    There needs to be a semantic check here...
			// to make sure:
			//   a) [ expr ] and [ ] are not mixed
			//   b) [ expr ] and an init are not used together

		|	newArrayDeclarator (arrayInitializer)?
		)
	;

argList
	:	(	expressionList
		|	/*nothing*/
			{#argList = #[ELIST,"ELIST"];}
		)
	;

newArrayDeclarator
	:	(
			// CONFLICT:
			// newExpression is a primaryExpression which can be
			// followed by an array index reference.  This is ok,
			// as the generated code will stay in this loop as
			// long as it sees an LBRACK (proper behavior)
			options {
				warnWhenFollowAmbig = false;
			}
		:
			lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);}
				(expression)?
			RBRACK!
		)+
	;

constant
	:	NUM_INT
	|	CHAR_LITERAL
	|	STRING_LITERAL
	|	NUM_FLOAT
	|	NUM_LONG
	|	NUM_DOUBLE
	;


//----------------------------------------------------------------------------
// The Java scanner
//----------------------------------------------------------------------------
class GeneratedJavaLexer extends Lexer;

options {
	exportVocab=GeneratedJava; // call the vocabulary "GeneratedJava"
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\uFFFF';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	// bottom of JavaLexer.java
	codeGenBitsetTestThreshold=20;
}

{
    final Verifier ver = VerifierSingleton.getInstance();

    // explicitly set tab width to 1 (default in ANTLR 2.7.1)
    // in ANTLR 2.7.2a2 the default has changed from 1 to 8
    public void tab()
    {
        setColumn( getColumn() + 1 );
    }
}

// OPERATORS
QUESTION     : '?' {ver.verifyOpEnd(getLine(), getColumn(), "?");} ;
LPAREN   : '(';
RPAREN   : ')';
LBRACK   : '['  ;
RBRACK   : ']'  ;
LCURLY   : '{'  ;
RCURLY   : '}'  ;
COLON        : ':' ;
COMMA        : ',' {ver.verifyWSAfter(getLine(), getColumn() - 1, MyToken.COMMA);} ;
ASSIGN       : '=';
EQUAL        : "==" {ver.verifyOpEnd(getLine(), getColumn(), "==");} ;
LNOT         : '!' ; // check above
BNOT         : '~' ; // check above
NOT_EQUAL    : "!=" {ver.verifyOpEnd(getLine(), getColumn(), "!=");};
DIV          : '/' {ver.verifyOpEnd(getLine(), getColumn(), "/");} ;
DIV_ASSIGN   : "/=";
PLUS         : '+'; // must handle the UNARY_PLUS
PLUS_ASSIGN  : "+=";
INC          : "++" ; // must handle POST_INC
MINUS        : '-' ; // must handle the UNARY_MINUS
MINUS_ASSIGN : "-=";
DEC          : "--" ; // must handle POST_DEC
STAR         : '*' ; // star is special cause it can be used in imports.
STAR_ASSIGN  : "*=";
MOD          : '%' {ver.verifyOpEnd(getLine(), getColumn(), "%");} ;
MOD_ASSIGN   : "%=";
SR           : ">>" {ver.verifyOpEnd(getLine(), getColumn(), ">>");};
SR_ASSIGN    : ">>=";
BSR          : ">>>" {ver.verifyOpEnd(getLine(), getColumn(), ">>>");};
BSR_ASSIGN   : ">>>=";
GE           : ">=" {ver.verifyOpEnd(getLine(), getColumn(), ">=");};
GT           : ">" {ver.verifyOpEnd(getLine(), getColumn(), ">");};
SL           : "<<" {ver.verifyOpEnd(getLine(), getColumn(), "<<");};
SL_ASSIGN    : "<<=";
LE           : "<=" {ver.verifyOpEnd(getLine(), getColumn(), "<=");};
LT           : '<' {ver.verifyOpEnd(getLine(), getColumn(), "<");};
BXOR         : '^' {ver.verifyOpEnd(getLine(), getColumn(), "^");};
BXOR_ASSIGN  : "^=";
BOR          : '|' {ver.verifyOpEnd(getLine(), getColumn(), "|" );};
BOR_ASSIGN   : "|=";
LOR          : "||" {ver.verifyOpEnd(getLine(), getColumn(), "||");};
BAND         : '&' {ver.verifyOpEnd(getLine(), getColumn(), "&");};
BAND_ASSIGN  : "&=";
LAND         : "&&" {ver.verifyOpEnd(getLine(), getColumn(), "&&");};
SEMI   : ';'  ;


// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;

// Single-line comments
SL_COMMENT
   :  "//" { ver.reportCPPComment(getLine(), getColumn() - 3); }
      (~('\n'|'\r'))* ('\n'|'\r'('\n')?)
      {
         $setType(Token.SKIP);
         newline();
      }
   ;

// multiple-line comments
ML_COMMENT
{
   int startLine;
   int startCol;
}
   :  "/*"  { startLine = getLine(); startCol = getColumn() - 3; }
      (/* '\r' '\n' can be matched in one alternative or by matching
          '\r' in one iteration and '\n' in another.  I am trying to
          handle any flavor of newline that comes in, but the language
          that allows both "\r\n" and "\r" and "\n" to all be valid
          newline is ambiguous.  Consequently, the resulting grammar
          must be ambiguous.  I'm shutting this warning off.
       */
       options {
          generateAmbigWarnings=false;
       }
       :
          { LA(2)!='/' }? '*'
          | '\r' '\n'  {newline();}
          | '\r'       {newline();}
          | '\n'       {newline();}
          | ~('*'|'\n'|'\r')
      )*
      "*/"
      {
         ver.reportCComment(startLine, startCol,
                            getLine(), getColumn() - 2);
         $setType(Token.SKIP);
      }
   ;


// character literals
CHAR_LITERAL
	:	'\'' ( ESC | ~'\'' ) '\''
	;

// string literals
STRING_LITERAL
	:	'"' (ESC|~('"'|'\\'))* '"'
	;


// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
		|	('0'..'3')
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	('0'..'7')
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	('4'..'7')
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	('0'..'9')
			)?
		)
	;


// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
	:	'\3'..'\377'
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT
	{boolean isDecimal=false; Token t=null;}
    :   '.' {_ttype = DOT;}
            (	('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
                {
				if (t != null && t.getText().toUpperCase().indexOf('D')>=0) {
                	_ttype = NUM_DOUBLE;
				}
				else {
                	_ttype = NUM_FLOAT;
				}
				}
            )?

	|	(	'0' {isDecimal = true;} // special case for just '0'
			(	('x'|'X')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefor ok to hush warning.
					options {
						warnWhenFollowAmbig=false;
					}
				:	HEX_DIGIT
				)+
			|	('0'..'7')+									// octal
			)?
		|	('1'..'9') ('0'..'9')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l'|'L') { _ttype = NUM_LONG; }

		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
            |   f4:FLOAT_SUFFIX {t=f4;}
            )
            {
			if (t != null && t.getText().toUpperCase() .indexOf('D') >= 0) {
                _ttype = NUM_DOUBLE;
			}
            else {
                _ttype = NUM_FLOAT;
			}
			}
        )?
	;


// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;


protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;
