////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks that an EntityBean implementation satisfies EntityBean
 * requirements.  Such as:
 * <ul>
 * <li>The class is defined as <code>public</code>.</li>
 * <li>The class cannot be defined as <code>final</code>.</li>
 * <li>It contains a <code>public</code> constructor with no parameters.</li>
 * <li>It must not define the <code>finalize</code> method.</li>
</ul>
 * @author Rick Giles
 */
public class EntityBeanCheck
    extends AbstractBeanCheck
{
    /** the persistence policy to enforce */
    private PersistenceOption mPersistenceOption;

    /** EJB version */
    private String mVersion = "2.0";

    /**
     * Creates a new <code>EntityBeanCheck</code> instance.
     */
    public EntityBeanCheck()
    {
        mPersistenceOption = PersistenceOption.MIXED;
        setMethodChecker(new EntityBeanMethodChecker(this));
    }

    /**
     * Set the persistence option to enforce.
     * @param aOption string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setPersistence(String aOption)
        throws ConversionException
    {
        mPersistenceOption =
            (PersistenceOption) mPersistenceOption.decode(aOption);
        if (mPersistenceOption == null) {
            throw new ConversionException("unable to parse " + aOption);
        }
        else if (mPersistenceOption == PersistenceOption.BEAN) {
            setMethodChecker(new BeanManagedMethodChecker(this));
        }
        else if (mPersistenceOption == PersistenceOption.CONTAINER) {
            setMethodChecker(new ContainerManagedMethodChecker(this));
        }
        else {
            setMethodChecker(new EntityBeanMethodChecker(this));
        }
    }

    /**
     * Returns the set <code>PersistenceOption</code>.
     * @return the set <code>PersistenceOption</code>
     */
    public PersistenceOption getPersistenceOption()
    {
        return mPersistenceOption;
    }

    /**
     * Sets the EJB version.
     * @param aVersion the EJB version.
     */
    public void setVersion(String aVersion)
    {
        mVersion = aVersion;
    }

    /**
     * Determines the EJB version.
     * @return the EJB version.
     */
    public String getVersion()
    {
        return mVersion;
    }

    /**
     * {@inheritDoc}
     */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasImplements(aAST, "javax.ejb.EntityBean")) {
            checkBean(aAST, "Entity bean", true);
            checkAbstract(aAST);
            getMethodChecker().checkMethods(aAST);
        }
    }

    /**
     * Checks whether or not an entity bean is declared abstract
     * according to it persistence management.
     * @param aAST the AST for the entity bean class definition.
     */
    private void checkAbstract(DetailAST aAST)
    {
        // bean-managed persistence requires non-abstract class
        if ((getPersistenceOption() == PersistenceOption.BEAN)
            && Utils.isAbstract(aAST))
        {
            logName(aAST, "abstract.bean", new Object[] {});
        }
        //container-managed persistence requires abstract class
        if ((getPersistenceOption() == PersistenceOption.CONTAINER)
                    && !Utils.isAbstract(aAST))
        {
            logName(aAST, "nonabstract.bean", new Object[] {});
        }
    }
}
