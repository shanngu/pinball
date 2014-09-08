import Jama.Matrix;

public class PinballCalculator {
	private double s, r, initAngle;
	private Matrix c1, c2, c3, x;
	
	public PinballCalculator(double s, double r) {
		this.s = s;
		this.r = r;
		double[][] arrC1 = {{s / 2.0, -(s * Math.sqrt(3.0)) / 6.0}};
		double[][] arrC2 = {{-s / 2.0, -(s * Math.sqrt(3.0)) / 6.0}};
		double[][] arrC3 = {{0, -(s * Math.sqrt(3.0)) / 3.0}};
		c1 = new Matrix(arrC1);
		c2 = new Matrix(arrC2);
		c3 = new Matrix(arrC3);
		double[][] arrX = {{0, 0}};
		x = new Matrix(arrX);
	}
	
	public Matrix genRandVelocity() {
		double angle;
		angle = Math.random() * 2 * Math.PI;
		initAngle = angle;
		double[][] v = {{Math.cos(angle), Math.sin(angle)}};
		return new Matrix(v);
	}
	
	public Matrix genSystVelocity(double angle) {
		initAngle = angle;
		double[][] v = {{Math.cos(angle), Math.sin(angle)}};
		return new Matrix(v);
	}
	
	public double intersect(Matrix c, double r, Matrix x, Matrix v) {
		Matrix cMinusX = c.minus(x);
		double dotVCMX = 0;
		double D = 0;
		
		for (int i = 0; i < v.getArray().length; i++) {
			for (int j = 0; j < v.getArray()[i].length; j++) {
				dotVCMX += v.getArray()[i][j] * cMinusX.getArray()[i][j];
			}
		}
		
		if (dotVCMX <= 0) {
			return -1;
		}
		
		D += Math.pow(dotVCMX, 2);
		D -= Math.pow(cMinusX.normF(), 2);
		D += Math.pow(r, 2);
		
		if (D <= 0) {
			return -1;
		}
		
		return dotVCMX - Math.sqrt(D);
	}
	
	public Matrix reflect(Matrix c, Matrix x, Matrix v) {
		Matrix cMinusX = c.minus(x);
		double dotVCMX = 0;
		Matrix w;
		
		for (int i = 0; i < v.getArray().length; i++) {
			for (int j = 0; j < v.getArray()[i].length; j++) {
				dotVCMX += v.getArray()[i][j] * cMinusX.getArray()[i][j];
			}
		}
		
		w = cMinusX.times((2 * dotVCMX) / Math.pow(cMinusX.normF(), 2));
		w = v.minus(w);
		
		return w;
	}
	
	public double getS() {return s;}
	public double getR() {return r;}
	public double getInitAngle() {return initAngle;}
	public Matrix getC1() {return c1;}
	public Matrix getC2() {return c2;}
	public Matrix getC3() {return c3;}
	public Matrix getX() {return x;}
	public void setX(Matrix x) {
		this.x = x;
	}

}
