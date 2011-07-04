    package tool;   
      
    import java.io.BufferedReader;   
    import java.io.BufferedWriter;   
    import java.io.File;   
    import java.io.FileFilter;   
    import java.io.FileInputStream;   
    import java.io.FileOutputStream;   
    import java.io.IOException;   
    import java.io.InputStream;   
    import java.io.InputStreamReader;   
    import java.io.OutputStream;   
    import java.io.OutputStreamWriter;   
    import java.io.Reader;   
    import java.io.UnsupportedEncodingException;   
    import java.io.Writer;   
      /**
       * 转换gb2312编码的文件为utf-8
       * @author caixl
       *
       */
    public class FileEncodeConverter {   
      
        // 原文件目录   
        private static String srcDir = "c:/src";   
        // 转换后的存放目录   
        private static String desDir = "c:/des";    
        // 源文件编码   
        private static String srcEncode = "gb2312";   
        // 输出文件编码   
        private static String desEncode = "utf-8";   
           
        // 处理的文件过滤   
        private static FileFilter filter = new FileFilter() {   
            public boolean accept(File pathname) {   
                // 只处理：目录 或是 .java文件   
                if (pathname.isDirectory()   
                        || (pathname.isFile() && pathname.getName().endsWith(   
                                ".java")))   
                    return true;   
                else  
                    return false;   
            }   
        };   
           
        /**  
         * @param file  
         */  
        public static void readDir(File file)   
        {   
            File[] files = file.listFiles(filter);   
            for (File subFile : files) {   
                // 建立目标目录   
                if (subFile.isDirectory()) {   
                    File file3 = new File(desDir + subFile.getAbsolutePath().substring(srcDir.length()));   
                    if (!file3.exists()) {   
                        file3.mkdir();   
                    }   
                    file3 = null;   
                    readDir(subFile);   
                } else {   
                    System.err.println("一源文件：\t"+subFile.getAbsolutePath() + "\n目标文件：\t" + (desDir + subFile.getAbsolutePath().substring(srcDir.length())));   
                    System.err.println("-----------------------------------------------------------------");   
                    try {   
                        convert(subFile.getAbsolutePath(), desDir + subFile.getAbsolutePath().substring(srcDir.length()), srcEncode, desEncode);   
                    } catch (UnsupportedEncodingException e) {   
                        e.printStackTrace();   
                    } catch (IOException e) {   
                        e.printStackTrace();   
                    }   
                }   
            }   
        }   
           
        /**  
         *   
         * @param infile    源文件路径  
         * @param outfile   输出文件路径  
         * @param from  源文件编码  
         * @param to    目标文件编码  
         * @throws IOException  
         * @throws UnsupportedEncodingException  
         */  
        public static void convert(String infile, String outfile, String from,   
                String to) throws IOException, UnsupportedEncodingException {   
            // set up byte streams   
            InputStream in;   
            if (infile != null)   
                in = new FileInputStream(infile);   
            else  
                in = System.in;   
            OutputStream out;   
            if (outfile != null)   
                out = new FileOutputStream(outfile);   
            else  
                out = System.out;   
      
            // Use default encoding if no encoding is specified.   
            if (from == null)   
                from = System.getProperty("file.encoding");   
            if (to == null)   
                to = System.getProperty("file.encoding");   
      
            // Set up character stream   
            Reader r = new BufferedReader(new InputStreamReader(in, from));   
            Writer w = new BufferedWriter(new OutputStreamWriter(out, to));   
      
            // Copy characters from input to output. The InputStreamReader   
            // converts from the input encoding to Unicode,, and the   
            // OutputStreamWriter   
            // converts from Unicode to the output encoding. Characters that cannot   
            // be   
            // represented in the output encoding are output as '?'   
            char[] buffer = new char[4096];   
            int len;   
            while ((len = r.read(buffer)) != -1)   
                w.write(buffer, 0, len);   
            r.close();   
            w.flush();   
            w.close();   
        }   
           
      
        public static void main(String[] args) {   
            // 建立目标文件夹   
            File desFile = new File(desDir);   
            if (!desFile.exists()) {   
                desFile.mkdir();   
            }   
            desFile = null;   
      
            File srcFile = new File(srcDir);   
            // 读取目录 循环转换文件   
            readDir(srcFile);   
            srcFile = null;   
        }   
      
    }   

