////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.xpath;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Iterator for an attribute axis of an XPath element. The XPath
 * element is a DetailAST. Attributes correspond to bean
 * properties of the DetailAST.
 * @author Rick Giles
 */
public class AttributeAxisIterator
    implements Iterator
{
    /** actual iterator */
    private Iterator mIter = (new ArrayList()).iterator();

    /**
     * Constructs a <code>AttributeAxisIterator</code> for a
     * DetailAST element.
     * @param aAST the DetailAST element
     */
    public AttributeAxisIterator(DetailAST aAST)
    {
        Map props = new HashMap();
        //use BeanUtils to get the properties
        try {
            props = BeanUtils.describe(aAST);
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // add attributes for the properties to a list and
        // set the iterator to an iterator over that list.
        final List attributes = new ArrayList(props.size());
        final Set values = props.keySet();
        for (Iterator iter = values.iterator(); iter.hasNext();) {
            final String name = (String) iter.next();
            final String value = (String) props.get(name);
            attributes.add(new Attribute(aAST, name, value));
        }
        mIter = attributes.iterator();
    }

    /**@see java.util.Iterator#next() */
    public Object next()
    {
        return mIter.next();
    }

    /** @see java.util.Iterator#hasNext() */
    public boolean hasNext()
    {
        return mIter.hasNext();
    }

    /** @see java.util.Iterator#remove() */
    public void remove()
    {
        mIter.remove();
    }
}