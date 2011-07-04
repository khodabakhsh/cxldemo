package testCompass;

import java.util.Date;

import org.apache.lucene.queryParser.QueryParser;
import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.core.config.CompassConfiguration;
import org.junit.Test;

/**
 * Compass实现C/R/U/D
 * 
 * @author keven.kan
 * 
 */
public class testCompass {

	private Compass compass;

	/**
	 * 创建索引
	 */
	public testCompass() {

		CompassConfiguration conf = new CompassConfiguration().configure(
				"/testCompass/compass.info.cfg.xml").addClass(Info.class);

		compass = conf.buildCompass();
	}

	/**
	 * 添加
	 */
	@Test
	public void testSave() {
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginLocalTransaction(); // 开启事务
			Info f = new Info();
			f.setId(1);
			f.setTitle("标题呀");
			f.setContent("内容呀ww,"
				);
			f.setCreatetDate(new Date());
			f.setNumber(555);
			session.save(f); // 保存数据
			tx.commit(); // 提交事务
			System.out.println("添加成功!");
		} catch (CompassException e) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
			if (compass != null)
				compass.close();
		}
	}

	/**
	 * 搜索
	 */
	@Test
	public void testSearch() {
		CompassSession session = compass.openSession();
		CompassHits hits = null;
		CompassTransaction tx = null;

		try {
			tx = session.beginLocalTransaction();
			String queryString  = QueryParser.escape("2011-07-04");
			hits = session.find(queryString);
			System.out.println("共搜索" + hits.length() + "条记录!");

			if (hits.length() > 0) {
				for (int j = 0; j < hits.length(); j++) {
					Info i = (Info) hits.data(j);
					String ht = hits.highlighter(j).fragment("testDate");
					if (ht != null) {
						System.out.println("找到匹配的日期格式： "+ht);
					}
					System.out.println(i.getId());
					System.out.println(i.getTitle());
					System.out.println(i.getContent());
					System.out.println(i.getCreatetDate());
					System.out.println(i.getTestDate());
					System.out.println(i.getNumber());
				}
			}
		} catch (CompassException e) {
			e.printStackTrace();
		} finally {
			if (hits != null)
				hits.close();
			if (session != null)
				session.close();
			if (compass != null)
				compass.close();
		}
	}

	/**
	 * 使用Load查询
	 */
//	@Test
//	public void testLoad() {
//		CompassSession session = compass.openSession();
//
//		CompassTransaction tx = null;
//		try {
//			tx = session.beginLocalTransaction();
//
//			Info i = session.load(Info.class, 1);
//			// Info i =session.get(Info.class, 1);
//
//			System.out.println(i.getId());
//			System.out.println(i.getTitle());
//			System.out.println(i.getContent());
//			System.out.println(i.getCreatetDate());
//
//		} catch (CompassException e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null)
//				session.close();
//			if (compass != null)
//				compass.close();
//		}
//	}
//
//	/**
//	 * 删除
//	 */
//	@Test
//	public void testDel() {
//
//		CompassSession session = compass.openSession();
//		CompassTransaction tx = null;
//
//		try {
//			tx = session.beginLocalTransaction();
//			session.delete(Info.class, 1);
//			session.commit();
//			System.out.println("删除成功!");
//		} catch (CompassException e) {
//			if (tx != null)
//				session.rollback();
//		} finally {
//			if (session != null)
//				session.close();
//			if (compass != null)
//				compass.close();
//		}
//	}
//
//	/**
//	 * 修改
//	 */
//	@Test
//	public void testUpdate() {
//		CompassSession session = compass.openSession();
//		CompassTransaction tx = null;
//
//		try {
//			tx = session.beginLocalTransaction();
//			Info i = session.load(Info.class, 1); // 先查询再修改
//
//			i.setTitle("Android是基于Linux内核的操作系统，是Google公司在2007年11月5日公布的手机操作系统");
//			i.setContent("2010年末数据显示，仅正式推出两年的操作系统Android"
//					+ "已经超越称霸十年的诺基亚（Nokia）Symbian OS系统，"
//					+ "采用Android系统主要手机厂商包括宏达电子（HTC）、"
//					+ "三星（SAMSUNG）、摩托罗拉（MOTOROLA）、LG、Sony Ericsson、"
//					+ "魅族M9等，使之跃居全球最受欢迎的智能手机平台，" + "Android系统不但应用于智能手机，"
//					+ "也在平板电脑市场急速扩张。");
//			session.save(i);
//			System.out.println("更新成功!");
//			session.commit();
//		} catch (CompassException e) {
//			if (tx != null)
//				session.close();
//			if (compass != null)
//				compass.close();
//		}
//	}
//	public static void main(String[] args) {
//		List<String> list = new ArrayList<String>();
//		list.add(null);
//		System.out.println(list.size());
//	}

}