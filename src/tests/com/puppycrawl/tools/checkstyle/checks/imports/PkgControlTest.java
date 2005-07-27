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
        pcRoot.addGuard(new Guard(false, "org.springframework"));
        pcRoot.addGuard(new Guard(false, "org.hibernate"));
        pcRoot.addGuard(new Guard(true, "org.apache.commons"));

        pcCommon.addGuard(new Guard(true, "org.hibernate"));
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
        assertEquals(AccessResult.DISALLOWED, pcCommon
                .checkAccess("org.springframework.something"));
        assertEquals(AccessResult.ALLOWED, pcCommon
                .checkAccess("org.apache.commons.something"));
        assertEquals(AccessResult.DISALLOWED, pcCommon
                .checkAccess("org.apache.commons"));
        assertEquals(AccessResult.ALLOWED, pcCommon
                .checkAccess("org.hibernate.something"));
        assertEquals(AccessResult.DISALLOWED, pcCommon
                .checkAccess("com.badpackage.something"));
        assertEquals(AccessResult.DISALLOWED, pcRoot
                .checkAccess("org.hibernate.something"));
    }
}
