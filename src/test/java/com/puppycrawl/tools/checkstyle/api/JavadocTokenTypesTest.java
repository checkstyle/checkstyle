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

package com.puppycrawl.tools.checkstyle.api;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

public class JavadocTokenTypesTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(JavadocTokenTypes.class, true),
                "Constructor is not private");
    }

    @Test
    public void testTokenValues() {
        final String msg = "Please ensure that token values in `JavadocTokenTypes.java` have not"
                + " changed.";
        assertEquals(11, JavadocTokenTypes.RETURN_LITERAL, msg);
        assertEquals(8, JavadocTokenTypes.DEPRECATED_LITERAL, msg);
        assertEquals(16, JavadocTokenTypes.SINCE_LITERAL, msg);
        assertEquals(15, JavadocTokenTypes.SERIAL_DATA_LITERAL, msg);
        assertEquals(14, JavadocTokenTypes.SERIAL_FIELD_LITERAL, msg);
        assertEquals(10, JavadocTokenTypes.PARAM_LITERAL, msg);
        assertEquals(12, JavadocTokenTypes.SEE_LITERAL, msg);
        assertEquals(13, JavadocTokenTypes.SERIAL_LITERAL, msg);
        assertEquals(18, JavadocTokenTypes.VERSION_LITERAL, msg);
        assertEquals(9, JavadocTokenTypes.EXCEPTION_LITERAL, msg);
        assertEquals(17, JavadocTokenTypes.THROWS_LITERAL, msg);
        assertEquals(7, JavadocTokenTypes.AUTHOR_LITERAL, msg);
        assertEquals(21, JavadocTokenTypes.CUSTOM_NAME, msg);
        assertEquals(19, JavadocTokenTypes.JAVADOC_INLINE_TAG_START, msg);
        assertEquals(20, JavadocTokenTypes.JAVADOC_INLINE_TAG_END, msg);
        assertEquals(45, JavadocTokenTypes.CODE_LITERAL, msg);
        assertEquals(46, JavadocTokenTypes.DOC_ROOT_LITERAL, msg);
        assertEquals(48, JavadocTokenTypes.LINK_LITERAL, msg);
        assertEquals(47, JavadocTokenTypes.INHERIT_DOC_LITERAL, msg);
        assertEquals(49, JavadocTokenTypes.LINKPLAIN_LITERAL, msg);
        assertEquals(50, JavadocTokenTypes.LITERAL_LITERAL, msg);
        assertEquals(51, JavadocTokenTypes.VALUE_LITERAL, msg);
        assertEquals(28, JavadocTokenTypes.PACKAGE_CLASS, msg);
        assertEquals(30, JavadocTokenTypes.HASH, msg);
        assertEquals(33, JavadocTokenTypes.MEMBER, msg);
        assertEquals(34, JavadocTokenTypes.LEFT_BRACE, msg);
        assertEquals(35, JavadocTokenTypes.RIGHT_BRACE, msg);
        assertEquals(36, JavadocTokenTypes.ARGUMENT, msg);
        assertEquals(37, JavadocTokenTypes.COMMA, msg);
        assertEquals(27, JavadocTokenTypes.STRING, msg);
        assertEquals(43, JavadocTokenTypes.CLASS_NAME, msg);
        assertEquals(25, JavadocTokenTypes.PARAMETER_NAME, msg);
        assertEquals(23, JavadocTokenTypes.LITERAL_EXCLUDE, msg);
        assertEquals(22, JavadocTokenTypes.LITERAL_INCLUDE, msg);
        assertEquals(39, JavadocTokenTypes.FIELD_NAME, msg);
        assertEquals(41, JavadocTokenTypes.FIELD_TYPE, msg);
        assertEquals(100, JavadocTokenTypes.HTML_TAG_NAME, msg);
        assertEquals(5, JavadocTokenTypes.START, msg);
        assertEquals(57, JavadocTokenTypes.SLASH, msg);
        assertEquals(55, JavadocTokenTypes.END, msg);
        assertEquals(56, JavadocTokenTypes.SLASH_END, msg);
        assertEquals(58, JavadocTokenTypes.EQUALS, msg);
        assertEquals(89, JavadocTokenTypes.ATTR_VALUE, msg);
        assertEquals(59, JavadocTokenTypes.P_HTML_TAG_NAME, msg);
        assertEquals(60, JavadocTokenTypes.LI_HTML_TAG_NAME, msg);
        assertEquals(61, JavadocTokenTypes.TR_HTML_TAG_NAME, msg);
        assertEquals(62, JavadocTokenTypes.TD_HTML_TAG_NAME, msg);
        assertEquals(63, JavadocTokenTypes.TH_HTML_TAG_NAME, msg);
        assertEquals(64, JavadocTokenTypes.BODY_HTML_TAG_NAME, msg);
        assertEquals(65, JavadocTokenTypes.COLGROUP_HTML_TAG_NAME, msg);
        assertEquals(66, JavadocTokenTypes.DD_HTML_TAG_NAME, msg);
        assertEquals(67, JavadocTokenTypes.DT_HTML_TAG_NAME, msg);
        assertEquals(68, JavadocTokenTypes.HEAD_HTML_TAG_NAME, msg);
        assertEquals(69, JavadocTokenTypes.HTML_HTML_TAG_NAME, msg);
        assertEquals(70, JavadocTokenTypes.OPTION_HTML_TAG_NAME, msg);
        assertEquals(71, JavadocTokenTypes.TBODY_HTML_TAG_NAME, msg);
        assertEquals(72, JavadocTokenTypes.TFOOT_HTML_TAG_NAME, msg);
        assertEquals(73, JavadocTokenTypes.THEAD_HTML_TAG_NAME, msg);
        assertEquals(74, JavadocTokenTypes.AREA_HTML_TAG_NAME, msg);
        assertEquals(75, JavadocTokenTypes.BASE_HTML_TAG_NAME, msg);
        assertEquals(76, JavadocTokenTypes.BASEFONT_HTML_TAG_NAME, msg);
        assertEquals(77, JavadocTokenTypes.BR_HTML_TAG_NAME, msg);
        assertEquals(78, JavadocTokenTypes.COL_HTML_TAG_NAME, msg);
        assertEquals(79, JavadocTokenTypes.FRAME_HTML_TAG_NAME, msg);
        assertEquals(80, JavadocTokenTypes.HR_HTML_TAG_NAME, msg);
        assertEquals(81, JavadocTokenTypes.IMG_HTML_TAG_NAME, msg);
        assertEquals(82, JavadocTokenTypes.INPUT_HTML_TAG_NAME, msg);
        assertEquals(83, JavadocTokenTypes.ISINDEX_HTML_TAG_NAME, msg);
        assertEquals(84, JavadocTokenTypes.LINK_HTML_TAG_NAME, msg);
        assertEquals(85, JavadocTokenTypes.META_HTML_TAG_NAME, msg);
        assertEquals(86, JavadocTokenTypes.PARAM_HTML_TAG_NAME, msg);
        assertEquals(87, JavadocTokenTypes.EMBED_HTML_TAG_NAME, msg);
        assertEquals(88, JavadocTokenTypes.KEYGEN_HTML_TAG_NAME, msg);
        assertEquals(92, JavadocTokenTypes.SOURCE_HTML_TAG_NAME, msg);
        assertEquals(93, JavadocTokenTypes.TRACK_HTML_TAG_NAME, msg);
        assertEquals(94, JavadocTokenTypes.WBR_HTML_TAG_NAME, msg);
        assertEquals(2, JavadocTokenTypes.HTML_COMMENT_START, msg);
        assertEquals(91, JavadocTokenTypes.HTML_COMMENT_END, msg);
        assertEquals(3, JavadocTokenTypes.CDATA, msg);
        assertEquals(1, JavadocTokenTypes.LEADING_ASTERISK, msg);
        assertEquals(6, JavadocTokenTypes.NEWLINE, msg);
        assertEquals(24, JavadocTokenTypes.CHAR, msg);
        assertEquals(4, JavadocTokenTypes.WS, msg);
        assertEquals(-1, JavadocTokenTypes.EOF, msg);
        assertEquals(10000, JavadocTokenTypes.JAVADOC, msg);
        assertEquals(10071, JavadocTokenTypes.JAVADOC_TAG, msg);
        assertEquals(10072, JavadocTokenTypes.JAVADOC_INLINE_TAG, msg);
        assertEquals(10069, JavadocTokenTypes.REFERENCE, msg);
        assertEquals(10070, JavadocTokenTypes.PARAMETERS, msg);
        assertEquals(10068, JavadocTokenTypes.DESCRIPTION, msg);
        assertEquals(10001, JavadocTokenTypes.HTML_ELEMENT, msg);
        assertEquals(10002, JavadocTokenTypes.HTML_ELEMENT_START, msg);
        assertEquals(10003, JavadocTokenTypes.HTML_ELEMENT_END, msg);
        assertEquals(10005, JavadocTokenTypes.HTML_TAG, msg);
        assertEquals(10004, JavadocTokenTypes.ATTRIBUTE, msg);
        assertEquals(10008, JavadocTokenTypes.PARAGRAPH, msg);
        assertEquals(10006, JavadocTokenTypes.P_TAG_START, msg);
        assertEquals(10007, JavadocTokenTypes.P_TAG_END, msg);
        assertEquals(10011, JavadocTokenTypes.LI, msg);
        assertEquals(10009, JavadocTokenTypes.LI_TAG_START, msg);
        assertEquals(10010, JavadocTokenTypes.LI_TAG_END, msg);
        assertEquals(10014, JavadocTokenTypes.TR, msg);
        assertEquals(10012, JavadocTokenTypes.TR_TAG_START, msg);
        assertEquals(10013, JavadocTokenTypes.TR_TAG_END, msg);
        assertEquals(10017, JavadocTokenTypes.TD, msg);
        assertEquals(10015, JavadocTokenTypes.TD_TAG_START, msg);
        assertEquals(10016, JavadocTokenTypes.TD_TAG_END, msg);
        assertEquals(10020, JavadocTokenTypes.TH, msg);
        assertEquals(10018, JavadocTokenTypes.TH_TAG_START, msg);
        assertEquals(10019, JavadocTokenTypes.TH_TAG_END, msg);
        assertEquals(10023, JavadocTokenTypes.BODY, msg);
        assertEquals(10021, JavadocTokenTypes.BODY_TAG_START, msg);
        assertEquals(10022, JavadocTokenTypes.BODY_TAG_END, msg);
        assertEquals(10026, JavadocTokenTypes.COLGROUP, msg);
        assertEquals(10024, JavadocTokenTypes.COLGROUP_TAG_START, msg);
        assertEquals(10025, JavadocTokenTypes.COLGROUP_TAG_END, msg);
        assertEquals(10029, JavadocTokenTypes.DD, msg);
        assertEquals(10027, JavadocTokenTypes.DD_TAG_START, msg);
        assertEquals(10028, JavadocTokenTypes.DD_TAG_END, msg);
        assertEquals(10032, JavadocTokenTypes.DT, msg);
        assertEquals(10030, JavadocTokenTypes.DT_TAG_START, msg);
        assertEquals(10031, JavadocTokenTypes.DT_TAG_END, msg);
        assertEquals(10035, JavadocTokenTypes.HEAD, msg);
        assertEquals(10033, JavadocTokenTypes.HEAD_TAG_START, msg);
        assertEquals(10034, JavadocTokenTypes.HEAD_TAG_END, msg);
        assertEquals(10038, JavadocTokenTypes.HTML, msg);
        assertEquals(10036, JavadocTokenTypes.HTML_TAG_START, msg);
        assertEquals(10037, JavadocTokenTypes.HTML_TAG_END, msg);
        assertEquals(10041, JavadocTokenTypes.OPTION, msg);
        assertEquals(10039, JavadocTokenTypes.OPTION_TAG_START, msg);
        assertEquals(10040, JavadocTokenTypes.OPTION_TAG_END, msg);
        assertEquals(10044, JavadocTokenTypes.TBODY, msg);
        assertEquals(10042, JavadocTokenTypes.TBODY_TAG_START, msg);
        assertEquals(10043, JavadocTokenTypes.TBODY_TAG_END, msg);
        assertEquals(10047, JavadocTokenTypes.TFOOT, msg);
        assertEquals(10045, JavadocTokenTypes.TFOOT_TAG_START, msg);
        assertEquals(10046, JavadocTokenTypes.TFOOT_TAG_END, msg);
        assertEquals(10050, JavadocTokenTypes.THEAD, msg);
        assertEquals(10048, JavadocTokenTypes.THEAD_TAG_START, msg);
        assertEquals(10049, JavadocTokenTypes.THEAD_TAG_END, msg);
        assertEquals(10051, JavadocTokenTypes.SINGLETON_ELEMENT, msg);
        assertEquals(10052, JavadocTokenTypes.EMPTY_TAG, msg);
        assertEquals(10053, JavadocTokenTypes.AREA_TAG, msg);
        assertEquals(10054, JavadocTokenTypes.BASE_TAG, msg);
        assertEquals(10055, JavadocTokenTypes.BASEFONT_TAG, msg);
        assertEquals(10056, JavadocTokenTypes.BR_TAG, msg);
        assertEquals(10057, JavadocTokenTypes.COL_TAG, msg);
        assertEquals(10058, JavadocTokenTypes.FRAME_TAG, msg);
        assertEquals(10059, JavadocTokenTypes.HR_TAG, msg);
        assertEquals(10060, JavadocTokenTypes.IMG_TAG, msg);
        assertEquals(10061, JavadocTokenTypes.INPUT_TAG, msg);
        assertEquals(10062, JavadocTokenTypes.ISINDEX_TAG, msg);
        assertEquals(10063, JavadocTokenTypes.LINK_TAG, msg);
        assertEquals(10064, JavadocTokenTypes.META_TAG, msg);
        assertEquals(10065, JavadocTokenTypes.PARAM_TAG, msg);
        assertEquals(10075, JavadocTokenTypes.EMBED_TAG, msg);
        assertEquals(10076, JavadocTokenTypes.KEYGEN_TAG, msg);
        assertEquals(10077, JavadocTokenTypes.SOURCE_TAG, msg);
        assertEquals(10078, JavadocTokenTypes.TRACK_TAG, msg);
        assertEquals(10079, JavadocTokenTypes.WBR_TAG, msg);
        assertEquals(10073, JavadocTokenTypes.HTML_COMMENT, msg);
        assertEquals(10074, JavadocTokenTypes.TEXT, msg);
        assertEquals(95, JavadocTokenTypes.OPTGROUP_HTML_TAG_NAME, msg);
        assertEquals(10080, JavadocTokenTypes.OPTGROUP_TAG_START, msg);
        assertEquals(10081, JavadocTokenTypes.OPTGROUP_TAG_END, msg);
        assertEquals(10082, JavadocTokenTypes.OPTGROUP, msg);
        assertEquals(96, JavadocTokenTypes.RB_HTML_TAG_NAME, msg);
        assertEquals(10083, JavadocTokenTypes.RB_TAG_START, msg);
        assertEquals(10084, JavadocTokenTypes.RB_TAG_END, msg);
        assertEquals(10085, JavadocTokenTypes.RB, msg);
        assertEquals(97, JavadocTokenTypes.RT_HTML_TAG_NAME, msg);
        assertEquals(10086, JavadocTokenTypes.RT_TAG_START, msg);
        assertEquals(10087, JavadocTokenTypes.RT_TAG_END, msg);
        assertEquals(10088, JavadocTokenTypes.RT, msg);
        assertEquals(98, JavadocTokenTypes.RTC_HTML_TAG_NAME, msg);
        assertEquals(10089, JavadocTokenTypes.RTC_TAG_START, msg);
        assertEquals(10090, JavadocTokenTypes.RTC_TAG_END, msg);
        assertEquals(10091, JavadocTokenTypes.RTC, msg);
        assertEquals(99, JavadocTokenTypes.RP_HTML_TAG_NAME, msg);
        assertEquals(10092, JavadocTokenTypes.RP_TAG_START, msg);
        assertEquals(10093, JavadocTokenTypes.RP_TAG_END, msg);
        assertEquals(10094, JavadocTokenTypes.RP, msg);
    }

    @Test
    public void testRuleOffsetValue() throws Exception {
        final Field ruleTypesOffset = JavadocTokenTypes.class.getDeclaredField("RULE_TYPES_OFFSET");
        ruleTypesOffset.setAccessible(true);
        assertEquals(10000, ruleTypesOffset.getInt(null),
                "Please ensure that the field `RULE_TYPES_OFFSET` in"
                        + " `JavadocTokenTypes.java` has a value of 10000");
    }

}
