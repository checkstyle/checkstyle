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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <div>
 * Checks that any combination of String literals
 * is on the left side of an {@code equals()} comparison.
 * Also checks for String literals assigned to some field
 * (such as {@code someString.equals(anotherString = "text")}).
 * </div>
 *
 * <p>Rationale: Calling the {@code equals()} method on String literals
 * will avoid a potential {@code NullPointerException}. Also, it is
 * pretty common to see null checks right before equals comparisons
 * but following this rule such checks are not required.
 * </p>
 * <ul>
 * <li>
 * Property {@code ignoreEqualsIgnoreCase} - Control whether to ignore
 * {@code String.equalsIgnoreCase(String)} invocations.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code equals.avoid.null}
 * </li>
 * <li>
 * {@code equalsIgnoreCase.avoid.null}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@FileStatefulCheck
public class EqualsAvoidNullCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EQUALS_AVOID_NULL = "equals.avoid.null";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EQUALS_IGNORE_CASE_AVOID_NULL = "equalsIgnoreCase.avoid.null";

    /** Method name for comparison. */
    private static final String EQUALS = "equals";

    /** Type name for comparison. */
    private static final String STRING = "String";

    /** Curly for comparison. */
    private static final String LEFT_CURLY = "{";

    /** Control whether to ignore {@code String.equalsIgnoreCase(String)} invocations. */
    private boolean ignoreEqualsIgnoreCase;

    /** Stack of sets of field names, one for each class of a set of nested classes. */
    private FieldFrame currentFrame;

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
            TokenTypes.METHOD_CALL,
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LAMBDA,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
        };
    }

    /**
     * Setter to control whether to ignore {@code String.equalsIgnoreCase(String)} invocations.
     *
     * @param newValue whether to ignore checking
     *     {@code String.equalsIgnoreCase(String)}.
     * @since 5.4
     */
    public void setIgnoreEqualsIgnoreCase(boolean newValue) {
        ignoreEqualsIgnoreCase = newValue;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentFrame = new FieldFrame(null);
    }

    @Override
    public void visitToken(final DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.PARAMETER_DEF:
            case TokenTypes.PATTERN_VARIABLE_DEF:
            case TokenTypes.RECORD_COMPONENT_DEF:
                currentFrame.addField(ast);
                break;
            case TokenTypes.METHOD_CALL:
                processMethodCall(ast);
                break;
            case TokenTypes.SLIST:
                processSlist(ast);
                break;
            case TokenTypes.LITERAL_NEW:
                processLiteralNew(ast);
                break;
            case TokenTypes.OBJBLOCK:
                final int parentType = ast.getParent().getType();
                if (!astTypeIsClassOrEnumOrRecordDef(parentType)) {
                    processFrame(ast);
                }
                break;
            default:
                processFrame(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SLIST:
                leaveSlist(ast);
                break;
            case TokenTypes.LITERAL_NEW:
                leaveLiteralNew(ast);
                break;
            case TokenTypes.OBJBLOCK:
                final int parentType = ast.getParent().getType();
                if (!astTypeIsClassOrEnumOrRecordDef(parentType)) {
                    currentFrame = currentFrame.getParent();
                }
                break;
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.PARAMETER_DEF:
            case TokenTypes.RECORD_COMPONENT_DEF:
            case TokenTypes.METHOD_CALL:
            case TokenTypes.PATTERN_VARIABLE_DEF:
                break;
            default:
                currentFrame = currentFrame.getParent();
                break;
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        traverseFieldFrameTree(currentFrame);
    }

    /**
     * Determine whether SLIST begins a block, determined by braces, and add it as
     * a frame in this case.
     *
     * @param ast SLIST ast.
     */
    private void processSlist(DetailAST ast) {
        if (LEFT_CURLY.equals(ast.getText())) {
            final FieldFrame frame = new FieldFrame(currentFrame);
            currentFrame.addChild(frame);
            currentFrame = frame;
        }
    }

    /**
     * Determine whether SLIST begins a block, determined by braces.
     *
     * @param ast SLIST ast.
     */
    private void leaveSlist(DetailAST ast) {
        if (LEFT_CURLY.equals(ast.getText())) {
            currentFrame = currentFrame.getParent();
        }
    }

    /**
     * Process CLASS_DEF, METHOD_DEF, LITERAL_IF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO,
     * LITERAL_CATCH, LITERAL_TRY, CTOR_DEF, ENUM_DEF, ENUM_CONSTANT_DEF.
     *
     * @param ast processed ast.
     */
    private void processFrame(DetailAST ast) {
        final FieldFrame frame = new FieldFrame(currentFrame);
        final int astType = ast.getType();
        if (astTypeIsClassOrEnumOrRecordDef(astType)) {
            frame.setClassOrEnumOrRecordDef(true);
            frame.setFrameName(ast.findFirstToken(TokenTypes.IDENT).getText());
        }
        currentFrame.addChild(frame);
        currentFrame = frame;
    }

    /**
     * Add the method call to the current frame if it should be processed.
     *
     * @param methodCall METHOD_CALL ast.
     */
    private void processMethodCall(DetailAST methodCall) {
        final DetailAST dot = methodCall.getFirstChild();
        if (dot.getType() == TokenTypes.DOT) {
            final String methodName = dot.getLastChild().getText();
            if (EQUALS.equals(methodName)
                    || !ignoreEqualsIgnoreCase && "equalsIgnoreCase".equals(methodName)) {
                currentFrame.addMethodCall(methodCall);
            }
        }
    }

    /**
     * Determine whether LITERAL_NEW is an anonymous class definition and add it as
     * a frame in this case.
     *
     * @param ast LITERAL_NEW ast.
     */
    private void processLiteralNew(DetailAST ast) {
        if (ast.findFirstToken(TokenTypes.OBJBLOCK) != null) {
            final FieldFrame frame = new FieldFrame(currentFrame);
            currentFrame.addChild(frame);
            currentFrame = frame;
        }
    }

    /**
     * Determine whether LITERAL_NEW is an anonymous class definition and leave
     * the frame it is in.
     *
     * @param ast LITERAL_NEW ast.
     */
    private void leaveLiteralNew(DetailAST ast) {
        if (ast.findFirstToken(TokenTypes.OBJBLOCK) != null) {
            currentFrame = currentFrame.getParent();
        }
    }

    /**
     * Traverse the tree of the field frames to check all equals method calls.
     *
     * @param frame to check method calls in.
     */
    private void traverseFieldFrameTree(FieldFrame frame) {
        for (FieldFrame child: frame.getChildren()) {
            traverseFieldFrameTree(child);

            currentFrame = child;
            child.getMethodCalls().forEach(this::checkMethodCall);
        }
    }

    /**
     * Check whether the method call should be violated.
     *
     * @param methodCall method call to check.
     */
    private void checkMethodCall(DetailAST methodCall) {
        DetailAST objCalledOn = methodCall.getFirstChild().getFirstChild();
        if (objCalledOn.getType() == TokenTypes.DOT) {
            objCalledOn = objCalledOn.getLastChild();
        }
        final DetailAST expr = methodCall.findFirstToken(TokenTypes.ELIST).getFirstChild();
        if (containsOneArgument(methodCall)
                && containsAllSafeTokens(expr)
                && isCalledOnStringFieldOrVariable(objCalledOn)) {
            final String methodName = methodCall.getFirstChild().getLastChild().getText();
            if (EQUALS.equals(methodName)) {
                log(methodCall, MSG_EQUALS_AVOID_NULL);
            }
            else {
                log(methodCall, MSG_EQUALS_IGNORE_CASE_AVOID_NULL);
            }
        }
    }

    /**
     * Verify that method call has one argument.
     *
     * @param methodCall METHOD_CALL DetailAST
     * @return true if method call has one argument.
     */
    private static boolean containsOneArgument(DetailAST methodCall) {
        final DetailAST elist = methodCall.findFirstToken(TokenTypes.ELIST);
        return elist.getChildCount() == 1;
    }

    /**
     * Looks for all "safe" Token combinations in the argument
     * expression branch.
     *
     * @param expr the argument expression
     * @return - true if any child matches the set of tokens, false if not
     */
    private static boolean containsAllSafeTokens(final DetailAST expr) {
        DetailAST arg = expr.getFirstChild();
        arg = skipVariableAssign(arg);

        boolean argIsNotNull = false;
        if (arg.getType() == TokenTypes.PLUS) {
            DetailAST child = arg.getFirstChild();
            while (child != null
                    && !argIsNotNull) {
                argIsNotNull = child.getType() == TokenTypes.STRING_LITERAL
                        || child.getType() == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN
                        || child.getType() == TokenTypes.IDENT;
                child = child.getNextSibling();
            }
        }
        else {
            argIsNotNull = arg.getType() == TokenTypes.STRING_LITERAL
                    || arg.getType() == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN;
        }

        return argIsNotNull;
    }

    /**
     * Skips over an inner assign portion of an argument expression.
     *
     * @param currentAST current token in the argument expression
     * @return the next relevant token
     */
    private static DetailAST skipVariableAssign(final DetailAST currentAST) {
        DetailAST result = currentAST;
        while (result.getType() == TokenTypes.LPAREN) {
            result = result.getNextSibling();
        }
        if (result.getType() == TokenTypes.ASSIGN) {
            result = result.getFirstChild().getNextSibling();
        }
        return result;
    }

    /**
     * Determine, whether equals method is called on a field of String type.
     *
     * @param objCalledOn object ast.
     * @return true if the object is of String type.
     */
    private boolean isCalledOnStringFieldOrVariable(DetailAST objCalledOn) {
        final boolean result;
        final DetailAST previousSiblingAst = objCalledOn.getPreviousSibling();
        if (previousSiblingAst == null) {
            result = isStringFieldOrVariable(objCalledOn);
        }
        else {
            if (previousSiblingAst.getType() == TokenTypes.LITERAL_THIS) {
                result = isStringFieldOrVariableFromThisInstance(objCalledOn);
            }
            else {
                final String className = previousSiblingAst.getText();
                result = isStringFieldOrVariableFromClass(objCalledOn, className);
            }
        }
        return result;
    }

    /**
     * Whether the field or the variable is of String type.
     *
     * @param objCalledOn the field or the variable to check.
     * @return true if the field or the variable is of String type.
     */
    private boolean isStringFieldOrVariable(DetailAST objCalledOn) {
        boolean result = false;
        final String name = objCalledOn.getText();
        FieldFrame frame = currentFrame;
        while (frame != null) {
            final DetailAST field = frame.findField(name);
            if (field != null
                    && (frame.isClassOrEnumOrRecordDef()
                            || CheckUtil.isBeforeInSource(field, objCalledOn))) {
                result = STRING.equals(getFieldType(field));
                break;
            }
            frame = frame.getParent();
        }
        return result;
    }

    /**
     * Whether the field or the variable from THIS instance is of String type.
     *
     * @param objCalledOn the field or the variable from THIS instance to check.
     * @return true if the field or the variable from THIS instance is of String type.
     */
    private boolean isStringFieldOrVariableFromThisInstance(DetailAST objCalledOn) {
        final String name = objCalledOn.getText();
        final DetailAST field = getObjectFrame(currentFrame).findField(name);
        return field != null && STRING.equals(getFieldType(field));
    }

    /**
     * Whether the field or the variable from the specified class is of String type.
     *
     * @param objCalledOn the field or the variable from the specified class to check.
     * @param className the name of the class to check in.
     * @return true if the field or the variable from the specified class is of String type.
     */
    private boolean isStringFieldOrVariableFromClass(DetailAST objCalledOn,
            final String className) {
        boolean result = false;
        final String name = objCalledOn.getText();
        FieldFrame frame = currentFrame;
        while (frame != null) {
            if (className.equals(frame.getFrameName())) {
                final DetailAST field = frame.findField(name);
                result = STRING.equals(getFieldType(field));
                break;
            }
            frame = frame.getParent();
        }
        return result;
    }

    /**
     * Get the nearest parent frame which is CLASS_DEF, ENUM_DEF or ENUM_CONST_DEF.
     *
     * @param frame to start the search from.
     * @return the nearest parent frame which is CLASS_DEF, ENUM_DEF or ENUM_CONST_DEF.
     */
    private static FieldFrame getObjectFrame(FieldFrame frame) {
        FieldFrame objectFrame = frame;
        while (!objectFrame.isClassOrEnumOrRecordDef()) {
            objectFrame = objectFrame.getParent();
        }
        return objectFrame;
    }

    /**
     * Get field type.
     *
     * @param field to get the type from.
     * @return type of the field.
     */
    private static String getFieldType(DetailAST field) {
        String fieldType = null;
        final DetailAST identAst = field.findFirstToken(TokenTypes.TYPE)
                .findFirstToken(TokenTypes.IDENT);
        if (identAst != null) {
            fieldType = identAst.getText();
        }
        return fieldType;
    }

    /**
     * Verify that a token is either CLASS_DEF, RECORD_DEF, or ENUM_DEF.
     *
     * @param tokenType the type of token
     * @return true if token is of specified type.
     */
    private static boolean astTypeIsClassOrEnumOrRecordDef(int tokenType) {
        return tokenType == TokenTypes.CLASS_DEF
                || tokenType == TokenTypes.RECORD_DEF
                || tokenType == TokenTypes.ENUM_DEF;
    }

    /**
     * Holds the names of fields of a type.
     */
    private static final class FieldFrame {

        /** Parent frame. */
        private final FieldFrame parent;

        /** Set of frame's children. */
        private final Set<FieldFrame> children = new HashSet<>();

        /** Map of field name to field DetailAst. */
        private final Map<String, DetailAST> fieldNameToAst = new HashMap<>();

        /** Set of equals calls. */
        private final Set<DetailAST> methodCalls = new HashSet<>();

        /** Name of the class, enum or enum constant declaration. */
        private String frameName;

        /** Whether the frame is CLASS_DEF, ENUM_DEF, ENUM_CONST_DEF, or RECORD_DEF. */
        private boolean classOrEnumOrRecordDef;

        /**
         * Creates new frame.
         *
         * @param parent parent frame.
         */
        private FieldFrame(FieldFrame parent) {
            this.parent = parent;
        }

        /**
         * Set the frame name.
         *
         * @param frameName value to set.
         */
        public void setFrameName(String frameName) {
            this.frameName = frameName;
        }

        /**
         * Getter for the frame name.
         *
         * @return frame name.
         */
        public String getFrameName() {
            return frameName;
        }

        /**
         * Getter for the parent frame.
         *
         * @return parent frame.
         */
        public FieldFrame getParent() {
            return parent;
        }

        /**
         * Getter for frame's children.
         *
         * @return children of this frame.
         */
        public Set<FieldFrame> getChildren() {
            return Collections.unmodifiableSet(children);
        }

        /**
         * Add child frame to this frame.
         *
         * @param child frame to add.
         */
        public void addChild(FieldFrame child) {
            children.add(child);
        }

        /**
         * Add field to this FieldFrame.
         *
         * @param field the ast of the field.
         */
        public void addField(DetailAST field) {
            if (field.findFirstToken(TokenTypes.IDENT) != null) {
                fieldNameToAst.put(getFieldName(field), field);
            }
        }

        /**
         * Sets isClassOrEnumOrRecordDef.
         *
         * @param value value to set.
         */
        public void setClassOrEnumOrRecordDef(boolean value) {
            classOrEnumOrRecordDef = value;
        }

        /**
         * Getter for classOrEnumOrRecordDef.
         *
         * @return classOrEnumOrRecordDef.
         */
        public boolean isClassOrEnumOrRecordDef() {
            return classOrEnumOrRecordDef;
        }

        /**
         * Add method call to this frame.
         *
         * @param methodCall METHOD_CALL ast.
         */
        public void addMethodCall(DetailAST methodCall) {
            methodCalls.add(methodCall);
        }

        /**
         * Determines whether this FieldFrame contains the field.
         *
         * @param name name of the field to check.
         * @return DetailAST if this FieldFrame contains instance field.
         */
        public DetailAST findField(String name) {
            return fieldNameToAst.get(name);
        }

        /**
         * Getter for frame's method calls.
         *
         * @return method calls of this frame.
         */
        public Set<DetailAST> getMethodCalls() {
            return Collections.unmodifiableSet(methodCalls);
        }

        /**
         * Get the name of the field.
         *
         * @param field to get the name from.
         * @return name of the field.
         */
        private static String getFieldName(DetailAST field) {
            return field.findFirstToken(TokenTypes.IDENT).getText();
        }

    }

}
