package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

/** somejavadoc. */
public class InputTextBlockColumnLimit {
  static final String SAMPLE = """
   locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude
   ST001,Station 001,ZONE1,Zone 1,CP1,Competitor 1,123 Street,Unit 2,Houston,TX,77033,US,29.761496813335178,-95.53049214204984
   ST002,Station 002,ZONE2,,CP2,,668 Street,Unit 23,San Jose,CA,95191,US,37.35102477242508,-121.9209934020318
      """;

  /** somejavadoc. */
  public static void main(String[] args) {
    String textBlock = """
      This STRing will exceed the column limit of 100 but checktyle will not show many error because it is allowed
      Random String
      google config allows text block to exceed limit of 100 line length. it improves readability and stuff.
      same is true for this line also. same is true for this line as well. same is true for this line too.
      one more Random String.
        """;

    String textblock2 =
            """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
            """;

    String textblock3 = """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
            """;

    String textblock4 =
            """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
            """ + getSampleTest();

    String normalString = "a very long string is heavy to parse and takes so much processing power. it is not ideal to make long strings";
    // violation above 'Line is longer than 100 characters (found 138).'
  }

  public static String getSampleTest() {
    return "sampleTest";
  }
}
