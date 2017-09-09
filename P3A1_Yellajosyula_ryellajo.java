import java.util.*;
import java.io.*;
import java.text.*;
/**
  Author's Name: Rajya Laxmi Yellajosyula
  Creation day: July 29th 2017
  Last modification day: August 4th 2017
  This program processes the log file emacs.log,
  fetches the total number of files,
  File with the most number of revisions,
  File with the most number of users committs,
  Earliest commit to a file,
  User(s) with most commits in the log.
*/
public class P3A1_Yellajosyula_ryellajo
{
	//fields that hold the log file Name and total files count in the log 
	private int filecount;
	private String fileName;

	
	//Constructor that accepts name of the log file to be processed
	public P3A1_Yellajosyula_ryellajo(String f) throws FileNotFoundException
	{
			filecount = 0;
			fileName = f;
			
	}
	//Accessor method for file name
	public String getFileNames()
	  {
	    return fileName;
	  }
	  //Mutator method for file Name
	public void setFileName(String s)
	  {
	    fileName = s;
	  }
 	
	//Method that gets the total number of files in the log -Accessor for fileCount
	public int getFileCount() throws FileNotFoundException,IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String line;

    				while ((line = br.readLine()) != null) 
    				{
    						if(line.contains("RCS file:"))		
    							filecount++;
   					}
   		return filecount;
		
	}

	//Mutator method for file count
	public void setFileCount(int n)
	{
	    filecount = n;
	}

	//Method that fetches the maximum revisions of each file
	public Map<String,Integer> getMaxRevisions() throws FileNotFoundException,IOException
	{
			Map<String,Integer> fileContents = new HashMap<>();
			BufferedReader br = new BufferedReader(new FileReader(fileName));
    			String line,file = new String();
    			int revisions = 0;

    				while ((line = br.readLine()) != null) 
    				{
     					
					if(line.contains("RCS file:"))
    					{
    						
    						 String lines[] = line.split(":");
    						 file = lines[1];
    						
    					}

    					if(line.contains("total revisions:"))	
    					{
    							
    						//Splits the string in to parts to fetch the revisions number
    						String parts[] = line.split(";");
    					
    						//Replaces spaces before number with zero to make it easy to convert into integer
    						String temp1 = parts[0].replace(" ", "0");
    					
    						//Fetches the integer value of revisions string
    						String temp = temp1.substring(temp1.indexOf("total revisions:")+17 , temp1.length());

    						//Assigns it to revisions value
    						if(revisions<Integer.parseInt(temp))
    						{
    							revisions = Integer.parseInt(temp);
    						}
    								
    					}

    				}
    				    				 
    		fileContents.put(file,revisions);
    		return fileContents;


	}
//Method that return the file which has maximum commits made to it
	public Map<String,Integer> getMaxUserCommits() throws FileNotFoundException,IOException
	{
			Map<String,Integer> fileMax = new HashMap<>();
			Map<String,List<String>> fileContents = new HashMap<>();

			BufferedReader br = new BufferedReader(new FileReader(fileName));
    	String line,user,file = new String();
    	int userCount = 0,userMax =0;
    	List<String> userValues = null;

    	while ((line = br.readLine()) != null) 
    		{


						if(line.contains("RCS file:"))
    					{
    						
    						 String lines[] = line.split(":");
    						 file = lines[1];
    						 userCount = 0;	
                 userValues = new ArrayList<String>();
    					}

    					if(line.contains("author:"))
    					{
    						 String users[] = line.split(";");
    						 user = users[1].substring(9);

    						 if(!userValues.contains(user))
    						 	{userValues.add(user);

               }
    					}
    				fileContents.put(file,userValues);

    			}
    			

    		int max = 0;
    		String fileFinal = new String();
				 	
          for(Map.Entry<String,List<String>> m:fileContents.entrySet())
				 	{  

            if(m.getValue() != null)
            {
              if(max<m.getValue().size())
              {
               max = m.getValue().size();
               fileFinal = m.getKey();  
              }
            }

  				}  

     	fileMax.put(fileFinal,max);
    	return fileMax;
	}

//Method that fetches the user who made maximum commits and the count
	public Map<String,Integer> MaxUserCommits() throws FileNotFoundException,IOException
	{
		Map<String,Integer> fileContents = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String line,userName = "",file = new String();
    		int commits = 0;
    		String maxFile = new String();

    		ArrayList<String> users = new ArrayList<String>();


    				while ((line = br.readLine()) != null) 
    				{

						if(line.contains("author:"))
    					{
    						
    						 String lines[] = line.split(";");
    						 userName = lines[1];
    						 fileContents.put(userName,commits);
								 
    					}
    					
    			   }
    			      

    			 for(Map.Entry<String,Integer> m:fileContents.entrySet())
				 	{  
				 		BufferedReader br1 = new BufferedReader(new FileReader(fileName));
				 		while ((line = br1.readLine()) != null) 
    					{
  					 		
  							if(line.contains(m.getKey()))
  								fileContents.put(m.getKey(),m.getValue()+1);

  					 		  
  					 	} 
  					}
    				

    			int max = 0;

    		 for(Map.Entry<String,Integer> m:fileContents.entrySet())
				 	{  
				 		
				 		if(m.getValue()>max)
				 		{	max = m.getValue();
				 			file = m.getKey();
				 		}
  				}
  				Map<String,Integer> maxUser = new HashMap<>();
  				maxUser.put(file,max);
				 
    return maxUser;


	}

//Method that returns user who committed maximum number of times to a specific file
public  Map<String,Integer> userMostCommits(String file) throws IOException,FileNotFoundException,ParseException
{
       
      Map<String,Integer> fileMax = new HashMap<>();
      Map<String,List<String>> fileContents = new HashMap<>();

      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String line,user;
      int userMax =0,lineNumber =1;
      List<String> userValues = new ArrayList<String>();

      while ((line = br.readLine()) != null) 
        {
            lineNumber++;
            if(line.contains(file))
              {
                
                
                 userValues = new ArrayList<String>();
                 break;
              }
        }

        while ((line = br.readLine()) != null) 
        {
            if(line.contains("author:"))
              {
                 String users[] = line.split(";");
                 user = users[1].substring(9);

                 if(!userValues.contains(user))
                  
                    userValues.add(user);

                  
              }

              if(line.contains("===="))
                break;

        }
             
            
            fileContents.put(file,userValues);

            int userCount = 0;
            Map<String,Integer> contents = new HashMap<>();
            for(String s1:userValues)
            contents.put(s1,userCount);
            

          
          BufferedReader br1 = new BufferedReader(new FileReader(fileName));
          
         int i=1;

            while ((line = br1.readLine()) != null && (i<=lineNumber))
            {
                i++;
             }   
              
            while ((line = br1.readLine()) != null )
            {
              
              if(line.contains("===="))
                break;
              
             
              for(Map.Entry<String,Integer> m:contents.entrySet())
              {    
                    
                      if(line.contains(m.getKey()))
                        contents.put(m.getKey(),m.getValue()+1);

                  
              }

            }

  

        
           Map<String,Integer> result = new HashMap<>();
           int maxCommitUser = 0;
           String maxUser = ",";
        for(Map.Entry<String,Integer> m:contents.entrySet())
        {  
            if(m.getValue()>maxCommitUser)
            {
              maxCommitUser = m.getValue();
              maxUser = m.getKey();
            }
            if(m.getValue()==maxCommitUser && !maxUser.contains(m.getKey()) )
            { 
              maxCommitUser = m.getValue();
              maxUser += m.getKey();
            }


        }
        result.put(maxUser,maxCommitUser);
            return result;


}
//Method that returns the earliest commits of files in the log
public Map<String,Date> earliestCommits() throws FileNotFoundException,IOException,ParseException
	{
		Map<String,Date> fileContents = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String line,userName = "",file = new String();
    		int commits = 0;
    		String maxFile = new String();
    		ArrayList<String> users = new ArrayList<String>();
    		Date earlyDate = null,date;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Date> temp_list = new ArrayList<Date>();

    				while ((line = br.readLine()) != null) 
    				{
    					 
    							if(line.contains("RCS file:"))
    					{
    						
    						 String lines[] = line.split(":");
    						 file = lines[1];
    						 earlyDate = null;
    						fileContents.put(file,earlyDate);

    					}



						if(line.contains("date:"))
    					{
    						
    						 String lines[] = line.split(";");

    						 String dates[] =lines[0].split(" "); 

    						 String temp = dates[1]+" "+dates[2];

    						
    						 date = format.parse(temp);
							 
							 if(earlyDate == null)
							 	earlyDate = date;

							 if(earlyDate.after(date))
							 	earlyDate = date;
            
            fileContents.put(file,earlyDate);
							
    						 
    					}


    			   }
    			
      return fileContents;

	}
//Method that finds the last commits made to files
public Map<String,Date> LastCommits() throws FileNotFoundException,IOException,ParseException
	{
		Map<String,Date> fileContents = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String line,userName = "",file = new String();
    		int commits = 0;
    		String maxFile = new String();
    		ArrayList<String> users = new ArrayList<String>();
    		Date lastDate = null,date;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Date> temp_list = new ArrayList<Date>();

			while ((line = br.readLine()) != null) 
    				{
    					 
    							if(line.contains("RCS file:"))
    					{
    						
    						 String lines[] = line.split(":");
    						 file = lines[1];
    						 lastDate = null;
    						fileContents.put(file,lastDate);

    					}



						if(line.contains("date:"))
    					{
    						
    						 String lines[] = line.split(";");

    						 String dates[] =lines[0].split(" "); 

    						 String temp = dates[1]+" "+dates[2];

    						
    					 date = format.parse(temp);
							 
							 if(lastDate == null)
							 	lastDate = date;

							 if(lastDate.before(date))
							 	lastDate = date;
            
               fileContents.put(file,lastDate);
							
    						 
    					}


    			   }
    			
      return fileContents;

	}

//Method to fetch the years in the log file
  public Map<String,List<String>> getYears() throws FileNotFoundException,IOException,ParseException
  {
      Map<String,List<String>> fileYears = new HashMap<String,List<String>>();
      
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String user,line,year = new String();
      List<String> userValues = new ArrayList<String>();

         //Initialises a hash with all years as keys
          while ((line = br.readLine()) != null) 
          {
 

            if(line.contains("date:"))
              {
                
                String lines[] = line.split(";");
                 String dates[] =lines[0].split(" "); 
                 String temp1 = dates[1];
                 String years[] =temp1.split("-"); 
                  year = years[0];
                 userValues = new ArrayList<String>();
                 fileYears.put(year,userValues);
              }
            
          }



         for(Map.Entry<String,List<String>> m:fileYears.entrySet())
          {  
              
             
            BufferedReader br1 = new BufferedReader(new FileReader(fileName));


            while ((line = br1.readLine()) != null) 
            {
              if(line.contains("author:") && line.contains(m.getKey()))
              {
                List<String> usersList = m.getValue();
                String users[] = line.split(";");
                 user = users[1].substring(9);
 

                 if(!usersList.contains(user))
                  usersList.add(user);
                  
              
              fileYears.put(m.getKey(),usersList);
              
              }

               
            
            }
          }


         // fileYears.add(temp);
      return fileYears;

  }

//Method that fetches the log file name from the user
	public static String getFileName()
	{
		String file = new String();
		Scanner in = new Scanner(System.in);
   		boolean flag = false;
    

    while(!flag)
    {  
		 
		System.out.println("Enter the log file name");
		file = in.nextLine();

     		 if(file.contains(".log"))     
         	 flag = true;
			   
     }
 

		return file;
	}



		public static void main(String args[])throws IOException,FileNotFoundException,ParseException
		{
			

					//Creates an object of P3A1_Yellajosyula_ryellajo class  
					P3A1_Yellajosyula_ryellajo x = new P3A1_Yellajosyula_ryellajo(getFileName());	

         				 //Hash Maps to hold th results
					Map<String,Integer> result,result1,result2;
					Map<String,Date> result3;

					System.out.println("Total number of files in the log :\t "+x.getFileCount());
					
				 	 result = x.getMaxRevisions();

				 	for(Map.Entry<String,Integer> m:result.entrySet())
				 	{  
  					 System.out.println("File with the most number of revisions: "+m.getKey());
  					 System.out.println("Number of revisions: "+m.getValue());  
  					}  


				 	 result1 = x.getMaxUserCommits();

				 	for(Map.Entry<String,Integer> m:result1.entrySet())
				 	{  
  					 System.out.println("File with the most user commits: "+m.getKey());
  					 System.out.println("Number of user commits: "+m.getValue());  
  				}  

  					result2 = x.MaxUserCommits();

				 	for(Map.Entry<String,Integer> m:result2.entrySet())
				 	{  
  					 System.out.println("User with the most commits:"+m.getKey().substring(9));
  					 System.out.println("Number of commits done by this user: "+m.getValue());  
  					}  

  					result3 =x.earliestCommits();

				 	for(Map.Entry<String,Date> m:result3.entrySet())
				 	{  
            			    	
					System.out.println("File:"+m.getKey()+"\nEarliest commit date: "+m.getValue());  
  				    	Map<String,Integer> Fresults = x.userMostCommits(m.getKey());
					for(Map.Entry<String,Integer> f:Fresults.entrySet())
					{
					  System.out.println("User(s) with most commits for this file is"+f.getKey());
					  System.out.println("Total commits\t"+f.getValue()+"\n"); 
					} 
             
          }  

  					
  					

		}

}

