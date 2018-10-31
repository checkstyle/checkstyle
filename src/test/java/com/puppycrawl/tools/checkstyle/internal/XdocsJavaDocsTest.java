////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class XdocsJavaDocsTest extends AbstractModuleTestSupport {
    private static final List<List<Node>> CHECK_PROPERTIES = new ArrayList<>();
    private static final Map<String, String> CHECK_PROPERTY_DOC = new HashMap<>();
    private static final Map<String, String> CHECK_TEXT = new HashMap<>();

    private static Checker checker;

    private static String checkName;

    @Override
    protected String getPackageLocation() {
        return "com.puppycrawl.tools.checkstyle.internal";
    }

    @Before
    public void setUp() throws Exception {
        final DefaultConfiguration checkConfig = new DefaultConfiguration(
                JavaDocCapture.class.getName());
        checker = createChecker(checkConfig);
    }

    /**
     * Test contains asserts in callstack, but idea does not see them.
     * @noinspection JUnitTestMethodWithNoAssertions, OverlyComplexBooleanExpression
     */
    // -@cs[CyclomaticComplexity] needed until all suppressions are removed
    @Test
    public void testAllCheckSectionJavaDocs() throws Exception {
        final ModuleFactory moduleFactory = TestUtil.getPackageObjectFactory();

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final File file = path.toFile();
            final String fileName = file.getName();

            if ("config_reporting.xml".equals(fileName)) {
                continue;
            }

            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = section.getAttributes().getNamedItem("name")
                        .getNodeValue();

                // -@cs[BooleanExpressionComplexity] needed until all suppressions are removed
                if ("Content".equals(sectionName) || "Overview".equals(sectionName)
                        // suppression list
                        || !"AbbreviationAsWordInName".equals(sectionName)
                                && !"AbstractClassName".equals(sectionName)
                                && !"CatchParameterName".equals(sectionName)
                                && !"ClassTypeParameterName".equals(sectionName)
                                && !"ConstantName".equals(sectionName)
                                && !"InterfaceMemberImpliedModifier".equals(sectionName)
                                && !"InterfaceTypeParameterName".equals(sectionName)
                                && !"LambdaParameterName".equals(sectionName)
                                && !"LocalFinalVariableName".equals(sectionName)
                                && !"LocalVariableName".equals(sectionName)
                                && !"MemberName".equals(sectionName)
                                && !"MethodName".equals(sectionName)
                                && !"MethodTypeParameterName".equals(sectionName)
                                && !"PackageName".equals(sectionName)
                                && !"ParameterName".equals(sectionName)
                                && !"StaticVariableName".equals(sectionName)
                                && !"TypeName".equals(sectionName)
                                && !"AtclauseOrder".equals(sectionName)
                ) {
                    continue;
                }

                examineCheckSection(moduleFactory, fileName, sectionName, section);
            }
        }
    }

    private static void examineCheckSection(ModuleFactory moduleFactory, String fileName,
            String sectionName, Node section) throws Exception {
        final Object instance;

        try {
            instance = moduleFactory.createModule(sectionName);
        }
        catch (CheckstyleException ex) {
            throw new CheckstyleException(fileName + " couldn't find class: " + sectionName, ex);
        }

        CHECK_TEXT.clear();
        CHECK_PROPERTIES.clear();
        CHECK_PROPERTY_DOC.clear();
        checkName = sectionName;

        examineCheckSectionChildren(section);

        final List<File> files = new ArrayList<>();
        files.add(new File("src/main/java/" + instance.getClass().getName().replace(".", "/")
                + ".java"));

        checker.process(files);
    }

    private static void examineCheckSectionChildren(Node section) {
        for (Node subSection : XmlUtil.getChildrenElements(section)) {
            if (!"subsection".equals(subSection.getNodeName())) {
                final String text = getNodeText(subSection, false);
                if (text.startsWith("Since Checkstyle")) {
                    CHECK_TEXT.put("since", text.substring(17));
                }
                continue;
            }

            final String subSectionName = subSection.getAttributes().getNamedItem("name")
                    .getNodeValue();

            examineCheckSubSection(subSection, subSectionName);
        }
    }

    private static void examineCheckSubSection(Node subSection, String subSectionName) {
        switch (subSectionName) {
            case "Description":
            case "Examples":
                CHECK_TEXT.put(subSectionName, getNodeText(subSection, true).replace("\r", ""));
                break;
            case "Properties":
                populateProperties(subSection);
                CHECK_TEXT.put(subSectionName, createPropertiesText());
                break;
            case "Example of Usage":
                break;
            case "Error Messages":
                break;
            case "Package":
                break;
            case "Parent Module":
                break;
            case "Notes":
                break;
            case "Rule Description":
                break;
            default:
                break;
        }
    }

    private static void populateProperties(Node subSection) {
        boolean skip = true;

        for (Node row : XmlUtil.getChildrenElements(XmlUtil.getFirstChildElement(subSection))) {
            if (skip) {
                skip = false;
                continue;
            }

            CHECK_PROPERTIES.add(new ArrayList<>(XmlUtil.getChildrenElements(row)));
        }
    }

    private static String createPropertiesText() {
        final StringBuilder result = new StringBuilder(100);

        result.append("\n<ul>");

        for (List<Node> property : CHECK_PROPERTIES) {
            final String propertyName = getNodeText(property.get(0), true);

            result.append("\n<li>\nProperty {@code ");
            result.append(propertyName);
            result.append("} - ");

            final String temp = getNodeText(property.get(1), true);

            result.append(temp);
            CHECK_PROPERTY_DOC.put(propertyName, temp);

            if (propertyName.endsWith("token") || propertyName.endsWith("tokens")) {
                result.append(" Default value is: ");
            }
            else {
                result.append(" Default value is ");
            }

            result.append(getNodeText(property.get(3), true));

            if (result.charAt(result.length() - 1) != '.') {
                result.append('.');
            }

            result.append("\n</li>");
        }

        result.append("\n</ul>");

        return result.toString();
    }

    private static String getNodeText(Node node, boolean fixLinks) {
        final StringBuffer result = new StringBuffer(20);

        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() == Node.TEXT_NODE) {
                for (String temp : child.getTextContent().split("\n")) {
                    final String text = temp.trim();

                    if (!text.isEmpty()) {
                        if (shouldAppendSpace(result, text.charAt(0))) {
                            result.append(' ');
                        }

                        result.append(text);
                    }
                }
            }
            else {
                appendNodeText(result, child, fixLinks);
            }
        }

        return result.toString();
    }

    // -@cs[CyclomaticComplexity] No simple way to split this apart.
    private static void appendNodeText(StringBuffer result, Node node, boolean fixLinks) {
        final String name = transformXmlToJavaDocName(node.getNodeName());
        final boolean newLineOpenBefore = "p".equals(name) || "pre".equals(name)
                || "ul".equals(name) || "li".equals(name);
        final boolean newLineOpenAfter = newLineOpenBefore && !"ul".equals(name);
        final boolean newLineClose = newLineOpenAfter || "ul".equals(name);
        final boolean sanitize = "pre".equals(name);
        final boolean changeToTag = "code".equals(name);

        if (newLineOpenBefore) {
            result.append('\n');
        }
        else if (shouldAppendSpace(result, '<')) {
            result.append(' ');
        }

        if (changeToTag) {
            result.append("{@");
            result.append(name);
            result.append(' ');
        }
        else {
            result.append('<');
            result.append(name);
            result.append(getAttributeText(name, node.getAttributes(), fixLinks));
            result.append('>');
        }

        if (newLineOpenAfter) {
            result.append('\n');
        }

        if (sanitize) {
            result.append(sanitizeXml(node.getTextContent()));
        }
        else {
            result.append(getNodeText(node, fixLinks));
        }

        if (newLineClose) {
            result.append('\n');
        }

        if (changeToTag) {
            result.append('}');
        }
        else {
            result.append("</");
            result.append(name);
            result.append('>');
        }
    }

    private static boolean shouldAppendSpace(StringBuffer text, char firstCharToAppend) {
        final boolean result;

        if (text.length() == 0) {
            result = false;
        }
        else {
            final char last = text.charAt(text.length() - 1);

            result = (last == ':' || firstCharToAppend == '@' || Character.isAlphabetic(last)
                    || Character.isAlphabetic(firstCharToAppend)) && !Character.isWhitespace(last);
        }

        return result;
    }

    private static String transformXmlToJavaDocName(String name) {
        final String result;

        if ("source".equals(name)) {
            result = "pre";
        }
        else {
            result = name;
        }

        return result;
    }

    private static String getAttributeText(String nodeName, NamedNodeMap attributes,
            boolean fixLinks) {
        final StringBuilder result = new StringBuilder(20);

        for (int i = 0; i < attributes.getLength(); i++) {
            result.append(' ');

            final Node attribute = attributes.item(i);
            final String attrName = attribute.getNodeName();
            final String attrValue;

            if (fixLinks && "a".equals(nodeName) && "href".equals(attrName)
                    && attribute.getNodeValue().startsWith("apidocs/")) {
                attrValue = "https://checkstyle.org/" + attribute.getNodeValue();
            }
            else {
                attrValue = attribute.getNodeValue();
            }

            result.append(attrName);
            result.append("=\"");
            result.append(attrValue);
            result.append('"');
        }

        return result.toString();
    }

    private static String sanitizeXml(String nodeValue) {
        return nodeValue.replaceAll("^[\\r\\n\\s]+", "").replaceAll("[\\r\\n\\s]+$", "")
                .replace("<", "&lt;").replace(">", "&gt;");
    }

    private static class JavaDocCapture extends AbstractCheck {
        private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z].*");

        private int depth;

        @Override
        public boolean isCommentNodesRequired() {
            return true;
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {
                TokenTypes.BLOCK_COMMENT_BEGIN,
            };
        }

        @Override
        public int[] getDefaultTokens() {
            return getRequiredTokens();
        }

        @Override
        public int[] getAcceptableTokens() {
            return getRequiredTokens();
        }

        @Override
        public void beginTree(DetailAST rootAST) {
            depth = 0;
        }

        @Override
        public void visitToken(DetailAST ast) {
            if (JavadocUtil.isJavadocComment(ast)) {
                final DetailAST node = getParent(ast);

                switch (node.getType()) {
                    case TokenTypes.CLASS_DEF:
                        visitClass(ast);
                        break;
                    case TokenTypes.METHOD_DEF:
                        visitMethod(ast, node);
                        break;
                    case TokenTypes.VARIABLE_DEF:
                        final String propertyName = node.findFirstToken(TokenTypes.IDENT).getText();
                        final String propertyDoc = CHECK_PROPERTY_DOC.get(propertyName);

                        if (propertyDoc != null) {
                            Assert.assertEquals(checkName + "'s class field-level JavaDoc for "
                                    + propertyName, makeFirstUpper(propertyDoc),
                                    getJavaDocText(ast));
                        }
                        break;
                    case TokenTypes.CTOR_DEF:
                    case TokenTypes.ENUM_DEF:
                    case TokenTypes.ENUM_CONSTANT_DEF:
                        // ignore
                        break;
                    default:
                        Assert.fail("Unknown token '" + TokenUtil.getTokenName(node.getType())
                                + "': " + ast.getLineNo());
                        break;
                }
            }
        }

        @Override
        public void leaveToken(DetailAST ast) {
            final DetailAST node = getParent(ast);

            if (node.getType() == TokenTypes.CLASS_DEF && JavadocUtil.isJavadocComment(ast)) {
                depth--;
            }
        }

        private static DetailAST getParent(DetailAST node) {
            DetailAST result = node.getParent();
            int type = result.getType();

            while (type == TokenTypes.MODIFIERS || type == TokenTypes.ANNOTATION) {
                result = result.getParent();
                type = result.getType();
            }

            return result;
        }

        private void visitClass(DetailAST ast) {
            if (depth == 0) {
                Assert.assertEquals(
                        checkName + "'s class-level JavaDoc",
                        CHECK_TEXT.get("Description") + CHECK_TEXT.get("Properties")
                                + CHECK_TEXT.get("Examples") + " @since "
                                + CHECK_TEXT.get("since"), getJavaDocText(ast));
            }

            depth++;
        }

        private void visitMethod(DetailAST ast, DetailAST node) {
            if (depth == 0 && isSetterMethod(node)) {
                final String propertyUpper = node.findFirstToken(TokenTypes.IDENT)
                        .getText().substring(3);
                final String propertyName = makeFirstLower(propertyUpper);
                final String propertyDoc = CHECK_PROPERTY_DOC.get(propertyName);

                if (propertyDoc != null) {
                    final String javaDoc = getJavaDocText(ast);

                    Assert.assertEquals(checkName + "'s class method-level JavaDoc for "
                            + propertyName,
                            "Setter to " + makeFirstLower(propertyDoc),
                            javaDoc.substring(0, javaDoc.indexOf(" @param")));
                }
            }
        }

        /**
         * Returns whether an AST represents a setter method. This is similar to
         * {@link CheckUtil#isSetterMethod(DetailAST)} except this doesn't care
         * about the number of children in the method.
         * @param ast the AST to check with.
         * @return whether the AST represents a setter method.
         */
        private static boolean isSetterMethod(DetailAST ast) {
            boolean setterMethod = false;

            if (ast.getType() == TokenTypes.METHOD_DEF) {
                final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
                final String name = type.getNextSibling().getText();
                final boolean matchesSetterFormat = SETTER_PATTERN.matcher(name).matches();
                final boolean voidReturnType = type.findFirstToken(TokenTypes.LITERAL_VOID) != null;

                final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
                final boolean singleParam = params.getChildCount(TokenTypes.PARAMETER_DEF) == 1;

                if (matchesSetterFormat && voidReturnType && singleParam) {
                    final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                    setterMethod = slist != null;
                }
            }
            return setterMethod;
        }

        private static String getJavaDocText(DetailAST node) {
            final String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<document>\n"
                    + node.getFirstChild().getText().replaceAll("(^|\\r?\\n)\\s*\\* ?", "\n")
                            .trim() + "\n</document>";
            String result = null;

            try {
                result = getNodeText(XmlUtil.getRawXml(checkName, text, text).getFirstChild(),
                        false).replace("\r", "");
            }
            catch (ParserConfigurationException ex) {
                Assert.fail("Exception: " + ex.getClass() + " - " + ex.getMessage());
            }

            return result;
        }

        private static String makeFirstUpper(String str) {
            final char ch = str.charAt(0);
            final String result;

            if (Character.isLowerCase(ch)) {
                result = Character.toUpperCase(ch) + str.substring(1);
            }
            else {
                result = str;
            }

            return result;
        }

        private static String makeFirstLower(String str) {
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
    }
}
