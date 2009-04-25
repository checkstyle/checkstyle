package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.io.File;
import org.junit.Test;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class PackageAnnotationTest extends BaseCheckTestSupport
{
    /**
     * This tests 1 package annotation that is not in the package-info.java file.
     * 
     * @throws Exception
     */
    @Test
    public void testBadPackageAnnotation1() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);
        
        final String[] expected = {
            "0: Package annotations must be in the package-info.java info.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadPackageAnnotation1.java"), expected);
    }
    
    /**
     * This tests 2 package annotations that are not in the package-info.java file.
     * 
     * @throws Exception
     */
    @Test
    public void testBadPackageAnnotation2() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);
        
        final String[] expected = {
            "0: Package annotations must be in the package-info.java info.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadPackageAnnotation2.java"), expected);
    }
    
    /**
     * This tests a package annotation that is in the package-info.java file.
     * 
     * @throws Exception
     */
    @Test
    public void testGoodPackageAnnotation() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);
        
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "package-info.java"), expected);
    }
}
