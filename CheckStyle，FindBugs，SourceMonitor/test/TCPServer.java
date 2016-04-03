/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.ServerSocket;
import java.net.Socket;

/**
 * guessing number
 * @author zhapqingpei
 */
public class TCPServer {
    public static void main(String[] args){
        try{
            ServerSocket ss = new ServerSocket(10001);
            System.out.println("Server starts");
            while(true){
                Socket s = ss.accept();
                new LogicThreadGame(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}





