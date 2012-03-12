package dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import mytools.LogsReaderWriter;


import dao.bean.User;
import dao.inf.UserDAO;

/**
 * 这是一个访问数据的方法类，它实现了dao.inf.UserDAO接口，以下方法的功能请参阅接口中的说明
 * @author Devon
 *
 */
public class UserDaoImpl implements UserDAO {
	
	/**
	 * selectList方法中的参数，指示筛选条件，表示在sid精确查询时取得该sid的所有字段
	 */
	public static final int UP_AND_DOWN_WITH_ALLINFO = 0;
	
	/**
	 * selectList方法中的参数，指示筛选条件，此为取得所有用户并会删除一些不需要的字段
	 */
	public static final int UP_AND_DOWN = 1;

	/**
	 * selectList方法中的参数，指示筛选条件，此为只取得在线用户并会删除一些不需要的字段
	 */
	public static final int ONLY_UP = 2;

	/**
	 * 用户数据文件
	 */
	private static final File file = new File("data/user.txt");
	
	/**
	 * 单例
	 */
	private static UserDaoImpl userDaoImpl = new UserDaoImpl();

	
	private UserDaoImpl() {

	}
	
	/**
	 * 获得该类的唯一实例
	 * @return
	 */
	public static UserDaoImpl getInstance(){
		return userDaoImpl;
	}

	public synchronized boolean deleteUser(String sid) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				if (rowArr[0].equals(sid)) {
					continue;
				}
				stringBuffer.append(rowIn + "\n");
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			//e.printStackTrace();
			flag = false;
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}

		}
		
		if (flag == false) {
			return flag;
		} else {
			return flag;
		}

	}

	public synchronized String getNextSid() {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		String nextSid = null;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int maxSid = 0;
			String rowIn = null;
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				int temp = Integer.parseInt(rowArr[0]);
				if (maxSid < temp) {
					maxSid = temp;
				}
			}
			nextSid = String.format("%06d", maxSid + 1);

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

		return nextSid;
	}

	public synchronized boolean insertUser(User user) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(user.getSid() + ",");
		stringBuffer.append(user.getSpassword() + ",");
		stringBuffer.append(user.getSname() + ",");
		stringBuffer.append(user.getSsex() + ",");
		stringBuffer.append(user.getNage() + ",");
		stringBuffer.append(user.getSaddress() + ",");
		stringBuffer.append(user.getNisonlin() + ",");
		stringBuffer.append(user.getDregtime());

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, true);
			fileWriter.write(stringBuffer.toString() + "\n");
			fileWriter.flush();
		} catch (IOException e) {
			//e.printStackTrace();
			flag = false;
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return flag;

	}

	public synchronized boolean resetAllPWD() {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				for (int i = 0;;) {
					if (i == 1) {
						stringBuffer.append("123456,");
						i++;
						continue;
					}
					stringBuffer.append(rowArr[i]);
					if (++i == rowArr.length) {
						stringBuffer.append("\n");
						break;
					}
					stringBuffer.append(",");
				}
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			flag = false;
			//e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		return flag;
	}

	public synchronized boolean resetPWD(String sid) {
		return resetPWD(sid, "123456");
	}
	
	public synchronized boolean resetPWD(String sid, String newPWD) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String rowIn = null;
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				if (rowArr[0].equals(sid)) {
					for (int i = 0;;) {
						if (i == 1) {
							stringBuffer.append(newPWD + ",");
							i++;
							continue;
						}
						stringBuffer.append(rowArr[i]);
						if (++i == rowArr.length) {
							stringBuffer.append("\n");
							break;
						}
						stringBuffer.append(",");
					}
				} else {
					stringBuffer.append(rowIn + "\n");
				}
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			flag = false;
			//e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return flag;
	}
	
	public synchronized Vector<Vector<String>> selectList(String sid, String sname, int state) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		try {// 从文件中加载所有用户信息到Vector<Vector<String>>
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				Vector<String> row = new Vector<String>();
				for (String string : rowArr) {
					row.add(string);
				}
				data.add(row);
			}
			
			if(state == 1){//用于usermanager面板
				Iterator<Vector<String>> i = data.iterator();
				while (i.hasNext()) {
					Vector<String> rowTemp = i.next();
					//rowTemp.removeElementAt(6);// 先移除是否在线项，以供显示
					rowTemp.removeElementAt(1);// 再移除密码项，以供显示
				}
				
			}else if(state == 2){//用于servermanager面板
				Iterator<Vector<String>> i = data.iterator();
				while (i.hasNext()) {
					Vector<String> rowTemp = i.next();
					if(rowTemp.get(6).equals("离线")){
						i.remove();
						continue;
					}
					rowTemp.removeElementAt(7);// 先移除注册时间项，以供显示
					rowTemp.removeElementAt(5);// 再移除地址项，以供显示
					rowTemp.removeElementAt(1);// 再移除密码项，以供显示
				}
			}
			
			String sidNoSpc = null;// <--sid == null
			String snameNoSpc = null;// <--sname == null
			if (sid != null) {// 去除用户输入的空格
				sidNoSpc = sid.replaceAll(" ", "");
			}
			if (sname != null) {// 去除用户输入的空格
				snameNoSpc = sname.replaceAll(" ", "");
			}

			if ((sidNoSpc == null || "".equals(sidNoSpc))
					&& (snameNoSpc == null || "".equals(snameNoSpc))) {// 空查询，返回所有用户。。。。
				return data;

			} else if ((sidNoSpc == null || "".equals(sidNoSpc))
					&& (snameNoSpc != null && !"".equals(snameNoSpc))) {// 有实际意义的sname时。。。。
				Iterator<Vector<String>> i2 = data.iterator();
				while (i2.hasNext()) {
					Vector<String> rowTemp = i2.next();
					if (!rowTemp.get(1).contains(snameNoSpc)) {// 遍历所有用户，与参数作比较，!equals的删除，
						i2.remove();
					}
				}
				return data;

			} else if ((sidNoSpc != null && !"".equals(sidNoSpc))
					&& (snameNoSpc == null || "".equals(snameNoSpc))) {// 有实际意义的sid时。。。。
				Iterator<Vector<String>> i3 = data.iterator();
				while (i3.hasNext()) {
					Vector<String> rowTemp = i3.next();
					if (!rowTemp.get(0).equals(sidNoSpc)) {// 遍历所有用户，与参数作比较，!equals的删除，
						i3.remove();
					}
				}
				return data;

			} else if ((sidNoSpc != null && !"".equals(sidNoSpc))
					&& (snameNoSpc != null && !"".equals(snameNoSpc))) {// sid和sname都有时。。。。
				Iterator<Vector<String>> i4 = data.iterator();
				while (i4.hasNext()) {
					Vector<String> rowTemp = i4.next();
					if (!rowTemp.get(0).equals(sidNoSpc)
							|| !rowTemp.get(1).equals(snameNoSpc)) {// 遍历所有用户，与参数作比较，!equals的删除，
						i4.remove();
					}
				}
				return data;
			}

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}

		}

		return null;
	}

	public synchronized User selectList(String sid) {
		Vector<Vector<String>> data = selectList(sid, "", UserDaoImpl.UP_AND_DOWN_WITH_ALLINFO);
		Iterator<Vector<String>> i = data.iterator();
		if (i.hasNext()) {
			Vector<String> row = i.next();
			User user = new User();
			user.setSid(sid);
			user.setSpassword(row.get(1));
			user.setSname(row.get(2));
			user.setSsex(row.get(3));
			user.setNage(row.get(4));
			user.setSaddress(row.get(5));
			user.setNisonlin(row.get(6));
			user.setDregtime(row.get(7));
			return user;

		} else {
			return null;
		}

	}


	public synchronized boolean upOrDown(String sid, String tag) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String rowIn = null;
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				if (rowArr[0].equals(sid)) {
					for (int i = 0;;) {
						if (i == 6) {
							stringBuffer.append(tag + ",");
							i++;
							continue;
						}
						stringBuffer.append(rowArr[i]);
						if (++i == rowArr.length) {
							stringBuffer.append("\n");
							break;
						}
						stringBuffer.append(",");
					}
				} else {
					stringBuffer.append(rowIn + "\n");
				}
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			flag = false;
			//e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		return flag;
	}

	public synchronized boolean upOrDown(String tag) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				for (int i = 0;;) {
					if (i == 6) {
						stringBuffer.append(tag + ",");
						i++;
						continue;
					}
					stringBuffer.append(rowArr[i]);
					if (++i == rowArr.length) {
						stringBuffer.append("\n");
						break;
					}
					stringBuffer.append(",");
				}
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			flag = false;
			//e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		return flag;
	}

	public synchronized boolean updateUser(User user) {
		//在使用前保证文件已存在
		LogsReaderWriter.createNewFile(file.getPath());
		
		boolean flag = true;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				if (rowArr[0].equals(user.getSid())) {
					stringBuffer.append(user.getSid() + ",");
					stringBuffer.append(user.getSpassword() + ",");
					stringBuffer.append(user.getSname() + ",");
					stringBuffer.append(user.getSsex() + ",");
					stringBuffer.append(user.getNage() + ",");
					stringBuffer.append(user.getSaddress() + ",");
					stringBuffer.append(user.getNisonlin() + ",");
					stringBuffer.append(user.getDregtime() + "\n");

				} else {
					stringBuffer.append(rowIn + "\n");
				}
			}
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (FileNotFoundException e) {
			flag = false;
			//e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return flag;
	}



	/*public User selectUserAllInfo(String sid) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {// 从文件中加载所有用户信息到Vector<Vector<String>>
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String rowIn = null;
			while ((rowIn = bufferedReader.readLine()) != null) {
				String[] rowArr = rowIn.split(",");
				Vector<String> row = new Vector<String>();
				for (String string : rowArr) {
					row.add(string);
				}
				data.add(row);
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}

		Iterator<Vector<String>> i3 = data.iterator();
		while (i3.hasNext()) {
			Vector<String> rowTemp = i3.next();
			if (!rowTemp.get(0).equals(sid)) {// 遍历所有用户，与参数作比较，!equals的删除，
				i3.remove();
			}
		}

		Vector<String> row = data.get(0);
		User user = new User();
		user.setSid(sid);
		user.setSpassword(row.get(1));
		user.setSname(row.get(2));
		user.setSsex(row.get(3));
		user.setNage(row.get(4));
		user.setSaddress(row.get(5));
		user.setNisonlin(row.get(6));
		user.setDregtime(row.get(7));
		
		return user;

	}*/
}
