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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * Abstract class for checks which require to work with and identify many frames.
 */
@FileStatefulCheck
public abstract class AbstractFrameCheck extends AbstractCheck {

    /**
     * An AbstractFrame type.
     */
    public enum FrameType {
        /**
         * Class frame type.
         */
        CLASS_FRAME,
        /**
         * Constructor frame type.
         */
        CTOR_FRAME,
        /**
         * Method frame type.
         */
        METHOD_FRAME,
        /**
         * Block frame type.
         */
        BLOCK_FRAME,
        /**
         * Catch frame type.
         */
        CATCH_FRAME,
        /**
         * For frame type.
         */
        FOR_FRAME,

    }

    /**
     * Frame for the currently processed AST.
     */
    private final Deque<AbstractFrame> current = new ArrayDeque<>();

    /**
     * Tree of all the parsed frames.
     */
    private Map<DetailAST, AbstractFrame> frames;

    /**
     * Parses the next AST for declarations.
     *
     * @param frameStack stack containing the FrameTree being built.
     * @param ast        AST to parse.
     */
    protected abstract void collectDeclarations(Deque<AbstractFrame> frameStack,
                                                DetailAST ast);

    /**
     * Ends parsing of the AST for declarations.
     *
     * @param frameStack Stack containing the FrameTree being built.
     * @param ast        AST that was parsed.
     */
    protected abstract void endCollectingDeclarations(Queue<AbstractFrame> frameStack,
                                                      DetailAST ast);

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

    /**
     * Collects variable declarations.
     *
     * @param ast   variable token.
     * @param frame current frame.
     */
    protected static void collectVariableDeclarations(DetailAST ast, AbstractFrame frame) {
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
     * Registers an AST and its parsed frame.
     * @param ast token to register.
     * @param frame parsed frame of the token.
     */
    protected void register(DetailAST ast, AbstractFrame frame) {
        frames.put(ast, frame);
    }

    /**
     * Adds the parsed frame of an AST to the stack.
     * @param ast token that was parsed.
     */
    protected void pushFrame(DetailAST ast) {
        current.push(frames.get(ast));
    }

    /**
     * Removes the latest added parsed frame from the stack.
     */
    protected void popFrame() {
        current.pop();
    }

    /**
     * Gets the name of the nearest parent ClassFrame.
     *
     * @return the name of the nearest parent ClassFrame.
     */
    protected String getNearestClassFrameName() {
        return getNearestClassFrame().getFrameName();
    }

    /**
     * Gets the nearest parent ClassFrame.
     *
     * @return the nearest parent ClassFrame.
     */
    protected AbstractFrame getNearestClassFrame() {
        AbstractFrame frame = current.peek();
        while (frame.getType() != FrameType.CLASS_FRAME) {
            frame = frame.getParent();
        }
        return frame;
    }

    /**
     * Find the class frame containing declaration.
     *
     * @param name          IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    protected AbstractFrame findClassFrame(DetailAST name, boolean lookForMethod) {
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
     * @param name          IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    protected AbstractFrame findFrame(DetailAST name, boolean lookForMethod) {
        return findFrame(current.peek(), name, lookForMethod);
    }

    /**
     * Find frame containing declaration.
     *
     * @param frame         The parent frame to searching in.
     * @param name          IDENT ast of the declaration to find.
     * @param lookForMethod whether we are looking for a method name.
     * @return AbstractFrame containing declaration or null.
     */
    private static AbstractFrame findFrame(AbstractFrame frame, DetailAST name,
                                           boolean lookForMethod) {
        return frame.getIfContains(name, lookForMethod);
    }

    /**
     * Whether the AST is a definition of an anonymous class.
     *
     * @param ast the AST to process.
     * @return true if the AST is a definition of an anonymous class.
     */
    protected static boolean isAnonymousClassDef(DetailAST ast) {
        final DetailAST lastChild = ast.getLastChild();
        return lastChild != null
                && lastChild.getType() == TokenTypes.OBJBLOCK;
    }

    /**
     * Checks if 2 AST are similar by their type and text.
     *
     * @param left  The first AST to check.
     * @param right The second AST to check.
     * @return {@code true} if they are similar.
     */
    protected static boolean isAstSimilar(DetailAST left, DetailAST right) {
        return left.getType() == right.getType() && left.getText().equals(right.getText());
    }

    /**
     * A declaration frame.
     */
    public abstract static class AbstractFrame {

        /**
         * Set of name of variables declared in this frame.
         */
        private final Set<DetailAST> varIdents;

        /**
         * Parent frame.
         */
        private final AbstractFrame parent;

        /**
         * Name identifier token.
         */
        private final DetailAST frameNameIdent;

        /**
         * Constructor -- invocable only via super() from subclasses.
         *
         * @param parent parent frame.
         * @param ident  frame name ident.
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
        public void addIdent(DetailAST identToAdd) {
            varIdents.add(identToAdd);
        }

        public final AbstractFrame getParent() {
            return parent;
        }

        /**
         * Returns the name of the frame.
         *
         * @return the frame name.
         */
        protected String getFrameName() {
            return frameNameIdent.getText();
        }

        public final DetailAST getFrameNameIdent() {
            return frameNameIdent;
        }

        /**
         * Check whether the frame contains a field or a variable with the given name.
         *
         * @param nameToFind the IDENT ast of the name we're looking for.
         * @return whether it was found.
         */
        protected boolean containsFieldOrVariable(DetailAST nameToFind) {
            return containsFieldOrVariableDef(varIdents, nameToFind);
        }

        /**
         * Check whether the frame contains a given name.
         *
         * @param nameToFind    IDENT ast of the name we're looking for.
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
         *
         * @param set   the set of declarations.
         * @param ident the specified IDENT ast.
         * @return true if the set contains a declaration with the text of the specified
         *         IDENT ast and it is declared in a proper position.
         */
        protected boolean containsFieldOrVariableDef(Set<DetailAST> set, DetailAST ident) {
            boolean result = false;
            for (DetailAST ast : set) {
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
         * @param ast   the IDENT ast of the definition to check.
         * @return true if ast is correspondent to ident.
         */
        protected boolean isProperDefinition(DetailAST ident, DetailAST ast) {
            final String nameToFind = ident.getText();
            return nameToFind.equals(ast.getText())
                    && CheckUtil.isBeforeInSource(ast, ident);
        }
    }

    /**
     * A frame initiated at method definition; holds a method definition token.
     */
    public static class MethodFrame extends AbstractFrame {

        /**
         * Creates method frame.
         *
         * @param parent parent frame.
         * @param ident  method name identifier token.
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
    public static class ConstructorFrame extends AbstractFrame {

        /**
         * Creates a constructor frame.
         *
         * @param parent parent frame.
         * @param ident  frame name ident.
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
    public static class ClassFrame extends AbstractFrame {

        /**
         * Set of idents of instance members declared in this frame.
         */
        private final Set<DetailAST> instanceMembers;
        /**
         * Set of idents of instance methods declared in this frame.
         */
        private final Set<DetailAST> instanceMethods;
        /**
         * Set of idents of variables declared in this frame.
         */
        private final Set<DetailAST> staticMembers;
        /**
         * Set of idents of static methods declared in this frame.
         */
        private final Set<DetailAST> staticMethods;

        /**
         * Creates new instance of ClassFrame.
         *
         * @param parent parent frame.
         * @param ident  frame name ident.
         */
        /* package */ ClassFrame(AbstractFrame parent, DetailAST ident) {
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
                final DetailAST mods = member.getParent().findFirstToken(TokenTypes.MODIFIERS);
                final boolean finalMod = mods.findFirstToken(TokenTypes.FINAL) != null;
                if (finalMod && isAstSimilar(member, instanceMember)) {
                    result = true;
                    break;
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
         * same name and number of parameters.
         *
         * @param set   the set of definitions.
         * @param ident the specified method call IDENT ast.
         * @return true if the set contains a definition with the
         *         same name and number of parameters.
         */
        private static boolean containsMethodDef(Set<DetailAST> set, DetailAST ident) {
            boolean result = false;
            for (DetailAST ast : set) {
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
         * @param ast   the ast of a method definition to compare with.
         * @return true if a method definition has the same name and number of parameters
         *         as the method call.
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
    public static class AnonymousClassFrame extends ClassFrame {

        /**
         * The name of the frame.
         */
        private final String frameName;

        /**
         * Creates anonymous class frame.
         *
         * @param parent    parent frame.
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
    public static class BlockFrame extends AbstractFrame {

        /**
         * Creates block frame.
         *
         * @param parent parent frame.
         * @param ident  ident frame name ident.
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
    public static class CatchFrame extends AbstractFrame {

        /**
         * Creates catch frame.
         *
         * @param parent parent frame.
         * @param ident  ident frame name ident.
         */
        protected CatchFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        public FrameType getType() {
            return FrameType.CATCH_FRAME;
        }

    }

    /**
     * A frame initiated on entering a for block; holds local for variable names.
     */
    public static class ForFrame extends AbstractFrame {

        /**
         * Creates for frame.
         *
         * @param parent parent frame.
         * @param ident  ident frame name ident.
         */
        protected ForFrame(AbstractFrame parent, DetailAST ident) {
            super(parent, ident);
        }

        @Override
        public FrameType getType() {
            return FrameType.FOR_FRAME;
        }

    }
}
