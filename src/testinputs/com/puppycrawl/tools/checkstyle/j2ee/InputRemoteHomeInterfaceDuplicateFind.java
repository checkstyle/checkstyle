package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * test for RemoteHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputRemoteHomeInterfaceDuplicateFind
    extends EJBHome
{
    public Object findByPrimaryKey(Object aParam)
        throws FinderException, RemoteException;
        
    public Object findByPrimaryKey()
            throws FinderException, RemoteException;
}
