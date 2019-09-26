//Syed hadi zia rizvi 260775855

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import acm.gui.TableLayout;
import acm.program.Program;

@SuppressWarnings("serial")
public class JCalcGUI extends Program implements ChangeListener, ActionListener{

	String prefix = "";
	JTextField input = new JTextField(""); // input text field is empty
	JTextField output = new JTextField(""); // output text field is empty
	JTextField prec_tf = new JTextField(""); // precision text field is empty for now
	JSlider prec_slider;
	
	public void init()
	{
		
		setSize(500, 600);
		setLayout(new TableLayout(8, 4, 10, 10));
		
		output.setEditable(false);
		output.setBackground(Color.WHITE);
		
		// To add we need the object and we need to give some constraint -> String
		add(input,"gridwidth = 4 height = 25");
		add(output,"gridwidth = 4 height = 25");
		
		String BUTTON_SIZE = "80";
		String button_label[]= {"C","(",")","/","7","8","9","*","4","5","6","-","1","2","3","+","0","."};
		
		String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE; //the constraints
		
		for (int i = 0; i < button_label.length; i++) {
			//adding JButtons in the grid
			JButton cur_button = new JButton(button_label[i]);
			cur_button.setFont(new Font("Arial", Font.PLAIN, 20));
			cur_button.setBackground(Color.WHITE);
			cur_button.setOpaque(true);
			cur_button.setFocusPainted(false);
			cur_button.setBorderPainted(true);
			cur_button.setContentAreaFilled(true);
			cur_button.addActionListener(this);
			add(cur_button, constraint);
		}
		//adding the = button
		JButton cur_button = new JButton("=");
		cur_button.setFont(new Font("Arial", Font.PLAIN, 20));
		cur_button.setBackground(Color.WHITE);
		cur_button.setOpaque(true);
		cur_button.setFocusPainted(false);
		cur_button.setBorderPainted(true);
		cur_button.setContentAreaFilled(true);
		cur_button.addActionListener(this); //to take inputs from the button
		add(cur_button, "gridwidth=2");
		
		
		//Label for slider
		add(new JLabel("Precision"));
		
		//setting the Slider
		int default_val = 6; //so the default value is 6
		String default_val_str = default_val + "";
		prec_slider = new JSlider(1,10,default_val);
		prec_slider.addChangeListener(this); //to change the slider values and then give the corresponding input
		prec_slider.setPaintTicks(true);
		prec_slider.setMajorTickSpacing(2);
		prec_slider.setMinorTickSpacing(1);
		prec_slider.updateUI();  
		add(prec_slider,"gridwidth = 2");
		
		//Precision text field
		prec_tf.setText(default_val_str);
		prec_tf.setEditable(false);
		prec_tf.setBackground(Color.WHITE);
		add(prec_tf);	
	    }
	
	
//when the slider changes, we change the slider text field
	@Override
	public void stateChanged(ChangeEvent arg0) {
		int prec_slider_value = prec_slider.getValue();
		prec_tf.setText(prec_slider_value+"");
		
	}
//when button pressed	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cur_command = e.getActionCommand();
		//To clear the entered input
		if(cur_command.equals("C"))
		{
			prefix = "";
			input.setText("");
			output.setText("");
			
		}
		//to calculate the answer
		else if(e.getActionCommand()=="=")
		{
			//you have to compute the post-fix from from infix then evaluate post-fix and update output with result.
			 String answer= Calculation(prefix);
			 double value5 = Double.parseDouble(answer); 
			 String finalanswer= roundAvoid(value5,prec_slider.getValue());
			 output.setText(finalanswer);
		}
		//keep adding to the input textfield till = is pressed
		else
		{
			prefix += e.getActionCommand();
			input.setText(prefix);
		}
	
	}
	
	/**
	 * This is a method that checks the precision selected on the 
	 * slider and then rounds off the answer to the selected
	 * precision
	 * @param value this is the calculated answer
	 * @param places this is the selected precision
	 * @return answer3 the rounded off answer
	 */
	
	public  String roundAvoid(double value, int places) {
	    double scale = Math.pow(10, places); // Calculating the scale
	    double answer2= Math.round(value * scale) / scale; //using the math.round method to round off
	    String answer3= Double.toString(answer2); //converting the answer into string
		int index= answer3.indexOf('.');
		    //if the length of the answer is less than the required precision
			while(answer3.length()-index< prec_slider.getValue()+1)  
			{
				answer3= answer3 + "0";
			}
			return answer3; //return the rounded off answer
	}
	  //method to calculate the answer by taking the entered string
	  //as the argument for this method
	  public String Calculation(String text) {
		  Queue inQueue = new Queue(); 
		 
	  //here we divide the string into tokens
		   StringTokenizer parsedString = new StringTokenizer(text,"+-*/()",true); 
		  
	  while (parsedString.hasMoreTokens()) { 
		  inQueue.Enqueue(parsedString.nextToken());}
		  
		  
	  //here we are creating the output queue
	  Queue OutQue= new Queue();
	  Stack Stack = new Stack();
	  
	  //Taking the numbers from the input queue and converting them into post-fix form according to priority
	         while(!inQueue.isEmpty()) {
	         String input = inQueue.Dequeue().trim();
	      
	  //if token is a number, put it into the output queue
	     
	         if(isDigit(input)) {
	            OutQue.Enqueue(input);}
	         
	  //if token is an operator put it into stack according to priority
	     
	         if(!isDigit(input) && isBracket (input)==0) {
		    	 //while stack operator has > priority, {pop stack}
		    	 while( !Stack.isEmpty()  && priority(Stack.top.KeyVal) >= priority(input)) {
		    		   OutQue.Enqueue(Stack.pop());}
		    	       Stack.push(input);}
	         
	  //if token is a bracket, put it into stack according to priority then pop it
	          
	         if(isBracket(input)==1) {
	    	       Stack.push(input);}
	         if(isBracket(input)==2) {
                   while(isBracket(Stack.top.KeyVal)!=1){
                	   OutQue.Enqueue(Stack.pop());}
	    	       Stack.pop();}
        
	     }
	  //putting the post-fix from into the output queue   
	  while (!Stack.isEmpty()) {
		 OutQue.Enqueue(Stack.pop());}
	     
	  
	    
	  //making a stack to store 
	  Stack CalcStack = new Stack();
	 
	 
	  String op1; 
	  String op2;
	  Double Val;
	  while(!OutQue.isEmpty()) {
		     String KeyValue = OutQue.Dequeue();
		     char kv = KeyValue.charAt(0);
		 
             if(isDigit(KeyValue)) {
        	    //if the string in the output queue is a digit we push it into the stack
        	    CalcStack.push(KeyValue);}
         
             if(!isDigit(KeyValue)) {
        	    //We pop last two numbers in the stacks to perform an operation on them
        	     op1 = CalcStack.pop();
        	     op2 = CalcStack.pop();
        	
        	 //Converting the Numbers into double-form
        	 double value1 = Double.parseDouble(op2); 
        	 double value2 = Double.parseDouble(op1);
        	
        	 switch (kv) {
        	
        	 case '+':
        	    Val= value1 + value2;  //Adding the two popped numbers
                CalcStack.push(Double.toString(Val));  //pushing the result back into the stack
        	 break;
        	
        	 case '-':
        		Val= value1 - value2; //Subtracting the two popped numbers
            	CalcStack.push(Double.toString(Val)); //pushing the result back into the stack
             break;
            
             case '/':
            	Val= value1 / value2; //Subtracting the two popped numbers
            	CalcStack.push(Double.toString(Val)); //pushing the result back into the stack
             break;
            
             case '*' :
        	    Val= value1 * value2; //dividing the two popped numbers
         	    CalcStack.push(Double.toString(Val)); //pushing the result back into the stack
         	 break;
          }
        }
      }
    //returning the calculated answer 
	return(CalcStack.pop());
	
         
}
    
	
	 
	 
	 

	  	  
	/**
	  * * this method is used to give precedence to the 2 types
	  * of brackets
	  * @param input this is the token 
	  * @return 1 if there is an opening bracket,
	  * 2 if there is a closing bracket,
	  * 0 if the token is not a bracket
	  */
	 
	public int isBracket(String input){
	     if(input.equals("(")) {
	        return 1;}
	     if(input.equals(")")) {
	        return 2;}
	     else 
	        return 0;}
	
	/**this method checks if there is no other argument other than
	  * a digit, and returns the digit
	  * @param input this is the token
	  * @return the token in the queue to the output queue  
	  */
	 
   public boolean isDigit(String input) {
		 StringTokenizer st = new StringTokenizer(input, "+-*/()", false);
		 return st.hasMoreTokens();}
	 
	/**
	  * @param input this is the token
	  * @return 2 if the operands are multiply or divide
	  * as they have a greater priority,
	  * 1 if the operands are add or subtract
	  * as they have a lesser priority,
	  * 0 if the token isn't an operand
	  */
	 
   public int priority(String input){
		 if (input.equals("/")  || input.equals("*")) {
			return 2;}
		 if (input.equals("-") || input.equals("+")) {
		    return 1;}
		 else 
			return 0;}
	 
	  
	  
}

