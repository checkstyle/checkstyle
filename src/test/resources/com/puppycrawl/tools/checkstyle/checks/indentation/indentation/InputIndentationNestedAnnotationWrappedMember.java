package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 */                                                                           //indent:1 exp:1
@TenantOverride(name = "id.tenant",                                           //indent:0 exp:0
    joinColumns = @TenantColumn(name = "tenant_id", nullable = false))        //indent:4 exp:4
@TenantOverrides( {                                                           //indent:0 exp:0
    @TenantOverride(name = "id.tenant",                                       //indent:4 exp:4
        joinColumns = @TenantColumn(name = "tenant_id", nullable = false))})  //indent:8 exp:8
public class InputIndentationNestedAnnotationWrappedMember {                  //indent:0 exp:0
}                                                                             //indent:0 exp:0

@interface TenantOverrides {                                                  //indent:0 exp:0
    TenantOverride[] value();                                                 //indent:4 exp:4
}                                                                             //indent:0 exp:0

@interface TenantOverride {                                                   //indent:0 exp:0
    String name();                                                            //indent:4 exp:4

    TenantColumn joinColumns();                                               //indent:4 exp:4
}                                                                             //indent:0 exp:0

@interface TenantColumn {                                                     //indent:0 exp:0
    String name();                                                            //indent:4 exp:4

    boolean nullable();                                                       //indent:4 exp:4
}                                                                             //indent:0 exp:0
