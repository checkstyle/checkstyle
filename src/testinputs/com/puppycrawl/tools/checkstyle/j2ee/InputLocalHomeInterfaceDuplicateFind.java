package com.puppycrawl.tools.checkstyle.j2ee;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * test for RemoteHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputLocalHomeInterfaceDuplicateFind
    extends EJBLocalHome
{
    public Object findByPrimaryKey(Object aParam)
        throws FinderException;
        
    public Object findByPrimaryKey()
            throws FinderException;
}
