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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;



/**
 * <code>ASTUtil</code> is a <code>Utility Class</code> that contains utility code
 * for managing our SymTabAST.
 *
 * @see Definition
 * @see TypedDef
 */

public class ASTUtil {

    /**
     * gets a line number for the tree;  if the current SymTabAST node does not have one associated
     * with it, traverse its children until a line number is found.  Failure results in line
     * number value of 0.
     *
     * @param tree the SymTabAST to process
     *
     * @return int the resulting line number (0 if none is found)
     */
    public static int getLine(SymTabAST tree) {
        SymTabAST indexedNode = tree;

        // find a node that actually has line number info
        if (indexedNode.getLineNo() == 0) {
            indexedNode = (SymTabAST) indexedNode.getFirstChild();

            while (indexedNode != null && indexedNode.getLineNo() == 0) {
                indexedNode = (SymTabAST) indexedNode.getNextSibling();
            }

            if (indexedNode == null) {
                // we're screwed
                indexedNode = tree;
            }
        }

        return indexedNode.getLineNo();
    }

    /**
     * gets a column number for the tree;  if the current SymTabAST node does not have one associated
     * with it, traverse its children until a column number is found.  Failure results in column
     * number value of 0.
     *
     * @param tree the SymTabAST to process
     *
     * @return int the resulting line number (0 if none is found)
     */
    public static int getColumn(SymTabAST tree) {
        SymTabAST indexedNode = tree;

        // find a node that actually has line number info
        // REDTAG -- a label's ':' is a real token and has (the wrong) column info
        // because it is the parent of the ident node that people will want
        if (indexedNode.getColumnNo() == 0
            || indexedNode.getType() == TokenTypes.LABELED_STAT) {
            indexedNode = (SymTabAST) indexedNode.getFirstChild();

            while (indexedNode != null && indexedNode.getColumnNo() == 0) {
                indexedNode = (SymTabAST) indexedNode.getNextSibling();
            }

            if (indexedNode == null) {
                // we're screwed
                indexedNode = tree;
            }
        }

        return indexedNode.getColumnNo();
    }

    /**
     * Builds the dotted name String representation of the object contained within
     * the SymTabAST.
     *
     * @return String
     * @param tree the SymTabAST contaning the entire hierarcy of the object
     */
    public static String constructDottedName(SymTabAST tree) {
        String result;

        if (tree.getType() == TokenTypes.DOT) {
            SymTabAST left = (SymTabAST) tree.getFirstChild();
            SymTabAST right = (SymTabAST) left.getNextSibling();

            result =
                constructDottedName(left) + "." + constructDottedName(right);
        }
        else if (tree.getType() == TokenTypes.ARRAY_DECLARATOR) {
            StringBuffer buf = new StringBuffer();
            SymTabAST left = (SymTabAST) tree.getFirstChild();
            SymTabAST right = (SymTabAST) left.getNextSibling();

            buf.append(constructDottedName(left));

            if (right != null) {
                buf.append(".");
                buf.append(constructDottedName(right));
            }

            buf.append(" []");

            result = buf.toString();
        }
        else if (tree.getType() == TokenTypes.METHOD_CALL) {
            result =
                constructDottedName((SymTabAST) tree.getFirstChild()) + "()";
        }
        else {
            result = tree.getText();
        }

        return result;
    }

    /**
     * Returns the Package name in the hierarchy represented by the SymTabAST.
     *
     * @return String
     * @param tree the SymTabAST contaning the entire hierarcy of the object
     */
    public static String constructPackage(SymTabAST tree) {
        String fullName = constructDottedName(tree);

        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    /**
    * Returns the top Class name in the hierarchy represented by the SymTabAST.
    *
    * @return String
    * @param tree the SymTabAST contaning the entire hierarcy of the object
    */
    public static String constructClass(SymTabAST tree) {
        String fullName = constructDottedName(tree);

        return fullName.substring(
            fullName.lastIndexOf(".") + 1,
            fullName.length());
    }

    public static boolean treesBelowFilesAreEqual(
        SymTabAST firstRoot,
        File[] firstFiles,
        SymTabAST secondRoot,
        File[] secondFiles) {
        boolean result = true;

        if (firstFiles.length == secondFiles.length) {
            for (int i = 0; i < firstFiles.length; i++) {
                SymTabAST firstTree =
                    (SymTabAST) getFileNode(firstRoot, firstFiles[i])
                        .getFirstChild();
                SymTabAST secondTree =
                    (SymTabAST) getFileNode(secondRoot, secondFiles[i])
                        .getFirstChild();

                if (!firstTree.equalsList(secondTree)) {
                    result = false;
                    break;
                }
            }
        }
        else {
            result = false;
        }

        return result;
    }

    public static SymTabAST getFileNode(SymTabAST root, File file)
    {
        SymTabAST result = null;

        SymTabAST fileNode = (SymTabAST) root.getFirstChild();
        while (fileNode != null && result == null) {
            if (file.equals(fileNode.getFile())) {
                result = fileNode;
            }
            fileNode = (SymTabAST) fileNode.getNextSibling();
        }

        return result;
    }
}
