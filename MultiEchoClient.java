import java.net.*;
import java.io.*;

public class MultiEchoClient{
 private static InetAddress host;
 private static Socket link;
 private static BufferedReader in;
 private static PrintWriter out;
 private static BufferedReader keyboard;

public static void main(String[] args) throws IOException{
 if((args.length<2)||(args.length>2))
   throw new IllegalArgumentException("Parameter(s):<Server> {<Port>}");
 String host=args[0];
 int PORT=(args.length==2)?Integer.parseInt(args[1]):44444;
 try{
     link=new Socket(host,PORT);
     in=new BufferedReader(new InputStreamReader(link.getInputStream()));
     out=new PrintWriter(link.getOutputStream(),true);
     keyboard=new BufferedReader(new InputStreamReader(System.in));
     String message,response;
     do{
        System.out.println("Welcome to KduTV protocol, what can I do for you?('QUIT'to exit):");
        message =keyboard.readLine();
        out.println(message);
        response=in.readLine();
        System.out.println(response);
      }while(!message.equals("QUIT"));
}
catch(UnknownHostException e){
 System.out.println("\nHost ID not found\n");
}
catch(IOException e){
e.printStackTrace();
}
finally{
 try{
     if(link!=null){
        System.out.println("Closing down connection....");
        link.close();
}
}
catch (IOException e){
e.printStackTrace();
}
}
}
}


