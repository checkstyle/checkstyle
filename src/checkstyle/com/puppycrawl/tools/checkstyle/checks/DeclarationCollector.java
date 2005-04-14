////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Abstract class for chekcs which need to collect information about
 * declared members/parameters/variables.
 *
 * @author o_sukhodolsky
 */
public abstract class DeclarationCollector extends Check
{
    /** Stack of variable declaration frames. */
    private FrameStack mFrames;

    /** {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
        mFrames = new FrameStack();
    }

    /** {@inheritDoc} */
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.PARAMETER_DEF :
        case TokenTypes.VARIABLE_DEF : {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            this.mFrames.current().addName(nameAST.getText());
            break;
        }
        case TokenTypes.CLASS_DEF : {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            this.mFrames.current().addName(nameAST.getText());
            this.mFrames.enter(new ClassFrame());
            break;
        }
        case TokenTypes.SLIST :
            this.mFrames.enter(new BlockFrame());
            break;
        case TokenTypes.METHOD_DEF :
        case TokenTypes.CTOR_DEF :
            this.mFrames.enter(new MethodFrame());
            break;
        default:
            // do nothing
        }
    }


    /** {@inheritDoc} */
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF :
        case TokenTypes.INTERFACE_DEF :
        case TokenTypes.ENUM_DEF :
        case TokenTypes.ANNOTATION_DEF :
        case TokenTypes.SLIST :
        case TokenTypes.METHOD_DEF :
        case TokenTypes.CTOR_DEF :
            this.mFrames.leave();
            break;
        default :
            // do nothing
        }
    }

    /**
     * Check if given name is a name for declafred variable/parameter/member in
     * current environment.
     * @param aName a name to check
     * @return true is the given name is declare one.
     */
    protected final boolean isDeclared(String aName)
    {
        return (null != mFrames.findFrame(aName));
    }

    /**
     * Check if given name is a name for class field in current environment.
     * @param aName a name to check
     * @return true is the given name is name of method or member.
     */
    protected final boolean isClassField(String aName)
    {
        return (mFrames.findFrame(aName) instanceof ClassFrame);
    }
}

/**
 * A declaration frame.
 * @author Stephen Bloch
 * June 19, 2003
 */
abstract class LexicalFrame
{
    /** Set of name of variables declared in this frame. */
    private HashSet mVarNames;

    /** constructor -- invocable only via super() from subclasses */
    protected LexicalFrame()
    {
        mVarNames = new HashSet();
    }

    /** add a name to the frame.
     * @param aNameToAdd  the name we're adding
     */
    void addName(String aNameToAdd)
    {
        this.mVarNames.add(aNameToAdd);
    }

    /** check whether the frame contains a given name.
     * @param aNameToFind  the name we're looking for
     * @return whether it was found
     */
    boolean contains(String aNameToFind)
    {
        return this.mVarNames.contains(aNameToFind);
    }
}

/**
 * The global frame; should hold only class names.
 * @author Stephen Bloch
 */
class GlobalFrame extends LexicalFrame
{
    /** Create new instance of hte frame. */
    GlobalFrame()
    {
        super();
    }
}

/**
 * A frame initiated at method definition; holds parameter names.
 * @author Stephen Bloch
 */
class MethodFrame extends LexicalFrame
{
    /** Create new instance of hte frame. */
    MethodFrame()
    {
        super();
    }
}

/**
 * A frame initiated at class definition; holds instance variable
 * names.  For the present, I'm not worried about other class names,
 * method names, etc.
 * @author Stephen Bloch
 */
class ClassFrame extends LexicalFrame
{
    /** Create new instance of hte frame. */
    ClassFrame()
    {
        super();
    }
}

/**
 * A frame initiated on entering a statement list; holds local variable
 * names.  For the present, I'm not worried about other class names,
 * method names, etc.
 * @author Stephen Bloch
 */
class BlockFrame extends LexicalFrame
{
    /** Create new instance of hte frame. */
    BlockFrame()
    {
        super();
    }
}

/**
 * A stack of LexicalFrames.  Standard issue....
 * @author Stephen Bloch
 */
class FrameStack
{
    /** List of lexical frames. */
    private LinkedList mFrameList;

    /** Creates an empty FrameStack. */
    FrameStack()
    {
        mFrameList = new LinkedList();
        this.enter(new GlobalFrame());
    }

    /**
     * Enter a scope, i.e. push a frame on the stack.
     * @param aNewFrame  the already-created frame to push
     */
    void enter(LexicalFrame aNewFrame)
    {
        mFrameList.addFirst(aNewFrame);
    }

    /** Leave a scope, i.e. pop a frame from the stack.  */
    void leave()
    {
        mFrameList.removeFirst();
    }

    /**
     * Get current scope, i.e. top frame on the stack.
     * @return the current frame
     */
    LexicalFrame current()
    {
        return (LexicalFrame) mFrameList.getFirst();
    }

    /**
     * Search this and containing frames for a given name.
     * @param aNameToFind the name we're looking for
     * @return the frame in which it was found, or null if not found
     */
    LexicalFrame findFrame(String aNameToFind)
    {
        final Iterator it = mFrameList.iterator();
        while (it.hasNext()) {
            final LexicalFrame thisFrame = (LexicalFrame) it.next();
            if (thisFrame.contains(aNameToFind)) {
                return thisFrame;
            }
        }
        return null;
    }
}
