import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.util.LogColumn;
import lejos.util.NXTDataLogger;

import java.io.IOException;
import java.util.Queue;



/**
 * Set up a data collection protocol for the IR sensor and do interactive
 * data collection, one point at a time.
 *
 * @author Tim Barnes, 2014
 *
 * To run this program:
 *
 * 0. Install LeJOS on a computer, flash its firmware to the NXT
 *
 * 1. Compile the program
 *    >> nxjc CollectIRData.java
 *
 * 2. Link the program into a finished executable
 *    >> nxjlink CollectIRData -o CollectIRData.nxj
 *
 * 3. Start the graphical recording program in a different window:
 *    >> nxjchartinglogger &
 *
 * 4. Make sure the NXT is connected and turned on
 *
 * 5. Upload and run (with -r) the program
 *    >> nxjupload -r CollectIRData.nxj
 *
 * 6. Make sure the NXT is ready for a connection, it should display
 *    "Connecting..."
 *
 * 7. Type the NXT name into the charting logger and Connect
 *
 * 8. Collect data, it gets logged to the text file specified
 *    in the charting logger
 */



/**
 * A bag that holds a set of numbers that together specify how the IR beacon
 * and detector should be arranged in order to collect a data point.
 */
class PositionSpec
{
	/** The distance between the IR beacon and detector in meters */
	public final float R;

	/** The azimuthal angle for the IR beacon's orientation in degrees
	 *  (or, equivalently, the sensor's position)
	 */
	public final int   azi;

	/** The elevation angle for the IR beacon's orientation in degrees
	 *  (or, equivalently, the sensor's position)
	 */
	public final int   ele;

	/** The azimuthal angle for the IR sensor's orientation in degrees */
	public final int   sensAzi;

	/** The elevation angle for the IR sensor's orientation in degrees */
	public final int   sensEle;


	public PositionSpec(float _R, int _azi, int _ele,
	                    int _sensAzi, int _sensEle)
	{
		R = _R;
		azi = _azi;
		ele = _ele;
		sensAzi = _sensAzi;
		sensEle = _sensEle;
	}
}


/**
 * The big class that manages the IR sensor and collects/logs its data.
 *
 * Ideally, this would be refactored into a general data logging class and
 * a separate class that implements the pieces specific to the IR sensor.
 *
 * CollectIRData::main() gives an example of how to use the IRListener
 * public interface:
 * 1. Construct an IRListener object by giving it an NXTDataLogger to write to
 * 2. Add sensor arrangements for the experiment with addNewPoint()
 * 3. Collect/log all the added sensor arrangements in collectAllDataPoints()
 */
class IRListener
{
	private IRSeekerV2 infraRed;
	private NXTDataLogger log;

	public final int nSamples = 10;

	private Queue<PositionSpec> dataPointsToCollect;
	private int[][] cache;

	/**
	 * The constructor. `logger` should already be connected at this point.
	 *
	 * @param logger: The logging connection where all the data is sent
	 */
	public IRListener(NXTDataLogger logger)
	{
		dataPointsToCollect = new Queue<PositionSpec>();
		infraRed = new IRSeekerV2(SensorPort.S1, IRSeekerV2.Mode.AC);
		cache = new int[nSamples][5];
		setupLogger(logger);
	}

	/**
	 * Add a new sensor arrangement to the list of data points to collect
	 * in this experiment
	 *
	 * @param pt: Object whose members describe the sensor arrangement
	 */
	public void addNewPoint(PositionSpec pt)
	{
		dataPointsToCollect.push(pt);
	}

	/**
	 * Run the main data collection loop. For each stored sensor arrangement:
	 *
	 * 1. Display the prompt on the screen so the user can move the
	 *    IR beacon and sensor
	 *
	 * 2. Wait for a button press.
	 *
	 *      * If the user presses ENTER on the NXT, collect and log the
	 *        arrangement and collected sensor data, and move
	 *        to the next data point.
	 *
	 *      * If the user presses ESCAPE, leave before finishing (pause/quit)
	 */
	public void collectAllDataPoints()
	{
		while ( ! dataPointsToCollect.isEmpty() )
		{
			dispPrompt();
			switch (Button.waitForAnyPress())
			{
				case Button.ID_ENTER:
					collect();
					log();
					dataPointsToCollect.pop();
					break;
				case Button.ID_ESCAPE:
					return;
			}
		}
	}

	//************** INTERNAL **************//

	/**
	 * Part of the constructor that sets up the connected data logger.
	 * The logger is initialized by describing the table that should be
	 * constructed to hold all the data. This is done by making an array of
	 * `LogColumn` objects with a description of the logged value (which
	 * becomes the table column's text header) and the datatype of the value.
	 * Possible values are listed as LogColumn.DT_* .
	 *
	 * Note that, for logging to work correctly, the ordering of the LogColumns
	 * must be exactly mirrored during actual logging. The code in
	 * IRListener::log() follows the ordering set up here.
	 *
	 * @param logger: Constructor argument, data logging connection
	 */
	void setupLogger(NXTDataLogger logger)
	{
		log = logger; // Connect logger to this object

		// 5 entries for 5 DoF sensor arrangement,
		// 5 sensor values for each sample
		LogColumn[] columns = new LogColumn[5 + nSamples * 5];
		columns[0] = new LogColumn("Distance (m)", LogColumn.DT_FLOAT);
		columns[1] = new LogColumn("Pos. azimuthal angle (deg)",
		                           LogColumn.DT_INTEGER);
		columns[2] = new LogColumn("Pos. elevation angle (deg)",
		                           LogColumn.DT_INTEGER);
		columns[3] = new LogColumn("Ori. azimuthal angle (deg)",
		                           LogColumn.DT_INTEGER);
		columns[4] = new LogColumn("Ori. elevation angle (deg)",
		                           LogColumn.DT_INTEGER);

		for (int i = 0; i < nSamples; i++)
			for (int j = 0; j < 5; j++)
				columns[5 + 5 * i + j] = new LogColumn("Sensor " + j
				                                        + ", sample " + i,
				                                       LogColumn.DT_INTEGER);

		log.setColumns(columns);
	}

	/** Retrieve the current / most recent requested sensor arrangement */
	private PositionSpec getCurPt()
	{
		// LeJOS issue: peek() returns Object rather than template datatype
		return dataPointsToCollect.elementAt(0);
	}

	/**
	 * Display on the NXT screen how the
	 * IR beacon and sensor should be arranged
	 */
	void dispPrompt()
	{
		PositionSpec curPt = getCurPt();
		System.out.println("Move sensor to");
		System.out.println("R=" + curPt.R + ",");
		System.out.println("azi=" + curPt.azi + ", ele=" + curPt.ele);
		System.out.println("Orient sensor to");
		System.out.println("azi=" + curPt.sensAzi + ", ele=" + curPt.sensEle);
		System.out.println("Press [Enter]");
	}

	/**
	 * Collect `nSamples` readings from the IR sensor as quickly as possible,
	 * and store them in `cache` for logging. Note that cache is a
	 * 5 x nSamples matrix, and getSensorValues() returns a 5-element vector
	 * across its detector regions.
	 */
	void collect()
	{
		for (int i = 0; i < nSamples; i++)
			cache[i] = infraRed.getSensorValues();
	}

	/**
	 * Log the values recorded in `cache` by collect() over the
	 * logging connection. Note that the values must be written to the log with
	 * writeLog() in the exact same order as they were described
	 * in setupLogger()
	 */
	void log()
	{
		PositionSpec curPt = getCurPt();
		log.writeLog(curPt.R);
		log.writeLog(curPt.azi);
		log.writeLog(curPt.ele);
		log.writeLog(curPt.sensAzi);
		log.writeLog(curPt.sensEle);

		for (int sample = 0; sample < nSamples; sample++)
			for (int sensorIndex = 0; sensorIndex < 5; sensorIndex++)
				log.writeLog( cache[sample][sensorIndex] );

		log.finishLine();
	}
}



/**
 * Outermost/Main class. Note that the program expects the NXT to connect
 * through USB. LeJOS also allows a bluetooth connection, but the drivers
 * are outdated on Mac.
 */
public class CollectIRData
{
	public static void main (String[] args)
	{
		// Wait for a connection from e.g. the 'nxjchartinglogger' program
		NXTDataLogger logger = new NXTDataLogger();
		System.out.println("Connecting...");
		NXTConnection conn = USB.waitForConnection(0, NXTConnection.PACKET);
		try
		{
			logger.startRealtimeLog(conn);
		}
		catch (IOException e)
		{
			return;
		}
		System.out.println("Connected!");


		// Create the data collector
		IRListener infraRed = new IRListener(logger);


		// Make experiment data points
		int nR = 30;
		float[] R = new float[nR];
		for (int i = 0; i < nR; i++)
			R[i] = (float) Math.exp(Math.log(Math.sqrt(2)) * (i - nR / 2));

		int nAzi = 36;
		int[] azi = new int[nAzi];
		for (int i = 0; i < nAzi; i++)
			azi[i] = (i - nAzi / 2) * 360 / nAzi;

		int nEle = 36;
		int[] ele = new int[nEle];
		for (int i = 0; i < nEle; i++)
			ele[i] = (i - nEle / 2) * 180 / nEle;


		// Add the data points to the data collector
		for (float r : R)
			infraRed.addNewPoint( new PositionSpec(   r, 0, 0, 0, 0) );
		for (int a : azi)
			infraRed.addNewPoint( new PositionSpec(0.5f, 0, 0, a, 0) );
		for (int e : ele)
			infraRed.addNewPoint( new PositionSpec(0.5f, 0, 0, 0, e) );


		// Run the experiment
		infraRed.collectAllDataPoints();
	}
}
