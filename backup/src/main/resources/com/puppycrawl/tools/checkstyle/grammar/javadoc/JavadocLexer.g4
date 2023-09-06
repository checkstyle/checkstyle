///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

lexer grammar JavadocLexer;

//Please add new tokens only in the end of list! Otherwise you break compatibility!
tokens {
    LEADING_ASTERISK, HTML_COMMENT_START,
    // Deprecated since https://github.com/checkstyle/checkstyle/issues/4937
    DEPRECATED_CDATA_DO_NOT_USE,
    WS, START, NEWLINE, AUTHOR_LITERAL, DEPRECATED_LITERAL, EXCEPTION_LITERAL,
    PARAM_LITERAL, RETURN_LITERAL, SEE_LITERAL, SERIAL_LITERAL,
    SERIAL_FIELD_LITERAL, SERIAL_DATA_LITERAL, SINCE_LITERAL, THROWS_LITERAL,
    VERSION_LITERAL, JAVADOC_INLINE_TAG_START, JAVADOC_INLINE_TAG_END,
    CUSTOM_NAME, LITERAL_INCLUDE, LITERAL_EXCLUDE, CHAR, PARAMETER_NAME, Char1,
    STRING, PACKAGE_CLASS, DOT, HASH, CLASS, Char2, MEMBER,
    LEFT_BRACE, RIGHT_BRACE, ARGUMENT, COMMA, Char20, FIELD_NAME,
    Char3, FIELD_TYPE, Char4, CLASS_NAME, Char5, CODE_LITERAL,
    DOC_ROOT_LITERAL, INHERIT_DOC_LITERAL, LINK_LITERAL, LINKPLAIN_LITERAL,
    LITERAL_LITERAL, VALUE_LITERAL, Char7, Char8,
    Char10, END, SLASH_END, SLASH, EQUALS, P_HTML_TAG_NAME,
    LI_HTML_TAG_NAME, TR_HTML_TAG_NAME, TD_HTML_TAG_NAME, TH_HTML_TAG_NAME,
    BODY_HTML_TAG_NAME, COLGROUP_HTML_TAG_NAME, DD_HTML_TAG_NAME,
    DT_HTML_TAG_NAME, HEAD_HTML_TAG_NAME, HTML_HTML_TAG_NAME, OPTION_HTML_TAG_NAME,
    TBODY_HTML_TAG_NAME, TFOOT_HTML_TAG_NAME, THEAD_HTML_TAG_NAME,
    AREA_HTML_TAG_NAME, BASE_HTML_TAG_NAME, BASEFONT_HTML_TAG_NAME,
    BR_HTML_TAG_NAME, COL_HTML_TAG_NAME, FRAME_HTML_TAG_NAME, HR_HTML_TAG_NAME,
    IMG_HTML_TAG_NAME, INPUT_HTML_TAG_NAME, ISINDEX_HTML_TAG_NAME,
    LINK_HTML_TAG_NAME, META_HTML_TAG_NAME, PARAM_HTML_TAG_NAME,
    EMBED_HTML_TAG_NAME, KEYGEN_HTML_TAG_NAME, ATTR_VALUE, Char12,
    HTML_COMMENT_END, SOURCE_HTML_TAG_NAME, TRACK_HTML_TAG_NAME,
    WBR_HTML_TAG_NAME, OPTGROUP_HTML_TAG_NAME, RB_HTML_TAG_NAME,
    RT_HTML_TAG_NAME, RTC_HTML_TAG_NAME, RP_HTML_TAG_NAME, HTML_TAG_NAME, Char11
}

@lexer::header {
import java.util.*;

import com.puppycrawl.tools.checkstyle.grammar.CrAwareLexerSimulator;
}

@lexer::members {
      boolean recognizeXmlTags = true;
      boolean isJavadocTagAvailable = true;
      int insideJavadocInlineTag = 0;
      boolean insidePreTag = false;
      boolean referenceCatched = false;

      boolean insideReferenceArguments = false;

      boolean htmlTagNameCatched = false;
      boolean attributeCatched = false;

      int previousTokenType = 0;
      int previousToPreviousTokenType = 0;

      public void emit(Token token) {
            super.emit(token);
            previousToPreviousTokenType = previousTokenType;
            previousTokenType = token.getType();

            if (previousTokenType == NEWLINE) {
                  isJavadocTagAvailable = true;
            } else if (previousTokenType != WS && previousTokenType != LEADING_ASTERISK) {
                  isJavadocTagAvailable = false;
            }
      }

      public void skipCurrentTokenConsuming() {
            _input.seek(_input.index() - 1);
      }

    /**
     * We need to create a different constructor in order to use our
     * own implementation of the LexerATNSimulator. This is the
     * reason for the unused 'crAwareConstructor' argument.
     *
     * @param input the character stream to tokenize
     * @param crAwareConstructor dummy parameter
     */
    public JavadocLexer(CharStream input, boolean crAwareConstructor) {
      super(input);
      _interp = new CrAwareLexerSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }
}

LEADING_ASTERISK : ( (' '|'\t') {_tokenStartCharPositionInLine == 0
                                    || previousTokenType == NEWLINE}? ) (' '|'\t')* '*'
      | '*' {_tokenStartCharPositionInLine == 0 || previousTokenType == NEWLINE}?
      ;

HTML_COMMENT_START : '<!--' {recognizeXmlTags}?
      -> pushMode(htmlComment)
      ;
WS      :   (' '|'\t')+ ;

START: '<' {recognizeXmlTags && (Character.isLetter(_input.LA(1)) || _input.LA(1) == '/')}?
      -> pushMode(xmlTagDefinition)
      ;

//PRE_TAG_START: ('<pre>' | '<PRE>') {!insidePreTag}?
//     {insidePreTag=true; recognizeXmlTags=false;}
//      ;
//PRE_TAG_END: ('</pre>' | '</PRE>') {insidePreTag}?
//      {insidePreTag=false; recognizeXmlTags=true;}
//      ;

NEWLINE: '\n' | '\r\n' | '\r';

AUTHOR_LITERAL : '@author' {isJavadocTagAvailable}?;
DEPRECATED_LITERAL : '@deprecated' {isJavadocTagAvailable}?;
EXCEPTION_LITERAL : '@exception' {isJavadocTagAvailable}? -> pushMode(exception);
PARAM_LITERAL : '@param' {isJavadocTagAvailable}? -> pushMode(param);
RETURN_LITERAL : '@return' {isJavadocTagAvailable}?;
SEE_LITERAL : '@see' {isJavadocTagAvailable}? -> pushMode(seeLink);
SERIAL_LITERAL : '@serial' {isJavadocTagAvailable}?;
SERIAL_FIELD_LITERAL : '@serialField' {isJavadocTagAvailable}? -> pushMode(serialField);
SERIAL_DATA_LITERAL : '@serialData' {isJavadocTagAvailable}?;
SINCE_LITERAL : '@since' {isJavadocTagAvailable}?;
THROWS_LITERAL : '@throws' {isJavadocTagAvailable}? -> pushMode(exception);
VERSION_LITERAL : '@version' {isJavadocTagAvailable}?;

JAVADOC_INLINE_TAG_START:
      '{' {_input.LA(1) == '@'}? {insideJavadocInlineTag++;} -> pushMode(javadocInlineTag);

JAVADOC_INLINE_TAG_END: '}' {insideJavadocInlineTag>0}?
      {insideJavadocInlineTag--; recognizeXmlTags=true;}
      ;

CUSTOM_NAME: '@' [a-zA-Z0-9:._-]+ {isJavadocTagAvailable}?;

LITERAL_INCLUDE: 'include' {previousToPreviousTokenType==SERIAL_LITERAL}?;
LITERAL_EXCLUDE: 'exclude' {previousToPreviousTokenType==SERIAL_LITERAL}?;

CHAR        :   . ;

///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAG MODES  ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
mode param;
Space0: WS -> type(WS);
PARAMETER_NAME: [a-zA-Z0-9<>_$]+ -> mode(DEFAULT_MODE);
Char1: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);
///////////////////////////////////////////////////////////////////////////////////////////////
mode seeLink;
Space1: WS
      {
            if (referenceCatched) {
                  _mode = DEFAULT_MODE;
                  referenceCatched = false;
            }
      }
      -> type(WS);
Newline5: NEWLINE
      {
            if (referenceCatched) {
                  _mode = DEFAULT_MODE;
                  referenceCatched = false;
            }
      }
      -> type(NEWLINE);
Leading_asterisk3: LEADING_ASTERISK -> type(LEADING_ASTERISK);
XmlTagOpen1: '<' -> type(START), pushMode(xmlTagDefinition);
STRING: '"' .*? '"' {referenceCatched = false;} -> mode(DEFAULT_MODE);
PACKAGE_CLASS: Identifier ('.' Identifier)* {referenceCatched = true;};
DOT: '.';
HASH: '#' {referenceCatched = true;} -> mode(classMember);
CLASS: [A-Z] [a-zA-Z0-9_$]* {referenceCatched = true;};
End20: JAVADOC_INLINE_TAG_END
      {
            insideJavadocInlineTag--;
            recognizeXmlTags=true;
            referenceCatched = false;
      }
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
// exit from 'seeLink' mode without consuming current character
Char2: .
      {
            skipCurrentTokenConsuming();
            referenceCatched = false;
      } -> skip, mode(DEFAULT_MODE);

///////////////////////////////////////////////////////////////////////////////////////////////
mode classMember;
MEMBER: [a-zA-Z0-9_$]+ {!insideReferenceArguments}?;
LEFT_BRACE: '(' {insideReferenceArguments=true;};
RIGHT_BRACE: ')' {insideReferenceArguments=false;};
ARGUMENT: ([a-zA-Z0-9_$] | '.' | '[' | ']')+ {insideReferenceArguments}?;
COMMA: ',' {insideReferenceArguments}?;
Leading_asterisk6: LEADING_ASTERISK
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
            }
      } -> type(LEADING_ASTERISK);
Newline7: NEWLINE
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
            }
      } -> type(NEWLINE);
Space20: WS
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
            }
      }  -> type(WS);
End2: JAVADOC_INLINE_TAG_END
      {
            insideJavadocInlineTag--;
            recognizeXmlTags=true;
            referenceCatched = false;
            insideReferenceArguments = false;
      }
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
Char20: .
      {
            skipCurrentTokenConsuming();
            referenceCatched = false;
            insideReferenceArguments = false;
      } -> skip, mode(DEFAULT_MODE);
///////////////////////////////////////////////////////////////////////////////////////////////
mode serialField;
Space2: WS -> type(WS);
FIELD_NAME: [a-zA-Z0-9_$]+ -> mode(serialFieldFieldType);
Char3: .
      {
            skipCurrentTokenConsuming();
            referenceCatched = false;

      } -> skip, mode(DEFAULT_MODE);
///////////////////////////////////////////////////////////////////////////////////////////////
mode serialFieldFieldType;
Space3: WS -> type(WS);
FIELD_TYPE: [a-zA-Z0-9_$]+ -> mode(DEFAULT_MODE);
Char4: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);
///////////////////////////////////////////////////////////////////////////////////////////////
mode exception;
Space4: WS -> type(WS);
CLASS_NAME: ([a-zA-Z0-9_$] | '.')+ -> mode(DEFAULT_MODE);
Char5: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);



///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAG MODES  ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
mode javadocInlineTag;
CODE_LITERAL : '@code' {recognizeXmlTags=false;} -> mode(code);
DOC_ROOT_LITERAL : '@docRoot' -> mode(DEFAULT_MODE);
INHERIT_DOC_LITERAL : '@inheritDoc' -> mode(DEFAULT_MODE);
LINK_LITERAL : '@link' -> pushMode(seeLink);
LINKPLAIN_LITERAL : '@linkplain' -> pushMode(seeLink);
LITERAL_LITERAL : '@literal' {recognizeXmlTags=false;} -> mode(code);
VALUE_LITERAL : '@value' -> pushMode(value);
CustomName1: '@' [a-zA-Z0-9:._-]+ {recognizeXmlTags=false;}
                                      -> type(CUSTOM_NAME), mode(DEFAULT_MODE);
Char6: . -> type(CHAR), mode(DEFAULT_MODE);
///////////////////////////////////////////////////////////////////////////////////////////////
mode code;
Space7: WS -> type(WS), mode(codeText);
Newline2: NEWLINE -> type(NEWLINE), mode(codeText);
Leading_asterisk4: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Char7: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);

///////////////////////////////////////////////////////////////////////////////////////////////
mode codeText;
Leading_asterisk5: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Brackets: '{' (~[}] | Brackets)* '}' -> type(CHAR);
Text: ~[}] -> type(CHAR);
Char8: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);

///////////////////////////////////////////////////////////////////////////////////////////////
mode value;
Leading_asterisk2: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Space6: WS -> type(WS);
Newline4: NEWLINE -> type(NEWLINE);
Package_Class2: PACKAGE_CLASS -> type(PACKAGE_CLASS);
Hash2: HASH -> type(HASH), mode(classMember);
End1: JAVADOC_INLINE_TAG_END
      {insideJavadocInlineTag--; recognizeXmlTags=true;}
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
Char10: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);

fragment JavaLetter: [A-Za-z_$];
fragment JavaLetterOrDigit: [0-9A-Za-z_$];
fragment Identifier: JavaLetter (JavaLetterOrDigit)*;



///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  HTML TAG MODES  //////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
mode xmlTagDefinition;

END       :   '>' {htmlTagNameCatched = false;} -> mode(DEFAULT_MODE) ;
SLASH_END :   '/>' {htmlTagNameCatched = false;} -> mode(DEFAULT_MODE) ;
SLASH       :   '/' ;
EQUALS      :   '=' -> mode(htmlAttr);

// with optional end tag
P_HTML_TAG_NAME: P {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
LI_HTML_TAG_NAME: L I {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TR_HTML_TAG_NAME: T R {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TD_HTML_TAG_NAME: T D {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TH_HTML_TAG_NAME: T H {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
BODY_HTML_TAG_NAME: B O D Y {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
COLGROUP_HTML_TAG_NAME: C O L G R O U P {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
DD_HTML_TAG_NAME: D D {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
DT_HTML_TAG_NAME: D T {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
HEAD_HTML_TAG_NAME: H E A D {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
HTML_HTML_TAG_NAME: H T M L {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
OPTION_HTML_TAG_NAME: O P T I O N {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TBODY_HTML_TAG_NAME: T B O D Y {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TFOOT_HTML_TAG_NAME: T F O O T {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
THEAD_HTML_TAG_NAME: T H E A D {!htmlTagNameCatched}? {htmlTagNameCatched=true;};

// singleton tags
AREA_HTML_TAG_NAME: A R E A {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
BASE_HTML_TAG_NAME: B A S E {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
BASEFONT_HTML_TAG_NAME: B A S E F O N T {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
BR_HTML_TAG_NAME: B R {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
COL_HTML_TAG_NAME: C O L {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
FRAME_HTML_TAG_NAME: F R A M E {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
HR_HTML_TAG_NAME: H R {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
IMG_HTML_TAG_NAME: I M G {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
INPUT_HTML_TAG_NAME: I N P U T {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
ISINDEX_HTML_TAG_NAME: I S I N D E X {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
LINK_HTML_TAG_NAME: L I N K {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
META_HTML_TAG_NAME: M E T A {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
PARAM_HTML_TAG_NAME: P A R A M {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
EMBED_HTML_TAG_NAME: E M B E D {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
KEYGEN_HTML_TAG_NAME: K E Y G E N {!htmlTagNameCatched}? {htmlTagNameCatched=true;};

fragment
HEXDIGIT    :   [a-fA-F0-9] ;

fragment
DIGIT       :   [0-9] ;

fragment
NAME_CHAR    :   NAME_START_CHAR
            |   '-' | '_' | '.' | DIGIT
            |   '\u00B7'
            |   '\u0300'..'\u036F'
            |   '\u203F'..'\u2040'
            ;

fragment
NAME_START_CHAR
            :   [:a-zA-Z]
            |   '\u2070'..'\u218F'
            |   '\u2C00'..'\u2FEF'
            |   '\u3001'..'\uD7FF'
            |   '\uF900'..'\uFDCF'
            |   '\uFDF0'..'\uFFFD'
            ;

fragment
FragmentReference: ([a-zA-Z0-9_-] | '.')+
      | ([a-zA-Z0-9_-] | '.')* '#' [a-zA-Z0-9_-]+ ( '(' (([a-zA-Z0-9_-] | '.')+ | ',' | ' ')* ')' )?
      ;
//case-insensitive alphabet
fragment A:('a'|'A');
fragment B:('b'|'B');
fragment C:('c'|'C');
fragment D:('d'|'D');
fragment E:('e'|'E');
fragment F:('f'|'F');
fragment G:('g'|'G');
fragment H:('h'|'H');
fragment I:('i'|'I');
fragment J:('j'|'J');
fragment K:('k'|'K');
fragment L:('l'|'L');
fragment M:('m'|'M');
fragment N:('n'|'N');
fragment O:('o'|'O');
fragment P:('p'|'P');
fragment Q:('q'|'Q');
fragment R:('r'|'R');
fragment S:('s'|'S');
fragment T:('t'|'T');
fragment U:('u'|'U');
fragment V:('v'|'V');
fragment W:('w'|'W');
fragment X:('x'|'X');
fragment Y:('y'|'Y');
fragment Z:('z'|'Z');
///////////////////////////////////////////////////////////////////////////////////////////////
mode htmlAttr;
Leading_asterisk7: LEADING_ASTERISK -> type(LEADING_ASTERISK);
NewLine8: NEWLINE -> type(NEWLINE);

ATTR_VALUE  : '"' ~[<"]* '"'        {!attributeCatched}? {attributeCatched=true;}
            | '\'' ~[<']* '\''      {!attributeCatched}? {attributeCatched=true;}
            | ( '-' | '+' | DIGIT)+ {!attributeCatched}? {attributeCatched=true;}
            | (~[> \t\r\n/] | SlashInAttr)+ {!attributeCatched}? {attributeCatched=true;}
            ;

fragment SlashInAttr: '/' {_input.LA(1) != '>'}?;

Char12: . {attributeCatched}?
      {
            skipCurrentTokenConsuming();
            attributeCatched = false;
      } -> skip, mode(xmlTagDefinition);
WhiteSpace2: WS -> type(WS);
///////////////////////////////////////////////////////////////////////////////////////////////
mode htmlComment;
HTML_COMMENT_END: '-->' -> mode(DEFAULT_MODE);
LeadingAst: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline6: NEWLINE -> type(NEWLINE);
WhiteSpace: WS -> type(WS);
CommentChar: . -> type(CHAR);

mode xmlTagDefinition;
SOURCE_HTML_TAG_NAME: S O U R C E {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
TRACK_HTML_TAG_NAME: T R A C K {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
WBR_HTML_TAG_NAME: W B R {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
OPTGROUP_HTML_TAG_NAME: O P T G R O U P {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
RB_HTML_TAG_NAME: R B {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
RT_HTML_TAG_NAME: R T {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
RTC_HTML_TAG_NAME: R T C {!htmlTagNameCatched}? {htmlTagNameCatched=true;};
RP_HTML_TAG_NAME: R P {!htmlTagNameCatched}? {htmlTagNameCatched=true;};

// other tag names and attribute names
HTML_TAG_NAME: NAME_START_CHAR NAME_CHAR* {htmlTagNameCatched=true;};

LeadingLEADING_ASTERISK1: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline1: NEWLINE -> type(NEWLINE);
WhiteSpace3: WS -> type(WS);

Char11: .
      {
            skipCurrentTokenConsuming();
            htmlTagNameCatched = false;
      } -> skip, mode(DEFAULT_MODE);
