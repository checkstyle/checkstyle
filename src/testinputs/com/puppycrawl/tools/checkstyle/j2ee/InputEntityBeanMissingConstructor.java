package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

/**
 * Test EntityBean that is missing a public constructor with no parameters.
 */
public class InputEntityBeanMissingConstructor
    implements EntityBean
{
    public InputEntityBeanMissingConstructor(Object aObject)
    {
    }

    public Object thisMethod(Object aObject)
    {
        thisMethod(this);
        return this;
    }
    
    static int sInt;
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext arg0) throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        
    }
}