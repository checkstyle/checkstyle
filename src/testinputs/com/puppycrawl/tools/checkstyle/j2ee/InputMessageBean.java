package com.puppycrawl.tools.checkstyle.j2ee;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Test MessageBeanCheck
 */
public class InputMessageBean
    implements MessageDrivenBean, MessageListener 
{

    /**
     * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.MessageDrivenBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message arg0) {
        // TODO Auto-generated method stub
        
    }
}

abstract class AbstractMessageBean
    implements MessageDrivenBean, MessageListener 
{
    public AbstractMessageBean()
    {
    }
    
    public void ejbCreate()
    {
    }
}

final class FinalMessageBean
    implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener 
{
    public FinalMessageBean()
    {
    }

    public Object method()
    {
        this.equals("");
        "".equals(this);
        return this;
    }

    protected static final int ejbCreate(int i)
    {
        return 0;
    }
    /**
     * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
     */
    public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.ejb.MessageDrivenBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message arg0) {
        // TODO Auto-generated method stub
        
    }
    
    public void ejbCreate()
    {
    }
}