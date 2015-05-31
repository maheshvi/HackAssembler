package com.vibhor.hack.assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Assembler extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int freeMemAddr = 16;
	private static HashMap<String, Integer> memAddrSymbolMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> instrAddrSymbolMap = new HashMap<String, Integer>();

	private static List<Instruction> instructionList = new ArrayList<>();

	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Assembly Files", "asm");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(
				"C:\\Vibhor's Dropbox\\Dropbox\\Personal\\Coursera\\nand2tetris\\projects\\06"));

		Assembler myAssembler = new Assembler();

		int returnVal = chooser.showOpenDialog(myAssembler);
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
		Parser myParser = new Parser();
		myAssembler.generateInstructions(br, myParser);
		
		String myFilePath = chooser.getSelectedFile().getAbsolutePath();
		myFilePath = myFilePath.substring(0, myFilePath.indexOf(".asm"))+".hack";
		try{
			File file = new File(myFilePath);
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		myAssembler.generateSymbolTables(myParser, bw);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public Assembler() {
//		super("File Chooser Test Frame");
//		setSize(350, 200);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void generateInstructions(BufferedReader aAssemblyFile,
			Parser aParser) {
		int myPrevLineNum = -1;
		Instruction myInstr;
		try {
			String line;

			line = aAssemblyFile.readLine();
			while (line != null) {
				myInstr = aParser.getInstruction(line, myPrevLineNum);
				if (myInstr != null) {
					myPrevLineNum = myInstr.getPrevLineNum();
					if (myInstr.getType() == InstructionType.LOOP_LABEL) {
						if (!instrAddrSymbolMap.containsKey(myInstr
								.getSymbolCode())) {
							instrAddrSymbolMap.put(myInstr.getSymbolCode(),
									myPrevLineNum + 1);
						}
					} else {
						instructionList.add(myInstr);
					}
					System.out.println("Instruction Type = ["
							+ myInstr.getType() + "] , Instruction = ["
							+ myInstr.getInstrCode() + "]");
				}
				line = aAssemblyFile.readLine();
			}
			aAssemblyFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateSymbolTables(Parser aParser, BufferedWriter bw) throws IOException {
		// Pre-fill all predefined symbols
		for (PreDefinedSymbolTypes symbolType : PreDefinedSymbolTypes.values()) {
			memAddrSymbolMap
					.put(symbolType.toString(), symbolType.getMemAddr());
		}

		String binaryCode;
		for (Instruction myInstr : instructionList) {
			if (myInstr.isContainingSymbol()) {
				if (!instrAddrSymbolMap.containsKey(myInstr.getSymbolCode())) {
					if (!memAddrSymbolMap.containsKey(myInstr.getSymbolCode()))
						memAddrSymbolMap.put(myInstr.getSymbolCode(),
								freeMemAddr++);
					binaryCode = aParser.parseInstruction(myInstr,
							memAddrSymbolMap.get(myInstr.getSymbolCode()));
				} else {
					binaryCode = aParser.parseInstruction(myInstr,
							instrAddrSymbolMap.get(myInstr.getSymbolCode()));
				}

			} else {
				binaryCode = aParser.parseInstruction(myInstr, -1);
			}
			System.out.println("Bin Code for [" + myInstr.getInstrCode()
					+ "]  ==> [" + binaryCode + "]");
			bw.write(binaryCode);
			bw.newLine();
		}
		
		bw.close();
	}

}
