package com.yzb.sec.common.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by brander on 2019/1/27
 */
public class MD5Util {
    public static String md5Salt(String password, String salt) {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");

            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest((password + salt).getBytes());
            // 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
            // mysql的优化思路：
            // 第一步，将数据全部转换成正数：
            String hexString = "";
            for (byte b : bs) {
                // 第一步，将数据全部转换成正数：
                // 解释：为什么采用b&255
                /*
                 * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
                 * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据)
                 * 运算时： b: 0000 0000 0000 0000 0000 0000 1001 1100 255: 0000
                 * 0000 0000 0000 0000 0000 1111 1111 结果：0000 0000 0000 0000
                 * 0000 0000 1001 1100 此时的temp是一个int类型的整数
                 */
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp < 16 && temp >= 0) {
                    // 手动补上一个“0”
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final char[] PRINTABLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679".toCharArray();
    private static final int DEFAULT_MAX_RANDOM_LENGTH = 15;

    public static void main(String[] args) {
        System.out.println(md5Salt("111111", "Lzeh3R9VAkpLIEy"));
        System.out.println(getSalt());
    }

    private static byte[] getNewStringAsBytes() throws NoSuchAlgorithmException {
        SecureRandom randomizer = SecureRandom.getInstance("SHA1PRNG");

        byte[] random = new byte[DEFAULT_MAX_RANDOM_LENGTH];

        randomizer.nextBytes(random);

        return random;
    }

    public static String getSalt() {
        String salt = "qwertyuiop";
        try {
            salt = convertBytesToString(getNewStringAsBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return salt;
    }


    private static String convertBytesToString(byte[] random) {
        char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }
        return new String(output);
    }


}
