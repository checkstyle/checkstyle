package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

/** Somejavadoc. */
public class InputTextBlockColumnLimit {
  static final String SAMPLE = """
   locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude
   ST001,Station 001,ZONE1,Zone 1,CP1,Competitor 1,123 Street,Unit 2,Houston,TX,77033,US,29.761496813335178,-95.53049214204984
   ST002,Station 002,ZONE2,,CP2,,668 Street,Unit 23,San Jose,CA,95191,US,37.35102477242508,-121.9209934020318
      """;

  /** Somejavadoc. */
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
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.
        """;

    String textblock3 = """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.
        """;

    String textblock4 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.
        """ + getSampleTest();

    String textblock5 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.
        """ + "end.";

    String textblock6 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.
        """
            + getSampleTest();

    String textblock7 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC""";

    String normalString = "a very long string is heavy to parse and takes so much processing power. it is not ideal to make long strings";
    // false negative above, ok until #17707
  }

  public static String getSampleTest() {
    return "sampleTest";
  }
}
