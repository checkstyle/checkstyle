package com.puppycrawl.tools.checkstyle.checks.imports;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AccessResultTest.class, AvoidStarImportTest.class,
        AvoidStarImportTest.class, GuardTest.class,
        IllegalImportCheckTest.class,
        ImportControlCheckTest.class, ImportControlLoaderTest.class,
        ImportOrderCheckTest.class,
        PkgControlTest.class, RedundantImportCheckTest.class,
        UnusedImportsCheckTest.class })
public class AllImportsTests
{
}
