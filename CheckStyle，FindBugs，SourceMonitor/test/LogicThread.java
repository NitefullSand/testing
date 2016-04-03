/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Zhao Qinpei
 */
public class LogicThread extends Thread{
    Socket socket;
    InputStream is;
    OutputStream os;
    public LogicThread(Socket socket){
        socket = socket;
        start();
    }
    public void run(){
       byte[] b = new byte[1024];
       try{
           os = socket.getOutputStream();
           is = socket.getInputStream();
           for(int i = 0; i < 3; i++){
               int n = is.read();
               byte[] response = logic(b,0,n);
               os.write(response);
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           close();
       }
    }
    //close the connection
    private void close(){
        try{
            os.close();
            is.close();
            socket.close();
        }catch(Exception e){}
    }
    /**
     * logic method, echo
     * @param b client sends data to buffer
     * @param off starting point
     * @param len valid data length
     * @return a byte array
     */
    private byte[] logic(byte[] b, int off, int len){
        byte[] response = new byte[len];
        System.arraycopy(b, 0, response, 0, len);
        return response;
    }
}





