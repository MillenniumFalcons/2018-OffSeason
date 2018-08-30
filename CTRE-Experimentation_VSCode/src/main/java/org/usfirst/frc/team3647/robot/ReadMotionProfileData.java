package org.usfirst.frc.team3647.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadMotionProfileData
{
    //This ArrayList contains all the values (unseparated) from a CSV file
    public static List<String> allValues = new ArrayList<String>();
    public static int lineCount = countLines("C:\\Users\\Kunal\\Downloads\\Same_Side_Scale_left_Talon.csv");
    //These arrays below contain the parsed values of ArrayList
	public static double[] position = new double[lineCount];	
	public static double[] velocity = new double[lineCount];	
    public static int[] dT = new int[lineCount];		
    public static double[][] data = arrayConvert(lineCount);
    
    private int pointTimeMilliS;

    public ReadMotionProfileData(String csvFile)
    {
        countLines(csvFile);
        System.out.println("There are " + lineCount + " lines in your CSV File");
    }
    
    public static int countLines(String csvFile)
    {
    	int lc = 0;
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {

            while ((line = br.readLine()) != null) 
            {
                lc++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lc;
    }

    //take file path input ex: C:\\Users\\Username\\Downloads\\file.csv
    public void parseCSVFile(String csvFile) //throws IOException
    {
        String line = "";
        String cvsSplitBy = ",";
        String[] pvt = new String[4];

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {
            while ((line = br.readLine()) != null) 
            {
                // use comma as separator
                pvt = line.split(cvsSplitBy);
                
                //print each line of CSV
                //System.out.println("Value [value1 = " + pvt[0] + " , value2 = " + pvt[1] + " , value3 = " + pvt[2] + "]");
                allValues.add(pvt[0]);
                allValues.add(pvt[1]);
                allValues.add(pvt[2]);
            }
            
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        //parses the list that contains all the values of CSV and assigns each value to its respective type
        int count = 0;
    	//parsing for Position, Velocity, and dT
    	for(int i = 0; i < allValues.size(); i+=3)
    	{
    		allValues.set(i, allValues.get(i).replace(" ", ""));
    		position[count] = Double.parseDouble(allValues.get(i));
    		
    		allValues.set(i+1, allValues.get(i+1).replace(" ", ""));
    		velocity[count] = Double.parseDouble(allValues.get(i+1));
    		
    		allValues.set(i+2, allValues.get(i+2).replace(" ", ""));
    		dT[count] = Integer.parseInt(allValues.get(i+2));
    		
    		count++;
    	}
    }

    public static double[][] arrayConvert(int rows)
    {
        double[][] output = new double[rows][2];
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < 2; j++)
            {
            	if(j == 0)
            		output[i][j] = position[i];
            	else if(j == 1)
            		output[i][j] = velocity[i];
            }
        }

        return output;
    }
    
}