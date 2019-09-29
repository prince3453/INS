
class Railfence {

    String plainText, cipherText;
    int key, currentPointer = 0, v = -1;
    StringBuffer temp[];

    Railfence(int key, String plainText) {
        this.plainText = plainText;
        this.key = key;
        init();
    }

    void init() {
        temp = new StringBuffer[key];
        for (int i = 0; i < key; i++) {
            temp[i] = new StringBuffer();
        }
    }

    void encrypt() {
        cipherText = "";
        System.out.println(plainText);
        for (char c : plainText.toCharArray()) {
            temp[currentPointer].append(c);
            if (currentPointer == 0 || currentPointer == key - 1) {
                v *= (-1);
            }
            currentPointer += v;
        }
        for (int i = 0; i < key; i++) {
            cipherText += temp[i].toString();
        }
    }

}

public class INS_P9 {

    public static void main(String[] args) {
        Railfence r = new Railfence(4, "helloworld");
        r.encrypt();
        System.out.println("ENC:" + r.cipherText);
    }
    
}
