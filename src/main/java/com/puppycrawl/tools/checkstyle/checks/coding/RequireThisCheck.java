////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>Checks that code doesn't rely on the &quot;this&quot; default.
 * That is references to instance variables and methods of the present
 * object are explicitly of the form &quot;this.varName&quot; or
 * &quot;this.methodName(args)&quot;.
 * </p>
 * Check has the following options:
 * <p><b>checkFields</b> - whether to check references to fields. Default value is <b>true</b>.</p>
 * <p><b>checkMethods</b> - whether to check references to methods.
 * Default value is <b>true</b>.</p>
 * <p><b>validateOnlyOverlapping</b> - whether to check only overlapping by variables or
 * arguments. Default value is <b>true</b>.</p>
 *
 * <p>Warning: the Check is very controversial if 'validateOnlyOverlapping' option is set to 'false'
 * and not that actual nowadays.</p>
 *
 * <p>Examples of use:
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;/&gt;
 * </pre>
 * An example of how to configure to check {@code this} qualifier for
 * methods only:
 * <pre>
 * &lt;module name=&quot;RequireThis&quot;&gt;
 *   &lt;property name=&quot;checkFields&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;checkMethods&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
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
 * OK, likewise.</p>
 *
 * @author Stephen Bloch
 * @author o_sukhodolsky
 * @author Andrei Selkin
 */
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
    private static final ImmutableSet<Integer> DECLARATION_TOKENS = ImmutableSet.of(
        TokenTypes.VARIABLE_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.CLASS_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.PARAMETER_DEF,
        TokenTypes.TYPE_ARGUMENT
    );
    /** Set of all assign tokens. */
    private static final ImmutableSet<Integer> ASSIGN_TOKENS = ImmutableSet.of(
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
    private static final ImmutableSet<Integer> COMPOUND_ASSIGN_TOKENS = ImmutableSet.of(
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

    /** Tree of all the parsed frames. */
    private Map<DetailAST, AbstractFrame> frames;

    /** Frame for the currently processed AST. */
    private AbstractFrame current;

    /** Whether we should check fields usage. */
    private boolean checkFields = true;
    /** Whether we should check methods usage. */
    private boolean checkMethods = true;
    /** Whether we should check only overlapping by variables or arguments. */
    private boolean validateOnlyOverlapping = true;

    /**
     * Setter for checkFields property.
     * @param checkFields should we check fields usage or not.
     */
    public void setCheckFields(boolean checkFields) {
        this.checkFields = checkFields;
    }

    /**
     * Setter for checkMethods property.
     * @param checkMethods should we check methods usage or not.
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
    }

    /**
     * Setter for validateOnlyOverlapping property.
     * @param validateOnlyOverlapping should we check only overlapping by variables or arguments.
     */
    public void setValidateOnlyOverlapping(boolean validateOnlyOverlapping) {
        this.validateOnlyOverlapping = validateOnlyOverlapping;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        frames = Maps.newHashMap();
        current = null;

        final Deque<AbstractFrame> frameStack = Lists.newLinkedList();
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
            case TokenTypes.IDENT :
                processIdent(ast);
                break;
            case TokenTypes.CLASS_DEF :
            case TokenTypes.INTERFACE_DEF :
            case TokenTypes.ENUM_DEF :
            case TokenTypes.ANNOTATION_DEF :
            case TokenTypes.SLIST :
            case TokenTypes.METHOD_DEF :
            case TokenTypes.CTOR_DEF :
                current = frames.get(ast);
                break;
            default :
                // do nothing
        }
    }

    /**
     * Checks if a given IDENT is method call or field name which
     * requires explicit {@code this} qualifier.
     * @param ast IDENT to check.
     */
    private void processIdent(DetailAST ast) {
        final int parentType = ast.getParent().getType();
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
        if (frame.getFrameName().equals(getNearestClassFrameName())) {
            log(ast, msgKey, ast.getText(), "");
        }
        else {
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
        final boolean importOrPackage = ScopeUtils.getSurroundingScope(ast) == null;
        final boolean methodNameInMethodCall = parentType == TokenTypes.DOT
                && ast.getPreviousSibling() != null;
        final boolean typeName = parentType == TokenTypes.TYPE
                || parentType == TokenTypes.LITERAL_NEW;
        AbstractFrame frame = null;

        if (!importOrPackage
                && !methodNameInMethodCall
                && !typeName
                && !isDeclarationToken(parentType)) {
            frame = getClassFrameWhereViolationIsFound(ast);
        }
        return frame;
    }

    /**
     * Parses the next AST for declarations.
     * @param frameStack stack containing the FrameTree being built.
     * @param ast AST to parse.
     */
    private static void collectDeclarations(Deque<AbstractFrame> frameStack, DetailAST ast) {
        final AbstractFrame frame = frameStack.peek();
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF :
                collectVariableDeclarations(ast, frame);
                break;
            case TokenTypes.PARAMETER_DEF :
                final DetailAST parameterIdent = ast.findFirstToken(TokenTypes.IDENT);
                frame.addIdent(parameterIdent);
                break;
            case TokenTypes.CLASS_DEF :
            case TokenTypes.INTERFACE_DEF :
            case TokenTypes.ENUM_DEF :
            case TokenTypes.ANNOTATION_DEF :
                final DetailAST classFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ClassFrame(frame, classFrameNameIdent));
                break;
            case TokenTypes.SLIST :
                frameStack.addFirst(new BlockFrame(frame, ast));
                break;
            case TokenTypes.METHOD_DEF :
                final DetailAST methodFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                if (frame.getType() == FrameType.CLASS_FRAME) {
                    final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
                    if (mods.branchContains(TokenTypes.LITERAL_STATIC)) {
                        ((ClassFrame) frame).addStaticMethod(methodFrameNameIdent);
                    }
                    else {
                        ((ClassFrame) frame).addInstanceMethod(methodFrameNameIdent);
                    }
                }
                frameStack.addFirst(new MethodFrame(frame, methodFrameNameIdent));
                break;
            case TokenTypes.CTOR_DEF :
                final DetailAST ctorFrameNameIdent = ast.findFirstToken(TokenTypes.IDENT);
                frameStack.addFirst(new ConstructorFrame(frame, ctorFrameNameIdent));
                break;
            default:
                // do nothing
        }
    }

    /**
     * Collects variable declarations.
     * @param ast variable token.
     * @param frame current frame.
     */
    private static void collectVariableDeclarations(DetailAST ast, AbstractFrame frame) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (frame.getType() == FrameType.CLASS_FRAME) {
            final DetailAST mods =
                    ast.findFirstToken(TokenTypes.MODIFIERS);
            if (ScopeUtils.isInInterfaceBlock(ast)
                    || mods.branchContains(TokenTypes.LITERAL_STATIC)) {
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
     * Ends parsing of the AST for declarations.
     * @param frameStack Stack containing the FrameTree being built.
     * @param ast AST that was parsed.
     */
    private void endCollectingDeclarations(Queue<AbstractFrame> frameStack, DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF :
            case TokenTypes.INTERFACE_DEF :
            case TokenTypes.ENUM_DEF :
            case TokenTypes.ANNOTATION_DEF :
            case TokenTypes.SLIST :
            case TokenTypes.METHOD_DEF :
            case TokenTypes.CTOR_DEF :
                frames.put(ast, frameStack.poll());
                break;
            default :
                // do nothing
        }
    }

    /**
     * Returns the class frame where violation is found (where the field is used without 'this')
     * or null otherwise.
     * @param ast IDENT ast to check.
     * @return the class frame where violation is found or null otherwise.
     */
    private AbstractFrame getClassFrameWhereViolationIsFound(DetailAST ast) {
        AbstractFrame frameWhereViolationIsFound = null;
        final AbstractFrame variableDeclarationFrame = findFrame(ast, false);
        if (variableDeclarationFrame != null) {
            final FrameType variableDeclarationFrameType = variableDeclarationFrame.getType();
            final DetailAST prevSibling = ast.getPreviousSibling();
            if (variableDeclarationFrameType == FrameType.CLASS_FRAME
                    && !validateOnlyOverlapping
                    && prevSibling == null
                    && !ScopeUtils.isInInterfaceBlock(ast)
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
            else if (variableDeclarationFrameType == FrameType.BLOCK_FRAME) {
                if (isOverlappingByLocalVariable(ast)) {
                    if (canAssignValueToClassField(ast)
                            && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                            && !isReturnedVariable(variableDeclarationFrame, ast)
                            && canBeReferencedFromStaticContext(ast)) {
                        frameWhereViolationIsFound = findFrame(ast, true);
                    }
                }
                else if (!validateOnlyOverlapping
                         && prevSibling == null
                         && isAssignToken(ast.getParent().getType())
                         && !isUserDefinedArrangementOfThis(variableDeclarationFrame, ast)
                         && canBeReferencedFromStaticContext(ast)) {
                    frameWhereViolationIsFound = findFrame(ast, true);
                }
            }
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

        final Set<DetailAST> variableUsagesInsideBlock =
            getAllTokensWhichAreEqualToCurrent(definitionToken, ident, blockEndToken.getLineNo());

        boolean userDefinedArrangementOfThis = false;
        for (DetailAST variableUsage : variableUsagesInsideBlock) {
            final DetailAST prevSibling = variableUsage.getPreviousSibling();
            if (prevSibling != null
                    && prevSibling.getType() == TokenTypes.LITERAL_THIS) {
                userDefinedArrangementOfThis = true;
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
        final Set<DetailAST> rcurlyTokens = getAllTokensOfType(blockNameIdent, TokenTypes.RCURLY);
        DetailAST blockEndToken = null;
        for (DetailAST currentRcurly : rcurlyTokens) {
            final DetailAST parent = currentRcurly.getParent();
            if (blockStartToken.getLineNo() == parent.getLineNo()) {
                blockEndToken = currentRcurly;
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
            returnedVariable = returnToken.findAll(ident).hasMoreNodes();
            if (returnedVariable) {
                break;
            }
        }
        return returnedVariable;
    }

    /**
     * Checks whether a field can be referenced from a static context.
     * @param ident ident token.
     * @return true if field can be referenced from a static context.
     */
    private boolean canBeReferencedFromStaticContext(DetailAST ident) {
        AbstractFrame variableDeclarationFrame = findFrame(ident, false);
        boolean staticInitializationBlock = false;
        while (variableDeclarationFrame.getType() == FrameType.BLOCK_FRAME) {
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
                        || modifiers.branchContains(TokenTypes.LITERAL_STATIC);
                }
            }
            else {
                final DetailAST frameNameIdent = variableDeclarationFrame.getFrameNameIdent();
                final DetailAST definitionToken = frameNameIdent.getParent();
                staticContext = definitionToken.branchContains(TokenTypes.LITERAL_STATIC);
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
        final AbstractFrame fieldUsageFrame = findFrame(ast, false);
        final boolean fieldUsageInConstructor = isInsideConstructorFrame(fieldUsageFrame);

        final AbstractFrame declarationFrame = findFrame(ast, true);
        boolean finalField = false;
        if (declarationFrame != null) {
            finalField = ((ClassFrame) declarationFrame).hasFinalField(ast);
        }

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
        if (sibling != null && isAssignToken(parent.getType())) {
            final ClassFrame classFrame = (ClassFrame) findFrame(ast, true);
            if (classFrame != null) {
                final Set<DetailAST> exprIdents = getAllTokensOfType(sibling, TokenTypes.IDENT);
                if (isCompoundAssignToken(parent.getType())) {
                    overlapping = true;
                }
                else {
                    overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
                }
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
        if (sibling != null && isAssignToken(parent.getType())) {
            final ClassFrame classFrame = (ClassFrame) findFrame(ast, true);
            if (classFrame != null) {
                final Set<DetailAST> exprIdents = getAllTokensOfType(sibling, TokenTypes.IDENT);
                if (classFrame.hasInstanceMember(ast)) {
                    overlapping = classFrame.containsFieldOrVariableDef(exprIdents, ast);
                }
            }
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
        final Set<DetailAST> result = Sets.newHashSet();
        final Deque<DetailAST> stack = Queues.newArrayDeque();
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
        final Set<DetailAST> result = Sets.newHashSet();
        final Deque<DetailAST> stack = Queues.newArrayDeque();
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
        final Set<DetailAST> result = Sets.newHashSet();
        final Deque<DetailAST> stack = Queues.newArrayDeque();
        while (vertex != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                vertex = stack.pop();
            }
            while (vertex != null) {
                if (token.equals(vertex)
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
        final AbstractFrame frame = findFrame(ast, true);
        if (frame != null
                && !validateOnlyOverlapping
                && ((ClassFrame) frame).hasInstanceMethod(ast)
                && !((ClassFrame) frame).hasStaticMethod(ast)) {
            result = frame;
        }
        return result;
    }

    /**
     * Find frame containing declaration.
     * @param name IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    private AbstractFrame findFrame(DetailAST name, boolean lookForMethod) {
        final AbstractFrame result;
        if (current == null) {
            result = null;
        }
        else {
            result = current.getIfContains(name, lookForMethod);
        }
        return result;
    }

    /**
     * Check that token is related to Definition tokens.
     * @param parentType token Type.
     * @return true if token is related to Definition Tokens.
     */
    private static boolean isDeclarationToken(int parentType) {
        return DECLARATION_TOKENS.contains(parentType);
    }

    /**
     * Check that token is related to assign tokens.
     * @param tokenType token type.
     * @return true if token is related to assign tokens.
     */
    private static boolean isAssignToken(int tokenType) {
        return ASSIGN_TOKENS.contains(tokenType);
    }

    /**
     * Check that token is related to compound assign tokens.
     * @param tokenType token type.
     * @return true if token is related to compound assign tokens.
     */
    private static boolean isCompoundAssignToken(int tokenType) {
        return COMPOUND_ASSIGN_TOKENS.contains(tokenType);
    }

    /**
     * Gets the name of the nearest parent ClassFrame.
     * @return the name of the nearest parent ClassFrame.
     */
    private String getNearestClassFrameName() {
        AbstractFrame frame = current;
        while (frame.getType() != FrameType.CLASS_FRAME) {
            frame = frame.getParent();
        }
        return frame.getFrameName();
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
    }

    /**
     * A declaration frame.
     * @author Stephen Bloch
     * @author Andrei Selkin
     */
    private abstract static class AbstractFrame {
        /** Set of name of variables declared in this frame. */
        private final Set<DetailAST> varIdents;

        /** Parent frame. */
        private final AbstractFrame parent;

        /** Name identifier token. */
        private final DetailAST frameNameIdent;

        /**
         * Constructor -- invokable only via super() from subclasses.
         * @param parent parent frame.
         * @param ident frame name ident.
         */
        protected AbstractFrame(AbstractFrame parent, DetailAST ident) {
            this.parent = parent;
            frameNameIdent = ident;
            varIdents = Sets.newHashSet();
        }

        /**
         * Get the type of the frame.
         * @return a FrameType.
         */
        protected abstract FrameType getType();

        /**
         * Add a name to the frame.
         * @param identToAdd the name we're adding.
         */
        private void addIdent(DetailAST identToAdd) {
            varIdents.add(identToAdd);
        }

        protected AbstractFrame getParent() {
            return parent;
        }

        protected String getFrameName() {
            return frameNameIdent.getText();
        }

        public DetailAST getFrameNameIdent() {
            return frameNameIdent;
        }

        /**
         * Check whether the frame contains a field or a variable with the given name.
         * @param nameToFind the IDENT ast of the name we're looking for.
         * @return whether it was found.
         */
        protected boolean containsFieldOrVariable(DetailAST nameToFind) {
            return containsFieldOrVariableDef(varIdents, nameToFind);
        }

        /**
         * Check whether the frame contains a given name.
         * @param nameToFind IDENT ast of the name we're looking for.
         * @param lookForMethod whether we are looking for a method name.
         * @return whether it was found.
         */
        protected AbstractFrame getIfContains(DetailAST nameToFind, boolean lookForMethod) {
            final AbstractFrame frame;

            if (!lookForMethod
                && containsFieldOrVariable(nameToFind)) {
                frame = this;
            }
            else {
                frame = parent.getIfContains(nameToFind, lookForMethod);
            }
            return frame;
        }

        /**
         * Whether the set contains a declaration with the text of the specified
         * IDENT ast and it is declared in a proper position.
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
         * @param ident the IDENT ast to check.
         * @param ast the IDENT ast of the definition to check.
         * @return true if ast is correspondent to ident.
         */
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String nameToFind = ident.getText();
            return nameToFind.equals(ast.getText())
                && checkPosition(ast, ident);
        }

        /**
         * Whether the declaration is located before the checked ast.
         * @param ast1 the IDENT ast of the declaration.
         * @param ast2 the IDENT ast to check.
         * @return true, if the declaration is located before the checked ast.
         */
        private static boolean checkPosition(DetailAST ast1, DetailAST ast2) {
            boolean result = false;
            if (ast1.getLineNo() < ast2.getLineNo()
                    || ast1.getLineNo() == ast2.getLineNo()
                    && ast1.getColumnNo() < ast2.getColumnNo()) {
                result = true;
            }
            return result;
        }
    }

    /**
     * A frame initiated at method definition; holds a method definition token.
     * @author Stephen Bloch
     * @author Andrei Selkin
     */
    private static class MethodFrame extends AbstractFrame {

        /**
         * Creates method frame.
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
     * @author Andrei Selkin
     */
    private static class ConstructorFrame extends AbstractFrame {

        /**
         * Creates a constructor frame.
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
     * A frame initiated at class< enum or interface definition; holds instance variable names.
     * @author Stephen Bloch
     * @author Andrei Selkin
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
         * @param parent parent frame.
         * @param ident frame name ident.
         */
        ClassFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
            instanceMembers = Sets.newHashSet();
            instanceMethods = Sets.newHashSet();
            staticMembers = Sets.newHashSet();
            staticMethods = Sets.newHashSet();
        }

        @Override
        protected FrameType getType() {
            return FrameType.CLASS_FRAME;
        }

        /**
         * Adds static member's ident.
         * @param ident an ident of static member of the class.
         */
        public void addStaticMember(final DetailAST ident) {
            staticMembers.add(ident);
        }

        /**
         * Adds static method's name.
         * @param ident an ident of static method of the class.
         */
        public void addStaticMethod(final DetailAST ident) {
            staticMethods.add(ident);
        }

        /**
         * Adds instance member's ident.
         * @param ident an ident of instance member of the class.
         */
        public void addInstanceMember(final DetailAST ident) {
            instanceMembers.add(ident);
        }

        /**
         * Adds instance method's name.
         * @param ident an ident of instance method of the class.
         */
        public void addInstanceMethod(final DetailAST ident) {
            instanceMethods.add(ident);
        }

        /**
         * Checks if a given name is a known instance member of the class.
         * @param ident the IDENT ast of the name to check.
         * @return true is the given name is a name of a known
         *         instance member of the class.
         */
        public boolean hasInstanceMember(final DetailAST ident) {
            return containsFieldOrVariableDef(instanceMembers, ident);
        }

        /**
         * Checks if a given name is a known instance method of the class.
         * @param ident the IDENT ast of the method call to check.
         * @return true if the given ast is correspondent to a known
         *         instance method of the class.
         */
        public boolean hasInstanceMethod(final DetailAST ident) {
            return containsMethodDef(instanceMethods, ident);
        }

        /**
         * Checks if a given name is a known static method of the class.
         * @param ident the IDENT ast of the method call to check.
         * @return true is the given ast is correspondent to a known
         *         instance method of the class.
         */
        public boolean hasStaticMethod(final DetailAST ident) {
            return containsMethodDef(staticMethods, ident);
        }

        /**
         * Checks whether given instance member has final modifier.
         * @param instanceMember an instance member of a class.
         * @return true if given instance member has final modifier.
         */
        public boolean hasFinalField(final DetailAST instanceMember) {
            boolean result = false;
            for (DetailAST member : instanceMembers) {
                final DetailAST mods = member.getParent().findFirstToken(TokenTypes.MODIFIERS);
                final boolean finalMod = mods.branchContains(TokenTypes.FINAL);
                if (finalMod && member.equals(instanceMember)) {
                    result = true;
                }
            }
            return result;
        }

        @Override
        protected boolean containsFieldOrVariable(DetailAST nameToFind) {
            return containsFieldOrVariableDef(instanceMembers, nameToFind)
                    || containsFieldOrVariableDef(staticMembers, nameToFind);
        }

        @Override
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String nameToFind = ident.getText();
            return nameToFind.equals(ast.getText());
        }

        @Override
        protected AbstractFrame getIfContains(DetailAST nameToFind, boolean lookForMethod) {
            AbstractFrame frame = null;

            if (lookForMethod && containsMethod(nameToFind)
                || containsFieldOrVariable(nameToFind)) {
                frame = this;
            }
            else if (getParent() != null) {
                frame = getParent().getIfContains(nameToFind, lookForMethod);
            }
            return frame;
        }

        /**
         * Check whether the frame contains a given method.
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
         * @param set the set of definitions.
         * @param ident the specified method call IDENT ast.
         * @return true if the set contains a definition with the
         *     same name and number of parameters.
         */
        private boolean containsMethodDef(Set<DetailAST> set, DetailAST ident) {
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
         * @param ident the specified method call IDENT ast.
         * @param ast the ast of a method definition to compare with.
         * @return true if a method definition has the same name and number of parameters
         *     as the method call.
         */
        private boolean isSimilarSignature(DetailAST ident, DetailAST ast) {
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
     * A frame initiated on entering a statement list; holds local variable names.
     * @author Stephen Bloch
     */
    private static class BlockFrame extends AbstractFrame {

        /**
         * Creates block frame.
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
}
