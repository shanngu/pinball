import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;

public class Pinball {
	
	@SuppressWarnings("unchecked")
	public static void tests(double rVal, int tests, boolean isRandom) {
		PinballCalculator pb = new PinballCalculator(6, rVal);
		ArrayList<String> circles = new ArrayList<>();
		boolean isC1Intersected, isC2Intersected, isC3Intersected;
		double c1t, c2t, c3t;
		int[] maxTen = new int[1000];
		double[] tenAngles = new double[1000];
		ArrayList<String>[] circlesHit = new ArrayList[1000];
		int[] freq = new int[1000];
		int hits;
		Matrix v;
		double angle = 0;
		double spacing = (2 * Math.PI) / tests;
		for (int i = 0; i < tests; i++) {
			hits = 0;
			if (isRandom) {
				v = pb.genRandVelocity();
			} else {
				v = pb.genSystVelocity(angle);
				angle += spacing;
			}
			isC1Intersected = false;
			isC2Intersected = false;
			isC3Intersected = false;
			circles.clear();
			do {
				c1t = -1;
				c2t = -1;
				c3t = -1;
				if (!isC1Intersected) {
					c1t = pb.intersect(pb.getC1(), pb.getR(), pb.getX(), v);
				}
				if (!isC2Intersected) {
					c2t = pb.intersect(pb.getC2(), pb.getR(), pb.getX(), v);
				}
				if (!isC3Intersected) {
					c3t = pb.intersect(pb.getC3(), pb.getR(), pb.getX(), v);
				}
				
				if (!(c1t == -1 && c2t == -1 && c3t == -1)) {
					double mint = 0;
					Matrix c = new Matrix(0, 0);
					if (c1t != -1) {
						mint = c1t;
					} else if (c2t != -1) {
						mint = c2t;
					} else if (c3t != -1) {
						mint = c3t;
					}
					if (c1t != -1) {
						if (mint >= c1t) {
							mint = c1t;
							c = pb.getC1();
							isC1Intersected = true;
							isC2Intersected = false;
							isC3Intersected = false;
						}
					}
					if (c2t != -1) {
						if (mint >= c2t) {
							mint = c2t;
							c = pb.getC2();
							isC1Intersected = false;
							isC2Intersected = true;
							isC3Intersected = false;
						}
					}
					if (c3t != -1) {
						if (mint >= c3t) {
							mint = c3t;
							c = pb.getC3();
							isC1Intersected = false;
							isC2Intersected = false;
							isC3Intersected = true;
						}
					}
					if (mint == c1t && mint != -1) {
						circles.add("1");
					} else if (mint == c2t && mint != -1) {
						circles.add("2");
					} else if (mint == c3t && mint != -1) {
						circles.add("3");
					}
					pb.setX(pb.getX().plus(v.times(mint)));
					v = pb.reflect(c, pb.getX(), v);
					hits++;
				}
			} while (!(c1t == -1 && c2t == -1 && c3t == -1));
			
			freq[hits]++;
			maxTen[hits] = 1;
			tenAngles[hits] = pb.getInitAngle();
			ArrayList<String> tempCircles = circles;
			circlesHit[hits] = new ArrayList<String>(tempCircles);
		}
		
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] != 0) {
				System.out.println("Frequency for " + i + " hits: " + freq[i] + 
					"\tRelative frequency: " + ((double)freq[i] / (double) tests));
			}
		}
		System.out.println("Angles and Respective Sequences for Top 10 Hit Values");
		int max = 0;
		for (int i = maxTen.length - 1; i >= 0; i--) {
			if (maxTen[i] != 0) {
				if (circlesHit[i] == null) {
					System.out.println(i + " Hit(s): " + "Angle of " + tenAngles[i] + " radians with sequence []");
				} else {
					System.out.println(i + " Hit(s): " + "Angle of " + tenAngles[i] + " radians with sequence " + circlesHit[i]);
				}
				max++;
			}
			if (max == 10) {
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int testCases;
		System.out.println("How many test cases would you like to do?\n" +
				"(Warning that 10 million test cases would take almost 3 minutes to complete.)");
		System.out.print("Enter a number of test cases (0 to exit): ");
		testCases = input.nextInt();
		System.out.println();
		while (testCases != 0) {
			System.out.println("Testing s = 6, r = 1 for " + testCases + " random angles.");
			tests(1, testCases, true);
			System.out.println("\nTesting s = 6, r = 1 for " + testCases + " systematic angles.");
			tests(1, testCases, false);
			System.out.println("\nTesting s = 6, r = 2 for " + testCases + " random angles.");
			tests(2, testCases, true);
			System.out.println("\nTesting s = 6, r = 2 for " + testCases + " systematic angles.");
			tests(2, testCases, false);
			System.out.println("\nHow many test cases would you like to do?\n" +
					"(Warning that 10 million test cases would take almost 3 minutes to complete.)");
			System.out.print("Enter a number of test cases (0 to exit): ");
			testCases = input.nextInt();
			System.out.println();
		}
		System.out.println("Thanks for grading the assignment!");
		input.close();
		return;
	}

}
