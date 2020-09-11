package com.cloudmusic.export;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static void genMiguData(JSONArray jsonArray) throws IOException {
        //1.创建一个工作薄
        XSSFWorkbook xssfWorkbook=new XSSFWorkbook();
        //2.创建工作表
        XSSFSheet sheet = xssfWorkbook.createSheet("周杰伦歌曲大全");
        //单元格样式
        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
        //字体样式
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontName("黑体");
        font.setColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFont(font);


        //3.创建行
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("歌名");
        cell.setCellStyle(cellStyle);

        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("歌手");
        cell1.setCellStyle(cellStyle);

        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("专辑");
        cell2.setCellStyle(cellStyle);

        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("封面");
        cell3.setCellStyle(cellStyle);
        XSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("播放链接");
        cell3.setCellStyle(cellStyle);


        for (int i = 0; i < jsonArray.length(); i++) {
            XSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(jsonArray.getJSONObject(i).getString("songName"));
            row1.createCell(1).setCellValue(jsonArray.getJSONObject(i).getString("singerName"));
            row1.createCell(2).setCellValue(jsonArray.getJSONObject(i).getString("albumName"));
            row1.createCell(3).setCellValue(jsonArray.getJSONObject(i).getString("cover"));
            row1.createCell(4).setCellValue(jsonArray.getJSONObject(i).getString("mp3"));
        }

        FileOutputStream fileOutputStream=new FileOutputStream("e:/周杰伦歌曲汇集.xls");
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        xssfWorkbook.close();
    }
}
