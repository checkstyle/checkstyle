package com.puppycrawl.tools.checkstyle.j2ee;

import javax.ejb.EntityBean;

/**
 * Test EntityBean
 */
public abstract class InputAbstractEntityBean
    implements EntityBean
{

    public InputAbstractEntityBean()
    {
    }
    
    public Object ejbFindByPrimaryKey(Object aObject)
    {
        return null;
    }
}