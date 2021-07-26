/*
CustomImportOrder
customImportOrderRules = STATIC###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/


import static com.puppycrawl.tools.checkstyle.utils.AnnotationUtil.containsAnnotation;
import static com.puppycrawl.tools.checkstyle.utils.AnnotationUtil.getAnnotation;


import com.sun.accessibility.internal.resources.*; // violation



import java.util.Arrays; // violation
import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.accessibility.Accessible;


import org.apache.commons.beanutils.converters.ArrayConverter; // violation

class InputCustomImportOrderNoPackage2 {

}
