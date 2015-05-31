package urp;

public class PositionalCube {

	public static final byte NORM_VAR = 1;
	public static final byte COMPLIMENT_VAR = 2;
	public static final byte DONT_CARE_VAR = 3;
	public static final byte COVER_VAR = 3;
	public static final byte INVALID_VAR = 0;
	
	private final int varCount;
	private final long mask;
	private long value;
	
	public PositionalCube(int varCount) {
		this.varCount = varCount;
		this.mask = (1 << (2*varCount)) -1;
		this.value = this.mask;
	}
	
	public PositionalCube(int varCount, long aValue) {
		this.varCount = varCount;
		this.mask = (1 << (2*varCount)) -1;
		this.value = aValue;
	}
	
	public void parsePositionalCube(String aStr2Parse){
		String myCleanStr = cleanStr2Parse(aStr2Parse);
		
		if (myCleanStr.isEmpty()) {
			this.value = this.mask;
			return;
		}
		
		String[] mySplitStr = myCleanStr.split("\\s+");
		int myVarUsed = Integer.parseInt(mySplitStr[0]);
		
		if (myVarUsed <= 0){
			this.value = this.mask;
			return;
		}
		
		int[] myProdVars = new int[myVarUsed];
		
		for(int k = 0 ;k < myVarUsed; k++){
			myProdVars[k] = Integer.parseInt(mySplitStr[k+1]);
		}
		this.value = getPositionalCube(myProdVars);
	}
	
	private String cleanStr2Parse(String dirtyStr2Parse){
		return dirtyStr2Parse.trim().replaceAll(" +", " ");
	}
	
	private long getPositionalCube(int[] aProdVars){
		long myRes = mask;
		int myLen = aProdVars.length, myCubeVal;
		
		if (myLen > 0){
			for(int k = 0; k<myLen ; k++){
				myCubeVal = Math.abs(aProdVars[k]);
				if (Math.signum(aProdVars[k]) > 0){
					myRes = (myRes & ~(COVER_VAR << 2*(myCubeVal-1))) | (NORM_VAR << (2*(myCubeVal-1)));
				}else{
					myRes = (myRes & ~(COVER_VAR << 2*(myCubeVal-1))) | (COMPLIMENT_VAR << (2*(myCubeVal-1)));
				}
			}
		}
			
		return myRes;
	}
	
	public byte getVarValAtPos(int aVarNum){
		return (byte) ((value >> (2*(aVarNum-1))) & (long)COVER_VAR);
	}
	
	public void setVarValAtpos(int aVarNum, byte aVal){
		long myMadeMask = (~((long)COVER_VAR << (2*(aVarNum -1))) & mask);
		long myVal2Or = (aVal << (2*(aVarNum - 1)));
		value = value & myMadeMask;
		value = value | myVal2Or;
	}
	
	public PositionalCube getPosCoFactor(int aVarNum){
		
		if (aVarNum > this.varCount) return null;
		byte myVal = this.getVarValAtPos(aVarNum);
		PositionalCube myRes = new PositionalCube(this.varCount, this.value);
		if (myVal == COMPLIMENT_VAR) {
			return null;
		}else if (myVal == NORM_VAR){
			myRes.setVarValAtpos(aVarNum, (byte)DONT_CARE_VAR);
		}
		
		return myRes;
	}
	
	public PositionalCube getNegCoFactor(int aVarNum){
		
		if (aVarNum > this.varCount) return null;
		byte myVal = this.getVarValAtPos(aVarNum);
		PositionalCube myRes = new PositionalCube(this.varCount, this.value);
		if (myVal == NORM_VAR) {
			return null;
		}else if (myVal == COMPLIMENT_VAR){
			myRes.setVarValAtpos(aVarNum, (byte)DONT_CARE_VAR);
		}
		
		return myRes;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public int getVarCount() {
		return varCount;
	}

	public long getValue() {
		return value;
	}
	
	public boolean isUnit(){
		return (mask & value) == mask;
	}
	
	public boolean isSingleVarOnly(){
		return Long.bitCount(~value & mask) == 1;
	}
	
	public int getSingleVarNum(){
		if (isSingleVarOnly()){
			String myBinStr = Long.toBinaryString(~value & mask);
			return (int)Math.ceil((myBinStr.length() - myBinStr.indexOf('1') + 1)/2);
		}
		return 0;
	}
	
	public int getSingleVarSignedNum(){
		if (isSingleVarOnly()){
			int myPosition = getSingleVarNum();
			byte myVal = getVarValAtPos(myPosition);
			if (myVal == COMPLIMENT_VAR) return -myPosition;
			else return myPosition;
		}
		return 0;
	}
	
	
	
}
