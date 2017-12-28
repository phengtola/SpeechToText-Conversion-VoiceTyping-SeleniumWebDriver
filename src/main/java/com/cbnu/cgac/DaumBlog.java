package com.cbnu.cgac;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DaumBlog { // 다음 : "다음 블로그", "티스토리", "브런치" 세 가지 플랫폼 검색. 브런치는 검색 결과가 적어 비효율적이므로 제외

    public static void main(String args[]) throws IOException {

        /*

        // CSV 데이터 삽입에 생성 할 변수
        Map<String, Object> hmap = null;
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        String searchQuery = "버섯";

        for(int month = 1; month <= 1; month++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date("2016/0" + month + "/01")); // 2016/1/01으로 하면 에러, 2016/01/01 처럼 월 또한 두 자릿수로 해야함
            DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for(int day = 1; day <= 2; day++) { // maxDay로 수정
                if(day < 10)
                    calendar.setTime(new Date("2016/0" + month + "/0" + day));
                else
                    calendar.setTime(new Date("2016/0" + month + "/" + day));

                String currentDate = sdf.format(calendar.getTime());

                System.out.println("검색 키워드 : " + searchQuery);
                System.out.println("해당 월의 최대 일수 : " + maxDay);
                System.out.println("기간 : " + currentDate);

                Map<String, String> cookies = new HashMap<>();
                cookies.put("uvkey","WVXeusuFomcAAC@Oa6wAAACE");
                cookies.put("_ga","GA1.2.224147451.1498808207");
                cookies.put("ssab","");
                cookies.put("ODT","SNPZ_1DVZ_IIMZ_BR1Z_IVRZ_NKSZ_WSAZ_");
                cookies.put("DDT","NNSZ_MS2Z_VO2Z_CCBZ_FGKZ_DICZ_GG1Z_");
                cookies.put("DTQUERY","%ED%8C%8C%ED%94%84%EB%A6%AC%EC%B9%B4");
                cookies.put("TIARA","1Y7AC2.hl4lhDISSC1pCnHYXLdhIMkoZly_kYI.CL_1HgiagjSEVHrpRKcq.sQIOyq64x1QtD-qQKMQgqthlXQmWflcNQRw");
                Document totalCountDocument = Jsoup.connect("http://search.daum.net/search?nil_suggest=btn&w=blog&DA=STC&q=" + searchQuery + "&page=1&m=board&sd="+ currentDate +"000000&ed=" + currentDate + "235959&period=u")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                        .cookies(cookies)
                        .timeout(1000000)
                        .get();
                Elements totalCountElement = totalCountDocument.select("#blogColl .txt_info"); // 해당 날짜(currentDate)의 전체 포스트 개수
                System.out.println(totalCountElement.text());
                System.out.println("http://search.daum.net/search?nil_suggest=btn&w=blog&DA=STC&q=" + searchQuery + "&page=1&m=board&sd="+ currentDate +"000000&ed=" + currentDate + "235959&period=u");
            }

            */



        Document totalCountDocument = Jsoup.connect("http://allvod.sbs.co.kr/allvod/vodEndPage.do?mdaId=22000061521").get();
        Elements totalCountElement = totalCountDocument.select(".cthd_option_date"); // 해당 날짜(currentDate)의 전체 포스트 개수
        System.out.println("data");
        System.out.println(totalCountElement.text());


    }
}

