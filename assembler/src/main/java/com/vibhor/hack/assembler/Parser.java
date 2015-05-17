package com.vibhor.hack.assembler;

import org.apache.commons.lang3.StringUtils;

public class Parser {

	public Instruction getInstruction(String aInstrLine, int aPrevInstrNum) {
		String myCleanedInstr = getCleanedInstr(aInstrLine);

		if (myCleanedInstr.isEmpty())
			return null;

		char startChar = myCleanedInstr.charAt(0);
		InstructionType myInstrType;
		int myPrevInstrLineNum;

		if (startChar == '@') {
			myInstrType = InstructionType.A_INSTRUCTION;
			myPrevInstrLineNum = aPrevInstrNum + 1;
		} else if (startChar == '(') {
			myInstrType = InstructionType.LOOP_LABEL;
			myPrevInstrLineNum = aPrevInstrNum;
		} else {
			myInstrType = InstructionType.C_INSTRUCTION;
			myPrevInstrLineNum = aPrevInstrNum + 1;
		}

		return new Instruction(myInstrType, myPrevInstrLineNum, myCleanedInstr);
	}

	private String getCleanedInstr(String aInstrLine) {
		String myRes;

		// Trim around for empty spaces
		myRes = aInstrLine.trim();
		if (myRes.isEmpty())
			return "";

		// Remove code after "//"
		if (myRes.contains("//")) {
			myRes = myRes.substring(0, myRes.indexOf("//")).trim();
			if (myRes.isEmpty())
				return "";
		}
		// Remove empty spaces in between
		myRes = myRes.replaceAll("\\s+", "");
		return myRes;
	}

	public String parseInstruction(Instruction aInstruct, int aSymbolValue) {

		switch (aInstruct.getType()) {
		case A_INSTRUCTION:
			return parseAInstruction(aInstruct, aSymbolValue);
		case C_INSTRUCTION:
			return parseCInstruction(aInstruct);
		default:
			return "";
		}
	}

	private String parseAInstruction(Instruction aInstruct, int aSymbolValue) {
		int myNum;
		if (aInstruct.isContainingSymbol())
			myNum = aSymbolValue;
		else
			myNum = Integer.parseInt(aInstruct.getInstrCode().substring(1));
		return StringUtils.leftPad(Integer.toBinaryString(myNum), 16, "0");
	}

	private String parseCInstruction(Instruction aInstruct) {
		String myCInstr = aInstruct.getInstrCode();
		String myJmpInstr = "", myDestInstr = "", myCompInstr = "";
		// does it contains semi-colon
		if (myCInstr.contains(";")) {
			myJmpInstr = myCInstr.substring(myCInstr.indexOf(';') + 1);
			myCInstr = myCInstr.substring(0, myCInstr.indexOf(';'));
		}
		if (myCInstr.contains("=")) {
			myCompInstr = myCInstr.substring(myCInstr.indexOf('=') + 1);
			myDestInstr = myCInstr.substring(0, myCInstr.indexOf('='));
		} else {
			myCompInstr = myCInstr;
		}

		return "111" + ComputeType.getComputeType(myCompInstr).getBinaryCode()
				+ DestType.getDestType(myDestInstr).getBinaryCode()
				+ JumpType.getJumpType(myJmpInstr).getBinaryCode();
	}

}
