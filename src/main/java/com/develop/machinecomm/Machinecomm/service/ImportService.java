package com.develop.machinecomm.Machinecomm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.develop.machinecomm.Machinecomm.repository.ImportUserCrudRepository;
import com.develop.machinecomm.Machinecomm.model.UserImporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.develop.machinecomm.Machinecomm.model.Users;

@Service
public class ImportService {

    private UserService userService;
    private ImportUserCrudRepository importRepository;


    @Autowired
    public ImportService(UserService userService,
                         ImportUserCrudRepository importRepository){
        this.userService = userService;
        this.importRepository = importRepository;
    }

    public void saveImportFile(UserImporter userImporter) {
        this.importRepository.save(userImporter);
    }

    public List<Users> getSpreadSheetData() throws IOException {
        UserImporter userImporter;
        userImporter = this.importRepository.getUsersFileData();
        FileInputStream file = new FileInputStream(
                new File(userImporter.getUsersFilePath()));

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        //Row row;
        Iterator<Row> rowIt = sheet.iterator();
        Users user;
        List<Users> usersList = new ArrayList<>();
        while(rowIt.hasNext()) {
            Row row = rowIt.next();
            user = new Users();
            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {

              Cell cell = cellIterator.next();
              if (cell != null && (!cell.toString().equals("Nome") &&
                      !cell.toString().equals("Matrícula")) && cell.getColumnIndex() != 0) {
                  if (cell.getColumnIndex() == 2) {
                      user.setName(cell.toString());
                  }

                  if (cell.getColumnIndex() == 1) {

                      int i = (int)cell.getNumericCellValue();
                      String  strCellValue = String.valueOf(i);
                      user.setReg(strCellValue);
                      user.setRfid(strCellValue);
                  }

                  user.setFunc("Operador");
                  user.setType(2);
                  user.setPerfis(Collections.singleton(1));
                  user.setPassword("M@tchbox2019");
                  user.setEmail("");
              }
            }
            if (user.getName() != null && user.getReg() != null) {
                usersList.add(user);
            }
        }

        return usersList;
    }

    public boolean createUsers() throws IOException {
        List<Users> usersList = this.getSpreadSheetData();

        for (Users user : usersList) {
            this.userService.createUser(user);
        }
        return true;
    }
}
