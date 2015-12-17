import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Scanner;

public class UIMS {

	static Scanner scan = new Scanner(System.in);
	
	private SLItemList [] hashArray; 
	private int hashTableSize;
	private int totalCustomers = 0;
	private byte [] salt;
	int log;
	
	//constructor
	public UIMS(){
		hashTableSize = 2;
		hashArray = new SLItemList[hashTableSize];
		log = 1;
		generateSalt();
	}
	
	private void reallocate() {
		//ensures that the table size will be prime after doubling table size
		hashTableSize *= 2; 
		hashTableSize = getNextPrime(hashTableSize); 
		
		SItem node;
		//creates new hashTable object with new size
		SLItemList [] newHashArray = new SLItemList[hashTableSize];
		log++;
		for (int i = 0; i < hashArray.length; ++i){
			for (node = (hashArray[i] != null? hashArray[i].head : null); node != null; node = node.next){
				int key = hashFunction(node.userID);
				
				//if link link at array[i] doesn't exist create one and then add the userID
				if (newHashArray[key] == null){ 
					newHashArray [key] = new SLItemList();
				}
				//adds each item to its new spot
				newHashArray[key].pushFront(node.userID, node.customerID);		
			}
		}
		hashArray = newHashArray; //saves new array to the old one
	}
	
	//finds the next prime number when tablesize is doubled.
	//this will ensure that the table size is always going to be prime.
	private int getNextPrime(int tableSize){
		for (int i = 2; i < tableSize / 2; i++){
			if (tableSize % i == 0){
				tableSize += 1;
				i = 2;
			}
		}
		return tableSize;
	}
	
	//figures out the load factor to see if reallocation is necessary. 
	private boolean load() {
		return (double)totalCustomers / hashTableSize >= .75 ? true : false;
	}
	
	//gets the userID from manual inserts
	public static String getID() {
		String uid = scan.next();
		return uid;
	}
	
	//checks to see if the userID is valid
	public boolean isValid(String userID){
		if (userID.length() > 0 && userID.length() < 17){
			for (int i = 0; i < userID.length(); i++){
				char character = userID.charAt(i);
				if (Character.isLetterOrDigit(character) == false)
					return false;	
			}
			return true;
		}
		else return false;
	}
	
	//checks to see if the userID is available.
	public boolean isAvailable(String userID) {
		
		int key = getHashValue(userID);
		
		try{
			return (hashArray[key].find(userID) == null);
		}
		catch (NullPointerException e){
		return true;
		}
	}
	
	//looks up the customer id
	public int lookupCustomerId(String userID){
		int key = getHashValue(userID);
		try{
			return hashArray[key].find(userID).customerID;
		}
		catch (NullPointerException e){
			return 0;
		}
		
	}
	
	//adds the userID to the hashTable
	public void add(String userID){
		int key = getHashValue(userID);
	
		if(hashArray[key] == null){
			hashArray[key] = new SLItemList();
		}
		hashArray[key].pushFront(userID, ++totalCustomers);
		if (load()){
			reallocate();		
		}	
	}
	
	//returns the total amount of customers in the hashtable
	public int getTotalCustomers(){
		return totalCustomers;
	}

	//generates the random salt value
	private void generateSalt(){

		salt = new byte[96];

		for(int i = 0 ; i <salt.length-1; i++){

		salt[i] = (byte) (Math.random()*2);

		}

		salt[salt.length -1] = 1;
		salt = Conversion.BitseqToDigitseq(salt, 4);

		}
	
	//this is the multiplicative hash function. 
	//this is being used in the program.
	private int hashFunction(String userId){
		
		String pad = ("________________" + userId).substring(userId.length());
		BigInteger p = new BigInteger ("2").pow(96);
		BigInteger a = new BigInteger (salt);
		BigInteger x = new BigInteger (Conversion.stringToBitSeq(pad));
		BigInteger result = a.multiply(x).mod(p);
		BigInteger size = new BigInteger("2").pow(96-log);
		result = result.divide(size);

		return result.intValue();

	}
	
	//prints the hashTable
	public void print() {
		for (int i = 0; i < hashArray.length; i++){
			if(hashArray[i] != null){
				System.out.printf("%3d -", i);
				boolean firstTimeHeadPrinted = true;
				for (SItem temp = hashArray[i].head; temp != null; temp = temp.next){
					if(firstTimeHeadPrinted){
						System.out.print(temp.userID);
						firstTimeHeadPrinted = false;
					}
					else{
						System.out.printf(", %s", temp.userID);
					}
				}
				System.out.println();
			}
			
			
			
		}
	}
	
	//gets userID's total bytes as an integer.
	//this isn't used anymore, but what used earlier in the program
	//process to get the total bit using ascii values of the string.
	public int getTotalBytes(String userID){
		final Charset UTF_8 = Charset.forName("UTF-8");
			
		byte[] bytes = userID.getBytes(UTF_8);
			
		int totalBytes = 0;
		for (int i = 0; i < bytes.length; i++){
				totalBytes += bytes[i];
		}
		return totalBytes;
		}
		
		
		// calculate the hash value for a given userID
		//this isn't used anymore either, but gets the 
		//hashvalue with out using multiplicative hashing
		public int getHashValue(String userID){
			int hashValue;
			
			hashValue = hashFunction(userID) % hashTableSize;
			
			return hashValue;
		}
	
	
	
	
}

