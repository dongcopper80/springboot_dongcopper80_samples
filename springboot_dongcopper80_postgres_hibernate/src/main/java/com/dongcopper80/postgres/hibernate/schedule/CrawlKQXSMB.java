/*
 * The MIT License
 *
 * Copyright 2022 Nguyễn Thúc Đồng (dongcopper80).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongcopper80.postgres.hibernate.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Component
public class CrawlKQXSMB {

    private final Logger LOG = LoggerFactory.getLogger(CrawlKQXSMB.class);

    @Cacheable(value = "cacheKetQuaXoSoHangNgay", keyGenerator = "kqxsKeyGenerator")
    private String saveKQXSMB(String date, String kqxs) {
        LOG.info("KQXSMB {} with {}", date, kqxs);
        return kqxs;
    }

    //run at 19PM everyday
    @Scheduled(cron = "0 0 19 * * ?")
    public void reportCurrentTime() {

        Calendar cal = Calendar.getInstance();

        try {

            String URL = "https://www.minhngoc.net/ket-qua-xo-so/mien-bac/" + new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()) + ".html";

            Document document = Jsoup.connect(URL).get();

            Element table = document.select("table[class=bkqtinhmienbac]").first();

            String ketqua = table.text();

            Pattern kihieu = Pattern.compile("(\\d{1,2}[A-Z]{2})");
            Matcher khmatcher = kihieu.matcher(ketqua);

            String khdb = "";
            int count = 0;
            int date_of_week = cal.get(Calendar.DAY_OF_WEEK);

            while (khmatcher.find()) {
                if (!khdb.equals("")) {
                    khdb += "-";
                }
                khdb += khmatcher.group(0);
            }

            ketqua = ketqua.replaceAll("(\\d{1,2}[A-Z]{2})", "");

            saveKQXSMB(new SimpleDateFormat("ddMMyyyy").format(cal.getTime()), ketqua);

            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(ketqua);

            while (matcher.find()) {
                if (count >= 0 && count <= 2) {
                    //process lottery day result
                } else if (count == 3) {
                    //giai db
                } else if (count == 4) {
                    //giai 1
                } else if (count == 5 || count == 6) {
                    //giai 2
                } else if (count >= 7 && count <= 12) {
                    //giai 3
                } else if (count >= 13 && count <= 16) {
                    //giai 4
                } else if (count >= 17 && count <= 22) {
                    //giai 5
                } else if (count >= 23 && count <= 25) {
                    //giai 6
                } else if (count >= 26 && count <= 29) {
                    //giai 7
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
