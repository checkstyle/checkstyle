package com.puppycrawl.tools.checkstyle.j2ee;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

/**
 * Test EntityBean
 */
public class InputEntityBeanMatchEjbCreate
    implements EntityBean
{

    public InputEntityBeanMatchEjbCreate ejbCreate()
    {
        return null;
    }
    
    public InputEntityBeanMatchEjbCreate ejbCreate(int i)
    {
        return null;
    }
    
    public void ejbPostCreate(long i)
    {
    }
    
    public InputEntityBeanMatchEjbCreate ejbCreateThing(int i)
    {
        return null;
    }
    
    public void ejbPostCreatething(int i)
    {
    }
    
    public InputEntityBeanMatchEjbCreate ejbCreateInteger(java.lang.Integer i)
    {
        return null;
    }
    
    public void ejbPostCreateInteger(java.lang.Integer i)
    {
    }
    
    public InputEntityBeanMatchEjbCreate ejbCreateInteger(java.lang.Integer i,
        long x)
    {
        return null;
    }
    
    public void ejbPostCreateInteger(java.lang.Integer i,
        double x)
    {
    }
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

