////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import antlr.CommonToken;
}


/** Java 1.5 Recognizer
 *
 * Based on Java 1.5 grammar submitted by Michael Studman to www.antlr.org
 * Also based on initial generics enhancements to java 1.4 grammar by
 * Matt Quail from Cequa.
 *
 */
class GeneratedJava15Recognizer extends GeneratedJavaRecognizer;

// Options don't get inherited, copy of option block required.
options {
	k = 2;                           // two token lookahead
	exportVocab=GeneratedJava15;     // Call its vocabulary "GeneratedJava15"
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

tokens {
    ASSERT="assert"; STATIC_IMPORT; ENUM_DEF; ENUM_CONSTANT_DEF; FOR_EACH_CLAUSE;
	ANNOTATION_DEF; ANNOTATIONS; ANNOTATION; ANNOTATION_MEMBER_VALUE_PAIR; ANNOTATION_FIELD_DEF;
	ANNOTATION_ARRAY_INIT; TYPE_ARGUMENTS; TYPE_ARGUMENT; TYPE_PARAMETERS;
	TYPE_PARAMETER; WILDCARD_TYPE; TYPE_UPPER_BOUNDS; TYPE_LOWER_BOUNDS;
}

{
    /**
     * Counts the number of LT seen in the typeArguments production.
     * It is used in semantic predicates to ensure we have seen
     * enough closing '>' characters; which actually may have been
     * either GT, SR or BSR tokens.
     */
    private int ltCounter = 0;

    /**
     * Counts the number of '>' characters that have been seen but
     * have not yet been associated with the end of a typeParameters or
     * typeArguments production. This is necessary because SR and BSR
     * tokens have significance (the extra '>' characters) not only for the production
     * that sees them but also productions higher in the stack (possibly right up to an outer-most
     * typeParameters production). As the stack of the typeArguments/typeParameters productions unwind,
     * any '>' characters seen prematurely through SRs or BSRs are reconciled.
     */
    private int gtToReconcile = 0;

    /**
     * The most recently seen gt sequence (GT, SR or BSR)
     * encountered in any type argument or type parameter production.
     * We retain this so we can keep manage the synthetic GT tokens/
     * AST nodes we emit to have '<' & '>' balanced trees when encountering
     * SR and BSR tokens.
     */
    private DetailAST currentGtSequence = null;

    /**
     * Consume a sequence of '>' characters (GT, SR or BSR)
     * and match these against the '<' characters seen.
     */
    private void consumeCurrentGtSequence(DetailAST gtSequence)
    {
        currentGtSequence = gtSequence;
        gtToReconcile += currentGtSequence.getText().length();
        ltCounter -= currentGtSequence.getText().length();
    }

    /**
     * Emits a single GT AST node with the line and column correctly
     * set to its position in the source file. This must only
     * ever be called when a typeParameters or typeArguments production
     * is ending and there is at least one GT character to be emitted.
     *
     * @see #areThereGtsToEmit
     */
    private DetailAST emitSingleGt()
    {
        gtToReconcile -= 1;
        CommonToken gtToken = new CommonToken(GT, ">");
        gtToken.setLine(currentGtSequence.getLineNo());
        gtToken.setColumn(currentGtSequence.getColumnNo() + (currentGtSequence.getText().length() - gtToReconcile));
        return (DetailAST)astFactory.create(gtToken);
    }

    /**
     * @return true if there is at least one '>' seen but
     * not reconciled with the end of a typeParameters or
     * typeArguments production; returns false otherwise
     */
    private boolean areThereGtsToEmit()
    {
        return (gtToReconcile > 0);
    }

    /**
     * @return true if there is exactly one '>' seen but
     * not reconciled with the end of a typeParameters
     * production; returns false otherwise
     */
    private boolean isThereASingleGtToEmit()
    {
        return (gtToReconcile == 1);
    }

    /**
     * @return true if the '<' and '>' are evenly matched
     * at the current typeParameters/typeArguments nested depth
     */
    private boolean areLtsAndGtsBalanced(int currentLtLevel)
    {
        return ((currentLtLevel != 0) || ltCounter == currentLtLevel);
    }
}

// Compilation Unit: In Java, this is a single file.  This is the start
//   rule for this parser
compilationUnit
	:	// A compilation unit starts with an optional package definition
	    // semantic check because package definitions can be annotated
	    // which causes possible non-determinism in Antrl
		(	(annotations "package")=> packageDefinition
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
	:	annotations p:"package"^ {#p.setType(PACKAGE_DEF);} identifier SEMI
	;

// Import statement: import followed by a package or class name
importDefinition
	options {defaultErrorHandler = true;}
	:	i:"import"^ {#i.setType(IMPORT);} ( "static" {#i.setType(STATIC_IMPORT);} )? identifierStar SEMI
	;

// A type definition in a file is either a class, interface, enum of annotation definition
typeDefinition
	options {defaultErrorHandler = true;}
	:	m:modifiers! typeDefinitionInternal[#m]
	|	SEMI
	;

// Internal type definition for production reuse
protected
typeDefinitionInternal[AST modifiers]
    : classDefinition[#modifiers]
	| interfaceDefinition[#modifiers]
	| enumDefinition[#modifiers]
	| annotationDefinition[#modifiers]
	;

// A type specification is a type name with possible brackets afterwards
//   (which would make it an array type).
typeSpec[boolean addImagNode]
	: classTypeSpec[addImagNode]
	| builtInTypeSpec[addImagNode]
	;

// A class type specification is a class type with either:
// - possible brackets afterwards
//   (which would make it an array type).
// - generic type arguments after
classTypeSpec[boolean addImagNode]
	:   classOrInterfaceType[addImagNode]
        (options{greedy=true;}: lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)*
		{
			if ( addImagNode ) {
				#classTypeSpec = #(#[TYPE,"TYPE"], #classTypeSpec);
			}
		}
	;

classOrInterfaceType[boolean addImagNode]
	:   IDENT^ (typeArguments[addImagNode])?
        (options{greedy=true;}: // match as many as possible
            DOT^
            IDENT (typeArguments[addImagNode])?
        )*
    ;

// A generic type argument is a class type, a possibly bounded wildcard type or a built-in type array
typeArgument[boolean addImagNode]
	:   (   classTypeSpec[addImagNode]
	    |   builtInTypeArraySpec[addImagNode]
	    |   wildcardType[addImagNode]
	    )
		{#typeArgument = #(#[TYPE_ARGUMENT,"TYPE_ARGUMENT"], #typeArgument);}
    ;

wildcardType[boolean addImagNode]
    :   q:QUESTION {#q.setType(WILDCARD_TYPE);}
        (("extends" | "super")=> typeArgumentBounds[addImagNode])?
    ;

typeArguments[boolean addImagNode]
{int currentLtLevel = 0;}
    :
        {currentLtLevel = ltCounter;}
        LT {ltCounter++;}
        typeArgument[addImagNode]
        (options{greedy=true;}: // match as many as possible
            // If there are any '>' to reconcile
            // (i.e. we've recently encountered a DT, SR or BSR
            // - the end of one or more type arguments and
            // possibly an enclosing type parameter)
            // then further type arguments are not possible
            {gtToReconcile == 0}? COMMA typeArgument[addImagNode]
        )*

        (   // turn warning off since Antlr generates the right code,
            // plus we have our semantic predicate below
            options{generateAmbigWarnings=false;}:
            typeArgumentsOrParametersEnd
        )?

        // As we are leaving a typeArguments production, the enclosing '>'
        // we've just read (and we've possibly seen more than one in the
        // case of SRs and BSRs) can now be marked as reconciled with a '<'
        // but we still leave unreconciled the count for any excess '>'
        // for other typeArguments or typeParameters productions higher in
        // the stack
        {
            if (areThereGtsToEmit())
            {
                astFactory.addASTChild(currentAST, emitSingleGt());
            }
        }
        // make sure we have gobbled up enough '>' characters
        // if we are at the "top level" of nested typeArgument productions
        {areLtsAndGtsBalanced(currentLtLevel)}?

        {#typeArguments = #(#[TYPE_ARGUMENTS, "TYPE_ARGUMENTS"], #typeArguments);}
    ;

// this gobbles up *some* amount of '>' characters, and counts how many
// it gobbled.
protected typeArgumentsOrParametersEnd!
    :   g:GT {consumeCurrentGtSequence((DetailAST)#g);}
    |   sr:SR {consumeCurrentGtSequence((DetailAST)#sr);}
    |   bsr:BSR {consumeCurrentGtSequence((DetailAST)#bsr);}
    ;

typeArgumentBounds[boolean addImagNode]
    {boolean isUpperBounds = false;}
    :
        ( "extends"! {isUpperBounds=true;} | "super"! ) classOrInterfaceType[addImagNode]
		{
		    if (isUpperBounds)
		    {
		        #typeArgumentBounds = #(#[TYPE_UPPER_BOUNDS,"TYPE_UPPER_BOUNDS"], #typeArgumentBounds);
		    }
		    else
		    {
		        #typeArgumentBounds = #(#[TYPE_LOWER_BOUNDS,"TYPE_LOWER_BOUNDS"], #typeArgumentBounds);
		    }
		}
    ;

// A builtin type array specification is a builtin type with brackets afterwards
builtInTypeArraySpec[boolean addImagNode]
	:	builtInType
	    (options{greedy=true;}: lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK!)+
		{
			if ( addImagNode ) {
				#builtInTypeArraySpec = #(#[TYPE,"TYPE"], #builtInTypeArraySpec);
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

// A type name. which is either a (possibly qualified and parameterized)
// class name or a primitive (builtin) type
type
	:	classOrInterfaceType[false]
	|	builtInType
	;

// A list of zero or more modifiers.  We could have used (modifier)* in
//   place of a call to modifiers, but I thought it was a good idea to keep
//   this rule separate so they can easily be collected in a Vector if
//   someone so desires
modifiers
	:                                    
	    (
	        //hush warnings since the semantic check for "@interface" solves the non-determinism
	        options{generateAmbigWarnings=false;}:

	        modifier
	        |
	        //Semantic check that we aren't matching @interface as this is not an annotation
	        //A nicer way to do this would be, um, nice
	        {LA(1)==AT && !LT(2).getText().equals("interface")}? annotation
	    )*

		{#modifiers = #([MODIFIERS, "MODIFIERS"], #modifiers);}
	;

annotation!
    :   AT i:identifier ( l:LPAREN ( args:annotationArguments )? r:RPAREN )?
        {#annotation = #(#[ANNOTATION,"ANNOTATION"], AT, i, l, args, r);}
    ;

annotations
    :   (annotation)*
        {#annotations = #(#[ANNOTATIONS,"ANNOTATIONS"], #annotations);}
    ;

annotationArguments
    :   annotationMemberValueInitializer | anntotationMemberValuePairs
    ;

anntotationMemberValuePairs
    :   annotationMemberValuePair ( COMMA annotationMemberValuePair )*
    ;

annotationMemberValuePair!
    :   i:IDENT a:ASSIGN v:annotationMemberValueInitializer
        {#annotationMemberValuePair = #(#[ANNOTATION_MEMBER_VALUE_PAIR,"ANNOTATION_MEMBER_VALUE_PAIR"], i, a, v);}
    ;

annotationMemberValueInitializer
    :
        conditionalExpression | annotation | annotationMemberArrayInitializer
    ;

// This is an initializer used to set up an annotation member array.
annotationMemberArrayInitializer
	:	lc:LCURLY^ {#lc.setType(ANNOTATION_ARRAY_INIT);}
			(	annotationMemberArrayValueInitializer
				(
					// CONFLICT: does a COMMA after an initializer start a new
					//           initializer or start the option ',' at end?
					//           ANTLR generates proper code by matching
					//			 the comma as soon as possible.
					options {
						warnWhenFollowAmbig = false;
					}
				:
					COMMA annotationMemberArrayValueInitializer
				)*
				(COMMA)?
			)?
		RCURLY
	;

// The two things that can initialize an annotation array element are a conditional expression
//   and an annotation (nested annotation array initialisers are not valid)
annotationMemberArrayValueInitializer
	:	conditionalExpression
	|   annotation
	;

// Definition of a Java class
classDefinition![AST modifiers]
	:	c:"class" IDENT
		// it _might_ have type paramaters
		(tp:typeParameters)?
		// it _might_ have a superclass...
		sc:superClassClause
		// it might implement some interfaces...
		ic:implementsClause
		// now parse the body of the class
		cb:classBlock
		{#classDefinition = #(#[CLASS_DEF,"CLASS_DEF"],
							   modifiers, c, IDENT, tp, sc, ic, cb);}
    ;

superClassClause!
	:	( "extends" c:classOrInterfaceType[false] )?
		{#superClassClause = #(#[EXTENDS_CLAUSE,"EXTENDS_CLAUSE"],c);}
	;

// Definition of a Java Interface
interfaceDefinition![AST modifiers]
	:	i:"interface" IDENT
        // it _might_ have type paramaters
        (tp:typeParameters)?
		// it might extend some other interfaces
		ie:interfaceExtends
		// now parse the body of the interface (looks like a class...)
		cb:classBlock
		{#interfaceDefinition = #(#[INTERFACE_DEF,"INTERFACE_DEF"],
									modifiers, i, IDENT,tp,ie,cb);}
	;

enumDefinition![AST modifiers]
	:	e:"enum" IDENT
		// it might implement some interfaces...
		ic:implementsClause
		// now parse the body of the enum
		eb:enumBlock
		{#enumDefinition = #(#[ENUM_DEF,"ENUM_DEF"],
							   modifiers, e, IDENT, ic, eb);}
    ;

annotationDefinition![AST modifiers]
	:	a:AT i:"interface" IDENT
		// now parse the body of the annotation
		ab:annotationBlock
		{#annotationDefinition = #(#[ANNOTATION_DEF,"ANNOTATION_DEF"],
							        modifiers, a, i, IDENT, ab);}
    ;

typeParameters
{int currentLtLevel = 0;}
    :
        {currentLtLevel = ltCounter;}
        LT {ltCounter++;}
        typeParameter (COMMA typeParameter)*
        (typeArgumentsOrParametersEnd)?

        // There should be only one '>' to reconcile - the enclosing
        // '>' for the type parameter. Any other adjacent '>' seen should
        // have been reconciled with type arguments for the last type parameter
        // hence we can assert here that there is but one unaccounted '>'.
        {isThereASingleGtToEmit()}?
        //And then there were none..

        {
            astFactory.addASTChild(currentAST, emitSingleGt());
        }
        // make sure we have gobbled up enough '>' characters
        // if we are at the "top level" of nested typeArgument productions
        {areLtsAndGtsBalanced(currentLtLevel)}?

        {#typeParameters = #(#[TYPE_PARAMETERS, "TYPE_PARAMETERS"], #typeParameters);}
    ;

typeParameter
    :
        // I'm pretty sure Antlr generates the right thing here:
        (id:IDENT) ( options{generateAmbigWarnings=false;}: typeParameterBounds )?
		{#typeParameter = #(#[TYPE_PARAMETER,"TYPE_PARAMETER"], #typeParameter);}
    ;

typeParameterBounds
    :
        "extends"! classOrInterfaceType[true]
        (BAND classOrInterfaceType[true])*
        {#typeParameterBounds = #(#[TYPE_UPPER_BOUNDS,"TYPE_UPPER_BOUNDS"], #typeParameterBounds);}
    ;

// This is the body of an annotation. You can have annotation fields and extra semicolons,
// That's about it (until you see what an annoation field is...)
annotationBlock
	:	LCURLY
	    ( annotationField | SEMI )*
		RCURLY
		{#annotationBlock = #([OBJBLOCK, "OBJBLOCK"], #annotationBlock);}
	;

// An annotation field
annotationField!
    :   mods:modifiers
		(	td:typeDefinitionInternal[#mods]
			{#annotationField = #td;}

		|   t:typeSpec[false]               // annotation field
			(	i:IDENT  // the name of the field

				LPAREN RPAREN

				rt:declaratorBrackets[#t]

                ( d:"default" amvi:annotationMemberValueInitializer )?

				s:SEMI

				{#annotationField =
				    #(#[ANNOTATION_FIELD_DEF,"ANNOTATION_FIELD_DEF"],
                         mods,
                         #(#[TYPE,"TYPE"],rt),
                         i,
                         LPAREN,
                         RPAREN,
                         d,
                         amvi,
                         s
                         );}
			|	v:variableDefinitions[#mods,#t] s6:SEMI
				{
					#annotationField = #v;
					#v.addChild(#s6);
				}
			)
		)
    ;

// This is the body of an enum. You can have zero or more enum constants
// followed by any number of fields like a regular class
enumBlock
	:	LCURLY
	        ( enumConstant ( options{greedy=true;}: COMMA enumConstant )* ( COMMA )? )?
	        ( SEMI ( field | SEMI )* )?
		RCURLY
		{#enumBlock = #([OBJBLOCK, "OBJBLOCK"], #enumBlock);}
	;

//An enum constant may have optional parameters and may have a
//a body
enumConstant!
    :   an:annotations
        i:IDENT
        (	l:LPAREN
            args:argList
            r:RPAREN
        )?
        ( b:enumConstantBlock )?
        {#enumConstant = #([ENUM_CONSTANT_DEF, "ENUM_CONSTANT_DEF"], an, i, l, args, r, b);}
    ;

//The class-like body of an enum constant
enumConstantBlock
    :   LCURLY
        ( enumConstantField | SEMI )*
        RCURLY
        {#enumConstantBlock = #([OBJBLOCK, "OBJBLOCK"], #enumConstantBlock);}
    ;

//An enum constant field is just like a class field but without
//the posibility of a constructor definition or a static initializer
enumConstantField!
    :   mods:modifiers
		(	td:typeDefinitionInternal[#mods]
			{#enumConstantField = #td;}

		|	// A generic method has the typeParameters before the return type.
            // This is not allowed for variable definitions, but this production
            // allows it, a semantic check could be used if you wanted.
            (tp:typeParameters)? t:typeSpec[false]  // method or variable declaration(s)
			(	IDENT  // the name of the method

				// parse the formal parameter declarations.
				LPAREN param:parameterDeclarationList RPAREN

				rt:declaratorBrackets[#t]

				// get the list of exceptions that this method is
				// declared to throw
				(tc:throwsClause)?

				( s2:compoundStatement | s3:SEMI )
				{#enumConstantField = #(#[METHOD_DEF,"METHOD_DEF"],
						     mods,
						     tp,
							 #(#[TYPE,"TYPE"],rt),
							 IDENT,
							 LPAREN,
							 param,
							 RPAREN,
							 tc,
							 s2,
							 s3);}
			|	v:variableDefinitions[#mods,#t] s6:SEMI
				{
					#enumConstantField = #v;
					#v.addChild(#s6);
				}
			)
		)

    // "{ ... }" instance initializer
	|	s4:compoundStatement
		{#enumConstantField = #(#[INSTANCE_INIT,"INSTANCE_INIT"], s4);}
	;

// An interface can extend several other interfaces...
interfaceExtends
	:	(
		e:"extends"!
		classOrInterfaceType[false] ( COMMA classOrInterfaceType[false] )*
		)?
		{#interfaceExtends = #(#[EXTENDS_CLAUSE,"EXTENDS_CLAUSE"],
							#interfaceExtends);}
	;

// A class can implement several interfaces...
implementsClause
	:	(
			i:"implements"! classOrInterfaceType[false] ( COMMA classOrInterfaceType[false] )*
		)?
		{#implementsClause = #(#[IMPLEMENTS_CLAUSE,"IMPLEMENTS_CLAUSE"],
								 #implementsClause);}
	;

// Now the various things that can be defined inside a class or interface...
// Note that not all of these are really valid in an interface (constructors,
//   for example), and if this grammar were used for a compiler there would
//   need to be some semantic checks to make sure we're doing the right thing...
field!
	:	// method, constructor, or variable declaration
		mods:modifiers
		(	td:typeDefinitionInternal[#mods]
			{#field = #td;}

	    // A generic method/ctor has the typeParameters before the return type.
        // This is not allowed for variable definitions, but this production
        // allows it, a semantic check could be used if you wanted.
        |   (tp:typeParameters)?
            (
                h:ctorHead s:constructorBody // constructor
                {#field = #(#[CTOR_DEF,"CTOR_DEF"], mods, tp, h, s);}

                |
                t:typeSpec[false]  // method or variable declaration(s)
                (	IDENT  // the name of the method

                    // parse the formal parameter declarations.
                    LPAREN param:parameterDeclarationList RPAREN

                    rt:declaratorBrackets[#t]

                    // get the list of exceptions that this method is
                    // declared to throw
                    (tc:throwsClause)?

                    ( s2:compoundStatement | s5:SEMI )
                    {#field = #(#[METHOD_DEF,"METHOD_DEF"],
                                 mods,
                                 tp,
                                 #(#[TYPE,"TYPE"],rt),
                                 IDENT,
                                 LPAREN,
                                 param,
                                 RPAREN,
                                 tc,
                                 s2,
                                 s5);}
                |	v:variableDefinitions[#mods,#t] s6:SEMI
                    {
                        #field = #v;
                        #v.addChild(#s6);
                    }
                )
            )
		)

    // "static { ... }" class initializer
	|	si:"static" s3:compoundStatement
		{#si.setType(STATIC_INIT);
		 #si.setText("STATIC_INIT");
		 #field = #(#si, s3);}

    // "{ ... }" instance initializer
	|	s4:compoundStatement
		{#field = #(#[INSTANCE_INIT,"INSTANCE_INIT"], s4);}
	;

// A list of formal parameters
//     Zero or more parameters
//     If a parameter is variable length (e.g. String... myArg) it is the right-most parameter
parameterDeclarationList
    // The semantic check in ( .... )* block is flagged as superfluous, and seems superfluous but
    // is the only way I could make this work. If my understanding is correct this is a known bug in Antlr
    :   (   ( parameterDeclaration )=> parameterDeclaration
            ( options {warnWhenFollowAmbig=false;} : ( COMMA parameterDeclaration ) => COMMA parameterDeclaration )*
            ( COMMA variableLengthParameterDeclaration )?
        |
            variableLengthParameterDeclaration
        )?
		{#parameterDeclarationList = #(#[PARAMETERS,"PARAMETERS"],
									#parameterDeclarationList);}
	;

variableLengthParameterDeclaration!
	:	pm:parameterModifier t:typeSpec[false] td:TRIPLE_DOT IDENT
		pd:declaratorBrackets[#t]
		{#variableLengthParameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"],
                                                pm, #([TYPE,"TYPE"],pd), td, IDENT);}
    ;

parameterModifier
    //final can appear amongst annotations in any order - greedily consume any preceding
    //annotations to shut nond-eterminism warnings off
	:	(options{greedy=true;} : annotation)* (f:"final")? (annotation)*
		{#parameterModifier = #(#[MODIFIERS,"MODIFIERS"], #parameterModifier);}
    ;

// overrides the statement production in java.g, adds assertStatement
statement
	:	traditionalStatement
	|	assertStatement
	;

// assert statement, available since JDK 1.4
assertStatement
	:	ASSERT^ expression ( COLON expression )? SEMI
	;

forStatement
    :   f:"for"^
        LPAREN
            ( (forInit SEMI)=>traditionalForClause
              |
              forEachClause)
        RPAREN
        statement                     // statement to loop over
    ;

traditionalForClause
    :
        forInit SEMI   // initializer
        forCond SEMI   // condition test
        forIter         // updater
    ;

forEachClause
    :
        p:parameterDeclaration COLON expression
        {#forEachClause = #(#[FOR_EACH_CLAUSE,"FOR_EACH_CLAUSE"], #forEachClause);}
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
		:
			(typeArguments[false])?
			(	"this"! lp1:LPAREN^ argList RPAREN SEMI
				{#lp1.setType(CTOR_CALL);}

			|   "super"! lp2:LPAREN^ argList RPAREN SEMI
					{#lp2.setType(SUPER_CTOR_CALL);}
			)

		|	// (new Outer()).super()  (create enclosing instance)
			primaryExpression DOT! (typeArguments[false])? "super"! lp3:LPAREN^ argList RPAREN SEMI
			{#lp3.setType(SUPER_CTOR_CALL);}
		)
    ;

// TODO: handle type parameters more effectively - I think this production needs
// a refactoring like the original Antlr Java grammar got
// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
	:	primaryExpression // start with a primary

		(	// qualified id (id.id.id.id...) -- build the name
			DOT^
			( (typeArguments[false])?
			  ( IDENT
			  | "this"
			  | "super" // ClassName.super.field
			  )
			| "class"
			| newExpression
			)
			// the above line needs a semantic check to make sure "class"
			// is the _last_ qualifier.

			// allow ClassName[].class
		|	( lbc:LBRACK^ {#lbc.setType(ARRAY_DECLARATOR);} RBRACK! )+
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
			RPAREN
		)*

		// possibly add on a post-increment or post-decrement.
		// allows INC/DEC on too much, but semantics can check
		(	in:INC^ {#in.setType(POST_INC);}
	 	|	de:DEC^ {#de.setType(POST_DEC);}
		|	// nothing
		)
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
	:	"new"^ (typeArguments[false])? type
		(	LPAREN argList RPAREN (classBlock)?

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

class GeneratedJava15Lexer extends GeneratedJavaLexer;

options {
    //This shouldn't be (importing its own vocab), but without it the
    //generated lexer doesn't recognise "enum" as a special literal.
    //Antlr mailing list was silent on why this is so.
	importVocab=GeneratedJava15;
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\uFFFE';
	codeGenBitsetTestThreshold=20;
}

//token signifying annotations and annotation declaration
AT
    :   '@'
    ;

//overriden definition of this lexer rule to recognize the ... token - for
//variable argument length
NUM_INT
	{boolean isDecimal=false; Token t=null;}
    :   '.' {_ttype = DOT;}
            (
                (('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
                {
				if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                	_ttype = NUM_FLOAT;
				}
				else {
                	_ttype = NUM_DOUBLE; // assume double
				}
				})
				|
				// JDK 1.5 token for variable length arguments
				(".." {_ttype = TRIPLE_DOT;})
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

			|	//float or double with leading zero
				(('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

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
			if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                _ttype = NUM_FLOAT;
			}
            else {
	           	_ttype = NUM_DOUBLE; // assume double
			}
			}
        )?
	;
