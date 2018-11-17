import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class MultiClientHandler extends Thread{
        private Socket client;
        private BufferedReader login,keyboard,in;
        private PrintWriter out,outputStream=null,clientOutputStream=null;
        private String received,array[], userID;
        private int intLoginID=0,days=0,years=0,numberOfYears=0,year=0,day=0,month=0;
        public MultiClientHandler(Socket socket){
               client=socket;
        try{
                in=new BufferedReader(new InputStreamReader(client.getInputStream()));
                out=new PrintWriter(client.getOutputStream(),true);
           }
           catch(IOException e){
                e.printStackTrace();
                }
       }



public  void CheckDays(int m)
{
    switch (m)
    {
        case 1:
            days+=31;
            break;
        case 2:
            days+=28;
            break;
        case 3:
            days+=31;
            break;
        case 4:
            days+=30;
            break;
        case 5:
            days+=31;
            break;
        case 6:
            days+=30;
            break;
        case 7:
            days+=31;
            break;
        case 8:
            days+=31;
            break;
        case 9:
            days+=30;
            break;
        case 10:
             days+=31;
             break;
        case 11:
            days+=30;
             break;
        case 12:
            days+=31;      
    }           
    
}

public  void CheckYears()
{
  numberOfYears=(year)-2000;
  days+=numberOfYears*365;

     for(int y=2000;y<year;y++)
    {
             if(y%4==0)
             {
              days++;
             }
    }
}

public void convertDate() throws IOException
{ 
  
  String array[],data;
  BufferedReader dateConversion=null;
  PrintWriter julianDate=null;
  dateConversion=new BufferedReader(new FileReader("MovieShowInfo.txt"));
    julianDate=new PrintWriter(new FileWriter("MovieShowInfo2.txt"));
     while((data=dateConversion.readLine())!=null) 
     {
     days=0;
     array=data.split(":"); 
     day=Integer.parseInt(array[2].substring(0,2));
     month=Integer.parseInt(array[2].substring(3, 5));
     year=Integer.parseInt(array[2].substring(6));
     days+=day;
     
     for(int i=1;i<month; i++)
          CheckDays(i);
       
       
     CheckYears();
     julianDate.println(array[0]+":"+array[1]+":"+days+":"+array[3]+":"+array[4]+":"+array[5]+":"+array[6]+":"+array[7]+":"+array[8]);
     
     
     }
      julianDate.close();

        
}
 
public void DaysToDate()
{
  int day=0,month=0,year=0,remainingDays=0;
  year=2000+((days/365));
  remainingDays=days%365;
  day=(remainingDays%31)+1;
  month=(remainingDays/31)+1;
  System.out.println(day+"/"+month+"/"+year);
  out.println(array[0]+":"+array[1]+":"+day+"/"+month+"/"+year+":"+array[3]+":"+array[4]+":"+array[5]+":"+array[6]+":"+array[7]+":"+array[8]);
   
}

public void CategoryAvailability()throws IOException
{
  BufferedReader categoryFound=null;
  int  intCategoryAvailable=0,remainingDays=0,compareDays=0;
  String category=received.substring(1),data="",reply="";
  categoryFound=new BufferedReader(new FileReader("MovieShowInfo2.txt"));
   days=0;
     array=data.split(":");
     day=Integer.parseInt(received.substring(1,3));
     month=Integer.parseInt(received.substring(4,6));
     year=Integer.parseInt(received.substring(7));
     days+=day;
      
     
     for(int i=1;i<month; i++)
          CheckDays(i);
          CheckYears();


  while((data=categoryFound.readLine())!=null){
   array=data.split(":");
   compareDays=Integer.parseInt(array[2]);
     

   if(days==(compareDays)){
     
       reply+=data;
       intCategoryAvailable=1;
       
     }
  }
  if(intCategoryAvailable==1)
  {
      DaysToDate();  
       
      
 }
    else
       out.println("Category not found");

}


public void RegisterAccount() throws IOException
{
 String array[]=received.split(":");
  if(array.length==4)
  {
      try
        {
         clientOutputStream=new PrintWriter(new FileWriter("ClientInfo.txt",true));
         System.out.println(received.substring(1));
         clientOutputStream.println(received.substring(1));
         out.println("Registered successfully");

         }finally
                {
                  if(clientOutputStream!=null)
                    {
                      clientOutputStream.close();
                    }
                }
       
  }
  else
     {
          out.println("Invalid due to string size");
     } 

}
public void ListTheProgramme()throws IOException
{ 
 
  String[] array;
  BufferedReader in=null;
  try{   
       in=new BufferedReader(new FileReader("MovieShowInfo.txt"));
      String data,reply=""; 
       while((data=in.readLine())!=null) {
       array=data.split(":");
       reply+=data; 
       
       }
        out.println(reply);
     }finally{
              if(in!=null)
                 in.close();
             }

}

public void Login()throws IOException
{
 String message=received.substring(1),data;
 String array2[]=message.split(":");
 if(intLoginID==1)
 {
  out.println("User have logged in");
 }
 else
    {
 
     if(array2.length!=2)
     out.println("Invalid length");
     login=new BufferedReader(new FileReader("ClientInfo.txt"));
     while((data=login.readLine())!=null){
     array=data.split(":");
 
     if((array2[0].equals(array[0]))&&(array2[1].equals(array[1]))){
       intLoginID=1;
       userID=array2[0];
      break;
     }
   }
 
  if(intLoginID==1)
       out.println("+OK");
    else
       out.println("Invalid ID/Password"); 
   }
}
public void Channel() throws IOException
{
  BufferedReader channel=null;
  int intChannel=0;
  String showChannel=received.substring(1),data,reply="";
  channel=new BufferedReader(new FileReader("MovieShowInfo.txt"));

  while((data=channel.readLine())!=null) {
   array=data.split(":");
   if(showChannel.equals(array[1])) {
     reply+=data;
     intChannel=1;
    }
}
 if(intChannel==1)
    out.println(reply);
 else
    out.println("Channel not found");

}
public void SubCategory()throws IOException
{
  BufferedReader subCategory=null;
  int intSubCategory=0;
  String strSubCategory=received.substring(1),data,reply="";
  subCategory=new BufferedReader(new FileReader("MovieShowInfo.txt"));
  
  while((data=subCategory.readLine())!=null) {
   array=data.split(":");
   if(strSubCategory.equals(array[7])) {
     reply+=data;
     intSubCategory=1;
    }
}
 if(intSubCategory==1)
    out.println(reply);
 else
    out.println("Sub Category not found");
}
public void TotalCost()
{

}

public void CostPerCategory()
{
   


    
}

public void ListCategory()throws IOException
{
  BufferedReader categoryFound=null;
  int  intCategory=0;
  String category=received.substring(1),data,reply="";
  categoryFound=new BufferedReader(new FileReader("MovieShowInfo.txt"));

  while((data=categoryFound.readLine())!=null){
   array=data.split(":");
   if(category.equals(array[6])){
       reply+=data;
       intCategory=1;
     }
  }
  if(intCategory==1)
       out.println(reply);
    else
       out.println("Category not found");

}

public void Logout()
{
 if(received.equals("Logout") || (received.equals("logout")))
 {
   intLoginID=0;
   out.println("Logout Successful");
 }
else
     out.println("Invalid Command");
 if (intLoginID==0)
     out.println("You have not loggged in");

}
public void RecordTvShowMovie()throws IOException
{

  if (intLoginID==1)
  {
   BufferedReader idFound=null;
   int foundmovieID=0,duration,intHours,intMinutes,endTime,totalMinutes,remainingMinutes,remainMinutes2,saveRemainingMinutes;
   String movieID=received.substring(1),data,reply="",timeArray[],strEndTime,strTotalMinutes;
   idFound=new BufferedReader(new FileReader("MovieShowInfo.txt")); 

   while((data=idFound.readLine())!=null) {
    array=data.split(":");
    if(movieID.equals(array[0])) {
       foundmovieID=1;
 
     days=0;
     day=Integer.parseInt(array[2].substring(0,2));
     month=Integer.parseInt(array[2].substring(3,5));
     year=Integer.parseInt(array[2].substring(6));
     days+=day;


     for(int i=1;i<month; i++)
          CheckDays(i);
          CheckYears();


       break; 
       }
   }
   if(foundmovieID==0)
      out.println("ID not found");
   else{
        outputStream=new PrintWriter(new FileWriter("Viewing.txt",true));
        duration=Integer.parseInt(array[4]);
        
        String strHours=array[3].substring(0,2);
        String strMinutes=array[3].substring(3,5);
        
        System.out.println(strHours+"."+strMinutes); 
        
        intHours=Integer.parseInt(strHours);
        intMinutes=Integer.parseInt(strMinutes);
        endTime=intMinutes+duration;
        if(endTime>=60)
        {
        System.out.println(">60"+endTime);
        remainingMinutes=endTime%60;
          if(remainingMinutes>=60)
          {        
           intHours+=2;
           remainMinutes2=remainingMinutes%60;
           saveRemainingMinutes=remainingMinutes;
          }
          else
           saveRemainingMinutes=remainingMinutes;
           strEndTime=String.valueOf(intHours+String.valueOf(remainingMinutes));
           outputStream.println(userID+":"+array[0]+":"+days+":"+array[3]+":"+strEndTime);
      }
        else 
         {
          totalMinutes=intMinutes+duration;
          System.out.println(totalMinutes);
          strTotalMinutes=String.valueOf(totalMinutes);
          
          outputStream.println(userID+":"+array[0]+":"+days+":"+array[3]+":"+strHours+"."+strTotalMinutes);
         }
      out.println("The program has been recorded successfully");
      outputStream.close();
    }
   }
}

public void CheckShowID()throws IOException
{
 BufferedReader showID=null;
 int intshowID=0;
 String id=received.substring(1),data;
 showID=new BufferedReader(new FileReader("MovieShowInfo.txt"));
 
 while((data=showID.readLine())!=null){
   array=data.split(":");
   System.out.println(array[0]);
   if(id.equals(array[0])){
      out.println(data);
      intshowID=1;
      break;
      }
   }
   if(intshowID==0)
      
     out.println("ID not found");
}
public void CheckTimeSlot() throws IOException
{
 BufferedReader timeSlot=null;
 int intTimeSlot=0;
 String timeSlotData=received.substring(1),data,reply="";
 timeSlot=new BufferedReader(new FileReader("MovieShowInfo.txt"));
 while((data=timeSlot.readLine())!=null) {
        array=data.split(":");
   System.out.println(array[3]);
   if(timeSlotData.equals(array[3])){
      reply+=data;
      intTimeSlot=1;
     }
  }
  if(intTimeSlot==1)
     out.println(reply);
  else
     out.println("That particular timeslot is not found");
}
public void ProgrammeAvailable()
{

}
public void run(){
     
    try{
        convertDate(); 
      do{
         received="";
         received=in.readLine();
                 
         switch(received.charAt(0))
         {    
          case 'A':
          case 'a':
          CategoryAvailability(); 
          break;

          case 'B':
          case 'b':
          ProgrammeAvailable(); 
          break;

          case 'C':
          case 'c':
          ListTheProgramme();
          break;

          case 'D':
          case 'd':
          CostPerCategory();
          break;

          case 'E':
          case 'e':
          TotalCost(); 
          break;

          case 'F':
          case 'f':
          ListCategory(); 
          break;

          case 'G':
          case 'g':
          CheckTimeSlot();
          break;

          case 'H':
          case 'h':
          CheckShowID();
          break;
          
          case 'I':
          case 'i':
          RecordTvShowMovie();
          break;
          case 'J':
          case 'j':
          RegisterAccount();
          break;

          case 'K':
          case 'k':
          Login();
          break;

          case 'L':
          case 'l':
          Logout();
          break;
          case 'Q':
          out.println("Thanks for using the system");
          break;
          case 'M':
          case 'm':
          Channel();
          break;
          case 'N':
          case 'n': 
          SubCategory();      
          default:
                out.println("-ERROR");
          }
        }while(!received.equals("QUIT"));
 }
   catch(IOException e) {
          e.printStackTrace();
      }

try{
        if(client!=null){
        System.out.println("Closing down conection...");
        client.close();
        }
   }catch(IOException e){
        e.printStackTrace();
        } 
}
}





