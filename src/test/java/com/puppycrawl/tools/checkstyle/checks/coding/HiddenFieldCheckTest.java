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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck.MSG_KEY;

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
        final String[] expected = {
            "31:34: " + getCheckMessage(MSG_KEY, "value"),
            "64:31: " + getCheckMessage(MSG_KEY, "languageCode"),
            "74:35: " + getCheckMessage(MSG_KEY, "number"),
            "87:35: " + getCheckMessage(MSG_KEY, "id"),
            "115:33: " + getCheckMessage(MSG_KEY, "note"),
            "141:48: " + getCheckMessage(MSG_KEY, "stringValue"),
            "141:69: " + getCheckMessage(MSG_KEY, "intValue"),
            "155:69: " + getCheckMessage(MSG_KEY, "doubleValue"),
            "168:51: " + getCheckMessage(MSG_KEY, "firstString"),
            "168:64: " + getCheckMessage(MSG_KEY, "secondString"),
            "187:49: " + getCheckMessage(MSG_KEY, "first"),
            "214:62: " + getCheckMessage(MSG_KEY, "mPi"),
            "231:27: " + getCheckMessage(MSG_KEY, "justSomeList"),
            "231:61: " + getCheckMessage(MSG_KEY, "justSomeMap"),
            "246:55: " + getCheckMessage(MSG_KEY, "someObject"),
            "255:52: " + getCheckMessage(MSG_KEY, "someObject"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenFieldLambdas.java"), expected);
    }

    @Test
    public void testStaticVisibilityFromAnonymousClasses() throws Exception {
        final String[] expected = {
            "22:45: " + getCheckMessage(MSG_KEY, "other"),
            "28:42: " + getCheckMessage(MSG_KEY, "other"),
            "36:49: " + getCheckMessage(MSG_KEY, "other"),
            "46:53: " + getCheckMessage(MSG_KEY, "other"),
            "58:26: " + getCheckMessage(MSG_KEY, "someField"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenFieldStaticVisibility.java"), expected);
    }

    @Test
    public void testNoParameters()
            throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "39:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "44:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "59:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "74:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "78:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "90:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "91:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "96:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "152:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "157:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "162:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "166:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "214:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "231:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "237:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField1.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "80:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "94:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "99:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "117:29: " + getCheckMessage(MSG_KEY, "prop"),
            "123:29: " + getCheckMessage(MSG_KEY, "prop"),
            "129:29: " + getCheckMessage(MSG_KEY, "prop"),
            "141:28: " + getCheckMessage(MSG_KEY, "prop"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "165:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "169:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "196:23: " + getCheckMessage(MSG_KEY, "y"),
            "217:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "227:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "234:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "240:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "247:41: " + getCheckMessage(MSG_KEY, "x"),
            "253:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "270:41: " + getCheckMessage(MSG_KEY, "prop"),
            "284:29: " + getCheckMessage(MSG_KEY, "prop"),
            "295:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "307:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField2.java"), expected);
    }

    /** Tests ignoreFormat property. */
    @Test
    public void testIgnoreFormat()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("ignoreFormat", "^i.*$");
        assertWithMessage("Ignore format should not be null")
            .that(checkConfig.getProperty("ignoreFormat"))
            .isNotNull();
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "79:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "92:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "97:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "115:29: " + getCheckMessage(MSG_KEY, "prop"),
            "121:29: " + getCheckMessage(MSG_KEY, "prop"),
            "127:29: " + getCheckMessage(MSG_KEY, "prop"),
            "139:28: " + getCheckMessage(MSG_KEY, "prop"),
            "153:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "158:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "163:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "167:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "194:23: " + getCheckMessage(MSG_KEY, "y"),
            "215:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "225:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "232:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "238:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "245:41: " + getCheckMessage(MSG_KEY, "x"),
            "251:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "268:41: " + getCheckMessage(MSG_KEY, "prop"),
            "282:29: " + getCheckMessage(MSG_KEY, "prop"),
            "293:42: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField3.java"), expected);
    }

    /** Tests ignoreSetter property. */
    @Test
    public void testIgnoreSetter()
            throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "80:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "94:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "99:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "123:29: " + getCheckMessage(MSG_KEY, "prop"),
            "129:29: " + getCheckMessage(MSG_KEY, "prop"),
            "141:28: " + getCheckMessage(MSG_KEY, "prop"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "165:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "169:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "196:23: " + getCheckMessage(MSG_KEY, "y"),
            "217:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "227:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "234:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "240:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "247:41: " + getCheckMessage(MSG_KEY, "x"),
            "270:41: " + getCheckMessage(MSG_KEY, "prop"),
            "295:42: " + getCheckMessage(MSG_KEY, "prop2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField4.java"), expected);
    }

    /** Tests ignoreSetter and setterCanReturnItsClass properties. */
    @Test
    public void testIgnoreChainSetter()
            throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "80:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "94:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "99:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "123:29: " + getCheckMessage(MSG_KEY, "prop"),
            "129:29: " + getCheckMessage(MSG_KEY, "prop"),
            "141:28: " + getCheckMessage(MSG_KEY, "prop"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "165:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "169:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "196:23: " + getCheckMessage(MSG_KEY, "y"),
            "217:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "227:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "234:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "240:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "247:41: " + getCheckMessage(MSG_KEY, "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField5.java"), expected);
    }

    /** Tests ignoreConstructorParameter property. */
    @Test
    public void testIgnoreConstructorParameter()
            throws Exception {
        final String[] expected = {
            "29:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "38:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "43:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "48:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "59:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "68:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "69:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "74:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "78:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "83:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "91:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "92:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "97:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "115:29: " + getCheckMessage(MSG_KEY, "prop"),
            "121:29: " + getCheckMessage(MSG_KEY, "prop"),
            "127:29: " + getCheckMessage(MSG_KEY, "prop"),
            "139:28: " + getCheckMessage(MSG_KEY, "prop"),
            "153:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "158:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "163:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "167:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "194:23: " + getCheckMessage(MSG_KEY, "y"),
            "215:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "232:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "238:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "245:41: " + getCheckMessage(MSG_KEY, "x"),
            "251:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "268:41: " + getCheckMessage(MSG_KEY, "prop"),
            "282:29: " + getCheckMessage(MSG_KEY, "prop"),
            "293:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "305:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField6.java"), expected);
    }

    /** Test against a class with field declarations in different order. */
    @Test
    public void testReordered()
            throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:40: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "80:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "94:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "100:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "122:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "135:21: " + getCheckMessage(MSG_KEY, "hidden"),
            "142:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "148:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenFieldReorder.java"), expected);
    }

    @Test
    public void testIgnoreAbstractMethods() throws Exception {

        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "33:34: " + getCheckMessage(MSG_KEY, "hidden"),
            "40:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "45:18: " + getCheckMessage(MSG_KEY, "hidden"),
            "50:33: " + getCheckMessage(MSG_KEY, "hidden"),
            "61:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "64:26: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "70:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "71:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "76:22: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "80:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "85:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "87:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "93:17: " + getCheckMessage(MSG_KEY, "innerHidden"),
            "94:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "99:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "117:29: " + getCheckMessage(MSG_KEY, "prop"),
            "123:29: " + getCheckMessage(MSG_KEY, "prop"),
            "129:29: " + getCheckMessage(MSG_KEY, "prop"),
            "141:28: " + getCheckMessage(MSG_KEY, "prop"),
            "155:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "160:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "165:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "169:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "196:23: " + getCheckMessage(MSG_KEY, "y"),
            "217:17: " + getCheckMessage(MSG_KEY, "hidden"),
            "227:22: " + getCheckMessage(MSG_KEY, "hidden"),
            "234:13: " + getCheckMessage(MSG_KEY, "hidden"),
            "240:13: " + getCheckMessage(MSG_KEY, "hiddenStatic"),
            "253:30: " + getCheckMessage(MSG_KEY, "xAxis"),
            "270:41: " + getCheckMessage(MSG_KEY, "prop"),
            "284:29: " + getCheckMessage(MSG_KEY, "prop"),
            "295:42: " + getCheckMessage(MSG_KEY, "prop2"),
            "307:19: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputHiddenField7.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHiddenFieldReceiver.java"), expected);
    }

    @Test
    public void testHiddenFieldEnhancedInstanceof() throws Exception {

        final String[] expected = {
            "26:39: " + getCheckMessage(MSG_KEY, "price"),
            "37:35: " + getCheckMessage(MSG_KEY, "hiddenStaticField"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldEnhancedInstanceof.java"), expected);
    }

    @Test
    public void testHiddenFieldSwitchExpression() throws Exception {

        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_KEY, "x"),
            "32:30: " + getCheckMessage(MSG_KEY, "word"),
            "33:35: " + getCheckMessage(MSG_KEY, "otherWord"),
            "35:21: " + getCheckMessage(MSG_KEY, "y"),
            "39:21: " + getCheckMessage(MSG_KEY, "z"),
            "43:21: " + getCheckMessage(MSG_KEY, "a"),
            "47:21: " + getCheckMessage(MSG_KEY, "b"),
            "54:13: " + getCheckMessage(MSG_KEY, "x"),
            "58:30: " + getCheckMessage(MSG_KEY, "word"),
            "59:35: " + getCheckMessage(MSG_KEY, "otherWord"),
            "61:21: " + getCheckMessage(MSG_KEY, "y"),
            "65:21: " + getCheckMessage(MSG_KEY, "z"),
            "69:21: " + getCheckMessage(MSG_KEY, "a"),
            "73:21: " + getCheckMessage(MSG_KEY, "b"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldSwitchExpression.java"), expected);
    }

    @Test
    public void testHiddenFieldRecords() throws Exception {

        final String[] expected = {
            "23:17: " + getCheckMessage(MSG_KEY, "myHiddenInt"),
            "28:17: " + getCheckMessage(MSG_KEY, "myHiddenInt"),
            "36:17: " + getCheckMessage(MSG_KEY, "hiddenField"),
            "44:39: " + getCheckMessage(MSG_KEY, "hiddenStaticField"),
            "59:39: " + getCheckMessage(MSG_KEY, "price"),
            "68:21: " + getCheckMessage(MSG_KEY, "x"),
            "72:20: " + getCheckMessage(MSG_KEY, "string"),
            "82:21: " + getCheckMessage(MSG_KEY, "x"),
            "86:20: " + getCheckMessage(MSG_KEY, "string"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldRecords.java"), expected);
    }

    @Test
    public void testHiddenFieldLambdasInNestedScope() throws Exception {
        final String[] expected = {
            "21:34: " + getCheckMessage(MSG_KEY, "value"),
        };
        verifyWithInlineConfigParser(
            getPath("InputHiddenFieldLambdas2.java"), expected);
    }

    @Test
    public void testClassNestedInRecord() throws Exception {

        final String[] expected = {
            "23:26: " + getCheckMessage(MSG_KEY, "a"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputHiddenFieldClassNestedInRecord.java"),
            expected);
    }

    @Test
    public void testHiddenFieldInnerRecordsImplicitlyStatic() throws Exception {

        final String[] expected = {
            "36:30: " + getCheckMessage(MSG_KEY, "pointer"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldInnerRecordsImplicitlyStatic.java"),
                expected);
    }

    @Test
    public void testHiddenFieldRecordsImplicitlyStaticClassComparison() throws Exception {

        final String[] expected = {
            "49:27: " + getCheckMessage(MSG_KEY, "x"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldRecordsImplicitlyStaticClassComparison.java"),
                expected);
    }

    @Test
    public void testHiddenFieldRecordPattern() throws Exception {

        final String[] expected = {
            "18:46: " + getCheckMessage(MSG_KEY, "s"),
            "18:53: " + getCheckMessage(MSG_KEY, "x"),
            "26:39: " + getCheckMessage(MSG_KEY, "s"),
            "26:46: " + getCheckMessage(MSG_KEY, "x"),
            "35:45: " + getCheckMessage(MSG_KEY, "s"),
            "35:52: " + getCheckMessage(MSG_KEY, "x"),
            "35:63: " + getCheckMessage(MSG_KEY, "z"),
            "43:37: " + getCheckMessage(MSG_KEY, "s"),
            "43:44: " + getCheckMessage(MSG_KEY, "x"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputHiddenFieldRecordPattern.java"), expected);
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

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(
                    TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.orElseThrow(),
                        "frame", new CheckIfStatefulFieldCleared()))
                .isTrue();
    }

    private static final class CheckIfStatefulFieldCleared implements Predicate<Object> {

        @Override
        public boolean test(Object frame) {
            boolean result = frame != null;

            // verify object is cleared
            if (result
                    && (TestUtil.getInternalState(frame, "parent") != null
                        || !TestUtil.<Boolean>getInternalState(frame, "staticType")
                        || TestUtil.getInternalState(frame, "frameName") != null)) {
                result = false;
            }

            return result;
        }

    }

}
