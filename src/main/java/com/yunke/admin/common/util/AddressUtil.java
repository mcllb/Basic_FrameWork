package com.yunke.admin.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import com.yunke.admin.YunkeAdminApplication;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;

/**
 * @description 根据IP地址获取城市
 */
@Slf4j
public class AddressUtil {

    private final static String INNER_ADDR = "内网IP|内网IP";


    public static String getCityInfo(String ip) {

        try {
            ClassPathResource resource = new ClassPathResource("ip2region/ip2region.xdb");
            InputStream inputStream = resource.getInputStream();
            File file = FileUtil.createTempFile("ip2region", ".xdb", true);
            FileUtil.writeFromStream(inputStream, file);
            byte[] cBuff = Searcher.loadContentFromFile(file.getPath());
            Searcher searcher = Searcher.newWithBuffer(cBuff);
            String region = searcher.search(ip);
            String addr = region.replace("0|", "").replace("|0", "");
            if(INNER_ADDR.equals(addr)){
                addr = "内网IP";
            }
            Console.log("ip={},region={},addr={}", ip, region, addr);
            searcher.close();

            return addr;
        } catch (Exception e) {
            log.error("获取地址信息异常：{}", e.getLocalizedMessage());
        }
        return "XX XX";
    }

    public static String getApplicationPath() {
        ApplicationHome h = new ApplicationHome(YunkeAdminApplication.class);
        File jarF = h.getSource();
        return jarF.getParentFile().toString();
    }

    public static void main(String[] args) {

        System.out.println(getCityInfo("192.168.1.106"));
        System.out.println(getCityInfo("1.81.134.10"));
    }
}
