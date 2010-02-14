package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class PkgControlRegExpTest
{
    private final PkgControl pcRoot = new PkgControl("com.kazgroup.courtlink");
    private final PkgControl pcCommon = new PkgControl(pcRoot, "common");

    @Before
    public void setUp() throws Exception
    {
        pcRoot.addGuard(new Guard(false, false, ".*\\.(spring|lui)framework", false, true));
        pcRoot.addGuard(new Guard(false, false, "org\\.hibernate", false, true));
        pcRoot.addGuard(new Guard(true, false, "org\\.(apache|lui)\\.commons", false, true));

        pcCommon.addGuard(new Guard(true, false, "org\\.h.*", false, true));
    }

    @Test
    public void testFullPkg()
    {
        assertEquals("com.kazgroup.courtlink", pcRoot.getFullPackage());
        assertEquals("com.kazgroup.courtlink.common", pcCommon.getFullPackage());
    }

    @Test
    public void testLocateFinest()
    {
        assertEquals(pcRoot, pcRoot
                .locateFinest("com.kazgroup.courtlink.domain"));
        assertEquals(pcCommon, pcRoot
                .locateFinest("com.kazgroup.courtlink.common.api"));
        assertNull(pcRoot.locateFinest("com"));
    }

    @Test
    public void testCheckAccess()
    {
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.springframework.something",
                "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.luiframework.something",
                "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "de.springframework.something",
                "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "de.luiframework.something",
                "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.ALLOWED, pcCommon
                .checkAccess("org.apache.commons.something",
                        "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.ALLOWED, pcCommon
                .checkAccess("org.lui.commons.something",
                        "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.apache.commons", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "org.lui.commons", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.ALLOWED, pcCommon.checkAccess(
                "org.hibernate.something", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcCommon.checkAccess(
                "com.badpackage.something", "com.kazgroup.courtlink.common"));
        assertEquals(AccessResult.DISALLOWED, pcRoot.checkAccess(
                "org.hibernate.something", "com.kazgroup.courtlink"));
    }

    @Test
    public void testUnknownPkg()
    {
        assertNull(pcRoot.locateFinest("net.another"));
    }
}
