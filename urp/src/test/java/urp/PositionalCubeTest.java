package urp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	public void testIsUnit() {
		PositionalCube pc = new PositionalCube(5);
		assertTrue(pc.isUnit());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5");
		assertFalse(pc.isUnit());
		
		pc = new PositionalCube(5);
		pc.setVarValAtpos(1, PositionalCube.DONT_CARE_VAR);
		pc.setVarValAtpos(2, PositionalCube.DONT_CARE_VAR);
		pc.setVarValAtpos(4, PositionalCube.DONT_CARE_VAR);
		assertTrue(pc.isUnit());
		
	}
	
	@Test
	public void testIsSingleVar() {
		PositionalCube pc = new PositionalCube(5);
		assertFalse(pc.isSingleVarOnly());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 2");
		assertTrue(pc.isSingleVarOnly());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -4");
		assertTrue(pc.isSingleVarOnly());
		
		pc = new PositionalCube(5);
		pc.setVarValAtpos(1, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(5, PositionalCube.COMPLIMENT_VAR);
		assertFalse(pc.isSingleVarOnly());
		
	}
	
	@Test
	public void testGetSingleVarSignedNum() {
		PositionalCube pc = new PositionalCube(5);
		assertEquals(0, pc.getSingleVarSignedNum());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 2");
		assertEquals(2, pc.getSingleVarSignedNum());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -4");
		assertEquals(-4, pc.getSingleVarSignedNum());
		
		pc = new PositionalCube(5);
		pc.setVarValAtpos(1, PositionalCube.NORM_VAR);
		pc.setVarValAtpos(5, PositionalCube.COMPLIMENT_VAR);
		assertEquals(0, pc.getSingleVarSignedNum());
		
	}	
	
	@Test
	public void testAnd() {
		PositionalCube pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5   ");

		PositionalCube myRes = pc.and(3, true);
		PositionalCube myExpRes = new PositionalCube(5);
		myExpRes.parsePositionalCube("3 2 3 -5");
		assertEquals(myExpRes, myRes);

		myRes = pc.and(3, false);
		myExpRes = new PositionalCube(5);
		myExpRes.parsePositionalCube("3 2 -3 -5");
		assertEquals(myExpRes, myRes);
		
		myRes = pc.and(2, false);
		assertEquals(null, myRes);
		
		myRes = pc.and(2, true);
		myExpRes = new PositionalCube(5);
		myExpRes.parsePositionalCube("2 2 -5");
		assertEquals(myExpRes, myRes);
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

	@Test
	public void testInvert() {
		PositionalCube pc = new PositionalCube(5);
		assertEquals(null, pc.invert());
		
		PositionalCube p1,p2,p3;
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("2 2 -5   ");		
		PositionalCubeList myExpRes = new PositionalCubeList(5);
		p1 = new PositionalCube(5);
		p1.parsePositionalCube("1 -2");
		p2 = new PositionalCube(5);
		p2.parsePositionalCube("1 5");
		myExpRes.insertPositionalCube(p1);
		myExpRes.insertPositionalCube(p2);
		assertEquals(myExpRes, pc.invert());
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 3");		
		myExpRes = new PositionalCubeList(5);
		p1 = new PositionalCube(5);
		p1.parsePositionalCube("1 -3");
		myExpRes.insertPositionalCube(p1);
		assertEquals(myExpRes, pc.invert());	
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -4");		
		myExpRes = new PositionalCubeList(5);
		p1 = new PositionalCube(5);
		p1.parsePositionalCube("1 4");
		myExpRes.insertPositionalCube(p1);
		assertEquals(myExpRes, pc.invert());		
		
		pc = new PositionalCube(5);
		pc.parsePositionalCube("3 2 -4 -5");		
		myExpRes = new PositionalCubeList(5);
		p1 = new PositionalCube(5);
		p1.parsePositionalCube("1 -2");
		p2 = new PositionalCube(5);
		p2.parsePositionalCube("1 4");
		p3 = new PositionalCube(5);
		p3.parsePositionalCube("1 5");
		myExpRes.insertPositionalCube(p1);
		myExpRes.insertPositionalCube(p2);
		myExpRes.insertPositionalCube(p3);
		assertEquals(myExpRes, pc.invert());		
		
	}		
	
}
