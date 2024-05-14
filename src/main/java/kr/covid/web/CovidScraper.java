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

            String title = document.select("thead").text();

            // 테이블 선택
            Element table = document.select("tbody").first(); // 여기에 원하는 선택자를 넣으세요

            // 선택된 테이블 내의 모든 행 선택
            Elements rows = table.select("tr");

            List<CovidStatus> covidStatusList = new ArrayList<>();

            // 선택된 테이블 내의 각 행 출력
            for (Element row : rows) {
                String location = row.select("td.bg-gray").text();
                System.out.println(location);
                String country = row.select("td:nth-child(1)").text().replace(",","");
                String total = row.select("td:nth-child(2)").text().replace(",","");

                covidStatusList.add(new CovidStatus(location,country,total));
            }


            for (CovidStatus covidStatus : covidStatusList){
                System.out.println(covidStatus);
            }

            // 엑셀 파일로 저장
            String excelFileName = "covid_status.xlsx";
            ExcelExpoter.exportToExcel(covidStatusList,excelFileName);

            // pdf 파일로 저장
            String pdfFileNmae = "covid_status.pdf";
            PdfExporter.exportToPdf(covidStatusList,pdfFileNmae);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
