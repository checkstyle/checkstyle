package com.puppycrawl.tools.checkstyle;

import java.util.Properties;

import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;
import com.puppycrawl.tools.checkstyle.checks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.RightCurlyOption;

import junit.framework.TestCase;

/**
 * @author Rick Giles
 * @version 14-Nov-2002
 */
public class ConfigurationLoaderTest extends TestCase
{
 
    private static Configuration loadConfiguration(String aName)
        throws CheckstyleException
    {
        String fName =
            System.getProperty("testinputs.dir") + "/configs/" + aName;
        return ConfigurationLoader.loadConfiguration(fName, new Properties());
    }
    
    public void testEmptyConfiguration()
        throws Exception
    {
        Configuration config =
            loadConfiguration("empty_configuration.xml");
                    
        GlobalProperties globalProps =
            new GlobalProperties(new Properties(), System.out);
        assertEquals("properties", globalProps.getProperties(),
            config.getProperties());
                
        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
        assertEquals("checkConfigs.length", 0, checkConfigs.length);
    }
    
    public void testCheck()
        throws Exception
    {
        Configuration config =
            loadConfiguration("avoidstarimport_configuration.xml");
                    
        GlobalProperties globalProps =
            new GlobalProperties(new Properties(), System.out);
        assertEquals("properties", globalProps.getProperties(),
            config.getProperties());
                
        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
        assertEquals("checkConfigs.length", 1, checkConfigs.length);
        
        assertTrue("checkConfigs[0]",
            (checkConfigs[0].createInstance(this.getClass().getClassLoader()))
                instanceof AvoidStarImport);
    }
    
    public void testCheckOption()
        throws Exception
    {
        Configuration config =
            loadConfiguration("rightcurlycheck_configuration.xml");
                    
        GlobalProperties globalProps =
            new GlobalProperties(new Properties(), System.out);
        assertEquals("properties", globalProps.getProperties(),
            config.getProperties());
                
        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
        assertEquals("checkConfigs.length", 1, checkConfigs.length);
        
        RightCurlyCheck rightCurly =
            (RightCurlyCheck) (checkConfigs[0].
                createInstance(this.getClass().getClassLoader()));
        RightCurlyOption option =
            (RightCurlyOption) rightCurly.getAbstractOption();
        assertEquals("option", "alone", option.toString());
    }
    
    public void testOverrideGlobalProperties()
        throws Exception
    {
        Configuration config =
            loadConfiguration("override_default_configuration.xml");
        assertEquals("checkstyle.tab.width", 4, config.getTabWidth());
        assertEquals("checkstyle.cache.file", config.getCacheFile(), "cache");
        assertEquals("checkstyle.basedir", config.getBasedir(), "basedir");
        assertEquals("checkstyle.locale.language",
            config.getLocaleLanguage(), "language");
        assertEquals("checkstyle.locale.country",
            config.getLocaleCountry(), "country");
    }
    
    /** check that a global property doesn't effect a check property.
     *  and vice versa
     */
    public void testGlobalConflict()
        throws Exception
    {
        Configuration config =
            loadConfiguration("conflict_configuration.xml");
                    
        GlobalProperties globalProps =
            new GlobalProperties(new Properties(), System.out);
        assertEquals("properties", globalProps.getProperties(),
            config.getProperties());
                
        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
        
        RightCurlyCheck rightCurly =
            (RightCurlyCheck) (checkConfigs[0].
                createInstance(this.getClass().getClassLoader()));
        RightCurlyOption option =
            (RightCurlyOption) rightCurly.getAbstractOption();
        assertEquals("option", "alone", option.toString());
    }
    
    /** check that a global property doesn't effect a check property.
     *  and vice versa
     */
    public void testFromGlobal()
        throws Exception
    {
        Configuration config =
            loadConfiguration("fromglobal_configuration.xml");
                                    
        CheckConfiguration[] checkConfigs =
            config.getCheckConfigurations();        
        RightCurlyCheck rightCurly =
            (RightCurlyCheck) (checkConfigs[0].
                createInstance(this.getClass().getClassLoader()));
        RightCurlyOption option =
            (RightCurlyOption) rightCurly.getAbstractOption();
        assertEquals("option", "alone", option.toString());
    }
    
    public void testOverridePropsGlobal()
        throws Exception
    {
        String fName = System.getProperty("testinputs.dir") + "/configs/"
                + "fromglobal_configuration.xml";
        
        Properties overrideProps = new Properties();
        overrideProps.put("rightcurlycheck.option", "same");
        
        Configuration config =
            ConfigurationLoader.loadConfiguration(fName, overrideProps);
                                    
        CheckConfiguration[] checkConfigs =
            config.getCheckConfigurations();        
        RightCurlyCheck rightCurly =
            (RightCurlyCheck) (checkConfigs[0].
                createInstance(this.getClass().getClassLoader()));
        RightCurlyOption option =
            (RightCurlyOption) rightCurly.getAbstractOption();
        assertEquals("option", "same", option.toString());
    }       
}
