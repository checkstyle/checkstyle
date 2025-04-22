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

    public ParserRuleContext nonTightTagStartContext;
}

javadoc: (
            htmlElement
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
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
            | optgroup
            | rb
            | rt
            | rtc
            | rp

            | pTagStart[true]
            | liTagStart[true]
            | trTagStart[true]
            | tdTagStart[true]
            | thTagStart[true]
            | bodyTagStart[true]
            | colgroupTagStart[true]
            | ddTagStart[true]
            | dtTagStart[true]
            | headTagStart[true]
            | htmlTagStart[true]
            | optionTagStart[true]
            | tbodyTagStart[true]
            | theadTagStart[true]
            | tfootTagStart[true]
            | optgroupTagStart[true]
            | rbTagStart[true]
            | rtTagStart[true]
            | rtcTagStart[true]
            | rpTagStart[true]

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
            | optgroupTagEnd
            | rbTagEnd
            | rtTagEnd
            | rtcTagEnd
            | rpTagEnd
            ;

htmlElementStart:  START HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
htmlElementEnd: START SLASH HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
attribute:    HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)*
              EQUALS (NEWLINE | LEADING_ASTERISK | WS)*
              (ATTR_VALUE | text | HTML_TAG_NAME);

htmlTag: htmlElementStart (htmlElement
                              | ({!isNextJavadocTag()}? LEADING_ASTERISK)
                              | htmlComment
                              | NEWLINE
                              | text
                              | javadocInlineTag)* htmlElementEnd
                              {isSameTagNames($htmlElementStart.ctx, $htmlElementEnd.ctx)}?
            ;

///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////  HTML TAGS WITH OPTIONAL END TAG ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
pTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START P_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
pTagEnd: START SLASH P_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
paragraph: pTagStart[false]
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
            | optgroup
            | rb
            | rt
            | rtc
            | rp

            | liTagStart[true]
            | trTagStart[true]
            | tdTagStart[true]
            | thTagStart[true]
            | bodyTagStart[true]
            | colgroupTagStart[true]
            | ddTagStart[true]
            | dtTagStart[true]
            | headTagStart[true]
            | htmlTagStart[true]
            | optionTagStart[true]
            | tbodyTagStart[true]
            | theadTagStart[true]
            | tfootTagStart[true]
            | optgroupTagStart[true]
            | rbTagStart[true]
            | rtTagStart[true]
            | rtcTagStart[true]
            | rpTagStart[true]
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | NEWLINE
            | text
            | javadocInlineTag)*
        pTagEnd
        ;

liTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START LI_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
liTagEnd: START SLASH LI_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
li: liTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    liTagEnd
    ;

trTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START TR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
trTagEnd: START SLASH TR_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tr: trTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    trTagEnd
    ;

tdTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START TD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tdTagEnd: START SLASH TD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
td: tdTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    tdTagEnd
    ;

thTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START TH_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
thTagEnd: START SLASH TH_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
th: thTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    thTagEnd
    ;

bodyTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START BODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
bodyTagEnd: START SLASH BODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
body: bodyTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    bodyTagEnd
    ;

colgroupTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START COLGROUP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
colgroupTagEnd: START SLASH COLGROUP_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
colgroup: colgroupTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    colgroupTagEnd
    ;

ddTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START DD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
ddTagEnd: START SLASH DD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
dd: ddTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    ddTagEnd
    ;

dtTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START DT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
dtTagEnd: START SLASH DT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
dt: dtTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    dtTagEnd
    ;

headTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START HEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
headTagEnd: START SLASH HEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
head: headTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    headTagEnd
    ;

htmlTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START HTML_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
htmlTagEnd: START SLASH HTML_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
html: htmlTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    htmlTagEnd
    ;

optionTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START OPTION_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
optionTagEnd: START SLASH OPTION_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
option: optionTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    optionTagEnd
    ;

tbodyTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START TBODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tbodyTagEnd: START SLASH TBODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tbody: tbodyTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | theadTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    tbodyTagEnd
    ;

tfootTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START TFOOT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
tfootTagEnd: START SLASH TFOOT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
tfoot: tfootTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | theadTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    tfootTagEnd
    ;

theadTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START THEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
theadTagEnd: START SLASH THEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
thead: theadTagStart[false]
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
        | optgroup
        | rb
        | rt
        | rtc
        | rp

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | optgroupTagStart[true]
        | rbTagStart[true]
        | rtTagStart[true]
        | rtcTagStart[true]
        | rpTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    theadTagEnd
    ;

///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  SINGLETON HTML TAGS  /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
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
            | wrongSingletonTag
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

wrongSingletonTag: START SLASH singletonTagName
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


///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAGS  ////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
description: (
                  ({!isNextJavadocTag()}? LEADING_ASTERISK)
                  | htmlComment
                  | NEWLINE
                  | text
                  | javadocInlineTag
                  | htmlElement
            )+;

reference:  PACKAGE_CLASS (HASH MEMBER parameters?)?
            | HASH MEMBER parameters?
    ;

parameters: LEFT_BRACE (ARGUMENT | COMMA | WS | NEWLINE | LEADING_ASTERISK)* RIGHT_BRACE;

javadocTag: AUTHOR_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

    | DEPRECATED_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | EXCEPTION_LITERAL (WS | NEWLINE | {!isNextJavadocTag()}? LEADING_ASTERISK)+ (CLASS_NAME)?
          (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | PARAM_LITERAL (WS | NEWLINE | {!isNextJavadocTag()}? LEADING_ASTERISK)+ (PARAMETER_NAME)?
          (WS | NEWLINE)* ((WS | NEWLINE) description)?


      | RETURN_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SEE_LITERAL (WS | NEWLINE | {!isNextJavadocTag()}? LEADING_ASTERISK)+
          (
            htmlElement (WS | NEWLINE)* description?
            | (reference | STRING)  (WS | NEWLINE)* ((WS | NEWLINE) description)?
          )

      | SERIAL_LITERAL (WS | NEWLINE)*
          ((WS | NEWLINE) description | LITERAL_INCLUDE | LITERAL_EXCLUDE)? (WS | NEWLINE)*

      | SERIAL_DATA_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SERIAL_FIELD_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) FIELD_NAME)? (WS | NEWLINE)*
          ((WS | NEWLINE) FIELD_TYPE)? (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | SINCE_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | THROWS_LITERAL (WS | NEWLINE | {!isNextJavadocTag()}? LEADING_ASTERISK)+ (CLASS_NAME)?
          (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | VERSION_LITERAL (WS | NEWLINE)* ((WS | NEWLINE) description)?

      | CUSTOM_NAME (WS | NEWLINE)* ((WS | NEWLINE) description)?
    ;
///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAGS  /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
javadocInlineTag:
      JAVADOC_INLINE_TAG_START
      (
            CODE_LITERAL (WS | NEWLINE | LEADING_ASTERISK | text)*
            | DOC_ROOT_LITERAL (WS | NEWLINE | LEADING_ASTERISK)*
            | INHERIT_DOC_LITERAL (WS | NEWLINE | LEADING_ASTERISK)*
            | LINK_LITERAL (WS | NEWLINE | LEADING_ASTERISK)+ reference
                    (WS | NEWLINE)* ((WS | NEWLINE) description)?
            | LINK_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* ((WS | NEWLINE) description)?
            | LINKPLAIN_LITERAL (WS | NEWLINE | LEADING_ASTERISK)+ reference
                    (WS | NEWLINE)* ((WS | NEWLINE) description)?
            | LINKPLAIN_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* ((WS | NEWLINE) description)?
            | LITERAL_LITERAL (WS | NEWLINE | LEADING_ASTERISK | text)*
            | VALUE_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* ((WS | NEWLINE) reference)?
            | CUSTOM_NAME (WS | NEWLINE | LEADING_ASTERISK)* ((WS | NEWLINE) description)?
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

optgroupTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START OPTGROUP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
optgroupTagEnd: START SLASH OPTGROUP_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
optgroup: optgroupTagStart[false]
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

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    optgroupTagEnd
    ;

rbTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START RB_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
rbTagEnd: START SLASH RB_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
rb: rbTagStart[false]
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

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    rbTagEnd
    ;

rtTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START RT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
rtTagEnd: START SLASH RT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
rt: rtTagStart[false]
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

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    rtTagEnd
    ;

rtcTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START RTC_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
rtcTagEnd: START SLASH RTC_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
rtc: rtcTagStart[false]
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

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    rtcTagEnd
    ;

rpTagStart[boolean isNonTight]
@after {
    if (isNonTight && nonTightTagStartContext == null) {
        nonTightTagStartContext = _localctx;
    }
}
    : START RP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* END;
rpTagEnd: START SLASH RP_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* END;
rp: rpTagStart[false]
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

        | pTagStart[true]
        | liTagStart[true]
        | trTagStart[true]
        | tdTagStart[true]
        | thTagStart[true]
        | bodyTagStart[true]
        | colgroupTagStart[true]
        | ddTagStart[true]
        | dtTagStart[true]
        | headTagStart[true]
        | htmlTagStart[true]
        | optionTagStart[true]
        | tbodyTagStart[true]
        | tfootTagStart[true]
        | ({!isNextJavadocTag()}? LEADING_ASTERISK)
        | htmlComment
        | NEWLINE
        | text
        | javadocInlineTag)*
    rpTagEnd
    ;
