package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * test for RemoteHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputRemoteHomeInterface
    extends EJBHome
{
    public Integer createInteger(int aParam)
        throws CreateException, RemoteException;
        
    abstract void createSomething(int aParam);
    
    abstract void findSomething(int aParam);
    
    public Integer findInteger(int aParam)
        throws FinderException, RemoteException;
}
