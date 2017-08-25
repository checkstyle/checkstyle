parser grammar JavadocParser;

options { tokenVocab=JavadocLexer; }

@parser::members {
    boolean isNextJavadocTag() {
        int token1 = _input.LA(2);
        int token2 = _input.LA(3);
        return isJavadocTag(token1)
            || (token1 == WS && isJavadocTag(token2));
    }

    boolean isJavadocTag(int type) {
        switch(type) {
            case AUTHOR_LITERAL:
            case DEPRECATED_LITERAL:
            case EXCEPTION_LITERAL:
            case PARAM_LITERAL:
            case RETURN_LITERAL:
            case SEE_LITERAL:
            case SERIAL_LITERAL:
            case SERIAL_FIELD_LITERAL:
            case SERIAL_DATA_LITERAL:
            case SINCE_LITERAL:
            case THROWS_LITERAL:
            case VERSION_LITERAL:
            case CUSTOM_NAME:
                return true;
            default:
                return false;
        }
    }

      boolean isSameTagNames(ParserRuleContext htmlTagStart, ParserRuleContext htmlTagEnd) {
            String startTag = htmlTagStart.getToken(HTML_TAG_NAME, 0).getText().toLowerCase();
            String endTag = htmlTagEnd.getToken(HTML_TAG_NAME, 0).getText().toLowerCase();
            return startTag.equals(endTag);
      }
}

javadoc: (
            htmlElement
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag
         )*
         (LEADING_ASTERISK? WS* javadocTag)*
         EOF;

htmlElement: htmlTag
            | singletonElement
            | paragraph
            | li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot

            | pTagStart
            | liTagStart
            | trTagStart
            | tdTagStart
            | thTagStart
            | bodyTagStart
            | colgroupTagStart
            | ddTagStart
            | dtTagStart
            | headTagStart
            | htmlTagStart
            | optionTagStart
            | tbodyTagStart
            | theadTagStart
            | tfootTagStart

            | pTagEnd
            | liTagEnd
            | trTagEnd
            | tdTagEnd
            | thTagEnd
            | bodyTagEnd
            | colgroupTagEnd
            | ddTagEnd
            | dtTagEnd
            | headTagEnd
            | htmlTagEnd
            | optionTagEnd
            | tbodyTagEnd
            | theadTagEnd
            | tfootTagEnd
            ;

htmlElementStart:  START HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
htmlElementEnd: START SLASH HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
attribute:    HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)*
              EQUALS (NEWLINE | LEADING_ASTERISK | WS)*
              (ATTR_VALUE | text | HTML_TAG_NAME);

htmlTag: htmlElementStart (htmlElement
                              | ({!isNextJavadocTag()}? LEADING_ASTERISK)
                              | htmlComment
                              | CDATA
                              | NEWLINE
                              | text
                              | javadocInlineTag)* htmlElementEnd
                              {isSameTagNames($htmlElementStart.ctx, $htmlElementEnd.ctx)}?
            ;

//////////////////////////////////////////////////////////////////////////////////////
////////////////////  HTML TAGS WITH OPTIONAL END TAG ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
pTagStart: START P_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
pTagEnd: START SLASH P_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
paragraph: pTagStart
            (htmlTag
            | singletonElement
            | li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | liTagStart
            | trTagStart
            | tdTagStart
            | thTagStart
            | bodyTagStart
            | colgroupTagStart
            | ddTagStart
            | dtTagStart
            | headTagStart
            | htmlTagStart
            | optionTagStart
            | tbodyTagStart
            | theadTagStart
            | tfootTagStart
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
        pTagEnd
        ;

liTagStart: START LI_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
liTagEnd: START SLASH LI_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
li: liTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    liTagEnd
    ;

trTagStart: START TR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
trTagEnd: START SLASH TR_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tr: trTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    trTagEnd
    ;

tdTagStart: START TD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tdTagEnd: START SLASH TD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
td: tdTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    tdTagEnd
    ;

thTagStart: START TH_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
thTagEnd: START SLASH TH_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
th: thTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    thTagEnd
    ;

bodyTagStart: START BODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
bodyTagEnd: START SLASH BODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
body: bodyTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    bodyTagEnd
    ;

colgroupTagStart: START COLGROUP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
colgroupTagEnd: START SLASH COLGROUP_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
colgroup: colgroupTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    colgroupTagEnd
    ;

ddTagStart: START DD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
ddTagEnd: START SLASH DD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
dd: ddTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    ddTagEnd
    ;

dtTagStart: START DT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
dtTagEnd: START SLASH DT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
dt: dtTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | head
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    dtTagEnd
    ;

headTagStart: START HEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
headTagEnd: START SLASH HEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
head: headTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | html
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    headTagEnd
    ;

htmlTagStart: START HTML_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
htmlTagEnd: START SLASH HTML_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
html: htmlTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | option
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    htmlTagEnd
    ;

optionTagStart: START OPTION_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
optionTagEnd: START SLASH OPTION_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
option: optionTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | tbody
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | tbodyTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    optionTagEnd
    ;

tbodyTagStart: START TBODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tbodyTagEnd: START SLASH TBODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tbody: tbodyTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | thead
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | theadTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    tbodyTagEnd
    ;

tfootTagStart: START TFOOT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tfootTagEnd: START SLASH TFOOT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tfoot: tfootTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | thead
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | theadTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    tfootTagEnd
    ;

theadTagStart: START THEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
theadTagEnd: START SLASH THEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
thead: theadTagStart
    (htmlTag
        | singletonElement
        | paragraph
        | li
        | tr
        | td
        | th
        | body
        | colgroup
        | dd
        | dt
        | head
        | html
        | option
        | tbody
        | tfoot
        | pTagStart
        | liTagStart
        | trTagStart
        | tdTagStart
        | thTagStart
        | bodyTagStart
        | colgroupTagStart
        | ddTagStart
        | dtTagStart
        | headTagStart
        | htmlTagStart
        | optionTagStart
        | tbodyTagStart
        | tfootTagStart
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | CDATA
        | NEWLINE
        | text
        | javadocInlineTag)*
    theadTagEnd
    ;

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  SINLETON HTML TAGS  //////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
singletonElement: emptyTag
            | areaTag
            | baseTag
            | basefontTag
            | brTag
            | colTag
            | frameTag
            | hrTag
            | imgTag
            | inputTag
            | isindexTag
            | linkTag
            | metaTag
            | paramTag
            | embedTag
            | keygenTag
            | sourceTag
            | trackTag
            | wbrTag
            | wrongSinletonTag
            ;

emptyTag: START
                  (
                  HTML_TAG_NAME
                  | P_HTML_TAG_NAME
                  | LI_HTML_TAG_NAME
                  | TR_HTML_TAG_NAME
                  | TD_HTML_TAG_NAME
                  | TH_HTML_TAG_NAME
                  | BODY_HTML_TAG_NAME
                  | COLGROUP_HTML_TAG_NAME
                  | DD_HTML_TAG_NAME
                  | DT_HTML_TAG_NAME
                  | HEAD_HTML_TAG_NAME
                  | HTML_HTML_TAG_NAME
                  | OPTION_HTML_TAG_NAME
                  | TBODY_HTML_TAG_NAME
                  | TFOOT_HTML_TAG_NAME
                  | THEAD_HTML_TAG_NAME
                  )
                  (attribute | NEWLINE | LEADING_ASTERISK | WS)* SLASH_END;

areaTag: START AREA_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
baseTag: START BASE_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
basefontTag: START BASEFONT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
brTag: START BR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_END | END);
colTag: START COL_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_END | END);
frameTag: START FRAME_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
hrTag: START HR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_END | END);
imgTag: START IMG_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_END | END);
inputTag: START INPUT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
isindexTag: START ISINDEX_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
linkTag: START LINK_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
metaTag: START META_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
paramTag: START PARAM_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
embedTag: START EMBED_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
keygenTag: START KEYGEN_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
sourceTag: START SOURCE_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
trackTag: START TRACK_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);
wbrTag: START WBR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)*
         (SLASH_END | END);

wrongSinletonTag: START SLASH singletonTagName
                  END {notifyErrorListeners($singletonTagName.start,
                             "javadoc.wrong.singleton.html.tag", null);}
                  ;
singletonTagName: (AREA_HTML_TAG_NAME
                  | BASE_HTML_TAG_NAME
                  | BASEFONT_HTML_TAG_NAME
                  | BR_HTML_TAG_NAME
                  | COL_HTML_TAG_NAME
                  | FRAME_HTML_TAG_NAME
                  | HR_HTML_TAG_NAME
                  | IMG_HTML_TAG_NAME
                  | INPUT_HTML_TAG_NAME
                  | ISINDEX_HTML_TAG_NAME
                  | LINK_HTML_TAG_NAME
                  | META_HTML_TAG_NAME
                  | PARAM_HTML_TAG_NAME
                  | EMBED_HTML_TAG_NAME
                  | KEYGEN_HTML_TAG_NAME
                  | SOURCE_HTML_TAG_NAME
                  | TRACK_HTML_TAG_NAME
                  | WBR_HTML_TAG_NAME
                  )
                  ;


//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAGS  ////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
description: (
                  ({!isNextJavadocTag()}? LEADING_ASTERISK)
                  | htmlComment
                  | CDATA
                  | NEWLINE
                  | text
                  | javadocInlineTag
                  | htmlElement
            )+;

reference:
      (
            PACKAGE (DOT | CLASS)* HASH? MEMBER? parameters?
            | (DOT | CLASS)+ HASH? MEMBER? parameters?
            | HASH? MEMBER parameters?
      )
      ;
parameters: LEFT_BRACE (ARGUMENT | COMMA | WS | NEWLINE | LEADING_ASTERISK)* RIGHT_BRACE;

javadocTag: AUTHOR_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

    | DEPRECATED_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | EXCEPTION_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) CLASS_NAME)? (WS | NEWLINE)*
          ((WS | NEWLINE) description)?

      | PARAM_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) PARAMETER_NAME)? (WS | NEWLINE)*
          ((WS | NEWLINE) description)?

      | RETURN_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SEE_LITERAL (WS | NEWLINE)* reference? (STRING | htmlElement)* (WS | NEWLINE)*
          ((WS | NEWLINE) description)?

      | SERIAL_LITERAL (WS | NEWLINE)*
          ((WS | NEWLINE) description | LITERAL_INCLUDE | LITERAL_EXCLUDE)? (WS | NEWLINE)*

      | SERIAL_DATA_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SERIAL_FIELD_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) FIELD_NAME)? (WS | NEWLINE)*
          ((WS | NEWLINE) FIELD_TYPE)? (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SINCE_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | THROWS_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) CLASS_NAME)? (WS | NEWLINE)*
          ((WS | NEWLINE) description)?

      | VERSION_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | CUSTOM_NAME (WS | NEWLINE)* ((WS | NEWLINE) description)?
    ;
//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAGS  /////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
javadocInlineTag:
      JAVADOC_INLINE_TAG_START
      (
            CODE_LITERAL (WS | NEWLINE | LEADING_ASTERISK | text)*
            | DOC_ROOT_LITERAL (WS | NEWLINE | LEADING_ASTERISK)*
            | INHERIT_DOC_LITERAL (WS | NEWLINE | LEADING_ASTERISK)*
            | LINK_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* reference (WS | NEWLINE)*
                ((WS | NEWLINE) description)?
            | LINKPLAIN_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* reference (WS | NEWLINE)*
                ((WS | NEWLINE) description)?
            | LITERAL_LITERAL (WS | NEWLINE | LEADING_ASTERISK | text)*
            | VALUE_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* ((WS | NEWLINE) reference)?
            | CUSTOM_NAME (WS | NEWLINE | LEADING_ASTERISK)* (WS | NEWLINE)*
                ((WS | NEWLINE) description)?
      )
      JAVADOC_INLINE_TAG_END
      ;


htmlComment: HTML_COMMENT_START (text | NEWLINE | LEADING_ASTERISK)* HTML_COMMENT_END;

text : ((CHAR | WS)
 {
  _la = _input.LA(1);
  if ((_la != WS) && (_la != CHAR)) return _localctx;
  else if (_alt == 1) continue;
 }
       )+;
