package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Locale;
import java.util.Properties;

public class ConfigurationTest
    extends TestCase
{
    private final String BASEDIR = "basedir";
    private final String CACHE_FILE = "cache_file";
    
    private final int TAB_WIDTH = 8;
    
    private Properties mProps = null;
    private GlobalProperties mGlobalProps = null;
    private Configuration mConfig = null;
    
    public void setUp()
        throws Exception
    {
        mProps = new Properties();
        mProps.setProperty(Defn.BASEDIR_PROP, BASEDIR);
        mProps.setProperty(Defn.CACHE_FILE_PROP, CACHE_FILE);
        mProps.setProperty(Defn.TAB_WIDTH_PROP, "" + TAB_WIDTH);
        mGlobalProps = new GlobalProperties(mProps, System.out);
        mConfig = new Configuration(mGlobalProps, new CheckConfiguration[0]);
        mConfig.setClassLoader(this.getClass().getClassLoader());
    }
    
    public void testConstructor() throws Exception
    {
        assertNotNull(mConfig);
    }
    
    public void testEmptyProperties()
        throws Exception
    {
        Properties props = new Properties();
        GlobalProperties globalProps = new GlobalProperties(props, System.out);
        Configuration config =
            new Configuration(globalProps, new CheckConfiguration[0]);
        Properties defaultProps = config.getProperties();
        assertEquals(4, defaultProps.size());
    }
        
    
    public void testGetCacheFile()
    {
        assertEquals(CACHE_FILE, mConfig.getCacheFile());
    }
    
    public void testGetClassLoader()
    {
        assertEquals(this.getClass().getClassLoader(),
                     mConfig.getClassLoader());
    }
    
    public void testGetLocaleCountry()
    {
        assertEquals(Locale.getDefault().getCountry(),
            mConfig.getLocaleCountry());
    }
    
    public void testGetLocaleLanguage()
    {
        assertEquals(Locale.getDefault().getLanguage(),
            mConfig.getLocaleLanguage());
    }
    
    public void testGetProperties()
    {
        Properties props = mConfig.getProperties();
        int expectedSize = Defn.ALL_STRING_PROPS.length
                           + Defn.ALL_INT_PROPS.length;
        assertEquals("size", expectedSize, props.size());
        
        for (int i = 0; i < Defn.ALL_INT_PROPS.length; i++) {
            final String key = Defn.ALL_INT_PROPS[i];
            assertTrue("Missing property: " + key,
                       props.containsKey(key));
        }

        for (int i = 0; i < Defn.ALL_STRING_PROPS.length; i++) {
            final String key = Defn.ALL_STRING_PROPS[i];
            assertTrue("Missing property: " + key,
                       props.containsKey(key));
        }
    }
    
    public void testGetTabWidth()
    {
        assertEquals(TAB_WIDTH, mConfig.getTabWidth());
    }
}
