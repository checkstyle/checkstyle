import com.puppycrawl.tools.checkstyle.checks.j2ee.*;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Describe class AllTests
 * @author Rick Giles
 * @version 28-Jun-2003
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for J2EE checks");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(EntityBeanCheckTest.class));
        suite.addTest(new TestSuite(FinalStaticCheckTest.class));
        suite.addTest(new TestSuite(LocalHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(LocalInterfaceCheckTest.class));
        suite.addTest(new TestSuite(MessageBeanCheckTest.class));
        suite.addTest(new TestSuite(RemoteHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(RemoteInterfaceCheckTest.class));
        suite.addTest(new TestSuite(SessionBeanCheckTest.class));
        suite.addTest(new TestSuite(ThisParameterCheckTest.class));
        suite.addTest(new TestSuite(ThisReturnCheckTest.class));
        //$JUnit-END$
        return suite;
    }
}
