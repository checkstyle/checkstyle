package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * test for RemoteInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputRemoteInterface
    extends EJBObject
{
    public void valid1()
        throws RemoteException;
        
    public void valid2()
        throws java.rmi.RemoteException;
    
    public void invalid()
        throws Exception;
}
