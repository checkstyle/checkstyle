////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.Configuration;
import java.io.*;
import java.lang.*;
import java.lang.String;
import java.sql.Connection;
import java.util.List;
import java.util.List;

/**
 * Test case for imports
 * @author Oliver Burn
 **/
class InputImport
{
    /** ignore **/
    private Class mUse1 = Connection.class;
    /** ignore **/
    private Class mUse2 = java.io.File.class;
}
