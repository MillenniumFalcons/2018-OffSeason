package org.usfirst.frc.team3647.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadMotionProfileData
{
    private double data[][];
    private int pointTimeMilliS;

    public ReadMotionProfileData(String filename, boolean reversed)
    {
        pointTimeMilliS = 0;
        try
        {
            readFile(filename);
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }

    private void readFile(String filename) throws IOException
    {
        BufferedReader bufferReader = new BufferedReader(new FileReader(filename));
        int lines = Integer.parseInt(bufferReader.readLine());

        data = new double[lines][3];
        String[] line;
        double[] temp;

        for(int i = 1; i < lines; i++)
        {
            line = bufferReader.readLine().split(",\t");
            temp = new double[4];

            temp[0] = Double.parseDouble(line[0]);
            temp[1] = Double.parseDouble(line[1]);
            temp[2] = Double.parseDouble(line[2]);

            if(pointTimeMilliS == 0) // convert to milliseconds and only do it once
            {
                pointTimeMilliS = (int) (Double.parseDouble(line[3]) * 1000);
            }
            data[i] = temp;
        }
        bufferReader.close();
    }

    public double[][] getMPData()
    {
        return data;
    }
}