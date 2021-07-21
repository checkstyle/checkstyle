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
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

lexer grammar JavaLexer;

channels { COMMENTS }
//Please add new tokens only in the end of list! Otherwise you break compatibility!
tokens {
    //These are for compatability, antlr4 doesn't count the EOF token
    EOF_PLACEHOLDER, PLACEHOLDER1, NULL_TREE_LOOKAHEAD,

    //Pre-1.4 tokens
    BLOCK, MODIFIERS, OBJBLOCK, SLIST, CTOR_DEF, METHOD_DEF, VARIABLE_DEF,
    INSTANCE_INIT, STATIC_INIT, TYPE, CLASS_DEF, INTERFACE_DEF,
    PACKAGE_DEF, ARRAY_DECLARATOR, EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE,
    PARAMETERS, PARAMETER_DEF, LABELED_STAT, TYPECAST, INDEX_OP,
    POST_INC, POST_DEC, METHOD_CALL, EXPR, ARRAY_INIT,
    IMPORT, UNARY_MINUS, UNARY_PLUS, CASE_GROUP, ELIST, FOR_INIT, FOR_CONDITION,
    FOR_ITERATOR, EMPTY_STAT, FINAL, ABSTRACT,
    STRICTFP, SUPER_CTOR_CALL, CTOR_CALL,

    //ANTLR-generated pre-1.4 tokens now listed here to preserve their numerical
    //order so as to make all future version of this grammar backwardly compatible
    LITERAL_package,SEMI,LITERAL_import,LBRACK,RBRACK,
    LITERAL_void,LITERAL_boolean,LITERAL_byte,
    LITERAL_char,LITERAL_short,LITERAL_int,
    LITERAL_float,LITERAL_long,LITERAL_double,
    IDENT,DOT,STAR,LITERAL_private,LITERAL_public,
    LITERAL_protected,LITERAL_static,
    LITERAL_transient,LITERAL_native,
    LITERAL_synchronized,LITERAL_volatile,

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_class,LITERAL_extends,
    LITERAL_interface,LCURLY,RCURLY,COMMA,
    LITERAL_implements,LPAREN,RPAREN,LITERAL_this,
    LITERAL_super,ASSIGN,LITERAL_throws,COLON,
    LITERAL_if,LITERAL_while,LITERAL_do,
    LITERAL_break,LITERAL_continue,LITERAL_return,
    LITERAL_switch,LITERAL_throw,LITERAL_for,
    LITERAL_else,LITERAL_case,LITERAL_default,

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    LITERAL_try,LITERAL_catch,LITERAL_finally,
    PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,SR_ASSIGN,
    BSR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,QUESTION,
    LOR,LAND,BOR,BXOR,BAND,NOT_EQUAL,EQUAL,LT,GT,LE,GE,
    LITERAL_instanceof,SL,SR,BSR,PLUS,MINUS,DIV,MOD,
    INC,DEC,BNOT,LNOT,LITERAL_true,LITERAL_false,
    LITERAL_null,LITERAL_new,NUM_INT,CHAR_LITERAL,
    STRING_LITERAL,NUM_FLOAT,NUM_LONG,NUM_DOUBLE,WS,SINGLE_LINE_COMMENT,
    BLOCK_COMMENT_BEGIN,ESC,HEX_DIGIT,VOCAB,EXPONENT,FLOAT_SUFFIX,

    //Please add new tokens only in the end of list! Otherwise you break compatibility!
    //Token for Java 1.4 language enhancements
    ASSERT,

    //Tokens for Java 1.5 language enhancements
    STATIC_IMPORT, ENUM, ENUM_DEF, ENUM_CONSTANT_DEF, FOR_EACH_CLAUSE,
    ANNOTATION_DEF, ANNOTATIONS, ANNOTATION, ANNOTATION_MEMBER_VALUE_PAIR, ANNOTATION_FIELD_DEF,
    ANNOTATION_ARRAY_INIT, TYPE_ARGUMENTS, TYPE_ARGUMENT, TYPE_PARAMETERS,
    TYPE_PARAMETER, WILDCARD_TYPE, TYPE_UPPER_BOUNDS, TYPE_LOWER_BOUNDS, AT, ELLIPSIS,
    GENERIC_START, GENERIC_END, TYPE_EXTENSION_AND,

    //Please add new tokens only in the end of list! Otherwise you break compatibility!

    // token which was not included to grammar initially
    // we need to put it to the end to maintain binary compatibility
    // with previous versions
    DO_WHILE,

    //Tokens for Java 1.7 language enhancements
    RESOURCE_SPECIFICATION, RESOURCES, RESOURCE,

    //Tokens for Java 1.8
    DOUBLE_COLON,  METHOD_REF, LAMBDA,

    //Support of java comments has been extended
    BLOCK_COMMENT_END,COMMENT_CONTENT,

    //Need to add these here to preserve order of tokens
    SINGLE_LINE_COMMENT_CONTENT, BLOCK_COMMENT_CONTENT, STD_ESC,
    BINARY_DIGIT, ID_START, ID_PART, INT_LITERAL, LONG_LITERAL,
    FLOAT_LITERAL, DOUBLE_LITERAL, HEX_FLOAT_LITERAL, HEX_DOUBLE_LITERAL,
    SIGNED_INTEGER, BINARY_EXPONENT,

    PATTERN_VARIABLE_DEF, RECORD_DEF, LITERAL_record,
    RECORD_COMPONENTS, RECORD_COMPONENT_DEF, COMPACT_CTOR_DEF,
    TEXT_BLOCK_LITERAL_BEGIN, TEXT_BLOCK_CONTENT, TEXT_BLOCK_LITERAL_END,
    LITERAL_yield, SWITCH_RULE,

    LITERAL_non_sealed, LITERAL_sealed, LITERAL_permits,
    PERMITS_CLAUSE
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
    public JavaLexer(CharStream input, boolean crAwareConstructor) {
      super(input);
      _interp = new CrAwareLexerSimulator(this,
        new ATNDeserializer().deserialize(_serializedATN.toCharArray()),
         _decisionToDFA, _sharedContextCache);
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
LITERAL_boolean:         'boolean';
LITERAL_break:           'break';
LITERAL_byte:            'byte';
LITERAL_case:            'case';
LITERAL_catch:           'catch';
LITERAL_char:            'char';
LITERAL_class:           'class';
LITERAL_const:           'const';
LITERAL_continue:        'continue';
LITERAL_default:         'default';
LITERAL_do:              'do';
LITERAL_double:          'double';
LITERAL_else:            'else';
ENUM:                    'enum';
EXTENDS_CLAUSE:          'extends';
FINAL:                   'final';
LITERAL_finally:         'finally';
LITERAL_float:           'float';
LITERAL_for:             'for';
LITERAL_if:              'if';
LITERAL_goto:            'goto';
LITERAL_implements:      'implements';
IMPORT:                  'import';
LITERAL_instanceof:      'instanceof';
LITERAL_int:             'int';
LITERAL_interface:       'interface';
LITERAL_long:            'long';
LITERAL_native:          'native';
LITERAL_new:             'new';
LITERAL_package:         'package';
LITERAL_private:         'private';
LITERAL_protected:       'protected';
LITERAL_public:          'public';
LITERAL_return:          'return';
LITERAL_short:           'short';
LITERAL_static:          'static';
STRICTFP:                'strictfp';
LITERAL_super:           'super';
LITERAL_switch:          'switch';
LITERAL_synchronized:    'synchronized';
LITERAL_this:            'this';
LITERAL_throw:           'throw';
LITERAL_throws:          'throws';
LITERAL_transient:       'transient';
LITERAL_try:             'try';
LITERAL_void:            'void';
LITERAL_volatile:        'volatile';
LITERAL_while:           'while';
LITERAL_record:          'record';
LITERAL_yield:           'yield';
LITERAL_non_sealed:      'non-sealed';
LITERAL_sealed:          'sealed';
LITERAL_permits:         'permits';

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

LITERAL_true:            'true';

LITERAL_false:           'false';

CHAR_LITERAL:            '\'' (~['\\\r\n] | EscapeSequence) '\'';

STRING_LITERAL:          '"' (~["\\\r\n] | EscapeSequence)* '"';

TEXT_BLOCK_LITERAL_BEGIN: '"' '"' '"' -> pushMode(TextBlock);

LITERAL_null:            'null';

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
    // Match single line comment delimiter
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
            ( '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
            | StandardEscape
            )
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

    // Text Block Fragment Rules
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
