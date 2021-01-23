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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that classes are designed for extension (subclass creation).
 * </p>
 * <p>
 * Nothing wrong could be with founded classes.
 * This check makes sense only for library projects (not application projects)
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
 * (a good practice is to explain its self-use of overridable methods) the check will not
 * rise a violation. The violation can also be skipped if the method which can be overridden
 * in a subclass has one or more annotations that are specified in ignoredAnnotations
 * option. Note, that by default @Override annotation is not included in the
 * ignoredAnnotations set as in a subclass the method which has the annotation can also be
 * overridden in its subclass.
 * </p>
 * <p>
 * Problem is described at "Effective Java, 2nd Edition by Joshua Bloch" book, chapter
 * "Item 17: Design and document for inheritance or else prohibit it".
 * </p>
 * <p>
 * Some quotes from book:
 * </p>
 * <blockquote>The class must document its self-use of overridable methods.
 * By convention, a method that invokes overridable methods contains a description
 * of these invocations at the end of its documentation comment. The description
 * begins with the phrase “This implementation.”
 * </blockquote>
 * <blockquote>
 * The best solution to this problem is to prohibit subclassing in classes that
 * are not designed and documented to be safely subclassed.
 * </blockquote>
 * <blockquote>
 * If a concrete class does not implement a standard interface, then you may
 * inconvenience some programmers by prohibiting inheritance. If you feel that you
 * must allow inheritance from such a class, one reasonable approach is to ensure
 * that the class never invokes any of its overridable methods and to document this
 * fact. In other words, eliminate the class’s self-use of overridable methods entirely.
 * In doing so, you’ll create a class that is reasonably safe to subclass. Overriding a
 * method will never affect the behavior of any other method.
 * </blockquote>
 * <p>
 * The check finds classes that have overridable methods (public or protected methods
 * that are non-static, not-final, non-abstract) and have non-empty implementation.
 * </p>
 * <p>
 * Rationale: This library design style protects superclasses against being broken
 * by subclasses. The downside is that subclasses are limited in their flexibility,
 * in particular they cannot prevent execution of code in the superclass, but that
 * also means that subclasses cannot corrupt the state of the superclass by forgetting
 * to call the superclass's method.
 * </p>
 * <p>
 * More specifically, it enforces a programming style where superclasses provide
 * empty "hooks" that can be implemented by subclasses.
 * </p>
 * <p>
 * Example of code that cause violation as it is designed for extension:
 * </p>
 * <pre>
 * public abstract class Plant {
 *   private String roots;
 *   private String trunk;
 *
 *   protected void validate() {
 *     if (roots == null) throw new IllegalArgumentException("No roots!");
 *     if (trunk == null) throw new IllegalArgumentException("No trunk!");
 *   }
 *
 *   public abstract void grow();
 * }
 *
 * public class Tree extends Plant {
 *   private List leaves;
 *
 *   &#64;Overrides
 *   protected void validate() {
 *     super.validate();
 *     if (leaves == null) throw new IllegalArgumentException("No leaves!");
 *   }
 *
 *   public void grow() {
 *     validate();
 *   }
 * }
 * </pre>
 * <p>
 * Example of code without violation:
 * </p>
 * <pre>
 * public abstract class Plant {
 *   private String roots;
 *   private String trunk;
 *
 *   private void validate() {
 *     if (roots == null) throw new IllegalArgumentException("No roots!");
 *     if (trunk == null) throw new IllegalArgumentException("No trunk!");
 *     validateEx();
 *   }
 *
 *   protected void validateEx() { }
 *
 *   public abstract void grow();
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code ignoredAnnotations} - Specify annotations which allow the check to
 * skip the method from validation.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code After, AfterClass, Before, BeforeClass, Test}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;DesignForExtension&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check to allow methods which have @Override and @Test annotations
 * to be designed for extension.
 * </p>
 * <pre>
 * &lt;module name=&quot;DesignForExtension&quot;&gt;
 *   &lt;property name=&quot;ignoredAnnotations&quot; value=&quot;Override, Test&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public class A extends B {
 *   &#64;Override
 *   public int foo() {
 *     return 2;
 *   }
 *
 *   public int foo2() {return 8;} // violation
 * }
 *
 * public class B {
 *   &#47;**
 *    * This implementation ...
 *      &#64;return some int value.
 *    *&#47;
 *   public int foo() {
 *     return 1;
 *   }
 *
 *   public int foo3() {return 3;} // violation
 * }
 *
 * public class FooTest {
 *   &#64;Test
 *   public void testFoo() {
 *     final B b = new A();
 *     assertEquals(2, b.foo());
 *   }
 *
 *   public int foo4() {return 4;} // violation
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code design.forExtension}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class DesignForExtensionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "design.forExtension";

    /**
     * Specify annotations which allow the check to skip the method from validation.
     */
    private Set<String> ignoredAnnotations = Arrays.stream(new String[] {"Test", "Before", "After",
        "BeforeClass", "AfterClass", }).collect(Collectors.toSet());

    /**
     * Setter to specify annotations which allow the check to skip the method from validation.
     *
     * @param ignoredAnnotations method annotations.
     */
    public void setIgnoredAnnotations(String... ignoredAnnotations) {
        this.ignoredAnnotations = Arrays.stream(ignoredAnnotations).collect(Collectors.toSet());
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
    public int[] getRequiredTokens() {
        // The check does not subscribe to CLASS_DEF token as now it is stateless. If the check
        // subscribes to CLASS_DEF token it will become stateful, since we need to have additional
        // stack to hold CLASS_DEF tokens.
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!hasJavadocComment(ast)
                && canBeOverridden(ast)
                && (isNativeMethod(ast)
                    || !hasEmptyImplementation(ast))
                && !hasIgnoredAnnotation(ast, ignoredAnnotations)
                && !ScopeUtil.isInRecordBlock(ast)) {
            final DetailAST classDef = getNearestClassOrEnumDefinition(ast);
            if (canBeSubclassed(classDef)) {
                final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast, MSG_KEY, className, methodName);
            }
        }
    }

    /**
     * Checks whether a method has a javadoc comment.
     *
     * @param methodDef method definition token.
     * @return true if a method has a javadoc comment.
     */
    private static boolean hasJavadocComment(DetailAST methodDef) {
        return hasJavadocCommentOnToken(methodDef, TokenTypes.MODIFIERS)
                || hasJavadocCommentOnToken(methodDef, TokenTypes.TYPE);
    }

    /**
     * Checks whether a token has a javadoc comment.
     *
     * @param methodDef method definition token.
     * @param tokenType token type.
     * @return true if a token has a javadoc comment.
     */
    private static boolean hasJavadocCommentOnToken(DetailAST methodDef, int tokenType) {
        final DetailAST token = methodDef.findFirstToken(tokenType);
        return branchContainsJavadocComment(token);
    }

    /**
     * Checks whether a javadoc comment exists under the token.
     *
     * @param token tree token.
     * @return true if a javadoc comment exists under the token.
     */
    private static boolean branchContainsJavadocComment(DetailAST token) {
        boolean result = false;
        DetailAST curNode = token;
        while (curNode != null) {
            if (curNode.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtil.isJavadocComment(curNode)) {
                result = true;
                break;
            }

            DetailAST toVisit = curNode.getFirstChild();
            while (toVisit == null) {
                if (curNode == token) {
                    break;
                }

                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }
            curNode = toVisit;
        }

        return result;
    }

    /**
     * Checks whether a methods is native.
     *
     * @param ast method definition token.
     * @return true if a methods is native.
     */
    private static boolean isNativeMethod(DetailAST ast) {
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        return mods.findFirstToken(TokenTypes.LITERAL_NATIVE) != null;
    }

    /**
     * Checks whether a method has only comments in the body (has an empty implementation).
     * Method is OK if its implementation is empty.
     *
     * @param ast method definition token.
     * @return true if a method has only comments in the body.
     */
    private static boolean hasEmptyImplementation(DetailAST ast) {
        boolean hasEmptyBody = true;
        final DetailAST methodImplOpenBrace = ast.findFirstToken(TokenTypes.SLIST);
        final DetailAST methodImplCloseBrace = methodImplOpenBrace.getLastChild();
        final Predicate<DetailAST> predicate = currentNode -> {
            return currentNode != methodImplCloseBrace
                && !TokenUtil.isCommentType(currentNode.getType());
        };
        final Optional<DetailAST> methodBody =
            TokenUtil.findFirstTokenByPredicate(methodImplOpenBrace, predicate);
        if (methodBody.isPresent()) {
            hasEmptyBody = false;
        }
        return hasEmptyBody;
    }

    /**
     * Checks whether a method can be overridden.
     * Method can be overridden if it is not private, abstract, final or static.
     * Note that the check has nothing to do for interfaces.
     *
     * @param methodDef method definition token.
     * @return true if a method can be overridden in a subclass.
     */
    private static boolean canBeOverridden(DetailAST methodDef) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        return ScopeUtil.getSurroundingScope(methodDef).isIn(Scope.PROTECTED)
            && !ScopeUtil.isInInterfaceOrAnnotationBlock(methodDef)
            && modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null
            && modifiers.findFirstToken(TokenTypes.ABSTRACT) == null
            && modifiers.findFirstToken(TokenTypes.FINAL) == null
            && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null;
    }

    /**
     * Checks whether a method has any of ignored annotations.
     *
     * @param methodDef method definition token.
     * @param annotations a set of ignored annotations.
     * @return true if a method has any of ignored annotations.
     */
    private static boolean hasIgnoredAnnotation(DetailAST methodDef, Set<String> annotations) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        final Optional<DetailAST> annotation = TokenUtil.findFirstTokenByPredicate(modifiers,
            currentToken -> {
                return currentToken.getType() == TokenTypes.ANNOTATION
                    && annotations.contains(getAnnotationName(currentToken));
            });
        return annotation.isPresent();
    }

    /**
     * Gets the name of the annotation.
     *
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
     *
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
     *
     * @param classDef class definition token.
     * @return true if the containing class can be subclassed.
     */
    private static boolean canBeSubclassed(DetailAST classDef) {
        final DetailAST modifiers = classDef.findFirstToken(TokenTypes.MODIFIERS);
        return classDef.getType() != TokenTypes.ENUM_DEF
            && modifiers.findFirstToken(TokenTypes.FINAL) == null
            && hasDefaultOrExplicitNonPrivateCtor(classDef);
    }

    /**
     * Checks whether a class has default or explicit non-private constructor.
     *
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
                if (ctorMods.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null) {
                    hasExplicitNonPrivateCtor = true;
                    break;
                }
            }
            candidate = candidate.getNextSibling();
        }

        return hasDefaultConstructor || hasExplicitNonPrivateCtor;
    }

}
