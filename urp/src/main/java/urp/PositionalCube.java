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
		this.mask = (1 << (2 * varCount)) - 1;
		this.value = this.mask;
	}

	public PositionalCube(int varCount, long aValue) {
		this.varCount = varCount;
		this.mask = (1 << (2 * varCount)) - 1;
		this.value = aValue;
	}

	public void parsePositionalCube(String aStr2Parse) {
		String myCleanStr = cleanStr2Parse(aStr2Parse);

		if (myCleanStr.isEmpty()) {
			this.value = this.mask;
			return;
		}

		String[] mySplitStr = myCleanStr.split("\\s+");
		int myVarUsed = Integer.parseInt(mySplitStr[0]);

		if (myVarUsed <= 0) {
			this.value = this.mask;
			return;
		}

		int[] myProdVars = new int[myVarUsed];

		for (int k = 0; k < myVarUsed; k++) {
			myProdVars[k] = Integer.parseInt(mySplitStr[k + 1]);
		}
		this.value = getPositionalCube(myProdVars);
	}

	private String cleanStr2Parse(String dirtyStr2Parse) {
		return dirtyStr2Parse.trim().replaceAll(" +", " ");
	}

	private long getPositionalCube(int[] aProdVars) {
		long myRes = mask;
		int myLen = aProdVars.length, myCubeVal;

		if (myLen > 0) {
			for (int k = 0; k < myLen; k++) {
				myCubeVal = Math.abs(aProdVars[k]);
				if (Math.signum(aProdVars[k]) > 0) {
					myRes = (myRes & ~(COVER_VAR << 2 * (myCubeVal - 1)))
							| (NORM_VAR << (2 * (myCubeVal - 1)));
				} else {
					myRes = (myRes & ~(COVER_VAR << 2 * (myCubeVal - 1)))
							| (COMPLIMENT_VAR << (2 * (myCubeVal - 1)));
				}
			}
		}

		return myRes;
	}

	public byte getVarValAtPos(int aVarNum) {
		return (byte) ((value >> (2 * (aVarNum - 1))) & (long) COVER_VAR);
	}

	public void setVarValAtpos(int aVarNum, byte aVal) {
		long myMadeMask = (~((long) COVER_VAR << (2 * (aVarNum - 1))) & mask);
		long myVal2Or = (aVal << (2 * (aVarNum - 1)));
		value = value & myMadeMask;
		value = value | myVal2Or;
	}

	public PositionalCube getPosCoFactor(int aVarNum) {

		if (aVarNum > this.varCount)
			return null;
		byte myVal = this.getVarValAtPos(aVarNum);
		PositionalCube myRes = new PositionalCube(this.varCount, this.value);
		if (myVal == COMPLIMENT_VAR) {
			return null;
		} else if (myVal == NORM_VAR) {
			myRes.setVarValAtpos(aVarNum, (byte) DONT_CARE_VAR);
		}

		return myRes;
	}

	public PositionalCube getNegCoFactor(int aVarNum) {

		if (aVarNum > this.varCount)
			return null;
		byte myVal = this.getVarValAtPos(aVarNum);
		PositionalCube myRes = new PositionalCube(this.varCount, this.value);
		if (myVal == NORM_VAR) {
			return null;
		} else if (myVal == COMPLIMENT_VAR) {
			myRes.setVarValAtpos(aVarNum, (byte) DONT_CARE_VAR);
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

	public boolean isUnit() {
		return (mask & value) == mask;
	}

	public boolean isSingleVarOnly() {
		return Long.bitCount(~value & mask) == 1;
	}

	public int getSingleVarNum() {
		if (isSingleVarOnly()) {
			String myBinStr = Long.toBinaryString(~value & mask);
			return (int) Math
					.ceil((myBinStr.length() - myBinStr.indexOf('1') + 1) / 2);
		}
		return 0;
	}

	public int getSingleVarSignedNum() {
		if (isSingleVarOnly()) {
			int myPosition = getSingleVarNum();
			byte myVal = getVarValAtPos(myPosition);
			if (myVal == COMPLIMENT_VAR)
				return -myPosition;
			else
				return myPosition;
		}
		return 0;
	}

	public PositionalCube and(int aVarNum, boolean aVarValue) {
		byte myValAtPos = getVarValAtPos(aVarNum);
		PositionalCube myPossiblePosCube = new PositionalCube(varCount, value);
		if (aVarValue) {
			if (myValAtPos == COMPLIMENT_VAR)
				return null;
			else {
				myPossiblePosCube.setVarValAtpos(aVarNum, NORM_VAR);
				return myPossiblePosCube;
			}
		} else {
			if (myValAtPos == NORM_VAR)
				return null;
			else {
				myPossiblePosCube.setVarValAtpos(aVarNum, COMPLIMENT_VAR);
				return myPossiblePosCube;
			}
		}
	}

	public PositionalCubeList invert() {
		if (isUnit())
			return null;
		else {
			PositionalCubeList pcl = new PositionalCubeList(varCount);
			for (int k = 0; k < varCount; k++) {
				byte myVarValAtPos = getVarValAtPos(k + 1);
				PositionalCube myInsertedPosCube = new PositionalCube(varCount);
				if (myVarValAtPos != DONT_CARE_VAR) {
					if (myVarValAtPos == NORM_VAR)
						myInsertedPosCube.setVarValAtpos(k + 1, COMPLIMENT_VAR);
					else
						myInsertedPosCube.setVarValAtpos(k + 1, NORM_VAR);
					pcl.insertPositionalCube(myInsertedPosCube);
				}
			}
			return pcl;
		}
	}
	
	

	@Override
	public String toString() {
		String myRes = ""; //varCount + " ";
		byte myValValue;
		int myCount= 0;
		for (int k = 0; k <varCount ; k++){
			myValValue = getVarValAtPos(k+1);
			
			if (myValValue == NORM_VAR) {
				myRes = myRes + (k+1) + " ";
				myCount++;
			}
			else if (myValValue == COMPLIMENT_VAR){
				myRes = myRes + "-" + (k+1) + " ";
				myCount++;
			}
		}
		myRes = myCount + " " + myRes;
		return myRes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (value ^ (value >>> 32));
		result = prime * result + varCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionalCube other = (PositionalCube) obj;
		if (value != other.value)
			return false;
		if (varCount != other.varCount)
			return false;
		return true;
	}

}
