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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.Utils;
import antlr.collections.AST;

// TODO: Clean up potential duplicate code here and in UnusedImportsCheck
/**
 * <p>
 * Checks for illegal instantiations where a factory method is preferred.
 * </p>
 * <p>
 * Rationale: Depending on the project, for some classes it might be
 * preferable to create instances through factory methods rather than
 * calling the constructor.
 * </p>
 * <p>
 * A simple example is the java.lang.Boolean class, to save memory and CPU
 * cycles it is preferable to use the predeifined constants TRUE and FALSE.
 * Constructor invocations should be replaced by calls to Boolean.valueOf().
 * </p>
 * <p>
 * Some extremely performance sensitive projects may require the use of factory
 * methods for other classes as well, to enforce the usage of number caches or
 * object pools.
 * </p>
 * <p>
 * Limitations: It is currently not possible to specify array classes.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="IllegalInstantiation"/&gt;
 * </pre>
 * @author lkuehne
 */
public class IllegalInstantiationCheck
    extends AbstractImportCheck
{
    /** Set of fully qualified classnames. E.g. "java.lang.Boolean" */
    private final Set mIllegalClasses = new HashSet();

    /** name of the package */
    private String mPkgName = null;

    /** the imports for the file */
    private final Set mImports = new HashSet();

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.PACKAGE_DEF
        };
    }

    /**
     * Prevent user from changing tokens in the configuration.
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public int[] getAcceptableTokens()
    {
        return new int[] {};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.PACKAGE_DEF
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        super.beginTree(aRootAST);
        mPkgName = null;
        mImports.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
            case TokenTypes.LITERAL_NEW:
                processLiteralNew(aAST);
                break;
            case TokenTypes.PACKAGE_DEF:
                processPackageDef(aAST);
                break;
            case TokenTypes.IMPORT:
                processImport(aAST);
                break;
        }
    }

    /**
     * Perform processing for an import token
     * @param aAST the import token
     */
    private void processImport(DetailAST aAST)
    {
        final FullIdent name = getImportText(aAST);
        if (name != null) {
            // Note: different from UnusedImportsCheck.processImport(),
            // '.*' imports are also added here
            mImports.add(name);
        }
    }

    /**
     * Perform processing for an package token
     * @param aAST the package token
     */
    private void processPackageDef(DetailAST aAST)
    {
        final DetailAST packageNameAST = (DetailAST) aAST.getFirstChild();
        final FullIdent packageIdent =
                FullIdent.createFullIdent(packageNameAST);
        mPkgName = packageIdent.getText();
    }

    /**
     * Perform processing for an "new" token
     * @param aAST the "new" token
     */
    private void processLiteralNew(DetailAST aAST)
    {
        final DetailAST typeNameAST = (DetailAST) aAST.getFirstChild();

        final AST nameSibling = typeNameAST.getNextSibling();
        if (nameSibling != null
                && nameSibling.getType() == TokenTypes.ARRAY_DECLARATOR)
        {
            // aAST == "new Boolean[]"
            return;
        }

        FullIdent typeIdent = FullIdent.createFullIdent(typeNameAST);
        final String typeName = typeIdent.getText();
        final int lineNo = aAST.getLineNo();
        final int colNo = aAST.getColumnNo();
        final String fqClassName = getIllegalInstantiation(typeName);
        if (fqClassName != null) {
            log(lineNo, colNo, "instantiation.avoid", fqClassName);
        }
    }

    /**
     * Checks illegal instantiations.
     * @param aClassName instantiated class, may or may not be qualified
     * @return the fully qualified class name of aClassName
     * or null if instantiation of aClassName is OK
     */
    private String getIllegalInstantiation(String aClassName)
    {
        final String javaLang = "java.lang.";

        if (mIllegalClasses.contains(aClassName)) {
            return aClassName;
        }

        final int clsNameLen = aClassName.length();
        final int pkgNameLen = (mPkgName == null) ? 0 : mPkgName.length();

        final Iterator illIter = mIllegalClasses.iterator();
        while (illIter.hasNext()) {
            final String illegal = (String) illIter.next();
            final int illegalLen = illegal.length();

            // class from java.lang
            if (((illegalLen - javaLang.length()) == clsNameLen)
                && illegal.endsWith(aClassName)
                && illegal.startsWith(javaLang))
            {
                return illegal;
            }

            // class from same package

            // the toplevel package (mPkgName == null) is covered by the
            // "illegalInsts.contains(aClassName)" check above

            // the test is the "no garbage" version of
            // illegal.equals(mPkgName + "." + aClassName)
            if (mPkgName != null
                && clsNameLen == illegalLen - pkgNameLen - 1
                && illegal.charAt(pkgNameLen) == '.'
                && illegal.endsWith(aClassName)
                && illegal.startsWith(mPkgName))
            {
                return illegal;
            }
            // import statements
            final Iterator importIter = mImports.iterator();
            while (importIter.hasNext()) {
                final FullIdent importLineText = (FullIdent) importIter.next();
                final String importArg = importLineText.getText();
                if (importArg.endsWith(".*")) {
                    final String fqClass =
                        importArg.substring(0, importArg.length() - 1)
                        + aClassName;
                    // assume that illegalInsts only contain existing classes
                    // or else we might create a false alarm here
                    if (mIllegalClasses.contains(fqClass)) {
                        return fqClass;
                    }
                }
                else {
                    if (Utils.baseClassname(importArg).equals(aClassName)
                        && mIllegalClasses.contains(importArg))
                    {
                        return importArg;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets the classes that are illegal to instantiate.
     * @param aClassNames a comma seperate list of class names
     */
    public void setClasses(String aClassNames)
    {
        mIllegalClasses.clear();
        final StringTokenizer tok = new StringTokenizer(aClassNames, ",");
        while (tok.hasMoreTokens()) {
            mIllegalClasses.add(tok.nextToken());
        }
    }
}
