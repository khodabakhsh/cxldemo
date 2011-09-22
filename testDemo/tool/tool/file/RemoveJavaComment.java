package tool.file;

import java.io.*;

/**
 *   一个去除java源码注释的小工具
 *   把指定目录下的java源码去掉注释放到另一目录下 
 * 
 * @author caixl , Sep 22, 2011
 *
 */
public class RemoveJavaComment {

	static String srcStr = "E:\\j2seAPIsrc ";
	static String desStr = "E:\\j2seAPIsrc_无注释 ";

	//处理文件 ,指定输入目录和输出目录（单个文件也可以） 
	private void profile(File src, File des) throws Exception {
		StringBuffer content = new StringBuffer();
		String temp;
		int start;
		int end;
		int from = 0;

		BufferedReader br = new BufferedReader(new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(des));

		while ((temp = br.readLine()) != null) {
			content.append(temp);
			content.append("\n ");
		}

		while ((start = content.indexOf("/* ", from)) != -1) {
			end = content.indexOf("*/ ", start) + 2;
			content.delete(start, end);
			from = start;
		}

		from = 0;
		int e;
		//指定from以提高速度 
		while ((start = content.indexOf("// ", from)) != -1) {
			e = content.indexOf("\n ", start);
			end = (e == -1) ? content.length() : (e + 1);
			content.delete(start, end);
			from = start;
		}

		bw.write(content.toString());
		br.close();
		bw.close();
	}

	//处理目录 
	private void prodir(File src, File des) throws Exception {

		des.mkdir(); //创建目录 
		File[] files = src.listFiles();

		for (int i = 0; i < files.length; i++) {
			//下一级目录/文件 
			File nf = new File(des, files[i].getName());
			if (files[i].isDirectory()) {
				//是目录，递归 
				prodir(files[i], nf);
			} else { //文件 
				profile(files[i], nf);
			}
		}
	}

	//输入目录还是文件 
	public void dispReq(File src, File des) throws Exception {
		if (src.isDirectory()) {
			prodir(src, des);
		} else {
			profile(src, des);
		}
	}

	public   static   void   main(String[]   args)   { 
                if   (args.length   >=   2)   { 
                        srcStr   =   args[0]; 
                        desStr   =   args[1]; 
                } 
                File   src   =   new   File(srcStr); 
                File   des   =   new   File(desStr); 
                try   { 
                        new   RemoveJavaComment().dispReq(src,   des); 
                } 
                catch   (Exception   ex)   { 
                        System.out.println(ex); 
                } 
        }}
