package com.labelwall.util.storage;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-12-04.
 */
public class FileTypeHepler {

    public static FileType getFileType(byte[] buff) {
        //assert断言用于程序的调试，
        if (buff != null && buff.length > 4) {
            byte[] bytes = Arrays.copyOfRange(buff, 0, 4);//将指定数组的指定范围复制到一个新数组
            String magic = bytesHex(bytes);
            for (FileType item : FileType.values()) {//遍历枚举类型
                if (magic.startsWith(item.getValue())) {//startsWith测试此字符串是否以指定的前缀开始。
                    return item;
                }
            }
        }
        return null;
    }

    private static String bytesHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes == null || bytes.length <= 0)
            return null;
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
}
