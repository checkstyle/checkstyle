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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import com.puppycrawl.tools.checkstyle.api.Configuration;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Describe class <code>ConfigurationLoaderDigester</code> here.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class ConfigurationDigesterLoader
    extends DefaultHandler
{
    /** overriding properties **/
    private Properties mOverrideProps = new Properties();

    /**
     * Creates a new <code>ConfigurationLoaderDigester</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private ConfigurationDigesterLoader()
    {
    }
    
    /**
     * Returns the check configurations in a specified file.
     * @param aRulesFname name of digester rules file
     * @param aConfigFname name of config file
     * @param aOverrideProps overriding properties
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     */
    public static Configuration loadConfiguration(String aRulesFname,
                                                   String aConfigFname,
                                                   Properties aOverrideProps)
        throws CheckstyleException
    {
        try {
            final File rulesFile = new File(aRulesFname);
            final File configFile = new File(aConfigFname);
            final RuleSet ruleSet = new FromXmlRuleSet(rulesFile.toURL(),
                new ConfigurationRuleParser());
            final Digester digester = new Digester();
            digester.addRuleSet(ruleSet);
            digester.setValidating(false);
            digester.push(new DefaultConfiguration("root"));
            //TODO: apply aOverrideProps and perform property expansion
            return (DefaultConfiguration) digester.parse(configFile);
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aConfigFname);
        }
        catch (SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aConfigFname + " - " + e.getMessage());
        }
        catch (IOException e) {
            throw new CheckstyleException("unable to read " + aConfigFname);
        }
    }
}