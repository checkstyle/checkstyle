////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * GeneratedJavadocTokenTypesTest.
 * @noinspection ClassIndependentOfModule
 */
public class GeneratedJavadocTokenTypesTest {

    private static final String MSG = "Ensure that token numbers generated for the elements"
            + "present in JavadocParser are consistent with what the tests assert.";

    /**
     * This method checks that the numbers generated for tokens in <tt>JavadocLexer.g4</tt> don't
     * change with the lexer grammar itself.
     * <br>ANTLR maps all the lexer elements to compile time constants used internally by ANTLR.
     * Compatibility damage is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testTokenNumbers() {
        assertEquals(1, JavadocParser.LEADING_ASTERISK, MSG);
        assertEquals(2, JavadocParser.HTML_COMMENT_START, MSG);
        assertEquals(3, JavadocParser.CDATA, MSG);
        assertEquals(4, JavadocParser.WS, MSG);
        assertEquals(5, JavadocParser.START, MSG);
        assertEquals(6, JavadocParser.NEWLINE, MSG);
        assertEquals(7, JavadocParser.AUTHOR_LITERAL, MSG);
        assertEquals(8, JavadocParser.DEPRECATED_LITERAL, MSG);
        assertEquals(9, JavadocParser.EXCEPTION_LITERAL, MSG);
        assertEquals(10, JavadocParser.PARAM_LITERAL, MSG);
        assertEquals(11, JavadocParser.RETURN_LITERAL, MSG);
        assertEquals(12, JavadocParser.SEE_LITERAL, MSG);
        assertEquals(13, JavadocParser.SERIAL_LITERAL, MSG);
        assertEquals(14, JavadocParser.SERIAL_FIELD_LITERAL, MSG);
        assertEquals(15, JavadocParser.SERIAL_DATA_LITERAL, MSG);
        assertEquals(16, JavadocParser.SINCE_LITERAL, MSG);
        assertEquals(17, JavadocParser.THROWS_LITERAL, MSG);
        assertEquals(18, JavadocParser.VERSION_LITERAL, MSG);
        assertEquals(19, JavadocParser.JAVADOC_INLINE_TAG_START, MSG);
        assertEquals(20, JavadocParser.JAVADOC_INLINE_TAG_END, MSG);
        assertEquals(21, JavadocParser.CUSTOM_NAME, MSG);
        assertEquals(22, JavadocParser.LITERAL_INCLUDE, MSG);
        assertEquals(23, JavadocParser.LITERAL_EXCLUDE, MSG);
        assertEquals(24, JavadocParser.CHAR, MSG);
        assertEquals(25, JavadocParser.PARAMETER_NAME, MSG);
        assertEquals(26, JavadocParser.Char1, MSG);
        assertEquals(27, JavadocParser.STRING, MSG);
        assertEquals(28, JavadocParser.PACKAGE_CLASS, MSG);
        assertEquals(29, JavadocParser.DOT, MSG);
        assertEquals(30, JavadocParser.HASH, MSG);
        assertEquals(31, JavadocParser.CLASS, MSG);
        assertEquals(32, JavadocParser.Char2, MSG);
        assertEquals(33, JavadocParser.MEMBER, MSG);
        assertEquals(34, JavadocParser.LEFT_BRACE, MSG);
        assertEquals(35, JavadocParser.RIGHT_BRACE, MSG);
        assertEquals(36, JavadocParser.ARGUMENT, MSG);
        assertEquals(37, JavadocParser.COMMA, MSG);
        assertEquals(38, JavadocParser.Char20, MSG);
        assertEquals(39, JavadocParser.FIELD_NAME, MSG);
        assertEquals(40, JavadocParser.Char3, MSG);
        assertEquals(41, JavadocParser.FIELD_TYPE, MSG);
        assertEquals(42, JavadocParser.Char4, MSG);
        assertEquals(43, JavadocParser.CLASS_NAME, MSG);
        assertEquals(44, JavadocParser.Char5, MSG);
        assertEquals(45, JavadocParser.CODE_LITERAL, MSG);
        assertEquals(46, JavadocParser.DOC_ROOT_LITERAL, MSG);
        assertEquals(47, JavadocParser.INHERIT_DOC_LITERAL, MSG);
        assertEquals(48, JavadocParser.LINK_LITERAL, MSG);
        assertEquals(49, JavadocParser.LINKPLAIN_LITERAL, MSG);
        assertEquals(50, JavadocParser.LITERAL_LITERAL, MSG);
        assertEquals(51, JavadocParser.VALUE_LITERAL, MSG);
        assertEquals(52, JavadocParser.Char7, MSG);
        assertEquals(53, JavadocParser.Char8, MSG);
        assertEquals(54, JavadocParser.Char10, MSG);
        assertEquals(55, JavadocParser.END, MSG);
        assertEquals(56, JavadocParser.SLASH_END, MSG);
        assertEquals(57, JavadocParser.SLASH, MSG);
        assertEquals(58, JavadocParser.EQUALS, MSG);
        assertEquals(59, JavadocParser.P_HTML_TAG_NAME, MSG);
        assertEquals(60, JavadocParser.LI_HTML_TAG_NAME, MSG);
        assertEquals(61, JavadocParser.TR_HTML_TAG_NAME, MSG);
        assertEquals(62, JavadocParser.TD_HTML_TAG_NAME, MSG);
        assertEquals(63, JavadocParser.TH_HTML_TAG_NAME, MSG);
        assertEquals(64, JavadocParser.BODY_HTML_TAG_NAME, MSG);
        assertEquals(65, JavadocParser.COLGROUP_HTML_TAG_NAME, MSG);
        assertEquals(66, JavadocParser.DD_HTML_TAG_NAME, MSG);
        assertEquals(67, JavadocParser.DT_HTML_TAG_NAME, MSG);
        assertEquals(68, JavadocParser.HEAD_HTML_TAG_NAME, MSG);
        assertEquals(69, JavadocParser.HTML_HTML_TAG_NAME, MSG);
        assertEquals(70, JavadocParser.OPTION_HTML_TAG_NAME, MSG);
        assertEquals(71, JavadocParser.TBODY_HTML_TAG_NAME, MSG);
        assertEquals(72, JavadocParser.TFOOT_HTML_TAG_NAME, MSG);
        assertEquals(73, JavadocParser.THEAD_HTML_TAG_NAME, MSG);
        assertEquals(74, JavadocParser.AREA_HTML_TAG_NAME, MSG);
        assertEquals(75, JavadocParser.BASE_HTML_TAG_NAME, MSG);
        assertEquals(76, JavadocParser.BASEFONT_HTML_TAG_NAME, MSG);
        assertEquals(77, JavadocParser.BR_HTML_TAG_NAME, MSG);
        assertEquals(78, JavadocParser.COL_HTML_TAG_NAME, MSG);
        assertEquals(79, JavadocParser.FRAME_HTML_TAG_NAME, MSG);
        assertEquals(80, JavadocParser.HR_HTML_TAG_NAME, MSG);
        assertEquals(81, JavadocParser.IMG_HTML_TAG_NAME, MSG);
        assertEquals(82, JavadocParser.INPUT_HTML_TAG_NAME, MSG);
        assertEquals(83, JavadocParser.ISINDEX_HTML_TAG_NAME, MSG);
        assertEquals(84, JavadocParser.LINK_HTML_TAG_NAME, MSG);
        assertEquals(85, JavadocParser.META_HTML_TAG_NAME, MSG);
        assertEquals(86, JavadocParser.PARAM_HTML_TAG_NAME, MSG);
        assertEquals(87, JavadocParser.EMBED_HTML_TAG_NAME, MSG);
        assertEquals(88, JavadocParser.KEYGEN_HTML_TAG_NAME, MSG);
        assertEquals(92, JavadocParser.SOURCE_HTML_TAG_NAME, MSG);
        assertEquals(93, JavadocParser.TRACK_HTML_TAG_NAME, MSG);
        assertEquals(94, JavadocParser.WBR_HTML_TAG_NAME, MSG);
        assertEquals(95, JavadocParser.OPTGROUP_HTML_TAG_NAME, MSG);
        assertEquals(96, JavadocParser.RB_HTML_TAG_NAME, MSG);
        assertEquals(97, JavadocParser.RT_HTML_TAG_NAME, MSG);
        assertEquals(98, JavadocParser.RTC_HTML_TAG_NAME, MSG);
        assertEquals(99, JavadocParser.RP_HTML_TAG_NAME, MSG);
        assertEquals(100, JavadocParser.HTML_TAG_NAME, MSG);
        assertEquals(101, JavadocParser.Char11, MSG);
        assertEquals(89, JavadocParser.ATTR_VALUE, MSG);
        assertEquals(90, JavadocParser.Char12, MSG);
        assertEquals(91, JavadocParser.HTML_COMMENT_END, MSG);
    }

    /**
     * This method checks that the numbers generated for rules in <tt>JavadocParser.g4</tt> don't
     * change with the Parser grammar itself.
     * <br>ANTLR maps all the parser rules to compile time constants used internally by ANTLR.
     * Compatibility damage is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testRuleNumbers() {
        assertEquals(0, JavadocParser.RULE_javadoc, MSG);
        assertEquals(1, JavadocParser.RULE_htmlElement, MSG);
        assertEquals(2, JavadocParser.RULE_htmlElementStart, MSG);
        assertEquals(3, JavadocParser.RULE_htmlElementEnd, MSG);
        assertEquals(4, JavadocParser.RULE_attribute, MSG);
        assertEquals(5, JavadocParser.RULE_htmlTag, MSG);
        assertEquals(6, JavadocParser.RULE_pTagStart, MSG);
        assertEquals(7, JavadocParser.RULE_pTagEnd, MSG);
        assertEquals(8, JavadocParser.RULE_paragraph, MSG);
        assertEquals(9, JavadocParser.RULE_liTagStart, MSG);
        assertEquals(10, JavadocParser.RULE_liTagEnd, MSG);
        assertEquals(11, JavadocParser.RULE_li, MSG);
        assertEquals(12, JavadocParser.RULE_trTagStart, MSG);
        assertEquals(13, JavadocParser.RULE_trTagEnd, MSG);
        assertEquals(14, JavadocParser.RULE_tr, MSG);
        assertEquals(15, JavadocParser.RULE_tdTagStart, MSG);
        assertEquals(16, JavadocParser.RULE_tdTagEnd, MSG);
        assertEquals(17, JavadocParser.RULE_td, MSG);
        assertEquals(18, JavadocParser.RULE_thTagStart, MSG);
        assertEquals(19, JavadocParser.RULE_thTagEnd, MSG);
        assertEquals(20, JavadocParser.RULE_th, MSG);
        assertEquals(21, JavadocParser.RULE_bodyTagStart, MSG);
        assertEquals(22, JavadocParser.RULE_bodyTagEnd, MSG);
        assertEquals(23, JavadocParser.RULE_body, MSG);
        assertEquals(24, JavadocParser.RULE_colgroupTagStart, MSG);
        assertEquals(25, JavadocParser.RULE_colgroupTagEnd, MSG);
        assertEquals(26, JavadocParser.RULE_colgroup, MSG);
        assertEquals(27, JavadocParser.RULE_ddTagStart, MSG);
        assertEquals(28, JavadocParser.RULE_ddTagEnd, MSG);
        assertEquals(29, JavadocParser.RULE_dd, MSG);
        assertEquals(30, JavadocParser.RULE_dtTagStart, MSG);
        assertEquals(31, JavadocParser.RULE_dtTagEnd, MSG);
        assertEquals(32, JavadocParser.RULE_dt, MSG);
        assertEquals(33, JavadocParser.RULE_headTagStart, MSG);
        assertEquals(34, JavadocParser.RULE_headTagEnd, MSG);
        assertEquals(35, JavadocParser.RULE_head, MSG);
        assertEquals(36, JavadocParser.RULE_htmlTagStart, MSG);
        assertEquals(37, JavadocParser.RULE_htmlTagEnd, MSG);
        assertEquals(38, JavadocParser.RULE_html, MSG);
        assertEquals(39, JavadocParser.RULE_optionTagStart, MSG);
        assertEquals(40, JavadocParser.RULE_optionTagEnd, MSG);
        assertEquals(41, JavadocParser.RULE_option, MSG);
        assertEquals(42, JavadocParser.RULE_tbodyTagStart, MSG);
        assertEquals(43, JavadocParser.RULE_tbodyTagEnd, MSG);
        assertEquals(44, JavadocParser.RULE_tbody, MSG);
        assertEquals(45, JavadocParser.RULE_tfootTagStart, MSG);
        assertEquals(46, JavadocParser.RULE_tfootTagEnd, MSG);
        assertEquals(47, JavadocParser.RULE_tfoot, MSG);
        assertEquals(48, JavadocParser.RULE_theadTagStart, MSG);
        assertEquals(49, JavadocParser.RULE_theadTagEnd, MSG);
        assertEquals(50, JavadocParser.RULE_thead, MSG);
        assertEquals(51, JavadocParser.RULE_singletonElement, MSG);
        assertEquals(52, JavadocParser.RULE_emptyTag, MSG);
        assertEquals(53, JavadocParser.RULE_areaTag, MSG);
        assertEquals(54, JavadocParser.RULE_baseTag, MSG);
        assertEquals(55, JavadocParser.RULE_basefontTag, MSG);
        assertEquals(56, JavadocParser.RULE_brTag, MSG);
        assertEquals(57, JavadocParser.RULE_colTag, MSG);
        assertEquals(58, JavadocParser.RULE_frameTag, MSG);
        assertEquals(59, JavadocParser.RULE_hrTag, MSG);
        assertEquals(60, JavadocParser.RULE_imgTag, MSG);
        assertEquals(61, JavadocParser.RULE_inputTag, MSG);
        assertEquals(62, JavadocParser.RULE_isindexTag, MSG);
        assertEquals(63, JavadocParser.RULE_linkTag, MSG);
        assertEquals(64, JavadocParser.RULE_metaTag, MSG);
        assertEquals(65, JavadocParser.RULE_paramTag, MSG);
        assertEquals(66, JavadocParser.RULE_wrongSingletonTag, MSG);
        assertEquals(67, JavadocParser.RULE_singletonTagName, MSG);
        assertEquals(68, JavadocParser.RULE_description, MSG);
        assertEquals(69, JavadocParser.RULE_reference, MSG);
        assertEquals(70, JavadocParser.RULE_parameters, MSG);
        assertEquals(71, JavadocParser.RULE_javadocTag, MSG);
        assertEquals(72, JavadocParser.RULE_javadocInlineTag, MSG);
        assertEquals(73, JavadocParser.RULE_htmlComment, MSG);
        assertEquals(74, JavadocParser.RULE_text, MSG);
        assertEquals(75, JavadocParser.RULE_embedTag, MSG);
        assertEquals(76, JavadocParser.RULE_keygenTag, MSG);
        assertEquals(77, JavadocParser.RULE_sourceTag, MSG);
        assertEquals(78, JavadocParser.RULE_trackTag, MSG);
        assertEquals(79, JavadocParser.RULE_wbrTag, MSG);
        assertEquals(80, JavadocParser.RULE_optgroupTagStart, MSG);
        assertEquals(81, JavadocParser.RULE_optgroupTagEnd, MSG);
        assertEquals(82, JavadocParser.RULE_optgroup, MSG);
        assertEquals(83, JavadocParser.RULE_rbTagStart, MSG);
        assertEquals(84, JavadocParser.RULE_rbTagEnd, MSG);
        assertEquals(85, JavadocParser.RULE_rb, MSG);
        assertEquals(86, JavadocParser.RULE_rtTagStart, MSG);
        assertEquals(87, JavadocParser.RULE_rtTagEnd, MSG);
        assertEquals(88, JavadocParser.RULE_rt, MSG);
        assertEquals(89, JavadocParser.RULE_rtcTagStart, MSG);
        assertEquals(90, JavadocParser.RULE_rtcTagEnd, MSG);
        assertEquals(91, JavadocParser.RULE_rtc, MSG);
        assertEquals(92, JavadocParser.RULE_rpTagStart, MSG);
        assertEquals(93, JavadocParser.RULE_rpTagEnd, MSG);
        assertEquals(94, JavadocParser.RULE_rp, MSG);
    }

}
