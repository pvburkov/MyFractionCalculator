
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
		
		//дл€ создани€ выходного xml-файла при вводе данных через файл
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); 
	    Document resultFile = dbf.newDocumentBuilder().newDocument();
	    
	    Element root = resultFile.createElement("fullSolution");
	    root.setAttribute("xmlns", "http://www.javacore.ru/schemas/");
	    resultFile.appendChild(root);
		
	    //определение входного потока
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
        	//ввод очередного примера (стрка из файла либо из консоли)
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
        	
        	//анализ строки (exit завершает выполнение)
        	if (getInputFilePath().equals("EMPTY") && inputLine.equals("exit")) {
        		System.out.println("Goodbye! Thanks for using this program!");
        		break;
        	}
        	else {
        		//проверка длины строки; мин длина строки примера = 7 (A/B*C/D)
        		inputLine = inputLine.trim();
        		if (inputLine.length() < 7) {
        			if (getInputFilePath().equals("EMPTY")) {
        				System.out.println("ERROR! Wrong input, string is too short. Try again.");
        			} else {
        				Element item = resultFile.createElement("mathTask");
        				item.setAttribute("ID", Integer.toString(taskNum + 1)); // int to string
        				item.setAttribute("task", inputLine);
        				item.setAttribute("result", "Wrong input, string is too short.");
        				root.appendChild(item);	
        				++taskNum;
        			}
        			continue;
        		}
        		else {
        			//разбор строки на части (включа€ обработку исключений)
        			String sU1, sD1, sU2, sD2, inputLineCopy = inputLine;
        			long u1, d1, u2, d2;
        			String keyMathOper;
        			try {
        				//разбиение строки на подстроки
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
	        			//преобразование подстрок в числа (long)
    					u1 = Long.parseLong(sU1);
    					d1 = Long.parseLong(sD1);
    					u2 = Long.parseLong(sU2);
    					d2 = Long.parseLong(sD2);
    					//проверка введенных чисел, €вл€ютс€ ли они строго положительными
    					if (u1 <= 0 || d1 <= 0 || u2 <= 0 || d2 <= 0) {
    						if (getInputFilePath().equals("EMPTY")) {
    							System.out.println("ERROR! Null of less-than-0-element found. Try again without any element less or equal to 0.");
    						} else {
    							Element item = resultFile.createElement("mathTask");
    	        				item.setAttribute("ID", Integer.toString(taskNum + 1)); // int to string
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
    					//выполнение указанной матем.операции
    					if (keyMathOper == "+") {
            				out = left.fractSum(right);
            			} else if (keyMathOper == "-") {
            				out = left.fractDiff(right);
            			} else if (keyMathOper == "*") {
            				out = left.fractMult(right);
            			} else {
            				out = left.fractDiv(right);
            			}
    					//запись результата в файл либо вывод в консоль
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("The result of this math task is " + out.printFraction());
    					} else {
    						String printTask = Long.toString(u1) + "/" + Long.toString(d1) + " " + keyMathOper;
    						printTask = printTask + " " + Long.toString(u2) + "/" + Long.toString(d2);
    						
    						Element item = resultFile.createElement("mathTask");
	        				item.setAttribute("ID", Integer.toString(taskNum + 1)); // int to string
	        				item.setAttribute("task", printTask);
	        				item.setAttribute("result", out.printFraction());
	        				root.appendChild(item);	
	        				++taskNum;
    					}
    				} catch (StringIndexOutOfBoundsException e1) {
    					//ошибка чтени€ строки, св€занна€ с некорректным вводом
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
    					} else {
    						Element item = resultFile.createElement("mathTask");
	        				item.setAttribute("ID", Integer.toString(taskNum + 1)); // int to string
	        				item.setAttribute("task", inputLineCopy);
	        				item.setAttribute("result", "Wrong input - math-task string contains mistakes. Exception: " + e1);
	        				root.appendChild(item);	
	        				++taskNum;
    					}
    					continue;
    				} catch (NumberFormatException e2) {
    					//ошибка, св€занна€ с некорректным преобразованием строки в число (по причине неверного ввода)
    					if (getInputFilePath().equals("EMPTY")) {
    						System.out.println("ERROR! Incorrect input. Try again according to example 'A/B + C/D'");
    					} else {
    						Element item = resultFile.createElement("mathTask");
	        				item.setAttribute("ID", Integer.toString(taskNum + 1)); // int to string
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
		
		//завершение записи выходного файла (в случае ввода через файл)
		if (!(getInputFilePath().equals("EMPTY"))) {
			File file = new File("result.xml"); 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(resultFile), new StreamResult(file));
		}
	}
}
