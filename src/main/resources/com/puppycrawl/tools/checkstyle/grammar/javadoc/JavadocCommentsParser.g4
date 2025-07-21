parser grammar JavadocCommentsParser;

options {
    tokenVocab = JavadocCommentsLexer;
}

@parser::header {
import java.util.Set;
import java.util.stream.Collectors;
import com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsParserUtility;
import com.puppycrawl.tools.checkstyle.grammar.SimpleToken;
}

@parser::members {
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
}

javadoc
    : mainDescription? blockTag* EOF
    ;

mainDescription
    : (TEXT | inlineTag | htmlElement)+
    ;

// Block tags
blockTag
    : authorTag
    | deprecatedTag
    | returnTag
    | parameterTag
    | throwsTag
    | exceptionTag
    | sinceTag
    | versionTag
    | seeTag
    | hiddenTag
    | usesTag
    | providesTag
    | serialTag
    | serialDataTag
    | serialFieldTag
    | customBlockTag
    ;

authorTag
    : AT_SIGN tagName=AUTHOR description?
    ;

deprecatedTag
    : AT_SIGN tagName=DEPRECATED description?
    ;

returnTag
    : AT_SIGN tagName=RETURN description?
    ;

parameterTag
    : AT_SIGN tagName=PARAM paramName=PARAMETER_NAME description?
    ;

throwsTag
    : AT_SIGN tagName=THROWS exceptionName=qualifiedName description?
    ;

exceptionTag
    : AT_SIGN tagName=EXCEPTION exceptionName=qualifiedName description?
    ;

sinceTag
    : AT_SIGN tagName=SINCE description?
    ;

versionTag
    : AT_SIGN tagName=VERSION description?
    ;

seeTag
    : AT_SIGN
     ( tagName=SEE STRING_LITERAL
     | tagName=SEE reference description?
     | tagName=SEE htmlElement description?
     )
    ;

hiddenTag: AT_SIGN tagName=LITERAL_HIDDEN description?;

usesTag: AT_SIGN tagName=USES serviceType=qualifiedName description?;

providesTag: AT_SIGN tagName=PROVIDES serviceType=qualifiedName description?;

serialTag: AT_SIGN tagName=SERIAL description?;

serialDataTag: AT_SIGN tagName=SERIAL_DATA description?;

serialFieldTag: AT_SIGN tagName=SERIAL_FIELD fieldName=IDENTIFIER FIELD_TYPE  description?;

customBlockTag
    : AT_SIGN tagName=CUSTOM_NAME description?
    ;

// Inline tags
inlineTag
    : JAVADOC_INLINE_TAG_START
      ( codeInlineTag
      | linkInlineTag
      | linkPlainInlineTag
      | valueInlineTag
      | inheritDocInlineTag
      | summaryInlineTag
      | systemPropertyInlineTag
      | indexInlineTag
      | returnInlineTag
      | literalInlineTag
      | snippetInlineTag
      | customInlineTag
      )
      JAVADOC_INLINE_TAG_END
    ;

codeInlineTag
    : tagName=CODE TEXT*
    ;

linkPlainInlineTag
    : tagName=LINKPLAIN reference description?
    ;

linkInlineTag
    : tagName=LINK reference description?
    ;

valueInlineTag
    : tagName=VALUE FORMAT_SPECIFIER? reference?
    ;

inheritDocInlineTag
    : tagName=INHERIT_DOC reference?
    ;

summaryInlineTag
    : tagName=SUMMARY description?
    ;

systemPropertyInlineTag
    : tagName=SYSTEM_PROPERTY propertyName=qualifiedName?
    ;

indexInlineTag
    : tagName=INDEX INDEX_TERM description?
    ;

returnInlineTag
    : tagName=RETURN description?
    ;

literalInlineTag
    : tagName=LITERAL description?
    ;

snippetInlineTag
    : tagName=SNIPPET snippetAttributes+=snippetAttribute* (COLON snippetBody)?
    ;

customInlineTag
    : tagName=CUSTOM_NAME description?
    ;

// Components
reference
    : HASH memberReference
    | (module=qualifiedName SLASH)? type=typeName (HASH memberReference)?
    ;

typeName
    : qualifiedName (typeArguments)?
    ;

qualifiedName
    : IDENTIFIER
    ;

typeArguments
    : LT typeArgument (COMMA typeArgument)* GT
    ;

typeArgument
    : typeName
    | QUESTION
    | QUESTION EXTENDS typeName
    | QUESTION SUPER typeName
    ;

memberReference
    : IDENTIFIER (LPAREN parameterTypeList? RPAREN)?
    ;

parameterTypeList
    : PARAMETER_TYPE (COMMA PARAMETER_TYPE)*
    ;

snippetAttribute
    : SNIPPET_ATTR_NAME EQUALS ATTRIBUTE_VALUE
    ;

snippetBody
    : TEXT+
    ;

description
    : (TEXT | inlineTag | htmlElement)+
    ;

// HTML Elements
htmlElement
    : voidElement
    | selfClosingElement
    | tightElement
    | nonTightElement
    ;

voidElement
    : { isVoidTag() }? htmlTagStart
    ;

tightElement
    : { !JavadocCommentsParserUtility.isNonTightTag(_input, unclosedTagNameTokens) }?
      htmlTagStart htmlContent? htmlTagEnd
    ;

nonTightElement
    : htmlTagStart nonTightHtmlContent?
    ;

selfClosingElement
    : TAG_OPEN TAG_NAME htmlAttribute* TAG_SLASH_CLOSE
    ;

htmlTagStart
    : TAG_OPEN TAG_NAME htmlAttribute* TAG_CLOSE
    ;

htmlTagEnd
    : TAG_OPEN TAG_SLASH TAG_NAME TAG_CLOSE
    ;

htmlAttribute
    : TAG_ATTR_NAME (EQUALS ATTRIBUTE_VALUE)?
    ;

htmlContent
    : (TEXT | htmlElement | inlineTag)+
    ;

nonTightHtmlContent
    : (TEXT | inlineTag)+
    ;
