package com.puppycrawl.tools.checkstyle;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.xmlrules.DigesterRuleParser;

import org.xml.sax.Attributes;

/**
 * This is a RuleSet that parses XML into Digester rules, and then
 * adds those rules to a 'target' Digester.
 * @author Rick Giles
 * @version 1-Dec-2002
 */
public class ConfigurationRuleParser extends DigesterRuleParser
{
    /** name of the Rule class */
    private final String ruleClassName = Rule.class.getName();

    /** @see org.apache.commons.digester.xmlrules.DigesterRuleParser */
    public void addRuleInstances(Digester aDigester)
    {
        super.addRuleInstances(aDigester);
        aDigester.addFactoryCreate(
            "*/config-create-rule", new ConfigCreateRuleFactory());
        aDigester.addRule(
            "*/config-create-rule", new PatternRule("pattern"));
        aDigester.addSetNext("*/config-create-rule", "add", ruleClassName);
        
        aDigester.addFactoryCreate(
            "*/config", new ConfigRuleFactory());
        aDigester.addRule(
            "*/config", new PatternRule("pattern"));
        aDigester.addSetNext("*/config", "add", ruleClassName);
    }

    /**
     * Factory for creating a configCreateRule
     */
    protected class ConfigCreateRuleFactory
        extends AbstractObjectCreationFactory
    {
        public Object createObject(Attributes anAttributes)
        {
            final String className = anAttributes.getValue("classname");

            return new ConfigCreateRule(className);
        }
    }
    
    /**
     * Factory for creating a configCreateRule
     */
    protected class ConfigRuleFactory
        extends AbstractObjectCreationFactory
    {
        public Object createObject(Attributes anAttributes)
        {
            final String name = anAttributes.getValue("name");
            final String className = anAttributes.getValue("classname");

            return new ConfigRule(name, className);
        }
    }

    /**
     * A rule for extracting the pattern matching strings from the rules XML.
     * In the digester-rules document type, a pattern can either be declared
     * in the 'value' attribute of a <pattern> element (in which case the
     * pattern applies to all rules elements contained within the <pattern>
     * element), or it can be declared in the optional 'pattern' attribute of
     * a rule element.
     */
    private class PatternRule extends Rule
    {

        private String mAttrName;
        private String mPattern = null;

        /**
         * Creates a pattern rule.
         * @param attrName The name of the attribute containing the pattern
         */
        public PatternRule(String aAttrName)
        {
            mAttrName = aAttrName;
        }

        /**
         * If a pattern is defined for the attribute, push it onto the
         * pattern stack.
         * @param aAttrs the attributes to search.
         * 
         */
        public void begin(String namespace, String name, Attributes aAttrs)
        {
            mPattern = aAttrs.getValue(mAttrName);
            if (mPattern != null) {
                patternStack.push(mPattern);
            }
        }

        /**
         * If there was a pattern for this element, pop it off the pattern
         * stack.
         */
        public void end(String namespace, String name)
        {
            if (mPattern != null) {
                patternStack.pop();
            }
        }
    }
    
    protected class ConfigRule extends Rule
    {
        private String mName;
        /** name of the class for the configuration */
        private String mClassName;
        private boolean isNameMatch;

        /**
         * Construct an configuration create rule with the specified class
         * name.
         *
         * @param aClassName Java class name of the object to be created
         */
        public ConfigRule(String aName, String aClassName)
        {
            mName = aName;
            mClassName = aClassName;
        }

        /**
         * Process the beginning of this element.
         * @param aAtts The attribute list of this element
         */
        public void begin(String aNamespace, String aName, Attributes aAtts)
        {
            // TODO: add logging
//            if (digester.log.isDebugEnabled()) {
//                digester.log.debug(
//                    "[ObjectCreateRule]{" + digester.match + "}New "
//                    + realClassName);
//            }

            // Instantiate the new object and push it on the context stack
            final String name = aAtts.getValue("name");
            if ((name != null) && (mName.equals(name))) {
                isNameMatch = true;
                final DefaultConfiguration config =
                    new DefaultConfiguration(mName);
                config.addAttribute("classname", mClassName);
                final DefaultConfiguration parent =
                    (DefaultConfiguration) digester.peek();
                parent.addChild(config);
                digester.push(config);
            }
            else {
                isNameMatch = false;
            }
        }
        
        /**
         * Process the end of this element.
         */
        public void end(String namespace, String name)
        {
            if (isNameMatch) {
                Object top = digester.pop();
            }
            // TODO: add logging
//            if (digester.log.isDebugEnabled()) {
//                digester.log.debug("[ObjectCreateRule]{" + digester.match +
//                        "} Pop " + top.getClass().getName());
//            }   
        }
    
    
        /**
         * Render a printable version of this Rule.
         * @return a String representation of this Rule.
         */
        public String toString()
        {   
            StringBuffer sb = new StringBuffer("ConfigCreateRule0[");
            sb.append("className=");
            sb.append(mClassName);
             sb.append("]");
            return (sb.toString());    
        }
    }
    
    protected class ConfigCreateRule extends Rule
    {
        /** name of the class for the configuration */
        private String mClassName;

        /**
         * Construct an configuration create rule with the specified class
         * name.
         *
         * @param aClassName Java class name of the object to be created
         */
        public ConfigCreateRule(String aClassName)
        {
            mClassName = aClassName;
        }

        /**
         * Process the beginning of this element.
         * @param aAtts The attribute list of this element
         */
        public void begin(String aNamespace, String aName, Attributes aAtts)
        {
            // TODO: add logging
//            if (digester.log.isDebugEnabled()) {
//                digester.log.debug(
//                    "[ObjectCreateRule]{" + digester.match + "}New "
//                    + realClassName);
//            }

            // Instantiate the new object and push it on the context stack
            final DefaultConfiguration config =
                new DefaultConfiguration(mClassName);
            config.addAttribute("classname", mClassName);
            final int attCount = aAtts.getLength();
            for (int i = 0; i < attCount; i++) {
                final String name = aAtts.getQName(i);
                final String value = aAtts.getValue(i);
                config.addAttribute(name, value);
            }
            final DefaultConfiguration parent =
                (DefaultConfiguration) digester.peek();
            parent.addChild(config);
            digester.push(config);
        }
        
        /**
         * Process the end of this element.
         */
        public void end(String namespace, String name)
        {
    
            Object top = digester.pop();
            // TODO: add logging
//            if (digester.log.isDebugEnabled()) {
//                digester.log.debug("[ObjectCreateRule]{" + digester.match +
//                        "} Pop " + top.getClass().getName());
//            }   
        }
    
    
        /**
         * Render a printable version of this Rule.
         * @return a String representation of this Rule.
         */
        public String toString()
        {   
            StringBuffer sb = new StringBuffer("ConfigCreateRule[");
            sb.append("className=");
            sb.append(mClassName);
             sb.append("]");
            return (sb.toString());    
        }

    }
}
