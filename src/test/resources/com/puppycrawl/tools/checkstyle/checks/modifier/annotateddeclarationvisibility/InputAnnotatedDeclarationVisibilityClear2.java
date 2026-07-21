/*
AnnotatedDeclarationVisibility
annotations = org.jspecify.annotations.Nullable, org.junit.jupiter.api.Disabled, \
              com.puppycrawl.tools.checkstyle.checks.modifier.\
              annotateddeclarationvisibility.Annotation
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

@interface Disabled {}

@interface Nullable {}

@interface Annotation {}

public class InputAnnotatedDeclarationVisibilityClear2 {

    @Disabled
    @Annotation
    private void allowedMethod() {}

    @Nullable
    public int allowedField;
}
