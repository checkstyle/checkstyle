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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.AbstractFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.AnonymousClassFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.BlockFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.CatchFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.ClassFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.ConstructorFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.ForFrame;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.FrameType;
import com.puppycrawl.tools.checkstyle.utils.FrameUtil.MethodFrame;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that references to instance variables and methods of the present
 * object are explicitly of the form "this.varName" or "this.methodName(args)"
 * and that those references don't rely on the default behavior when "this." is absent.
 * </p>
 * <p>Warning: the Check is very controversial if 'validateOnlyOverlapping' option is set to 'false'
 * and not that actual nowadays.</p>
 * <p>Rationale:</p>
 * <ol>
 *   <li>
 *     The same notation/habit for C++ and Java (C++ have global methods, so having
 *     &quot;this.&quot; do make sense in it to distinguish call of method of class
 *     instead of global).
 *   </li>
 *   <li>
 *     Non-IDE development (ease of refactoring, some clearness to distinguish
 *     static and non-static methods).
 *   </li>
 * </ol>
 * <p>Limitations: Nothing is currently done about static variables
 * or catch-blocks.  Static methods invoked on a class name seem to be OK;
 * both the class name and the method name have a DOT parent.
 * Non-static methods invoked on either this or a variable name seem to be
 * OK, likewise.
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
 * <li>
 * Property {@code validateOnlyOverlapping} - Control whether to check only
 * overlapping by variables or arguments.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;/&gt;
 * </pre>
 * <p>
 * To configure to check the {@code this} qualifier for fields only:
 * </p>
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;&gt;
 *   &lt;property name=&quot;checkMethods&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Examples of how the check works if validateOnlyOverlapping option is set to true:
 * </p>
 * <pre>
 * public static class A {
 *   private int field1;
 *   private int field2;
 *
 *   public A(int field1) {
 *     // Overlapping by constructor argument.
 *     field1 = field1; // violation: Reference to instance variable "field1" needs "this".
 *     field2 = 0;
 *   }
 *
 *   void foo3() {
 *     String field1 = "values";
 *     // Overlapping by local variable.
 *     field1 = field1; // violation:  Reference to instance variable "field1" needs "this".
 *   }
 * }
 *
 * public static class B {
 *   private int field;
 *
 *   public A(int f) {
 *     field = f;
 *   }
 *
 *   String addSuffixToField(String field) {
 *     // Overlapping by method argument. Equal to "return field = field + "suffix";"
 *     return field += "suffix"; // violation: Reference to instance variable "field" needs "this".
 *   }
 * }
 * </pre>
 * <p>
 * Please, be aware of the following logic, which is implemented in the check:
 * </p>
 * <p>
 * 1) If you arrange 'this' in your code on your own, the check will not raise violation for
 * variables which use 'this' to reference a class field, for example:
 * </p>
 * <pre>
 * public class C {
 *   private int scale;
 *   private int x;
 *   public void foo(int scale) {
 *     scale = this.scale; // no violation
 *     if (scale &gt; 0) {
 *       scale = -scale; // no violation
 *     }
 *     x *= scale;
 *   }
 * }
 * </pre>
 * <p>
 * 2) If method parameter is returned from the method, the check will not raise violation for
 * returned variable/parameter, for example:
 * </p>
 * <pre>
 * public class D {
 *   private String prefix;
 *   public String modifyPrefix(String prefix) {
 *     prefix = "^" + prefix + "$" // no violation (modification of parameter)
 *     return prefix; // modified method parameter is returned from the method
 *   }
 * }
 * </pre>
 * <p>
 * Examples of how the check works if validateOnlyOverlapping option is set to false:
 * </p>
 * <pre>
 * public static class A {
 *   private int field1;
 *   private int field2;
 *
 *   public A(int field1) {
 *     field1 = field1; // violation: Reference to instance variable "field1" needs "this".
 *     field2 = 0; // violation: Reference to instance variable "field2" needs "this".
 *     String field2;
 *     field2 = "0"; // No violation. Local var allowed
 *   }
 *
 *   void foo3() {
 *     String field1 = "values";
 *     field1 = field1; // violation:  Reference to instance variable "field1" needs "this".
 *   }
 * }
 *
 * public static class B {
 *   private int field;
 *
 *   public A(int f) {
 *     field = f; // violation:  Reference to instance variable "field" needs "this".
 *   }
 *
 *   String addSuffixToField(String field) {
 *     return field += "suffix"; // violation: Reference to instance variable "field" needs "this".
 *   }
 * }
 *
 * // If the variable is locally defined, there won't be a violation provided the variable
 * // doesn't overlap.
 * class C {
 *   private String s1 = "foo1";
 *   String s2 = "foo2";
 *
 *   C() {
 *     s1 = "bar1"; // Violation. Reference to instance variable 's1' needs "this.".
 *     String s2;
 *     s2 = "bar2"; // No violation. Local var allowed.
 *     s2 += s2; // Violation. Overlapping. Reference to instance variable 's2' needs "this.".
 *   }
 * }
 * </pre>
 *
 * @since 3.4
 */
// -@cs[ClassDataAbstractionCoupling] This check requires to work with and identify many frames.
@FileStatefulCheck
public class RequireThisCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_METHOD = "require.this.method";
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_VARIABLE = "require.this.variable";

    /** Frame for the currently processed AST. */
    private final Deque<AbstractFrame> current = new ArrayDeque<>();

    /** Tree of all the parsed frames. */
    private Map<DetailAST, AbstractFrame> frames;

    /** Control whether to check references to fields. */
    private boolean checkFields = true;
    /** Control whether to check references to methods. */
    private boolean checkMethods = true;
    /** Control whether to check only overlapping by variables or arguments. */
    private boolean validateOnlyOverlapping = true;

    /**
     * Setter to control whether to check references to fields.
     * @param checkFields should we check fields usage or not.
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

    /**
     * Setter to control whether to check only overlapping by variables or arguments.
     * @param validateOnlyOverlapping should we check only overlapping by variables or arguments.
     */
    public void setValidateOnlyOverlapping(boolean validateOnlyOverlapping) {
        this.validateOnlyOverlapping = validateOnlyOverlapping;
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
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        frames = new HashMap<>();
        current.clear();

        final Deque<AbstractFrame> frameStack = new LinkedList<>();
        DetailAST curNode = rootAST;
        while (curNode != null) {
            collectDeclarations(frameStack, curNode);
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                endCollectingDeclarations(frameStack, curNode);
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }
            curNode = toVisit;
        }
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.IDENT:
                processIdent(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.SLIST:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_FOR:
                current.push(frames.get(ast));
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
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_FOR:
                current.pop();
                break;
            default:
                // do nothing
        }
    }

    /**
     * Checks if a given IDENT is method call or field name which
     * requires explicit {@code this} qualifier.
     * @param ast IDENT to check.
     */
    private void processIdent(DetailAST ast) {
        int parentType = ast.getParent().getType();
        if (parentType == TokenTypes.EXPR
                && ast.getParent().getParent().getParent().getType()
                    == TokenTypes.ANNOTATION_FIELD_DEF) {
            parentType = TokenTypes.ANNOTATION_FIELD_DEF;
        }
        switch (parentType) {
            case TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR:
            case TokenTypes.ANNOTATION:
            case TokenTypes.ANNOTATION_FIELD_DEF:
                // no need to check annotations content
                break;
            case TokenTypes.METHOD_CALL:
                if (checkMethods) {
                    final AbstractFrame frame = getMethodWithoutThis(ast);
                    if (frame != null) {
                        logViolation(MSG_METHOD, ast, frame);
                    }
                }
                break;
            default:
                if (checkFields) {
                    final AbstractFrame frame = getFieldWithoutThis(ast, parentType);
                    if (frame != null) {
                        logViolation(MSG_VARIABLE, ast, frame);
                    }
                }
                break;
        }
    }

    /**
     * Helper method to log a LocalizedMessage.
     * @param ast a node to get line id column numbers associated with the message.
     * @param msgKey key to locale message format.
     * @param frame the class frame where the violation is found.
     */
    private void logViolation(String msgKey, DetailAST ast, AbstractFrame frame) {
        if (frame.getFrameName().equals(FrameUtil.getNearestClassFrame(current.peek())
                .getFrameName())) {
            log(ast, msgKey, ast.getText(), "");
        }
        else if (!(frame instanceof FrameUtil.AnonymousClassFrame)) {
            log(ast, msgKey, ast.getText(), frame.getFrameName() + '.');
        }
    }

    /**
     * Returns the frame where the field is declared, if the given field is used without
     * 'this', and null otherwise.
     * @param ast field definition ast token.
     * @param parentType type of the parent.
     * @return the frame where the field is declared, if the given field is used without
     *         'this' and null otherwise.
     */
    private AbstractFrame getFieldWithoutThis(DetailAST ast, int parentType) {
        final boolean importOrPackage = ScopeUtil.getSurroundingScope(ast) == null;
        final boolean typeName = parentType == TokenTypes.TYPE
                || parentType == TokenTypes.LITERAL_NEW;
        AbstractFrame frame = null;

        if (!importOrPackage
                && !typeName
                && !FrameUtil.isDeclarationToken(parentType)
                && !FrameUtil.isLambdaParameter(ast)) {
            final AbstractFrame fieldFrame = FrameUtil.findClassFrame(ast, current.peek(),
                    false);

            if (fieldFrame != null && ((ClassFrame) fieldFrame).hasInstanceMember(ast)) {
                frame = getClassFrameWhereViolationIsFound(ast);
            }
        }
        return frame;
    }

    /**
     * Parses the next AST for declarations.
     * @param frameStack stack containing the FrameTree being built.
     * @param ast        AST to parse.
     */
    // -@cs[JavaNCSS] This method is a big switch and is too hard to remove.
    protected void collectDeclarations(Deque<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame = frameStack.peek();
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF:
                FrameUtil.collectVariableDeclarations(ast, frame);
                break;
            case TokenTypes.PARAMETER_DEF:
                if (!CheckUtil.isReceiverParameter(ast)
                        && !FrameUtil.isLambdaParameter(ast)
                        && ast.getParent().getType() != TokenTypes.LITERAL_CATCH) {
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
                frameStack.addFirst(new BlockFrame(frame, ast));
                break;
            case TokenTypes.METHOD_DEF:
                final DetailAST methodFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
                if (mods.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
                    ((ClassFrame) frame).addInstanceMethod(methodFrameNameIdent);
                }
                else {
                    ((ClassFrame) frame).addStaticMethod(methodFrameNameIdent);
                }
                frameStack.addFirst(new MethodFrame(frame, methodFrameNameIdent));
                break;
            case TokenTypes.CTOR_DEF:
                final DetailAST ctorFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ConstructorFrame(frame, ctorFrameNameIdent));
                break;
            case TokenTypes.ENUM_CONSTANT_DEF:
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                ((ClassFrame) frame).addStaticMember(ident);
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
                if (FrameUtil.isAnonymousClassDef(ast)) {
                    frameStack.addFirst(new AnonymousClassFrame(frame,
                            ast.getFirstChild().toString()));
                }
                break;
            default:
                // do nothing
        }
    }

    /**
     * Ends parsing of the AST for declarations.
     * @param frameStack Stack containing the FrameTree being built.
     * @param ast        AST that was parsed.
     */
    protected void endCollectingDeclarations(Queue<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame;
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.SLIST:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_FOR:
                frame = frameStack.poll();
                frames.put(ast, frame);
                break;
            case TokenTypes.LITERAL_NEW:
                if (FrameUtil.isAnonymousClassDef(ast)) {
                    frame = frameStack.poll();
                    frames.put(ast, frame);
                }
                break;
            default:
                // do nothing
        }
    }

    /**
     * Returns the class frame where violation is found (where the field is used without 'this')
     * or null otherwise.
     * @param ast IDENT ast to check.
     * @return the class frame where violation is found or null otherwise.
     */
    // -@cs[CyclomaticComplexity] Method already invokes too many methods that fully explain
    // a logic, additional abstraction will not make logic/algorithm more readable.
    private AbstractFrame getClassFrameWhereViolationIsFound(DetailAST ast) {
        AbstractFrame frameWhereViolationIsFound = null;
        final AbstractFrame variableDeclarationFrame = FrameUtil.findFrame(ast, current.peek(),
                false);
        final FrameType variableDeclarationFrameType = variableDeclarationFrame.getType();
        final DetailAST prevSibling = ast.getPreviousSibling();
        if (variableDeclarationFrameType == FrameType.CLASS_FRAME
                && !validateOnlyOverlapping
                && (prevSibling == null || !FrameUtil.isInExpression(ast))
                && canBeReferencedFromStaticContext(ast)) {
            frameWhereViolationIsFound = variableDeclarationFrame;
        }
        else if (variableDeclarationFrameType == FrameType.METHOD_FRAME) {
            if (isOverlappingByArgument(ast)) {
                if (!isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                        && !isReturnedVariable(variableDeclarationFrame, ast)
                        && canBeReferencedFromStaticContext(ast)
                        && canAssignValueToClassField(ast)) {
                    frameWhereViolationIsFound = FrameUtil.findFrame(ast, current.peek(), true);
                }
            }
            else if (!validateOnlyOverlapping
                     && prevSibling == null
                     && FrameUtil.isAssignToken(ast.getParent().getType())
                     && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                     && canBeReferencedFromStaticContext(ast)
                     && canAssignValueToClassField(ast)) {
                frameWhereViolationIsFound = FrameUtil.findFrame(ast, current.peek(), true);
            }
        }
        else if (variableDeclarationFrameType == FrameType.CTOR_FRAME
                 && isOverlappingByArgument(ast)
                 && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)) {
            frameWhereViolationIsFound = FrameUtil.findFrame(ast, current.peek(), true);
        }
        else if (variableDeclarationFrameType == FrameType.BLOCK_FRAME
                    && isOverlappingByLocalVariable(ast)
                    && canAssignValueToClassField(ast)
                    && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                    && !isReturnedVariable(variableDeclarationFrame, ast)
                    && canBeReferencedFromStaticContext(ast)) {
            frameWhereViolationIsFound = FrameUtil.findFrame(ast, current.peek(), true);
        }
        return frameWhereViolationIsFound;
    }

    /**
     * Checks whether user arranges 'this' for variable in method, constructor, or block on his own.
     * @param currentFrame current frame.
     * @param ident ident token.
     * @return true if user arranges 'this' for variable in method, constructor,
     *         or block on his own.
     */
    private static boolean isUserDefinedArrangementOfThis(AbstractFrame currentFrame,
                                                          DetailAST ident) {
        final DetailAST blockFrameNameIdent = currentFrame.getFrameNameIdent();
        final DetailAST definitionToken = blockFrameNameIdent.getParent();
        final DetailAST blockStartToken = definitionToken.findFirstToken(TokenTypes.SLIST);
        final DetailAST blockEndToken = getBlockEndToken(blockFrameNameIdent, blockStartToken);

        boolean userDefinedArrangementOfThis = false;

        final Set<DetailAST> variableUsagesInsideBlock =
            getAllTokensWhichAreEqualToCurrent(definitionToken, ident,
                blockEndToken.getLineNo());

        for (DetailAST variableUsage : variableUsagesInsideBlock) {
            final DetailAST prevSibling = variableUsage.getPreviousSibling();
            if (prevSibling != null
                    && prevSibling.getType() == TokenTypes.LITERAL_THIS) {
                userDefinedArrangementOfThis = true;
                break;
            }
        }
        return userDefinedArrangementOfThis;
    }

    /**
     * Returns the token which ends the code block.
     * @param blockNameIdent block name identifier.
     * @param blockStartToken token which starts the block.
     * @return the token which ends the code block.
     */
    private static DetailAST getBlockEndToken(DetailAST blockNameIdent, DetailAST blockStartToken) {
        DetailAST blockEndToken = null;
        final DetailAST blockNameIdentParent = blockNameIdent.getParent();
        if (blockNameIdentParent.getType() == TokenTypes.CASE_GROUP) {
            blockEndToken = blockNameIdentParent.getNextSibling();
        }
        else {
            final Set<DetailAST> rcurlyTokens = getAllTokensOfType(blockNameIdent,
                    TokenTypes.RCURLY);
            for (DetailAST currentRcurly : rcurlyTokens) {
                final DetailAST parent = currentRcurly.getParent();
                if (TokenUtil.areOnSameLine(blockStartToken, parent)) {
                    blockEndToken = currentRcurly;
                }
            }
        }
        return blockEndToken;
    }

    /**
     * Checks whether the current variable is returned from the method.
     * @param currentFrame current frame.
     * @param ident variable ident token.
     * @return true if the current variable is returned from the method.
     */
    private static boolean isReturnedVariable(AbstractFrame currentFrame, DetailAST ident) {
        final DetailAST blockFrameNameIdent = currentFrame.getFrameNameIdent();
        final DetailAST definitionToken = blockFrameNameIdent.getParent();
        final DetailAST blockStartToken = definitionToken.findFirstToken(TokenTypes.SLIST);
        final DetailAST blockEndToken = getBlockEndToken(blockFrameNameIdent, blockStartToken);

        final Set<DetailAST> returnsInsideBlock = getAllTokensOfType(definitionToken,
            TokenTypes.LITERAL_RETURN, blockEndToken.getLineNo());

        boolean returnedVariable = false;
        for (DetailAST returnToken : returnsInsideBlock) {
            returnedVariable = isAstInside(returnToken, ident);
            if (returnedVariable) {
                break;
            }
        }
        return returnedVariable;
    }

    /**
     * Checks if the given {@code ast} is equal to the {@code tree} or a child of it.
     * @param tree The tree to search.
     * @param ast The AST to look for.
     * @return {@code true} if the {@code ast} was found.
     */
    private static boolean isAstInside(DetailAST tree, DetailAST ast) {
        boolean result = false;

        if (FrameUtil.isAstSimilar(tree, ast)) {
            result = true;
        }
        else {
            for (DetailAST child = tree.getFirstChild(); child != null
                    && !result; child = child.getNextSibling()) {
                result = isAstInside(child, ast);
            }
        }

        return result;
    }

    /**
     * Checks whether a field can be referenced from a static context.
     * @param ident ident token.
     * @return true if field can be referenced from a static context.
     */
    private boolean canBeReferencedFromStaticContext(DetailAST ident) {
        AbstractFrame variableDeclarationFrame = FrameUtil.findFrame(ident, current.peek(),
                false);
        boolean staticInitializationBlock = false;
        while (variableDeclarationFrame.getType() == FrameType.BLOCK_FRAME
                || variableDeclarationFrame.getType() == FrameType.FOR_FRAME) {
            final DetailAST blockFrameNameIdent = variableDeclarationFrame.getFrameNameIdent();
            final DetailAST definitionToken = blockFrameNameIdent.getParent();
            if (definitionToken.getType() == TokenTypes.STATIC_INIT) {
                staticInitializationBlock = true;
                break;
            }
            variableDeclarationFrame = variableDeclarationFrame.getParent();
        }

        boolean staticContext = false;
        if (staticInitializationBlock) {
            staticContext = true;
        }
        else {
            if (variableDeclarationFrame.getType() == FrameType.CLASS_FRAME) {
                final DetailAST codeBlockDefinition = getCodeBlockDefinitionToken(ident);
                if (codeBlockDefinition != null) {
                    final DetailAST modifiers = codeBlockDefinition.getFirstChild();
                    staticContext = codeBlockDefinition.getType() == TokenTypes.STATIC_INIT
                        || modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
                }
            }
            else {
                final DetailAST frameNameIdent = variableDeclarationFrame.getFrameNameIdent();
                final DetailAST definitionToken = frameNameIdent.getParent();
                staticContext = definitionToken.findFirstToken(TokenTypes.MODIFIERS)
                        .findFirstToken(TokenTypes.LITERAL_STATIC) != null;
            }
        }
        return !staticContext;
    }

    /**
     * Returns code block definition token for current identifier.
     * @param ident ident token.
     * @return code block definition token for current identifier or null if code block
     *         definition was not found.
     */
    private static DetailAST getCodeBlockDefinitionToken(DetailAST ident) {
        DetailAST parent = ident.getParent();
        while (parent != null
               && parent.getType() != TokenTypes.METHOD_DEF
               && parent.getType() != TokenTypes.CTOR_DEF
               && parent.getType() != TokenTypes.STATIC_INIT) {
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * Checks whether a value can be assigned to a field.
     * A value can be assigned to a final field only in constructor block. If there is a method
     * block, value assignment can be performed only to non final field.
     * @param ast an identifier token.
     * @return true if a value can be assigned to a field.
     */
    private boolean canAssignValueToClassField(DetailAST ast) {
        final AbstractFrame fieldUsageFrame = FrameUtil.findFrame(ast, current.peek(), false);
        final boolean fieldUsageInConstructor = isInsideConstructorFrame(fieldUsageFrame);

        final AbstractFrame declarationFrame = FrameUtil.findFrame(ast, current.peek(), true);
        final boolean finalField = ((ClassFrame) declarationFrame).hasFinalField(ast);

        return fieldUsageInConstructor || !finalField;
    }

    /**
     * Checks whether a field usage frame is inside constructor frame.
     * @param frame frame, where field is used.
     * @return true if the field usage frame is inside constructor frame.
     */
    private static boolean isInsideConstructorFrame(AbstractFrame frame) {
        boolean assignmentInConstructor = false;
        AbstractFrame fieldUsageFrame = frame;
        if (fieldUsageFrame.getType() == FrameType.BLOCK_FRAME) {
            while (fieldUsageFrame.getType() == FrameType.BLOCK_FRAME) {
                fieldUsageFrame = fieldUsageFrame.getParent();
            }
            if (fieldUsageFrame.getType() == FrameType.CTOR_FRAME) {
                assignmentInConstructor = true;
            }
        }
        return assignmentInConstructor;
    }

    /**
     * Checks whether an overlapping by method or constructor argument takes place.
     * @param ast an identifier.
     * @return true if an overlapping by method or constructor argument takes place.
     */
    private boolean isOverlappingByArgument(DetailAST ast) {
        boolean overlapping = false;
        final DetailAST parent = ast.getParent();
        final DetailAST sibling = ast.getNextSibling();
        if (sibling != null && FrameUtil.isAssignToken(parent.getType())) {
            if (FrameUtil.isCompoundAssignToken(parent.getType())) {
                overlapping = true;
            }
            else {
                final ClassFrame classFrame = (ClassFrame) FrameUtil.findFrame(ast,
                        current.peek(), true);
                final Set<DetailAST> exprIdents = getAllTokensOfType(sibling, TokenTypes.IDENT);
                overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
            }
        }
        return overlapping;
    }

    /**
     * Checks whether an overlapping by local variable takes place.
     * @param ast an identifier.
     * @return true if an overlapping by local variable takes place.
     */
    private boolean isOverlappingByLocalVariable(DetailAST ast) {
        boolean overlapping = false;
        final DetailAST parent = ast.getParent();
        final DetailAST sibling = ast.getNextSibling();
        if (sibling != null && FrameUtil.isAssignToken(parent.getType())) {
            final ClassFrame classFrame = (ClassFrame) FrameUtil.findFrame(ast, current.peek(),
                    true);
            final Set<DetailAST> exprIdents = getAllTokensOfType(sibling, TokenTypes.IDENT);
            overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
        }
        return overlapping;
    }

    /**
     * Collects all tokens of specific type starting with the current ast node.
     * @param ast ast node.
     * @param tokenType token type.
     * @return a set of all tokens of specific type starting with the current ast node.
     */
    private static Set<DetailAST> getAllTokensOfType(DetailAST ast, int tokenType) {
        DetailAST vertex = ast;
        final Set<DetailAST> result = new HashSet<>();
        final Deque<DetailAST> stack = new ArrayDeque<>();
        while (vertex != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                vertex = stack.pop();
            }
            while (vertex != null) {
                if (vertex.getType() == tokenType) {
                    result.add(vertex);
                }
                if (vertex.getNextSibling() != null) {
                    stack.push(vertex.getNextSibling());
                }
                vertex = vertex.getFirstChild();
            }
        }
        return result;
    }

    /**
     * Collects all tokens of specific type starting with the current ast node and which line
     * number is lower or equal to the end line number.
     * @param ast ast node.
     * @param tokenType token type.
     * @param endLineNumber end line number.
     * @return a set of all tokens of specific type starting with the current ast node and which
     *         line number is lower or equal to the end line number.
     */
    private static Set<DetailAST> getAllTokensOfType(DetailAST ast, int tokenType,
                                                     int endLineNumber) {
        DetailAST vertex = ast;
        final Set<DetailAST> result = new HashSet<>();
        final Deque<DetailAST> stack = new ArrayDeque<>();
        while (vertex != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                vertex = stack.pop();
            }
            while (vertex != null) {
                if (tokenType == vertex.getType()
                    && vertex.getLineNo() <= endLineNumber) {
                    result.add(vertex);
                }
                if (vertex.getNextSibling() != null) {
                    stack.push(vertex.getNextSibling());
                }
                vertex = vertex.getFirstChild();
            }
        }
        return result;
    }

    /**
     * Collects all tokens which are equal to current token starting with the current ast node and
     * which line number is lower or equal to the end line number.
     * @param ast ast node.
     * @param token token.
     * @param endLineNumber end line number.
     * @return a set of tokens which are equal to current token starting with the current ast node
     *         and which line number is lower or equal to the end line number.
     */
    private static Set<DetailAST> getAllTokensWhichAreEqualToCurrent(DetailAST ast, DetailAST token,
                                                                     int endLineNumber) {
        DetailAST vertex = ast;
        final Set<DetailAST> result = new HashSet<>();
        final Deque<DetailAST> stack = new ArrayDeque<>();
        while (vertex != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                vertex = stack.pop();
            }
            while (vertex != null) {
                if (FrameUtil.isAstSimilar(token, vertex)
                        && vertex.getLineNo() <= endLineNumber) {
                    result.add(vertex);
                }
                if (vertex.getNextSibling() != null) {
                    stack.push(vertex.getNextSibling());
                }
                vertex = vertex.getFirstChild();
            }
        }
        return result;
    }

    /**
     * Returns the frame where the method is declared, if the given method is used without
     * 'this' and null otherwise.
     * @param ast the IDENT ast of the name to check.
     * @return the frame where the method is declared, if the given method is used without
     *         'this' and null otherwise.
     */
    private AbstractFrame getMethodWithoutThis(DetailAST ast) {
        AbstractFrame result = null;
        if (!validateOnlyOverlapping) {
            final AbstractFrame frame = FrameUtil.findFrame(ast, current.peek(), true);
            if (frame != null
                    && ((ClassFrame) frame).hasInstanceMethod(ast)
                    && !((ClassFrame) frame).hasStaticMethod(ast)) {
                result = frame;
            }
        }
        return result;
    }
}
