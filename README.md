# LogFileAnalysis
This project mainly focuses on analysing Log file data of a centralised repository in an organisation using JAVA.It looks for trends in the log data  and user commits  which is achieved through following activities.

Activity 1:
============

In this activity, the P3A1_Yellajosyula_ryellajo class is created to support various functions for processing the user provided log file.Some of them are as below:

•	Total number of  files are in the logging data :

getFileCount() method of P3A1_Yellajosyula_ryellajo class is used and each of the entry in the log file is processed using BufferedReader to output the total number of files in the complete log.

•	File with the most number of revisions:

getMaxRevisions() method defined in P3A1_Yellajosyula_ryellajo class is called to fetch the file  that has maximum revisions value associated with it in the entire log.

String processing,Type Casting (Strings to integer)and type comparision concepts are used in this regard to output the fileName along with the revisions associated with it as Key,Value pairs using a Hash Map. 

•	File with the most number of users committing things to it:

getMaxUserCommits() method defined in P3A1_Yellajosyula_ryellajo class is called to fetch the file  with the most number of users committing things to it in the entire log.

Each file and its associated users are first created as a Hash Map having String values(file Name) as Key and List of Strings (its users) as Value pairs.Then the HashMap with maximum value for size of its Value List is found.This is the file which has maximum number of users associated with a file. Desired Output having the fileName along with the number of users associated with it as Key,Value pairs using a Hash Map is displayed to the user console. 

•	Earliest commit to a file:

earliestCommits() method defined in P3A1_Yellajosyula_ryellajo class is called to fetch the file  and the earliest commit done to it in the entire log.Also,the user with most commits and its value for each file are displayed as output.

Each file and the dates of commits done to it are processed from the file and using date Comparision functions like before() and after(),the earliest commit done to each file is found and stored in a HashMap.Then using this HashMap,for each of the file i.e. for every Key in the HashMap,the user who did maximum commits to it and its number is found by calling userMostCommits()function. 

Concepts of Strings processing,selection sort,Date comparisions,HashMaps with different types of  Key-Value Pairs are used in achieving the desired output which is as follows:
 <file name>, 
<earliest commit of this file> 
<user(s) with most commits>
 <num of commits by this user>

If a file has only 2 users who committed changes to it,then both the users are displayed as users with most commits with a value of 1 for number of commits.

•	User with the most number of commits:

MaxUserCommits() method of class is called to fetch the user who made maximum commits in the entire log.First,Each of the distinct users in the log file are fetched and a HashMap with these users as Keys is created having value as integer count of zero associated with every key.Then,the file is processed again to find the occurrence of each of these users in the log and the count value is incremented for every match found to each Key of the HashMap.Then,the HashMap is processed to find the maximum Value ie..count value and the associated Key which is the user.

Concepts of String processing,Selection Sort,HashMaps and Lists are used in achieving the desired output. 

Activity 2:
===========
In this activity, P3A2_Yellajosyula_ryellajo class uses OpenCSV library and the CSVWriter class to output below list of values to a CSV file named Details.csv in corresponding columns.

First column: The  name of every file changed

Second column: Total number of commits don’t to this file

Third column: Earliest or first commit done to this file

Fourth column: Last date of commit to this file.

P3A1_Yellajosyula_ryellajo  class and its methods are called using its class instance in achieving above results.Concepts of String processing,Selection Sort,Date comparisions,HashMaps and Lists are used in achieving the desired output.


Activity 3:
==========

In this activity, P3A3_Yellajosyula_ryellajo class uses OpenCSV library and the CSVWriter class to output below list of values to a CSV file named TimeChunks.csv in corresponding columns.

•	Columns: Time period chunks - Years
•	Rows: Top 20% of Users with the most number of commits

TimePeriod is set to Years and the top 20% users with most number of commits done in each year is fetched  using static methods in P3A3_Yellajosyula_ryellajo class and public methods of P3A1_Yellajosyula_ryellajo class.This is output to the TimeChunks.csv file with years forming the column header and the top 20% of users as their values in corresponding rows.

Approach :
==========
For this activity,initially all the users in the log are grouped by years using a HashMap with year as key and list of users as values.Then,the log file is processed again to match the occurrence of both key and each of value in the list of users.
A new HashMap having user indexes from value list of above hashMap as keys and the corresponding count of commits is formed for every key of previous map.This new HashMap is then sorted based on values i.e.number of commits done by this user in a particular year.Then Top 20% of the size of the value list is fetched using Math.ceil() function and  a new HashMap with this year as key and the values for  keys from above sorted list as values is created.

This activity displays the list of all years in the log to the user console and provides him an oppurtunity to get the results for specified years.

If he chooses to fetch all the results,then all the years and corresponding top 20% users based on commits done is output as result.
Concepts of String processing,Comparator interface and its methods, HashMaps,sorting them based on Values and Lists are used in achieving the desired output.

Applications:
==============
One of the most significant application of this project is to perform various analytics in IT industry.

1.To understand the stability of a software:

Using this project,we can find the number of commits done to files in a Code repository for a software product.Analysing the data reflects the files with most commits as unstable part of software where most bugs/issues are reported.

2.Identify and revert any bad check-in made to the code repository using last commit made to a file.

3.Interpret  the Performance of employees in a project based on number of commits they did in a code repository.

4.Recognise most complex areas of a software product by finding out the module files with maximum user commits to it.This also states the module which needs most resources.

This code can be used in analysing any log file provided by a user following the format as in the sample log file emacs.log.With slight changes to the code,it can be reused to fit the analysis for varied formats.



