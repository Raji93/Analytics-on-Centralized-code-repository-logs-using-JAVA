import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.text.DateFormatSymbols;
import com.opencsv.CSVWriter;
/**
  Author's Name: Rajya Laxmi Yellajosyula
  Creation day: August 2nd 2017
  Last modification day: August 4th 2017
  This program processes the log file emacs.log,
  fetches the top 20 percent users by the number of commits they did 
  and these users are grouped by the year of commit.
  CSVWriter,HashMaps,Sets and ArrayLists are mainly used in achieving the expected results.
  All the results are stored in TimeChunks.csv of the current execution folder.
*/
public class P3A3_Yellajosyula_ryellajo
{
  
   public static void main(String[] args) throws Exception
   {
      String csv = "TimeChunks.csv";
      CSVWriter writer = new CSVWriter(new FileWriter(csv));

      //Fetching fileName from  user using Activity1 public static function 
      String fileName = P3A1_Yellajosyula_ryellajo.getFileName();
      P3A1_Yellajosyula_ryellajo x = new P3A1_Yellajosyula_ryellajo(fileName);
      
      //Maps to hold the final and intermediate results
      Map<String,List<String>> fileContents,resultSet;

      //Gets Years and corresponding users
      fileContents = x.getYears();

      //Fetches the top 20 percent of users
      resultSet = getCommitsOrder(fileContents,fileName);

      System.out.println("Enter 1 to get the list of top 20% users who did most commits in year of your choice.Enter 0 for all results");
      Scanner in = new Scanner(System.in);

      int userChoice = in.nextInt();
      switch(userChoice)
      {
        case 1:
            {
                //Prints all the years in the log for the user to select 
                 Map<String,List<String>> years = x.getYears();
                for(Map.Entry<String,List<String>> m:resultSet.entrySet())
                  System.out.print(m.getKey()+"\t");

                System.out.println("Enter the year you want to see results for from the above listed years.seperate values by ,");
                Scanner user2 = new Scanner(System.in);

                String[] userInput = user2.nextLine().split(",");
                //Calls method to print the top 20% users based on commits for user chosen years
                getUserRequiredList(resultSet,userInput,x,writer);
                break;
            }
       

        case 0:
            {
              //Prints all the results
              getAllResults(resultSet,writer);
              break;
            }
                  
      }
     


      //close the writer
      writer.close(); 
      System.out.println("File creation Successful.Look for TimeChunks.csv for Results.");
   }


//Method that counts the commits made by users in each year and sorts them based on commits. 
public static Map<String,List<String>> getCommitsOrder(Map<String,List<String>> fileYears,String fileName) throws FileNotFoundException,IOException
{

   String user,line,year = new String();
         List<String> userValues = new ArrayList<String>();
         Map<String,List<String>> resultSet = new HashMap<>();

         for(Map.Entry<String,List<String>> m:fileYears.entrySet())
          {  
             year = m.getKey();

            int usersCount = m.getValue().size();
          
            Map<Integer,Integer> usersCommits = new HashMap<Integer,Integer>();


            for(int i=0;i<usersCount;i++)
            {
              int count =0;

              usersCommits.put(i,count);
              String username = m.getValue().get(i);
               
              BufferedReader br = new BufferedReader(new FileReader(fileName));

              while ((line = br.readLine()) != null) 
              {
                  if(line.contains(year) && line.contains(username))

                   //Fetches user commits per year and user
                    usersCommits.put(i,count++);
          
              }

            }


            //Sorts the user indexes in list based on the number of commits they did
            List<Integer> temp = sortOrder(usersCommits);
            List<String> users = new ArrayList<String>();

             for(int i=0;i<temp.size();i++)
                users.add(m.getValue().get(temp.get(i)));

            //Adds final result of year and corresponding users to result map
            resultSet.put(m.getKey(),users);
         }

return resultSet;

}
public static void getAllResults(Map<String,List<String>> resultSet,CSVWriter writer) throws IOException
{
      //get Maximum size of users list for years 
      int max = 0;
      for(Map.Entry<String,List<String>> m:resultSet.entrySet())
       {  
          if(max<m.getValue().size())
          max = m.getValue().size();
          
        }
 
      List<String[]> values = new ArrayList<String[]>();
      String[] rows = new String[max+1];
      int i=0,j=0;
      String appender = ",";
      String temp=appender;
      List<String> finalResult =new ArrayList<String>();
        for(Map.Entry<String,List<String>> m:resultSet.entrySet())
       {  

          temp += m.getKey()+appender;
        
        }
        //adding years as column names
        finalResult.add(i,temp);

         
        //Adding users as rows
        for(int k = 1;k<=max;k++)
        {
            String users = appender;
            for(Map.Entry<String,List<String>> m:resultSet.entrySet())
              {
                  if(k<=m.getValue().size())
                    users +=m.getValue().get(k-1)+appender;
                  else
                    users +=appender;
               } 
               finalResult.add(k,users);
        }


        for(int k=0;k<finalResult.size();k++)
        {
          String records[] = finalResult.get(k).split(",");
         writer.writeNext(records);  
        }
}
//Method that prints rresults into csv for user required years
public static void getUserRequiredList(Map<String,List<String>> resultSet,String[] userInput,P3A1_Yellajosyula_ryellajo x,CSVWriter writer)throws FileNotFoundException,IOException,Exception
{


     int size = userInput.length;
     //System.out.println("users size is"+size);

    //Creates header record
      String [] record = "Year,Users".split(",");

        
      //Write the record to file
      writer.writeNext(record);


       for(Map.Entry<String,List<String>> m:resultSet.entrySet())
       {  
         for(int j= 0;j<size;j++)
         {
          if(m.getKey().equals(userInput[j]))
          {   
            String temp=m.getKey()+",";
            for(int k=0;k<m.getValue().size();k++)
                  temp +=m.getValue().get(k)+",";
        
              String[] rs = temp.split(",");
              writer.writeNext(rs);

          }

        }
      }
}
//sorts the indexes of the users based on the maximum commits to get top 20 Percent users
public static ArrayList<Integer> sortOrder(Map<Integer,Integer> map)
{
    int numberOfUsers = map.size();
    double sizing = (numberOfUsers*0.2);

   int requiredSize = (int)Math.ceil(sizing);
   
   Set<Entry<Integer, Integer>> set = map.entrySet();
   List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(set);
   ArrayList<Integer> resultSet = new ArrayList<Integer>();
    
  //Compares the sets values    
  Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
 


  int temp =1;
  
  for(Map.Entry<Integer, Integer> entry:list)
  {

    if(temp<=requiredSize)
    {
      resultSet.add(entry.getKey());
      temp++;
       
    }
  }    

return resultSet;

}

  
}