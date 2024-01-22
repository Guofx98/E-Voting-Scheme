package com.example.caserver.utils;

import com.example.caserver.entity.Key;
import sun.misc.BASE64Encoder;

import java.math.BigInteger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class BlindSignature {

    public static Map<String,Object> initKey() throws Exception {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String,Object> map = new HashMap<>();

        map.put("publicKey",publicKey);
        map.put("privateKey",privateKey);

        return map;
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static BigInteger sign(BigInteger blinded, Key privateKey){
        return blinded.modPow(new BigInteger(privateKey.getD()),new BigInteger(privateKey.getN()));
    }
}
