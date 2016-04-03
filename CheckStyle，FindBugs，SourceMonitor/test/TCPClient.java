/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * guessing number
 * @author zhapqingpei
 */
public class TCPClient {
    public static void main(String[] args){
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;
        byte[] data = new byte[2];
        
        try{
            socket = new Socket("127.0.0.1", 10001);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            
            br = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.println("Please input the number 0~50:");
                String s = br.readLine();
                if(s.equals("quit")){
                    os.write("quit".getBytes());
                    break;
                }
                boolean b = true;
                try{
                    Integer.parseInt(s);
                }catch(Exception e){
                    b = false;
                }
                if(b){
                    os.write(s.getBytes());
                    is.read(data);
                    switch(data[0]){
                        case 0:
                            System.out.println("equals! You got the right number!");
                            break;
                        case 1:
                            System.out.println("too large");
                            break;
                        case 2:
                            System.out.println("too small");
                            break;
                        default:
                            System.out.println("Other error");                   
                    }
                    System.out.println("You've tried "+data[1]+" times!");
                    if(data[1]>=5){
                        System.out.println("Oops, you lose!");
                        os.write("quit".getBytes());
                        break;
                    }
                } 
                else{
                        System.out.println("Input error");
                    }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                br.close();
                is.close();
                os.close();
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}



