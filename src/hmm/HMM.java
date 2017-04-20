/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmm;
import java.io.*;
import java.util.*;
import java.util.regex.*;
/**
 *
 * @author Tong
 */
public class HMM {

    /**
     * @param args the command line arguments
     */
    
    public final String trainFileName = "Link3_train_seqs.txt";
    public final String testFileName = "Link4_test_seqs.txt";
    public final String geneFileName = "Link2_sequenceOfEcoliStrainM54.txt";
    public String allData = null; 
    public String geneSequence = null;
    int numberOfLines = 0;
    int lineNumber = 0;
    String line = null;
    String line2 = null;
    String[][] coordinateString;
    int[][] coordinate;
    
    ArrayList<Integer> startSequence = new ArrayList();
    ArrayList<Integer> endSequence = new ArrayList();
    
    public static void main(String[] args) {
        // TODO code application logic here
        HMM a = new HMM();
        a.countLines(a.testFileName);
        a.getSubsequencesArray(a.testFileName);
        
        for (int i = 0; i < a.lineNumber; i++){
                for (int j = 0; j < 100; j++){
                    System.out.print(a.coordinate[i][j] + ", ");
                }
                System.out.println("");
            }
        System.out.println(a.lineNumber);
        //a.getGeneData(a.geneFileName);
        //a.getGeneSequence(190,256);
        //System.out.println(a.geneSequence);
        //System.out.println(a.geneSequence.length());
        
        
        String bbb = "1234567890"; 
        String abc = bbb.substring(0,5);
        System.out.println(abc);

        //System.out.println(a.lineNumber);
    }
    
    public void getSubsequencesArray(String fileName){
        countLines(fileName);
        coordinate = new int[lineNumber][100];
        coordinateString = new String[lineNumber][100];
        int columnCounter = 0;
        int rowCounter = 0;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            //Get the subsequence numbers in the file
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);   
                String[] columns = line.split("\\t");
                startSequence.add(Integer.parseInt(columns[0]));
                endSequence.add(Integer.valueOf(columns[1]));
                
                //save the third column as String in coordinateString
                for (String b:columns[2].replace("[","").replace("]", "").replace(",", "").split("  ")){
                    //System.out.println(b.substring(1));
                    for(String c:b.split(" ")){
                        //System.out.println(c);
                        coordinateString[rowCounter][columnCounter] = c;
                        columnCounter++;
                    }
                    columnCounter =0; 
                }
                rowCounter++;
            }
            
            //change the data type of the third column from String to integer and save it in coordinate[][]
            for (int i = 0; i < lineNumber; i++){
                //input first column as the first value in coordinate[][]
                coordinate[i][0] = startSequence.get(i);
                for (int j =1; j < lineNumber; j++){
                    if (coordinateString[i][j] != null){
                        coordinate[i][j] = Integer.parseInt(coordinateString[i][j]);
                        //System.out.print(coordinate[i][j] + ",");
                    }
                }
                //System.out.println("");
            }
            
            //input second column as the last value in coordinate[][]
            for (int i = 0; i < lineNumber; i++){
                for (int j =1; j < 100; j++){
                    if (coordinate[i][j] == 0 && coordinate[i][j-1] != 0 && coordinate[i][j-1] != endSequence.get(i)){
                        coordinate[i][j] = endSequence.get(i);
                    }
                }
            }
            
            //print out the values coordinate[][] 
            /*for (int i = 0; i < lineNumber; i++){
                for (int j = 0; j < lineNumber; j++){
                    System.out.print(coordinate[i][j] + ", ");
                }
                System.out.println("");
            }*/
            //close the file
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'"); 
        }
    }
    
    //count the number of lines in the files
    public void countLines(String fileName){
        try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            File file = new File(fileName);
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            lineNumber = lineNumberReader.getLineNumber();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'"); 
        }
    }
    
    public int[][] getCoordinate(){
        return coordinate;
    }
    //public void setCoordinate(int[][] coordinate){
    //    HMM.coordinate = coordinate;
    //}
    
    public void getGeneData(String fileName){
        try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                allData = allData + line; 
                //System.out.println(line);     
            } 
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'"); 
        }
    }
    
    public void getGeneSequence(int s, int e){
        geneSequence = allData.substring(s, e);
    }
}
