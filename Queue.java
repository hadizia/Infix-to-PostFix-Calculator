//Syed Hadi Zia Rizvi 260775855


/**@author hadi
 *this class implements the queue which is a data structure that 
 *holds the data. The data that is input first is output first as well 
 */
public class Queue {
	
	public listNode tail;
	public listNode head;
    
	/**
	 * Enqueue adds the new string to the head of the queue
	 * @param newstring this is the parsed token from the input 
	 */
	public void Enqueue(String newstring)
	{
		listNode newnode = new listNode(newstring);

	//We check if the queue is empty
		 
		if(tail == null)
		{
			tail = newnode;
			head = newnode;
			
		}else
		{
    //If the queue has something in it
	     
			newnode.next = tail;
			tail.prev = newnode;
			tail = newnode;
		}
	}
	/**
	 * @return it removes the last string from the queue and
	 * returns it
	 */
	public String Dequeue()
	{
    // Check if we a have head node in this method
		 
		if(head == null)
		{
			System.out.println("Error: the queue is empty");
			return null;
		}
		
    // Taking the string in this method
		 
		String stringResult = head.KeyVal;
		
		if(head.prev == null)
		{			
			head = null;
			tail = null;
		}else
		{
			listNode pre_head = head.prev;
			pre_head.next = null;
			head = pre_head;
		}
		
		return stringResult;
	}
    /**
     * this method returns a boolean value
     * @return returns true if the queue is empty
     */
  public boolean isEmpty() {
		return (head==null);
	}
}