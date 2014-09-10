////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Deque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Abstract class for checks which need to collect information about
 * declared members/parameters/variables.
 *
 * @author o_sukhodolsky
 */
public abstract class DeclarationCollector extends Check
{
    /**
     * Tree of all the parsed frames
     */
    private Map<DetailAST, LexicalFrame> mFrames;

    /**
     * Frame for the currently processed AST
     */
    private LexicalFrame mCurrent;

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final Deque<LexicalFrame> aFrameStack = Lists.newLinkedList();
        aFrameStack.add(new GlobalFrame());

        mFrames = Maps.newHashMap();

        DetailAST curNode = aRootAST;
        while (curNode != null) {
            collectDeclarations(aFrameStack, curNode);
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                endCollectingDeclarations(aFrameStack, curNode);
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }
            curNode = toVisit;
        }
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF :
        case TokenTypes.INTERFACE_DEF :
        case TokenTypes.ENUM_DEF :
        case TokenTypes.ANNOTATION_DEF :
        case TokenTypes.SLIST :
        case TokenTypes.METHOD_DEF :
        case TokenTypes.CTOR_DEF :
            this.mCurrent = this.mFrames.get(aAST);
            break;
        default :
            // do nothing
        }
    } // end visitToken

    /**
     * Parse the next AST for declarations
     *
     * @param aFrameStack Stack containing the FrameTree being built
     * @param aAST AST to parse
     */
    private void collectDeclarations(Deque<LexicalFrame> aFrameStack,
        DetailAST aAST)
    {
        final LexicalFrame frame = aFrameStack.peek();
        switch (aAST.getType()) {
        case TokenTypes.VARIABLE_DEF :  {
            final String name =
                    aAST.findFirstToken(TokenTypes.IDENT).getText();
            if (frame instanceof ClassFrame) {
                final DetailAST mods =
                    aAST.findFirstToken(TokenTypes.MODIFIERS);
                if (ScopeUtils.inInterfaceBlock(aAST)
                        || mods.branchContains(TokenTypes.LITERAL_STATIC))
                {
                    ((ClassFrame) frame).addStaticMember(name);
                }
                else {
                    ((ClassFrame) frame).addInstanceMember(name);
                }
            }
            else {
                frame.addName(name);
            }
            break;
        }
        case TokenTypes.PARAMETER_DEF : {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            frame.addName(nameAST.getText());
            break;
        }
        case TokenTypes.CLASS_DEF :
        case TokenTypes.INTERFACE_DEF :
        case TokenTypes.ENUM_DEF :
        case TokenTypes.ANNOTATION_DEF : {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            frame.addName(nameAST.getText());
            aFrameStack.addFirst(new ClassFrame(frame));
            break;
        }
        case TokenTypes.SLIST :
            aFrameStack.addFirst(new BlockFrame(frame));
            break;
        case TokenTypes.METHOD_DEF : {
            final String name = aAST.findFirstToken(TokenTypes.IDENT).getText();
            if (frame instanceof ClassFrame) {
                final DetailAST mods =
                    aAST.findFirstToken(TokenTypes.MODIFIERS);
                if (mods.branchContains(TokenTypes.LITERAL_STATIC)) {
                    ((ClassFrame) frame).addStaticMember(name);
                }
                else {
                    ((ClassFrame) frame).addInstanceMember(name);
                }
            }
        }
        case TokenTypes.CTOR_DEF :
            aFrameStack.addFirst(new MethodFrame(frame));
            break;
        default:
            // do nothing
        }
    }


    /**
     * End parsing of the AST for declarations.
     *
     * @param aFrameStack Stack containing the FrameTree being built
     * @param aAST AST that was parsed
     */
    private void endCollectingDeclarations(Queue<LexicalFrame> aFrameStack,
        DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF :
        case TokenTypes.INTERFACE_DEF :
        case TokenTypes.ENUM_DEF :
        case TokenTypes.ANNOTATION_DEF :
        case TokenTypes.SLIST :
        case TokenTypes.METHOD_DEF :
        case TokenTypes.CTOR_DEF :
            this.mFrames.put(aAST, aFrameStack.poll());
            break;
        default :
            // do nothing
        }
    }

    /**
     * Check if given name is a name for class field in current environment.
     * @param aName a name to check
     * @return true is the given name is name of method or member.
     */
    protected final boolean isClassField(String aName)
    {
        final LexicalFrame frame = findFrame(aName);
        return (frame instanceof ClassFrame)
                && ((ClassFrame) frame).hasInstanceMember(aName);
    }

    /**
     * Find frame containing declaration
     * @param aName name of the declaration to find
     * @return LexicalFrame containing declaration or null
     */
    private LexicalFrame findFrame(String aName)
    {
        if (mCurrent != null) {
            return mCurrent.getIfContains(aName);
        }
        else {
            return null;
        }
    }

    /**
     * A declaration frame.
     * @author Stephen Bloch
     * June 19, 2003
     */
    private abstract static class LexicalFrame
    {
        /** Set of name of variables declared in this frame. */
        private final Set<String> mVarNames;
        /**
         * Parent frame.
         */
        private final LexicalFrame mParent;

        /**
         * constructor -- invokable only via super() from subclasses
         *
         * @param aParent parent frame
         */
        protected LexicalFrame(LexicalFrame aParent)
        {
            mParent = aParent;
            mVarNames = Sets.newHashSet();
        }

        /** add a name to the frame.
         * @param aNameToAdd  the name we're adding
         */
        void addName(String aNameToAdd)
        {
            mVarNames.add(aNameToAdd);
        }

        /** check whether the frame contains a given name.
         * @param aNameToFind  the name we're looking for
         * @return whether it was found
         */
        boolean contains(String aNameToFind)
        {
            return mVarNames.contains(aNameToFind);
        }

        /** check whether the frame contains a given name.
         * @param aNameToFind  the name we're looking for
         * @return whether it was found
         */
        LexicalFrame getIfContains(String aNameToFind)
        {
            if (contains(aNameToFind)) {
                return this;
            }
            else if (mParent != null) {
                return mParent.getIfContains(aNameToFind);
            }
            else {
                return null;
            }
        }
    }

    /**
     * The global frame; should hold only class names.
     * @author Stephen Bloch
     */
    private static class GlobalFrame extends LexicalFrame
    {

        /**
         * Constructor for the root of the FrameTree
         */
        protected GlobalFrame()
        {
            super(null);
        }
    }

    /**
     * A frame initiated at method definition; holds parameter names.
     * @author Stephen Bloch
     */
    private static class MethodFrame extends LexicalFrame
    {
        /**
         * @param aParent parent frame
         */
        protected MethodFrame(LexicalFrame aParent)
        {
            super(aParent);
        }
    }

    /**
     * A frame initiated at class definition; holds instance variable
     * names.  For the present, I'm not worried about other class names,
     * method names, etc.
     * @author Stephen Bloch
     */
    private static class ClassFrame extends LexicalFrame
    {
        /** Set of name of instance members declared in this frame. */
        private final Set<String> mInstanceMembers;
        /** Set of name of variables declared in this frame. */
        private final Set<String> mStaticMembers;

        /**
         * Creates new instance of ClassFrame
         * @param aParent parent frame
         */
        public ClassFrame(LexicalFrame aParent)
        {
            super(aParent);
            mInstanceMembers = Sets.newHashSet();
            mStaticMembers = Sets.newHashSet();
        }

        /**
         * Adds static member's name.
         * @param aName a name of static member of the class
         */
        public void addStaticMember(final String aName)
        {
            mStaticMembers.add(aName);
        }

        /**
         * Adds instance member's name.
         * @param aName a name of instance member of the class
         */
        public void addInstanceMember(final String aName)
        {
            mInstanceMembers.add(aName);
        }

        /**
         * Checks if a given name is a known instance member of the class.
         * @param aName a name to check
         * @return true is the given name is a name of a known
         *         instance member of the class
         */
        public boolean hasInstanceMember(final String aName)
        {
            return mInstanceMembers.contains(aName);
        }

        @Override
        boolean contains(String aNameToFind)
        {
            return super.contains(aNameToFind)
                    || mInstanceMembers.contains(aNameToFind)
                    || mStaticMembers.contains(aNameToFind);
        }
    }

    /**
     * A frame initiated on entering a statement list; holds local variable
     * names.  For the present, I'm not worried about other class names,
     * method names, etc.
     * @author Stephen Bloch
     */
    private static class BlockFrame extends LexicalFrame
    {

        /**
         * @param aParent parent frame
         */
        protected BlockFrame(LexicalFrame aParent)
        {
            super(aParent);
        }
    }
}
