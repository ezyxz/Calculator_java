package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainProgramme {
	static final int num = 21;// 设置总按钮数量
	static List<String> list = new ArrayList<String>(); //存放每个数字与操作符
	JFrame guiFrame = new JFrame("Calculator");
	NumberButton[] arraybuttons = new NumberButton[num]; //建立class list
	JPanel panel2 = new JPanel();
    static JTextField text ;
    JLabel label;
    //////////////////////////////////////////////////////////////////////////
    /**
     * pick函数主要功能介绍：
     * 将一串字符例如"1.1+200"分别提取为"1.1","+","200"存入全局变量list当中
     * 
     * */
    public void pick(String expr) 
    {
		StringBuffer sb=null;
		for(int i=0;i<expr.length();i++) 
		{
			sb = new StringBuffer(String.valueOf(expr.charAt(i)));// 先取字符串expr的第i个字符
			while(expr.charAt(i)!='+' && expr.charAt(i)!='-' && expr.charAt(i)!='*' 
					&& expr.charAt(i)!='/' && expr.charAt(i)!='(' && expr.charAt(i)!=')'
					&& expr.charAt(i)!='^')//如果不是操作符便继续取字符并且存入StringBuffer当中
			{
			   	if(i==expr.length()-1)     //首先判断 是否取到expr最后一位，否则推出循环
			   		break;
			   	i++;
			    if(expr.charAt(i)=='+' || expr.charAt(i)=='-' || expr.charAt(i)=='*' 
						|| expr.charAt(i)=='/' || expr.charAt(i)==')' || expr.charAt(i)=='^')//或者当遇见字符串也退出
			    	
			    {
			        break;	
			    }
			   	sb.append(String.valueOf(expr.charAt(i)));//先只剩下数字或小数点的可能 存入StringBuffer中
				
			}
			  
			list.add(sb.toString()); //完成第一个数字查找 将StringBuffer变为String存入list中
			if(expr.charAt(i)=='+' || expr.charAt(i)=='-' || expr.charAt(i)=='*' 
					|| expr.charAt(i)=='/'|| expr.charAt(i)==')'||expr.charAt(i)=='^')
				if(sb.toString().charAt(0)!=expr.charAt(i))  // 将操作符存入list中
				    list.add(String.valueOf(expr.charAt(i)));

		}
        
   		
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 此函数shunting 将返回一个使用调度场算法后
     * 转化为后缀式表达 将独立的数字与操作符压入队列中
     * 
     */
    public static Queue<String> shunting() 
	{
		 Stack <String> stackData=new Stack <String>();
		 Queue<String> queueData = new LinkedList<String>();
         
		 while(!list.isEmpty()) {
		    if(!list.get(0).equals("+") && !list.get(0).equals("-") && !list.get(0).equals("*")
		        &&!list.get(0).equals("/") && !list.get(0).equals("(") 
		        && !list.get(0).equals(")")&& !list.get(0).equals("^")) //若该字符串是数字则直接压入队列中
		    {	
			   queueData.add(list.get(0));
		    }
		    else if(list.get(0).equals("("))  //若是"(" 则直接压入栈中
		    {
		    	stackData.push(list.get(0));
		    }
		    else if(list.get(0).equals(")")) //若是 ")" 则将栈中所有操作符压出栈 知道遇到 ")"
		    {
		    	if(stackData.isEmpty()) 
		    	{
		    		System.out.println("error!");
		    		return null;
		    	}
		    		
		    	while(!stackData.peek().equals("(")){
		    		queueData.add(stackData.pop());
		    		
		    	}
		    	stackData.pop();
		    	
		    }
		    else if(list.get(0).equals("^")) //在此计算器仅支持四则运算和乘方 所以乘方运算为最高优先级 所以直接压入栈
		    {
		    	stackData.push(list.get(0));
		    }
		    else if(list.get(0).equals("+")||list.get(0).equals("-") )//operator 若是"+"或者"-" 则需要判断栈当中所存的第一个操作符
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
		    else if(list.get(0).equals("*")||list.get(0).equals("/"))//若是"*"或者"/" 则需要判断栈当中所存的第一个操作符若是"*"，"/"，"^"则压出栈，放入队列直到不再是并把操作符压入栈
		    {
		    	if(!stackData.isEmpty()) 
		    	{
		    	    while(!stackData.isEmpty() 
		    	    &&(stackData.peek().equals("*")||stackData.peek().equals("/")||stackData.peek().equals("^"))) 
		    	    {
		    		       queueData.add(stackData.pop());
		    		}
		    	     
		        }
		    	stackData.push(list.get(0));
		    }
		    
		    list.remove(0);
			 
		 }
		 while(!stackData.isEmpty()) { //将剩余栈中所有的操作符压出栈 压入队列
			 if(stackData.peek().charAt(0)=='(' || stackData.peek().charAt(0)=='）') //错误检查，若遇到括号则 算数表达错误！
				 return null;
			 queueData.add(stackData.pop());
			 
		 }
		 return queueData;
		 
	}
    /**
     * 此函数将队列中的后缀式表达 运算出结果
     * 
     * @param queueData
     * @return null 或者 float
     */
    ////////////////////////////////////////////////////////////////////////////
	public static Object calculate(Queue<String> queueData) {
		 Stack <String> stackData=new Stack <String>();
		 if(queueData==null) {   //错误判断：若是输入参数为空 
			 text.setText("error:格式错误"); 
				return null;

		}else {
			 
		 
        while(queueData.peek().charAt(0)!='+' && queueData.peek().charAt(0)!='-'
         && queueData.peek().charAt(0)!='*' && queueData.peek().charAt(0)!='/' && queueData.peek().charAt(0)!='^')
       	 stackData.push(queueData.poll());  // 先把队列中数字压出知道碰到操作符
       while(!queueData.isEmpty()) 
       {	
       float num1;
       float num2;
       switch(queueData.peek().charAt(0))   // 把栈中的两个数字压出 并把队列中的head压出进行运算 把结果压回栈中 知道队列为空
                                            // 途中若碰到 emptystack 则说明数字格式错误 ！
       {
       case'+':
    	      try {
       	           num1 = Float.parseFloat(stackData.pop().toString());
 	               num2 = Float.parseFloat(stackData.pop().toString());
    	      }catch(EmptyStackException e) {
    				 text.setText("error:格式错误");
    	    	   return 0;
    	      } 
 	               
    	      stackData.push(num2+num1+"");
 	          queueData.poll();
 	          break;
		
       case'-':
    	   try {
   	           num1 = Float.parseFloat(stackData.pop().toString());
	               num2 = Float.parseFloat(stackData.pop().toString());
	      }catch(EmptyStackException e) {
				 text.setText("error:格式错误");
	    	   return 0;
	      } 
 	          stackData.push(num2-num1+"");
 	          queueData.poll();
 	          break;
		
       case'*':
    	   try {
   	           num1 = Float.parseFloat(stackData.pop().toString());
	               num2 = Float.parseFloat(stackData.pop().toString());
	      }catch(EmptyStackException e) {
				 text.setText("error:格式错误");
	    	   return 0;
	      } 
 	          stackData.push(num2*num1+"");
 	          queueData.poll();
 	           break;
		
       case'/':
    	   try {
   	           num1 = Float.parseFloat(stackData.pop().toString());
	               num2 = Float.parseFloat(stackData.pop().toString());
	      }catch(EmptyStackException e) {
				 text.setText("error:格式错误");
	    	   return 0;
	      } 
 	          stackData.push(num2/num1+"");
 	          queueData.poll();
 	           break;
		
       case '^':
    	   try {
   	           num1 = Float.parseFloat(stackData.pop().toString());
	               num2 = Float.parseFloat(stackData.pop().toString());
	      }catch(EmptyStackException e) {
				 text.setText("error:格式错误");
	    	   return 0;
	      } 
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
		

		
	}
    ////////////////////////////////////////////////////////////////////////////

	   public void creatGUI() {
	    	panel2.setLayout(null);
	    	panel2.setPreferredSize(new Dimension(340,400)); // 设为手动输入每个按钮和文本框的位置与大小
	    	
            int state=0;
            label = new JLabel();
            label.setText("Author:18992581");
            label.setBounds(5, 5, 150, 10);
	    	for(int i=6;i<9;i++) {
            	arraybuttons[i]=new NumberButton(i+1+"");
            	arraybuttons[i].button.setBounds(40+60*state,150, 50, 50);
            	state++;
            	
            }
	    	state=0;
	    	for(int i=3;i<6;i++) {
            	arraybuttons[i]=new NumberButton(i+1+"");
            	arraybuttons[i].button.setBounds(40+60*state,210, 50, 50);
            	state++;
            }
	    	state=0;
	    	for(int i=0;i<3;i++) {
            	arraybuttons[i]=new NumberButton(i+1+"");
            	arraybuttons[i].button.setBounds(40+60*state,270, 50, 50);
            	state++;
            }
	 		arraybuttons[9]= new NumberButton(0+"");
	 		arraybuttons[9].button.setBounds(40,330, 110, 50);
	 		arraybuttons[10]= new NumberButton(".");
	 		arraybuttons[10].button.setBounds(160, 330, 50, 50);

     		
	 		arraybuttons[11]= new NumberButton("MC");
	 		arraybuttons[11].button.setBounds(40,90, 55, 50);
	 		arraybuttons[12]= new NumberButton("MB");
	 		arraybuttons[12].button.setBounds(100,90,55, 50);
	 	
	 		arraybuttons[13]= new NumberButton("+");
	 		arraybuttons[13].button.setBounds(230,150, 70, 35);
	 		arraybuttons[14]= new NumberButton("-");
	 		arraybuttons[14].button.setBounds(230,195, 70, 35);
	 		arraybuttons[15]= new NumberButton("*");
	 		arraybuttons[15].button.setBounds(230,240, 70, 35);
	 		arraybuttons[16]= new NumberButton("/");
	 		arraybuttons[16].button.setBounds(230,285, 70, 35);
	 		arraybuttons[17]= new NumberButton("=");
	 		arraybuttons[17].button.setBounds(230,330, 70, 50);
	 		arraybuttons[18]= new NumberButton("(");
	 		arraybuttons[18].button.setBounds(160,90,40, 50);
	 		arraybuttons[19]= new NumberButton(")");
	 		arraybuttons[19].button.setBounds(205,90, 40, 50);
	 		arraybuttons[20]= new NumberButton("^");
	 		arraybuttons[20].button.setBounds(250,90, 50, 50);
	 		
	 		ButtonPressed  bp = new ButtonPressed();

	 		for(int i = 0;i<num ;i++) {
            	panel2.add(arraybuttons[i].button);
            	arraybuttons[i].button.addActionListener(bp); 	// 建立监听
	 		}
            text = new JTextField(30);
            text.setBounds(40, 30, 260, 40);
            panel2.add(text);
            panel2.add(label);

	    	
	 		
	 		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 		guiFrame.add(panel2);
	 		guiFrame.pack();
	 		guiFrame.setLocationRelativeTo(null);
	 		guiFrame.setVisible(true);
	    	
	    }
	/////////////////////////////////////////////////////////////////   
    public class ButtonPressed implements ActionListener //实现监听 建立inner class
	{
		   int state=0;
		   int Jzero=0;
		   int Jpoint=0;
		   int Joperator=0;

		@Override
		public void actionPerformed(ActionEvent e) {
			///number 处理
			for(int i = 0;i<9 ;i++) {
			if (e.getSource() == arraybuttons[i].button && (Jzero==0||Jpoint==1)){
				String temp=text.getText();
				text.setText(temp+arraybuttons[i].behalf);
				if (state <0)
			    	state=1;
			    else
				    state++;	
				Joperator=0;
			}
		
			}
			/// MB （退格） 处理
			if (e.getSource() == arraybuttons[12].button && state >0 ) {
				String temp = text.getText();
				
				String lastchar = temp.substring(temp.length()-1);
				if(lastchar.equals(".")) {
					Jpoint=0;
					Jzero=1;
					System.out.println("get");
				}
				if(lastchar.equals("+")||lastchar.equals("-")||lastchar.equals("*")||lastchar.equals("/")) {
					Joperator=0;
					System.out.println("get operator");
				}
				temp = temp.substring(0,temp.length()-1);
				text.setText(temp);
				state--;
				if (state<=0 ) {
					System.out.println("blank");
					state=0;
					Jzero=0;
					Jpoint=0;
					Joperator=0;
					list = new ArrayList<String>();	
				}
			
			}
			/// MC （清除） 处理 
			if (e.getSource() == arraybuttons[11].button) {
				text.setText("");
				state=0;
				Jzero=0;
				Jpoint=0;
				Joperator=0;
				list = new ArrayList<String>();
				
			}
			/// 0 处理
			if (e.getSource() == arraybuttons[9].button && (Jzero==0||Jpoint==1)) {
				String temp=text.getText();
				text.setText(temp+arraybuttons[9].behalf);
				Jzero=1;
				Joperator=0;
				if (state <0)
			    	state=1;
			    else
				    state++;
			}
			/// . 处理
			if(e.getSource() == arraybuttons[10].button && Jpoint==0 && state >0 && Joperator==0) {
				String temp=text.getText();
				text.setText(temp+arraybuttons[10].behalf);
				Jpoint=1;
				Jzero=0;
				if (state <0)
			    	state=1;
			    else
				    state++;
				
			}
			/// operator 处理
			for(int i = 13;i<17 ;i++) {
				if (e.getSource() == arraybuttons[i].button && state >0 && Joperator==0){
					String temp=text.getText();
					text.setText(temp+arraybuttons[i].behalf);
					if (state <0)
				    	state=1;
				    else
					    state++;
					Jpoint=0;
					Jzero=0;
					Joperator=1;
				}
			}
			    /// ( 处理
				if (e.getSource() == arraybuttons[18].button  && Joperator==1){
					String temp=text.getText();
					text.setText(temp+arraybuttons[18].behalf);
					if (state <0)
				    	state=1;
				    else
					    state++;
				
				}
				/// ) 处理
				if (e.getSource() == arraybuttons[19].button  && Joperator==0){
					String temp=text.getText();
					text.setText(temp+arraybuttons[19].behalf);
					if (state <0)
				    	state=1;
				    else
					    state++;
				
				}
			// = 处理
			if (e.getSource() == arraybuttons[17].button ){
				
				String temp=text.getText();
				if(temp.charAt(temp.length()-1)!='=') {
				    pick(temp);
				    text.setText(temp+arraybuttons[17].behalf);
				    if (state <0)
				    	state=1;
			        else
			        	state++;	
				    try 
				    {
				    	float ans = (float) calculate(shunting());
				       
				    	if ( ans== Math.ceil(ans) ) {
				    	     text.setText(temp+"= "+(int)ans);
				        }else {
				    	     text.setText(temp+"= "+ans);
				         }
				    }catch(NullPointerException |ClassCastException e1) {
				    	text.setText("error:格式错误！");
				    }
				    
				}
			}
			// ^ 处理
			if (e.getSource() == arraybuttons[20].button && state >0 && Joperator==0){
				String temp=text.getText();
				text.setText(temp+arraybuttons[20].behalf);
				if (state <0)
			    	state=1;
			    else
				    state++;
				Jpoint=0;
				Jzero=0;
				Joperator=1;
			}
			
			
		}
		
	}

	public static void main(String[] args) {
		new MainProgramme().creatGUI();

	}

}
