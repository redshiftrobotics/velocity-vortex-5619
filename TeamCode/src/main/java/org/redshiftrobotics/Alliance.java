package org.redshiftrobotics;


public enum Alliance {
	RED, BLUE;

	public Alliance other() {
		return this == Alliance.RED ? Alliance.BLUE : Alliance.RED;
	}
}
