package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.EJBLocalObject;

/**
 * test for RemoteInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputLocalInterface
    extends EJBLocalObject
{
    public void invalid1()
        throws RemoteException;
        
    public void invalid2()
        throws java.rmi.RemoteException;
    
    public void valid()
        throws Exception;
}
