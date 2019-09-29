package ins;

import java.util.Scanner;

/**
 *
 * @author Raj Dhanani
 */
class AES {

    String plainText, cipherText, key;
    String plainBinary, keyBinary;
    String w[][] = new String[4][4];
    String wT[][] = new String[4][4];
    String xa[], ya[], za[];
    String roundkey[][], state[][] = new String[4][4];
    String[][] rCon = {
        {"01", "00", "00", "00"},
        {"02", "00", "00", "00"},
        {"04", "00", "00", "00"},
        {"08", "00", "00", "00"},
        {"10", "00", "00", "00"},
        {"20", "00", "00", "00"},
        {"40", "00", "00", "00"},
        {"80", "00", "00", "00"},
        {"1b", "00", "00", "00"},
        {"36", "00", "00", "00"}};

    AES(String plainText, String key) {
        this.plainText = plainText;
        this.key = key;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                w[i][j] = key.substring(i * 8 + j * 2, i * 8 + j * 2 + 2);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = plainText.substring(i * 8 + j * 2, i * 8 + j * 2 + 2);
            }
        }
//        printMatrix(w);
        generateBinaries();
        encrypt();
    }

    void encrypt() {

        state = transpose(state);
        System.out.println("Plain Text:");
        printMatrix(state);
        roundkey = roundKey(0);
        roundkey = transpose(roundkey);
        System.out.println("--------------------------------------");
        System.out.println("STATE 0:");
        state = addRoundKey(state, roundkey);
        printMatrix(state);
        for (int i = 1; i < 11; i++) {
            System.out.println("--------------------------------------");
            System.out.println("STATE " + i + ":");
            printMatrix(state);
            System.out.println("");
            for (int j = 0; j < 4; j++) { //SUBSTITUTE BYTES
                for (int k = 0; k < 4; k++) {
                    state[j][k] = sBox(state[j][k]);
                }
            }
            printMatrix(state);
            System.out.println("");
            for (int j = 1; j < 4; j++) { // SHIFT ROW
                state[j] = circularByteShift(state[j], j);
            }
            printMatrix(state);
            System.out.println("");
            if (i != 10) {
                state = mixColumn(state);
                printMatrix(state);
            }
            System.out.println("");
            roundkey = roundKey(i);
            roundkey = transpose(roundkey);
            System.out.println("ROUND KEY:");
            printMatrix(roundkey);
            System.out.println("");
            state = addRoundKey(state, roundkey);
            printMatrix(state);
            System.out.println("");
        }
        cipherText = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cipherText += state[i][j];
            }
        }
    }

    String[][] mixColumn(String[][] state) {
        String[][] m = {
            {"02", "03", "01", "01"},
            {"01", "02", "03", "01"},
            {"01", "01", "02", "03"},
            {"03", "01", "01", "02"}
        }, mul = new String[4][4];
//        String t = "00";

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String t = "00";
                for (int k = 0; k < 4; k++) {
//                    mul[i][j] = SUM( m[i][k] * state[k][j] ) ;
                    t = hexToBinary(t.charAt(0)) + hexToBinary(t.charAt(1));
                    String binaryState = hexToBinary(state[k][j].charAt(0)) + hexToBinary(state[k][j].charAt(1));

                    if (m[i][k].equals("01")) {
                        t = XOR(t, binaryState);
                    } else if (m[i][k].equals("02")) {
                        if (binaryState.charAt(0) == '1') {
                            binaryState = binaryState.substring(1) + "0";
                            binaryState = XOR(binaryState, "00011011");
                        } else {
                            binaryState = binaryState.substring(1) + "0";
                        }
                        t = XOR(t, binaryState);
                    } else {
                        String binaryStateOld = hexToBinary(state[k][j].charAt(0)) + hexToBinary(state[k][j].charAt(1));
                        if (binaryState.charAt(0) == '1') {
                            binaryState = binaryState.substring(1) + "0";
                            binaryState = XOR(binaryState, "00011011");
                        } else {
                            binaryState = binaryState.substring(1) + "0";
                        }
                        binaryState = XOR(binaryState, binaryStateOld);
                        t = XOR(t, binaryState);
                    }

                    t = binaryToHex(t);
                }
                mul[i][j] = t;
            }
        }

        return mul;
    }

    String[][] addRoundKey(String[][] state, String[][] roundkey) {
        String data[][] = new String[4][4];
        for (int i = 0; i < 4; i++) {
            data[i] = ArrayXOR(state[i], roundkey[i]);
        }
        return data;
    }

    void printMatrix(String y[][]) {
//        System.out.println("[");
        for (int i = 0; i < y.length; i++) {
//            System.out.print("[");
            for (int j = 0; j < y[i].length; j++) {
                System.out.print(y[i][j]);
                if (j != y[i].length - 1) {
                    System.out.print(",");
                }
            }
//            System.out.println("],");
            System.out.println("");
        }
//        System.out.println("]");
    }

    String[][] roundKey(int round) {
        if (round == 0) {

            xa = circularByteShift(w[3], 1);
            ya = subWord(xa);
            za = ArrayXOR(ya, rCon[0]);

        } else if (round > 0) {

            w[0] = ArrayXOR(w[0], za);
            w[1] = ArrayXOR(w[0], w[1]);
            w[2] = ArrayXOR(w[1], w[2]);
            w[3] = ArrayXOR(w[2], w[3]);
            xa = circularByteShift(w[3], 1);
            ya = subWord(xa);
            if (round != 10) {
                za = ArrayXOR(ya, rCon[round]);
            }

        }
        return w;
    }

    String[] subWord(String[] word) {
        word = word.clone();
        for (int i = 0; i < word.length; i++) {
            word[i] = sBox(word[i]);
        }
        return word;
    }

    String[] ArrayXOR(String x1[], String x2[]) {
        String[] x3 = new String[x1.length];
        for (int i = 0; i < x1.length; i++) {
            x3[i] = binaryToHex(XOR(hexToBinary(x1[i].charAt(0)), hexToBinary(x2[i].charAt(0)))) + binaryToHex(XOR(hexToBinary(x1[i].charAt(1)), hexToBinary(x2[i].charAt(1))));
        }
        return x3;
    }

    String sBox(String data) {

        String s[][]
                = {
                    {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
                    {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
                    {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
                    {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
                    {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
                    {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
                    {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
                    {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
                    {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
                    {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
                    {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
                    {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
                    {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
                    {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
                    {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
                    {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}
                };

        return s[Integer.parseInt(data.substring(0, 1), 16)][Integer.parseInt(data.substring(1, 2), 16)];

    }

    String[][] transpose(String[][] x) {
        String[][] y = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                y[i][j] = x[j][i];
            }
        }
        return y;
    }

    String[] circularByteShift(String data[], int shifts) {
        data = data.clone();
        for (int i = 0; i < shifts; i++) {
            data = lcs(data);
        }
        return data;
    }

    String[] lcs(String data[]) {
        int i;
        String t[] = new String[data.length];
        for (i = 0; i < data.length - 1; i++) {
            t[i] = data[i + 1];
        }
        t[i] = data[0];
        return t;
    }

    String hexToBinary(char c) {
        String t;
        if (c >= 'a' && c <= 'z') {
            t = Integer.toBinaryString(c - 'a' + 10);
        } else if (c >= 'A' && c <= 'Z') {
            t = Integer.toBinaryString(c - 'A' + 10);
        } else if (c >= '0' && c <= '9') {
            t = Integer.toBinaryString(c - '0');
        } else {
            t = null;
        }

        for (int i = t.length(); i < 4; i++) {
            t = "0" + t;
        }
        return t;
    }

    String binaryToHex(String b) {
        String hex = "";
        for (int i = 0; i < b.length(); i += 4) {
            hex += Integer.toHexString(Integer.parseInt(b.substring(i, i + 4), 2));
        }
        return hex;
    }

    void generateBinaries() {
        plainBinary = "";
        keyBinary = "";
        for (char c : plainText.toCharArray()) {
            plainBinary += hexToBinary(c);
        }
        for (char c : key.toCharArray()) {
            keyBinary += hexToBinary(c);
        }
    }

    String XOR(String _1, String _2) {
        int l = _1.length();
        String xor = "";
        for (int i = 0; i < l; i++) {
            char __1, __2;
            __1 = _1.charAt(i);
            __2 = _2.charAt(i);
            if (__1 == __2) {
                xor += "0";
            } else {
                xor += "1";
            }
        }
        return xor;
    }

}

public class INS_P12 {

    public static void main(String[] args) {
        // key = 5468617473206D79204B756E67204675
        // plain text = 54776F204F6E65204E696E652054776F
        // 0f1571c947d9e8590cb7add6af7f6798
        // AES a = new AES("0123456789abcdeffedcba9876543210",
        //                 "0f1571c947d9e8590cb7add6af7f6798");
        // AES a = new AES("3243f6a8885a308d313198a2e0370734","2b7e151628aed2a6abf7158809cf4f3c");
        String plain, key;
        Scanner in = new Scanner(System.in);
        System.out.println("PLAIN TEXT:");
        plain = in.nextLine();
        System.out.println("KEY:");
        key = in.nextLine();
        AES a = new AES(plain, key);
        System.out.println("CIPHER : " + a.cipherText);
    }
}
