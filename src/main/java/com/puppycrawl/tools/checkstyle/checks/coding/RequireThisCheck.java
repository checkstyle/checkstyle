////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>Checks that code doesn't rely on the &quot;this&quot; default.
 * That is references to instance variables and methods of the present
 * object are explicitly of the form &quot;this.varName&quot; or
 * &quot;this.methodName(args)&quot;.
 *</p>
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
 * <p>Limitations: I'm not currently doing anything about static variables
 * or catch-blocks.  Static methods invoked on a class name seem to be OK;
 * both the class name and the method name have a DOT parent.
 * Non-static methods invoked on either this or a variable name seem to be
 * OK, likewise.</p>
 * <p>Much of the code for this check was cribbed from Rick Giles's
 * {@code HiddenFieldCheck}.</p>
 *
 * @author Stephen Bloch
 * @author o_sukhodolsky
 */
public class RequireThisCheck extends Check {

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

    /**
     * Set of all declaration tokens.
     */
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

    /**
     * Tree of all the parsed frames.
     */
    private Map<DetailAST, LexicalFrame> frames;

    /**
     * Frame for the currently processed AST.
     */
    private LexicalFrame current;

    /** Whether we should check fields usage. */
    private boolean checkFields = true;
    /** Whether we should check methods usage. */
    private boolean checkMethods = true;

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
        final Deque<LexicalFrame> frameStack = Lists.newLinkedList();
        frameStack.add(new GlobalFrame());

        frames = Maps.newHashMap();

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
     * require explicit {@code this} qualifier.
     *
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
                // let's check method calls
                if (checkMethods && isClassMethod(ast)) {
                    log(ast, MSG_METHOD, ast.getText());
                }
                break;
            default:
                if (checkFields) {
                    processField(ast, parentType);
                }
                break;
        }
    }

    /**
     * Process validation of Field.
     * @param ast field definition ast token
     * @param parentType type of the parent
     */
    private void processField(DetailAST ast, int parentType) {
        final boolean importOrPackage = ScopeUtils.getSurroundingScope(ast) == null;
        final boolean methodNameInMethodCall = parentType == TokenTypes.DOT
                && ast.getPreviousSibling() != null;
        final boolean typeName = parentType == TokenTypes.TYPE
                || parentType == TokenTypes.LITERAL_NEW;

        if (!importOrPackage
                && !methodNameInMethodCall
                && !typeName
                && !isDeclarationToken(parentType)
                && isClassField(ast)) {
            log(ast, MSG_VARIABLE, ast.getText());
        }
    }

    /**
     * Parse the next AST for declarations.
     *
     * @param frameStack Stack containing the FrameTree being built
     * @param ast AST to parse
     */
    private static void collectDeclarations(Deque<LexicalFrame> frameStack,
        DetailAST ast) {
        final LexicalFrame frame = frameStack.peek();
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
                final DetailAST classIdent = ast.findFirstToken(TokenTypes.IDENT);
                frame.addIdent(classIdent);
                frameStack.addFirst(new ClassFrame(frame));
                break;
            case TokenTypes.SLIST :
                frameStack.addFirst(new BlockFrame(frame));
                break;
            case TokenTypes.METHOD_DEF :
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                if (frame instanceof ClassFrame) {
                    final DetailAST mods =
                            ast.findFirstToken(TokenTypes.MODIFIERS);
                    if (mods.branchContains(TokenTypes.LITERAL_STATIC)) {
                        ((ClassFrame) frame).addStaticMethod(ident);
                    }
                    else {
                        ((ClassFrame) frame).addInstanceMethod(ident);
                    }
                }
                frameStack.addFirst(new MethodFrame(frame));
                break;
            case TokenTypes.CTOR_DEF :
                frameStack.addFirst(new MethodFrame(frame));
                break;
            default:
                // do nothing
        }
    }

    /**
     * Collect Variable Declarations.
     * @param ast variable token
     * @param frame current frame
     */
    private static void collectVariableDeclarations(DetailAST ast, LexicalFrame frame) {
        final DetailAST ident =
                ast.findFirstToken(TokenTypes.IDENT);
        if (frame instanceof ClassFrame) {
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
     * End parsing of the AST for declarations.
     *
     * @param frameStack Stack containing the FrameTree being built
     * @param ast AST that was parsed
     */
    private void endCollectingDeclarations(Queue<LexicalFrame> frameStack,
        DetailAST ast) {
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
     * Check if given name is a name for class field in current environment.
     * @param ident an IDENT ast to check
     * @return true is the given name is name of member.
     */
    private boolean isClassField(DetailAST ident) {
        final LexicalFrame frame = findFrame(ident, false);
        return frame instanceof ClassFrame
                && ((ClassFrame) frame).hasInstanceMember(ident);
    }

    /**
     * Check if given name is a name for class method in current environment.
     * @param ident the IDENT ast of the name to check
     * @return true is the given name is name of method.
     */
    private boolean isClassMethod(DetailAST ident) {
        final LexicalFrame frame = findFrame(ident, true);
        return frame instanceof ClassFrame
                && ((ClassFrame) frame).hasInstanceMethod(ident);
    }

    /**
     * Find frame containing declaration.
     * @param name IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return LexicalFrame containing declaration or null.
     */
    private LexicalFrame findFrame(DetailAST name, boolean lookForMethod) {
        if (current == null) {
            return null;
        }
        else {
            return current.getIfContains(name, lookForMethod);
        }
    }

    /**
     * Check that token is related to Definition tokens.
     * @param parentType token Type
     * @return true if token is related to Definition Tokens
     */
    private static boolean isDeclarationToken(int parentType) {
        return DECLARATION_TOKENS.contains(parentType);
    }

    /**
     * A declaration frame.
     * @author Stephen Bloch
     */
    private static class LexicalFrame {
        /** Set of name of variables declared in this frame. */
        private final Set<DetailAST> varIdents;

        /**
         * Parent frame.
         */
        private final LexicalFrame parent;

        /**
         * Constructor -- invokable only via super() from subclasses.
         *
         * @param parent parent frame
         */
        protected LexicalFrame(LexicalFrame parent) {
            this.parent = parent;
            varIdents = Sets.newHashSet();
        }

        /**
         * Add a name to the frame.
         * @param identToAdd the name we're adding
         */
        private void addIdent(DetailAST identToAdd) {
            varIdents.add(identToAdd);
        }

        protected LexicalFrame getParent() {
            return parent;
        }

        /** Check whether the frame contains a given name.
         * @param nameToFind the IDENT ast of the name we're looking for
         * @return whether it was found
         */
        boolean contains(DetailAST nameToFind) {
            return containsName(varIdents, nameToFind);
        }

        /** Check whether the frame contains a given name.
         * @param nameToFind IDENT ast of the name we're looking for.
         * @param lookForMethod whether we are looking for a method name.
         * @return whether it was found.
         */
        protected LexicalFrame getIfContains(DetailAST nameToFind, boolean lookForMethod) {
            LexicalFrame frame = null;

            if (!lookForMethod
                && contains(nameToFind)) {
                frame = this;
            }
            else if (parent != null) {
                frame = parent.getIfContains(nameToFind, lookForMethod);
            }
            return frame;
        }

        /**
         * Whether the set contains a declaration with the text of the specified
         * IDENT ast and it is declared in a proper position.
         * @param set the set of declarations.
         * @param ident the specified IDENT ast
         * @return true if the set contains a declaration with the text of the specified
         *         IDENT ast and it is declared in a proper position.
         */
        protected boolean containsName(Set<DetailAST> set, DetailAST ident) {
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
     * The global frame; should hold only class names.
     * @author Stephen Bloch
     */
    private static class GlobalFrame extends LexicalFrame {

        /**
         * Constructor for the root of the FrameTree.
         */
        protected GlobalFrame() {
            super(null);
        }
    }

    /**
     * A frame initiated at method definition; holds parameter names.
     * @author Stephen Bloch
     */
    private static class MethodFrame extends LexicalFrame {
        /**
         * Creates method frame.
         * @param parent parent frame
         */
        protected MethodFrame(LexicalFrame parent) {
            super(parent);
        }
    }

    /**
     * A frame initiated at class definition; holds instance variable
     * names.  For the present, I'm not worried about other class names,
     * method names, etc.
     * @author Stephen Bloch
     */
    private static class ClassFrame extends LexicalFrame {
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
         * @param parent parent frame
         */
        ClassFrame(LexicalFrame parent) {
            super(parent);
            instanceMembers = Sets.newHashSet();
            instanceMethods = Sets.newHashSet();
            staticMembers = Sets.newHashSet();
            staticMethods = Sets.newHashSet();
        }

        /**
         * Adds static member's ident.
         * @param ident an ident of static member of the class
         */
        public void addStaticMember(final DetailAST ident) {
            staticMembers.add(ident);
        }

        /**
         * Adds static method's name.
         * @param ident an ident of static method of the class
         */
        public void addStaticMethod(final DetailAST ident) {
            staticMethods.add(ident);
        }

        /**
         * Adds instance member's ident.
         * @param ident an ident of instance member of the class
         */
        public void addInstanceMember(final DetailAST ident) {
            instanceMembers.add(ident);
        }

        /**
         * Adds instance method's name.
         * @param ident an ident of instance method of the class
         */
        public void addInstanceMethod(final DetailAST ident) {
            instanceMethods.add(ident);
        }

        /**
         * Checks if a given name is a known instance member of the class.
         * @param ident the IDENT ast of the name to check
         * @return true is the given name is a name of a known
         *         instance member of the class
         */
        public boolean hasInstanceMember(final DetailAST ident) {
            return containsName(instanceMembers, ident);
        }

        /**
         * Checks if a given name is a known instance method of the class.
         * @param ident the IDENT ast of the name to check
         * @return true is the given name is a name of a known
         *         instance method of the class
         */
        public boolean hasInstanceMethod(final DetailAST ident) {
            return containsName(instanceMethods, ident);
        }

        @Override
        boolean contains(DetailAST nameToFind) {
            return super.contains(nameToFind)
                    || containsName(instanceMembers, nameToFind)
                    || containsName(instanceMethods, nameToFind)
                    || containsName(staticMembers, nameToFind)
                    || containsName(staticMethods, nameToFind);
        }

        @Override
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String nameToFind = ident.getText();
            return nameToFind.equals(ast.getText());
        }

        @Override
        protected LexicalFrame getIfContains(DetailAST nameToFind, boolean lookForMethod) {
            LexicalFrame frame;

            if (contains(nameToFind)) {
                frame = this;
            }
            else {
                frame = getParent().getIfContains(nameToFind, lookForMethod);
            }
            return frame;
        }
    }

    /**
     * A frame initiated on entering a statement list; holds local variable
     * names.  For the present, I'm not worried about other class names,
     * method names, etc.
     * @author Stephen Bloch
     */
    private static class BlockFrame extends LexicalFrame {

        /**
         * Creates block frame.
         * @param parent parent frame
         */
        protected BlockFrame(LexicalFrame parent) {
            super(parent);
        }
    }
}
