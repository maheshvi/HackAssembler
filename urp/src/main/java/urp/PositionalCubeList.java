package urp;

import java.util.ArrayList;

public class PositionalCubeList {

	private final ArrayList<PositionalCube> cubeList = new ArrayList<PositionalCube>();
	private final int varCount;
	private long[] posCount;
	private long[] negCount;
	private boolean[] singleVarPos;
	private boolean[] singleVarNeg;

	public PositionalCubeList(int varCount) {
		this.varCount = varCount;
		posCount = new long[varCount];
		negCount = new long[varCount];
		singleVarPos = new boolean[varCount];
		singleVarNeg = new boolean[varCount];

		for (int k = 0; k < varCount; k++) {
			singleVarPos[k] = false;
			singleVarNeg[k] = false;
		}
	}

	public void insertPositionalCube(PositionalCube aPosCube) {
		if (aPosCube == null)
			return;
		if (cubeList.contains(aPosCube))
			return;
		int myVarVal, mySingleVarPosition;
		for (int k = 0; k < varCount; k++) {
			myVarVal = aPosCube.getVarValAtPos(k + 1);
			if (myVarVal == PositionalCube.NORM_VAR)
				posCount[k] = posCount[k] + 1;
			else if (myVarVal == PositionalCube.COMPLIMENT_VAR)
				negCount[k] = negCount[k] + 1;
			if (aPosCube.isSingleVarOnly()) {
				mySingleVarPosition = aPosCube.getSingleVarSignedNum();
				if (mySingleVarPosition > 0)
					singleVarPos[mySingleVarPosition - 1] = true;
				else
					singleVarNeg[-mySingleVarPosition - 1] = true;
			}
		}
		cubeList.add(aPosCube);
	}

	public PositionalCubeList getPosCoFactor(int aVarNum) {
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		PositionalCube myPosCoFactor;
		for (PositionalCube myPositionalCube : cubeList) {
			myPosCoFactor = myPositionalCube.getPosCoFactor(aVarNum);
			if (myPosCoFactor != null)
				myRes.insertPositionalCube(myPosCoFactor);
		}
		return myRes;
	}

	public PositionalCubeList getNegCoFactor(int aVarNum) {
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		PositionalCube myNegCoFactor;
		for (PositionalCube myPositionalCube : cubeList) {
			myNegCoFactor = myPositionalCube.getNegCoFactor(aVarNum);
			if (myNegCoFactor != null)
				myRes.insertPositionalCube(myNegCoFactor);
		}
		return myRes;
	}

	public boolean isUnate() {
		for (int k = 0; k < varCount; k++) {
			if (!((posCount[k] == 0) || (negCount[k] == 0)))
				return false;
		}

		return true;
	}

	public boolean hasUnitPositionalCube() {
		for (PositionalCube myPositionalCube : cubeList) {
			if (myPositionalCube.isUnit())
				return true;
		}
		return false;

	}

	public int getMostBinateVarNum() {
		long myDiff = cubeList.size();
		int myVarPos = 0;
		long myPresence = 0;
		for (int k = 0; k < varCount; k++) {
			if (!((posCount[k] == 0) || (negCount[k] == 0))) {
				if (posCount[k] + negCount[k] == myPresence) {
					if (myVarPos == 0){
						myVarPos = k+1;
					}
					if (Math.abs(posCount[k] - negCount[k]) < myDiff) {
						myDiff = Math.abs(posCount[k] - negCount[k]);
						myVarPos = k + 1;
					}
					
				}else if(posCount[k] + negCount[k] > myPresence){
					myVarPos = k+1;
					myPresence = posCount[k] + negCount[k];
					myDiff = Math.abs(posCount[k] - negCount[k]);
				}
			}
		}
		return myVarPos;
	}

	public int getMostSpreadUnate() {
		int mySum = 0, myVarPos = 0;
		for (int k = 0; k < varCount; k++) {
			if (((posCount[k] == 0) || (negCount[k] == 0))) {
				if (Math.abs(posCount[k] + negCount[k]) > mySum) {
					mySum = (int) Math.abs(posCount[k] + negCount[k]);
					myVarPos = k + 1;
				}
			}
		}
		return myVarPos;
	}

	public boolean isComplementAdditiveSatisfied() {
		for (int k = 0; k < varCount; k++) {
			if ((singleVarPos[k]) && (singleVarNeg[k]))
				return true;
		}
		return false;
	}

	public boolean isTautology() {
		if (isUnate()) {
			if (hasUnitPositionalCube())
				return true;
			else
				return false;

		} else if (isComplementAdditiveSatisfied())
			return true;
		else {
			int myVarNum = getMostBinateVarNum();
			return (getPosCoFactor(myVarNum).isTautology() && getNegCoFactor(
					myVarNum).isTautology());
		}
	}

	public PositionalCubeList and(int aVarNum, boolean aVarValue) {
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		PositionalCube myAndedPosCube;
		for (PositionalCube myPositionalCube : cubeList) {
			myAndedPosCube = myPositionalCube.and(aVarNum, aVarValue);
			if (myAndedPosCube != null)
				myRes.insertPositionalCube(myAndedPosCube);
		}
		return myRes;

	}

	public PositionalCubeList or(PositionalCubeList aCubeList) {
		for (PositionalCube myPositionalCube : aCubeList.getCubeList()) {
			if (myPositionalCube != null)
				this.insertPositionalCube(myPositionalCube);
		}
		return this;
	}

	public PositionalCubeList invert() {
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		if (isEmptyCubeList()) {
			myRes.insertPositionalCube(new PositionalCube(varCount));
			return myRes;
		} else if (hasUnitPositionalCube())
			return myRes;
		else if (cubeListSize() == 1)
			return cubeList.get(0).invert();
		else {
			int myVarNum = getMostBinateVarNum();
			if (myVarNum == 0)
				myVarNum = getMostSpreadUnate();
			return getPosCoFactor(myVarNum).invert().and(myVarNum, true).or(
					getNegCoFactor(myVarNum).invert().and(myVarNum, false));
		}
	}

	@Override
	public String toString() {
		String myRes = varCount + "\n" + cubeListSize() + "\n";
		for (PositionalCube myPositionalCube : cubeList) {
			myRes = myRes + myPositionalCube.toString() + "\n";
		}
		return myRes;

	}

	public ArrayList<PositionalCube> getCubeList() {
		return cubeList;
	}

	public boolean isEmptyCubeList() {

		return cubeList.isEmpty();
	}

	public int cubeListSize() {
		return cubeList.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cubeList == null) ? 0 : cubeList.hashCode());
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
		PositionalCubeList other = (PositionalCubeList) obj;
		if (cubeList == null) {
			if (other.cubeList != null)
				return false;
		} else if (!cubeList.equals(other.cubeList))
			return false;
		if (varCount != other.varCount)
			return false;
		return true;
	}
}
