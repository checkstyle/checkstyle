/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test fields with different modifier combinations. */
public class InputGoogleNonConstantFieldNameMixedModifiers {

    static final int STATIC_FINAL = 1;
    public static final int PUBLIC_STATIC_FINAL = 2;
    private static final int private_static_final = 3;
    protected static final int Protected_Static_Final = 4;

    static int staticField;
    public static int Public_Static;
    private static int private_static;
    protected static int protected_Static;

    final int validFinal = 1;
    public final int publicFinal = 2;
    private final int privateFinal = 3;
    protected final int protectedFinal = 4;

    final int Final_Instance = 1;
    // violation above, ''Final_Instance' .* underscores allowed only between adjacent digits.'

    public final int Public_Final = 2;
    // violation above, ''Public_Final' .* underscores allowed only between adjacent digits.'

    private final int mPrivateFinal = 3;
    // violation above, ''mPrivateFinal' .* avoid single lowercase letter followed by uppercase'

    protected final int f = 4;
    // violation above 'Non-constant field name 'f' must start lowercase, be at least 2 chars'

    int validInstance;
    public int publicInstance;
    private int privateInstance;
    protected int protectedInstance;

    int Instance_Bad;
    // violation above, ''Instance_Bad' .* underscores allowed only between adjacent digits.'

    public int Public_Bad;
    // violation above, ''Public_Bad' .* underscores allowed only between adjacent digits.'

    private int mField;
    // violation above, ''mField' .* avoid single lowercase letter followed by uppercase'

    protected int a;
    // violation above 'Non-constant field name 'a' must start lowercase, be at least 2 chars'
}
