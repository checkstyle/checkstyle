package com.puppycrawl.tools.checkstyle;

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
}

final class FinalMessageBean
    implements MessageDrivenBean, MessageListener 
{
    public FinalMessageBean()
    {
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