////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Root class for entity bean method checks.
 * @author Rick Giles
 */
public class EntityBeanMethodChecker
    extends BeanMethodChecker
{
    /** set of ejbCreate methods for matching with ejbPostCreate methods */
    private final Set<DetailAST> mEjbCreates = new HashSet<DetailAST>();

    /** set of ejbPostCreate methods for matching with ejbCreate methods */
    private final Set<DetailAST> mEjbPostCreates = new HashSet<DetailAST>();

    /**
     * Constructs a EntityBeanMethodChecker for a bean check.
     * @param aCheck the bean check.
     */
    public EntityBeanMethodChecker(EntityBeanCheck aCheck)
    {
        super(aCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMethods(DetailAST aAST)
    {
        mEjbCreates.clear();
        mEjbPostCreates.clear();

        super.checkMethods(aAST);

        checkCreateMatch();
    }

    /**
     * Checks that every ejbCreate method has a matching ejbPostCreate method.
     */
    protected void checkCreateMatch()
    {
        final Iterator<DetailAST> it = mEjbCreates.iterator();
        while (it.hasNext()) {
            final DetailAST createMethod = it.next();
            final DetailAST nameAST =
                createMethod.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final String method = name.substring("ejbCreate".length());

            // search for matching ejbPostCreate;
            boolean match = false;
            final Iterator<DetailAST> itPostCreate = mEjbPostCreates.iterator();
            while (!match && itPostCreate.hasNext()) {
                final DetailAST postCreateMethod =
                    itPostCreate.next();
                final DetailAST postCreateNameAST =
                    postCreateMethod.findFirstToken(TokenTypes.IDENT);
                final String postCreateName = postCreateNameAST.getText();
                if (!postCreateName.equals("ejbPostCreate" + method)) {
                    continue;
                }
                match =
                    Utils.sameParameters(createMethod, postCreateMethod);
            }
            if (!match) {
                final String suffix = name.substring("ejbCreate".length());
                final String postCreateName = "ejbPostCreate" + suffix;
                logName(createMethod, "unmatchedejbcreate.bean",
                    new Object[] {postCreateName});
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMethod(DetailAST aMethodAST)
    {
        super.checkMethod(aMethodAST);

        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();

        if (name.startsWith("ejbHome")) {
            checkHomeMethod(aMethodAST);
        }
        else if (name.startsWith("ejbPostCreate")) {
            checkPostCreateMethod(aMethodAST);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkCreateMethod(DetailAST aMethodAST)
    {
        super.checkCreateMethod(aMethodAST);

        mEjbCreates.add(aMethodAST);

        // The return type must be the entity bean’s primary key type
        if (Utils.isVoid(aMethodAST)) {
            logName(aMethodAST, "voidmethod.bean", new Object[] {});
        }
    }


    /**
      * Checks whether an ejbHome&lt;METHOD&gt;(...) method of an
      * entity bean satisfies requirements.
      * @param aMethodAST the AST for the method definition.
      */
    protected void checkHomeMethod(DetailAST aMethodAST)
    {
        // the method may be final
        checkMethod(aMethodAST, true);

        // The throws clause for a home method of an entity bean must not throw
        // the java.rmi.RemoteException.
        checkNotThrows(aMethodAST, "java.rmi.RemoteException");
    }

    /**
      * Checks whether an ejbPostCreate&lt;METHOD&gt;(...) method of an
      * entity bean satisfies requirements.
      * @param aMethodAST the AST for the method definition.
      */
    protected void checkPostCreateMethod(DetailAST aMethodAST)
    {
        // the method must not be final
        checkMethod(aMethodAST, false);

        mEjbPostCreates.add(aMethodAST);

        // The return type must be void
        if (!Utils.isVoid(aMethodAST)) {
            logName(aMethodAST, "nonvoidmethod.bean", new Object[] {});
        }
    }
}
