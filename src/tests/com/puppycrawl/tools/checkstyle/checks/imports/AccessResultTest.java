////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;

public class AccessResultTest
{
    @Test
    public void testNormal()
    {
        assertEquals("ALLOWED", AccessResult.ALLOWED.getLabel());
        assertEquals(AccessResult.ALLOWED.toString(), AccessResult.ALLOWED
                .getLabel());
        assertTrue(AccessResult.DISALLOWED.equals(AccessResult.DISALLOWED));
        assertFalse(AccessResult.DISALLOWED.equals(this));
        assertEquals(AccessResult.DISALLOWED.hashCode(),
                AccessResult.DISALLOWED.hashCode());
        final AccessResult revived = AccessResult
                .getInstance(AccessResult.UNKNOWN.getLabel());
        assertEquals(AccessResult.UNKNOWN, revived);
        assertTrue(AccessResult.UNKNOWN == revived);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadname()
    {
        AccessResult.getInstance("badname");
    }

    @Test
    public void testSerial() throws Exception
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(AccessResult.ALLOWED);
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(bais);
        final AccessResult revivied = (AccessResult) ois.readObject();
        assertNotNull(revivied);
        assertTrue(revivied == AccessResult.ALLOWED);
    }
}
