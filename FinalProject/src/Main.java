import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends UIMS{

		public static void main(String[] args) {
			//String userID = UIMS.getID();
			//boolean valid = UIMS.isValid(userID);
			//int foo = UIMS.getTotalBytes(userID); 
			
			UIMS myInterface = new UIMS();
			
			Scanner scan = null;
						
			for (int i = 0; i < args.length; i++){
				try{
					scan = new Scanner(new File (args[i]));
					while (scan.hasNextLine()){
						String uid = scan.nextLine();
						if (!myInterface.isAvailable(uid)){
						
							System.err.println(uid + " is not available");
							
						}
						else if (!myInterface.isValid(uid)){
							System.err.println(uid + " is not valid");
						}	
						else
							myInterface.add(uid);
						
					}
				}	
				catch (FileNotFoundException f){
					System.err.println("file not found");
				}
				
			}
		
			scan = new Scanner(System.in);
			while (scan.hasNextLine()){	
				String uid = scan.nextLine();
				if (!myInterface.isAvailable(uid)){
					
					System.err.println(uid + " is not available");
					
				}	
				else if (!myInterface.isValid(uid)){
					System.err.println(uid + " is not valid");
				}	
				else
					myInterface.add(uid);
				
			}		
			
		myInterface.print();
		scan.close();
	}
}

