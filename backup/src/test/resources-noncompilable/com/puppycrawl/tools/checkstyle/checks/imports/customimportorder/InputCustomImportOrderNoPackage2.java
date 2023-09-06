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


import com.sun.accessibility.internal.resources.*; // violation '.* should be separated from previous import group by one line'



import java.util.Arrays; // violation 'Extra separation in import group before .*'
import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.accessibility.Accessible;


import org.apache.commons.beanutils.converters.ArrayConverter; // violation 'Extra separation in import group before .*'

class InputCustomImportOrderNoPackage2 {

}
