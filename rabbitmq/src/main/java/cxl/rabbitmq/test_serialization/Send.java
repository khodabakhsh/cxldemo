package cxl.rabbitmq.test_serialization;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "serialization";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		Message msg = new Message();
		msg.setName("测试serialization");
		msg.setContent("这就是测试的内容了吗？");
		
		// Serialize to a byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(msg);
		out.close();
		// Get the bytes of the serialized object
		byte[] buf = bos.toByteArray();
		bos.close();

		channel.basicPublish("", QUEUE_NAME, null, buf);
		System.out.println(" [x] Sent '" + msg.getName() + "'");

		channel.close();
		connection.close();
	}
}
