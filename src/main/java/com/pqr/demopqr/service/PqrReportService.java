package com.pqr.demopqr.service;

import com.pqr.demopqr.dao.IPqrRepository;
import com.pqr.demopqr.dto.PqrDTO;
import com.pqr.demopqr.model.enums.EstadoPQR;
import com.pqr.demopqr.util.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PqrReportService {

    private final IPqrRepository pqrRepository;

    private final PqrService pqrService;

    String[] headers = {"Id", "Asunto", "Tipo", "Estado", "Creado Por", "Asignado A", "Fecha Creacion", "Fecha Limite", "Vigencia"};

    @Autowired
    public PqrReportService(IPqrRepository pqrRepository, PqrService pqrService){
        this.pqrRepository = pqrRepository;
        this.pqrService = pqrService;
    }

    public File getReport() throws IOException {
        List<PqrDTO> data = pqrService.getAll();

        try(XSSFWorkbook workbook = new XSSFWorkbook()){
            createSheet(workbook, data);

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) +
                    "PQR_Report_" + Utils.convertDateToString(new Date(), Utils.FECHA_GUION) + ".xlsx";

            FileOutputStream outputStream = new FileOutputStream(StringUtils.cleanPath(fileLocation));
            workbook.write(outputStream);

            return new File(StringUtils.cleanPath(fileLocation));
        }
    }

    private void createSheet(XSSFWorkbook workbook, List<PqrDTO> data){
        Sheet sheet = workbook.createSheet();

        int headerLength = headers.length;

        for(int i = 0; i < headerLength; i++){
            sheet.setColumnWidth(i, 10000);
        }

        /* headers */
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        int index = 0;

        Cell headerCell;
        for (String key : headers) {
            headerCell = header.createCell(index);
            headerCell.setCellValue(key);
            headerCell.setCellStyle(headerStyle);

            index++;
        }

        /* content */
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int indexRow = 1;

        for (PqrDTO pqrDTO : data) {
           createRow(sheet, pqrDTO, style, indexRow);

            indexRow++;
        }

        /* agrega total de registros */
        Integer count = data.size();

        Row row = sheet.createRow(indexRow);

        Cell cell = row.createCell(0);
        cell.setCellValue("Total: ");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(String.valueOf(count));
        cell.setCellStyle(style);
    }

    private void createRow(Sheet sheet, PqrDTO pqrDTO, CellStyle style, int indexRow){
        Row row = sheet.createRow(indexRow);

        Cell cell = row.createCell(0);
        cell.setCellValue(pqrDTO.getId());
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(pqrDTO.getAsunto());
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(pqrDTO.getTipo().getValue());
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(pqrDTO.getEstado().getValue());
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue(pqrDTO.getAutor().getUsername());
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue(pqrDTO.getUsuario().getUsername());
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue(Utils.sanitize(Utils.convertDateToString(pqrDTO.getFechaCreacion(), Utils.FECHA_GUION)));
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue(Utils.sanitize(Utils.convertDateToString(pqrDTO.getFechaLimite(), Utils.FECHA_GUION)));
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue(getVigencia(pqrDTO));
        cell.setCellStyle(style);
    }

    private String getVigencia(PqrDTO pqrDTO){
        if(!pqrDTO.getEstado().equals(EstadoPQR.CERRADO) && pqrDTO.getFechaLimite().before(new Date())){
            return "Vencida";
        }
        return "Vigente";
    }

}
