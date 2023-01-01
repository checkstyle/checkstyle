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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParserBaseVisitor;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class JavaAstVisitorTest extends AbstractModuleTestSupport {

    /**
     * If a visit method is not overridden, we should explain why we do not 'visit' the
     * parse tree at this node and construct an AST. Reasons could include that we have
     * no terminal symbols (tokens) in the corresponding production rule, or that
     * we handle the construction of this particular AST in its parent node. If we
     * have a production rule where we have terminal symbols (tokens), but we do not build
     * an AST from tokens in the rule context, the rule is extraneous.
     */
    private static final Set<String> VISIT_METHODS_NOT_OVERRIDDEN = Set.of(
            // no tokens in production rule, so no AST to build
            "visitClassOrInterfaceOrPrimitiveType",
            "visitNonWildcardTypeArgs",
            "visitStat",
            "visitAnnotationConstantRest",
            "visitSwitchLabeledRule",
            "visitLocalVariableDeclaration",
            "visitTypes",
            "visitSwitchStat",
            "visitSwitchPrimary",
            "visitClassDef",
            "visitInterfaceMemberDeclaration",
            "visitMemberDeclaration",
            "visitLiteralPrimary",
            "visitPatternDefinition",
            "visitLocalType",
            "visitLocalTypeDeclaration",
            "visitRecordBodyDeclaration",
            "visitResource",
            "visitVariableInitializer",
            "visitLambdaBody",
            "visitPatternVariableDef",

            // AST built in parent rule
            "visitCreatedNameExtended",
            "visitSuperSuffixSimple",
            "visitFieldAccessNoIdent",
            "visitClassType",
            "visitClassOrInterfaceTypeExtended",
            "visitQualifiedNameExtended"
    );

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javaastvisitor";
    }

    @Test
    public void testAllVisitMethodsAreOverridden() {
        final Method[] baseVisitMethods = JavaLanguageParserBaseVisitor
                .class.getDeclaredMethods();
        final Method[] visitMethods = JavaAstVisitor.class.getDeclaredMethods();

        final Set<String> filteredBaseVisitMethodNames = Arrays.stream(baseVisitMethods)
                .filter(method -> !VISIT_METHODS_NOT_OVERRIDDEN.contains(method.getName()))
                .filter(method -> method.getName().contains("visit"))
                .filter(method -> method.getModifiers() == Modifier.PUBLIC)
                .map(Method::getName)
                .collect(Collectors.toSet());

        final Set<String> filteredVisitMethodNames = Arrays.stream(visitMethods)
                .filter(method -> method.getName().contains("visit"))
                .filter(method -> method.getModifiers() == Modifier.PUBLIC)
                .map(Method::getName)
                .collect(Collectors.toSet());

        // remove overridden 'visit' method from ParseTreeVisitor interface in JavaAstVisitor
        filteredVisitMethodNames.remove("visit");

        final String message = "Visit methods in 'JavaLanguageParserBaseVisitor' generated from "
                + "production rules and labeled alternatives in 'JavaLanguageParser.g4' should "
                + "be overridden in 'JavaAstVisitor' or be added to 'VISIT_METHODS_NOT_OVERRIDDEN' "
                + "with comment explaining why.";

        assertWithMessage(message)
                .that(filteredVisitMethodNames)
                .containsExactlyElementsIn(filteredBaseVisitMethodNames);
    }

    @Test
    public void testOrderOfVisitMethodsAndProductionRules() throws Exception {
        // Order of BaseVisitor's generated 'visit' methods match the order of
        // production rules in 'JavaLanguageParser.g4'.
        final String baseVisitorFilename = "target/generated-sources/antlr/com/puppycrawl"
                + "/tools/checkstyle/grammar/java/JavaLanguageParserBaseVisitor.java";
        final DetailAST baseVisitorAst = JavaParser.parseFile(new File(baseVisitorFilename),
                            JavaParser.Options.WITHOUT_COMMENTS);

        final String visitorFilename = "src/main/java/com/puppycrawl/tools/checkstyle"
                + "/JavaAstVisitor.java";
        final DetailAST visitorAst = JavaParser.parseFile(new File(visitorFilename),
                            JavaParser.Options.WITHOUT_COMMENTS);

        final List<String> orderedBaseVisitorMethodNames =
                getOrderedVisitMethodNames(baseVisitorAst);
        final List<String> orderedVisitorMethodNames =
                getOrderedVisitMethodNames(visitorAst);

        orderedBaseVisitorMethodNames.removeAll(VISIT_METHODS_NOT_OVERRIDDEN);

        // remove overridden 'visit' method from ParseTreeVisitor interface in JavaAstVisitor
        orderedVisitorMethodNames.remove("visit");

        assertWithMessage("Visit methods in 'JavaAstVisitor' should appear in same order as "
                + "production rules and labeled alternatives in 'JavaLanguageParser.g4'.")
                .that(orderedVisitorMethodNames)
                .containsExactlyElementsIn(orderedBaseVisitorMethodNames)
                .inOrder();
    }

    /**
     * Finds all {@code visit...} methods in a source file, and collects
     * the method names into a list. This method counts on the simple structure
     * of 'JavaAstVisitor' and 'JavaLanguageParserBaseVisitor'.
     *
     * @param root the root of the AST to extract method names from
     * @return list of all {@code visit...} method names
     */
    private static List<String> getOrderedVisitMethodNames(DetailAST root) {
        final List<String> orderedVisitMethodNames = new ArrayList<>();

        DetailAST classDef = root.getFirstChild();
        while (classDef.getType() != TokenTypes.CLASS_DEF) {
            classDef = classDef.getNextSibling();
        }

        final DetailAST objBlock = classDef.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST objBlockChild = objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        while (objBlockChild != null) {
            if (isVisitMethod(objBlockChild)) {
                orderedVisitMethodNames.add(objBlockChild
                        .findFirstToken(TokenTypes.IDENT)
                        .getText());
            }
            objBlockChild = objBlockChild.getNextSibling();
        }
        return orderedVisitMethodNames;
    }

    /**
     * Checks if given AST is a visit method.
     *
     * @param objBlockChild AST to check
     * @return true if AST is a visit method
     */
    private static boolean isVisitMethod(DetailAST objBlockChild) {
        return objBlockChild.getType() == TokenTypes.METHOD_DEF
                && objBlockChild.findFirstToken(TokenTypes.IDENT).getText().contains("visit");
    }

    @Test
    public void testNullSelfInAddLastSibling() throws Exception {
        final Method addLastSibling = JavaAstVisitor.class
                .getDeclaredMethod("addLastSibling", DetailAstImpl.class, DetailAstImpl.class);
        addLastSibling.setAccessible(true);
        assertWithMessage("Method should not throw exception.")
                .that(addLastSibling.invoke(JavaAstVisitor.class, null, null))
                .isNull();
    }
    /**
     * This test exists to kill surviving mutation from pitest removing expression AST building
     * optimization in {@link JavaAstVisitor#visitBinOp(JavaLanguageParser.BinOpContext)}.
     * We do not use {@link JavaParser#parse(FileContents)} here due to DFA clearing hack.
     *
     * <p>
     * Reason: we have iterative expression AST building to avoid stackoverflow
     * in {@link JavaAstVisitor#visitBinOp(JavaLanguageParser.BinOpContext)}. In actual
     * generated parser, we avoid stackoverflow thanks to the left recursive expression
     * rule (eliminating unnecessary recursive calls to hierarchical expression production rules).
     * However, ANTLR's ParserATNSimulator has no such optimization. So, the number of recursive
     * calls to ParserATNSimulator#closure when calling ParserATNSimulator#clearDFA causes a
     * StackOverflow error. We avoid this by using the single argument constructor (thus not
     * forcing DFA clearing) in this test.
     * </p>
     *
     * @throws Exception if input file does not exist
     */

    @Test
    public void testNoStackOverflowOnDeepStringConcat() throws Exception {
        final File file =
                new File(getPath("InputJavaAstVisitorNoStackOverflowOnDeepStringConcat.java"));
        final FileText fileText = new FileText(file, StandardCharsets.UTF_8.name());
        final FileContents contents = new FileContents(fileText);

        final String fullText = contents.getText().getFullText().toString();
        final CharStream codePointCharStream = CharStreams.fromString(fullText);
        final JavaLanguageLexer lexer = new JavaLanguageLexer(codePointCharStream, true);
        lexer.setCommentListener(contents);

        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        final JavaLanguageParser parser = new JavaLanguageParser(tokenStream);

        final JavaLanguageParser.CompilationUnitContext compilationUnit = parser.compilationUnit();

        // We restrict execution to use limited resources here, so that we can
        // kill surviving pitest mutation from removal of nested binary operation
        // optimization in JavaAstVisitor#visitBinOp. Limited resources (small stack size)
        // ensure that we throw a StackOverflowError if optimization is removed.
        final DetailAST root = TestUtil.getResultWithLimitedResources(
                () -> new JavaAstVisitor(tokenStream).visit(compilationUnit)
        );

        assertWithMessage("File parsing and AST building should complete successfully.")
                .that(root)
                .isNotNull();
    }
}
