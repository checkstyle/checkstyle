// Generated from com/puppycrawl/tools/checkstyle/grammar/java/JavaLanguageParser.g4 by ANTLR 4.13.2
package com.puppycrawl.tools.checkstyle.grammar.java;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JavaLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMPILATION_UNIT=1, PLACEHOLDER1=2, NULL_TREE_LOOKAHEAD=3, BLOCK=4, MODIFIERS=5, 
		OBJBLOCK=6, SLIST=7, CTOR_DEF=8, METHOD_DEF=9, VARIABLE_DEF=10, INSTANCE_INIT=11, 
		STATIC_INIT=12, TYPE=13, CLASS_DEF=14, INTERFACE_DEF=15, PACKAGE_DEF=16, 
		ARRAY_DECLARATOR=17, EXTENDS_CLAUSE=18, IMPLEMENTS_CLAUSE=19, PARAMETERS=20, 
		PARAMETER_DEF=21, LABELED_STAT=22, TYPECAST=23, INDEX_OP=24, POST_INC=25, 
		POST_DEC=26, METHOD_CALL=27, EXPR=28, ARRAY_INIT=29, IMPORT=30, UNARY_MINUS=31, 
		UNARY_PLUS=32, CASE_GROUP=33, ELIST=34, FOR_INIT=35, FOR_CONDITION=36, 
		FOR_ITERATOR=37, EMPTY_STAT=38, FINAL=39, ABSTRACT=40, STRICTFP=41, SUPER_CTOR_CALL=42, 
		CTOR_CALL=43, LITERAL_PACKAGE=44, SEMI=45, LITERAL_IMPORT=46, LBRACK=47, 
		RBRACK=48, LITERAL_VOID=49, LITERAL_BOOLEAN=50, LITERAL_BYTE=51, LITERAL_CHAR=52, 
		LITERAL_SHORT=53, LITERAL_INT=54, LITERAL_FLOAT=55, LITERAL_LONG=56, LITERAL_DOUBLE=57, 
		IDENT=58, DOT=59, STAR=60, LITERAL_PRIVATE=61, LITERAL_PUBLIC=62, LITERAL_PROTECTED=63, 
		LITERAL_STATIC=64, LITERAL_TRANSIENT=65, LITERAL_NATIVE=66, LITERAL_SYNCHRONIZED=67, 
		LITERAL_VOLATILE=68, LITERAL_CLASS=69, LITERAL_EXTENDS=70, LITERAL_INTERFACE=71, 
		LCURLY=72, RCURLY=73, COMMA=74, LITERAL_IMPLEMENTS=75, LPAREN=76, RPAREN=77, 
		LITERAL_THIS=78, LITERAL_SUPER=79, ASSIGN=80, LITERAL_THROWS=81, COLON=82, 
		LITERAL_IF=83, LITERAL_WHILE=84, LITERAL_DO=85, LITERAL_BREAK=86, LITERAL_CONTINUE=87, 
		LITERAL_RETURN=88, LITERAL_SWITCH=89, LITERAL_THROW=90, LITERAL_FOR=91, 
		LITERAL_ELSE=92, LITERAL_CASE=93, LITERAL_DEFAULT=94, LITERAL_TRY=95, 
		LITERAL_CATCH=96, LITERAL_FINALLY=97, PLUS_ASSIGN=98, MINUS_ASSIGN=99, 
		STAR_ASSIGN=100, DIV_ASSIGN=101, MOD_ASSIGN=102, SR_ASSIGN=103, BSR_ASSIGN=104, 
		SL_ASSIGN=105, BAND_ASSIGN=106, BXOR_ASSIGN=107, BOR_ASSIGN=108, QUESTION=109, 
		LOR=110, LAND=111, BOR=112, BXOR=113, BAND=114, NOT_EQUAL=115, EQUAL=116, 
		LT=117, GT=118, LE=119, GE=120, LITERAL_INSTANCEOF=121, SL=122, SR=123, 
		BSR=124, PLUS=125, MINUS=126, DIV=127, MOD=128, INC=129, DEC=130, BNOT=131, 
		LNOT=132, LITERAL_TRUE=133, LITERAL_FALSE=134, LITERAL_NULL=135, LITERAL_NEW=136, 
		NUM_INT=137, CHAR_LITERAL=138, STRING_LITERAL=139, NUM_FLOAT=140, NUM_LONG=141, 
		NUM_DOUBLE=142, WS=143, SINGLE_LINE_COMMENT=144, BLOCK_COMMENT_BEGIN=145, 
		ESC=146, HEX_DIGIT=147, VOCAB=148, EXPONENT=149, FLOAT_SUFFIX=150, ASSERT=151, 
		STATIC_IMPORT=152, ENUM=153, ENUM_DEF=154, ENUM_CONSTANT_DEF=155, FOR_EACH_CLAUSE=156, 
		ANNOTATION_DEF=157, ANNOTATIONS=158, ANNOTATION=159, ANNOTATION_MEMBER_VALUE_PAIR=160, 
		ANNOTATION_FIELD_DEF=161, ANNOTATION_ARRAY_INIT=162, TYPE_ARGUMENTS=163, 
		TYPE_ARGUMENT=164, TYPE_PARAMETERS=165, TYPE_PARAMETER=166, WILDCARD_TYPE=167, 
		TYPE_UPPER_BOUNDS=168, TYPE_LOWER_BOUNDS=169, AT=170, ELLIPSIS=171, GENERIC_START=172, 
		GENERIC_END=173, TYPE_EXTENSION_AND=174, DO_WHILE=175, RESOURCE_SPECIFICATION=176, 
		RESOURCES=177, RESOURCE=178, DOUBLE_COLON=179, METHOD_REF=180, LAMBDA=181, 
		BLOCK_COMMENT_END=182, COMMENT_CONTENT=183, SINGLE_LINE_COMMENT_CONTENT=184, 
		BLOCK_COMMENT_CONTENT=185, STD_ESC=186, BINARY_DIGIT=187, ID_START=188, 
		ID_PART=189, INT_LITERAL=190, LONG_LITERAL=191, FLOAT_LITERAL=192, DOUBLE_LITERAL=193, 
		HEX_FLOAT_LITERAL=194, HEX_DOUBLE_LITERAL=195, SIGNED_INTEGER=196, BINARY_EXPONENT=197, 
		PATTERN_VARIABLE_DEF=198, RECORD_DEF=199, LITERAL_RECORD=200, RECORD_COMPONENTS=201, 
		RECORD_COMPONENT_DEF=202, COMPACT_CTOR_DEF=203, TEXT_BLOCK_LITERAL_BEGIN=204, 
		TEXT_BLOCK_CONTENT=205, TEXT_BLOCK_LITERAL_END=206, LITERAL_YIELD=207, 
		SWITCH_RULE=208, LITERAL_NON_SEALED=209, LITERAL_SEALED=210, LITERAL_PERMITS=211, 
		PERMITS_CLAUSE=212, PATTERN_DEF=213, LITERAL_WHEN=214, RECORD_PATTERN_DEF=215, 
		RECORD_PATTERN_COMPONENTS=216, NOT_FOR_USAGE_1=217, NOT_FOR_USAGE_2=218, 
		NOT_FOR_USAGE_3=219, NOT_FOR_USAGE_4=220, NOT_FOR_USAGE_5=221, NOT_FOR_USAGE_6=222, 
		NOT_FOR_USAGE_7=223, LITERAL_UNDERSCORE=224, UNNAMED_PATTERN_DEF=225, 
		LITERAL_MODULE=226, MODULE_IMPORT=227, DECIMAL_LITERAL_LONG=228, DECIMAL_LITERAL=229, 
		HEX_LITERAL_LONG=230, HEX_LITERAL=231, OCT_LITERAL_LONG=232, OCT_LITERAL=233, 
		BINARY_LITERAL_LONG=234, BINARY_LITERAL=235;
	public static final int
		RULE_compilationUnit = 0, RULE_packageDeclaration = 1, RULE_importDeclaration = 2, 
		RULE_typeDeclaration = 3, RULE_types = 4, RULE_modifier = 5, RULE_variableModifier = 6, 
		RULE_classDeclaration = 7, RULE_recordDeclaration = 8, RULE_recordComponentsList = 9, 
		RULE_recordComponents = 10, RULE_recordComponent = 11, RULE_lastRecordComponent = 12, 
		RULE_recordBody = 13, RULE_recordBodyDeclaration = 14, RULE_compactConstructorDeclaration = 15, 
		RULE_classExtends = 16, RULE_implementsClause = 17, RULE_typeParameters = 18, 
		RULE_typeParameter = 19, RULE_typeUpperBounds = 20, RULE_typeBound = 21, 
		RULE_typeBoundType = 22, RULE_enumDeclaration = 23, RULE_enumBody = 24, 
		RULE_enumConstants = 25, RULE_enumConstant = 26, RULE_enumBodyDeclarations = 27, 
		RULE_interfaceDeclaration = 28, RULE_interfaceExtends = 29, RULE_classBody = 30, 
		RULE_interfaceBody = 31, RULE_classBodyDeclaration = 32, RULE_memberDeclaration = 33, 
		RULE_methodDeclaration = 34, RULE_methodBody = 35, RULE_throwsList = 36, 
		RULE_constructorDeclaration = 37, RULE_fieldDeclaration = 38, RULE_interfaceBodyDeclaration = 39, 
		RULE_interfaceMemberDeclaration = 40, RULE_interfaceMethodDeclaration = 41, 
		RULE_variableDeclarators = 42, RULE_variableDeclarator = 43, RULE_variableDeclaratorId = 44, 
		RULE_variableInitializer = 45, RULE_arrayInitializer = 46, RULE_classOrInterfaceType = 47, 
		RULE_classOrInterfaceTypeExtended = 48, RULE_typeArgument = 49, RULE_qualifiedNameList = 50, 
		RULE_formalParameters = 51, RULE_formalParameterList = 52, RULE_formalParameter = 53, 
		RULE_lastFormalParameter = 54, RULE_qualifiedName = 55, RULE_qualifiedNameExtended = 56, 
		RULE_literal = 57, RULE_integerLiteral = 58, RULE_floatLiteral = 59, RULE_textBlockLiteral = 60, 
		RULE_annotations = 61, RULE_annotation = 62, RULE_elementValuePairs = 63, 
		RULE_elementValuePair = 64, RULE_elementValue = 65, RULE_elementValueArrayInitializer = 66, 
		RULE_annotationTypeDeclaration = 67, RULE_annotationTypeBody = 68, RULE_annotationTypeElementDeclaration = 69, 
		RULE_annotationTypeElementRest = 70, RULE_annotationMethodRest = 71, RULE_annotationConstantRest = 72, 
		RULE_defaultValue = 73, RULE_constructorBlock = 74, RULE_explicitConstructorInvocation = 75, 
		RULE_block = 76, RULE_blockStatement = 77, RULE_localVariableDeclaration = 78, 
		RULE_localTypeDeclaration = 79, RULE_statement = 80, RULE_switchExpressionOrStatement = 81, 
		RULE_switchBlock = 82, RULE_switchLabeledRule = 83, RULE_switchLabeledExpression = 84, 
		RULE_switchLabeledBlock = 85, RULE_switchLabeledThrow = 86, RULE_elseStat = 87, 
		RULE_catchClause = 88, RULE_catchParameter = 89, RULE_catchType = 90, 
		RULE_finallyBlock = 91, RULE_resourceSpecification = 92, RULE_resources = 93, 
		RULE_resource = 94, RULE_resourceDeclaration = 95, RULE_variableAccess = 96, 
		RULE_fieldAccessNoIdent = 97, RULE_switchBlockStatementGroup = 98, RULE_switchLabel = 99, 
		RULE_caseConstants = 100, RULE_caseConstant = 101, RULE_forControl = 102, 
		RULE_forInit = 103, RULE_enhancedForControl = 104, RULE_enhancedForControlWithRecordPattern = 105, 
		RULE_parExpression = 106, RULE_expressionList = 107, RULE_expression = 108, 
		RULE_expr = 109, RULE_typeCastParameters = 110, RULE_lambdaParameters = 111, 
		RULE_multiLambdaParams = 112, RULE_primary = 113, RULE_classType = 114, 
		RULE_creator = 115, RULE_createdName = 116, RULE_createdNameExtended = 117, 
		RULE_innerCreator = 118, RULE_arrayCreatorRest = 119, RULE_bracketsWithExp = 120, 
		RULE_classCreatorRest = 121, RULE_typeArgumentsOrDiamond = 122, RULE_nonWildcardTypeArgumentsOrDiamond = 123, 
		RULE_nonWildcardTypeArguments = 124, RULE_typeArgumentsTypeList = 125, 
		RULE_typeList = 126, RULE_typeType = 127, RULE_classOrInterfaceOrPrimitiveType = 128, 
		RULE_arrayDeclarator = 129, RULE_primitiveType = 130, RULE_typeArguments = 131, 
		RULE_superSuffix = 132, RULE_arguments = 133, RULE_pattern = 134, RULE_innerPattern = 135, 
		RULE_guardedPattern = 136, RULE_guard = 137, RULE_primaryPattern = 138, 
		RULE_typePattern = 139, RULE_recordPattern = 140, RULE_recordComponentPatternList = 141, 
		RULE_permittedSubclassesAndInterfaces = 142, RULE_id = 143;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "packageDeclaration", "importDeclaration", "typeDeclaration", 
			"types", "modifier", "variableModifier", "classDeclaration", "recordDeclaration", 
			"recordComponentsList", "recordComponents", "recordComponent", "lastRecordComponent", 
			"recordBody", "recordBodyDeclaration", "compactConstructorDeclaration", 
			"classExtends", "implementsClause", "typeParameters", "typeParameter", 
			"typeUpperBounds", "typeBound", "typeBoundType", "enumDeclaration", "enumBody", 
			"enumConstants", "enumConstant", "enumBodyDeclarations", "interfaceDeclaration", 
			"interfaceExtends", "classBody", "interfaceBody", "classBodyDeclaration", 
			"memberDeclaration", "methodDeclaration", "methodBody", "throwsList", 
			"constructorDeclaration", "fieldDeclaration", "interfaceBodyDeclaration", 
			"interfaceMemberDeclaration", "interfaceMethodDeclaration", "variableDeclarators", 
			"variableDeclarator", "variableDeclaratorId", "variableInitializer", 
			"arrayInitializer", "classOrInterfaceType", "classOrInterfaceTypeExtended", 
			"typeArgument", "qualifiedNameList", "formalParameters", "formalParameterList", 
			"formalParameter", "lastFormalParameter", "qualifiedName", "qualifiedNameExtended", 
			"literal", "integerLiteral", "floatLiteral", "textBlockLiteral", "annotations", 
			"annotation", "elementValuePairs", "elementValuePair", "elementValue", 
			"elementValueArrayInitializer", "annotationTypeDeclaration", "annotationTypeBody", 
			"annotationTypeElementDeclaration", "annotationTypeElementRest", "annotationMethodRest", 
			"annotationConstantRest", "defaultValue", "constructorBlock", "explicitConstructorInvocation", 
			"block", "blockStatement", "localVariableDeclaration", "localTypeDeclaration", 
			"statement", "switchExpressionOrStatement", "switchBlock", "switchLabeledRule", 
			"switchLabeledExpression", "switchLabeledBlock", "switchLabeledThrow", 
			"elseStat", "catchClause", "catchParameter", "catchType", "finallyBlock", 
			"resourceSpecification", "resources", "resource", "resourceDeclaration", 
			"variableAccess", "fieldAccessNoIdent", "switchBlockStatementGroup", 
			"switchLabel", "caseConstants", "caseConstant", "forControl", "forInit", 
			"enhancedForControl", "enhancedForControlWithRecordPattern", "parExpression", 
			"expressionList", "expression", "expr", "typeCastParameters", "lambdaParameters", 
			"multiLambdaParams", "primary", "classType", "creator", "createdName", 
			"createdNameExtended", "innerCreator", "arrayCreatorRest", "bracketsWithExp", 
			"classCreatorRest", "typeArgumentsOrDiamond", "nonWildcardTypeArgumentsOrDiamond", 
			"nonWildcardTypeArguments", "typeArgumentsTypeList", "typeList", "typeType", 
			"classOrInterfaceOrPrimitiveType", "arrayDeclarator", "primitiveType", 
			"typeArguments", "superSuffix", "arguments", "pattern", "innerPattern", 
			"guardedPattern", "guard", "primaryPattern", "typePattern", "recordPattern", 
			"recordComponentPatternList", "permittedSubclassesAndInterfaces", "id"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "'extends'", null, null, null, null, 
			null, null, null, null, null, null, null, "'import'", null, null, null, 
			null, null, null, null, null, "'final'", "'abstract'", "'strictfp'", 
			null, null, "'package'", "';'", null, "'['", "']'", "'void'", "'boolean'", 
			"'byte'", "'char'", "'short'", "'int'", "'float'", "'long'", "'double'", 
			null, "'.'", "'*'", "'private'", "'public'", "'protected'", "'static'", 
			"'transient'", "'native'", "'synchronized'", "'volatile'", "'class'", 
			null, "'interface'", "'{'", "'}'", "','", "'implements'", "'('", "')'", 
			"'this'", "'super'", "'='", "'throws'", "':'", "'if'", "'while'", "'do'", 
			"'break'", "'continue'", "'return'", "'switch'", "'throw'", "'for'", 
			"'else'", "'case'", "'default'", "'try'", "'catch'", "'finally'", "'+='", 
			"'-='", "'*='", "'/='", "'%='", "'>>='", "'>>>='", "'<<='", "'&='", "'^='", 
			"'|='", "'?'", "'||'", "'&&'", "'|'", "'^'", "'&'", "'!='", "'=='", "'<'", 
			"'>'", "'<='", "'>='", "'instanceof'", null, null, null, "'+'", "'-'", 
			"'/'", "'%'", "'++'", "'--'", "'~'", "'!'", "'true'", "'false'", "'null'", 
			"'new'", null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "'assert'", null, "'enum'", null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "'@'", "'...'", null, null, null, null, null, null, null, "'::'", 
			null, "'->'", null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "'record'", null, null, 
			null, null, null, null, "'yield'", null, "'non-sealed'", "'sealed'", 
			"'permits'", null, null, "'when'", null, null, null, null, null, null, 
			null, null, null, "'_'", null, "'module'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMPILATION_UNIT", "PLACEHOLDER1", "NULL_TREE_LOOKAHEAD", "BLOCK", 
			"MODIFIERS", "OBJBLOCK", "SLIST", "CTOR_DEF", "METHOD_DEF", "VARIABLE_DEF", 
			"INSTANCE_INIT", "STATIC_INIT", "TYPE", "CLASS_DEF", "INTERFACE_DEF", 
			"PACKAGE_DEF", "ARRAY_DECLARATOR", "EXTENDS_CLAUSE", "IMPLEMENTS_CLAUSE", 
			"PARAMETERS", "PARAMETER_DEF", "LABELED_STAT", "TYPECAST", "INDEX_OP", 
			"POST_INC", "POST_DEC", "METHOD_CALL", "EXPR", "ARRAY_INIT", "IMPORT", 
			"UNARY_MINUS", "UNARY_PLUS", "CASE_GROUP", "ELIST", "FOR_INIT", "FOR_CONDITION", 
			"FOR_ITERATOR", "EMPTY_STAT", "FINAL", "ABSTRACT", "STRICTFP", "SUPER_CTOR_CALL", 
			"CTOR_CALL", "LITERAL_PACKAGE", "SEMI", "LITERAL_IMPORT", "LBRACK", "RBRACK", 
			"LITERAL_VOID", "LITERAL_BOOLEAN", "LITERAL_BYTE", "LITERAL_CHAR", "LITERAL_SHORT", 
			"LITERAL_INT", "LITERAL_FLOAT", "LITERAL_LONG", "LITERAL_DOUBLE", "IDENT", 
			"DOT", "STAR", "LITERAL_PRIVATE", "LITERAL_PUBLIC", "LITERAL_PROTECTED", 
			"LITERAL_STATIC", "LITERAL_TRANSIENT", "LITERAL_NATIVE", "LITERAL_SYNCHRONIZED", 
			"LITERAL_VOLATILE", "LITERAL_CLASS", "LITERAL_EXTENDS", "LITERAL_INTERFACE", 
			"LCURLY", "RCURLY", "COMMA", "LITERAL_IMPLEMENTS", "LPAREN", "RPAREN", 
			"LITERAL_THIS", "LITERAL_SUPER", "ASSIGN", "LITERAL_THROWS", "COLON", 
			"LITERAL_IF", "LITERAL_WHILE", "LITERAL_DO", "LITERAL_BREAK", "LITERAL_CONTINUE", 
			"LITERAL_RETURN", "LITERAL_SWITCH", "LITERAL_THROW", "LITERAL_FOR", "LITERAL_ELSE", 
			"LITERAL_CASE", "LITERAL_DEFAULT", "LITERAL_TRY", "LITERAL_CATCH", "LITERAL_FINALLY", 
			"PLUS_ASSIGN", "MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", 
			"SR_ASSIGN", "BSR_ASSIGN", "SL_ASSIGN", "BAND_ASSIGN", "BXOR_ASSIGN", 
			"BOR_ASSIGN", "QUESTION", "LOR", "LAND", "BOR", "BXOR", "BAND", "NOT_EQUAL", 
			"EQUAL", "LT", "GT", "LE", "GE", "LITERAL_INSTANCEOF", "SL", "SR", "BSR", 
			"PLUS", "MINUS", "DIV", "MOD", "INC", "DEC", "BNOT", "LNOT", "LITERAL_TRUE", 
			"LITERAL_FALSE", "LITERAL_NULL", "LITERAL_NEW", "NUM_INT", "CHAR_LITERAL", 
			"STRING_LITERAL", "NUM_FLOAT", "NUM_LONG", "NUM_DOUBLE", "WS", "SINGLE_LINE_COMMENT", 
			"BLOCK_COMMENT_BEGIN", "ESC", "HEX_DIGIT", "VOCAB", "EXPONENT", "FLOAT_SUFFIX", 
			"ASSERT", "STATIC_IMPORT", "ENUM", "ENUM_DEF", "ENUM_CONSTANT_DEF", "FOR_EACH_CLAUSE", 
			"ANNOTATION_DEF", "ANNOTATIONS", "ANNOTATION", "ANNOTATION_MEMBER_VALUE_PAIR", 
			"ANNOTATION_FIELD_DEF", "ANNOTATION_ARRAY_INIT", "TYPE_ARGUMENTS", "TYPE_ARGUMENT", 
			"TYPE_PARAMETERS", "TYPE_PARAMETER", "WILDCARD_TYPE", "TYPE_UPPER_BOUNDS", 
			"TYPE_LOWER_BOUNDS", "AT", "ELLIPSIS", "GENERIC_START", "GENERIC_END", 
			"TYPE_EXTENSION_AND", "DO_WHILE", "RESOURCE_SPECIFICATION", "RESOURCES", 
			"RESOURCE", "DOUBLE_COLON", "METHOD_REF", "LAMBDA", "BLOCK_COMMENT_END", 
			"COMMENT_CONTENT", "SINGLE_LINE_COMMENT_CONTENT", "BLOCK_COMMENT_CONTENT", 
			"STD_ESC", "BINARY_DIGIT", "ID_START", "ID_PART", "INT_LITERAL", "LONG_LITERAL", 
			"FLOAT_LITERAL", "DOUBLE_LITERAL", "HEX_FLOAT_LITERAL", "HEX_DOUBLE_LITERAL", 
			"SIGNED_INTEGER", "BINARY_EXPONENT", "PATTERN_VARIABLE_DEF", "RECORD_DEF", 
			"LITERAL_RECORD", "RECORD_COMPONENTS", "RECORD_COMPONENT_DEF", "COMPACT_CTOR_DEF", 
			"TEXT_BLOCK_LITERAL_BEGIN", "TEXT_BLOCK_CONTENT", "TEXT_BLOCK_LITERAL_END", 
			"LITERAL_YIELD", "SWITCH_RULE", "LITERAL_NON_SEALED", "LITERAL_SEALED", 
			"LITERAL_PERMITS", "PERMITS_CLAUSE", "PATTERN_DEF", "LITERAL_WHEN", "RECORD_PATTERN_DEF", 
			"RECORD_PATTERN_COMPONENTS", "NOT_FOR_USAGE_1", "NOT_FOR_USAGE_2", "NOT_FOR_USAGE_3", 
			"NOT_FOR_USAGE_4", "NOT_FOR_USAGE_5", "NOT_FOR_USAGE_6", "NOT_FOR_USAGE_7", 
			"LITERAL_UNDERSCORE", "UNNAMED_PATTERN_DEF", "LITERAL_MODULE", "MODULE_IMPORT", 
			"DECIMAL_LITERAL_LONG", "DECIMAL_LITERAL", "HEX_LITERAL_LONG", "HEX_LITERAL", 
			"OCT_LITERAL_LONG", "OCT_LITERAL", "BINARY_LITERAL_LONG", "BINARY_LITERAL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JavaLanguageParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



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

	public JavaLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompilationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavaLanguageParser.EOF, 0); }
		public PackageDeclarationContext packageDeclaration() {
			return getRuleContext(PackageDeclarationContext.class,0);
		}
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<TypeDeclarationContext> typeDeclaration() {
			return getRuleContexts(TypeDeclarationContext.class);
		}
		public TypeDeclarationContext typeDeclaration(int i) {
			return getRuleContext(TypeDeclarationContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(288);
				packageDeclaration();
				}
				break;
			}
			setState(294);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(291);
					importDeclaration();
					}
					} 
				}
				setState(296);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028803457220679L) != 0) || ((((_la - 153)) & ~0x3f) == 0 && ((1L << (_la - 153)) & 216313519602270209L) != 0)) {
				{
				{
				setState(297);
				typeDeclaration();
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(303);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PackageDeclarationContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TerminalNode LITERAL_PACKAGE() { return getToken(JavaLanguageParser.LITERAL_PACKAGE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public PackageDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPackageDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageDeclarationContext packageDeclaration() throws RecognitionException {
		PackageDeclarationContext _localctx = new PackageDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			annotations(true);
			setState(306);
			match(LITERAL_PACKAGE);
			setState(307);
			qualifiedName();
			setState(308);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportDeclarationContext extends ParserRuleContext {
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
	 
		public ImportDeclarationContext() { }
		public void copyFrom(ImportDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SingleSemiImportContext extends ImportDeclarationContext {
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public SingleSemiImportContext(ImportDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSingleSemiImport(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImportDecContext extends ImportDeclarationContext {
		public TerminalNode IMPORT() { return getToken(JavaLanguageParser.IMPORT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TerminalNode LITERAL_STATIC() { return getToken(JavaLanguageParser.LITERAL_STATIC, 0); }
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public TerminalNode STAR() { return getToken(JavaLanguageParser.STAR, 0); }
		public TerminalNode LITERAL_MODULE() { return getToken(JavaLanguageParser.LITERAL_MODULE, 0); }
		public ImportDecContext(ImportDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitImportDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importDeclaration);
		int _la;
		try {
			setState(327);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new ImportDecContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(310);
				match(IMPORT);
				setState(312);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LITERAL_STATIC) {
					{
					setState(311);
					match(LITERAL_STATIC);
					}
				}

				setState(314);
				qualifiedName();
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOT) {
					{
					setState(315);
					match(DOT);
					setState(316);
					match(STAR);
					}
				}

				setState(319);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new ImportDecContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(321);
				match(IMPORT);
				setState(322);
				match(LITERAL_MODULE);
				setState(323);
				qualifiedName();
				setState(324);
				match(SEMI);
				}
				break;
			case 3:
				_localctx = new SingleSemiImportContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public TypesContext type;
		public Token SEMI;
		public List<Token> semi = new ArrayList<Token>();
		public TypesContext types() {
			return getRuleContext(TypesContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(JavaLanguageParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(JavaLanguageParser.SEMI, i);
		}
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeDeclaration);
		try {
			int _alt;
			setState(341);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
			case ABSTRACT:
			case STRICTFP:
			case LITERAL_PRIVATE:
			case LITERAL_PUBLIC:
			case LITERAL_PROTECTED:
			case LITERAL_STATIC:
			case LITERAL_TRANSIENT:
			case LITERAL_NATIVE:
			case LITERAL_SYNCHRONIZED:
			case LITERAL_VOLATILE:
			case LITERAL_CLASS:
			case LITERAL_INTERFACE:
			case LITERAL_DEFAULT:
			case ENUM:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
				enterOuterAlt(_localctx, 1);
				{
				setState(332);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(329);
						((TypeDeclarationContext)_localctx).modifier = modifier();
						((TypeDeclarationContext)_localctx).mods.add(((TypeDeclarationContext)_localctx).modifier);
						}
						} 
					}
					setState(334);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				setState(335);
				((TypeDeclarationContext)_localctx).type = types(_localctx.mods);
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(337); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(336);
						((TypeDeclarationContext)_localctx).SEMI = match(SEMI);
						((TypeDeclarationContext)_localctx).semi.add(((TypeDeclarationContext)_localctx).SEMI);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(339); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypesContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public AnnotationTypeDeclarationContext annotationTypeDeclaration() {
			return getRuleContext(AnnotationTypeDeclarationContext.class,0);
		}
		public RecordDeclarationContext recordDeclaration() {
			return getRuleContext(RecordDeclarationContext.class,0);
		}
		public TypesContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TypesContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_types; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypesContext types(List<ModifierContext> mods) throws RecognitionException {
		TypesContext _localctx = new TypesContext(_ctx, getState(), mods);
		enterRule(_localctx, 8, RULE_types);
		try {
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LITERAL_CLASS:
				enterOuterAlt(_localctx, 1);
				{
				setState(343);
				classDeclaration(mods);
				}
				break;
			case ENUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(344);
				enumDeclaration(mods);
				}
				break;
			case LITERAL_INTERFACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(345);
				interfaceDeclaration(mods);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 4);
				{
				setState(346);
				annotationTypeDeclaration(mods);
				}
				break;
			case LITERAL_RECORD:
				enterOuterAlt(_localctx, 5);
				{
				setState(347);
				recordDeclaration(mods);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode LITERAL_PUBLIC() { return getToken(JavaLanguageParser.LITERAL_PUBLIC, 0); }
		public TerminalNode LITERAL_PROTECTED() { return getToken(JavaLanguageParser.LITERAL_PROTECTED, 0); }
		public TerminalNode LITERAL_PRIVATE() { return getToken(JavaLanguageParser.LITERAL_PRIVATE, 0); }
		public TerminalNode LITERAL_STATIC() { return getToken(JavaLanguageParser.LITERAL_STATIC, 0); }
		public TerminalNode ABSTRACT() { return getToken(JavaLanguageParser.ABSTRACT, 0); }
		public TerminalNode LITERAL_DEFAULT() { return getToken(JavaLanguageParser.LITERAL_DEFAULT, 0); }
		public TerminalNode FINAL() { return getToken(JavaLanguageParser.FINAL, 0); }
		public TerminalNode STRICTFP() { return getToken(JavaLanguageParser.STRICTFP, 0); }
		public TerminalNode LITERAL_NATIVE() { return getToken(JavaLanguageParser.LITERAL_NATIVE, 0); }
		public TerminalNode LITERAL_SYNCHRONIZED() { return getToken(JavaLanguageParser.LITERAL_SYNCHRONIZED, 0); }
		public TerminalNode LITERAL_TRANSIENT() { return getToken(JavaLanguageParser.LITERAL_TRANSIENT, 0); }
		public TerminalNode LITERAL_VOLATILE() { return getToken(JavaLanguageParser.LITERAL_VOLATILE, 0); }
		public TerminalNode LITERAL_NON_SEALED() { return getToken(JavaLanguageParser.LITERAL_NON_SEALED, 0); }
		public TerminalNode LITERAL_SEALED() { return getToken(JavaLanguageParser.LITERAL_SEALED, 0); }
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_modifier);
		try {
			setState(365);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(350);
				annotation();
				}
				break;
			case LITERAL_PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(351);
				match(LITERAL_PUBLIC);
				}
				break;
			case LITERAL_PROTECTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(352);
				match(LITERAL_PROTECTED);
				}
				break;
			case LITERAL_PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(353);
				match(LITERAL_PRIVATE);
				}
				break;
			case LITERAL_STATIC:
				enterOuterAlt(_localctx, 5);
				{
				setState(354);
				match(LITERAL_STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 6);
				{
				setState(355);
				match(ABSTRACT);
				}
				break;
			case LITERAL_DEFAULT:
				enterOuterAlt(_localctx, 7);
				{
				setState(356);
				match(LITERAL_DEFAULT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 8);
				{
				setState(357);
				match(FINAL);
				}
				break;
			case STRICTFP:
				enterOuterAlt(_localctx, 9);
				{
				setState(358);
				match(STRICTFP);
				}
				break;
			case LITERAL_NATIVE:
				enterOuterAlt(_localctx, 10);
				{
				setState(359);
				match(LITERAL_NATIVE);
				}
				break;
			case LITERAL_SYNCHRONIZED:
				enterOuterAlt(_localctx, 11);
				{
				setState(360);
				match(LITERAL_SYNCHRONIZED);
				}
				break;
			case LITERAL_TRANSIENT:
				enterOuterAlt(_localctx, 12);
				{
				setState(361);
				match(LITERAL_TRANSIENT);
				}
				break;
			case LITERAL_VOLATILE:
				enterOuterAlt(_localctx, 13);
				{
				setState(362);
				match(LITERAL_VOLATILE);
				}
				break;
			case LITERAL_NON_SEALED:
				enterOuterAlt(_localctx, 14);
				{
				setState(363);
				match(LITERAL_NON_SEALED);
				}
				break;
			case LITERAL_SEALED:
				enterOuterAlt(_localctx, 15);
				{
				setState(364);
				match(LITERAL_SEALED);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableModifierContext extends ParserRuleContext {
		public TerminalNode FINAL() { return getToken(JavaLanguageParser.FINAL, 0); }
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public VariableModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableModifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableModifierContext variableModifier() throws RecognitionException {
		VariableModifierContext _localctx = new VariableModifierContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variableModifier);
		try {
			setState(369);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(367);
				match(FINAL);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(368);
				annotation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TerminalNode LITERAL_CLASS() { return getToken(JavaLanguageParser.LITERAL_CLASS, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public ClassExtendsContext classExtends() {
			return getRuleContext(ClassExtendsContext.class,0);
		}
		public ImplementsClauseContext implementsClause() {
			return getRuleContext(ImplementsClauseContext.class,0);
		}
		public PermittedSubclassesAndInterfacesContext permittedSubclassesAndInterfaces() {
			return getRuleContext(PermittedSubclassesAndInterfacesContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration(List<ModifierContext> mods) throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 14, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			match(LITERAL_CLASS);
			setState(372);
			id();
			setState(374);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(373);
				typeParameters();
				}
			}

			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS_CLAUSE) {
				{
				setState(376);
				classExtends();
				}
			}

			setState(380);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_IMPLEMENTS) {
				{
				setState(379);
				implementsClause();
				}
			}

			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_PERMITS) {
				{
				setState(382);
				permittedSubclassesAndInterfaces();
				}
			}

			setState(385);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TerminalNode LITERAL_RECORD() { return getToken(JavaLanguageParser.LITERAL_RECORD, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public RecordComponentsListContext recordComponentsList() {
			return getRuleContext(RecordComponentsListContext.class,0);
		}
		public RecordBodyContext recordBody() {
			return getRuleContext(RecordBodyContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public ImplementsClauseContext implementsClause() {
			return getRuleContext(ImplementsClauseContext.class,0);
		}
		public RecordDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public RecordDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_recordDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordDeclarationContext recordDeclaration(List<ModifierContext> mods) throws RecognitionException {
		RecordDeclarationContext _localctx = new RecordDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 16, RULE_recordDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(LITERAL_RECORD);
			setState(388);
			id();
			setState(390);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(389);
				typeParameters();
				}
			}

			setState(392);
			recordComponentsList();
			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_IMPLEMENTS) {
				{
				setState(393);
				implementsClause();
				}
			}

			setState(396);
			recordBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordComponentsListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public RecordComponentsContext recordComponents() {
			return getRuleContext(RecordComponentsContext.class,0);
		}
		public RecordComponentsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordComponentsList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordComponentsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordComponentsListContext recordComponentsList() throws RecognitionException {
		RecordComponentsListContext _localctx = new RecordComponentsListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_recordComponentsList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			match(LPAREN);
			setState(400);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 575897802350002176L) != 0) || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 90093571536846849L) != 0)) {
				{
				setState(399);
				recordComponents();
				}
			}

			setState(402);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordComponentsContext extends ParserRuleContext {
		public List<RecordComponentContext> recordComponent() {
			return getRuleContexts(RecordComponentContext.class);
		}
		public RecordComponentContext recordComponent(int i) {
			return getRuleContext(RecordComponentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public LastRecordComponentContext lastRecordComponent() {
			return getRuleContext(LastRecordComponentContext.class,0);
		}
		public RecordComponentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordComponents; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordComponents(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordComponentsContext recordComponents() throws RecognitionException {
		RecordComponentsContext _localctx = new RecordComponentsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_recordComponents);
		int _la;
		try {
			int _alt;
			setState(417);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				recordComponent();
				setState(409);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(405);
						match(COMMA);
						setState(406);
						recordComponent();
						}
						} 
					}
					setState(411);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				}
				setState(414);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(412);
					match(COMMA);
					setState(413);
					lastRecordComponent();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(416);
				lastRecordComponent();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordComponentContext extends ParserRuleContext {
		public TypeTypeContext type;
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public RecordComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordComponent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordComponent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordComponentContext recordComponent() throws RecognitionException {
		RecordComponentContext _localctx = new RecordComponentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_recordComponent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			annotations(true);
			setState(420);
			((RecordComponentContext)_localctx).type = typeType(true);
			setState(421);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LastRecordComponentContext extends ParserRuleContext {
		public TypeTypeContext type;
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TerminalNode ELLIPSIS() { return getToken(JavaLanguageParser.ELLIPSIS, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public LastRecordComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lastRecordComponent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLastRecordComponent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LastRecordComponentContext lastRecordComponent() throws RecognitionException {
		LastRecordComponentContext _localctx = new LastRecordComponentContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_lastRecordComponent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			annotations(true);
			setState(424);
			((LastRecordComponentContext)_localctx).type = typeType(true);
			setState(425);
			match(ELLIPSIS);
			setState(426);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordBodyContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<RecordBodyDeclarationContext> recordBodyDeclaration() {
			return getRuleContexts(RecordBodyDeclarationContext.class);
		}
		public RecordBodyDeclarationContext recordBodyDeclaration(int i) {
			return getRuleContext(RecordBodyDeclarationContext.class,i);
		}
		public RecordBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordBodyContext recordBody() throws RecognitionException {
		RecordBodyContext _localctx = new RecordBodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_recordBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			match(LCURLY);
			setState(432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028812048202823L) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & 9007267974217729L) != 0) || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
				{
				{
				setState(429);
				recordBodyDeclaration();
				}
				}
				setState(434);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(435);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordBodyDeclarationContext extends ParserRuleContext {
		public CompactConstructorDeclarationContext compactConstructorDeclaration() {
			return getRuleContext(CompactConstructorDeclarationContext.class,0);
		}
		public ClassBodyDeclarationContext classBodyDeclaration() {
			return getRuleContext(ClassBodyDeclarationContext.class,0);
		}
		public RecordBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordBodyDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordBodyDeclarationContext recordBodyDeclaration() throws RecognitionException {
		RecordBodyDeclarationContext _localctx = new RecordBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_recordBodyDeclaration);
		try {
			setState(439);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(437);
				compactConstructorDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(438);
				classBodyDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompactConstructorDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ConstructorBlockContext constructorBlock() {
			return getRuleContext(ConstructorBlockContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public CompactConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compactConstructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCompactConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompactConstructorDeclarationContext compactConstructorDeclaration() throws RecognitionException {
		CompactConstructorDeclarationContext _localctx = new CompactConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_compactConstructorDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(441);
					((CompactConstructorDeclarationContext)_localctx).modifier = modifier();
					((CompactConstructorDeclarationContext)_localctx).mods.add(((CompactConstructorDeclarationContext)_localctx).modifier);
					}
					} 
				}
				setState(446);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			setState(447);
			id();
			setState(448);
			constructorBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassExtendsContext extends ParserRuleContext {
		public TypeTypeContext type;
		public TerminalNode EXTENDS_CLAUSE() { return getToken(JavaLanguageParser.EXTENDS_CLAUSE, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public ClassExtendsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classExtends; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassExtends(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassExtendsContext classExtends() throws RecognitionException {
		ClassExtendsContext _localctx = new ClassExtendsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_classExtends);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(450);
			match(EXTENDS_CLAUSE);
			setState(451);
			((ClassExtendsContext)_localctx).type = typeType(false);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImplementsClauseContext extends ParserRuleContext {
		public TerminalNode LITERAL_IMPLEMENTS() { return getToken(JavaLanguageParser.LITERAL_IMPLEMENTS, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public ImplementsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implementsClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitImplementsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImplementsClauseContext implementsClause() throws RecognitionException {
		ImplementsClauseContext _localctx = new ImplementsClauseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_implementsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			match(LITERAL_IMPLEMENTS);
			setState(454);
			typeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParametersContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public TypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParametersContext typeParameters() throws RecognitionException {
		TypeParametersContext _localctx = new TypeParametersContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(456);
			match(LT);
			setState(457);
			typeParameter();
			setState(462);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(458);
				match(COMMA);
				setState(459);
				typeParameter();
				}
				}
				setState(464);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(465);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParameterContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeUpperBoundsContext typeUpperBounds() {
			return getRuleContext(TypeUpperBoundsContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(467);
			annotations(false);
			setState(468);
			id();
			setState(470);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS_CLAUSE) {
				{
				setState(469);
				typeUpperBounds();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeUpperBoundsContext extends ParserRuleContext {
		public TerminalNode EXTENDS_CLAUSE() { return getToken(JavaLanguageParser.EXTENDS_CLAUSE, 0); }
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TypeBoundContext typeBound() {
			return getRuleContext(TypeBoundContext.class,0);
		}
		public TypeUpperBoundsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeUpperBounds; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeUpperBounds(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeUpperBoundsContext typeUpperBounds() throws RecognitionException {
		TypeUpperBoundsContext _localctx = new TypeUpperBoundsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_typeUpperBounds);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(472);
			match(EXTENDS_CLAUSE);
			setState(473);
			annotations(false);
			setState(474);
			typeBound();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeBoundContext extends ParserRuleContext {
		public List<TypeBoundTypeContext> typeBoundType() {
			return getRuleContexts(TypeBoundTypeContext.class);
		}
		public TypeBoundTypeContext typeBoundType(int i) {
			return getRuleContext(TypeBoundTypeContext.class,i);
		}
		public List<TerminalNode> BAND() { return getTokens(JavaLanguageParser.BAND); }
		public TerminalNode BAND(int i) {
			return getToken(JavaLanguageParser.BAND, i);
		}
		public TypeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBound; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeBoundContext typeBound() throws RecognitionException {
		TypeBoundContext _localctx = new TypeBoundContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_typeBound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(476);
			typeBoundType();
			setState(481);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BAND) {
				{
				{
				setState(477);
				match(BAND);
				setState(478);
				typeBoundType();
				}
				}
				setState(483);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeBoundTypeContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ClassOrInterfaceOrPrimitiveTypeContext classOrInterfaceOrPrimitiveType() {
			return getRuleContext(ClassOrInterfaceOrPrimitiveTypeContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public TypeBoundTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBoundType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeBoundType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeBoundTypeContext typeBoundType() throws RecognitionException {
		TypeBoundTypeContext _localctx = new TypeBoundTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_typeBoundType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(484);
			annotations(false);
			setState(485);
			classOrInterfaceOrPrimitiveType();
			setState(489);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(486);
				arrayDeclarator();
				}
				}
				setState(491);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TerminalNode ENUM() { return getToken(JavaLanguageParser.ENUM, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public EnumBodyContext enumBody() {
			return getRuleContext(EnumBodyContext.class,0);
		}
		public ImplementsClauseContext implementsClause() {
			return getRuleContext(ImplementsClauseContext.class,0);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration(List<ModifierContext> mods) throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 46, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			match(ENUM);
			setState(493);
			id();
			setState(495);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_IMPLEMENTS) {
				{
				setState(494);
				implementsClause();
				}
			}

			setState(497);
			enumBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumBodyContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public EnumConstantsContext enumConstants() {
			return getRuleContext(EnumConstantsContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(JavaLanguageParser.COMMA, 0); }
		public EnumBodyDeclarationsContext enumBodyDeclarations() {
			return getRuleContext(EnumBodyDeclarationsContext.class,0);
		}
		public EnumBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnumBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyContext enumBody() throws RecognitionException {
		EnumBodyContext _localctx = new EnumBodyContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			match(LCURLY);
			setState(501);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENT || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 90093571536846849L) != 0)) {
				{
				setState(500);
				enumConstants();
				}
			}

			setState(504);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(503);
				match(COMMA);
				}
			}

			setState(507);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(506);
				enumBodyDeclarations();
				}
			}

			setState(509);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumConstantsContext extends ParserRuleContext {
		public List<EnumConstantContext> enumConstant() {
			return getRuleContexts(EnumConstantContext.class);
		}
		public EnumConstantContext enumConstant(int i) {
			return getRuleContext(EnumConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public EnumConstantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstants; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnumConstants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantsContext enumConstants() throws RecognitionException {
		EnumConstantsContext _localctx = new EnumConstantsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_enumConstants);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(511);
			enumConstant();
			setState(516);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(512);
					match(COMMA);
					setState(513);
					enumConstant();
					}
					} 
				}
				setState(518);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumConstantContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public EnumConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnumConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantContext enumConstant() throws RecognitionException {
		EnumConstantContext _localctx = new EnumConstantContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_enumConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(519);
			annotations(true);
			setState(520);
			id();
			setState(522);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(521);
				arguments();
				}
			}

			setState(525);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LCURLY) {
				{
				setState(524);
				classBody();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumBodyDeclarationsContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public EnumBodyDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBodyDeclarations; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnumBodyDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyDeclarationsContext enumBodyDeclarations() throws RecognitionException {
		EnumBodyDeclarationsContext _localctx = new EnumBodyDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_enumBodyDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(527);
			match(SEMI);
			setState(531);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028812048202823L) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & 9007267974217729L) != 0) || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
				{
				{
				setState(528);
				classBodyDeclaration();
				}
				}
				setState(533);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TerminalNode LITERAL_INTERFACE() { return getToken(JavaLanguageParser.LITERAL_INTERFACE, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public InterfaceExtendsContext interfaceExtends() {
			return getRuleContext(InterfaceExtendsContext.class,0);
		}
		public PermittedSubclassesAndInterfacesContext permittedSubclassesAndInterfaces() {
			return getRuleContext(PermittedSubclassesAndInterfacesContext.class,0);
		}
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_interfaceDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDeclarationContext interfaceDeclaration(List<ModifierContext> mods) throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 56, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(534);
			match(LITERAL_INTERFACE);
			setState(535);
			id();
			setState(537);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(536);
				typeParameters();
				}
			}

			setState(540);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS_CLAUSE) {
				{
				setState(539);
				interfaceExtends();
				}
			}

			setState(543);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_PERMITS) {
				{
				setState(542);
				permittedSubclassesAndInterfaces();
				}
			}

			setState(545);
			interfaceBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceExtendsContext extends ParserRuleContext {
		public TerminalNode EXTENDS_CLAUSE() { return getToken(JavaLanguageParser.EXTENDS_CLAUSE, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public InterfaceExtendsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceExtends; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceExtends(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceExtendsContext interfaceExtends() throws RecognitionException {
		InterfaceExtendsContext _localctx = new InterfaceExtendsContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_interfaceExtends);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(EXTENDS_CLAUSE);
			setState(548);
			typeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(550);
			match(LCURLY);
			setState(554);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028812048202823L) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & 9007267974217729L) != 0) || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
				{
				{
				setState(551);
				classBodyDeclaration();
				}
				}
				setState(556);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(557);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceBodyContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<InterfaceBodyDeclarationContext> interfaceBodyDeclaration() {
			return getRuleContexts(InterfaceBodyDeclarationContext.class);
		}
		public InterfaceBodyDeclarationContext interfaceBodyDeclaration(int i) {
			return getRuleContext(InterfaceBodyDeclarationContext.class,i);
		}
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			match(LCURLY);
			setState(563);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028803458268231L) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & 9007267974217729L) != 0) || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
				{
				{
				setState(560);
				interfaceBodyDeclaration();
				}
				}
				setState(565);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(566);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyDeclarationContext extends ParserRuleContext {
		public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBodyDeclaration; }
	 
		public ClassBodyDeclarationContext() { }
		public void copyFrom(ClassBodyDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassBlockContext extends ClassBodyDeclarationContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode LITERAL_STATIC() { return getToken(JavaLanguageParser.LITERAL_STATIC, 0); }
		public ClassBlockContext(ClassBodyDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassBlock(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends ClassBodyDeclarationContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public MemberDeclarationContext memberDeclaration() {
			return getRuleContext(MemberDeclarationContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ClassDefContext(ClassBodyDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyClassContext extends ClassBodyDeclarationContext {
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public EmptyClassContext(ClassBodyDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEmptyClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyDeclarationContext classBodyDeclaration() throws RecognitionException {
		ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(580);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				_localctx = new EmptyClassContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(568);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new ClassBlockContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(570);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LITERAL_STATIC) {
					{
					setState(569);
					match(LITERAL_STATIC);
					}
				}

				setState(572);
				block();
				}
				break;
			case 3:
				_localctx = new ClassDefContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(576);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(573);
						((ClassDefContext)_localctx).modifier = modifier();
						((ClassDefContext)_localctx).mods.add(((ClassDefContext)_localctx).modifier);
						}
						} 
					}
					setState(578);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				setState(579);
				memberDeclaration(((ClassDefContext) _localctx).mods);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MemberDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public RecordDeclarationContext recordDeclaration() {
			return getRuleContext(RecordDeclarationContext.class,0);
		}
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public AnnotationTypeDeclarationContext annotationTypeDeclaration() {
			return getRuleContext(AnnotationTypeDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration(List<ModifierContext> mods) throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 66, RULE_memberDeclaration);
		try {
			setState(590);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(582);
				recordDeclaration(mods);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(583);
				methodDeclaration(mods);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(584);
				fieldDeclaration(mods);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(585);
				constructorDeclaration(mods);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(586);
				interfaceDeclaration(mods);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(587);
				annotationTypeDeclaration(mods);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(588);
				classDeclaration(mods);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(589);
				enumDeclaration(mods);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeParametersContext typeParams;
		public TypeTypeContext type;
		public ArrayDeclaratorContext arrayDeclarator;
		public List<ArrayDeclaratorContext> cStyleArrDec = new ArrayList<ArrayDeclaratorContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public ThrowsListContext throwsList() {
			return getRuleContext(ThrowsListContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration(List<ModifierContext> mods) throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 68, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(593);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(592);
				((MethodDeclarationContext)_localctx).typeParams = typeParameters();
				}
			}

			setState(595);
			((MethodDeclarationContext)_localctx).type = typeType(true);
			setState(596);
			id();
			setState(597);
			formalParameters();
			{
			setState(601);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(598);
				((MethodDeclarationContext)_localctx).arrayDeclarator = arrayDeclarator();
				((MethodDeclarationContext)_localctx).cStyleArrDec.add(((MethodDeclarationContext)_localctx).arrayDeclarator);
				}
				}
				setState(603);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(605);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_THROWS) {
				{
				setState(604);
				throwsList();
				}
			}

			setState(607);
			methodBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodBodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public MethodBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMethodBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodBodyContext methodBody() throws RecognitionException {
		MethodBodyContext _localctx = new MethodBodyContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_methodBody);
		try {
			setState(611);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LCURLY:
				enterOuterAlt(_localctx, 1);
				{
				setState(609);
				block();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(610);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThrowsListContext extends ParserRuleContext {
		public TerminalNode LITERAL_THROWS() { return getToken(JavaLanguageParser.LITERAL_THROWS, 0); }
		public QualifiedNameListContext qualifiedNameList() {
			return getRuleContext(QualifiedNameListContext.class,0);
		}
		public ThrowsListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwsList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitThrowsList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowsListContext throwsList() throws RecognitionException {
		ThrowsListContext _localctx = new ThrowsListContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_throwsList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(613);
			match(LITERAL_THROWS);
			setState(614);
			qualifiedNameList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public ConstructorBlockContext constructorBody;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public ConstructorBlockContext constructorBlock() {
			return getRuleContext(ConstructorBlockContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public ThrowsListContext throwsList() {
			return getRuleContext(ThrowsListContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration(List<ModifierContext> mods) throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 74, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(616);
				typeParameters();
				}
			}

			setState(619);
			id();
			setState(620);
			formalParameters();
			setState(622);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_THROWS) {
				{
				setState(621);
				throwsList();
				}
			}

			setState(624);
			((ConstructorDeclarationContext)_localctx).constructorBody = constructorBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration(List<ModifierContext> mods) throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 76, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(626);
			((FieldDeclarationContext)_localctx).type = typeType(true);
			setState(627);
			variableDeclarators(_localctx.mods, _localctx.type);
			setState(628);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceBodyDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public InterfaceMemberDeclarationContext interfaceMemberDeclaration() {
			return getRuleContext(InterfaceMemberDeclarationContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public InterfaceBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBodyDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyDeclarationContext interfaceBodyDeclaration() throws RecognitionException {
		InterfaceBodyDeclarationContext _localctx = new InterfaceBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_interfaceBodyDeclaration);
		try {
			int _alt;
			setState(638);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
			case ABSTRACT:
			case STRICTFP:
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
			case IDENT:
			case LITERAL_PRIVATE:
			case LITERAL_PUBLIC:
			case LITERAL_PROTECTED:
			case LITERAL_STATIC:
			case LITERAL_TRANSIENT:
			case LITERAL_NATIVE:
			case LITERAL_SYNCHRONIZED:
			case LITERAL_VOLATILE:
			case LITERAL_CLASS:
			case LITERAL_INTERFACE:
			case LITERAL_DEFAULT:
			case LT:
			case ENUM:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				enterOuterAlt(_localctx, 1);
				{
				setState(633);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(630);
						((InterfaceBodyDeclarationContext)_localctx).modifier = modifier();
						((InterfaceBodyDeclarationContext)_localctx).mods.add(((InterfaceBodyDeclarationContext)_localctx).modifier);
						}
						} 
					}
					setState(635);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
				}
				setState(636);
				interfaceMemberDeclaration(_localctx.mods);
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(637);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceMemberDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public RecordDeclarationContext recordDeclaration() {
			return getRuleContext(RecordDeclarationContext.class,0);
		}
		public InterfaceMethodDeclarationContext interfaceMethodDeclaration() {
			return getRuleContext(InterfaceMethodDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public AnnotationTypeDeclarationContext annotationTypeDeclaration() {
			return getRuleContext(AnnotationTypeDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceMemberDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public InterfaceMemberDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_interfaceMemberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMemberDeclarationContext interfaceMemberDeclaration(List<ModifierContext> mods) throws RecognitionException {
		InterfaceMemberDeclarationContext _localctx = new InterfaceMemberDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 80, RULE_interfaceMemberDeclaration);
		try {
			setState(647);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(640);
				fieldDeclaration(mods);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(641);
				recordDeclaration(mods);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(642);
				interfaceMethodDeclaration(mods);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(643);
				interfaceDeclaration(mods);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(644);
				annotationTypeDeclaration(mods);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(645);
				classDeclaration(mods);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(646);
				enumDeclaration(mods);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceMethodDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public ArrayDeclaratorContext arrayDeclarator;
		public List<ArrayDeclaratorContext> cStyleArrDec = new ArrayList<ArrayDeclaratorContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public MethodBodyContext methodBody() {
			return getRuleContext(MethodBodyContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public ThrowsListContext throwsList() {
			return getRuleContext(ThrowsListContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public InterfaceMethodDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public InterfaceMethodDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_interfaceMethodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInterfaceMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMethodDeclarationContext interfaceMethodDeclaration(List<ModifierContext> mods) throws RecognitionException {
		InterfaceMethodDeclarationContext _localctx = new InterfaceMethodDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 82, RULE_interfaceMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(649);
				typeParameters();
				}
			}

			setState(652);
			((InterfaceMethodDeclarationContext)_localctx).type = typeType(true);
			setState(653);
			id();
			setState(654);
			formalParameters();
			setState(658);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(655);
				((InterfaceMethodDeclarationContext)_localctx).arrayDeclarator = arrayDeclarator();
				((InterfaceMethodDeclarationContext)_localctx).cStyleArrDec.add(((InterfaceMethodDeclarationContext)_localctx).arrayDeclarator);
				}
				}
				setState(660);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(662);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_THROWS) {
				{
				setState(661);
				throwsList();
				}
			}

			setState(664);
			methodBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclaratorsContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public VariableDeclaratorsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public VariableDeclaratorsContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods, TypeTypeContext type) {
			super(parent, invokingState);
			this.mods = mods;
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarators; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableDeclarators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorsContext variableDeclarators(List<ModifierContext> mods,TypeTypeContext type) throws RecognitionException {
		VariableDeclaratorsContext _localctx = new VariableDeclaratorsContext(_ctx, getState(), mods, type);
		enterRule(_localctx, 84, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(666);
			variableDeclarator(mods, type);
			setState(671);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(667);
				match(COMMA);
				setState(668);
				variableDeclarator(mods, type);
				}
				}
				setState(673);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclaratorContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(JavaLanguageParser.ASSIGN, 0); }
		public VariableInitializerContext variableInitializer() {
			return getRuleContext(VariableInitializerContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods, TypeTypeContext type) {
			super(parent, invokingState);
			this.mods = mods;
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator(List<ModifierContext> mods,TypeTypeContext type) throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState(), mods, type);
		enterRule(_localctx, 86, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			id();
			setState(678);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(675);
				arrayDeclarator();
				}
				}
				setState(680);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(683);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(681);
				match(ASSIGN);
				setState(682);
				variableInitializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclaratorIdContext extends ParserRuleContext {
		public List<VariableModifierContext> mods;
		public ParserRuleContext type;
		public TerminalNode LITERAL_THIS() { return getToken(JavaLanguageParser.LITERAL_THIS, 0); }
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public VariableDeclaratorIdContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public VariableDeclaratorIdContext(ParserRuleContext parent, int invokingState, List<VariableModifierContext> mods, ParserRuleContext type) {
			super(parent, invokingState);
			this.mods = mods;
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaratorId; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableDeclaratorId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorIdContext variableDeclaratorId(List<VariableModifierContext> mods,ParserRuleContext type) throws RecognitionException {
		VariableDeclaratorIdContext _localctx = new VariableDeclaratorIdContext(_ctx, getState(), mods, type);
		enterRule(_localctx, 88, RULE_variableDeclaratorId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(691);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LITERAL_THIS:
				{
				setState(685);
				match(LITERAL_THIS);
				}
				break;
			case IDENT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				{
				{
				setState(686);
				qualifiedName();
				setState(689);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOT) {
					{
					setState(687);
					match(DOT);
					setState(688);
					match(LITERAL_THIS);
					}
				}

				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(696);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(693);
				arrayDeclarator();
				}
				}
				setState(698);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableInitializerContext extends ParserRuleContext {
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableInitializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableInitializerContext variableInitializer() throws RecognitionException {
		VariableInitializerContext _localctx = new VariableInitializerContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_variableInitializer);
		try {
			setState(701);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LCURLY:
				enterOuterAlt(_localctx, 1);
				{
				setState(699);
				arrayInitializer();
				}
				break;
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
			case IDENT:
			case LPAREN:
			case LITERAL_THIS:
			case LITERAL_SUPER:
			case LITERAL_SWITCH:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case BNOT:
			case LNOT:
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case LITERAL_NULL:
			case LITERAL_NEW:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case AT:
			case FLOAT_LITERAL:
			case DOUBLE_LITERAL:
			case HEX_FLOAT_LITERAL:
			case HEX_DOUBLE_LITERAL:
			case LITERAL_RECORD:
			case TEXT_BLOCK_LITERAL_BEGIN:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
			case DECIMAL_LITERAL_LONG:
			case DECIMAL_LITERAL:
			case HEX_LITERAL_LONG:
			case HEX_LITERAL:
			case OCT_LITERAL_LONG:
			case OCT_LITERAL:
			case BINARY_LITERAL_LONG:
			case BINARY_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(700);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<VariableInitializerContext> variableInitializer() {
			return getRuleContexts(VariableInitializerContext.class);
		}
		public VariableInitializerContext variableInitializer(int i) {
			return getRuleContext(VariableInitializerContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			match(LCURLY);
			setState(712);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101264847871L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
				{
				setState(704);
				variableInitializer();
				setState(709);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(705);
						match(COMMA);
						setState(706);
						variableInitializer();
						}
						} 
					}
					setState(711);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
				}
				}
			}

			setState(715);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(714);
				match(COMMA);
				}
			}

			setState(717);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassOrInterfaceTypeContext extends ParserRuleContext {
		public boolean createImaginaryNode;
		public ClassOrInterfaceTypeExtendedContext classOrInterfaceTypeExtended;
		public List<ClassOrInterfaceTypeExtendedContext> extended = new ArrayList<ClassOrInterfaceTypeExtendedContext>();
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public List<ClassOrInterfaceTypeExtendedContext> classOrInterfaceTypeExtended() {
			return getRuleContexts(ClassOrInterfaceTypeExtendedContext.class);
		}
		public ClassOrInterfaceTypeExtendedContext classOrInterfaceTypeExtended(int i) {
			return getRuleContext(ClassOrInterfaceTypeExtendedContext.class,i);
		}
		public ClassOrInterfaceTypeContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ClassOrInterfaceTypeContext(ParserRuleContext parent, int invokingState, boolean createImaginaryNode) {
			super(parent, invokingState);
			this.createImaginaryNode = createImaginaryNode;
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassOrInterfaceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrInterfaceTypeContext classOrInterfaceType(boolean createImaginaryNode) throws RecognitionException {
		ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(_ctx, getState(), createImaginaryNode);
		enterRule(_localctx, 94, RULE_classOrInterfaceType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(719);
			annotations(false);
			setState(720);
			id();
			setState(722);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				{
				setState(721);
				typeArguments();
				}
				break;
			}
			setState(727);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(724);
					((ClassOrInterfaceTypeContext)_localctx).classOrInterfaceTypeExtended = classOrInterfaceTypeExtended();
					((ClassOrInterfaceTypeContext)_localctx).extended.add(((ClassOrInterfaceTypeContext)_localctx).classOrInterfaceTypeExtended);
					}
					} 
				}
				setState(729);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassOrInterfaceTypeExtendedContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ClassOrInterfaceTypeExtendedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceTypeExtended; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassOrInterfaceTypeExtended(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrInterfaceTypeExtendedContext classOrInterfaceTypeExtended() throws RecognitionException {
		ClassOrInterfaceTypeExtendedContext _localctx = new ClassOrInterfaceTypeExtendedContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_classOrInterfaceTypeExtended);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			match(DOT);
			setState(731);
			annotations(false);
			setState(732);
			id();
			setState(734);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				{
				setState(733);
				typeArguments();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentContext extends ParserRuleContext {
		public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgument; }
	 
		public TypeArgumentContext() { }
		public void copyFrom(TypeArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WildCardTypeArgumentContext extends TypeArgumentContext {
		public Token upperBound;
		public Token lowerBound;
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(JavaLanguageParser.QUESTION, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public TerminalNode EXTENDS_CLAUSE() { return getToken(JavaLanguageParser.EXTENDS_CLAUSE, 0); }
		public TerminalNode LITERAL_SUPER() { return getToken(JavaLanguageParser.LITERAL_SUPER, 0); }
		public WildCardTypeArgumentContext(TypeArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitWildCardTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTypeArgumentContext extends TypeArgumentContext {
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public SimpleTypeArgumentContext(TypeArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSimpleTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentContext typeArgument() throws RecognitionException {
		TypeArgumentContext _localctx = new TypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_typeArgument);
		int _la;
		try {
			setState(746);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				_localctx = new SimpleTypeArgumentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(736);
				typeType(false);
				}
				break;
			case 2:
				_localctx = new WildCardTypeArgumentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(737);
				annotations(false);
				setState(738);
				match(QUESTION);
				setState(744);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EXTENDS_CLAUSE || _la==LITERAL_SUPER) {
					{
					setState(741);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case EXTENDS_CLAUSE:
						{
						setState(739);
						((WildCardTypeArgumentContext)_localctx).upperBound = match(EXTENDS_CLAUSE);
						}
						break;
					case LITERAL_SUPER:
						{
						setState(740);
						((WildCardTypeArgumentContext)_localctx).lowerBound = match(LITERAL_SUPER);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(743);
					typeType(false);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameListContext extends ParserRuleContext {
		public List<AnnotationsContext> annotations() {
			return getRuleContexts(AnnotationsContext.class);
		}
		public AnnotationsContext annotations(int i) {
			return getRuleContext(AnnotationsContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public QualifiedNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedNameList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitQualifiedNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameListContext qualifiedNameList() throws RecognitionException {
		QualifiedNameListContext _localctx = new QualifiedNameListContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_qualifiedNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(748);
			annotations(false);
			setState(749);
			qualifiedName();
			setState(756);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(750);
				match(COMMA);
				setState(751);
				annotations(false);
				setState(752);
				qualifiedName();
				}
				}
				setState(758);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FormalParametersContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FormalParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFormalParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			match(LPAREN);
			setState(761);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 575898352105816064L) != 0) || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 90093571536846849L) != 0)) {
				{
				setState(760);
				formalParameterList();
				}
			}

			setState(763);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FormalParameterListContext extends ParserRuleContext {
		public List<FormalParameterContext> formalParameter() {
			return getRuleContexts(FormalParameterContext.class);
		}
		public FormalParameterContext formalParameter(int i) {
			return getRuleContext(FormalParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public LastFormalParameterContext lastFormalParameter() {
			return getRuleContext(LastFormalParameterContext.class,0);
		}
		public FormalParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFormalParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterListContext formalParameterList() throws RecognitionException {
		FormalParameterListContext _localctx = new FormalParameterListContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_formalParameterList);
		int _la;
		try {
			int _alt;
			setState(778);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(765);
				formalParameter();
				setState(770);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(766);
						match(COMMA);
						setState(767);
						formalParameter();
						}
						} 
					}
					setState(772);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
				}
				setState(775);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(773);
					match(COMMA);
					setState(774);
					lastFormalParameter();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(777);
				lastFormalParameter();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FormalParameterContext extends ParserRuleContext {
		public VariableModifierContext variableModifier;
		public List<VariableModifierContext> mods = new ArrayList<VariableModifierContext>();
		public TypeTypeContext type;
		public VariableDeclaratorIdContext variableDeclaratorId() {
			return getRuleContext(VariableDeclaratorIdContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_formalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(783);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(780);
					((FormalParameterContext)_localctx).variableModifier = variableModifier();
					((FormalParameterContext)_localctx).mods.add(((FormalParameterContext)_localctx).variableModifier);
					}
					} 
				}
				setState(785);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			}
			setState(786);
			((FormalParameterContext)_localctx).type = typeType(true);
			setState(787);
			variableDeclaratorId(_localctx.mods, _localctx.type);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LastFormalParameterContext extends ParserRuleContext {
		public VariableModifierContext variableModifier;
		public List<VariableModifierContext> mods = new ArrayList<VariableModifierContext>();
		public TypeTypeContext type;
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TerminalNode ELLIPSIS() { return getToken(JavaLanguageParser.ELLIPSIS, 0); }
		public VariableDeclaratorIdContext variableDeclaratorId() {
			return getRuleContext(VariableDeclaratorIdContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public LastFormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lastFormalParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLastFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LastFormalParameterContext lastFormalParameter() throws RecognitionException {
		LastFormalParameterContext _localctx = new LastFormalParameterContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_lastFormalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(792);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,80,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(789);
					((LastFormalParameterContext)_localctx).variableModifier = variableModifier();
					((LastFormalParameterContext)_localctx).mods.add(((LastFormalParameterContext)_localctx).variableModifier);
					}
					} 
				}
				setState(794);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,80,_ctx);
			}
			setState(795);
			((LastFormalParameterContext)_localctx).type = typeType(true);
			setState(796);
			annotations(false);
			setState(797);
			match(ELLIPSIS);
			setState(798);
			variableDeclaratorId(_localctx.mods, _localctx.type);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public QualifiedNameExtendedContext qualifiedNameExtended;
		public List<QualifiedNameExtendedContext> extended = new ArrayList<QualifiedNameExtendedContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<QualifiedNameExtendedContext> qualifiedNameExtended() {
			return getRuleContexts(QualifiedNameExtendedContext.class);
		}
		public QualifiedNameExtendedContext qualifiedNameExtended(int i) {
			return getRuleContext(QualifiedNameExtendedContext.class,i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(800);
			id();
			setState(804);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(801);
					((QualifiedNameContext)_localctx).qualifiedNameExtended = qualifiedNameExtended();
					((QualifiedNameContext)_localctx).extended.add(((QualifiedNameContext)_localctx).qualifiedNameExtended);
					}
					} 
				}
				setState(806);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameExtendedContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public QualifiedNameExtendedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedNameExtended; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitQualifiedNameExtended(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameExtendedContext qualifiedNameExtended() throws RecognitionException {
		QualifiedNameExtendedContext _localctx = new QualifiedNameExtendedContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_qualifiedNameExtended);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(807);
			match(DOT);
			setState(808);
			annotations(false);
			setState(809);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public FloatLiteralContext floatLiteral() {
			return getRuleContext(FloatLiteralContext.class,0);
		}
		public TextBlockLiteralContext textBlockLiteral() {
			return getRuleContext(TextBlockLiteralContext.class,0);
		}
		public TerminalNode CHAR_LITERAL() { return getToken(JavaLanguageParser.CHAR_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(JavaLanguageParser.STRING_LITERAL, 0); }
		public TerminalNode LITERAL_TRUE() { return getToken(JavaLanguageParser.LITERAL_TRUE, 0); }
		public TerminalNode LITERAL_FALSE() { return getToken(JavaLanguageParser.LITERAL_FALSE, 0); }
		public TerminalNode LITERAL_NULL() { return getToken(JavaLanguageParser.LITERAL_NULL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_literal);
		try {
			setState(819);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL_LONG:
			case DECIMAL_LITERAL:
			case HEX_LITERAL_LONG:
			case HEX_LITERAL:
			case OCT_LITERAL_LONG:
			case OCT_LITERAL:
			case BINARY_LITERAL_LONG:
			case BINARY_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(811);
				integerLiteral();
				}
				break;
			case FLOAT_LITERAL:
			case DOUBLE_LITERAL:
			case HEX_FLOAT_LITERAL:
			case HEX_DOUBLE_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(812);
				floatLiteral();
				}
				break;
			case TEXT_BLOCK_LITERAL_BEGIN:
				enterOuterAlt(_localctx, 3);
				{
				setState(813);
				textBlockLiteral();
				}
				break;
			case CHAR_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(814);
				match(CHAR_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(815);
				match(STRING_LITERAL);
				}
				break;
			case LITERAL_TRUE:
				enterOuterAlt(_localctx, 6);
				{
				setState(816);
				match(LITERAL_TRUE);
				}
				break;
			case LITERAL_FALSE:
				enterOuterAlt(_localctx, 7);
				{
				setState(817);
				match(LITERAL_FALSE);
				}
				break;
			case LITERAL_NULL:
				enterOuterAlt(_localctx, 8);
				{
				setState(818);
				match(LITERAL_NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntegerLiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL_LONG() { return getToken(JavaLanguageParser.DECIMAL_LITERAL_LONG, 0); }
		public TerminalNode DECIMAL_LITERAL() { return getToken(JavaLanguageParser.DECIMAL_LITERAL, 0); }
		public TerminalNode HEX_LITERAL_LONG() { return getToken(JavaLanguageParser.HEX_LITERAL_LONG, 0); }
		public TerminalNode HEX_LITERAL() { return getToken(JavaLanguageParser.HEX_LITERAL, 0); }
		public TerminalNode OCT_LITERAL_LONG() { return getToken(JavaLanguageParser.OCT_LITERAL_LONG, 0); }
		public TerminalNode OCT_LITERAL() { return getToken(JavaLanguageParser.OCT_LITERAL, 0); }
		public TerminalNode BINARY_LITERAL_LONG() { return getToken(JavaLanguageParser.BINARY_LITERAL_LONG, 0); }
		public TerminalNode BINARY_LITERAL() { return getToken(JavaLanguageParser.BINARY_LITERAL, 0); }
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(821);
			_la = _input.LA(1);
			if ( !(((((_la - 228)) & ~0x3f) == 0 && ((1L << (_la - 228)) & 255L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends ParserRuleContext {
		public TerminalNode DOUBLE_LITERAL() { return getToken(JavaLanguageParser.DOUBLE_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(JavaLanguageParser.FLOAT_LITERAL, 0); }
		public TerminalNode HEX_DOUBLE_LITERAL() { return getToken(JavaLanguageParser.HEX_DOUBLE_LITERAL, 0); }
		public TerminalNode HEX_FLOAT_LITERAL() { return getToken(JavaLanguageParser.HEX_FLOAT_LITERAL, 0); }
		public FloatLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatLiteralContext floatLiteral() throws RecognitionException {
		FloatLiteralContext _localctx = new FloatLiteralContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_floatLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(823);
			_la = _input.LA(1);
			if ( !(((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 15L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TextBlockLiteralContext extends ParserRuleContext {
		public TerminalNode TEXT_BLOCK_LITERAL_BEGIN() { return getToken(JavaLanguageParser.TEXT_BLOCK_LITERAL_BEGIN, 0); }
		public TerminalNode TEXT_BLOCK_CONTENT() { return getToken(JavaLanguageParser.TEXT_BLOCK_CONTENT, 0); }
		public TerminalNode TEXT_BLOCK_LITERAL_END() { return getToken(JavaLanguageParser.TEXT_BLOCK_LITERAL_END, 0); }
		public TextBlockLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_textBlockLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTextBlockLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TextBlockLiteralContext textBlockLiteral() throws RecognitionException {
		TextBlockLiteralContext _localctx = new TextBlockLiteralContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_textBlockLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(825);
			match(TEXT_BLOCK_LITERAL_BEGIN);
			setState(826);
			match(TEXT_BLOCK_CONTENT);
			setState(827);
			match(TEXT_BLOCK_LITERAL_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationsContext extends ParserRuleContext {
		public boolean createImaginaryNode;
		public AnnotationContext annotation;
		public List<AnnotationContext> anno = new ArrayList<AnnotationContext>();
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public AnnotationsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AnnotationsContext(ParserRuleContext parent, int invokingState, boolean createImaginaryNode) {
			super(parent, invokingState);
			this.createImaginaryNode = createImaginaryNode;
		}
		@Override public int getRuleIndex() { return RULE_annotations; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationsContext annotations(boolean createImaginaryNode) throws RecognitionException {
		AnnotationsContext _localctx = new AnnotationsContext(_ctx, getState(), createImaginaryNode);
		enterRule(_localctx, 122, RULE_annotations);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(832);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(829);
					((AnnotationsContext)_localctx).annotation = annotation();
					((AnnotationsContext)_localctx).anno.add(((AnnotationsContext)_localctx).annotation);
					}
					} 
				}
				setState(834);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(JavaLanguageParser.AT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ElementValuePairsContext elementValuePairs() {
			return getRuleContext(ElementValuePairsContext.class,0);
		}
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(835);
			match(AT);
			setState(836);
			qualifiedName();
			setState(843);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(837);
				match(LPAREN);
				setState(840);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
				case 1:
					{
					setState(838);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(839);
					elementValue();
					}
					break;
				}
				setState(842);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValuePairsContext extends ParserRuleContext {
		public List<ElementValuePairContext> elementValuePair() {
			return getRuleContexts(ElementValuePairContext.class);
		}
		public ElementValuePairContext elementValuePair(int i) {
			return getRuleContext(ElementValuePairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public ElementValuePairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePairs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitElementValuePairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairsContext elementValuePairs() throws RecognitionException {
		ElementValuePairsContext _localctx = new ElementValuePairsContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(845);
			elementValuePair();
			setState(850);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(846);
				match(COMMA);
				setState(847);
				elementValuePair();
				}
				}
				setState(852);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValuePairContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(JavaLanguageParser.ASSIGN, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitElementValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(853);
			id();
			setState(854);
			match(ASSIGN);
			setState(855);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public ElementValueArrayInitializerContext elementValueArrayInitializer() {
			return getRuleContext(ElementValueArrayInitializerContext.class,0);
		}
		public ElementValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitElementValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_elementValue);
		try {
			setState(860);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(857);
				expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(858);
				annotation();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(859);
				elementValueArrayInitializer();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValueArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<ElementValueContext> elementValue() {
			return getRuleContexts(ElementValueContext.class);
		}
		public ElementValueContext elementValue(int i) {
			return getRuleContext(ElementValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueArrayInitializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitElementValueArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(862);
			match(LCURLY);
			setState(871);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101264847871L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
				{
				setState(863);
				elementValue();
				setState(868);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(864);
						match(COMMA);
						setState(865);
						elementValue();
						}
						} 
					}
					setState(870);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
				}
				}
			}

			setState(874);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(873);
				match(COMMA);
				}
			}

			setState(876);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationTypeDeclarationContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TerminalNode AT() { return getToken(JavaLanguageParser.AT, 0); }
		public TerminalNode LITERAL_INTERFACE() { return getToken(JavaLanguageParser.LITERAL_INTERFACE, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public AnnotationTypeBodyContext annotationTypeBody() {
			return getRuleContext(AnnotationTypeBodyContext.class,0);
		}
		public AnnotationTypeDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AnnotationTypeDeclarationContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeDeclarationContext annotationTypeDeclaration(List<ModifierContext> mods) throws RecognitionException {
		AnnotationTypeDeclarationContext _localctx = new AnnotationTypeDeclarationContext(_ctx, getState(), mods);
		enterRule(_localctx, 134, RULE_annotationTypeDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(878);
			match(AT);
			setState(879);
			match(LITERAL_INTERFACE);
			setState(880);
			id();
			setState(881);
			annotationTypeBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationTypeBodyContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<AnnotationTypeElementDeclarationContext> annotationTypeElementDeclaration() {
			return getRuleContexts(AnnotationTypeElementDeclarationContext.class);
		}
		public AnnotationTypeElementDeclarationContext annotationTypeElementDeclaration(int i) {
			return getRuleContext(AnnotationTypeElementDeclarationContext.class,i);
		}
		public AnnotationTypeBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationTypeBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeBodyContext annotationTypeBody() throws RecognitionException {
		AnnotationTypeBodyContext _localctx = new AnnotationTypeBodyContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_annotationTypeBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(883);
			match(LCURLY);
			setState(887);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028803458268231L) != 0) || ((((_la - 153)) & ~0x3f) == 0 && ((1L << (_la - 153)) & 2828401303477157889L) != 0) || _la==LITERAL_UNDERSCORE || _la==LITERAL_MODULE) {
				{
				{
				setState(884);
				annotationTypeElementDeclaration();
				}
				}
				setState(889);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(890);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationTypeElementDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public AnnotationTypeElementRestContext annotationTypeElementRest() {
			return getRuleContext(AnnotationTypeElementRestContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public AnnotationTypeElementDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeElementDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationTypeElementDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeElementDeclarationContext annotationTypeElementDeclaration() throws RecognitionException {
		AnnotationTypeElementDeclarationContext _localctx = new AnnotationTypeElementDeclarationContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_annotationTypeElementDeclaration);
		try {
			int _alt;
			setState(900);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
			case ABSTRACT:
			case STRICTFP:
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
			case IDENT:
			case LITERAL_PRIVATE:
			case LITERAL_PUBLIC:
			case LITERAL_PROTECTED:
			case LITERAL_STATIC:
			case LITERAL_TRANSIENT:
			case LITERAL_NATIVE:
			case LITERAL_SYNCHRONIZED:
			case LITERAL_VOLATILE:
			case LITERAL_CLASS:
			case LITERAL_INTERFACE:
			case LITERAL_DEFAULT:
			case ENUM:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				enterOuterAlt(_localctx, 1);
				{
				setState(895);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(892);
						((AnnotationTypeElementDeclarationContext)_localctx).modifier = modifier();
						((AnnotationTypeElementDeclarationContext)_localctx).mods.add(((AnnotationTypeElementDeclarationContext)_localctx).modifier);
						}
						} 
					}
					setState(897);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
				}
				setState(898);
				annotationTypeElementRest(_localctx.mods);
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(899);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationTypeElementRestContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public AnnotationTypeElementRestContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AnnotationTypeElementRestContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods) {
			super(parent, invokingState);
			this.mods = mods;
		}
		@Override public int getRuleIndex() { return RULE_annotationTypeElementRest; }
	 
		public AnnotationTypeElementRestContext() { }
		public void copyFrom(AnnotationTypeElementRestContext ctx) {
			super.copyFrom(ctx);
			this.mods = ctx.mods;
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationFieldContext extends AnnotationTypeElementRestContext {
		public TypeTypeContext type;
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public AnnotationMethodRestContext annotationMethodRest() {
			return getRuleContext(AnnotationMethodRestContext.class,0);
		}
		public AnnotationConstantRestContext annotationConstantRest() {
			return getRuleContext(AnnotationConstantRestContext.class,0);
		}
		public AnnotationFieldContext(AnnotationTypeElementRestContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationField(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationTypeContext extends AnnotationTypeElementRestContext {
		public TypeTypeContext type;
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public RecordDeclarationContext recordDeclaration() {
			return getRuleContext(RecordDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public AnnotationTypeDeclarationContext annotationTypeDeclaration() {
			return getRuleContext(AnnotationTypeDeclarationContext.class,0);
		}
		public AnnotationTypeContext(AnnotationTypeElementRestContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationTypeElementRestContext annotationTypeElementRest(List<ModifierContext> mods) throws RecognitionException {
		AnnotationTypeElementRestContext _localctx = new AnnotationTypeElementRestContext(_ctx, getState(), mods);
		enterRule(_localctx, 140, RULE_annotationTypeElementRest);
		try {
			setState(932);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				_localctx = new AnnotationFieldContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(902);
				((AnnotationFieldContext)_localctx).type = typeType(true);
				setState(905);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
				case 1:
					{
					setState(903);
					annotationMethodRest(mods, ((AnnotationFieldContext) _localctx).type);
					}
					break;
				case 2:
					{
					setState(904);
					annotationConstantRest(mods, ((AnnotationFieldContext) _localctx).type);
					}
					break;
				}
				setState(907);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(909);
				((AnnotationTypeContext)_localctx).type = typeType(true);
				setState(910);
				match(SEMI);
				}
				break;
			case 3:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(912);
				classDeclaration(mods);
				setState(914);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
				case 1:
					{
					setState(913);
					match(SEMI);
					}
					break;
				}
				}
				break;
			case 4:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(916);
				recordDeclaration(mods);
				setState(918);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
				case 1:
					{
					setState(917);
					match(SEMI);
					}
					break;
				}
				}
				break;
			case 5:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(920);
				interfaceDeclaration(mods);
				setState(922);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(921);
					match(SEMI);
					}
					break;
				}
				}
				break;
			case 6:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(924);
				enumDeclaration(mods);
				setState(926);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
				case 1:
					{
					setState(925);
					match(SEMI);
					}
					break;
				}
				}
				break;
			case 7:
				_localctx = new AnnotationTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(928);
				annotationTypeDeclaration(mods);
				setState(930);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
				case 1:
					{
					setState(929);
					match(SEMI);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationMethodRestContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public ArrayDeclaratorContext arrayDeclarator;
		public List<ArrayDeclaratorContext> cStyleArrDec = new ArrayList<ArrayDeclaratorContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public AnnotationMethodRestContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AnnotationMethodRestContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods, TypeTypeContext type) {
			super(parent, invokingState);
			this.mods = mods;
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_annotationMethodRest; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationMethodRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationMethodRestContext annotationMethodRest(List<ModifierContext> mods,TypeTypeContext type) throws RecognitionException {
		AnnotationMethodRestContext _localctx = new AnnotationMethodRestContext(_ctx, getState(), mods, type);
		enterRule(_localctx, 142, RULE_annotationMethodRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(934);
			id();
			setState(935);
			match(LPAREN);
			setState(936);
			match(RPAREN);
			setState(940);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK || _la==AT) {
				{
				{
				setState(937);
				((AnnotationMethodRestContext)_localctx).arrayDeclarator = arrayDeclarator();
				((AnnotationMethodRestContext)_localctx).cStyleArrDec.add(((AnnotationMethodRestContext)_localctx).arrayDeclarator);
				}
				}
				setState(942);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(944);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LITERAL_DEFAULT) {
				{
				setState(943);
				defaultValue();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationConstantRestContext extends ParserRuleContext {
		public List<ModifierContext> mods;
		public TypeTypeContext type;
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public AnnotationConstantRestContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AnnotationConstantRestContext(ParserRuleContext parent, int invokingState, List<ModifierContext> mods, TypeTypeContext type) {
			super(parent, invokingState);
			this.mods = mods;
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_annotationConstantRest; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAnnotationConstantRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationConstantRestContext annotationConstantRest(List<ModifierContext> mods,TypeTypeContext type) throws RecognitionException {
		AnnotationConstantRestContext _localctx = new AnnotationConstantRestContext(_ctx, getState(), mods, type);
		enterRule(_localctx, 144, RULE_annotationConstantRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(946);
			variableDeclarators(mods, type);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultValueContext extends ParserRuleContext {
		public TerminalNode LITERAL_DEFAULT() { return getToken(JavaLanguageParser.LITERAL_DEFAULT, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(948);
			match(LITERAL_DEFAULT);
			setState(949);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorBlockContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public ExplicitConstructorInvocationContext explicitConstructorInvocation() {
			return getRuleContext(ExplicitConstructorInvocationContext.class,0);
		}
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public ConstructorBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitConstructorBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorBlockContext constructorBlock() throws RecognitionException {
		ConstructorBlockContext _localctx = new ConstructorBlockContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_constructorBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(951);
			match(LCURLY);
			setState(953);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				{
				setState(952);
				explicitConstructorInvocation();
				}
				break;
			}
			setState(958);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,104,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(955);
					blockStatement();
					}
					} 
				}
				setState(960);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,104,_ctx);
			}
			setState(961);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitConstructorInvocationContext extends ParserRuleContext {
		public ExplicitConstructorInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitConstructorInvocation; }
	 
		public ExplicitConstructorInvocationContext() { }
		public void copyFrom(ExplicitConstructorInvocationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitCtorCallContext extends ExplicitConstructorInvocationContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TerminalNode LITERAL_THIS() { return getToken(JavaLanguageParser.LITERAL_THIS, 0); }
		public TerminalNode LITERAL_SUPER() { return getToken(JavaLanguageParser.LITERAL_SUPER, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ExplicitCtorCallContext(ExplicitConstructorInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitExplicitCtorCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryCtorCallContext extends ExplicitConstructorInvocationContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public TerminalNode LITERAL_SUPER() { return getToken(JavaLanguageParser.LITERAL_SUPER, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public PrimaryCtorCallContext(ExplicitConstructorInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPrimaryCtorCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplicitConstructorInvocationContext explicitConstructorInvocation() throws RecognitionException {
		ExplicitConstructorInvocationContext _localctx = new ExplicitConstructorInvocationContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_explicitConstructorInvocation);
		int _la;
		try {
			setState(979);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				_localctx = new ExplicitCtorCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(964);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(963);
					typeArguments();
					}
				}

				setState(966);
				_la = _input.LA(1);
				if ( !(_la==LITERAL_THIS || _la==LITERAL_SUPER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(967);
				arguments();
				setState(968);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new PrimaryCtorCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(970);
				expr(0);
				setState(971);
				match(DOT);
				setState(973);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(972);
					typeArguments();
					}
				}

				setState(975);
				match(LITERAL_SUPER);
				setState(976);
				arguments();
				setState(977);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(981);
			match(LCURLY);
			setState(985);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(982);
					blockStatement();
					}
					} 
				}
				setState(987);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			}
			setState(988);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockStatementContext extends ParserRuleContext {
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
	 
		public BlockStatementContext() { }
		public void copyFrom(BlockStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StatContext extends BlockStatementContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatContext(BlockStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LocalVarContext extends BlockStatementContext {
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public LocalVarContext(BlockStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLocalVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LocalTypeContext extends BlockStatementContext {
		public LocalTypeDeclarationContext localTypeDeclaration() {
			return getRuleContext(LocalTypeDeclarationContext.class,0);
		}
		public LocalTypeContext(BlockStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLocalType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_blockStatement);
		try {
			setState(996);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				_localctx = new LocalVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(990);
				if (!(!isYieldStatement())) throw new FailedPredicateException(this, "!isYieldStatement()");
				setState(991);
				localVariableDeclaration();
				setState(992);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new StatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(994);
				statement();
				}
				break;
			case 3:
				_localctx = new LocalTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(995);
				localTypeDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LocalVariableDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public TypeTypeContext type;
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLocalVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_localVariableDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1001);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,110,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(998);
					((LocalVariableDeclarationContext)_localctx).modifier = modifier();
					((LocalVariableDeclarationContext)_localctx).mods.add(((LocalVariableDeclarationContext)_localctx).modifier);
					}
					} 
				}
				setState(1003);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,110,_ctx);
			}
			setState(1004);
			((LocalVariableDeclarationContext)_localctx).type = typeType(true);
			setState(1005);
			variableDeclarators(_localctx.mods, _localctx.type);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LocalTypeDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public RecordDeclarationContext recordDeclaration() {
			return getRuleContext(RecordDeclarationContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public LocalTypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localTypeDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLocalTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalTypeDeclarationContext localTypeDeclaration() throws RecognitionException {
		LocalTypeDeclarationContext _localctx = new LocalTypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_localTypeDeclaration);
		int _la;
		try {
			setState(1020);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
			case ABSTRACT:
			case STRICTFP:
			case LITERAL_PRIVATE:
			case LITERAL_PUBLIC:
			case LITERAL_PROTECTED:
			case LITERAL_STATIC:
			case LITERAL_TRANSIENT:
			case LITERAL_NATIVE:
			case LITERAL_SYNCHRONIZED:
			case LITERAL_VOLATILE:
			case LITERAL_CLASS:
			case LITERAL_INTERFACE:
			case LITERAL_DEFAULT:
			case ENUM:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
				enterOuterAlt(_localctx, 1);
				{
				setState(1010);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028798088511495L) != 0) || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 1649267441665L) != 0)) {
					{
					{
					setState(1007);
					((LocalTypeDeclarationContext)_localctx).modifier = modifier();
					((LocalTypeDeclarationContext)_localctx).mods.add(((LocalTypeDeclarationContext)_localctx).modifier);
					}
					}
					setState(1012);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1017);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LITERAL_CLASS:
					{
					setState(1013);
					classDeclaration(_localctx.mods);
					}
					break;
				case ENUM:
					{
					setState(1014);
					enumDeclaration(_localctx.mods);
					}
					break;
				case LITERAL_INTERFACE:
					{
					setState(1015);
					interfaceDeclaration(_localctx.mods);
					}
					break;
				case LITERAL_RECORD:
					{
					setState(1016);
					recordDeclaration(_localctx.mods);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(1019);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssertExpContext extends StatementContext {
		public TerminalNode ASSERT() { return getToken(JavaLanguageParser.ASSERT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public AssertExpContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitAssertExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStatContext extends StatementContext {
		public TerminalNode LITERAL_IF() { return getToken(JavaLanguageParser.LITERAL_IF, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ElseStatContext elseStat() {
			return getRuleContext(ElseStatContext.class,0);
		}
		public IfStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitIfStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockStatContext extends StatementContext {
		public BlockContext blockLabel;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBlockStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TryWithResourceStatContext extends StatementContext {
		public TerminalNode LITERAL_TRY() { return getToken(JavaLanguageParser.LITERAL_TRY, 0); }
		public ResourceSpecificationContext resourceSpecification() {
			return getRuleContext(ResourceSpecificationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<CatchClauseContext> catchClause() {
			return getRuleContexts(CatchClauseContext.class);
		}
		public CatchClauseContext catchClause(int i) {
			return getRuleContext(CatchClauseContext.class,i);
		}
		public FinallyBlockContext finallyBlock() {
			return getRuleContext(FinallyBlockContext.class,0);
		}
		public TryWithResourceStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTryWithResourceStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TryStatContext extends StatementContext {
		public TerminalNode LITERAL_TRY() { return getToken(JavaLanguageParser.LITERAL_TRY, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext finallyBlock() {
			return getRuleContext(FinallyBlockContext.class,0);
		}
		public List<CatchClauseContext> catchClause() {
			return getRuleContexts(CatchClauseContext.class);
		}
		public CatchClauseContext catchClause(int i) {
			return getRuleContext(CatchClauseContext.class,i);
		}
		public TryStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTryStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SwitchStatContext extends StatementContext {
		public SwitchExpressionOrStatementContext switchExpressionOrStatement() {
			return getRuleContext(SwitchExpressionOrStatementContext.class,0);
		}
		public SwitchStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SyncStatContext extends StatementContext {
		public TerminalNode LITERAL_SYNCHRONIZED() { return getToken(JavaLanguageParser.LITERAL_SYNCHRONIZED, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SyncStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSyncStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatContext extends StatementContext {
		public TerminalNode LITERAL_CONTINUE() { return getToken(JavaLanguageParser.LITERAL_CONTINUE, 0); }
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ContinueStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitContinueStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatContext extends StatementContext {
		public TerminalNode LITERAL_BREAK() { return getToken(JavaLanguageParser.LITERAL_BREAK, 0); }
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public BreakStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBreakStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForStatContext extends StatementContext {
		public TerminalNode LITERAL_FOR() { return getToken(JavaLanguageParser.LITERAL_FOR, 0); }
		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitForStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LabelStatContext extends StatementContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public LabelStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLabelStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoStatContext extends StatementContext {
		public TerminalNode LITERAL_DO() { return getToken(JavaLanguageParser.LITERAL_DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LITERAL_WHILE() { return getToken(JavaLanguageParser.LITERAL_WHILE, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public DoStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitDoStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatContext extends StatementContext {
		public TerminalNode LITERAL_RETURN() { return getToken(JavaLanguageParser.LITERAL_RETURN, 0); }
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitReturnStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThrowStatContext extends StatementContext {
		public TerminalNode LITERAL_THROW() { return getToken(JavaLanguageParser.LITERAL_THROW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public ThrowStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitThrowStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class YieldStatContext extends StatementContext {
		public TerminalNode LITERAL_YIELD() { return getToken(JavaLanguageParser.LITERAL_YIELD, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public YieldStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitYieldStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyStatContext extends StatementContext {
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public EmptyStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEmptyStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpStatContext extends StatementContext {
		public ExpressionContext statementExpression;
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitExpStat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatContext extends StatementContext {
		public TerminalNode LITERAL_WHILE() { return getToken(JavaLanguageParser.LITERAL_WHILE, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitWhileStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_statement);
		int _la;
		try {
			int _alt;
			setState(1112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,124,_ctx) ) {
			case 1:
				_localctx = new BlockStatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1022);
				((BlockStatContext)_localctx).blockLabel = block();
				}
				break;
			case 2:
				_localctx = new AssertExpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1023);
				match(ASSERT);
				setState(1024);
				expression();
				setState(1027);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLON) {
					{
					setState(1025);
					match(COLON);
					setState(1026);
					expression();
					}
				}

				setState(1029);
				match(SEMI);
				}
				break;
			case 3:
				_localctx = new IfStatContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1031);
				match(LITERAL_IF);
				setState(1032);
				parExpression();
				setState(1033);
				statement();
				setState(1035);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
				case 1:
					{
					setState(1034);
					elseStat();
					}
					break;
				}
				}
				break;
			case 4:
				_localctx = new ForStatContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1037);
				match(LITERAL_FOR);
				setState(1038);
				forControl();
				setState(1039);
				statement();
				}
				break;
			case 5:
				_localctx = new WhileStatContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1041);
				match(LITERAL_WHILE);
				setState(1042);
				parExpression();
				setState(1043);
				statement();
				}
				break;
			case 6:
				_localctx = new DoStatContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1045);
				match(LITERAL_DO);
				setState(1046);
				statement();
				setState(1047);
				match(LITERAL_WHILE);
				setState(1048);
				parExpression();
				setState(1049);
				match(SEMI);
				}
				break;
			case 7:
				_localctx = new TryStatContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1051);
				match(LITERAL_TRY);
				setState(1052);
				block();
				setState(1062);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LITERAL_CATCH:
					{
					setState(1054); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(1053);
							catchClause();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(1056); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,116,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(1059);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,117,_ctx) ) {
					case 1:
						{
						setState(1058);
						finallyBlock();
						}
						break;
					}
					}
					break;
				case LITERAL_FINALLY:
					{
					setState(1061);
					finallyBlock();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 8:
				_localctx = new TryWithResourceStatContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1064);
				match(LITERAL_TRY);
				setState(1065);
				resourceSpecification();
				setState(1066);
				block();
				setState(1070);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,119,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1067);
						catchClause();
						}
						} 
					}
					setState(1072);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,119,_ctx);
				}
				setState(1074);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,120,_ctx) ) {
				case 1:
					{
					setState(1073);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case 9:
				_localctx = new YieldStatContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(1076);
				match(LITERAL_YIELD);
				setState(1077);
				expression();
				setState(1078);
				match(SEMI);
				}
				break;
			case 10:
				_localctx = new SwitchStatContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(1080);
				switchExpressionOrStatement();
				}
				break;
			case 11:
				_localctx = new SyncStatContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(1081);
				match(LITERAL_SYNCHRONIZED);
				setState(1082);
				parExpression();
				setState(1083);
				block();
				}
				break;
			case 12:
				_localctx = new ReturnStatContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(1085);
				match(LITERAL_RETURN);
				setState(1087);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1086);
					expression();
					}
				}

				setState(1089);
				match(SEMI);
				}
				break;
			case 13:
				_localctx = new ThrowStatContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(1090);
				match(LITERAL_THROW);
				setState(1091);
				expression();
				setState(1092);
				match(SEMI);
				}
				break;
			case 14:
				_localctx = new BreakStatContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(1094);
				match(LITERAL_BREAK);
				setState(1096);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENT || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
					{
					setState(1095);
					id();
					}
				}

				setState(1098);
				match(SEMI);
				}
				break;
			case 15:
				_localctx = new ContinueStatContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(1099);
				match(LITERAL_CONTINUE);
				setState(1101);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENT || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) {
					{
					setState(1100);
					id();
					}
				}

				setState(1103);
				match(SEMI);
				}
				break;
			case 16:
				_localctx = new EmptyStatContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(1104);
				match(SEMI);
				}
				break;
			case 17:
				_localctx = new ExpStatContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(1105);
				((ExpStatContext)_localctx).statementExpression = expression();
				setState(1106);
				match(SEMI);
				}
				break;
			case 18:
				_localctx = new LabelStatContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(1108);
				id();
				setState(1109);
				match(COLON);
				setState(1110);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchExpressionOrStatementContext extends ParserRuleContext {
		public TerminalNode LITERAL_SWITCH() { return getToken(JavaLanguageParser.LITERAL_SWITCH, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public TerminalNode LCURLY() { return getToken(JavaLanguageParser.LCURLY, 0); }
		public SwitchBlockContext switchBlock() {
			return getRuleContext(SwitchBlockContext.class,0);
		}
		public TerminalNode RCURLY() { return getToken(JavaLanguageParser.RCURLY, 0); }
		public SwitchExpressionOrStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchExpressionOrStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchExpressionOrStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchExpressionOrStatementContext switchExpressionOrStatement() throws RecognitionException {
		SwitchExpressionOrStatementContext _localctx = new SwitchExpressionOrStatementContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_switchExpressionOrStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1114);
			match(LITERAL_SWITCH);
			setState(1115);
			parExpression();
			setState(1116);
			match(LCURLY);
			switchBlockDepth++;
			setState(1118);
			switchBlock();
			switchBlockDepth--;
			setState(1120);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchBlockContext extends ParserRuleContext {
		public SwitchBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchBlock; }
	 
		public SwitchBlockContext() { }
		public void copyFrom(SwitchBlockContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SwitchRulesContext extends SwitchBlockContext {
		public List<SwitchLabeledRuleContext> switchLabeledRule() {
			return getRuleContexts(SwitchLabeledRuleContext.class);
		}
		public SwitchLabeledRuleContext switchLabeledRule(int i) {
			return getRuleContext(SwitchLabeledRuleContext.class,i);
		}
		public SwitchRulesContext(SwitchBlockContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchRules(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SwitchBlocksContext extends SwitchBlockContext {
		public SwitchBlockStatementGroupContext switchBlockStatementGroup;
		public List<SwitchBlockStatementGroupContext> groups = new ArrayList<SwitchBlockStatementGroupContext>();
		public SwitchLabelContext switchLabel;
		public List<SwitchLabelContext> emptyLabels = new ArrayList<SwitchLabelContext>();
		public List<SwitchBlockStatementGroupContext> switchBlockStatementGroup() {
			return getRuleContexts(SwitchBlockStatementGroupContext.class);
		}
		public SwitchBlockStatementGroupContext switchBlockStatementGroup(int i) {
			return getRuleContext(SwitchBlockStatementGroupContext.class,i);
		}
		public List<SwitchLabelContext> switchLabel() {
			return getRuleContexts(SwitchLabelContext.class);
		}
		public SwitchLabelContext switchLabel(int i) {
			return getRuleContext(SwitchLabelContext.class,i);
		}
		public SwitchBlocksContext(SwitchBlockContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchBlocks(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchBlockContext switchBlock() throws RecognitionException {
		SwitchBlockContext _localctx = new SwitchBlockContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_switchBlock);
		int _la;
		try {
			int _alt;
			setState(1139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				_localctx = new SwitchRulesContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1123); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1122);
					switchLabeledRule();
					}
					}
					setState(1125); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==LITERAL_CASE || _la==LITERAL_DEFAULT );
				}
				break;
			case 2:
				_localctx = new SwitchBlocksContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1130);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1127);
						((SwitchBlocksContext)_localctx).switchBlockStatementGroup = switchBlockStatementGroup();
						((SwitchBlocksContext)_localctx).groups.add(((SwitchBlocksContext)_localctx).switchBlockStatementGroup);
						}
						} 
					}
					setState(1132);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
				}
				setState(1136);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LITERAL_CASE || _la==LITERAL_DEFAULT) {
					{
					{
					setState(1133);
					((SwitchBlocksContext)_localctx).switchLabel = switchLabel();
					((SwitchBlocksContext)_localctx).emptyLabels.add(((SwitchBlocksContext)_localctx).switchLabel);
					}
					}
					setState(1138);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchLabeledRuleContext extends ParserRuleContext {
		public SwitchLabeledExpressionContext switchLabeledExpression() {
			return getRuleContext(SwitchLabeledExpressionContext.class,0);
		}
		public SwitchLabeledBlockContext switchLabeledBlock() {
			return getRuleContext(SwitchLabeledBlockContext.class,0);
		}
		public SwitchLabeledThrowContext switchLabeledThrow() {
			return getRuleContext(SwitchLabeledThrowContext.class,0);
		}
		public SwitchLabeledRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabeledRule; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchLabeledRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabeledRuleContext switchLabeledRule() throws RecognitionException {
		SwitchLabeledRuleContext _localctx = new SwitchLabeledRuleContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_switchLabeledRule);
		try {
			setState(1144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,129,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1141);
				switchLabeledExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1142);
				switchLabeledBlock();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1143);
				switchLabeledThrow();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchLabeledExpressionContext extends ParserRuleContext {
		public SwitchLabelContext switchLabel() {
			return getRuleContext(SwitchLabelContext.class,0);
		}
		public TerminalNode LAMBDA() { return getToken(JavaLanguageParser.LAMBDA, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public SwitchLabeledExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabeledExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchLabeledExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabeledExpressionContext switchLabeledExpression() throws RecognitionException {
		SwitchLabeledExpressionContext _localctx = new SwitchLabeledExpressionContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_switchLabeledExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1146);
			switchLabel();
			setState(1147);
			match(LAMBDA);
			setState(1148);
			expression();
			setState(1149);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchLabeledBlockContext extends ParserRuleContext {
		public SwitchLabelContext switchLabel() {
			return getRuleContext(SwitchLabelContext.class,0);
		}
		public TerminalNode LAMBDA() { return getToken(JavaLanguageParser.LAMBDA, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SwitchLabeledBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabeledBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchLabeledBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabeledBlockContext switchLabeledBlock() throws RecognitionException {
		SwitchLabeledBlockContext _localctx = new SwitchLabeledBlockContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_switchLabeledBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1151);
			switchLabel();
			setState(1152);
			match(LAMBDA);
			setState(1153);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchLabeledThrowContext extends ParserRuleContext {
		public SwitchLabelContext switchLabel() {
			return getRuleContext(SwitchLabelContext.class,0);
		}
		public TerminalNode LAMBDA() { return getToken(JavaLanguageParser.LAMBDA, 0); }
		public TerminalNode LITERAL_THROW() { return getToken(JavaLanguageParser.LITERAL_THROW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public SwitchLabeledThrowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabeledThrow; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchLabeledThrow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabeledThrowContext switchLabeledThrow() throws RecognitionException {
		SwitchLabeledThrowContext _localctx = new SwitchLabeledThrowContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_switchLabeledThrow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1155);
			switchLabel();
			setState(1156);
			match(LAMBDA);
			setState(1157);
			match(LITERAL_THROW);
			setState(1158);
			expression();
			setState(1159);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseStatContext extends ParserRuleContext {
		public TerminalNode LITERAL_ELSE() { return getToken(JavaLanguageParser.LITERAL_ELSE, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ElseStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitElseStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseStatContext elseStat() throws RecognitionException {
		ElseStatContext _localctx = new ElseStatContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_elseStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1161);
			match(LITERAL_ELSE);
			setState(1162);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CatchClauseContext extends ParserRuleContext {
		public TerminalNode LITERAL_CATCH() { return getToken(JavaLanguageParser.LITERAL_CATCH, 0); }
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public CatchParameterContext catchParameter() {
			return getRuleContext(CatchParameterContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_catchClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1164);
			match(LITERAL_CATCH);
			setState(1165);
			match(LPAREN);
			setState(1166);
			catchParameter();
			setState(1167);
			match(RPAREN);
			setState(1168);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CatchParameterContext extends ParserRuleContext {
		public VariableModifierContext variableModifier;
		public List<VariableModifierContext> mods = new ArrayList<VariableModifierContext>();
		public CatchTypeContext catchType() {
			return getRuleContext(CatchTypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public CatchParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCatchParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchParameterContext catchParameter() throws RecognitionException {
		CatchParameterContext _localctx = new CatchParameterContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_catchParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1170);
					((CatchParameterContext)_localctx).variableModifier = variableModifier();
					((CatchParameterContext)_localctx).mods.add(((CatchParameterContext)_localctx).variableModifier);
					}
					} 
				}
				setState(1175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			}
			setState(1176);
			catchType();
			setState(1177);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CatchTypeContext extends ParserRuleContext {
		public List<ClassOrInterfaceTypeContext> classOrInterfaceType() {
			return getRuleContexts(ClassOrInterfaceTypeContext.class);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType(int i) {
			return getRuleContext(ClassOrInterfaceTypeContext.class,i);
		}
		public List<TerminalNode> BOR() { return getTokens(JavaLanguageParser.BOR); }
		public TerminalNode BOR(int i) {
			return getToken(JavaLanguageParser.BOR, i);
		}
		public CatchTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCatchType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchTypeContext catchType() throws RecognitionException {
		CatchTypeContext _localctx = new CatchTypeContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1179);
			classOrInterfaceType(false);
			setState(1184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BOR) {
				{
				{
				setState(1180);
				match(BOR);
				setState(1181);
				classOrInterfaceType(false);
				}
				}
				setState(1186);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FinallyBlockContext extends ParserRuleContext {
		public TerminalNode LITERAL_FINALLY() { return getToken(JavaLanguageParser.LITERAL_FINALLY, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFinallyBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyBlockContext finallyBlock() throws RecognitionException {
		FinallyBlockContext _localctx = new FinallyBlockContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1187);
			match(LITERAL_FINALLY);
			setState(1188);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResourceSpecificationContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public ResourcesContext resources() {
			return getRuleContext(ResourcesContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public TerminalNode SEMI() { return getToken(JavaLanguageParser.SEMI, 0); }
		public ResourceSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resourceSpecification; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitResourceSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceSpecificationContext resourceSpecification() throws RecognitionException {
		ResourceSpecificationContext _localctx = new ResourceSpecificationContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_resourceSpecification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1190);
			match(LPAREN);
			setState(1191);
			resources();
			setState(1193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(1192);
				match(SEMI);
				}
			}

			setState(1195);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResourcesContext extends ParserRuleContext {
		public List<ResourceContext> resource() {
			return getRuleContexts(ResourceContext.class);
		}
		public ResourceContext resource(int i) {
			return getRuleContext(ResourceContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(JavaLanguageParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(JavaLanguageParser.SEMI, i);
		}
		public ResourcesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resources; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitResources(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourcesContext resources() throws RecognitionException {
		ResourcesContext _localctx = new ResourcesContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_resources);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1197);
			resource();
			setState(1202);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1198);
					match(SEMI);
					setState(1199);
					resource();
					}
					} 
				}
				setState(1204);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResourceContext extends ParserRuleContext {
		public ResourceDeclarationContext resourceDeclaration() {
			return getRuleContext(ResourceDeclarationContext.class,0);
		}
		public VariableAccessContext variableAccess() {
			return getRuleContext(VariableAccessContext.class,0);
		}
		public ResourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resource; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitResource(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceContext resource() throws RecognitionException {
		ResourceContext _localctx = new ResourceContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_resource);
		try {
			setState(1207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,134,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1205);
				resourceDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1206);
				variableAccess();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResourceDeclarationContext extends ParserRuleContext {
		public VariableModifierContext variableModifier;
		public List<VariableModifierContext> mods = new ArrayList<VariableModifierContext>();
		public ClassOrInterfaceTypeContext type;
		public VariableDeclaratorIdContext variableDeclaratorId() {
			return getRuleContext(VariableDeclaratorIdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(JavaLanguageParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public ResourceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resourceDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitResourceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResourceDeclarationContext resourceDeclaration() throws RecognitionException {
		ResourceDeclarationContext _localctx = new ResourceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_resourceDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1212);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,135,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1209);
					((ResourceDeclarationContext)_localctx).variableModifier = variableModifier();
					((ResourceDeclarationContext)_localctx).mods.add(((ResourceDeclarationContext)_localctx).variableModifier);
					}
					} 
				}
				setState(1214);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,135,_ctx);
			}
			setState(1215);
			((ResourceDeclarationContext)_localctx).type = classOrInterfaceType(true);
			setState(1216);
			variableDeclaratorId(_localctx.mods, _localctx.type);
			setState(1217);
			match(ASSIGN);
			setState(1218);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableAccessContext extends ParserRuleContext {
		public FieldAccessNoIdentContext fieldAccessNoIdent;
		public List<FieldAccessNoIdentContext> accessList = new ArrayList<FieldAccessNoIdentContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LITERAL_THIS() { return getToken(JavaLanguageParser.LITERAL_THIS, 0); }
		public List<FieldAccessNoIdentContext> fieldAccessNoIdent() {
			return getRuleContexts(FieldAccessNoIdentContext.class);
		}
		public FieldAccessNoIdentContext fieldAccessNoIdent(int i) {
			return getRuleContext(FieldAccessNoIdentContext.class,i);
		}
		public VariableAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableAccess; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitVariableAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableAccessContext variableAccess() throws RecognitionException {
		VariableAccessContext _localctx = new VariableAccessContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_variableAccess);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1223);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1220);
					((VariableAccessContext)_localctx).fieldAccessNoIdent = fieldAccessNoIdent();
					((VariableAccessContext)_localctx).accessList.add(((VariableAccessContext)_localctx).fieldAccessNoIdent);
					}
					} 
				}
				setState(1225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			}
			setState(1228);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				{
				setState(1226);
				id();
				}
				break;
			case LITERAL_THIS:
				{
				setState(1227);
				match(LITERAL_THIS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldAccessNoIdentContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public FieldAccessNoIdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldAccessNoIdent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFieldAccessNoIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldAccessNoIdentContext fieldAccessNoIdent() throws RecognitionException {
		FieldAccessNoIdentContext _localctx = new FieldAccessNoIdentContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_fieldAccessNoIdent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1230);
			expr(0);
			setState(1231);
			match(DOT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchBlockStatementGroupContext extends ParserRuleContext {
		public BlockStatementContext blockStatement;
		public List<BlockStatementContext> slists = new ArrayList<BlockStatementContext>();
		public List<SwitchLabelContext> switchLabel() {
			return getRuleContexts(SwitchLabelContext.class);
		}
		public SwitchLabelContext switchLabel(int i) {
			return getRuleContext(SwitchLabelContext.class,i);
		}
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public SwitchBlockStatementGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchBlockStatementGroup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchBlockStatementGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchBlockStatementGroupContext switchBlockStatementGroup() throws RecognitionException {
		SwitchBlockStatementGroupContext _localctx = new SwitchBlockStatementGroupContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_switchBlockStatementGroup);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1234); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1233);
					switchLabel();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1236); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,138,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(1239); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1238);
					((SwitchBlockStatementGroupContext)_localctx).blockStatement = blockStatement();
					((SwitchBlockStatementGroupContext)_localctx).slists.add(((SwitchBlockStatementGroupContext)_localctx).blockStatement);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1241); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,139,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchLabelContext extends ParserRuleContext {
		public SwitchLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchLabel; }
	 
		public SwitchLabelContext() { }
		public void copyFrom(SwitchLabelContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefaultLabelContext extends SwitchLabelContext {
		public TerminalNode LITERAL_DEFAULT() { return getToken(JavaLanguageParser.LITERAL_DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public DefaultLabelContext(SwitchLabelContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitDefaultLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CaseLabelContext extends SwitchLabelContext {
		public TerminalNode LITERAL_CASE() { return getToken(JavaLanguageParser.LITERAL_CASE, 0); }
		public CaseConstantsContext caseConstants() {
			return getRuleContext(CaseConstantsContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public CaseLabelContext(SwitchLabelContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCaseLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchLabelContext switchLabel() throws RecognitionException {
		SwitchLabelContext _localctx = new SwitchLabelContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_switchLabel);
		try {
			setState(1252);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LITERAL_CASE:
				_localctx = new CaseLabelContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1243);
				match(LITERAL_CASE);
				setState(1244);
				caseConstants();
				setState(1246);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,140,_ctx) ) {
				case 1:
					{
					setState(1245);
					match(COLON);
					}
					break;
				}
				}
				break;
			case LITERAL_DEFAULT:
				_localctx = new DefaultLabelContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1248);
				match(LITERAL_DEFAULT);
				setState(1250);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,141,_ctx) ) {
				case 1:
					{
					setState(1249);
					match(COLON);
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseConstantsContext extends ParserRuleContext {
		public List<CaseConstantContext> caseConstant() {
			return getRuleContexts(CaseConstantContext.class);
		}
		public CaseConstantContext caseConstant(int i) {
			return getRuleContext(CaseConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public CaseConstantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseConstants; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCaseConstants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseConstantsContext caseConstants() throws RecognitionException {
		CaseConstantsContext _localctx = new CaseConstantsContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_caseConstants);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1254);
			caseConstant();
			setState(1259);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,143,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1255);
					match(COMMA);
					setState(1256);
					caseConstant();
					}
					} 
				}
				setState(1261);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,143,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseConstantContext extends ParserRuleContext {
		public PatternContext pattern() {
			return getRuleContext(PatternContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LITERAL_DEFAULT() { return getToken(JavaLanguageParser.LITERAL_DEFAULT, 0); }
		public CaseConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseConstant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCaseConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseConstantContext caseConstant() throws RecognitionException {
		CaseConstantContext _localctx = new CaseConstantContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_caseConstant);
		try {
			setState(1265);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,144,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1262);
				pattern();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1263);
				expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1264);
				match(LITERAL_DEFAULT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForControlContext extends ParserRuleContext {
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forControl; }
	 
		public ForControlContext() { }
		public void copyFrom(ForControlContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EnhancedForContext extends ForControlContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public EnhancedForControlContext enhancedForControl() {
			return getRuleContext(EnhancedForControlContext.class,0);
		}
		public EnhancedForControlWithRecordPatternContext enhancedForControlWithRecordPattern() {
			return getRuleContext(EnhancedForControlWithRecordPatternContext.class,0);
		}
		public EnhancedForContext(ForControlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnhancedFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForForContext extends ForControlContext {
		public ExpressionContext forCond;
		public ExpressionListContext forUpdate;
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public List<TerminalNode> SEMI() { return getTokens(JavaLanguageParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(JavaLanguageParser.SEMI, i);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForForContext(ForControlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitForFor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_forControl);
		int _la;
		try {
			setState(1287);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,149,_ctx) ) {
			case 1:
				_localctx = new EnhancedForContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1267);
				match(LPAREN);
				setState(1270);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,145,_ctx) ) {
				case 1:
					{
					setState(1268);
					enhancedForControl();
					}
					break;
				case 2:
					{
					setState(1269);
					enhancedForControlWithRecordPattern();
					}
					break;
				}
				setState(1272);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ForForContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1274);
				match(LPAREN);
				setState(1276);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 37156484702796807L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1275);
					forInit();
					}
				}

				setState(1278);
				match(SEMI);
				setState(1280);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1279);
					((ForForContext)_localctx).forCond = expression();
					}
				}

				setState(1282);
				match(SEMI);
				setState(1284);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1283);
					((ForForContext)_localctx).forUpdate = expressionList();
					}
				}

				setState(1286);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForInitContext extends ParserRuleContext {
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_forInit);
		try {
			setState(1291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,150,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1289);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1290);
				expressionList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnhancedForControlContext extends ParserRuleContext {
		public VariableModifierContext variableModifier;
		public List<VariableModifierContext> mods = new ArrayList<VariableModifierContext>();
		public TypeTypeContext type;
		public VariableDeclaratorIdContext variableDeclaratorId() {
			return getRuleContext(VariableDeclaratorIdContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public EnhancedForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhancedForControl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnhancedForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnhancedForControlContext enhancedForControl() throws RecognitionException {
		EnhancedForControlContext _localctx = new EnhancedForControlContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_enhancedForControl);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1296);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1293);
					((EnhancedForControlContext)_localctx).variableModifier = variableModifier();
					((EnhancedForControlContext)_localctx).mods.add(((EnhancedForControlContext)_localctx).variableModifier);
					}
					} 
				}
				setState(1298);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
			}
			setState(1299);
			((EnhancedForControlContext)_localctx).type = typeType(true);
			setState(1300);
			variableDeclaratorId(_localctx.mods, _localctx.type);
			setState(1301);
			match(COLON);
			setState(1302);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnhancedForControlWithRecordPatternContext extends ParserRuleContext {
		public PatternContext pattern() {
			return getRuleContext(PatternContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public EnhancedForControlWithRecordPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhancedForControlWithRecordPattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitEnhancedForControlWithRecordPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnhancedForControlWithRecordPatternContext enhancedForControlWithRecordPattern() throws RecognitionException {
		EnhancedForControlWithRecordPatternContext _localctx = new EnhancedForControlWithRecordPatternContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_enhancedForControlWithRecordPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1304);
			pattern();
			setState(1305);
			match(COLON);
			setState(1306);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ParExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitParExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParExpressionContext parExpression() throws RecognitionException {
		ParExpressionContext _localctx = new ParExpressionContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1308);
			match(LPAREN);
			setState(1309);
			expression();
			setState(1310);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionListContext extends ParserRuleContext {
		public ExpressionContext startExp;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1312);
			((ExpressionListContext)_localctx).startExp = expression();
			setState(1317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1313);
				match(COMMA);
				setState(1314);
				expression();
				}
				}
				setState(1319);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1320);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RefOpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public RefOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRefOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SuperExpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LITERAL_SUPER() { return getToken(JavaLanguageParser.LITERAL_SUPER, 0); }
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public SuperSuffixContext superSuffix() {
			return getRuleContext(SuperSuffixContext.class,0);
		}
		public SuperExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSuperExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstanceOfExpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LITERAL_INSTANCEOF() { return getToken(JavaLanguageParser.LITERAL_INSTANCEOF, 0); }
		public PrimaryPatternContext primaryPattern() {
			return getRuleContext(PrimaryPatternContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public InstanceOfExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInstanceOfExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BitShiftContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> LT() { return getTokens(JavaLanguageParser.LT); }
		public TerminalNode LT(int i) {
			return getToken(JavaLanguageParser.LT, i);
		}
		public List<TerminalNode> GT() { return getTokens(JavaLanguageParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(JavaLanguageParser.GT, i);
		}
		public BitShiftContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBitShift(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewExpContext extends ExprContext {
		public TerminalNode LITERAL_NEW() { return getToken(JavaLanguageParser.LITERAL_NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitNewExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrefixContext extends ExprContext {
		public Token prefix;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(JavaLanguageParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(JavaLanguageParser.MINUS, 0); }
		public TerminalNode INC() { return getToken(JavaLanguageParser.INC, 0); }
		public TerminalNode DEC() { return getToken(JavaLanguageParser.DEC, 0); }
		public TerminalNode BNOT() { return getToken(JavaLanguageParser.BNOT, 0); }
		public TerminalNode LNOT() { return getToken(JavaLanguageParser.LNOT, 0); }
		public PrefixContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPrefix(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CastExpContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TypeCastParametersContext typeCastParameters() {
			return getRuleContext(TypeCastParametersContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CastExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCastExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IndexOpContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(JavaLanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(JavaLanguageParser.RBRACK, 0); }
		public IndexOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitIndexOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InvOpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public InvOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInvOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InitExpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LITERAL_NEW() { return getToken(JavaLanguageParser.LITERAL_NEW, 0); }
		public InnerCreatorContext innerCreator() {
			return getRuleContext(InnerCreatorContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public InitExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInitExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleMethodCallContext extends ExprContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public SimpleMethodCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSimpleMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaExpContext extends ExprContext {
		public LambdaParametersContext lambdaParameters() {
			return getRuleContext(LambdaParametersContext.class,0);
		}
		public TerminalNode LAMBDA() { return getToken(JavaLanguageParser.LAMBDA, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LambdaExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLambdaExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThisExpContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LITERAL_THIS() { return getToken(JavaLanguageParser.LITERAL_THIS, 0); }
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public ThisExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitThisExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryExpContext extends ExprContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PrimaryExpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPrimaryExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PostfixContext extends ExprContext {
		public Token postfix;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode INC() { return getToken(JavaLanguageParser.INC, 0); }
		public TerminalNode DEC() { return getToken(JavaLanguageParser.DEC, 0); }
		public PostfixContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPostfix(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodRefContext extends ExprContext {
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public TerminalNode DOUBLE_COLON() { return getToken(JavaLanguageParser.DOUBLE_COLON, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LITERAL_NEW() { return getToken(JavaLanguageParser.LITERAL_NEW, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MethodRefContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMethodRef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TernaryOpContext extends ExprContext {
		public Token bop;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COLON() { return getToken(JavaLanguageParser.COLON, 0); }
		public TerminalNode QUESTION() { return getToken(JavaLanguageParser.QUESTION, 0); }
		public TernaryOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTernaryOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinOpContext extends ExprContext {
		public Token bop;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode STAR() { return getToken(JavaLanguageParser.STAR, 0); }
		public TerminalNode DIV() { return getToken(JavaLanguageParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(JavaLanguageParser.MOD, 0); }
		public TerminalNode PLUS() { return getToken(JavaLanguageParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(JavaLanguageParser.MINUS, 0); }
		public TerminalNode LE() { return getToken(JavaLanguageParser.LE, 0); }
		public TerminalNode GE() { return getToken(JavaLanguageParser.GE, 0); }
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public TerminalNode EQUAL() { return getToken(JavaLanguageParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(JavaLanguageParser.NOT_EQUAL, 0); }
		public TerminalNode BAND() { return getToken(JavaLanguageParser.BAND, 0); }
		public TerminalNode BXOR() { return getToken(JavaLanguageParser.BXOR, 0); }
		public TerminalNode BOR() { return getToken(JavaLanguageParser.BOR, 0); }
		public TerminalNode LAND() { return getToken(JavaLanguageParser.LAND, 0); }
		public TerminalNode LOR() { return getToken(JavaLanguageParser.LOR, 0); }
		public TerminalNode ASSIGN() { return getToken(JavaLanguageParser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(JavaLanguageParser.PLUS_ASSIGN, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(JavaLanguageParser.MINUS_ASSIGN, 0); }
		public TerminalNode STAR_ASSIGN() { return getToken(JavaLanguageParser.STAR_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(JavaLanguageParser.DIV_ASSIGN, 0); }
		public TerminalNode BAND_ASSIGN() { return getToken(JavaLanguageParser.BAND_ASSIGN, 0); }
		public TerminalNode BOR_ASSIGN() { return getToken(JavaLanguageParser.BOR_ASSIGN, 0); }
		public TerminalNode BXOR_ASSIGN() { return getToken(JavaLanguageParser.BXOR_ASSIGN, 0); }
		public TerminalNode SR_ASSIGN() { return getToken(JavaLanguageParser.SR_ASSIGN, 0); }
		public TerminalNode BSR_ASSIGN() { return getToken(JavaLanguageParser.BSR_ASSIGN, 0); }
		public TerminalNode SL_ASSIGN() { return getToken(JavaLanguageParser.SL_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(JavaLanguageParser.MOD_ASSIGN, 0); }
		public BinOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBinOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodCallContext extends ExprContext {
		public Token bop;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public MethodCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 218;
		enterRecursionRule(_localctx, 218, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1364);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,158,_ctx) ) {
			case 1:
				{
				_localctx = new PrimaryExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1323);
				primary();
				}
				break;
			case 2:
				{
				_localctx = new SimpleMethodCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1324);
				id();
				setState(1325);
				match(LPAREN);
				setState(1327);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1326);
					expressionList();
					}
				}

				setState(1329);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new NewExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1331);
				match(LITERAL_NEW);
				setState(1332);
				creator();
				}
				break;
			case 4:
				{
				_localctx = new PrefixContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1333);
				((PrefixContext)_localctx).prefix = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 51L) != 0)) ) {
					((PrefixContext)_localctx).prefix = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1334);
				expr(20);
				}
				break;
			case 5:
				{
				_localctx = new PrefixContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1335);
				((PrefixContext)_localctx).prefix = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==BNOT || _la==LNOT) ) {
					((PrefixContext)_localctx).prefix = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1336);
				expr(19);
				}
				break;
			case 6:
				{
				_localctx = new MethodRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1337);
				typeType(false);
				setState(1338);
				match(DOUBLE_COLON);
				setState(1340);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1339);
					typeArguments();
					}
				}

				setState(1344);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENT:
				case LITERAL_RECORD:
				case LITERAL_YIELD:
				case LITERAL_NON_SEALED:
				case LITERAL_SEALED:
				case LITERAL_PERMITS:
				case LITERAL_WHEN:
				case LITERAL_UNDERSCORE:
				case LITERAL_MODULE:
					{
					setState(1342);
					id();
					}
					break;
				case LITERAL_NEW:
					{
					setState(1343);
					match(LITERAL_NEW);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 7:
				{
				_localctx = new MethodRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1346);
				classType();
				setState(1347);
				match(DOUBLE_COLON);
				setState(1349);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1348);
					typeArguments();
					}
				}

				setState(1351);
				match(LITERAL_NEW);
				}
				break;
			case 8:
				{
				_localctx = new CastExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1353);
				match(LPAREN);
				setState(1354);
				typeCastParameters();
				setState(1355);
				match(RPAREN);
				setState(1356);
				expr(15);
				}
				break;
			case 9:
				{
				_localctx = new LambdaExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1358);
				lambdaParameters();
				setState(1359);
				match(LAMBDA);
				setState(1362);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LITERAL_VOID:
				case LITERAL_BOOLEAN:
				case LITERAL_BYTE:
				case LITERAL_CHAR:
				case LITERAL_SHORT:
				case LITERAL_INT:
				case LITERAL_FLOAT:
				case LITERAL_LONG:
				case LITERAL_DOUBLE:
				case IDENT:
				case LPAREN:
				case LITERAL_THIS:
				case LITERAL_SUPER:
				case LITERAL_SWITCH:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_TRUE:
				case LITERAL_FALSE:
				case LITERAL_NULL:
				case LITERAL_NEW:
				case CHAR_LITERAL:
				case STRING_LITERAL:
				case AT:
				case FLOAT_LITERAL:
				case DOUBLE_LITERAL:
				case HEX_FLOAT_LITERAL:
				case HEX_DOUBLE_LITERAL:
				case LITERAL_RECORD:
				case TEXT_BLOCK_LITERAL_BEGIN:
				case LITERAL_YIELD:
				case LITERAL_NON_SEALED:
				case LITERAL_SEALED:
				case LITERAL_PERMITS:
				case LITERAL_WHEN:
				case LITERAL_UNDERSCORE:
				case LITERAL_MODULE:
				case DECIMAL_LITERAL_LONG:
				case DECIMAL_LITERAL:
				case HEX_LITERAL_LONG:
				case HEX_LITERAL:
				case OCT_LITERAL_LONG:
				case OCT_LITERAL:
				case BINARY_LITERAL_LONG:
				case BINARY_LITERAL:
					{
					setState(1360);
					expr(0);
					}
					break;
				case LCURLY:
					{
					setState(1361);
					block();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1478);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1476);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,168,_ctx) ) {
					case 1:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1366);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(1367);
						((BinOpContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==STAR || _la==DIV || _la==MOD) ) {
							((BinOpContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1368);
						expr(15);
						}
						break;
					case 2:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1369);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(1370);
						((BinOpContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((BinOpContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1371);
						expr(14);
						}
						break;
					case 3:
						{
						_localctx = new BitShiftContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1372);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(1380);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,159,_ctx) ) {
						case 1:
							{
							setState(1373);
							match(LT);
							setState(1374);
							match(LT);
							}
							break;
						case 2:
							{
							setState(1375);
							match(GT);
							setState(1376);
							match(GT);
							setState(1377);
							match(GT);
							}
							break;
						case 3:
							{
							setState(1378);
							match(GT);
							setState(1379);
							match(GT);
							}
							break;
						}
						setState(1382);
						expr(13);
						}
						break;
					case 4:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1383);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(1384);
						((BinOpContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & 15L) != 0)) ) {
							((BinOpContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1385);
						expr(11);
						}
						break;
					case 5:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1386);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(1387);
						((BinOpContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==NOT_EQUAL || _la==EQUAL) ) {
							((BinOpContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1388);
						expr(10);
						}
						break;
					case 6:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1389);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(1390);
						((BinOpContext)_localctx).bop = match(BAND);
						setState(1391);
						expr(9);
						}
						break;
					case 7:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1392);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(1393);
						((BinOpContext)_localctx).bop = match(BXOR);
						setState(1394);
						expr(8);
						}
						break;
					case 8:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1395);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(1396);
						((BinOpContext)_localctx).bop = match(BOR);
						setState(1397);
						expr(7);
						}
						break;
					case 9:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1398);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(1399);
						((BinOpContext)_localctx).bop = match(LAND);
						setState(1400);
						expr(6);
						}
						break;
					case 10:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1401);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(1402);
						((BinOpContext)_localctx).bop = match(LOR);
						setState(1403);
						expr(5);
						}
						break;
					case 11:
						{
						_localctx = new TernaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1404);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(1405);
						((TernaryOpContext)_localctx).bop = match(QUESTION);
						setState(1406);
						expr(0);
						setState(1407);
						match(COLON);
						setState(1408);
						expr(3);
						}
						break;
					case 12:
						{
						_localctx = new BinOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1410);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1411);
						((BinOpContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & 536608769L) != 0)) ) {
							((BinOpContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1412);
						expr(2);
						}
						break;
					case 13:
						{
						_localctx = new RefOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1413);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(1414);
						((RefOpContext)_localctx).bop = match(DOT);
						setState(1415);
						id();
						}
						break;
					case 14:
						{
						_localctx = new MethodCallContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1416);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(1417);
						((MethodCallContext)_localctx).bop = match(DOT);
						setState(1418);
						id();
						setState(1419);
						match(LPAREN);
						setState(1421);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
							{
							setState(1420);
							expressionList();
							}
						}

						setState(1423);
						match(RPAREN);
						}
						break;
					case 15:
						{
						_localctx = new ThisExpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1425);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(1426);
						((ThisExpContext)_localctx).bop = match(DOT);
						setState(1427);
						match(LITERAL_THIS);
						}
						break;
					case 16:
						{
						_localctx = new InitExpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1428);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(1429);
						((InitExpContext)_localctx).bop = match(DOT);
						setState(1430);
						match(LITERAL_NEW);
						setState(1432);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(1431);
							nonWildcardTypeArguments();
							}
						}

						setState(1434);
						innerCreator();
						}
						break;
					case 17:
						{
						_localctx = new SuperExpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1435);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(1436);
						((SuperExpContext)_localctx).bop = match(DOT);
						setState(1438);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(1437);
							nonWildcardTypeArguments();
							}
						}

						setState(1440);
						match(LITERAL_SUPER);
						setState(1442);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,163,_ctx) ) {
						case 1:
							{
							setState(1441);
							superSuffix();
							}
							break;
						}
						}
						break;
					case 18:
						{
						_localctx = new InvOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1444);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(1445);
						((InvOpContext)_localctx).bop = match(DOT);
						setState(1446);
						nonWildcardTypeArguments();
						setState(1447);
						id();
						setState(1448);
						match(LPAREN);
						setState(1450);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
							{
							setState(1449);
							expressionList();
							}
						}

						setState(1452);
						match(RPAREN);
						}
						break;
					case 19:
						{
						_localctx = new IndexOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1454);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(1455);
						match(LBRACK);
						setState(1456);
						expr(0);
						setState(1457);
						match(RBRACK);
						}
						break;
					case 20:
						{
						_localctx = new PostfixContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1459);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(1460);
						((PostfixContext)_localctx).postfix = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==INC || _la==DEC) ) {
							((PostfixContext)_localctx).postfix = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 21:
						{
						_localctx = new MethodRefContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1461);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(1462);
						match(DOUBLE_COLON);
						setState(1464);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(1463);
							typeArguments();
							}
						}

						setState(1468);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case IDENT:
						case LITERAL_RECORD:
						case LITERAL_YIELD:
						case LITERAL_NON_SEALED:
						case LITERAL_SEALED:
						case LITERAL_PERMITS:
						case LITERAL_WHEN:
						case LITERAL_UNDERSCORE:
						case LITERAL_MODULE:
							{
							setState(1466);
							id();
							}
							break;
						case LITERAL_NEW:
							{
							setState(1467);
							match(LITERAL_NEW);
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						}
						break;
					case 22:
						{
						_localctx = new InstanceOfExpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(1470);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(1471);
						((InstanceOfExpContext)_localctx).bop = match(LITERAL_INSTANCEOF);
						setState(1474);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
						case 1:
							{
							setState(1472);
							primaryPattern();
							}
							break;
						case 2:
							{
							setState(1473);
							typeType(true);
							}
							break;
						}
						}
						break;
					}
					} 
				}
				setState(1480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeCastParametersContext extends ParserRuleContext {
		public List<TypeTypeContext> typeType() {
			return getRuleContexts(TypeTypeContext.class);
		}
		public TypeTypeContext typeType(int i) {
			return getRuleContext(TypeTypeContext.class,i);
		}
		public List<TerminalNode> BAND() { return getTokens(JavaLanguageParser.BAND); }
		public TerminalNode BAND(int i) {
			return getToken(JavaLanguageParser.BAND, i);
		}
		public TypeCastParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeCastParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeCastParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeCastParametersContext typeCastParameters() throws RecognitionException {
		TypeCastParametersContext _localctx = new TypeCastParametersContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_typeCastParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1481);
			typeType(true);
			setState(1486);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BAND) {
				{
				{
				setState(1482);
				match(BAND);
				setState(1483);
				typeType(true);
				}
				}
				setState(1488);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaParametersContext extends ParserRuleContext {
		public LambdaParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaParameters; }
	 
		public LambdaParametersContext() { }
		public void copyFrom(LambdaParametersContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SingleLambdaParamContext extends LambdaParametersContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public SingleLambdaParamContext(LambdaParametersContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSingleLambdaParam(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultiLambdaParamContext extends LambdaParametersContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public MultiLambdaParamsContext multiLambdaParams() {
			return getRuleContext(MultiLambdaParamsContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public MultiLambdaParamContext(LambdaParametersContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMultiLambdaParam(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FormalLambdaParamContext extends LambdaParametersContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FormalLambdaParamContext(LambdaParametersContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitFormalLambdaParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaParametersContext lambdaParameters() throws RecognitionException {
		LambdaParametersContext _localctx = new LambdaParametersContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_lambdaParameters);
		int _la;
		try {
			setState(1499);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,172,_ctx) ) {
			case 1:
				_localctx = new SingleLambdaParamContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1489);
				id();
				}
				break;
			case 2:
				_localctx = new FormalLambdaParamContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1490);
				match(LPAREN);
				setState(1492);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 575898352105816064L) != 0) || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 90093571536846849L) != 0)) {
					{
					setState(1491);
					formalParameterList();
					}
				}

				setState(1494);
				match(RPAREN);
				}
				break;
			case 3:
				_localctx = new MultiLambdaParamContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1495);
				match(LPAREN);
				setState(1496);
				multiLambdaParams();
				setState(1497);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiLambdaParamsContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public MultiLambdaParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiLambdaParams; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitMultiLambdaParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiLambdaParamsContext multiLambdaParams() throws RecognitionException {
		MultiLambdaParamsContext _localctx = new MultiLambdaParamsContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_multiLambdaParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1501);
			id();
			setState(1506);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1502);
				match(COMMA);
				setState(1503);
				id();
				}
				}
				setState(1508);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenPrimaryContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ParenPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitParenPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SwitchPrimaryContext extends PrimaryContext {
		public SwitchExpressionOrStatementContext switchExpressionOrStatement() {
			return getRuleContext(SwitchExpressionOrStatementContext.class,0);
		}
		public SwitchPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSwitchPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimitivePrimaryContext extends PrimaryContext {
		public PrimitiveTypeContext type;
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public TerminalNode LITERAL_CLASS() { return getToken(JavaLanguageParser.LITERAL_CLASS, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public PrimitivePrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPrimitivePrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TokenPrimaryContext extends PrimaryContext {
		public TerminalNode LITERAL_THIS() { return getToken(JavaLanguageParser.LITERAL_THIS, 0); }
		public TerminalNode LITERAL_SUPER() { return getToken(JavaLanguageParser.LITERAL_SUPER, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TokenPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTokenPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassRefPrimaryContext extends PrimaryContext {
		public ClassOrInterfaceTypeContext type;
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public TerminalNode LITERAL_CLASS() { return getToken(JavaLanguageParser.LITERAL_CLASS, 0); }
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public ClassRefPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassRefPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralPrimaryContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitLiteralPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_primary);
		int _la;
		try {
			setState(1538);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,176,_ctx) ) {
			case 1:
				_localctx = new SwitchPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1509);
				switchExpressionOrStatement();
				}
				break;
			case 2:
				_localctx = new ParenPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1510);
				match(LPAREN);
				setState(1511);
				expr(0);
				setState(1512);
				match(RPAREN);
				}
				break;
			case 3:
				_localctx = new TokenPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1514);
				match(LITERAL_THIS);
				}
				break;
			case 4:
				_localctx = new TokenPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1515);
				match(LITERAL_SUPER);
				}
				break;
			case 5:
				_localctx = new LiteralPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1516);
				literal();
				}
				break;
			case 6:
				_localctx = new TokenPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1517);
				id();
				}
				break;
			case 7:
				_localctx = new ClassRefPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1518);
				((ClassRefPrimaryContext)_localctx).type = classOrInterfaceType(false);
				setState(1522);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK || _la==AT) {
					{
					{
					setState(1519);
					arrayDeclarator();
					}
					}
					setState(1524);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1525);
				match(DOT);
				setState(1526);
				match(LITERAL_CLASS);
				}
				break;
			case 8:
				_localctx = new PrimitivePrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1528);
				((PrimitivePrimaryContext)_localctx).type = primitiveType();
				setState(1532);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK || _la==AT) {
					{
					{
					setState(1529);
					arrayDeclarator();
					}
					}
					setState(1534);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1535);
				match(DOT);
				setState(1536);
				match(LITERAL_CLASS);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassTypeContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_classType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1543);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
			case 1:
				{
				setState(1540);
				classOrInterfaceType(false);
				setState(1541);
				match(DOT);
				}
				break;
			}
			setState(1545);
			annotations(false);
			setState(1546);
			id();
			setState(1548);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1547);
				typeArguments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreatorContext extends ParserRuleContext {
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public CreatedNameContext createdName() {
			return getRuleContext(CreatedNameContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public List<AnnotationsContext> annotations() {
			return getRuleContexts(AnnotationsContext.class);
		}
		public AnnotationsContext annotations(int i) {
			return getRuleContext(AnnotationsContext.class,i);
		}
		public ArrayCreatorRestContext arrayCreatorRest() {
			return getRuleContext(ArrayCreatorRestContext.class,0);
		}
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_creator);
		try {
			setState(1562);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				enterOuterAlt(_localctx, 1);
				{
				setState(1550);
				nonWildcardTypeArguments();
				setState(1551);
				createdName();
				setState(1552);
				classCreatorRest();
				}
				break;
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
			case IDENT:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1554);
				annotations(false);
				setState(1555);
				createdName();
				setState(1560);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LBRACK:
				case AT:
					{
					setState(1556);
					annotations(false);
					setState(1557);
					arrayCreatorRest();
					}
					break;
				case LPAREN:
					{
					setState(1559);
					classCreatorRest();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreatedNameContext extends ParserRuleContext {
		public CreatedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createdName; }
	 
		public CreatedNameContext() { }
		public void copyFrom(CreatedNameContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CreatedNamePrimitiveContext extends CreatedNameContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public CreatedNamePrimitiveContext(CreatedNameContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCreatedNamePrimitive(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CreatedNameObjectContext extends CreatedNameContext {
		public CreatedNameExtendedContext createdNameExtended;
		public List<CreatedNameExtendedContext> extended = new ArrayList<CreatedNameExtendedContext>();
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() {
			return getRuleContext(TypeArgumentsOrDiamondContext.class,0);
		}
		public List<CreatedNameExtendedContext> createdNameExtended() {
			return getRuleContexts(CreatedNameExtendedContext.class);
		}
		public CreatedNameExtendedContext createdNameExtended(int i) {
			return getRuleContext(CreatedNameExtendedContext.class,i);
		}
		public CreatedNameObjectContext(CreatedNameContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCreatedNameObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatedNameContext createdName() throws RecognitionException {
		CreatedNameContext _localctx = new CreatedNameContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_createdName);
		int _la;
		try {
			setState(1576);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				_localctx = new CreatedNameObjectContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1564);
				annotations(false);
				setState(1565);
				id();
				setState(1567);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(1566);
					typeArgumentsOrDiamond();
					}
				}

				setState(1572);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(1569);
					((CreatedNameObjectContext)_localctx).createdNameExtended = createdNameExtended();
					((CreatedNameObjectContext)_localctx).extended.add(((CreatedNameObjectContext)_localctx).createdNameExtended);
					}
					}
					setState(1574);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
				_localctx = new CreatedNamePrimitiveContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1575);
				primitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreatedNameExtendedContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() {
			return getRuleContext(TypeArgumentsOrDiamondContext.class,0);
		}
		public CreatedNameExtendedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createdNameExtended; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitCreatedNameExtended(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatedNameExtendedContext createdNameExtended() throws RecognitionException {
		CreatedNameExtendedContext _localctx = new CreatedNameExtendedContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_createdNameExtended);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1578);
			match(DOT);
			setState(1579);
			annotations(false);
			setState(1580);
			id();
			setState(1582);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1581);
				typeArgumentsOrDiamond();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InnerCreatorContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public NonWildcardTypeArgumentsOrDiamondContext nonWildcardTypeArgumentsOrDiamond() {
			return getRuleContext(NonWildcardTypeArgumentsOrDiamondContext.class,0);
		}
		public InnerCreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_innerCreator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInnerCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InnerCreatorContext innerCreator() throws RecognitionException {
		InnerCreatorContext _localctx = new InnerCreatorContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_innerCreator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1584);
			annotations(false);
			setState(1585);
			id();
			setState(1587);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1586);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(1589);
			classCreatorRest();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayCreatorRestContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(JavaLanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(JavaLanguageParser.RBRACK, 0); }
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public List<BracketsWithExpContext> bracketsWithExp() {
			return getRuleContexts(BracketsWithExpContext.class);
		}
		public BracketsWithExpContext bracketsWithExp(int i) {
			return getRuleContext(BracketsWithExpContext.class,i);
		}
		public ArrayCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayCreatorRest; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitArrayCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayCreatorRestContext arrayCreatorRest() throws RecognitionException {
		ArrayCreatorRestContext _localctx = new ArrayCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_arrayCreatorRest);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1591);
			match(LBRACK);
			setState(1614);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(1592);
				match(RBRACK);
				setState(1596);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LBRACK || _la==AT) {
					{
					{
					setState(1593);
					arrayDeclarator();
					}
					}
					setState(1598);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1599);
				arrayInitializer();
				}
				break;
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
			case IDENT:
			case LPAREN:
			case LITERAL_THIS:
			case LITERAL_SUPER:
			case LITERAL_SWITCH:
			case PLUS:
			case MINUS:
			case INC:
			case DEC:
			case BNOT:
			case LNOT:
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case LITERAL_NULL:
			case LITERAL_NEW:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case AT:
			case FLOAT_LITERAL:
			case DOUBLE_LITERAL:
			case HEX_FLOAT_LITERAL:
			case HEX_DOUBLE_LITERAL:
			case LITERAL_RECORD:
			case TEXT_BLOCK_LITERAL_BEGIN:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
			case DECIMAL_LITERAL_LONG:
			case DECIMAL_LITERAL:
			case HEX_LITERAL_LONG:
			case HEX_LITERAL:
			case OCT_LITERAL_LONG:
			case OCT_LITERAL:
			case BINARY_LITERAL_LONG:
			case BINARY_LITERAL:
				{
				setState(1600);
				expression();
				setState(1601);
				match(RBRACK);
				setState(1605);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,187,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1602);
						bracketsWithExp();
						}
						} 
					}
					setState(1607);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,187,_ctx);
				}
				setState(1611);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,188,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1608);
						arrayDeclarator();
						}
						} 
					}
					setState(1613);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,188,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BracketsWithExpContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(JavaLanguageParser.LBRACK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(JavaLanguageParser.RBRACK, 0); }
		public BracketsWithExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketsWithExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitBracketsWithExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketsWithExpContext bracketsWithExp() throws RecognitionException {
		BracketsWithExpContext _localctx = new BracketsWithExpContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_bracketsWithExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1616);
			annotations(false);
			setState(1617);
			match(LBRACK);
			setState(1618);
			expression();
			setState(1619);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassCreatorRestContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ClassCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classCreatorRest; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassCreatorRestContext classCreatorRest() throws RecognitionException {
		ClassCreatorRestContext _localctx = new ClassCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_classCreatorRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1621);
			arguments();
			setState(1623);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,190,_ctx) ) {
			case 1:
				{
				setState(1622);
				classBody();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentsOrDiamondContext extends ParserRuleContext {
		public TypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentsOrDiamond; }
	 
		public TypeArgumentsOrDiamondContext() { }
		public void copyFrom(TypeArgumentsOrDiamondContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgsContext extends TypeArgumentsOrDiamondContext {
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TypeArgsContext(TypeArgumentsOrDiamondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeArgs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DiamondContext extends TypeArgumentsOrDiamondContext {
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public DiamondContext(TypeArgumentsOrDiamondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitDiamond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() throws RecognitionException {
		TypeArgumentsOrDiamondContext _localctx = new TypeArgumentsOrDiamondContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_typeArgumentsOrDiamond);
		try {
			setState(1628);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,191,_ctx) ) {
			case 1:
				_localctx = new DiamondContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1625);
				match(LT);
				setState(1626);
				match(GT);
				}
				break;
			case 2:
				_localctx = new TypeArgsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1627);
				typeArguments();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NonWildcardTypeArgumentsOrDiamondContext extends ParserRuleContext {
		public NonWildcardTypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonWildcardTypeArgumentsOrDiamond; }
	 
		public NonWildcardTypeArgumentsOrDiamondContext() { }
		public void copyFrom(NonWildcardTypeArgumentsOrDiamondContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NonWildcardDiamondContext extends NonWildcardTypeArgumentsOrDiamondContext {
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public NonWildcardDiamondContext(NonWildcardTypeArgumentsOrDiamondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitNonWildcardDiamond(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NonWildcardTypeArgsContext extends NonWildcardTypeArgumentsOrDiamondContext {
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public NonWildcardTypeArgsContext(NonWildcardTypeArgumentsOrDiamondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitNonWildcardTypeArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonWildcardTypeArgumentsOrDiamondContext nonWildcardTypeArgumentsOrDiamond() throws RecognitionException {
		NonWildcardTypeArgumentsOrDiamondContext _localctx = new NonWildcardTypeArgumentsOrDiamondContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1633);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,192,_ctx) ) {
			case 1:
				_localctx = new NonWildcardDiamondContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1630);
				match(LT);
				setState(1631);
				match(GT);
				}
				break;
			case 2:
				_localctx = new NonWildcardTypeArgsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1632);
				nonWildcardTypeArguments();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NonWildcardTypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public TypeArgumentsTypeListContext typeArgumentsTypeList() {
			return getRuleContext(TypeArgumentsTypeListContext.class,0);
		}
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public NonWildcardTypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonWildcardTypeArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitNonWildcardTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonWildcardTypeArgumentsContext nonWildcardTypeArguments() throws RecognitionException {
		NonWildcardTypeArgumentsContext _localctx = new NonWildcardTypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1635);
			match(LT);
			setState(1636);
			typeArgumentsTypeList();
			setState(1637);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentsTypeListContext extends ParserRuleContext {
		public List<TypeTypeContext> typeType() {
			return getRuleContexts(TypeTypeContext.class);
		}
		public TypeTypeContext typeType(int i) {
			return getRuleContext(TypeTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public TypeArgumentsTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentsTypeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeArgumentsTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsTypeListContext typeArgumentsTypeList() throws RecognitionException {
		TypeArgumentsTypeListContext _localctx = new TypeArgumentsTypeListContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_typeArgumentsTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1639);
			typeType(false);
			setState(1644);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1640);
				match(COMMA);
				setState(1641);
				typeType(false);
				}
				}
				setState(1646);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeListContext extends ParserRuleContext {
		public List<TypeTypeContext> typeType() {
			return getRuleContexts(TypeTypeContext.class);
		}
		public TypeTypeContext typeType(int i) {
			return getRuleContext(TypeTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1647);
			typeType(false);
			setState(1652);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1648);
				match(COMMA);
				setState(1649);
				typeType(false);
				}
				}
				setState(1654);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeTypeContext extends ParserRuleContext {
		public boolean createImaginaryNode;
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ClassOrInterfaceOrPrimitiveTypeContext classOrInterfaceOrPrimitiveType() {
			return getRuleContext(ClassOrInterfaceOrPrimitiveTypeContext.class,0);
		}
		public List<ArrayDeclaratorContext> arrayDeclarator() {
			return getRuleContexts(ArrayDeclaratorContext.class);
		}
		public ArrayDeclaratorContext arrayDeclarator(int i) {
			return getRuleContext(ArrayDeclaratorContext.class,i);
		}
		public TypeTypeContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TypeTypeContext(ParserRuleContext parent, int invokingState, boolean createImaginaryNode) {
			super(parent, invokingState);
			this.createImaginaryNode = createImaginaryNode;
		}
		@Override public int getRuleIndex() { return RULE_typeType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeTypeContext typeType(boolean createImaginaryNode) throws RecognitionException {
		TypeTypeContext _localctx = new TypeTypeContext(_ctx, getState(), createImaginaryNode);
		enterRule(_localctx, 254, RULE_typeType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1655);
			annotations(false);
			setState(1656);
			classOrInterfaceOrPrimitiveType();
			setState(1660);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,195,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1657);
					arrayDeclarator();
					}
					} 
				}
				setState(1662);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,195,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassOrInterfaceOrPrimitiveTypeContext extends ParserRuleContext {
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassOrInterfaceOrPrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceOrPrimitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitClassOrInterfaceOrPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrInterfaceOrPrimitiveTypeContext classOrInterfaceOrPrimitiveType() throws RecognitionException {
		ClassOrInterfaceOrPrimitiveTypeContext _localctx = new ClassOrInterfaceOrPrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_classOrInterfaceOrPrimitiveType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1665);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
			case AT:
			case LITERAL_RECORD:
			case LITERAL_YIELD:
			case LITERAL_NON_SEALED:
			case LITERAL_SEALED:
			case LITERAL_PERMITS:
			case LITERAL_WHEN:
			case LITERAL_UNDERSCORE:
			case LITERAL_MODULE:
				{
				setState(1663);
				classOrInterfaceType(false);
				}
				break;
			case LITERAL_VOID:
			case LITERAL_BOOLEAN:
			case LITERAL_BYTE:
			case LITERAL_CHAR:
			case LITERAL_SHORT:
			case LITERAL_INT:
			case LITERAL_FLOAT:
			case LITERAL_LONG:
			case LITERAL_DOUBLE:
				{
				setState(1664);
				primitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayDeclaratorContext extends ParserRuleContext {
		public AnnotationsContext anno;
		public TerminalNode LBRACK() { return getToken(JavaLanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(JavaLanguageParser.RBRACK, 0); }
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ArrayDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclarator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitArrayDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayDeclaratorContext arrayDeclarator() throws RecognitionException {
		ArrayDeclaratorContext _localctx = new ArrayDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 258, RULE_arrayDeclarator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1667);
			((ArrayDeclaratorContext)_localctx).anno = annotations(false);
			setState(1668);
			match(LBRACK);
			setState(1669);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode LITERAL_BOOLEAN() { return getToken(JavaLanguageParser.LITERAL_BOOLEAN, 0); }
		public TerminalNode LITERAL_CHAR() { return getToken(JavaLanguageParser.LITERAL_CHAR, 0); }
		public TerminalNode LITERAL_BYTE() { return getToken(JavaLanguageParser.LITERAL_BYTE, 0); }
		public TerminalNode LITERAL_SHORT() { return getToken(JavaLanguageParser.LITERAL_SHORT, 0); }
		public TerminalNode LITERAL_INT() { return getToken(JavaLanguageParser.LITERAL_INT, 0); }
		public TerminalNode LITERAL_LONG() { return getToken(JavaLanguageParser.LITERAL_LONG, 0); }
		public TerminalNode LITERAL_FLOAT() { return getToken(JavaLanguageParser.LITERAL_FLOAT, 0); }
		public TerminalNode LITERAL_DOUBLE() { return getToken(JavaLanguageParser.LITERAL_DOUBLE, 0); }
		public TerminalNode LITERAL_VOID() { return getToken(JavaLanguageParser.LITERAL_VOID, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 260, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1671);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 287667426198290432L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JavaLanguageParser.LT, 0); }
		public List<TypeArgumentContext> typeArgument() {
			return getRuleContexts(TypeArgumentContext.class);
		}
		public TypeArgumentContext typeArgument(int i) {
			return getRuleContext(TypeArgumentContext.class,i);
		}
		public TerminalNode GT() { return getToken(JavaLanguageParser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1673);
			match(LT);
			setState(1674);
			typeArgument();
			setState(1679);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1675);
				match(COMMA);
				setState(1676);
				typeArgument();
				}
				}
				setState(1681);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1682);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuperSuffixContext extends ParserRuleContext {
		public SuperSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_superSuffix; }
	 
		public SuperSuffixContext() { }
		public void copyFrom(SuperSuffixContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SuperSuffixDotContext extends SuperSuffixContext {
		public TerminalNode DOT() { return getToken(JavaLanguageParser.DOT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public SuperSuffixDotContext(SuperSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSuperSuffixDot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SuperSuffixSimpleContext extends SuperSuffixContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public SuperSuffixSimpleContext(SuperSuffixContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitSuperSuffixSimple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuperSuffixContext superSuffix() throws RecognitionException {
		SuperSuffixContext _localctx = new SuperSuffixContext(_ctx, getState());
		enterRule(_localctx, 264, RULE_superSuffix);
		int _la;
		try {
			setState(1698);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				_localctx = new SuperSuffixSimpleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1684);
				match(LPAREN);
				setState(1686);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
					{
					setState(1685);
					expressionList();
					}
				}

				setState(1688);
				match(RPAREN);
				}
				break;
			case DOT:
				_localctx = new SuperSuffixDotContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1689);
				match(DOT);
				setState(1690);
				id();
				setState(1696);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
				case 1:
					{
					setState(1691);
					match(LPAREN);
					setState(1693);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
						{
						setState(1692);
						expressionList();
						}
					}

					setState(1695);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1700);
			match(LPAREN);
			setState(1702);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 49)) & ~0x3f) == 0 && ((1L << (_la - 49)) & 1101256459263L) != 0) || ((((_la - 125)) & ~0x3f) == 0 && ((1L << (_la - 125)) & 35184372117491L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 17544946553103L) != 0)) {
				{
				setState(1701);
				expressionList();
				}
			}

			setState(1704);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternContext extends ParserRuleContext {
		public InnerPatternContext innerPattern() {
			return getRuleContext(InnerPatternContext.class,0);
		}
		public PatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternContext pattern() throws RecognitionException {
		PatternContext _localctx = new PatternContext(_ctx, getState());
		enterRule(_localctx, 268, RULE_pattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1706);
			innerPattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InnerPatternContext extends ParserRuleContext {
		public GuardedPatternContext guardedPattern() {
			return getRuleContext(GuardedPatternContext.class,0);
		}
		public RecordPatternContext recordPattern() {
			return getRuleContext(RecordPatternContext.class,0);
		}
		public PrimaryPatternContext primaryPattern() {
			return getRuleContext(PrimaryPatternContext.class,0);
		}
		public InnerPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_innerPattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitInnerPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InnerPatternContext innerPattern() throws RecognitionException {
		InnerPatternContext _localctx = new InnerPatternContext(_ctx, getState());
		enterRule(_localctx, 270, RULE_innerPattern);
		try {
			setState(1711);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,203,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1708);
				guardedPattern();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1709);
				recordPattern();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1710);
				primaryPattern();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GuardedPatternContext extends ParserRuleContext {
		public PrimaryPatternContext primaryPattern() {
			return getRuleContext(PrimaryPatternContext.class,0);
		}
		public GuardContext guard() {
			return getRuleContext(GuardContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public GuardedPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guardedPattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitGuardedPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GuardedPatternContext guardedPattern() throws RecognitionException {
		GuardedPatternContext _localctx = new GuardedPatternContext(_ctx, getState());
		enterRule(_localctx, 272, RULE_guardedPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1713);
			primaryPattern();
			setState(1714);
			guard();
			setState(1715);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GuardContext extends ParserRuleContext {
		public TerminalNode LITERAL_WHEN() { return getToken(JavaLanguageParser.LITERAL_WHEN, 0); }
		public GuardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guard; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitGuard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GuardContext guard() throws RecognitionException {
		GuardContext _localctx = new GuardContext(_ctx, getState());
		enterRule(_localctx, 274, RULE_guard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1717);
			match(LITERAL_WHEN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryPatternContext extends ParserRuleContext {
		public PrimaryPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryPattern; }
	 
		public PrimaryPatternContext() { }
		public void copyFrom(PrimaryPatternContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PatternVariableDefContext extends PrimaryPatternContext {
		public TypePatternContext typePattern() {
			return getRuleContext(TypePatternContext.class,0);
		}
		public PatternVariableDefContext(PrimaryPatternContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPatternVariableDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RecordPatternDefContext extends PrimaryPatternContext {
		public RecordPatternContext recordPattern() {
			return getRuleContext(RecordPatternContext.class,0);
		}
		public RecordPatternDefContext(PrimaryPatternContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordPatternDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryPatternContext primaryPattern() throws RecognitionException {
		PrimaryPatternContext _localctx = new PrimaryPatternContext(_ctx, getState());
		enterRule(_localctx, 276, RULE_primaryPattern);
		try {
			setState(1721);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,204,_ctx) ) {
			case 1:
				_localctx = new PatternVariableDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1719);
				typePattern();
				}
				break;
			case 2:
				_localctx = new RecordPatternDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1720);
				recordPattern();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypePatternContext extends ParserRuleContext {
		public TypePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typePattern; }
	 
		public TypePatternContext() { }
		public void copyFrom(TypePatternContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypePatternDefContext extends TypePatternContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public TypeTypeContext type;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public TypePatternDefContext(TypePatternContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitTypePatternDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnnamedPatternDefContext extends TypePatternContext {
		public TerminalNode LITERAL_UNDERSCORE() { return getToken(JavaLanguageParser.LITERAL_UNDERSCORE, 0); }
		public UnnamedPatternDefContext(TypePatternContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitUnnamedPatternDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypePatternContext typePattern() throws RecognitionException {
		TypePatternContext _localctx = new TypePatternContext(_ctx, getState());
		enterRule(_localctx, 278, RULE_typePattern);
		try {
			int _alt;
			setState(1733);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,206,_ctx) ) {
			case 1:
				_localctx = new TypePatternDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1726);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,205,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1723);
						((TypePatternDefContext)_localctx).modifier = modifier();
						((TypePatternDefContext)_localctx).mods.add(((TypePatternDefContext)_localctx).modifier);
						}
						} 
					}
					setState(1728);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,205,_ctx);
				}
				setState(1729);
				((TypePatternDefContext)_localctx).type = typeType(true);
				setState(1730);
				id();
				}
				break;
			case 2:
				_localctx = new UnnamedPatternDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1732);
				match(LITERAL_UNDERSCORE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordPatternContext extends ParserRuleContext {
		public ModifierContext modifier;
		public List<ModifierContext> mods = new ArrayList<ModifierContext>();
		public TypeTypeContext type;
		public TerminalNode LPAREN() { return getToken(JavaLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavaLanguageParser.RPAREN, 0); }
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public RecordComponentPatternListContext recordComponentPatternList() {
			return getRuleContext(RecordComponentPatternListContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public RecordPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordPattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordPatternContext recordPattern() throws RecognitionException {
		RecordPatternContext _localctx = new RecordPatternContext(_ctx, getState());
		enterRule(_localctx, 280, RULE_recordPattern);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1738);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,207,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1735);
					((RecordPatternContext)_localctx).modifier = modifier();
					((RecordPatternContext)_localctx).mods.add(((RecordPatternContext)_localctx).modifier);
					}
					} 
				}
				setState(1740);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,207,_ctx);
			}
			setState(1741);
			((RecordPatternContext)_localctx).type = typeType(true);
			setState(1742);
			match(LPAREN);
			setState(1744);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 39)) & ~0x3f) == 0 && ((1L << (_la - 39)) & 36028798089559047L) != 0) || ((((_la - 170)) & ~0x3f) == 0 && ((1L << (_la - 170)) & 90093571536846849L) != 0)) {
				{
				setState(1743);
				recordComponentPatternList();
				}
			}

			setState(1746);
			match(RPAREN);
			setState(1748);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
			case 1:
				{
				setState(1747);
				id();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RecordComponentPatternListContext extends ParserRuleContext {
		public List<InnerPatternContext> innerPattern() {
			return getRuleContexts(InnerPatternContext.class);
		}
		public InnerPatternContext innerPattern(int i) {
			return getRuleContext(InnerPatternContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public RecordComponentPatternListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordComponentPatternList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitRecordComponentPatternList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecordComponentPatternListContext recordComponentPatternList() throws RecognitionException {
		RecordComponentPatternListContext _localctx = new RecordComponentPatternListContext(_ctx, getState());
		enterRule(_localctx, 282, RULE_recordComponentPatternList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1750);
			innerPattern();
			setState(1755);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1751);
				match(COMMA);
				setState(1752);
				innerPattern();
				}
				}
				setState(1757);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PermittedSubclassesAndInterfacesContext extends ParserRuleContext {
		public TerminalNode LITERAL_PERMITS() { return getToken(JavaLanguageParser.LITERAL_PERMITS, 0); }
		public List<ClassOrInterfaceTypeContext> classOrInterfaceType() {
			return getRuleContexts(ClassOrInterfaceTypeContext.class);
		}
		public ClassOrInterfaceTypeContext classOrInterfaceType(int i) {
			return getRuleContext(ClassOrInterfaceTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavaLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavaLanguageParser.COMMA, i);
		}
		public PermittedSubclassesAndInterfacesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_permittedSubclassesAndInterfaces; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitPermittedSubclassesAndInterfaces(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PermittedSubclassesAndInterfacesContext permittedSubclassesAndInterfaces() throws RecognitionException {
		PermittedSubclassesAndInterfacesContext _localctx = new PermittedSubclassesAndInterfacesContext(_ctx, getState());
		enterRule(_localctx, 284, RULE_permittedSubclassesAndInterfaces);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1758);
			match(LITERAL_PERMITS);
			setState(1759);
			classOrInterfaceType(false);
			setState(1764);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1760);
				match(COMMA);
				setState(1761);
				classOrInterfaceType(false);
				}
				}
				setState(1766);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ParserRuleContext {
		public TerminalNode LITERAL_UNDERSCORE() { return getToken(JavaLanguageParser.LITERAL_UNDERSCORE, 0); }
		public TerminalNode LITERAL_RECORD() { return getToken(JavaLanguageParser.LITERAL_RECORD, 0); }
		public TerminalNode LITERAL_YIELD() { return getToken(JavaLanguageParser.LITERAL_YIELD, 0); }
		public TerminalNode LITERAL_NON_SEALED() { return getToken(JavaLanguageParser.LITERAL_NON_SEALED, 0); }
		public TerminalNode LITERAL_SEALED() { return getToken(JavaLanguageParser.LITERAL_SEALED, 0); }
		public TerminalNode LITERAL_PERMITS() { return getToken(JavaLanguageParser.LITERAL_PERMITS, 0); }
		public TerminalNode IDENT() { return getToken(JavaLanguageParser.IDENT, 0); }
		public TerminalNode LITERAL_WHEN() { return getToken(JavaLanguageParser.LITERAL_WHEN, 0); }
		public TerminalNode LITERAL_MODULE() { return getToken(JavaLanguageParser.LITERAL_MODULE, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaLanguageParserVisitor ) return ((JavaLanguageParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 286, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1767);
			_la = _input.LA(1);
			if ( !(_la==IDENT || ((((_la - 200)) & ~0x3f) == 0 && ((1L << (_la - 200)) & 83906177L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 77:
			return blockStatement_sempred((BlockStatementContext)_localctx, predIndex);
		case 109:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean blockStatement_sempred(BlockStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return !isYieldStatement();
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 14);
		case 2:
			return precpred(_ctx, 13);
		case 3:
			return precpred(_ctx, 12);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 5);
		case 10:
			return precpred(_ctx, 4);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 30);
		case 14:
			return precpred(_ctx, 29);
		case 15:
			return precpred(_ctx, 28);
		case 16:
			return precpred(_ctx, 27);
		case 17:
			return precpred(_ctx, 26);
		case 18:
			return precpred(_ctx, 25);
		case 19:
			return precpred(_ctx, 24);
		case 20:
			return precpred(_ctx, 21);
		case 21:
			return precpred(_ctx, 18);
		case 22:
			return precpred(_ctx, 11);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u00eb\u06ea\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007"+
		"\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007"+
		"\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007"+
		"\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007"+
		"\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007"+
		"\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007"+
		",\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u0007"+
		"1\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u0007"+
		"6\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007"+
		";\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007"+
		"@\u0002A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0002E\u0007"+
		"E\u0002F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007I\u0002J\u0007"+
		"J\u0002K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007N\u0002O\u0007"+
		"O\u0002P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007S\u0002T\u0007"+
		"T\u0002U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007X\u0002Y\u0007"+
		"Y\u0002Z\u0007Z\u0002[\u0007[\u0002\\\u0007\\\u0002]\u0007]\u0002^\u0007"+
		"^\u0002_\u0007_\u0002`\u0007`\u0002a\u0007a\u0002b\u0007b\u0002c\u0007"+
		"c\u0002d\u0007d\u0002e\u0007e\u0002f\u0007f\u0002g\u0007g\u0002h\u0007"+
		"h\u0002i\u0007i\u0002j\u0007j\u0002k\u0007k\u0002l\u0007l\u0002m\u0007"+
		"m\u0002n\u0007n\u0002o\u0007o\u0002p\u0007p\u0002q\u0007q\u0002r\u0007"+
		"r\u0002s\u0007s\u0002t\u0007t\u0002u\u0007u\u0002v\u0007v\u0002w\u0007"+
		"w\u0002x\u0007x\u0002y\u0007y\u0002z\u0007z\u0002{\u0007{\u0002|\u0007"+
		"|\u0002}\u0007}\u0002~\u0007~\u0002\u007f\u0007\u007f\u0002\u0080\u0007"+
		"\u0080\u0002\u0081\u0007\u0081\u0002\u0082\u0007\u0082\u0002\u0083\u0007"+
		"\u0083\u0002\u0084\u0007\u0084\u0002\u0085\u0007\u0085\u0002\u0086\u0007"+
		"\u0086\u0002\u0087\u0007\u0087\u0002\u0088\u0007\u0088\u0002\u0089\u0007"+
		"\u0089\u0002\u008a\u0007\u008a\u0002\u008b\u0007\u008b\u0002\u008c\u0007"+
		"\u008c\u0002\u008d\u0007\u008d\u0002\u008e\u0007\u008e\u0002\u008f\u0007"+
		"\u008f\u0001\u0000\u0003\u0000\u0122\b\u0000\u0001\u0000\u0005\u0000\u0125"+
		"\b\u0000\n\u0000\f\u0000\u0128\t\u0000\u0001\u0000\u0005\u0000\u012b\b"+
		"\u0000\n\u0000\f\u0000\u012e\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0003\u0002\u0139\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"\u013e\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002\u0148\b\u0002\u0001\u0003"+
		"\u0005\u0003\u014b\b\u0003\n\u0003\f\u0003\u014e\t\u0003\u0001\u0003\u0001"+
		"\u0003\u0004\u0003\u0152\b\u0003\u000b\u0003\f\u0003\u0153\u0003\u0003"+
		"\u0156\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004\u015d\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u016e\b\u0005\u0001\u0006\u0001\u0006\u0003\u0006\u0172\b\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0177\b\u0007\u0001\u0007\u0003"+
		"\u0007\u017a\b\u0007\u0001\u0007\u0003\u0007\u017d\b\u0007\u0001\u0007"+
		"\u0003\u0007\u0180\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u0187\b\b\u0001\b\u0001\b\u0003\b\u018b\b\b\u0001\b\u0001\b"+
		"\u0001\t\u0001\t\u0003\t\u0191\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0005\n\u0198\b\n\n\n\f\n\u019b\t\n\u0001\n\u0001\n\u0003\n\u019f\b"+
		"\n\u0001\n\u0003\n\u01a2\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0005\r"+
		"\u01af\b\r\n\r\f\r\u01b2\t\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0003"+
		"\u000e\u01b8\b\u000e\u0001\u000f\u0005\u000f\u01bb\b\u000f\n\u000f\f\u000f"+
		"\u01be\t\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0005\u0012\u01cd\b\u0012\n\u0012\f\u0012\u01d0"+
		"\t\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u01d7\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u01e0\b\u0015\n\u0015\f\u0015"+
		"\u01e3\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u01e8\b"+
		"\u0016\n\u0016\f\u0016\u01eb\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0003\u0017\u01f0\b\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u01f6\b\u0018\u0001\u0018\u0003\u0018\u01f9\b\u0018\u0001"+
		"\u0018\u0003\u0018\u01fc\b\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0005\u0019\u0203\b\u0019\n\u0019\f\u0019\u0206\t\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u020b\b\u001a\u0001\u001a"+
		"\u0003\u001a\u020e\b\u001a\u0001\u001b\u0001\u001b\u0005\u001b\u0212\b"+
		"\u001b\n\u001b\f\u001b\u0215\t\u001b\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0003\u001c\u021a\b\u001c\u0001\u001c\u0003\u001c\u021d\b\u001c\u0001"+
		"\u001c\u0003\u001c\u0220\b\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0005\u001e\u0229\b\u001e\n"+
		"\u001e\f\u001e\u022c\t\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001"+
		"\u001f\u0005\u001f\u0232\b\u001f\n\u001f\f\u001f\u0235\t\u001f\u0001\u001f"+
		"\u0001\u001f\u0001 \u0001 \u0003 \u023b\b \u0001 \u0001 \u0005 \u023f"+
		"\b \n \f \u0242\t \u0001 \u0003 \u0245\b \u0001!\u0001!\u0001!\u0001!"+
		"\u0001!\u0001!\u0001!\u0001!\u0003!\u024f\b!\u0001\"\u0003\"\u0252\b\""+
		"\u0001\"\u0001\"\u0001\"\u0001\"\u0005\"\u0258\b\"\n\"\f\"\u025b\t\"\u0001"+
		"\"\u0003\"\u025e\b\"\u0001\"\u0001\"\u0001#\u0001#\u0003#\u0264\b#\u0001"+
		"$\u0001$\u0001$\u0001%\u0003%\u026a\b%\u0001%\u0001%\u0001%\u0003%\u026f"+
		"\b%\u0001%\u0001%\u0001&\u0001&\u0001&\u0001&\u0001\'\u0005\'\u0278\b"+
		"\'\n\'\f\'\u027b\t\'\u0001\'\u0001\'\u0003\'\u027f\b\'\u0001(\u0001(\u0001"+
		"(\u0001(\u0001(\u0001(\u0001(\u0003(\u0288\b(\u0001)\u0003)\u028b\b)\u0001"+
		")\u0001)\u0001)\u0001)\u0005)\u0291\b)\n)\f)\u0294\t)\u0001)\u0003)\u0297"+
		"\b)\u0001)\u0001)\u0001*\u0001*\u0001*\u0005*\u029e\b*\n*\f*\u02a1\t*"+
		"\u0001+\u0001+\u0005+\u02a5\b+\n+\f+\u02a8\t+\u0001+\u0001+\u0003+\u02ac"+
		"\b+\u0001,\u0001,\u0001,\u0001,\u0003,\u02b2\b,\u0003,\u02b4\b,\u0001"+
		",\u0005,\u02b7\b,\n,\f,\u02ba\t,\u0001-\u0001-\u0003-\u02be\b-\u0001."+
		"\u0001.\u0001.\u0001.\u0005.\u02c4\b.\n.\f.\u02c7\t.\u0003.\u02c9\b.\u0001"+
		".\u0003.\u02cc\b.\u0001.\u0001.\u0001/\u0001/\u0001/\u0003/\u02d3\b/\u0001"+
		"/\u0005/\u02d6\b/\n/\f/\u02d9\t/\u00010\u00010\u00010\u00010\u00030\u02df"+
		"\b0\u00011\u00011\u00011\u00011\u00011\u00031\u02e6\b1\u00011\u00031\u02e9"+
		"\b1\u00031\u02eb\b1\u00012\u00012\u00012\u00012\u00012\u00012\u00052\u02f3"+
		"\b2\n2\f2\u02f6\t2\u00013\u00013\u00033\u02fa\b3\u00013\u00013\u00014"+
		"\u00014\u00014\u00054\u0301\b4\n4\f4\u0304\t4\u00014\u00014\u00034\u0308"+
		"\b4\u00014\u00034\u030b\b4\u00015\u00055\u030e\b5\n5\f5\u0311\t5\u0001"+
		"5\u00015\u00015\u00016\u00056\u0317\b6\n6\f6\u031a\t6\u00016\u00016\u0001"+
		"6\u00016\u00016\u00017\u00017\u00057\u0323\b7\n7\f7\u0326\t7\u00018\u0001"+
		"8\u00018\u00018\u00019\u00019\u00019\u00019\u00019\u00019\u00019\u0001"+
		"9\u00039\u0334\b9\u0001:\u0001:\u0001;\u0001;\u0001<\u0001<\u0001<\u0001"+
		"<\u0001=\u0005=\u033f\b=\n=\f=\u0342\t=\u0001>\u0001>\u0001>\u0001>\u0001"+
		">\u0003>\u0349\b>\u0001>\u0003>\u034c\b>\u0001?\u0001?\u0001?\u0005?\u0351"+
		"\b?\n?\f?\u0354\t?\u0001@\u0001@\u0001@\u0001@\u0001A\u0001A\u0001A\u0003"+
		"A\u035d\bA\u0001B\u0001B\u0001B\u0001B\u0005B\u0363\bB\nB\fB\u0366\tB"+
		"\u0003B\u0368\bB\u0001B\u0003B\u036b\bB\u0001B\u0001B\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001D\u0001D\u0005D\u0376\bD\nD\fD\u0379\tD\u0001D\u0001"+
		"D\u0001E\u0005E\u037e\bE\nE\fE\u0381\tE\u0001E\u0001E\u0003E\u0385\bE"+
		"\u0001F\u0001F\u0001F\u0003F\u038a\bF\u0001F\u0001F\u0001F\u0001F\u0001"+
		"F\u0001F\u0001F\u0003F\u0393\bF\u0001F\u0001F\u0003F\u0397\bF\u0001F\u0001"+
		"F\u0003F\u039b\bF\u0001F\u0001F\u0003F\u039f\bF\u0001F\u0001F\u0003F\u03a3"+
		"\bF\u0003F\u03a5\bF\u0001G\u0001G\u0001G\u0001G\u0005G\u03ab\bG\nG\fG"+
		"\u03ae\tG\u0001G\u0003G\u03b1\bG\u0001H\u0001H\u0001I\u0001I\u0001I\u0001"+
		"J\u0001J\u0003J\u03ba\bJ\u0001J\u0005J\u03bd\bJ\nJ\fJ\u03c0\tJ\u0001J"+
		"\u0001J\u0001K\u0003K\u03c5\bK\u0001K\u0001K\u0001K\u0001K\u0001K\u0001"+
		"K\u0001K\u0003K\u03ce\bK\u0001K\u0001K\u0001K\u0001K\u0003K\u03d4\bK\u0001"+
		"L\u0001L\u0005L\u03d8\bL\nL\fL\u03db\tL\u0001L\u0001L\u0001M\u0001M\u0001"+
		"M\u0001M\u0001M\u0001M\u0003M\u03e5\bM\u0001N\u0005N\u03e8\bN\nN\fN\u03eb"+
		"\tN\u0001N\u0001N\u0001N\u0001O\u0005O\u03f1\bO\nO\fO\u03f4\tO\u0001O"+
		"\u0001O\u0001O\u0001O\u0003O\u03fa\bO\u0001O\u0003O\u03fd\bO\u0001P\u0001"+
		"P\u0001P\u0001P\u0001P\u0003P\u0404\bP\u0001P\u0001P\u0001P\u0001P\u0001"+
		"P\u0001P\u0003P\u040c\bP\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001"+
		"P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001"+
		"P\u0004P\u041f\bP\u000bP\fP\u0420\u0001P\u0003P\u0424\bP\u0001P\u0003"+
		"P\u0427\bP\u0001P\u0001P\u0001P\u0001P\u0005P\u042d\bP\nP\fP\u0430\tP"+
		"\u0001P\u0003P\u0433\bP\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001"+
		"P\u0001P\u0001P\u0001P\u0001P\u0003P\u0440\bP\u0001P\u0001P\u0001P\u0001"+
		"P\u0001P\u0001P\u0001P\u0003P\u0449\bP\u0001P\u0001P\u0001P\u0003P\u044e"+
		"\bP\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0003"+
		"P\u0459\bP\u0001Q\u0001Q\u0001Q\u0001Q\u0001Q\u0001Q\u0001Q\u0001Q\u0001"+
		"R\u0004R\u0464\bR\u000bR\fR\u0465\u0001R\u0005R\u0469\bR\nR\fR\u046c\t"+
		"R\u0001R\u0005R\u046f\bR\nR\fR\u0472\tR\u0003R\u0474\bR\u0001S\u0001S"+
		"\u0001S\u0003S\u0479\bS\u0001T\u0001T\u0001T\u0001T\u0001T\u0001U\u0001"+
		"U\u0001U\u0001U\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001W\u0001"+
		"W\u0001W\u0001X\u0001X\u0001X\u0001X\u0001X\u0001X\u0001Y\u0005Y\u0494"+
		"\bY\nY\fY\u0497\tY\u0001Y\u0001Y\u0001Y\u0001Z\u0001Z\u0001Z\u0005Z\u049f"+
		"\bZ\nZ\fZ\u04a2\tZ\u0001[\u0001[\u0001[\u0001\\\u0001\\\u0001\\\u0003"+
		"\\\u04aa\b\\\u0001\\\u0001\\\u0001]\u0001]\u0001]\u0005]\u04b1\b]\n]\f"+
		"]\u04b4\t]\u0001^\u0001^\u0003^\u04b8\b^\u0001_\u0005_\u04bb\b_\n_\f_"+
		"\u04be\t_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001`\u0005`\u04c6\b`\n"+
		"`\f`\u04c9\t`\u0001`\u0001`\u0003`\u04cd\b`\u0001a\u0001a\u0001a\u0001"+
		"b\u0004b\u04d3\bb\u000bb\fb\u04d4\u0001b\u0004b\u04d8\bb\u000bb\fb\u04d9"+
		"\u0001c\u0001c\u0001c\u0003c\u04df\bc\u0001c\u0001c\u0003c\u04e3\bc\u0003"+
		"c\u04e5\bc\u0001d\u0001d\u0001d\u0005d\u04ea\bd\nd\fd\u04ed\td\u0001e"+
		"\u0001e\u0001e\u0003e\u04f2\be\u0001f\u0001f\u0001f\u0003f\u04f7\bf\u0001"+
		"f\u0001f\u0001f\u0001f\u0003f\u04fd\bf\u0001f\u0001f\u0003f\u0501\bf\u0001"+
		"f\u0001f\u0003f\u0505\bf\u0001f\u0003f\u0508\bf\u0001g\u0001g\u0003g\u050c"+
		"\bg\u0001h\u0005h\u050f\bh\nh\fh\u0512\th\u0001h\u0001h\u0001h\u0001h"+
		"\u0001h\u0001i\u0001i\u0001i\u0001i\u0001j\u0001j\u0001j\u0001j\u0001"+
		"k\u0001k\u0001k\u0005k\u0524\bk\nk\fk\u0527\tk\u0001l\u0001l\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0003m\u0530\bm\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u053d\bm\u0001m\u0001"+
		"m\u0003m\u0541\bm\u0001m\u0001m\u0001m\u0003m\u0546\bm\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u0553"+
		"\bm\u0003m\u0555\bm\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u0565\bm\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u058e\bm\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u0599"+
		"\bm\u0001m\u0001m\u0001m\u0001m\u0003m\u059f\bm\u0001m\u0001m\u0003m\u05a3"+
		"\bm\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0003m\u05ab\bm\u0001m\u0001"+
		"m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001m\u0001"+
		"m\u0003m\u05b9\bm\u0001m\u0001m\u0003m\u05bd\bm\u0001m\u0001m\u0001m\u0001"+
		"m\u0003m\u05c3\bm\u0005m\u05c5\bm\nm\fm\u05c8\tm\u0001n\u0001n\u0001n"+
		"\u0005n\u05cd\bn\nn\fn\u05d0\tn\u0001o\u0001o\u0001o\u0003o\u05d5\bo\u0001"+
		"o\u0001o\u0001o\u0001o\u0001o\u0003o\u05dc\bo\u0001p\u0001p\u0001p\u0005"+
		"p\u05e1\bp\np\fp\u05e4\tp\u0001q\u0001q\u0001q\u0001q\u0001q\u0001q\u0001"+
		"q\u0001q\u0001q\u0001q\u0001q\u0005q\u05f1\bq\nq\fq\u05f4\tq\u0001q\u0001"+
		"q\u0001q\u0001q\u0001q\u0005q\u05fb\bq\nq\fq\u05fe\tq\u0001q\u0001q\u0001"+
		"q\u0003q\u0603\bq\u0001r\u0001r\u0001r\u0003r\u0608\br\u0001r\u0001r\u0001"+
		"r\u0003r\u060d\br\u0001s\u0001s\u0001s\u0001s\u0001s\u0001s\u0001s\u0001"+
		"s\u0001s\u0001s\u0003s\u0619\bs\u0003s\u061b\bs\u0001t\u0001t\u0001t\u0003"+
		"t\u0620\bt\u0001t\u0005t\u0623\bt\nt\ft\u0626\tt\u0001t\u0003t\u0629\b"+
		"t\u0001u\u0001u\u0001u\u0001u\u0003u\u062f\bu\u0001v\u0001v\u0001v\u0003"+
		"v\u0634\bv\u0001v\u0001v\u0001w\u0001w\u0001w\u0005w\u063b\bw\nw\fw\u063e"+
		"\tw\u0001w\u0001w\u0001w\u0001w\u0005w\u0644\bw\nw\fw\u0647\tw\u0001w"+
		"\u0005w\u064a\bw\nw\fw\u064d\tw\u0003w\u064f\bw\u0001x\u0001x\u0001x\u0001"+
		"x\u0001x\u0001y\u0001y\u0003y\u0658\by\u0001z\u0001z\u0001z\u0003z\u065d"+
		"\bz\u0001{\u0001{\u0001{\u0003{\u0662\b{\u0001|\u0001|\u0001|\u0001|\u0001"+
		"}\u0001}\u0001}\u0005}\u066b\b}\n}\f}\u066e\t}\u0001~\u0001~\u0001~\u0005"+
		"~\u0673\b~\n~\f~\u0676\t~\u0001\u007f\u0001\u007f\u0001\u007f\u0005\u007f"+
		"\u067b\b\u007f\n\u007f\f\u007f\u067e\t\u007f\u0001\u0080\u0001\u0080\u0003"+
		"\u0080\u0682\b\u0080\u0001\u0081\u0001\u0081\u0001\u0081\u0001\u0081\u0001"+
		"\u0082\u0001\u0082\u0001\u0083\u0001\u0083\u0001\u0083\u0001\u0083\u0005"+
		"\u0083\u068e\b\u0083\n\u0083\f\u0083\u0691\t\u0083\u0001\u0083\u0001\u0083"+
		"\u0001\u0084\u0001\u0084\u0003\u0084\u0697\b\u0084\u0001\u0084\u0001\u0084"+
		"\u0001\u0084\u0001\u0084\u0001\u0084\u0003\u0084\u069e\b\u0084\u0001\u0084"+
		"\u0003\u0084\u06a1\b\u0084\u0003\u0084\u06a3\b\u0084\u0001\u0085\u0001"+
		"\u0085\u0003\u0085\u06a7\b\u0085\u0001\u0085\u0001\u0085\u0001\u0086\u0001"+
		"\u0086\u0001\u0087\u0001\u0087\u0001\u0087\u0003\u0087\u06b0\b\u0087\u0001"+
		"\u0088\u0001\u0088\u0001\u0088\u0001\u0088\u0001\u0089\u0001\u0089\u0001"+
		"\u008a\u0001\u008a\u0003\u008a\u06ba\b\u008a\u0001\u008b\u0005\u008b\u06bd"+
		"\b\u008b\n\u008b\f\u008b\u06c0\t\u008b\u0001\u008b\u0001\u008b\u0001\u008b"+
		"\u0001\u008b\u0003\u008b\u06c6\b\u008b\u0001\u008c\u0005\u008c\u06c9\b"+
		"\u008c\n\u008c\f\u008c\u06cc\t\u008c\u0001\u008c\u0001\u008c\u0001\u008c"+
		"\u0003\u008c\u06d1\b\u008c\u0001\u008c\u0001\u008c\u0003\u008c\u06d5\b"+
		"\u008c\u0001\u008d\u0001\u008d\u0001\u008d\u0005\u008d\u06da\b\u008d\n"+
		"\u008d\f\u008d\u06dd\t\u008d\u0001\u008e\u0001\u008e\u0001\u008e\u0001"+
		"\u008e\u0005\u008e\u06e3\b\u008e\n\u008e\f\u008e\u06e6\t\u008e\u0001\u008f"+
		"\u0001\u008f\u0001\u008f\u0000\u0001\u00da\u0090\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088"+
		"\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0"+
		"\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8"+
		"\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0"+
		"\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8"+
		"\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe\u0100"+
		"\u0102\u0104\u0106\u0108\u010a\u010c\u010e\u0110\u0112\u0114\u0116\u0118"+
		"\u011a\u011c\u011e\u0000\r\u0001\u0000\u00e4\u00eb\u0001\u0000\u00c0\u00c3"+
		"\u0001\u0000NO\u0002\u0000}~\u0081\u0082\u0001\u0000\u0083\u0084\u0002"+
		"\u0000<<\u007f\u0080\u0001\u0000}~\u0001\u0000ux\u0001\u0000st\u0002\u0000"+
		"PPbl\u0001\u0000\u0081\u0082\u0001\u000019\u0007\u0000::\u00c8\u00c8\u00cf"+
		"\u00cf\u00d1\u00d3\u00d6\u00d6\u00e0\u00e0\u00e2\u00e2\u0790\u0000\u0121"+
		"\u0001\u0000\u0000\u0000\u0002\u0131\u0001\u0000\u0000\u0000\u0004\u0147"+
		"\u0001\u0000\u0000\u0000\u0006\u0155\u0001\u0000\u0000\u0000\b\u015c\u0001"+
		"\u0000\u0000\u0000\n\u016d\u0001\u0000\u0000\u0000\f\u0171\u0001\u0000"+
		"\u0000\u0000\u000e\u0173\u0001\u0000\u0000\u0000\u0010\u0183\u0001\u0000"+
		"\u0000\u0000\u0012\u018e\u0001\u0000\u0000\u0000\u0014\u01a1\u0001\u0000"+
		"\u0000\u0000\u0016\u01a3\u0001\u0000\u0000\u0000\u0018\u01a7\u0001\u0000"+
		"\u0000\u0000\u001a\u01ac\u0001\u0000\u0000\u0000\u001c\u01b7\u0001\u0000"+
		"\u0000\u0000\u001e\u01bc\u0001\u0000\u0000\u0000 \u01c2\u0001\u0000\u0000"+
		"\u0000\"\u01c5\u0001\u0000\u0000\u0000$\u01c8\u0001\u0000\u0000\u0000"+
		"&\u01d3\u0001\u0000\u0000\u0000(\u01d8\u0001\u0000\u0000\u0000*\u01dc"+
		"\u0001\u0000\u0000\u0000,\u01e4\u0001\u0000\u0000\u0000.\u01ec\u0001\u0000"+
		"\u0000\u00000\u01f3\u0001\u0000\u0000\u00002\u01ff\u0001\u0000\u0000\u0000"+
		"4\u0207\u0001\u0000\u0000\u00006\u020f\u0001\u0000\u0000\u00008\u0216"+
		"\u0001\u0000\u0000\u0000:\u0223\u0001\u0000\u0000\u0000<\u0226\u0001\u0000"+
		"\u0000\u0000>\u022f\u0001\u0000\u0000\u0000@\u0244\u0001\u0000\u0000\u0000"+
		"B\u024e\u0001\u0000\u0000\u0000D\u0251\u0001\u0000\u0000\u0000F\u0263"+
		"\u0001\u0000\u0000\u0000H\u0265\u0001\u0000\u0000\u0000J\u0269\u0001\u0000"+
		"\u0000\u0000L\u0272\u0001\u0000\u0000\u0000N\u027e\u0001\u0000\u0000\u0000"+
		"P\u0287\u0001\u0000\u0000\u0000R\u028a\u0001\u0000\u0000\u0000T\u029a"+
		"\u0001\u0000\u0000\u0000V\u02a2\u0001\u0000\u0000\u0000X\u02b3\u0001\u0000"+
		"\u0000\u0000Z\u02bd\u0001\u0000\u0000\u0000\\\u02bf\u0001\u0000\u0000"+
		"\u0000^\u02cf\u0001\u0000\u0000\u0000`\u02da\u0001\u0000\u0000\u0000b"+
		"\u02ea\u0001\u0000\u0000\u0000d\u02ec\u0001\u0000\u0000\u0000f\u02f7\u0001"+
		"\u0000\u0000\u0000h\u030a\u0001\u0000\u0000\u0000j\u030f\u0001\u0000\u0000"+
		"\u0000l\u0318\u0001\u0000\u0000\u0000n\u0320\u0001\u0000\u0000\u0000p"+
		"\u0327\u0001\u0000\u0000\u0000r\u0333\u0001\u0000\u0000\u0000t\u0335\u0001"+
		"\u0000\u0000\u0000v\u0337\u0001\u0000\u0000\u0000x\u0339\u0001\u0000\u0000"+
		"\u0000z\u0340\u0001\u0000\u0000\u0000|\u0343\u0001\u0000\u0000\u0000~"+
		"\u034d\u0001\u0000\u0000\u0000\u0080\u0355\u0001\u0000\u0000\u0000\u0082"+
		"\u035c\u0001\u0000\u0000\u0000\u0084\u035e\u0001\u0000\u0000\u0000\u0086"+
		"\u036e\u0001\u0000\u0000\u0000\u0088\u0373\u0001\u0000\u0000\u0000\u008a"+
		"\u0384\u0001\u0000\u0000\u0000\u008c\u03a4\u0001\u0000\u0000\u0000\u008e"+
		"\u03a6\u0001\u0000\u0000\u0000\u0090\u03b2\u0001\u0000\u0000\u0000\u0092"+
		"\u03b4\u0001\u0000\u0000\u0000\u0094\u03b7\u0001\u0000\u0000\u0000\u0096"+
		"\u03d3\u0001\u0000\u0000\u0000\u0098\u03d5\u0001\u0000\u0000\u0000\u009a"+
		"\u03e4\u0001\u0000\u0000\u0000\u009c\u03e9\u0001\u0000\u0000\u0000\u009e"+
		"\u03fc\u0001\u0000\u0000\u0000\u00a0\u0458\u0001\u0000\u0000\u0000\u00a2"+
		"\u045a\u0001\u0000\u0000\u0000\u00a4\u0473\u0001\u0000\u0000\u0000\u00a6"+
		"\u0478\u0001\u0000\u0000\u0000\u00a8\u047a\u0001\u0000\u0000\u0000\u00aa"+
		"\u047f\u0001\u0000\u0000\u0000\u00ac\u0483\u0001\u0000\u0000\u0000\u00ae"+
		"\u0489\u0001\u0000\u0000\u0000\u00b0\u048c\u0001\u0000\u0000\u0000\u00b2"+
		"\u0495\u0001\u0000\u0000\u0000\u00b4\u049b\u0001\u0000\u0000\u0000\u00b6"+
		"\u04a3\u0001\u0000\u0000\u0000\u00b8\u04a6\u0001\u0000\u0000\u0000\u00ba"+
		"\u04ad\u0001\u0000\u0000\u0000\u00bc\u04b7\u0001\u0000\u0000\u0000\u00be"+
		"\u04bc\u0001\u0000\u0000\u0000\u00c0\u04c7\u0001\u0000\u0000\u0000\u00c2"+
		"\u04ce\u0001\u0000\u0000\u0000\u00c4\u04d2\u0001\u0000\u0000\u0000\u00c6"+
		"\u04e4\u0001\u0000\u0000\u0000\u00c8\u04e6\u0001\u0000\u0000\u0000\u00ca"+
		"\u04f1\u0001\u0000\u0000\u0000\u00cc\u0507\u0001\u0000\u0000\u0000\u00ce"+
		"\u050b\u0001\u0000\u0000\u0000\u00d0\u0510\u0001\u0000\u0000\u0000\u00d2"+
		"\u0518\u0001\u0000\u0000\u0000\u00d4\u051c\u0001\u0000\u0000\u0000\u00d6"+
		"\u0520\u0001\u0000\u0000\u0000\u00d8\u0528\u0001\u0000\u0000\u0000\u00da"+
		"\u0554\u0001\u0000\u0000\u0000\u00dc\u05c9\u0001\u0000\u0000\u0000\u00de"+
		"\u05db\u0001\u0000\u0000\u0000\u00e0\u05dd\u0001\u0000\u0000\u0000\u00e2"+
		"\u0602\u0001\u0000\u0000\u0000\u00e4\u0607\u0001\u0000\u0000\u0000\u00e6"+
		"\u061a\u0001\u0000\u0000\u0000\u00e8\u0628\u0001\u0000\u0000\u0000\u00ea"+
		"\u062a\u0001\u0000\u0000\u0000\u00ec\u0630\u0001\u0000\u0000\u0000\u00ee"+
		"\u0637\u0001\u0000\u0000\u0000\u00f0\u0650\u0001\u0000\u0000\u0000\u00f2"+
		"\u0655\u0001\u0000\u0000\u0000\u00f4\u065c\u0001\u0000\u0000\u0000\u00f6"+
		"\u0661\u0001\u0000\u0000\u0000\u00f8\u0663\u0001\u0000\u0000\u0000\u00fa"+
		"\u0667\u0001\u0000\u0000\u0000\u00fc\u066f\u0001\u0000\u0000\u0000\u00fe"+
		"\u0677\u0001\u0000\u0000\u0000\u0100\u0681\u0001\u0000\u0000\u0000\u0102"+
		"\u0683\u0001\u0000\u0000\u0000\u0104\u0687\u0001\u0000\u0000\u0000\u0106"+
		"\u0689\u0001\u0000\u0000\u0000\u0108\u06a2\u0001\u0000\u0000\u0000\u010a"+
		"\u06a4\u0001\u0000\u0000\u0000\u010c\u06aa\u0001\u0000\u0000\u0000\u010e"+
		"\u06af\u0001\u0000\u0000\u0000\u0110\u06b1\u0001\u0000\u0000\u0000\u0112"+
		"\u06b5\u0001\u0000\u0000\u0000\u0114\u06b9\u0001\u0000\u0000\u0000\u0116"+
		"\u06c5\u0001\u0000\u0000\u0000\u0118\u06ca\u0001\u0000\u0000\u0000\u011a"+
		"\u06d6\u0001\u0000\u0000\u0000\u011c\u06de\u0001\u0000\u0000\u0000\u011e"+
		"\u06e7\u0001\u0000\u0000\u0000\u0120\u0122\u0003\u0002\u0001\u0000\u0121"+
		"\u0120\u0001\u0000\u0000\u0000\u0121\u0122\u0001\u0000\u0000\u0000\u0122"+
		"\u0126\u0001\u0000\u0000\u0000\u0123\u0125\u0003\u0004\u0002\u0000\u0124"+
		"\u0123\u0001\u0000\u0000\u0000\u0125\u0128\u0001\u0000\u0000\u0000\u0126"+
		"\u0124\u0001\u0000\u0000\u0000\u0126\u0127\u0001\u0000\u0000\u0000\u0127"+
		"\u012c\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000\u0000\u0000\u0129"+
		"\u012b\u0003\u0006\u0003\u0000\u012a\u0129\u0001\u0000\u0000\u0000\u012b"+
		"\u012e\u0001\u0000\u0000\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c"+
		"\u012d\u0001\u0000\u0000\u0000\u012d\u012f\u0001\u0000\u0000\u0000\u012e"+
		"\u012c\u0001\u0000\u0000\u0000\u012f\u0130\u0005\u0000\u0000\u0001\u0130"+
		"\u0001\u0001\u0000\u0000\u0000\u0131\u0132\u0003z=\u0000\u0132\u0133\u0005"+
		",\u0000\u0000\u0133\u0134\u0003n7\u0000\u0134\u0135\u0005-\u0000\u0000"+
		"\u0135\u0003\u0001\u0000\u0000\u0000\u0136\u0138\u0005\u001e\u0000\u0000"+
		"\u0137\u0139\u0005@\u0000\u0000\u0138\u0137\u0001\u0000\u0000\u0000\u0138"+
		"\u0139\u0001\u0000\u0000\u0000\u0139\u013a\u0001\u0000\u0000\u0000\u013a"+
		"\u013d\u0003n7\u0000\u013b\u013c\u0005;\u0000\u0000\u013c\u013e\u0005"+
		"<\u0000\u0000\u013d\u013b\u0001\u0000\u0000\u0000\u013d\u013e\u0001\u0000"+
		"\u0000\u0000\u013e\u013f\u0001\u0000\u0000\u0000\u013f\u0140\u0005-\u0000"+
		"\u0000\u0140\u0148\u0001\u0000\u0000\u0000\u0141\u0142\u0005\u001e\u0000"+
		"\u0000\u0142\u0143\u0005\u00e2\u0000\u0000\u0143\u0144\u0003n7\u0000\u0144"+
		"\u0145\u0005-\u0000\u0000\u0145\u0148\u0001\u0000\u0000\u0000\u0146\u0148"+
		"\u0005-\u0000\u0000\u0147\u0136\u0001\u0000\u0000\u0000\u0147\u0141\u0001"+
		"\u0000\u0000\u0000\u0147\u0146\u0001\u0000\u0000\u0000\u0148\u0005\u0001"+
		"\u0000\u0000\u0000\u0149\u014b\u0003\n\u0005\u0000\u014a\u0149\u0001\u0000"+
		"\u0000\u0000\u014b\u014e\u0001\u0000\u0000\u0000\u014c\u014a\u0001\u0000"+
		"\u0000\u0000\u014c\u014d\u0001\u0000\u0000\u0000\u014d\u014f\u0001\u0000"+
		"\u0000\u0000\u014e\u014c\u0001\u0000\u0000\u0000\u014f\u0156\u0003\b\u0004"+
		"\u0000\u0150\u0152\u0005-\u0000\u0000\u0151\u0150\u0001\u0000\u0000\u0000"+
		"\u0152\u0153\u0001\u0000\u0000\u0000\u0153\u0151\u0001\u0000\u0000\u0000"+
		"\u0153\u0154\u0001\u0000\u0000\u0000\u0154\u0156\u0001\u0000\u0000\u0000"+
		"\u0155\u014c\u0001\u0000\u0000\u0000\u0155\u0151\u0001\u0000\u0000\u0000"+
		"\u0156\u0007\u0001\u0000\u0000\u0000\u0157\u015d\u0003\u000e\u0007\u0000"+
		"\u0158\u015d\u0003.\u0017\u0000\u0159\u015d\u00038\u001c\u0000\u015a\u015d"+
		"\u0003\u0086C\u0000\u015b\u015d\u0003\u0010\b\u0000\u015c\u0157\u0001"+
		"\u0000\u0000\u0000\u015c\u0158\u0001\u0000\u0000\u0000\u015c\u0159\u0001"+
		"\u0000\u0000\u0000\u015c\u015a\u0001\u0000\u0000\u0000\u015c\u015b\u0001"+
		"\u0000\u0000\u0000\u015d\t\u0001\u0000\u0000\u0000\u015e\u016e\u0003|"+
		">\u0000\u015f\u016e\u0005>\u0000\u0000\u0160\u016e\u0005?\u0000\u0000"+
		"\u0161\u016e\u0005=\u0000\u0000\u0162\u016e\u0005@\u0000\u0000\u0163\u016e"+
		"\u0005(\u0000\u0000\u0164\u016e\u0005^\u0000\u0000\u0165\u016e\u0005\'"+
		"\u0000\u0000\u0166\u016e\u0005)\u0000\u0000\u0167\u016e\u0005B\u0000\u0000"+
		"\u0168\u016e\u0005C\u0000\u0000\u0169\u016e\u0005A\u0000\u0000\u016a\u016e"+
		"\u0005D\u0000\u0000\u016b\u016e\u0005\u00d1\u0000\u0000\u016c\u016e\u0005"+
		"\u00d2\u0000\u0000\u016d\u015e\u0001\u0000\u0000\u0000\u016d\u015f\u0001"+
		"\u0000\u0000\u0000\u016d\u0160\u0001\u0000\u0000\u0000\u016d\u0161\u0001"+
		"\u0000\u0000\u0000\u016d\u0162\u0001\u0000\u0000\u0000\u016d\u0163\u0001"+
		"\u0000\u0000\u0000\u016d\u0164\u0001\u0000\u0000\u0000\u016d\u0165\u0001"+
		"\u0000\u0000\u0000\u016d\u0166\u0001\u0000\u0000\u0000\u016d\u0167\u0001"+
		"\u0000\u0000\u0000\u016d\u0168\u0001\u0000\u0000\u0000\u016d\u0169\u0001"+
		"\u0000\u0000\u0000\u016d\u016a\u0001\u0000\u0000\u0000\u016d\u016b\u0001"+
		"\u0000\u0000\u0000\u016d\u016c\u0001\u0000\u0000\u0000\u016e\u000b\u0001"+
		"\u0000\u0000\u0000\u016f\u0172\u0005\'\u0000\u0000\u0170\u0172\u0003|"+
		">\u0000\u0171\u016f\u0001\u0000\u0000\u0000\u0171\u0170\u0001\u0000\u0000"+
		"\u0000\u0172\r\u0001\u0000\u0000\u0000\u0173\u0174\u0005E\u0000\u0000"+
		"\u0174\u0176\u0003\u011e\u008f\u0000\u0175\u0177\u0003$\u0012\u0000\u0176"+
		"\u0175\u0001\u0000\u0000\u0000\u0176\u0177\u0001\u0000\u0000\u0000\u0177"+
		"\u0179\u0001\u0000\u0000\u0000\u0178\u017a\u0003 \u0010\u0000\u0179\u0178"+
		"\u0001\u0000\u0000\u0000\u0179\u017a\u0001\u0000\u0000\u0000\u017a\u017c"+
		"\u0001\u0000\u0000\u0000\u017b\u017d\u0003\"\u0011\u0000\u017c\u017b\u0001"+
		"\u0000\u0000\u0000\u017c\u017d\u0001\u0000\u0000\u0000\u017d\u017f\u0001"+
		"\u0000\u0000\u0000\u017e\u0180\u0003\u011c\u008e\u0000\u017f\u017e\u0001"+
		"\u0000\u0000\u0000\u017f\u0180\u0001\u0000\u0000\u0000\u0180\u0181\u0001"+
		"\u0000\u0000\u0000\u0181\u0182\u0003<\u001e\u0000\u0182\u000f\u0001\u0000"+
		"\u0000\u0000\u0183\u0184\u0005\u00c8\u0000\u0000\u0184\u0186\u0003\u011e"+
		"\u008f\u0000\u0185\u0187\u0003$\u0012\u0000\u0186\u0185\u0001\u0000\u0000"+
		"\u0000\u0186\u0187\u0001\u0000\u0000\u0000\u0187\u0188\u0001\u0000\u0000"+
		"\u0000\u0188\u018a\u0003\u0012\t\u0000\u0189\u018b\u0003\"\u0011\u0000"+
		"\u018a\u0189\u0001\u0000\u0000\u0000\u018a\u018b\u0001\u0000\u0000\u0000"+
		"\u018b\u018c\u0001\u0000\u0000\u0000\u018c\u018d\u0003\u001a\r\u0000\u018d"+
		"\u0011\u0001\u0000\u0000\u0000\u018e\u0190\u0005L\u0000\u0000\u018f\u0191"+
		"\u0003\u0014\n\u0000\u0190\u018f\u0001\u0000\u0000\u0000\u0190\u0191\u0001"+
		"\u0000\u0000\u0000\u0191\u0192\u0001\u0000\u0000\u0000\u0192\u0193\u0005"+
		"M\u0000\u0000\u0193\u0013\u0001\u0000\u0000\u0000\u0194\u0199\u0003\u0016"+
		"\u000b\u0000\u0195\u0196\u0005J\u0000\u0000\u0196\u0198\u0003\u0016\u000b"+
		"\u0000\u0197\u0195\u0001\u0000\u0000\u0000\u0198\u019b\u0001\u0000\u0000"+
		"\u0000\u0199\u0197\u0001\u0000\u0000\u0000\u0199\u019a\u0001\u0000\u0000"+
		"\u0000\u019a\u019e\u0001\u0000\u0000\u0000\u019b\u0199\u0001\u0000\u0000"+
		"\u0000\u019c\u019d\u0005J\u0000\u0000\u019d\u019f\u0003\u0018\f\u0000"+
		"\u019e\u019c\u0001\u0000\u0000\u0000\u019e\u019f\u0001\u0000\u0000\u0000"+
		"\u019f\u01a2\u0001\u0000\u0000\u0000\u01a0\u01a2\u0003\u0018\f\u0000\u01a1"+
		"\u0194\u0001\u0000\u0000\u0000\u01a1\u01a0\u0001\u0000\u0000\u0000\u01a2"+
		"\u0015\u0001\u0000\u0000\u0000\u01a3\u01a4\u0003z=\u0000\u01a4\u01a5\u0003"+
		"\u00fe\u007f\u0000\u01a5\u01a6\u0003\u011e\u008f\u0000\u01a6\u0017\u0001"+
		"\u0000\u0000\u0000\u01a7\u01a8\u0003z=\u0000\u01a8\u01a9\u0003\u00fe\u007f"+
		"\u0000\u01a9\u01aa\u0005\u00ab\u0000\u0000\u01aa\u01ab\u0003\u011e\u008f"+
		"\u0000\u01ab\u0019\u0001\u0000\u0000\u0000\u01ac\u01b0\u0005H\u0000\u0000"+
		"\u01ad\u01af\u0003\u001c\u000e\u0000\u01ae\u01ad\u0001\u0000\u0000\u0000"+
		"\u01af\u01b2\u0001\u0000\u0000\u0000\u01b0\u01ae\u0001\u0000\u0000\u0000"+
		"\u01b0\u01b1\u0001\u0000\u0000\u0000\u01b1\u01b3\u0001\u0000\u0000\u0000"+
		"\u01b2\u01b0\u0001\u0000\u0000\u0000\u01b3\u01b4\u0005I\u0000\u0000\u01b4"+
		"\u001b\u0001\u0000\u0000\u0000\u01b5\u01b8\u0003\u001e\u000f\u0000\u01b6"+
		"\u01b8\u0003@ \u0000\u01b7\u01b5\u0001\u0000\u0000\u0000\u01b7\u01b6\u0001"+
		"\u0000\u0000\u0000\u01b8\u001d\u0001\u0000\u0000\u0000\u01b9\u01bb\u0003"+
		"\n\u0005\u0000\u01ba\u01b9\u0001\u0000\u0000\u0000\u01bb\u01be\u0001\u0000"+
		"\u0000\u0000\u01bc\u01ba\u0001\u0000\u0000\u0000\u01bc\u01bd\u0001\u0000"+
		"\u0000\u0000\u01bd\u01bf\u0001\u0000\u0000\u0000\u01be\u01bc\u0001\u0000"+
		"\u0000\u0000\u01bf\u01c0\u0003\u011e\u008f\u0000\u01c0\u01c1\u0003\u0094"+
		"J\u0000\u01c1\u001f\u0001\u0000\u0000\u0000\u01c2\u01c3\u0005\u0012\u0000"+
		"\u0000\u01c3\u01c4\u0003\u00fe\u007f\u0000\u01c4!\u0001\u0000\u0000\u0000"+
		"\u01c5\u01c6\u0005K\u0000\u0000\u01c6\u01c7\u0003\u00fc~\u0000\u01c7#"+
		"\u0001\u0000\u0000\u0000\u01c8\u01c9\u0005u\u0000\u0000\u01c9\u01ce\u0003"+
		"&\u0013\u0000\u01ca\u01cb\u0005J\u0000\u0000\u01cb\u01cd\u0003&\u0013"+
		"\u0000\u01cc\u01ca\u0001\u0000\u0000\u0000\u01cd\u01d0\u0001\u0000\u0000"+
		"\u0000\u01ce\u01cc\u0001\u0000\u0000\u0000\u01ce\u01cf\u0001\u0000\u0000"+
		"\u0000\u01cf\u01d1\u0001\u0000\u0000\u0000\u01d0\u01ce\u0001\u0000\u0000"+
		"\u0000\u01d1\u01d2\u0005v\u0000\u0000\u01d2%\u0001\u0000\u0000\u0000\u01d3"+
		"\u01d4\u0003z=\u0000\u01d4\u01d6\u0003\u011e\u008f\u0000\u01d5\u01d7\u0003"+
		"(\u0014\u0000\u01d6\u01d5\u0001\u0000\u0000\u0000\u01d6\u01d7\u0001\u0000"+
		"\u0000\u0000\u01d7\'\u0001\u0000\u0000\u0000\u01d8\u01d9\u0005\u0012\u0000"+
		"\u0000\u01d9\u01da\u0003z=\u0000\u01da\u01db\u0003*\u0015\u0000\u01db"+
		")\u0001\u0000\u0000\u0000\u01dc\u01e1\u0003,\u0016\u0000\u01dd\u01de\u0005"+
		"r\u0000\u0000\u01de\u01e0\u0003,\u0016\u0000\u01df\u01dd\u0001\u0000\u0000"+
		"\u0000\u01e0\u01e3\u0001\u0000\u0000\u0000\u01e1\u01df\u0001\u0000\u0000"+
		"\u0000\u01e1\u01e2\u0001\u0000\u0000\u0000\u01e2+\u0001\u0000\u0000\u0000"+
		"\u01e3\u01e1\u0001\u0000\u0000\u0000\u01e4\u01e5\u0003z=\u0000\u01e5\u01e9"+
		"\u0003\u0100\u0080\u0000\u01e6\u01e8\u0003\u0102\u0081\u0000\u01e7\u01e6"+
		"\u0001\u0000\u0000\u0000\u01e8\u01eb\u0001\u0000\u0000\u0000\u01e9\u01e7"+
		"\u0001\u0000\u0000\u0000\u01e9\u01ea\u0001\u0000\u0000\u0000\u01ea-\u0001"+
		"\u0000\u0000\u0000\u01eb\u01e9\u0001\u0000\u0000\u0000\u01ec\u01ed\u0005"+
		"\u0099\u0000\u0000\u01ed\u01ef\u0003\u011e\u008f\u0000\u01ee\u01f0\u0003"+
		"\"\u0011\u0000\u01ef\u01ee\u0001\u0000\u0000\u0000\u01ef\u01f0\u0001\u0000"+
		"\u0000\u0000\u01f0\u01f1\u0001\u0000\u0000\u0000\u01f1\u01f2\u00030\u0018"+
		"\u0000\u01f2/\u0001\u0000\u0000\u0000\u01f3\u01f5\u0005H\u0000\u0000\u01f4"+
		"\u01f6\u00032\u0019\u0000\u01f5\u01f4\u0001\u0000\u0000\u0000\u01f5\u01f6"+
		"\u0001\u0000\u0000\u0000\u01f6\u01f8\u0001\u0000\u0000\u0000\u01f7\u01f9"+
		"\u0005J\u0000\u0000\u01f8\u01f7\u0001\u0000\u0000\u0000\u01f8\u01f9\u0001"+
		"\u0000\u0000\u0000\u01f9\u01fb\u0001\u0000\u0000\u0000\u01fa\u01fc\u0003"+
		"6\u001b\u0000\u01fb\u01fa\u0001\u0000\u0000\u0000\u01fb\u01fc\u0001\u0000"+
		"\u0000\u0000\u01fc\u01fd\u0001\u0000\u0000\u0000\u01fd\u01fe\u0005I\u0000"+
		"\u0000\u01fe1\u0001\u0000\u0000\u0000\u01ff\u0204\u00034\u001a\u0000\u0200"+
		"\u0201\u0005J\u0000\u0000\u0201\u0203\u00034\u001a\u0000\u0202\u0200\u0001"+
		"\u0000\u0000\u0000\u0203\u0206\u0001\u0000\u0000\u0000\u0204\u0202\u0001"+
		"\u0000\u0000\u0000\u0204\u0205\u0001\u0000\u0000\u0000\u02053\u0001\u0000"+
		"\u0000\u0000\u0206\u0204\u0001\u0000\u0000\u0000\u0207\u0208\u0003z=\u0000"+
		"\u0208\u020a\u0003\u011e\u008f\u0000\u0209\u020b\u0003\u010a\u0085\u0000"+
		"\u020a\u0209\u0001\u0000\u0000\u0000\u020a\u020b\u0001\u0000\u0000\u0000"+
		"\u020b\u020d\u0001\u0000\u0000\u0000\u020c\u020e\u0003<\u001e\u0000\u020d"+
		"\u020c\u0001\u0000\u0000\u0000\u020d\u020e\u0001\u0000\u0000\u0000\u020e"+
		"5\u0001\u0000\u0000\u0000\u020f\u0213\u0005-\u0000\u0000\u0210\u0212\u0003"+
		"@ \u0000\u0211\u0210\u0001\u0000\u0000\u0000\u0212\u0215\u0001\u0000\u0000"+
		"\u0000\u0213\u0211\u0001\u0000\u0000\u0000\u0213\u0214\u0001\u0000\u0000"+
		"\u0000\u02147\u0001\u0000\u0000\u0000\u0215\u0213\u0001\u0000\u0000\u0000"+
		"\u0216\u0217\u0005G\u0000\u0000\u0217\u0219\u0003\u011e\u008f\u0000\u0218"+
		"\u021a\u0003$\u0012\u0000\u0219\u0218\u0001\u0000\u0000\u0000\u0219\u021a"+
		"\u0001\u0000\u0000\u0000\u021a\u021c\u0001\u0000\u0000\u0000\u021b\u021d"+
		"\u0003:\u001d\u0000\u021c\u021b\u0001\u0000\u0000\u0000\u021c\u021d\u0001"+
		"\u0000\u0000\u0000\u021d\u021f\u0001\u0000\u0000\u0000\u021e\u0220\u0003"+
		"\u011c\u008e\u0000\u021f\u021e\u0001\u0000\u0000\u0000\u021f\u0220\u0001"+
		"\u0000\u0000\u0000\u0220\u0221\u0001\u0000\u0000\u0000\u0221\u0222\u0003"+
		">\u001f\u0000\u02229\u0001\u0000\u0000\u0000\u0223\u0224\u0005\u0012\u0000"+
		"\u0000\u0224\u0225\u0003\u00fc~\u0000\u0225;\u0001\u0000\u0000\u0000\u0226"+
		"\u022a\u0005H\u0000\u0000\u0227\u0229\u0003@ \u0000\u0228\u0227\u0001"+
		"\u0000\u0000\u0000\u0229\u022c\u0001\u0000\u0000\u0000\u022a\u0228\u0001"+
		"\u0000\u0000\u0000\u022a\u022b\u0001\u0000\u0000\u0000\u022b\u022d\u0001"+
		"\u0000\u0000\u0000\u022c\u022a\u0001\u0000\u0000\u0000\u022d\u022e\u0005"+
		"I\u0000\u0000\u022e=\u0001\u0000\u0000\u0000\u022f\u0233\u0005H\u0000"+
		"\u0000\u0230\u0232\u0003N\'\u0000\u0231\u0230\u0001\u0000\u0000\u0000"+
		"\u0232\u0235\u0001\u0000\u0000\u0000\u0233\u0231\u0001\u0000\u0000\u0000"+
		"\u0233\u0234\u0001\u0000\u0000\u0000\u0234\u0236\u0001\u0000\u0000\u0000"+
		"\u0235\u0233\u0001\u0000\u0000\u0000\u0236\u0237\u0005I\u0000\u0000\u0237"+
		"?\u0001\u0000\u0000\u0000\u0238\u0245\u0005-\u0000\u0000\u0239\u023b\u0005"+
		"@\u0000\u0000\u023a\u0239\u0001\u0000\u0000\u0000\u023a\u023b\u0001\u0000"+
		"\u0000\u0000\u023b\u023c\u0001\u0000\u0000\u0000\u023c\u0245\u0003\u0098"+
		"L\u0000\u023d\u023f\u0003\n\u0005\u0000\u023e\u023d\u0001\u0000\u0000"+
		"\u0000\u023f\u0242\u0001\u0000\u0000\u0000\u0240\u023e\u0001\u0000\u0000"+
		"\u0000\u0240\u0241\u0001\u0000\u0000\u0000\u0241\u0243\u0001\u0000\u0000"+
		"\u0000\u0242\u0240\u0001\u0000\u0000\u0000\u0243\u0245\u0003B!\u0000\u0244"+
		"\u0238\u0001\u0000\u0000\u0000\u0244\u023a\u0001\u0000\u0000\u0000\u0244"+
		"\u0240\u0001\u0000\u0000\u0000\u0245A\u0001\u0000\u0000\u0000\u0246\u024f"+
		"\u0003\u0010\b\u0000\u0247\u024f\u0003D\"\u0000\u0248\u024f\u0003L&\u0000"+
		"\u0249\u024f\u0003J%\u0000\u024a\u024f\u00038\u001c\u0000\u024b\u024f"+
		"\u0003\u0086C\u0000\u024c\u024f\u0003\u000e\u0007\u0000\u024d\u024f\u0003"+
		".\u0017\u0000\u024e\u0246\u0001\u0000\u0000\u0000\u024e\u0247\u0001\u0000"+
		"\u0000\u0000\u024e\u0248\u0001\u0000\u0000\u0000\u024e\u0249\u0001\u0000"+
		"\u0000\u0000\u024e\u024a\u0001\u0000\u0000\u0000\u024e\u024b\u0001\u0000"+
		"\u0000\u0000\u024e\u024c\u0001\u0000\u0000\u0000\u024e\u024d\u0001\u0000"+
		"\u0000\u0000\u024fC\u0001\u0000\u0000\u0000\u0250\u0252\u0003$\u0012\u0000"+
		"\u0251\u0250\u0001\u0000\u0000\u0000\u0251\u0252\u0001\u0000\u0000\u0000"+
		"\u0252\u0253\u0001\u0000\u0000\u0000\u0253\u0254\u0003\u00fe\u007f\u0000"+
		"\u0254\u0255\u0003\u011e\u008f\u0000\u0255\u0259\u0003f3\u0000\u0256\u0258"+
		"\u0003\u0102\u0081\u0000\u0257\u0256\u0001\u0000\u0000\u0000\u0258\u025b"+
		"\u0001\u0000\u0000\u0000\u0259\u0257\u0001\u0000\u0000\u0000\u0259\u025a"+
		"\u0001\u0000\u0000\u0000\u025a\u025d\u0001\u0000\u0000\u0000\u025b\u0259"+
		"\u0001\u0000\u0000\u0000\u025c\u025e\u0003H$\u0000\u025d\u025c\u0001\u0000"+
		"\u0000\u0000\u025d\u025e\u0001\u0000\u0000\u0000\u025e\u025f\u0001\u0000"+
		"\u0000\u0000\u025f\u0260\u0003F#\u0000\u0260E\u0001\u0000\u0000\u0000"+
		"\u0261\u0264\u0003\u0098L\u0000\u0262\u0264\u0005-\u0000\u0000\u0263\u0261"+
		"\u0001\u0000\u0000\u0000\u0263\u0262\u0001\u0000\u0000\u0000\u0264G\u0001"+
		"\u0000\u0000\u0000\u0265\u0266\u0005Q\u0000\u0000\u0266\u0267\u0003d2"+
		"\u0000\u0267I\u0001\u0000\u0000\u0000\u0268\u026a\u0003$\u0012\u0000\u0269"+
		"\u0268\u0001\u0000\u0000\u0000\u0269\u026a\u0001\u0000\u0000\u0000\u026a"+
		"\u026b\u0001\u0000\u0000\u0000\u026b\u026c\u0003\u011e\u008f\u0000\u026c"+
		"\u026e\u0003f3\u0000\u026d\u026f\u0003H$\u0000\u026e\u026d\u0001\u0000"+
		"\u0000\u0000\u026e\u026f\u0001\u0000\u0000\u0000\u026f\u0270\u0001\u0000"+
		"\u0000\u0000\u0270\u0271\u0003\u0094J\u0000\u0271K\u0001\u0000\u0000\u0000"+
		"\u0272\u0273\u0003\u00fe\u007f\u0000\u0273\u0274\u0003T*\u0000\u0274\u0275"+
		"\u0005-\u0000\u0000\u0275M\u0001\u0000\u0000\u0000\u0276\u0278\u0003\n"+
		"\u0005\u0000\u0277\u0276\u0001\u0000\u0000\u0000\u0278\u027b\u0001\u0000"+
		"\u0000\u0000\u0279\u0277\u0001\u0000\u0000\u0000\u0279\u027a\u0001\u0000"+
		"\u0000\u0000\u027a\u027c\u0001\u0000\u0000\u0000\u027b\u0279\u0001\u0000"+
		"\u0000\u0000\u027c\u027f\u0003P(\u0000\u027d\u027f\u0005-\u0000\u0000"+
		"\u027e\u0279\u0001\u0000\u0000\u0000\u027e\u027d\u0001\u0000\u0000\u0000"+
		"\u027fO\u0001\u0000\u0000\u0000\u0280\u0288\u0003L&\u0000\u0281\u0288"+
		"\u0003\u0010\b\u0000\u0282\u0288\u0003R)\u0000\u0283\u0288\u00038\u001c"+
		"\u0000\u0284\u0288\u0003\u0086C\u0000\u0285\u0288\u0003\u000e\u0007\u0000"+
		"\u0286\u0288\u0003.\u0017\u0000\u0287\u0280\u0001\u0000\u0000\u0000\u0287"+
		"\u0281\u0001\u0000\u0000\u0000\u0287\u0282\u0001\u0000\u0000\u0000\u0287"+
		"\u0283\u0001\u0000\u0000\u0000\u0287\u0284\u0001\u0000\u0000\u0000\u0287"+
		"\u0285\u0001\u0000\u0000\u0000\u0287\u0286\u0001\u0000\u0000\u0000\u0288"+
		"Q\u0001\u0000\u0000\u0000\u0289\u028b\u0003$\u0012\u0000\u028a\u0289\u0001"+
		"\u0000\u0000\u0000\u028a\u028b\u0001\u0000\u0000\u0000\u028b\u028c\u0001"+
		"\u0000\u0000\u0000\u028c\u028d\u0003\u00fe\u007f\u0000\u028d\u028e\u0003"+
		"\u011e\u008f\u0000\u028e\u0292\u0003f3\u0000\u028f\u0291\u0003\u0102\u0081"+
		"\u0000\u0290\u028f\u0001\u0000\u0000\u0000\u0291\u0294\u0001\u0000\u0000"+
		"\u0000\u0292\u0290\u0001\u0000\u0000\u0000\u0292\u0293\u0001\u0000\u0000"+
		"\u0000\u0293\u0296\u0001\u0000\u0000\u0000\u0294\u0292\u0001\u0000\u0000"+
		"\u0000\u0295\u0297\u0003H$\u0000\u0296\u0295\u0001\u0000\u0000\u0000\u0296"+
		"\u0297\u0001\u0000\u0000\u0000\u0297\u0298\u0001\u0000\u0000\u0000\u0298"+
		"\u0299\u0003F#\u0000\u0299S\u0001\u0000\u0000\u0000\u029a\u029f\u0003"+
		"V+\u0000\u029b\u029c\u0005J\u0000\u0000\u029c\u029e\u0003V+\u0000\u029d"+
		"\u029b\u0001\u0000\u0000\u0000\u029e\u02a1\u0001\u0000\u0000\u0000\u029f"+
		"\u029d\u0001\u0000\u0000\u0000\u029f\u02a0\u0001\u0000\u0000\u0000\u02a0"+
		"U\u0001\u0000\u0000\u0000\u02a1\u029f\u0001\u0000\u0000\u0000\u02a2\u02a6"+
		"\u0003\u011e\u008f\u0000\u02a3\u02a5\u0003\u0102\u0081\u0000\u02a4\u02a3"+
		"\u0001\u0000\u0000\u0000\u02a5\u02a8\u0001\u0000\u0000\u0000\u02a6\u02a4"+
		"\u0001\u0000\u0000\u0000\u02a6\u02a7\u0001\u0000\u0000\u0000\u02a7\u02ab"+
		"\u0001\u0000\u0000\u0000\u02a8\u02a6\u0001\u0000\u0000\u0000\u02a9\u02aa"+
		"\u0005P\u0000\u0000\u02aa\u02ac\u0003Z-\u0000\u02ab\u02a9\u0001\u0000"+
		"\u0000\u0000\u02ab\u02ac\u0001\u0000\u0000\u0000\u02acW\u0001\u0000\u0000"+
		"\u0000\u02ad\u02b4\u0005N\u0000\u0000\u02ae\u02b1\u0003n7\u0000\u02af"+
		"\u02b0\u0005;\u0000\u0000\u02b0\u02b2\u0005N\u0000\u0000\u02b1\u02af\u0001"+
		"\u0000\u0000\u0000\u02b1\u02b2\u0001\u0000\u0000\u0000\u02b2\u02b4\u0001"+
		"\u0000\u0000\u0000\u02b3\u02ad\u0001\u0000\u0000\u0000\u02b3\u02ae\u0001"+
		"\u0000\u0000\u0000\u02b4\u02b8\u0001\u0000\u0000\u0000\u02b5\u02b7\u0003"+
		"\u0102\u0081\u0000\u02b6\u02b5\u0001\u0000\u0000\u0000\u02b7\u02ba\u0001"+
		"\u0000\u0000\u0000\u02b8\u02b6\u0001\u0000\u0000\u0000\u02b8\u02b9\u0001"+
		"\u0000\u0000\u0000\u02b9Y\u0001\u0000\u0000\u0000\u02ba\u02b8\u0001\u0000"+
		"\u0000\u0000\u02bb\u02be\u0003\\.\u0000\u02bc\u02be\u0003\u00d8l\u0000"+
		"\u02bd\u02bb\u0001\u0000\u0000\u0000\u02bd\u02bc\u0001\u0000\u0000\u0000"+
		"\u02be[\u0001\u0000\u0000\u0000\u02bf\u02c8\u0005H\u0000\u0000\u02c0\u02c5"+
		"\u0003Z-\u0000\u02c1\u02c2\u0005J\u0000\u0000\u02c2\u02c4\u0003Z-\u0000"+
		"\u02c3\u02c1\u0001\u0000\u0000\u0000\u02c4\u02c7\u0001\u0000\u0000\u0000"+
		"\u02c5\u02c3\u0001\u0000\u0000\u0000\u02c5\u02c6\u0001\u0000\u0000\u0000"+
		"\u02c6\u02c9\u0001\u0000\u0000\u0000\u02c7\u02c5\u0001\u0000\u0000\u0000"+
		"\u02c8\u02c0\u0001\u0000\u0000\u0000\u02c8\u02c9\u0001\u0000\u0000\u0000"+
		"\u02c9\u02cb\u0001\u0000\u0000\u0000\u02ca\u02cc\u0005J\u0000\u0000\u02cb"+
		"\u02ca\u0001\u0000\u0000\u0000\u02cb\u02cc\u0001\u0000\u0000\u0000\u02cc"+
		"\u02cd\u0001\u0000\u0000\u0000\u02cd\u02ce\u0005I\u0000\u0000\u02ce]\u0001"+
		"\u0000\u0000\u0000\u02cf\u02d0\u0003z=\u0000\u02d0\u02d2\u0003\u011e\u008f"+
		"\u0000\u02d1\u02d3\u0003\u0106\u0083\u0000\u02d2\u02d1\u0001\u0000\u0000"+
		"\u0000\u02d2\u02d3\u0001\u0000\u0000\u0000\u02d3\u02d7\u0001\u0000\u0000"+
		"\u0000\u02d4\u02d6\u0003`0\u0000\u02d5\u02d4\u0001\u0000\u0000\u0000\u02d6"+
		"\u02d9\u0001\u0000\u0000\u0000\u02d7\u02d5\u0001\u0000\u0000\u0000\u02d7"+
		"\u02d8\u0001\u0000\u0000\u0000\u02d8_\u0001\u0000\u0000\u0000\u02d9\u02d7"+
		"\u0001\u0000\u0000\u0000\u02da\u02db\u0005;\u0000\u0000\u02db\u02dc\u0003"+
		"z=\u0000\u02dc\u02de\u0003\u011e\u008f\u0000\u02dd\u02df\u0003\u0106\u0083"+
		"\u0000\u02de\u02dd\u0001\u0000\u0000\u0000\u02de\u02df\u0001\u0000\u0000"+
		"\u0000\u02dfa\u0001\u0000\u0000\u0000\u02e0\u02eb\u0003\u00fe\u007f\u0000"+
		"\u02e1\u02e2\u0003z=\u0000\u02e2\u02e8\u0005m\u0000\u0000\u02e3\u02e6"+
		"\u0005\u0012\u0000\u0000\u02e4\u02e6\u0005O\u0000\u0000\u02e5\u02e3\u0001"+
		"\u0000\u0000\u0000\u02e5\u02e4\u0001\u0000\u0000\u0000\u02e6\u02e7\u0001"+
		"\u0000\u0000\u0000\u02e7\u02e9\u0003\u00fe\u007f\u0000\u02e8\u02e5\u0001"+
		"\u0000\u0000\u0000\u02e8\u02e9\u0001\u0000\u0000\u0000\u02e9\u02eb\u0001"+
		"\u0000\u0000\u0000\u02ea\u02e0\u0001\u0000\u0000\u0000\u02ea\u02e1\u0001"+
		"\u0000\u0000\u0000\u02ebc\u0001\u0000\u0000\u0000\u02ec\u02ed\u0003z="+
		"\u0000\u02ed\u02f4\u0003n7\u0000\u02ee\u02ef\u0005J\u0000\u0000\u02ef"+
		"\u02f0\u0003z=\u0000\u02f0\u02f1\u0003n7\u0000\u02f1\u02f3\u0001\u0000"+
		"\u0000\u0000\u02f2\u02ee\u0001\u0000\u0000\u0000\u02f3\u02f6\u0001\u0000"+
		"\u0000\u0000\u02f4\u02f2\u0001\u0000\u0000\u0000\u02f4\u02f5\u0001\u0000"+
		"\u0000\u0000\u02f5e\u0001\u0000\u0000\u0000\u02f6\u02f4\u0001\u0000\u0000"+
		"\u0000\u02f7\u02f9\u0005L\u0000\u0000\u02f8\u02fa\u0003h4\u0000\u02f9"+
		"\u02f8\u0001\u0000\u0000\u0000\u02f9\u02fa\u0001\u0000\u0000\u0000\u02fa"+
		"\u02fb\u0001\u0000\u0000\u0000\u02fb\u02fc\u0005M\u0000\u0000\u02fcg\u0001"+
		"\u0000\u0000\u0000\u02fd\u0302\u0003j5\u0000\u02fe\u02ff\u0005J\u0000"+
		"\u0000\u02ff\u0301\u0003j5\u0000\u0300\u02fe\u0001\u0000\u0000\u0000\u0301"+
		"\u0304\u0001\u0000\u0000\u0000\u0302\u0300\u0001\u0000\u0000\u0000\u0302"+
		"\u0303\u0001\u0000\u0000\u0000\u0303\u0307\u0001\u0000\u0000\u0000\u0304"+
		"\u0302\u0001\u0000\u0000\u0000\u0305\u0306\u0005J\u0000\u0000\u0306\u0308"+
		"\u0003l6\u0000\u0307\u0305\u0001\u0000\u0000\u0000\u0307\u0308\u0001\u0000"+
		"\u0000\u0000\u0308\u030b\u0001\u0000\u0000\u0000\u0309\u030b\u0003l6\u0000"+
		"\u030a\u02fd\u0001\u0000\u0000\u0000\u030a\u0309\u0001\u0000\u0000\u0000"+
		"\u030bi\u0001\u0000\u0000\u0000\u030c\u030e\u0003\f\u0006\u0000\u030d"+
		"\u030c\u0001\u0000\u0000\u0000\u030e\u0311\u0001\u0000\u0000\u0000\u030f"+
		"\u030d\u0001\u0000\u0000\u0000\u030f\u0310\u0001\u0000\u0000\u0000\u0310"+
		"\u0312\u0001\u0000\u0000\u0000\u0311\u030f\u0001\u0000\u0000\u0000\u0312"+
		"\u0313\u0003\u00fe\u007f\u0000\u0313\u0314\u0003X,\u0000\u0314k\u0001"+
		"\u0000\u0000\u0000\u0315\u0317\u0003\f\u0006\u0000\u0316\u0315\u0001\u0000"+
		"\u0000\u0000\u0317\u031a\u0001\u0000\u0000\u0000\u0318\u0316\u0001\u0000"+
		"\u0000\u0000\u0318\u0319\u0001\u0000\u0000\u0000\u0319\u031b\u0001\u0000"+
		"\u0000\u0000\u031a\u0318\u0001\u0000\u0000\u0000\u031b\u031c\u0003\u00fe"+
		"\u007f\u0000\u031c\u031d\u0003z=\u0000\u031d\u031e\u0005\u00ab\u0000\u0000"+
		"\u031e\u031f\u0003X,\u0000\u031fm\u0001\u0000\u0000\u0000\u0320\u0324"+
		"\u0003\u011e\u008f\u0000\u0321\u0323\u0003p8\u0000\u0322\u0321\u0001\u0000"+
		"\u0000\u0000\u0323\u0326\u0001\u0000\u0000\u0000\u0324\u0322\u0001\u0000"+
		"\u0000\u0000\u0324\u0325\u0001\u0000\u0000\u0000\u0325o\u0001\u0000\u0000"+
		"\u0000\u0326\u0324\u0001\u0000\u0000\u0000\u0327\u0328\u0005;\u0000\u0000"+
		"\u0328\u0329\u0003z=\u0000\u0329\u032a\u0003\u011e\u008f\u0000\u032aq"+
		"\u0001\u0000\u0000\u0000\u032b\u0334\u0003t:\u0000\u032c\u0334\u0003v"+
		";\u0000\u032d\u0334\u0003x<\u0000\u032e\u0334\u0005\u008a\u0000\u0000"+
		"\u032f\u0334\u0005\u008b\u0000\u0000\u0330\u0334\u0005\u0085\u0000\u0000"+
		"\u0331\u0334\u0005\u0086\u0000\u0000\u0332\u0334\u0005\u0087\u0000\u0000"+
		"\u0333\u032b\u0001\u0000\u0000\u0000\u0333\u032c\u0001\u0000\u0000\u0000"+
		"\u0333\u032d\u0001\u0000\u0000\u0000\u0333\u032e\u0001\u0000\u0000\u0000"+
		"\u0333\u032f\u0001\u0000\u0000\u0000\u0333\u0330\u0001\u0000\u0000\u0000"+
		"\u0333\u0331\u0001\u0000\u0000\u0000\u0333\u0332\u0001\u0000\u0000\u0000"+
		"\u0334s\u0001\u0000\u0000\u0000\u0335\u0336\u0007\u0000\u0000\u0000\u0336"+
		"u\u0001\u0000\u0000\u0000\u0337\u0338\u0007\u0001\u0000\u0000\u0338w\u0001"+
		"\u0000\u0000\u0000\u0339\u033a\u0005\u00cc\u0000\u0000\u033a\u033b\u0005"+
		"\u00cd\u0000\u0000\u033b\u033c\u0005\u00ce\u0000\u0000\u033cy\u0001\u0000"+
		"\u0000\u0000\u033d\u033f\u0003|>\u0000\u033e\u033d\u0001\u0000\u0000\u0000"+
		"\u033f\u0342\u0001\u0000\u0000\u0000\u0340\u033e\u0001\u0000\u0000\u0000"+
		"\u0340\u0341\u0001\u0000\u0000\u0000\u0341{\u0001\u0000\u0000\u0000\u0342"+
		"\u0340\u0001\u0000\u0000\u0000\u0343\u0344\u0005\u00aa\u0000\u0000\u0344"+
		"\u034b\u0003n7\u0000\u0345\u0348\u0005L\u0000\u0000\u0346\u0349\u0003"+
		"~?\u0000\u0347\u0349\u0003\u0082A\u0000\u0348\u0346\u0001\u0000\u0000"+
		"\u0000\u0348\u0347\u0001\u0000\u0000\u0000\u0348\u0349\u0001\u0000\u0000"+
		"\u0000\u0349\u034a\u0001\u0000\u0000\u0000\u034a\u034c\u0005M\u0000\u0000"+
		"\u034b\u0345\u0001\u0000\u0000\u0000\u034b\u034c\u0001\u0000\u0000\u0000"+
		"\u034c}\u0001\u0000\u0000\u0000\u034d\u0352\u0003\u0080@\u0000\u034e\u034f"+
		"\u0005J\u0000\u0000\u034f\u0351\u0003\u0080@\u0000\u0350\u034e\u0001\u0000"+
		"\u0000\u0000\u0351\u0354\u0001\u0000\u0000\u0000\u0352\u0350\u0001\u0000"+
		"\u0000\u0000\u0352\u0353\u0001\u0000\u0000\u0000\u0353\u007f\u0001\u0000"+
		"\u0000\u0000\u0354\u0352\u0001\u0000\u0000\u0000\u0355\u0356\u0003\u011e"+
		"\u008f\u0000\u0356\u0357\u0005P\u0000\u0000\u0357\u0358\u0003\u0082A\u0000"+
		"\u0358\u0081\u0001\u0000\u0000\u0000\u0359\u035d\u0003\u00d8l\u0000\u035a"+
		"\u035d\u0003|>\u0000\u035b\u035d\u0003\u0084B\u0000\u035c\u0359\u0001"+
		"\u0000\u0000\u0000\u035c\u035a\u0001\u0000\u0000\u0000\u035c\u035b\u0001"+
		"\u0000\u0000\u0000\u035d\u0083\u0001\u0000\u0000\u0000\u035e\u0367\u0005"+
		"H\u0000\u0000\u035f\u0364\u0003\u0082A\u0000\u0360\u0361\u0005J\u0000"+
		"\u0000\u0361\u0363\u0003\u0082A\u0000\u0362\u0360\u0001\u0000\u0000\u0000"+
		"\u0363\u0366\u0001\u0000\u0000\u0000\u0364\u0362\u0001\u0000\u0000\u0000"+
		"\u0364\u0365\u0001\u0000\u0000\u0000\u0365\u0368\u0001\u0000\u0000\u0000"+
		"\u0366\u0364\u0001\u0000\u0000\u0000\u0367\u035f\u0001\u0000\u0000\u0000"+
		"\u0367\u0368\u0001\u0000\u0000\u0000\u0368\u036a\u0001\u0000\u0000\u0000"+
		"\u0369\u036b\u0005J\u0000\u0000\u036a\u0369\u0001\u0000\u0000\u0000\u036a"+
		"\u036b\u0001\u0000\u0000\u0000\u036b\u036c\u0001\u0000\u0000\u0000\u036c"+
		"\u036d\u0005I\u0000\u0000\u036d\u0085\u0001\u0000\u0000\u0000\u036e\u036f"+
		"\u0005\u00aa\u0000\u0000\u036f\u0370\u0005G\u0000\u0000\u0370\u0371\u0003"+
		"\u011e\u008f\u0000\u0371\u0372\u0003\u0088D\u0000\u0372\u0087\u0001\u0000"+
		"\u0000\u0000\u0373\u0377\u0005H\u0000\u0000\u0374\u0376\u0003\u008aE\u0000"+
		"\u0375\u0374\u0001\u0000\u0000\u0000\u0376\u0379\u0001\u0000\u0000\u0000"+
		"\u0377\u0375\u0001\u0000\u0000\u0000\u0377\u0378\u0001\u0000\u0000\u0000"+
		"\u0378\u037a\u0001\u0000\u0000\u0000\u0379\u0377\u0001\u0000\u0000\u0000"+
		"\u037a\u037b\u0005I\u0000\u0000\u037b\u0089\u0001\u0000\u0000\u0000\u037c"+
		"\u037e\u0003\n\u0005\u0000\u037d\u037c\u0001\u0000\u0000\u0000\u037e\u0381"+
		"\u0001\u0000\u0000\u0000\u037f\u037d\u0001\u0000\u0000\u0000\u037f\u0380"+
		"\u0001\u0000\u0000\u0000\u0380\u0382\u0001\u0000\u0000\u0000\u0381\u037f"+
		"\u0001\u0000\u0000\u0000\u0382\u0385\u0003\u008cF\u0000\u0383\u0385\u0005"+
		"-\u0000\u0000\u0384\u037f\u0001\u0000\u0000\u0000\u0384\u0383\u0001\u0000"+
		"\u0000\u0000\u0385\u008b\u0001\u0000\u0000\u0000\u0386\u0389\u0003\u00fe"+
		"\u007f\u0000\u0387\u038a\u0003\u008eG\u0000\u0388\u038a\u0003\u0090H\u0000"+
		"\u0389\u0387\u0001\u0000\u0000\u0000\u0389\u0388\u0001\u0000\u0000\u0000"+
		"\u038a\u038b\u0001\u0000\u0000\u0000\u038b\u038c\u0005-\u0000\u0000\u038c"+
		"\u03a5\u0001\u0000\u0000\u0000\u038d\u038e\u0003\u00fe\u007f\u0000\u038e"+
		"\u038f\u0005-\u0000\u0000\u038f\u03a5\u0001\u0000\u0000\u0000\u0390\u0392"+
		"\u0003\u000e\u0007\u0000\u0391\u0393\u0005-\u0000\u0000\u0392\u0391\u0001"+
		"\u0000\u0000\u0000\u0392\u0393\u0001\u0000\u0000\u0000\u0393\u03a5\u0001"+
		"\u0000\u0000\u0000\u0394\u0396\u0003\u0010\b\u0000\u0395\u0397\u0005-"+
		"\u0000\u0000\u0396\u0395\u0001\u0000\u0000\u0000\u0396\u0397\u0001\u0000"+
		"\u0000\u0000\u0397\u03a5\u0001\u0000\u0000\u0000\u0398\u039a\u00038\u001c"+
		"\u0000\u0399\u039b\u0005-\u0000\u0000\u039a\u0399\u0001\u0000\u0000\u0000"+
		"\u039a\u039b\u0001\u0000\u0000\u0000\u039b\u03a5\u0001\u0000\u0000\u0000"+
		"\u039c\u039e\u0003.\u0017\u0000\u039d\u039f\u0005-\u0000\u0000\u039e\u039d"+
		"\u0001\u0000\u0000\u0000\u039e\u039f\u0001\u0000\u0000\u0000\u039f\u03a5"+
		"\u0001\u0000\u0000\u0000\u03a0\u03a2\u0003\u0086C\u0000\u03a1\u03a3\u0005"+
		"-\u0000\u0000\u03a2\u03a1\u0001\u0000\u0000\u0000\u03a2\u03a3\u0001\u0000"+
		"\u0000\u0000\u03a3\u03a5\u0001\u0000\u0000\u0000\u03a4\u0386\u0001\u0000"+
		"\u0000\u0000\u03a4\u038d\u0001\u0000\u0000\u0000\u03a4\u0390\u0001\u0000"+
		"\u0000\u0000\u03a4\u0394\u0001\u0000\u0000\u0000\u03a4\u0398\u0001\u0000"+
		"\u0000\u0000\u03a4\u039c\u0001\u0000\u0000\u0000\u03a4\u03a0\u0001\u0000"+
		"\u0000\u0000\u03a5\u008d\u0001\u0000\u0000\u0000\u03a6\u03a7\u0003\u011e"+
		"\u008f\u0000\u03a7\u03a8\u0005L\u0000\u0000\u03a8\u03ac\u0005M\u0000\u0000"+
		"\u03a9\u03ab\u0003\u0102\u0081\u0000\u03aa\u03a9\u0001\u0000\u0000\u0000"+
		"\u03ab\u03ae\u0001\u0000\u0000\u0000\u03ac\u03aa\u0001\u0000\u0000\u0000"+
		"\u03ac\u03ad\u0001\u0000\u0000\u0000\u03ad\u03b0\u0001\u0000\u0000\u0000"+
		"\u03ae\u03ac\u0001\u0000\u0000\u0000\u03af\u03b1\u0003\u0092I\u0000\u03b0"+
		"\u03af\u0001\u0000\u0000\u0000\u03b0\u03b1\u0001\u0000\u0000\u0000\u03b1"+
		"\u008f\u0001\u0000\u0000\u0000\u03b2\u03b3\u0003T*\u0000\u03b3\u0091\u0001"+
		"\u0000\u0000\u0000\u03b4\u03b5\u0005^\u0000\u0000\u03b5\u03b6\u0003\u0082"+
		"A\u0000\u03b6\u0093\u0001\u0000\u0000\u0000\u03b7\u03b9\u0005H\u0000\u0000"+
		"\u03b8\u03ba\u0003\u0096K\u0000\u03b9\u03b8\u0001\u0000\u0000\u0000\u03b9"+
		"\u03ba\u0001\u0000\u0000\u0000\u03ba\u03be\u0001\u0000\u0000\u0000\u03bb"+
		"\u03bd\u0003\u009aM\u0000\u03bc\u03bb\u0001\u0000\u0000\u0000\u03bd\u03c0"+
		"\u0001\u0000\u0000\u0000\u03be\u03bc\u0001\u0000\u0000\u0000\u03be\u03bf"+
		"\u0001\u0000\u0000\u0000\u03bf\u03c1\u0001\u0000\u0000\u0000\u03c0\u03be"+
		"\u0001\u0000\u0000\u0000\u03c1\u03c2\u0005I\u0000\u0000\u03c2\u0095\u0001"+
		"\u0000\u0000\u0000\u03c3\u03c5\u0003\u0106\u0083\u0000\u03c4\u03c3\u0001"+
		"\u0000\u0000\u0000\u03c4\u03c5\u0001\u0000\u0000\u0000\u03c5\u03c6\u0001"+
		"\u0000\u0000\u0000\u03c6\u03c7\u0007\u0002\u0000\u0000\u03c7\u03c8\u0003"+
		"\u010a\u0085\u0000\u03c8\u03c9\u0005-\u0000\u0000\u03c9\u03d4\u0001\u0000"+
		"\u0000\u0000\u03ca\u03cb\u0003\u00dam\u0000\u03cb\u03cd\u0005;\u0000\u0000"+
		"\u03cc\u03ce\u0003\u0106\u0083\u0000\u03cd\u03cc\u0001\u0000\u0000\u0000"+
		"\u03cd\u03ce\u0001\u0000\u0000\u0000\u03ce\u03cf\u0001\u0000\u0000\u0000"+
		"\u03cf\u03d0\u0005O\u0000\u0000\u03d0\u03d1\u0003\u010a\u0085\u0000\u03d1"+
		"\u03d2\u0005-\u0000\u0000\u03d2\u03d4\u0001\u0000\u0000\u0000\u03d3\u03c4"+
		"\u0001\u0000\u0000\u0000\u03d3\u03ca\u0001\u0000\u0000\u0000\u03d4\u0097"+
		"\u0001\u0000\u0000\u0000\u03d5\u03d9\u0005H\u0000\u0000\u03d6\u03d8\u0003"+
		"\u009aM\u0000\u03d7\u03d6\u0001\u0000\u0000\u0000\u03d8\u03db\u0001\u0000"+
		"\u0000\u0000\u03d9\u03d7\u0001\u0000\u0000\u0000\u03d9\u03da\u0001\u0000"+
		"\u0000\u0000\u03da\u03dc\u0001\u0000\u0000\u0000\u03db\u03d9\u0001\u0000"+
		"\u0000\u0000\u03dc\u03dd\u0005I\u0000\u0000\u03dd\u0099\u0001\u0000\u0000"+
		"\u0000\u03de\u03df\u0004M\u0000\u0000\u03df\u03e0\u0003\u009cN\u0000\u03e0"+
		"\u03e1\u0005-\u0000\u0000\u03e1\u03e5\u0001\u0000\u0000\u0000\u03e2\u03e5"+
		"\u0003\u00a0P\u0000\u03e3\u03e5\u0003\u009eO\u0000\u03e4\u03de\u0001\u0000"+
		"\u0000\u0000\u03e4\u03e2\u0001\u0000\u0000\u0000\u03e4\u03e3\u0001\u0000"+
		"\u0000\u0000\u03e5\u009b\u0001\u0000\u0000\u0000\u03e6\u03e8\u0003\n\u0005"+
		"\u0000\u03e7\u03e6\u0001\u0000\u0000\u0000\u03e8\u03eb\u0001\u0000\u0000"+
		"\u0000\u03e9\u03e7\u0001\u0000\u0000\u0000\u03e9\u03ea\u0001\u0000\u0000"+
		"\u0000\u03ea\u03ec\u0001\u0000\u0000\u0000\u03eb\u03e9\u0001\u0000\u0000"+
		"\u0000\u03ec\u03ed\u0003\u00fe\u007f\u0000\u03ed\u03ee\u0003T*\u0000\u03ee"+
		"\u009d\u0001\u0000\u0000\u0000\u03ef\u03f1\u0003\n\u0005\u0000\u03f0\u03ef"+
		"\u0001\u0000\u0000\u0000\u03f1\u03f4\u0001\u0000\u0000\u0000\u03f2\u03f0"+
		"\u0001\u0000\u0000\u0000\u03f2\u03f3\u0001\u0000\u0000\u0000\u03f3\u03f9"+
		"\u0001\u0000\u0000\u0000\u03f4\u03f2\u0001\u0000\u0000\u0000\u03f5\u03fa"+
		"\u0003\u000e\u0007\u0000\u03f6\u03fa\u0003.\u0017\u0000\u03f7\u03fa\u0003"+
		"8\u001c\u0000\u03f8\u03fa\u0003\u0010\b\u0000\u03f9\u03f5\u0001\u0000"+
		"\u0000\u0000\u03f9\u03f6\u0001\u0000\u0000\u0000\u03f9\u03f7\u0001\u0000"+
		"\u0000\u0000\u03f9\u03f8\u0001\u0000\u0000\u0000\u03fa\u03fd\u0001\u0000"+
		"\u0000\u0000\u03fb\u03fd\u0005-\u0000\u0000\u03fc\u03f2\u0001\u0000\u0000"+
		"\u0000\u03fc\u03fb\u0001\u0000\u0000\u0000\u03fd\u009f\u0001\u0000\u0000"+
		"\u0000\u03fe\u0459\u0003\u0098L\u0000\u03ff\u0400\u0005\u0097\u0000\u0000"+
		"\u0400\u0403\u0003\u00d8l\u0000\u0401\u0402\u0005R\u0000\u0000\u0402\u0404"+
		"\u0003\u00d8l\u0000\u0403\u0401\u0001\u0000\u0000\u0000\u0403\u0404\u0001"+
		"\u0000\u0000\u0000\u0404\u0405\u0001\u0000\u0000\u0000\u0405\u0406\u0005"+
		"-\u0000\u0000\u0406\u0459\u0001\u0000\u0000\u0000\u0407\u0408\u0005S\u0000"+
		"\u0000\u0408\u0409\u0003\u00d4j\u0000\u0409\u040b\u0003\u00a0P\u0000\u040a"+
		"\u040c\u0003\u00aeW\u0000\u040b\u040a\u0001\u0000\u0000\u0000\u040b\u040c"+
		"\u0001\u0000\u0000\u0000\u040c\u0459\u0001\u0000\u0000\u0000\u040d\u040e"+
		"\u0005[\u0000\u0000\u040e\u040f\u0003\u00ccf\u0000\u040f\u0410\u0003\u00a0"+
		"P\u0000\u0410\u0459\u0001\u0000\u0000\u0000\u0411\u0412\u0005T\u0000\u0000"+
		"\u0412\u0413\u0003\u00d4j\u0000\u0413\u0414\u0003\u00a0P\u0000\u0414\u0459"+
		"\u0001\u0000\u0000\u0000\u0415\u0416\u0005U\u0000\u0000\u0416\u0417\u0003"+
		"\u00a0P\u0000\u0417\u0418\u0005T\u0000\u0000\u0418\u0419\u0003\u00d4j"+
		"\u0000\u0419\u041a\u0005-\u0000\u0000\u041a\u0459\u0001\u0000\u0000\u0000"+
		"\u041b\u041c\u0005_\u0000\u0000\u041c\u0426\u0003\u0098L\u0000\u041d\u041f"+
		"\u0003\u00b0X\u0000\u041e\u041d\u0001\u0000\u0000\u0000\u041f\u0420\u0001"+
		"\u0000\u0000\u0000\u0420\u041e\u0001\u0000\u0000\u0000\u0420\u0421\u0001"+
		"\u0000\u0000\u0000\u0421\u0423\u0001\u0000\u0000\u0000\u0422\u0424\u0003"+
		"\u00b6[\u0000\u0423\u0422\u0001\u0000\u0000\u0000\u0423\u0424\u0001\u0000"+
		"\u0000\u0000\u0424\u0427\u0001\u0000\u0000\u0000\u0425\u0427\u0003\u00b6"+
		"[\u0000\u0426\u041e\u0001\u0000\u0000\u0000\u0426\u0425\u0001\u0000\u0000"+
		"\u0000\u0427\u0459\u0001\u0000\u0000\u0000\u0428\u0429\u0005_\u0000\u0000"+
		"\u0429\u042a\u0003\u00b8\\\u0000\u042a\u042e\u0003\u0098L\u0000\u042b"+
		"\u042d\u0003\u00b0X\u0000\u042c\u042b\u0001\u0000\u0000\u0000\u042d\u0430"+
		"\u0001\u0000\u0000\u0000\u042e\u042c\u0001\u0000\u0000\u0000\u042e\u042f"+
		"\u0001\u0000\u0000\u0000\u042f\u0432\u0001\u0000\u0000\u0000\u0430\u042e"+
		"\u0001\u0000\u0000\u0000\u0431\u0433\u0003\u00b6[\u0000\u0432\u0431\u0001"+
		"\u0000\u0000\u0000\u0432\u0433\u0001\u0000\u0000\u0000\u0433\u0459\u0001"+
		"\u0000\u0000\u0000\u0434\u0435\u0005\u00cf\u0000\u0000\u0435\u0436\u0003"+
		"\u00d8l\u0000\u0436\u0437\u0005-\u0000\u0000\u0437\u0459\u0001\u0000\u0000"+
		"\u0000\u0438\u0459\u0003\u00a2Q\u0000\u0439\u043a\u0005C\u0000\u0000\u043a"+
		"\u043b\u0003\u00d4j\u0000\u043b\u043c\u0003\u0098L\u0000\u043c\u0459\u0001"+
		"\u0000\u0000\u0000\u043d\u043f\u0005X\u0000\u0000\u043e\u0440\u0003\u00d8"+
		"l\u0000\u043f\u043e\u0001\u0000\u0000\u0000\u043f\u0440\u0001\u0000\u0000"+
		"\u0000\u0440\u0441\u0001\u0000\u0000\u0000\u0441\u0459\u0005-\u0000\u0000"+
		"\u0442\u0443\u0005Z\u0000\u0000\u0443\u0444\u0003\u00d8l\u0000\u0444\u0445"+
		"\u0005-\u0000\u0000\u0445\u0459\u0001\u0000\u0000\u0000\u0446\u0448\u0005"+
		"V\u0000\u0000\u0447\u0449\u0003\u011e\u008f\u0000\u0448\u0447\u0001\u0000"+
		"\u0000\u0000\u0448\u0449\u0001\u0000\u0000\u0000\u0449\u044a\u0001\u0000"+
		"\u0000\u0000\u044a\u0459\u0005-\u0000\u0000\u044b\u044d\u0005W\u0000\u0000"+
		"\u044c\u044e\u0003\u011e\u008f\u0000\u044d\u044c\u0001\u0000\u0000\u0000"+
		"\u044d\u044e\u0001\u0000\u0000\u0000\u044e\u044f\u0001\u0000\u0000\u0000"+
		"\u044f\u0459\u0005-\u0000\u0000\u0450\u0459\u0005-\u0000\u0000\u0451\u0452"+
		"\u0003\u00d8l\u0000\u0452\u0453\u0005-\u0000\u0000\u0453\u0459\u0001\u0000"+
		"\u0000\u0000\u0454\u0455\u0003\u011e\u008f\u0000\u0455\u0456\u0005R\u0000"+
		"\u0000\u0456\u0457\u0003\u00a0P\u0000\u0457\u0459\u0001\u0000\u0000\u0000"+
		"\u0458\u03fe\u0001\u0000\u0000\u0000\u0458\u03ff\u0001\u0000\u0000\u0000"+
		"\u0458\u0407\u0001\u0000\u0000\u0000\u0458\u040d\u0001\u0000\u0000\u0000"+
		"\u0458\u0411\u0001\u0000\u0000\u0000\u0458\u0415\u0001\u0000\u0000\u0000"+
		"\u0458\u041b\u0001\u0000\u0000\u0000\u0458\u0428\u0001\u0000\u0000\u0000"+
		"\u0458\u0434\u0001\u0000\u0000\u0000\u0458\u0438\u0001\u0000\u0000\u0000"+
		"\u0458\u0439\u0001\u0000\u0000\u0000\u0458\u043d\u0001\u0000\u0000\u0000"+
		"\u0458\u0442\u0001\u0000\u0000\u0000\u0458\u0446\u0001\u0000\u0000\u0000"+
		"\u0458\u044b\u0001\u0000\u0000\u0000\u0458\u0450\u0001\u0000\u0000\u0000"+
		"\u0458\u0451\u0001\u0000\u0000\u0000\u0458\u0454\u0001\u0000\u0000\u0000"+
		"\u0459\u00a1\u0001\u0000\u0000\u0000\u045a\u045b\u0005Y\u0000\u0000\u045b"+
		"\u045c\u0003\u00d4j\u0000\u045c\u045d\u0005H\u0000\u0000\u045d\u045e\u0006"+
		"Q\uffff\uffff\u0000\u045e\u045f\u0003\u00a4R\u0000\u045f\u0460\u0006Q"+
		"\uffff\uffff\u0000\u0460\u0461\u0005I\u0000\u0000\u0461\u00a3\u0001\u0000"+
		"\u0000\u0000\u0462\u0464\u0003\u00a6S\u0000\u0463\u0462\u0001\u0000\u0000"+
		"\u0000\u0464\u0465\u0001\u0000\u0000\u0000\u0465\u0463\u0001\u0000\u0000"+
		"\u0000\u0465\u0466\u0001\u0000\u0000\u0000\u0466\u0474\u0001\u0000\u0000"+
		"\u0000\u0467\u0469\u0003\u00c4b\u0000\u0468\u0467\u0001\u0000\u0000\u0000"+
		"\u0469\u046c\u0001\u0000\u0000\u0000\u046a\u0468\u0001\u0000\u0000\u0000"+
		"\u046a\u046b\u0001\u0000\u0000\u0000\u046b\u0470\u0001\u0000\u0000\u0000"+
		"\u046c\u046a\u0001\u0000\u0000\u0000\u046d\u046f\u0003\u00c6c\u0000\u046e"+
		"\u046d\u0001\u0000\u0000\u0000\u046f\u0472\u0001\u0000\u0000\u0000\u0470"+
		"\u046e\u0001\u0000\u0000\u0000\u0470\u0471\u0001\u0000\u0000\u0000\u0471"+
		"\u0474\u0001\u0000\u0000\u0000\u0472\u0470\u0001\u0000\u0000\u0000\u0473"+
		"\u0463\u0001\u0000\u0000\u0000\u0473\u046a\u0001\u0000\u0000\u0000\u0474"+
		"\u00a5\u0001\u0000\u0000\u0000\u0475\u0479\u0003\u00a8T\u0000\u0476\u0479"+
		"\u0003\u00aaU\u0000\u0477\u0479\u0003\u00acV\u0000\u0478\u0475\u0001\u0000"+
		"\u0000\u0000\u0478\u0476\u0001\u0000\u0000\u0000\u0478\u0477\u0001\u0000"+
		"\u0000\u0000\u0479\u00a7\u0001\u0000\u0000\u0000\u047a\u047b\u0003\u00c6"+
		"c\u0000\u047b\u047c\u0005\u00b5\u0000\u0000\u047c\u047d\u0003\u00d8l\u0000"+
		"\u047d\u047e\u0005-\u0000\u0000\u047e\u00a9\u0001\u0000\u0000\u0000\u047f"+
		"\u0480\u0003\u00c6c\u0000\u0480\u0481\u0005\u00b5\u0000\u0000\u0481\u0482"+
		"\u0003\u0098L\u0000\u0482\u00ab\u0001\u0000\u0000\u0000\u0483\u0484\u0003"+
		"\u00c6c\u0000\u0484\u0485\u0005\u00b5\u0000\u0000\u0485\u0486\u0005Z\u0000"+
		"\u0000\u0486\u0487\u0003\u00d8l\u0000\u0487\u0488\u0005-\u0000\u0000\u0488"+
		"\u00ad\u0001\u0000\u0000\u0000\u0489\u048a\u0005\\\u0000\u0000\u048a\u048b"+
		"\u0003\u00a0P\u0000\u048b\u00af\u0001\u0000\u0000\u0000\u048c\u048d\u0005"+
		"`\u0000\u0000\u048d\u048e\u0005L\u0000\u0000\u048e\u048f\u0003\u00b2Y"+
		"\u0000\u048f\u0490\u0005M\u0000\u0000\u0490\u0491\u0003\u0098L\u0000\u0491"+
		"\u00b1\u0001\u0000\u0000\u0000\u0492\u0494\u0003\f\u0006\u0000\u0493\u0492"+
		"\u0001\u0000\u0000\u0000\u0494\u0497\u0001\u0000\u0000\u0000\u0495\u0493"+
		"\u0001\u0000\u0000\u0000\u0495\u0496\u0001\u0000\u0000\u0000\u0496\u0498"+
		"\u0001\u0000\u0000\u0000\u0497\u0495\u0001\u0000\u0000\u0000\u0498\u0499"+
		"\u0003\u00b4Z\u0000\u0499\u049a\u0003\u011e\u008f\u0000\u049a\u00b3\u0001"+
		"\u0000\u0000\u0000\u049b\u04a0\u0003^/\u0000\u049c\u049d\u0005p\u0000"+
		"\u0000\u049d\u049f\u0003^/\u0000\u049e\u049c\u0001\u0000\u0000\u0000\u049f"+
		"\u04a2\u0001\u0000\u0000\u0000\u04a0\u049e\u0001\u0000\u0000\u0000\u04a0"+
		"\u04a1\u0001\u0000\u0000\u0000\u04a1\u00b5\u0001\u0000\u0000\u0000\u04a2"+
		"\u04a0\u0001\u0000\u0000\u0000\u04a3\u04a4\u0005a\u0000\u0000\u04a4\u04a5"+
		"\u0003\u0098L\u0000\u04a5\u00b7\u0001\u0000\u0000\u0000\u04a6\u04a7\u0005"+
		"L\u0000\u0000\u04a7\u04a9\u0003\u00ba]\u0000\u04a8\u04aa\u0005-\u0000"+
		"\u0000\u04a9\u04a8\u0001\u0000\u0000\u0000\u04a9\u04aa\u0001\u0000\u0000"+
		"\u0000\u04aa\u04ab\u0001\u0000\u0000\u0000\u04ab\u04ac\u0005M\u0000\u0000"+
		"\u04ac\u00b9\u0001\u0000\u0000\u0000\u04ad\u04b2\u0003\u00bc^\u0000\u04ae"+
		"\u04af\u0005-\u0000\u0000\u04af\u04b1\u0003\u00bc^\u0000\u04b0\u04ae\u0001"+
		"\u0000\u0000\u0000\u04b1\u04b4\u0001\u0000\u0000\u0000\u04b2\u04b0\u0001"+
		"\u0000\u0000\u0000\u04b2\u04b3\u0001\u0000\u0000\u0000\u04b3\u00bb\u0001"+
		"\u0000\u0000\u0000\u04b4\u04b2\u0001\u0000\u0000\u0000\u04b5\u04b8\u0003"+
		"\u00be_\u0000\u04b6\u04b8\u0003\u00c0`\u0000\u04b7\u04b5\u0001\u0000\u0000"+
		"\u0000\u04b7\u04b6\u0001\u0000\u0000\u0000\u04b8\u00bd\u0001\u0000\u0000"+
		"\u0000\u04b9\u04bb\u0003\f\u0006\u0000\u04ba\u04b9\u0001\u0000\u0000\u0000"+
		"\u04bb\u04be\u0001\u0000\u0000\u0000\u04bc\u04ba\u0001\u0000\u0000\u0000"+
		"\u04bc\u04bd\u0001\u0000\u0000\u0000\u04bd\u04bf\u0001\u0000\u0000\u0000"+
		"\u04be\u04bc\u0001\u0000\u0000\u0000\u04bf\u04c0\u0003^/\u0000\u04c0\u04c1"+
		"\u0003X,\u0000\u04c1\u04c2\u0005P\u0000\u0000\u04c2\u04c3\u0003\u00d8"+
		"l\u0000\u04c3\u00bf\u0001\u0000\u0000\u0000\u04c4\u04c6\u0003\u00c2a\u0000"+
		"\u04c5\u04c4\u0001\u0000\u0000\u0000\u04c6\u04c9\u0001\u0000\u0000\u0000"+
		"\u04c7\u04c5\u0001\u0000\u0000\u0000\u04c7\u04c8\u0001\u0000\u0000\u0000"+
		"\u04c8\u04cc\u0001\u0000\u0000\u0000\u04c9\u04c7\u0001\u0000\u0000\u0000"+
		"\u04ca\u04cd\u0003\u011e\u008f\u0000\u04cb\u04cd\u0005N\u0000\u0000\u04cc"+
		"\u04ca\u0001\u0000\u0000\u0000\u04cc\u04cb\u0001\u0000\u0000\u0000\u04cd"+
		"\u00c1\u0001\u0000\u0000\u0000\u04ce\u04cf\u0003\u00dam\u0000\u04cf\u04d0"+
		"\u0005;\u0000\u0000\u04d0\u00c3\u0001\u0000\u0000\u0000\u04d1\u04d3\u0003"+
		"\u00c6c\u0000\u04d2\u04d1\u0001\u0000\u0000\u0000\u04d3\u04d4\u0001\u0000"+
		"\u0000\u0000\u04d4\u04d2\u0001\u0000\u0000\u0000\u04d4\u04d5\u0001\u0000"+
		"\u0000\u0000\u04d5\u04d7\u0001\u0000\u0000\u0000\u04d6\u04d8\u0003\u009a"+
		"M\u0000\u04d7\u04d6\u0001\u0000\u0000\u0000\u04d8\u04d9\u0001\u0000\u0000"+
		"\u0000\u04d9\u04d7\u0001\u0000\u0000\u0000\u04d9\u04da\u0001\u0000\u0000"+
		"\u0000\u04da\u00c5\u0001\u0000\u0000\u0000\u04db\u04dc\u0005]\u0000\u0000"+
		"\u04dc\u04de\u0003\u00c8d\u0000\u04dd\u04df\u0005R\u0000\u0000\u04de\u04dd"+
		"\u0001\u0000\u0000\u0000\u04de\u04df\u0001\u0000\u0000\u0000\u04df\u04e5"+
		"\u0001\u0000\u0000\u0000\u04e0\u04e2\u0005^\u0000\u0000\u04e1\u04e3\u0005"+
		"R\u0000\u0000\u04e2\u04e1\u0001\u0000\u0000\u0000\u04e2\u04e3\u0001\u0000"+
		"\u0000\u0000\u04e3\u04e5\u0001\u0000\u0000\u0000\u04e4\u04db\u0001\u0000"+
		"\u0000\u0000\u04e4\u04e0\u0001\u0000\u0000\u0000\u04e5\u00c7\u0001\u0000"+
		"\u0000\u0000\u04e6\u04eb\u0003\u00cae\u0000\u04e7\u04e8\u0005J\u0000\u0000"+
		"\u04e8\u04ea\u0003\u00cae\u0000\u04e9\u04e7\u0001\u0000\u0000\u0000\u04ea"+
		"\u04ed\u0001\u0000\u0000\u0000\u04eb\u04e9\u0001\u0000\u0000\u0000\u04eb"+
		"\u04ec\u0001\u0000\u0000\u0000\u04ec\u00c9\u0001\u0000\u0000\u0000\u04ed"+
		"\u04eb\u0001\u0000\u0000\u0000\u04ee\u04f2\u0003\u010c\u0086\u0000\u04ef"+
		"\u04f2\u0003\u00d8l\u0000\u04f0\u04f2\u0005^\u0000\u0000\u04f1\u04ee\u0001"+
		"\u0000\u0000\u0000\u04f1\u04ef\u0001\u0000\u0000\u0000\u04f1\u04f0\u0001"+
		"\u0000\u0000\u0000\u04f2\u00cb\u0001\u0000\u0000\u0000\u04f3\u04f6\u0005"+
		"L\u0000\u0000\u04f4\u04f7\u0003\u00d0h\u0000\u04f5\u04f7\u0003\u00d2i"+
		"\u0000\u04f6\u04f4\u0001\u0000\u0000\u0000\u04f6\u04f5\u0001\u0000\u0000"+
		"\u0000\u04f7\u04f8\u0001\u0000\u0000\u0000\u04f8\u04f9\u0005M\u0000\u0000"+
		"\u04f9\u0508\u0001\u0000\u0000\u0000\u04fa\u04fc\u0005L\u0000\u0000\u04fb"+
		"\u04fd\u0003\u00ceg\u0000\u04fc\u04fb\u0001\u0000\u0000\u0000\u04fc\u04fd"+
		"\u0001\u0000\u0000\u0000\u04fd\u04fe\u0001\u0000\u0000\u0000\u04fe\u0500"+
		"\u0005-\u0000\u0000\u04ff\u0501\u0003\u00d8l\u0000\u0500\u04ff\u0001\u0000"+
		"\u0000\u0000\u0500\u0501\u0001\u0000\u0000\u0000\u0501\u0502\u0001\u0000"+
		"\u0000\u0000\u0502\u0504\u0005-\u0000\u0000\u0503\u0505\u0003\u00d6k\u0000"+
		"\u0504\u0503\u0001\u0000\u0000\u0000\u0504\u0505\u0001\u0000\u0000\u0000"+
		"\u0505\u0506\u0001\u0000\u0000\u0000\u0506\u0508\u0005M\u0000\u0000\u0507"+
		"\u04f3\u0001\u0000\u0000\u0000\u0507\u04fa\u0001\u0000\u0000\u0000\u0508"+
		"\u00cd\u0001\u0000\u0000\u0000\u0509\u050c\u0003\u009cN\u0000\u050a\u050c"+
		"\u0003\u00d6k\u0000\u050b\u0509\u0001\u0000\u0000\u0000\u050b\u050a\u0001"+
		"\u0000\u0000\u0000\u050c\u00cf\u0001\u0000\u0000\u0000\u050d\u050f\u0003"+
		"\f\u0006\u0000\u050e\u050d\u0001\u0000\u0000\u0000\u050f\u0512\u0001\u0000"+
		"\u0000\u0000\u0510\u050e\u0001\u0000\u0000\u0000\u0510\u0511\u0001\u0000"+
		"\u0000\u0000\u0511\u0513\u0001\u0000\u0000\u0000\u0512\u0510\u0001\u0000"+
		"\u0000\u0000\u0513\u0514\u0003\u00fe\u007f\u0000\u0514\u0515\u0003X,\u0000"+
		"\u0515\u0516\u0005R\u0000\u0000\u0516\u0517\u0003\u00d8l\u0000\u0517\u00d1"+
		"\u0001\u0000\u0000\u0000\u0518\u0519\u0003\u010c\u0086\u0000\u0519\u051a"+
		"\u0005R\u0000\u0000\u051a\u051b\u0003\u00d8l\u0000\u051b\u00d3\u0001\u0000"+
		"\u0000\u0000\u051c\u051d\u0005L\u0000\u0000\u051d\u051e\u0003\u00d8l\u0000"+
		"\u051e\u051f\u0005M\u0000\u0000\u051f\u00d5\u0001\u0000\u0000\u0000\u0520"+
		"\u0525\u0003\u00d8l\u0000\u0521\u0522\u0005J\u0000\u0000\u0522\u0524\u0003"+
		"\u00d8l\u0000\u0523\u0521\u0001\u0000\u0000\u0000\u0524\u0527\u0001\u0000"+
		"\u0000\u0000\u0525\u0523\u0001\u0000\u0000\u0000\u0525\u0526\u0001\u0000"+
		"\u0000\u0000\u0526\u00d7\u0001\u0000\u0000\u0000\u0527\u0525\u0001\u0000"+
		"\u0000\u0000\u0528\u0529\u0003\u00dam\u0000\u0529\u00d9\u0001\u0000\u0000"+
		"\u0000\u052a\u052b\u0006m\uffff\uffff\u0000\u052b\u0555\u0003\u00e2q\u0000"+
		"\u052c\u052d\u0003\u011e\u008f\u0000\u052d\u052f\u0005L\u0000\u0000\u052e"+
		"\u0530\u0003\u00d6k\u0000\u052f\u052e\u0001\u0000\u0000\u0000\u052f\u0530"+
		"\u0001\u0000\u0000\u0000\u0530\u0531\u0001\u0000\u0000\u0000\u0531\u0532"+
		"\u0005M\u0000\u0000\u0532\u0555\u0001\u0000\u0000\u0000\u0533\u0534\u0005"+
		"\u0088\u0000\u0000\u0534\u0555\u0003\u00e6s\u0000\u0535\u0536\u0007\u0003"+
		"\u0000\u0000\u0536\u0555\u0003\u00dam\u0014\u0537\u0538\u0007\u0004\u0000"+
		"\u0000\u0538\u0555\u0003\u00dam\u0013\u0539\u053a\u0003\u00fe\u007f\u0000"+
		"\u053a\u053c\u0005\u00b3\u0000\u0000\u053b\u053d\u0003\u0106\u0083\u0000"+
		"\u053c\u053b\u0001\u0000\u0000\u0000\u053c\u053d\u0001\u0000\u0000\u0000"+
		"\u053d\u0540\u0001\u0000\u0000\u0000\u053e\u0541\u0003\u011e\u008f\u0000"+
		"\u053f\u0541\u0005\u0088\u0000\u0000\u0540\u053e\u0001\u0000\u0000\u0000"+
		"\u0540\u053f\u0001\u0000\u0000\u0000\u0541\u0555\u0001\u0000\u0000\u0000"+
		"\u0542\u0543\u0003\u00e4r\u0000\u0543\u0545\u0005\u00b3\u0000\u0000\u0544"+
		"\u0546\u0003\u0106\u0083\u0000\u0545\u0544\u0001\u0000\u0000\u0000\u0545"+
		"\u0546\u0001\u0000\u0000\u0000\u0546\u0547\u0001\u0000\u0000\u0000\u0547"+
		"\u0548\u0005\u0088\u0000\u0000\u0548\u0555\u0001\u0000\u0000\u0000\u0549"+
		"\u054a\u0005L\u0000\u0000\u054a\u054b\u0003\u00dcn\u0000\u054b\u054c\u0005"+
		"M\u0000\u0000\u054c\u054d\u0003\u00dam\u000f\u054d\u0555\u0001\u0000\u0000"+
		"\u0000\u054e\u054f\u0003\u00deo\u0000\u054f\u0552\u0005\u00b5\u0000\u0000"+
		"\u0550\u0553\u0003\u00dam\u0000\u0551\u0553\u0003\u0098L\u0000\u0552\u0550"+
		"\u0001\u0000\u0000\u0000\u0552\u0551\u0001\u0000\u0000\u0000\u0553\u0555"+
		"\u0001\u0000\u0000\u0000\u0554\u052a\u0001\u0000\u0000\u0000\u0554\u052c"+
		"\u0001\u0000\u0000\u0000\u0554\u0533\u0001\u0000\u0000\u0000\u0554\u0535"+
		"\u0001\u0000\u0000\u0000\u0554\u0537\u0001\u0000\u0000\u0000\u0554\u0539"+
		"\u0001\u0000\u0000\u0000\u0554\u0542\u0001\u0000\u0000\u0000\u0554\u0549"+
		"\u0001\u0000\u0000\u0000\u0554\u054e\u0001\u0000\u0000\u0000\u0555\u05c6"+
		"\u0001\u0000\u0000\u0000\u0556\u0557\n\u000e\u0000\u0000\u0557\u0558\u0007"+
		"\u0005\u0000\u0000\u0558\u05c5\u0003\u00dam\u000f\u0559\u055a\n\r\u0000"+
		"\u0000\u055a\u055b\u0007\u0006\u0000\u0000\u055b\u05c5\u0003\u00dam\u000e"+
		"\u055c\u0564\n\f\u0000\u0000\u055d\u055e\u0005u\u0000\u0000\u055e\u0565"+
		"\u0005u\u0000\u0000\u055f\u0560\u0005v\u0000\u0000\u0560\u0561\u0005v"+
		"\u0000\u0000\u0561\u0565\u0005v\u0000\u0000\u0562\u0563\u0005v\u0000\u0000"+
		"\u0563\u0565\u0005v\u0000\u0000\u0564\u055d\u0001\u0000\u0000\u0000\u0564"+
		"\u055f\u0001\u0000\u0000\u0000\u0564\u0562\u0001\u0000\u0000\u0000\u0565"+
		"\u0566\u0001\u0000\u0000\u0000\u0566\u05c5\u0003\u00dam\r\u0567\u0568"+
		"\n\n\u0000\u0000\u0568\u0569\u0007\u0007\u0000\u0000\u0569\u05c5\u0003"+
		"\u00dam\u000b\u056a\u056b\n\t\u0000\u0000\u056b\u056c\u0007\b\u0000\u0000"+
		"\u056c\u05c5\u0003\u00dam\n\u056d\u056e\n\b\u0000\u0000\u056e\u056f\u0005"+
		"r\u0000\u0000\u056f\u05c5\u0003\u00dam\t\u0570\u0571\n\u0007\u0000\u0000"+
		"\u0571\u0572\u0005q\u0000\u0000\u0572\u05c5\u0003\u00dam\b\u0573\u0574"+
		"\n\u0006\u0000\u0000\u0574\u0575\u0005p\u0000\u0000\u0575\u05c5\u0003"+
		"\u00dam\u0007\u0576\u0577\n\u0005\u0000\u0000\u0577\u0578\u0005o\u0000"+
		"\u0000\u0578\u05c5\u0003\u00dam\u0006\u0579\u057a\n\u0004\u0000\u0000"+
		"\u057a\u057b\u0005n\u0000\u0000\u057b\u05c5\u0003\u00dam\u0005\u057c\u057d"+
		"\n\u0003\u0000\u0000\u057d\u057e\u0005m\u0000\u0000\u057e\u057f\u0003"+
		"\u00dam\u0000\u057f\u0580\u0005R\u0000\u0000\u0580\u0581\u0003\u00dam"+
		"\u0003\u0581\u05c5\u0001\u0000\u0000\u0000\u0582\u0583\n\u0002\u0000\u0000"+
		"\u0583\u0584\u0007\t\u0000\u0000\u0584\u05c5\u0003\u00dam\u0002\u0585"+
		"\u0586\n\u001e\u0000\u0000\u0586\u0587\u0005;\u0000\u0000\u0587\u05c5"+
		"\u0003\u011e\u008f\u0000\u0588\u0589\n\u001d\u0000\u0000\u0589\u058a\u0005"+
		";\u0000\u0000\u058a\u058b\u0003\u011e\u008f\u0000\u058b\u058d\u0005L\u0000"+
		"\u0000\u058c\u058e\u0003\u00d6k\u0000\u058d\u058c\u0001\u0000\u0000\u0000"+
		"\u058d\u058e\u0001\u0000\u0000\u0000\u058e\u058f\u0001\u0000\u0000\u0000"+
		"\u058f\u0590\u0005M\u0000\u0000\u0590\u05c5\u0001\u0000\u0000\u0000\u0591"+
		"\u0592\n\u001c\u0000\u0000\u0592\u0593\u0005;\u0000\u0000\u0593\u05c5"+
		"\u0005N\u0000\u0000\u0594\u0595\n\u001b\u0000\u0000\u0595\u0596\u0005"+
		";\u0000\u0000\u0596\u0598\u0005\u0088\u0000\u0000\u0597\u0599\u0003\u00f8"+
		"|\u0000\u0598\u0597\u0001\u0000\u0000\u0000\u0598\u0599\u0001\u0000\u0000"+
		"\u0000\u0599\u059a\u0001\u0000\u0000\u0000\u059a\u05c5\u0003\u00ecv\u0000"+
		"\u059b\u059c\n\u001a\u0000\u0000\u059c\u059e\u0005;\u0000\u0000\u059d"+
		"\u059f\u0003\u00f8|\u0000\u059e\u059d\u0001\u0000\u0000\u0000\u059e\u059f"+
		"\u0001\u0000\u0000\u0000\u059f\u05a0\u0001\u0000\u0000\u0000\u05a0\u05a2"+
		"\u0005O\u0000\u0000\u05a1\u05a3\u0003\u0108\u0084\u0000\u05a2\u05a1\u0001"+
		"\u0000\u0000\u0000\u05a2\u05a3\u0001\u0000\u0000\u0000\u05a3\u05c5\u0001"+
		"\u0000\u0000\u0000\u05a4\u05a5\n\u0019\u0000\u0000\u05a5\u05a6\u0005;"+
		"\u0000\u0000\u05a6\u05a7\u0003\u00f8|\u0000\u05a7\u05a8\u0003\u011e\u008f"+
		"\u0000\u05a8\u05aa\u0005L\u0000\u0000\u05a9\u05ab\u0003\u00d6k\u0000\u05aa"+
		"\u05a9\u0001\u0000\u0000\u0000\u05aa\u05ab\u0001\u0000\u0000\u0000\u05ab"+
		"\u05ac\u0001\u0000\u0000\u0000\u05ac\u05ad\u0005M\u0000\u0000\u05ad\u05c5"+
		"\u0001\u0000\u0000\u0000\u05ae\u05af\n\u0018\u0000\u0000\u05af\u05b0\u0005"+
		"/\u0000\u0000\u05b0\u05b1\u0003\u00dam\u0000\u05b1\u05b2\u00050\u0000"+
		"\u0000\u05b2\u05c5\u0001\u0000\u0000\u0000\u05b3\u05b4\n\u0015\u0000\u0000"+
		"\u05b4\u05c5\u0007\n\u0000\u0000\u05b5\u05b6\n\u0012\u0000\u0000\u05b6"+
		"\u05b8\u0005\u00b3\u0000\u0000\u05b7\u05b9\u0003\u0106\u0083\u0000\u05b8"+
		"\u05b7\u0001\u0000\u0000\u0000\u05b8\u05b9\u0001\u0000\u0000\u0000\u05b9"+
		"\u05bc\u0001\u0000\u0000\u0000\u05ba\u05bd\u0003\u011e\u008f\u0000\u05bb"+
		"\u05bd\u0005\u0088\u0000\u0000\u05bc\u05ba\u0001\u0000\u0000\u0000\u05bc"+
		"\u05bb\u0001\u0000\u0000\u0000\u05bd\u05c5\u0001\u0000\u0000\u0000\u05be"+
		"\u05bf\n\u000b\u0000\u0000\u05bf\u05c2\u0005y\u0000\u0000\u05c0\u05c3"+
		"\u0003\u0114\u008a\u0000\u05c1\u05c3\u0003\u00fe\u007f\u0000\u05c2\u05c0"+
		"\u0001\u0000\u0000\u0000\u05c2\u05c1\u0001\u0000\u0000\u0000\u05c3\u05c5"+
		"\u0001\u0000\u0000\u0000\u05c4\u0556\u0001\u0000\u0000\u0000\u05c4\u0559"+
		"\u0001\u0000\u0000\u0000\u05c4\u055c\u0001\u0000\u0000\u0000\u05c4\u0567"+
		"\u0001\u0000\u0000\u0000\u05c4\u056a\u0001\u0000\u0000\u0000\u05c4\u056d"+
		"\u0001\u0000\u0000\u0000\u05c4\u0570\u0001\u0000\u0000\u0000\u05c4\u0573"+
		"\u0001\u0000\u0000\u0000\u05c4\u0576\u0001\u0000\u0000\u0000\u05c4\u0579"+
		"\u0001\u0000\u0000\u0000\u05c4\u057c\u0001\u0000\u0000\u0000\u05c4\u0582"+
		"\u0001\u0000\u0000\u0000\u05c4\u0585\u0001\u0000\u0000\u0000\u05c4\u0588"+
		"\u0001\u0000\u0000\u0000\u05c4\u0591\u0001\u0000\u0000\u0000\u05c4\u0594"+
		"\u0001\u0000\u0000\u0000\u05c4\u059b\u0001\u0000\u0000\u0000\u05c4\u05a4"+
		"\u0001\u0000\u0000\u0000\u05c4\u05ae\u0001\u0000\u0000\u0000\u05c4\u05b3"+
		"\u0001\u0000\u0000\u0000\u05c4\u05b5\u0001\u0000\u0000\u0000\u05c4\u05be"+
		"\u0001\u0000\u0000\u0000\u05c5\u05c8\u0001\u0000\u0000\u0000\u05c6\u05c4"+
		"\u0001\u0000\u0000\u0000\u05c6\u05c7\u0001\u0000\u0000\u0000\u05c7\u00db"+
		"\u0001\u0000\u0000\u0000\u05c8\u05c6\u0001\u0000\u0000\u0000\u05c9\u05ce"+
		"\u0003\u00fe\u007f\u0000\u05ca\u05cb\u0005r\u0000\u0000\u05cb\u05cd\u0003"+
		"\u00fe\u007f\u0000\u05cc\u05ca\u0001\u0000\u0000\u0000\u05cd\u05d0\u0001"+
		"\u0000\u0000\u0000\u05ce\u05cc\u0001\u0000\u0000\u0000\u05ce\u05cf\u0001"+
		"\u0000\u0000\u0000\u05cf\u00dd\u0001\u0000\u0000\u0000\u05d0\u05ce\u0001"+
		"\u0000\u0000\u0000\u05d1\u05dc\u0003\u011e\u008f\u0000\u05d2\u05d4\u0005"+
		"L\u0000\u0000\u05d3\u05d5\u0003h4\u0000\u05d4\u05d3\u0001\u0000\u0000"+
		"\u0000\u05d4\u05d5\u0001\u0000\u0000\u0000\u05d5\u05d6\u0001\u0000\u0000"+
		"\u0000\u05d6\u05dc\u0005M\u0000\u0000\u05d7\u05d8\u0005L\u0000\u0000\u05d8"+
		"\u05d9\u0003\u00e0p\u0000\u05d9\u05da\u0005M\u0000\u0000\u05da\u05dc\u0001"+
		"\u0000\u0000\u0000\u05db\u05d1\u0001\u0000\u0000\u0000\u05db\u05d2\u0001"+
		"\u0000\u0000\u0000\u05db\u05d7\u0001\u0000\u0000\u0000\u05dc\u00df\u0001"+
		"\u0000\u0000\u0000\u05dd\u05e2\u0003\u011e\u008f\u0000\u05de\u05df\u0005"+
		"J\u0000\u0000\u05df\u05e1\u0003\u011e\u008f\u0000\u05e0\u05de\u0001\u0000"+
		"\u0000\u0000\u05e1\u05e4\u0001\u0000\u0000\u0000\u05e2\u05e0\u0001\u0000"+
		"\u0000\u0000\u05e2\u05e3\u0001\u0000\u0000\u0000\u05e3\u00e1\u0001\u0000"+
		"\u0000\u0000\u05e4\u05e2\u0001\u0000\u0000\u0000\u05e5\u0603\u0003\u00a2"+
		"Q\u0000\u05e6\u05e7\u0005L\u0000\u0000\u05e7\u05e8\u0003\u00dam\u0000"+
		"\u05e8\u05e9\u0005M\u0000\u0000\u05e9\u0603\u0001\u0000\u0000\u0000\u05ea"+
		"\u0603\u0005N\u0000\u0000\u05eb\u0603\u0005O\u0000\u0000\u05ec\u0603\u0003"+
		"r9\u0000\u05ed\u0603\u0003\u011e\u008f\u0000\u05ee\u05f2\u0003^/\u0000"+
		"\u05ef\u05f1\u0003\u0102\u0081\u0000\u05f0\u05ef\u0001\u0000\u0000\u0000"+
		"\u05f1\u05f4\u0001\u0000\u0000\u0000\u05f2\u05f0\u0001\u0000\u0000\u0000"+
		"\u05f2\u05f3\u0001\u0000\u0000\u0000\u05f3\u05f5\u0001\u0000\u0000\u0000"+
		"\u05f4\u05f2\u0001\u0000\u0000\u0000\u05f5\u05f6\u0005;\u0000\u0000\u05f6"+
		"\u05f7\u0005E\u0000\u0000\u05f7\u0603\u0001\u0000\u0000\u0000\u05f8\u05fc"+
		"\u0003\u0104\u0082\u0000\u05f9\u05fb\u0003\u0102\u0081\u0000\u05fa\u05f9"+
		"\u0001\u0000\u0000\u0000\u05fb\u05fe\u0001\u0000\u0000\u0000\u05fc\u05fa"+
		"\u0001\u0000\u0000\u0000\u05fc\u05fd\u0001\u0000\u0000\u0000\u05fd\u05ff"+
		"\u0001\u0000\u0000\u0000\u05fe\u05fc\u0001\u0000\u0000\u0000\u05ff\u0600"+
		"\u0005;\u0000\u0000\u0600\u0601\u0005E\u0000\u0000\u0601\u0603\u0001\u0000"+
		"\u0000\u0000\u0602\u05e5\u0001\u0000\u0000\u0000\u0602\u05e6\u0001\u0000"+
		"\u0000\u0000\u0602\u05ea\u0001\u0000\u0000\u0000\u0602\u05eb\u0001\u0000"+
		"\u0000\u0000\u0602\u05ec\u0001\u0000\u0000\u0000\u0602\u05ed\u0001\u0000"+
		"\u0000\u0000\u0602\u05ee\u0001\u0000\u0000\u0000\u0602\u05f8\u0001\u0000"+
		"\u0000\u0000\u0603\u00e3\u0001\u0000\u0000\u0000\u0604\u0605\u0003^/\u0000"+
		"\u0605\u0606\u0005;\u0000\u0000\u0606\u0608\u0001\u0000\u0000\u0000\u0607"+
		"\u0604\u0001\u0000\u0000\u0000\u0607\u0608\u0001\u0000\u0000\u0000\u0608"+
		"\u0609\u0001\u0000\u0000\u0000\u0609\u060a\u0003z=\u0000\u060a\u060c\u0003"+
		"\u011e\u008f\u0000\u060b\u060d\u0003\u0106\u0083\u0000\u060c\u060b\u0001"+
		"\u0000\u0000\u0000\u060c\u060d\u0001\u0000\u0000\u0000\u060d\u00e5\u0001"+
		"\u0000\u0000\u0000\u060e\u060f\u0003\u00f8|\u0000\u060f\u0610\u0003\u00e8"+
		"t\u0000\u0610\u0611\u0003\u00f2y\u0000\u0611\u061b\u0001\u0000\u0000\u0000"+
		"\u0612\u0613\u0003z=\u0000\u0613\u0618\u0003\u00e8t\u0000\u0614\u0615"+
		"\u0003z=\u0000\u0615\u0616\u0003\u00eew\u0000\u0616\u0619\u0001\u0000"+
		"\u0000\u0000\u0617\u0619\u0003\u00f2y\u0000\u0618\u0614\u0001\u0000\u0000"+
		"\u0000\u0618\u0617\u0001\u0000\u0000\u0000\u0619\u061b\u0001\u0000\u0000"+
		"\u0000\u061a\u060e\u0001\u0000\u0000\u0000\u061a\u0612\u0001\u0000\u0000"+
		"\u0000\u061b\u00e7\u0001\u0000\u0000\u0000\u061c\u061d\u0003z=\u0000\u061d"+
		"\u061f\u0003\u011e\u008f\u0000\u061e\u0620\u0003\u00f4z\u0000\u061f\u061e"+
		"\u0001\u0000\u0000\u0000\u061f\u0620\u0001\u0000\u0000\u0000\u0620\u0624"+
		"\u0001\u0000\u0000\u0000\u0621\u0623\u0003\u00eau\u0000\u0622\u0621\u0001"+
		"\u0000\u0000\u0000\u0623\u0626\u0001\u0000\u0000\u0000\u0624\u0622\u0001"+
		"\u0000\u0000\u0000\u0624\u0625\u0001\u0000\u0000\u0000\u0625\u0629\u0001"+
		"\u0000\u0000\u0000\u0626\u0624\u0001\u0000\u0000\u0000\u0627\u0629\u0003"+
		"\u0104\u0082\u0000\u0628\u061c\u0001\u0000\u0000\u0000\u0628\u0627\u0001"+
		"\u0000\u0000\u0000\u0629\u00e9\u0001\u0000\u0000\u0000\u062a\u062b\u0005"+
		";\u0000\u0000\u062b\u062c\u0003z=\u0000\u062c\u062e\u0003\u011e\u008f"+
		"\u0000\u062d\u062f\u0003\u00f4z\u0000\u062e\u062d\u0001\u0000\u0000\u0000"+
		"\u062e\u062f\u0001\u0000\u0000\u0000\u062f\u00eb\u0001\u0000\u0000\u0000"+
		"\u0630\u0631\u0003z=\u0000\u0631\u0633\u0003\u011e\u008f\u0000\u0632\u0634"+
		"\u0003\u00f6{\u0000\u0633\u0632\u0001\u0000\u0000\u0000\u0633\u0634\u0001"+
		"\u0000\u0000\u0000\u0634\u0635\u0001\u0000\u0000\u0000\u0635\u0636\u0003"+
		"\u00f2y\u0000\u0636\u00ed\u0001\u0000\u0000\u0000\u0637\u064e\u0005/\u0000"+
		"\u0000\u0638\u063c\u00050\u0000\u0000\u0639\u063b\u0003\u0102\u0081\u0000"+
		"\u063a\u0639\u0001\u0000\u0000\u0000\u063b\u063e\u0001\u0000\u0000\u0000"+
		"\u063c\u063a\u0001\u0000\u0000\u0000\u063c\u063d\u0001\u0000\u0000\u0000"+
		"\u063d\u063f\u0001\u0000\u0000\u0000\u063e\u063c\u0001\u0000\u0000\u0000"+
		"\u063f\u064f\u0003\\.\u0000\u0640\u0641\u0003\u00d8l\u0000\u0641\u0645"+
		"\u00050\u0000\u0000\u0642\u0644\u0003\u00f0x\u0000\u0643\u0642\u0001\u0000"+
		"\u0000\u0000\u0644\u0647\u0001\u0000\u0000\u0000\u0645\u0643\u0001\u0000"+
		"\u0000\u0000\u0645\u0646\u0001\u0000\u0000\u0000\u0646\u064b\u0001\u0000"+
		"\u0000\u0000\u0647\u0645\u0001\u0000\u0000\u0000\u0648\u064a\u0003\u0102"+
		"\u0081\u0000\u0649\u0648\u0001\u0000\u0000\u0000\u064a\u064d\u0001\u0000"+
		"\u0000\u0000\u064b\u0649\u0001\u0000\u0000\u0000\u064b\u064c\u0001\u0000"+
		"\u0000\u0000\u064c\u064f\u0001\u0000\u0000\u0000\u064d\u064b\u0001\u0000"+
		"\u0000\u0000\u064e\u0638\u0001\u0000\u0000\u0000\u064e\u0640\u0001\u0000"+
		"\u0000\u0000\u064f\u00ef\u0001\u0000\u0000\u0000\u0650\u0651\u0003z=\u0000"+
		"\u0651\u0652\u0005/\u0000\u0000\u0652\u0653\u0003\u00d8l\u0000\u0653\u0654"+
		"\u00050\u0000\u0000\u0654\u00f1\u0001\u0000\u0000\u0000\u0655\u0657\u0003"+
		"\u010a\u0085\u0000\u0656\u0658\u0003<\u001e\u0000\u0657\u0656\u0001\u0000"+
		"\u0000\u0000\u0657\u0658\u0001\u0000\u0000\u0000\u0658\u00f3\u0001\u0000"+
		"\u0000\u0000\u0659\u065a\u0005u\u0000\u0000\u065a\u065d\u0005v\u0000\u0000"+
		"\u065b\u065d\u0003\u0106\u0083\u0000\u065c\u0659\u0001\u0000\u0000\u0000"+
		"\u065c\u065b\u0001\u0000\u0000\u0000\u065d\u00f5\u0001\u0000\u0000\u0000"+
		"\u065e\u065f\u0005u\u0000\u0000\u065f\u0662\u0005v\u0000\u0000\u0660\u0662"+
		"\u0003\u00f8|\u0000\u0661\u065e\u0001\u0000\u0000\u0000\u0661\u0660\u0001"+
		"\u0000\u0000\u0000\u0662\u00f7\u0001\u0000\u0000\u0000\u0663\u0664\u0005"+
		"u\u0000\u0000\u0664\u0665\u0003\u00fa}\u0000\u0665\u0666\u0005v\u0000"+
		"\u0000\u0666\u00f9\u0001\u0000\u0000\u0000\u0667\u066c\u0003\u00fe\u007f"+
		"\u0000\u0668\u0669\u0005J\u0000\u0000\u0669\u066b\u0003\u00fe\u007f\u0000"+
		"\u066a\u0668\u0001\u0000\u0000\u0000\u066b\u066e\u0001\u0000\u0000\u0000"+
		"\u066c\u066a\u0001\u0000\u0000\u0000\u066c\u066d\u0001\u0000\u0000\u0000"+
		"\u066d\u00fb\u0001\u0000\u0000\u0000\u066e\u066c\u0001\u0000\u0000\u0000"+
		"\u066f\u0674\u0003\u00fe\u007f\u0000\u0670\u0671\u0005J\u0000\u0000\u0671"+
		"\u0673\u0003\u00fe\u007f\u0000\u0672\u0670\u0001\u0000\u0000\u0000\u0673"+
		"\u0676\u0001\u0000\u0000\u0000\u0674\u0672\u0001\u0000\u0000\u0000\u0674"+
		"\u0675\u0001\u0000\u0000\u0000\u0675\u00fd\u0001\u0000\u0000\u0000\u0676"+
		"\u0674\u0001\u0000\u0000\u0000\u0677\u0678\u0003z=\u0000\u0678\u067c\u0003"+
		"\u0100\u0080\u0000\u0679\u067b\u0003\u0102\u0081\u0000\u067a\u0679\u0001"+
		"\u0000\u0000\u0000\u067b\u067e\u0001\u0000\u0000\u0000\u067c\u067a\u0001"+
		"\u0000\u0000\u0000\u067c\u067d\u0001\u0000\u0000\u0000\u067d\u00ff\u0001"+
		"\u0000\u0000\u0000\u067e\u067c\u0001\u0000\u0000\u0000\u067f\u0682\u0003"+
		"^/\u0000\u0680\u0682\u0003\u0104\u0082\u0000\u0681\u067f\u0001\u0000\u0000"+
		"\u0000\u0681\u0680\u0001\u0000\u0000\u0000\u0682\u0101\u0001\u0000\u0000"+
		"\u0000\u0683\u0684\u0003z=\u0000\u0684\u0685\u0005/\u0000\u0000\u0685"+
		"\u0686\u00050\u0000\u0000\u0686\u0103\u0001\u0000\u0000\u0000\u0687\u0688"+
		"\u0007\u000b\u0000\u0000\u0688\u0105\u0001\u0000\u0000\u0000\u0689\u068a"+
		"\u0005u\u0000\u0000\u068a\u068f\u0003b1\u0000\u068b\u068c\u0005J\u0000"+
		"\u0000\u068c\u068e\u0003b1\u0000\u068d\u068b\u0001\u0000\u0000\u0000\u068e"+
		"\u0691\u0001\u0000\u0000\u0000\u068f\u068d\u0001\u0000\u0000\u0000\u068f"+
		"\u0690\u0001\u0000\u0000\u0000\u0690\u0692\u0001\u0000\u0000\u0000\u0691"+
		"\u068f\u0001\u0000\u0000\u0000\u0692\u0693\u0005v\u0000\u0000\u0693\u0107"+
		"\u0001\u0000\u0000\u0000\u0694\u0696\u0005L\u0000\u0000\u0695\u0697\u0003"+
		"\u00d6k\u0000\u0696\u0695\u0001\u0000\u0000\u0000\u0696\u0697\u0001\u0000"+
		"\u0000\u0000\u0697\u0698\u0001\u0000\u0000\u0000\u0698\u06a3\u0005M\u0000"+
		"\u0000\u0699\u069a\u0005;\u0000\u0000\u069a\u06a0\u0003\u011e\u008f\u0000"+
		"\u069b\u069d\u0005L\u0000\u0000\u069c\u069e\u0003\u00d6k\u0000\u069d\u069c"+
		"\u0001\u0000\u0000\u0000\u069d\u069e\u0001\u0000\u0000\u0000\u069e\u069f"+
		"\u0001\u0000\u0000\u0000\u069f\u06a1\u0005M\u0000\u0000\u06a0\u069b\u0001"+
		"\u0000\u0000\u0000\u06a0\u06a1\u0001\u0000\u0000\u0000\u06a1\u06a3\u0001"+
		"\u0000\u0000\u0000\u06a2\u0694\u0001\u0000\u0000\u0000\u06a2\u0699\u0001"+
		"\u0000\u0000\u0000\u06a3\u0109\u0001\u0000\u0000\u0000\u06a4\u06a6\u0005"+
		"L\u0000\u0000\u06a5\u06a7\u0003\u00d6k\u0000\u06a6\u06a5\u0001\u0000\u0000"+
		"\u0000\u06a6\u06a7\u0001\u0000\u0000\u0000\u06a7\u06a8\u0001\u0000\u0000"+
		"\u0000\u06a8\u06a9\u0005M\u0000\u0000\u06a9\u010b\u0001\u0000\u0000\u0000"+
		"\u06aa\u06ab\u0003\u010e\u0087\u0000\u06ab\u010d\u0001\u0000\u0000\u0000"+
		"\u06ac\u06b0\u0003\u0110\u0088\u0000\u06ad\u06b0\u0003\u0118\u008c\u0000"+
		"\u06ae\u06b0\u0003\u0114\u008a\u0000\u06af\u06ac\u0001\u0000\u0000\u0000"+
		"\u06af\u06ad\u0001\u0000\u0000\u0000\u06af\u06ae\u0001\u0000\u0000\u0000"+
		"\u06b0\u010f\u0001\u0000\u0000\u0000\u06b1\u06b2\u0003\u0114\u008a\u0000"+
		"\u06b2\u06b3\u0003\u0112\u0089\u0000\u06b3\u06b4\u0003\u00d8l\u0000\u06b4"+
		"\u0111\u0001\u0000\u0000\u0000\u06b5\u06b6\u0005\u00d6\u0000\u0000\u06b6"+
		"\u0113\u0001\u0000\u0000\u0000\u06b7\u06ba\u0003\u0116\u008b\u0000\u06b8"+
		"\u06ba\u0003\u0118\u008c\u0000\u06b9\u06b7\u0001\u0000\u0000\u0000\u06b9"+
		"\u06b8\u0001\u0000\u0000\u0000\u06ba\u0115\u0001\u0000\u0000\u0000\u06bb"+
		"\u06bd\u0003\n\u0005\u0000\u06bc\u06bb\u0001\u0000\u0000\u0000\u06bd\u06c0"+
		"\u0001\u0000\u0000\u0000\u06be\u06bc\u0001\u0000\u0000\u0000\u06be\u06bf"+
		"\u0001\u0000\u0000\u0000\u06bf\u06c1\u0001\u0000\u0000\u0000\u06c0\u06be"+
		"\u0001\u0000\u0000\u0000\u06c1\u06c2\u0003\u00fe\u007f\u0000\u06c2\u06c3"+
		"\u0003\u011e\u008f\u0000\u06c3\u06c6\u0001\u0000\u0000\u0000\u06c4\u06c6"+
		"\u0005\u00e0\u0000\u0000\u06c5\u06be\u0001\u0000\u0000\u0000\u06c5\u06c4"+
		"\u0001\u0000\u0000\u0000\u06c6\u0117\u0001\u0000\u0000\u0000\u06c7\u06c9"+
		"\u0003\n\u0005\u0000\u06c8\u06c7\u0001\u0000\u0000\u0000\u06c9\u06cc\u0001"+
		"\u0000\u0000\u0000\u06ca\u06c8\u0001\u0000\u0000\u0000\u06ca\u06cb\u0001"+
		"\u0000\u0000\u0000\u06cb\u06cd\u0001\u0000\u0000\u0000\u06cc\u06ca\u0001"+
		"\u0000\u0000\u0000\u06cd\u06ce\u0003\u00fe\u007f\u0000\u06ce\u06d0\u0005"+
		"L\u0000\u0000\u06cf\u06d1\u0003\u011a\u008d\u0000\u06d0\u06cf\u0001\u0000"+
		"\u0000\u0000\u06d0\u06d1\u0001\u0000\u0000\u0000\u06d1\u06d2\u0001\u0000"+
		"\u0000\u0000\u06d2\u06d4\u0005M\u0000\u0000\u06d3\u06d5\u0003\u011e\u008f"+
		"\u0000\u06d4\u06d3\u0001\u0000\u0000\u0000\u06d4\u06d5\u0001\u0000\u0000"+
		"\u0000\u06d5\u0119\u0001\u0000\u0000\u0000\u06d6\u06db\u0003\u010e\u0087"+
		"\u0000\u06d7\u06d8\u0005J\u0000\u0000\u06d8\u06da\u0003\u010e\u0087\u0000"+
		"\u06d9\u06d7\u0001\u0000\u0000\u0000\u06da\u06dd\u0001\u0000\u0000\u0000"+
		"\u06db\u06d9\u0001\u0000\u0000\u0000\u06db\u06dc\u0001\u0000\u0000\u0000"+
		"\u06dc\u011b\u0001\u0000\u0000\u0000\u06dd\u06db\u0001\u0000\u0000\u0000"+
		"\u06de\u06df\u0005\u00d3\u0000\u0000\u06df\u06e4\u0003^/\u0000\u06e0\u06e1"+
		"\u0005J\u0000\u0000\u06e1\u06e3\u0003^/\u0000\u06e2\u06e0\u0001\u0000"+
		"\u0000\u0000\u06e3\u06e6\u0001\u0000\u0000\u0000\u06e4\u06e2\u0001\u0000"+
		"\u0000\u0000\u06e4\u06e5\u0001\u0000\u0000\u0000\u06e5\u011d\u0001\u0000"+
		"\u0000\u0000\u06e6\u06e4\u0001\u0000\u0000\u0000\u06e7\u06e8\u0007\f\u0000"+
		"\u0000\u06e8\u011f\u0001\u0000\u0000\u0000\u00d4\u0121\u0126\u012c\u0138"+
		"\u013d\u0147\u014c\u0153\u0155\u015c\u016d\u0171\u0176\u0179\u017c\u017f"+
		"\u0186\u018a\u0190\u0199\u019e\u01a1\u01b0\u01b7\u01bc\u01ce\u01d6\u01e1"+
		"\u01e9\u01ef\u01f5\u01f8\u01fb\u0204\u020a\u020d\u0213\u0219\u021c\u021f"+
		"\u022a\u0233\u023a\u0240\u0244\u024e\u0251\u0259\u025d\u0263\u0269\u026e"+
		"\u0279\u027e\u0287\u028a\u0292\u0296\u029f\u02a6\u02ab\u02b1\u02b3\u02b8"+
		"\u02bd\u02c5\u02c8\u02cb\u02d2\u02d7\u02de\u02e5\u02e8\u02ea\u02f4\u02f9"+
		"\u0302\u0307\u030a\u030f\u0318\u0324\u0333\u0340\u0348\u034b\u0352\u035c"+
		"\u0364\u0367\u036a\u0377\u037f\u0384\u0389\u0392\u0396\u039a\u039e\u03a2"+
		"\u03a4\u03ac\u03b0\u03b9\u03be\u03c4\u03cd\u03d3\u03d9\u03e4\u03e9\u03f2"+
		"\u03f9\u03fc\u0403\u040b\u0420\u0423\u0426\u042e\u0432\u043f\u0448\u044d"+
		"\u0458\u0465\u046a\u0470\u0473\u0478\u0495\u04a0\u04a9\u04b2\u04b7\u04bc"+
		"\u04c7\u04cc\u04d4\u04d9\u04de\u04e2\u04e4\u04eb\u04f1\u04f6\u04fc\u0500"+
		"\u0504\u0507\u050b\u0510\u0525\u052f\u053c\u0540\u0545\u0552\u0554\u0564"+
		"\u058d\u0598\u059e\u05a2\u05aa\u05b8\u05bc\u05c2\u05c4\u05c6\u05ce\u05d4"+
		"\u05db\u05e2\u05f2\u05fc\u0602\u0607\u060c\u0618\u061a\u061f\u0624\u0628"+
		"\u062e\u0633\u063c\u0645\u064b\u064e\u0657\u065c\u0661\u066c\u0674\u067c"+
		"\u0681\u068f\u0696\u069d\u06a0\u06a2\u06a6\u06af\u06b9\u06be\u06c5\u06ca"+
		"\u06d0\u06d4\u06db\u06e4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}