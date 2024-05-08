package kr.covid.web;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExpoter {
    public static void exportToExcel(List<CovidStatus> covidStatusList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("코로나 현황");

//        헤더 행 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"지역 및 국가", "환자발생 수(사망)"};

        // 병합된 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("지역 및 국가");

        // 데이터 행 추가
        for (int i=0;i<covidStatusList.size();i++){
            CovidStatus covidStatus = covidStatusList.get(i);
            Row row = sheet.createRow(i+1);

            row.createCell(0).setCellValue(covidStatus.getLocation());
            row.createCell(1).setCellValue(covidStatus.getCountry());
            row.createCell(2).setCellValue(covidStatus.getTotal());
        }

        // 엑셀에 파일로 저장
        try(FileOutputStream fileOut = new FileOutputStream(fileName)){
            workbook.write(fileOut);
        }catch (IOException e){
            e.printStackTrace();
        }
        workbook.close();
    }
}
