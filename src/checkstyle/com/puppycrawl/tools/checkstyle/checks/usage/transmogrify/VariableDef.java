
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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;



/**
 * <code>VariableDef</code> is a <code>Definition</code> that contains
 * information about the definition of a variable.
 *
 * @see Definition
 * @see TypedDef
 */
public class VariableDef extends Definition implements IVariable {

    public static final int PRIVATE_VISIBILITY = 0;
    public static final int PROTECTED_VISIBILITY = 1;
    public static final int PUBLIC_VISIBILITY = 2;
    public static final int DEFAULT_VISIBILITY = 3;

    private IClass _type = null;

    public VariableDef(String name, Scope parentScope, SymTabAST node) {
        super(name, parentScope, node);
    }

    /**
     * Returns the <code>Type</code> of the variable.
     *
     * @see TypedDef
     *
     * @return the <code>Type</code> of the variable
     */
    public IClass getType() {
        return _type;
    }

    /**
     * Sets the type of the variable.
     *
     * @see TypedDef
     *
     * @param def the <code>Type</code> object that represents the type of the
     *            variable.
     */
    public void setType(IClass type) {
        _type = type;
    }

    public int getVisibility() {
        int result = DEFAULT_VISIBILITY;

        SymTabAST visibilityNode = getVisibilityNode();
        if (visibilityNode != null) {
            if (visibilityNode.getType() == TokenTypes.LITERAL_PRIVATE) {
                result = PRIVATE_VISIBILITY;
            }
            else if (
                visibilityNode.getType() == TokenTypes.LITERAL_PROTECTED) {
                result = PROTECTED_VISIBILITY;
            }
            else if (visibilityNode.getType() == TokenTypes.LITERAL_PUBLIC) {
                result = PUBLIC_VISIBILITY;
            }
        }

        return result;
    }

    private SymTabAST getVisibilityNode() {
        SymTabAST result = null;

        SymTabAST modifiersNode =
            getTreeNode().findFirstToken(TokenTypes.MODIFIERS);
        SymTabAST modifier = (SymTabAST) modifiersNode.getFirstChild();
        while (modifier != null) {
            if (isVisibilityNode(modifier)) {
                result = modifier;
                break;
            }
            modifier = (SymTabAST) modifier.getNextSibling();
        }

        return result;
    }

    private boolean isVisibilityNode(SymTabAST node) {
        return (
            node.getType() == TokenTypes.LITERAL_PUBLIC
                || node.getType() == TokenTypes.LITERAL_PROTECTED
                || node.getType() == TokenTypes.LITERAL_PRIVATE);
    }

    public boolean isAssignedAtDeclaration() {
        boolean result = false;

        if (getTreeNode().findFirstToken(TokenTypes.ASSIGN) != null) {
            result = true;
        }

        return result;
    }

}
