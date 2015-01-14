////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
header {
package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.text.MessageFormat;
import antlr.CommonHiddenStreamToken;
}

/** Java 1.5 Recognizer
 *
 * This grammar is in the PUBLIC DOMAIN
 */
class GeneratedJavaRecognizer extends Parser;

options {
	k = 2;                           // two token lookahead
	exportVocab=GeneratedJava;       // Call its vocabulary "GeneratedJava"
	codeGenMakeSwitchThreshold = 2;  // Some optimizations
	codeGenBitsetTestThreshold = 3;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
}

//Please add new tokens only in the end of list! Otherwise you break compatibility!
tokens {
    //Pre-1.4 tokens
	BLOCK; MODIFIERS; OBJBLOCK; SLIST; CTOR_DEF; METHOD_DEF; VARIABLE_DEF;
	INSTANCE_INIT; STATIC_INIT; TYPE; CLASS_DEF; INTERFACE_DEF;
	PACKAGE_DEF; ARRAY_DECLARATOR; EXTENDS_CLAUSE; IMPLEMENTS_CLAUSE;
	PARAMETERS; PARAMETER_DEF; LABELED_STAT; TYPECAST; INDEX_OP;
	POST_INC; POST_DEC; METHOD_CALL; EXPR; ARRAY_INIT;
	IMPORT; UNARY_MINUS; UNARY_PLUS; CASE_GROUP; ELIST; FOR_INIT; FOR_CONDITION;
	FOR_ITERATOR; EMPTY_STAT; FINAL="final"; ABSTRACT="abstract";
	STRICTFP="strictfp"; SUPER_CTOR_CALL; CTOR_CALL;

	//ANTLR-generated pre-1.4 tokens now listed here to preserve their numerical
	//order so as to make all future version of this grammar backwardly compatibile
    LITERAL_package="package";SEMI;LITERAL_import="import";LBRACK;RBRACK;
    LITERAL_void="void";LITERAL_boolean="boolean";LITERAL_byte="byte";
    LITERAL_char="char";LITERAL_short="short";LITERAL_int="int";
    LITERAL_float="float";LITERAL_long="long";LITERAL_double="double";
    IDENT;DOT;STAR;LITERAL_private="private";LITERAL_public="public";
    LITERAL_protected="protected";LITERAL_static="static";
    LITERAL_transient="transient";LITERAL_native="native";
    LITERAL_synchronized="synchronized";LITERAL_volatile="volatile";

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_class="class";LITERAL_extends="extends";
    LITERAL_interface="interface";LCURLY;RCURLY;COMMA;
    LITERAL_implements="implements";LPAREN;RPAREN;LITERAL_this="this";
    LITERAL_super="super";ASSIGN;LITERAL_throws="throws";COLON;
    LITERAL_if="if";LITERAL_while="while";LITERAL_do="do";
    LITERAL_break="break";LITERAL_continue="continue";LITERAL_return="return";
    LITERAL_switch="switch";LITERAL_throw="throw";LITERAL_for="for";
    LITERAL_else="else";LITERAL_case="case";LITERAL_default="default";

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_try="try";LITERAL_catch="catch";LITERAL_finally="finally";
    PLUS_ASSIGN;MINUS_ASSIGN;STAR_ASSIGN;DIV_ASSIGN;MOD_ASSIGN;SR_ASSIGN;
    BSR_ASSIGN;SL_ASSIGN;BAND_ASSIGN;BXOR_ASSIGN;BOR_ASSIGN;QUESTION;
    LOR;LAND;BOR;BXOR;BAND;NOT_EQUAL;EQUAL;LT;GT;LE;GE;
    LITERAL_instanceof="instanceof";SL;SR;BSR;PLUS;MINUS;DIV;MOD;
    INC;DEC;BNOT;LNOT;LITERAL_true="true";LITERAL_false="false";
    LITERAL_null="null";LITERAL_new="new";NUM_INT;CHAR_LITERAL;
    STRING_LITERAL;NUM_FLOAT;NUM_LONG;NUM_DOUBLE;WS;SINGLE_LINE_COMMENT;
    BLOCK_COMMENT_BEGIN;ESC;HEX_DIGIT;VOCAB;EXPONENT;FLOAT_SUFFIX;

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    //Token for Java 1.4 language enhancements
    ASSERT;

    //Tokens for Java 1.5 language enhancements
    STATIC_IMPORT; ENUM; ENUM_DEF; ENUM_CONSTANT_DEF; FOR_EACH_CLAUSE;
	ANNOTATION_DEF; ANNOTATIONS; ANNOTATION; ANNOTATION_MEMBER_VALUE_PAIR; ANNOTATION_FIELD_DEF;
	ANNOTATION_ARRAY_INIT; TYPE_ARGUMENTS; TYPE_ARGUMENT; TYPE_PARAMETERS;
	TYPE_PARAMETER; WILDCARD_TYPE; TYPE_UPPER_BOUNDS; TYPE_LOWER_BOUNDS; AT; ELLIPSIS;
	GENERIC_START; GENERIC_END; TYPE_EXTENSION_AND;

    //Please add new tokens only in the end of list! Otherwise you break compatibility!

    // token which was not included to grammar initially
    // we need to put it to the end to maintain binary compatibility
    // with previous versions
    DO_WHILE;
    
    //Tokens for Java 1.7 language enhancements
    RESOURCE_SPECIFICATION; RESOURCES; RESOURCE;

    //Tokens for Java 1.8
    DOUBLE_COLON;  METHOD_REF; LAMBDA;

    //Support of java comments has been extended
    BLOCK_COMMENT_END;COMMENT_CONTENT;
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
        CommonHiddenStreamToken gtToken = new CommonHiddenStreamToken(GENERIC_END, ">");
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
		( options{generateAmbigWarnings=false;}:importDefinition )*

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
	|	SEMI
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
        (options{greedy=true;}: lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK)*
		{
			if ( addImagNode ) {
				#classTypeSpec = #(#[TYPE,"TYPE"], #classTypeSpec);
			}
		}
	;

classOrInterfaceType[boolean addImagNode]
	: ({LA(1) == AT}? annotations
            | )
    IDENT (options{warnWhenFollowAmbig=false;}: typeArguments[addImagNode])?
        (options{greedy=true; }: // match as many as possible
            DOT^
            IDENT (options{warnWhenFollowAmbig=false;}: typeArguments[addImagNode])?
        )*
    ;

// A generic type argument is a class type, a possibly bounded wildcard type or a built-in type array
typeArgument[boolean addImagNode]
:   (   ({LA(1) == AT}? annotations
         | ) (
        classTypeSpec[addImagNode]
	    |   builtInTypeArraySpec[addImagNode]
	    |   wildcardType[addImagNode])
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
        lt:LT {#lt.setType(GENERIC_START); ;ltCounter++;}
        // (Dinesh Bolkensteyn) Added support for Java 7 diamond notation (disabled ambiguous warnings since generated code seems to work)
        (options{generateAmbigWarnings=false;}:typeArgument[addImagNode]
        (options{greedy=true;}: // match as many as possible
            // If there are any '>' to reconcile
            // (i.e. we've recently encountered a DT, SR or BSR
            // - the end of one or more type arguments and
            // possibly an enclosing type parameter)
            // then further type arguments are not possible
            {gtToReconcile == 0}? COMMA typeArgument[addImagNode]
        )*)?

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
    :
        (
            e:"extends"^ {#e.setType(TYPE_UPPER_BOUNDS); }
          | s:"super"^ { #s.setType(TYPE_LOWER_BOUNDS); }
        )
        (
            classOrInterfaceType[addImagNode]
          | builtInType
        )
        (options{greedy=true;}: lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK)*
    ;

// A builtin type array specification is a builtin type with brackets afterwards
builtInTypeArraySpec[boolean addImagNode]
	:	builtInType
	    (options{greedy=true;}: lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK)+
		{
			if ( addImagNode ) {
				#builtInTypeArraySpec = #(#[TYPE,"TYPE"], #builtInTypeArraySpec);
			}
		}
	;

// A builtin type specification is a builtin type with possible brackets
// afterwards (which would make it an array type).
builtInTypeSpec[boolean addImagNode]
	:	builtInType (lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK)*
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

/** A declaration is the creation of a reference or primitive-type variable
 *  Create a separate Type/Var tree for each var in the var list.
 */
declaration!
	:	m:modifiers t:typeSpec[false] v:variableDefinitions[#m,#t]
		{#declaration = #v;}
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
	:	IDENT  (options{warnWhenFollowAmbig=false;}: DOT^ IDENT )*
	;

identifierStar
	:	IDENT
		( DOT^ IDENT )*
		( DOT^ STAR  )?
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
	|	"synchronized"
//	|	"const"			// reserved word, but not valid
	|	"volatile"
	|	"strictfp"
	|	"default"
	;

annotation!
    :   AT i:identifier (options {generateAmbigWarnings=false;}: l:LPAREN ( args:annotationArguments )? r:RPAREN )?
        {#annotation = #(#[ANNOTATION,"ANNOTATION"], AT, i, l, args, r);}
    ;

annotations
    :   (options{generateAmbigWarnings=false;}:annotation)*
        {#annotations = #(#[ANNOTATIONS,"ANNOTATIONS"], #annotations);}
    ;

annotationArguments
    :   annotationMemberValueInitializer | annotationMemberValuePairs
    ;

annotationMemberValuePairs
    :   annotationMemberValuePair ( COMMA annotationMemberValuePair )*
    ;

annotationMemberValuePair!
    :   i:IDENT a:ASSIGN v:annotationMemberValueInitializer
        {#annotationMemberValuePair = #(#[ANNOTATION_MEMBER_VALUE_PAIR,"ANNOTATION_MEMBER_VALUE_PAIR"], i, a, v);}
    ;

annotationMemberValueInitializer
    :
        annotationExpression | annotation | annotationMemberArrayInitializer
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
	:	annotationExpression
	|   annotation
	;

annotationExpression
    :   conditionalExpression
        {#annotationExpression = #(#[EXPR,"EXPR"],#annotationExpression);}
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

superClassClause
	:	( e:"extends"^ {#e.setType(EXTENDS_CLAUSE);}
          c:classOrInterfaceType[false] )?
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
	:	e:ENUM IDENT
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
        lt:LT {#lt.setType(GENERIC_START); ltCounter++;}
        typeParameter (COMMA typeParameter)*
        (typeArgumentsOrParametersEnd)?

        // There should be only one '>' to reconcile - the enclosing
        // '>' for the type parameter. Any other adjacent '>' seen should
        // have been reconciled with type arguments for the last type parameter
        // hence we can assert here that there is but one unaccounted '>'.
        {
        	if (isThereASingleGtToEmit()) {
            	astFactory.addASTChild(currentAST, emitSingleGt());
            }
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
        e:"extends"^ classOrInterfaceType[true]
        (b:BAND {#b.setType(TYPE_EXTENSION_AND);} classOrInterfaceType[true])*
        {#e.setType(TYPE_UPPER_BOUNDS);}
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

                ( d:annotationDefault )?

				s:SEMI

				{#annotationField =
				    #(#[ANNOTATION_FIELD_DEF,"ANNOTATION_FIELD_DEF"],
                         mods,
                         #(#[TYPE,"TYPE"],rt),
                         i,
                         LPAREN,
                         RPAREN,
                         d,
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

annotationDefault
    : "default"^ annotationMemberValueInitializer
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

// This is the body of a class.  You can have fields and extra semicolons,
// That's about it (until you see what a field is...)
classBlock
	:	LCURLY
			( field | SEMI )*
		RCURLY
		{#classBlock = #([OBJBLOCK, "OBJBLOCK"], #classBlock);}
	;

// An interface can extend several other interfaces...
interfaceExtends
	:	(
		e:"extends"^ {#e.setType(EXTENDS_CLAUSE);} 
        classOrInterfaceType[false] ( COMMA classOrInterfaceType[false] )*
		)?
	;

// A class can implement several interfaces...
implementsClause
	:	(
			i:"implements"^ {#i.setType(IMPLEMENTS_CLAUSE);}
            classOrInterfaceType[false] ( COMMA classOrInterfaceType[false] )*
		)?
	;

   // Now the various things that can be defined inside a class or interface...
   // Note that not all of these are really valid in an interface (constructors,
   //   for example), and if this grammar were used for a compiler there would
   //   need to be some semantic checks to make sure we're doing the right thing...
   field!
   	:	// method, constructor, or variable declaration
       		mods:modifiers
       		(td:typeDefinitionInternal[#mods]
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
                       |	v:variableDefinitions[#mods,#t] (s6:SEMI)?
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

constructorBody
    :   lc:LCURLY^ {#lc.setType(SLIST);}
		// Predicate might be slow but only checked once per constructor def
		// not for general methods.
		(	(explicitConstructorInvocation) => explicitConstructorInvocation
		|
		)
        (statement)*
        RCURLY
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
			(	t:"this"^ LPAREN argList RPAREN SEMI
				{#t.setType(CTOR_CALL);}

			|   s:"super"^ LPAREN argList RPAREN SEMI
                {#s.setType(SUPER_CTOR_CALL);}
			)

		|	// (new Outer()).super()  (create enclosing instance)
			primaryExpression DOT (typeArguments[false])? s1:"super"^ LPAREN argList RPAREN SEMI
			{#s1.setType(SUPER_CTOR_CALL);}
		)
    ;

variableDefinitions[AST mods, AST t]
	:	variableDeclarator[(AST) getASTFactory().dupTree(mods),
						   (AST) getASTFactory().dupList(t)] //dupList as this also copies siblings (like TYPE_ARGUMENTS)
		(	COMMA
			variableDeclarator[(AST) getASTFactory().dupTree(mods),
							   (AST) getASTFactory().dupList(t)] //dupList as this also copies siblings (like TYPE_ARGUMENTS)
		)*
	;

/** Declaration of a variable.  This can be a class/instance variable,
 *   or a local variable in a method
 * It can also include possible initialization.
 */
variableDeclarator![AST mods, AST t]
	:	id:IDENT d:declaratorBrackets[t] v:varInitializer
		{#variableDeclarator = #(#[VARIABLE_DEF,"VARIABLE_DEF"], mods, #(#[TYPE,"TYPE"],d), id, v);}
	;

declaratorBrackets[AST typ]
	:	{#declaratorBrackets=typ;}
		(lb:LBRACK^ {#lb.setType(ARRAY_DECLARATOR);} RBRACK)*
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
					COMMA initializer
				)*
			)?
			(COMMA)?
		RCURLY
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
ctorHead
	:	IDENT  // the name of the method

		// parse the formal parameter declarations.
		LPAREN parameterDeclarationList RPAREN

		// get the list of exceptions that this method is declared to throw
		(throwsClause)?
	;

// This is a list of exception classes that the method is declared to throw
throwsClause
	:	"throws"^ ({LA(1) == AT}? annotations 
                    | ) identifier ( COMMA identifier )*
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
	:	pm:parameterModifier t:typeSpec[false] td:ELLIPSIS IDENT
		pd:declaratorBrackets[#t]
		{#variableLengthParameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"],
                                                pm, #([TYPE,"TYPE"],pd), td, IDENT);}
    ;

parameterModifier
    //final can appear amongst annotations in any order - greedily consume any preceding
    //annotations to shut nond-eterminism warnings off
	:	(options{greedy=true;} : annotation)* (f:"final")? (options {warnWhenFollowAmbig=false;}: annotation)*
		{#parameterModifier = #(#[MODIFIERS,"MODIFIERS"], #parameterModifier);}
    ;

// A formal parameter.
parameterDeclaration!
	:	pm:parameterModifier (t:typeSpec[false])? id:IDENT
		pd:declaratorBrackets[#t]
		{#parameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"],
									pm, #([TYPE,"TYPE"],pd), id);}
	;
//Added for support Java7's "multi-catch", several types separated by '|'
catchParameterDeclaration!
    :   pm:parameterModifier mct:multiCatchTypes id:IDENT
            {#catchParameterDeclaration = #(#[PARAMETER_DEF,"PARAMETER_DEF"], pm, #([TYPE,"TYPE"],mct), id);}
    ;

multiCatchTypes
	: typeSpec[false] (BOR^ typeSpec[false])*;

// Compound statement.  This is used in many contexts:
//   Inside a class definition prefixed with "static":
//      it is a class initializer
//   Inside a class definition without "static":
//      it is an instance initializer
//   As the body of a method
//   As a completely independent braced block of code inside a method
//      it starts a new scope for variable definitions

compoundStatement
	:	lc:LCURLY^ {#lc.setType(SLIST);}
			// include the (possibly-empty) list of statements
			(statement)*
		RCURLY
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

// a traditional (JDK < 1.4) java statement, assert keyword is not allowed
traditionalStatement
	// A list of statements in curly braces -- start a new scope!
	:	compoundStatement

		// declarations are ambiguous with "ID DOT" relative to expression
		// statements.  Must backtrack to be sure.  Could use a semantic
		// predicate to test symbol table to see what the type was coming
		// up, but that's pretty hard without a symbol table ;)
		|	(declaration)=> declaration SEMI

		// An expression statement.  This could be a method call,
		// assignment statement, or any other expression evaluated for
		// side-effects.
		|	{LA(2) != COLON}? expression (SEMI)?

		// class definition
		|	m:modifiers! classDefinition[#m]

		// Attach a label to the front of a statement
		|	IDENT c:COLON^ {#c.setType(LABELED_STAT);} statement

		// If-else statement
		|	"if"^ LPAREN expression RPAREN statement
			(
				// CONFLICT: the old "dangling-else" problem...
				//           ANTLR generates proper code matching
				//			 as soon as possible.  Hush warning.
				options {
					warnWhenFollowAmbig = false;
				}
			:
				elseStatement
		)?

		// For statement
		|	forStatement

		// While statement
		|	"while"^ LPAREN expression RPAREN statement

		// do-while statement
		|	"do"^ statement w:"while" {#w.setType(DO_WHILE);} LPAREN expression RPAREN SEMI

		// get out of a loop (or switch)
		|	"break"^ (IDENT)? SEMI

		// do next iteration of a loop
		|	"continue"^ (IDENT)? SEMI

		// Return an expression
		|	"return"^ (expression)? SEMI

		// switch/case statement
		|	"switch"^ LPAREN expression RPAREN LCURLY
				( casesGroup )*
			RCURLY

		// exception try-catch block
		|	tryBlock

		// throw an exception
		|	"throw"^ expression SEMI

		// synchronize a statement
		|	"synchronized"^ LPAREN expression RPAREN compoundStatement

		// empty statement
		|	s:SEMI {#s.setType(EMPTY_STAT);}
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
        forEachDeclarator COLON expression
        {#forEachClause = #(#[FOR_EACH_CLAUSE,"FOR_EACH_CLAUSE"], #forEachClause);}
    ;

forEachDeclarator!
	:	m:modifiers t:typeSpec[false] id:IDENT d:declaratorBrackets[#t]
		{#forEachDeclarator = #(#[VARIABLE_DEF,"VARIABLE_DEF"], m, #(#[TYPE,"TYPE"],d), id);}
	;

elseStatement
    : "else"^ statement
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
		(caseSList)?
		{#casesGroup = #([CASE_GROUP, "CASE_GROUP"], #casesGroup);}
	;

aCase
	:	("case"^ expression | "default"^) COLON
	;

caseSList
	:	
		(
			//Here was nondeterministic warnig between default block into switch and default modifier
			 //on methods (Java8). But we have semantic check for this.
			options {
				warnWhenFollowAmbig = false;
			}
			:
			{LA(1)!=LITERAL_default}?
				statement
        )+
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
// (Dinesh Bolkensteyn): Added support for Java 7 try-with-resources
tryBlock
	:	"try"^
	    
	    // try-with-resources
	    (resourceSpecification)?
	    
	    compoundStatement
		(handler)*
		( finallyHandler )?
	;

resourceSpecification
	: LPAREN resources (SEMI)? RPAREN
	  {#resourceSpecification = #([RESOURCE_SPECIFICATION, "RESOURCE_SPECIFICATION"], #resourceSpecification);}
	;
	
resources
	: resource (SEMI resource)*
	  {#resources = #([RESOURCES, "RESOURCES"], #resources);}
	;


resource
	: modifiers typeSpec[true] IDENT resource_assign
	  {#resource = #([RESOURCE, "RESOURCE"], #resource);}
;
 
resource_assign
	: ASSIGN^ expression
	;

// an exception handler
handler
	:	"catch"^ LPAREN catchParameterDeclaration RPAREN compoundStatement
	;

finallyHandler
    : "finally"^ compoundStatement
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
	:	(lambdaExpression) => lambdaExpression
    |   {LA(1)!=RPAREN}? assignmentExpression
		{#expression = #(#[EXPR,"EXPR"],#expression);}
	;


// This is a list of expressions.
expressionList
	:	expression (COMMA expression)*
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
			((lambdaExpression)=>lambdaExpression
			| assignmentExpression)
		)?
	;


// conditional test (level 12)
conditionalExpression
	:	logicalOrExpression
		( QUESTION^ 
            ((lambdaExpression)=>lambdaExpression
                | assignmentExpression)
         COLON ((lambdaExpression)=>lambdaExpression
                | conditionalExpression) )?
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
	:	shiftExpression ( "instanceof"^ typeSpec[true])?
		(	(options{warnWhenFollowAmbig=false;} : 	(	LT^
				|	GT^
				|	LE^
				|	GE^
				)
				shiftExpression
			)*
		
		)
	;


// bit shift expressions (level 4)
shiftExpression
	:	additiveExpression ((SL^ | SR^ | BSR^) additiveExpression)*
	;


// binary addition/subtraction (level 3)
additiveExpression
	:	multiplicativeExpression (options{warnWhenFollowAmbig=false;} : (PLUS^ | MINUS^) multiplicativeExpression)*
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression
	:	unaryExpression ((STAR^ | DIV^ | MOD^ ) unaryExpression)*
	;

unaryExpression
	:	INC^ unaryExpression
	|	DEC^ unaryExpression
	|	MINUS^ {#MINUS.setType(UNARY_MINUS);} unaryExpression
	|	PLUS^  {#PLUS.setType(UNARY_PLUS);} unaryExpression
	|	unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus
	:	BNOT^ unaryExpression
	|	LNOT^ unaryExpression

	|	(	// subrule allows option to shut off warnings
			options {
				// "(int" ambig with postfixExpr due to lack of sequence
				// info in linear approximate LL(k).  It's ok.  Shut up.
				generateAmbigWarnings=false;
			}
		:	// If typecast is built in type, must be numeric operand
			// Also, no reason to backtrack if type keyword like int, float...
			(LPAREN builtInTypeSpec[true] RPAREN unaryExpression) => 
            lpb:LPAREN^ {#lpb.setType(TYPECAST);} builtInTypeSpec[true] RPAREN
			unaryExpression

			// Have to backtrack to see if operator follows.  If no operator
			// follows, it's a typecast.  No semantic checking needed to parse.
			// if it _looks_ like a cast, it _is_ a cast; else it's a "(expr)"
		|	(LPAREN typeCastParameters RPAREN unaryExpressionNotPlusMinus)=>
			lp:LPAREN^ {#lp.setType(TYPECAST);} typeCastParameters RPAREN
			unaryExpressionNotPlusMinus

        |   (LPAREN typeCastParameters RPAREN lambdaExpression) =>
                lpl:LPAREN^ {#lpl.setType(TYPECAST);} typeCastParameters RPAREN
                lambdaExpression

		|	postfixExpression
		)
	;

typeCastParameters
	: classTypeSpec[true] (BAND^ classTypeSpec[true])*
	;

// TODO: handle type parameters more effectively - I think this production needs
// a refactoring like the original Antlr Java grammar got
// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
	:	primaryExpression // start with a primary

		(options{warnWhenFollowAmbig=false;} : 	// qualified id (id.id.id.id...) -- build the name
			DOT^
			( (typeArguments[false])?
			  ( IDENT ((typeArguments[false] DOUBLE_COLON)=>typeArguments[false])? 
			  | "this"
			  | "super" // ClassName.super.field
			  )
			| "class"
			| newExpression
			| annotations
			)

			//Java 8 method references. For example: List<Integer> numbers = Arrays.asList(1,2,3,4,5,6); numbers.forEach(System.out::println);
		|
			dc:DOUBLE_COLON^ {#dc.setType(METHOD_REF);}
			(
				(typeArguments[false])?
					(IDENT
				| LITERAL_new)
			)

			// the above line needs a semantic check to make sure "class"
			// is the _last_ qualifier.

			// allow ClassName[].class or just ClassName[]
		|	(options{warnWhenFollowAmbig=false;} : lbc:LBRACK^ {#lbc.setType(ARRAY_DECLARATOR);} RBRACK )+
			//Since java 8 here can be method reference
			(options{warnWhenFollowAmbig=false;} : DOT^ "class")?

			// an array indexing operation
		|	lb:LBRACK^ {#lb.setType(INDEX_OP);} expression RBRACK

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

// the basic element of an expression
primaryExpression
	:   IDENT ((typeArguments[false] DOUBLE_COLON)=>typeArguments[false])? 
	|	constant
	|	"true"
	|	"false"
	|	"this"
	|	"null"
	|	newExpression
	|	LPAREN ((lambdaExpression)=>lambdaExpression | assignmentExpression) RPAREN
	|	"super"
		// look for int.class and int[].class and int[]
	|	builtInType
		(options{warnWhenFollowAmbig=false;} : lbt:LBRACK^ {#lbt.setType(ARRAY_DECLARATOR);} RBRACK )*
		//Since java 8 here can be method reference
		(options{warnWhenFollowAmbig=false;} : DOT^ "class")?
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

argList
	:	(	{LA(1)!=RPAREN}? expressionList
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
			RBRACK
		)+
	;

constant
	:	NUM_INT
	|   NUM_LONG
	|   NUM_FLOAT
	|   NUM_DOUBLE
	|	CHAR_LITERAL
	|	STRING_LITERAL
	;

lambdaExpression
	:	lambdaParameters LAMBDA^ lambdaBody
	;

lambdaParameters
	:	IDENT
	|	LPAREN (parameterDeclarationList)? RPAREN
	|	LPAREN inferredParameterList RPAREN
	;

lambdaBody
	:	(options{generateAmbigWarnings=false;}: expression 
	|	statement)
	;
inferredParameterList
	:	IDENT (COMMA IDENT)*
	;


//----------------------------------------------------------------------------
// The Java scanner
//----------------------------------------------------------------------------
class GeneratedJavaLexer extends Lexer;

options {
	exportVocab=GeneratedJava; // call the vocabulary "Java"
	testLiterals=false;        // don't automatically test for literals
	k=4;                       // four characters of lookahead
	charVocabulary='\u0000'..'\uFFFE';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	// bottom of JavaLexer.java
	codeGenBitsetTestThreshold=20;
}

// JavaLexer verbatim source code
{

    // explicitly set tab width to 1 (default in ANTLR 2.7.1)
    // in ANTLR 2.7.2a2 the default has changed from 1 to 8
    public void tab()
    {
        setColumn( getColumn() + 1 );
    }

    private CommentListener mCommentListener = null;

    // TODO: Check visibility of this method one parsing is done in central
    // utility method
    public void setCommentListener(CommentListener aCommentListener)
    {
        mCommentListener = aCommentListener;
    }

    private boolean mTreatAssertAsKeyword = true;

    public void setTreatAssertAsKeyword(boolean aTreatAsKeyword)
    {
        mTreatAssertAsKeyword = aTreatAsKeyword;
    }

    private boolean mTreatEnumAsKeyword = true;

    public void setTreatEnumAsKeyword(boolean aTreatAsKeyword)
    {
        mTreatEnumAsKeyword = aTreatAsKeyword;
    }

}


// OPERATORS
QUESTION		:	'?'		;
LPAREN			:	'('		;
RPAREN			:	')'		;
LBRACK			:	'['		;
RBRACK			:	']'		;
LCURLY			:	'{'		;
RCURLY			:	'}'		;
COLON			:	':'		;
DOUBLE_COLON	:	"::"	;
COMMA			:	','		;
//DOT			:	'.'		;
ASSIGN			:	'='		;
EQUAL			:	"=="	;
LNOT			:	'!'		;
BNOT			:	'~'		;
NOT_EQUAL		:	"!="	;
DIV				:	'/'		;
DIV_ASSIGN		:	"/="	;
PLUS			:	'+'		;
PLUS_ASSIGN		:	"+="	;
INC				:	"++"	;
MINUS			:	'-'		;
MINUS_ASSIGN	:	"-="	;
DEC				:	"--"	;
STAR			:	'*'		;
STAR_ASSIGN		:	"*="	;
MOD				:	'%'		;
MOD_ASSIGN		:	"%="	;
SR				:	">>"	;
SR_ASSIGN		:	">>="	;
BSR				:	">>>"	;
BSR_ASSIGN		:	">>>="	;
GE				:	">="	;
GT				:	">"		;
SL				:	"<<"	;
SL_ASSIGN		:	"<<="	;
LE				:	"<="	;
LT				:	'<'		;
LAMBDA          :   "->"    ;
BXOR			:	'^'		;
BXOR_ASSIGN		:	"^="	;
BOR				:	'|'		;
BOR_ASSIGN		:	"|="	;
LOR				:	"||"	;
BAND			:	'&'		;
BAND_ASSIGN		:	"&="	;
LAND			:	"&&"	;
SEMI			:	';'		;

//token signifying annotations and annotation declaration
AT
    :   '@'
    ;

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
SINGLE_LINE_COMMENT
    :	"//"
        { mCommentListener.reportSingleLineComment("//", getLine(),
                                                   getColumn() - 3); }
        content: SINGLE_LINE_COMMENT_CONTENT
        { $setText(content.getText());}
	;

protected
SINGLE_LINE_COMMENT_CONTENT
    :   (~('\n'|'\r'))* ('\n'|'\r'('\n')?|)
        {newline();}
        ;

// block comments
BLOCK_COMMENT_BEGIN
{
   int startLine = -1;
   int startCol = -1;
}
	:	"/*"  { startLine = getLine(); startCol = getColumn() - 3; }
		content:BLOCK_COMMENT_CONTENT
		"*/"
      {
         mCommentListener.reportBlockComment("/*", startLine, startCol,
                            getLine(), getColumn() - 2);
         $setText(content.getText());
      }
	;

protected
BLOCK_COMMENT_CONTENT
    :   (   /*  '\r' '\n' can be matched in one alternative or by matching
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
        |   '\r' '\n'       {newline();}
        |   '\r'            {newline();}
        |   '\n'            {newline();}
        |   ~('*'|'\n'|'\r')
        )*
    ;

// character literals
CHAR_LITERAL
	:	'\'' ( ESC | ~'\'' ) '\''
	;

// string literals
STRING_LITERAL
    :   '"' ( ESC | ~'"' )* '"'
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
		(
			('u')+
			(options { generateAmbigWarnings=false; }
			:	'0' '0' '5' ('c'|'C')
				(options { generateAmbigWarnings=false; }
				:	'\\' ('u')+ '0' '0' '5' ('c'|'C')
				|	STD_ESC
				)
			|	HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			)
		|
			STD_ESC
		)
	;


protected
STD_ESC
	:	'n'
	|	'r'
	|	't'
	|	'b'
	|	'f'
	|	'"'
	|	'\''
	|	'\\'
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
	;

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;
	
// binary digit (again, note it's protected!)
protected
BINARY_DIGIT
	:	('0'|'1')
	;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
	:	'\3'..'\377'
	;

protected ID_START:
        '_' | '$' | 
        (            
            {Character.isJavaIdentifierStart(LA(1))}?
            ~(
                '_' | '$' | '/' | '*' | '0'..'9' | 
                '.' | '\'' | '\\' | '"' | '\t' | '\n' | 
                '\r' | ' ' | '\f' | '(' | ')' |
                '{' | '}' | '[' | ']'| ';' | ',' | '=' |
                '+' | '~' | '&' | '<' | '>' | '-' | '!' |
                '^' | '%' | ':' | '?' | '|'| '@'
            )
        )
    ;
    exception
    catch[SemanticException ex]
    {
            throw new SemanticException(
                MessageFormat.format(
                    "Unexpected character {0} in identifier",
                    new Object[] {"0x" + Integer.toHexString(LA(1))}),
                getFilename(), getLine(), getColumn());
    }

protected ID_PART :
        '_' | '$' | 
        (
            {Character.isJavaIdentifierPart(LA(1))}?
            ~(
                '_' | '$' | '/' | '*' |
                '.' | '\'' | '\\' | '"' | '\t' | '\n' | 
                '\r' | ' ' | '\f' | '(' | ')' |
                '{' | '}' | '[' | ']'| ';' | ',' | '=' |
                '+' | '~' | '&' | '<' | '>' | '-' | '!' |
                '^' | '%' | ':' | '?' | '|' | '@'
            )
        )
        ;
        exception
        catch[SemanticException ex]
        {
            throw new SemanticException(
                MessageFormat.format(
                    "Unexpected character {0} in identifier",
                    new Object[] {"0x" + Integer.toHexString(LA(1))}),
                getFilename(), getLine(), getColumn());
        }

// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer. As enum and assert
// are purposefully not part of the literal list, we do manual tests on
// the ident to test whether this should be an ENUM or ASSERT token.
// This behaviour is controlled by the treatAssertAsKeyword and
// treatEnumAsKeyword boolean properties on the lexer
IDENT
	options {testLiterals=true;}
	:   ID_START (ID_PART)*
        {
			if (mTreatAssertAsKeyword && "assert".equals($getText)) {
				$setType(ASSERT);
			}
			if (mTreatEnumAsKeyword && "enum".equals($getText)) {
				$setType(ENUM);
			}
        }
	;

//overriden definition of this lexer rule to recognize the ... token - for
//variable argument length
NUM_INT
      :   (ELLIPSIS)=>ELLIPSIS {$setType(ELLIPSIS);}
      |   (DOT)=>DOT {$setType(DOT);}
      |   (DOUBLE_LITERAL)=>DOUBLE_LITERAL {$setType(NUM_DOUBLE);}
      |   (FLOAT_LITERAL)=>FLOAT_LITERAL {$setType(NUM_FLOAT);}
      |   (HEX_DOUBLE_LITERAL)=>HEX_DOUBLE_LITERAL {$setType(NUM_DOUBLE);}
      |   (HEX_FLOAT_LITERAL)=>HEX_FLOAT_LITERAL {$setType(NUM_FLOAT);}
      |   (LONG_LITERAL)=>LONG_LITERAL {$setType(NUM_LONG);}
      |   (INT_LITERAL)=>INT_LITERAL {$setType(NUM_INT);}
      ;

protected INT_LITERAL
    :   (    '0'
             (  ('x'|'X')(HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?              // Hexa
             |  ('b'|'B')(BINARY_DIGIT)((BINARY_DIGIT|'_')*(BINARY_DIGIT))?     // Binary
             |  ((('0'..'7')|'_')*('0'..'7'))?                                  // If empty 0, otherwise octal (which may start with an underscore)
             )
        |   ('1'..'9') (('0'..'9'|'_')*('0'..'9'))?                             // Non-zero decimal
        )
    ;

protected LONG_LITERAL
    :   (    '0'
             (  ('x'|'X')(HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?              // Hexa
             |  ('b'|'B')(BINARY_DIGIT)((BINARY_DIGIT|'_')*(BINARY_DIGIT))?     // Binary
             |  ((('0'..'7')|'_')*('0'..'7'))?                                  // If empty 0, otherwise octal (which may start with an underscore)
             )
        |   ('1'..'9') (('0'..'9'|'_')*('0'..'9'))?                             // Non-zero decimal
        )
        // long signifier
        ('l'|'L')
    ;

protected FLOAT_LITERAL
    :   (
            ((('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)? '.')=>
            (   (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?) '.' (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)?
            |   '.' (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)
            )
            (EXPONENT)? ('f'|'F')?
        |
            (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?) ((EXPONENT ('f'|'F')?) | ('f'|'F'))
        )
    ;

protected DOUBLE_LITERAL
    :   (
            ((('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)? '.')=>
            (   (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?) '.' (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)?
            |   '.' (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)
            )
        |
            (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)
        )
        (EXPONENT)? ('d'|'D')
    ;

protected HEX_FLOAT_LITERAL
    :   '0' ('x'|'X')
        (
            (((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)? '.')=>
            (   ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?) '.' ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)?
            |   '.' ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)
            )
        |
            ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)
        )
        BINARY_EXPONENT ('f'|'F')?
    ;

protected HEX_DOUBLE_LITERAL
    :   '0' ('x'|'X')
        (
            (((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)? '.')=>
            (   ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?) '.' ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)?
            |   '.' ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)
            )
        |
            ((HEX_DIGIT)((HEX_DIGIT|'_')*(HEX_DIGIT))?)
        )
        BINARY_EXPONENT ('d'|'D')
    ;

protected ELLIPSIS
    :   "..."
    ;

protected DOT
    :   '.'
    ;

// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') SIGNED_INTEGER
	;

protected
SIGNED_INTEGER
    :   ('+'|'-')? (('0'..'9')(('0'..'9'|'_')*('0'..'9'))?)
    ;

protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;

protected
BINARY_EXPONENT
    :   ('p'|'P') SIGNED_INTEGER
    ;
