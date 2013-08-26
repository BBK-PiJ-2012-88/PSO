package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swarm.BinaryConverter;
import swarm.BinaryConverterImpl;

public class BinaryConverterTest {
	
	private BinaryConverter classUnderTest = new BinaryConverterImpl();
	
	@Test
	public void test1() {
		double[] binaryNumber = {1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,1,0};
		double expected = -1.76;
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(expected, result, 0.000001);
	}
	
	@Test
	public void test2(){
		double[] binaryNumber = {0,1,0,0,0,0,0,1,1,0,1,1,1,0,1,1,1,0,1,0,0,1,1,1,0,1,0,1,0,0,1,0};
		double expected = 23.4567;
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}
	
	@Test
	public void test3(){
		double[] binaryNumber = {0,1,0,0,0,0,0,1,0,0,0,0,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,0,1,1};
		double expected = 8.736354;
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}
	
	@Test
	public void test4(){
		double[] binaryNumber ={1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,0,0,0};
		double expected = -256.65674;
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(expected, result, 0.00001);
	}
	
	@Test
	public void testConvertDecimalToBinary1(){
		double[] binaryNumber = classUnderTest.convertRealToBinary(123.76373);
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(123.76373, result, 0.00001);
	}
	
	@Test
	public void testConvertDecimalToBinary2(){
		double[] binaryNumber = classUnderTest.convertRealToBinary(0);
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(0, result, 0.00001);
	}
	
	@Test
	public void testConvertDecimalToBinary3(){
		double[] binaryNumber = classUnderTest.convertRealToBinary(372367.75);
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(372367.75, result, 0.00001);
	}
	
	@Test
	public void testConvertDecimalToBinary4(){
		double[] binaryNumber = classUnderTest.convertRealToBinary(-323.45453);
		double result = classUnderTest.convertBinaryToReal(binaryNumber);
		assertEquals(-323.45453, result, 0.00001);
	}

}
