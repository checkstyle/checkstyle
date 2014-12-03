package com.puppycrawl.tools.checkstyle.design;

/**
 * Input for HideUtilityClassConstructorCheck, a non utility class that has 
 * 
 * @author lkuehne
 */
public class InputRegression1762702
{
    public long constructionTime = System.currentTimeMillis();

    public static InputRegression1762702 create()
    {
        return new InputRegression1762702();
    }
}