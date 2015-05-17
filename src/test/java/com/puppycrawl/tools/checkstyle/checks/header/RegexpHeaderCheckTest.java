////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.header;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author richter
 */
public class RegexpHeaderCheckTest {

    public RegexpHeaderCheckTest() {
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderNull() {
        // check null passes
        RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // recreate for each test because multiple invocations fail
        String header = null;
        instance.setHeader(header);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderEmpty() {
        // check null passes
        RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // check empty string passes
        instance = new RegexpHeaderCheck();
        String header = "";
        instance.setHeader(header);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderSimple() {
        RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // check valid header passes
        instance = new RegexpHeaderCheck();
        String header = "abc.*";
        instance.setHeader(header);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeader() {
        // check invalid header passes
        RegexpHeaderCheck instance = new RegexpHeaderCheck();
        String header = "^/**\\n * Licensed to the Apache Software Foundation (ASF)";
        try {
            instance.setHeader(header);
            Assert.fail(String.format("%s should have been thrown", ConversionException.class));
        }
        catch (ConversionException ex) {
            // expected
        }
    }

}
