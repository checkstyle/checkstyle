////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

public final class JavadocTokenTypes
{
    private static final int RULE_TYPES_OFFSET = 10000;

    // root node
    public static final int JAVADOC = JavadocParser.RULE_javadoc + RULE_TYPES_OFFSET;

    //--------------------------------------------------------------------------------------------//
    //------------------        JAVADOC TAGS          --------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int JAVADOC_TAG = JavadocParser.RULE_javadocTag + RULE_TYPES_OFFSET;
    public static final int JAVADOC_INLINE_TAG = JavadocParser.RULE_javadocInlineTag + RULE_TYPES_OFFSET;

    public static final int RETURN_LITERAL = JavadocParser.RETURN_LITERAL;
    public static final int DEPRECATED_LITERAL = JavadocParser.DEPRECATED_LITERAL;
    public static final int SINCE_LITERAL = JavadocParser.SINCE_LITERAL;
    public static final int SERIAL_DATA_LITERAL = JavadocParser.SERIAL_DATA_LITERAL;
    public static final int SERIAL_FIELD_LITERAL = JavadocParser.SERIAL_FIELD_LITERAL;
    public static final int PARAM_LITERAL = JavadocParser.PARAM_LITERAL;
    public static final int SEE_LITERAL = JavadocParser.SEE_LITERAL;
    public static final int SERIAL_LITERAL = JavadocParser.SERIAL_LITERAL;
    public static final int VERSION_LITERAL = JavadocParser.VERSION_LITERAL;
    public static final int CUSTOM_NAME = JavadocParser.CUSTOM_NAME;
    public static final int EXCEPTION_LITERAL = JavadocParser.EXCEPTION_LITERAL;
    public static final int THROWS_LITERAL = JavadocParser.THROWS_LITERAL;
    public static final int AUTHOR_LITERAL = JavadocParser.AUTHOR_LITERAL;

    public static final int JAVADOC_INLINE_TAG_START = JavadocParser.JAVADOC_INLINE_TAG_START; // '{'
    public static final int JAVADOC_INLINE_TAG_END = JavadocParser.JAVADOC_INLINE_TAG_END; // '}'

    public static final int CODE_LITERAL = JavadocParser.CODE_LITERAL;
    public static final int DOC_ROOT_LITERAL = JavadocParser.DOC_ROOT_LITERAL;
    public static final int LINK_LITERAL = JavadocParser.LINK_LITERAL;
    public static final int INHERIT_DOC_LITERAL = JavadocParser.INHERIT_DOC_LITERAL;
    public static final int LINKPLAIN_LITERAL = JavadocParser.LINKPLAIN_LITERAL;
    public static final int LITERAL_LITERAL = JavadocParser.LITERAL_LITERAL; // @literal
    public static final int VALUE_LITERAL = JavadocParser.VALUE_LITERAL;

    // @see and {@link} argument
    public static final int REFERENCE = JavadocParser.RULE_reference + RULE_TYPES_OFFSET;

    // @see and {@link} reference components
    public static final int PACKAGE = JavadocParser.PACKAGE;
    public static final int CLASS = JavadocParser.CLASS;
    public static final int DOT = JavadocParser.DOT;
    public static final int HASH = JavadocParser.HASH; // #
    public static final int MEMBER = JavadocParser.MEMBER;
    public static final int PARAMETERS = JavadocParser.RULE_parameters + RULE_TYPES_OFFSET; // parent node for LEFT_BRACE, RIGHT_BRACE, ARGUMENT, COMMA nodes
    public static final int LEFT_BRACE = JavadocParser.LEFT_BRACE;
    public static final int RIGHT_BRACE = JavadocParser.RIGHT_BRACE;
    public static final int ARGUMENT = JavadocParser.ARGUMENT;
    public static final int COMMA = JavadocParser.COMMA;

    // @see and {@link} argument
    public static final int STRING = JavadocParser.STRING; // "text"

    // description argument for many Javadoc tags
    public static final int DESCRIPTION = JavadocParser.RULE_description + RULE_TYPES_OFFSET;

    // First argument of @exception: @exception class-name description
    public static final int CLASS_NAME = JavadocParser.CLASS_NAME;

    // First argument of @param: @param parameter-name description
    public static final int PARAMETER_NAME = JavadocParser.PARAMETER_NAME;

    // @serial and @serialField arguments
    public static final int LITERAL_EXCLUDE = JavadocParser.LITERAL_EXCLUDE;
    public static final int LITERAL_INCLUDE = JavadocParser.LITERAL_INCLUDE;
    public static final int FIELD_NAME = JavadocParser.FIELD_NAME;
    public static final int FIELD_TYPE = JavadocParser.FIELD_TYPE;


    //--------------------------------------------------------------------------------------------//
    //------------------        HTML TAGS          -----------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int HTML_ELEMENT = JavadocParser.RULE_htmlElement + RULE_TYPES_OFFSET; // parent node for all html tags
    public static final int HTML_ELEMENT_OPEN = JavadocParser.RULE_htmlElementOpen + RULE_TYPES_OFFSET + RULE_TYPES_OFFSET; // <XXX>
    public static final int HTML_ELEMENT_CLOSE = JavadocParser.RULE_htmlElementClose + RULE_TYPES_OFFSET; // </XXX>

    public static final int HTML_TAG = JavadocParser.RULE_htmlTag + RULE_TYPES_OFFSET; // non-special HTML tag
    public static final int HTML_TAG_NAME = JavadocParser.HTML_TAG_NAME; // identifier inside HTML tag: tag name or attribute name
    public static final int ATTRIBUTE = JavadocParser.RULE_attribute + RULE_TYPES_OFFSET + RULE_TYPES_OFFSET; // html tag attribute. Parent node for: HTML_TAG_IDENT, EQUALS, ATTR_VALUE

    // HTML tag components
    public static final int OPEN = JavadocParser.OPEN; // '<'
    public static final int SLASH = JavadocParser.SLASH; // '/'
    public static final int CLOSE = JavadocParser.CLOSE; // '>'
    public static final int SLASH_CLOSE = JavadocParser.SLASH_CLOSE; // '/>'
    public static final int EQUALS = JavadocParser.EQUALS; // '='
    public static final int ATTR_VALUE = JavadocParser.ATTR_VALUE; // attribute value

    /////////////////////// HTML TAGS WITH OPTIONAL CLOSE TAG /////////////////////////////////////
    public static final int PARAGRAPH = JavadocParser.RULE_paragraph + RULE_TYPES_OFFSET;
    public static final int P_TAG_OPEN = JavadocParser.RULE_pTagOpen + RULE_TYPES_OFFSET;
    public static final int P_TAG_CLOSE = JavadocParser.RULE_pTagClose + RULE_TYPES_OFFSET;
    public static final int P_HTML_TAG_NAME = JavadocParser.P_HTML_TAG_NAME;

    public static final int LI = JavadocParser.RULE_li + RULE_TYPES_OFFSET;
    public static final int LI_TAG_OPEN = JavadocParser.RULE_liTagOpen + RULE_TYPES_OFFSET;
    public static final int LI_TAG_CLOSE = JavadocParser.RULE_liTagClose + RULE_TYPES_OFFSET;
    public static final int LI_HTML_TAG_NAME = JavadocParser.LI_HTML_TAG_NAME;

    public static final int TR = JavadocParser.RULE_tr + RULE_TYPES_OFFSET;
    public static final int TR_TAG_OPEN = JavadocParser.RULE_trTagOpen + RULE_TYPES_OFFSET;
    public static final int TR_TAG_CLOSE = JavadocParser.RULE_trTagClose + RULE_TYPES_OFFSET;
    public static final int TR_HTML_TAG_NAME = JavadocParser.TR_HTML_TAG_NAME;

    public static final int TD = JavadocParser.RULE_td + RULE_TYPES_OFFSET;
    public static final int TD_TAG_OPEN = JavadocParser.RULE_tdTagOpen + RULE_TYPES_OFFSET;
    public static final int TD_TAG_CLOSE = JavadocParser.RULE_tdTagClose + RULE_TYPES_OFFSET;
    public static final int TD_HTML_TAG_NAME = JavadocParser.TD_HTML_TAG_NAME;

    public static final int TH = JavadocParser.RULE_th + RULE_TYPES_OFFSET;
    public static final int TH_TAG_OPEN = JavadocParser.RULE_thTagOpen + RULE_TYPES_OFFSET;
    public static final int TH_TAG_CLOSE = JavadocParser.RULE_thTagClose + RULE_TYPES_OFFSET;
    public static final int TH_HTML_TAG_NAME = JavadocParser.TH_HTML_TAG_NAME;

    public static final int BODY = JavadocParser.RULE_body + RULE_TYPES_OFFSET;
    public static final int BODY_TAG_OPEN = JavadocParser.RULE_bodyTagOpen + RULE_TYPES_OFFSET;
    public static final int BODY_TAG_CLOSE = JavadocParser.RULE_bodyTagClose + RULE_TYPES_OFFSET;
    public static final int BODY_HTML_TAG_NAME = JavadocParser.BODY_HTML_TAG_NAME;

    public static final int COLGROUP = JavadocParser.RULE_colgroup + RULE_TYPES_OFFSET;
    public static final int COLGROUP_TAG_OPEN = JavadocParser.RULE_colgroupTagOpen + RULE_TYPES_OFFSET;
    public static final int COLGROUP_TAG_CLOSE = JavadocParser.RULE_colgroupTagClose + RULE_TYPES_OFFSET;
    public static final int COLGROUP_HTML_TAG_NAME = JavadocParser.COLGROUP_HTML_TAG_NAME;

    public static final int DD = JavadocParser.RULE_dd + RULE_TYPES_OFFSET;
    public static final int DD_TAG_OPEN = JavadocParser.RULE_ddTagOpen + RULE_TYPES_OFFSET;
    public static final int DD_TAG_CLOSE = JavadocParser.RULE_ddTagClose + RULE_TYPES_OFFSET;
    public static final int DD_HTML_TAG_NAME = JavadocParser.DD_HTML_TAG_NAME;

    public static final int DT = JavadocParser.RULE_dt + RULE_TYPES_OFFSET;
    public static final int DT_TAG_OPEN = JavadocParser.RULE_dtTagOpen + RULE_TYPES_OFFSET;
    public static final int DT_TAG_CLOSE = JavadocParser.RULE_dtTagClose + RULE_TYPES_OFFSET;
    public static final int DT_HTML_TAG_NAME = JavadocParser.DT_HTML_TAG_NAME;

    public static final int HEAD = JavadocParser.RULE_head + RULE_TYPES_OFFSET;
    public static final int HEAD_TAG_OPEN = JavadocParser.RULE_headTagOpen + RULE_TYPES_OFFSET;
    public static final int HEAD_TAG_CLOSE = JavadocParser.RULE_headTagClose + RULE_TYPES_OFFSET;
    public static final int HEAD_HTML_TAG_NAME = JavadocParser.HEAD_HTML_TAG_NAME;

    public static final int HTML = JavadocParser.RULE_html + RULE_TYPES_OFFSET;
    public static final int HTML_TAG_OPEN = JavadocParser.RULE_htmlTagOpen + RULE_TYPES_OFFSET;
    public static final int HTML_TAG_CLOSE = JavadocParser.RULE_htmlTagClose + RULE_TYPES_OFFSET;
    public static final int HTML_HTML_TAG_NAME = JavadocParser.HTML_HTML_TAG_NAME;

    public static final int OPTION = JavadocParser.RULE_option + RULE_TYPES_OFFSET;
    public static final int OPTION_TAG_OPEN = JavadocParser.RULE_optionTagOpen + RULE_TYPES_OFFSET;
    public static final int OPTION_TAG_CLOSE = JavadocParser.RULE_optionTagClose + RULE_TYPES_OFFSET;
    public static final int OPTION_HTML_TAG_NAME = JavadocParser.OPTION_HTML_TAG_NAME;

    public static final int TBODY = JavadocParser.RULE_tbody + RULE_TYPES_OFFSET;
    public static final int TBODY_TAG_OPEN = JavadocParser.RULE_tbodyTagOpen + RULE_TYPES_OFFSET;
    public static final int TBODY_TAG_CLOSE = JavadocParser.RULE_tbodyTagClose + RULE_TYPES_OFFSET;
    public static final int TBODY_HTML_TAG_NAME = JavadocParser.TBODY_HTML_TAG_NAME;

    public static final int TFOOT = JavadocParser.RULE_tfoot + RULE_TYPES_OFFSET;
    public static final int TFOOT_TAG_OPEN = JavadocParser.RULE_tfootTagOpen + RULE_TYPES_OFFSET;
    public static final int TFOOT_TAG_CLOSE = JavadocParser.RULE_tfootTagClose + RULE_TYPES_OFFSET;
    public static final int TFOOT_HTML_TAG_NAME = JavadocParser.TFOOT_HTML_TAG_NAME;

    public static final int THEAD = JavadocParser.RULE_thead + RULE_TYPES_OFFSET;
    public static final int THEAD_TAG_OPEN = JavadocParser.RULE_theadTagOpen + RULE_TYPES_OFFSET;
    public static final int THEAD_TAG_CLOSE = JavadocParser.RULE_theadTagClose + RULE_TYPES_OFFSET;
    public static final int THEAD_HTML_TAG_NAME = JavadocParser.THEAD_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////// SINGLETON HTML TAGS  //////////////////////////////////////////////////
    public static final int SINGLETON_ELEMENT = JavadocParser.RULE_singletonElement + RULE_TYPES_OFFSET; // parent node for all singleton tags

    public static final int SINGLETON_TAG = JavadocParser.RULE_singletonTag + RULE_TYPES_OFFSET; // non-special singleton tag

    public static final int AREA_TAG = JavadocParser.RULE_areaTag + RULE_TYPES_OFFSET;
    public static final int AREA_HTML_TAG_NAME = JavadocParser.AREA_HTML_TAG_NAME;

    public static final int BASE_TAG = JavadocParser.RULE_baseTag + RULE_TYPES_OFFSET;
    public static final int BASE_HTML_TAG_NAME = JavadocParser.BASE_HTML_TAG_NAME;

    public static final int BASEFRONT_TAG = JavadocParser.RULE_basefrontTag + RULE_TYPES_OFFSET;
    public static final int BASEFRONT_HTML_TAG_NAME = JavadocParser.BASEFRONT_HTML_TAG_NAME;

    public static final int BR_TAG = JavadocParser.RULE_brTag + RULE_TYPES_OFFSET;
    public static final int BR_HTML_TAG_NAME = JavadocParser.BR_HTML_TAG_NAME;

    public static final int COL_TAG = JavadocParser.RULE_colTag + RULE_TYPES_OFFSET;
    public static final int COL_HTML_TAG_NAME = JavadocParser.COL_HTML_TAG_NAME;

    public static final int FRAME_TAG = JavadocParser.RULE_frameTag + RULE_TYPES_OFFSET;
    public static final int FRAME_HTML_TAG_NAME = JavadocParser.FRAME_HTML_TAG_NAME;

    public static final int HR_TAG = JavadocParser.RULE_hrTag + RULE_TYPES_OFFSET;
    public static final int HR_HTML_TAG_NAME = JavadocParser.HR_HTML_TAG_NAME;

    public static final int IMG_TAG = JavadocParser.RULE_imgTag + RULE_TYPES_OFFSET;
    public static final int IMG_HTML_TAG_NAME = JavadocParser.IMG_HTML_TAG_NAME;

    public static final int INPUT_TAG = JavadocParser.RULE_inputTag + RULE_TYPES_OFFSET;
    public static final int INPUT_HTML_TAG_NAME = JavadocParser.INPUT_HTML_TAG_NAME;

    public static final int ISINDEX_TAG = JavadocParser.RULE_isindexTag + RULE_TYPES_OFFSET;
    public static final int ISINDEX_HTML_TAG_NAME = JavadocParser.ISINDEX_HTML_TAG_NAME;

    public static final int LINK_TAG = JavadocParser.RULE_linkTag + RULE_TYPES_OFFSET;
    public static final int LINK_HTML_TAG_NAME = JavadocParser.LINK_HTML_TAG_NAME;

    public static final int META_TAG = JavadocParser.RULE_metaTag + RULE_TYPES_OFFSET;
    public static final int META_HTML_TAG_NAME = JavadocParser.META_HTML_TAG_NAME;

    public static final int PARAM_TAG = JavadocParser.RULE_paramTag + RULE_TYPES_OFFSET;
    public static final int PARAM_HTML_TAG_NAME = JavadocParser.PARAM_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // HTML comments
    public static final int HTML_COMMENT = JavadocParser.RULE_htmlComment + RULE_TYPES_OFFSET + RULE_TYPES_OFFSET;
    public static final int HTML_COMMENT_START = JavadocParser.HTML_COMMENT_START; // <!---
    public static final int HTML_COMMENT_END = JavadocParser.HTML_COMMENT_END; // -->

    public static final int CDATA = JavadocParser.CDATA; // '<![CDATA[...]]>'

    //--------------------------------------------------------------------------------------------//
    //------------------        OTHER          ---------------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int LEADING_ASTERISK = JavadocParser.LEADING_ASTERISK;
    public static final int NEWLINE = JavadocParser.NEWLINE; // \n
    public static final int CHAR = JavadocParser.CHAR; // any symbol
    public static final int WS = JavadocParser.WS; // whitespace, \t
    public static final int TEXT = JavadocParser.RULE_text + RULE_TYPES_OFFSET; // CHAR and WS sequence

    public static final int EOF = JavadocParser.EOF; // end of file



    private JavadocTokenTypes()
    {
    }

}
