package com.vibhor.hack.assembler;

public enum DestType {

	M("M", "001"),
	D("D", "010"),
	MD("MD", "011"),
	A("A", "100"),
	AM("AM", "101"),
	AD("AD", "110"),
	AMD("AMD", "111"),
	NULL("", "000");

	private final String instrCode;
	private final String binaryCode;
	
	private DestType(String instrCode, String binaryCode) {
		this.instrCode = instrCode;
		this.binaryCode = binaryCode;
	}
	
	public String getInstrCode() {
		return instrCode;
	}
	
	public String getBinaryCode() {
		return binaryCode;
	}
	
	public static DestType getDestType(String aDestStr){
		String myCleanStr = aDestStr.replaceAll("\\s+", "").toUpperCase();
		for(DestType destInst: DestType.values()){
			if (destInst.getInstrCode().equals(myCleanStr)) return destInst;
		}
		return NULL;
	}

	
	

}
