package com.Jason.app.util;




import com.Jason.app.Constants;

import java.util.Random;

/**
 * @author chengwu 封装签名类，包括单次，多次以及下载签名
 */
public class Sign {

	/**
	 * 返回图片识别的签名
	 * 

	 *            包含用户秘钥信息

	 *            bucket名

	 *            超时时间
	 * @return 返回base64编码的字符串

	 */
        public static   String appSign() {
            String appId = Constants.TC_APPID;
            String secretId =  Constants.TC_SecretId;
            String secretKey =  Constants.TC_SecretKey;
            long now = System.currentTimeMillis() / 1000;   
            int rdm = Math.abs(new Random().nextInt());
            String plainText = String.format("a=%s&b=%s&k=%s&t=%d&e=%d", appId, "", secretId, now, now + 2592000);
            byte[] hmacDigest = null;

            try {
                    hmacDigest = CommonCodecUtils.HmacSha1(plainText, secretKey);
            } catch (Exception e) {


            }
            byte[] signContent = new byte[hmacDigest.length + plainText.getBytes().length];
            System.arraycopy(hmacDigest, 0, signContent, 0, hmacDigest.length);
            System.arraycopy(plainText.getBytes(), 0, signContent, hmacDigest.length, plainText.getBytes().length);

            return CommonCodecUtils.Base64Encode(signContent);
        }
}
