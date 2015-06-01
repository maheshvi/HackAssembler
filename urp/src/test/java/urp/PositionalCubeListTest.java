package urp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionalCubeListTest {

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
	public void testGetPosCoFactor() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));
		
		PositionalCubeList myExpRes = new PositionalCubeList(3);
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b111101));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b011101));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b111110));		
		assertEquals(myExpRes, list.getPosCoFactor(2));

		myExpRes = new PositionalCubeList(3);
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b110111));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b011111));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b101011));		
		assertEquals(myExpRes, list.getPosCoFactor(1));
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110110));
		list.insertPositionalCube(new PositionalCube(3, 0b011110));
		list.insertPositionalCube(new PositionalCube(3, 0b101010));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));		
		
		myExpRes = new PositionalCubeList(3);
		assertEquals(myExpRes, list.getPosCoFactor(1));
	}	
	
	@Test
	public void testGetNegCoFactor() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));
		
		PositionalCubeList myExpRes = new PositionalCubeList(3);
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b011101));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b101101));
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b111110));		
		assertEquals(myExpRes, list.getNegCoFactor(2));

		myExpRes = new PositionalCubeList(3);
		myExpRes.insertPositionalCube(new PositionalCube(3, 0b111111));
		assertEquals(myExpRes, list.getNegCoFactor(1));
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));		
		
		myExpRes = new PositionalCubeList(3);
		assertEquals(myExpRes, list.getNegCoFactor(1));
	}	

	@Test
	public void testIsUnate() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));
		assertFalse(list.isUnate());

		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertFalse(list.isUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertFalse(list.isUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b100101));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertTrue(list.isUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b100111));
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		assertTrue(list.isUnate());		
	}	
	
	@Test
	public void testHasUnitPositionalCube() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));
		assertFalse(list.hasUnitPositionalCube());

		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertFalse(list.hasUnitPositionalCube());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertFalse(list.hasUnitPositionalCube());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b100101));
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		assertTrue(list.hasUnitPositionalCube());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b100111));
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		assertTrue(list.hasUnitPositionalCube());		
	}		

	@Test
	public void testMostBinateVarNum() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111110));
		assertEquals(1, list.getMostBinateVarNum());

		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b011101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertEquals(2, list.getMostBinateVarNum());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b010101));
		list.insertPositionalCube(new PositionalCube(3, 0b111101));
		assertEquals(3, list.getMostBinateVarNum());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b110101));
		list.insertPositionalCube(new PositionalCube(3, 0b101101));
		list.insertPositionalCube(new PositionalCube(3, 0b100101));
		list.insertPositionalCube(new PositionalCube(3, 0b111111));
		assertEquals(0, list.getMostBinateVarNum());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b100101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b011010));
		assertEquals(1, list.getMostBinateVarNum());	
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b100101));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 0b011001));
		list.insertPositionalCube(new PositionalCube(3, 0b011010));
		assertEquals(3, list.getMostBinateVarNum());		
	}	
	
	@Test
	public void testMostSpreadUnate() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_10));
		assertEquals(0, list.getMostSpreadUnate());

		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_01));
		assertEquals(1, list.getMostSpreadUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_11));
		list.insertPositionalCube(new PositionalCube(3, 0b01_10_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_01));
		assertEquals(1, list.getMostSpreadUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_11));
		list.insertPositionalCube(new PositionalCube(3, 0b10_11_11));
		list.insertPositionalCube(new PositionalCube(3, 0b10_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_11));
		assertEquals(2, list.getMostSpreadUnate());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b10_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_10_10));
		assertEquals(0, list.getMostSpreadUnate());	
	}
	
	@Test
	public void testIsComplementAdditiveSatisfied() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_10));
		assertFalse(list.isComplementAdditiveSatisfied());

		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_01));
		assertFalse(list.isComplementAdditiveSatisfied());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_11));
		list.insertPositionalCube(new PositionalCube(3, 0b01_10_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_01));
		assertFalse(list.isComplementAdditiveSatisfied());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_11));
		list.insertPositionalCube(new PositionalCube(3, 0b10_11_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_11));
		assertFalse(list.isComplementAdditiveSatisfied());
		
		list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_01_11));
		list.insertPositionalCube(new PositionalCube(3, 0b10_11_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_10_11));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_11));
		assertTrue(list.isComplementAdditiveSatisfied());
	}	
	
	@Test
	public void testTautology() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b01_11_01));
		list.insertPositionalCube(new PositionalCube(3, 0b10_10_01));
		list.insertPositionalCube(new PositionalCube(3, 0b11_11_10));
		System.out.println(list);
		assertTrue(list.isTautology());
	}

	@Test
	public void testInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
		pc = new PositionalCube(5);
		pc.parsePositionalCube("3 2 3 4");
		list.insertPositionalCube(pc);

		pc = new PositionalCube(5);
		pc.parsePositionalCube("2 -1 5");
		list.insertPositionalCube(pc);

		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -3 -4");
		list.insertPositionalCube(pc);
		
		System.out.println(list);
		System.out.println(" == INVERSION ==");
		System.out.println(list.invert());
	}
	
	@Test
	public void testSingleSumInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 1");
		list.insertPositionalCube(pc);

		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 3");
		list.insertPositionalCube(pc);

//		pc = new PositionalCube(5);
//		pc.parsePositionalCube("1 -3 -4");
//		list.insertPositionalCube(pc);
		
		System.out.println(list);
		System.out.println(" == INVERSION ==");
		System.out.println(list.invert());
	}

	@Test
	public void testSingleProductInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
		pc = new PositionalCube(5);
		pc.parsePositionalCube("3 2 3 4");
		list.insertPositionalCube(pc);

		PositionalCubeList resList = new PositionalCubeList(5);
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -2");
		resList.insertPositionalCube(pc);
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -3");
		resList.insertPositionalCube(pc);
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -4");
		resList.insertPositionalCube(pc);
		
//		System.out.println(list);
//		System.out.println(" == INVERSION ==");
//		System.out.println(list.invert());
		
		assertEquals(resList, list.invert());
	}
	
	@Test
	public void testSingleLiteralInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 3");
		list.insertPositionalCube(pc);

		PositionalCubeList resList = new PositionalCubeList(5);
		pc = new PositionalCube(5);
		pc.parsePositionalCube("1 -3");
		resList.insertPositionalCube(pc);
		
//		System.out.println(list);
//		System.out.println(" == INVERSION ==");
//		System.out.println(list.invert());
		
		assertEquals(resList, list.invert());
	}
	
	@Test
	public void testEmptyListInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
//		pc = new PositionalCube(5);
//		pc.parsePositionalCube("1 3");
//		list.insertPositionalCube(pc);

		PositionalCubeList resList = new PositionalCubeList(5);
		pc = new PositionalCube(5);
//		pc.parsePositionalCube("1 -3");
		resList.insertPositionalCube(pc);
		
//		System.out.println(list);
//		System.out.println(" == INVERSION ==");
//		System.out.println(list.invert());
		
		assertEquals(resList, list.invert());
	}
	
	@Test
	public void testAllDontCareInversion() {
		PositionalCubeList list = new PositionalCubeList(5);
		PositionalCube pc;
		pc = new PositionalCube(5);
//		pc.parsePositionalCube("1 3");
		list.insertPositionalCube(pc);

		PositionalCubeList resList = new PositionalCubeList(5);
//		pc = new PositionalCube(5);
////		pc.parsePositionalCube("1 -3");
//		resList.insertPositionalCube(pc);
		
//		System.out.println(list);
//		System.out.println(" == INVERSION ==");
//		System.out.println(list.invert());
		
		assertEquals(resList, list.invert());
	}
}
