
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

import antlr.ASTFactory;
import antlr.collections.AST;

/**
 * Factory for <code>SymTabAST</code> objects.
 * @author Rick Giles
 */
public class SymTabASTFactory
{
    /** singleton factory */
    private static ASTFactory factory;
    
    static
    {
        factory = new ASTFactory();
        factory.setASTNodeClass(SymTabAST.class.getName());
    }
    
    /**
     * Creates a <code>SymTabAST</code> with a given type and text.
     * @param aType the type for the new <code>SymTabAST</code>.
     * @param aText the text for the new <code>SymTabAST</code>.
     * @return the new <code>SymTabAST</code>.
     */
    public static SymTabAST create(int aType, String aText)
    {
        return (SymTabAST) factory.create(aType, aText);
    }

    /**
     * Creates an <code>SymTabAST</code> from a given <code>AST</code>.
     * @param aAST the <code>AST</code> for the new <code>SymTabAST</code>.
     * @return the new <code>SymTabAST</code>.
     */    
    public static SymTabAST create(AST aAST)
    {
        return (SymTabAST) factory.create(aAST);
    }
    
    ///CLOVER:OFF
    /** prevent instantiation */
    private SymTabASTFactory()
    {
    }
    ///CLOVER:ON
}
