/*
CustomImportOrder
customImportOrderRules = STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE
standardPackageRegExp = java|javax|event.*
thirdPartyPackageRegExp = com
specialImportsRegExp = An|lang|java|collect|event
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import javax.lang.model.element.AnnotationValue;
import java.awt.event.ActionEvent;
import java.lang.*;
import java.awt.color.ColorSpace;

import com.google.common.annotations.Beta; // violation

import com.google.common.collect.HashMultimap;

public class InputCustomImportOrderFindBetterPatternMatch {
}
