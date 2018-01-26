package com.chenyu.tcp.privatechat;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author yu_chen
 * @create 2018-01-26 16:32
 **/
public class CloseUtil {

    /**
     * 关闭所有的流
     *
     * @param ios
     */
    public static void closeAll(Closeable... ios) {

        for (Closeable io : ios) {
            try {
                if (io != null) {
                    io.close();
                }
            } catch (IOException e) {

            }
        }

    }
}
