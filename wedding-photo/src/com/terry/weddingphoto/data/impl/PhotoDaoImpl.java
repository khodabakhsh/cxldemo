package com.terry.weddingphoto.data.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.terry.weddingphoto.data.intf.IPhotoDao;
import com.terry.weddingphoto.model.Comment;
import com.terry.weddingphoto.model.Photo;
import com.terry.weddingphoto.util.EMF;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-3-15 下午01:36:04
 */
public class PhotoDaoImpl implements IPhotoDao {

	EntityManager em = EMF.get().createEntityManager();

	
	public boolean savePhoto(Photo photo) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(photo);
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	
	public int saveOrUpdatePhoto(String filename, byte[] data) {
		try {
			Photo photo = null;
			EntityManager em = EMF.get().createEntityManager();
			Query query = em.createQuery("SELECT f FROM "
					+ Photo.class.getName() + " f where f.filename=:filename");
			query.setParameter("filename", filename);
			List<Photo> photos = query.getResultList();
			boolean update = false;
			if (photos == null || photos.size() == 0) {
				photo = new Photo();
				photo.setCdate(new Date());
				photo.setFilename(filename);
				photo.setRemark("");
				photo.setComment(0);
			} else {
				update = true;
				photo = photos.get(0);
			}
			photo.setData(new Blob(data));

			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(photo);
			tx.commit();
			return update ? 0 : 1;
		} catch (Exception e) {
			return -1;
		}
	}

	
	public boolean updatePhoto(String pid, byte[] data) {
		try {
			Photo photo = null;
			EntityManager em = EMF.get().createEntityManager();
			Key key = KeyFactory.stringToKey(pid);
			if (key == null || !key.isComplete())
				return false;
			photo = em.find(Photo.class, key);
			if (photo == null)
				return false;

			photo.setData(new Blob(data));
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(photo);
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public boolean updatePhoto(String pid, String remark, boolean canComment) {
		try {
			Photo photo = null;
			EntityManager em = EMF.get().createEntityManager();
			Key key = KeyFactory.stringToKey(pid);
			if (key == null || !key.isComplete())
				return false;
			photo = em.find(Photo.class, key);
			if (photo == null)
				return false;
			photo.setRemark(remark);

			if (canComment) {
				if (photo.getComment() == -1) {
					photo.setComment(photo.getComments().size());
				}
			} else
				photo.setComment(-1);

			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(photo);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	
	public List<Photo> getPhotos(int start, int limit) {
		Query query = em.createQuery("SELECT f FROM " + Photo.class.getName()
				+ " f order by f.cdate");
		query.setFirstResult(start);
		if (limit > 0)
			query.setMaxResults(limit);
		return query.getResultList();
	}

	
	public Photo getPhotoById(String id) {
		Key key = KeyFactory.stringToKey(id);
		if (key == null || !key.isComplete())
			return null;
		return em.find(Photo.class, key);
	}

	
	public int deleteCommentById(String cid) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			Key key = KeyFactory.stringToKey(cid);
			if (key == null || !key.isComplete())
				return -1;
			Comment c = em.find(Comment.class, key);
			Key key2 = KeyFactory.stringToKey(c.getPid());
			if (key2 == null || !key2.isComplete())
				return -1;
			Photo p = em.find(Photo.class, key2);
			if (p == null)
				return -1;
			p.setComment(p.getComment() - 1);
			if (p.getComment() < 0)
				p.setComment(0);
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(p);
			em.remove(c);
			tx.commit();
			return p.getComment();
		} catch (Exception e) {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	
	public List<Comment> getCommentsByPhotoId(String pid, int start, int limit) {
		Query query = em.createQuery("SELECT c FROM " + Comment.class.getName()
				+ " c where c.pid=:pid order by c.cdate");
		query.setParameter("pid", pid);
		query.setFirstResult(start);
		if (limit > 0)
			query.setMaxResults(limit);
		return query.getResultList();
	}

	
	public int saveComment(Comment comment) {
		try {
			EntityManager em = EMF.get().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			Key key = KeyFactory.stringToKey(comment.getPid());
			if (key == null || !key.isComplete())
				return -1;
			Photo p = em.find(Photo.class, key);
			if (p == null)
				return -1;
			p.setComment(p.getComment() + 1);
			if (p.getComment() < 0)
				p.setComment(0);
			comment.setPhoto(p);
			tx.begin();
			em.persist(p);
			em.persist(comment);
			tx.commit();
			return p.getComment();
		} catch (Exception e) {
			return -1;
		}
	}

	
	public boolean deletePhotoById(String pid) {
		EntityManager em = EMF.get().createEntityManager();
		Key key = KeyFactory.stringToKey(pid);
		if (key == null || !key.isComplete())
			return false;
		Photo p = em.find(Photo.class, key);
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(p);
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public boolean checkPhotoExists(String filename) {
		List<Photo> photos = getPhotosByFilename(filename);
		if (photos == null || photos.size() == 0)
			return false;
		else
			return true;
	}

	@SuppressWarnings("unchecked")
	
	public List<Photo> getPhotosByFilename(String filename) {
		Query query = em.createQuery("SELECT f FROM " + Photo.class.getName()
				+ " f where f.filename=:filename");
		query.setParameter("filename", filename);
		return query.getResultList();
	}

	
	public int getPhotosCount() {
		Query query = em.createQuery("SELECT count(p) FROM "
				+ Photo.class.getName() + " p");
		query.setHint("datanucleus.query.resultSizeMethod", "count");
		return (Integer) query.getSingleResult();
	}
}
