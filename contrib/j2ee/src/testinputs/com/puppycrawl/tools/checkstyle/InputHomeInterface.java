package com.puppycrawl.tools.checkstyle;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * test for LocalHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputHomeInterface
    extends EJBHome
{
    public Integer createInteger(int aParam)
        throws CreateException, RemoteException;
        
    abstract void createSomething(int aParam);
    
    abstract void findSomething(int aParam);
    
    public Integer findInteger(int aParam)
        throws FinderException, RemoteException;
}
