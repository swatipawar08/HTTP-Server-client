/*@author - Swati Pawar 
 * HTTP Client Program
 * makes connection with server 
 * sends GET and PUT request
 * */

package DC;
import java.net.*;
import java.io.*;
public class client {
	
	public static void main(String[] args) throws IOException {
		
		/*Checking if command has less than four arguments */
		if(args.length<4) {
			System.out.println("wrong number of inputs");
			System.out.println(args.length);
			return;
		    }
		
		String host = args[1]; 
		int portno = Integer.parseInt(args[2]); 
		String command = args[3]; 
		String filename = args[4];
		
		/*Checking if command is for our local server or Real webserver*/
		if (args[1].equalsIgnoreCase("host")) {
			
			/*making connection to local host */
			Socket connection = new Socket("localhost",portno);
		
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			InputStreamReader in = new InputStreamReader(connection.getInputStream());	
			BufferedReader buff = new 	BufferedReader(in);
			
	        /*Sending GET or PUT Request to our local server */
			out.println( command + " " + filename + " " + "HTTP/1.0" );
			
			out.flush();
			
			String line = buff.readLine();
			while( line != null )
	         {
	           System.out.println( line );
	            line = buff.readLine();
	         }
			
			/*closing open connections*/
			in.close();
	        out.close();
	        buff.close();
	        connection.close();
			
		} 
		/*Connecting to real web server */
		else 
		{    
			 Socket s = new Socket(host, portno);
			
			 PrintWriter cmd = new PrintWriter(s.getOutputStream());

			 cmd.println(command + " " +  filename + " " + "HTTP/1.1");
			 
			 cmd.println("Host:" + host );
			 cmd.println("");
			 cmd.flush();

		    /*BufferedReader to read the server response */
		    BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));
		    String outStr;

		    /*Prints response */
		    while((outStr = bufRead.readLine()) != null){
		        System.out.println(outStr);
		        }
		    /*Closing write port and buffer */
		    bufRead.close();
		    cmd.close();
		    s.close();

		}
		
   
	}
	
}