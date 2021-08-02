////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class HiddenFieldCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield";
    }

    @Test
    public void testStaticVisibilityFromLambdas() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "31:34: " + getCheckMessage(MSG_KEY, "value"),
            "63:31: " + getCheckMessage(MSG_KEY, "languageCode"),
            "72:35: " + getCheckMessage(MSG_KEY, "number"),
            "85:35: " + getCheckMessage(MSG_KEY, "id"),
            "113:33: " + getCheckMessage(MSG_KEY, "note"),
            "138:48: " + getCheckMessage(MSG_KEY, "stringValue"),
            "138:69: " + getCheckMessage(MSG_KEY, "intValue"),
            "149:69: " + getCheckMessage(MSG_KEY, "doubleValue"),
            "161:51: " + getCheckMessage(MSG_KEY, "firstString"),
            "161:64: " + getCheckMessage(MSG_KEY, "secondString"),
            "177:49: " + getCheckMessage(MSG_KEY, "first"),
            "203:62: " + getCheckMessage(MSG_KEY, "mPi"),
            "219:27: " + getCheckMessage(MSG_KEY, "justSomeList"),
            "219:61: " + getCheckMessage(MSG_KEY, "justSomeMap"),
            "231:55: " + getCheckMessage(MSG_KEY, "someObject"),
            "239:52: " + getCheckMessage(MSG_KEY, "someObject"),
        };
        verify(checkConfig, getPath("InputHiddenFieldLambdas.java"), expected);
    }

    @Test
    public void testStaticVisibilityFromAnonymousClasses() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "22:45: " + getCheckMessage(MSG_KEY, "other"),
            "28:42: " + getCheckMessage(MSG_KEY, "other"),
            "36:49: " + getCheckMessage(MSG_KEY, "other"),
            "46:53: " + getCheckMessage(MSG_KEY, "other"),
            "58:26: " + getCheckMessage(MSG_KEY, "someField"),
        };
        verify(checkConfig, getPath("InputHiddenFieldStaticVisibility.java"), expected);
    }

    @Test
    public void testNoParameters()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("tokens", "VARIABLE_DEF");
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verify(checkConfig, getPath("InputHiddenField1.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "61:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "118:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:29: " + getCheckMessage(MSG_KEY, "prop"),
            "136:28: " + getCheckMessage(MSG_KEY, "prop"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "191:23: " + getCheckMessage(MSG_KEY, "y"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "222:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "242:41: " + getCheckMessage(MSG_KEY, "x"),
            "248:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "265:41: " + getCheckMessage(MSG_KEY, "prop"),
            "279:29: " + getCheckMessage(MSG_KEY, "prop"),
            "290:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "302:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField2.java"), expected);
    }

    /** Tests ignoreFormat property. */
    @Test
    public void testIgnoreFormat()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreFormat", "^i.*$");
        assertNotNull(checkConfig.getProperty("ignoreFormat"),
                "Ignore format should not be null");
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "118:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:29: " + getCheckMessage(MSG_KEY, "prop"),
            "136:28: " + getCheckMessage(MSG_KEY, "prop"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "191:23: " + getCheckMessage(MSG_KEY, "y"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "222:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "242:41: " + getCheckMessage(MSG_KEY, "x"),
            "248:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "265:41: " + getCheckMessage(MSG_KEY, "prop"),
            "279:29: " + getCheckMessage(MSG_KEY, "prop"),
            "290:42: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verify(checkConfig, getPath("InputHiddenField3.java"), expected);
    }

    /** Tests ignoreSetter property. */
    @Test
    public void testIgnoreSetter()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreSetter", "true");
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "61:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "118:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:29: " + getCheckMessage(MSG_KEY, "prop"),
            "136:28: " + getCheckMessage(MSG_KEY, "prop"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "191:23: " + getCheckMessage(MSG_KEY, "y"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "222:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "242:41: " + getCheckMessage(MSG_KEY, "x"),
            "265:41: " + getCheckMessage(MSG_KEY, "prop"),
            "290:42: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verify(checkConfig, getPath("InputHiddenField4.java"), expected);
    }

    /** Tests ignoreSetter and setterCanReturnItsClass properties. */
    @Test
    public void testIgnoreChainSetter()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreSetter", "true");
        checkConfig.addProperty("setterCanReturnItsClass", "true");
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "61:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "118:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:29: " + getCheckMessage(MSG_KEY, "prop"),
            "136:28: " + getCheckMessage(MSG_KEY, "prop"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "191:23: " + getCheckMessage(MSG_KEY, "y"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "222:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "242:41: " + getCheckMessage(MSG_KEY, "x"),
        };
        verify(checkConfig, getPath("InputHiddenField5.java"), expected);
    }

    /** Tests ignoreConstructorParameter property. */
    @Test
    public void testIgnoreConstructorParameter()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreConstructorParameter", "true");
        final String[] expected = {
            "29:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "38:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "43:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "47:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "57:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "66:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "72:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "75:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "80:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "111:29: " + getCheckMessage(MSG_KEY, "prop"),
            "117:29: " + getCheckMessage(MSG_KEY, "prop"),
            "123:29: " + getCheckMessage(MSG_KEY, "prop"),
            "135:28: " + getCheckMessage(MSG_KEY, "prop"),
            "149:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "154:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "159:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "163:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "190:23: " + getCheckMessage(MSG_KEY, "y"),
            "211:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "228:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "234:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "241:41: " + getCheckMessage(MSG_KEY, "x"),
            "247:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "264:41: " + getCheckMessage(MSG_KEY, "prop"),
            "278:29: " + getCheckMessage(MSG_KEY, "prop"),
            "289:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "301:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField6.java"), expected);
    }

    /** Test against a class with field declarations in different order. */
    @Test
    public void testReordered()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:40: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "61:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "95:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "117:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "130:21: " + getCheckMessage(MSG_KEY, "hidden"),
            "137:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "143:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verify(checkConfig, getPath("InputHiddenFieldReorder.java"), expected);
    }

    @Test
    public void testIgnoreAbstractMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreAbstractMethods", "true");

        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "58:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "61:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "67:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "73:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "81:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "82:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "88:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "89:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "94:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "112:29: " + getCheckMessage(MSG_KEY, "prop"),
            "118:29: " + getCheckMessage(MSG_KEY, "prop"),
            "124:29: " + getCheckMessage(MSG_KEY, "prop"),
            "136:28: " + getCheckMessage(MSG_KEY, "prop"),
            "150:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "164:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "191:23: " + getCheckMessage(MSG_KEY, "y"),
            "212:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "222:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "229:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "235:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "248:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "265:41: " + getCheckMessage(MSG_KEY, "prop"),
            "279:29: " + getCheckMessage(MSG_KEY, "prop"),
            "290:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "302:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputHiddenField7.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHiddenFieldReceiver.java"), expected);
    }

    @Test
    public void testHiddenFieldEnhancedInstanceof() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("tokens", "PATTERN_VARIABLE_DEF");

        final String[] expected = {
            "26:39: " + getCheckMessage(MSG_KEY, "price"),
            "37:35: " + getCheckMessage(MSG_KEY, "hiddenStaticField"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputHiddenFieldEnhancedInstanceof.java"), expected);
    }

    @Test
    public void testHiddenFieldSwitchExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(HiddenFieldCheck.class);

        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_KEY, "x"),
            "32:30: " + getCheckMessage(MSG_KEY, "word"),
            "33:35: " + getCheckMessage(MSG_KEY, "otherWord"),
            "34:21: " + getCheckMessage(MSG_KEY, "y"),
            "38:21: " + getCheckMessage(MSG_KEY, "z"),
            "42:21: " + getCheckMessage(MSG_KEY, "a"),
            "46:21: " + getCheckMessage(MSG_KEY, "b"),
            "53:13: " + getCheckMessage(MSG_KEY, "x"),
            "57:30: " + getCheckMessage(MSG_KEY, "word"),
            "58:35: " + getCheckMessage(MSG_KEY, "otherWord"),
            "59:21: " + getCheckMessage(MSG_KEY, "y"),
            "63:21: " + getCheckMessage(MSG_KEY, "z"),
            "67:21: " + getCheckMessage(MSG_KEY, "a"),
            "71:21: " + getCheckMessage(MSG_KEY, "b"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputHiddenFieldSwitchExpression.java"), expected);
    }

    @Test
    public void testHiddenFieldRecords() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);

        final String[] expected = {
            "23:17: " + getCheckMessage(MSG_KEY, "myHiddenInt"),
            "28:17: " + getCheckMessage(MSG_KEY, "myHiddenInt"),
            "36:17: " + getCheckMessage(MSG_KEY, "hiddenField"),
            "44:39: " + getCheckMessage(MSG_KEY, "hiddenStaticField"),
            "58:39: " + getCheckMessage(MSG_KEY, "price"),
            "67:21: " + getCheckMessage(MSG_KEY, "x"),
            "71:20: " + getCheckMessage(MSG_KEY, "string"),
            "81:21: " + getCheckMessage(MSG_KEY, "x"),
            "85:20: " + getCheckMessage(MSG_KEY, "string"),
        };
        verify(checkConfig,
            getNonCompilablePath("InputHiddenFieldRecords.java"), expected);
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testClearState() throws Exception {
        final HiddenFieldCheck check = new HiddenFieldCheck();
        final DetailAST root = JavaParser.parseFile(new File(getPath("InputHiddenField8.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertTrue(classDef.isPresent(), "Ast should contain CLASS_DEF");
        assertTrue(
                TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.get(), "frame",
                        new CheckIfStatefulFieldCleared()), "State is not cleared on beginTree");
    }

    private static class CheckIfStatefulFieldCleared implements Predicate<Object> {

        @Override
        public boolean test(Object frame) {
            boolean result = frame != null;

            // verify object is cleared
            if (result) {
                final Class<?> frameClass = frame.getClass();

                try {
                    if (TestUtil.getClassDeclaredField(frameClass, "parent").get(frame) != null
                            || !((Boolean) TestUtil.getClassDeclaredField(frameClass, "staticType")
                                    .get(frame))
                            || TestUtil.getClassDeclaredField(frameClass, "frameName")
                                    .get(frame) != null) {
                        result = false;
                    }
                }
                catch (NoSuchFieldException | IllegalArgumentException
                        | IllegalAccessException ex) {
                    throw new IllegalStateException(ex);
                }
            }

            return result;
        }

    }

}
