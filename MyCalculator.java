package mycalcpackage;
import calcworkpackage.CalcWork;

public class MyCalculator {

	public static void main(String[] args) throws Exception {
		
		CalcWork work;
		try {
			work = new CalcWork(args[0]);
			work.work();
		} catch (Exception e) {
			System.out.println("There is no file path - work through console.");
			work = new CalcWork();
			work.work();
		}
		System.out.println("End of work.");
		
	}
	
}
