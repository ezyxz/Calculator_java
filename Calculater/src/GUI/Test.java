package GUI;
import java.awt.Dimension;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Test {
	public static String midToBehind(String formula) {  
	    String S = ""; // 后缀  
	    StringTokenizer st1 = new StringTokenizer(formula);  
	    String[] Operators = new String[st1.countTokens()];  
	  
	    int Top = -1;  
	    while (st1.hasMoreTokens()) {  
	        String C = st1.nextToken();  
	        ;  
	        switch (C.charAt(0)) {  
	        case ' ':  
	            break;  
	        case '+': // 
	        case '>':  
	        case '<':  
	        case '-':  
	            while (Top >= 0) // 
	            {  
	                String c = Operators[Top--]; // pop Operator  
	                if (c.equals("(")) {  
	                    Operators[++Top] = c; // push Operator  
	                    break;  
	                } else {  
	                    S = S + c;  
	                }  
	            }  
	            Operators[++Top] = C; // push Operator  
	            S += " ";  
	            break;  
	        case '*': //   
	        case '/':  
	            while (Top >= 0) //   
	            {  
	                String c = Operators[Top--]; // pop Operator  
	                if (c.equals("(")) {  
	                    Operators[++Top] = c; // push Operator  
	                    break;  
	                } else {  
	                    if (c.equals("+") || c.equals("-")) {  
	                        Operators[++Top] = c; // push Operator  
	                        break;  
	                    } else {  
	                        S = S + c;  
	                    }  
	                }  
	            }  
	            Operators[++Top] = C; // push Operator  
	            S += " ";  
	            break;  
	        case '(': //  
	            Operators[++Top] = C;  
	            S += " ";  
	            break;  
	        case ')': //  
	            while (Top >= 0) //  
	            {  
	                String c = Operators[Top--]; // pop Operator  
	                if (c.equals("(")) {  
	                    break;  
	                } else {  
	                    S = S + c;  
	                }  
	            }  
	            S += " ";  
	            break;  
	        default: //   
	            S = S + C + " ";  
	            break;  
	        }  
	    }  
	    while (Top >= 0) {  
	        S = S + Operators[Top--]; // pop Operator  
	    }  
	  
	    System.out.println(S); // 
	  
	    return S;  
	}  
	////////////////////////////////////////////
	public static boolean checkBehind(String lpszParse) {  
		  
	    int Otp = -1;  
	    boolean returnFlag = true;  
	    if (lpszParse != null) {  
	        // 后缀表达式不含有括号  
	        if (lpszParse.contains("(") || lpszParse.contains(")")) {  
	            return false;  
	        }  
	  
	        // 分割表达式  
	        StringTokenizer st1 = new StringTokenizer(lpszParse);  
	        String[] Operands = new String[st1.countTokens()];  
	        while (st1.hasMoreTokens()) {  
	            String s = st1.nextToken();  
	            switch (s.charAt(0)) {  
	            case ' ':  
	  
	            case '+':  
	            case '-':  
	            case '*':  
	            case '/':  
	            case '>':  
	            case '<':  
	                // 如果为运算符，刚将操作数出栈，如果小于两个操作数，则不正确  
	                if (Otp < 1) {  
	                    returnFlag = false;  
	                } else {  
	  
	                    // 若存在两个操作数，则将两个操作数出栈  
	                    String o1 = Operands[Otp--];  
	                    String o2 = Operands[Otp--];  
	  
	                    // 将运算结果做为一个操作数，入栈，供下一次运算  
	                    Operands[++Otp] = o1 + "&" + o2;  
	                }  
	                break;  
	            default:  
	  
	                // 若为操作数，则入栈  
	                Operands[++Otp] = s;  
	            }  
	  
	            // 如果存在不合法，跳出循环  
	            if (!returnFlag) {  
	                break;  
	            }  
	        }  
	        // 运算完成，栈内应该只剩最后一次运算的结果  
	        if (--Otp >= 0) {  
	            returnFlag = false;  
	        }  
	    }  
	  
	    // 检测结果近回  
	    return returnFlag;  
	}  

	////////////////////////////////////////////
	public static void main(String[] args) {
		
		System.out.println(checkBehind("12+"));
     }
	
}
