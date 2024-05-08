package kr.covid.web;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PdfExporter {
    public static void exportToPdf(List<CovidStatus> covidStatusList, String fileName) throws FileNotFoundException {
        try {

            // 한글 폰트
            PdfFont font = PdfFontFactory.createFont("D2Coding.ttf", PdfEncodings.IDENTITY_H,true);

            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(fileName));
            Document document =new Document(pdfDocument);

            // 테이블 생성 및 설정
            float[] columnWidths = {100,50,50,50};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            String[] headers = {"지역 및 국가","환자발생 수(사망)"};

            // 병합된 헤더 셀 설정
            Cell headerCell = new Cell(1, 2) // rowspan = 1, colspan = 2
                    .add(new Paragraph(headers[0]).setFont(font)) // 지역 및 국가 제목
                    .setTextAlignment(TextAlignment.CENTER); // 가운데 정렬
            table.addHeaderCell(headerCell);

            // 나머지 헤더 셀 추가
            Cell deathCell = new Cell()
                    .add(new Paragraph(headers[1]).setFont(font)) // 환자발생 수(사망) 제목
                    .setTextAlignment(TextAlignment.CENTER); // 가운데 정렬
            table.addHeaderCell(deathCell);

            // 데이터 셀 설정
            for (CovidStatus covidStatus : covidStatusList) {
                table.addCell(new Cell().add(new Paragraph(covidStatus.getLocation()).setFont(font))); // 지역
                table.addCell(new Cell().add(new Paragraph(covidStatus.getCountry()).setFont(font))); // 국가
                table.addCell(new Cell().add(new Paragraph(covidStatus.getTotal()).setFont(font))); // 환자발생 수(사망)
            }


            document.add(table);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
