lexer grammar JavadocCommentsLexer;

channels {
    LEADING_ASTERISKS, WHITESPACES, NEWLINES
}

tokens {
    JAVADOC, LEADING_ASTERISK, NEWLINE, TEXT, WS, JAVADOC_INLINE_TAG_START, JAVADOC_INLINE_TAG_END,
    CODE, LINK, IDENTIFIER, HASH, LPAREN, RPAREN, COMMA, LINKPLAIN,
    AUTHOR, DEPRECATED, RETURN, PARAM, TAG_OPEN, TAG_CLOSE, TAG_SLASH_CLOSE,
    TAG_SLASH, EQUALS, TAG_NAME, ATTRIBUTE_VALUE, SLASH, PARAMETER_TYPE, LT, GT, EXTENDS,
    SUPER, QUESTION, VALUE, FORMAT_SPECIFIER, INHERIT_DOC, SUMMARY, SYSTEM_PROPERTY,
    INDEX, INDEX_TERM, RETURN, SNIPPET, SNIPPET_ATTR_NAME, COLON, EXCEPTION, THROWS, PARAMETER_NAME, SINCE,
    VERSION, SEE, STRING_LITERAL, LITERAL_HIDDEN, SERIAL, SERIAL_DATA, SERIAL_FIELD, FIELD_TYPE, AT_SIGN
}

@lexer::header {
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import org.antlr.v4.runtime.Token;
import com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsLexerUtility;
import com.puppycrawl.tools.checkstyle.grammar.SimpleToken;
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

        boolean isInlineTag = nextChar == '{' && afterNextChar == '@';
        return !isJavadocBlockTag && !isHtmlTag && !isInlineTag;
    }

    public boolean isJavadocBlockTag() {
        int nextChar = _input.LA(1);

        return (previousTokenType == WS || previousTokenType == LEADING_ASTERISK
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
}

LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}? {
        if (!Character.isWhitespace(_input.LA(1))) {
           pushMode(TEXT_MODE);
        }
    } -> channel(LEADING_ASTERISKS)
    ;

WS :   (' '|'\t')+ -> channel(WHITESPACES),  pushMode(TEXT_MODE) ;

NEWLINE
    : '\r'? '\n' {setAfterNewline();} -> channel(NEWLINES)
    ;

AT_SIGN
    : {isJavadocBlockTag()}? '@' -> pushMode(BLOCK_TAG)
    ;

mode TEXT_MODE;
Text_NEWLINE: '\r'? '\n' {setAfterNewline();} -> mode(DEFAULT_MODE), type(NEWLINE), channel(NEWLINES);
TEXT: TEXT_CHAR+;
AT_SIGN2: {isJavadocBlockTag()}? '@' -> type(AT_SIGN), pushMode(BLOCK_TAG);
JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;} -> pushMode(JAVADOC_INLINE_TAG);
TAG_OPEN: '<' -> pushMode(TAG);
fragment TEXT_CHAR: {isNormalText()}? ~[\r\n];

mode JAVADOC_INLINE_TAG;
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
SNIPPET: 'snippet' -> pushMode(SNIPPET_ATTRIBUTE);
CUSTOM_NAME: [a-zA-Z0-9:._-]+ -> pushMode(INLINE_TAG_DESCRIPTION);

mode PLAIN_TEXT_TAG;
Code_NEWLINE: '\r'? '\n' {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES), pushMode(START_OF_LINE);
Code_LBRACE: '{' { braceCounter++; } -> type(TEXT);
Code_RBRACE: '}' { braceCounter > 1 }? { braceCounter--; } -> type(TEXT);
JAVADOC_INLINE_TAG_END: '}' { braceCounter == 1 }? { braceCounter--; } -> popMode, popMode;
Code_TEXT: ~[{}\r\n]+ -> type(TEXT);

mode SNIPPET_ATTRIBUTE;
SNIPPET_ATTR_NAME: Letter LetterOrDigit*;
SNIPPET_EQUALS: '=' -> type(EQUALS), pushMode(ATTR_VALUE);
COLON: ':' -> popMode, pushMode(PLAIN_TEXT_TAG);
SnippetAttribute_NEWLINE: '\r'? '\n' {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES), pushMode(START_OF_LINE);
SnippetArrtibute_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
SnippetAttribute_JAVADOC_INLINE_TAG_END: '}' { braceCounter--; } -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

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
Link_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);

fragment LetterOrDigit: Letter | [0-9];
fragment Letter: [a-zA-Z$_];

mode LINK_TAG_DESCRIPTION;
LinkDescription_TEXT: ~[{}\r\n]+ -> type(TEXT);
LinkDescription_JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;} -> pushMode(JAVADOC_INLINE_TAG), type(JAVADOC_INLINE_TAG_START);
LinkDescription_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
LinkDescription_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode, popMode;

mode PARAMETER_LIST;
ParameterList_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
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

mode VALUE_MODE;
Value_IDENTIFIER: ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER);
FORMAT_SPECIFIER: '%' [#+\- 0,(]* [0-9]* ('.' [0-9]+)? [a-zA-Z];
Value_HASH: '#' -> type(HASH);
Value_WS: [ \t]+ -> channel(WHITESPACES);
Value_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
Value_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

mode INLINE_TAG_DESCRIPTION;
InlineDescription_TEXT: InlineDescription_TEXT_CHAR+ -> type(TEXT);
fragment InlineDescription_TEXT_CHAR: {isNormalText()}? ~[}\r\n];
InlineDescription_JAVADOC_INLINE_TAG_START: '{@' { braceCounter = 1;}
    -> pushMode(JAVADOC_INLINE_TAG), type(JAVADOC_INLINE_TAG_START);
InlineDescription_TAG_OPEN: '<' -> pushMode(TAG), type(TAG_OPEN);
InlineDescription_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
InlineDescription_JAVADOC_INLINE_TAG_END: '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode;

mode INDEX_TERM_MODE;
INDEX_TERM: ( '"' (~["\r\n}])+ '"' | ~[ \t\r\n"}]+ ) -> popMode, pushMode(PLAIN_TEXT_TAG);
IndexTerm_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
IndexTerm_WS: [ \t]+ -> channel(WHITESPACES);

mode START_OF_LINE;
StartOfLine_LEADING_ASTERISK: [ \t]* '*' -> channel(LEADING_ASTERISKS), popMode, type(LEADING_ASTERISK);

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

mode FIELD_NAME;
FieldName_IDENTIFIER: Letter LetterOrDigit* -> type(IDENTIFIER), pushMode(FIELD_TYPE_MODE);
FieldName_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
FieldName_NEWLINE
    : '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;

mode FIELD_TYPE_MODE;
FieldType_WS: [ \t]+ -> type(WS), channel(WHITESPACES);
FIELD_TYPE: ([a-zA-Z0-9_$] | '.' | '[' | ']')+ -> mode(TEXT_MODE);

mode SEE_MODE;
STRING_LITERAL: '"' .*? '"' -> mode(TEXT_MODE);
See_TAG_OPEN: '<' {_input.seek(_input.index() - 1);} -> skip, mode(TEXT_MODE);
See_IDENTIFIER: ([a-zA-Z0-9_$] | '.')+
    {
        int la = _input.LA(1);
        if (Character.isWhitespace(la) || la == '\n' || la == '\r') {
            mode(TEXT_MODE);
        }
    } -> type(IDENTIFIER);

See_HASH: '#' -> type(HASH);
See_LPAREN: '(' -> type(LPAREN), pushMode(PARAMETER_LIST);
See_NEWLINE
    : '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;
See_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

mode QUALIFIED_IDENTIFIER;
DOTTED_IDENTIFIER: ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(TEXT_MODE);
DottedIdentifier_NEWLINE
    : '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES)
    ;
DottedIdentifier_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

mode EXCEPTION_NAME_MODE;
EXCEPTION_NAME: ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(TEXT_MODE);
ExceptionName_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
ExceptionName_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

mode PARAMETER_NAME_MODE;
PARAMETER_NAME: [a-zA-Z0-9<>_$]+ -> mode(TEXT_MODE);
Param_WS: [ \t]+ -> type(WS), channel(WHITESPACES);

mode TAG;
TAG_CLOSE: '>' {hasSeenTagName = false;} -> popMode;
TAG_SLASH_CLOSE: '/>' {hasSeenTagName = false;} -> popMode;
TAG_SLASH: '/';
EQUALS: '=' -> pushMode(ATTR_VALUE);
TAG_NAME: {hasSeenTagName == false}? TagNameStartChar TagNameChar* {hasSeenTagName = true;};
TAG_ATTR_NAME: {hasSeenTagName == true}? TagNameStartChar TagNameChar*;
Tag_NEWLINE: '\r'? '\n' {setAfterNewline();} -> pushMode(START_OF_LINE), type(NEWLINE), channel(NEWLINES);
TAG_WHITESPACE: [ \t]+ -> type(WS), channel(WHITESPACES);

fragment TagNameChar: TagNameStartChar | '-' | '_' | '.' | DIGIT | '\u00B7' | '\u0300'..'\u036F' | '\u203F'..'\u2040';
fragment TagNameStartChar: [:a-zA-Z] | '\u2070'..'\u218F' | '\u2C00'..'\u2FEF' | '\u3001'..'\uD7FF' | '\uF900'..'\uFDCF' | '\uFDF0'..'\uFFFD';
fragment DIGIT: [0-9];

mode ATTR_VALUE;
ATTRIBUTE_VALUE: ' '* ATTRIBUTE -> popMode;
ATTRIBUTE: DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING | ATTCHARS | HEXCHARS | DECCHARS;
fragment ATTCHARS: ATTCHAR+ ' '?;
fragment ATTCHAR: '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ':' | ';' | '#' | [0-9a-zA-Z];
fragment HEXCHARS: '#' [0-9a-fA-F]+;
fragment DECCHARS: [0-9]+ '%'?;
fragment DOUBLE_QUOTE_STRING: '"' ~[<"]* '"';
fragment SINGLE_QUOTE_STRING: '\'' ~[<']* '\'';
