
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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Resolves primitive identifiers (int, double) to their corresponding
 * <code>ClassDef</code>.  This class uses the Singleton pattern.
 *
 * @author <a href="mailto:smileyy@thoughtworks.com">andrew</a>
 * @version 1.0
 * @since 1.0
 */

public class LiteralResolver {

    private static Map intMap;
    private static Map nameMap;
    private static Map classMap;

    static {
        nameMap = new HashMap();

        nameMap.put("boolean", new ExternalClass(Boolean.TYPE));
        nameMap.put("byte", new ExternalClass(Byte.TYPE));
        nameMap.put("char", new ExternalClass(Character.TYPE));
        nameMap.put("short", new ExternalClass(Short.TYPE));
        nameMap.put("int", new ExternalClass(Integer.TYPE));
        nameMap.put("float", new ExternalClass(Float.TYPE));
        nameMap.put("long", new ExternalClass(Long.TYPE));
        nameMap.put("double", new ExternalClass(Double.TYPE));

        intMap = new HashMap();

        intMap.put(
            new Integer(TokenTypes.LITERAL_BOOLEAN),
            new ExternalClass(Boolean.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_BYTE),
            new ExternalClass(Byte.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_CHAR),
            new ExternalClass(Character.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_SHORT),
            new ExternalClass(Short.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_INT),
            new ExternalClass(Integer.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_FLOAT),
            new ExternalClass(Float.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_LONG),
            new ExternalClass(Long.TYPE));
        intMap.put(
            new Integer(TokenTypes.LITERAL_DOUBLE),
            new ExternalClass(Double.TYPE));
        intMap.put(
            new Integer(TokenTypes.STRING_LITERAL),
            new ExternalClass("".getClass()));

        classMap = new HashMap();
        classMap.put(
            new ExternalClass(Boolean.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_BOOLEAN, "boolean"));
        classMap.put(
            new ExternalClass(Byte.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_BYTE, "byte"));
        classMap.put(
            new ExternalClass(Character.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_CHAR, "char"));
        classMap.put(
            new ExternalClass(Short.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_SHORT, "short"));
        classMap.put(
            new ExternalClass(Integer.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_INT, "int"));
        classMap.put(
            new ExternalClass(Float.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_FLOAT, "float"));
        classMap.put(
            new ExternalClass(Long.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_LONG, "long"));
        classMap.put(
            new ExternalClass(Double.TYPE),
            SymTabASTFactory.create(TokenTypes.LITERAL_DOUBLE, "double"));

    }

    /**
     * Returns a <code>LiteralResolver</code>
     *
     * @return a <code>LiteralResolver</code>
     */

    public static LiteralResolver getResolver() {
        return new LiteralResolver();
    }

    /**
     * Returns the <code>ClassDef</code> for a primitive type reference.
     *
     * <p>
     * We could probably do without passing in the context, if we could figure
     * out a way to access the base scope.
     * </p>
     *
     * @param literalType the JavaTokenType for the literal type
     * @param context the scope in which the search performed
     * @return returns the <code>ClassDef</code>corresponding to the primitive
     *         type
     */

    public static IClass getDefinition(int literalType) {
        Integer key = new Integer(literalType);
        return (IClass) intMap.get(key);
    }

    public static IClass getDefinition(String name) {
        return (IClass) nameMap.get(name);
    }

    public static SymTabAST getASTNode(IClass primitive) {
        return (SymTabAST) classMap.get(primitive);
    }
}
