
public class SItem {
	
	String userID;
	SItem next;
	int customerID;

	//constructor
	public SItem(String newUserID, int counter, SItem nextNode) {
		this.userID = newUserID; 
		this.next = nextNode;
		this.customerID = counter;
	}
}

	
	