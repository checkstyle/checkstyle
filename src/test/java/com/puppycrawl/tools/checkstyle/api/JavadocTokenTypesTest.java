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

package com.puppycrawl.tools.checkstyle.api;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

public class JavadocTokenTypesTest {
    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(JavadocTokenTypes.class, true));
    }

    @Test
    public void testTokenValues() {
        final String msg = "Please ensure that token values in `JavadocTokenTypes.java` have not"
                + " changed.";
        assertEquals(msg, 11, JavadocTokenTypes.RETURN_LITERAL);
        assertEquals(msg, 8, JavadocTokenTypes.DEPRECATED_LITERAL);
        assertEquals(msg, 16, JavadocTokenTypes.SINCE_LITERAL);
        assertEquals(msg, 15, JavadocTokenTypes.SERIAL_DATA_LITERAL);
        assertEquals(msg, 14, JavadocTokenTypes.SERIAL_FIELD_LITERAL);
        assertEquals(msg, 10, JavadocTokenTypes.PARAM_LITERAL);
        assertEquals(msg, 12, JavadocTokenTypes.SEE_LITERAL);
        assertEquals(msg, 13, JavadocTokenTypes.SERIAL_LITERAL);
        assertEquals(msg, 18, JavadocTokenTypes.VERSION_LITERAL);
        assertEquals(msg, 9, JavadocTokenTypes.EXCEPTION_LITERAL);
        assertEquals(msg, 17, JavadocTokenTypes.THROWS_LITERAL);
        assertEquals(msg, 7, JavadocTokenTypes.AUTHOR_LITERAL);
        assertEquals(msg, 21, JavadocTokenTypes.CUSTOM_NAME);
        assertEquals(msg, 19, JavadocTokenTypes.JAVADOC_INLINE_TAG_START);
        assertEquals(msg, 20, JavadocTokenTypes.JAVADOC_INLINE_TAG_END);
        assertEquals(msg, 45, JavadocTokenTypes.CODE_LITERAL);
        assertEquals(msg, 46, JavadocTokenTypes.DOC_ROOT_LITERAL);
        assertEquals(msg, 48, JavadocTokenTypes.LINK_LITERAL);
        assertEquals(msg, 47, JavadocTokenTypes.INHERIT_DOC_LITERAL);
        assertEquals(msg, 49, JavadocTokenTypes.LINKPLAIN_LITERAL);
        assertEquals(msg, 50, JavadocTokenTypes.LITERAL_LITERAL);
        assertEquals(msg, 51, JavadocTokenTypes.VALUE_LITERAL);
        assertEquals(msg, 28, JavadocTokenTypes.PACKAGE_CLASS);
        assertEquals(msg, 30, JavadocTokenTypes.HASH);
        assertEquals(msg, 33, JavadocTokenTypes.MEMBER);
        assertEquals(msg, 34, JavadocTokenTypes.LEFT_BRACE);
        assertEquals(msg, 35, JavadocTokenTypes.RIGHT_BRACE);
        assertEquals(msg, 36, JavadocTokenTypes.ARGUMENT);
        assertEquals(msg, 37, JavadocTokenTypes.COMMA);
        assertEquals(msg, 27, JavadocTokenTypes.STRING);
        assertEquals(msg, 43, JavadocTokenTypes.CLASS_NAME);
        assertEquals(msg, 25, JavadocTokenTypes.PARAMETER_NAME);
        assertEquals(msg, 23, JavadocTokenTypes.LITERAL_EXCLUDE);
        assertEquals(msg, 22, JavadocTokenTypes.LITERAL_INCLUDE);
        assertEquals(msg, 39, JavadocTokenTypes.FIELD_NAME);
        assertEquals(msg, 41, JavadocTokenTypes.FIELD_TYPE);
        assertEquals(msg, 95, JavadocTokenTypes.HTML_TAG_NAME);
        assertEquals(msg, 5, JavadocTokenTypes.START);
        assertEquals(msg, 57, JavadocTokenTypes.SLASH);
        assertEquals(msg, 55, JavadocTokenTypes.END);
        assertEquals(msg, 56, JavadocTokenTypes.SLASH_END);
        assertEquals(msg, 58, JavadocTokenTypes.EQUALS);
        assertEquals(msg, 89, JavadocTokenTypes.ATTR_VALUE);
        assertEquals(msg, 59, JavadocTokenTypes.P_HTML_TAG_NAME);
        assertEquals(msg, 60, JavadocTokenTypes.LI_HTML_TAG_NAME);
        assertEquals(msg, 61, JavadocTokenTypes.TR_HTML_TAG_NAME);
        assertEquals(msg, 62, JavadocTokenTypes.TD_HTML_TAG_NAME);
        assertEquals(msg, 63, JavadocTokenTypes.TH_HTML_TAG_NAME);
        assertEquals(msg, 64, JavadocTokenTypes.BODY_HTML_TAG_NAME);
        assertEquals(msg, 65, JavadocTokenTypes.COLGROUP_HTML_TAG_NAME);
        assertEquals(msg, 66, JavadocTokenTypes.DD_HTML_TAG_NAME);
        assertEquals(msg, 67, JavadocTokenTypes.DT_HTML_TAG_NAME);
        assertEquals(msg, 68, JavadocTokenTypes.HEAD_HTML_TAG_NAME);
        assertEquals(msg, 69, JavadocTokenTypes.HTML_HTML_TAG_NAME);
        assertEquals(msg, 70, JavadocTokenTypes.OPTION_HTML_TAG_NAME);
        assertEquals(msg, 71, JavadocTokenTypes.TBODY_HTML_TAG_NAME);
        assertEquals(msg, 72, JavadocTokenTypes.TFOOT_HTML_TAG_NAME);
        assertEquals(msg, 73, JavadocTokenTypes.THEAD_HTML_TAG_NAME);
        assertEquals(msg, 74, JavadocTokenTypes.AREA_HTML_TAG_NAME);
        assertEquals(msg, 75, JavadocTokenTypes.BASE_HTML_TAG_NAME);
        assertEquals(msg, 76, JavadocTokenTypes.BASEFONT_HTML_TAG_NAME);
        assertEquals(msg, 77, JavadocTokenTypes.BR_HTML_TAG_NAME);
        assertEquals(msg, 78, JavadocTokenTypes.COL_HTML_TAG_NAME);
        assertEquals(msg, 79, JavadocTokenTypes.FRAME_HTML_TAG_NAME);
        assertEquals(msg, 80, JavadocTokenTypes.HR_HTML_TAG_NAME);
        assertEquals(msg, 81, JavadocTokenTypes.IMG_HTML_TAG_NAME);
        assertEquals(msg, 82, JavadocTokenTypes.INPUT_HTML_TAG_NAME);
        assertEquals(msg, 83, JavadocTokenTypes.ISINDEX_HTML_TAG_NAME);
        assertEquals(msg, 84, JavadocTokenTypes.LINK_HTML_TAG_NAME);
        assertEquals(msg, 85, JavadocTokenTypes.META_HTML_TAG_NAME);
        assertEquals(msg, 86, JavadocTokenTypes.PARAM_HTML_TAG_NAME);
        assertEquals(msg, 87, JavadocTokenTypes.EMBED_HTML_TAG_NAME);
        assertEquals(msg, 88, JavadocTokenTypes.KEYGEN_HTML_TAG_NAME);
        assertEquals(msg, 92, JavadocTokenTypes.SOURCE_HTML_TAG_NAME);
        assertEquals(msg, 93, JavadocTokenTypes.TRACK_HTML_TAG_NAME);
        assertEquals(msg, 94, JavadocTokenTypes.WBR_HTML_TAG_NAME);
        assertEquals(msg, 2, JavadocTokenTypes.HTML_COMMENT_START);
        assertEquals(msg, 91, JavadocTokenTypes.HTML_COMMENT_END);
        assertEquals(msg, 3, JavadocTokenTypes.CDATA);
        assertEquals(msg, 1, JavadocTokenTypes.LEADING_ASTERISK);
        assertEquals(msg, 6, JavadocTokenTypes.NEWLINE);
        assertEquals(msg, 24, JavadocTokenTypes.CHAR);
        assertEquals(msg, 4, JavadocTokenTypes.WS);
        assertEquals(msg, -1, JavadocTokenTypes.EOF);
        assertEquals(msg, 10000, JavadocTokenTypes.JAVADOC);
        assertEquals(msg, 10076, JavadocTokenTypes.JAVADOC_TAG);
        assertEquals(msg, 10077, JavadocTokenTypes.JAVADOC_INLINE_TAG);
        assertEquals(msg, 10074, JavadocTokenTypes.REFERENCE);
        assertEquals(msg, 10075, JavadocTokenTypes.PARAMETERS);
        assertEquals(msg, 10073, JavadocTokenTypes.DESCRIPTION);
        assertEquals(msg, 10001, JavadocTokenTypes.HTML_ELEMENT);
        assertEquals(msg, 10002, JavadocTokenTypes.HTML_ELEMENT_START);
        assertEquals(msg, 10003, JavadocTokenTypes.HTML_ELEMENT_END);
        assertEquals(msg, 10005, JavadocTokenTypes.HTML_TAG);
        assertEquals(msg, 10004, JavadocTokenTypes.ATTRIBUTE);
        assertEquals(msg, 10008, JavadocTokenTypes.PARAGRAPH);
        assertEquals(msg, 10006, JavadocTokenTypes.P_TAG_START);
        assertEquals(msg, 10007, JavadocTokenTypes.P_TAG_END);
        assertEquals(msg, 10011, JavadocTokenTypes.LI);
        assertEquals(msg, 10009, JavadocTokenTypes.LI_TAG_START);
        assertEquals(msg, 10010, JavadocTokenTypes.LI_TAG_END);
        assertEquals(msg, 10014, JavadocTokenTypes.TR);
        assertEquals(msg, 10012, JavadocTokenTypes.TR_TAG_START);
        assertEquals(msg, 10013, JavadocTokenTypes.TR_TAG_END);
        assertEquals(msg, 10017, JavadocTokenTypes.TD);
        assertEquals(msg, 10015, JavadocTokenTypes.TD_TAG_START);
        assertEquals(msg, 10016, JavadocTokenTypes.TD_TAG_END);
        assertEquals(msg, 10020, JavadocTokenTypes.TH);
        assertEquals(msg, 10018, JavadocTokenTypes.TH_TAG_START);
        assertEquals(msg, 10019, JavadocTokenTypes.TH_TAG_END);
        assertEquals(msg, 10023, JavadocTokenTypes.BODY);
        assertEquals(msg, 10021, JavadocTokenTypes.BODY_TAG_START);
        assertEquals(msg, 10022, JavadocTokenTypes.BODY_TAG_END);
        assertEquals(msg, 10026, JavadocTokenTypes.COLGROUP);
        assertEquals(msg, 10024, JavadocTokenTypes.COLGROUP_TAG_START);
        assertEquals(msg, 10025, JavadocTokenTypes.COLGROUP_TAG_END);
        assertEquals(msg, 10029, JavadocTokenTypes.DD);
        assertEquals(msg, 10027, JavadocTokenTypes.DD_TAG_START);
        assertEquals(msg, 10028, JavadocTokenTypes.DD_TAG_END);
        assertEquals(msg, 10032, JavadocTokenTypes.DT);
        assertEquals(msg, 10030, JavadocTokenTypes.DT_TAG_START);
        assertEquals(msg, 10031, JavadocTokenTypes.DT_TAG_END);
        assertEquals(msg, 10035, JavadocTokenTypes.HEAD);
        assertEquals(msg, 10033, JavadocTokenTypes.HEAD_TAG_START);
        assertEquals(msg, 10034, JavadocTokenTypes.HEAD_TAG_END);
        assertEquals(msg, 10038, JavadocTokenTypes.HTML);
        assertEquals(msg, 10036, JavadocTokenTypes.HTML_TAG_START);
        assertEquals(msg, 10037, JavadocTokenTypes.HTML_TAG_END);
        assertEquals(msg, 10041, JavadocTokenTypes.OPTION);
        assertEquals(msg, 10039, JavadocTokenTypes.OPTION_TAG_START);
        assertEquals(msg, 10040, JavadocTokenTypes.OPTION_TAG_END);
        assertEquals(msg, 10044, JavadocTokenTypes.TBODY);
        assertEquals(msg, 10042, JavadocTokenTypes.TBODY_TAG_START);
        assertEquals(msg, 10043, JavadocTokenTypes.TBODY_TAG_END);
        assertEquals(msg, 10047, JavadocTokenTypes.TFOOT);
        assertEquals(msg, 10045, JavadocTokenTypes.TFOOT_TAG_START);
        assertEquals(msg, 10046, JavadocTokenTypes.TFOOT_TAG_END);
        assertEquals(msg, 10050, JavadocTokenTypes.THEAD);
        assertEquals(msg, 10048, JavadocTokenTypes.THEAD_TAG_START);
        assertEquals(msg, 10049, JavadocTokenTypes.THEAD_TAG_END);
        assertEquals(msg, 10051, JavadocTokenTypes.SINGLETON_ELEMENT);
        assertEquals(msg, 10052, JavadocTokenTypes.EMPTY_TAG);
        assertEquals(msg, 10053, JavadocTokenTypes.AREA_TAG);
        assertEquals(msg, 10054, JavadocTokenTypes.BASE_TAG);
        assertEquals(msg, 10055, JavadocTokenTypes.BASEFONT_TAG);
        assertEquals(msg, 10056, JavadocTokenTypes.BR_TAG);
        assertEquals(msg, 10057, JavadocTokenTypes.COL_TAG);
        assertEquals(msg, 10058, JavadocTokenTypes.FRAME_TAG);
        assertEquals(msg, 10059, JavadocTokenTypes.HR_TAG);
        assertEquals(msg, 10060, JavadocTokenTypes.IMG_TAG);
        assertEquals(msg, 10061, JavadocTokenTypes.INPUT_TAG);
        assertEquals(msg, 10062, JavadocTokenTypes.ISINDEX_TAG);
        assertEquals(msg, 10063, JavadocTokenTypes.LINK_TAG);
        assertEquals(msg, 10064, JavadocTokenTypes.META_TAG);
        assertEquals(msg, 10065, JavadocTokenTypes.PARAM_TAG);
        assertEquals(msg, 10066, JavadocTokenTypes.EMBED_TAG);
        assertEquals(msg, 10067, JavadocTokenTypes.KEYGEN_TAG);
        assertEquals(msg, 10068, JavadocTokenTypes.SOURCE_TAG);
        assertEquals(msg, 10069, JavadocTokenTypes.TRACK_TAG);
        assertEquals(msg, 10070, JavadocTokenTypes.WBR_TAG);
        assertEquals(msg, 10078, JavadocTokenTypes.HTML_COMMENT);
        assertEquals(msg, 10079, JavadocTokenTypes.TEXT);
    }

    @Test
    public void testRuleOffsetValue() throws Exception {
        final Field ruleTypesOffset = JavadocTokenTypes.class.getDeclaredField("RULE_TYPES_OFFSET");
        ruleTypesOffset.setAccessible(true);
        assertEquals("Please ensure that the field `RULE_TYPES_OFFSET` in"
                + " `JavadocTokenTypes.java` has a value of 10000",
                10000, ruleTypesOffset.getInt(null));
    }
}
