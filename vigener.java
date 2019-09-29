
import java.util.Scanner;

class Vigenere {

    String plainText, cipherText, key;

    public Vigenere(String plainText, String key) {
        this.key = key;
        this.plainText = plainText;
    }

    public Vigenere(String key) {
        this.key = key;
    }

    void encrypt() {
        cipherText = "";
        char p, c;
        int index;
        int nonalphabetcounter = 0;
        for (int i = 0; i < plainText.length(); i++) {
            p = plainText.charAt(i);
            if (p >= 'a' && p <= 'z') {
                index = (i - nonalphabetcounter) % key.length();
                char t = key.charAt(index);
                c = (char) ((p - 'a' + (Character.toLowerCase(t) - 'a')) % 26 + 'a');
            } else if (p >= 'A' && p <= 'Z') {
                index = (i - nonalphabetcounter) % key.length();
                char t = key.charAt(index);
                c = (char) ((p - 'A' + (Character.toUpperCase(t) - 'A')) % 26 + 'A');
            } else {
                nonalphabetcounter++;
                c = p;
            }
            cipherText += c;
        }
    }

    String decrypt(String cipherText) {
        this.cipherText = cipherText;
        plainText = "";
        char p, c;
        int index, x, nonalphabetcounter = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            c = cipherText.charAt(i);
            if (c >= 'a' && c <= 'z') {
                index = (i - nonalphabetcounter) % key.length();
                char t = key.charAt(index);
                p = (char) ((((x = (c - 'a' - (Character.toLowerCase(t) - 'a')) % 26) < 0) ? x + 26 : x) + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                index = (i - nonalphabetcounter) % key.length();
                char t = key.charAt(index);
                p = (char) ((((x = (c - 'A' - (Character.toUpperCase(t) - 'A')) % 26) < 0) ? x + 26 : x) + 'A');
            } else {
                nonalphabetcounter++;
                p = c;
            }
            plainText += p;
        }
        return plainText;
    }

}

public class INS_P7 {

    /*
        EXAMPLE:
            Enter Plain Text and Key (LINE SEPERATED):
            HELLO WORLD
            KEY
            ENC:RIJVS UYVJN
            DEC:HELLO WORLD
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Plain Text and Key (LINE SEPERATED):");
        Vigenere v = new Vigenere(in.nextLine(), in.nextLine());
        v.encrypt();
        System.out.println("ENC:" + v.cipherText);
        System.out.println("DEC:" + v.decrypt(v.cipherText));
    }
}
