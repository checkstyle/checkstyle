// Generated from com/puppycrawl/tools/checkstyle/grammar/javadoc/JavadocCommentsParser.g4 by ANTLR 4.13.2
package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import java.util.Set;
import java.util.stream.Collectors;
import com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsParserUtil;
import com.puppycrawl.tools.checkstyle.grammar.SimpleToken;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JavadocCommentsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		JAVADOC=1, LEADING_ASTERISK=2, NEWLINE=3, TEXT=4, WS=5, JAVADOC_INLINE_TAG=6, 
		JAVADOC_INLINE_TAG_START=7, JAVADOC_INLINE_TAG_END=8, CODE=9, LINK=10, 
		IDENTIFIER=11, HASH=12, LPAREN=13, RPAREN=14, COMMA=15, LINKPLAIN=16, 
		AUTHOR=17, DEPRECATED=18, RETURN=19, PARAM=20, TAG_OPEN=21, TAG_CLOSE=22, 
		TAG_SLASH_CLOSE=23, TAG_SLASH=24, EQUALS=25, TAG_NAME=26, ATTRIBUTE_VALUE=27, 
		SLASH=28, PARAMETER_TYPE=29, LT=30, GT=31, EXTENDS=32, SUPER=33, QUESTION=34, 
		VALUE=35, FORMAT_SPECIFIER=36, INHERIT_DOC=37, SUMMARY=38, SYSTEM_PROPERTY=39, 
		INDEX=40, INDEX_TERM=41, SNIPPET=42, SNIPPET_ATTR_NAME=43, COLON=44, EXCEPTION=45, 
		THROWS=46, PARAMETER_NAME=47, SINCE=48, VERSION=49, SEE=50, STRING_LITERAL=51, 
		LITERAL_HIDDEN=52, SERIAL=53, SERIAL_DATA=54, SERIAL_FIELD=55, FIELD_TYPE=56, 
		AT_SIGN=57, UNUSED_TOKEN1=58, REFERENCE=59, MEMBER_REFERENCE=60, PARAMETER_TYPE_LIST=61, 
		TYPE_ARGUMENTS=62, TYPE_ARGUMENT=63, DESCRIPTION=64, SNIPPET_ATTRIBUTES=65, 
		SNIPPET_ATTRIBUTE=66, SNIPPET_BODY=67, HTML_ELEMENT=68, VOID_ELEMENT=69, 
		HTML_CONTENT=70, HTML_TAG_START=71, HTML_TAG_END=72, HTML_ATTRIBUTES=73, 
		HTML_ATTRIBUTE=74, JAVADOC_BLOCK_TAG=75, CODE_INLINE_TAG=76, LINK_INLINE_TAG=77, 
		LINKPLAIN_INLINE_TAG=78, VALUE_INLINE_TAG=79, INHERIT_DOC_INLINE_TAG=80, 
		SUMMARY_INLINE_TAG=81, SYSTEM_PROPERTY_INLINE_TAG=82, INDEX_INLINE_TAG=83, 
		RETURN_INLINE_TAG=84, LITERAL_INLINE_TAG=85, SNIPPET_INLINE_TAG=86, CUSTOM_INLINE_TAG=87, 
		AUTHOR_BLOCK_TAG=88, DEPRECATED_BLOCK_TAG=89, RETURN_BLOCK_TAG=90, PARAM_BLOCK_TAG=91, 
		EXCEPTION_BLOCK_TAG=92, THROWS_BLOCK_TAG=93, SINCE_BLOCK_TAG=94, VERSION_BLOCK_TAG=95, 
		SEE_BLOCK_TAG=96, HIDDEN_BLOCK_TAG=97, USES_BLOCK_TAG=98, PROVIDES_BLOCK_TAG=99, 
		SERIAL_BLOCK_TAG=100, SERIAL_DATA_BLOCK_TAG=101, SERIAL_FIELD_BLOCK_TAG=102, 
		CUSTOM_BLOCK_TAG=103, HTML_COMMENT_START=104, HTML_COMMENT_END=105, HTML_COMMENT=106, 
		HTML_COMMENT_CONTENT=107, USES=108, PROVIDES=109, LITERAL=110, CUSTOM_NAME=111, 
		Snippet_ATTRIBUTE=112, See_TAG_OPEN=113, TAG_ATTR_NAME=114, ATTRIBUTE=115, 
		Link_COMMA=116;
	public static final int
		RULE_javadoc = 0, RULE_mainDescription = 1, RULE_blockTag = 2, RULE_authorTag = 3, 
		RULE_deprecatedTag = 4, RULE_returnTag = 5, RULE_parameterTag = 6, RULE_throwsTag = 7, 
		RULE_exceptionTag = 8, RULE_sinceTag = 9, RULE_versionTag = 10, RULE_seeTag = 11, 
		RULE_hiddenTag = 12, RULE_usesTag = 13, RULE_providesTag = 14, RULE_serialTag = 15, 
		RULE_serialDataTag = 16, RULE_serialFieldTag = 17, RULE_customBlockTag = 18, 
		RULE_inlineTag = 19, RULE_inlineTagContent = 20, RULE_codeInlineTag = 21, 
		RULE_linkPlainInlineTag = 22, RULE_linkInlineTag = 23, RULE_valueInlineTag = 24, 
		RULE_inheritDocInlineTag = 25, RULE_summaryInlineTag = 26, RULE_systemPropertyInlineTag = 27, 
		RULE_indexInlineTag = 28, RULE_returnInlineTag = 29, RULE_literalInlineTag = 30, 
		RULE_snippetInlineTag = 31, RULE_customInlineTag = 32, RULE_reference = 33, 
		RULE_typeName = 34, RULE_qualifiedName = 35, RULE_typeArguments = 36, 
		RULE_typeArgument = 37, RULE_memberReference = 38, RULE_parameterTypeList = 39, 
		RULE_snippetAttribute = 40, RULE_snippetBody = 41, RULE_description = 42, 
		RULE_htmlElement = 43, RULE_voidElement = 44, RULE_tightElement = 45, 
		RULE_nonTightElement = 46, RULE_selfClosingElement = 47, RULE_htmlTagStart = 48, 
		RULE_htmlTagEnd = 49, RULE_htmlAttribute = 50, RULE_htmlContent = 51, 
		RULE_nonTightHtmlContent = 52, RULE_htmlComment = 53, RULE_htmlCommentContent = 54;
	private static String[] makeRuleNames() {
		return new String[] {
			"javadoc", "mainDescription", "blockTag", "authorTag", "deprecatedTag", 
			"returnTag", "parameterTag", "throwsTag", "exceptionTag", "sinceTag", 
			"versionTag", "seeTag", "hiddenTag", "usesTag", "providesTag", "serialTag", 
			"serialDataTag", "serialFieldTag", "customBlockTag", "inlineTag", "inlineTagContent", 
			"codeInlineTag", "linkPlainInlineTag", "linkInlineTag", "valueInlineTag", 
			"inheritDocInlineTag", "summaryInlineTag", "systemPropertyInlineTag", 
			"indexInlineTag", "returnInlineTag", "literalInlineTag", "snippetInlineTag", 
			"customInlineTag", "reference", "typeName", "qualifiedName", "typeArguments", 
			"typeArgument", "memberReference", "parameterTypeList", "snippetAttribute", 
			"snippetBody", "description", "htmlElement", "voidElement", "tightElement", 
			"nonTightElement", "selfClosingElement", "htmlTagStart", "htmlTagEnd", 
			"htmlAttribute", "htmlContent", "nonTightHtmlContent", "htmlComment", 
			"htmlCommentContent"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "'code'", "'link'", 
			null, null, "'('", "')'", null, "'linkplain'", "'author'", "'deprecated'", 
			"'return'", "'param'", "'<'", null, null, null, "'='", null, null, null, 
			null, null, "'>'", "'extends'", "'super'", "'?'", "'value'", null, "'inheritDoc'", 
			"'summary'", "'systemProperty'", "'index'", null, "'snippet'", null, 
			"':'", "'exception'", "'throws'", null, "'since'", "'version'", null, 
			null, "'hidden'", "'serial'", "'serialData'", "'serialField'", null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "'<!--'", 
			"'-->'", null, null, "'uses'", "'provides'", "'literal'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "JAVADOC", "LEADING_ASTERISK", "NEWLINE", "TEXT", "WS", "JAVADOC_INLINE_TAG", 
			"JAVADOC_INLINE_TAG_START", "JAVADOC_INLINE_TAG_END", "CODE", "LINK", 
			"IDENTIFIER", "HASH", "LPAREN", "RPAREN", "COMMA", "LINKPLAIN", "AUTHOR", 
			"DEPRECATED", "RETURN", "PARAM", "TAG_OPEN", "TAG_CLOSE", "TAG_SLASH_CLOSE", 
			"TAG_SLASH", "EQUALS", "TAG_NAME", "ATTRIBUTE_VALUE", "SLASH", "PARAMETER_TYPE", 
			"LT", "GT", "EXTENDS", "SUPER", "QUESTION", "VALUE", "FORMAT_SPECIFIER", 
			"INHERIT_DOC", "SUMMARY", "SYSTEM_PROPERTY", "INDEX", "INDEX_TERM", "SNIPPET", 
			"SNIPPET_ATTR_NAME", "COLON", "EXCEPTION", "THROWS", "PARAMETER_NAME", 
			"SINCE", "VERSION", "SEE", "STRING_LITERAL", "LITERAL_HIDDEN", "SERIAL", 
			"SERIAL_DATA", "SERIAL_FIELD", "FIELD_TYPE", "AT_SIGN", "UNUSED_TOKEN1", 
			"REFERENCE", "MEMBER_REFERENCE", "PARAMETER_TYPE_LIST", "TYPE_ARGUMENTS", 
			"TYPE_ARGUMENT", "DESCRIPTION", "SNIPPET_ATTRIBUTES", "SNIPPET_ATTRIBUTE", 
			"SNIPPET_BODY", "HTML_ELEMENT", "VOID_ELEMENT", "HTML_CONTENT", "HTML_TAG_START", 
			"HTML_TAG_END", "HTML_ATTRIBUTES", "HTML_ATTRIBUTE", "JAVADOC_BLOCK_TAG", 
			"CODE_INLINE_TAG", "LINK_INLINE_TAG", "LINKPLAIN_INLINE_TAG", "VALUE_INLINE_TAG", 
			"INHERIT_DOC_INLINE_TAG", "SUMMARY_INLINE_TAG", "SYSTEM_PROPERTY_INLINE_TAG", 
			"INDEX_INLINE_TAG", "RETURN_INLINE_TAG", "LITERAL_INLINE_TAG", "SNIPPET_INLINE_TAG", 
			"CUSTOM_INLINE_TAG", "AUTHOR_BLOCK_TAG", "DEPRECATED_BLOCK_TAG", "RETURN_BLOCK_TAG", 
			"PARAM_BLOCK_TAG", "EXCEPTION_BLOCK_TAG", "THROWS_BLOCK_TAG", "SINCE_BLOCK_TAG", 
			"VERSION_BLOCK_TAG", "SEE_BLOCK_TAG", "HIDDEN_BLOCK_TAG", "USES_BLOCK_TAG", 
			"PROVIDES_BLOCK_TAG", "SERIAL_BLOCK_TAG", "SERIAL_DATA_BLOCK_TAG", "SERIAL_FIELD_BLOCK_TAG", 
			"CUSTOM_BLOCK_TAG", "HTML_COMMENT_START", "HTML_COMMENT_END", "HTML_COMMENT", 
			"HTML_COMMENT_CONTENT", "USES", "PROVIDES", "LITERAL", "CUSTOM_NAME", 
			"Snippet_ATTRIBUTE", "See_TAG_OPEN", "TAG_ATTR_NAME", "ATTRIBUTE", "Link_COMMA"
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
	public String getGrammarFileName() { return "JavadocCommentsParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    private static final Set<String> VOID_TAGS = Set.of(
	        "area", "base", "basefont", "br", "col", "frame", "hr",
	        "img", "input", "isindex", "link", "meta", "param"
	    );

	    private Set<SimpleToken> unclosedTagNameTokens;

	    public boolean isVoidTag() {
	        String tagName = _input.LT(2).getText();
	        return VOID_TAGS.contains(tagName.toLowerCase());
	    }

	    public JavadocCommentsParser(CommonTokenStream tokens, Set<SimpleToken> unclosed) {
	        super(tokens);
	        _interp = new ParserATNSimulator(
	            this,
	            _ATN,
	            _decisionToDFA,
	            _sharedContextCache
	        );
	        this.unclosedTagNameTokens = unclosed;
	    }

	public JavadocCommentsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JavadocContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavadocCommentsParser.EOF, 0); }
		public MainDescriptionContext mainDescription() {
			return getRuleContext(MainDescriptionContext.class,0);
		}
		public List<BlockTagContext> blockTag() {
			return getRuleContexts(BlockTagContext.class);
		}
		public BlockTagContext blockTag(int i) {
			return getRuleContext(BlockTagContext.class,i);
		}
		public JavadocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javadoc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitJavadoc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JavadocContext javadoc() throws RecognitionException {
		JavadocContext _localctx = new JavadocContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_javadoc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(110);
				mainDescription();
				}
				break;
			}
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT_SIGN) {
				{
				{
				setState(113);
				blockTag();
				}
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(119);
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
	public static class MainDescriptionContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public List<InlineTagContext> inlineTag() {
			return getRuleContexts(InlineTagContext.class);
		}
		public InlineTagContext inlineTag(int i) {
			return getRuleContext(InlineTagContext.class,i);
		}
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public List<HtmlCommentContext> htmlComment() {
			return getRuleContexts(HtmlCommentContext.class);
		}
		public HtmlCommentContext htmlComment(int i) {
			return getRuleContext(HtmlCommentContext.class,i);
		}
		public MainDescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainDescription; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitMainDescription(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainDescriptionContext mainDescription() throws RecognitionException {
		MainDescriptionContext _localctx = new MainDescriptionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainDescription);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(125);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						setState(121);
						match(TEXT);
						}
						break;
					case 2:
						{
						setState(122);
						inlineTag();
						}
						break;
					case 3:
						{
						setState(123);
						htmlElement();
						}
						break;
					case 4:
						{
						setState(124);
						htmlComment();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(127); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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
	public static class BlockTagContext extends ParserRuleContext {
		public AuthorTagContext authorTag() {
			return getRuleContext(AuthorTagContext.class,0);
		}
		public DeprecatedTagContext deprecatedTag() {
			return getRuleContext(DeprecatedTagContext.class,0);
		}
		public ReturnTagContext returnTag() {
			return getRuleContext(ReturnTagContext.class,0);
		}
		public ParameterTagContext parameterTag() {
			return getRuleContext(ParameterTagContext.class,0);
		}
		public ThrowsTagContext throwsTag() {
			return getRuleContext(ThrowsTagContext.class,0);
		}
		public ExceptionTagContext exceptionTag() {
			return getRuleContext(ExceptionTagContext.class,0);
		}
		public SinceTagContext sinceTag() {
			return getRuleContext(SinceTagContext.class,0);
		}
		public VersionTagContext versionTag() {
			return getRuleContext(VersionTagContext.class,0);
		}
		public SeeTagContext seeTag() {
			return getRuleContext(SeeTagContext.class,0);
		}
		public HiddenTagContext hiddenTag() {
			return getRuleContext(HiddenTagContext.class,0);
		}
		public UsesTagContext usesTag() {
			return getRuleContext(UsesTagContext.class,0);
		}
		public ProvidesTagContext providesTag() {
			return getRuleContext(ProvidesTagContext.class,0);
		}
		public SerialTagContext serialTag() {
			return getRuleContext(SerialTagContext.class,0);
		}
		public SerialDataTagContext serialDataTag() {
			return getRuleContext(SerialDataTagContext.class,0);
		}
		public SerialFieldTagContext serialFieldTag() {
			return getRuleContext(SerialFieldTagContext.class,0);
		}
		public CustomBlockTagContext customBlockTag() {
			return getRuleContext(CustomBlockTagContext.class,0);
		}
		public BlockTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitBlockTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockTagContext blockTag() throws RecognitionException {
		BlockTagContext _localctx = new BlockTagContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_blockTag);
		try {
			setState(145);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(129);
				authorTag();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(130);
				deprecatedTag();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(131);
				returnTag();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(132);
				parameterTag();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(133);
				throwsTag();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(134);
				exceptionTag();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(135);
				sinceTag();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(136);
				versionTag();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(137);
				seeTag();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(138);
				hiddenTag();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(139);
				usesTag();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(140);
				providesTag();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(141);
				serialTag();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(142);
				serialDataTag();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(143);
				serialFieldTag();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(144);
				customBlockTag();
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
	public static class AuthorTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode AUTHOR() { return getToken(JavadocCommentsParser.AUTHOR, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public AuthorTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_authorTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitAuthorTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AuthorTagContext authorTag() throws RecognitionException {
		AuthorTagContext _localctx = new AuthorTagContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_authorTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(AT_SIGN);
			setState(148);
			((AuthorTagContext)_localctx).tagName = match(AUTHOR);
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(149);
				description();
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
	public static class DeprecatedTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode DEPRECATED() { return getToken(JavadocCommentsParser.DEPRECATED, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public DeprecatedTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deprecatedTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitDeprecatedTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeprecatedTagContext deprecatedTag() throws RecognitionException {
		DeprecatedTagContext _localctx = new DeprecatedTagContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_deprecatedTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(AT_SIGN);
			setState(153);
			((DeprecatedTagContext)_localctx).tagName = match(DEPRECATED);
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(154);
				description();
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
	public static class ReturnTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode RETURN() { return getToken(JavadocCommentsParser.RETURN, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public ReturnTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitReturnTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTagContext returnTag() throws RecognitionException {
		ReturnTagContext _localctx = new ReturnTagContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_returnTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(AT_SIGN);
			setState(158);
			((ReturnTagContext)_localctx).tagName = match(RETURN);
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(159);
				description();
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
	public static class ParameterTagContext extends ParserRuleContext {
		public Token tagName;
		public Token paramName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode PARAM() { return getToken(JavadocCommentsParser.PARAM, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode PARAMETER_NAME() { return getToken(JavadocCommentsParser.PARAMETER_NAME, 0); }
		public ParameterTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitParameterTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterTagContext parameterTag() throws RecognitionException {
		ParameterTagContext _localctx = new ParameterTagContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_parameterTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(AT_SIGN);
			setState(163);
			((ParameterTagContext)_localctx).tagName = match(PARAM);
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(164);
				((ParameterTagContext)_localctx).paramName = match(PARAMETER_NAME);
				}
				break;
			}
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(167);
				description();
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
	public static class ThrowsTagContext extends ParserRuleContext {
		public Token tagName;
		public QualifiedNameContext exceptionName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode THROWS() { return getToken(JavadocCommentsParser.THROWS, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ThrowsTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwsTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitThrowsTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowsTagContext throwsTag() throws RecognitionException {
		ThrowsTagContext _localctx = new ThrowsTagContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_throwsTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(AT_SIGN);
			setState(171);
			((ThrowsTagContext)_localctx).tagName = match(THROWS);
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(172);
				((ThrowsTagContext)_localctx).exceptionName = qualifiedName();
				}
				break;
			}
			setState(176);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(175);
				description();
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
	public static class ExceptionTagContext extends ParserRuleContext {
		public Token tagName;
		public QualifiedNameContext exceptionName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode EXCEPTION() { return getToken(JavadocCommentsParser.EXCEPTION, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ExceptionTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitExceptionTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptionTagContext exceptionTag() throws RecognitionException {
		ExceptionTagContext _localctx = new ExceptionTagContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_exceptionTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(AT_SIGN);
			setState(179);
			((ExceptionTagContext)_localctx).tagName = match(EXCEPTION);
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(180);
				((ExceptionTagContext)_localctx).exceptionName = qualifiedName();
				}
				break;
			}
			setState(184);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(183);
				description();
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
	public static class SinceTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode SINCE() { return getToken(JavadocCommentsParser.SINCE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public SinceTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sinceTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSinceTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SinceTagContext sinceTag() throws RecognitionException {
		SinceTagContext _localctx = new SinceTagContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_sinceTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(AT_SIGN);
			setState(187);
			((SinceTagContext)_localctx).tagName = match(SINCE);
			setState(189);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(188);
				description();
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
	public static class VersionTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode VERSION() { return getToken(JavadocCommentsParser.VERSION, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public VersionTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitVersionTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VersionTagContext versionTag() throws RecognitionException {
		VersionTagContext _localctx = new VersionTagContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_versionTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(AT_SIGN);
			setState(192);
			((VersionTagContext)_localctx).tagName = match(VERSION);
			setState(194);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(193);
				description();
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
	public static class SeeTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(JavadocCommentsParser.STRING_LITERAL, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public HtmlElementContext htmlElement() {
			return getRuleContext(HtmlElementContext.class,0);
		}
		public TerminalNode SEE() { return getToken(JavadocCommentsParser.SEE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public SeeTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seeTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSeeTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeeTagContext seeTag() throws RecognitionException {
		SeeTagContext _localctx = new SeeTagContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_seeTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(AT_SIGN);
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(197);
				((SeeTagContext)_localctx).tagName = match(SEE);
				setState(198);
				match(STRING_LITERAL);
				}
				break;
			case 2:
				{
				setState(199);
				((SeeTagContext)_localctx).tagName = match(SEE);
				setState(200);
				reference();
				setState(202);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(201);
					description();
					}
					break;
				}
				}
				break;
			case 3:
				{
				setState(204);
				((SeeTagContext)_localctx).tagName = match(SEE);
				setState(205);
				htmlElement();
				setState(207);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(206);
					description();
					}
					break;
				}
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
	public static class HiddenTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode LITERAL_HIDDEN() { return getToken(JavadocCommentsParser.LITERAL_HIDDEN, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public HiddenTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hiddenTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHiddenTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HiddenTagContext hiddenTag() throws RecognitionException {
		HiddenTagContext _localctx = new HiddenTagContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_hiddenTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(AT_SIGN);
			setState(212);
			((HiddenTagContext)_localctx).tagName = match(LITERAL_HIDDEN);
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(213);
				description();
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
	public static class UsesTagContext extends ParserRuleContext {
		public Token tagName;
		public QualifiedNameContext serviceType;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode USES() { return getToken(JavadocCommentsParser.USES, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public UsesTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usesTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitUsesTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UsesTagContext usesTag() throws RecognitionException {
		UsesTagContext _localctx = new UsesTagContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_usesTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(AT_SIGN);
			setState(217);
			((UsesTagContext)_localctx).tagName = match(USES);
			setState(218);
			((UsesTagContext)_localctx).serviceType = qualifiedName();
			setState(220);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(219);
				description();
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
	public static class ProvidesTagContext extends ParserRuleContext {
		public Token tagName;
		public QualifiedNameContext serviceType;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode PROVIDES() { return getToken(JavadocCommentsParser.PROVIDES, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public ProvidesTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_providesTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitProvidesTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProvidesTagContext providesTag() throws RecognitionException {
		ProvidesTagContext _localctx = new ProvidesTagContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_providesTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(AT_SIGN);
			setState(223);
			((ProvidesTagContext)_localctx).tagName = match(PROVIDES);
			setState(224);
			((ProvidesTagContext)_localctx).serviceType = qualifiedName();
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(225);
				description();
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
	public static class SerialTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode SERIAL() { return getToken(JavadocCommentsParser.SERIAL, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public SerialTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serialTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSerialTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SerialTagContext serialTag() throws RecognitionException {
		SerialTagContext _localctx = new SerialTagContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_serialTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(AT_SIGN);
			setState(229);
			((SerialTagContext)_localctx).tagName = match(SERIAL);
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(230);
				description();
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
	public static class SerialDataTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode SERIAL_DATA() { return getToken(JavadocCommentsParser.SERIAL_DATA, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public SerialDataTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serialDataTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSerialDataTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SerialDataTagContext serialDataTag() throws RecognitionException {
		SerialDataTagContext _localctx = new SerialDataTagContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_serialDataTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(AT_SIGN);
			setState(234);
			((SerialDataTagContext)_localctx).tagName = match(SERIAL_DATA);
			setState(236);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(235);
				description();
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
	public static class SerialFieldTagContext extends ParserRuleContext {
		public Token tagName;
		public Token fieldName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode SERIAL_FIELD() { return getToken(JavadocCommentsParser.SERIAL_FIELD, 0); }
		public TerminalNode FIELD_TYPE() { return getToken(JavadocCommentsParser.FIELD_TYPE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(JavadocCommentsParser.IDENTIFIER, 0); }
		public SerialFieldTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_serialFieldTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSerialFieldTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SerialFieldTagContext serialFieldTag() throws RecognitionException {
		SerialFieldTagContext _localctx = new SerialFieldTagContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_serialFieldTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(AT_SIGN);
			setState(239);
			((SerialFieldTagContext)_localctx).tagName = match(SERIAL_FIELD);
			setState(241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(240);
				((SerialFieldTagContext)_localctx).fieldName = match(IDENTIFIER);
				}
				break;
			}
			setState(244);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(243);
				match(FIELD_TYPE);
				}
				break;
			}
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(246);
				description();
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
	public static class CustomBlockTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode AT_SIGN() { return getToken(JavadocCommentsParser.AT_SIGN, 0); }
		public TerminalNode CUSTOM_NAME() { return getToken(JavadocCommentsParser.CUSTOM_NAME, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public CustomBlockTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_customBlockTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitCustomBlockTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CustomBlockTagContext customBlockTag() throws RecognitionException {
		CustomBlockTagContext _localctx = new CustomBlockTagContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_customBlockTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(AT_SIGN);
			setState(250);
			((CustomBlockTagContext)_localctx).tagName = match(CUSTOM_NAME);
			setState(252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(251);
				description();
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
	public static class InlineTagContext extends ParserRuleContext {
		public TerminalNode JAVADOC_INLINE_TAG_START() { return getToken(JavadocCommentsParser.JAVADOC_INLINE_TAG_START, 0); }
		public InlineTagContentContext inlineTagContent() {
			return getRuleContext(InlineTagContentContext.class,0);
		}
		public TerminalNode JAVADOC_INLINE_TAG_END() { return getToken(JavadocCommentsParser.JAVADOC_INLINE_TAG_END, 0); }
		public InlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineTagContext inlineTag() throws RecognitionException {
		InlineTagContext _localctx = new InlineTagContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_inlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(JAVADOC_INLINE_TAG_START);
			setState(255);
			inlineTagContent();
			setState(256);
			match(JAVADOC_INLINE_TAG_END);
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
	public static class InlineTagContentContext extends ParserRuleContext {
		public CodeInlineTagContext codeInlineTag() {
			return getRuleContext(CodeInlineTagContext.class,0);
		}
		public LinkInlineTagContext linkInlineTag() {
			return getRuleContext(LinkInlineTagContext.class,0);
		}
		public LinkPlainInlineTagContext linkPlainInlineTag() {
			return getRuleContext(LinkPlainInlineTagContext.class,0);
		}
		public ValueInlineTagContext valueInlineTag() {
			return getRuleContext(ValueInlineTagContext.class,0);
		}
		public InheritDocInlineTagContext inheritDocInlineTag() {
			return getRuleContext(InheritDocInlineTagContext.class,0);
		}
		public SummaryInlineTagContext summaryInlineTag() {
			return getRuleContext(SummaryInlineTagContext.class,0);
		}
		public SystemPropertyInlineTagContext systemPropertyInlineTag() {
			return getRuleContext(SystemPropertyInlineTagContext.class,0);
		}
		public IndexInlineTagContext indexInlineTag() {
			return getRuleContext(IndexInlineTagContext.class,0);
		}
		public ReturnInlineTagContext returnInlineTag() {
			return getRuleContext(ReturnInlineTagContext.class,0);
		}
		public LiteralInlineTagContext literalInlineTag() {
			return getRuleContext(LiteralInlineTagContext.class,0);
		}
		public SnippetInlineTagContext snippetInlineTag() {
			return getRuleContext(SnippetInlineTagContext.class,0);
		}
		public CustomInlineTagContext customInlineTag() {
			return getRuleContext(CustomInlineTagContext.class,0);
		}
		public InlineTagContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineTagContent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitInlineTagContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineTagContentContext inlineTagContent() throws RecognitionException {
		InlineTagContentContext _localctx = new InlineTagContentContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_inlineTagContent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CODE:
				{
				setState(258);
				codeInlineTag();
				}
				break;
			case LINK:
				{
				setState(259);
				linkInlineTag();
				}
				break;
			case LINKPLAIN:
				{
				setState(260);
				linkPlainInlineTag();
				}
				break;
			case VALUE:
				{
				setState(261);
				valueInlineTag();
				}
				break;
			case INHERIT_DOC:
				{
				setState(262);
				inheritDocInlineTag();
				}
				break;
			case SUMMARY:
				{
				setState(263);
				summaryInlineTag();
				}
				break;
			case SYSTEM_PROPERTY:
				{
				setState(264);
				systemPropertyInlineTag();
				}
				break;
			case INDEX:
				{
				setState(265);
				indexInlineTag();
				}
				break;
			case RETURN:
				{
				setState(266);
				returnInlineTag();
				}
				break;
			case LITERAL:
				{
				setState(267);
				literalInlineTag();
				}
				break;
			case SNIPPET:
				{
				setState(268);
				snippetInlineTag();
				}
				break;
			case CUSTOM_NAME:
				{
				setState(269);
				customInlineTag();
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
	public static class CodeInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode CODE() { return getToken(JavadocCommentsParser.CODE, 0); }
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public CodeInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codeInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitCodeInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeInlineTagContext codeInlineTag() throws RecognitionException {
		CodeInlineTagContext _localctx = new CodeInlineTagContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_codeInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			((CodeInlineTagContext)_localctx).tagName = match(CODE);
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TEXT) {
				{
				{
				setState(273);
				match(TEXT);
				}
				}
				setState(278);
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
	public static class LinkPlainInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode LINKPLAIN() { return getToken(JavadocCommentsParser.LINKPLAIN, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public LinkPlainInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linkPlainInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitLinkPlainInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinkPlainInlineTagContext linkPlainInlineTag() throws RecognitionException {
		LinkPlainInlineTagContext _localctx = new LinkPlainInlineTagContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_linkPlainInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			((LinkPlainInlineTagContext)_localctx).tagName = match(LINKPLAIN);
			setState(280);
			reference();
			setState(282);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(281);
				description();
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
	public static class LinkInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode LINK() { return getToken(JavadocCommentsParser.LINK, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public LinkInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linkInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitLinkInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinkInlineTagContext linkInlineTag() throws RecognitionException {
		LinkInlineTagContext _localctx = new LinkInlineTagContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_linkInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			((LinkInlineTagContext)_localctx).tagName = match(LINK);
			setState(285);
			reference();
			setState(287);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(286);
				description();
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
	public static class ValueInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode VALUE() { return getToken(JavadocCommentsParser.VALUE, 0); }
		public TerminalNode FORMAT_SPECIFIER() { return getToken(JavadocCommentsParser.FORMAT_SPECIFIER, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public ValueInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitValueInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueInlineTagContext valueInlineTag() throws RecognitionException {
		ValueInlineTagContext _localctx = new ValueInlineTagContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_valueInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(289);
			((ValueInlineTagContext)_localctx).tagName = match(VALUE);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FORMAT_SPECIFIER) {
				{
				setState(290);
				match(FORMAT_SPECIFIER);
				}
			}

			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER || _la==HASH) {
				{
				setState(293);
				reference();
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
	public static class InheritDocInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode INHERIT_DOC() { return getToken(JavadocCommentsParser.INHERIT_DOC, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public InheritDocInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritDocInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitInheritDocInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InheritDocInlineTagContext inheritDocInlineTag() throws RecognitionException {
		InheritDocInlineTagContext _localctx = new InheritDocInlineTagContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inheritDocInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			((InheritDocInlineTagContext)_localctx).tagName = match(INHERIT_DOC);
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER || _la==HASH) {
				{
				setState(297);
				reference();
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
	public static class SummaryInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode SUMMARY() { return getToken(JavadocCommentsParser.SUMMARY, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public SummaryInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_summaryInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSummaryInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SummaryInlineTagContext summaryInlineTag() throws RecognitionException {
		SummaryInlineTagContext _localctx = new SummaryInlineTagContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_summaryInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			((SummaryInlineTagContext)_localctx).tagName = match(SUMMARY);
			setState(302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(301);
				description();
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
	public static class SystemPropertyInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public QualifiedNameContext propertyName;
		public TerminalNode SYSTEM_PROPERTY() { return getToken(JavadocCommentsParser.SYSTEM_PROPERTY, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public SystemPropertyInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_systemPropertyInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSystemPropertyInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SystemPropertyInlineTagContext systemPropertyInlineTag() throws RecognitionException {
		SystemPropertyInlineTagContext _localctx = new SystemPropertyInlineTagContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_systemPropertyInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			((SystemPropertyInlineTagContext)_localctx).tagName = match(SYSTEM_PROPERTY);
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(305);
				((SystemPropertyInlineTagContext)_localctx).propertyName = qualifiedName();
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
	public static class IndexInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode INDEX_TERM() { return getToken(JavadocCommentsParser.INDEX_TERM, 0); }
		public TerminalNode INDEX() { return getToken(JavadocCommentsParser.INDEX, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public IndexInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitIndexInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexInlineTagContext indexInlineTag() throws RecognitionException {
		IndexInlineTagContext _localctx = new IndexInlineTagContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_indexInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			((IndexInlineTagContext)_localctx).tagName = match(INDEX);
			setState(309);
			match(INDEX_TERM);
			setState(311);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(310);
				description();
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
	public static class ReturnInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode RETURN() { return getToken(JavadocCommentsParser.RETURN, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public ReturnInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitReturnInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnInlineTagContext returnInlineTag() throws RecognitionException {
		ReturnInlineTagContext _localctx = new ReturnInlineTagContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_returnInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			((ReturnInlineTagContext)_localctx).tagName = match(RETURN);
			setState(315);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(314);
				description();
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
	public static class LiteralInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode LITERAL() { return getToken(JavadocCommentsParser.LITERAL, 0); }
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public LiteralInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitLiteralInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralInlineTagContext literalInlineTag() throws RecognitionException {
		LiteralInlineTagContext _localctx = new LiteralInlineTagContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_literalInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			((LiteralInlineTagContext)_localctx).tagName = match(LITERAL);
			setState(319); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(318);
				match(TEXT);
				}
				}
				setState(321); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TEXT );
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
	public static class SnippetInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public SnippetAttributeContext snippetAttribute;
		public List<SnippetAttributeContext> snippetAttributes = new ArrayList<SnippetAttributeContext>();
		public TerminalNode SNIPPET() { return getToken(JavadocCommentsParser.SNIPPET, 0); }
		public TerminalNode COLON() { return getToken(JavadocCommentsParser.COLON, 0); }
		public SnippetBodyContext snippetBody() {
			return getRuleContext(SnippetBodyContext.class,0);
		}
		public List<SnippetAttributeContext> snippetAttribute() {
			return getRuleContexts(SnippetAttributeContext.class);
		}
		public SnippetAttributeContext snippetAttribute(int i) {
			return getRuleContext(SnippetAttributeContext.class,i);
		}
		public SnippetInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_snippetInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSnippetInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SnippetInlineTagContext snippetInlineTag() throws RecognitionException {
		SnippetInlineTagContext _localctx = new SnippetInlineTagContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_snippetInlineTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			((SnippetInlineTagContext)_localctx).tagName = match(SNIPPET);
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SNIPPET_ATTR_NAME) {
				{
				{
				setState(324);
				((SnippetInlineTagContext)_localctx).snippetAttribute = snippetAttribute();
				((SnippetInlineTagContext)_localctx).snippetAttributes.add(((SnippetInlineTagContext)_localctx).snippetAttribute);
				}
				}
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(332);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(330);
				match(COLON);
				setState(331);
				snippetBody();
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
	public static class CustomInlineTagContext extends ParserRuleContext {
		public Token tagName;
		public TerminalNode CUSTOM_NAME() { return getToken(JavadocCommentsParser.CUSTOM_NAME, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public CustomInlineTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_customInlineTag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitCustomInlineTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CustomInlineTagContext customInlineTag() throws RecognitionException {
		CustomInlineTagContext _localctx = new CustomInlineTagContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_customInlineTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			((CustomInlineTagContext)_localctx).tagName = match(CUSTOM_NAME);
			setState(336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(335);
				description();
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
	public static class ReferenceContext extends ParserRuleContext {
		public QualifiedNameContext module;
		public TypeNameContext type;
		public TerminalNode HASH() { return getToken(JavadocCommentsParser.HASH, 0); }
		public MemberReferenceContext memberReference() {
			return getRuleContext(MemberReferenceContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode SLASH() { return getToken(JavadocCommentsParser.SLASH, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_reference);
		try {
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HASH:
				enterOuterAlt(_localctx, 1);
				{
				setState(338);
				match(HASH);
				setState(339);
				memberReference();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(343);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(340);
					((ReferenceContext)_localctx).module = qualifiedName();
					setState(341);
					match(SLASH);
					}
					break;
				}
				setState(345);
				((ReferenceContext)_localctx).type = typeName();
				setState(348);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
				case 1:
					{
					setState(346);
					match(HASH);
					setState(347);
					memberReference();
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
	public static class TypeNameContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_typeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			qualifiedName();
			setState(354);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(353);
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
	public static class QualifiedNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JavadocCommentsParser.IDENTIFIER, 0); }
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_qualifiedName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			match(IDENTIFIER);
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
		public TerminalNode LT() { return getToken(JavadocCommentsParser.LT, 0); }
		public List<TypeArgumentContext> typeArgument() {
			return getRuleContexts(TypeArgumentContext.class);
		}
		public TypeArgumentContext typeArgument(int i) {
			return getRuleContext(TypeArgumentContext.class,i);
		}
		public TerminalNode GT() { return getToken(JavadocCommentsParser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavadocCommentsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavadocCommentsParser.COMMA, i);
		}
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			match(LT);
			setState(359);
			typeArgument();
			setState(364);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(360);
				match(COMMA);
				setState(361);
				typeArgument();
				}
				}
				setState(366);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(367);
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
	public static class TypeArgumentContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(JavadocCommentsParser.QUESTION, 0); }
		public TerminalNode EXTENDS() { return getToken(JavadocCommentsParser.EXTENDS, 0); }
		public TerminalNode SUPER() { return getToken(JavadocCommentsParser.SUPER, 0); }
		public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentContext typeArgument() throws RecognitionException {
		TypeArgumentContext _localctx = new TypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_typeArgument);
		try {
			setState(377);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(369);
				typeName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(370);
				match(QUESTION);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(371);
				match(QUESTION);
				setState(372);
				match(EXTENDS);
				setState(373);
				typeName();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(374);
				match(QUESTION);
				setState(375);
				match(SUPER);
				setState(376);
				typeName();
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
	public static class MemberReferenceContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JavadocCommentsParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(JavadocCommentsParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavadocCommentsParser.RPAREN, 0); }
		public ParameterTypeListContext parameterTypeList() {
			return getRuleContext(ParameterTypeListContext.class,0);
		}
		public MemberReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitMemberReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberReferenceContext memberReference() throws RecognitionException {
		MemberReferenceContext _localctx = new MemberReferenceContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_memberReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			match(IDENTIFIER);
			setState(385);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(380);
				match(LPAREN);
				setState(382);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARAMETER_TYPE) {
					{
					setState(381);
					parameterTypeList();
					}
				}

				setState(384);
				match(RPAREN);
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
	public static class ParameterTypeListContext extends ParserRuleContext {
		public List<TerminalNode> PARAMETER_TYPE() { return getTokens(JavadocCommentsParser.PARAMETER_TYPE); }
		public TerminalNode PARAMETER_TYPE(int i) {
			return getToken(JavadocCommentsParser.PARAMETER_TYPE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavadocCommentsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavadocCommentsParser.COMMA, i);
		}
		public ParameterTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterTypeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitParameterTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterTypeListContext parameterTypeList() throws RecognitionException {
		ParameterTypeListContext _localctx = new ParameterTypeListContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_parameterTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(PARAMETER_TYPE);
			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==PARAMETER_TYPE) {
				{
				{
				setState(389);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(388);
					match(COMMA);
					}
				}

				setState(391);
				match(PARAMETER_TYPE);
				}
				}
				setState(396);
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
	public static class SnippetAttributeContext extends ParserRuleContext {
		public TerminalNode SNIPPET_ATTR_NAME() { return getToken(JavadocCommentsParser.SNIPPET_ATTR_NAME, 0); }
		public TerminalNode EQUALS() { return getToken(JavadocCommentsParser.EQUALS, 0); }
		public TerminalNode ATTRIBUTE_VALUE() { return getToken(JavadocCommentsParser.ATTRIBUTE_VALUE, 0); }
		public SnippetAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_snippetAttribute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSnippetAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SnippetAttributeContext snippetAttribute() throws RecognitionException {
		SnippetAttributeContext _localctx = new SnippetAttributeContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_snippetAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(SNIPPET_ATTR_NAME);
			setState(398);
			match(EQUALS);
			setState(399);
			match(ATTRIBUTE_VALUE);
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
	public static class SnippetBodyContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public SnippetBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_snippetBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSnippetBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SnippetBodyContext snippetBody() throws RecognitionException {
		SnippetBodyContext _localctx = new SnippetBodyContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_snippetBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(401);
				match(TEXT);
				}
				}
				setState(404); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TEXT );
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
	public static class DescriptionContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public List<InlineTagContext> inlineTag() {
			return getRuleContexts(InlineTagContext.class);
		}
		public InlineTagContext inlineTag(int i) {
			return getRuleContext(InlineTagContext.class,i);
		}
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public List<HtmlCommentContext> htmlComment() {
			return getRuleContexts(HtmlCommentContext.class);
		}
		public HtmlCommentContext htmlComment(int i) {
			return getRuleContext(HtmlCommentContext.class,i);
		}
		public DescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_description; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitDescription(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptionContext description() throws RecognitionException {
		DescriptionContext _localctx = new DescriptionContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_description);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(410); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(410);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
					case 1:
						{
						setState(406);
						match(TEXT);
						}
						break;
					case 2:
						{
						setState(407);
						inlineTag();
						}
						break;
					case 3:
						{
						setState(408);
						htmlElement();
						}
						break;
					case 4:
						{
						setState(409);
						htmlComment();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(412); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
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
	public static class HtmlElementContext extends ParserRuleContext {
		public VoidElementContext voidElement() {
			return getRuleContext(VoidElementContext.class,0);
		}
		public SelfClosingElementContext selfClosingElement() {
			return getRuleContext(SelfClosingElementContext.class,0);
		}
		public TightElementContext tightElement() {
			return getRuleContext(TightElementContext.class,0);
		}
		public NonTightElementContext nonTightElement() {
			return getRuleContext(NonTightElementContext.class,0);
		}
		public HtmlElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlElementContext htmlElement() throws RecognitionException {
		HtmlElementContext _localctx = new HtmlElementContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_htmlElement);
		try {
			setState(418);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(414);
				voidElement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				selfClosingElement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(416);
				tightElement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(417);
				nonTightElement();
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
	public static class VoidElementContext extends ParserRuleContext {
		public HtmlTagStartContext htmlTagStart() {
			return getRuleContext(HtmlTagStartContext.class,0);
		}
		public VoidElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voidElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitVoidElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VoidElementContext voidElement() throws RecognitionException {
		VoidElementContext _localctx = new VoidElementContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_voidElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(420);
			if (!( isVoidTag() )) throw new FailedPredicateException(this, " isVoidTag() ");
			setState(421);
			htmlTagStart();
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
	public static class TightElementContext extends ParserRuleContext {
		public HtmlTagStartContext htmlTagStart() {
			return getRuleContext(HtmlTagStartContext.class,0);
		}
		public HtmlTagEndContext htmlTagEnd() {
			return getRuleContext(HtmlTagEndContext.class,0);
		}
		public HtmlContentContext htmlContent() {
			return getRuleContext(HtmlContentContext.class,0);
		}
		public TightElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tightElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitTightElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TightElementContext tightElement() throws RecognitionException {
		TightElementContext _localctx = new TightElementContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_tightElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			if (!( !JavadocCommentsParserUtil.isNonTightTag(_input, unclosedTagNameTokens) )) throw new FailedPredicateException(this, " !JavadocCommentsParserUtil.isNonTightTag(_input, unclosedTagNameTokens) ");
			setState(424);
			htmlTagStart();
			setState(426);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				{
				setState(425);
				htmlContent();
				}
				break;
			}
			setState(428);
			htmlTagEnd();
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
	public static class NonTightElementContext extends ParserRuleContext {
		public HtmlTagStartContext htmlTagStart() {
			return getRuleContext(HtmlTagStartContext.class,0);
		}
		public NonTightHtmlContentContext nonTightHtmlContent() {
			return getRuleContext(NonTightHtmlContentContext.class,0);
		}
		public NonTightElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonTightElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitNonTightElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTightElementContext nonTightElement() throws RecognitionException {
		NonTightElementContext _localctx = new NonTightElementContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_nonTightElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			htmlTagStart();
			setState(432);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				{
				setState(431);
				nonTightHtmlContent();
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
	public static class SelfClosingElementContext extends ParserRuleContext {
		public HtmlAttributeContext htmlAttribute;
		public List<HtmlAttributeContext> htmlAttributes = new ArrayList<HtmlAttributeContext>();
		public TerminalNode TAG_OPEN() { return getToken(JavadocCommentsParser.TAG_OPEN, 0); }
		public TerminalNode TAG_NAME() { return getToken(JavadocCommentsParser.TAG_NAME, 0); }
		public TerminalNode TAG_SLASH_CLOSE() { return getToken(JavadocCommentsParser.TAG_SLASH_CLOSE, 0); }
		public List<HtmlAttributeContext> htmlAttribute() {
			return getRuleContexts(HtmlAttributeContext.class);
		}
		public HtmlAttributeContext htmlAttribute(int i) {
			return getRuleContext(HtmlAttributeContext.class,i);
		}
		public SelfClosingElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selfClosingElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitSelfClosingElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelfClosingElementContext selfClosingElement() throws RecognitionException {
		SelfClosingElementContext _localctx = new SelfClosingElementContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_selfClosingElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			match(TAG_OPEN);
			setState(435);
			match(TAG_NAME);
			setState(439);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TAG_ATTR_NAME) {
				{
				{
				setState(436);
				((SelfClosingElementContext)_localctx).htmlAttribute = htmlAttribute();
				((SelfClosingElementContext)_localctx).htmlAttributes.add(((SelfClosingElementContext)_localctx).htmlAttribute);
				}
				}
				setState(441);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(442);
			match(TAG_SLASH_CLOSE);
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
	public static class HtmlTagStartContext extends ParserRuleContext {
		public HtmlAttributeContext htmlAttribute;
		public List<HtmlAttributeContext> htmlAttributes = new ArrayList<HtmlAttributeContext>();
		public TerminalNode TAG_OPEN() { return getToken(JavadocCommentsParser.TAG_OPEN, 0); }
		public TerminalNode TAG_NAME() { return getToken(JavadocCommentsParser.TAG_NAME, 0); }
		public TerminalNode TAG_CLOSE() { return getToken(JavadocCommentsParser.TAG_CLOSE, 0); }
		public List<HtmlAttributeContext> htmlAttribute() {
			return getRuleContexts(HtmlAttributeContext.class);
		}
		public HtmlAttributeContext htmlAttribute(int i) {
			return getRuleContext(HtmlAttributeContext.class,i);
		}
		public HtmlTagStartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlTagStart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlTagStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlTagStartContext htmlTagStart() throws RecognitionException {
		HtmlTagStartContext _localctx = new HtmlTagStartContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_htmlTagStart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			match(TAG_OPEN);
			setState(445);
			match(TAG_NAME);
			setState(449);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TAG_ATTR_NAME) {
				{
				{
				setState(446);
				((HtmlTagStartContext)_localctx).htmlAttribute = htmlAttribute();
				((HtmlTagStartContext)_localctx).htmlAttributes.add(((HtmlTagStartContext)_localctx).htmlAttribute);
				}
				}
				setState(451);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(452);
			match(TAG_CLOSE);
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
	public static class HtmlTagEndContext extends ParserRuleContext {
		public TerminalNode TAG_OPEN() { return getToken(JavadocCommentsParser.TAG_OPEN, 0); }
		public TerminalNode TAG_SLASH() { return getToken(JavadocCommentsParser.TAG_SLASH, 0); }
		public TerminalNode TAG_NAME() { return getToken(JavadocCommentsParser.TAG_NAME, 0); }
		public TerminalNode TAG_CLOSE() { return getToken(JavadocCommentsParser.TAG_CLOSE, 0); }
		public HtmlTagEndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlTagEnd; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlTagEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlTagEndContext htmlTagEnd() throws RecognitionException {
		HtmlTagEndContext _localctx = new HtmlTagEndContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_htmlTagEnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(454);
			match(TAG_OPEN);
			setState(455);
			match(TAG_SLASH);
			setState(456);
			match(TAG_NAME);
			setState(457);
			match(TAG_CLOSE);
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
	public static class HtmlAttributeContext extends ParserRuleContext {
		public TerminalNode TAG_ATTR_NAME() { return getToken(JavadocCommentsParser.TAG_ATTR_NAME, 0); }
		public TerminalNode EQUALS() { return getToken(JavadocCommentsParser.EQUALS, 0); }
		public TerminalNode ATTRIBUTE_VALUE() { return getToken(JavadocCommentsParser.ATTRIBUTE_VALUE, 0); }
		public HtmlAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlAttribute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlAttributeContext htmlAttribute() throws RecognitionException {
		HtmlAttributeContext _localctx = new HtmlAttributeContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_htmlAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(459);
			match(TAG_ATTR_NAME);
			setState(462);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUALS) {
				{
				setState(460);
				match(EQUALS);
				setState(461);
				match(ATTRIBUTE_VALUE);
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
	public static class HtmlContentContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public List<InlineTagContext> inlineTag() {
			return getRuleContexts(InlineTagContext.class);
		}
		public InlineTagContext inlineTag(int i) {
			return getRuleContext(InlineTagContext.class,i);
		}
		public List<HtmlCommentContext> htmlComment() {
			return getRuleContexts(HtmlCommentContext.class);
		}
		public HtmlCommentContext htmlComment(int i) {
			return getRuleContext(HtmlCommentContext.class,i);
		}
		public HtmlContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlContent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlContentContext htmlContent() throws RecognitionException {
		HtmlContentContext _localctx = new HtmlContentContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_htmlContent);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(468); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(468);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
					case 1:
						{
						setState(464);
						match(TEXT);
						}
						break;
					case 2:
						{
						setState(465);
						htmlElement();
						}
						break;
					case 3:
						{
						setState(466);
						inlineTag();
						}
						break;
					case 4:
						{
						setState(467);
						htmlComment();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(470); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
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
	public static class NonTightHtmlContentContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public List<InlineTagContext> inlineTag() {
			return getRuleContexts(InlineTagContext.class);
		}
		public InlineTagContext inlineTag(int i) {
			return getRuleContext(InlineTagContext.class,i);
		}
		public NonTightHtmlContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonTightHtmlContent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitNonTightHtmlContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTightHtmlContentContext nonTightHtmlContent() throws RecognitionException {
		NonTightHtmlContentContext _localctx = new NonTightHtmlContentContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_nonTightHtmlContent);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(474); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(474);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case TEXT:
						{
						setState(472);
						match(TEXT);
						}
						break;
					case JAVADOC_INLINE_TAG_START:
						{
						setState(473);
						inlineTag();
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
				setState(476); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
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
	public static class HtmlCommentContext extends ParserRuleContext {
		public TerminalNode HTML_COMMENT_START() { return getToken(JavadocCommentsParser.HTML_COMMENT_START, 0); }
		public HtmlCommentContentContext htmlCommentContent() {
			return getRuleContext(HtmlCommentContentContext.class,0);
		}
		public TerminalNode HTML_COMMENT_END() { return getToken(JavadocCommentsParser.HTML_COMMENT_END, 0); }
		public HtmlCommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlComment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlCommentContext htmlComment() throws RecognitionException {
		HtmlCommentContext _localctx = new HtmlCommentContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_htmlComment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			match(HTML_COMMENT_START);
			setState(479);
			htmlCommentContent();
			setState(480);
			match(HTML_COMMENT_END);
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
	public static class HtmlCommentContentContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(JavadocCommentsParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(JavadocCommentsParser.TEXT, i);
		}
		public HtmlCommentContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlCommentContent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavadocCommentsParserVisitor ) return ((JavadocCommentsParserVisitor<? extends T>)visitor).visitHtmlCommentContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlCommentContentContext htmlCommentContent() throws RecognitionException {
		HtmlCommentContentContext _localctx = new HtmlCommentContentContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_htmlCommentContent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TEXT) {
				{
				{
				setState(482);
				match(TEXT);
				}
				}
				setState(487);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 44:
			return voidElement_sempred((VoidElementContext)_localctx, predIndex);
		case 45:
			return tightElement_sempred((TightElementContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean voidElement_sempred(VoidElementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  isVoidTag() ;
		}
		return true;
	}
	private boolean tightElement_sempred(TightElementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return  !JavadocCommentsParserUtil.isNonTightTag(_input, unclosedTagNameTokens) ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001t\u01e9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0001"+
		"\u0000\u0003\u0000p\b\u0000\u0001\u0000\u0005\u0000s\b\u0000\n\u0000\f"+
		"\u0000v\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0004\u0001~\b\u0001\u000b\u0001\f\u0001\u007f\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002\u0092\b\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u0097\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0003\u0004\u009c\b\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005\u00a1\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u00a6\b\u0006\u0001\u0006\u0003\u0006\u00a9\b\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0003\u0007\u00ae\b\u0007\u0001\u0007\u0003\u0007"+
		"\u00b1\b\u0007\u0001\b\u0001\b\u0001\b\u0003\b\u00b6\b\b\u0001\b\u0003"+
		"\b\u00b9\b\b\u0001\t\u0001\t\u0001\t\u0003\t\u00be\b\t\u0001\n\u0001\n"+
		"\u0001\n\u0003\n\u00c3\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u00cb\b\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0003\u000b\u00d0\b\u000b\u0003\u000b\u00d2\b\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0003\f\u00d7\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u00dd\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00e3\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00e8\b"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00ed\b\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00f2\b\u0011\u0001\u0011\u0003"+
		"\u0011\u00f5\b\u0011\u0001\u0011\u0003\u0011\u00f8\b\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0003\u0012\u00fd\b\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u010f\b\u0014\u0001\u0015\u0001\u0015"+
		"\u0005\u0015\u0113\b\u0015\n\u0015\f\u0015\u0116\t\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0003\u0016\u011b\b\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0003\u0017\u0120\b\u0017\u0001\u0018\u0001\u0018\u0003\u0018\u0124"+
		"\b\u0018\u0001\u0018\u0003\u0018\u0127\b\u0018\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u012b\b\u0019\u0001\u001a\u0001\u001a\u0003\u001a\u012f\b"+
		"\u001a\u0001\u001b\u0001\u001b\u0003\u001b\u0133\b\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0003\u001c\u0138\b\u001c\u0001\u001d\u0001\u001d\u0003"+
		"\u001d\u013c\b\u001d\u0001\u001e\u0001\u001e\u0004\u001e\u0140\b\u001e"+
		"\u000b\u001e\f\u001e\u0141\u0001\u001f\u0001\u001f\u0005\u001f\u0146\b"+
		"\u001f\n\u001f\f\u001f\u0149\t\u001f\u0001\u001f\u0001\u001f\u0003\u001f"+
		"\u014d\b\u001f\u0001 \u0001 \u0003 \u0151\b \u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0003!\u0158\b!\u0001!\u0001!\u0001!\u0003!\u015d\b!\u0003!\u015f"+
		"\b!\u0001\"\u0001\"\u0003\"\u0163\b\"\u0001#\u0001#\u0001$\u0001$\u0001"+
		"$\u0001$\u0005$\u016b\b$\n$\f$\u016e\t$\u0001$\u0001$\u0001%\u0001%\u0001"+
		"%\u0001%\u0001%\u0001%\u0001%\u0001%\u0003%\u017a\b%\u0001&\u0001&\u0001"+
		"&\u0003&\u017f\b&\u0001&\u0003&\u0182\b&\u0001\'\u0001\'\u0003\'\u0186"+
		"\b\'\u0001\'\u0005\'\u0189\b\'\n\'\f\'\u018c\t\'\u0001(\u0001(\u0001("+
		"\u0001(\u0001)\u0004)\u0193\b)\u000b)\f)\u0194\u0001*\u0001*\u0001*\u0001"+
		"*\u0004*\u019b\b*\u000b*\f*\u019c\u0001+\u0001+\u0001+\u0001+\u0003+\u01a3"+
		"\b+\u0001,\u0001,\u0001,\u0001-\u0001-\u0001-\u0003-\u01ab\b-\u0001-\u0001"+
		"-\u0001.\u0001.\u0003.\u01b1\b.\u0001/\u0001/\u0001/\u0005/\u01b6\b/\n"+
		"/\f/\u01b9\t/\u0001/\u0001/\u00010\u00010\u00010\u00050\u01c0\b0\n0\f"+
		"0\u01c3\t0\u00010\u00010\u00011\u00011\u00011\u00011\u00011\u00012\u0001"+
		"2\u00012\u00032\u01cf\b2\u00013\u00013\u00013\u00013\u00043\u01d5\b3\u000b"+
		"3\f3\u01d6\u00014\u00014\u00044\u01db\b4\u000b4\f4\u01dc\u00015\u0001"+
		"5\u00015\u00015\u00016\u00056\u01e4\b6\n6\f6\u01e7\t6\u00016\u0000\u0000"+
		"7\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjl\u0000\u0000\u0217"+
		"\u0000o\u0001\u0000\u0000\u0000\u0002}\u0001\u0000\u0000\u0000\u0004\u0091"+
		"\u0001\u0000\u0000\u0000\u0006\u0093\u0001\u0000\u0000\u0000\b\u0098\u0001"+
		"\u0000\u0000\u0000\n\u009d\u0001\u0000\u0000\u0000\f\u00a2\u0001\u0000"+
		"\u0000\u0000\u000e\u00aa\u0001\u0000\u0000\u0000\u0010\u00b2\u0001\u0000"+
		"\u0000\u0000\u0012\u00ba\u0001\u0000\u0000\u0000\u0014\u00bf\u0001\u0000"+
		"\u0000\u0000\u0016\u00c4\u0001\u0000\u0000\u0000\u0018\u00d3\u0001\u0000"+
		"\u0000\u0000\u001a\u00d8\u0001\u0000\u0000\u0000\u001c\u00de\u0001\u0000"+
		"\u0000\u0000\u001e\u00e4\u0001\u0000\u0000\u0000 \u00e9\u0001\u0000\u0000"+
		"\u0000\"\u00ee\u0001\u0000\u0000\u0000$\u00f9\u0001\u0000\u0000\u0000"+
		"&\u00fe\u0001\u0000\u0000\u0000(\u010e\u0001\u0000\u0000\u0000*\u0110"+
		"\u0001\u0000\u0000\u0000,\u0117\u0001\u0000\u0000\u0000.\u011c\u0001\u0000"+
		"\u0000\u00000\u0121\u0001\u0000\u0000\u00002\u0128\u0001\u0000\u0000\u0000"+
		"4\u012c\u0001\u0000\u0000\u00006\u0130\u0001\u0000\u0000\u00008\u0134"+
		"\u0001\u0000\u0000\u0000:\u0139\u0001\u0000\u0000\u0000<\u013d\u0001\u0000"+
		"\u0000\u0000>\u0143\u0001\u0000\u0000\u0000@\u014e\u0001\u0000\u0000\u0000"+
		"B\u015e\u0001\u0000\u0000\u0000D\u0160\u0001\u0000\u0000\u0000F\u0164"+
		"\u0001\u0000\u0000\u0000H\u0166\u0001\u0000\u0000\u0000J\u0179\u0001\u0000"+
		"\u0000\u0000L\u017b\u0001\u0000\u0000\u0000N\u0183\u0001\u0000\u0000\u0000"+
		"P\u018d\u0001\u0000\u0000\u0000R\u0192\u0001\u0000\u0000\u0000T\u019a"+
		"\u0001\u0000\u0000\u0000V\u01a2\u0001\u0000\u0000\u0000X\u01a4\u0001\u0000"+
		"\u0000\u0000Z\u01a7\u0001\u0000\u0000\u0000\\\u01ae\u0001\u0000\u0000"+
		"\u0000^\u01b2\u0001\u0000\u0000\u0000`\u01bc\u0001\u0000\u0000\u0000b"+
		"\u01c6\u0001\u0000\u0000\u0000d\u01cb\u0001\u0000\u0000\u0000f\u01d4\u0001"+
		"\u0000\u0000\u0000h\u01da\u0001\u0000\u0000\u0000j\u01de\u0001\u0000\u0000"+
		"\u0000l\u01e5\u0001\u0000\u0000\u0000np\u0003\u0002\u0001\u0000on\u0001"+
		"\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pt\u0001\u0000\u0000\u0000"+
		"qs\u0003\u0004\u0002\u0000rq\u0001\u0000\u0000\u0000sv\u0001\u0000\u0000"+
		"\u0000tr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000uw\u0001\u0000"+
		"\u0000\u0000vt\u0001\u0000\u0000\u0000wx\u0005\u0000\u0000\u0001x\u0001"+
		"\u0001\u0000\u0000\u0000y~\u0005\u0004\u0000\u0000z~\u0003&\u0013\u0000"+
		"{~\u0003V+\u0000|~\u0003j5\u0000}y\u0001\u0000\u0000\u0000}z\u0001\u0000"+
		"\u0000\u0000}{\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\u007f"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001"+
		"\u0000\u0000\u0000\u0080\u0003\u0001\u0000\u0000\u0000\u0081\u0092\u0003"+
		"\u0006\u0003\u0000\u0082\u0092\u0003\b\u0004\u0000\u0083\u0092\u0003\n"+
		"\u0005\u0000\u0084\u0092\u0003\f\u0006\u0000\u0085\u0092\u0003\u000e\u0007"+
		"\u0000\u0086\u0092\u0003\u0010\b\u0000\u0087\u0092\u0003\u0012\t\u0000"+
		"\u0088\u0092\u0003\u0014\n\u0000\u0089\u0092\u0003\u0016\u000b\u0000\u008a"+
		"\u0092\u0003\u0018\f\u0000\u008b\u0092\u0003\u001a\r\u0000\u008c\u0092"+
		"\u0003\u001c\u000e\u0000\u008d\u0092\u0003\u001e\u000f\u0000\u008e\u0092"+
		"\u0003 \u0010\u0000\u008f\u0092\u0003\"\u0011\u0000\u0090\u0092\u0003"+
		"$\u0012\u0000\u0091\u0081\u0001\u0000\u0000\u0000\u0091\u0082\u0001\u0000"+
		"\u0000\u0000\u0091\u0083\u0001\u0000\u0000\u0000\u0091\u0084\u0001\u0000"+
		"\u0000\u0000\u0091\u0085\u0001\u0000\u0000\u0000\u0091\u0086\u0001\u0000"+
		"\u0000\u0000\u0091\u0087\u0001\u0000\u0000\u0000\u0091\u0088\u0001\u0000"+
		"\u0000\u0000\u0091\u0089\u0001\u0000\u0000\u0000\u0091\u008a\u0001\u0000"+
		"\u0000\u0000\u0091\u008b\u0001\u0000\u0000\u0000\u0091\u008c\u0001\u0000"+
		"\u0000\u0000\u0091\u008d\u0001\u0000\u0000\u0000\u0091\u008e\u0001\u0000"+
		"\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0090\u0001\u0000"+
		"\u0000\u0000\u0092\u0005\u0001\u0000\u0000\u0000\u0093\u0094\u00059\u0000"+
		"\u0000\u0094\u0096\u0005\u0011\u0000\u0000\u0095\u0097\u0003T*\u0000\u0096"+
		"\u0095\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097"+
		"\u0007\u0001\u0000\u0000\u0000\u0098\u0099\u00059\u0000\u0000\u0099\u009b"+
		"\u0005\u0012\u0000\u0000\u009a\u009c\u0003T*\u0000\u009b\u009a\u0001\u0000"+
		"\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\t\u0001\u0000\u0000"+
		"\u0000\u009d\u009e\u00059\u0000\u0000\u009e\u00a0\u0005\u0013\u0000\u0000"+
		"\u009f\u00a1\u0003T*\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0001\u0000\u0000\u0000\u00a1\u000b\u0001\u0000\u0000\u0000\u00a2\u00a3"+
		"\u00059\u0000\u0000\u00a3\u00a5\u0005\u0014\u0000\u0000\u00a4\u00a6\u0005"+
		"/\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a8\u0001\u0000\u0000\u0000\u00a7\u00a9\u0003T*\u0000"+
		"\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000"+
		"\u00a9\r\u0001\u0000\u0000\u0000\u00aa\u00ab\u00059\u0000\u0000\u00ab"+
		"\u00ad\u0005.\u0000\u0000\u00ac\u00ae\u0003F#\u0000\u00ad\u00ac\u0001"+
		"\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00b0\u0001"+
		"\u0000\u0000\u0000\u00af\u00b1\u0003T*\u0000\u00b0\u00af\u0001\u0000\u0000"+
		"\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u000f\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b3\u00059\u0000\u0000\u00b3\u00b5\u0005-\u0000\u0000\u00b4"+
		"\u00b6\u0003F#\u0000\u00b5\u00b4\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b8\u0001\u0000\u0000\u0000\u00b7\u00b9\u0003"+
		"T*\u0000\u00b8\u00b7\u0001\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000"+
		"\u0000\u00b9\u0011\u0001\u0000\u0000\u0000\u00ba\u00bb\u00059\u0000\u0000"+
		"\u00bb\u00bd\u00050\u0000\u0000\u00bc\u00be\u0003T*\u0000\u00bd\u00bc"+
		"\u0001\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u0013"+
		"\u0001\u0000\u0000\u0000\u00bf\u00c0\u00059\u0000\u0000\u00c0\u00c2\u0005"+
		"1\u0000\u0000\u00c1\u00c3\u0003T*\u0000\u00c2\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u0015\u0001\u0000\u0000"+
		"\u0000\u00c4\u00d1\u00059\u0000\u0000\u00c5\u00c6\u00052\u0000\u0000\u00c6"+
		"\u00d2\u00053\u0000\u0000\u00c7\u00c8\u00052\u0000\u0000\u00c8\u00ca\u0003"+
		"B!\u0000\u00c9\u00cb\u0003T*\u0000\u00ca\u00c9\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00d2\u0001\u0000\u0000\u0000"+
		"\u00cc\u00cd\u00052\u0000\u0000\u00cd\u00cf\u0003V+\u0000\u00ce\u00d0"+
		"\u0003T*\u0000\u00cf\u00ce\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00c5\u0001\u0000"+
		"\u0000\u0000\u00d1\u00c7\u0001\u0000\u0000\u0000\u00d1\u00cc\u0001\u0000"+
		"\u0000\u0000\u00d2\u0017\u0001\u0000\u0000\u0000\u00d3\u00d4\u00059\u0000"+
		"\u0000\u00d4\u00d6\u00054\u0000\u0000\u00d5\u00d7\u0003T*\u0000\u00d6"+
		"\u00d5\u0001\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7"+
		"\u0019\u0001\u0000\u0000\u0000\u00d8\u00d9\u00059\u0000\u0000\u00d9\u00da"+
		"\u0005l\u0000\u0000\u00da\u00dc\u0003F#\u0000\u00db\u00dd\u0003T*\u0000"+
		"\u00dc\u00db\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000"+
		"\u00dd\u001b\u0001\u0000\u0000\u0000\u00de\u00df\u00059\u0000\u0000\u00df"+
		"\u00e0\u0005m\u0000\u0000\u00e0\u00e2\u0003F#\u0000\u00e1\u00e3\u0003"+
		"T*\u0000\u00e2\u00e1\u0001\u0000\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000"+
		"\u0000\u00e3\u001d\u0001\u0000\u0000\u0000\u00e4\u00e5\u00059\u0000\u0000"+
		"\u00e5\u00e7\u00055\u0000\u0000\u00e6\u00e8\u0003T*\u0000\u00e7\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8\u001f"+
		"\u0001\u0000\u0000\u0000\u00e9\u00ea\u00059\u0000\u0000\u00ea\u00ec\u0005"+
		"6\u0000\u0000\u00eb\u00ed\u0003T*\u0000\u00ec\u00eb\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed!\u0001\u0000\u0000\u0000"+
		"\u00ee\u00ef\u00059\u0000\u0000\u00ef\u00f1\u00057\u0000\u0000\u00f0\u00f2"+
		"\u0005\u000b\u0000\u0000\u00f1\u00f0\u0001\u0000\u0000\u0000\u00f1\u00f2"+
		"\u0001\u0000\u0000\u0000\u00f2\u00f4\u0001\u0000\u0000\u0000\u00f3\u00f5"+
		"\u00058\u0000\u0000\u00f4\u00f3\u0001\u0000\u0000\u0000\u00f4\u00f5\u0001"+
		"\u0000\u0000\u0000\u00f5\u00f7\u0001\u0000\u0000\u0000\u00f6\u00f8\u0003"+
		"T*\u0000\u00f7\u00f6\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000"+
		"\u0000\u00f8#\u0001\u0000\u0000\u0000\u00f9\u00fa\u00059\u0000\u0000\u00fa"+
		"\u00fc\u0005o\u0000\u0000\u00fb\u00fd\u0003T*\u0000\u00fc\u00fb\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fd%\u0001\u0000"+
		"\u0000\u0000\u00fe\u00ff\u0005\u0007\u0000\u0000\u00ff\u0100\u0003(\u0014"+
		"\u0000\u0100\u0101\u0005\b\u0000\u0000\u0101\'\u0001\u0000\u0000\u0000"+
		"\u0102\u010f\u0003*\u0015\u0000\u0103\u010f\u0003.\u0017\u0000\u0104\u010f"+
		"\u0003,\u0016\u0000\u0105\u010f\u00030\u0018\u0000\u0106\u010f\u00032"+
		"\u0019\u0000\u0107\u010f\u00034\u001a\u0000\u0108\u010f\u00036\u001b\u0000"+
		"\u0109\u010f\u00038\u001c\u0000\u010a\u010f\u0003:\u001d\u0000\u010b\u010f"+
		"\u0003<\u001e\u0000\u010c\u010f\u0003>\u001f\u0000\u010d\u010f\u0003@"+
		" \u0000\u010e\u0102\u0001\u0000\u0000\u0000\u010e\u0103\u0001\u0000\u0000"+
		"\u0000\u010e\u0104\u0001\u0000\u0000\u0000\u010e\u0105\u0001\u0000\u0000"+
		"\u0000\u010e\u0106\u0001\u0000\u0000\u0000\u010e\u0107\u0001\u0000\u0000"+
		"\u0000\u010e\u0108\u0001\u0000\u0000\u0000\u010e\u0109\u0001\u0000\u0000"+
		"\u0000\u010e\u010a\u0001\u0000\u0000\u0000\u010e\u010b\u0001\u0000\u0000"+
		"\u0000\u010e\u010c\u0001\u0000\u0000\u0000\u010e\u010d\u0001\u0000\u0000"+
		"\u0000\u010f)\u0001\u0000\u0000\u0000\u0110\u0114\u0005\t\u0000\u0000"+
		"\u0111\u0113\u0005\u0004\u0000\u0000\u0112\u0111\u0001\u0000\u0000\u0000"+
		"\u0113\u0116\u0001\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000\u0000"+
		"\u0114\u0115\u0001\u0000\u0000\u0000\u0115+\u0001\u0000\u0000\u0000\u0116"+
		"\u0114\u0001\u0000\u0000\u0000\u0117\u0118\u0005\u0010\u0000\u0000\u0118"+
		"\u011a\u0003B!\u0000\u0119\u011b\u0003T*\u0000\u011a\u0119\u0001\u0000"+
		"\u0000\u0000\u011a\u011b\u0001\u0000\u0000\u0000\u011b-\u0001\u0000\u0000"+
		"\u0000\u011c\u011d\u0005\n\u0000\u0000\u011d\u011f\u0003B!\u0000\u011e"+
		"\u0120\u0003T*\u0000\u011f\u011e\u0001\u0000\u0000\u0000\u011f\u0120\u0001"+
		"\u0000\u0000\u0000\u0120/\u0001\u0000\u0000\u0000\u0121\u0123\u0005#\u0000"+
		"\u0000\u0122\u0124\u0005$\u0000\u0000\u0123\u0122\u0001\u0000\u0000\u0000"+
		"\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0126\u0001\u0000\u0000\u0000"+
		"\u0125\u0127\u0003B!\u0000\u0126\u0125\u0001\u0000\u0000\u0000\u0126\u0127"+
		"\u0001\u0000\u0000\u0000\u01271\u0001\u0000\u0000\u0000\u0128\u012a\u0005"+
		"%\u0000\u0000\u0129\u012b\u0003B!\u0000\u012a\u0129\u0001\u0000\u0000"+
		"\u0000\u012a\u012b\u0001\u0000\u0000\u0000\u012b3\u0001\u0000\u0000\u0000"+
		"\u012c\u012e\u0005&\u0000\u0000\u012d\u012f\u0003T*\u0000\u012e\u012d"+
		"\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000\u0000\u012f5\u0001"+
		"\u0000\u0000\u0000\u0130\u0132\u0005\'\u0000\u0000\u0131\u0133\u0003F"+
		"#\u0000\u0132\u0131\u0001\u0000\u0000\u0000\u0132\u0133\u0001\u0000\u0000"+
		"\u0000\u01337\u0001\u0000\u0000\u0000\u0134\u0135\u0005(\u0000\u0000\u0135"+
		"\u0137\u0005)\u0000\u0000\u0136\u0138\u0003T*\u0000\u0137\u0136\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0001\u0000\u0000\u0000\u01389\u0001\u0000"+
		"\u0000\u0000\u0139\u013b\u0005\u0013\u0000\u0000\u013a\u013c\u0003T*\u0000"+
		"\u013b\u013a\u0001\u0000\u0000\u0000\u013b\u013c\u0001\u0000\u0000\u0000"+
		"\u013c;\u0001\u0000\u0000\u0000\u013d\u013f\u0005n\u0000\u0000\u013e\u0140"+
		"\u0005\u0004\u0000\u0000\u013f\u013e\u0001\u0000\u0000\u0000\u0140\u0141"+
		"\u0001\u0000\u0000\u0000\u0141\u013f\u0001\u0000\u0000\u0000\u0141\u0142"+
		"\u0001\u0000\u0000\u0000\u0142=\u0001\u0000\u0000\u0000\u0143\u0147\u0005"+
		"*\u0000\u0000\u0144\u0146\u0003P(\u0000\u0145\u0144\u0001\u0000\u0000"+
		"\u0000\u0146\u0149\u0001\u0000\u0000\u0000\u0147\u0145\u0001\u0000\u0000"+
		"\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u0148\u014c\u0001\u0000\u0000"+
		"\u0000\u0149\u0147\u0001\u0000\u0000\u0000\u014a\u014b\u0005,\u0000\u0000"+
		"\u014b\u014d\u0003R)\u0000\u014c\u014a\u0001\u0000\u0000\u0000\u014c\u014d"+
		"\u0001\u0000\u0000\u0000\u014d?\u0001\u0000\u0000\u0000\u014e\u0150\u0005"+
		"o\u0000\u0000\u014f\u0151\u0003T*\u0000\u0150\u014f\u0001\u0000\u0000"+
		"\u0000\u0150\u0151\u0001\u0000\u0000\u0000\u0151A\u0001\u0000\u0000\u0000"+
		"\u0152\u0153\u0005\f\u0000\u0000\u0153\u015f\u0003L&\u0000\u0154\u0155"+
		"\u0003F#\u0000\u0155\u0156\u0005\u001c\u0000\u0000\u0156\u0158\u0001\u0000"+
		"\u0000\u0000\u0157\u0154\u0001\u0000\u0000\u0000\u0157\u0158\u0001\u0000"+
		"\u0000\u0000\u0158\u0159\u0001\u0000\u0000\u0000\u0159\u015c\u0003D\""+
		"\u0000\u015a\u015b\u0005\f\u0000\u0000\u015b\u015d\u0003L&\u0000\u015c"+
		"\u015a\u0001\u0000\u0000\u0000\u015c\u015d\u0001\u0000\u0000\u0000\u015d"+
		"\u015f\u0001\u0000\u0000\u0000\u015e\u0152\u0001\u0000\u0000\u0000\u015e"+
		"\u0157\u0001\u0000\u0000\u0000\u015fC\u0001\u0000\u0000\u0000\u0160\u0162"+
		"\u0003F#\u0000\u0161\u0163\u0003H$\u0000\u0162\u0161\u0001\u0000\u0000"+
		"\u0000\u0162\u0163\u0001\u0000\u0000\u0000\u0163E\u0001\u0000\u0000\u0000"+
		"\u0164\u0165\u0005\u000b\u0000\u0000\u0165G\u0001\u0000\u0000\u0000\u0166"+
		"\u0167\u0005\u001e\u0000\u0000\u0167\u016c\u0003J%\u0000\u0168\u0169\u0005"+
		"\u000f\u0000\u0000\u0169\u016b\u0003J%\u0000\u016a\u0168\u0001\u0000\u0000"+
		"\u0000\u016b\u016e\u0001\u0000\u0000\u0000\u016c\u016a\u0001\u0000\u0000"+
		"\u0000\u016c\u016d\u0001\u0000\u0000\u0000\u016d\u016f\u0001\u0000\u0000"+
		"\u0000\u016e\u016c\u0001\u0000\u0000\u0000\u016f\u0170\u0005\u001f\u0000"+
		"\u0000\u0170I\u0001\u0000\u0000\u0000\u0171\u017a\u0003D\"\u0000\u0172"+
		"\u017a\u0005\"\u0000\u0000\u0173\u0174\u0005\"\u0000\u0000\u0174\u0175"+
		"\u0005 \u0000\u0000\u0175\u017a\u0003D\"\u0000\u0176\u0177\u0005\"\u0000"+
		"\u0000\u0177\u0178\u0005!\u0000\u0000\u0178\u017a\u0003D\"\u0000\u0179"+
		"\u0171\u0001\u0000\u0000\u0000\u0179\u0172\u0001\u0000\u0000\u0000\u0179"+
		"\u0173\u0001\u0000\u0000\u0000\u0179\u0176\u0001\u0000\u0000\u0000\u017a"+
		"K\u0001\u0000\u0000\u0000\u017b\u0181\u0005\u000b\u0000\u0000\u017c\u017e"+
		"\u0005\r\u0000\u0000\u017d\u017f\u0003N\'\u0000\u017e\u017d\u0001\u0000"+
		"\u0000\u0000\u017e\u017f\u0001\u0000\u0000\u0000\u017f\u0180\u0001\u0000"+
		"\u0000\u0000\u0180\u0182\u0005\u000e\u0000\u0000\u0181\u017c\u0001\u0000"+
		"\u0000\u0000\u0181\u0182\u0001\u0000\u0000\u0000\u0182M\u0001\u0000\u0000"+
		"\u0000\u0183\u018a\u0005\u001d\u0000\u0000\u0184\u0186\u0005\u000f\u0000"+
		"\u0000\u0185\u0184\u0001\u0000\u0000\u0000\u0185\u0186\u0001\u0000\u0000"+
		"\u0000\u0186\u0187\u0001\u0000\u0000\u0000\u0187\u0189\u0005\u001d\u0000"+
		"\u0000\u0188\u0185\u0001\u0000\u0000\u0000\u0189\u018c\u0001\u0000\u0000"+
		"\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018a\u018b\u0001\u0000\u0000"+
		"\u0000\u018bO\u0001\u0000\u0000\u0000\u018c\u018a\u0001\u0000\u0000\u0000"+
		"\u018d\u018e\u0005+\u0000\u0000\u018e\u018f\u0005\u0019\u0000\u0000\u018f"+
		"\u0190\u0005\u001b\u0000\u0000\u0190Q\u0001\u0000\u0000\u0000\u0191\u0193"+
		"\u0005\u0004\u0000\u0000\u0192\u0191\u0001\u0000\u0000\u0000\u0193\u0194"+
		"\u0001\u0000\u0000\u0000\u0194\u0192\u0001\u0000\u0000\u0000\u0194\u0195"+
		"\u0001\u0000\u0000\u0000\u0195S\u0001\u0000\u0000\u0000\u0196\u019b\u0005"+
		"\u0004\u0000\u0000\u0197\u019b\u0003&\u0013\u0000\u0198\u019b\u0003V+"+
		"\u0000\u0199\u019b\u0003j5\u0000\u019a\u0196\u0001\u0000\u0000\u0000\u019a"+
		"\u0197\u0001\u0000\u0000\u0000\u019a\u0198\u0001\u0000\u0000\u0000\u019a"+
		"\u0199\u0001\u0000\u0000\u0000\u019b\u019c\u0001\u0000\u0000\u0000\u019c"+
		"\u019a\u0001\u0000\u0000\u0000\u019c\u019d\u0001\u0000\u0000\u0000\u019d"+
		"U\u0001\u0000\u0000\u0000\u019e\u01a3\u0003X,\u0000\u019f\u01a3\u0003"+
		"^/\u0000\u01a0\u01a3\u0003Z-\u0000\u01a1\u01a3\u0003\\.\u0000\u01a2\u019e"+
		"\u0001\u0000\u0000\u0000\u01a2\u019f\u0001\u0000\u0000\u0000\u01a2\u01a0"+
		"\u0001\u0000\u0000\u0000\u01a2\u01a1\u0001\u0000\u0000\u0000\u01a3W\u0001"+
		"\u0000\u0000\u0000\u01a4\u01a5\u0004,\u0000\u0000\u01a5\u01a6\u0003`0"+
		"\u0000\u01a6Y\u0001\u0000\u0000\u0000\u01a7\u01a8\u0004-\u0001\u0000\u01a8"+
		"\u01aa\u0003`0\u0000\u01a9\u01ab\u0003f3\u0000\u01aa\u01a9\u0001\u0000"+
		"\u0000\u0000\u01aa\u01ab\u0001\u0000\u0000\u0000\u01ab\u01ac\u0001\u0000"+
		"\u0000\u0000\u01ac\u01ad\u0003b1\u0000\u01ad[\u0001\u0000\u0000\u0000"+
		"\u01ae\u01b0\u0003`0\u0000\u01af\u01b1\u0003h4\u0000\u01b0\u01af\u0001"+
		"\u0000\u0000\u0000\u01b0\u01b1\u0001\u0000\u0000\u0000\u01b1]\u0001\u0000"+
		"\u0000\u0000\u01b2\u01b3\u0005\u0015\u0000\u0000\u01b3\u01b7\u0005\u001a"+
		"\u0000\u0000\u01b4\u01b6\u0003d2\u0000\u01b5\u01b4\u0001\u0000\u0000\u0000"+
		"\u01b6\u01b9\u0001\u0000\u0000\u0000\u01b7\u01b5\u0001\u0000\u0000\u0000"+
		"\u01b7\u01b8\u0001\u0000\u0000\u0000\u01b8\u01ba\u0001\u0000\u0000\u0000"+
		"\u01b9\u01b7\u0001\u0000\u0000\u0000\u01ba\u01bb\u0005\u0017\u0000\u0000"+
		"\u01bb_\u0001\u0000\u0000\u0000\u01bc\u01bd\u0005\u0015\u0000\u0000\u01bd"+
		"\u01c1\u0005\u001a\u0000\u0000\u01be\u01c0\u0003d2\u0000\u01bf\u01be\u0001"+
		"\u0000\u0000\u0000\u01c0\u01c3\u0001\u0000\u0000\u0000\u01c1\u01bf\u0001"+
		"\u0000\u0000\u0000\u01c1\u01c2\u0001\u0000\u0000\u0000\u01c2\u01c4\u0001"+
		"\u0000\u0000\u0000\u01c3\u01c1\u0001\u0000\u0000\u0000\u01c4\u01c5\u0005"+
		"\u0016\u0000\u0000\u01c5a\u0001\u0000\u0000\u0000\u01c6\u01c7\u0005\u0015"+
		"\u0000\u0000\u01c7\u01c8\u0005\u0018\u0000\u0000\u01c8\u01c9\u0005\u001a"+
		"\u0000\u0000\u01c9\u01ca\u0005\u0016\u0000\u0000\u01cac\u0001\u0000\u0000"+
		"\u0000\u01cb\u01ce\u0005r\u0000\u0000\u01cc\u01cd\u0005\u0019\u0000\u0000"+
		"\u01cd\u01cf\u0005\u001b\u0000\u0000\u01ce\u01cc\u0001\u0000\u0000\u0000"+
		"\u01ce\u01cf\u0001\u0000\u0000\u0000\u01cfe\u0001\u0000\u0000\u0000\u01d0"+
		"\u01d5\u0005\u0004\u0000\u0000\u01d1\u01d5\u0003V+\u0000\u01d2\u01d5\u0003"+
		"&\u0013\u0000\u01d3\u01d5\u0003j5\u0000\u01d4\u01d0\u0001\u0000\u0000"+
		"\u0000\u01d4\u01d1\u0001\u0000\u0000\u0000\u01d4\u01d2\u0001\u0000\u0000"+
		"\u0000\u01d4\u01d3\u0001\u0000\u0000\u0000\u01d5\u01d6\u0001\u0000\u0000"+
		"\u0000\u01d6\u01d4\u0001\u0000\u0000\u0000\u01d6\u01d7\u0001\u0000\u0000"+
		"\u0000\u01d7g\u0001\u0000\u0000\u0000\u01d8\u01db\u0005\u0004\u0000\u0000"+
		"\u01d9\u01db\u0003&\u0013\u0000\u01da\u01d8\u0001\u0000\u0000\u0000\u01da"+
		"\u01d9\u0001\u0000\u0000\u0000\u01db\u01dc\u0001\u0000\u0000\u0000\u01dc"+
		"\u01da\u0001\u0000\u0000\u0000\u01dc\u01dd\u0001\u0000\u0000\u0000\u01dd"+
		"i\u0001\u0000\u0000\u0000\u01de\u01df\u0005h\u0000\u0000\u01df\u01e0\u0003"+
		"l6\u0000\u01e0\u01e1\u0005i\u0000\u0000\u01e1k\u0001\u0000\u0000\u0000"+
		"\u01e2\u01e4\u0005\u0004\u0000\u0000\u01e3\u01e2\u0001\u0000\u0000\u0000"+
		"\u01e4\u01e7\u0001\u0000\u0000\u0000\u01e5\u01e3\u0001\u0000\u0000\u0000"+
		"\u01e5\u01e6\u0001\u0000\u0000\u0000\u01e6m\u0001\u0000\u0000\u0000\u01e7"+
		"\u01e5\u0001\u0000\u0000\u0000Cot}\u007f\u0091\u0096\u009b\u00a0\u00a5"+
		"\u00a8\u00ad\u00b0\u00b5\u00b8\u00bd\u00c2\u00ca\u00cf\u00d1\u00d6\u00dc"+
		"\u00e2\u00e7\u00ec\u00f1\u00f4\u00f7\u00fc\u010e\u0114\u011a\u011f\u0123"+
		"\u0126\u012a\u012e\u0132\u0137\u013b\u0141\u0147\u014c\u0150\u0157\u015c"+
		"\u015e\u0162\u016c\u0179\u017e\u0181\u0185\u018a\u0194\u019a\u019c\u01a2"+
		"\u01aa\u01b0\u01b7\u01c1\u01ce\u01d4\u01d6\u01da\u01dc\u01e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}