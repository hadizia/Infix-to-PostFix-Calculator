//Syed Hadi Zia Rizvi 260775855


/**@author hadi
 * this class implements a stack that is a data structure 
 * used to store the input given. The stack stores the 
 * inputs in a way that the first input is the last 
 * to be output when required.
 * the new input becomes the top of the stack
 * and replaces the previous input
 */


public class Stack {
public listNode top;
	/**
	 * @param newStr is the argument we give to the method
	 * 'push' is used to push a new string on to the top of
	 * the stack. The newString now becomes the top and the 
	 * older one shifts to top.next 
	 */
	public void push(String newStr)
	{
		listNode newnode = new listNode(newStr);
	    //when top has something stored in it
		if(top != null)
		{		
			top.next = newnode;
			newnode.prev = top;}
		top = newnode;
		 
	}
	
	/**
	 * pop method removes the top from the stack and returns it  
	 * @return returns the top of the stack if the stack isnt empty. Else it returns a null value
	 */
	public String pop()
	{
		//when top is empty
		if(top == null)
		{
			System.out.println("Error: empty stack");
			return null;}
		
		String result_string = top.KeyVal;		
		// we have one node in the stack
		if(top.prev == null)
		{
			top = null;}
		else
		{
	    // we have more than one node in the stack
			listNode pre_top = top.prev;
			pre_top.next = null;
			top = pre_top;}
		
		return result_string;
	}			
	/**
	 * 
	 * @return returns true if the stack is empty
	 * returns false if stack is not empty 
	 */
	public boolean isEmpty() {
		return(top==null);}
	
    }