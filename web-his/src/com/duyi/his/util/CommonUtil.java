package com.duyi.his.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class CommonUtil {
    public static final Digester md5 = new Digester(DigestAlgorithm.MD5);
    public static final int DEFAULT_PAGE = 1 ;
    public static final int DEFAULT_ROWS = 10 ;
}
