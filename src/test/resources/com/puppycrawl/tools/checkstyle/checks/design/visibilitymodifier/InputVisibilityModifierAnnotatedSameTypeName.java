package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.InputVisibilityModifierLocalAnnotations.Rule;
import com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier.InputVisibilityModifierLocalAnnotations.ClassRule;

public class InputVisibilityModifierAnnotatedSameTypeName
{
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder();

    @ClassRule
    public TemporaryFolder publicJUnitClassRule = new TemporaryFolder();
}
