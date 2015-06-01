/**
 * 
 */
package urp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * @author Vibhor Maheshwari
 *
 */
public class SolveUrpInverse extends JFrame  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Positional Cube Files", "pcn");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(
				"E:\\ProgrammingAssignment1Files\\UnateRecursiveComplement"));
		
		SolveUrpInverse mySolveUrpInverse = new SolveUrpInverse();
		
		int returnVal = chooser.showOpenDialog(mySolveUrpInverse);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());
		}

		// create instructions out of the file
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(chooser
					.getSelectedFile().getAbsolutePath())));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to read file");
			e.printStackTrace();
			return;
		}
		
		PositionalCubeList myInpCubeList = mySolveUrpInverse.parsePcnFile(br);
		
		PositionalCubeList myInvertedCubeList = myInpCubeList.invert();
		System.out.println(myInpCubeList);
		System.out.println(" == INVERSION ==");
		System.out.println(myInvertedCubeList);		
		
		
		String myFilePath = chooser.getSelectedFile().getAbsolutePath();
		myFilePath = myFilePath.substring(0, myFilePath.indexOf(".pcn"))+".ans";
		try{
			File file = new File(myFilePath);
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(myInvertedCubeList.toString());
		bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}		
		
		
	}
	
	public PositionalCubeList parsePcnFile(BufferedReader aPcnFile){
		PositionalCubeList myRes = null;
		try {
			String line;

			line = aPcnFile.readLine();
			if ((line == null) || (line.isEmpty())) return null;
			int myVarCount = Integer.parseInt(cleanStr2Parse(line));
			myRes = new PositionalCubeList(myVarCount);

			line = aPcnFile.readLine();
			if ((line == null) || (line.isEmpty())) return myRes;
			long myCubeCount = Long.parseLong(cleanStr2Parse(line));
			PositionalCube pc;
			for(long k = 0; k < myCubeCount; k++){ 
				line = aPcnFile.readLine();
				pc = new PositionalCube(myVarCount);
				pc.parsePositionalCube(line);
				myRes.insertPositionalCube(pc);
			}
			aPcnFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myRes;
	}
	
	private String cleanStr2Parse(String dirtyStr2Parse) {
		return dirtyStr2Parse.trim().replaceAll(" +", " ");
	}	

}
