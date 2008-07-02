package com.puppycrawl.tools.checkstyle.checks.naming;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {AbstractClassNameCheckTest.class,
    ConstantNameCheckTest.class, LocalFinalVariableNameCheckTest.class,
    LocalVariableNameCheckTest.class, MemberNameCheckTest.class,
    MethodNameCheckTest.class, PackageNameCheckTest.class,
    ParameterNameCheckTest.class, StaticVariableNameCheckTest.class,
    TypeNameCheckTest.class, TypeParameterNameTest.class})
public class AllNamingTests
{
}
