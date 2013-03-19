package echo;
import java.io.IOException;  
import java.net.InetAddress;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.util.Set;  
 
/**  
 * Echo服务器  
 * @author finux  
 */ 
public class EchoServer {  
    public final static int BUFFER_SIZE = 1024; //默认端口  
    public final static String HOST = "127.0.0.1";  
    public final static int PORT = 8888;  
      
    public static void main(String[] args) {  
        ServerSocketChannel ssc = null;  
        //缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);  
        Selector selector = null;  
        try {  
            selector = Selector.open();  
            ssc = ServerSocketChannel.open();  
            ssc.socket().bind(new InetSocketAddress(InetAddress.getByName(HOST), PORT));  
            ssc.configureBlocking(false);  
            ssc.register(selector, SelectionKey.OP_ACCEPT);       
            print("服务器启动，准备好连接...");  
            System.out.println(selector);
            while (selector.select() > 0) {       
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  
                for (SelectionKey key: selectionKeys) {  
                    if (key.isAcceptable()) {  
                        SocketChannel sc = ssc.accept();  
                        print("有新的连接！地址：" + sc.socket().getRemoteSocketAddress());  
                        sc.configureBlocking(false);  
                        sc.register(selector, SelectionKey.OP_READ);  
                        // 不要写成:  
                        // sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
                        // 毕竟这样多注册的无用的事件SelectionKey.OP_WRTE  
                        // 如果是这样，在完成accept后，CPU也许会跑到100%  
                          
                    }  
                    //same to if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {  
                    if (key.isReadable()) {   
                        SocketChannel sc = (SocketChannel)key.channel();  
                        print("有新的读取！地址：" + sc.socket().getRemoteSocketAddress());                        
                        buffer.clear();                       
                        sc.read(buffer);  
                        buffer.flip();  
                        byte[] b = new byte[buffer.limit()];  
                        buffer.get(b);  
                        String s = new String(b);  
                        if (s.equals("bye")) {  
                            print("断开连接：" + sc.socket().getRemoteSocketAddress());    
                            //断开连接后，取消此键的通道到其选择器的注册  
                            key.cancel();  
                            sc.close();  
                            continue;  
                        }  
                        print("读取的内容为：" + s);     
                        buffer.clear();  
                        s = "echo: " + s;  
                        buffer.put(s.getBytes());  
                        buffer.flip();  
                        sc.write(buffer);  
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