package com.vibhor.hack.assembler;

public class Instruction {

	private InstructionType type;
	private int prevLineNum = -1;
	private final String instrCode;
	
	public Instruction(InstructionType type, int instrLineNum, String instrCode) {
		this.type = type;
		this.prevLineNum = instrLineNum;
		this.instrCode = instrCode;
	}

	public boolean hasSymbol(){
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
	
	public String getSymbol(){
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
}
