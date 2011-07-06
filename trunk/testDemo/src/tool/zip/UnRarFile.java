package tool.zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 解压rar文件(需要系统安装Winrar 软件)
 * @author Michael sun
 */
public class UnRarFile {
    /**
     * 解压rar文件
     * 
     * @param targetPath
     * @param absolutePath
     */
    public void unRarFile(String targetPath, String absolutePath) {

        try {

            // 系统安装winrar的路径
            String cmd = "C:\\Program Files\\WinRAR\\WinRAR.exe";
            String unrarCmd = cmd + " x -r -p- -o+ " + absolutePath + " "
                    + targetPath;

            Runtime rt = Runtime.getRuntime();
            Process pre = rt.exec(unrarCmd);
            InputStreamReader isr = new InputStreamReader(pre.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
                System.out.println(line);
            }

            bf.close();
        } catch (Exception e) {
            System.out.println("解压发生异常");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String targetPath = "c:\\";
        String rarFilePath = "c:\\test.rar";
        UnRarFile unrar = new UnRarFile();
        unrar.unRarFile(targetPath, rarFilePath);
    }

}
