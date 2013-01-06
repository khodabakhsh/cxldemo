package cxl.rabbitmq.test_serialization;

import java.io.Serializable;

//Ðèimplements serilizable
public class Message implements Serializable {
	public String name;
	public String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[name:" + name + " , content:" + content + "]";
	}

}
