lexer grammar JavadocCommentsLexer;

channels {
    LEADING_ASTERISKS, WHITESPACES, NEWLINES
}

tokens {
    JAVADOC, LEADING_ASTERISK, NEWLINE, TEXT, WS, JAVADOC_INLINE_TAG, JAVADOC_INLINE_TAG_START, JAVADOC_INLINE_TAG_END,
    CODE, LINK, IDENTIFIER, HASH, LPAREN, RPAREN, COMMA, LINKPLAIN,
    AUTHOR, DEPRECATED, RETURN, PARAM, TAG_OPEN, TAG_CLOSE, TAG_SLASH_CLOSE,
    TAG_SLASH, EQUALS, TAG_NAME, ATTRIBUTE_VALUE, SLASH, PARAMETER_TYPE, LT, GT, EXTENDS,
    SUPER, QUESTION, VALUE, FORMAT_SPECIFIER, INHERIT_DOC, SUMMARY, SYSTEM_PROPERTY,
    INDEX, INDEX_TERM, RETURN, SNIPPET, SNIPPET_ATTR_NAME, COLON, EXCEPTION, THROWS, PARAMETER_NAME, SINCE,
    VERSION, SEE, STRING_LITERAL, LITERAL_HIDDEN, SERIAL, SERIAL_DATA, SERIAL_FIELD, FIELD_TYPE, AT_SIGN,
    TYPE_NAME, REFERENCE, MEMBER_REFERENCE, PARAMETER_TYPE_LIST, TYPE_ARGUMENTS, TYPE_ARGUMENT, DESCRIPTION,
    SNIPPET_ATTRIBUTES, SNIPPET_ATTRIBUTE, SNIPPET_BODY, HTML_ELEMENT, VOID_ELEMENT, HTML_CONTENT,
    HTML_TAG_START, HTML_TAG_END, HTML_ATTRIBUTES, HTML_ATTRIBUTE, JAVADOC_BLOCK_TAG,
    CODE_INLINE_TAG, LINK_INLINE_TAG, LINKPLAIN_INLINE_TAG, VALUE_INLINE_TAG,
    INHERIT_DOC_INLINE_TAG, SUMMARY_INLINE_TAG, SYSTEM_PROPERTY_INLINE_TAG,
    INDEX_INLINE_TAG, RETURN_INLINE_TAG, LITERAL_INLINE_TAG, SNIPPET_INLINE_TAG,
    CUSTOM_INLINE_TAG, AUTHOR_BLOCK_TAG, DEPRECATED_BLOCK_TAG, RETURN_BLOCK_TAG,
    PARAM_BLOCK_TAG, EXCEPTION_BLOCK_TAG, THROWS_BLOCK_TAG, SINCE_BLOCK_TAG, VERSION_BLOCK_TAG,
    SEE_BLOCK_TAG, HIDDEN_BLOCK_TAG, USES_BLOCK_TAG, PROVIDES_BLOCK_TAG, SERIAL_BLOCK_TAG,
    SERIAL_DATA_BLOCK_TAG, SERIAL_FIELD_BLOCK_TAG, CUSTOM_BLOCK_TAG, JAVADOC_INLINE_TAG, HTML_COMMENT_START,
    HTML_COMMENT_END, HTML_COMMENT, HTML_COMMENT_CONTENT
}

@lexer::header {
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import org.antlr.v4.runtime.Token;
import com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsLexerUtility;
import com.puppycrawl.tools.checkstyle.grammar.SimpleToken;
import com.puppycrawl.tools.checkstyle.grammar.CrAwareLexerSimulator;
}

@lexer::members {
    private int previousTokenType = 0;
    private Token previousToken = null;
    private boolean afterNewline = true;
    private boolean isJavadocBlockTag = true;
    private boolean hasSeenTagName = false;
    private int braceCounter = 0;
    private boolean inSeeReferencePart = false;

    private final Deque<Token> openTagNameTokens = new ArrayDeque<>();
    private final Deque<Token> closeTagNameTokens = new ArrayDeque<>();

    public boolean isAfterNewline() {
        return afterNewline;
    }

    public void setAfterNewline() {
        afterNewline = true;
    }

    public boolean isNormalText() {
        int nextChar = _input.LA(1);
        int afterNextChar = _input.LA(2);
        boolean isJavadocBlockTag = isJavadocBlockTag();
        boolean isHtmlTag = nextChar == '<'
                    && (Character.isLetter(afterNextChar) || afterNextChar == '/');
        boolean isHtmlComment = nextChar == '<' && afterNextChar == '!';

        boolean isInlineTag = nextChar == '{' && afterNextChar == '@';
        return !isJavadocBlockTag && !isHtmlTag && !isInlineTag && !isHtmlComment;
    }

    public boolean isJavadocBlockTag() {
        int nextChar = _input.LA(1);

        return (previousTokenType == WS || previousTokenType == LEADING_ASTERISK || previousToken == null
                || previousTokenType == NEWLINE) && nextChar == '@';
    }

    @Override
    public void emit(Token token) {
        super.emit(token);
        if (token.getType() == TAG_NAME) {
            if (JavadocCommentsLexerUtility.isOpenTagName(previousToken)) {
                openTagNameTokens.push(token);
            }
            else {
                closeTagNameTokens.push(token);
            }
        }
        previousTokenType = token.getType();
        previousToken = token;
        if (previousTokenType != NEWLINE) {
            afterNewline = false;
        }
    }

    public Set<SimpleToken> getUnclosedTagNameTokens() {
        return JavadocCommentsLexerUtility.getUnclosedTagNameTokens(openTagNameTokens, closeTagNameTokens);
    }

    /**
     * We need to create a different constructor in order to use our
     * own implementation of the LexerATNSimulator. This is the
     * reason for the unused 'crAwareConstructor' argument.
     *
     * @param input the character stream to tokenize
     * @param crAwareConstructor dummy parameter
     */
    public JavadocCommentsLexer(CharStream input, boolean crAwareConstructor) {
      super(input);
      _interp = new CrAwareLexerSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }
}

LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}? -> channel(LEADING_ASTERISKS), pushMode(TEXT_MODE)
    ;

NEWLINE
    : ('\r\n' | '\r' | '\n') {setAfterNewline();} -> channel(NEWLINES)
    ;

AT_SIGN
    : {isJavadocBlockTag()}? '@' -> pushMode(BLOCK_TAG)
    ;

SWITCH_TO_TEXT_MODE
    : . { _input.seek(_tokenStartCharIndex); setCharPositionInLine(getCharPositionInLine() - 1); }
    -> skip, pushMode(TEXT_MODE)
    ;

// --- TEXT_MODE ---
// Purpose: Handles plain text in Javadoc comments, excluding block and inline tags.
// This mode represents the outermost context and switches to other modes when tags are encountered.
// Example: "* This is a Javadoc comment line."
mode TEXT_MODE;
Text_NEWLINE: NEWLINE {setAfterNewline();} -> mode(DEFAULT_MODE), type(NEWLINE), channel(NEWLINES);
HTML_COMMENT_START : '<!--' -> pushMode(HTML_COMMENT_MODE);
TEXT: TEXT_CHAR+ { if (getText().trim().isEmpty()) { setType(WS); setChannel(WHITESPACES); }};
AT_SIGN2: {isJavadocBlockTag()}? '@' -> type(AT_SIGN), pushMode(BLOCK_TAG);
JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;} -> pushMode(JAVADOC_INLINE_TAG_MODE);
TAG_OPEN: '<' -> pushMode(TAG);
fragment TEXT_CHAR: {isNormalText()}? ~[\r\n];

// --- HTML_COMMENT ---
// Purpose: Handles HTML comments within Javadoc comments.
mode HTML_COMMENT_MODE;
HTML_COMMENT_END: '-->' -> popMode;
HtmlComment_NEWLINE
    : '\r'? '\n' -> type(NEWLINE), channel(NEWLINES)
    ;
HtmlComment_TEXT
    : (   ~[\r\n-]
        | '-' ~[\r\n-]
        | '-' '-' ~[\r\n>]
      )+ -> type(TEXT)
    ;

// --- START_OF_LINE ---
// Purpose: Used to identify leading asterisks at the beginning of each Javadoc line.
// Example: " * This is a line in a Javadoc block"
mode START_OF_LINE;
StartOfLine_LEADING_ASTERISK: [ \t]* '*' -> channel(LEADING_ASTERISKS), popMode, type(LEADING_ASTERISK);
POP_MODE: . {_input.seek(_input.index() - 1);} -> skip, popMode;

// --- BLOCK_TAG ---
// Purpose: Entering point for block-level tags parsing like @param, @return, @throws in Javadoc comments.
// Example: "@param myArg Description of the parameter"
mode BLOCK_TAG;
AUTHOR: 'author' -> pushMode(TEXT_MODE);
DEPRECATED: 'deprecated' -> pushMode(TEXT_MODE);
RETURN_BLOCK_TAG: 'return' -> type(RETURN), pushMode(TEXT_MODE);
PARAM: 'param' -> pushMode(PARAMETER_NAME_MODE);
EXCEPTION: 'exception' -> pushMode(QUALIFIED_IDENTIFIER);
THROWS: 'throws' -> pushMode(QUALIFIED_IDENTIFIER);
SINCE: 'since' -> pushMode(TEXT_MODE);
VERSION: 'version' -> pushMode(TEXT_MODE);
SEE: 'see' {inSeeReferencePart = true;} -> pushMode(SEE_MODE);
LITERAL_HIDDEN: 'hidden' -> pushMode(TEXT_MODE);
USES: 'uses' -> pushMode(QUALIFIED_IDENTIFIER);
PROVIDES: 'provides' -> pushMode(QUALIFIED_IDENTIFIER);
SERIAL: 'serial' -> pushMode(TEXT_MODE);
SERIAL_DATA: 'serialData' -> pushMode(TEXT_MODE);
SERIAL_FIELD: 'serialField' -> pushMode(FIELD_NAME);
BlockTag_CUSTOM_NAME: [a-zA-Z0-9:._-]+ -> type(CUSTOM_NAME), pushMode(TEXT_MODE);

// --- FIELD_NAME ---
// Purpose: Parses the field name in @serialField block tag.
// Example: "@serialField fieldName"
mode FIELD_NAME;
FieldName_IDENTIFIER: Letter LetterOrDigit* -> type(IDENTIFIER), pushMode(FIELD_TYPE_MODE);
FieldName_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
FieldName_NEWLINE
    : NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;

// --- FIELD_TYPE_MODE ---
// Purpose: Parses the type of a field in a @serialField tag after the field name.
// Example: "int" in "@serialField fieldName int field description
mode FIELD_TYPE_MODE;
FieldType_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
FIELD_TYPE: ([a-zA-Z0-9_$] | '.' | '[' | ']')+ -> mode(TEXT_MODE);

// --- SEE_MODE ---
// Purpose: Parses the content of the @see tag, which can include references, strings, or links.
// Example: "@see java.util.List"
// Example: "@see "some string""
// Example: "@see <a href='...'>link</a>"
mode SEE_MODE;
STRING_LITERAL: '"' .*? '"' {inSeeReferencePart = false;} -> mode(TEXT_MODE);
See_TAG_OPEN: '<' {_input.seek(_input.index() - 1); inSeeReferencePart = false;} -> skip, mode(TEXT_MODE);
See_IDENTIFIER: ([a-zA-Z0-9_$] | '.')+
    {
        int la = _input.LA(1);
        if (Character.isWhitespace(la) || la == '\n' || la == '\r') {
            inSeeReferencePart = false;
            mode(TEXT_MODE);
        }
    } -> type(IDENTIFIER);

See_HASH: '#' -> type(HASH);
See_LPAREN: '(' -> type(LPAREN), pushMode(PARAMETER_LIST);
See_NEWLINE
    : NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;
See_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

// --- QUALIFIED_IDENTIFIER ---
// Purpose: Parses fully qualified class or interface names, such as in @users or @provides tags.
// Example: "@uses java.util.List"
mode QUALIFIED_IDENTIFIER;
DOTTED_IDENTIFIER: ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(TEXT_MODE);
DottedIdentifier_NEWLINE
    : NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;
DottedIdentifier_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

// --- EXCEPTION_NAME_MODE ---
// Purpose: Parses exception type names used in @exception or @throws tags.
// Example: "java.io.IOException"
mode EXCEPTION_NAME_MODE;
EXCEPTION_NAME: ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(TEXT_MODE);
ExceptionName_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
ExceptionName_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

// --- PARAMETER_NAME_MODE ---
// Purpose: Parses the name of a parameter in the @param tag.
// Example: "@param myParam Description
// Example: "@param <T> Description
mode PARAMETER_NAME_MODE;
PARAMETER_NAME: [a-zA-Z0-9<>_$]+ -> mode(TEXT_MODE);
Param_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
Param_WS: [ \t]+ -> type(WS), channel(WHITESPACES);


// --- JAVADOC_INLINE_TAG_MODE ---
// Purpose: Entering point for inline tags such as {@code ...}, {@link ...}.
// Example: "{@code int x = 5;}"
mode JAVADOC_INLINE_TAG_MODE;
CODE: 'code' -> pushMode(PLAIN_TEXT_TAG);
LINK: 'link'-> pushMode(LINK_MODE);
LINKPLAIN: 'linkplain' -> pushMode(LINK_MODE);
VALUE: 'value' -> pushMode(VALUE_MODE);
INHERIT_DOC: 'inheritDoc' -> pushMode(LINK_MODE);
SUMMARY: 'summary' -> pushMode(INLINE_TAG_DESCRIPTION);
SYSTEM_PROPERTY: 'systemProperty' -> pushMode(VALUE_MODE);
INDEX: 'index' -> pushMode(INDEX_TERM_MODE);
RETURN: 'return' -> pushMode(INLINE_TAG_DESCRIPTION);
LITERAL: 'literal' -> pushMode(PLAIN_TEXT_TAG);
SNIPPET: 'snippet' -> pushMode(SNIPPET_ATTRIBUTE_MODE);
CUSTOM_NAME: [a-zA-Z0-9:._-]+ -> pushMode(INLINE_TAG_DESCRIPTION);

// --- PLAIN_TEXT_TAG ---
// Purpose: Parses the inner content of inline tags that contain plain text like {@code}, {@literal}.
// Example: "{@code  {int x = 5;} }"
mode PLAIN_TEXT_TAG;
Code_NEWLINE: NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES), pushMode(START_OF_LINE);
Code_LBRACE: '{' { braceCounter++; } -> type(TEXT);
Code_RBRACE: '}' { braceCounter > 1 }? { braceCounter--; } -> type(TEXT);
JAVADOC_INLINE_TAG_END: '}' { braceCounter == 1 }? { braceCounter--; } -> popMode, popMode;
Code_TEXT: ~[{}\r\n]+ -> type(TEXT);

// --- SNIPPET_ATTRIBUTE_MODE ---
// Purpose: Parses attributes inside {@snippet} inline tags.
// Example: "{@snippet region=example}" or "{@snippet file=MyFile.java :region}"
mode SNIPPET_ATTRIBUTE_MODE;
SNIPPET_ATTR_NAME: Letter LetterOrDigit*;
SNIPPET_EQUALS: '=' -> type(EQUALS), pushMode(SNIPPET_ATTR_VALUE);
COLON: ':' -> popMode, pushMode(PLAIN_TEXT_TAG);
SnippetAttribute_NEWLINE: NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES), pushMode(START_OF_LINE);
SnippetArrtibute_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
SnippetAttribute_JAVADOC_INLINE_TAG_END: '}' { braceCounter--; } -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

// --- SNIPPET_ATTR_VALUE ---
// Purpose: Parses attribute values within {@snippet} Javadoc inline tags,
// ensuring that colons (:) act as delimiter instead of
// being included in the value. This prevents cases like
// {@snippet lang="java": code} from treating ":" as part of the "lang"
mode SNIPPET_ATTR_VALUE;
Snippet_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
Snippet_ATTRIBUTE_VALUE: ' '* Snippet_ATTRIBUTE -> type(ATTRIBUTE_VALUE), popMode;
Snippet_ATTRIBUTE: Snippet_DOUBLE_QUOTE_STRING | Snippet_SINGLE_QUOTE_STRING | Snippet_ATTCHARS | Snippet_HEXCHARS | Snippet_DECCHARS;
fragment Snippet_ATTCHARS: ATTCHAR+ ' '?;
fragment Snippet_ATTCHAR: '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ';' | '#' | [0-9a-zA-Z];
fragment Snippet_HEXCHARS: '#' [0-9a-fA-F]+;
fragment Snippet_DECCHARS: [0-9]+ '%'?;
fragment Snippet_DOUBLE_QUOTE_STRING: '"' ~[:"]* '"';
fragment Snippet_SINGLE_QUOTE_STRING: '\'' ~[:']* '\'';

// --- LINK_MODE ---
// Purpose: Parses the reference inside {@link}, {@linkplain}, {@inheritDoc} inline tags.
// Example: "{@link java.util.List#add(Object)}"
// Example: "{@linkplain java.util.Map}"
mode LINK_MODE;
EXTENDS: 'extends';
SUPER: 'super';
IDENTIFIER: ([a-zA-Z0-9_$] | '.')+
    {
        int la = _input.LA(1);
        if (Character.isWhitespace(la) || la == '\n' || la == '\r') {
            pushMode(LINK_TAG_DESCRIPTION);
        }
    };
QUESTION: '?';
HASH: '#';
LPAREN: '(' -> pushMode(PARAMETER_LIST);
SLASH: '/';
Link_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
Link_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;
LT: '<';
GT: '>' { if (Character.isWhitespace(_input.LA(1))) pushMode(LINK_TAG_DESCRIPTION); };
Link_COMMA: ',' -> type(COMMA);
Link_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);

fragment LetterOrDigit: Letter | [0-9];
fragment Letter: [a-zA-Z$_];

// --- LINK_TAG_DESCRIPTION ---
// Purpose: Parses the optional description text after the reference in a {@link} tag.
// Example: "{@link java.util.List#add(Object) Adds an item to the list.}"
mode LINK_TAG_DESCRIPTION;
LinkDescription_TEXT: ~[{}\r\n]+ -> type(TEXT);
LinkDescription_JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;} -> pushMode(JAVADOC_INLINE_TAG_MODE), type(JAVADOC_INLINE_TAG_START);
LinkDescription_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
LinkDescription_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode, popMode;

// --- PARAMETER_LIST ---
// Purpose: Parses parameter types in method references within {@link} or @see tags.
// Example: "{@link java.util.Map#put(Object, Object)}"
mode PARAMETER_LIST;
ParameterList_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
ParameterList_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
PARAMETER_TYPE: ([a-zA-Z0-9_$] | '.' | '[' | ']')+;
COMMA: ',';
RPAREN: ')' {
      if (inSeeReferencePart) {
          mode(TEXT_MODE);
          inSeeReferencePart = false;
      } else {
          popMode();
          pushMode(LINK_TAG_DESCRIPTION);
      }
  };

// --- VALUE_MODE ---
// Purpose: Parses the contents of {@value} or {@systemProperty} tags, which often include constants or variables.
// Example: "{@value java.lang.Integer#MAX_VALUE}"
mode VALUE_MODE;
Value_IDENTIFIER: ([a-zA-Z0-9_$] | '.' | '-')+ -> type(IDENTIFIER);
FORMAT_SPECIFIER: '%' [#+\- 0,(]* [0-9]* ('.' [0-9]+)? [a-zA-Z];
Value_HASH: '#' -> type(HASH);
Value_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
Value_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
Value_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

// --- INLINE_TAG_DESCRIPTION ---
// Purpose: Parses inline text content following an inline tag with optional HTML or nested inline tags.
// Example: "{@summary This is a short description.}"
mode INLINE_TAG_DESCRIPTION;
InlineDescription_TEXT: InlineDescription_TEXT_CHAR+ -> type(TEXT);
fragment InlineDescription_TEXT_CHAR: {isNormalText()}? ~[}\r\n];
InlineDescription_JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;}
    -> pushMode(JAVADOC_INLINE_TAG_MODE), type(JAVADOC_INLINE_TAG_START);
InlineDescription_TAG_OPEN: '<' -> pushMode(TAG), type(TAG_OPEN);
InlineDescription_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
InlineDescription_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

// --- INDEX_TERM_MODE ---
// Purpose: Parses terms inside {@index} tags.
// Example: "{@index "term name"}"
mode INDEX_TERM_MODE;
INDEX_TERM: ( '"' (~["\r\n])+ '"' | ~[ \t\r\n"}]+ | '"' (~["\r\n}])+ ) -> popMode, pushMode(PLAIN_TEXT_TAG);
IndexTerm_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
IndexTerm_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

// --- TAG ---
// Purpose: Parses HTML tags inside Javadoc comments, such as <p>, <ul>, <li>, etc.
// Example: "<p>This is a paragraph.</p>"
mode TAG;
TAG_CLOSE: '>' {hasSeenTagName = false;} -> popMode;
TAG_SLASH_CLOSE: '/>' {hasSeenTagName = false;} -> popMode;
TAG_SLASH: '/';
EQUALS: '=' -> pushMode(ATTR_VALUE);
TAG_NAME: {hasSeenTagName == false}? TagNameStartChar TagNameChar* {hasSeenTagName = true;};
TAG_ATTR_NAME: {hasSeenTagName == true}? TagNameStartChar TagNameChar*;
Tag_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
TAG_WHITESPACE: [ \t]+ -> type(WS), channel(WHITESPACES);

fragment TagNameChar: TagNameStartChar | '-' | '_' | '.' | DIGIT | '\u00B7' | '\u0300'..'\u036F' | '\u203F'..'\u2040';
fragment TagNameStartChar: [:a-zA-Z] | '\u2070'..'\u218F' | '\u2C00'..'\u2FEF' | '\u3001'..'\uD7FF' | '\uF900'..'\uFDCF' | '\uFDF0'..'\uFFFD';
fragment DIGIT: [0-9];

// --- ATTR_VALUE ---
// Purpose: Parses attribute values within HTML tags, such as href="..." or class='...'.
// Example: "<a href="https://example.com">"
mode ATTR_VALUE;
AttrValue_NEWLINE: NEWLINE {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
ATTRIBUTE_VALUE: ' '* ATTRIBUTE -> popMode;
ATTRIBUTE: DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING | ATTCHARS | HEXCHARS | DECCHARS | UNQUOTED_STRING;
fragment ATTCHARS: ATTCHAR+ ' '?;
fragment ATTCHAR: '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ':' | ';' | '#' | [0-9a-zA-Z];
fragment HEXCHARS: '#' [0-9a-fA-F]+;
fragment DECCHARS: [0-9]+ '%'?;
fragment DOUBLE_QUOTE_STRING: '"' ~[<"]* '"';
fragment SINGLE_QUOTE_STRING: '\'' ~[<']* '\'';
fragment UNQUOTED_STRING: ( ~[> }\t\r\n/] | SLASH_IN_ATTR )+;
fragment SLASH_IN_ATTR: '/' {_input.LA(1) != '>'}?;