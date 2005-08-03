package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.checks.imports.AccessResult;
import com.puppycrawl.tools.checkstyle.checks.imports.Guard;
import com.puppycrawl.tools.checkstyle.checks.imports.PkgControl;
import junit.framework.TestCase;

public class PkgControlTest extends TestCase
{
    private final PkgControl pcRoot = new PkgControl("com.kazgroup.courtlink");
    private final PkgControl pcCommon = new PkgControl(pcRoot, "common");

    protected void setUp() throws Exception
    {
        super.setUp();
        pcRoot.addGuard(new Guard(false, false, "org.springframework", false));
        pcRoot.addGuard(new Guard(false, false, "org.hibernate", false));
        pcRoot.addGuard(new Guard(true, false, "org.apache.commons", false));

        pcCommon.addGuard(new Guard(true, false, "org.hibernate", false));
    }

    public void testFullPkg()
    {
        assertEquals("com.kazgroup.courtlink", pcRoot.getFullPackage());
        assertEquals("com.kazgroup.courtlink.common", pcCommon.getFullPackage());
    }

    public void testLocateFinest()
    {
        assertEquals(pcRoot, pcRoot
                .locateFinest("com.kazgroup.courtlink.domain"));
        assertEquals(pcCommon, pcRoot
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertNull(pcRoot.locateFinest("com"));
    }

    public void testCheckAccess()
    {
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.springframework.something",
                "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.ALLOWED, pcCommon
                .checkAccess("org.apache.commons.something",
                        "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.apache.commons", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.ALLOWED, pcCommon.checkAccess(
                "org.hibernate.something", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "com.badpackage.something", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcRoot.checkAccess(
                "org.hibernate.something", "com.kazgroup.courtlink"));
    }
    
    public void testUnknownPkg()
    {
        assertNull(pcRoot.locateFinest("net.another"));
    }
}
