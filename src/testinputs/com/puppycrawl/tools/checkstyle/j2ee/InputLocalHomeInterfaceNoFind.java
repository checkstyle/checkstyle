package com.puppycrawl.tools.checkstyle.j2ee;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * test for RemoteHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputLocalHomeInterfaceNoFind
    extends EJBLocalHome
{
    public Object findByPrimaryKey(Object aParam, Object aParam2)
        throws FinderException;
        
    public Object findByPrimaryKey()
            throws FinderException;
}
