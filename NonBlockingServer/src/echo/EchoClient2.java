package echo;
import java.util.Set;  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.InetSocketAddress;  
import java.net.InetAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
 
/**  
 * Echo客户端2  
 * @author finux  
 */ 
public class EchoClient2 {  
    public static void main(String[] args) {  
        ByteBuffer buffer = ByteBuffer.allocate(EchoServer.BUFFER_SIZE);  
        Selector selector = null;  
        SocketChannel sc = null;  
        try {  
            selector = Selector.open();  
            sc = SocketChannel.open();  
            sc.configureBlocking(false);  
            sc.register(selector, SelectionKey.OP_CONNECT);  
            sc.connect(new InetSocketAddress(InetAddress.getByName(EchoServer.HOST), EchoServer.PORT));  
            print("客户端启动，准备连接...");  
              
            boolean writed = false;  
            boolean down = false;  
            while (!down && selector.select() > 0) {                  
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  
                for (SelectionKey key: selectionKeys) {                   
                    //int ops = key.readyOps();  
                    //if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {  
                    if (key.isConnectable()) {  
                        print("完成连接！");  
                        if (sc.isConnectionPending()) {  
                            sc.finishConnect();  
                        }  
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);                  
                    }  
                    //if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE && !writed) {  
                    if (key.isWritable() && !writed) {  
                        //从准备IO中读取内容  
                        System.out.print("Input(bye to end): ");  
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   
                        String s = br.readLine();  
                        if (s != null && !s.trim().equals("")) {  
                            buffer.clear();  
                            buffer.put(s.getBytes());  
                            buffer.flip();  
                            sc.write(buffer);  
                            writed = true;  
                            if (s.equals("bye")) {  
                                down = true;  
                                break;  
                            }  
                        }  
                    }  
                    //if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ && writed) {  
                    if (key.isReadable() && writed) {  
                        buffer.clear();  
                        sc.read(buffer);  
                        buffer.flip();  
                        byte[] b = new byte[buffer.limit()];  
                        buffer.get(b);  
                        print(new String(b));  
                        writed = false;  
                    }  
                }  
                selectionKeys.clear();  
            }  
        } catch(IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    private static void print(String s) {  
        System.out.println(s);  
    }  
} 