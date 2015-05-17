package com.vibhor.hack.assembler;

public enum ComputeType {
	ZERO("0", "0101010"),
	UNITY("1","0111111"),
	NEG_UNITY("-1", "0111010"),
	D("D", "0001100"),
	A("A", "0110000"),
	M("M", "1110000"),
	NOT_D("!D", "0001101"),
	NOT_A("!A", "0110001"),
	NOT_M("!M", "1110001"),
	NEG_D("-D", "0001111"),
	NEG_A("-A", "0110011"),
	NEG_M("-M", "1110011"),
	D_PLUS("D+1","0011111"),
	A_PLUS("A+1", "0110111"),
	M_PLUS("M+1", "1110111"),
	D_MINUS("D-1", "0001110"),
	A_MINUS("A-1", "0110010"),
	M_MINUS("M-1", "1110010"),
	D_PLUS_A("D+A", "0000010"),
	D_PLUS_M("D+M", "1000010"),
	D_MINUS_A("D-A", "0010011"),
	D_MINUS_M("D-M", "1010011"),
	D_AND_A("D&A", "0000000"),
	D_AND_M("D&M", "1000000"),
	D_OR_A("D|A", "0010101"),
	D_OR_M("D|M", "1010101");
	
	private final String instrCode;
	private final String binaryCode;
	
	private ComputeType(String instrCode, String binaryCode) {
		this.instrCode = instrCode;
		this.binaryCode = binaryCode;
	}

	public String getInstrCode() {
		return instrCode;
	}

	public String getBinaryCode() {
		return binaryCode;
	}

	public ComputeType getComputeType(String aComputeStr){
		String myCleanStr = aComputeStr.replaceAll("\\s+", "").toUpperCase();
		for(ComputeType computeInst: ComputeType.values()){
			if (computeInst.getInstrCode().equals(myCleanStr)) return computeInst;
		}
		return null;
	}

}
