/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

/**
 * Input for HideUtilityClassConstructorCheck, a non utility class that has
 *
 * @author lkuehne
 */
public class InputHideUtilityClassConstructorRegression
{
    public long constructionTime = System.currentTimeMillis();

    public static InputHideUtilityClassConstructorRegression create()
    {
        return new InputHideUtilityClassConstructorRegression();
    }
}
