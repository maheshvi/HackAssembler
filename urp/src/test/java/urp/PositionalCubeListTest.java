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
	public void testTautology() {
		PositionalCubeList list = new PositionalCubeList(3);
		list.insertPositionalCube(new PositionalCube(3, 5));
		list.insertPositionalCube(new PositionalCube(3, 17));
		list.insertPositionalCube(new PositionalCube(3, 0b101001));
		list.insertPositionalCube(new PositionalCube(3, 2));
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
		
//		System.out.println(list);
//		System.out.println(" == INVERSION ==");
//		System.out.println(list.invert());
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
