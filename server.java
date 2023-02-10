/*@author - Swati Pawar 
 * HTTP Server Program
 * makes connection on port  
 * waits for client to get connected
 * */

package DC;
import java.net.*;
import java.time.Instant;
import java.io.IOException;
import java.io.*;
public class server  {

	public static void main(String[] args) throws IOException {
		Instant instant = Instant.now();
      
		/*Checking if command has less than two arguments */
		if(args.length<2) {
			System.out.println("wrong number of inputs");
			System.out.println(args.length);
			return;
		    }
		/*Initialize Sockets */
		int portno = Integer.parseInt(args[1]);
		ServerSocket Server = new ServerSocket(portno);
		Socket connection = Server.accept();
	    
		/* Initializing input-output buffer */
		InputStreamReader in = new InputStreamReader(connection.getInputStream());
		BufferedReader buff = new 	BufferedReader(in);
		PrintWriter out = new PrintWriter(connection.getOutputStream());	
	
		String line = buff.readLine();
		
		/*read the response from client */
        while( line != null && line.length() > 0 )
        {
            out.println( "Command: " + line );
            String[] args1 = line.split(" ");
  			String filename = args1[1];
  			  			
            /*Reading the GET Request and printing Headers */
            if(line.contains("GET")) {
               out.println("statusMessage : GET request received");
               out.println("200 ok");
           	   }
               else {
            	    out.println("statusMessage : PUT request received");
                    }
            //Adding HTTP headers
          	out.println("Server : localhost");
            out.println("Content-Type: text/html; charset=utf-8");
            out.println("Connection : Keep Alive");
       	    out.println("Last-Modified:" + instant );
            
            /*Reading index.html and text file */
            if(line.contains("index.html") || line.contains("txt")) {
              	
            	/*checking if file exits on a path*/
              	File filecheck = new File(filename);
               	if(!filecheck.exists()) {
               		out.println("404 File Not Found");
                  	}
               	    else {
               	                 	
                 	StringBuilder contentBuilder = new StringBuilder();
            	    FileReader file = new FileReader( filename);
           
                       try {
                    	   BufferedReader bf = new BufferedReader(file);
                           String fileinfo;
                    	    while ((fileinfo = bf.readLine()) != null) {
                                  contentBuilder.append(fileinfo);
                         	       }
                       bf.close();
                       String result = contentBuilder.toString();
                       out.println(result);
                	   }
                       catch(IOException ex) {
                    	   ex.printStackTrace();
                           }
               	       }
                }
            /*Reading the PUT Request */
            if (line.contains("PUT")) {
            	
            	StringBuilder contentBuilder2 = new StringBuilder();
            	FileReader inputfile = new FileReader( filename);
               	
                try {	
                	BufferedReader bf2 = new BufferedReader(inputfile);
                	String content;
                    while ((content = bf2.readLine()) != null) {
                          contentBuilder2.append(content + "\n");
                         }
                    bf2.close();
                    String filecontent = contentBuilder2.toString();
                    
                    /*Writing the contents to the new file */
                    FileWriter writer = new FileWriter( "C:/Data_Communication/Programs/testdownloaded.txt");
      			    writer.write(filecontent); 
      			    out.println("200 OK File Created");
      			    writer.close();
      				 
                	}
                    catch(IOException ex) {
                    	ex.printStackTrace();
                        }
			
                 }
            out.flush();
            line = buff.readLine();   
          
          }
        /*closing the connections */
        in.close();
        out.close();
        connection.close();     
        Server.close();
	}
        
	
}
	