package com.puppycrawl.tools.checkstyle;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * test for LocalHomeInterfaceCheck
 * @author Rick Giles
 *
 */
public interface InputLocalHomeInterface
    extends javax.ejb.EJBLocalHome
{
    public Integer createInteger(int aParam)
        throws CreateException;
        
    abstract void createSomething(int aParam);
    
    abstract void findSomething(int aParam);
    
    public Integer findInteger(int aParam)
        throws FinderException;
    
    public void method() throws java.rmi.RemoteException;
}
