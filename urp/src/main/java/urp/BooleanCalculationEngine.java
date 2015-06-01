/**
 * 
 */
package urp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Vibhor Maheshwari
 *
 */
public class BooleanCalculationEngine extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String FOLDER_PATH = "E:\\ProgrammingAssignment1Files\\BooleanCalculatorEngine";
	private PositionalCubeList[] cubeListArr = new PositionalCubeList[20];

	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Boolean Calc Files", "txt");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(FOLDER_PATH));

		BooleanCalculationEngine myBooleanCalcEngine = new BooleanCalculationEngine();

		int returnVal = chooser.showOpenDialog(myBooleanCalcEngine);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());
		}

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(chooser
					.getSelectedFile().getAbsolutePath())));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to read file");
			e.printStackTrace();
			return;
		}

		myBooleanCalcEngine.parseBooleanCommands(br);
	}

	public void parseBooleanCommands(BufferedReader aCommandFile) {

		try {
			String line;
			int[] myData;

			line = aCommandFile.readLine();
			while (line != null) {
				if (!line.trim().isEmpty()) {
					myData = parseLine(line);
					switch (myData[0]) {
					case 1:
						cubeListArr[myData[1]] = PositionalCubeList
								.parsePcnFile(FOLDER_PATH + "\\" + myData[1]
										+ ".pcn");
						break;
					case 2:
						cubeListArr[myData[1]] = cubeListArr[myData[2]]
								.invert();
						break;

					case 3:
						cubeListArr[myData[1]] = cubeListArr[myData[2]]
								.and(cubeListArr[myData[3]]);
						break;
					case 4:
						cubeListArr[myData[1]] = cubeListArr[myData[2]]
								.or(cubeListArr[myData[3]]);
						break;
					case 5:
						cubeListArr[myData[1]].writeToFile(FOLDER_PATH + "\\"
								+ myData[1] + ".pcn");
						break;
					case 6:
						aCommandFile.close();
						return;
					default:
					}
					System.out.println(line);
					for(int k= 0; k < 4; k++)
						System.out.print(myData[k] + " ");
					System.out.println();
				}
				line = aCommandFile.readLine();
			}

			aCommandFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int[] parseLine(String aStr2Parse) {
		int[] myRes = new int[4];

		String myCleanStr = cleanStr2Parse(aStr2Parse);

		if (myCleanStr.isEmpty()) {
			return myRes;
		}

		String[] mySplitStr = myCleanStr.split("\\s+");
		int myCommandNum;
		switch (mySplitStr[0]) {
		case "r":
			myCommandNum = 1;
			break;
		case "!":
			myCommandNum = 2;
			break;
		case "&":
			myCommandNum = 3;
			break;
		case "+":
			myCommandNum = 4;
			break;
		case "p":
			myCommandNum = 5;
			break;
		case "q":
			myCommandNum = 6;
			break;
		default:
			myCommandNum = 0;
		}
		myRes[0] = myCommandNum;

		for (int k = 1; k < mySplitStr.length; k++) {
			myRes[k] = Integer.parseInt(mySplitStr[k]);
		}
		return myRes;
	}

	private String cleanStr2Parse(String dirtyStr2Parse) {
		return dirtyStr2Parse.trim().replaceAll(" +", " ");
	}

}
