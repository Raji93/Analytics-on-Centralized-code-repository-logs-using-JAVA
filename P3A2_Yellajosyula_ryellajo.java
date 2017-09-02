import java.io.*;
import java.util.*;
import java.text.DateFormatSymbols;
import com.opencsv.CSVWriter;
 
/**
  Author's Name: Rajya Laxmi Yellajosyula
  Creation day: July 31st 2017
  Last modification day: August 4th 2017
  This program processes the log file emacs.log,
  fetches the name of every file changed,Number of commits made to it,
  First date of commit and Last date of commit. 
  All the results are stored in Details.csv of the current execution folder.
*/
public class P3A2_Yellajosyula_ryellajo
{
  
   public static void main(String[] args) throws FileNotFoundException,IOException
   {   String csv = "Details.csv";
      CSVWriter writer = new CSVWriter(new FileWriter(csv));
        
      //Creates header record
      String [] record = "File,Number of Commits,First Date of commit,Last Date of commit".split(",");

        
      //Write the record to file
      writer.writeNext(record);

      //Fetching fileName from  user using Activity1 public static function 
      String fileName = P3A1_Yellajosyula_ryellajo.getFileName();
      
      P3A1_Yellajosyula_ryellajo x = new P3A1_Yellajosyula_ryellajo(fileName); 
      Map<String,Date> result2,result3;
      Map<String,Integer> result4;

    try{      
          result2 = x.earliestCommits();  
          result3 = x.LastCommits();
          result4 = getTotalCommits(fileName);


      //List that stores the final results
      List<String[]> details = new ArrayList<String[]>();


      for(Map.Entry<String,Date> m1:result2.entrySet())
      {  
            for(Map.Entry<String,Date> m2:result3.entrySet())
            {
               
               
                  if(m1.getKey().equals(m2.getKey()))
                  {
                      

                        for(Map.Entry<String,Integer> m3:result4.entrySet())
                        {
                             if(m1.getKey().equals(m3.getKey()))
                             {   
                              String[] temp ={m1.getKey(),m3.getValue().toString(),m1.getValue().toString(),m2.getValue().toString()};

                             details.add(temp);
                            }
                        }                   
                    }
                  

            }
      }
      
      //Writes all the records to CSV    
      writer.writeAll(details);
      //close the writer
      writer.close(); 
    }
    catch(Exception e){

        System.out.println(e.getMessage()+"Exception caused while parsing the field");

      }
  System.out.println("File creation Successful.Look for Details.csv for Results.");
 
   }
 

//Method that finds number of commits made to each file and return as HashMap of filename and number of commits
  public static Map<String,Integer> getTotalCommits(String fileName) throws FileNotFoundException,IOException
  {
        Map<String,Integer> fileContents = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line,file = new String();
        int commit = 0,users =0;
        String maxFile = new String();
        int userMax =0;


            while ((line = br.readLine()) != null) 
            {

            if(line.contains("RCS file:"))
              {
                  //Fetches filenames
                 String lines[] = line.split(":");
                 file = lines[1];
                 commit = 0; 
              }

              //Counting the commits
              if(line.contains("author:"))
                commit++;
      
              fileContents.put(file,commit);


            }
         
        return fileContents;


  }

  
}