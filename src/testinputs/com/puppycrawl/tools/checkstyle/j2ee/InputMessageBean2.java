package com.puppycrawl.tools.checkstyle.j2ee;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Test MessageBeanCheck
 */
public class InputMessageBean2
    implements MessageDrivenBean, MessageListener 
{
    public InputMessageBean2()
    {
    }
    
    public void ejbCreate()
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
}

