package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for Java 7 numerical literals.
 */
public class InputJava7NumericalLiterals
{
    int i1 = 0b00011110;
    int i2 = 0B00011110;
    int i3 = 0xA;
    int i4 = 0x1___A_F;
    int i5 = 0b1;
    int i6 = 0b1___1_0;
    int i7 = 0;
    int i8 = 02;
    int i9 = 0_123;
    int i10 = 1;
    int i11 = 1___3;
    int i12 = 1_43_43598_7;
    
    long l1 = 0b00011110L;
    long l2 = 0B00011110l;
    long l3 = 0xAL;
    long l4 = 0x1___A_FL;
    long l5 = 0b1L;
    long l6 = 0b1___1_0L;
    long l7 = 0l;
    long l8 = 02L;
    long l9 = 0_123l;
    long l10 = 1l;
    long l11 = 1___3l;
    long l12 = 1_43_43598_7L;
    long l13 = 1_43_43598_7; // int promoted to long
    
    // the grammar considers floating point values to be of type "float" by default which is wrong, it should be "double".
    
    float f1 = .1f;
    float f2 = 1.; // double "downgraded" to float
    float f3 = 0f;
    float f4 = 1e0; // double "downgraded" to float
    float f5 = 1e0f;
    float f6 = 12.345F;
    float f7 = .5____2_1; // double "downgraded" to float
    float f8 = 1__42__3.; // double "downgraded" to float
    float f9 = 0__2_4__324f;
    float f10 = 1_34e0; // double "downgraded" to float
    float f11 = 1__1_2e0f;
    float f12 = 2_1___2.3__4_5F;
    float f13 = 1_34e0__4__3; // double "downgraded" to float
    float f14 = 1__1_2e00__000_4f;
    float f15 = 2_1___2.3__4_5e00______0_5F;
    
    double d1 = .1d;
    double d2 = 1.D;
    double d3 = 0d;
    double d4 = 1e0D;
    double d5 = 1e0d;
    double d6 = 12.345D;
    double d7 = .5____2_1d;
    double d8 = 1__42__3.D;
    double d9 = 0__2_4__324d;
    double d10 = 1_34e0d;
    double d11 = 1__1_2e0d;
    double d12 = 2_1___2.3__4_5D;
    double d13 = 1_34e0__4__3d;
    double d14 = 1__1_2e00__000_4d;
    double d15 = 2_1___2.3__4_5e00______0_5D;
    double d16 = 0.12___34; // "float" promoted to double
    
    float hf1 = 0x.1___AFp1; // double "downgraded" to float
    float hf2 = 0x.1___AFp0__0__0f;
    float hf3 = 0x2__3_34.4___AFP00_00f;
    
    double hd1 = 0x.1___AFp1;
    double hd2 = 0x.1___AFp0__0__0d;
    double hd3 = 0x2__3_34.4___AFP00_00d;
    
    int doc1 = 1234_5678;
    long doc2 = 1_2_3_4__5_6_7_8L;
    int doc3 = 0b0001_0010_0100_1000;
    double doc4 = 3.141_592_653_589_793d;
    double doc5 = 0x1.ffff_ffff_ffff_fP1_023; // Double.MAX_VALUE

}
