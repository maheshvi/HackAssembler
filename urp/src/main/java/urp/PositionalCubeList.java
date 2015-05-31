package urp;

import java.util.ArrayList;

public class PositionalCubeList {
	
	private final ArrayList<PositionalCube> cubeList = new ArrayList<PositionalCube>();
	private final int varCount;
	private long[] posCount;
	private long[] negCount;
	
	
	public PositionalCubeList(int varCount) {
		this.varCount = varCount;
		posCount =  new long[varCount];
		negCount =  new long[varCount];
	}

	public void insertPositionalCube(PositionalCube aPosCube){
		if (aPosCube == null) return;
		int myVarVal;
		for(int k=0; k< varCount; k++){
			myVarVal = aPosCube.getVarValAtPos(k+1);
			if (myVarVal == 1) posCount[k] = posCount[k]+1;
			else if (myVarVal == 2) negCount[k] = negCount[k]+1;
		}
		cubeList.add(aPosCube);
	}
	
	public PositionalCubeList getPosCoFactor(int aVarNum){
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		PositionalCube myPosCoFactor;
		for(PositionalCube myPositionalCube: cubeList){
			myPosCoFactor = myPositionalCube.getPosCoFactor(aVarNum);
			if (myPosCoFactor != null) myRes.insertPositionalCube(myPosCoFactor);
		}
		return myRes;
	}
	
	public PositionalCubeList getNegCoFactor(int aVarNum){
		PositionalCubeList myRes = new PositionalCubeList(varCount);
		PositionalCube myNegCoFactor;
		for(PositionalCube myPositionalCube: cubeList){
			myNegCoFactor = myPositionalCube.getNegCoFactor(aVarNum);
			if (myNegCoFactor != null) myRes.insertPositionalCube(myNegCoFactor);
		}
		return myRes;
	}
	
	public boolean isUnate(){
		for(int k=0; k < varCount ; k++){
			if (!( (posCount[k] == 0) || (negCount[k]==0) ) ) return false;
		}
		
		return true;
	}
	
	public boolean hasUnitPositionalCube(){
		for(PositionalCube myPositionalCube: cubeList){
			if (myPositionalCube.isUnit()) return true;
		}
		return false;
		
	}

	public int getMostBinateVarNum(){
		int myDiff = cubeList.size(), myVarPos=0;
		for(int k=0; k < varCount ; k++){
			if (!( (posCount[k] == 0) || (negCount[k]==0) ) ) {
				if (Math.abs(posCount[k]-negCount[k])<myDiff){
					myDiff = (int)Math.abs(posCount[k]-negCount[k]);
					myVarPos = k+1;
				}
			}
		}
		return myVarPos;
	}
}
