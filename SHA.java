import java.util.*;
import java.math.BigInteger; 
//https://docs.oracle.com/javase/7/docs/api/java/math/class-use/BigInteger.html 
class SHA{
	static String h0="01100111010001010010001100000001";
	static String h1="11101111110011011010101110001001";
	static String h2="10011000101110101101110011111110";
	static String h3="00010000001100100101010001110110";
	static String h4="11000011110100101110000111110000";
	static String a=h0,b=h1,c=h2,d=h3,e=h4;
	//static 
	public static void main(String[] args){
		System.out.println("enter the message:");
		Scanner s1=new Scanner(System.in);
		String msg=s1.nextLine();
		//String str="abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
		String bit_stream="";
		int i,tmp=0;
		for (i=0; i<msg.length(); i++) {
			if(msg.charAt(i)==' ')
				msg=msg.replace(" ","");
			else if(msg.charAt(i)>=65 && msg.charAt(i)<=90){
				tmp=msg.charAt(i)+32;	
				bit_stream=bit_stream+"0"+Integer.toBinaryString((int)(msg.charAt(tmp)));
			}
			else
				bit_stream=bit_stream+"0"+Integer.toBinaryString((int)(msg.charAt(i)));	
		}
		if(bit_stream.length()<448){
			bit_stream=bit_stream+"1";
			while(bit_stream.length()!=448){
				bit_stream=bit_stream+"0";
			}
		}
		String padding=Integer.toBinaryString(msg.length()*8);
		while (padding.length()!=64) {
			padding="0"+padding;
		}
		bit_stream=bit_stream+padding;
		//System.out.println(bit_stream+"\n"+bit_stream.length()+"\n"+padding.length()+"\n");
		String word[]=word_generation(bit_stream);
		String word_1[]=new String[80];
		for (i=0; i<80; i++) {
			if(i<16)
				word_1[i]=word[i];
			else
				word_1[i]="";
		}
		
		for (i=0; i<80; i++) {
			//round(word[i%16],i);
			if(i<16)
				round(word_1[i],i);
			else{
				word_1[i]=left_Shift(toExor(toExor(toExor(word_1[(i-3)],word_1[(i-8)]),word_1[(i-14)]),word_1[(i-16)]),1);
				round(word_1[i],i);
			}
		}
		a=toAdd(a,h0)+toAdd(b,h1)+toAdd(c,h2)+toAdd(d,h3)+toAdd(e,h4);
		String hex="",hex_f="";
		int h_cnt=0;
		System.out.println(Integer.parseInt("1010",2));
		/*for (i=0; i<160; i++) {
			if(i%8!=0){
				hex=hex+a.charAt(i);
				//System.out.println(hex);
			}
			else{
				System.out.println(hex);
				h_cnt=Integer.parseInt(hex,2);
				hex_f=hex_f+Integer.toString(h_cnt,16);
				hex="";
				hex=hex+a.charAt(i);
			}
		}*/
		String hexString = new BigInteger(a, 2).toString(16);
		System.out.println("hex: "+hexString);
		//String output=round(h0,h1,h2,h3,h4,"01100001011000100110001110000000",0);
		//System.out.println("he"+output+"\n");
	}
	static String[] word_generation(String str1){
		String word[]=new String[16],tmp="";
		int j=0;
		for (int i=0; i<512; i++) {
			tmp=tmp+str1.charAt(i);
			if((i+1)%32==0){
				System.out.println(j);
				if(j<16)
					word[j]=tmp;
				tmp="";
				j++;

			}
		}
		return word;
	}
	static void round(String w,int r_no){
		String bin="",e_cp="",k="";
		e_cp=e;
		if(r_no>=0 && r_no<=19){
			bin=toOr(toAnd(b,c),toAnd(toNot(b),d));
			k="01011010100000100111100110011001";
		}
		else if (r_no>=20 && r_no<=39){
			bin=toExor(toExor(b,c),d);
			k="01101110110110011110101110100001";
		}
		else if (r_no>=40 && r_no<=59){
			bin=toOr(toOr(toAnd(b,c),toAnd(b,d)),toAnd(c,d));
			k="10001111000110111011110011011100";
		}
		else{
			bin=toExor(toExor(b,c),d);
			k="11001010011000101100000111010110";
		}
		e_cp=toAdd(e_cp,bin);
		String tmp=left_Shift(a,5);
		e_cp=toAdd(tmp,e_cp);
		e_cp=toAdd(e_cp,w);
		e_cp=toAdd(e_cp,k);
		e=d;
		d=c;
		c=left_Shift(b,30);
		b=a;
		a=e_cp;
		System.out.println("Round"+r_no+": "+a+"\n"+b+"\n"+c+"\n"+d+"\n"+e);
	}
	static String left_Shift(String str1,int shift){
		char tmp[]=str1.toCharArray(),ch='a';
		String str2="";
		for (int i=0; i<shift; i++) {
			str2="";
			for (int j=0; j<a.length(); j++) {
				if(j==0){
					ch=tmp[j];
					tmp[j]=tmp[j+1];
				}
				else if(j==a.length()-1)
					tmp[j]=ch;
				else 
					tmp[j]=tmp[j+1];
				str2=str2+tmp[j];
			}
		}
		return str2;
	}
	static String toAdd(String str1,String str2){
		char c1[]=str1.toCharArray(),c2[]=str2.toCharArray(),ch='0';
		String demo="";
		for (int i=a.length()-1; i>=0; i--) {
			if(c1[i]=='0' && c2[i]=='0'){
				if(ch=='0'){
					c1[i]='0';
					ch='0';
				}
				else{
					c1[i]='1';
					ch='0';
				}
			}
			else if (c1[i]=='0' && c2[i]=='1') {
				if(ch=='0'){
					c1[i]='1';
					ch='0';
				}
				else{
					c1[i]='0';
					ch='1';
				}
			}
			else if (c1[i]=='1' && c2[i]=='0') {
				if(ch=='0'){
					c1[i]='1';
					ch='0';
				}
				else{
					c1[i]='0';
					ch='1';
				}
			}
			else if (c1[i]=='1' && c2[i]=='1') {
				if(ch=='0'){
					c1[i]='0';
					ch='1';
				}
				else{
					c1[i]='1';
					ch='1';
				}
			}
			demo=c1[i]+demo;
		}
		//System.out.println(demo);
		return demo;
	}
	public static String toExor(String str1, String str2){
		int i=0;
		String bin="";
		for(i=0;i<32;i++){
			if(str1.charAt(i)==str2.charAt(i))
				bin=bin+"0";
			else
				bin=bin+"1";
		}
		return bin;
	}
	public static String toAnd(String str1, String str2){
		int i=0;
		String bin="";
		for(i=0;i<32;i++){
			if(str1.charAt(i)=='1' && str2.charAt(i)=='1')
				bin=bin+"1";
			else
				bin=bin+"0";
		}
		return bin;
	}
	public static String toOr(String str1, String str2){
		int i=0;
		String bin="";
		for(i=0;i<32;i++){
			if(str1.charAt(i)=='1' || str2.charAt(i)=='1')
				bin=bin+"1";
			else
				bin=bin+"0";
		}
		return bin;
	}
	public static String toNot(String str1){
		int i=0;
		String bin="";
		for(i=0;i<32;i++){
			if(str1.charAt(i)=='1')
				bin=bin+"0";
			else
				bin=bin+"1";
		}
		return bin;
	}
}