////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars.javadoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** @noinspection ClassIndependentOfModule */
public class GeneratedJavadocTokenTypesTest {

    private static final String MSG = "Ensure that token numbers generated for the elements"
            + "present in JavadocParser are consistent with what the tests assert.";

    /**
     * This method checks that the numbers generated for tokens in <tt>JavadocLexer.g4</tt> don't
     * change with the lexer grammar itself.
     * <br>ANTLR maps all the lexer elements to compile time constants used internally by ANTLR.
     * Compatability damange is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testTokenNumbers() {
        assertEquals(MSG, 1, JavadocParser.LEADING_ASTERISK);
        assertEquals(MSG, 2, JavadocParser.HTML_COMMENT_START);
        assertEquals(MSG, 3, JavadocParser.CDATA);
        assertEquals(MSG, 4, JavadocParser.WS);
        assertEquals(MSG, 5, JavadocParser.START);
        assertEquals(MSG, 6, JavadocParser.NEWLINE);
        assertEquals(MSG, 7, JavadocParser.AUTHOR_LITERAL);
        assertEquals(MSG, 8, JavadocParser.DEPRECATED_LITERAL);
        assertEquals(MSG, 9, JavadocParser.EXCEPTION_LITERAL);
        assertEquals(MSG, 10, JavadocParser.PARAM_LITERAL);
        assertEquals(MSG, 11, JavadocParser.RETURN_LITERAL);
        assertEquals(MSG, 12, JavadocParser.SEE_LITERAL);
        assertEquals(MSG, 13, JavadocParser.SERIAL_LITERAL);
        assertEquals(MSG, 14, JavadocParser.SERIAL_FIELD_LITERAL);
        assertEquals(MSG, 15, JavadocParser.SERIAL_DATA_LITERAL);
        assertEquals(MSG, 16, JavadocParser.SINCE_LITERAL);
        assertEquals(MSG, 17, JavadocParser.THROWS_LITERAL);
        assertEquals(MSG, 18, JavadocParser.VERSION_LITERAL);
        assertEquals(MSG, 19, JavadocParser.JAVADOC_INLINE_TAG_START);
        assertEquals(MSG, 20, JavadocParser.JAVADOC_INLINE_TAG_END);
        assertEquals(MSG, 21, JavadocParser.CUSTOM_NAME);
        assertEquals(MSG, 22, JavadocParser.LITERAL_INCLUDE);
        assertEquals(MSG, 23, JavadocParser.LITERAL_EXCLUDE);
        assertEquals(MSG, 24, JavadocParser.CHAR);
        assertEquals(MSG, 25, JavadocParser.PARAMETER_NAME);
        assertEquals(MSG, 26, JavadocParser.Char1);
        assertEquals(MSG, 27, JavadocParser.STRING);
        assertEquals(MSG, 28, JavadocParser.PACKAGE_CLASS);
        assertEquals(MSG, 29, JavadocParser.HASH);
        assertEquals(MSG, 30, JavadocParser.Char2);
        assertEquals(MSG, 31, JavadocParser.MEMBER);
        assertEquals(MSG, 32, JavadocParser.LEFT_BRACE);
        assertEquals(MSG, 33, JavadocParser.RIGHT_BRACE);
        assertEquals(MSG, 34, JavadocParser.ARGUMENT);
        assertEquals(MSG, 35, JavadocParser.COMMA);
        assertEquals(MSG, 36, JavadocParser.Char20);
        assertEquals(MSG, 37, JavadocParser.FIELD_NAME);
        assertEquals(MSG, 38, JavadocParser.Char3);
        assertEquals(MSG, 39, JavadocParser.FIELD_TYPE);
        assertEquals(MSG, 40, JavadocParser.Char4);
        assertEquals(MSG, 41, JavadocParser.CLASS_NAME);
        assertEquals(MSG, 42, JavadocParser.Char5);
        assertEquals(MSG, 43, JavadocParser.CODE_LITERAL);
        assertEquals(MSG, 44, JavadocParser.DOC_ROOT_LITERAL);
        assertEquals(MSG, 45, JavadocParser.INHERIT_DOC_LITERAL);
        assertEquals(MSG, 46, JavadocParser.LINK_LITERAL);
        assertEquals(MSG, 47, JavadocParser.LINKPLAIN_LITERAL);
        assertEquals(MSG, 48, JavadocParser.LITERAL_LITERAL);
        assertEquals(MSG, 49, JavadocParser.VALUE_LITERAL);
        assertEquals(MSG, 50, JavadocParser.Char7);
        assertEquals(MSG, 51, JavadocParser.Char8);
        assertEquals(MSG, 52, JavadocParser.Char10);
        assertEquals(MSG, 53, JavadocParser.END);
        assertEquals(MSG, 54, JavadocParser.SLASH_END);
        assertEquals(MSG, 55, JavadocParser.SLASH);
        assertEquals(MSG, 56, JavadocParser.EQUALS);
        assertEquals(MSG, 57, JavadocParser.P_HTML_TAG_NAME);
        assertEquals(MSG, 58, JavadocParser.LI_HTML_TAG_NAME);
        assertEquals(MSG, 59, JavadocParser.TR_HTML_TAG_NAME);
        assertEquals(MSG, 60, JavadocParser.TD_HTML_TAG_NAME);
        assertEquals(MSG, 61, JavadocParser.TH_HTML_TAG_NAME);
        assertEquals(MSG, 62, JavadocParser.BODY_HTML_TAG_NAME);
        assertEquals(MSG, 63, JavadocParser.COLGROUP_HTML_TAG_NAME);
        assertEquals(MSG, 64, JavadocParser.DD_HTML_TAG_NAME);
        assertEquals(MSG, 65, JavadocParser.DT_HTML_TAG_NAME);
        assertEquals(MSG, 66, JavadocParser.HEAD_HTML_TAG_NAME);
        assertEquals(MSG, 67, JavadocParser.HTML_HTML_TAG_NAME);
        assertEquals(MSG, 68, JavadocParser.OPTION_HTML_TAG_NAME);
        assertEquals(MSG, 69, JavadocParser.TBODY_HTML_TAG_NAME);
        assertEquals(MSG, 70, JavadocParser.TFOOT_HTML_TAG_NAME);
        assertEquals(MSG, 71, JavadocParser.THEAD_HTML_TAG_NAME);
        assertEquals(MSG, 72, JavadocParser.AREA_HTML_TAG_NAME);
        assertEquals(MSG, 73, JavadocParser.BASE_HTML_TAG_NAME);
        assertEquals(MSG, 74, JavadocParser.BASEFONT_HTML_TAG_NAME);
        assertEquals(MSG, 75, JavadocParser.BR_HTML_TAG_NAME);
        assertEquals(MSG, 76, JavadocParser.COL_HTML_TAG_NAME);
        assertEquals(MSG, 77, JavadocParser.FRAME_HTML_TAG_NAME);
        assertEquals(MSG, 78, JavadocParser.HR_HTML_TAG_NAME);
        assertEquals(MSG, 79, JavadocParser.IMG_HTML_TAG_NAME);
        assertEquals(MSG, 80, JavadocParser.INPUT_HTML_TAG_NAME);
        assertEquals(MSG, 81, JavadocParser.ISINDEX_HTML_TAG_NAME);
        assertEquals(MSG, 82, JavadocParser.LINK_HTML_TAG_NAME);
        assertEquals(MSG, 83, JavadocParser.META_HTML_TAG_NAME);
        assertEquals(MSG, 84, JavadocParser.PARAM_HTML_TAG_NAME);
        assertEquals(MSG, 85, JavadocParser.EMBED_HTML_TAG_NAME);
        assertEquals(MSG, 86, JavadocParser.KEYGEN_HTML_TAG_NAME);
        assertEquals(MSG, 87, JavadocParser.SOURCE_HTML_TAG_NAME);
        assertEquals(MSG, 88, JavadocParser.TRACK_HTML_TAG_NAME);
        assertEquals(MSG, 89, JavadocParser.WBR_HTML_TAG_NAME);
        assertEquals(MSG, 90, JavadocParser.HTML_TAG_NAME);
        assertEquals(MSG, 91, JavadocParser.Char11);
        assertEquals(MSG, 92, JavadocParser.ATTR_VALUE);
        assertEquals(MSG, 93, JavadocParser.Char12);
        assertEquals(MSG, 94, JavadocParser.HTML_COMMENT_END);
    }

    /**
     * This method checks that the numbers generated for rules in <tt>JavadocParser.g4</tt> don't
     * change with the Parser grammar itself.
     * <br>ANTLR maps all the parser rules to compile time constants used internally by ANTLR.
     * Compatability damange is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testRuleNumbers() {
        assertEquals(MSG, 0, JavadocParser.RULE_javadoc);
        assertEquals(MSG, 1, JavadocParser.RULE_htmlElement);
        assertEquals(MSG, 2, JavadocParser.RULE_htmlElementStart);
        assertEquals(MSG, 3, JavadocParser.RULE_htmlElementEnd);
        assertEquals(MSG, 4, JavadocParser.RULE_attribute);
        assertEquals(MSG, 5, JavadocParser.RULE_htmlTag);
        assertEquals(MSG, 6, JavadocParser.RULE_pTagStart);
        assertEquals(MSG, 7, JavadocParser.RULE_pTagEnd);
        assertEquals(MSG, 8, JavadocParser.RULE_paragraph);
        assertEquals(MSG, 9, JavadocParser.RULE_liTagStart);
        assertEquals(MSG, 10, JavadocParser.RULE_liTagEnd);
        assertEquals(MSG, 11, JavadocParser.RULE_li);
        assertEquals(MSG, 12, JavadocParser.RULE_trTagStart);
        assertEquals(MSG, 13, JavadocParser.RULE_trTagEnd);
        assertEquals(MSG, 14, JavadocParser.RULE_tr);
        assertEquals(MSG, 15, JavadocParser.RULE_tdTagStart);
        assertEquals(MSG, 16, JavadocParser.RULE_tdTagEnd);
        assertEquals(MSG, 17, JavadocParser.RULE_td);
        assertEquals(MSG, 18, JavadocParser.RULE_thTagStart);
        assertEquals(MSG, 19, JavadocParser.RULE_thTagEnd);
        assertEquals(MSG, 20, JavadocParser.RULE_th);
        assertEquals(MSG, 21, JavadocParser.RULE_bodyTagStart);
        assertEquals(MSG, 22, JavadocParser.RULE_bodyTagEnd);
        assertEquals(MSG, 23, JavadocParser.RULE_body);
        assertEquals(MSG, 24, JavadocParser.RULE_colgroupTagStart);
        assertEquals(MSG, 25, JavadocParser.RULE_colgroupTagEnd);
        assertEquals(MSG, 26, JavadocParser.RULE_colgroup);
        assertEquals(MSG, 27, JavadocParser.RULE_ddTagStart);
        assertEquals(MSG, 28, JavadocParser.RULE_ddTagEnd);
        assertEquals(MSG, 29, JavadocParser.RULE_dd);
        assertEquals(MSG, 30, JavadocParser.RULE_dtTagStart);
        assertEquals(MSG, 31, JavadocParser.RULE_dtTagEnd);
        assertEquals(MSG, 32, JavadocParser.RULE_dt);
        assertEquals(MSG, 33, JavadocParser.RULE_headTagStart);
        assertEquals(MSG, 34, JavadocParser.RULE_headTagEnd);
        assertEquals(MSG, 35, JavadocParser.RULE_head);
        assertEquals(MSG, 36, JavadocParser.RULE_htmlTagStart);
        assertEquals(MSG, 37, JavadocParser.RULE_htmlTagEnd);
        assertEquals(MSG, 38, JavadocParser.RULE_html);
        assertEquals(MSG, 39, JavadocParser.RULE_optionTagStart);
        assertEquals(MSG, 40, JavadocParser.RULE_optionTagEnd);
        assertEquals(MSG, 41, JavadocParser.RULE_option);
        assertEquals(MSG, 42, JavadocParser.RULE_tbodyTagStart);
        assertEquals(MSG, 43, JavadocParser.RULE_tbodyTagEnd);
        assertEquals(MSG, 44, JavadocParser.RULE_tbody);
        assertEquals(MSG, 45, JavadocParser.RULE_tfootTagStart);
        assertEquals(MSG, 46, JavadocParser.RULE_tfootTagEnd);
        assertEquals(MSG, 47, JavadocParser.RULE_tfoot);
        assertEquals(MSG, 48, JavadocParser.RULE_theadTagStart);
        assertEquals(MSG, 49, JavadocParser.RULE_theadTagEnd);
        assertEquals(MSG, 50, JavadocParser.RULE_thead);
        assertEquals(MSG, 51, JavadocParser.RULE_singletonElement);
        assertEquals(MSG, 52, JavadocParser.RULE_emptyTag);
        assertEquals(MSG, 53, JavadocParser.RULE_areaTag);
        assertEquals(MSG, 54, JavadocParser.RULE_baseTag);
        assertEquals(MSG, 55, JavadocParser.RULE_basefontTag);
        assertEquals(MSG, 56, JavadocParser.RULE_brTag);
        assertEquals(MSG, 57, JavadocParser.RULE_colTag);
        assertEquals(MSG, 58, JavadocParser.RULE_frameTag);
        assertEquals(MSG, 59, JavadocParser.RULE_hrTag);
        assertEquals(MSG, 60, JavadocParser.RULE_imgTag);
        assertEquals(MSG, 61, JavadocParser.RULE_inputTag);
        assertEquals(MSG, 62, JavadocParser.RULE_isindexTag);
        assertEquals(MSG, 63, JavadocParser.RULE_linkTag);
        assertEquals(MSG, 64, JavadocParser.RULE_metaTag);
        assertEquals(MSG, 65, JavadocParser.RULE_paramTag);
        assertEquals(MSG, 66, JavadocParser.RULE_embedTag);
        assertEquals(MSG, 67, JavadocParser.RULE_keygenTag);
        assertEquals(MSG, 68, JavadocParser.RULE_sourceTag);
        assertEquals(MSG, 69, JavadocParser.RULE_trackTag);
        assertEquals(MSG, 70, JavadocParser.RULE_wbrTag);
        assertEquals(MSG, 71, JavadocParser.RULE_wrongSinletonTag);
        assertEquals(MSG, 72, JavadocParser.RULE_singletonTagName);
        assertEquals(MSG, 73, JavadocParser.RULE_description);
        assertEquals(MSG, 74, JavadocParser.RULE_reference);
        assertEquals(MSG, 75, JavadocParser.RULE_parameters);
        assertEquals(MSG, 76, JavadocParser.RULE_javadocTag);
        assertEquals(MSG, 77, JavadocParser.RULE_javadocInlineTag);
        assertEquals(MSG, 78, JavadocParser.RULE_htmlComment);
        assertEquals(MSG, 79, JavadocParser.RULE_text);
    }
}
