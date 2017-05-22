package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by ltudor on 3/28/17.
 */
public final class AvoidAnnotationCombinationCheck extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_COMBINATION_ILLEGAL = "annotation.combination.illegal";

    /**
     * Matches {@code @} at the beginning of a string.
     * Used for transforming annotations specified as {@code @Override} into {@code Override}.
     */
    private static final Pattern MATCH_STARTING_AT = CommonUtils.createPattern("^\\@");

    /**
     * This field contains the de-cannonized annotation set by the user.
     * If the user specifies the annotation as FQDN e.g. {@code java.lang.Override} then this will contain
     * just {@code Override} -- but the {@link #fullAnnotation} Optional instance will store the FQDN form (in
     * this case {@code java.lang.Override}.
     * Also note that this value is stripped of any leading {@code @} so if the user specifies {@code @Override}
     * it will store just {@code Override}.
     *
     * @see #fullAnnotation
     */
    private String annotation;

    /**
     * Stores the FQDN of the annotation if given by the user otherwise will be empty.
     * If the user specifies in {@link #setAnnotation(String)} the value {@code java.lang.Override} then this
     * member will be initialize to an Optional instance with the value {@code java.lang.Override}. If however
     * the user specifies in {@link #setAnnotation(String)} just {@code Override} as the value then this will be
     * an empty Optional.
     *
     * @see #setAnnotation(String)
     */
    private Optional<String> fullAnnotation;

    /**
     * Stores the modifier as specified via the user (e.g. {@code final} or {@code static} etc.).
     * Based on this we compute internally the value of {@link #modifierInt}.
     *
     * @see TokenUtils#getTokenId(String)
     */
    private String modifier;

    /**
     * Token value for the token specified in {@link #modifier}.
     * "Computed" via {@link TokenUtils#getTokenId(String)}.
     *
     * @see #modifier
     */
    private int modifierInt;

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        String processed = MATCH_STARTING_AT.matcher(annotation).replaceFirst("");
        int lastIdx = processed.lastIndexOf('.');
        if (lastIdx >= 0) {
            this.fullAnnotation = Optional.of(processed);
            this.annotation = processed.substring(lastIdx + 1);
        } else {
            this.fullAnnotation = Optional.empty();
            this.annotation = processed;
        }
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
        this.modifierInt = TokenUtils.getTokenId(modifier);
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[]
                {TokenTypes.VARIABLE_DEF,};
    }

    @Override
    public void visitToken(final DetailAST ast) {
        if (!ast.branchContains(modifierInt)) return;
        if (AnnotationUtility.containsAnnotation(ast, annotation)
                || fullAnnotation.map(s -> AnnotationUtility.containsAnnotation(ast, s)).orElse(false)) {
            log(ast.getLineNo(), MSG_KEY_ANNOTATION_COMBINATION_ILLEGAL, annotation, modifier);
        }
    }
}
