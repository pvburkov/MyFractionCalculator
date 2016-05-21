
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import org.w3c.dom.Document;

public class CalcWork {
	
	//fields of class UserInput
	private String inputFilePath = new String();
	
	//constructors
	public CalcWork() {
		setInputFilePath("EMPTY");
	}
	
	public CalcWork(String inputFilePath) {
		setInputFilePath(inputFilePath);
	}

	//methods
	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	
	public void work() throws IOException, Exception {
		
		//для создания выходного xml-файла при вводе данных через файл
		//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    //DocumentBuilder db = dbf.newDocumentBuilder(); 
	    //Document resultFile = db.newDocument();
		
		Scanner in;
		
		if (getInputFilePath().equals("EMPTY")) {
			in = new Scanner(System.in);
		}
		else {
			in = new Scanner(Paths.get(inputFilePath));
			
		}
		String inputLine = new String();
        do {
        	if (getInputFilePath().equals("EMPTY")) {
        		System.out.println("Print new math problem in this style 'A/B + C/D' or print 'exit':");
        		inputLine = in.nextLine();
        	} else {
        		if (in.hasNextLine()) {
        			inputLine = in.nextLine();
        		} else {
        			break;
        		}
        	}
        	
        	inputLine = inputLine.toLowerCase();
        	
        	if (getInputFilePath().equals("EMPTY") && inputLine.equals("exit")) {
        		System.out.println("Goodbye! Thanks for using this program!");
        		break;
        	}
        	else {
        		inputLine = inputLine.trim();
        		if (inputLine.length() < 7) {
        			if (getInputFilePath().equals("EMPTY")) {
        				System.out.println("ERROR! Wrong input, string is too short. Try again.");
        			} else {
        				//to xml	
        			}
        			continue;
        		}
        		else {
        			String sU1, sD1, sU2, sD2;
        			long u1, d1, u2, d2;
        			String keyMathOper;
        			try {	
        				sU1 = inputLine.substring(0, inputLine.indexOf("/"));
	        			inputLine = inputLine.substring(inputLine.indexOf("/") + 1);
	        			sU1 = sU1.trim();
	        			if (inputLine.indexOf("+") != -1) {
	        				keyMathOper = "+";
	        			} else if (inputLine.indexOf("-") != -1) {
	        				keyMathOper = "-";
	        			} else if (inputLine.indexOf("*") != -1) {
	        				keyMathOper = "*";
	        			} else {
	        				keyMathOper = "/";
	        			}
	        			sD1 = inputLine.substring(0, inputLine.indexOf(keyMathOper));
	        			inputLine = inputLine.substring(inputLine.indexOf(keyMathOper) + 1);
	        			sD1 = sD1.trim();
	        			sU2 = inputLine.substring(0, inputLine.indexOf("/"));
	        			inputLine = inputLine.substring(inputLine.indexOf("/") + 1);
	        			sU2 = sU2.trim();
	        			sD2 = inputLine;
	        			sD2 = sD2.trim();
    					u1 = Long.parseLong(sU1);
    					d1 = Long.parseLong(sD1);
    					u2 = Long.parseLong(sU2);
    					d2 = Long.parseLong(sD2);
    					if (u1 <= 0 || d1 <= 0 || u2 <= 0 || d2 <= 0) {
    						if (getInputFilePath().equals("EMPTY")) {
    							System.out.println("ERROR! Null of less-than-0-element found. Try again without any element less or equal to 0.");
    						} else {
    							// to xml
    						}
    						continue;
    					}
    					Fraction left = new Fraction(u1, d1);
    					Fraction right = new Fraction(u2, d2);
    					Fraction out = new Fraction();
    					if (keyMathOper == "+") {
            				out = left.fractSum(right);
            			} else if (keyMathOper == "-") {
            				out = left.fractDiff(right);
            			} else if (keyMathOper == "*") {
            				out = left.fractMult(right);
            			} else {
            				out = left.fractDiv(right);
            			}
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("The result of this math task is " + out.printFraction());
    					} else {
    						// to xml
    					}
    				} catch (StringIndexOutOfBoundsException e1) {
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
    					} else {
    						// to xml
    					}
    					continue;
    				} catch (NumberFormatException e2) {
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
    					} else {
    						// to xml
    					}
    					continue;
    				}
        		}
        	}
        } while (true);
		in.close();
	}
}
