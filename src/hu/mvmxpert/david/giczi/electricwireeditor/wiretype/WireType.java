package hu.mvmxpert.david.giczi.electricwireeditor.wiretype;

public enum WireType {

	bal, jobb, közép;
	
	public static WireType getWireType(String type) {
		
		switch (type) {
		case "közép":
			return WireType.közép;
		case "jobb":
			return WireType.jobb;
		default:
		 return WireType.bal;
		}
	}
	
}
