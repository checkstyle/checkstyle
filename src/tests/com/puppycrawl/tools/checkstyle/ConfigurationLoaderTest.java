package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.util.Properties;
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
//        Configuration config =
//            loadConfiguration("empty_configuration.xml");
//                    
//        GlobalProperties globalProps =
//            new GlobalProperties(new Properties(), System.out);
//        assertEquals("properties", globalProps.getProperties(),
//            config.getProperties());
//                
//        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
//        assertEquals("checkConfigs.length", 0, checkConfigs.length);
    }
    
    public void testCheck()
        throws Exception
    {
//        Configuration config =
//            loadConfiguration("avoidstarimport_configuration.xml");
//                    
//        GlobalProperties globalProps =
//            new GlobalProperties(new Properties(), System.out);
//        assertEquals("properties", globalProps.getProperties(),
//            config.getProperties());
//                
//        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
//        assertEquals("checkConfigs.length", 1, checkConfigs.length);
//        
//        assertTrue("checkConfigs[0]",
//            (checkConfigs[0].createInstance(this.getClass().getClassLoader()))
//                instanceof AvoidStarImport);
    }
    
    public void testCheckOption()
        throws Exception
    {
//        Configuration config =
//            loadConfiguration("rightcurlycheck_configuration.xml");
//                    
//        GlobalProperties globalProps =
//            new GlobalProperties(new Properties(), System.out);
//        assertEquals("properties", globalProps.getProperties(),
//            config.getProperties());
//                
//        CheckConfiguration[] checkConfigs = config.getCheckConfigurations();
//        assertEquals("checkConfigs.length", 1, checkConfigs.length);
//        
//        RightCurlyCheck rightCurly =
//            (RightCurlyCheck) (checkConfigs[0].
//                createInstance(this.getClass().getClassLoader()));
//        RightCurlyOption option =
//            (RightCurlyOption) rightCurly.getAbstractOption();
//        assertEquals("option", "alone", option.toString());
    }
    
}
