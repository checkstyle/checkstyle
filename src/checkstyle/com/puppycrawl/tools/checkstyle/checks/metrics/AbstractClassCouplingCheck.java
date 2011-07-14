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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import java.util.Set;

/**
 * Base class for coupling calculation.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public abstract class AbstractClassCouplingCheck extends Check
{
    /** Class names to ignore. */
    private final Set<String> mIgnoredClassNames = Sets.newHashSet();
    /** Allowed complexity. */
    private int mMax;
    /** package of the file we check. */
    private String mPackageName;

    /** Stack of contexts. */
    private final FastStack<Context> mContextStack = FastStack.newInstance();
    /** Current context. */
    private Context mContext;

    /**
     * Creates new instance of the check.
     * @param aDefaultMax default value for allowed complexity.
     */
    protected AbstractClassCouplingCheck(int aDefaultMax)
    {
        setMax(aDefaultMax);

        mIgnoredClassNames.add("boolean");
        mIgnoredClassNames.add("byte");
        mIgnoredClassNames.add("char");
        mIgnoredClassNames.add("double");
        mIgnoredClassNames.add("float");
        mIgnoredClassNames.add("int");
        mIgnoredClassNames.add("long");
        mIgnoredClassNames.add("short");
        mIgnoredClassNames.add("void");
        mIgnoredClassNames.add("Boolean");
        mIgnoredClassNames.add("Byte");
        mIgnoredClassNames.add("Character");
        mIgnoredClassNames.add("Double");
        mIgnoredClassNames.add("Float");
        mIgnoredClassNames.add("Integer");
        mIgnoredClassNames.add("Long");
        mIgnoredClassNames.add("Object");
        mIgnoredClassNames.add("Short");
        mIgnoredClassNames.add("String");
        mIgnoredClassNames.add("StringBuffer");
        mIgnoredClassNames.add("Void");
        mIgnoredClassNames.add("ArrayIndexOutOfBoundsException");
        mIgnoredClassNames.add("Exception");
        mIgnoredClassNames.add("RuntimeException");
        mIgnoredClassNames.add("IllegalArgumentException");
        mIgnoredClassNames.add("IllegalStateException");
        mIgnoredClassNames.add("IndexOutOfBoundsException");
        mIgnoredClassNames.add("NullPointerException");
        mIgnoredClassNames.add("Throwable");
        mIgnoredClassNames.add("SecurityException");
        mIgnoredClassNames.add("UnsupportedOperationException");
    }

    @Override
    public final int[] getDefaultTokens()
    {
        return getRequiredTokens();
    }

    /** @return allowed complexity. */
    public final int getMax()
    {
        return mMax;
    }

    /**
     * Sets maximul allowed complexity.
     * @param aMax allowed complexity.
     */
    public final void setMax(int aMax)
    {
        mMax = aMax;
    }

    @Override
    public final void beginTree(DetailAST aAST)
    {
        mPackageName = "";
    }

    /** @return message key we use for log violations. */
    protected abstract String getLogMessageId();

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.PACKAGE_DEF:
            visitPackageDef(aAST);
            break;
        case TokenTypes.CLASS_DEF:
        case TokenTypes.INTERFACE_DEF:
        case TokenTypes.ANNOTATION_DEF:
        case TokenTypes.ENUM_DEF:
            visitClassDef(aAST);
            break;
        case TokenTypes.TYPE:
            mContext.visitType(aAST);
            break;
        case TokenTypes.LITERAL_NEW:
            mContext.visitLiteralNew(aAST);
            break;
        case TokenTypes.LITERAL_THROWS:
            mContext.visitLiteralThrows(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CLASS_DEF:
        case TokenTypes.INTERFACE_DEF:
        case TokenTypes.ANNOTATION_DEF:
        case TokenTypes.ENUM_DEF:
            leaveClassDef();
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Stores package of current class we check.
     * @param aPkg package definition.
     */
    private void visitPackageDef(DetailAST aPkg)
    {
        final FullIdent ident = FullIdent.createFullIdent(aPkg.getLastChild()
                .getPreviousSibling());
        mPackageName = ident.getText();
    }

    /**
     * Creates new context for a given class.
     * @param aClassDef class definition node.
     */
    private void visitClassDef(DetailAST aClassDef)
    {
        mContextStack.push(mContext);
        final String className =
            aClassDef.findFirstToken(TokenTypes.IDENT).getText();
        mContext = new Context(className,
                               aClassDef.getLineNo(),
                               aClassDef.getColumnNo());
    }

    /** Restores previous context. */
    private void leaveClassDef()
    {
        mContext.checkCoupling();
        mContext = mContextStack.pop();
    }

    /**
     * Incapsulates information about class coupling.
     *
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     * @author o_sukhodolsky
     */
    private class Context
    {
        /**
         * Set of referenced classes.
         * Sorted by name for predictable error messages in unit tests.
         */
        private final Set<String> mReferencedClassNames = Sets.newTreeSet();
        /** Own class name. */
        private final String mClassName;
        /* Location of own class. (Used to log violations) */
        /** Line number of class definition. */
        private final int mLineNo;
        /** Column number of class definition. */
        private final int mColumnNo;

        /**
         * Create new context associated with given class.
         * @param aClassName name of the given class.
         * @param aLineNo line of class definition.
         * @param aColumnNo column of class definition.
         */
        public Context(String aClassName, int aLineNo, int aColumnNo)
        {
            mClassName = aClassName;
            mLineNo = aLineNo;
            mColumnNo = aColumnNo;
        }

        /**
         * Visits throws clause and collects all exceptions we throw.
         * @param aThrows throws to process.
         */
        public void visitLiteralThrows(DetailAST aThrows)
        {
            for (DetailAST childAST = aThrows.getFirstChild();
                 childAST != null;
                 childAST = childAST.getNextSibling())
            {
                if (childAST.getType() != TokenTypes.COMMA) {
                    addReferencedClassName(childAST);
                }
            }
        }

        /**
         * Visits type.
         * @param aAST type to process.
         */
        public void visitType(DetailAST aAST)
        {
            final String className = CheckUtils.createFullType(aAST).getText();
            mContext.addReferencedClassName(className);
        }

        /**
         * Visits NEW.
         * @param aAST NEW to process.
         */
        public void visitLiteralNew(DetailAST aAST)
        {
            mContext.addReferencedClassName(aAST.getFirstChild());
        }

        /**
         * Adds new referenced class.
         * @param aAST a node which represents referenced class.
         */
        private void addReferencedClassName(DetailAST aAST)
        {
            final String className = FullIdent.createFullIdent(aAST).getText();
            addReferencedClassName(className);
        }

        /**
         * Adds new referenced class.
         * @param aClassName class name of the referenced class.
         */
        private void addReferencedClassName(String aClassName)
        {
            if (isSignificant(aClassName)) {
                mReferencedClassNames.add(aClassName);
            }
        }

        /** Checks if coupling less than allowed or not. */
        public void checkCoupling()
        {
            mReferencedClassNames.remove(mClassName);
            mReferencedClassNames.remove(mPackageName + "." + mClassName);

            if (mReferencedClassNames.size() > mMax) {
                log(mLineNo, mColumnNo, getLogMessageId(),
                        mReferencedClassNames.size(), getMax(),
                        mReferencedClassNames.toString());
            }
        }

        /**
         * Checks if given class shouldn't be ignored and not from java.lang.
         * @param aClassName class to check.
         * @return true if we should count this class.
         */
        private boolean isSignificant(String aClassName)
        {
            return (aClassName.length() > 0)
                    && !mIgnoredClassNames.contains(aClassName)
                    && !aClassName.startsWith("java.lang.");
        }
    }
}
