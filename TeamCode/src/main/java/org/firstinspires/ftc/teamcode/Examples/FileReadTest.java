package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.util.Util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@TeleOp(name="Read", group="File")
@Disabled
public class FileReadTest extends OpMode {

    File inputFile;
    FileReader input;
    String text;
    Util util = new Util(this);

    @Override
    public void init() {

    }

    @Override
    public void start() {
        //set the text to an empty string;

        //Declare the filepath to read from
        inputFile = new File("/sdcard/testout.txt");
        try {
            //Set the input file
            input = new FileReader(inputFile);

            //grab the contents one character at a time and append it to the output string
            int c;
            while ((c = input.read()) != -1) {
                text += (char) c;
            }

            //close the file resource
            input.close();


        } catch (IOException e) {
            DbgLog.logStacktrace(e);
        }

    }

    @Override
    public void loop() {
        util.writeLine("Read: "+text);

    }
}

