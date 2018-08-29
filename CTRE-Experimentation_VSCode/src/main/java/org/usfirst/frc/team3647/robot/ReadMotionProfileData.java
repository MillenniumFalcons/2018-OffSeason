package org.usfirst.frc.team3647.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadMotionProfileData
{
    //This ArrayList contains all the values (unseparated) from a CSV file
    public static List<String> allValues = new ArrayList<String>();
    //These arrays below contain the parsed values of ArrayList
	//**NEED WAY TO DYNAMICALLY CHANGE ARRAY SIZE**
	public static double[] position = new double[154];	//**NEED WAY TO DYNAMICALLY CHANGE ARRAY SIZE**
	public static double[] velocity = new double[154];	//**NEED WAY TO DYNAMICALLY CHANGE ARRAY SIZE**
    public static int[] dT = new int[154];		//**NEED WAY TO DYNAMICALLY CHANGE ARRAY SIZE**
    public static double[][] data = arrayConvert(154);
    
    private int pointTimeMilliS;

    //take file path input ex: C:\\Users\\Username\\Downloads\\file.csv
    public void parseCSVFile(String csvFile) throws IOException
    {
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {

            while ((line = br.readLine()) != null) 
            {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
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