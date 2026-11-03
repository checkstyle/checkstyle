/*
GoogleNonConstantFieldName


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

int validFieldName;
public int fooBar;
protected int fooBarBaz;
private int foo123;
final int guava33_4_5 = 0;
int version1_2_3;

static int staticField_1;
static int BadStaticName;

// violation below 'Non-constant field name 'Bad_Field_Name' must start lowercase'
int Bad_Field_Name;

// violation below 'Non-constant field name 'f' must start lowercase, be at least 2 chars'
int f;

// violation below ''foo_bar' .* underscores allowed only between adjacent digits.'
int foo_bar;

class InnerClass {
    int innerValidField;
    // violation below ''inner_bad_field' .* underscores allowed only between adjacent digits.'
    int inner_bad_field;
}

