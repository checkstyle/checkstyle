package com.puppycrawl.tools.checkstyle.checks.design;

import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.checks.design.InputLocalAnnotations.Rule;

public class InputAnnotatedVisibilitySameTypeName
{
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder();
}
