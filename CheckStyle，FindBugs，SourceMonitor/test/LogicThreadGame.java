/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Zhao Qinpei
 */
public class LogicThreadGame extends Thread{
    Socket s;
    static Random r = new Random();
    
    public LogicThreadGame(Socket s){
        this.s = s;
        start();
    }
    
    public void run(){
        // random number [0, 50]
        int randomNumber = Math.abs(r.nextInt()%51);
        //times user guessed
        int guessNumber = 0;
        InputStream is = null;
        OutputStream os = null;
        byte[] data = new byte[2];
        try{
            is = s.getInputStream();
            os = s.getOutputStream();
            while(true){
                byte[] b = new byte[1024];
                int n = is.read(b);
                String send = new String(b, 0, n);
                if(send.equals("quit")){
                    break;
                }
                try{
                    int num = Integer.parseInt(send);
                    guessNumber++;
                    data[1] = (byte)guessNumber;
                    if(num > randomNumber){
                        data[0] = 1;
                    }else if(num < randomNumber){
                        data[0] = 2;
                    }else{
                        data[0] = 0;
                        guessNumber = 0;
                        randomNumber = Math.abs(r.nextInt()%51);
                    }
                    os.write(data);
                }catch(Exception e){
                    data[0] = 3;
                    data[1] = (byte)guessNumber;
                    os.write(data);
                    break;
                }
                os.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                is.close();
                os.close();
                s.close();
            }catch(Exception e){}
        }
    }
}
