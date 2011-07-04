/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.Date;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

/**
 *
 * @author cxl
 */
@Table(name="topics")
public class Topics extends ActiveRecordBase{
    @Id private Integer id;
    @Column
    private String title;
    
    @Column
    private String author;
    
    @Column
    private Date lastPostTime;
    
    
    @Column
    private String postText;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Date getLastPostTime() {
		return lastPostTime;
	}


	public void setLastPostTime(Date lastPostTime) {
		this.lastPostTime = lastPostTime;
	}


	public String getPostText() {
		return postText;
	}


	public void setPostText(String postText) {
		this.postText = postText;
	}

}
