////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.commons.collections.ArrayStack;

// TODO: allow preset indentation styles (IE... GNU style, Sun style, etc...)?

// TODO: optionally make imports (and other?) statements required to start 
//   line? -- but maybe this should be a different check

// TODO: optionally allow array children, throws clause, etc... 
//   to be of any indentation > required, for emacs-style indentation

// TODO: this is not illegal, but probably should be:
//        myfunc3(11, 11, Integer.
//            getInteger("mytest").intValue(),  // this should be in 4 more
//            11);

// TODO: any dot-based indentation doesn't work (at least not yet...) the
//  problem is that we don't know which way an expression tree will be built
//  and with dot trees, they are built backwards.  This means code like
//  
//  org.blah.mystuff
//      .myclass.getFactoryObject()
//          .objFunc().otherMethod();
// and
//  return ((MethodCallHandler) parent)
//      .findContainingMethodCall(this);
//  is all checked at the level of the first line.  Simple dots are actually
// checked but the method call handler will have to be changed drastically
// to fix the above...


/**
 * Checks correct indentation of Java Code.
 *
 * <p>
 * The basic idea behind this is that while
 * pretty printers are sometimes convienent for bulk reformats of
 * legacy code, they often either aren't configurable enough or
 * just can't anticipate how format should be done.  Sometimes this is
 * personal preference, other times it is practical experience.  In any
 * case, this check should just ensure that a minimal set of indentation
 * rules are followed.
 * </p>
 *
 * <p>
 * Implementation --
 *  Basically, this check requests visitation for all handled token 
 *  types (those tokens registered in the HandlerFactory).  When visitToken
 *  is called, a new ExpressionHandler is created for the AST and pushed 
 *  onto the mHandlers stack.  The new handler then checks the indentation
 *  for the currently visiting AST.  When leaveToken is called, the
 *  ExpressionHandler is popped from the stack.
 * </p>
 *
 * <p>
 *  While on the stack the ExpressionHandler can be queried for the
 *  indentation level it suggests for children as well as for other
 *  values.
 * </p>
 *
 * <p>
 *  While an ExpressionHandler checks the indentation level of its own
 *  AST, it typically also checks surrounding ASTs.  For instance, a
 *  while loop handler checks the while loop as well as the braces
 *  and immediate children.
 * </p>
 * Created on November 2, 2002, 10:59 PM
 * <p>
 * <pre>
 *   - handler class -to-> ID mapping kept in Map
 *   - parent passed in during construction
 *   - suggest child indent level 
 *   - allows for some tokens to be on same line (ie inner classes OBJBLOCK)  
 *     and not increase indentation level
 *   - looked at using double dispatch for suggestedChildLevel(), but it  
 *     doesn't seem worthwhile, at least now
 *   - both tabs and spaces are considered whitespace in front of the line... 
 *     tabs are converted to spaces 
 *   - block parents with parens -- for, while, if, etc... -- are checked that 
 *     they match the level of the parent
 * </pre>
 *
 * @author  jrichard
 */
public class IndentationCheck extends Check 
{
    /** children checked by parent handlers */
    private static final int[] CHECKED_CHILDREN = new int[] {
        TokenTypes.VARIABLE_DEF, 
        TokenTypes.EXPR, 
        TokenTypes.OBJBLOCK, 
        TokenTypes.LITERAL_BREAK
    };
    
    
    /** how many tabs or spaces to use */
    private int mIndentationAmount = 4;

    /** how much to indent a case label */
    private int mCaseIndentationAmount = mIndentationAmount;
    
    /** how far brace should be indented when on next line */
    private int mBraceAdjustment = 0;

    /** handlers currently in use */
    private ArrayStack mHandlers = new ArrayStack();
    
    /** factory from which handlers are distributed */
    private HandlerFactory mHandlerFactory = new HandlerFactory();
    
    
    
    /** factory for handlers -- looks up constructor via reflection */
    private class HandlerFactory 
    {
        /** registered handlers */
        private Map mTypeHandlers = new HashMap();
        
        /** 
         * registers a handler
         *
         * @param aType   type from TokenTypes
         * @param aHandlerClass  the handler to register
         */
        private void register(int aType, Class aHandlerClass) 
        {
            try {
                Constructor ctor = aHandlerClass.getConstructor(new Class[] {
                    IndentationCheck.class,
                    DetailAST.class,            // current AST
                    ExpressionHandler.class     // parent
                });
                mTypeHandlers.put(new Integer(aType), ctor);
            } 
            catch (NoSuchMethodException e) {
                System.out.println("couldn't find ctor for " + aHandlerClass);
                System.out.println("methods are: " + Arrays.asList(
                    aHandlerClass.getConstructors()).toString());
                e.printStackTrace();
            } 
            catch (SecurityException e) {
                System.out.println("couldn't find ctor for " + aHandlerClass);
                e.printStackTrace();
            }
        }
        
        /** creates a HandlerFactory */
        private HandlerFactory()
        {
            register(TokenTypes.CASE_GROUP, CaseHandler.class);
            register(TokenTypes.LITERAL_SWITCH, SwitchHandler.class);
            register(TokenTypes.SLIST, SlistHandler.class);
            register(TokenTypes.PACKAGE_DEF, PackageDefHandler.class);
            register(TokenTypes.LITERAL_ELSE, ElseHandler.class);
            register(TokenTypes.LITERAL_IF, IfHandler.class);
            register(TokenTypes.LITERAL_TRY, TryHandler.class);
            register(TokenTypes.LITERAL_CATCH, CatchHandler.class);
            register(TokenTypes.LITERAL_FINALLY, FinallyHandler.class);
            register(TokenTypes.LITERAL_DO, DoWhileHandler.class);
            register(TokenTypes.LITERAL_WHILE, WhileHandler.class);
            register(TokenTypes.LITERAL_FOR, ForHandler.class);
            register(TokenTypes.METHOD_DEF, MethodDefHandler.class);
            register(TokenTypes.CTOR_DEF, MethodDefHandler.class);
            register(TokenTypes.CLASS_DEF, ClassDefHandler.class);
            register(TokenTypes.OBJBLOCK, ObjectBlockHandler.class);
            register(TokenTypes.INTERFACE_DEF, ClassDefHandler.class);
            register(TokenTypes.IMPORT, ImportHandler.class);
            register(TokenTypes.ARRAY_INIT, ArrayInitHandler.class);
            register(TokenTypes.METHOD_CALL, MethodCallHandler.class);
            register(TokenTypes.CTOR_CALL, MethodCallHandler.class);
            register(TokenTypes.LABELED_STAT, LabelHandler.class);
            register(TokenTypes.LABELED_STAT, LabelHandler.class);
            register(TokenTypes.STATIC_INIT, StaticInitHandler.class);
        }

        /**
         * returns true if this type (form TokenTypes) is handled
         *
         * @param aType type from TokenTypes
         * @return true if handler is registered, false otherwise
         */
        private boolean isHandledType(int aType) 
        {
            Set typeSet = mTypeHandlers.keySet();
            return typeSet.contains(new Integer(aType));
        }

        /** 
         * gets list of registered handler types
         *
         * @return int[] of TokenType types
         */
        private int[] getHandledTypes() 
        {
            Set typeSet = mTypeHandlers.keySet();
            int[] types = new int[typeSet.size()];
            int index = 0;
            for (Iterator i = typeSet.iterator(); i.hasNext(); index++) {
                types[index] = ((Integer) i.next()).intValue();
            }
            
            return types;
        }


        /** 
         * get the handler for an AST 
         * @param aAst  ast to handle
         * @param aParent  the handler parent of this AST
         * @return the ExpressionHandler for aAst
         */
        private ExpressionHandler getHandler(DetailAST aAst, 
            ExpressionHandler aParent) 
        {
            int type = aAst.getType();            
            
            ExpressionHandler expHandler = null;
            try {
                Constructor handlerCtor = (Constructor) mTypeHandlers.get(
                    new Integer(type));
                if (handlerCtor != null) {
                    expHandler = (ExpressionHandler) handlerCtor.newInstance(
                        new Object[] {
                            IndentationCheck.this,
                            aAst,
                            aParent
                        }
                    );
                }
            } 
            catch (InstantiationException e) {
                System.out.println("couldn't instantiate constructor for " 
                    + aAst);
                e.printStackTrace();
            } 
            catch (IllegalAccessException e) {
                System.out.println("couldn't access constructor for " + aAst);
                e.printStackTrace();
            } 
            catch (InvocationTargetException e) {
                System.out.println("couldn't instantiate constructor for " 
                    + aAst);
                e.printStackTrace();
            }
            if (expHandler == null) {
                System.err.println("no handler for type " + type);
            }
            return expHandler;
        }
        
    }

    /**
     * Represents a set of lines.
     */
    private static class LineSet
    {
        /** maps line numbers to it's start column */
        private SortedMap mLines = new TreeMap();

        Integer getStartColumn(Integer aLineNum)
        {
            Integer colNum = (Integer) mLines.get(aLineNum);
            return colNum;
        }

        int getStartColumn(int aLineNum)
        {
            Integer colNum = (Integer) mLines.get(new Integer(aLineNum));
            return colNum.intValue();
        }
        
        int firstLineCol()
        {
            Object firstLineKey = mLines.firstKey();
            return ((Integer) mLines.get(firstLineKey)).intValue();
        }
        
        int firstLine()
        {
            return ((Integer) mLines.firstKey()).intValue();
        }
        
        int lastLine() 
        {
            return ((Integer) mLines.lastKey()).intValue();
        }
        
        void addLineAndCol(Integer aLineNum, int aCol)
        {
            mLines.put(aLineNum, new Integer(aCol));
        }
        
        boolean isEmpty()
        {
            return mLines.isEmpty();
        }
    }

    /** when a field field is not initialize, it should be set to this value */
    private static final int UNINITIALIZED = Integer.MIN_VALUE;

    /** handler for one AST type */
    private abstract class ExpressionHandler 
    {
        /** the AST which is handled by this handler */
        protected DetailAST mMainAst;
        
        /** name used during output to user */
        protected String mTypeName;
        
        /** containing AST handler */
        protected ExpressionHandler mParent;
        
        /** indentation amount for this handler */
        private int mLevel = UNINITIALIZED;
        
        private ExpressionHandler(String aTypeName, DetailAST aExpr, 
            ExpressionHandler aParent) 
        {
            mTypeName = aTypeName;
            mMainAst = aExpr;
            mParent = aParent;
        }

        /** 
         * the indentation amount for this handler
         *
         * @return the expected indentation amount
         */
        public int getLevel() 
        {
            if (mLevel == UNINITIALIZED) {
                mLevel = getLevelImpl();
            }
            return mLevel;
        }

        /** implementation of getLevel, for performance */
        protected int getLevelImpl()
        {
            return mParent.suggestedChildLevel(this);
        }
        
        /** 
         * indentation suggested for a child, children don't have
         * to respect this, but most do
         *
         * @param aChild child AST (so suggestion level can differ
         *    based on child type)
         * @return suggested indentation for child
         */
        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            return getLevel() + mIndentationAmount;
        }

        /** 
         * parent handler
         * 
         * @return parent handler
         */
        public ExpressionHandler getParent() 
        {
            return mParent;
        }
        
        protected void logError(DetailAST aAst, String aSubtypeName, 
            int aActualLevel) 
        {
            // TODO: i18n
            String typeStr = (aSubtypeName == "" ? "" : (" " + aSubtypeName));
            log(aAst.getLineNo(), mTypeName + typeStr 
                + " at indentation level " 
                + aActualLevel + " not at correct indentation, " + getLevel());
        }
        
        protected void logError(DetailAST aAst, String aSubtypeName) 
        {
            String typeStr = (aSubtypeName == "" ? "" : (" " + aSubtypeName));
            log(aAst.getLineNo(), mTypeName + typeStr 
                + " at indentation level not at correct indentation, " 
                + getLevel());
        }
        
        protected boolean startsLine(DetailAST aAst) 
        {
            return getLineStart(aAst) == expandedTabsColumnNo(aAst);
        }
        
        protected boolean areOnSameLine(DetailAST aAst1, DetailAST aAst2) 
        {
            return aAst1 != null && aAst2 != null 
                && aAst1.getLineNo() == aAst2.getLineNo();
        }
        
        protected boolean atLevelOrGreater(DetailAST aParent) 
        {
            if (expandedTabsColumnNo(aParent) < getLevel()) {
                return false;
            }

            for (DetailAST child = aParent.getLastChild(); child != null;
                child = child.getPreviousSibling())
            {
                if (!atLevelOrGreater(child)) {
                    return false;
                }
            }
            return true;
        }

        protected int getLineStart(DetailAST aAst) 
        {
            // TODO: this breaks indentation -- add to tests
            String line = IndentationCheck.this.getLines()[
                aAst.getLineNo() - 1];
            return getLineStart(line);
        }        
        
        // TODO: this whole checking of consecuitive/expression line indents is
        // smelling pretty bad... and is in serious need of pruning.  But, I 
        // want to finish the invalid tests before I start messing around with 
        // it.
                
        protected void checkLinesIndent(int aStartLine, int aEndLine, 
            int aIndentLevel) 
        {
            // check first line
            checkSingleLine(aStartLine, aIndentLevel);
            
            // check following lines
            aIndentLevel += mIndentationAmount;
            for (int i = aStartLine + 1; i <= aEndLine; i++) {
                checkSingleLine(i, aIndentLevel);
            }
        }
        
        private void checkSingleLine(int aLineNum, int aIndentLevel) 
        {
            String line = IndentationCheck.this.getLines()[aLineNum - 1];
            int start = getLineStart(line);
            if (start < aIndentLevel) {
                log(aLineNum, mTypeName + " child at indentation level " 
                    + start + " not at correct indentation, " + aIndentLevel);
            }    
        }
        
        protected int getLineStart(String aLine) 
        {   
            for (int start = 0; start < aLine.length(); start++) {
                char c = aLine.charAt(start);
                
                if (!Character.isWhitespace(c)) {
                    return Utils.lengthExpandedTabs(aLine, start, getTabWidth());
                }
            }
            return 0;
        }

        // TODO: allowNesting either shouldn't be allowed with 
        //  firstLineMatches, or I should change the firstLineMatches logic 
        //  so it doesn't match if the first line is nested
        protected void checkChildren(DetailAST aParent, int[] aTokenTypes, 
            int aStartLevel, 
            boolean aFirstLineMatches, boolean aAllowNesting) 
        {
            Arrays.sort(aTokenTypes);
            for (DetailAST child = (DetailAST) aParent.getFirstChild(); 
                    child != null; 
                    child = (DetailAST) child.getNextSibling()) 
            {
                if (Arrays.binarySearch(aTokenTypes, child.getType()) >= 0) {
                    checkExpressionSubtree(child, aStartLevel, 
                        aFirstLineMatches, aAllowNesting);
                }
            }
        }

        protected void checkExpressionSubtree(DetailAST aTree, int aLevel) 
        {
            checkExpressionSubtree(aTree, aLevel, false, false);
        }
        
        protected void checkExpressionSubtree(
            DetailAST aTree, 
            int aLevel, 
            boolean aFirstLineMatches, 
            boolean aAllowNesting
        )
        {
            LineSet subtreeLines = new LineSet();
            if (aFirstLineMatches && !aAllowNesting) {
                int firstLine = getFirstLine(Integer.MAX_VALUE, aTree);
                subtreeLines.addLineAndCol(new Integer(firstLine), 
                    getLineStart(
                        IndentationCheck.this.getLines()[firstLine - 1]));
            }
            findSubtreeLines(subtreeLines, aTree, aAllowNesting);
            
            checkLinesIndent(subtreeLines, aLevel, aFirstLineMatches);
        }
        
        protected void checkLinesIndent(LineSet aLines, 
                                        int aIndentLevel, 
                                        boolean aFirstLineMatches) 
        {
            if (aLines.isEmpty()) {
                return;
            }
            
            // check first line
            int startLine = aLines.firstLine();
            int endLine = aLines.lastLine();
            int startCol = aLines.firstLineCol();
            
            int realStartCol = getLineStart(
                IndentationCheck.this.getLines()[startLine - 1]);
            
            if (realStartCol == startCol) {
                checkSingleLine(startLine, startCol, aIndentLevel, 
                    aFirstLineMatches);
                
                // if first line starts the line, following lines are indented 
                // one level; but if the first line of this expression is 
                // nested with the previous expression (which is assumed if it 
                // doesn't start the line) then don't indent more, the first 
                // indentation is absorbed by the nesting
                
            } 
            
            if (aFirstLineMatches || startLine > mMainAst.getLineNo()) {
                aIndentLevel += mIndentationAmount;
            }

            
            // check following lines
            for (int i = startLine + 1; i <= endLine; i++) {
                Integer col = aLines.getStartColumn(new Integer(i));
                // startCol could be null if this line didn't have an 
                // expression that was required to be checked (it could be 
                // checked by a child expression)

                
                // TODO: not sure if this does anything, look at taking it out

                // TODO: we can check here if this line starts or the previous
                // line ends in a dot.  If so, we should increase the indent.
            
                // TODO: check if -2 is possible here?  but unlikely to be a 
                // problem...
                String thisLine = IndentationCheck.this.getLines()[i - 1];
                String prevLine = IndentationCheck.this.getLines()[i - 2];
                if (thisLine.matches("^\\s*\\.") 
                    || prevLine.matches("\\.\\s*$")) 
                {
                    aIndentLevel += mIndentationAmount;
                }
                
                if (col != null) { 
                    checkSingleLine(i, col.intValue(), aIndentLevel, false);
                }
            }
        }
        
        private void checkSingleLine(int aLineNum, int aColNum, 
            int aIndentLevel, boolean aMustMatch) 
        {
            String line = IndentationCheck.this.getLines()[aLineNum - 1];
            int start = getLineStart(line);
            // if must match is set, it is an error if the line start is not
            // at the correct indention level; otherwise, it is an only an 
            // error if this statement starts the line and it is less than
            // the correct indentation level
            if (aMustMatch ? start != aIndentLevel
                : aColNum == start && start < aIndentLevel) 
            {
                // TODO: i18n or use logError
                log(aLineNum, mTypeName + " child at indentation level "
                    + start + " not at correct indentation, " + aIndentLevel);
            }
        }
        
        private int getFirstLine(int aStartLine, DetailAST aTree) 
        {
            // find line for this node
            // TODO: getLineNo should probably not return < 0, but it is for 
            // the interface methods... I should ask about this

            int currLine = aTree.getLineNo();
            if (currLine < aStartLine) {
                aStartLine = currLine;
            } 
            
            // check children
            for (DetailAST node = (DetailAST) aTree.getFirstChild(); 
                node != null; 
                node = (DetailAST) node.getNextSibling()) 
            {
                aStartLine = getFirstLine(aStartLine, node);
            }   
            
            return aStartLine;
        }

        protected int expandedTabsColumnNo(DetailAST aAst)
        {
            String line = 
                IndentationCheck.this.getLines()[aAst.getLineNo() - 1];

            return Utils.lengthExpandedTabs(line, aAst.getColumnNo(), 
                getTabWidth());
        }
        
        protected void findSubtreeLines(LineSet aLines, DetailAST aTree, 
            boolean aAllowNesting) 
        {
            // find line for this node
            // TODO: getLineNo should probably not return < 0, but it is for 
            // the interface methods... I should ask about this
            if (mHandlerFactory.isHandledType(aTree.getType()) 
                || aTree.getLineNo() < 0) 
            {
                return;
            }
            
            // TODO: the problem with this is that not all tree tokens actually
            // have the right column number -- I should get a list of these 
            // and verify that checking nesting this way won't cause problems
//            if (allowNesting && tree.getColumnNo() != getLineStart(tree)) {
//                return;
//            }

            Integer lineNum = new Integer(aTree.getLineNo());
            Integer colNum = aLines.getStartColumn(lineNum);
            
            int thisLineColumn = expandedTabsColumnNo(aTree);
            if (colNum == null) {
                aLines.addLineAndCol(lineNum, thisLineColumn);
            } 
            else {
                if (expandedTabsColumnNo(aTree) < colNum.intValue()) {
                    aLines.addLineAndCol(lineNum, thisLineColumn);
                }
            }
            
            // check children
            for (DetailAST node = (DetailAST) aTree.getFirstChild(); 
                node != null; 
                node = (DetailAST) node.getNextSibling()) 
            {
                findSubtreeLines(aLines, node, aAllowNesting);
            }
        }
        
        protected void checkModifiers() 
        {
            DetailAST modifiers = mMainAst.findFirstToken(TokenTypes.MODIFIERS);
            for (DetailAST modifier = (DetailAST) modifiers.getFirstChild(); 
                    modifier != null; 
                    modifier = (DetailAST) modifier.getNextSibling()) 
            {
                /*
                if (!areOnSameLine(modifier, prevExpr)) {
                    continue;
                }
                 */
                if (startsLine(modifier) 
                    && expandedTabsColumnNo(modifier) != getLevel()) 
                {
                    logError(modifier, "modifier", 
                        expandedTabsColumnNo(modifier));
                }
            }
        }
        
        
        public abstract void checkIndentation();
        
    }
    
    
    // the "block" handler classes use a common superclass BlockParentHandler, 
    //  employing the Template Method pattern.  
    //  - template method to get the lcurly
    //  - template method to get the rcurly
    //  - if curlys aren't present, then template method to get expressions 
    //    is called
    //  - now all the repetitous code which checks for BOL, if curlys are on 
    //    same line, etc...
    //    can be collapsed into  the superclass
    // 
    // handler for parents of blocks ('if', 'else', 'while', etc...)
    private class BlockParentHandler extends ExpressionHandler 
    {
        public BlockParentHandler(String aName, DetailAST aAst, 
            ExpressionHandler aParent) 
        {
            super(aName, aAst, aParent);
        }
        
        protected DetailAST getToplevelAST() 
        {
            return mMainAst;
        }

        protected void checkToplevelToken() 
        {
            DetailAST toplevel = getToplevelAST();
            
            if (toplevel == null 
                || expandedTabsColumnNo(toplevel) == getLevel()) 
            {
                return;
            }
            if (!toplevelMustStartLine() && !startsLine(toplevel)) {
                return;
            }
            logError(toplevel, "", expandedTabsColumnNo(toplevel));
        }

        protected boolean toplevelMustStartLine() 
        {
            return true;
        }
        
        protected boolean hasCurlys() 
        {
            return getLCurly() != null && getRCurly() != null;
        }
        
        protected DetailAST getLCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.SLIST);
        }
        protected DetailAST getRCurly() 
        {
            DetailAST slist = mMainAst.findFirstToken(TokenTypes.SLIST);
            if (slist == null) {
                return null;
            }
                        
            return slist.findFirstToken(TokenTypes.RCURLY);
        }

        protected void checkLCurly() 
        {
            // the lcurly can either be at the correct indentation, or nested 
            // with a previous expression
            DetailAST lcurly = getLCurly();
                        
            if (lcurly == null
                || expandedTabsColumnNo(lcurly) == curlyLevel()
                || !startsLine(lcurly)) 
            {
                return;
            }
            
            logError(lcurly, "lcurly", expandedTabsColumnNo(lcurly));
        }
        
        private int curlyLevel()
        {
            return getLevel() + mBraceAdjustment;
        }
        
        
        protected boolean rcurlyMustStart() 
        {
            return true;
        }
        
        protected boolean childrenMayNest() 
        {
            return false;
        }
        
        protected void checkRCurly() 
        {
            // the rcurly can either be at the correct indentation, or 
            // on the same line as the lcurly
            DetailAST lcurly = getLCurly();
            DetailAST rcurly = getRCurly();
            if (rcurly == null
                || expandedTabsColumnNo(rcurly) == curlyLevel()
                || (!rcurlyMustStart() && !startsLine(rcurly))
                || areOnSameLine(rcurly, lcurly)) 
            {
                return;
            }
            logError(rcurly, "rcurly", expandedTabsColumnNo(rcurly));
        }

        protected DetailAST getNonlistChild() 
        {
            return (DetailAST) mMainAst.findFirstToken(
                TokenTypes.RPAREN).getNextSibling();            
        }
        
        private void checkNonlistChild() 
        {
            // TODO: look for SEMI and check for it here?
            DetailAST nonlist = getNonlistChild();
            if (nonlist == null) {
                return;
            }
            
            checkExpressionSubtree(nonlist, getLevel() + mIndentationAmount);
        }
        
        protected DetailAST getListChild() 
        {
            return mMainAst.findFirstToken(TokenTypes.SLIST);
        }
        
        protected DetailAST getRParen() 
        {
            return mMainAst.findFirstToken(TokenTypes.RPAREN);
        }
        
        protected DetailAST getLParen() 
        {
            return mMainAst.findFirstToken(TokenTypes.LPAREN);
        }
        
        protected void checkRParen() 
        {
            // the rcurly can either be at the correct indentation, or on 
            // the same line as the lcurly
            DetailAST rparen = getRParen();
            if (rparen == null 
                || expandedTabsColumnNo(rparen) == getLevel() 
                || !startsLine(rparen)) 
            {
                return;
            }
            logError(rparen, "rparen", expandedTabsColumnNo(rparen));
        }

        protected void checkLParen()
        {
            // the rcurly can either be at the correct indentation, or on the 
            // same line as the lcurly
            DetailAST lparen = getLParen();
            if (lparen == null 
                || expandedTabsColumnNo(lparen) == getLevel() 
                || !startsLine(lparen)) 
            {
                return;
            }
            logError(lparen, "lparen", expandedTabsColumnNo(lparen));
        }

        public void checkIndentation() 
        {
                
            checkToplevelToken();
            // seperate to allow for eventual configuration
            checkLParen();
            checkRParen();
            if (hasCurlys()) {
                checkLCurly();
                checkRCurly();
            }
            DetailAST listChild = getListChild();
            if (listChild != null) {
                // NOTE: switch statements usually don't have curlys
                if (!hasCurlys() || !areOnSameLine(getLCurly(), getRCurly())) {
                    checkChildren(listChild, CHECKED_CHILDREN, getLevel() 
                        + mIndentationAmount, true, childrenMayNest());
                }
            } 
            else {
                checkNonlistChild();
            }
        }
    }
    
    private class PrimordialHandler extends ExpressionHandler 
    {
        public PrimordialHandler() 
        {
            super(null, null, null);
        }
        public void checkIndentation() 
        {
            // nothing to check
        }
        
        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            return 0;
        }
        
        public int getLevelImpl() 
        {
            return 0;
        }
    }
    
    private class ArrayInitHandler extends BlockParentHandler 
    {
        public ArrayInitHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("array initialization", aAst, aParent);
        }
                
        public int getLevelImpl() 
        {
            DetailAST parentAST = mMainAst.getParent();
            int type = parentAST.getType();
            if (type == TokenTypes.LITERAL_NEW || type == TokenTypes.ASSIGN) {
                // note: assumes new or assignment is line to align with
                return getLineStart(parentAST);
            } 
            else {
                return mParent.getLevel();
            }
        }

        protected DetailAST getToplevelAST() 
        {
            return null;
        }
        
        protected DetailAST getLCurly()
        {
            return mMainAst;
        }
        
        protected DetailAST getRCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.RCURLY);
        }
        
        protected boolean rcurlyMustStart() 
        {
            return false;
        }
        
        protected boolean childrenMayNest() 
        {
            return true;
        }
        
        protected DetailAST getListChild() 
        {
            return mMainAst;
        }        
    }    
    
    
    private class PackageDefHandler extends ExpressionHandler 
    {
        public PackageDefHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("package def", aAst, aParent);
        }
        
        public void checkIndentation() 
        {
            int columnNo = expandedTabsColumnNo(mMainAst);
            if (columnNo != getLevel()) {
                logError(mMainAst, "", columnNo);
            }
            
            checkLinesIndent(mMainAst.getLineNo(), 
                mMainAst.findFirstToken(TokenTypes.SEMI).getLineNo(), 
                getLevel());
        }
    }
    

    
    private class LabelHandler extends ExpressionHandler 
    {
        private final int[] mLabelChildren = new int[] {
            TokenTypes.IDENT, 
        };
        
        public LabelHandler(DetailAST aExpr, ExpressionHandler aParent) 
        {
            super("label", aExpr, aParent);
        }
        
        public int getLevelImpl()
        {
            return super.getLevelImpl() - mIndentationAmount;
        }
        
        private void checkLabel() 
        {
            checkChildren(mMainAst, mLabelChildren, getLevel(), true, false);
        }
        
        public void checkIndentation() 
        {
            checkLabel();
            // need to check children (like 'block' parents do)
            DetailAST parent = (DetailAST) 
                mMainAst.getFirstChild().getNextSibling();
            checkExpressionSubtree(parent, getLevel() + mIndentationAmount, true, false);
        }        
    }    
    
    
    private class CaseHandler extends ExpressionHandler 
    {
        private final int[] mCaseChildren = new int[] {
            TokenTypes.LITERAL_CASE, 
            TokenTypes.LITERAL_DEFAULT
        };
        
        public CaseHandler(DetailAST aExpr, ExpressionHandler aParent) 
        {
            super("case", aExpr, aParent);
        }
        
        public int getLevelImpl()
        {
            return mParent.getLevel() + mCaseIndentationAmount;
        }
        
        private void checkCase() 
        {
            checkChildren(mMainAst, mCaseChildren, getLevel(), true, false);
        }
        
        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            return getLevel();
        }
        
        public void checkIndentation() 
        {
            checkCase();
        }        
    }    
    
    private class SwitchHandler extends BlockParentHandler 
    {
        public SwitchHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("switch", aAst, aParent);
        }
        
        protected DetailAST getLCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.LCURLY);
        }

        protected DetailAST getRCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.RCURLY);
        }

        protected DetailAST getListChild() 
        {
            // all children should be taken care of by case handler (plus 
            // there is no parent of just the cases, if checking is needed 
            // here in the future, an additional way beyond checkChildren() 
            // will have to be devised to get children)
            return null;
        }
        protected DetailAST getNonlistChild() 
        {
            return null;            
        }
        
        private void checkSwitchExpr() 
        {
            checkExpressionSubtree(
                (DetailAST) mMainAst.findFirstToken(TokenTypes.LPAREN).
                    getNextSibling(), 
                getLevel());
        }

        public void checkIndentation() 
        {
            checkSwitchExpr();
            
            super.checkIndentation();
        }
    }
    
    // handles lone blocks, and case statements, static blocks, init blocks    
    private class SlistHandler extends BlockParentHandler 
    {
        public SlistHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("block", aAst, aParent);
        }

        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            // this is:
            //  switch (var) {
            //     case 3: {
            //        break;
            //     }
            //  }
            //  ... the case SLIST is followed by a user-created SLIST and
            //  preceeded by a switch
            
            
            // if our parent is a block handler we want to be transparent
            if ((mParent instanceof BlockParentHandler 
                    && !(mParent instanceof SlistHandler))
                || (mParent instanceof CaseHandler 
                    && aChild instanceof SlistHandler)) 
            {
                return mParent.suggestedChildLevel(aChild);
            } 
            else {
                return super.suggestedChildLevel(aChild);
            }
        }
        
        protected DetailAST getNonlistChild() 
        {
            // blocks always have either block children or they are transparent
            // and aren't checking children at all.  In the later case, the 
            // superclass will want to check single children, so when it 
            // does tell it we have none.
            return null;
        }
        
        protected DetailAST getListChild() 
        {
            return mMainAst;
        }
        
        protected DetailAST getLCurly() 
        {
            return mMainAst;
        }
        
        protected DetailAST getRCurly()  
        {
            return mMainAst.findFirstToken(TokenTypes.RCURLY);
        }
        
        protected DetailAST getToplevelAST() 
        {
            return null;
            
            //            // TODO: STATIC_INIT has column num of lcurly... need
            //            //   to ask about this
            //            DetailAST mParent = mMainAst.getParent();
            //            if (mParent.getType() == TokenTypes.STATIC_INIT) {
            //                if (mParent.getColumnNo() == getLevel()) {
            //                    return;
            //                }
            //                logError(mParent, "static", 
            //                    mParent.getColumnNo());
            //            }
        
        }
        
        private boolean hasBlockParent() 
        {
            int parentType = mMainAst.getParent().getType();
            return parentType == TokenTypes.LITERAL_IF
                || parentType == TokenTypes.LITERAL_FOR
                || parentType == TokenTypes.LITERAL_WHILE
                || parentType == TokenTypes.LITERAL_DO
                || parentType == TokenTypes.LITERAL_ELSE
                || parentType == TokenTypes.LITERAL_TRY
                || parentType == TokenTypes.LITERAL_CATCH
                || parentType == TokenTypes.LITERAL_FINALLY
                || parentType == TokenTypes.CTOR_DEF
                || parentType == TokenTypes.METHOD_DEF
                || parentType == TokenTypes.STATIC_INIT;
        }
        
        public void checkIndentation() 
        {
            // only need to check this if parent is not 
            // an if, else, while, do, ctor, method
            if (hasBlockParent()) {
                return;
            }
            super.checkIndentation();
        }
    }

    private class DoWhileHandler extends BlockParentHandler 
    {
        public DoWhileHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("do..while", aAst, aParent);
        }

        private void checkCondExpr() 
        {
            DetailAST condAst = (DetailAST) mMainAst
                .findFirstToken(TokenTypes.LPAREN).getNextSibling();
            checkExpressionSubtree(condAst, getLevel());
        }
        
        public void checkIndentation() 
        {
            super.checkIndentation();
            // TODO: checkWhile();  // while is not in the grammar, why not?
            checkCondExpr();
        }
        
    }
    
    
    private class WhileHandler extends BlockParentHandler 
    {
        public WhileHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("while", aAst, aParent);
        }

        private void checkCondExpr() 
        {
            DetailAST condAst = mMainAst.findFirstToken(TokenTypes.EXPR);
            checkExpressionSubtree(condAst, getLevel() + mIndentationAmount);
        }

        public void checkIndentation() 
        {
            checkCondExpr();
            super.checkIndentation();
        }
        
    }
    
    private class ForHandler extends BlockParentHandler 
    {
        public ForHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("for", aAst, aParent);
        }        
        
        private void checkForParams() 
        {
            checkExpressionSubtree(
                mMainAst.findFirstToken(TokenTypes.FOR_INIT), 
                getLevel() + mIndentationAmount);
            checkExpressionSubtree(
                mMainAst.findFirstToken(TokenTypes.FOR_CONDITION), 
                getLevel() + mIndentationAmount);
            checkExpressionSubtree(
                mMainAst.findFirstToken(TokenTypes.FOR_ITERATOR), 
                getLevel() + mIndentationAmount);
        }
        
        public void checkIndentation() 
        {
            checkForParams();
            super.checkIndentation();
        }
        
        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            if (aChild instanceof ElseHandler) {
                return getLevel();
            } 
            else {
                return super.suggestedChildLevel(aChild);
            }
        }
    }
    
    
    private class ElseHandler extends BlockParentHandler 
    {
        public ElseHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("else", aAst, aParent);
        }

        protected void checkToplevelToken() 
        {
            
            // check if else is nested with rcurly of if:
            //
            //  } else ...
            
            DetailAST ifAST = mMainAst.getParent();
            if (ifAST != null) {
                DetailAST slist = ifAST.findFirstToken(TokenTypes.SLIST);
                if (slist != null) {
                    DetailAST lcurly = slist.getLastChild();
                    if (lcurly != null 
                        && lcurly.getLineNo() == mMainAst.getLineNo()) 
                    {
                        // indentation checked as part of LITERAL IF check
                        return;
                    }
                }
            }
         
            super.checkToplevelToken();
        }
                
        protected DetailAST getNonlistChild() 
        {
            return (DetailAST) mMainAst.getFirstChild();
        }                
    }
    
    private class ImportHandler extends ExpressionHandler 
    {
        public ImportHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("import", aAst, aParent);
        }
        
        public void checkIndentation() 
        {
            int lineStart = mMainAst.getLineNo();
            DetailAST semi = mMainAst.findFirstToken(TokenTypes.SEMI);
            int lineEnd = semi.getLineNo();
            
            if (mMainAst.getLineNo() != lineEnd) {
                checkLinesIndent(lineStart, lineEnd, getLevel());
            }
        }
    }        

    private class IfHandler extends BlockParentHandler 
    {
        public IfHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("if", aAst, aParent);
        }

        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            if (aChild instanceof ElseHandler) {
                return getLevel();
            } 
            else {
                return super.suggestedChildLevel(aChild);
            }
        }
        
        public int getLevelImpl() 
        {
            if (isElseIf()) {
                return mParent.getLevel();
            } 
            else {
                return super.getLevelImpl();
            }
        }
        
        private boolean isElseIf() 
        {
            // check if there is an 'else' and an 'if' on the same line
            DetailAST parent = mMainAst.getParent();
            return parent.getType() == TokenTypes.LITERAL_ELSE
                && parent.getLineNo() == mMainAst.getLineNo();
        }
        
        protected void checkToplevelToken() 
        {
            if (isElseIf()) {
                return;
            }
                
            super.checkToplevelToken();
        }
        
        private void checkCondExpr() 
        {
            DetailAST condAst = (DetailAST) 
                mMainAst.findFirstToken(TokenTypes.LPAREN).getNextSibling();
            checkExpressionSubtree(condAst, getLevel() + mIndentationAmount);
        }
        
        public void checkIndentation() 
        {
            super.checkIndentation();
            checkCondExpr();
        }
    }

    
    private class TryHandler extends BlockParentHandler 
    {
        public TryHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("try", aAst, aParent);
        }

        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            if (aChild instanceof CatchHandler 
                || aChild instanceof FinallyHandler) 
            {
                return getLevel();
            } 
            else {
                return super.suggestedChildLevel(aChild);
            }
        }
    }
    
    private class CatchHandler extends BlockParentHandler 
    {
        public CatchHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("catch", aAst, aParent);
        }

        protected boolean toplevelMustStartLine() 
        {
            return false;
        }

        private void checkCondExpr() 
        {
            DetailAST condAst = (DetailAST)
                mMainAst.findFirstToken(TokenTypes.LPAREN).getNextSibling();
            checkExpressionSubtree(condAst, getLevel());
        }
        
        public void checkIndentation() 
        {
            super.checkIndentation();
            checkCondExpr();
        }
    }

    private class FinallyHandler extends BlockParentHandler 
    {
        public FinallyHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("finally", aAst, aParent);
        }

        protected boolean toplevelMustStartLine() 
        {
            return false;
        }
    }
    
    
    private class MethodDefHandler extends BlockParentHandler 
    {
        public MethodDefHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super((aAst.getType() == TokenTypes.CTOR_DEF) 
                ? "ctor def" : "method def", aAst, aParent);
        }

        protected DetailAST getToplevelAST() 
        {
            // we check this stuff ourselves below
            return null;
        }
        
        private void checkIdent() 
        {
            DetailAST ident = mMainAst.findFirstToken(TokenTypes.IDENT);
            int columnNo = expandedTabsColumnNo(ident);
            if (startsLine(ident) && columnNo != getLevel()) {
                logError(ident, "", columnNo);
            }
        }

        private void checkThrows() 
        {
            DetailAST throwsAst = 
                mMainAst.findFirstToken(TokenTypes.LITERAL_THROWS);
            if (throwsAst == null) {
                return;
            }
            
            int columnNo = expandedTabsColumnNo(throwsAst);
            
            if (startsLine(throwsAst) 
                && columnNo != getLevel() + mIndentationAmount) 
            {
                logError(throwsAst, "throws", columnNo);
            }
        }
        
        
        private void checkType() 
        {
            DetailAST ident = mMainAst.findFirstToken(TokenTypes.TYPE);
            int columnNo = expandedTabsColumnNo(ident);
            if (startsLine(ident) && columnNo != getLevel()) {
                logError(ident, "return type", columnNo);
            }
        }

        private void checkParameters() 
        {
            DetailAST params = mMainAst.findFirstToken(TokenTypes.PARAMETERS);
            checkExpressionSubtree(params, getLevel());
        }
                
        public void checkIndentation() 
        {
            checkModifiers();
            checkIdent();
            checkThrows();
            if (mMainAst.getType() != TokenTypes.CTOR_DEF) {
                checkType();
            }
            checkParameters();
            
            if (getLCurly() == null) {
                // asbtract method def -- no body
                return;
            }
            super.checkIndentation();            
        }
    }
    
    private class MethodCallHandler extends ExpressionHandler 
    {
        
        public MethodCallHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super(
                aAst.getType() == TokenTypes.CTOR_CALL 
                    ? "ctor call" : "method call", 
                aAst, 
                aParent);
        }
        
        private void checkLParen() 
        {
            DetailAST lparen = mMainAst;
            int columnNo = expandedTabsColumnNo(lparen);
            
            if (columnNo == getLevel()) {
                return;
            }

            if (!startsLine(lparen)) {
                return;
            }
            
            logError(lparen, "lparen", columnNo);
        }
        
        private void checkRParen() 
        {
            // the rparen can either be at the correct indentation, or on 
            // the same line as the lparen
            DetailAST rparen = mMainAst.findFirstToken(TokenTypes.RPAREN);
            int columnNo = expandedTabsColumnNo(rparen);
            
            if (columnNo == getLevel()) {
                return;
            }
            
            if (!startsLine(rparen)) {
                return;
            }
            logError(rparen, "rparen", columnNo);            
        }

        public int getLevelImpl()
        {
            // if inside a method call's params, this could be part of 
            // an expression, so get the previous line's start

            if (mParent instanceof MethodCallHandler) {
                MethodCallHandler container = ((MethodCallHandler) mParent)
                    .findContainingMethodCall(this);
                if (container != null) {
                    if (areOnSameLine(container.mMainAst, mMainAst)) {
                        return container.getLevel();
                    } 
                    else {
                        return container.getLevel() + mIndentationAmount;
                    }                 
                } 

                // if we get here, we are the child of the left hand side (name
                //  side) of a method call with no "containing" call, use 
                //  the first non-method callparent
                
                ExpressionHandler p = mParent;
                while (p instanceof MethodCallHandler) {
                    p = p.mParent;
                }
                return p.suggestedChildLevel(this);
            }
            
            // if our expression isn't first on the line, just use the start
            // of the line
            LineSet lines = new LineSet();
            findSubtreeLines(lines, (DetailAST) mMainAst.getFirstChild(), true);
            int firstCol = lines.firstLineCol();
            int lineStart = getLineStart(getFirstAst(mMainAst));
            if (lineStart != firstCol) {
                return lineStart;
            }
            else {
                return super.getLevelImpl();
            }
        }
        
        private DetailAST getFirstAst(DetailAST aAst)
        {
            // walk down the first child part of the dots that make up a method
            // call name
            
            // TODO: I'm not convinced this will work yet, what will happen
            // when there is a method call in the dots? -- we would have to go
            // down that call?
            
            DetailAST ast = (DetailAST) aAst.getFirstChild();
            while (ast != null && ast.getType() == TokenTypes.DOT) {
                ast = (DetailAST) ast.getFirstChild();
            }  

            if (ast == null) {
                ast = aAst;
            }
            
            return ast;
        }
        
        public int suggestedChildLevel(ExpressionHandler aChild) 
        {
            // for whatever reason a method that crosses lines, like asList
            // here:
            //            System.out.println("methods are: " + Arrays.asList(
            //                new String[] {"method"}).toString());
            // will not have the right line num, so just get the child name

            DetailAST first = (DetailAST) mMainAst.getFirstChild();
            int indentLevel = getLineStart(first);
            if (aChild instanceof MethodCallHandler) {
                if (!areOnSameLine((DetailAST) aChild.mMainAst.getFirstChild(), 
                    (DetailAST) mMainAst.getFirstChild())) 
                {
                    indentLevel += mIndentationAmount;
                }
                
            }
            return indentLevel;                    
        }
        
        private MethodCallHandler findContainingMethodCall(
            ExpressionHandler aChild)
        {
            DetailAST firstChild = (DetailAST) mMainAst.getFirstChild();
            DetailAST secondChild = (DetailAST) firstChild.getNextSibling();
            DetailAST climber = aChild.mMainAst.getParent();
            while (climber != null) {
                if (climber == firstChild) {
                    // part of method name
                    if (mParent instanceof MethodCallHandler) {
                        return ((MethodCallHandler) mParent)
                            .findContainingMethodCall(this);
                    } 
                    else {
                        return null;
                    }
                } 
                else if (climber == secondChild) {
                    // part of method arguments, this the method the child 
                    // is contained in
                    return this;
                }
                climber = climber.getParent();
            }
            return null;
        }
        
        
        public void checkIndentation() 
        {
            DetailAST methodName = (DetailAST) mMainAst.getFirstChild();
            checkExpressionSubtree(methodName, getLevel(), false, false);
            
            checkLParen();
            DetailAST rparen = mMainAst.findFirstToken(TokenTypes.RPAREN);
            DetailAST lparen = mMainAst;
            
            if (rparen.getLineNo() != lparen.getLineNo()) {
                
                // if this method name is on the same line as a containing 
                // method, don't indent, this allows expressions like:
                //    method("my str" + method2(
                //        "my str2"));
                // as well as
                //    method("my str" + 
                //        method2(
                //            "my str2"));
                //
                
                checkExpressionSubtree(
                    mMainAst.findFirstToken(TokenTypes.ELIST), 
                    getLevel() + mIndentationAmount, false, true);
                
                checkRParen();
            }
        }
        
    }
     
    // only for anonomous inner classes
    private class ObjectBlockHandler extends BlockParentHandler 
    {
        
        public ObjectBlockHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super("object def", aAst, aParent);
        }

        protected DetailAST getToplevelAST() 
        {
            return null;
        }
        
        protected DetailAST getLCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.LCURLY);
        }

        protected DetailAST getRCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.RCURLY);
        }
        
        protected DetailAST getListChild() 
        {
            return mMainAst;
        }
        
        public int getLevelImpl() 
        {
            DetailAST parentAST = mMainAst.getParent();
            if (parentAST.getType() != TokenTypes.LITERAL_NEW) {
                return mParent.getLevel();
            } 
            else {
                return getLineStart(parentAST);
            }
        }
        
        public void checkIndentation() 
        {
            // if we have a class or interface as a parent, don't do anything, 
            // as this is checked by class def; so 
            // only do this if we have a new for a parent (anonymous inner 
            // class)
            DetailAST parentAST = mMainAst.getParent();
            if (parentAST.getType() != TokenTypes.LITERAL_NEW) {
                return;
            }
            
            super.checkIndentation();
        }        
    }
    
    private class ClassDefHandler extends BlockParentHandler 
    {
        
        public ClassDefHandler(DetailAST aAst, ExpressionHandler aParent) 
        {
            super(aAst.getType() == TokenTypes.CLASS_DEF 
                ? "class def" : "interface def", aAst, aParent);
        }
        
        protected DetailAST getLCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.LCURLY);
        }

        protected DetailAST getRCurly() 
        {
            return mMainAst.findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.RCURLY);
        }
        
        protected DetailAST getToplevelAST() 
        {
            return null;
            // note: ident checked by hand in check indentation;
        }
        
        protected DetailAST getListChild() 
        {
            return mMainAst.findFirstToken(TokenTypes.OBJBLOCK);
        }

        public void checkIndentation() 
        {

            // TODO: still need to better deal with the modifiers and "class"
            checkModifiers();
            
            LineSet lines = new LineSet();

            // checks that line with class name starts at correct indentation,
            //  and following lines (in implements and extends clauses) are 
            //  indented at least one level
            DetailAST ident = mMainAst.findFirstToken(TokenTypes.IDENT);
            int lineStart = getLineStart(ident);
            if (lineStart != getLevel()) {
                logError(ident, "ident", lineStart);
            }

            lines.addLineAndCol(new Integer(ident.getLineNo()), lineStart);
            
            DetailAST impl = mMainAst.findFirstToken(
                TokenTypes.IMPLEMENTS_CLAUSE);
            if (impl != null && impl.getFirstChild() != null) {
                findSubtreeLines(lines, impl, false);
            }

            DetailAST ext = mMainAst.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
            if (ext != null && ext.getFirstChild() != null) {
                findSubtreeLines(lines, ext, false);
            }
            
            checkLinesIndent(ident.getLineNo(), lines.lastLine(), getLevel());
            
            super.checkIndentation();
        }
        
    }


    private class StaticInitHandler extends BlockParentHandler
    {
        public StaticInitHandler(DetailAST aAst, ExpressionHandler aParent)
        {
            super("static initialization", aAst, aParent);
        }
    }


    /** Creates a new instance of IndentationCheck */
    public IndentationCheck() 
    {
    }

    /** how many tabs or spaces to indent */
    public void setIndentationAmount(int aIndentAmount) 
    {
        mIndentationAmount = aIndentAmount;
    }
    
    /** adjusts braces (positive offset) */
    public void setBraceAdjustment(int aAdjustmentAmount) 
    {
        mBraceAdjustment = aAdjustmentAmount;
    }
    
    public void setCaseIndent(int aAmount)
    {
        mCaseIndentationAmount = aAmount;
    }
    
    
    public int[] getDefaultTokens() 
    {
        return mHandlerFactory.getHandledTypes();
    }

    public void beginTree(DetailAST aAst) 
    {
        mHandlers.clear();
        mHandlers.push(new PrimordialHandler());
    }
    
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST) 
    {
        ExpressionHandler handler = mHandlerFactory.getHandler(aAST, 
            (ExpressionHandler) mHandlers.peek());
        mHandlers.push(handler);
        handler.checkIndentation();
                
    }

    public void leaveToken(DetailAST aAST) 
    {
        mHandlers.pop();
    }
    
}
