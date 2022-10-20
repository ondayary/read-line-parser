package com.line;

import com.LineReader;
import com.line.domain.Hospital;
import com.line.parser.HospitalParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HospitalMain {
    public static void main(String[] args) throws IOException {

        /*LineReader<Hospital> hospitalLineReader = new LineReader<>(new HospitalParser());
        String filename = "/Users/daon/Downloads/seoul_hospital_incoding.csv";
        List<Hospital> hospitals = hospitalLineReader.readLines(filename);

        System.out.println(hospitals.size());
        for(Hospital hospital : hospitals) {
            System.out.printf("%s,%s,%s,%s,%s,%s,%s",
                              hospital.getId(), hospital.getAddress(), hospital.getDistrict(),
                              hospital.getCategory(), hospital.getEmergencyRoom(), hospital.getName(),
                              hospital.getSubdivision());
        }*/

        FileController<Hospital> hospitalFileController = new FileController<>(new HospitalParser());
        String filename = "/Users/daon/Downloads/seoul_hospital_incoding.csv";
        List<Hospital> hospitals = hospitalFileController.readLines(filename);

        System.out.println(hospitals.size());
        List<String> lines = new ArrayList<>();
        // loop
        for (Hospital hospital : hospitals) { // hospitals에서 hospital을 하나씩 꺼내쓰겠다.
            lines.add(hospital.getSqlInsertQuery());
        }

        String sqlFilename = "seoul_hospital_insert.sql";
        hospitalFileController.createANewFile(sqlFilename);
        hospitalFileController.writeLines(lines, sqlFilename);
    }
}