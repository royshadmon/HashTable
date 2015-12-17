
public class SLItemList extends UIMS {
	SItem head;
	
	//constructor
	public SLItemList(){
		head = null;
	}
	
	// adds new entry to front of List
	public void pushFront (String userID, int counter){
		SItem newNode= new SItem(userID, counter, head);
		head = newNode;
	}
	
	//returns null if not found 
	//returns MyNode object with userID if found
	SItem find(String uid){
		
		SItem testedNode = head;
		
		while (testedNode != null){
			if (testedNode.userID.equals(uid)){
				//break;
				return testedNode;
			}
			testedNode = testedNode.next;
		}
		return null;
		//return testedNode;
		
	}
	
	//gets the first item in the link list
	public SItem getHead() {
		return head;
	}


}
