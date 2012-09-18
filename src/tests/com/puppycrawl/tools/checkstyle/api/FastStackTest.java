////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FastStackTest
{
    @Test(expected = IllegalStateException.class)
    public void testPeek()
    {
        new FastStack<String>().peek();
    }

    @Test(expected = IllegalStateException.class)
    public void testPop()
    {
        new FastStack<String>().pop();
    }

    @Test
    public void testNormal()
    {
        final FastStack<Integer> fs = FastStack.newInstance();
        assertNotNull(fs);
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());
        final int num = 100;
        for (int i = 0; i < num; i++) {
            fs.push(i);
        }
        assertEquals(num, fs.size());
        assertFalse(fs.isEmpty());
        assertEquals(1, fs.peek(1).intValue());
        assertEquals(num - 1, fs.peek().intValue());
        assertEquals(num, fs.size());
        for (int i = 0; i < num; i++) {
            fs.pop();
        }
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());

        fs.push(666);
        assertEquals(1, fs.size());
        assertFalse(fs.isEmpty());
        assertTrue(fs.contains(666));
        assertFalse(fs.contains(667));
        fs.clear();
        assertFalse(fs.contains(666));
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());
        assertFalse(fs.iterator().hasNext());
    }
}
