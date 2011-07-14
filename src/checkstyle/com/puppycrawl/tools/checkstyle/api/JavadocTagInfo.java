////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * This enum defines the various Javadoc tags and there properties.
 *
 * <p>
 * This class was modeled after documentation located at
 * <a href="http://java.sun.com/j2se/1.5.0/docs/tooldocs/windows/javadoc.html">
 * javadoc</a>
 *
 * and
 *
 * <a href="http://java.sun.com/j2se/javadoc/writingdoccomments/index.html">
 * how to write</a>.
 * </p>
 *
 * <p>
 * Some of this documentation was a little incomplete (ex: valid placement of
 * code, value, and literal tags).
 * </p>
 *
 * <p>
 * Whenever an inconsistency was found the author's judgment was used.
 * </p>
 *
 * <p>
 * For now, the number of required/optional tag arguments are not included
 * because some Javadoc tags have very complex rules for determining this
 * (ex: {@code {@value}} tag).
 * </p>
 *
 * <p>
 * Also, the {@link #isValidOn(DetailAST) isValidOn} method does not consider
 * classes defined in a local code block (method, init block, etc.).
 * </>
 *
 * @author Travis Schneeberger
 */
public enum JavadocTagInfo
{
    /**
     * {@code @author}.
     */
    AUTHOR("@author", "author", Type.BLOCK, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF;
        }
    },

    /**
     * {@code {@code}}.
     */
    CODE("{@code}", "code", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code {@docRoot}}.
     */
    DOC_ROOT("{@docRoot}", "docRoot", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @deprecated}.
     */
    DEPRECATED("@deprecated", "deprecated", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || type == TokenTypes.ENUM_CONSTANT_DEF
                || type == TokenTypes.ANNOTATION_FIELD_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @exception}.
     */
    EXCEPTION("@exception", "exception", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.METHOD_DEF || type == TokenTypes.CTOR_DEF;
        }
    },

    /**
     * {@code {@inheritDoc}}.
     */
    INHERIT_DOC("{@inheritDoc}", "inheritDoc", Type.INLINE, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();

            return type == TokenTypes.METHOD_DEF
                && !aAst.branchContains(TokenTypes.LITERAL_STATIC)
                && ScopeUtils.getScopeFromMods(aAst
                    .findFirstToken(TokenTypes.MODIFIERS)) != Scope.PRIVATE;
        }
    },

    /**
     * {@code {@link}}.
     */
    LINK("{@link}", "link", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code {@linkplain}}.
     */
    LINKPLAIN("{@linkplain}", "linkplain", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code {@literal}}.
     */
    LITERAL("{@literal}", "literal", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @param}.
     */
    PARAM("@param", "param", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF;
        }
    },

    /**
     * {@code @return}.
     */
    RETURN("@return", "return", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            final DetailAST returnType = aAst.findFirstToken(TokenTypes.TYPE);

            return type == TokenTypes.METHOD_DEF
                && returnType.getFirstChild().getType()
                != TokenTypes.LITERAL_VOID;

        }
    },

    /**
     * {@code @see}.
     */
    SEE("@see", "see", Type.BLOCK, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @serial}.
     */
    SERIAL("@serial", "serial", Type.BLOCK, true, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();

            return type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst);
        }
    },

    /**
     * {@code @serialData}.
     */
    SERIAL_DATA("@serialData", "serialData", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            final DetailAST methodNameAst = aAst
                .findFirstToken(TokenTypes.IDENT);
            final String methodName = methodNameAst.getText();

            return type == TokenTypes.METHOD_DEF
                && ("writeObject".equals(methodName)
                    || "readObject".equals(methodName)
                    || "writeExternal".equals(methodName)
                    || "readExternal".equals(methodName)
                    || "writeReplace".equals(methodName)
                    || "readResolve"
                    .equals(methodName));
        }
    },

    /**
     * {@code @serialField}.
     */
    SERIAL_FIELD("@serialField", "serialField", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            final DetailAST varType = aAst.findFirstToken(TokenTypes.TYPE);

            return type == TokenTypes.VARIABLE_DEF
                && varType.getType() == TokenTypes.ARRAY_DECLARATOR
                && "ObjectStreamField"
                    .equals(varType.getFirstChild().getText());
        }
    },

    /**
     * {@code @since}.
     */
    SINCE("@since", "since", Type.BLOCK, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @throws}.
     */
    THROWS("@throws", "throws", Type.BLOCK, false, false)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF;
        }
    },

    /**
     * {@code {@value}}.
     */
    VALUE("{@value}", "value", Type.INLINE, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || (type == TokenTypes.VARIABLE_DEF
                && !ScopeUtils.isLocalVariableDef(aAst));
        }
    },

    /**
     * {@code @version}.
     */
    VERSION("@version", "version", Type.BLOCK, true, true)
    {
        /** {@inheritDoc} */
        @Override
        public boolean isValidOn(final DetailAST aAst)
        {
            final int type = aAst.getType();
            return type == TokenTypes.PACKAGE_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF;
        }
    };

    /** holds tag text to tag enum mappings **/
    private static final Map<String, JavadocTagInfo> TEXT_TO_TAG;
    /** holds tag name to tag enum mappings **/
    private static final Map<String, JavadocTagInfo> NAME_TO_TAG;

    static
    {
        final ImmutableMap.Builder<String, JavadocTagInfo> textToTagBuilder =
            new ImmutableMap.Builder<String, JavadocTagInfo>();

        final ImmutableMap.Builder<String, JavadocTagInfo> nameToTagBuilder =
            new ImmutableMap.Builder<String, JavadocTagInfo>();

        for (final JavadocTagInfo tag : JavadocTagInfo.values()) {
            textToTagBuilder.put(tag.getText(), tag);
            nameToTagBuilder.put(tag.getName(), tag);
        }

        TEXT_TO_TAG = textToTagBuilder.build();
        NAME_TO_TAG = nameToTagBuilder.build();
    }

    /** the tag text **/
    private final String mText;
    /** the tag name **/
    private final String mName;
    /** the tag type **/
    private final Type mType;
    /** if tag is valid in package.html **/
    private final boolean mValidInPackageHtml;
    /** if tag is valid in overview.html **/
    private final boolean mValidInOverviewHtml;

    /**
     * Sets the various properties of a Javadoc tag.
     *
     * @param aText the tag text
     * @param aName the tag name
     * @param aType the type of tag
     * @param aValidInPackageHtml whether the tag is valid
     * in package.html file
     * @param aValidInOverviewHtml whether the tag is valid
     * in overview.html file
     */
    private JavadocTagInfo(final String aText, final String aName,
        final Type aType, final boolean aValidInPackageHtml,
        final boolean aValidInOverviewHtml)
    {
        this.mText = aText;
        this.mName = aName;
        this.mType = aType;
        this.mValidInPackageHtml = aValidInPackageHtml;
        this.mValidInOverviewHtml = aValidInOverviewHtml;
    }

    /**
     * Checks if a particular Javadoc tag is valid within a Javadoc block of a
     * given AST.
     *
     * <p>
     * For example: Given a call to
     * <code>JavadocTag.RETURN{@link #isValidOn(DetailAST)}</code>.
     * </p>
     *
     * <p>
     * If passing in a DetailAST representing a non-void METHOD_DEF
     * <code> true </code> would be returned. If passing in a DetailAST
     * representing a CLASS_DEF <code> false </code> would be returned because
     * CLASS_DEF's cannot return a value.
     * </p>
     *
     * @param aAST the AST representing a type that can be Javadoc'd
     * @return true if tag is valid.
     */
    public abstract boolean isValidOn(DetailAST aAST);

    /**
     * Checks if tag is valid in a package.html Javadoc file.
     *
     * @return true if tag is valid.
     */
    public boolean isValidInPackageHtml()
    {
        return this.mValidInPackageHtml;
    }

    /**
     * Checks if tag is valid in a overview.html Javadoc file.
     *
     * @return true if tag is valid.
     */
    public boolean isValidInOverviewHtml()
    {
        return this.mValidInOverviewHtml;
    }

    /**
     * Gets the tag text.
     * @return the tag text
     */
    public String getText()
    {
        return this.mText;
    }

    /**
     * Gets the tag name.
     * @return the tag name
     */
    public String getName()
    {
        return this.mName;
    }

    /**
     * Gets the Tag type defined by {@link JavadocTagInfo.Type Type}.
     * @return the Tag type
     */
    public Type getType()
    {
        return this.mType;
    }

    /**
     * returns a JavadocTag from the tag text.
     * @param aText String representing the tag text
     * @return Returns a JavadocTag type from a String representing the tag
     * @throws NullPointerException if the text is null
     * @throws IllegalArgumentException if the text is not a valid tag
     */
    public static JavadocTagInfo fromText(final String aText)
    {
        if (aText == null) {
            throw new NullPointerException("the text is null");
        }

        final JavadocTagInfo tag = TEXT_TO_TAG.get(aText);

        if (tag == null) {
            throw new IllegalArgumentException("the text [" + aText
                + "] is not a valid Javadoc tag text");
        }

        return tag;
    }

    /**
     * returns a JavadocTag from the tag name.
     * @param aName String name of the tag
     * @return Returns a JavadocTag type from a String representing the tag
     * @throws NullPointerException if the text is null
     * @throws IllegalArgumentException if the text is not a valid tag. The name
     *    can be checked using {@link JavadocTagInfo#isValidName(String)}
     */
    public static JavadocTagInfo fromName(final String aName)
    {
        if (aName == null) {
            throw new NullPointerException("the name is null");
        }

        final JavadocTagInfo tag = NAME_TO_TAG.get(aName);

        if (tag == null) {
            throw new IllegalArgumentException("the name [" + aName
                + "] is not a valid Javadoc tag name");
        }

        return tag;
    }

    /**
     * Returns whether the provided name is for a valid tag.
     * @param aName the tag name to check.
     * @return whether the provided name is for a valid tag.
     */
    public static boolean isValidName(final String aName)
    {
        return NAME_TO_TAG.containsKey(aName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "text [" + this.mText + "] name [" + this.mName
            + "] type [" + this.mType
            + "] validInPackageHtml [" + this.mValidInPackageHtml
            + "] validInOverviewHtml [" + this.mValidInOverviewHtml + "]";
    }

    /**
     * The Javadoc Type.
     *
     * For example a {@code @param} tag is a block tag while a
     * {@code {@link}} tag is a inline tag.
     *
     * @author Travis Schneeberger
     */
    public enum Type
    {
        /** block type. **/
        BLOCK,

        /** inline type. **/
        INLINE;
    }
}
