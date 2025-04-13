///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

parser grammar JavaLanguageParser;

options { tokenVocab=JavaLanguageLexer; }

@parser::members {

    /**
     * This is the number of files to parse before clearing the parser's
     * DFA states. This number can have a significant impact on performance;
     * we have found 500 files to be a good balance between parser speed and
     * memory usage. This field must be public in order to be accessed and
     * used for {@link JavaLanguageParser#JavaLanguageParser(TokenStream, int)}
     * generated constructor.
     */
    public static final int CLEAR_DFA_LIMIT = 500;

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
        return _input.LT(1).getType() == JavaLanguageLexer.LITERAL_YIELD && switchBlockDepth > 0;
    }

    static int fileCounter = 0;

    /**
     * We create a custom constructor so that we can clear the DFA
     * states upon instantiation of JavaLanguageParser.
     *
     * @param input the token stream to parse
     * @param clearDfaLimit this is the number of files to parse before clearing
     *         the parser's DFA states. This number can have a significant impact
     *         on performance; more frequent clearing of DFA states can lead to
     *         slower parsing but lower memory usage. Conversely, not clearing the
     *         DFA states at all can lead to enormous memory usage, but may also
     *         have a negative effect on memory usage from higher garbage collector
     *         activity.
     */
    public JavaLanguageParser(TokenStream input, int clearDfaLimit) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN , _decisionToDFA, _sharedContextCache);
        fileCounter++;
        if (fileCounter > clearDfaLimit) {
            _interp.clearDFA();
            fileCounter = 0;
        }
    }
}

compilationUnit
    : packageDeclaration? importDeclaration* typeDeclaration* EOF
    ;

packageDeclaration
    : annotations[true] LITERAL_PACKAGE qualifiedName SEMI
    ;

importDeclaration
    : IMPORT LITERAL_STATIC? qualifiedName (DOT STAR)? SEMI    #importDec
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
    | LITERAL_PUBLIC
    | LITERAL_PROTECTED
    | LITERAL_PRIVATE
    | LITERAL_STATIC
    | ABSTRACT
    | LITERAL_DEFAULT
    | FINAL
    | STRICTFP
    | LITERAL_NATIVE
    | LITERAL_SYNCHRONIZED
    | LITERAL_TRANSIENT
    | LITERAL_VOLATILE
    | LITERAL_NON_SEALED
    | LITERAL_SEALED
    ;

variableModifier
    : FINAL
    | annotation
    ;

classDeclaration[List<ModifierContext> mods]
    : LITERAL_CLASS id typeParameters?
      classExtends?
      implementsClause?
      permittedSubclassesAndInterfaces?
      classBody
    ;

recordDeclaration[List<ModifierContext> mods]
    : LITERAL_RECORD id typeParameters?
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
    : LITERAL_IMPLEMENTS typeList
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
    : LITERAL_INTERFACE id typeParameters?
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
    | LITERAL_STATIC? block                                                #classBlock
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
    : LITERAL_THROWS qualifiedNameList
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
    | recordDeclaration[mods]
    | interfaceMethodDeclaration[mods]
    | interfaceDeclaration[mods]
    | annotationTypeDeclaration[mods]
    | classDeclaration[mods]
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
    : (LITERAL_THIS | (qualifiedName (DOT LITERAL_THIS)?)) arrayDeclarator*
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
    : typeType[false]                                                       #simpleTypeArgument
    | annotations[false] QUESTION
        (
          ( upperBound=EXTENDS_CLAUSE | lowerBound=LITERAL_SUPER )
          typeType[false]
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
    : id extended+=qualifiedNameExtended*
    ;

qualifiedNameExtended
    : DOT annotations[false] id
    ;

literal
    : integerLiteral
    | floatLiteral
    | textBlockLiteral
    | CHAR_LITERAL
    | STRING_LITERAL
    | LITERAL_TRUE
    | LITERAL_FALSE
    | LITERAL_NULL
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
    : AT LITERAL_INTERFACE id annotationTypeBody
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
    : LITERAL_DEFAULT elementValue
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
      (LITERAL_THIS | LITERAL_SUPER)
      arguments SEMI                                                       #explicitCtorCall
    | expr DOT typeArguments? LITERAL_SUPER arguments SEMI                 #primaryCtorCall
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
    | LITERAL_IF parExpression statement elseStat?                         #ifStat
    | LITERAL_FOR forControl statement                                     #forStat
    | LITERAL_WHILE parExpression statement                                #whileStat
    | LITERAL_DO statement LITERAL_WHILE parExpression SEMI                #doStat
    | LITERAL_TRY block (catchClause+ finallyBlock? | finallyBlock)        #tryStat
    | LITERAL_TRY resourceSpecification block catchClause* finallyBlock?   #tryWithResourceStat
    | LITERAL_YIELD expression SEMI                                        #yieldStat
    | switchExpressionOrStatement                                          #switchStat
    | LITERAL_SYNCHRONIZED parExpression block                             #syncStat
    | LITERAL_RETURN expression? SEMI                                      #returnStat
    | LITERAL_THROW expression SEMI                                        #throwStat
    | LITERAL_BREAK id? SEMI                                               #breakStat
    | LITERAL_CONTINUE id? SEMI                                            #continueStat
    | SEMI                                                                 #emptyStat
    | statementExpression=expression SEMI                                  #expStat
    | id COLON statement                                                   #labelStat
    ;

switchExpressionOrStatement
    : LITERAL_SWITCH parExpression LCURLY
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
    : switchLabel LAMBDA LITERAL_THROW expression SEMI
    ;

elseStat
    : LITERAL_ELSE statement
    ;

catchClause
    : LITERAL_CATCH LPAREN catchParameter RPAREN block
    ;

catchParameter
    : mods+=variableModifier* catchType id
    ;

catchType
    : classOrInterfaceType[false] (BOR classOrInterfaceType[false])*
    ;

finallyBlock
    : LITERAL_FINALLY block
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
    : accessList+=fieldAccessNoIdent* (id | LITERAL_THIS)
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
    : LITERAL_CASE caseConstants COLON?                                   #caseLabel
    | LITERAL_DEFAULT COLON?                                              #defaultLabel
    ;

caseConstants
    : caseConstant (COMMA caseConstant)*
    ;

caseConstant
    : pattern
    | expression
    | LITERAL_DEFAULT
    ;

forControl
    : LPAREN
        ( enhancedForControl | enhancedForControlWithRecordPattern )
      RPAREN                                                               #enhancedFor
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

enhancedForControlWithRecordPattern
    : pattern COLON expression
    ;

// EXPRESSIONS

parExpression
    : LPAREN expression RPAREN
    ;

expressionList
    : startExp=expression (COMMA expression)*
    ;

// We will use this rule to make sure that we have an EXPR node
// at the top of all expression subtrees.
expression
    : expr
    ;

expr
    : primary                                                              #primaryExp
    | expr bop=DOT id                                                      #refOp
    | expr bop=DOT id LPAREN expressionList? RPAREN                        #methodCall
    | expr bop=DOT LITERAL_THIS                                            #thisExp
    | expr bop=DOT LITERAL_NEW nonWildcardTypeArguments?
      innerCreator                                                         #initExp
    | expr bop=DOT nonWildcardTypeArguments?
      LITERAL_SUPER superSuffix?                                           #superExp
    | expr bop=DOT
      nonWildcardTypeArguments
      id LPAREN expressionList? RPAREN                                     #invOp
    | expr LBRACK expr RBRACK                                              #indexOp
    | id LPAREN expressionList? RPAREN                                     #simpleMethodCall
    | LITERAL_NEW creator                                                  #newExp
    | expr postfix=(INC | DEC)                                             #postfix
    | prefix=(PLUS | MINUS | INC | DEC) expr                               #prefix
    | prefix=(BNOT | LNOT) expr                                            #prefix
    | expr DOUBLE_COLON typeArguments? (id | LITERAL_NEW)                  #methodRef
    | typeType[false] DOUBLE_COLON typeArguments? (id | LITERAL_NEW)       #methodRef
    | classType DOUBLE_COLON typeArguments? LITERAL_NEW                    #methodRef
    | LPAREN typeCastParameters RPAREN expr                                #castExp
    | expr bop=(STAR | DIV | MOD) expr                                     #binOp
    | expr bop=(PLUS|MINUS) expr                                           #binOp
    // handle bitwise shifts below, not in lexer
    | expr (LT LT | GT GT GT | GT GT) expr                                 #bitShift
    | expr bop=LITERAL_INSTANCEOF (primaryPattern | typeType[true])        #instanceOfExp
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
      expr                                                                 #binOp
    | lambdaParameters LAMBDA (expr | block)                               #lambdaExp
    ;

typeCastParameters
    : typeType[true] (BAND typeType[true])*
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

primary
    : switchExpressionOrStatement                                          #switchPrimary
    | LPAREN expr RPAREN                                                   #parenPrimary
    | LITERAL_THIS                                                         #tokenPrimary
    | LITERAL_SUPER                                                        #tokenPrimary
    | literal                                                              #literalPrimary
    | id                                                                   #tokenPrimary
    | type=classOrInterfaceType[false] arrayDeclarator*
      DOT LITERAL_CLASS                                                    #classRefPrimary
    | type=primitiveType arrayDeclarator*
      DOT LITERAL_CLASS                                                    #primitivePrimary
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
    : annotations[false] id typeArgumentsOrDiamond?
       extended+=createdNameExtended*                                      #createdNameObject
    | primitiveType                                                        #createdNamePrimitive
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
    : LT GT                                                                #diamond
    | typeArguments                                                        #typeArgs
    ;

nonWildcardTypeArgumentsOrDiamond
    : LT GT                                                                #nonWildcardDiamond
    | nonWildcardTypeArguments                                             #nonWildcardTypeArgs
    ;

nonWildcardTypeArguments
    : LT typeArgumentsTypeList GT
    ;

// As we build a different AST for `TYPE_ARGUMENTS` than we do for simple type list
// (used in `IMPLEMENTS` and elsewhere), we have two separate rules, 'typeArgumentsTypeList'
// and 'typeList' for what is syntactically the same construct. This keeps the visitor
// implementation simpler.
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
    : LITERAL_BOOLEAN
    | LITERAL_CHAR
    | LITERAL_BYTE
    | LITERAL_SHORT
    | LITERAL_INT
    | LITERAL_LONG
    | LITERAL_FLOAT
    | LITERAL_DOUBLE
    | LITERAL_VOID
    ;

typeArguments
    : LT typeArgument (COMMA typeArgument)* GT
    ;

superSuffix
    : LPAREN expressionList? RPAREN                                        #superSuffixSimple
    | DOT id (LPAREN expressionList? RPAREN)?                              #superSuffixDot
    ;

arguments
    : LPAREN expressionList? RPAREN
    ;

/**
 * We do for patterns as we do for expressions; namely we have one parent
 * 'PATTERN_DEF' node, then have all nested pattern definitions inside of
 * the parent node.
 */
pattern
    : innerPattern
    ;

innerPattern
    : guardedPattern
    | recordPattern
    | primaryPattern
    ;

guardedPattern
    : primaryPattern guard expression
    ;

/**
 * We do not need to enforce what the compiler already does; namely, the '&&' syntax
 * in case labels was only supported as a preview feature in JDK18 and will fail compilation
 * now. Guarded patterns in expressions still uses '&&', while case labels now use 'when'.
 * We can allow both alternatives here, since this will help us to maintain backwards
 * compatibility and avoid more alternatives/complexity of maintaining two
 * separate pattern grammars for case labels and expressions.
 */
guard: ( LAND | LITERAL_WHEN );

primaryPattern
    : typePattern                                                          #patternVariableDef
    | LPAREN innerPattern RPAREN                                           #parenPattern
    | recordPattern                                                        #recordPatternDef
    ;

typePattern
    : mods+=modifier* type=typeType[true] id             #typePatternDef
    | LITERAL_UNDERSCORE                                 #unnamedPatternDef
    ;

recordPattern
    : mods+=modifier* type=typeType[true] LPAREN recordComponentPatternList? RPAREN id?
    ;

recordComponentPatternList
    : innerPattern (COMMA innerPattern)*
    ;

permittedSubclassesAndInterfaces
    : LITERAL_PERMITS classOrInterfaceType[false] (COMMA classOrInterfaceType[false])*
    ;

// Handle the 'keyword as identifier' problem
id:  LITERAL_UNDERSCORE
    | LITERAL_RECORD
    | LITERAL_YIELD
    | LITERAL_NON_SEALED
    | LITERAL_SEALED
    | LITERAL_PERMITS
    | IDENT
    | LITERAL_WHEN
    ;
