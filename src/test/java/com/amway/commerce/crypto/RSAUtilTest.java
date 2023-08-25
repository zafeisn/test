package com.amway.commerce.crypto;

import com.amway.commerce.string.ByteUtil;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-15
 * @desc:
 */
public class RSAUtilTest {

    String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKFlrMYfh7B+Z5NpKMo0JlShzZ7kutLhAIrSYJBo2fEY" +
            "y8yx1fPXuWc1HMqRh4z3BpuYHyZP/fTDQ8j0nFn+xGUCAwEAAQ==";

    String priKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAoWWsxh+HsH5nk2koyjQmVKHNnuS6" +
            "0uEAitJgkGjZ8RjLzLHV89e5ZzUcypGHjPcGm5gfJk/99MNDyPScWf7EZQIDAQABAkA3Vsxp1k/J" +
            "JV5QPgNdaYonBJ+jNpwUSE/OSZoQidMIhkL7/Je32KJaiEXCuOjlVfgRaoL525OkmnevK1oR0Sh1" +
            "AiEA35ewQMKeD+RO1wmBkszeEkoD4r8nP2RvI5/7qM/iZcMCIQC4ykCTN2Vcgf4LkqoidEJoLPUC" +
            "rFPwW7G2H4cXo+mCtwIgYU05vnTzJCdOx/WzFZdh7MOY/UwCYGvskaxFcClHdNECIHddvoTaPdGZ" +
            "7t7O6LPE4bilgccuRjJ3KL56cytkKEzVAiEAkE0220pmIyFB8aOEFf0tkGt5Oz2Y4PPxDl2INwoq" +
            "L3Q=";

    private String plainText = "中文abc123#@%";

    /**
     * 生成 RSA密钥对
     */
    @Test
    public void initKey() throws Exception {
        Map<String, Key> stringKeyMap = RSAUtil.initKey(512);
        Key publicKey = stringKeyMap.get(RSAUtil.PUBLIC_KEY);
        String enPubKey = Base64Util.encode(publicKey.getEncoded());
        /**
         * 8453055559475674011736755119565709572790241553346573425642976115674048203696166205997227239862146020462592918482900896623277351069239865589266202341852261+65537
         */
        System.out.println(publicKey);
        /**
         * MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKFlrMYfh7B+Z5NpKMo0JlShzZ7kutLhAIrSYJBo2fEY
         * y8yx1fPXuWc1HMqRh4z3BpuYHyZP/fTDQ8j0nFn+xGUCAwEAAQ==
         */
        System.out.println(enPubKey);
        Key privateKey = stringKeyMap.get(RSAUtil.PRIVATE_KEY);
        String enPriKey = Base64Util.encode(privateKey.getEncoded());
        /**
         * 8453055559475674011736755119565709572790241553346573425642976115674048203696166205997227239862146020462592918482900896623277351069239865589266202341852261+
         * 2898341570059323293982584254570106349240421715141841272069568584087027742881920137422462610835799909546244100312311108285517149305677508517038260850075765
         */
        System.out.println(privateKey);
        /**
         * MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAoWWsxh+HsH5nk2koyjQmVKHNnuS6
         * 0uEAitJgkGjZ8RjLzLHV89e5ZzUcypGHjPcGm5gfJk/99MNDyPScWf7EZQIDAQABAkA3Vsxp1k/J
         * JV5QPgNdaYonBJ+jNpwUSE/OSZoQidMIhkL7/Je32KJaiEXCuOjlVfgRaoL525OkmnevK1oR0Sh1
         * AiEA35ewQMKeD+RO1wmBkszeEkoD4r8nP2RvI5/7qM/iZcMCIQC4ykCTN2Vcgf4LkqoidEJoLPUC
         * rFPwW7G2H4cXo+mCtwIgYU05vnTzJCdOx/WzFZdh7MOY/UwCYGvskaxFcClHdNECIHddvoTaPdGZ
         * 7t7O6LPE4bilgccuRjJ3KL56cytkKEzVAiEAkE0220pmIyFB8aOEFf0tkGt5Oz2Y4PPxDl2INwoq
         * L3Q=
         */
        System.out.println(enPriKey);

    }

    /**
     * 从公钥字符串中获取公钥
     */
    @Test
    public void getPublicKey() throws Exception {

        System.out.println(pubKey.length());
        PublicKey publicKey = RSAUtil.getPublicKey(pubKey);
        // 845305555947567401173675511956570957279024155334657342564297611567404820369616620599722723986214602046259291848290089662327735106923986558926620234185226165537
        System.out.println(publicKey);
    }

    /**
     * 从私钥字符串中获取私钥
     */
    @Test
    public void getPrivateKey() throws Exception {

        System.out.println(pubKey.length());
        PrivateKey privateKey = RSAUtil.getPrivateKey(priKey);
        // 8453055559475674011736755119565709572790241553346573425642976115674048203696166205997227239862146020462592918482900896623277351069239865589266202341852261
        // 2898341570059323293982584254570106349240421715141841272069568584087027742881920137422462610835799909546244100312311108285517149305677508517038260850075765
        System.out.println(privateKey);
    }

    /**
     * RSA签名算法以及验签
     */
    @Test
    public void test() throws Exception {
        String sign = RSAUtil.sign("123", priKey, "UTF-8");
        // hConRazBdzcQyK1zq1QjTBeb2/5l+gFaG4b2KYT2TiQ4xst/SbfA4+UsvPg2I3SA1nYzWUTNkQDz4YivCCcBRQ==
        System.out.println(sign);
        boolean verify = RSAUtil.verify("123", sign, pubKey, "UTF-8");
        // true
        System.out.println(verify);
        String signs = "hConRazBdzcQyK1zq1QjTBeb2/5l+gFaG4b2KYT2TiQ4xst/SbfA4+UsvPg2I3SA1nYzWUTNkQDz4YivCCcBR1==";
        boolean verifys = RSAUtil.verify("123", signs, pubKey, "UTF-8");
        // fasle
        System.out.println(verifys);
    }

    /**
     * RSA公钥加密算法以及解密
     */
    @Test
    public void test01() throws Exception {
        // 加密
        String encrypt = RSAUtil.encrypt(plainText, RSAUtil.getPublicKey(pubKey), "UTF-8");
        // 解密
        String decrypt = RSAUtil.decrypt(encrypt, RSAUtil.getPrivateKey(priKey));
        // 中文abc123#@%
        System.out.println(decrypt);
    }
}