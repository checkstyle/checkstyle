package com.puppycrawl.tools.checkstyle;

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
            System.out.println(config);

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
            System.out.println(config);

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}