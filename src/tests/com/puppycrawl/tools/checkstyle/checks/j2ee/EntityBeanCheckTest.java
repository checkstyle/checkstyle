package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanCheck;
import com.puppycrawl.tools.checkstyle.checks.j2ee.PersistenceOption;

public class EntityBeanCheckTest extends BaseCheckTestCase
{
    public void testDefaultBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testMixedBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        checkConfig.addAttribute("persistence", 
            PersistenceOption.MIXED.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testBeanBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        checkConfig.addAttribute("persistence", 
            PersistenceOption.BEAN.toString());
        final String[] expected = {
            "13:14: Entity bean 'InputEntityBean' must have method 'ejbFindByPrimaryKey'.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testAbstractBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("j2ee/InputAbstractEntityBean.java"), expected);
        checkConfig.addAttribute("persistence", 
             PersistenceOption.CONTAINER.toString());
        verify(checkConfig, getPath("j2ee/InputAbstractEntityBean.java"), expected);
    }
    
    public void testAbstractBeanBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        checkConfig.addAttribute("persistence", 
             PersistenceOption.BEAN.toString());
        final String[] expected = {
            "8:23: Entity bean 'InputAbstractEntityBean' must not be abstract.",
        };
        verify(checkConfig, getPath("j2ee/InputAbstractEntityBean.java"), expected);
    }
    
    public void testContainerBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        checkConfig.addAttribute("persistence", 
            PersistenceOption.CONTAINER.toString());
        final String[] expected = {
            "13:14: Entity bean 'InputEntityBean' must be abstract.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testCreate()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        final String[] expected = {
            "20:31: Method 'ejbCreateSomething' must be non-void.",
            "20:31: Method 'ejbCreateSomething' must be public.",
            "20:31: Method 'ejbCreateSomething' must have a matching 'ejbPostCreateSomething' method.",
            "20:31: Method 'ejbCreateSomething' must not have modifier 'final'.",
            "20:31: Method 'ejbCreateSomething' must not have modifier 'static'.",
        };
        // default (MIXED) persistence
        verify(checkConfig, getPath("j2ee/InputEntityBeanCreate.java"), expected);
        //  bean-managed persistence
        checkConfig.addAttribute(
            "persistence",
            PersistenceOption.BEAN.toString());
        verify(checkConfig, getPath("j2ee/InputEntityBeanCreate.java"), expected);
//        //  container-managed persistence
//        checkConfig.addAttribute(
//            "persistence",
//            PersistenceOption.CONTAINER.toString());
//        verify(checkConfig, getPath("InputEntityBeanCreate.java"), expected);
    }
    
    public void testPostCreate()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        final String[] expected = {
            "20:33: Method 'ejbPostCreateSomething' must be public.",
            "20:33: Method 'ejbPostCreateSomething' must be void.",
            "20:33: Method 'ejbPostCreateSomething' must not have modifier 'final'.",
            "20:33: Method 'ejbPostCreateSomething' must not have modifier 'static'.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBeanPostCreate.java"), expected);
        //  bean-managed persistence
        checkConfig.addAttribute(
            "persistence",
            PersistenceOption.BEAN.toString());
        verify(checkConfig, getPath("j2ee/InputEntityBeanPostCreate.java"), expected);
//        //  container-managed persistence
//        checkConfig.addAttribute(
//            "persistence",
//            PersistenceOption.CONTAINER.toString());
//        verify(checkConfig, getPath("InputEntityBeanCreate.java"), expected);
    }

    public void testHome()
             throws Exception
     {
         final DefaultConfiguration checkConfig =
             createCheckConfig(EntityBeanCheck.class);
         final String[] expected = {
             "20:31: Method 'ejbHomeSomething' must be public.",
             "20:31: Method 'ejbHomeSomething' must not have modifier 'static'.",
             "20:31: Method 'ejbHomeSomething' must not throw 'java.rmi.RemoteException'.",
         };
         // default (MIXED) persistence
         verify(checkConfig, getPath("j2ee/InputEntityBeanHome.java"), expected);
         //  bean-managed persistence
         checkConfig.addAttribute(
             "persistence",
             PersistenceOption.BEAN.toString());
         verify(checkConfig, getPath("j2ee/InputEntityBeanHome.java"), expected);
//         //  container-managed persistence
//         checkConfig.addAttribute(
//             "persistence",
//             PersistenceOption.CONTAINER.toString());
//         verify(checkConfig, getPath("InputEntityBeanHome.java"), expected);
     }    
    public void testFind()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        checkConfig.addAttribute("persistence", 
            PersistenceOption.BEAN.toString());
        final String[] expected = {
            "20:31: Method 'ejbFindSomething' must be non-void.",
            "20:31: Method 'ejbFindSomething' must be public.",
            "20:31: Method 'ejbFindSomething' must not have modifier 'final'.",
            "20:31: Method 'ejbFindSomething' must not have modifier 'static'.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBeanFind.java"), expected);
    }
}
