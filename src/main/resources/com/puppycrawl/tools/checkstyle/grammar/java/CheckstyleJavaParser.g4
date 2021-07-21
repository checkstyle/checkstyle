/*
 [The "BSD licence"]
 Copyright (c) 2013 Terence Parr, Sam Harwell
 Copyright (c) 2017 Ivan Kochurkin (upgrade to Java 8)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCidAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

parser grammar CheckstyleJavaParser;

options { tokenVocab=JavaLexer; }

@parser::members {
    /**
    * This value tracks the depth of a switch expression. Along with the
    * IDENT to id rule at the end of the parser, this value helps us
    * to know if the "yield" we are parsing is an IDENT, method call, class,
    * field, etc. or if it is a java 13+ yield statement. Positive values
    * indicate that we are within a (possibly nested) switch expression.
    */
    private int switchBlockDepth = 0;

    /**
     * Checks if next statement is a yield statement.
     *
     * @return true if next statement is a yield statement.
     */
    private boolean isYieldStatement() {
        return _input.LT(1).getType() == JavaLexer.LITERAL_yield && switchBlockDepth > 0;
    }
}

compilationUnit
    : packageDeclaration? importDeclaration* typeDeclaration* EOF
    ;

packageDeclaration
    : annotations[true] LITERAL_package qualifiedName SEMI
    ;

importDeclaration
    : IMPORT LITERAL_static? qualifiedName (DOT STAR)? SEMI    #importDec
    | SEMI                                                     #singleSemiImport
    ;

typeDeclaration
    : mods+=modifier*
      type=types[$ctx.mods]
    | semi+=SEMI+
    ;

types[List<ModifierContext> mods]
    : classDeclaration[mods]
    | enumDeclaration[mods]
    | interfaceDeclaration[mods]
    | annotationTypeDeclaration[mods]
    | recordDeclaration[mods]
    ;


modifier
    : annotation
    | LITERAL_public
    | LITERAL_protected
    | LITERAL_private
    | LITERAL_static
    | ABSTRACT
    | LITERAL_default
    | FINAL
    | STRICTFP
    | LITERAL_native
    | LITERAL_synchronized
    | LITERAL_transient
    | LITERAL_volatile
    | LITERAL_non_sealed
    | LITERAL_sealed
    ;

variableModifier
    : FINAL
    | annotation
    ;

classDeclaration[List<ModifierContext> mods]
    : LITERAL_class id typeParameters?
      classExtends?
      implementsClause?
      permittedSubclassesAndInterfaces?
      classBody
    ;

recordDeclaration[List<ModifierContext> mods]
    : LITERAL_record id typeParameters?
      recordComponentsList
      implementsClause?
      recordBody
    ;

recordComponentsList
    : LPAREN recordComponents? RPAREN
    ;

recordComponents
    : recordComponent (COMMA recordComponent)* (COMMA lastRecordComponent)?
    | lastRecordComponent
    ;

recordComponent
    : annotations[true] type=typeType[true]
      id
    ;

lastRecordComponent
    : annotations[true] type=typeType[true]
      ELLIPSIS id
    ;

recordBody
    : LCURLY recordBodyDeclaration* RCURLY
    ;

recordBodyDeclaration
    : compactConstructorDeclaration
    | classBodyDeclaration
    ;

compactConstructorDeclaration
    : mods+=modifier* id constructorBlock
    ;

classExtends
    : EXTENDS_CLAUSE type=typeType[false]
    ;

implementsClause
    : LITERAL_implements typeList
    ;

typeParameters
    : LT typeParameter (COMMA typeParameter)* GT
    ;

typeParameter
    : annotations[false] id typeUpperBounds?
    ;

typeUpperBounds
    : EXTENDS_CLAUSE annotations[false] typeBound
    ;

typeBound
    : typeBoundType (BAND typeBoundType)*
    ;

typeBoundType
    : annotations[false] classOrInterfaceOrPrimitiveType arrayDeclarator*
    ;

enumDeclaration[List<ModifierContext> mods]
    : ENUM id implementsClause?
      enumBody
    ;

enumBody
    : LCURLY enumConstants? COMMA? enumBodyDeclarations? RCURLY
    ;

enumConstants
    : enumConstant (COMMA enumConstant)*
    ;

enumConstant
    : annotations[true] id arguments? classBody?
    ;

enumBodyDeclarations
    : SEMI classBodyDeclaration*
    ;

interfaceDeclaration[List<ModifierContext> mods]
    : LITERAL_interface id typeParameters?
      interfaceExtends?
      permittedSubclassesAndInterfaces?
      interfaceBody
    ;

interfaceExtends
    : EXTENDS_CLAUSE typeList
    ;

classBody
    : LCURLY classBodyDeclaration* RCURLY
    ;

interfaceBody
    : LCURLY interfaceBodyDeclaration* RCURLY
    ;

classBodyDeclaration
    : SEMI                                                                 #emptyClass
    | LITERAL_static? block                                                #classBlock
    | mods+=modifier*
      memberDeclaration[{((ClassDefContext) _localctx).mods}]              #classDef
    ;

memberDeclaration[List<ModifierContext> mods]
    : recordDeclaration[mods]
    | methodDeclaration[mods]
    | fieldDeclaration[mods]
    | constructorDeclaration[mods]
    | interfaceDeclaration[mods]
    | annotationTypeDeclaration[mods]
    | classDeclaration[mods]
    | enumDeclaration[mods]
    ;

methodDeclaration[List<ModifierContext> mods]
    : typeParams=typeParameters? type=typeType[true] id
      formalParameters (cStyleArrDec+=arrayDeclarator*)
      throwsList?
      methodBody
    ;

methodBody
    : block
    | SEMI
    ;

throwsList
    : LITERAL_throws qualifiedNameList
    ;

constructorDeclaration[List<ModifierContext> mods]
    : typeParameters? id formalParameters
      throwsList? constructorBody=constructorBlock
    ;

fieldDeclaration[List<ModifierContext> mods]
    : type=typeType[true]
      variableDeclarators[$mods, $ctx.type]
      SEMI
    ;

interfaceBodyDeclaration
    : mods+=modifier* interfaceMemberDeclaration[$ctx.mods]
    | SEMI
    ;

interfaceMemberDeclaration[List<ModifierContext> mods]
    : fieldDeclaration[mods]
    | interfaceMethodDeclaration[mods]
    | interfaceDeclaration[mods]
    | annotationTypeDeclaration[mods]
    | classDeclaration[mods]
    | recordDeclaration[mods]
    | enumDeclaration[mods]
    ;


// Early versions of Java allows brackets after the method name, eg.
// public int[] return2DArray() [] { ... }
// is the same as
// public int[][] return2DArray() { ... }
interfaceMethodDeclaration[List<ModifierContext> mods]
    : typeParameters?
      type=typeType[true]
      id formalParameters
      cStyleArrDec+=arrayDeclarator*
      throwsList? methodBody
    ;

variableDeclarators[List<ModifierContext> mods, TypeTypeContext type]
    : variableDeclarator[mods, type] (COMMA variableDeclarator[mods, type])*
    ;

variableDeclarator[List<ModifierContext> mods, TypeTypeContext type]
    : id arrayDeclarator* (ASSIGN variableInitializer)?
    ;

variableDeclaratorId[List<VariableModifierContext> mods, ParserRuleContext type]
    : (LITERAL_this | (id (DOT LITERAL_this)?)) arrayDeclarator*
    ;

variableInitializer
    : arrayInitializer
    | expression
    ;

arrayInitializer
    : LCURLY
      (variableInitializer (COMMA variableInitializer)*)? COMMA?
      RCURLY
    ;

classOrInterfaceType[boolean createImaginaryNode]
    : annotations[false] id typeArguments?
      extended+=classOrInterfaceTypeExtended*
    ;

classOrInterfaceTypeExtended
    : DOT annotations[false] id typeArguments?
    ;

typeArgument
    : typeType[true]                                                       #simpleTypeArgument
    | annotations[false] QUESTION
        (
          ( upperBound=EXTENDS_CLAUSE | lowerBound=LITERAL_super )
          typeType[true]
        )?                                                                 #wildCardTypeArgument
    ;

qualifiedNameList
    : annotations[false]
      qualifiedName (COMMA annotations[false] qualifiedName)*
    ;

formalParameters
    : LPAREN formalParameterList? RPAREN
    ;

formalParameterList
    : formalParameter (COMMA formalParameter)* (COMMA lastFormalParameter)?
    | lastFormalParameter
    ;

formalParameter
    : mods+=variableModifier* type=typeType[true]
      variableDeclaratorId[$ctx.mods, $ctx.type]
    ;

lastFormalParameter
    : mods+=variableModifier* type=typeType[true] annotations[false]
      ELLIPSIS variableDeclaratorId[$ctx.mods, $ctx.type]
    ;

qualifiedName
    : id (DOT id)*
    ;

literal
    : integerLiteral
    | floatLiteral
    | textBlockLiteral
    | CHAR_LITERAL
    | STRING_LITERAL
    | LITERAL_true
    | LITERAL_false
    | LITERAL_null
    ;

integerLiteral
    : DECIMAL_LITERAL_LONG
    | DECIMAL_LITERAL
    | HEX_LITERAL_LONG
    | HEX_LITERAL
    | OCT_LITERAL_LONG
    | OCT_LITERAL
    | BINARY_LITERAL_LONG
    | BINARY_LITERAL
    ;

floatLiteral
    : DOUBLE_LITERAL
    | FLOAT_LITERAL
    | HEX_DOUBLE_LITERAL
    | HEX_FLOAT_LITERAL
    ;

textBlockLiteral
    : TEXT_BLOCK_LITERAL_BEGIN TEXT_BLOCK_CONTENT TEXT_BLOCK_LITERAL_END
    ;

annotations[boolean createImaginaryNode]
    : anno+=annotation*
    ;

annotation
    : AT qualifiedName
      (LPAREN ( elementValuePairs | elementValue )? RPAREN)?
    ;

elementValuePairs
    : elementValuePair (COMMA elementValuePair)*
    ;

elementValuePair
    : id ASSIGN elementValue
    ;

elementValue
    : expression
    | annotation
    | elementValueArrayInitializer
    ;

elementValueArrayInitializer
    : LCURLY (elementValue (COMMA elementValue)*)? (COMMA)? RCURLY
    ;

annotationTypeDeclaration[List<ModifierContext> mods]
    : AT LITERAL_interface id annotationTypeBody
    ;

annotationTypeBody
    : LCURLY (annotationTypeElementDeclaration)* RCURLY
    ;

annotationTypeElementDeclaration
    : mods+=modifier* annotationTypeElementRest[$ctx.mods]
    | SEMI // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest[List<ModifierContext> mods]
    : type=typeType[true]
      ( annotationMethodRest[mods, ((AnnotationFieldContext) _localctx).type]
      | annotationConstantRest[mods, ((AnnotationFieldContext) _localctx).type]
      )
      SEMI                                                                 #annotationField
    | type=typeType[true]  SEMI                                            #annotationType
    | classDeclaration[mods] SEMI?                                         #annotationType
    | recordDeclaration[mods] SEMI?                                        #annotationType
    | interfaceDeclaration[mods] SEMI?                                     #annotationType
    | enumDeclaration[mods] SEMI?                                          #annotationType
    | annotationTypeDeclaration[mods] SEMI?                                #annotationType
    ;

annotationMethodRest[List<ModifierContext> mods, TypeTypeContext type]
    : id LPAREN RPAREN cStyleArrDec+=arrayDeclarator* defaultValue?
    ;

annotationConstantRest[List<ModifierContext> mods, TypeTypeContext type]
    : variableDeclarators[mods, type]
    ;

defaultValue
    : LITERAL_default elementValue
    ;

// STATEMENTS / BLOCKS
constructorBlock
    : LCURLY
      explicitConstructorInvocation?
      blockStatement*
      RCURLY
    ;

explicitConstructorInvocation
    : typeArguments?
      (LITERAL_this | LITERAL_super)
      arguments SEMI                                                       #explicitCtorCall
    | expr DOT typeArguments? LITERAL_super arguments SEMI                 #primaryCtorCall
    ;

block
    : LCURLY blockStatement* RCURLY
    ;

blockStatement
    : {!isYieldStatement()}? localVariableDeclaration SEMI                 #localVar
    | statement                                                            #stat
    | localTypeDeclaration                                                 #localType
    ;

localVariableDeclaration
    : mods+=modifier* type=typeType[true]
      variableDeclarators[$ctx.mods, $ctx.type]
    ;

localTypeDeclaration
    : mods+=modifier*
      ( classDeclaration[$ctx.mods]
      | enumDeclaration[$ctx.mods]
      | interfaceDeclaration[$ctx.mods]
      | recordDeclaration[$ctx.mods]
      )
    | SEMI
    ;

statement
    : blockLabel=block                                                     #blockStat
    | ASSERT expression (COLON expression)? SEMI                           #assertExp
    | LITERAL_if parExpression statement elseStat?                         #ifStat
    | LITERAL_for forControl statement                                     #forStat
    | LITERAL_while parExpression statement                                #whileStat
    | LITERAL_do statement LITERAL_while parExpression SEMI                #doStat
    | LITERAL_try block (catchClause+ finallyBlock? | finallyBlock)        #tryStat
    | LITERAL_try resourceSpecification block catchClause* finallyBlock?   #tryWithResourceStat
    | LITERAL_yield expression SEMI                                        #yieldStat
    | switchExpressionOrStatement                                          #switchStat
    | LITERAL_synchronized parExpression block                             #syncStat
    | LITERAL_return expression? SEMI                                      #returnStat
    | LITERAL_throw expression SEMI                                        #throwStat
    | LITERAL_break id? SEMI                                               #breakStat
    | LITERAL_continue id? SEMI                                            #continueStat
    | SEMI                                                                 #emptyStat
    | statementExpression=expression SEMI                                  #expStat
    | id COLON statement                                                   #labelStat
    ;

switchExpressionOrStatement
    : LITERAL_switch parExpression LCURLY
      {switchBlockDepth++;}
      switchBlock
      {switchBlockDepth--;}
      RCURLY
    ;

switchBlock
    : switchLabeledRule+                                                   #switchRules
    | groups+=switchBlockStatementGroup* emptyLabels+=switchLabel*         #switchBlocks
    ;

switchLabeledRule
    : switchLabeledExpression
    | switchLabeledBlock
    | switchLabeledThrow
    ;

switchLabeledExpression
    : switchLabel LAMBDA expression SEMI
    ;

switchLabeledBlock
    : switchLabel LAMBDA block
    ;

switchLabeledThrow
    : switchLabel LAMBDA LITERAL_throw expression SEMI
    ;

elseStat
    : LITERAL_else statement
    ;

catchClause
    : LITERAL_catch LPAREN catchParameter RPAREN block
    ;

catchParameter
    : mods+=variableModifier* catchType id
    ;

catchType
    : classOrInterfaceType[false] (BOR classOrInterfaceType[false])*
    ;

finallyBlock
    : LITERAL_finally block
    ;

resourceSpecification
    : LPAREN resources SEMI? RPAREN
    ;

resources
    : resource (SEMI resource)*
    ;

resource
    : resourceDeclaration | variableAccess
    ;

resourceDeclaration
    : mods+=variableModifier* type=classOrInterfaceType[true]
      variableDeclaratorId[$ctx.mods, $ctx.type] ASSIGN expression
    ;

variableAccess
    : accessList+=fieldAccessNoIdent* id
    ;

fieldAccessNoIdent
    : expr DOT
    ;

/**
 * Matches cases then statements, both of which are mandatory.
 * To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    : switchLabel+ slists+=blockStatement+
    ;

switchLabel
    : LITERAL_case caseConstants COLON?                                   #caseLabel
    | LITERAL_default COLON?                                              #defaultLabel
    ;

caseConstants
    : expression (COMMA expression)*
    ;

forControl
    : LPAREN enhancedForControl RPAREN                                     #enhancedFor
    | LPAREN forInit? SEMI forCond=expression?
      SEMI forUpdate=expressionList? RPAREN                                #forFor
    ;

forInit
    : localVariableDeclaration
    | expressionList
    ;

enhancedForControl
    : mods+=variableModifier* type=typeType[true]
      variableDeclaratorId[$ctx.mods, $ctx.type] COLON expression
    ;

// EXPRESSIONS

parExpression
    : LPAREN expression RPAREN
    ;

expressionList
    : startExp=expression (COMMA expression)*
    ;

methodCall
    : id LPAREN expressionList? RPAREN
    ;

// We will use this rule to make sure that we have an EXPR node
// at the top of all expression subtrees.
expression
    : expr
    ;

expr
    : primary                                                              #primaryExp
    | expr bop=DOT id                                                      #refOp
    | expr bop=DOT mCall=methodCall                                        #methodExp
    | expr bop=DOT LITERAL_this                                            #binOp
    | expr bop=DOT LITERAL_new nonWildcardTypeArguments?
      innerCreator                                                         #initExp
    | expr bop=DOT nonWildcardTypeArguments?
      LITERAL_super superSuffix?                                           #superExp
    | expr bop=DOT
      nonWildcardTypeArguments
      id LPAREN expressionList? RPAREN                                     #invOp
    | expr LBRACK expr RBRACK                                              #indexOp
    | mCall=methodCall                                                     #simpleMethodExp
    | LITERAL_new creator                                                  #newExp
    | LPAREN typeCastParameters RPAREN expr (INC | DEC)?                   #castExp
    | expr postfix=(INC | DEC)                                             #postfix
    | prefix=(PLUS | MINUS | INC | DEC) expr                               #prefix
    | prefix=(BNOT | LNOT) expr                                            #prefix
    | expr bop=(STAR | DIV | MOD) expr                                     #binOp
    | expr bop=(PLUS|MINUS) expr                                           #binOp
    // handle bitwise shifts below, not in lexer
    | expr (LT LT | GT GT GT | GT GT) expr                                 #bitOp
    | expr bop=LITERAL_instanceof (patternDefinition | typeType[true])     #instanceOfExp
    | expr bop=(LE | GE | GT | LT) expr                                    #binOp
    | expr bop=(EQUAL | NOT_EQUAL) expr                                    #binOp
    | expr bop=BAND expr                                                   #binOp
    | expr bop=BXOR expr                                                   #binOp
    | expr bop=BOR expr                                                    #binOp
    | expr bop=LAND expr                                                   #binOp
    | expr bop=LOR expr                                                    #binOp
    | <assoc=right> expr bop=QUESTION expr COLON expr                      #ternaryOp
    | <assoc=right> expr
      bop=(ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | STAR_ASSIGN | DIV_ASSIGN
        | BAND_ASSIGN | BOR_ASSIGN | BXOR_ASSIGN | SR_ASSIGN | BSR_ASSIGN
        | SL_ASSIGN | MOD_ASSIGN)
      expr?
      // Below `methodReference` production exists because ANTLR will not
      // match this rule first for code like `s = Bar1::<List<String>> m`
      // otherwise
      methodReference?                                                     #binOp
    | lambdaExpression                                                     #lambdaExp

    // Java 8 methodReference
    | expr DOUBLE_COLON typeArguments? (id | LITERAL_new)               #methodRef
    | typeType[false] DOUBLE_COLON typeArguments? (id | LITERAL_new)    #methodRef
    | classType DOUBLE_COLON typeArguments? LITERAL_new                    #methodRef
    ;

methodReference
    : DOUBLE_COLON typeArguments? (id | LITERAL_new)
    | typeType[false] DOUBLE_COLON typeArguments? (id | LITERAL_new)
    | classType DOUBLE_COLON typeArguments? LITERAL_new
    ;

typeCastParameters
    : typeType[true] (BAND typeType[true])*
    ;

// Java8
lambdaExpression
    : lambdaParameters LAMBDA lambdaBody
    ;

// Java8
lambdaParameters
    : id                                                                   #singleLambdaParam
    | LPAREN formalParameterList? RPAREN                                   #formalLambdaParam
    | LPAREN multiLambdaParams RPAREN                                      #multiLambdaParam
    ;

multiLambdaParams
    : id (COMMA id)*
    ;

// Java8
lambdaBody
    : expression
    | block
    ;

primary
    : switchExpressionOrStatement                                   #switchPrimary
    | LPAREN expr RPAREN                                            #parenPrimary
    | LITERAL_this                                                  #tokenPrimary
    | LITERAL_super                                                 #tokenPrimary
    | literal                                                       #literalPrimary
    | id                                                            #tokenPrimary
    | type=primaryClassOrInterfaceTypeNoArray arrayDeclarator*
      DOT LITERAL_class                                             #classRefPrimary
    | type=primaryPrimitiveTypeNoArray arrayDeclarator*
      DOT LITERAL_class                                             #primitivePrimary
    | nonWildcardTypeArguments LITERAL_this arguments               #paramPrimary
    ;

primaryClassOrInterfaceTypeNoArray
    : annotations[false] classOrInterfaceType[false]
    ;

primaryPrimitiveTypeNoArray
    : annotations[false] primitiveType
    ;

classType
    : (classOrInterfaceType[false] DOT)? annotations[false] id typeArguments?
    ;

creator
    : nonWildcardTypeArguments createdName classCreatorRest
    | annotations[false] createdName
      (annotations[false] arrayCreatorRest | classCreatorRest)
    ;

createdName
    : id typeArgumentsOrDiamond? extended+=createdNameExtended*  #createdNameObject
    | primitiveType                                                 #createdNamePrimitive
    ;

createdNameExtended
    : DOT annotations[false] id typeArgumentsOrDiamond?
    ;

innerCreator
    : annotations[false] id nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    : LBRACK
      ( RBRACK arrayDeclarator* arrayInitializer
      | expression RBRACK bracketsWithExp* arrayDeclarator*
      )
    ;

bracketsWithExp
    : annotations[false] LBRACK expression RBRACK
    ;

classCreatorRest
    : arguments classBody?
    ;

typeArgumentsOrDiamond
    : LT GT                                 #diamond
    | typeArguments                         #typeArgs
    ;

nonWildcardTypeArgumentsOrDiamond
    : LT GT                                 #nonWildcardDiamond
    | nonWildcardTypeArguments              #nonWildcardTypeArgs
    ;

nonWildcardTypeArguments
    : LT typeArgumentsTypeList GT
    ;

typeArgumentsTypeList
    : typeType[false] (COMMA typeType[false])*
    ;

typeList
    : typeType[false] (COMMA typeType[false])*
    ;

typeType[boolean createImaginaryNode]
    : annotations[false] classOrInterfaceOrPrimitiveType arrayDeclarator*
    ;

classOrInterfaceOrPrimitiveType
    : (classOrInterfaceType[false] | primitiveType)
    ;

arrayDeclarator
    : anno=annotations[false] LBRACK RBRACK
    ;

primitiveType
    : LITERAL_boolean
    | LITERAL_char
    | LITERAL_byte
    | LITERAL_short
    | LITERAL_int
    | LITERAL_long
    | LITERAL_float
    | LITERAL_double
    | LITERAL_void
    ;

typeArguments
    : LT typeArgument (COMMA typeArgument)* GT
    ;

superSuffix
    : LPAREN expressionList? RPAREN                 #superSuffixSimple
    | DOT id (LPAREN expressionList? RPAREN)?       #superSuffixDot
    ;

arguments
    : LPAREN expressionList? RPAREN
    ;

patternDefinition
    : patternVariableDefinition
    ;

patternVariableDefinition
    : mods+=modifier* type=typeType[true] id
    ;

permittedSubclassesAndInterfaces
    : LITERAL_permits classOrInterfaceType[false] (COMMA classOrInterfaceType[false])*
    ;

// Handle the 'keyword as identifier' problem
id  : LITERAL_record
    | LITERAL_yield
    | LITERAL_non_sealed
    | LITERAL_sealed
    | LITERAL_permits
    | IDENT
    ;
