///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

lexer grammar JavadocCommentsLexer;

channels {
    LEADING_ASTERISKS, WHITESPACES, NEWLINES
}

tokens {
    JAVADOC, LEADING_ASTERISK, NEWLINE, TEXT, WS, JAVADOC_INLINE_TAG,
    JAVADOC_INLINE_TAG_START, JAVADOC_INLINE_TAG_END,
    CODE, LINK, IDENTIFIER, HASH, LPAREN, RPAREN, COMMA, LINKPLAIN,
    AUTHOR, DEPRECATED, RETURN, PARAM, TAG_OPEN, TAG_CLOSE,
    TAG_SLASH_CLOSE, TAG_SLASH, EQUALS, TAG_NAME, ATTRIBUTE_VALUE,
    SLASH, PARAMETER_TYPE, LT, GT, EXTENDS, SUPER, QUESTION, VALUE,
    FORMAT_SPECIFIER, INHERIT_DOC, SUMMARY, SYSTEM_PROPERTY, INDEX,
    INDEX_TERM, RETURN, SNIPPET, SNIPPET_ATTR_NAME, COLON, EXCEPTION,
    THROWS, PARAMETER_NAME, SINCE, VERSION, SEE, STRING_LITERAL,
    LITERAL_HIDDEN, SERIAL, SERIAL_DATA, SERIAL_FIELD, FIELD_TYPE,
    AT_SIGN, UNUSED_TYPE_NAME, REFERENCE, MEMBER_REFERENCE, PARAMETER_TYPE_LIST,
    TYPE_ARGUMENTS, TYPE_ARGUMENT, DESCRIPTION, SNIPPET_ATTRIBUTES,
    SNIPPET_ATTRIBUTE, SNIPPET_BODY, HTML_ELEMENT, VOID_ELEMENT,
    HTML_CONTENT, HTML_TAG_START, HTML_TAG_END, HTML_ATTRIBUTES,
    HTML_ATTRIBUTE, JAVADOC_BLOCK_TAG, CODE_INLINE_TAG, LINK_INLINE_TAG,
    LINKPLAIN_INLINE_TAG, VALUE_INLINE_TAG, INHERIT_DOC_INLINE_TAG,
    SUMMARY_INLINE_TAG, SYSTEM_PROPERTY_INLINE_TAG, INDEX_INLINE_TAG,
    RETURN_INLINE_TAG, LITERAL_INLINE_TAG, SNIPPET_INLINE_TAG,
    CUSTOM_INLINE_TAG, AUTHOR_BLOCK_TAG, DEPRECATED_BLOCK_TAG,
    RETURN_BLOCK_TAG, PARAM_BLOCK_TAG, EXCEPTION_BLOCK_TAG,
    THROWS_BLOCK_TAG, SINCE_BLOCK_TAG, VERSION_BLOCK_TAG, SEE_BLOCK_TAG,
    HIDDEN_BLOCK_TAG, USES_BLOCK_TAG, PROVIDES_BLOCK_TAG, SERIAL_BLOCK_TAG,
    SERIAL_DATA_BLOCK_TAG, SERIAL_FIELD_BLOCK_TAG, CUSTOM_BLOCK_TAG,
    JAVADOC_INLINE_TAG, HTML_COMMENT_START, HTML_COMMENT_END, HTML_COMMENT,
    HTML_COMMENT_CONTENT
}

@lexer::header {
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import org.antlr.v4.runtime.Token;
import com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsLexerUtil;
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

    public Token getPreviousToken() {
        return this.previousToken;
    }

    public boolean isNormalText() {
        int nextChar = _input.LA(1);
        int afterNextChar = _input.LA(2);
        boolean isJavadocBlockTag = isJavadocBlockTag();

        boolean isHtmlTag = nextChar == '<'
                && (Character.isLetter(afterNextChar) || afterNextChar == '/');
        boolean isHtmlComment = nextChar == '<' && afterNextChar == '!';
        boolean isInlineTag = nextChar == '{' && afterNextChar == '@';

        return !isJavadocBlockTag && !isHtmlTag && !isInlineTag && !isHtmlComment
                && !isLeadingAsterisk();
    }

    private boolean isLeadingAsterisk() {
        return afterNewline && _input.LA(1) == '*';
    }

    public boolean isJavadocBlockTag() {
        int nextChar = _input.LA(1);

        return (previousTokenType == WS
                || previousTokenType == LEADING_ASTERISK
                || previousToken == null
                || previousTokenType == NEWLINE)
                && nextChar == '@';
    }

    @Override
    public void emit(Token token) {
        super.emit(token);
        if (token.getType() == TAG_NAME) {
            if (JavadocCommentsLexerUtil.isOpenTagName(previousToken)) {
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
        return JavadocCommentsLexerUtil.getUnclosedTagNameTokens(
                openTagNameTokens, closeTagNameTokens);
    }

    /**
     * We need to create a different constructor in order to use our
     * own implementation of the LexerATNSimulator. This is the reason
     * for the unused 'crAwareConstructor' argument.
     *
     * @param input the character stream to tokenize
     * @param crAwareConstructor dummy parameter
     */
    public JavadocCommentsLexer(CharStream input, boolean crAwareConstructor) {
        super(input);
        _interp = new CrAwareLexerSimulator(this, _ATN, _decisionToDFA,
                _sharedContextCache);
    }
}

LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}? -> channel(LEADING_ASTERISKS)
    ;

NEWLINE
    : ('\r\n' | '\r' | '\n') {setAfterNewline();} -> channel(NEWLINES)
    ;

HTML_COMMENT_START
    : '<!--' -> pushMode(HTML_COMMENT_MODE)
    ;

TEXT
    : TEXT_CHAR+
      { if (getText().trim().isEmpty()) { setType(WS); setChannel(WHITESPACES); }}
    ;

AT_SIGN
    : {isJavadocBlockTag()}? '@' -> pushMode(BLOCK_TAG)
    ;

JAVADOC_INLINE_TAG_START
    : '{@' { braceCounter = 1; } -> pushMode(JAVADOC_INLINE_TAG_MODE)
    ;

TAG_OPEN
    : '<' -> pushMode(TAG)
    ;

fragment TEXT_CHAR
    : {isNormalText()}? ~[\r\n]
    ;

// --- HTML_COMMENT ---
// Purpose: Handles HTML comments within Javadoc comments.
mode HTML_COMMENT_MODE;

HTML_COMMENT_END
    : '-->' -> popMode
    ;

HtmlComment_NEWLINE
    : '\r'? '\n' -> type(NEWLINE), channel(NEWLINES)
    ;

HtmlComment_TEXT
    : (   ~[\r\n-]
        | '-' ~[\r\n-]
        | '-' '-' ~[\r\n>]
      )+ -> type(TEXT)
    ;

// --- BLOCK_TAG ---
// Entering point for block-level tags parsing like @param, @return, @throws.
mode BLOCK_TAG;

AUTHOR: 'author' -> pushMode(DEFAULT_MODE);
DEPRECATED: 'deprecated' -> pushMode(DEFAULT_MODE);
RETURN_BLOCK_TAG: 'return' -> type(RETURN), pushMode(DEFAULT_MODE);
PARAM: 'param' -> pushMode(PARAMETER_NAME_MODE);
EXCEPTION: 'exception' -> pushMode(QUALIFIED_IDENTIFIER);
THROWS: 'throws' -> pushMode(QUALIFIED_IDENTIFIER);
SINCE: 'since' -> pushMode(DEFAULT_MODE);
VERSION: 'version' -> pushMode(DEFAULT_MODE);
SEE: 'see' { inSeeReferencePart = true; } -> pushMode(REFERENCE_MODE);
LITERAL_HIDDEN: 'hidden' -> pushMode(DEFAULT_MODE);
USES: 'uses' -> pushMode(QUALIFIED_IDENTIFIER);
PROVIDES: 'provides' -> pushMode(QUALIFIED_IDENTIFIER);
SERIAL: 'serial' -> pushMode(DEFAULT_MODE);
SERIAL_DATA: 'serialData' -> pushMode(DEFAULT_MODE);
SERIAL_FIELD: 'serialField' -> pushMode(FIELD_NAME);
BlockTag_CUSTOM_NAME: [a-zA-Z0-9:._-]+ -> type(CUSTOM_NAME), pushMode(DEFAULT_MODE);

// --- FIELD_NAME ---
// Purpose: Parses the field name in @serialField block tag.
mode FIELD_NAME;

FieldName_IDENTIFIER
    : Letter LetterOrDigit* -> type(IDENTIFIER), pushMode(FIELD_TYPE_MODE)
    ;

FieldName_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

FieldName_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

FieldName_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}? -> channel(LEADING_ASTERISKS),
      type(LEADING_ASTERISK)
    ;

// --- FIELD_TYPE_MODE ---
mode FIELD_TYPE_MODE;

FieldType_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

FIELD_TYPE
    : ([a-zA-Z0-9_$] | '.' | '[' | ']')+ -> mode(DEFAULT_MODE)
    ;

// --- QUALIFIED_IDENTIFIER ---
mode QUALIFIED_IDENTIFIER;

DOTTED_IDENTIFIER
    : ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(DEFAULT_MODE)
    ;

DottedIdentifier_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

DottedIdentifier_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

DottedIdentifier_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

// --- EXCEPTION_NAME_MODE ---
mode EXCEPTION_NAME_MODE;

EXCEPTION_NAME
    : ([a-zA-Z0-9_$] | '.')+ -> type(IDENTIFIER), mode(DEFAULT_MODE)
    ;

ExceptionName_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

ExceptionName_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

ExceptionName_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

// --- PARAMETER_NAME_MODE ---
mode PARAMETER_NAME_MODE;

PARAMETER_NAME
    : [a-zA-Z0-9<>_$]+ -> mode(DEFAULT_MODE)
    ;

Param_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Param_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

Param_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

// --- JAVADOC_INLINE_TAG_MODE ---
mode JAVADOC_INLINE_TAG_MODE;

CODE: 'code' -> pushMode(PLAIN_TEXT_TAG);
LINK: 'link' -> pushMode(REFERENCE_MODE);
LINKPLAIN: 'linkplain' -> pushMode(REFERENCE_MODE);
VALUE: 'value' -> pushMode(VALUE_MODE);
INHERIT_DOC: 'inheritDoc' -> pushMode(REFERENCE_MODE);
SUMMARY: 'summary' -> pushMode(INLINE_TAG_DESCRIPTION);
SYSTEM_PROPERTY: 'systemProperty' -> pushMode(VALUE_MODE);
INDEX: 'index' -> pushMode(INDEX_TERM_MODE);
RETURN: 'return' -> pushMode(INLINE_TAG_DESCRIPTION);
LITERAL: 'literal' -> pushMode(PLAIN_TEXT_TAG);
SNIPPET: 'snippet' -> pushMode(SNIPPET_ATTRIBUTE_MODE);
CUSTOM_NAME: [a-zA-Z0-9:._-]+ -> pushMode(INLINE_TAG_DESCRIPTION);

// --- PLAIN_TEXT_TAG ---
mode PLAIN_TEXT_TAG;

Code_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Code_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

Code_LBRACE
    : '{' { braceCounter++; } -> type(TEXT)
    ;

Code_RBRACE
    : '}' { braceCounter > 1 }? { braceCounter--; } -> type(TEXT)
    ;

JAVADOC_INLINE_TAG_END
    : '}' { braceCounter == 1 }? { braceCounter--; } -> popMode, popMode
    ;

Code_TEXT
    : Code_TEXT_CHAR+ -> type(TEXT)
    ;

fragment Code_TEXT_CHAR
    : {!isLeadingAsterisk()}? ~[{}\r\n]
    ;

// --- SNIPPET_ATTRIBUTE_MODE ---
mode SNIPPET_ATTRIBUTE_MODE;

SNIPPET_ATTR_NAME
    : Letter LetterOrDigit*
    ;

SNIPPET_EQUALS
    : '=' -> type(EQUALS), pushMode(SNIPPET_ATTR_VALUE)
    ;

COLON
    : ':' -> popMode, pushMode(PLAIN_TEXT_TAG)
    ;

SnippetAttribute_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

SnippetAttribute_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

SnippetAttribute_JAVADOC_INLINE_TAG_END
    : '}' { braceCounter--; } -> type(JAVADOC_INLINE_TAG_END), popMode, popMode
    ;

SnippetAttribute_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}? -> channel(LEADING_ASTERISKS),
      type(LEADING_ASTERISK)
    ;

// --- SNIPPET_ATTR_VALUE ---
// Ensures colons act as delimiter instead of being inside the attribute value.
mode SNIPPET_ATTR_VALUE;

Snippet_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Snippet_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

Snippet_ATTRIBUTE_VALUE
    : ' '* Snippet_ATTRIBUTE -> type(ATTRIBUTE_VALUE), popMode
    ;

Snippet_ATTRIBUTE
    : Snippet_DOUBLE_QUOTE_STRING | Snippet_SINGLE_QUOTE_STRING
      | Snippet_ATTCHARS | Snippet_HEXCHARS | Snippet_DECCHARS
    ;

fragment Snippet_ATTCHARS
    : Snippet_ATTCHAR+ ' '?
    ;

fragment Snippet_ATTCHAR
    : '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ';' | '#' | [0-9a-zA-Z]
    ;

fragment Snippet_HEXCHARS
    : '#' [0-9a-fA-F]+
    ;

fragment Snippet_DECCHARS
    : [0-9]+ '%'?
    ;

fragment Snippet_DOUBLE_QUOTE_STRING
    : '"' ~[:"]* '"'
    ;

fragment Snippet_SINGLE_QUOTE_STRING
    : '\'' ~[:']* '\''
    ;

// --- REFERENCE_MODE ---
mode REFERENCE_MODE;

EXTENDS: 'extends';
SUPER: 'super';

IDENTIFIER
    : ([a-zA-Z0-9_$] | '.')+
      {
          int la = _input.LA(1);
          if (Character.isWhitespace(la) || la == '\n' || la == '\r') {
              if (inSeeReferencePart) {
                  pushMode(DEFAULT_MODE);
                  inSeeReferencePart = false;
              } else {
                  pushMode(LINK_TAG_DESCRIPTION);
              }
          }
      }
    ;

QUESTION: '?';
HASH: '#';
LPAREN: '(' -> pushMode(PARAMETER_LIST);
SLASH: '/';

Link_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

Link_JAVADOC_INLINE_TAG_END
    : '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode
    ;

LT
    : { !inSeeReferencePart || previousTokenType == IDENTIFIER }? '<'
    ;

GT
    : '>' {
        int la = _input.LA(1);
        if (Character.isWhitespace(la) || la == '\n' || la == '\r') {
            if (inSeeReferencePart) {
                pushMode(DEFAULT_MODE);
                inSeeReferencePart = false;
            } else {
                pushMode(LINK_TAG_DESCRIPTION);
            }
        }
    }
    ;

Link_COMMA
    : ',' -> type(COMMA)
    ;

Link_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Link_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

STRING_LITERAL
    : '"' .*? '"' { inSeeReferencePart = false; } -> mode(DEFAULT_MODE)
    ;

See_TAG_OPEN
    : '<' {_input.seek(_input.index() - 1); inSeeReferencePart = false;}
      -> skip, mode(DEFAULT_MODE)
    ;

fragment LetterOrDigit: Letter | [0-9];
fragment Letter: [a-zA-Z$_];

// --- LINK_TAG_DESCRIPTION ---
mode LINK_TAG_DESCRIPTION;

LinkDescription_TEXT
    : LinkDescription_TEXT_CHAR+ -> type(TEXT)
    ;

LinkDescription_JAVADOC_INLINE_TAG_START
    : '{@' { braceCounter = 1; } -> pushMode(JAVADOC_INLINE_TAG_MODE),
      type(JAVADOC_INLINE_TAG_START)
    ;

LinkDescription_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

LinkDescription_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

LinkDescription_JAVADOC_INLINE_TAG_END
    : '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode, popMode
    ;

LinkDescription_TAG
    : '<' -> pushMode(TAG), type(TAG_OPEN)
    ;

fragment LinkDescription_TEXT_CHAR
    : {isNormalText()}? ~[{}\r\n]
    ;

// --- PARAMETER_LIST ---
mode PARAMETER_LIST;

ParameterList_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

ParameterList_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

ParameterList_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

PARAMETER_TYPE
    : ([a-zA-Z0-9_$] | '.' | '[' | ']')+
    ;

COMMA: ',';

RPAREN
    : ')' {
        if (inSeeReferencePart) {
            mode(DEFAULT_MODE);
            inSeeReferencePart = false;
        } else {
            popMode();
            pushMode(LINK_TAG_DESCRIPTION);
        }
    }
    ;

// --- VALUE_MODE ---
mode VALUE_MODE;

Value_IDENTIFIER
    : ([a-zA-Z0-9_$] | '.' | '-')+ -> type(IDENTIFIER)
    ;

FORMAT_SPECIFIER
    : '%' [#+\- 0,(]* [0-9]* ('.' [0-9]+)? [a-zA-Z]
    ;

Value_HASH: '#' -> type(HASH);

Value_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

Value_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Value_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

Value_JAVADOC_INLINE_TAG_END
    : '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode
    ;

// --- INLINE_TAG_DESCRIPTION ---
mode INLINE_TAG_DESCRIPTION;

InlineDescription_TEXT
    : InlineDescription_TEXT_CHAR+ -> type(TEXT)
    ;

fragment InlineDescription_TEXT_CHAR
    : {isNormalText()}? ~[}\r\n]
    ;

InlineDescription_JAVADOC_INLINE_TAG_START
    : '{@' { braceCounter = 1; } -> pushMode(JAVADOC_INLINE_TAG_MODE),
      type(JAVADOC_INLINE_TAG_START)
    ;

InlineDescription_TAG_OPEN
    : '<' -> pushMode(TAG), type(TAG_OPEN)
    ;

InlineDescription_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

InlineDescription_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

InlineDescription_JAVADOC_INLINE_TAG_END
    : '}' -> type(JAVADOC_INLINE_TAG_END), popMode, popMode
    ;

// --- INDEX_TERM_MODE ---
mode INDEX_TERM_MODE;

INDEX_TERM
    : ( '"' (~["\r\n])+ '"' | ~[ \t\r\n"}]+ | '"' (~["\r\n}])+ )
      -> popMode, pushMode(PLAIN_TEXT_TAG)
    ;

IndexTerm_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

IndexTerm_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

IndexTerm_WS
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

// --- TAG ---
mode TAG;

TAG_CLOSE
    : '>' { hasSeenTagName = false; } -> popMode
    ;

TAG_SLASH_CLOSE
    : '/>' { hasSeenTagName = false; } -> popMode
    ;

TAG_SLASH: '/';

EQUALS: '=' -> pushMode(ATTR_VALUE);

TAG_NAME
    : {hasSeenTagName == false}? TagNameStartChar TagNameChar*
      { hasSeenTagName = true; }
    ;

TAG_ATTR_NAME
    : {hasSeenTagName == true}? TagNameStartChar TagNameChar*
    ;

Tag_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

Tag_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

TAG_WHITESPACE
    : [ \t]+ -> type(WS), channel(WHITESPACES)
    ;

fragment TagNameChar
    : TagNameStartChar | '-' | '_' | '.' | DIGIT | '\u00B7'
      | '\u0300'..'\u036F' | '\u203F'..'\u2040'
    ;

fragment TagNameStartChar
    : [:a-zA-Z]
      | '\u2070'..'\u218F'
      | '\u2C00'..'\u2FEF'
      | '\u3001'..'\uD7FF'
      | '\uF900'..'\uFDCF'
      | '\uFDF0'..'\uFFFD'
    ;

fragment DIGIT: [0-9];

// --- ATTR_VALUE ---
mode ATTR_VALUE;

AttrValue_NEWLINE
    : NEWLINE {setAfterNewline();} -> type(NEWLINE), channel(NEWLINES)
    ;

AttrValue_LEADING_ASTERISK
    : [ \t]* '*' {isAfterNewline()}?
      -> channel(LEADING_ASTERISKS), type(LEADING_ASTERISK)
    ;

ATTRIBUTE_VALUE
    : ' '* ATTRIBUTE -> popMode
    ;

ATTRIBUTE
    : DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING | ATTCHARS | HEXCHARS
      | DECCHARS | UNQUOTED_STRING
    ;

fragment ATTCHARS: ATTCHAR+ ' '?;
fragment ATTCHAR
    : '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ':' | ';' | '#'
      | [0-9a-zA-Z]
    ;

fragment HEXCHARS: '#' [0-9a-fA-F]+;
fragment DECCHARS: [0-9]+ '%'?;

fragment DOUBLE_QUOTE_STRING
    : '"' ~[<"]* '"'
    ;

fragment SINGLE_QUOTE_STRING
    : '\'' ~[<']* '\''
    ;

fragment UNQUOTED_STRING
    : ( ~[> }\t\r\n/] | SLASH_IN_ATTR )+
    ;

fragment SLASH_IN_ATTR
    : '/' { _input.LA(1) != '>' }?
    ;
