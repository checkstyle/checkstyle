
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

import java.util.Hashtable;
import java.util.Vector;




/**
 * <code>ScopeIndex</code> provides methods for finding <code>Scope</code>s
 * related to a known <code>Occurrence</code>
 */
public class ScopeIndex {

    //This is a Hashtable full of Vectors.  The keys to this hashtable are filenames.
    //Each vector contains all of the scope objects from the specific file.
    private Hashtable indexOfFiles = new Hashtable();

    public Hashtable getIndex() {
        return indexOfFiles;
    }

    /**
     * returns the most specific <code>Scope</code> to which the specified
     * <code>Occurence</code> belongs.
     *
     * @param occ the <code>Occurrence</code> whose <code>Scope</code> we're interested in.
     * @return Scope
     */
    public Scope lookup(Occurrence occ) {
        String key = occ.getFile().getAbsolutePath();
        Vector scopeList = getFileVector(key);

        Scope result = findScope(scopeList, occ);

        return result;
    }

    /**
     * returns the most specific <code>Scope</code> to which the specified
     * <code>Occurence</code> belongs from the specified <code>Vector</code>
     * of <code>Scope</code>s.
     *
     * @param occ the <code>Occurrence</code> whose <code>Scope</code> we're interested in.
     * @param scopeList the <code>Vector</code> of <code>Scope</code>s to chose from.
     * @return Scope
     */
    public Scope findScope(Vector scopeList, Occurrence occ) {
        int i = 0;

        Scope bestSoFar = (Scope) scopeList.elementAt(i);

        while (!bestSoFar
            .getTreeNode()
            .getSpan()
            .contains(occ.getLine(), occ.getColumn())) {
            i++;
            bestSoFar = (Scope) scopeList.elementAt(i);
        }

        for (; i < scopeList.size(); i++) {
            Scope currentScope = (Scope) scopeList.elementAt(i);

            if (currentScope
                .getTreeNode()
                .getSpan()
                .contains(occ.getLine(), occ.getColumn())) {
                if (bestSoFar
                    .getTreeNode()
                    .getSpan()
                    .contains(currentScope.getTreeNode().getSpan())) {
                    bestSoFar = currentScope;
                }
            }
        }

        return bestSoFar;
    }

    /**
     * adds a <code>Scope</code> to the <code>ScopeIndex</code> for searching.
     *
     * @param scope the <code>Scope</code> to add.
     */
    public void addScope(Scope scope) {

        final SymTabAST SymTabAST = scope.getTreeNode();
        SymTabAST.getFile();
        Vector fileVector =
            getFileVector(scope.getTreeNode().getFile().getAbsolutePath());

        fileVector.addElement(scope);
    }

    /**
     * returns the <code>Vector</code> containing the <code>Scope</code>s related
     * to the specified filename.
     *
     * @param fileName the fileName to find scopes for.
     * @return Vector
     */
    private Vector getFileVector(String fileName) {
        Vector result = (Vector) indexOfFiles.get(fileName);

        if (result == null) {
            result = new Vector();
            indexOfFiles.put(fileName, result);
        }

        return result;
    }
}