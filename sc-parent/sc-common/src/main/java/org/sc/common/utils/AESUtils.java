package org.sc.common.utils;



import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * AES加密工具类
 *
 * @author Webb Dong
 */
public class AESUtils {

    private AESUtils() {
    }

    private static final byte[] AES_IV = initIv();
    /**
     * 密钥算法
     * <p>
     * java6支持56位密钥，bouncycastle支持64位
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     * <p>
     * JAVA6 支持PKCS5PADDING填充方式
     * <p>
     * Bouncy castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
     *
     * @return byte[] 二进制密钥
     **/
    public static byte[] initkey() throws Exception {
        // 实例化密钥生成器
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");

        // 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
        kg.init(256);

        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥
        // 生成密钥
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param plaintext 待加密数据
     * @param key       密钥
     * @return String 加密并且Base64编码之后的数据密文字符串
     */
    public static String encrypt(String plaintext, String key) {
        try {
            // 还原密钥
            Key k = toKey(Base64.decodeBase64(key));

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);

            // 执行操作
            return Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败：" + plaintext, e);
        }
    }

    /**
     * 解密数据
     *
     * @param ciphertext 待解密数据
     * @param key        密钥
     * @return byte[] 解密后的数据
     */
    public static String decrypt(String ciphertext, String key) {
        try {
            Key k = toKey(Base64.decodeBase64(key));

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");

            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);

            // 执行操作
            return new String(cipher.doFinal(Base64.decodeBase64(ciphertext)), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败：" + ciphertext, e);
        }
    }

    /**
     * 使用标准的AES加密算法(加密结果表示成16进制字符串)
     *
     * @param originalText  待加密的字符串
     * @param encryptionKey 密钥(16进制字符串表示)
     * @return
     */
    public static String encryptCBC(String originalText, String encryptionKey, String charset) {
        try {
            // 获得加密的密钥流
            byte[] md = Base64.decodeBase64(encryptionKey);
            SecretKeySpec key = new SecretKeySpec(md, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] byteContent = null;
            if (StringUtils.isBlank(charset)) {
                byteContent = originalText.getBytes(charset);
            } else {
                byteContent = originalText.getBytes();
            }
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用标准的AES解密算法(密文需要是16进制字符串)
     *
     * @param ciphertext    密文
     * @param decryptionKey 密钥(16进制字符串表示)
     * @return
     */
    public static String decryptCBC(String ciphertext, String decryptionKey, String charset) {
        try {
            // 获得解密的密钥流
            byte[] md = Base64.decodeBase64(decryptionKey);
            SecretKeySpec key = new SecretKeySpec(md, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] decoderBtye = Base64.decodeBase64(ciphertext);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(decoderBtye);
            return new String(result, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始向量的方法, 全部为0。针对AES算法,IV值一定是128位的(16字节).
     */
    private static byte[] initIv() {
        int blockSize = 16;
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; ++i) {
            iv[i] = 0;
        }
        return iv;
    }
}
