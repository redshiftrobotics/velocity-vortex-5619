package org.redshiftrobotics.SAMPLES.FileReadWrite;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.util.TelementryUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name="Write", group="File")
@Disabled
public class FileWriteTest extends OpMode {
    File outputfile;
    FileWriter out;
    String text;
	TelementryUtil util = new TelementryUtil(this);

    @Override
    public void init(){

    }

    @Override
    public void start() {
        //Declare the file path
        outputfile = new File("/sdcard/testout.txt");

        //Set the file for write mode
        try {
            out = new FileWriter(outputfile);
        } catch (IOException e) {
            DbgLog.logStacktrace(e);
        }

        //Write some text to the file
        text = "Hello World\nThis is another Line!";
        try {
            out.write(text);
        } catch (IOException e) {
            DbgLog.logStacktrace(e);
        }

        //Close the resource
        if(out != null) {
            try {
                out.close();
            } catch (IOException e) {
                DbgLog.logStacktrace(e);
            }
        }

        util.writeLine("Finished writing file");

    }

    @Override
    public void loop() {

    }
}
