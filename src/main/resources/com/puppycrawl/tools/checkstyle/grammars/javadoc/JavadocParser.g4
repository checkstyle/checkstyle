parser grammar JavadocParser;

options { tokenVocab=JavadocLexer; }

@parser::header {
package com.puppycrawl.tools.checkstyle.grammars.javadoc;
}

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

      boolean isSameTagNames(ParserRuleContext htmlTagOpen, ParserRuleContext htmlTagClose) {
            String openTag = htmlTagOpen.getToken(HTML_TAG_NAME, 0).getText().toLowerCase();
            String closeTag = htmlTagClose.getToken(HTML_TAG_NAME, 0).getText().toLowerCase();
            System.out.println(openTag + " - " + closeTag);
            return openTag.equals(closeTag);
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

            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen

            | pTagClose
            | liTagClose
            | trTagClose
            | tdTagClose
            | thTagClose
            | bodyTagClose
            | colgroupTagClose
            | ddTagClose
            | dtTagClose
            | headTagClose
            | htmlTagClose
            | optionTagClose
            | tbodyTagClose
            | theadTagClose
            | tfootTagClose
            ;

htmlElementOpen:  OPEN HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
htmlElementClose: OPEN SLASH HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
attribute:    HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* EQUALS (NEWLINE | LEADING_ASTERISK | WS)* (ATTR_VALUE | text | HTML_TAG_NAME);

htmlTag: htmlElementOpen (htmlElement
                              | ({!isNextJavadocTag()}? LEADING_ASTERISK)
                              | htmlComment
                              | CDATA
                              | NEWLINE
                              | text
                              | javadocInlineTag)* htmlElementClose //{isSameTagNames($htmlElementOpen.ctx, $htmlElementClose.ctx)}?

            | htmlElementOpen (htmlElement
                              | ({!isNextJavadocTag()}? LEADING_ASTERISK)
                              | htmlComment
                              | CDATA
                              | NEWLINE
                              | text
                              | javadocInlineTag)*
            {notifyErrorListeners($htmlElementOpen.ctx.getToken(HTML_TAG_NAME, 0).getSymbol(), "javadoc.missed.html.close", null);}
            ;

//////////////////////////////////////////////////////////////////////////////////////
////////////////////  HTML TAGS WITH OPTIONAL END TAG ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
pTagOpen: OPEN P_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
pTagClose: OPEN SLASH P_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
paragraph: pTagOpen
		(htmlTag
		| singletonTag
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
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
		pTagClose
		;

liTagOpen: OPEN LI_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
liTagClose: OPEN SLASH LI_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
li: liTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
		| ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	liTagClose
	;

trTagOpen: OPEN TR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
trTagClose: OPEN SLASH TR_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tr: trTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	trTagClose
	;

tdTagOpen: OPEN TD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tdTagClose: OPEN SLASH TD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
td: tdTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	tdTagClose
	;

thTagOpen: OPEN TH_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
thTagClose: OPEN SLASH TH_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
th: thTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	thTagClose
	;

bodyTagOpen: OPEN BODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
bodyTagClose: OPEN SLASH BODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
body: bodyTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	bodyTagClose
	;

colgroupTagOpen: OPEN COLGROUP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
colgroupTagClose: OPEN SLASH COLGROUP_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
colgroup: colgroupTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	colgroupTagClose
	;

ddTagOpen: OPEN DD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
ddTagClose: OPEN SLASH DD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
dd: ddTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	ddTagClose
	;

dtTagOpen: OPEN DT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
dtTagClose: OPEN SLASH DT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
dt: dtTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	dtTagClose
	;

headTagOpen: OPEN HEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
headTagClose: OPEN SLASH HEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
head: headTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	headTagClose
	;

htmlTagOpen: OPEN HTML_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
htmlTagClose: OPEN SLASH HTML_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
html: htmlTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	htmlTagClose
	;

optionTagOpen: OPEN OPTION_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
optionTagClose: OPEN SLASH OPTION_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
option: optionTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	optionTagClose
	;

tbodyTagOpen: OPEN TBODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tbodyTagClose: OPEN SLASH TBODY_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tbody: tbodyTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | theadTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	tbodyTagClose
	;

tfootTagOpen: OPEN TFOOT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tfootTagClose: OPEN SLASH TFOOT_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
tfoot: tfootTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	tfootTagClose
	;

theadTagOpen: OPEN THEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
theadTagClose: OPEN SLASH THEAD_HTML_TAG_NAME (NEWLINE | LEADING_ASTERISK | WS)* CLOSE;
thead: theadTagOpen
	(htmlTag
		| singletonTag
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
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | tfootTagOpen
            | ({!isNextJavadocTag()}? LEADING_ASTERISK)
            | htmlComment
            | CDATA
            | NEWLINE
            | text
            | javadocInlineTag)*
	theadTagClose
	;

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  SINLETON HTML TAGS  //////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
singletonElement: singletonTag
			| areaTag
			| baseTag
			| basefrontTag
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

                  | wrongSinletonTag
			;

singletonTag: OPEN
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
                  (attribute | NEWLINE | LEADING_ASTERISK | WS)* SLASH_CLOSE;

areaTag: OPEN AREA_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
baseTag: OPEN BASE_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
basefrontTag: OPEN BASEFRONT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
brTag: OPEN BR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
colTag: OPEN COL_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
frameTag: OPEN FRAME_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
hrTag: OPEN HR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
imgTag: OPEN IMG_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
inputTag: OPEN INPUT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
isindexTag: OPEN ISINDEX_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
linkTag: OPEN LINK_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
metaTag: OPEN META_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);
paramTag: OPEN PARAM_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK | WS)* (SLASH_CLOSE | CLOSE);

wrongSinletonTag: OPEN SLASH singletonTagName CLOSE {notifyErrorListeners($singletonTagName.start, "javadoc.wrong.singleton.html.tag", null);}
                  ;
singletonTagName: (AREA_HTML_TAG_NAME
                  | BASE_HTML_TAG_NAME
                  | BASEFRONT_HTML_TAG_NAME
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

javadocTag: AUTHOR_LITERAL (WS | NEWLINE)* description?

	| DEPRECATED_LITERAL (WS | NEWLINE)* description?

  	| EXCEPTION_LITERAL (WS | NEWLINE)* CLASS_NAME? (WS | NEWLINE)* description?

  	| PARAM_LITERAL (WS | NEWLINE)* PARAMETER_NAME? (WS | NEWLINE)* description?

  	| RETURN_LITERAL (WS | NEWLINE)* description?

  	| SEE_LITERAL (WS | NEWLINE)* reference? (STRING | htmlElement)* (WS | NEWLINE)* description?

  	| SERIAL_LITERAL (WS | NEWLINE)* (LITERAL_INCLUDE | LITERAL_EXCLUDE)? description?

  	| SERIAL_DATA_LITERAL (WS | NEWLINE)* description?

  	| SERIAL_FIELD_LITERAL (WS | NEWLINE)* FIELD_NAME? (WS | NEWLINE)* FIELD_TYPE? (WS | NEWLINE)* description?

  	| SINCE_LITERAL (WS | NEWLINE)* description?

  	| THROWS_LITERAL (WS | NEWLINE)* CLASS_NAME? (WS | NEWLINE)* description?

  	| VERSION_LITERAL (WS | NEWLINE)* description?

  	| CUSTOM_NAME (WS | NEWLINE)* description?
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
            | LINK_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* reference description?
            | LINKPLAIN_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* reference description?
            | LITERAL_LITERAL (WS | NEWLINE | LEADING_ASTERISK | text)*
            | VALUE_LITERAL (WS | NEWLINE | LEADING_ASTERISK)* reference?
            | CUSTOM_NAME (WS | NEWLINE | LEADING_ASTERISK)+ description?
      )
      JAVADOC_INLINE_TAG_END
      ;


htmlComment: HTML_COMMENT_START (text | NEWLINE | LEADING_ASTERISK)* HTML_COMMENT_END;

text : (CHAR | WS)+ ;
