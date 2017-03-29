package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.puppycrawl.tools.checkstyle.checks.annotation.AvoidAnnotationCombinationCheck.MSG_KEY_ANNOTATION_COMBINATION_ILLEGAL;

/**
 * Created by ltudor on 3/28/17.
 */
public class AvoidAnnotationCombinationCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "annotation" + File.separator + filename);
    }

    /**
     * This tests that specifying a combination modifier + annotation correctly flags it.
     */
    @Test
    public void testBadOverrideFromObject() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AvoidAnnotationCombinationCheck.class);
        checkConfig.addAttribute("annotation", "SuppressWarnings");
        checkConfig.addAttribute("modifier", "FINAL");

        final String[] expected = {
                "7: " + getCheckMessage(MSG_KEY_ANNOTATION_COMBINATION_ILLEGAL, "SuppressWarnings", "FINAL")
        };

        verify(checkConfig, getPath("WrongAnnotationAndModifierCombination.java"), expected);
    }

    /**
     * This tests that specifying a combination modifier + annotation correctly flags it.
     */
    @Test
    public void testBadOverrideFromObjectWithFqdn() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AvoidAnnotationCombinationCheck.class);
        checkConfig.addAttribute("annotation", "java.lang.SuppressWarnings");
        checkConfig.addAttribute("modifier", "FINAL");

        final String[] expected = {
                "7: " + getCheckMessage(MSG_KEY_ANNOTATION_COMBINATION_ILLEGAL, "SuppressWarnings", "FINAL")
        };

        verify(checkConfig, getPath("WrongAnnotationAndModifierCombination.java"), expected);
    }
}
