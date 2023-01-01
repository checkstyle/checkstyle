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

lexer grammar JavaLanguageLexer;

channels { COMMENTS }
//Please add new tokens only in the end of list! Otherwise you break compatibility!
tokens {
    // Root of Checkstyle AST
    COMPILATION_UNIT,

    // Maintain compatibility in token numbering
    PLACEHOLDER1, NULL_TREE_LOOKAHEAD,

    // Pre-1.4 tokens
    BLOCK, MODIFIERS, OBJBLOCK, SLIST, CTOR_DEF, METHOD_DEF, VARIABLE_DEF,
    INSTANCE_INIT, STATIC_INIT, TYPE, CLASS_DEF, INTERFACE_DEF,
    PACKAGE_DEF, ARRAY_DECLARATOR, EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE,
    PARAMETERS, PARAMETER_DEF, LABELED_STAT, TYPECAST, INDEX_OP,
    POST_INC, POST_DEC, METHOD_CALL, EXPR, ARRAY_INIT,
    IMPORT, UNARY_MINUS, UNARY_PLUS, CASE_GROUP, ELIST, FOR_INIT, FOR_CONDITION,
    FOR_ITERATOR, EMPTY_STAT, FINAL, ABSTRACT,
    STRICTFP, SUPER_CTOR_CALL, CTOR_CALL,

    // ANTLR-generated pre-1.4 tokens now listed here to preserve their numerical
    // order so as to make all future version of this grammar backwardly compatible
    LITERAL_PACKAGE,SEMI,LITERAL_IMPORT,LBRACK,RBRACK,
    LITERAL_VOID,LITERAL_BOOLEAN,LITERAL_BYTE,
    LITERAL_CHAR,LITERAL_SHORT,LITERAL_INT,
    LITERAL_FLOAT,LITERAL_LONG,LITERAL_DOUBLE,
    IDENT,DOT,STAR,LITERAL_PRIVATE,LITERAL_PUBLIC,
    LITERAL_PROTECTED,LITERAL_STATIC,
    LITERAL_TRANSIENT,LITERAL_NATIVE,
    LITERAL_SYNCHRONIZED,LITERAL_VOLATILE,

    // Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_CLASS,LITERAL_EXTENDS,
    LITERAL_INTERFACE,LCURLY,RCURLY,COMMA,
    LITERAL_IMPLEMENTS,LPAREN,RPAREN,LITERAL_THIS,
    LITERAL_SUPER,ASSIGN,LITERAL_THROWS,COLON,
    LITERAL_IF,LITERAL_WHILE,LITERAL_DO,
    LITERAL_BREAK,LITERAL_CONTINUE,LITERAL_RETURN,
    LITERAL_SWITCH,LITERAL_THROW,LITERAL_FOR,
    LITERAL_ELSE,LITERAL_CASE,LITERAL_DEFAULT,

    // Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_TRY,LITERAL_CATCH,LITERAL_FINALLY,
    PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,
    BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,
    LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,
    LITERAL_INSTANCEOF,SL,SR,BSR,PLUS,MINUS,DIV,MOD,
    INC,DEC,BNOT,LNOT,LITERAL_TRUE,LITERAL_FALSE,
    LITERAL_NULL,LITERAL_NEW,NUM_INT,CHAR_LITERAL,
    STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE,WS,SINGLE_LINE_COMMENT,
    BLOCK_COMMENT_BEGIN,ESC,HEX_DIGIT,VOCAB,EXPONENT,FLOAT_SUFFIX,

    // Please add new tokens only in the end of list! Otherwise you break compatibility!
    // Token for Java 1.4 language enhancements
    ASSERT,

    // Tokens for Java 1.5 language enhancements
    STATIC_IMPORT, ENUM, ENUM_DEF, ENUM_CONSTANT_DEF, FOR_EACH_CLAUSE,
    ANNOTATION_DEF, ANNOTATIONS, ANNOTATION, ANNOTATION_MEMBER_VALUE_PAIR, ANNOTATION_FIELD_DEF,
    ANNOTATION_ARRAY_INIT, TYPE_ARGUMENTS, TYPE_ARGUMENT, TYPE_PARAMETERS,
    TYPE_PARAMETER, WILDCARD_TYPE, TYPE_UPPER_BOUNDS, TYPE_LOWER_BOUNDS, AT, ELLIPSIS,
    GENERIC_START, GENERIC_END, TYPE_EXTENSION_AND,

    // Please add new tokens only in the end of list! Otherwise you break compatibility!

    // token which was not included to grammar initially
    // we need to put it to the end to maintain binary compatibility
    // with previous versions
    DO_WHILE,

    // Tokens for Java 1.7 language enhancements
    RESOURCE_SPECIFICATION, RESOURCES, RESOURCE,

    // Tokens for Java 1.8
    DOUBLE_COLON,  METHOD_REF, LAMBDA,

    // Support of java comments has been extended
    BLOCK_COMMENT_END,COMMENT_CONTENT,

    // Need to add these here to preserve order of tokens
    SINGLE_LINE_COMMENT_CONTENT, BLOCK_COMMENT_CONTENT, STD_ESC,
    BINARY_DIGIT, ID_START, ID_PART, INT_LITERAL, LONG_LITERAL,
    FLOAT_LITERAL, DOUBLE_LITERAL, HEX_FLOAT_LITERAL, HEX_DOUBLE_LITERAL,
    SIGNED_INTEGER, BINARY_EXPONENT,

    PATTERN_VARIABLE_DEF, RECORD_DEF, LITERAL_RECORD,
    RECORD_COMPONENTS, RECORD_COMPONENT_DEF, COMPACT_CTOR_DEF,
    TEXT_BLOCK_LITERAL_BEGIN, TEXT_BLOCK_CONTENT, TEXT_BLOCK_LITERAL_END,
    LITERAL_YIELD, SWITCH_RULE,

    LITERAL_NON_SEALED, LITERAL_SEALED, LITERAL_PERMITS,
    PERMITS_CLAUSE, PATTERN_DEF
}

@header {
import com.puppycrawl.tools.checkstyle.grammar.CommentListener;
import com.puppycrawl.tools.checkstyle.grammar.CrAwareLexerSimulator;
}

@lexer::members {
    /**
     * We need to create a different constructor in order to use our
     * own implementation of the LexerATNSimulator. This is the
     * reason for the unused 'crAwareConstructor' argument.
     *
     * @param input the character stream to tokenize
     * @param crAwareConstructor dummy parameter
     */
    public JavaLanguageLexer(CharStream input, boolean crAwareConstructor) {
      super(input);
      _interp = new CrAwareLexerSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private CommentListener commentListener = null;

    /**
     * Sets the CommentListener for the lexer.
     *
     * @param commentListener the commentListener to use in this lexer
     */
    public void setCommentListener(CommentListener commentListener){
            this.commentListener = commentListener;
    }

    /** Tracks the starting line of a block comment. */
    int startLine = -1;

    /** Tracks the starting column of a block comment. */
    int startCol = -1;
}

// Keywords and restricted identifiers
ABSTRACT:                'abstract';
ASSERT:                  'assert';
LITERAL_BOOLEAN:         'boolean';
LITERAL_BREAK:           'break';
LITERAL_BYTE:            'byte';
LITERAL_CASE:            'case';
LITERAL_CATCH:           'catch';
LITERAL_CHAR:            'char';
LITERAL_CLASS:           'class';
LITERAL_CONST:           'const';
LITERAL_CONTINUE:        'continue';
LITERAL_DEFAULT:         'default';
LITERAL_DO:              'do';
LITERAL_DOUBLE:          'double';
LITERAL_ELSE:            'else';
ENUM:                    'enum';
EXTENDS_CLAUSE:          'extends';
FINAL:                   'final';
LITERAL_FINALLY:         'finally';
LITERAL_FLOAT:           'float';
LITERAL_FOR:             'for';
LITERAL_IF:              'if';
LITERAL_GOTO:            'goto';
LITERAL_IMPLEMENTS:      'implements';
IMPORT:                  'import';
LITERAL_INSTANCEOF:      'instanceof';
LITERAL_INT:             'int';
LITERAL_INTERFACE:       'interface';
LITERAL_LONG:            'long';
LITERAL_NATIVE:          'native';
LITERAL_NEW:             'new';
LITERAL_PACKAGE:         'package';
LITERAL_PRIVATE:         'private';
LITERAL_PROTECTED:       'protected';
LITERAL_PUBLIC:          'public';
LITERAL_RETURN:          'return';
LITERAL_SHORT:           'short';
LITERAL_STATIC:          'static';
STRICTFP:                'strictfp';
LITERAL_SUPER:           'super';
LITERAL_SWITCH:          'switch';
LITERAL_SYNCHRONIZED:    'synchronized';
LITERAL_THIS:            'this';
LITERAL_THROW:           'throw';
LITERAL_THROWS:          'throws';
LITERAL_TRANSIENT:       'transient';
LITERAL_TRY:             'try';
LITERAL_VOID:            'void';
LITERAL_VOLATILE:        'volatile';
LITERAL_WHILE:           'while';
LITERAL_RECORD:          'record';
LITERAL_YIELD:           'yield';
LITERAL_NON_SEALED:      'non-sealed';
LITERAL_SEALED:          'sealed';
LITERAL_PERMITS:         'permits';

// Literals
DECIMAL_LITERAL_LONG:    ('0' | [1-9] (Digits? | '_'+ Digits)) [lL];
DECIMAL_LITERAL:         ('0' | [1-9] (Digits? | '_'+ Digits));

HEX_LITERAL_LONG:        '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL];
HEX_LITERAL:             '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])?;

OCT_LITERAL_LONG:        '0' '_'* [0-7] ([0-7_]* [0-7])? [lL];
OCT_LITERAL:             '0' '_'* [0-7] ([0-7_]* [0-7])?;

BINARY_LITERAL_LONG:     '0' [bB] [01] ([01_]* [01])? [lL];
BINARY_LITERAL:          '0' [bB] [01] ([01_]* [01])?;


DOUBLE_LITERAL:          (Digits '.' Digits? | '.' Digits) ExponentPart? [dD]
             |           Digits (ExponentPart [dD] | [dD])
             ;
FLOAT_LITERAL:           (Digits '.' Digits? | '.' Digits) ExponentPart? [fF]?
             |           Digits (ExponentPart [fF]? | [fF])
             ;


HEX_DOUBLE_LITERAL:      '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits)
                         [pP] [+-]? Digits [dD];

HEX_FLOAT_LITERAL:       '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits)
                         [pP] [+-]? Digits [fFdD]?;

LITERAL_TRUE:            'true';

LITERAL_FALSE:           'false';

CHAR_LITERAL:            '\'' (EscapeSequence | ~['\\\r\n]) '\'';

STRING_LITERAL:          '"' (EscapeSequence | ~["\\\r\n])* '"';

TEXT_BLOCK_LITERAL_BEGIN: '"' '"' '"' -> pushMode(TextBlock);

LITERAL_NULL:            'null';

// Separators

LPAREN:                  '(';
RPAREN:                  ')';
LCURLY:                  '{';
RCURLY:                  '}';
LBRACK:                  '[';
RBRACK:                  ']';
SEMI:                    ';';
COMMA:                   ',';
DOT:                     '.';

// Operators

ASSIGN:                  '=';
GT:                      '>';
LT:                      '<';
LNOT:                    '!';
BNOT:                    '~';
QUESTION:                '?';
COLON:                   ':';
EQUAL:                   '==';
LE:                      '<=';
GE:                      '>=';
NOT_EQUAL:               '!=';
LAND:                    '&&';
LOR:                     '||';
INC:                     '++';
DEC:                     '--';
PLUS:                    '+';
MINUS:                   '-';
STAR:                    '*';
DIV:                     '/';
BAND:                    '&';
BOR:                     '|';
BXOR:                    '^';
MOD:                     '%';
//SR:                    '>>'; handled in parser
//SL:                    '<<'; handled in parser

PLUS_ASSIGN:             '+=';
MINUS_ASSIGN:            '-=';
STAR_ASSIGN:             '*=';
DIV_ASSIGN:              '/=';
BAND_ASSIGN:             '&=';
BOR_ASSIGN:              '|=';
BXOR_ASSIGN:             '^=';
MOD_ASSIGN:              '%=';
SL_ASSIGN:               '<<=';
SR_ASSIGN:               '>>=';
//BSR:                   '>>>'; handled in parser
BSR_ASSIGN:              '>>>=';

// Java 8 tokens

LAMBDA:                  '->';
DOUBLE_COLON:            '::';

// Additional symbols not defined in the lexical specification

AT:                      '@';
ELLIPSIS:                '...';

// Whitespace and comments

WS:                      [ \t\r\n\u000C]+ -> skip;

BLOCK_COMMENT_BEGIN:
     // Match block comment start delimiter, set start position
     '/*' { startLine = _tokenStartLine; startCol = _tokenStartCharPositionInLine; }

      // Match content of comment, non-greedy
     .*?

     // Match comment ending delimiter
     '*/'
    {
        // Trim delimiters from comment text
        setText(getText().substring(2, getText().length() - 2));
        // Report comment
        commentListener.reportBlockComment("/*", startLine, startCol,
            _interp.getLine(), _interp.getCharPositionInLine() - 1);
        startLine = startCol = -1;
    } -> channel(COMMENTS);

SINGLE_LINE_COMMENT:
    // Match single-line comment delimiter
    '//'
    {
        // Report comment
        commentListener.reportSingleLineComment("//",
            _tokenStartLine, _tokenStartCharPositionInLine);
    }
     // Match content, including terminating newline (or nothing if EOF)
    ~[\r\n]* ( '\n' | '\r' ('\n')? | /** nothing */ )
    {
        // Trim delimiters from comment text
        setText(getText().substring(2));
    } -> channel(COMMENTS);

// Identifiers

IDENT:         Letter LetterOrDigit*;

// Fragment rules

fragment ExponentPart
    : [eE] [+-]? Digits
    ;

fragment EscapeSequence
    : '\\'
        ('u'+ ( '0' '0' '5' ('c' | 'C' )
                ('\\' 'u'+ HexDigit HexDigit HexDigit HexDigit | StandardEscape)
              | HexDigit HexDigit HexDigit HexDigit
              )
        | StandardEscape
        )
    ;

fragment StandardEscape
    : [btnfrs"'\\]
    | [0-3] (([0-7]) [0-7]?)?
    | [4-7] ([0-9])?
    ;

fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment Letter
    // these are the "java letters" below 0x7F
    : [a-zA-Z$_]
    // covers all characters above 0x7F which are not a surrogate
    | ~[\u0000-\u007F\uD800-\uDBFF]
    // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    | [\uD800-\uDBFF] [\uDC00-\uDFFF]
    ;

// Text block lexical mode
mode TextBlock;
    TEXT_BLOCK_CONTENT
        : ( TwoDoubleQuotes
          | OneDoubleQuote
          | Newline
          | ~'"'
          | TextBlockStandardEscape
          )+
        ;

    TEXT_BLOCK_LITERAL_END
        : '"' '"' '"' -> popMode
        ;

    // Text block fragment rules
    fragment TextBlockStandardEscape
        :   '\\' [btnfrs"'\\]
        ;

    fragment Newline
        :  '\n' | '\r' ('\n')?
        ;

    fragment TwoDoubleQuotes
        :   '"''"' ( Newline | ~'"' )
        ;

    fragment OneDoubleQuote
        :   '"' ( Newline | ~'"' )
        ;
