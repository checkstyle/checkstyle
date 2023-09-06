/*
AbbreviationAsWordInName
allowedAbbreviationLength = 2
allowedAbbreviations = (null)
ignoreFinal = false
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public abstract class InputAbbreviationAsWordInNameAbstractMultisetSetCount<E> { // ok

  private static final String SUPPORTS_REMOVE = "";

@CollectionFeature.Require(absent = SUPPORTS_REMOVE)
  public void testSetCount_negative_removeUnsupported() {
  }
}

@interface CollectionFeature {

    public @interface Require
    {
        String absent();
    }
}
