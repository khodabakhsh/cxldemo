package testCompass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

/**
 * 搜索实体信息类
 */
@Searchable
public class Info {

	@SearchableId
	private int id;

	@SearchableProperty(store = Store.YES)
	private String title;

	@SearchableProperty(store = Store.YES)
	private String content;

	@SearchableProperty(store = Store.YES)
	private int number;
	
	@SearchableProperty(store = Store.YES)
	private Date createtDate;

	public Date getCreatetDate() {
		return createtDate;
	}

	public void setCreatetDate(Date createtDate) {
		this.createtDate = createtDate;
	}

	public Info() {
	}

	public Info(int id, String title, String content) {

		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@SearchableProperty(store = Store.YES,name="testDate")
	public String getTestDate() {
		DateFormat dfWithSpace = new SimpleDateFormat("yyyy MM dd");
		DateFormat dfWithSymbol = new SimpleDateFormat("yyyy-MM-dd");
		return dfWithSpace.format(createtDate) + " "
				+ dfWithSymbol.format(createtDate);
	}
}
