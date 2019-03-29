package thread;

import java.io.*;

//多线程多层目录文件拷贝
public class Thread3 extends Thread {
    private String str;//源地址
    private String dstr;//目的地址

    public Thread3(String str, String dstr) {
        this.str = str;
        this.dstr = dstr;
        this.start();//启动线程
        try {
            this.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        File s = new File(str); //创建源文件
        File ds = new File(dstr);//创建源文件
        if (s.isDirectory()) {//判断源文件是否为目录;
            if (!ds.exists()) {//判断目的目录是否存在
                ds.mkdirs(); //如果目的目录不存在，则创建目的目录
            }
            String[] spath = s.list();//取出源目录下的所有子目录;
            for (String sptr : spath) {//遍历所有子目录，进行递归实现
                File es = new File(s, sptr); //创建源文件
                File des = new File(ds, sptr);//创建源文件
                Thread3 t = new Thread3(es.getAbsolutePath(), des.getAbsolutePath());//增加另一个拷贝线程
            }
        } else {//源文件不是目录，则直接进行拷贝
            System.out.println(Thread.currentThread().getName() + s.getAbsolutePath() + "开始拷贝");
            copy(s, ds);//拷贝源文件到目的文件ds中
        }
    }

    public void copy(File s, File d) { //文件拷贝
        InputStream fis = null; //文件输入流声明
        OutputStream fos = null;//文件输出流声明
        try {
            fis = new FileInputStream(s);
            fos = new FileOutputStream(d);
            int n ;
            byte[] buffer = new byte[1024 * 1024];
            while ((n = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
                fis.close();
                System.out.println(Thread.currentThread().getName() + s.getAbsolutePath() + "结束拷贝");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

}
