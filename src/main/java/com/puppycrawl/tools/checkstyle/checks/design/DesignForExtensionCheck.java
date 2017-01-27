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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * The check finds classes that are designed for extension (subclass creation).
 *
 * <p>
 * Nothing wrong could be with founded classes.
 * This check makes sense only for library projects (not an application projects)
 * which care of ideal OOP-design to make sure that class works in all cases even misusage.
 * Even in library projects this check most likely will find classes that are designed for extension
 * by somebody. User needs to use suppressions extensively to got a benefit from this check,
 * and keep in suppressions all confirmed/known classes that are deigned for inheritance
 * intentionally to let the check catch only new classes, and bring this to team/user attention.
 * </p>
 *
 * <p>
 * ATTENTION: Only user can decide whether a class is designed for extension or not.
 * The check just shows all classes which are possibly designed for extension.
 * If smth inappropriate is found please use suppression.
 * </p>
 *
 * <p>
 * ATTENTION: If the method which can be overridden in a subclass has a javadoc comment
 * (a good practise is to explain its self-use of overridable methods) the check will not
 * rise a violation. The violation can also be skipped if the method which can be overridden
 * in a subclass has one or more annotations that are specified in ignoredAnnotations
 * option. Note, that by default @Override annotation is not included in the
 * ignoredAnnotations set as in a subclass the method which has the annotation can also be
 * overridden in its subclass.
 * </p>
 *
 * <p>
 * More specifically, the check enforces a programming style where superclasses provide empty
 * "hooks" that can be implemented by subclasses.
 * </p>
 *
 * <p>
 * The check finds classes that have overridable methods (public or protected methods
 * that are non-static, not-final, non-abstract) and have non-empty implementation.
 * </p>
 *
 * <p>
 * This protects superclasses against being broken by subclasses. The downside is that subclasses
 * are limited in their flexibility, in particular, they cannot prevent execution of code in the
 * superclass, but that also means that subclasses cannot forget to call their super method.
 * </p>
 *
 * <p>
 * The check has the following options:
 * </p>
 * <ul>
 * <li>
 * ignoredAnnotations - annotations which allow the check to skip the method from validation.
 * Default value is <b>Test, Before, After, BeforeClass, AfterClass</b>.
 * </li>
 * </ul>
 *
 * @author lkuehne
 * @author Andrei Selkin
 */
public class DesignForExtensionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "design.forExtension";

    /**
     * A set of annotations which allow the check to skip the method from validation.
     */
    private Set<String> ignoredAnnotations = Stream.of("Test", "Before", "After", "BeforeClass",
        "AfterClass").collect(Collectors.toSet());

    /**
     * Sets annotations which allow the check to skip the method from validation.
     * @param ignoredAnnotations method annotations.
     */
    public void setIgnoredAnnotations(String... ignoredAnnotations) {
        this.ignoredAnnotations = Arrays.stream(ignoredAnnotations).collect(Collectors.toSet());
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        // The check does not subscribe to CLASS_DEF token as now it is stateless. If the check
        // subscribes to CLASS_DEF token it will become stateful, since we need to have additional
        // stack to hold CLASS_DEF tokens.
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!hasJavadocComment(ast)
                && (isNativeMethod(ast)
                    || !hasEmptyImplementation(ast))
                && canBeOverridden(ast)
                && !hasIgnoredAnnotation(ast, ignoredAnnotations)) {

            final DetailAST classDef = getNearestClassOrEnumDefinition(ast);
            if (canBeSubclassed(classDef)) {
                final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY, className, methodName);
            }
        }
    }

    /**
     * Checks whether a method has a javadoc comment.
     * @param methodDef method definition token.
     * @return true if a method has a javadoc comment.
     */
    private boolean hasJavadocComment(DetailAST methodDef) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.branchContains(TokenTypes.BLOCK_COMMENT_BEGIN);
    }

    /**
     * Checks whether a methods is native.
     * @param ast method definition token.
     * @return true if a methods is native.
     */
    private boolean isNativeMethod(DetailAST ast) {
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        return mods.branchContains(TokenTypes.LITERAL_NATIVE);
    }

    /**
     * Checks whether a method has only comments in the body (has an empty implementation).
     * Method is OK if its implementation is empty.
     * @param ast method definition token.
     * @return true if a method has only comments in the body.
     */
    private static boolean hasEmptyImplementation(DetailAST ast) {
        boolean hasEmptyBody = true;
        final DetailAST methodImplOpenBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (methodImplOpenBrace != null) {
            final DetailAST methodImplCloseBrace = methodImplOpenBrace.getLastChild();
            final Predicate<DetailAST> predicate = currentNode ->
                currentNode != null
                    && currentNode != methodImplCloseBrace
                    && currentNode.getLineNo() <= methodImplCloseBrace.getLineNo()
                    && !TokenUtils.isCommentType(currentNode.getType());
            final Optional<DetailAST> methodBody =
                TokenUtils.findFirstTokenByPredicate(methodImplOpenBrace, predicate);
            if (methodBody.isPresent()) {
                hasEmptyBody = false;
            }
        }
        return hasEmptyBody;
    }

    /**
     * Checks whether a method can be overridden.
     * Method can be overridden if it is not private, abstract, final or static.
     * Note that the check has nothing to do for interfaces.
     * @param methodDef method definition token.
     * @return true if a method can be overridden in a subclass.
     */
    private boolean canBeOverridden(DetailAST methodDef) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        return ScopeUtils.getSurroundingScope(methodDef).isIn(Scope.PROTECTED)
            && !ScopeUtils.isInInterfaceOrAnnotationBlock(methodDef)
            && !modifiers.branchContains(TokenTypes.LITERAL_PRIVATE)
            && !modifiers.branchContains(TokenTypes.ABSTRACT)
            && !modifiers.branchContains(TokenTypes.FINAL)
            && !modifiers.branchContains(TokenTypes.LITERAL_STATIC);
    }

    /**
     * Checks whether a method has any of ignored annotations.
     * @param methodDef method definition token.
     * @param annotations a set of ignored annotations.
     * @return true if a method has any of ignored annotations.
     */
    private static boolean hasIgnoredAnnotation(DetailAST methodDef, Set<String> annotations) {
        return annotations.stream().filter(annotation -> hasAnnotation(methodDef, annotation))
            .findAny().isPresent();
    }

    /**
     * Check if a method has specific annotation.
     * @param methodDef method definition token.
     * @param annotationName annotation name.
     * @return true, if a method has a specific annotation.
     */
    private static boolean hasAnnotation(DetailAST methodDef, String annotationName) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        boolean containsAnnotation = false;
        if (modifiers.branchContains(TokenTypes.ANNOTATION)) {
            final Optional<DetailAST> annotation = TokenUtils.findFirstTokenByPredicate(modifiers,
                currentToken -> currentToken != null
                    && currentToken.getType() == TokenTypes.ANNOTATION
                    && annotationName.equals(getAnnotationName(currentToken)));
            if (annotation.isPresent()) {
                containsAnnotation = true;
            }
        }
        return containsAnnotation;
    }

    /**
     * Gets the name of the annotation.
     * @param annotation to get name of.
     * @return the name of the annotation.
     */
    private static String getAnnotationName(DetailAST annotation) {
        final DetailAST dotAst = annotation.findFirstToken(TokenTypes.DOT);
        final String name;
        if (dotAst == null) {
            name = annotation.findFirstToken(TokenTypes.IDENT).getText();
        }
        else {
            name = dotAst.findFirstToken(TokenTypes.IDENT).getText();
        }
        return name;
    }

    /**
     * Returns CLASS_DEF or ENUM_DEF token which is the nearest to the given ast node.
     * Searches the tree towards the root until it finds a CLASS_DEF or ENUM_DEF node.
     * @param ast the start node for searching.
     * @return the CLASS_DEF or ENUM_DEF token.
     */
    private static DetailAST getNearestClassOrEnumDefinition(DetailAST ast) {
        DetailAST searchAST = ast;
        while (searchAST.getType() != TokenTypes.CLASS_DEF
               && searchAST.getType() != TokenTypes.ENUM_DEF) {
            searchAST = searchAST.getParent();
        }
        return searchAST;
    }

    /**
     * Checks if the given class (given CLASS_DEF node) can be subclassed.
     * @param classDef class definition token.
     * @return true if the containing class can be subclassed.
     */
    private static boolean canBeSubclassed(DetailAST classDef) {
        final DetailAST modifiers = classDef.findFirstToken(TokenTypes.MODIFIERS);
        return classDef.getType() != TokenTypes.ENUM_DEF
            && !modifiers.branchContains(TokenTypes.FINAL)
            && hasDefaultOrExplicitNonPrivateCtor(classDef);
    }

    /**
     * Checks whether a class has default or explicit non-private constructor.
     * @param classDef class ast token.
     * @return true if a class has default or explicit non-private constructor.
     */
    private static boolean hasDefaultOrExplicitNonPrivateCtor(DetailAST classDef) {
        // check if subclassing is prevented by having only private ctors
        final DetailAST objBlock = classDef.findFirstToken(TokenTypes.OBJBLOCK);

        boolean hasDefaultConstructor = true;
        boolean hasExplicitNonPrivateCtor = false;

        DetailAST candidate = objBlock.getFirstChild();

        while (candidate != null) {
            if (candidate.getType() == TokenTypes.CTOR_DEF) {
                hasDefaultConstructor = false;

                final DetailAST ctorMods =
                        candidate.findFirstToken(TokenTypes.MODIFIERS);
                if (!ctorMods.branchContains(TokenTypes.LITERAL_PRIVATE)) {
                    hasExplicitNonPrivateCtor = true;
                    break;
                }
            }
            candidate = candidate.getNextSibling();
        }

        return hasDefaultConstructor || hasExplicitNonPrivateCtor;
    }
}
