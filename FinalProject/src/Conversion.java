import java.math.BigInteger;


public class Conversion {
	
	//gets string into a bit sequence
	static byte[] stringToBitSeq(String s){
	byte[] stringArray = new byte[s.length() *6];
	String characters = "_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i = 0; i <s.length();i++){
			int convertString = (byte)characters.indexOf(s.charAt(i));
			for(int j = 5; j >= 0; j--){
				stringArray[i*6 +j]= (byte) (convertString & 1);
				convertString = convertString >> 1;
			}
		}

		return stringArray;
	}
	
	//changes bits to digit sequence
	static byte[] BitseqToDigitseq(byte[] in, int k){
	byte[] array = new byte[(int)Math.ceil((double)in.length/k)];
	for(int index = array.length-1; index >= 0; index--){
		for(int index2 = 0; index2 < k ; index2++){
			int Aindex = in.length - (array.length -1 - index)*k - index2 -1;
			array[index] += (Aindex >= 0 ? in[Aindex] : 0) * Math.pow(2, index2);
		}
	}
	return array;

	}

	//gets bit sequence and converts it to a big number
	BigInteger BitseqToBigNum(byte[] array){
		return new BigInteger (array); 
	}
	
}
