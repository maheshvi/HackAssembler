package com.vibhor.hack.assembler;

public class Parser {

	public Instruction getInstruction(String aInstrLine, int aPrevInstrNum){
		String myCleanedInstr = getCleanedInstr(aInstrLine);
		
		if (myCleanedInstr.isEmpty()) return null;
		
		char startChar = myCleanedInstr.charAt(0);
		InstructionType myInstrType;
		int myPrevInstrLineNum;
		
		if (startChar == '@') {myInstrType = InstructionType.A_INSTRUCTION; myPrevInstrLineNum = aPrevInstrNum + 1;}
		else if (startChar == '(') {myInstrType = InstructionType.LOOP_LABEL; myPrevInstrLineNum = aPrevInstrNum;}
		else {myInstrType = InstructionType.C_INSTRUCTION; myPrevInstrLineNum = aPrevInstrNum + 1;}
		
		return new Instruction(myInstrType, myPrevInstrLineNum, myCleanedInstr);
	}
	
	private String getCleanedInstr(String aInstrLine){
		String myRes;
		
		// Trim around for empty spaces
		myRes = aInstrLine.trim();
		if (myRes.isEmpty()) return "";
		
		// Remove code after "//" 
		myRes = myRes.substring(0, myRes.indexOf("//")).trim();
		if (myRes.isEmpty()) return "";
		
		// Remove empty spaces in between 
		myRes = myRes.replaceAll("\\s+", "");
		return myRes;
	}
	
	

}
