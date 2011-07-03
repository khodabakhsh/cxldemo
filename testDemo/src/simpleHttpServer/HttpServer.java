
package simpleHttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Threaded Network Server 这是一个简单的多线程HTTP服务器 采用多线程来处理高并发的用户请求
 * 运行这个程序启动服务器，然后在浏览器地址栏输入：http://localhost:8888/，即可看到返回结果。
 */
public class HttpServer {
    public static void main(String [] args){
        HttpServer hs=new HttpServer();
        int i=1, port=8888;
        Socket received=null;
        try{
            ServerSocket server=new ServerSocket(port);
            while(true){
                received=server.accept();
                if(received!=null){
                    hs.new ProcessThread(i++,received).start();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    class ProcessThread extends Thread
    {
        private int thread_number=0;
        private Socket received=null;
        
        public ProcessThread(int thread_number, Socket received) {
            super();
            this.thread_number = thread_number;
            this.received = received;
        }

        public void run() {
            System.out.println("第"+thread_number+"个处理线程启动了……");
            if(received!=null){
                try{
                    System.out.println("连接用户的地址："+received.getInetAddress().getHostAddress());
                    InputStream in=received.getInputStream();
                    BufferedReader d= new BufferedReader(new InputStreamReader(in));
                    String result=d.readLine();
                    while(result!=null && !result.equals("")){
                        System.out.println(result);
                        result=d.readLine();
                    }
                    OutputStream out=received.getOutputStream();
                    PrintWriter outstream=new PrintWriter(out,true);
                    String msg1="<html><head><title></title></head><body><h1>收到！</h1></body></html>";
                    outstream.println("HTTP/1.0 200 OK");// 返回应答消息,并结束应答
                    outstream.println("Content-Type:text/html;charset=GBK");
                    outstream.println();// 根据 HTTP 协议, 空行将结束头信息
                    outstream.println(msg1);
                    outstream.flush();
                    outstream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    try{
                        received.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

