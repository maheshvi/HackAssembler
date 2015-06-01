package urp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionalCubeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleVarPositionalCube() {
		PositionalCube pc = new PositionalCube(1, PositionalCube.NORM_VAR);
		assertEquals(PositionalCube.NORM_VAR, pc.getVarValAtPos(1));

		pc.setVarValAtpos(1, PositionalCube.COMPLIMENT_VAR);
		assertEquals(PositionalCube.COMPLIMENT_VAR, pc.getVarValAtPos(1));

		pc.setVarValAtpos(1, PositionalCube.DONT_CARE_VAR);
		assertEquals(PositionalCube.DONT_CARE_VAR, pc.getVarValAtPos(1));

	}

	@Test
	public void testTwoVarPositionalCube() {
		long myExpVal;
		PositionalCube pc = new PositionalCube(2);
		pc.setVarValAtpos(1, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(2, PositionalCube.COMPLIMENT_VAR);
		myExpVal = (PositionalCube.COMPLIMENT_VAR << 2 | PositionalCube.NORM_VAR ) ;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(1, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(2, PositionalCube.NORM_VAR);
		myExpVal = PositionalCube.NORM_VAR << 2 | PositionalCube.NORM_VAR;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(1, PositionalCube.COMPLIMENT_VAR);
		pc.setVarValAtpos(2, PositionalCube.COMPLIMENT_VAR);
		myExpVal = PositionalCube.COMPLIMENT_VAR << 2
				| PositionalCube.COMPLIMENT_VAR;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(1, PositionalCube.DONT_CARE_VAR);
		pc.setVarValAtpos(2, PositionalCube.DONT_CARE_VAR);
		myExpVal = PositionalCube.DONT_CARE_VAR << 2
				| PositionalCube.DONT_CARE_VAR;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(1, PositionalCube.DONT_CARE_VAR);
		pc.setVarValAtpos(2, PositionalCube.NORM_VAR);
		myExpVal = PositionalCube.NORM_VAR << 2 | PositionalCube.DONT_CARE_VAR;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(1, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(2, PositionalCube.DONT_CARE_VAR);
		myExpVal = PositionalCube.DONT_CARE_VAR << 2 | PositionalCube.NORM_VAR;

		assertEquals(myExpVal, pc.getValue());

	}

	@Test
	public void testMultiVarPositionalCube() {
		long myExpVal;
		PositionalCube pc = new PositionalCube(5);
		pc.setVarValAtpos(2, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(5, PositionalCube.COMPLIMENT_VAR);
		myExpVal = 0b1011110111;

		assertEquals(myExpVal, pc.getValue());

		pc.setVarValAtpos(2, PositionalCube.COMPLIMENT_VAR);
		pc.setVarValAtpos(5, PositionalCube.COMPLIMENT_VAR);
		myExpVal = 0b1011111011;

		assertEquals(myExpVal, pc.getValue());
		
		pc.setVarValAtpos(2, PositionalCube.DONT_CARE_VAR);
		pc.setVarValAtpos(5, PositionalCube.NORM_VAR);
		myExpVal = 0b0111111111;

		assertEquals(myExpVal, pc.getValue());
	}

	@Test
	public void testParseValues() {
		long myExpVal;
		PositionalCube pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5   ");
		myExpVal = 0b1011110111;

		assertEquals(myExpVal, pc.getValue());

		pc.parsePositionalCube(" 3 2 -3   -5");
		myExpVal = 0b1011100111;

		assertEquals(myExpVal, pc.getValue());
		
		pc.parsePositionalCube("4   2 -3 4 -5");
		myExpVal = 0b1001100111;

		assertEquals(myExpVal, pc.getValue());
	}

	@Test
	public void testisUnit() {
		
	}

	@Test
	public void testGetPosCoFactor() {
		long myExpVal;
		
		PositionalCube pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5   ");
		PositionalCube myResPc = pc.getPosCoFactor(2);
		myExpVal = 0b1011111111;

		assertEquals(myExpVal, myResPc.getValue());

		myResPc = pc.getPosCoFactor(3);
		myExpVal = myResPc.getValue();
		assertEquals(myExpVal, pc.getValue());

		myResPc = pc.getPosCoFactor(5);
		assertNull(myResPc);
	}

	@Test
	public void testGetNegCoFactor() {
		long myExpVal;
		
		PositionalCube pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5   ");
		PositionalCube myResPc = pc.getNegCoFactor(5);
		myExpVal = 0b1111110111;

		assertEquals(myExpVal, myResPc.getValue());

		myResPc = pc.getNegCoFactor(3);
		myExpVal = myResPc.getValue();
		assertEquals(myExpVal, pc.getValue());

		myResPc = pc.getNegCoFactor(2);
		assertNull(myResPc);
	}

}
