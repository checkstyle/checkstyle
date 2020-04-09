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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Deque;
import java.util.Queue;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that references to instance variables and methods of the present
 * object are not unnecessarily of the form "this.varName" or "this.methodName(args)".
 * </p>
 * <p>Limitations: Currently nothing can be done with references of the form
 * "ClassName.this.varName" or "ClassName.this.methodName(args)".
 * This form is mostly likely used for good reasons (disambiguation).
 * </p>
 * <ul>
 * <li>
 * Property {@code checkFields} - Control whether to check references to fields.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code checkMethods} - Control whether to check references to methods.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name=&quot;RedundantThis&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Foo {
 *   private int x;
 *
 *   public Foo() {
 *     this.x = x; // violation, 'this' is not necessary here
 *   }
 *
 *   public Foo(int x) {
 *     this.x = x; // OK, 'this' is necessary here
 *   }
 *
 *   public int methodOne() {
 *     methodThree();
 *     return this.x++; // violation, 'this' is not necessary here
 *   }
 *
 *   public int methodTwo(int x) {
 *     this.methodThree(); // violation, 'this' is not necessary here
 *     return this.x++; // OK, 'this' is necessary here
 *   }
 *
 *   public void methodThree() {}
 *
 *   public int methodFour() {
 *     int x = 5;
 *     return this.x + x ; // OK, 'this' is necessary here
 *   }
 *
 *   private static int y;
 *
 *   public void methodFive() {
 *     this.y = 0; // violation, 'this' is not necessary here
 *   }
 * }
 * </pre>
 *
 * <p>
 * To configure to check the {@code this} qualifier for fields only:
 * </p>
 * <pre>
 * &lt;module name="RedundantThis"&gt;
 *   &lt;property name=&quot;checkMethods&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Foo {
 *   private int x;
 *
 *   public Foo() {
 *     this.x++; // violation, 'this' is not necessary here
 *   }
 *
 *   public int methodOne(int x) {
 *     this.methodTwo(); // OK, methods are not checked
 *     return this.x++; // OK, 'this' is necessary here
 *   }
 *
 *   public void methodTwo() {}
 * }
 * </pre>
 * <p>
 * To configure to check the {@code this} qualifier for methods only:
 * </p>
 * <pre>
 * &lt;module name="RedundantThis"&gt;
 *   &lt;property name=&quot;checkFields&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Foo {
 *   private int x;
 *
 *   public Foo() {
 *     this.x++; // OK, fields are not checked
 *   }
 *
 *   public int methodOne() {
 *     methodThree();
 *     return this.x++; // OK, fields are not checked
 *   }
 *
 *   public int methodTwo() {
 *     this.methodThree(); // violation, 'this' is not necessary here
 *   }
 *
 *   public int methodThree() {}
 * }
 * </pre>
 *
 * @since 8.32
 */
// -@cs[ClassDataAbstractionCoupling] This check requires to work with and identify many frames.
public class RedundantThisCheck extends AbstractFrameCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_METHOD = "redundant.this.method";
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_VARIABLE = "redundant.this.variable";

    /** Control whether to check references to fields. */
    private boolean checkFields = true;

    /** Control whether to check references to methods. */
    private boolean checkMethods = true;

    /**
     * Setter to control whether to check references to fields.
     * @param checkFields should we check methods usage or not.
     */
    public void setCheckFields(boolean checkFields) {
        this.checkFields = checkFields;
    }

    /**
     * Setter to control whether to check references to methods.
     * @param checkMethods should we check methods usage or not.
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
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
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_THIS,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_THIS:
                processLiteralThis(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.SLIST:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_FOR:
                pushFrame(ast);
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.SLIST:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_FOR:
                popFrame();
                break;
            default:
                // do nothing
        }
    }

    /**
     * Checks if a given LITERAL_THIS is used for method call or field name which
     * does not require explicit {@code this} qualifier.
     * @param ast LITERAL_THIS to check.
     */
    private void processLiteralThis(DetailAST ast) {
        final DetailAST nextSibling = ast.getNextSibling();
        if (nextSibling != null && nextSibling.getType() == TokenTypes.IDENT) {
            final int parentType = ast.getParent().getParent().getType();

            if (parentType == TokenTypes.METHOD_CALL) {
                if (checkMethods) {
                    log(ast, MSG_METHOD, nextSibling.getText());
                }
            }
            else if (checkFields) {
                final AbstractFrame classFrame = getNearestClassFrame();
                final AbstractFrame shadowingFrame = findFrame(nextSibling, false);

                if (classFrame.equals(shadowingFrame)) {
                    log(ast, MSG_VARIABLE, nextSibling.getText());
                }
            }
        }
    }

    // -@cs[JavaNCSS] This method is a big switch and is too hard to remove.
    @Override
    protected void collectDeclarations(Deque<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame = frameStack.peek();
        switch (ast.getType()) {
            case TokenTypes.RESOURCE:
            case TokenTypes.VARIABLE_DEF:
                collectVariableDeclarations(ast, frame);
                break;
            case TokenTypes.PARAMETER_DEF:
                if (ast.getParent().getType() != TokenTypes.LITERAL_CATCH) {
                    final DetailAST parameterIdent = ast.findFirstToken(TokenTypes.IDENT);
                    frame.addIdent(parameterIdent);
                }
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
                final DetailAST classFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ClassFrame(frame, classFrameNameIdent));
                break;
            case TokenTypes.SLIST:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.LITERAL_TRY:
                frameStack.addFirst(new BlockFrame(frame, ast));
                break;
            case TokenTypes.METHOD_DEF:
                final DetailAST methodFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new MethodFrame(frame, methodFrameNameIdent));
                break;
            case TokenTypes.CTOR_DEF:
                final DetailAST ctorFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ConstructorFrame(frame, ctorFrameNameIdent));
                break;
            case TokenTypes.LITERAL_CATCH:
                final AbstractFrame catchFrame = new CatchFrame(frame, ast);
                catchFrame.addIdent(ast.findFirstToken(TokenTypes.PARAMETER_DEF).findFirstToken(
                        TokenTypes.IDENT));
                frameStack.addFirst(catchFrame);
                break;
            case TokenTypes.LITERAL_FOR:
                final AbstractFrame forFrame = new ForFrame(frame, ast);
                frameStack.addFirst(forFrame);
                break;
            case TokenTypes.LITERAL_NEW:
                if (isAnonymousClassDef(ast)) {
                    frameStack.addFirst(new AnonymousClassFrame(frame,
                            ast.getFirstChild().toString()));
                }
                break;
            default:
                // do nothing
        }
    }

    @Override
    protected void endCollectingDeclarations(Queue<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame;
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.SLIST:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_FOR:
                frame = frameStack.poll();
                register(ast, frame);
                break;
            case TokenTypes.LITERAL_NEW:
                if (isAnonymousClassDef(ast)) {
                    frame = frameStack.poll();
                    register(ast, frame);
                }
                break;
            default:
                // do nothing
        }
    }
}
