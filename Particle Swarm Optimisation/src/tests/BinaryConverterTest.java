package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryConverterTest {
	
	private BinaryConverter classUnderTest = new BinaryConverter();
	
	@Test
	public void test1() {
		int[] binaryNumber = {1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,1,0};
		double expected = -1.76;
		double result = classUnderTest.convertIEEE754ToReal(binaryNumber);
		assertEquals(expected, result, 0.000001);
	}
	
	@Test
	public void test2(){
		int[] binaryNumber = {0,1,0,0,0,0,0,1,1,0,1,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,0,1,0,0,1,0};
		double expected = 23.4567;
		double result = classUnderTest.convertIEEE754ToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}
	
	@Test
	public void test3(){
		int[] binaryNumber = {0,1,0,0,0,0,0,1,0,0,0,0,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,0,1,1};
		double expected = 8.736354;
		double result = classUnderTest.convertIEEE754ToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}
	
	@Test
	public void test4(){
		int[] binaryNumber ={1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,0,0,0};
		double expected = -256.65674;
		double result = classUnderTest.convertIEEE754ToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}

}
