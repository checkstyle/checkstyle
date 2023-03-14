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

package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * GeneratedJavadocTokenTypesTest.
 *
 * @noinspection ClassIndependentOfModule
 * @noinspectionreason ClassIndependentOfModule - architecture of test modules
 *      requires this structure
 */
public class GeneratedJavadocTokenTypesTest {

    private static final String MSG = "Ensure that token numbers generated for the elements"
            + "present in JavadocParser are consistent with what the tests assert.";

    /**
     * This method checks that the numbers generated for tokens in <tt>JavadocLexer.g4</tt> don't
     * change with the lexer grammar itself.
     * <br>ANTLR maps all the lexer elements to compile-time constants used internally by ANTLR.
     * Compatibility damage is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile-time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testTokenNumbers() {
        assertWithMessage(MSG)
            .that(JavadocParser.LEADING_ASTERISK)
            .isEqualTo(1);
        assertWithMessage(MSG)
            .that(JavadocParser.HTML_COMMENT_START)
            .isEqualTo(2);
        assertWithMessage(MSG)
            .that(JavadocParser.DEPRECATED_CDATA_DO_NOT_USE)
            .isEqualTo(3);
        assertWithMessage(MSG)
            .that(JavadocParser.WS)
            .isEqualTo(4);
        assertWithMessage(MSG)
            .that(JavadocParser.START)
            .isEqualTo(5);
        assertWithMessage(MSG)
            .that(JavadocParser.NEWLINE)
            .isEqualTo(6);
        assertWithMessage(MSG)
            .that(JavadocParser.AUTHOR_LITERAL)
            .isEqualTo(7);
        assertWithMessage(MSG)
            .that(JavadocParser.DEPRECATED_LITERAL)
            .isEqualTo(8);
        assertWithMessage(MSG)
            .that(JavadocParser.EXCEPTION_LITERAL)
            .isEqualTo(9);
        assertWithMessage(MSG)
            .that(JavadocParser.PARAM_LITERAL)
            .isEqualTo(10);
        assertWithMessage(MSG)
            .that(JavadocParser.RETURN_LITERAL)
            .isEqualTo(11);
        assertWithMessage(MSG)
            .that(JavadocParser.SEE_LITERAL)
            .isEqualTo(12);
        assertWithMessage(MSG)
            .that(JavadocParser.SERIAL_LITERAL)
            .isEqualTo(13);
        assertWithMessage(MSG)
            .that(JavadocParser.SERIAL_FIELD_LITERAL)
            .isEqualTo(14);
        assertWithMessage(MSG)
            .that(JavadocParser.SERIAL_DATA_LITERAL)
            .isEqualTo(15);
        assertWithMessage(MSG)
            .that(JavadocParser.SINCE_LITERAL)
            .isEqualTo(16);
        assertWithMessage(MSG)
            .that(JavadocParser.THROWS_LITERAL)
            .isEqualTo(17);
        assertWithMessage(MSG)
            .that(JavadocParser.VERSION_LITERAL)
            .isEqualTo(18);
        assertWithMessage(MSG)
            .that(JavadocParser.JAVADOC_INLINE_TAG_START)
            .isEqualTo(19);
        assertWithMessage(MSG)
            .that(JavadocParser.JAVADOC_INLINE_TAG_END)
            .isEqualTo(20);
        assertWithMessage(MSG)
            .that(JavadocParser.CUSTOM_NAME)
            .isEqualTo(21);
        assertWithMessage(MSG)
            .that(JavadocParser.LITERAL_INCLUDE)
            .isEqualTo(22);
        assertWithMessage(MSG)
            .that(JavadocParser.LITERAL_EXCLUDE)
            .isEqualTo(23);
        assertWithMessage(MSG)
            .that(JavadocParser.CHAR)
            .isEqualTo(24);
        assertWithMessage(MSG)
            .that(JavadocParser.PARAMETER_NAME)
            .isEqualTo(25);
        assertWithMessage(MSG)
            .that(JavadocParser.Char1)
            .isEqualTo(26);
        assertWithMessage(MSG)
            .that(JavadocParser.STRING)
            .isEqualTo(27);
        assertWithMessage(MSG)
            .that(JavadocParser.PACKAGE_CLASS)
            .isEqualTo(28);
        assertWithMessage(MSG)
            .that(JavadocParser.DOT)
            .isEqualTo(29);
        assertWithMessage(MSG)
            .that(JavadocParser.HASH)
            .isEqualTo(30);
        assertWithMessage(MSG)
            .that(JavadocParser.CLASS)
            .isEqualTo(31);
        assertWithMessage(MSG)
            .that(JavadocParser.Char2)
            .isEqualTo(32);
        assertWithMessage(MSG)
            .that(JavadocParser.MEMBER)
            .isEqualTo(33);
        assertWithMessage(MSG)
            .that(JavadocParser.LEFT_BRACE)
            .isEqualTo(34);
        assertWithMessage(MSG)
            .that(JavadocParser.RIGHT_BRACE)
            .isEqualTo(35);
        assertWithMessage(MSG)
            .that(JavadocParser.ARGUMENT)
            .isEqualTo(36);
        assertWithMessage(MSG)
            .that(JavadocParser.COMMA)
            .isEqualTo(37);
        assertWithMessage(MSG)
            .that(JavadocParser.Char20)
            .isEqualTo(38);
        assertWithMessage(MSG)
            .that(JavadocParser.FIELD_NAME)
            .isEqualTo(39);
        assertWithMessage(MSG)
            .that(JavadocParser.Char3)
            .isEqualTo(40);
        assertWithMessage(MSG)
            .that(JavadocParser.FIELD_TYPE)
            .isEqualTo(41);
        assertWithMessage(MSG)
            .that(JavadocParser.Char4)
            .isEqualTo(42);
        assertWithMessage(MSG)
            .that(JavadocParser.CLASS_NAME)
            .isEqualTo(43);
        assertWithMessage(MSG)
            .that(JavadocParser.Char5)
            .isEqualTo(44);
        assertWithMessage(MSG)
            .that(JavadocParser.CODE_LITERAL)
            .isEqualTo(45);
        assertWithMessage(MSG)
            .that(JavadocParser.DOC_ROOT_LITERAL)
            .isEqualTo(46);
        assertWithMessage(MSG)
            .that(JavadocParser.INHERIT_DOC_LITERAL)
            .isEqualTo(47);
        assertWithMessage(MSG)
            .that(JavadocParser.LINK_LITERAL)
            .isEqualTo(48);
        assertWithMessage(MSG)
            .that(JavadocParser.LINKPLAIN_LITERAL)
            .isEqualTo(49);
        assertWithMessage(MSG)
            .that(JavadocParser.LITERAL_LITERAL)
            .isEqualTo(50);
        assertWithMessage(MSG)
            .that(JavadocParser.VALUE_LITERAL)
            .isEqualTo(51);
        assertWithMessage(MSG)
            .that(JavadocParser.Char7)
            .isEqualTo(52);
        assertWithMessage(MSG)
            .that(JavadocParser.Char8)
            .isEqualTo(53);
        assertWithMessage(MSG)
            .that(JavadocParser.Char10)
            .isEqualTo(54);
        assertWithMessage(MSG)
            .that(JavadocParser.END)
            .isEqualTo(55);
        assertWithMessage(MSG)
            .that(JavadocParser.SLASH_END)
            .isEqualTo(56);
        assertWithMessage(MSG)
            .that(JavadocParser.SLASH)
            .isEqualTo(57);
        assertWithMessage(MSG)
            .that(JavadocParser.EQUALS)
            .isEqualTo(58);
        assertWithMessage(MSG)
            .that(JavadocParser.P_HTML_TAG_NAME)
            .isEqualTo(59);
        assertWithMessage(MSG)
            .that(JavadocParser.LI_HTML_TAG_NAME)
            .isEqualTo(60);
        assertWithMessage(MSG)
            .that(JavadocParser.TR_HTML_TAG_NAME)
            .isEqualTo(61);
        assertWithMessage(MSG)
            .that(JavadocParser.TD_HTML_TAG_NAME)
            .isEqualTo(62);
        assertWithMessage(MSG)
            .that(JavadocParser.TH_HTML_TAG_NAME)
            .isEqualTo(63);
        assertWithMessage(MSG)
            .that(JavadocParser.BODY_HTML_TAG_NAME)
            .isEqualTo(64);
        assertWithMessage(MSG)
            .that(JavadocParser.COLGROUP_HTML_TAG_NAME)
            .isEqualTo(65);
        assertWithMessage(MSG)
            .that(JavadocParser.DD_HTML_TAG_NAME)
            .isEqualTo(66);
        assertWithMessage(MSG)
            .that(JavadocParser.DT_HTML_TAG_NAME)
            .isEqualTo(67);
        assertWithMessage(MSG)
            .that(JavadocParser.HEAD_HTML_TAG_NAME)
            .isEqualTo(68);
        assertWithMessage(MSG)
            .that(JavadocParser.HTML_HTML_TAG_NAME)
            .isEqualTo(69);
        assertWithMessage(MSG)
            .that(JavadocParser.OPTION_HTML_TAG_NAME)
            .isEqualTo(70);
        assertWithMessage(MSG)
            .that(JavadocParser.TBODY_HTML_TAG_NAME)
            .isEqualTo(71);
        assertWithMessage(MSG)
            .that(JavadocParser.TFOOT_HTML_TAG_NAME)
            .isEqualTo(72);
        assertWithMessage(MSG)
            .that(JavadocParser.THEAD_HTML_TAG_NAME)
            .isEqualTo(73);
        assertWithMessage(MSG)
            .that(JavadocParser.AREA_HTML_TAG_NAME)
            .isEqualTo(74);
        assertWithMessage(MSG)
            .that(JavadocParser.BASE_HTML_TAG_NAME)
            .isEqualTo(75);
        assertWithMessage(MSG)
            .that(JavadocParser.BASEFONT_HTML_TAG_NAME)
            .isEqualTo(76);
        assertWithMessage(MSG)
            .that(JavadocParser.BR_HTML_TAG_NAME)
            .isEqualTo(77);
        assertWithMessage(MSG)
            .that(JavadocParser.COL_HTML_TAG_NAME)
            .isEqualTo(78);
        assertWithMessage(MSG)
            .that(JavadocParser.FRAME_HTML_TAG_NAME)
            .isEqualTo(79);
        assertWithMessage(MSG)
            .that(JavadocParser.HR_HTML_TAG_NAME)
            .isEqualTo(80);
        assertWithMessage(MSG)
            .that(JavadocParser.IMG_HTML_TAG_NAME)
            .isEqualTo(81);
        assertWithMessage(MSG)
            .that(JavadocParser.INPUT_HTML_TAG_NAME)
            .isEqualTo(82);
        assertWithMessage(MSG)
            .that(JavadocParser.ISINDEX_HTML_TAG_NAME)
            .isEqualTo(83);
        assertWithMessage(MSG)
            .that(JavadocParser.LINK_HTML_TAG_NAME)
            .isEqualTo(84);
        assertWithMessage(MSG)
            .that(JavadocParser.META_HTML_TAG_NAME)
            .isEqualTo(85);
        assertWithMessage(MSG)
            .that(JavadocParser.PARAM_HTML_TAG_NAME)
            .isEqualTo(86);
        assertWithMessage(MSG)
            .that(JavadocParser.EMBED_HTML_TAG_NAME)
            .isEqualTo(87);
        assertWithMessage(MSG)
            .that(JavadocParser.KEYGEN_HTML_TAG_NAME)
            .isEqualTo(88);
        assertWithMessage(MSG)
            .that(JavadocParser.ATTR_VALUE)
            .isEqualTo(89);
        assertWithMessage(MSG)
            .that(JavadocParser.Char12)
            .isEqualTo(90);
        assertWithMessage(MSG)
            .that(JavadocParser.HTML_COMMENT_END)
            .isEqualTo(91);
        assertWithMessage(MSG)
            .that(JavadocParser.SOURCE_HTML_TAG_NAME)
            .isEqualTo(92);
        assertWithMessage(MSG)
            .that(JavadocParser.TRACK_HTML_TAG_NAME)
            .isEqualTo(93);
        assertWithMessage(MSG)
            .that(JavadocParser.WBR_HTML_TAG_NAME)
            .isEqualTo(94);
        assertWithMessage(MSG)
            .that(JavadocParser.OPTGROUP_HTML_TAG_NAME)
            .isEqualTo(95);
        assertWithMessage(MSG)
            .that(JavadocParser.RB_HTML_TAG_NAME)
            .isEqualTo(96);
        assertWithMessage(MSG)
            .that(JavadocParser.RT_HTML_TAG_NAME)
            .isEqualTo(97);
        assertWithMessage(MSG)
            .that(JavadocParser.RTC_HTML_TAG_NAME)
            .isEqualTo(98);
        assertWithMessage(MSG)
            .that(JavadocParser.RP_HTML_TAG_NAME)
            .isEqualTo(99);
        assertWithMessage(MSG)
            .that(JavadocParser.HTML_TAG_NAME)
            .isEqualTo(100);
        assertWithMessage(MSG)
            .that(JavadocParser.Char11)
            .isEqualTo(101);

        final int tokenCount = (int) Arrays.stream(JavadocParser.class.getDeclaredFields())
                .filter(GeneratedJavadocTokenTypesTest::isPublicStaticFinalInt)
                .filter(field -> !isRule(field))
                .count();

        // Read JavaDoc before changing count below
        assertWithMessage("all tokens must be added to list in"
                        + " 'GeneratedJavadocTokenTypesTest' and verified"
                        + " that their old numbering didn't change")
            .that(tokenCount)
            .isEqualTo(101);
    }

    /**
     * This method checks that the numbers generated for rules in <tt>JavadocParser.g4</tt> don't
     * change with the Parser grammar itself.
     * <br>ANTLR maps all the parser rules to compile-time constants used internally by ANTLR.
     * Compatibility damage is incurred <i>(with respect to the previous checkstyle versions)
     * </i> if these compile-time constants keep changing with the grammar.
     *
     * @see "https://github.com/checkstyle/checkstyle/issues/5139"
     * @see "https://github.com/checkstyle/checkstyle/issues/5186"
     */
    @Test
    public void testRuleNumbers() {
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_javadoc)
            .isEqualTo(0);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlElement)
            .isEqualTo(1);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlElementStart)
            .isEqualTo(2);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlElementEnd)
            .isEqualTo(3);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_attribute)
            .isEqualTo(4);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlTag)
            .isEqualTo(5);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_pTagStart)
            .isEqualTo(6);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_pTagEnd)
            .isEqualTo(7);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_paragraph)
            .isEqualTo(8);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_liTagStart)
            .isEqualTo(9);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_liTagEnd)
            .isEqualTo(10);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_li)
            .isEqualTo(11);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_trTagStart)
            .isEqualTo(12);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_trTagEnd)
            .isEqualTo(13);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tr)
            .isEqualTo(14);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tdTagStart)
            .isEqualTo(15);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tdTagEnd)
            .isEqualTo(16);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_td)
            .isEqualTo(17);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_thTagStart)
            .isEqualTo(18);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_thTagEnd)
            .isEqualTo(19);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_th)
            .isEqualTo(20);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_bodyTagStart)
            .isEqualTo(21);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_bodyTagEnd)
            .isEqualTo(22);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_body)
            .isEqualTo(23);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_colgroupTagStart)
            .isEqualTo(24);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_colgroupTagEnd)
            .isEqualTo(25);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_colgroup)
            .isEqualTo(26);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_ddTagStart)
            .isEqualTo(27);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_ddTagEnd)
            .isEqualTo(28);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_dd)
            .isEqualTo(29);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_dtTagStart)
            .isEqualTo(30);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_dtTagEnd)
            .isEqualTo(31);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_dt)
            .isEqualTo(32);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_headTagStart)
            .isEqualTo(33);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_headTagEnd)
            .isEqualTo(34);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_head)
            .isEqualTo(35);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlTagStart)
            .isEqualTo(36);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlTagEnd)
            .isEqualTo(37);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_html)
            .isEqualTo(38);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_optionTagStart)
            .isEqualTo(39);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_optionTagEnd)
            .isEqualTo(40);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_option)
            .isEqualTo(41);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tbodyTagStart)
            .isEqualTo(42);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tbodyTagEnd)
            .isEqualTo(43);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tbody)
            .isEqualTo(44);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tfootTagStart)
            .isEqualTo(45);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tfootTagEnd)
            .isEqualTo(46);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_tfoot)
            .isEqualTo(47);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_theadTagStart)
            .isEqualTo(48);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_theadTagEnd)
            .isEqualTo(49);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_thead)
            .isEqualTo(50);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_singletonElement)
            .isEqualTo(51);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_emptyTag)
            .isEqualTo(52);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_areaTag)
            .isEqualTo(53);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_baseTag)
            .isEqualTo(54);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_basefontTag)
            .isEqualTo(55);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_brTag)
            .isEqualTo(56);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_colTag)
            .isEqualTo(57);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_frameTag)
            .isEqualTo(58);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_hrTag)
            .isEqualTo(59);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_imgTag)
            .isEqualTo(60);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_inputTag)
            .isEqualTo(61);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_isindexTag)
            .isEqualTo(62);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_linkTag)
            .isEqualTo(63);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_metaTag)
            .isEqualTo(64);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_paramTag)
            .isEqualTo(65);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_wrongSingletonTag)
            .isEqualTo(66);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_singletonTagName)
            .isEqualTo(67);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_description)
            .isEqualTo(68);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_reference)
            .isEqualTo(69);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_parameters)
            .isEqualTo(70);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_javadocTag)
            .isEqualTo(71);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_javadocInlineTag)
            .isEqualTo(72);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_htmlComment)
            .isEqualTo(73);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_text)
            .isEqualTo(74);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_embedTag)
            .isEqualTo(75);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_keygenTag)
            .isEqualTo(76);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_sourceTag)
            .isEqualTo(77);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_trackTag)
            .isEqualTo(78);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_wbrTag)
            .isEqualTo(79);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_optgroupTagStart)
            .isEqualTo(80);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_optgroupTagEnd)
            .isEqualTo(81);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_optgroup)
            .isEqualTo(82);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rbTagStart)
            .isEqualTo(83);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rbTagEnd)
            .isEqualTo(84);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rb)
            .isEqualTo(85);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rtTagStart)
            .isEqualTo(86);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rtTagEnd)
            .isEqualTo(87);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rt)
            .isEqualTo(88);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rtcTagStart)
            .isEqualTo(89);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rtcTagEnd)
            .isEqualTo(90);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rtc)
            .isEqualTo(91);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rpTagStart)
            .isEqualTo(92);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rpTagEnd)
            .isEqualTo(93);
        assertWithMessage(MSG)
            .that(JavadocParser.RULE_rp)
            .isEqualTo(94);

        final int tokenCount = (int) Arrays.stream(JavadocParser.class.getDeclaredFields())
                .filter(GeneratedJavadocTokenTypesTest::isPublicStaticFinalInt)
                .filter(GeneratedJavadocTokenTypesTest::isRule)
                .count();

        // Read JavaDoc before changing count below
        assertWithMessage("all rules must be added to list in"
                        + " 'GeneratedJavadocTokenTypesTest' and verified"
                        + " that their old numbering didn't change")
            .that(tokenCount)
            .isEqualTo(95);
    }

    /**
     * Checks that a given field is named 'RULE_'.
     *
     * @param field field to verify the name of
     * @return true if field is named 'RULE_'
     */
    private static boolean isRule(Field field) {
        return field.getName().startsWith("RULE_");
    }

    /**
     * Checks that a given field is 'public static final int'.
     *
     * @param field field to verify type and visibility of
     * @return true if field is declared as 'public static final int'
     */
    private static boolean isPublicStaticFinalInt(Field field) {
        final Class<?> fieldType = field.getType();
        final int mods = field.getModifiers();
        return fieldType.equals(Integer.TYPE)
                && Modifier.isPublic(mods)
                && Modifier.isStatic(mods)
                && Modifier.isFinal(mods);
    }

}
