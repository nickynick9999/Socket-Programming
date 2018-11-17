import java.io.*;
import java.net.*;
public class MultiEchoServer1{
 private static ServerSocket servSocket;
 public static void main(String[] args)throws IOException{
 int PORT=Integer.parseInt(args[0]);
   try{
       servSocket=new ServerSocket(PORT);
      }
      catch(IOException e){
        System.out.println("\nUnable to set port!");
        System.exit(1);
}
do{
        Socket client=servSocket.accept();
        System.out.println("\nNew client accepted.\n");
        MultiClientHandler handler=new MultiClientHandler(client);
        handler.start();
  }while(true);
}
}

