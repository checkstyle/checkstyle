
// Transmogrify License
// 
// Copyright (c) 2001, ThoughtWorks, Inc.
// All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the ThoughtWorks, Inc. nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.puppycrawl.tools.checkstyle.checks.usage.transmogrify;

import java.io.File;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;



/**
 * an extension of <code>antlr.CommonAST</code> that includes
 * extra information about the AST's location.  This information
 * is the file and line number where the AST was created.
 *
 * To use this AST node in your tree structure, assuming your
 * antlr.TreeParser is called parser, use
 *
 * parser.setASTNOdeCLass(SymTabAST.class.getName());
 *
 * make sure you also call setTokenObjectClass for the lexer as well
 *
 *
 * @see SymTabToken
 */
//TODO: Should be an adapter of DetailAST

public class SymTabAST
    extends antlr.CommonASTWithHiddenTokens
{
    private Scope _scope;
    private IDefinition _definition = null;
    private boolean _isMeaningful = true;

    private File _file;
    private int _line;
    private int _column;

//  parent is not used by Checkstyle
//    private SymTabAST parent;

    private Span _span;

    /** original syntax tree node */
    private DetailAST detailNode;
    
//    /**
//     * gets parent of this node
//     * @return <code>SymTabAST</code>
//     */
//    public SymTabAST getParent() {
//        return parent;
//    }

//    /**
//     * gets previous sibling of this node
//     * @return <code>SymTabAST</code>
//     */
//    public SymTabAST getPreviousSibling() {
//        return prevSibling;
//    }

    /**
     * sets parent of this node
     * @param parent
     * @return <code>void</code>
     */
    public void setParent(SymTabAST parent) {
//parent is not used by Checkstyle
//        this.parent = parent;
    }

    /**
     * gets the scope of this node
     * @return <code>Scope</code>
     */
    public Scope getScope() {
        return _scope;
    }

    /**
     * sets the scope of this node
     * @param scope
     * @return <code>void</code>
     */
    public void setScope(Scope scope) {
        _scope = scope;
    }

    /**
     * sets <code>Definition</code> for this node
     * @param definition
     * @param scope
     * @return <code>void</code>
     * @see #setDefinition(IDefinition, Scope, boolean)
     */
    public void setDefinition(IDefinition definition, Scope scope) {
        setDefinition(definition, scope, true);
    }

    /**
     * sets <code>Definition</code> for this node and adds <code>Reference</code>
     * to the <code>_definition</code> and <code>scope</code>
     * @param definition
     * @param scope
     * @param createReference
     * @return <code>void</code>
     * @see net.sourceforge.transmogrify.symtab.Reference
     */
    public void setDefinition(
        IDefinition definition,
        Scope scope,
        boolean createReference) {
        _definition = definition;
        Reference reference = new Reference(this);
        if (scope != null) {
            scope.addReferenceInScope(reference);
        }

        if (definition.isSourced() && createReference) {
            _definition.addReference(reference);
        }
    }

    /**
     * gets <code>_definitin</code>
     * @return <code>IDefinition</code>
     */
    public IDefinition getDefinition() {
        return _definition;
    }

    /**
     * tests if this node is meaningful or should be ignored
     * @return <code>boolean</code>
     */
    public boolean isMeaningful() {
        return _isMeaningful;
    }

    /**
     * sets <code>_isMeaningful</code> member
     * @param isMeaningful
     * @return <code>void</code>
     */
    public void setMeaningfulness(boolean isMeaningful) {
        _isMeaningful = isMeaningful;
    }

    /**
     * sets meaningfulness for this node and its children
     * @return <code>void</code>
     * @see #setMeaningfulness(boolean)
     */
    public void ignoreChildren() {
        if (getType() == TokenTypes.IDENT) {
            setMeaningfulness(false);
        }
        SymTabAST child = (SymTabAST) getFirstChild();
        while (child != null) {
            child.ignoreChildren();
            child = (SymTabAST) child.getNextSibling();
        }
    }

    /**
     * sets file where this node belong to
     * @param file
     * @return <code>void</code>
     */
    public void setFile(File file) {
        _file = file;
    }

    /**
     * finishes process for adding node to its parent
     * @param file file where this node belongs to
     * @param parent parent of this node
     * @param previousSibling previous sibling of this node
     * @return <code>Span</code> the span of this node
     * @see #setFile(File)
     * @see #setParent(SymTabAST)
     * @see #setPreviousSibling(SymTabAST)
     * @see #finishChildren(File)
     * @see #setSpan(Span)
     */
    public Span finishDefinition(
        File file,
        SymTabAST parent) {
        setFile(file);
        setParent(parent);

        Span result = finishChildren(file);

        if (getLineNo() != 0) {
            result.compose(
                new Span(
                    getLineNo(),
                    getColumnNo(),
                    getLineNo(),
                    getColumnNo()
                        + ((getText() == null) ? 0 : getText().length() - 1)));
        }

        setSpan(result);
        return result;
    }

    /**
     * finishes children of this node definition process
     * @param file file where this node belongs to
     * @return <code>Span</code>
     * @see #finishDefinition(File, SymTabAST, SymTabAST)
     */
    public Span finishChildren(File file) {
        Span result = null;
        SymTabAST current = (SymTabAST) getFirstChild();

        if (current == null) {
            result = getSpan();
        }
        else {
            while (current != null) {
                Span childSpan =
                    current.finishDefinition(file, this);

                if (childSpan != null) {
                    if (result == null) {
                        result = new Span(childSpan);
                    }
                    else {
                        result.compose(childSpan);
                    }
                }

                current = (SymTabAST) current.getNextSibling();
            }
        }

        return result;
    }

    /**
     * gets file where this node belongs to
     * @return <code>File</code>
     */
    public File getFile() {
        return _file;
    }

    /**
     * sets the line where this node reside
     * @return <code>void</code>
     */
    public void setLine(int line) {
        _line = line;
    }

    /**
     * gets the line where this node reside
     * @return <code>int</code>
     */
    public int getLineNo() {
        return _line;
    }

    /**
     * sets the column where this node reside
     * @param column
     */
    public void setColumn(int column) {
        _column = column;
    }

    /**
     * gets the column where this node reside
     * @return <code>int</code>
     */
    public int getColumnNo() {
        return _column;
    }

    /**
     * gets the definition name of this node
     * @return <code>String</code>
     * @see net.sourceforge.transmogrify.symtab.IDefinition
     */
    public String getName() {
        String result = null;
        if (_definition != null) {
            result = _definition.getName();
        }

        return result;
    }

    /**
     * prints the line, column and file for this node for debugging purpose
     * @return <code>String</code>
     */
    public String toString() {
        //StringBuffer resultBuffer = new StringBuffer(super.toString());
        StringBuffer resultBuffer = new StringBuffer(prefixString(true));
        resultBuffer.append("[" + getLineNo() + "," + getColumnNo() + "]");
        //if ( getSpan() != null ) {
        //  resultBuffer.append( " spans " + getSpan() );
        //}
        resultBuffer.append(" in " + getFile());
        //resultBuffer.append(" type: " + getType());
        return resultBuffer.toString();
    }

    public String prefixString(boolean verboseStringConversion) {
        StringBuffer b = new StringBuffer();

        try {
            final String name = TokenTypes.getTokenName(getType());
            // if verbose and type name not same as text (keyword probably)
            if (verboseStringConversion && !getText().equalsIgnoreCase(name)) {
                b.append('[');
                b.append(getText());
                b.append(",<");
                b.append(name);
                b.append(">]");
                return b.toString();
            }
        }
        catch (Exception ex) {
            ;
        }
        return getText();
    }

    /**
     * gets <code>Span</code> of this node
     * @return <code>Span</code>
     */
    public Span getSpan() {
        if ((_span == null)) {
            int endColumn = getColumnNo() + 1;
            final String text = getText();
            if (text != null) {
                endColumn += text.length() - 1;
            }
            _span = new Span(getLineNo(), getColumnNo() + 1, getLineNo(), endColumn);
        }
        return _span;
    }

    /**
     * sets <code>Span</code> for this node
     * @param span
     * @return <code>void</code>
     */
    public void setSpan(Span span) {
        _span = span;
    }

// not used by Checkstyle
//    /**
//     * tests if this node is inside the span
//     * @param line
//     * @param column
//     * @return <code>boolean</code> <code>true</code> if this node is within the span
//     *                              <code>false</code> otherwise
//     */
//    public boolean contains(int line, int column) {
//        return getSpan().contains(line, column);
//    }

    /**
     * gets enclosing node for this node based on line and column
     * @param line
     * @param column
     * @return <code>SymTabAST</code>
     * @see #getSpan()
     */
    public SymTabAST getEnclosingNode(int line, int column) {
        SymTabAST result = null;

        if ((getSpan() != null) && (getSpan().contains(line, column))) {
            SymTabAST child = (SymTabAST) getFirstChild();
            while (child != null && result == null) {
                result = child.getEnclosingNode(line, column);
                child = (SymTabAST) child.getNextSibling();
            }

            // if none of the children contain it, I'm the best node
            if (result == null) {
                result = this;
            }

        }
        return result;
    }

    public AST getFirstChild()
    {
        if (super.getFirstChild() == null) {
            DetailAST childDetailAST = null;
            final DetailAST detailAST = getDetailNode();
            if (detailAST != null) {
                childDetailAST = (DetailAST) detailAST.getFirstChild();
                if (childDetailAST != null) {
                    final SymTabAST child =
                        SymTabASTFactory.create(childDetailAST);
                    setFirstChild(child);
                    child.setParent(this);
                    child.setFile(getFile());
                }
            }
        }
        return super.getFirstChild(); 
    }
    
    public AST getNextSibling()
    {
        if (super.getNextSibling() == null) {
            DetailAST siblingDetailAST = null;
            final DetailAST detailAST = getDetailNode();           
            if (detailAST != null) {
                siblingDetailAST = (DetailAST) detailAST.getNextSibling();
                if (siblingDetailAST != null) {
                    final SymTabAST sibling =
                    SymTabASTFactory.create(siblingDetailAST);
                    setNextSibling(sibling);
//                    sibling.setParent(this.getParent());
                    sibling.setFile(getFile());
                }
            }
        }
        return super.getNextSibling(); 
    }

    
    /**
     * initialized this node with input node
     * @param aAST the node to initialize from. Must be a
     * <code>DetailAST</code> object.
     */
    public void initialize(AST aAST)
    {
        if (aAST != null) {
            super.initialize(aAST);
            final DetailAST detailAST = (DetailAST) aAST;
            setDetailNode(detailAST);
            _column = detailAST.getColumnNo() + 1;
            _line = detailAST.getLineNo();
        } 
    }
        
    /**
     * Gets first occurence of the child node with a certain type
     * @param type
     * @return <code>SymTabAST</code>
     * @see #getType()
     */
    public SymTabAST findFirstToken(int type) {
        SymTabAST result = null;

        AST sibling = getFirstChild();
        while (sibling != null) {
            if (sibling.getType() == type) {
                result = (SymTabAST) sibling;
                break;
            }
            sibling = sibling.getNextSibling();
        }

        return result;
    }

//  not used by Checkstyle
//    /**
//     * adds a node to the last position of this node children
//     * @param child
//     * @return <code>void</code>
//     */
//    public void addChild(SymTabAST child)
//    {
//        SymTabAST lastChild = (SymTabAST) getFirstChild();
//        if (lastChild == null) {
//            setFirstChild(child);
//            child.setParent(this);
//            child.setNextSibling(null);
//        }
//        else {
//            while (lastChild.getNextSibling() != null) {
//                lastChild = (SymTabAST) lastChild.getNextSibling();
//            }
//            lastChild.setNextSibling(child);
//            child.setNextSibling(null);
//            child.setParent(this);
//        }
//    }

    /**
     * Gets Iterator for this node
     * @return <code>SymTabASTIterator</code>
     */
    public SymTabASTIterator getChildren()
    {
        return new SymTabASTIterator(this);
    }

    /**
     * Returns the DetailAST associated with this node.
     * @return the DetailAST associated with this node.
     */
    public DetailAST getDetailNode()
    {
        return detailNode;
    }

    /**
     * Sets the DetailAST associated with this node.
     * @param aDetailAST the DetailAST associated with this node.
     */
    public void setDetailNode(DetailAST aDetailAST)
    {
        detailNode = aDetailAST;
        ASTManager.getInstance().put(aDetailAST, this);
    }

}