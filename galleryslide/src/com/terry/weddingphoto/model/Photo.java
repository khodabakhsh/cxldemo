package com.terry.weddingphoto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.datanucleus.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Blob;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-15 上午10:12:16
 */
@Entity
public class Photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3719075717115159161L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	@Enumerated
	private String filename;
	@Temporal(TemporalType.TIMESTAMP)
	private Date cdate;
	@Enumerated
	private String remark;
	@Enumerated
	private int comment;// 0允许评论 -1不允许 大于0的数字表示有多少个评论
	@Enumerated
	private Blob data;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Comment.class, mappedBy = "photo")
	private List<Comment> comments = new ArrayList<Comment>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public Blob getData() {
		return data;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getComment() {
		return comment;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}
}
