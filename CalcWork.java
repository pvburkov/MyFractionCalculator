
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public void work() throws Exception {
		
		Scanner in;
		int taskNum = 0;
		
		//äëÿ ñîçäàíèÿ âûõîäíîãî xml-ôàéëà ïðè ââîäå äàííûõ ÷åðåç ôàéë
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); 
	    	Document resultFile = dbf.newDocumentBuilder().newDocument();
	    
	    	Element root = resultFile.createElement("fullSolution");
	    	root.setAttribute("xmlns", "http://www.javacore.ru/schemas/");
	    	resultFile.appendChild(root);
		
	    	//îïðåäåëåíèå âõîäíîãî ïîòîêà
		if (getInputFilePath().equals("EMPTY")) {
			in = new Scanner(System.in);
		}
		else {
			try {
				in = new Scanner(Paths.get(inputFilePath));
			} catch (Exception e) {
				System.out.println("Wrong file name. (Exception " + e + "). Switching to work through console.");
				in = new Scanner(System.in);
				setInputFilePath("EMPTY");
			}
		}
		
		String inputLine = new String();
        	do {
        	//ââîä î÷åðåäíîãî ïðèìåðà (ñòðêà èç ôàéëà ëèáî èç êîíñîëè)
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
	        	
	        	//àíàëèç ñòðîêè (exit çàâåðøàåò âûïîëíåíèå)
	        	if (getInputFilePath().equals("EMPTY") && inputLine.equals("exit")) {
	        		System.out.println("Goodbye! Thanks for using this program!");
	        		break;
	        	}
	        	else {
	        		//ïðîâåðêà äëèíû ñòðîêè; ìèí äëèíà ñòðîêè ïðèìåðà = 7 (A/B*C/D)
	        		inputLine = inputLine.trim();
	        		if (inputLine.length() < 7) {
	        			if (getInputFilePath().equals("EMPTY")) {
	        				System.out.println("ERROR! Wrong input, string is too short. Try again.");
	        			} else {
	        				Element item = resultFile.createElement("mathTask");
	        				item.setAttribute("ID", Integer.toString(taskNum + 1));
	        				item.setAttribute("task", inputLine);
	        				item.setAttribute("result", "Wrong input, string is too short.");
	        				root.appendChild(item);	
	        				++taskNum;
	        			}
	        			continue;
	        		}
	        		else {
	        			//ðàçáîð ñòðîêè íà ÷àñòè (âêëþ÷àÿ îáðàáîòêó èñêëþ÷åíèé)
	        			String sU1, sD1, sU2, sD2, inputLineCopy = inputLine;
	        			long u1, d1, u2, d2;
	        			String keyMathOper;
	        			try {
	        				//ðàçáèåíèå ñòðîêè íà ïîäñòðîêè
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
		        			//ïðåîáðàçîâàíèå ïîäñòðîê â ÷èñëà (long)
	    					u1 = Long.parseLong(sU1);
	    					d1 = Long.parseLong(sD1);
	    					u2 = Long.parseLong(sU2);
	    					d2 = Long.parseLong(sD2);
	    					//ïðîâåðêà ââåäåííûõ ÷èñåë, ÿâëÿþòñÿ ëè îíè ñòðîãî ïîëîæèòåëüíûìè
	    					if (u1 <= 0 || d1 <= 0 || u2 <= 0 || d2 <= 0) {
	    						if (getInputFilePath().equals("EMPTY")) {
	    							System.out.println("ERROR! Null of less-than-0-element found. Try again without any element less or equal to 0.");
	    						} else {
	    							Element item = resultFile.createElement("mathTask");
		    	        				item.setAttribute("ID", Integer.toString(taskNum + 1));
		    	        				item.setAttribute("task", inputLineCopy);
		    	        				item.setAttribute("result", "Wrong input - elements less or equal to 0 found.");
		    	        				root.appendChild(item);	
		    	        				++taskNum;
	    						}
	    						continue;
	    					}
	    					Fraction left = new Fraction(u1, d1);
	    					Fraction right = new Fraction(u2, d2);
	    					Fraction out = new Fraction();
	    					//âûïîëíåíèå óêàçàííîé ìàòåì.îïåðàöèè
	    					if (keyMathOper == "+") {
		            				out = left.fractSum(right);
		            			} else if (keyMathOper == "-") {
		            				out = left.fractDiff(right);
		            			} else if (keyMathOper == "*") {
		            				out = left.fractMult(right);
		            			} else {
		            				out = left.fractDiv(right);
		            			}
	    					//çàïèñü ðåçóëüòàòà â ôàéë ëèáî âûâîä â êîíñîëü
	    					if (getInputFilePath().equals("EMPTY")) {
	    						System.out.println("The result of this math task is " + out.printFraction());
	    					} else {
	    						String printTask = Long.toString(u1) + "/" + Long.toString(d1) + " " + keyMathOper;
	    						printTask = printTask + " " + Long.toString(u2) + "/" + Long.toString(d2);
	    						
	    						Element item = resultFile.createElement("mathTask");
		        				item.setAttribute("ID", Integer.toString(taskNum + 1));
		        				item.setAttribute("task", printTask);
		        				item.setAttribute("result", out.printFraction());
		        				root.appendChild(item);	
		        				++taskNum;
	    					}
	    				} catch (StringIndexOutOfBoundsException e1) {
	    					//îøèáêà ÷òåíèÿ ñòðîêè, ñâÿçàííàÿ ñ íåêîððåêòíûì ââîäîì
	    					if (getInputFilePath().equals("EMPTY")) {
	    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
	    					} else {
	    						Element item = resultFile.createElement("mathTask");
		        				item.setAttribute("ID", Integer.toString(taskNum + 1));
		        				item.setAttribute("task", inputLineCopy);
		        				item.setAttribute("result", "Wrong input - math-task string contains mistakes. Exception: " + e1);
		        				root.appendChild(item);	
		        				++taskNum;
	    					}
	    					continue;
	    				} catch (NumberFormatException e2) {
	    					//îøèáêà, ñâÿçàííàÿ ñ íåêîððåêòíûì ïðåîáðàçîâàíèåì ñòðîêè â ÷èñëî (ïî ïðè÷èíå íåâåðíîãî ââîäà)
	    					if (getInputFilePath().equals("EMPTY")) {
	    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
	    					} else {
	    						Element item = resultFile.createElement("mathTask");
		        				item.setAttribute("ID", Integer.toString(taskNum + 1));
		        				item.setAttribute("task", inputLineCopy);
		        				item.setAttribute("result", "Wrong input - math-task string contains mistakes. Exception: " + e2);
		        				root.appendChild(item);	
		        				++taskNum;
	    					}
	    					continue;
	    				}
	        		}
	        	}
	        } while (true);
		in.close();
		
		//çàâåðøåíèå çàïèñè âûõîäíîãî ôàéëà (â ñëó÷àå ââîäà ÷åðåç ôàéë)
		if (!(getInputFilePath().equals("EMPTY"))) {
			File file = new File("result.xml"); 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(resultFile), new StreamResult(file));
		}
	}
}
