package com.puppycrawl.tools.checkstyle;

import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.LocalAnnotations.Rule;

public class AnnotatedVisibilitySameTypeName
{
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder();
}
