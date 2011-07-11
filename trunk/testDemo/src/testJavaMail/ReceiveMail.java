package testJavaMail;
import javax.mail.*;
import java.util.*;
import java.io.*;
/**
 * 接收邮件
 *
 */
public class ReceiveMail {

    //处理任何一种邮件都需要的方法
    private static void handle(Message msg) throws Exception {
        System.out.println("邮件主题:" + msg.getSubject());
        System.out.println("邮件作者:" + msg.getFrom()[0].toString());
        System.out.println("发送日期:" + msg.getSentDate());
    }

    //处理文本邮件
    private void handleText(Message msg) throws Exception {
        this.handle(msg);
        System.out.println("邮件内容:"+msg.getContent());
    }

    //处理Multipart邮件，包括了保存附件的功能
    private static void handleMultipart(Message msg) throws Exception {
        String disposition;
        BodyPart part;

        Multipart mp = (Multipart) msg.getContent();
        //Miltipart的数量,用于除了多个part,比如多个附件
        int mpCount = mp.getCount();
        for (int m = 0; m < mpCount; m++) {
            handle(msg);
            part = mp.getBodyPart(m);
            disposition = part.getDisposition();
            //判断是否有附件
            if (disposition != null && disposition.equals(Part.ATTACHMENT))
            {
                //这个方法负责保存附件
                saveAttach(part);
            } else {
                //不是附件，就只显示文本内容
                System.out.println(part.getContent());
            }
        }
    }

    private static void saveAttach(BodyPart part) throws Exception {
        //得到未经处理的附件名字
        String temp = part.getFileName();
        //除去发送邮件时，对中文附件名编码的头和尾，得到正确的附件名
        //（请参考发送邮件程序SendMail的附件名编码部分）
        String s = temp.substring(8, temp.indexOf("?="));
        //文件名经过了base64编码,下面是解码
        String fileName = base64Decoder(s);
        System.out.println("有附件:" + fileName);

        InputStream in = part.getInputStream();
        File savePath = new File("d:\\保存邮件附件\\");
        if(!savePath.exists()){
        	savePath.mkdirs();
        }
        FileOutputStream writer = new FileOutputStream(new File(savePath.getAbsolutePath()+ File.separator+fileName));
        byte[] content = new byte[255];
        int read = 0;
        while ((read = in.read(content)) != -1) {
            writer.write(content);
        }
        writer.close();
        in.close();
    }
    //base64解码
    private static String base64Decoder(String s) throws Exception {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return (new String(b));
    }

    public static void receive(String host, String username,String password) {
        try {
            //连接到邮件服务器并获得邮件
            Properties prop = new Properties();
            prop.put("mail.pop3.host", host);
            Session session = Session.getDefaultInstance(prop);
            Store store = session.getStore("pop3");
            store.connect(host, username, password);

            Folder inbox = store.getDefaultFolder().getFolder("INBOX");
            //设置inbox对象属性为可读写，这样可以控制在读完邮件后直接删除该邮件
            inbox.open(Folder.READ_WRITE);

            Message[] msg = inbox.getMessages();

            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
            inbox.fetch(msg, profile);

            for (int i = 0; i < msg.length; i++) {
                //标记此邮件的flag标志对象的DELETED位为true,可以在读完邮件后直接删除该邮件，具体执行时间是在调用
                //inbox.close()方法的时候
                msg[i].setFlag(Flags.Flag.DELETED, true);
                handleMultipart(msg[i]);
                System.out.println("****************************");
            }
            if (inbox != null) {
                //参数为true表明阅读完此邮件后将其删除，更多的属性请参考mail.jar的API
                inbox.close(false);
            }
            if (store != null) {
                store.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	receive("pop.139.com","13430363480","XXXXXX");
	}
}