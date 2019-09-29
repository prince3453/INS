
class Vernam {

    String plainText, cipherText, key;

    Vernam(String plainText, String key) {
        this.key = key;
        this.plainText = plainText;
        encrypt();
    }

    void encrypt() {
        byte[] pT = plainText.getBytes();
        byte[] ky = key.getBytes();
        byte[] cT = new byte[pT.length];
        int i = 0;
        for (byte x : pT) {
            int index = i % ky.length;
            cT[i] = (byte) (x ^ ky[index]);
            i++;
        }
        cipherText = new String(cT);
    }

    String decrypt(String cipherText) {
        this.cipherText = cipherText;
        byte[] cT = cipherText.getBytes();
        byte[] ky = key.getBytes();
        byte[] pT = new byte[cT.length];
        int i = 0;
        for (byte x : cT) {
            int index = i % ky.length;
            pT[i] = (byte) (x ^ ky[index]);
            i++;
        }
        plainText = new String(pT);
        return (plainText);
    }
}

public class INS_P8 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER PLAIN TEXT AND KEY (LINE SEPERATED):");
        Vernam v = new Vernam(in.nextLine(), in.nextLine());
        System.out.println("ENC:" + v.cipherText);
        System.out.println("DEC:" + v.decrypt(v.cipherText));
    }
}
