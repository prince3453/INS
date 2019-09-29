import java.util.*;
import java.math.*;

public class Diffie_h {

	public static void main(String[] args) {
		
		String str="";
		
		System.out.print("Enter the value of q : ");
		Scanner s1=new Scanner(System.in);
		str=s1.next();
		s1.nextLine();
		BigInteger q=new BigInteger(str);
		
		System.out.print("Enter the value of Alpha : ");
		str=s1.next();
		s1.nextLine();
		BigInteger al=new BigInteger(str);
		
		System.out.print("Enter the value of Xa : ");
		str=s1.next();
		s1.nextLine();
		BigInteger Xa=new BigInteger(str);
		
		System.out.print("Enter the value of Xb : ");
		str=s1.next();
		s1.nextLine();
		BigInteger Xb=new BigInteger(str);
		
		System.out.println("");
		
		BigInteger Ya=al.modPow(Xa,q );
		BigInteger Yb=al.modPow(Xb,q );
		
		BigInteger K1=Ya.modPow(Xb, q);
		BigInteger K2=Yb.modPow(Xa, q);
		
		if(K1.equals(K2))
		{
			System.out.println("Ya : "+Ya);
			System.out.println("Yb : "+Yb);
			System.out.println("K : "+K1);
		}
		else
		{
			System.out.println("Keys are not verified!");
		}
	}

}
