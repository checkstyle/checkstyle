package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;


public class DigesterLoaderTest
{
    public static void main(String[] args)
    {
        //test rules with elements named by shortened class name
        try {
            final String inputFname = "src/digester/checkstyle_checks.xml";
            final String rulesFname = "src/digester/checkstyle_rules.xml";
            final Configuration config =
                ConfigurationDigesterLoader.loadConfiguration(
                    rulesFname, inputFname, null);
            dump(config, 0);

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        
        //test rules with config elements
        try {
            final String inputFname = "src/digester/checkstyle_checksB.xml";
            final String rulesFname = "src/digester/checkstyle_rulesB.xml";
            final Configuration config =
                ConfigurationDigesterLoader.loadConfiguration(
                    rulesFname, inputFname, null);
            dump(config, 0);

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Method dump.
     * @param config
     */
    private static void dump(Configuration aConfig, int aLevel)
    {
        for (int i = 0; i < aLevel; i++) {
            System.out.print("  ");
        }
        dumpDetails(aConfig);
        final Configuration[] children = aConfig.getChildren();
        for (int i = 0; i < children.length; i++) {
            dump(children[i], aLevel + 1);
        }          
    }

    /**
     * Method dumpDetails.
     * @param config
     */
    private static void dumpDetails(Configuration aConfig)
    {
        System.out.print(aConfig.getName() + "[");
        final String[] attNames = aConfig.getAttributeNames();
        for (int i = 0; i < attNames.length; i++) {
            try {
                System.out.print(attNames[i] + "="
                    + aConfig.getAttribute(attNames[i]) + ",");
            }
            catch (CheckstyleException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("]");
    }


}