///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.BitSet;
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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that references to instance variables and methods of the present
 * object are explicitly of the form "this.varName" or "this.methodName(args)"
 * and that those references don't rely on the default behavior when "this." is absent.
 * </div>
 *
 * <p>Warning: the Check is very controversial if 'validateOnlyOverlapping' option is set to 'false'
 * and not that actual nowadays.</p>
 *
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
 *
 * <p>Limitations: Nothing is currently done about static variables
 * or catch-blocks.  Static methods invoked on a class name seem to be OK;
 * both the class name and the method name have a DOT parent.
 * Non-static methods invoked on either this or a variable name seem to be
 * OK, likewise.
 * </p>
 * <ul>
 * <li>
 * Property {@code checkFields} - Control whether to check references to fields.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code checkMethods} - Control whether to check references to methods.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code validateOnlyOverlapping} - Control whether to check only
 * overlapping by variables or arguments.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code require.this.method}
 * </li>
 * <li>
 * {@code require.this.variable}
 * </li>
 * </ul>
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

    /** Set of all declaration tokens. */
    private static final BitSet DECLARATION_TOKENS = TokenUtil.asBitSet(
        TokenTypes.VARIABLE_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.CLASS_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.ANNOTATION_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.PARAMETER_DEF,
        TokenTypes.TYPE_ARGUMENT,
        TokenTypes.RECORD_DEF,
        TokenTypes.RECORD_COMPONENT_DEF,
        TokenTypes.RESOURCE
    );
    /** Set of all assign tokens. */
    private static final BitSet ASSIGN_TOKENS = TokenUtil.asBitSet(
        TokenTypes.ASSIGN,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.SL_ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BXOR_ASSIGN
    );
    /** Set of all compound assign tokens. */
    private static final BitSet COMPOUND_ASSIGN_TOKENS = TokenUtil.asBitSet(
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.SL_ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BXOR_ASSIGN
    );

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
     *
     * @param checkFields should we check fields usage or not
     * @since 3.4
     */
    public void setCheckFields(boolean checkFields) {
        this.checkFields = checkFields;
    }

    /**
     * Setter to control whether to check references to methods.
     *
     * @param checkMethods should we check methods usage or not
     * @since 3.4
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
    }

    /**
     * Setter to control whether to check only overlapping by variables or arguments.
     *
     * @param validateOnlyOverlapping should we check only overlapping by variables or arguments
     * @since 6.17
     */
    public void setValidateOnlyOverlapping(boolean validateOnlyOverlapping) {
        this.validateOnlyOverlapping = validateOnlyOverlapping;
    }

    @Override
    public int[] getDefaultTokens() {
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_TRY,
            TokenTypes.RESOURCE,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
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
                curNode = curNode.getParent();
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
            case TokenTypes.RECORD_DEF:
                current.push(frames.get(ast));
                break;
            case TokenTypes.LITERAL_TRY:
                if (ast.getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                    current.push(frames.get(ast));
                }
                break;
            default:
                break;
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
            case TokenTypes.RECORD_DEF:
                current.pop();
                break;
            case TokenTypes.LITERAL_TRY:
                if (current.peek().getType() == FrameType.TRY_WITH_RESOURCES_FRAME) {
                    current.pop();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Checks if a given IDENT is method call or field name which
     * requires explicit {@code this} qualifier.
     *
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
                    final boolean canUseThis = !isInCompactConstructor(ast);
                    if (frame != null && canUseThis) {
                        logViolation(MSG_VARIABLE, ast, frame);
                    }
                }
                break;
        }
    }

    /**
     * Helper method to log a Violation.
     *
     * @param msgKey key to locale message format.
     * @param ast a node to get line id column numbers associated with the message.
     * @param frame the class frame where the violation is found.
     */
    private void logViolation(String msgKey, DetailAST ast, AbstractFrame frame) {
        if (frame.getFrameName().equals(getNearestClassFrameName())) {
            log(ast, msgKey, ast.getText(), "");
        }
        else if (!(frame instanceof AnonymousClassFrame)) {
            log(ast, msgKey, ast.getText(), frame.getFrameName() + '.');
        }
    }

    /**
     * Returns the frame where the field is declared, if the given field is used without
     * 'this', and null otherwise.
     *
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
                && !isDeclarationToken(parentType)
                && !isLambdaParameter(ast)) {
            final AbstractFrame fieldFrame = findClassFrame(ast, false);

            if (fieldFrame != null && ((ClassFrame) fieldFrame).hasInstanceMember(ast)) {
                frame = getClassFrameWhereViolationIsFound(ast);
            }
        }
        return frame;
    }

    /**
     * Return whether ast is in a COMPACT_CTOR_DEF.
     *
     * @param ast The token to check
     * @return true if ast is in a COMPACT_CTOR_DEF, false otherwise
     */
    private static boolean isInCompactConstructor(DetailAST ast) {
        boolean isInCompactCtor = false;
        DetailAST parent = ast;
        while (parent != null) {
            if (parent.getType() == TokenTypes.COMPACT_CTOR_DEF) {
                isInCompactCtor = true;
                break;
            }
            parent = parent.getParent();
        }
        return isInCompactCtor;
    }

    /**
     * Parses the next AST for declarations.
     *
     * @param frameStack stack containing the FrameTree being built.
     * @param ast AST to parse.
     */
    // -@cs[JavaNCSS] This method is a big switch and is too hard to remove.
    private static void collectDeclarations(Deque<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame = frameStack.peek();
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF:
                collectVariableDeclarations(ast, frame);
                break;
            case TokenTypes.RECORD_COMPONENT_DEF:
                final DetailAST componentIdent = ast.findFirstToken(TokenTypes.IDENT);
                ((ClassFrame) frame).addInstanceMember(componentIdent);
                break;
            case TokenTypes.PARAMETER_DEF:
                if (!CheckUtil.isReceiverParameter(ast)
                        && !isLambdaParameter(ast)) {
                    final DetailAST parameterIdent = ast.findFirstToken(TokenTypes.IDENT);
                    frame.addIdent(parameterIdent);
                }
                break;
            case TokenTypes.RESOURCE:
                final DetailAST resourceIdent = ast.findFirstToken(TokenTypes.IDENT);
                if (resourceIdent != null) {
                    frame.addIdent(resourceIdent);
                }
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.RECORD_DEF:
                final DetailAST classFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ClassFrame(frame, classFrameNameIdent));
                break;
            case TokenTypes.SLIST:
                frameStack.addFirst(new BlockFrame(frame, ast));
                break;
            case TokenTypes.METHOD_DEF:
                collectMethodDeclarations(frameStack, ast, frame);
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.COMPACT_CTOR_DEF:
                final DetailAST ctorFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ConstructorFrame(frame, ctorFrameNameIdent));
                break;
            case TokenTypes.ENUM_CONSTANT_DEF:
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                ((ClassFrame) frame).addStaticMember(ident);
                break;
            case TokenTypes.LITERAL_CATCH:
                final AbstractFrame catchFrame = new CatchFrame(frame, ast);
                frameStack.addFirst(catchFrame);
                break;
            case TokenTypes.LITERAL_FOR:
                final AbstractFrame forFrame = new ForFrame(frame, ast);
                frameStack.addFirst(forFrame);
                break;
            case TokenTypes.LITERAL_NEW:
                if (isAnonymousClassDef(ast)) {
                    frameStack.addFirst(new AnonymousClassFrame(frame,
                            ast.toString()));
                }
                break;
            case TokenTypes.LITERAL_TRY:
                if (ast.getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                    frameStack.addFirst(new TryWithResourcesFrame(frame, ast));
                }
                break;
            default:
                // do nothing
        }
    }

    /**
     * Collects variable declarations.
     *
     * @param ast variable token.
     * @param frame current frame.
     */
    private static void collectVariableDeclarations(DetailAST ast, AbstractFrame frame) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (frame.getType() == FrameType.CLASS_FRAME) {
            final DetailAST mods =
                    ast.findFirstToken(TokenTypes.MODIFIERS);
            if (ScopeUtil.isInInterfaceBlock(ast)
                    || mods.findFirstToken(TokenTypes.LITERAL_STATIC) != null) {
                ((ClassFrame) frame).addStaticMember(ident);
            }
            else {
                ((ClassFrame) frame).addInstanceMember(ident);
            }
        }
        else {
            frame.addIdent(ident);
        }
    }

    /**
     * Collects {@code METHOD_DEF} declarations.
     *
     * @param frameStack stack containing the FrameTree being built.
     * @param ast AST to parse.
     * @param frame current frame.
     */
    private static void collectMethodDeclarations(Deque<AbstractFrame> frameStack,
                                                  DetailAST ast, AbstractFrame frame) {
        final DetailAST methodFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (mods.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
            ((ClassFrame) frame).addInstanceMethod(methodFrameNameIdent);
        }
        else {
            ((ClassFrame) frame).addStaticMethod(methodFrameNameIdent);
        }
        frameStack.addFirst(new MethodFrame(frame, methodFrameNameIdent));
    }

    /**
     * Ends parsing of the AST for declarations.
     *
     * @param frameStack Stack containing the FrameTree being built.
     * @param ast AST that was parsed.
     */
    private void endCollectingDeclarations(Queue<AbstractFrame> frameStack, DetailAST ast) {
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
            case TokenTypes.RECORD_DEF:
            case TokenTypes.COMPACT_CTOR_DEF:
                frames.put(ast, frameStack.poll());
                break;
            case TokenTypes.LITERAL_NEW:
                if (isAnonymousClassDef(ast)) {
                    frameStack.remove();
                }
                break;
            case TokenTypes.LITERAL_TRY:
                if (ast.getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                    frames.put(ast, frameStack.poll());
                }
                break;
            default:
                // do nothing
        }
    }

    /**
     * Whether the AST is a definition of an anonymous class.
     *
     * @param ast the AST to process.
     * @return true if the AST is a definition of an anonymous class.
     */
    private static boolean isAnonymousClassDef(DetailAST ast) {
        final DetailAST lastChild = ast.getLastChild();
        return lastChild != null
            && lastChild.getType() == TokenTypes.OBJBLOCK;
    }

    /**
     * Returns the class frame where violation is found (where the field is used without 'this')
     * or null otherwise.
     *
     * @param ast IDENT ast to check.
     * @return the class frame where violation is found or null otherwise.
     */
    // -@cs[CyclomaticComplexity] Method already invokes too many methods that fully explain
    // a logic, additional abstraction will not make logic/algorithm more readable.
    private AbstractFrame getClassFrameWhereViolationIsFound(DetailAST ast) {
        AbstractFrame frameWhereViolationIsFound = null;
        final AbstractFrame variableDeclarationFrame = findFrame(ast, false);
        final FrameType variableDeclarationFrameType = variableDeclarationFrame.getType();
        final DetailAST prevSibling = ast.getPreviousSibling();
        if (variableDeclarationFrameType == FrameType.CLASS_FRAME
                && !validateOnlyOverlapping
                && (prevSibling == null || !isInExpression(ast))
                && canBeReferencedFromStaticContext(ast)) {
            frameWhereViolationIsFound = variableDeclarationFrame;
        }
        else if (variableDeclarationFrameType == FrameType.METHOD_FRAME) {
            if (isOverlappingByArgument(ast)) {
                if (!isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                        && !isReturnedVariable(variableDeclarationFrame, ast)
                        && canBeReferencedFromStaticContext(ast)
                        && canAssignValueToClassField(ast)) {
                    frameWhereViolationIsFound = findFrame(ast, true);
                }
            }
            else if (!validateOnlyOverlapping
                     && prevSibling == null
                     && isAssignToken(ast.getParent().getType())
                     && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                     && canBeReferencedFromStaticContext(ast)
                     && canAssignValueToClassField(ast)) {
                frameWhereViolationIsFound = findFrame(ast, true);
            }
        }
        else if (variableDeclarationFrameType == FrameType.CTOR_FRAME
                 && isOverlappingByArgument(ast)
                 && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)) {
            frameWhereViolationIsFound = findFrame(ast, true);
        }
        else if (variableDeclarationFrameType == FrameType.BLOCK_FRAME
                    && isOverlappingByLocalVariable(ast)
                    && canAssignValueToClassField(ast)
                    && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                    && !isReturnedVariable(variableDeclarationFrame, ast)
                    && canBeReferencedFromStaticContext(ast)) {
            frameWhereViolationIsFound = findFrame(ast, true);
        }
        return frameWhereViolationIsFound;
    }

    /**
     * Checks ast parent is in expression.
     *
     * @param ast token to check
     * @return true if token is part of expression, false otherwise
     */
    private static boolean isInExpression(DetailAST ast) {
        return TokenTypes.DOT == ast.getParent().getType()
                || TokenTypes.METHOD_REF == ast.getParent().getType();
    }

    /**
     * Checks whether user arranges 'this' for variable in method, constructor, or block on his own.
     *
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
     *
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
     *
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

        return returnsInsideBlock.stream()
            .anyMatch(returnToken -> isAstInside(returnToken, ident));
    }

    /**
     * Checks if the given {@code ast} is equal to the {@code tree} or a child of it.
     *
     * @param tree The tree to search.
     * @param ast The AST to look for.
     * @return {@code true} if the {@code ast} was found.
     */
    private static boolean isAstInside(DetailAST tree, DetailAST ast) {
        boolean result = false;

        if (isAstSimilar(tree, ast)) {
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
     *
     * @param ident ident token.
     * @return true if field can be referenced from a static context.
     */
    private static boolean canBeReferencedFromStaticContext(DetailAST ident) {
        boolean staticContext = false;

        final DetailAST codeBlockDefinition = getCodeBlockDefinitionToken(ident);
        if (codeBlockDefinition != null) {
            final DetailAST modifiers = codeBlockDefinition.getFirstChild();
            staticContext = codeBlockDefinition.getType() == TokenTypes.STATIC_INIT
                || modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
        }
        return !staticContext;
    }

    /**
     * Returns code block definition token for current identifier.
     *
     * @param ident ident token.
     * @return code block definition token for current identifier or null if code block
     *         definition was not found.
     */
    private static DetailAST getCodeBlockDefinitionToken(DetailAST ident) {
        DetailAST parent = ident;
        while (parent != null
               && parent.getType() != TokenTypes.METHOD_DEF
               && parent.getType() != TokenTypes.STATIC_INIT) {
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * Checks whether a value can be assigned to a field.
     * A value can be assigned to a final field only in constructor block. If there is a method
     * block, value assignment can be performed only to non final field.
     *
     * @param ast an identifier token.
     * @return true if a value can be assigned to a field.
     */
    private boolean canAssignValueToClassField(DetailAST ast) {
        final AbstractFrame fieldUsageFrame = findFrame(ast, false);
        final boolean fieldUsageInConstructor = isInsideConstructorFrame(fieldUsageFrame);

        final AbstractFrame declarationFrame = findFrame(ast, true);
        final boolean finalField = ((ClassFrame) declarationFrame).hasFinalField(ast);

        return fieldUsageInConstructor || !finalField;
    }

    /**
     * Checks whether a field usage frame is inside constructor frame.
     *
     * @param frame frame, where field is used.
     * @return true if the field usage frame is inside constructor frame.
     */
    private static boolean isInsideConstructorFrame(AbstractFrame frame) {
        AbstractFrame fieldUsageFrame = frame;
        while (fieldUsageFrame.getType() == FrameType.BLOCK_FRAME) {
            fieldUsageFrame = fieldUsageFrame.getParent();
        }
        return fieldUsageFrame.getType() == FrameType.CTOR_FRAME;
    }

    /**
     * Checks whether an overlapping by method or constructor argument takes place.
     *
     * @param ast an identifier.
     * @return true if an overlapping by method or constructor argument takes place.
     */
    private boolean isOverlappingByArgument(DetailAST ast) {
        boolean overlapping = false;
        final DetailAST parent = ast.getParent();
        final DetailAST sibling = ast.getNextSibling();
        if (sibling != null && isAssignToken(parent.getType())) {
            if (isCompoundAssignToken(parent.getType())) {
                overlapping = true;
            }
            else {
                final ClassFrame classFrame = (ClassFrame) findFrame(ast, true);
                final Set<DetailAST> exprIdents = getAllTokensOfType(sibling, TokenTypes.IDENT);
                overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
            }
        }
        return overlapping;
    }

    /**
     * Checks whether an overlapping by local variable takes place.
     *
     * @param ast an identifier.
     * @return true if an overlapping by local variable takes place.
     */
    private boolean isOverlappingByLocalVariable(DetailAST ast) {
        boolean overlapping = false;
        final DetailAST parent = ast.getParent();
        if (isAssignToken(parent.getType())) {
            final ClassFrame classFrame = (ClassFrame) findFrame(ast, true);
            final Set<DetailAST> exprIdents =
                getAllTokensOfType(ast.getNextSibling(), TokenTypes.IDENT);
            overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
        }
        return overlapping;
    }

    /**
     * Collects all tokens of specific type starting with the current ast node.
     *
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
     *
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
     *
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
                if (isAstSimilar(token, vertex)
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
     *
     * @param ast the IDENT ast of the name to check.
     * @return the frame where the method is declared, if the given method is used without
     *         'this' and null otherwise.
     */
    private AbstractFrame getMethodWithoutThis(DetailAST ast) {
        AbstractFrame result = null;
        if (!validateOnlyOverlapping) {
            final AbstractFrame frame = findFrame(ast, true);
            if (frame != null
                    && ((ClassFrame) frame).hasInstanceMethod(ast)
                    && !((ClassFrame) frame).hasStaticMethod(ast)) {
                result = frame;
            }
        }
        return result;
    }

    /**
     * Find the class frame containing declaration.
     *
     * @param name IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    private AbstractFrame findClassFrame(DetailAST name, boolean lookForMethod) {
        AbstractFrame frame = current.peek();

        while (true) {
            frame = findFrame(frame, name, lookForMethod);

            if (frame == null || frame instanceof ClassFrame) {
                break;
            }

            frame = frame.getParent();
        }

        return frame;
    }

    /**
     * Find frame containing declaration.
     *
     * @param name IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    private AbstractFrame findFrame(DetailAST name, boolean lookForMethod) {
        return findFrame(current.peek(), name, lookForMethod);
    }

    /**
     * Find frame containing declaration.
     *
     * @param frame The parent frame to searching in.
     * @param name IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    private static AbstractFrame findFrame(AbstractFrame frame, DetailAST name,
            boolean lookForMethod) {
        return frame.getIfContains(name, lookForMethod);
    }

    /**
     * Check that token is related to Definition tokens.
     *
     * @param parentType token Type.
     * @return true if token is related to Definition Tokens.
     */
    private static boolean isDeclarationToken(int parentType) {
        return DECLARATION_TOKENS.get(parentType);
    }

    /**
     * Check that token is related to assign tokens.
     *
     * @param tokenType token type.
     * @return true if token is related to assign tokens.
     */
    private static boolean isAssignToken(int tokenType) {
        return ASSIGN_TOKENS.get(tokenType);
    }

    /**
     * Check that token is related to compound assign tokens.
     *
     * @param tokenType token type.
     * @return true if token is related to compound assign tokens.
     */
    private static boolean isCompoundAssignToken(int tokenType) {
        return COMPOUND_ASSIGN_TOKENS.get(tokenType);
    }

    /**
     * Gets the name of the nearest parent ClassFrame.
     *
     * @return the name of the nearest parent ClassFrame.
     */
    private String getNearestClassFrameName() {
        AbstractFrame frame = current.peek();
        while (frame.getType() != FrameType.CLASS_FRAME) {
            frame = frame.getParent();
        }
        return frame.getFrameName();
    }

    /**
     * Checks if the token is a Lambda parameter.
     *
     * @param ast the {@code DetailAST} value of the token to be checked
     * @return true if the token is a Lambda parameter
     */
    private static boolean isLambdaParameter(DetailAST ast) {
        DetailAST parent;
        for (parent = ast; parent != null; parent = parent.getParent()) {
            if (parent.getType() == TokenTypes.LAMBDA) {
                break;
            }
        }
        final boolean isLambdaParameter;
        if (parent == null) {
            isLambdaParameter = false;
        }
        else if (ast.getType() == TokenTypes.PARAMETER_DEF) {
            isLambdaParameter = true;
        }
        else {
            final DetailAST lambdaParameters = parent.findFirstToken(TokenTypes.PARAMETERS);
            if (lambdaParameters == null) {
                isLambdaParameter = parent.getFirstChild().getText().equals(ast.getText());
            }
            else {
                isLambdaParameter = TokenUtil.findFirstTokenByPredicate(lambdaParameters,
                    paramDef -> {
                        final DetailAST param = paramDef.findFirstToken(TokenTypes.IDENT);
                        return param != null && param.getText().equals(ast.getText());
                    }).isPresent();
            }
        }
        return isLambdaParameter;
    }

    /**
     * Checks if 2 AST are similar by their type and text.
     *
     * @param left The first AST to check.
     * @param right The second AST to check.
     * @return {@code true} if they are similar.
     */
    private static boolean isAstSimilar(DetailAST left, DetailAST right) {
        return left.getType() == right.getType() && left.getText().equals(right.getText());
    }

    /** An AbstractFrame type. */
    private enum FrameType {

        /** Class frame type. */
        CLASS_FRAME,
        /** Constructor frame type. */
        CTOR_FRAME,
        /** Method frame type. */
        METHOD_FRAME,
        /** Block frame type. */
        BLOCK_FRAME,
        /** Catch frame type. */
        CATCH_FRAME,
        /** For frame type. */
        FOR_FRAME,
        /** Try with resources frame type. */
        TRY_WITH_RESOURCES_FRAME

    }

    /**
     * A declaration frame.
     */
    private abstract static class AbstractFrame {

        /** Set of name of variables declared in this frame. */
        private final Set<DetailAST> varIdents;

        /** Parent frame. */
        private final AbstractFrame parent;

        /** Name identifier token. */
        private final DetailAST frameNameIdent;

        /**
         * Constructor -- invocable only via super() from subclasses.
         *
         * @param parent parent frame.
         * @param ident frame name ident.
         */
        protected AbstractFrame(AbstractFrame parent, DetailAST ident) {
            this.parent = parent;
            frameNameIdent = ident;
            varIdents = new HashSet<>();
        }

        /**
         * Get the type of the frame.
         *
         * @return a FrameType.
         */
        protected abstract FrameType getType();

        /**
         * Add a name to the frame.
         *
         * @param identToAdd the name we're adding.
         */
        private void addIdent(DetailAST identToAdd) {
            varIdents.add(identToAdd);
        }

        /**
         * Returns the parent frame.
         *
         * @return the parent frame
         */
        protected AbstractFrame getParent() {
            return parent;
        }

        /**
         * Returns the name identifier text.
         *
         * @return the name identifier text
         */
        protected String getFrameName() {
            return frameNameIdent.getText();
        }

        /**
         * Returns the name identifier token.
         *
         * @return the name identifier token
         */
        public DetailAST getFrameNameIdent() {
            return frameNameIdent;
        }

        /**
         * Check whether the frame contains a field or a variable with the given name.
         *
         * @param identToFind the IDENT ast of the name we're looking for.
         * @return whether it was found.
         */
        protected boolean containsFieldOrVariable(DetailAST identToFind) {
            return containsFieldOrVariableDef(varIdents, identToFind);
        }

        /**
         * Check whether the frame contains a given name.
         *
         * @param identToFind IDENT ast of the name we're looking for.
         * @param lookForMethod whether we are looking for a method name.
         * @return whether it was found.
         */
        protected AbstractFrame getIfContains(DetailAST identToFind, boolean lookForMethod) {
            final AbstractFrame frame;

            if (!lookForMethod
                && containsFieldOrVariable(identToFind)) {
                frame = this;
            }
            else {
                frame = parent.getIfContains(identToFind, lookForMethod);
            }
            return frame;
        }

        /**
         * Whether the set contains a declaration with the text of the specified
         * IDENT ast and it is declared in a proper position.
         *
         * @param set the set of declarations.
         * @param ident the specified IDENT ast.
         * @return true if the set contains a declaration with the text of the specified
         *         IDENT ast and it is declared in a proper position.
         */
        protected boolean containsFieldOrVariableDef(Set<DetailAST> set, DetailAST ident) {
            boolean result = false;
            for (DetailAST ast: set) {
                if (isProperDefinition(ident, ast)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        /**
         * Whether the definition is correspondent to the IDENT.
         *
         * @param ident the IDENT ast to check.
         * @param ast the IDENT ast of the definition to check.
         * @return true if ast is correspondent to ident.
         */
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String identToFind = ident.getText();
            return identToFind.equals(ast.getText())
                && CheckUtil.isBeforeInSource(ast, ident);
        }
    }

    /**
     * A frame initiated at method definition; holds a method definition token.
     */
    private static class MethodFrame extends AbstractFrame {

        /**
         * Creates method frame.
         *
         * @param parent parent frame.
         * @param ident method name identifier token.
         */
        protected MethodFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        protected FrameType getType() {
            return FrameType.METHOD_FRAME;
        }

    }

    /**
     * A frame initiated at constructor definition.
     */
    private static class ConstructorFrame extends AbstractFrame {

        /**
         * Creates a constructor frame.
         *
         * @param parent parent frame.
         * @param ident frame name ident.
         */
        protected ConstructorFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        protected FrameType getType() {
            return FrameType.CTOR_FRAME;
        }

    }

    /**
     * A frame initiated at class, enum or interface definition; holds instance variable names.
     */
    private static class ClassFrame extends AbstractFrame {

        /** Set of idents of instance members declared in this frame. */
        private final Set<DetailAST> instanceMembers;
        /** Set of idents of instance methods declared in this frame. */
        private final Set<DetailAST> instanceMethods;
        /** Set of idents of variables declared in this frame. */
        private final Set<DetailAST> staticMembers;
        /** Set of idents of static methods declared in this frame. */
        private final Set<DetailAST> staticMethods;

        /**
         * Creates new instance of ClassFrame.
         *
         * @param parent parent frame.
         * @param ident frame name ident.
         */
        private ClassFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
            instanceMembers = new HashSet<>();
            instanceMethods = new HashSet<>();
            staticMembers = new HashSet<>();
            staticMethods = new HashSet<>();
        }

        @Override
        protected FrameType getType() {
            return FrameType.CLASS_FRAME;
        }

        /**
         * Adds static member's ident.
         *
         * @param ident an ident of static member of the class.
         */
        public void addStaticMember(final DetailAST ident) {
            staticMembers.add(ident);
        }

        /**
         * Adds static method's name.
         *
         * @param ident an ident of static method of the class.
         */
        public void addStaticMethod(final DetailAST ident) {
            staticMethods.add(ident);
        }

        /**
         * Adds instance member's ident.
         *
         * @param ident an ident of instance member of the class.
         */
        public void addInstanceMember(final DetailAST ident) {
            instanceMembers.add(ident);
        }

        /**
         * Adds instance method's name.
         *
         * @param ident an ident of instance method of the class.
         */
        public void addInstanceMethod(final DetailAST ident) {
            instanceMethods.add(ident);
        }

        /**
         * Checks if a given name is a known instance member of the class.
         *
         * @param ident the IDENT ast of the name to check.
         * @return true is the given name is a name of a known
         *         instance member of the class.
         */
        public boolean hasInstanceMember(final DetailAST ident) {
            return containsFieldOrVariableDef(instanceMembers, ident);
        }

        /**
         * Checks if a given name is a known instance method of the class.
         *
         * @param ident the IDENT ast of the method call to check.
         * @return true if the given ast is correspondent to a known
         *         instance method of the class.
         */
        public boolean hasInstanceMethod(final DetailAST ident) {
            return containsMethodDef(instanceMethods, ident);
        }

        /**
         * Checks if a given name is a known static method of the class.
         *
         * @param ident the IDENT ast of the method call to check.
         * @return true is the given ast is correspondent to a known
         *         instance method of the class.
         */
        public boolean hasStaticMethod(final DetailAST ident) {
            return containsMethodDef(staticMethods, ident);
        }

        /**
         * Checks whether given instance member has final modifier.
         *
         * @param instanceMember an instance member of a class.
         * @return true if given instance member has final modifier.
         */
        public boolean hasFinalField(final DetailAST instanceMember) {
            boolean result = false;
            for (DetailAST member : instanceMembers) {
                final DetailAST parent = member.getParent();
                if (parent.getType() == TokenTypes.RECORD_COMPONENT_DEF) {
                    result = true;
                }
                else {
                    final DetailAST mods = parent.findFirstToken(TokenTypes.MODIFIERS);
                    final boolean finalMod = mods.findFirstToken(TokenTypes.FINAL) != null;
                    if (finalMod && isAstSimilar(member, instanceMember)) {
                        result = true;
                    }
                }
            }
            return result;
        }

        @Override
        protected boolean containsFieldOrVariable(DetailAST identToFind) {
            return containsFieldOrVariableDef(instanceMembers, identToFind)
                    || containsFieldOrVariableDef(staticMembers, identToFind);
        }

        @Override
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String identToFind = ident.getText();
            return identToFind.equals(ast.getText());
        }

        @Override
        protected AbstractFrame getIfContains(DetailAST identToFind, boolean lookForMethod) {
            AbstractFrame frame = null;

            if (containsMethod(identToFind)
                || containsFieldOrVariable(identToFind)) {
                frame = this;
            }
            else if (getParent() != null) {
                frame = getParent().getIfContains(identToFind, lookForMethod);
            }
            return frame;
        }

        /**
         * Check whether the frame contains a given method.
         *
         * @param methodToFind the AST of the method to find.
         * @return true, if a method with the same name and number of parameters is found.
         */
        private boolean containsMethod(DetailAST methodToFind) {
            return containsMethodDef(instanceMethods, methodToFind)
                || containsMethodDef(staticMethods, methodToFind);
        }

        /**
         * Whether the set contains a method definition with the
         *     same name and number of parameters.
         *
         * @param set the set of definitions.
         * @param ident the specified method call IDENT ast.
         * @return true if the set contains a definition with the
         *     same name and number of parameters.
         */
        private static boolean containsMethodDef(Set<DetailAST> set, DetailAST ident) {
            boolean result = false;
            for (DetailAST ast: set) {
                if (isSimilarSignature(ident, ast)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        /**
         * Whether the method definition has the same name and number of parameters.
         *
         * @param ident the specified method call IDENT ast.
         * @param ast the ast of a method definition to compare with.
         * @return true if a method definition has the same name and number of parameters
         *     as the method call.
         */
        private static boolean isSimilarSignature(DetailAST ident, DetailAST ast) {
            boolean result = false;
            final DetailAST elistToken = ident.getParent().findFirstToken(TokenTypes.ELIST);
            if (elistToken != null && ident.getText().equals(ast.getText())) {
                final int paramsNumber =
                    ast.getParent().findFirstToken(TokenTypes.PARAMETERS).getChildCount();
                final int argsNumber = elistToken.getChildCount();
                result = paramsNumber == argsNumber;
            }
            return result;
        }

    }

    /**
     * An anonymous class frame; holds instance variable names.
     */
    private static class AnonymousClassFrame extends ClassFrame {

        /** The name of the frame. */
        private final String frameName;

        /**
         * Creates anonymous class frame.
         *
         * @param parent parent frame.
         * @param frameName name of the frame.
         */
        protected AnonymousClassFrame(AbstractFrame parent, String frameName) {
            super(parent, null);
            this.frameName = frameName;
        }

        @Override
        protected String getFrameName() {
            return frameName;
        }

    }

    /**
     * A frame initiated on entering a statement list; holds local variable names.
     */
    private static class BlockFrame extends AbstractFrame {

        /**
         * Creates block frame.
         *
         * @param parent parent frame.
         * @param ident ident frame name ident.
         */
        protected BlockFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        protected FrameType getType() {
            return FrameType.BLOCK_FRAME;
        }

    }

    /**
     * A frame initiated on entering a catch block; holds local catch variable names.
     */
    private static class CatchFrame extends AbstractFrame {

        /**
         * Creates catch frame.
         *
         * @param parent parent frame.
         * @param ident ident frame name ident.
         */
        protected CatchFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        public FrameType getType() {
            return FrameType.CATCH_FRAME;
        }

        @Override
        protected AbstractFrame getIfContains(DetailAST identToFind, boolean lookForMethod) {
            final AbstractFrame frame;

            if (!lookForMethod
                    && containsFieldOrVariable(identToFind)) {
                frame = this;
            }
            else if (getParent().getType() == FrameType.TRY_WITH_RESOURCES_FRAME) {
                // Skip try-with-resources frame because resources cannot be accessed from catch
                frame = getParent().getParent().getIfContains(identToFind, lookForMethod);
            }
            else {
                frame = getParent().getIfContains(identToFind, lookForMethod);
            }
            return frame;
        }

    }

    /**
     * A frame initiated on entering a for block; holds local for variable names.
     */
    private static class ForFrame extends AbstractFrame {

        /**
         * Creates for frame.
         *
         * @param parent parent frame.
         * @param ident ident frame name ident.
         */
        protected ForFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        public FrameType getType() {
            return FrameType.FOR_FRAME;
        }

    }

    /**
     * A frame initiated on entering a try-with-resources construct;
     * holds local resources for the try block.
     */
    private static class TryWithResourcesFrame extends AbstractFrame {

        /**
         * Creates try-with-resources frame.
         *
         * @param parent parent frame.
         * @param ident ident frame name ident.
         */
        protected TryWithResourcesFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        public FrameType getType() {
            return FrameType.TRY_WITH_RESOURCES_FRAME;
        }

    }

}
