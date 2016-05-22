
public class Fraction {
	
	//����� ������
	//fields of class Fraction
	private long upperPart = 1;
	private long downPart = 1;
	
	//constructors
	public Fraction() {}
	
	public Fraction(long up, long down) {
		this.upperPart = up;
		this.downPart = down;
	}
	
	//methods
	public void setFraction(long up, long down) {
		upperPart = up;
		downPart = down;
	}
	
	public long[] getFraction() {
		long fract[] = new long[2];
		fract[0] = this.upperPart;
		fract[1] = this.downPart;
		return fract;
	}
	
	//������� ������ ����� � ���� ������
	public String printFraction() {
		return (upperPart + "/" + downPart);
	}
	
	//������� ����� ������
	public Fraction fractSum(Fraction right) {
		
		Fraction result = new Fraction();
		long rightFr[] = new long[2];
		long upRes = 1;
		long downRes = 1;
		long coef = 1;
		rightFr = right.getFraction();
		if (this.downPart == rightFr[1]) {
			upRes = this.upperPart + rightFr[0];
			downRes = this.downPart;
			result.setFraction(upRes, downRes);
		}
		else if (this.downPart % rightFr[1] == 0) {
			coef = this.downPart / rightFr[1];
			upRes = coef * rightFr[0] + this.upperPart;
			downRes = this.downPart;
			result.setFraction(upRes, downRes);
		}
		else if (rightFr[1] % this.downPart == 0) {
			coef = rightFr[1] / this.downPart ;
			upRes = coef * this.upperPart + rightFr[0];
			downRes = rightFr[1];
			result.setFraction(upRes, downRes);
		}
		else {
			upRes = rightFr[1] * this.upperPart + this.downPart * rightFr[0];
			downRes = this.downPart * rightFr[1];
			result.setFraction(upRes, downRes);
		}
		return result;
		
	}
	
	//������� �������� ������
	public Fraction fractDiff(Fraction right) {
		
		Fraction result = new Fraction();
		long rightFr[] = new long[2];
		long upRes = 1;
		long downRes = 1;
		long coef = 1;
		rightFr = right.getFraction();
		if (this.downPart == rightFr[1]) {
			upRes = this.upperPart - rightFr[0];
			downRes = this.downPart;
			result.setFraction(upRes, downRes);
		}
		else if (this.downPart % rightFr[1] == 0) {
			coef = this.downPart / rightFr[1];
			upRes = this.upperPart - coef * rightFr[0];
			downRes = this.downPart;
			result.setFraction(upRes, downRes);
		}
		else if (rightFr[1] % this.downPart == 0) {
			coef = rightFr[1] / this.downPart ;
			upRes = coef * this.upperPart - rightFr[0];
			downRes = rightFr[1];
			result.setFraction(upRes, downRes);
		}
		else {
			upRes = rightFr[1] * this.upperPart - this.downPart * rightFr[0];
			downRes = this.downPart * rightFr[1];
			result.setFraction(upRes, downRes);
		}
		return result;
		
	}
	
	//������� ������������ ������
	public Fraction fractMult(Fraction right) {
		
		Fraction result = new Fraction();
		long rightFr[] = new long[2];
		long upRes = 1;
		long downRes = 1;
		rightFr = right.getFraction();
		upRes = this.upperPart * rightFr[0];
		downRes = this.downPart * rightFr[1];
		result.setFraction(upRes, downRes);
		return result;
		
	}
	
	//������� �������� (�������) ������
	public Fraction fractDiv(Fraction right) {
		
		Fraction result = new Fraction();
		long rightFr[] = new long[2];
		long upRes = 1;
		long downRes = 1;
		rightFr = right.getFraction();
		upRes = this.upperPart * rightFr[1];
		downRes = this.downPart * rightFr[0];
		result.setFraction(upRes, downRes);
		return result;
		
	}
}
