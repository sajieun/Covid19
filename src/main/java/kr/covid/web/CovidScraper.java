package kr.covid.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidScraper {
    public static void main(String[] args) throws IOException {
        String url = "https://ncov.kdca.go.kr/pot/www/CVID19/TNDNCY_OCCR/OTTR_OCCR.jsp";

        try {
            Document document = Jsoup.connect(url).get();

            String title = document.select("table > thead > tr").first().text();

            Element table = document.select("table").first();
            Elements rows = table.select("tbody > tr");

            List<CovidStatus> covidStatusList = new ArrayList<>();

            // 선택된 테이블 내의 각 행 출력
            for (Element row : rows) {
                String location = row.select("").text().replace(",","");
                String country = row.select("").text().replace(",","");
                String total = row.select("").text().replace(",","");
                String region = row.select("td").text();

                covidStatusList.add(new CovidStatus(location,country,total));
            }

            for (CovidStatus covidStatus : covidStatusList){
                System.out.println(covidStatus);
            }

            // 엑셀 파일로 저장
            String excelFileName = "covid_status_"+ document.data()+".xlsx";
            ExcelExpoter.exportToExcel(covidStatusList,excelFileName);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
