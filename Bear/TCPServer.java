import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class TCPServer {
	private static int port = 8819;
	private static ServerSocket ss;
	static Socket s;
	static BufferedReader br;
	static BufferedWriter bw;
	
	public static void main(String[] args) throws IOException{
		//new TCPServer();
	/*
		while(sc.hasNextLine()){
			System.out.println("2");
			String line = sc.nextLine();
			System.out.println("Bear:" + line);
			sendMessage(line);
		}
		*/
		
		ss = new ServerSocket(8819);
		System.out.println("ready");
		s = ss.accept();
		System.out.println("connected");
		
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		Thread t = new Thread(new R());
		t.setDaemon(true);
		t.start();
		
		Scanner sc = new Scanner(System.in);

		
        try {
            while (true) {
               
                    PrintWriter out =
                        new PrintWriter(s.getOutputStream(), true);
                    out.println(sc.nextLine());
                
            }
        }
        finally {
        	s.close();
            ss.close();
        }
	}
	
	public TCPServer() throws IOException{
		ss = new ServerSocket(8819);
		System.out.println("ready");
		s = ss.accept();
		System.out.println("connected");
		
		
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		

		System.out.println("0");
		Thread R = new Thread(new R());
		R.setDaemon(true);
		R.run();

		System.out.println("1");
		
		System.out.println("?");
	}
	
	static Scanner sc = new Scanner(System.in);
	public static void sendMessage(String line) throws IOException{
		bw.write(line);
		bw.flush();
	}

}

class R implements Runnable{
	
	public void run() {
		while(true){
			try {
				String line = TCPServer.br.readLine();
				if(line == null){
					break;
				}
				System.out.println("Martha:" + line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
