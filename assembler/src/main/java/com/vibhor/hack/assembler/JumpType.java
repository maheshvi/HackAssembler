package com.vibhor.hack.assembler;

public enum JumpType {

	JGT("JGT", "001"),
	JEQ("JEQ", "010"),
	JGE("JGE", "011"),
	JLT("JLT", "100"),
	JNE("JNE", "101"),
	JLE("JLE", "110"),
	JMP("JMP", "111"),
	NULL("", "000");

	private final String instrCode;
	private final String binaryCode;
	
	private JumpType(String instrCode, String binaryCode) {
		this.instrCode = instrCode;
		this.binaryCode = binaryCode;
	}

	public String getInstrCode() {
		return instrCode;
	}

	public String getBinaryCode() {
		return binaryCode;
	}
	
	public JumpType getDestType(String aJumpStr){
		String myCleanStr = aJumpStr.replaceAll("\\s+", "").toUpperCase();
		for(JumpType jumpInst: JumpType.values()){
			if (jumpInst.getInstrCode().equals(myCleanStr)) return jumpInst;
		}
		return NULL;
	}	
	
	
}
