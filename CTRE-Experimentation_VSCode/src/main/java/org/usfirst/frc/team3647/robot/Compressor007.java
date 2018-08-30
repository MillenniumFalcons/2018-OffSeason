package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Compressor;


public class Compressor007 
{
	static Compressor c = new Compressor(0);
	
	public static void runCompressor()
	{
		c.setClosedLoopControl(true);
	}
}
