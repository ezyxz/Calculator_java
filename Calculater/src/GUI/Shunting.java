package GUI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Shunting {
	static String expr="5+2-3*4/2^((1.43+2.57)/3)";
	static List<String> list = new ArrayList<String>();
	 static Stack <String> stackData=new Stack <String>();
	 static Queue<String> queueData = new LinkedList<String>();
	public static void shunting() 
	{
		
         
		 while(!list.isEmpty()) {
		    if(!list.get(0).equals("+") && !list.get(0).equals("-") && !list.get(0).equals("*")
		        &&!list.get(0).equals("/") && !list.get(0).equals("(") 
		        && !list.get(0).equals(")")&& !list.get(0).equals("^")) 
		    {	
			   queueData.add(list.get(0));
		    }
		    else if(list.get(0).equals("("))
		    {
		    	stackData.push(list.get(0));
		    }
		    else if(list.get(0).equals(")")) 
		    {
		    	if(stackData.isEmpty()) 
		    	{
		    		System.out.println("error!");
		    		return;
		    	}
		    		
		    	while(!stackData.peek().equals("(")){
		    		queueData.add(stackData.pop());
		    	}
		    	stackData.pop();
		    	
		    }
		    else if(list.get(0).equals("^")) 
		    {
		    	stackData.push(list.get(0));
		    }
		    else if(list.get(0).equals("+")||list.get(0).equals("-") )//operator
		    {   
		    	if(!stackData.isEmpty()) 
		    	{
		    	    while(!stackData.isEmpty() 
		    	    &&(stackData.peek().equals("+")||stackData.peek().equals("-")
		    	    ||stackData.peek().equals("*")||stackData.peek().equals("/")||stackData.peek().equals("^"))) 
		    	    {
		    		       queueData.add(stackData.pop());
		    		}
		    	  
		    	     
		        }
		    	stackData.push(list.get(0));
		    }
		    else if(list.get(0).equals("*")||list.get(0).equals("/"))
		    {
		    	if(!stackData.isEmpty()) 
		    	{
		    	    while(!stackData.isEmpty() 
		    	    &&(stackData.peek().equals("*")||stackData.peek().equals("/"))) 
		    	    {
		    		       queueData.add(stackData.pop());
		    		}
		    	     
		        }
		    	stackData.push(list.get(0));
		    }
		    
		    list.remove(0);
			 
		 }
		 while(!stackData.isEmpty())
			 queueData.add(stackData.pop());
		 //while(!queueData.isEmpty())
		 	// System.out.println(queueData.poll());
		 
	}
	/////////////////////////////////////////////////
	public static float calculate() {
		 Stack <String> stackData=new Stack <String>();
         while(queueData.peek().charAt(0)!='+' && queueData.peek().charAt(0)!='-'
          && queueData.peek().charAt(0)!='*' && queueData.peek().charAt(0)!='/' && queueData.peek().charAt(0)!='^')
        	 stackData.push(queueData.poll());
        while(!queueData.isEmpty()) 
        {	
        float num1;
        float num2;
        switch(queueData.peek().charAt(0)) 
        {
        case'+':
        	  num1 = Float.parseFloat(stackData.pop().toString());
  	          num2 = Float.parseFloat(stackData.pop().toString());
  	          stackData.push(num2+num1+"");
  	          queueData.poll();
  	          break;
		
        case'-':
		
        	  num1 = Float.parseFloat(stackData.pop().toString());
  	          num2 = Float.parseFloat(stackData.pop().toString());
  	          stackData.push(num2-num1+"");
  	          queueData.poll();
  	          break;
		
        case'*':
		
        	  num1 = Float.parseFloat(stackData.pop().toString());
  	          num2 = Float.parseFloat(stackData.pop().toString());
  	          stackData.push(num2*num1+"");
  	          queueData.poll();
  	           break;
		
        case'/':
	
        	  num1 = Float.parseFloat(stackData.pop().toString());
  	          num2 = Float.parseFloat(stackData.pop().toString());
  	          stackData.push(num2/num1+"");
  	          queueData.poll();
  	           break;
		
        case '^':
			  num1 = Float.parseFloat(stackData.pop().toString());
  	          num2 = Float.parseFloat(stackData.pop().toString());
  	          stackData.push(Math.pow(num2, num1)+"");
  	          queueData.poll();
  	        break;
		
		default:
			stackData.push(queueData.poll());
			break;
		}
        
        
        }
        return Float.parseFloat(stackData.pop());

		
	}
	////////////////////////////////////////////////////

	
	
	public static void main(String[] args) {
		StringBuffer sb=null;
		for(int i=0;i<expr.length();i++) 
		{
			sb = new StringBuffer(String.valueOf(expr.charAt(i)));
			while(expr.charAt(i)!='+' && expr.charAt(i)!='-' && expr.charAt(i)!='*' 
					&& expr.charAt(i)!='/' && expr.charAt(i)!='(' && expr.charAt(i)!=')'
					&& expr.charAt(i)!='^')
			{
			   	if(i==expr.length()-1)
			   		break;
			   	i++;
			    if(expr.charAt(i)=='+' || expr.charAt(i)=='-' || expr.charAt(i)=='*' 
						|| expr.charAt(i)=='/' || expr.charAt(i)==')' || expr.charAt(i)=='^') 
			    {
			        break;	
			    }
			   	sb.append(String.valueOf(expr.charAt(i)));
				
			}
			  
			list.add(sb.toString());
			if(expr.charAt(i)=='+' || expr.charAt(i)=='-' || expr.charAt(i)=='*' 
					|| expr.charAt(i)=='/'|| expr.charAt(i)==')'||expr.charAt(i)=='^')
				if(sb.toString().charAt(0)!=expr.charAt(i))
				    list.add(String.valueOf(expr.charAt(i)));

		}
		shunting();
		System.out.println(calculate());
		
		}

}
