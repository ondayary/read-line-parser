package com.line;

import com.line.parser.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileController<T> {
    Parser<T> parser;
    boolean isRemoveColumnName = true;

    public FileController(Parser<T> parser) {
        this.parser = parser;
    }

    public FileController(Parser<T> parser, boolean isRemoveColumnName) {
        this.parser = parser;
        this.isRemoveColumnName = isRemoveColumnName;
    }

    List<T> readLines(String filename) throws IOException {
        List<T> result = new ArrayList<>();
        BufferedReader br; //= new BufferedReader(new FileReader(filename));
        br = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8);
        String str;
        if (isRemoveColumnName) {
            br.readLine();
        }
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            result.add(parser.parse(str));
        }
        return result;
    }

    public void createANewFile(String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
        System.out.println("Have a file generated?:" + file.exists());
    }

    // 파일에 쓰기
    public void writeLines(List<String> lines, String filename) {
        File file = new File(filename);

        try {
            BufferedWriter writer = new BufferedWriter( // 일종의 삽이라고 생각하면 된다.
                                    new OutputStreamWriter( // FileWriter라고 생각하면 된다.
                                    new FileOutputStream(file), "utf-8")); // 이 기능을 쓴 이유는 유일하게 인코딩을 할 수 있기 때문
            // 이 공간이 한 줄씩 쓰는 부분
            for (String str : lines) {
                writer.write(str);
            }
            writer.close(); // 파일 다쓰면 파일 닫아주기
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("success");
    }
}
