package com.mry5l.apiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 * @author YJL
 * @version 1.0
 */
public class SignUtil {

    /**
     * 加密
     * @param body 请求参数
     * @param secretKey 密钥
     * @return 加密结果
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
