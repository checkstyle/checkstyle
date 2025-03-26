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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck.MSG_MULTIPLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.findTokenInAstByPredicate;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;

public class CheckUtilTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/checkutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(CheckUtil.class))
                .isTrue();
    }

    @Test
    public void testParseDoubleWithIncorrectToken() {
        final double parsedDouble = CheckUtil.parseDouble("1_02", TokenTypes.ASSIGN);
        assertWithMessage("Invalid parse result")
            .that(parsedDouble)
            .isEqualTo(Double.NaN);
    }

    @Test
    public void testEquals() {
        final DetailAstImpl litStatic = new DetailAstImpl();
        litStatic.setType(TokenTypes.LITERAL_STATIC);

        final DetailAstImpl modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.MODIFIERS);
        modifiers.addChild(litStatic);

        final DetailAstImpl metDef = new DetailAstImpl();
        metDef.setType(TokenTypes.METHOD_DEF);
        metDef.addChild(modifiers);

        assertWithMessage("Invalid result: ast is not equals method")
                .that(CheckUtil.isEqualsMethod(metDef))
                .isFalse();

        metDef.removeChildren();

        final DetailAstImpl metName = new DetailAstImpl();
        metName.setType(TokenTypes.IDENT);
        metName.setText("equals");
        metDef.addChild(metName);

        final DetailAstImpl modifiers2 = new DetailAstImpl();
        modifiers2.setType(TokenTypes.MODIFIERS);
        metDef.addChild(modifiers2);

        final DetailAstImpl parameter1 = new DetailAstImpl();
        final DetailAstImpl parameter2 = new DetailAstImpl();

        final DetailAstImpl parameters = new DetailAstImpl();
        parameters.setType(TokenTypes.PARAMETERS);

        parameters.addChild(parameter2);

        parameters.addChild(parameter1);
        metDef.addChild(parameters);

        assertWithMessage("Invalid result: ast is not equals method")
                .that(CheckUtil.isEqualsMethod(metDef))
                .isFalse();
    }

    @Test
    public void testGetAccessModifierFromModifiersTokenWrongTokenType() {
        final DetailAstImpl modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.METHOD_DEF);

        try {
            CheckUtil.getAccessModifierFromModifiersToken(modifiers);
            assertWithMessage("%s was expected.", IllegalArgumentException.class.getSimpleName())
                .fail();
        }
        catch (IllegalArgumentException exc) {
            final String expectedExceptionMsg = "expected non-null AST-token with type 'MODIFIERS'";
            final String actualExceptionMsg = exc.getMessage();
            assertWithMessage("Invalid exception message")
                .that(actualExceptionMsg)
                .isEqualTo(expectedExceptionMsg);
        }
    }

    @Test
    public void testGetTypeParameterNames() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final List<String> expected = Arrays.asList("V", "C");
        final List<String> actual = CheckUtil.getTypeParameterNames(parameterizedClassNode);

        assertWithMessage("Invalid type parameters")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetTypeParameters() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final DetailAST firstTypeParameter =
                getNode(parameterizedClassNode, TokenTypes.TYPE_PARAMETER);
        final List<DetailAST> expected = Arrays.asList(firstTypeParameter,
                firstTypeParameter.getNextSibling().getNextSibling());
        final List<DetailAST> actual = CheckUtil.getTypeParameters(parameterizedClassNode);

        assertWithMessage("Invalid type parameters")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testIsEqualsMethod() throws Exception {
        final DetailAST equalsMethodNode = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST someOtherMethod = equalsMethodNode.getNextSibling();

        assertWithMessage("Invalid result: AST provided is not equals method")
                .that(CheckUtil.isEqualsMethod(equalsMethodNode))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is equals method")
                .that(CheckUtil.isEqualsMethod(someOtherMethod))
                .isFalse();
    }

    @Test
    public void testIsNonVoidMethod() throws Exception {
        final DetailAST nonVoidMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST voidMethod = nonVoidMethod.getNextSibling();

        assertWithMessage("Invalid result: AST provided is void method")
                .that(CheckUtil.isNonVoidMethod(nonVoidMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is non void method")
                .that(CheckUtil.isNonVoidMethod(voidMethod))
                .isFalse();
    }

    @Test
    public void testGetAccessModifierFromModifiersToken() throws Exception {
        final DetailAST interfaceDef = getNodeFromFile(TokenTypes.INTERFACE_DEF);
        final AccessModifierOption modifierInterface = CheckUtil
                .getAccessModifierFromModifiersToken(interfaceDef
                        .findFirstToken(TokenTypes.OBJBLOCK)
                        .findFirstToken(TokenTypes.METHOD_DEF));
        assertWithMessage("Invalid access modifier")
            .that(modifierInterface)
            .isEqualTo(AccessModifierOption.PUBLIC);

        final DetailAST privateVariable = getNodeFromFile(TokenTypes.VARIABLE_DEF);
        final AccessModifierOption modifierPrivate =
                CheckUtil.getAccessModifierFromModifiersToken(privateVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPrivate)
            .isEqualTo(AccessModifierOption.PRIVATE);

        final DetailAST protectedVariable = privateVariable.getNextSibling();
        final AccessModifierOption modifierProtected =
                CheckUtil.getAccessModifierFromModifiersToken(protectedVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierProtected)
            .isEqualTo(AccessModifierOption.PROTECTED);

        final DetailAST publicVariable = protectedVariable.getNextSibling();
        final AccessModifierOption modifierPublic =
                CheckUtil.getAccessModifierFromModifiersToken(publicVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPublic)
            .isEqualTo(AccessModifierOption.PUBLIC);

        final DetailAST packageVariable = publicVariable.getNextSibling();
        final AccessModifierOption modifierPackage =
                CheckUtil.getAccessModifierFromModifiersToken(packageVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPackage)
            .isEqualTo(AccessModifierOption.PACKAGE);

        final DetailAST enumConstantDefinition = getNodeFromFile(TokenTypes.ENUM_CONSTANT_DEF);
        final AccessModifierOption modifierEnumConstant = CheckUtil
                .getAccessModifierFromModifiersToken(enumConstantDefinition);
        assertWithMessage("Invalid access modifier")
                .that(modifierEnumConstant)
                .isEqualTo(AccessModifierOption.PUBLIC);
    }

    @Test
    public void testGetFirstNode() throws Exception {
        final DetailAST classDef = getNodeFromFile(TokenTypes.CLASS_DEF);

        final DetailAST firstChild = classDef.getFirstChild().getFirstChild();
        final DetailAST firstNode = CheckUtil.getFirstNode(classDef);
        assertWithMessage("Invalid first node")
            .that(firstNode)
            .isEqualTo(firstChild);
    }

    @Test
    public void testGetFirstNode1() {
        final DetailAstImpl child = new DetailAstImpl();
        child.setLineNo(5);
        child.setColumnNo(6);

        final DetailAstImpl root = new DetailAstImpl();
        root.setLineNo(5);
        root.setColumnNo(6);

        root.addChild(child);

        final DetailAST firstNode = CheckUtil.getFirstNode(root);
        assertWithMessage("Unexpected node")
            .that(firstNode)
            .isEqualTo(root);
    }

    @Test
    public void testGetFirstNode2() {
        final DetailAstImpl child = new DetailAstImpl();
        child.setLineNo(6);
        child.setColumnNo(5);

        final DetailAstImpl root = new DetailAstImpl();
        root.setLineNo(5);
        root.setColumnNo(6);

        root.addChild(child);

        final DetailAST firstNode = CheckUtil.getFirstNode(root);
        assertWithMessage("Unexpected node")
            .that(firstNode)
            .isEqualTo(root);
    }

    @Test
    public void testParseDoubleFloatingPointValues() {
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-0.05f", TokenTypes.NUM_FLOAT))
            .isEqualTo(-0.05);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("10.0", TokenTypes.NUM_DOUBLE))
            .isEqualTo(10.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("1.23e3", TokenTypes.NUM_DOUBLE))
            .isEqualTo(1230);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-3.21E2", TokenTypes.NUM_DOUBLE))
            .isEqualTo(-321);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-0.0", TokenTypes.NUM_DOUBLE))
            .isEqualTo(-0.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("NaN", TokenTypes.NUM_DOUBLE))
            .isEqualTo(Double.NaN);
    }

    @Test
    public void testParseDoubleIntegerValues() {
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0L", TokenTypes.NUM_LONG))
            .isEqualTo(0.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0B101", TokenTypes.NUM_INT))
            .isEqualTo(0b101);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0b10001010001011010000101000101L", TokenTypes.NUM_LONG))
            .isEqualTo(289_775_941);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("1", TokenTypes.NUM_INT))
            .isEqualTo(1.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("8L", TokenTypes.NUM_LONG))
            .isEqualTo(8.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-21474836480", TokenTypes.NUM_LONG))
            .isEqualTo(-2.147_483_648E10);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-2", TokenTypes.NUM_INT))
            .isEqualTo(-2);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0xffffffff", TokenTypes.NUM_INT))
            .isEqualTo(-1);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0x0B63", TokenTypes.NUM_INT))
            .isEqualTo(2915.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("21474836470", TokenTypes.NUM_LONG))
            .isEqualTo(2.147_483_647E10);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("073l", TokenTypes.NUM_LONG))
            .isEqualTo(59.0);
    }

    @Test
    public void testParseClassNames() {
        final Set<String> actual = CheckUtil.parseClassNames(
                "I.am.class.name.with.dot.in.the.end.", "ClassOnly", "my.Class");
        final Set<String> expected = new HashSet<>();
        expected.add("I.am.class.name.with.dot.in.the.end.");
        expected.add("ClassOnly");
        expected.add("my.Class");
        expected.add("Class");
        assertWithMessage("Result is not expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testEqualsAvoidNullCheck() throws Exception {

        final String[] expected = {
            "14:28: " + getCheckMessage(EqualsAvoidNullCheck.class, MSG_EQUALS_AVOID_NULL),
            "21:17: " + getCheckMessage(EqualsAvoidNullCheck.class, MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil1.java"), expected);
    }

    @Test
    public void testMultipleVariableDeclarationsCheck() throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class, MSG_MULTIPLE),
            "14:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class, MSG_MULTIPLE),
        };
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil2.java"),
               expected);
    }

    @Test
    public void testNestedIfDepth() throws Exception {
        final String[] expected = {
            "26:17: " + getCheckMessage(NestedIfDepthCheck.class, MSG_KEY, 2, 1),
            "52:17: " + getCheckMessage(NestedIfDepthCheck.class, MSG_KEY, 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil3.java"), expected);
    }

    @Test
    public void testJavaDocMethod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil4.java"), expected);
    }

    @Test
    public void testJavaDocMethod2() throws Exception {
        final String[] expected = {
            "14:25: " + getCheckMessage(JavadocMethodCheck.class,
                  MSG_EXPECTED_TAG, "@param", "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil5.java"), expected);
    }

    @Test
    public void testJavadoc() throws Exception {
        final String[] expected = {
            "25:39: " + getCheckMessage(JavadocMethodCheck.class,
                  MSG_EXPECTED_TAG, "@param", "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCheckUtil7.java"), expected);
    }

    private DetailAST getNodeFromFile(int type) throws Exception {
        final Path path = Path.of(getPath("InputCheckUtilTest.java"));
        return getNode(JavaParser.parseFile(path.toFile(), JavaParser.Options.WITH_COMMENTS), type);
    }

    /**
     * Retrieves the AST node from a specific file based on the specified token type.
     *
     * @param type The token type to search for in the file.
     *             This parameter determines the type of AST node to retrieve.
     * @param filePath The file from which the AST node should be retrieved.
     * @return The AST node associated with the specified token type from the given file.
     * @throws Exception If there's an issue reading or parsing the file.
     */
    public static DetailAST getNode(Path filePath, int type) throws Exception {
        return getNode(JavaParser.parseFile(filePath.toFile(),
                JavaParser.Options.WITH_COMMENTS), type);
    }

    /**
     * Temporary java doc.
     *
     * @param root of type DetailAST
     * @param type of type int
     * @return call to get() from node
     */
    private static DetailAST getNode(DetailAST root, int type) {
        final Optional<DetailAST> node = findTokenInAstByPredicate(root,
            ast -> ast.getType() == type);

        assertWithMessage("Cannot find node of specified type: %s", type)
            .that(node.isPresent())
            .isTrue();

        return node.orElseThrow();
    }

    @Test
    public void testPackageInfo() {
        final boolean result = CheckUtil.isPackageInfo("/");

        assertWithMessage("Expected isPackageInfo() to return false for ('/')")
                .that(result)
                .isFalse();
    }

}
