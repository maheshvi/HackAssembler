package com.vibhor.hack.assembler;

public class Instruction {

	private final InstructionType type;
	private final int prevLineNum;
	private final String instrCode;
	private final boolean containsSymbol;
	private final String symbolCode;
	
	public Instruction(InstructionType type, int instrLineNum, String instrCode) {
		this.type = type;
		this.prevLineNum = instrLineNum;
		this.instrCode = instrCode;
		this.containsSymbol = hasSymbol();
		if (this.containsSymbol) this.symbolCode = this.getSymbol(); 
		else this.symbolCode = "";
	}

	private boolean hasSymbol(){
		switch(type){
		case C_INSTRUCTION:
			return false;
		case LOOP_LABEL:
			return true;
		case A_INSTRUCTION:
			return !(checkAInstrForSymbol() == null);
			default:
				return false;
		}
	}
	
	private String checkAInstrForSymbol(){
		String selSubStr = this.instrCode.substring(1);
		if (selSubStr.matches("\\b\\d+\\b")) return null;
		else return selSubStr;
	}
	
	private String getSymbol(){
		if (!hasSymbol()) return null;
		switch(type){
		case LOOP_LABEL:
			return getLoopLabel();
		case A_INSTRUCTION:
			return checkAInstrForSymbol();
		default:
			return null;
		}
	}
	
	
	private String getLoopLabel(){
		return instrCode.substring(instrCode.indexOf("(")+1,instrCode.indexOf(")"));
	}
	
	public boolean isContainingSymbol(){
		return this.containsSymbol;
	}

	public InstructionType getType() {
		return type;
	}

	public int getPrevLineNum() {
		return prevLineNum;
	}

	public String getInstrCode() {
		return instrCode;
	}

	public String getSymbolCode() {
		return symbolCode;
	}
	
	
	
}
