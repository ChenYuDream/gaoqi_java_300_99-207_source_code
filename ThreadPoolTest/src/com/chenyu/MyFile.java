package com.chenyu;

import java.io.File;

/**
 * 我的文件对象
 *
 * @author yu_chen
 * @create 2018-01-30 17:55
 **/
public class MyFile {

    /**
     * 文件的MD5
     */
    private String md5;

    /**
     * 当前文件对象
     */
    private File file;

    public MyFile(String md5, File file) {
        this.md5 = md5;
        this.file = file;
    }


    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
