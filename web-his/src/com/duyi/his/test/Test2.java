package com.duyi.his.test;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class Test2 {
    public static void main(String[] args) {
        Digester md51 = new Digester(DigestAlgorithm.MD5);
        Digester md52 = new Digester(DigestAlgorithm.MD5);

        String digestHex1 = md51.digestHex("123");
        String digestHex2 = md52.digestHex("123");
        System.out.println(digestHex1);
        System.out.println(digestHex2);
    }
}
