package com.bnksystem.trainning1team.util;import com.bnksystem.trainning1team.util.model.BNKColor;import com.bnksystem.trainning1team.util.model.CellModel;import org.apache.commons.collections4.map.LinkedMap;import org.apache.poi.ss.usermodel.*;import org.apache.poi.ss.util.CellRangeAddress;import org.apache.poi.xssf.usermodel.*;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import javax.servlet.ServletOutputStream;import javax.servlet.http.HttpServletResponse;import java.io.FileInputStream;import java.io.IOException;import java.io.InputStream;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.regex.Pattern;import static org.apache.poi.ss.usermodel.CellType.STRING;public class ExcelUtils {    private Logger logger = LoggerFactory.getLogger(getClass());    private ExcelType type;    private ExcelUtils(){}    private ExcelUtils(ExcelType type) {        this.type = type;    }    public static ExcelUtils getInstance(ExcelType type) {        return new ExcelUtils(type);    }    public XSSFWorkbook create(List<CellModel[]> headers, List<CellModel[]> rows) throws IOException {        XSSFWorkbook workbook = new XSSFWorkbook();        XSSFSheet sheet = workbook.createSheet("Sheet");        XSSFDataFormat format = workbook.createDataFormat();        int rowIndex = 0;        List<HashMap<String, Integer>> rowspanList = new ArrayList<>();        if (headers != null) {            for (CellModel[] cols : headers) {                XSSFRow row = sheet.createRow(rowIndex++);                int headerIdx = 0;                for (CellModel col : cols) {                    XSSFCellStyle style = workbook.createCellStyle();                    style.setBorderTop(col.getBorder());                    style.setBorderBottom(col.getBorder());                    style.setBorderLeft(col.getBorder());                    style.setBorderRight(col.getBorder());                    style.setWrapText(true);                    Font headerFont = workbook.createFont();                    headerFont.setBold(true);                    if (headerIdx == 0) {                        if (col.getHeight() > 0) {                            row.setHeight(col.getHeight());                        }                    }                    XSSFCell hTmp = row.createCell(headerIdx++);                    boolean chkSpan = false;                    int needNewCell = 0;                    for (HashMap<String, Integer> rowspan : rowspanList) {                        int chkHeaderidx = headerIdx < 1 ? 0 : headerIdx - 1;                        if ((rowspan.get("start") < (rowIndex - 1) && rowspan.get("end") >= (rowIndex - 1))                                && (rowspan.get("col1") <= chkHeaderidx && rowspan.get("col2") > chkHeaderidx)) {                            chkSpan = true;                            needNewCell = (rowspan.get("col2") - rowspan.get("col1"));                            break;                        }                    }                    XSSFCell cell = null;                    if (chkSpan) {                        hTmp.setCellValue("");                        hTmp.setCellStyle(style);                        for (int i=0;i<needNewCell;i++) {                            XSSFCell newCell = row.createCell(headerIdx++);                            newCell.setCellValue("");                            newCell.setCellStyle(style);                        }                        cell = row.createCell(headerIdx++);                    } else {                        cell = hTmp;                    }                    // 첫 행 작성 시 컬럼 너비 설정함.                    if (col.getWidth() > 0) {                        sheet.setColumnWidth(cell.getColumnIndex(), col.getWidth());                    }                    if (col.getHorizontalAlignment() != null) {                        style.setAlignment(col.getHorizontalAlignment());                    }                    if (col.getVerticalAlignment() != null) {                        style.setVerticalAlignment(col.getVerticalAlignment());                    }                    if (col.getBackgroundColor() != null) {                        style.setFillForegroundColor(col.getBackgroundColor());                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);                    }                    if (col.getFontFamily() != null && !"".equals(col.getFontFamily())) {                        headerFont.setFontName(col.getFontFamily());                    }                    if (col.getFontSize() > 0) {                        headerFont.setFontHeightInPoints((short) col.getFontSize());                    }                    if (col.getTextColor() != null) {                        headerFont.setColor(col.getTextColor().getIndex());                    }                    style.setFont(headerFont);                    cell.setCellStyle(style);                    switch (col.getType()) {                        case NUMERIC:                            cell.setCellValue((Double) col.getValue());                            break;                        case STRING:                            cell.setCellValue((String) col.getValue());                            break;                        case BOOLEAN:                            cell.setCellValue((Boolean) col.getValue());                            break;                        default:                            break;                    }                    if (col.getRowspan() > 1) {                        HashMap<String, Integer> data = new HashMap<>();                        data.put("start", (rowIndex - 1));                        data.put("end", (rowIndex - 1) + (col.getRowspan() - 1));                        data.put("col1", headerIdx > 1 ? (headerIdx - 1) : 0);                        data.put("col2", (headerIdx > 1 ? (headerIdx - 1) : 0) + (col.getColspan() > 0 ? (col.getColspan() - 1) : 0));                        for (int i = 0; i < (col.getColspan() - 1); i++) {                            XSSFCell tmp = row.createCell(headerIdx++);                            tmp.setCellValue("");                            tmp.setCellStyle(style);                        }                        rowspanList.add(data);                    } else {                        if (col.getColspan() > 1) {                            HashMap<String, Integer> data = new HashMap<>();                            data.put("start", (rowIndex - 1));                            data.put("end", (rowIndex - 1));                            data.put("col1", (headerIdx - 1));                            data.put("col2", (headerIdx - 1) + (col.getColspan() - 1));                            rowspanList.add(data);                            for (int i = 0; i < (col.getColspan() - 1); i++) {                                XSSFCell tmp = row.createCell(headerIdx++);                                tmp.setCellValue("");                                tmp.setCellStyle(style);                            }                        }                    }                }            }        }        if (rows != null) {            for (CellModel[] cols : rows) {                XSSFRow row = sheet.createRow(rowIndex++);                int columnIndex = 0;                for (CellModel col : cols) {                    Font dataFont = workbook.createFont();                    if (columnIndex == 0) {                        if (col.getHeight() > 0) {                            row.setHeight(col.getHeight());                        }                    }                    XSSFCell cell = row.createCell(columnIndex++);                    if (col.getWidth() > 0) {                        sheet.setColumnWidth(cell.getColumnIndex(), col.getWidth());                    }                    XSSFCellStyle style = workbook.createCellStyle();                    if (col.getHorizontalAlignment() != null) {                        style.setAlignment(col.getHorizontalAlignment());                    }                    if (col.getVerticalAlignment() != null) {                        style.setVerticalAlignment(col.getVerticalAlignment());                    }                    if (col.getBackgroundColor() != null) {                        logger.debug("CHECK backgroundColor = " + col.getBackgroundColor().getIndex());                        style.setFillForegroundColor(col.getBackgroundColor());                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);                    }                    if (col.getFontFamily() != null && !"".equals(col.getFontFamily())) {                        dataFont.setFontName(col.getFontFamily());                    }                    if (col.getFontSize() > 0) {                        dataFont.setFontHeightInPoints((short) col.getFontSize());                    }                    if (col.getTextColor() != null) {                        dataFont.setColor(col.getTextColor().getIndex());                    }                    dataFont.setBold(col.isBold());                    style.setBorderTop(col.getBorder());                    style.setBorderBottom(col.getBorder());                    style.setBorderLeft(col.getBorder());                    style.setBorderRight(col.getBorder());                    style.setWrapText(true);                    style.setFont(dataFont);                    if (col.getType() == CellType.NUMERIC) {                        style.setDataFormat(format.getFormat("#,##0"));                    }                    cell.setCellStyle(style);                    switch (col.getType()) {                        case NUMERIC:                            cell.setCellValue((Double) col.getValue());                            break;                        case STRING:                            cell.setCellValue((String) col.getValue());                            break;                        case BOOLEAN:                            cell.setCellValue((Boolean) col.getValue());                            break;                        default:                            break;                    }                    if (col.getRowspan() > 0) {                        HashMap<String, Integer> data = new HashMap<>();                        data.put("start", (rowIndex - 1));                        data.put("end", (rowIndex - 1) + (col.getRowspan() - 1));                        data.put("col1", columnIndex > 1 ? (columnIndex - 1) : 0);                        data.put("col2", (columnIndex > 1 ? (columnIndex - 1) : 0) + (col.getColspan() > 0 ? (col.getColspan() - 1) : 0));                        for (int i = 0; i < (col.getColspan() - 2); i++) {                            XSSFCell tmp = row.createCell(columnIndex++);                            tmp.setCellValue("");                            tmp.setCellStyle(style);                        }                        rowspanList.add(data);                    } else {                        if (col.getColspan() > 1) {                            HashMap<String, Integer> data = new HashMap<>();                            data.put("start", (rowIndex - 1));                            data.put("end", (rowIndex - 1));                            data.put("col1", (columnIndex - 1));                            data.put("col2", (columnIndex - 1) + (col.getColspan() - 2));                            rowspanList.add(data);                            for (int i = 0; i < (col.getColspan() - 2); i++) {                                XSSFCell tmp = row.createCell(columnIndex++);                                tmp.setCellValue("");                                tmp.setCellStyle(style);                            }                        }                    }                }            }        }        for (HashMap<String, Integer> rspan : rowspanList) {            logger.debug("CHECK start = " + rspan.get("start") + ":" + rspan.get("col1") + ", end = " + rspan.get("end") + ":" + rspan.get("col2"));            sheet.addMergedRegion(new CellRangeAddress(rspan.get("start"), rspan.get("end"), rspan.get("col1"), rspan.get("col2")));        }        return workbook;    }    public List<LinkedMap<String, Object>> parse(String[] headers, InputStream file, int startRowIndex) throws IOException {        List<LinkedMap<String, Object>> dataList = new ArrayList<>();        if (file != null) {            FileInputStream fis = (FileInputStream) file;            XSSFWorkbook workbook = new XSSFWorkbook(fis);            int rowIndex = 0;            int columnIndex = 0;            int startRowNo = startRowIndex >= 0 ? startRowIndex : 0;            XSSFSheet sheet = workbook.getSheetAt(0);            int rows = sheet.getPhysicalNumberOfRows();            int headerRealLength = 0;            int headerLength = 0;            int autoNamingHeaderStartIdx = -1;            int autoNamingHeaderEndIdx = -1;            if (headers != null) {                headerRealLength = headers.length;                headerLength = headerRealLength;                int idx = 0;                for (String header : headers) {                    if (header.contains(":")) {                        autoNamingHeaderStartIdx = idx;                        String[] arr = header.split(":");                        if (arr[1] != null && !"".equals(arr[1].trim())) {                            String strLen = arr[1].trim();                            if (Pattern.matches("^\\d+$", strLen)) {                                int len = Integer.parseInt(strLen);                                if (len > 0) {                                    headerLength += len;                                    idx += len;                                    continue;                                }                            }                        }                    }                    idx++;                }            }            logger.debug("CHECK headerLength = " + headerLength);            logger.debug("CHECK headers = " + headers);            for (rowIndex = startRowNo; rowIndex < rows; rowIndex++) {                LinkedMap<String, Object> map = new LinkedMap<>();                XSSFRow row = sheet.getRow(rowIndex);                int cells = row.getPhysicalNumberOfCells();                for (columnIndex = 0; columnIndex<cells; columnIndex++) {                    String headerName = "";                    if (headers != null && headerLength > columnIndex) {                        if (autoNamingHeaderStartIdx > -1 && autoNamingHeaderStartIdx == columnIndex) {                            headerName = headers[autoNamingHeaderStartIdx];                            logger.debug("headerName = " + headerName);                            int cLen = 1;                            if (headerName.contains(":")) {                                String strLen = headerName.split(":")[1];                                headerName = headerName.split(":")[0];                                cLen = Integer.parseInt(strLen);                            }                            List<String> valueList = new ArrayList<>();                            for (int i=autoNamingHeaderStartIdx;i<(autoNamingHeaderStartIdx + cLen);i++) {                                columnIndex = i;                                valueList.add(getValue(row.getCell(columnIndex)));                            }                            map.put(headerName, valueList);                        } else {                            //여기에서 헤더 출력                            headerName = headers[columnIndex];                            logger.debug("headerName[" + columnIndex + "] = " + headerName);                            System.out.println("--->" + getValue(row.getCell(columnIndex)));                            map.put(headerName, getValue(row.getCell(columnIndex)));                        }                    } else {                        headerName = "COL" + ("0000" + (columnIndex)).substring(((String.valueOf(columnIndex)).length() - 1));                        map.put(headerName, getValue(row.getCell(columnIndex)));                    }                }                dataList.add(map);            }        }        return dataList;    }    private String getValue(XSSFCell cell) {        String value = "";        switch (cell.getCellType()) {            case FORMULA:                value = cell.getCellFormula() + "";                break;            case NUMERIC:                value = Math.round(cell.getNumericCellValue()) + "";                break;            case STRING:                value = cell.getStringCellValue();                break;            case BLANK:                value = cell.getBooleanCellValue() + "";                break;            case ERROR:                value = cell.getErrorCellString();                break;        }        return value;    }    // 엑셀 공통 함수    public void getExcel(String[] headerList, Integer[] contentSize, String[] content, String fileName, List<HashMap<String, Object>> list, HttpServletResponse response) {        XSSFWorkbook workbook = null;        String excelFileName = DateUtils.getCurrentDate("yyyyMMdd") + "_" + fileName;        try {            if (list != null && list.size() > 0) {                /**                 * Header 생성                 */                List<CellModel[]> headers = new ArrayList<>();                CellModel[] headers1 = new CellModel[headerList.length];                for(int index=0; index<headerList.length; index++) {                    headers1[index] = CellModel.builder()                            .type(STRING)                            .value(headerList[index])                            .fontSize(14)                            .width(1000).height((short) 600).border(BorderStyle.THIN)                            .backgroundColor(BNKColor.XSSF_BACKGROUND_COLOR_LIGHTGRAY.getColor())                            .horizontalAlignment(HorizontalAlignment.CENTER)                            .verticalAlignment(VerticalAlignment.CENTER)                            .build();                }                headers.add(headers1);                System.out.println("헤더 생성 완료");                /**                 * DataPart 생성                 */                List<CellModel[]> dataParts = new ArrayList<>();                //for문 시작 (hashmap)                for (HashMap<String, Object> info: list) {                    int dataIndex = 0;                    List<CellModel> listCellModel = new ArrayList<>();                    // 컬럼 입력                    for(int index=0; index < contentSize.length; index++) {                        System.out.println("listCellModel = " + info.get(content[index].toString()));                        listCellModel.add(CellModel.builder()                                .type(STRING)                                .value((info.get(content[index].toString()) == null ? "" : info.get(content[index].toString()).toString()))                                .fontSize(14)                                .width(contentSize[index]).height((short) 600).border(BorderStyle.THIN)                                .horizontalAlignment(HorizontalAlignment.CENTER)                                .verticalAlignment(VerticalAlignment.CENTER)                                .build());                    }                    CellModel[] datas = new CellModel[listCellModel.size()];                    for (CellModel model : listCellModel) {                        datas[dataIndex++] = model;                    }                    dataParts.add(datas);                }                workbook = ExcelUtils.getInstance(ExcelType.XLSX).create(headers, dataParts);                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");                response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName+".xlsx");                ServletOutputStream servletOutputStream = response.getOutputStream();                workbook.write(servletOutputStream);                workbook.close();                servletOutputStream.flush();                servletOutputStream.close();            }        } catch (Exception e) {            e.printStackTrace();        }    }}