package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.checks.design.InputLocalAnnotations.Rule;
import com.puppycrawl.tools.checkstyle.checks.design.InputLocalAnnotations.ClassRule;

public class InputVisibilityModifierAnnotatedSameTypeName
{
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder();

    @ClassRule
    public TemporaryFolder publicJUnitClassRule = new TemporaryFolder();
}
