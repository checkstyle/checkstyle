////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.PrintStream;
import java.io.Serializable;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Locale;
//import java.util.Map;
import java.util.Properties;
//import java.util.Set;
//
//import org.apache.regexp.RESyntaxException;
//
//import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Represents the configuration that checkstyle uses when checking. A
 * configuration is Serializable, however the ClassLoader
 * configuration is lost.
 * @author Rick Giles
 **/
public class Configuration
    implements Serializable
{
    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** global configuration properties **/
    private GlobalProperties mGlobalProps = new GlobalProperties();

    /** check configurations **/
    private CheckConfiguration[] mCheckConfigs =
        new CheckConfiguration[0];

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new <code>Configuration</code> instance.
     *
     * @param aGlobalProps the global properties for the configuration
     * @param aCheckConfs array of check configurations
     */
    public Configuration(GlobalProperties aGlobalProps,
                          CheckConfiguration[] aCheckConfs)
    {
        mGlobalProps = aGlobalProps;
        mCheckConfigs = aCheckConfs;
    }

    /**
     * Creates a new <code>Configuration</code> instance.
     */
    public Configuration()
    {
    }



    /**
     * Extend default deserialization to initialize the RE member variables.
     *
     * @param aStream the ObjectInputStream that contains the serialized data
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object
     *     could not be found
     */
    private void readObject(ObjectInputStream aStream)
        throws IOException, ClassNotFoundException
    {
        // initialize the serialized fields
        aStream.defaultReadObject();
    }


    ////////////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Set the class loader for locating classes.
     * @param aLoader the class loader
     */
    public void setClassLoader(ClassLoader aLoader)
    {
        mGlobalProps.setClassLoader(aLoader);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////

    /** @return a Properties object representing the current configuration.
     * The returned object can be used to recreate the configuration.
     * Tip: used on a default object returns all the default objects. */
    public Properties getProperties()
    {
        return mGlobalProps.getProperties();
    }

    /** @return the class loader **/
    ClassLoader getClassLoader()
    {
        return mGlobalProps.getClassLoader();
    }
    
    /** @return the base directory **/
    String getBasedir()
    {
        return mGlobalProps.getBasedir();
    }

    /** @return locale language to report messages  **/
    String getLocaleLanguage()
    {
        return mGlobalProps.getLocaleLanguage();
    }

    /** @return locale country to report messages  **/
    String getLocaleCountry()
    {
        return mGlobalProps.getLocaleCountry();
    }

    /** @return distance between tab stops */
    int getTabWidth()
    {
        return mGlobalProps.getTabWidth();
    }

    /** @return whether javadoc package documentation is required */
    boolean isRequirePackageHtml()
    {
        return mGlobalProps.isRequirePackageHtml();
    }

    /** @return the File of the cache file **/
    String getCacheFile()
    {
        return mGlobalProps.getCacheFile();
    }
    
    /** @return the global properties **/
    public GlobalProperties getGlobalProperties()
    {
        return mGlobalProps;
    }
    
    /** @return the check configurations **/
    public CheckConfiguration[] getCheckConfigurations()
    {
        return mCheckConfigs;
    }
}
