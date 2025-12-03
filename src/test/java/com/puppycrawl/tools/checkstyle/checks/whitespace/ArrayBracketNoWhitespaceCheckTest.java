package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.Test;

/**
 * Exact tests for {@link ArrayBracketNoWhitespaceCheck} using the two provided inputs.
 * The expected arrays were generated from the `// violation` comments in the input files.
 */
public class ArrayBracketNoWhitespaceCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketnowhitespace";
    }

    /**
     * Test against InputArrayBracketNoWhitespaceDefault.java
     */
    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            // Line 21: int []badArray1;
            "21:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 22: int [][]badArray2;
            "22:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 23: String []badArray3;
            "23:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 24: Object []badArray4;
            "24:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 32: int badArray8 [];

            "32:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 33: int badArray9 [][];
            "33:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "33:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 34: String badArray10 [];
            "34:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 45: int[] arr4 = new int [10];
            "45:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 46: int[][] arr5 = new int [5][5];
            "46:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 47: String[] arr6 = new String [20];
            "47:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 50: int[] arr7 = new int[ 10];
            "50:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 51: int[] arr8 = new int[  10];
            "51:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 54: int[] arr9 = new int[10 ];
            "54:33: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 55: int[] arr10 = new int[10  ];
            "55:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 58: int[] arr11 = new int[ 10 ];
            "58:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "58:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 74: int z = arr [0];
            "74:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 75: int w = arr  [5];
            "75:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 76: arr [0] = 10;
            "76:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 79: int a = arr[ 0];
            "79:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 80: int b = arr[  5];
            "80:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 83: int c = arr[0 ];
            "83:23: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 84: int d = arr[5  ];
            "84:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 87: int e = arr[ 0 ];
            "87:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "87:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            // Line 101: int[] [] badMat1 = new int[5][5];
            "101:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "101:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 102: int[][] [] badMat2 = new int[3][3][3];
            "102:16: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "102:18: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 103: int[] [][] badMat3 = new int[2][2][2];
            "103:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "103:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 111: int val3 = matrix[0] [0];
            "111:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "111:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 112: int val4 = matrix[1] [2];
            "112:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "112:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 132: int len3 = arr[0] .hashCode();
            "132:25: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 133: String str2 = strings[0] .toString();
            "133:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 134: int len4 = strings[0] .length();
            "134:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 140: String result2 = strings[0] .substring(0, 1).toUpperCase();
            "140:35: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 154: int max2 = Math.max(arr[0] , arr[1]);
            "154:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 155: System.out.println(arr[0] , arr[1]);
            "155:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 156: method(arr[0] , arr[1], arr[2]);
            "156:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 157: method(arr[0], arr[1] , arr[2]);
            "157:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 163: int[] arr3 = {arr[0] , arr[1], arr[2]};
            "163:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 179: int y = arr[0] ;
            "179:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 180: arr[1] = 5 ;
            "180:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 181: int z = arr[3]  ;
            "181:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 191: int val = arr[i] ;
            "191:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 207: arr[0] ++;
            "207:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 208: arr[1] --;
            "208:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 209: int z = arr[2] ++;
            "209:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 210: int w = arr[3] --;
            "210:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 217: arr[2] ++;
            "217:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 218: arr[3] ++;
            "218:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 238: int x2 = arr[0]+ arr[1];
            "238:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 239: int y2 = arr[0]- arr[1];
            "239:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 240: int z2 = arr[0]* arr[1];
            "240:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 241: int w2 = arr[0]/ arr[1];
            "241:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 242: int m2 = arr[0]% arr[1];
            "242:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 243: boolean b5 = arr[0]== arr[1];
            "243:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 244: boolean b6 = arr[0]!= arr[1];
            "244:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 245: boolean b7 = arr[0]< arr[1];
            "245:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 246: boolean b8 = arr[0]> arr[1];
            "246:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 256: arr[0]+= 1;
            "256:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 257: arr[1]-= 1;
            "257:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 258: arr[2]*= 2;
            "258:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 259: arr[3]/= 2;
            "259:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 260: arr[4]%= 2;
            "260:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 275: int y = (arr[0] );
            "275:23: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 276: System.out.println(arr[0] );
            "276:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 277: method(arr[0] , arr[1], arr[2]);
            "277:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 284: int[] arr3 = {arr[0] , arr[1]};
            "284:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 291: int val2 = matrix[arr[0] ][arr[1]];
            "291:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 292: int val3 = matrix[arr[0]][arr[1] ];
            "292:40: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 315: int y = matrix[arr[0] ][arr[1]] + matrix[arr[2]][arr[3] ];
            "315:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "315:63: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 323: IntUnaryOperator op2 = i -> arr[i]* 2;
            "323:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 335: int[] arr3 = new int []{1, 2, 3};
            "335:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 336: int[] arr4 = new int[ ]{1, 2, 3};
            "336:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 337: int[] arr5 = new int[] {1, 2, 3};
            "337:58: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 348: int y = numbers [0];
            "348:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 349: numbers [0] = 10;
            "349:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 363: T item2 = items [0];
            "363:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 364: T item3 = items[ 0];
            "364:24: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 375: if (obj instanceof int []) {}
            "375:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 376: if (obj instanceof String []) {}
            "376:35: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };

        verifyWithInlineConfigParser(getPath("InputArrayBracketWhitespaceDefault.java"), expected);
    }

    /**
     * Test against InputArrayBracketNoWhitespaceDeclarations.java
     */
    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            // Line 24: byte []badBytes;
            "24:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 25: short []badShorts;
            "25:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 26: int []badInts;
            "26:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 27: long []badLongs;
            "27:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 28: float []badFloats;
            "28:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 29: double []badDoubles;
            "29:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 30: char []badChars;
            "30:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 31: boolean []badBooleans;
            "31:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 41: int [][]badMatrix2D;
            "41:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 42: int [][][]badMatrix3D;
            "42:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 45: int[] []badMatrix2D_2;
            "45:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "45:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 46: int[][] []badMatrix3D_2;
            "46:15: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "46:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 47: int[] [][] badMatrix3D_3;
            "47:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "47:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 50: int[]arr, goodCase1;
            "50:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 51: int[]arr2 ;
            "51:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 68: int badAfterVar1 [];
            "68:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 69: int badAfterVar2 [][];
            "69:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 72: int[] badMixedStyle1 [];
            "72:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 73: int badMixedStyle2[] [];
            "73:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 84: String []badStrings;
            "84:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 85: Object []badObjects;
            "85:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 86: Integer []badIntegers;
            "86:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 95: java.util.List<String> []badListArray1;
            "95:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 96: java.util.Map<String, Integer> []badMapArray1;
            "96:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 106: int []badMethod1()
            "106:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 107: int [][]badMethod2()
            "107:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "107:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 108: String []badMethod3()
            "108:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 115: int method6() []
            "115:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 116: int method7() [][]
            "116:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "116:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 126: void param4(int[]arr)
            "126:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 129: void badParam1(int []arr)
            "129:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 130: void badParam2(int [][]matrix)
            "130:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "130:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 131: void badParam3(String []strings)
            "131:28: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 136: InputArrayBracketNoWhitespaceDeclarations(int[]arr)
            "136:58: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 140: InputArrayBracketNoWhitespaceDeclarations(byte []arr)
            "140:58: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 141: InputArrayBracketNoWhitespaceDeclarations(short [][]matrix, char []chars)
            "141:58: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:60: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:75: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 154: int[]local6 = new int[10];
            "154:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 157: int []badLocal1 = new int[10];
            "157:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 158: int [][]badLocal2 = new int[5][5];
            "158:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "158:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 159: String []badLocal3 = new String[20];
            "159:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 166: int badLocal4 [] = new int[10];
            "166:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 167: int badLocal5 [][] = new int[5][5];
            "167:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "167:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 187: int[] arr3 = new int []{1, 2, 3};
            "187:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 188: String[] arr4 = new String []{"a", "b"};
            "188:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 191: int[] arr5 = new int[ ]{1, 2, 3};
            "191:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 192: String[] arr6 = new String[  ]{"a", "b"};
            "192:35: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            // Line 195: int[] arr7 = new int[] {1, 2, 3};
            "195:58: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 196: String[] arr8 = new String[]  {"a", "b"};
            "196:64: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            // Line 209: int[]arr5 = (int[]) obj;
            "209:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            // Line 212: int[] arr3 = (int []) obj;
            "212:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 213: int[][] arr4 = (int [][]) obj;
            "213:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "213:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 225: boolean b4 = obj instanceof int [];
            "225:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 226: boolean b5 = obj instanceof String [];
            "226:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 227: boolean b6 = obj instanceof int [][];
            "227:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "227:40: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 239: Class<?> cls4 = int [].class;
            "239:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 240: Class<?> cls5 = String [].class;
            "240:35: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            // Line 241: Class<?> cls6 = int [][].class;
            "241:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "241:34: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        
        verifyWithInlineConfigParser(getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }
}
