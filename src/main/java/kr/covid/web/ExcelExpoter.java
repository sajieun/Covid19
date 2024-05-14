package kr.covid.web;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExpoter {
    public static void exportToExcel(List<CovidStatus> covidStatusList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("코로나 현황");

        // 헤더 행 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"지역 및 국가", "환자발생 수(사망)"};

        // 병합된 헤더 셀 생성
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 1); // 병합할 셀 범위: A1부터 B1까지
        sheet.addMergedRegion(mergedRegion);

        // 헤더 셀 생성 및 값 설정
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue(headers[0]);

        Cell headerCell2 = headerRow.createCell(2);
        headerCell2.setCellValue(headers[1]);

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
