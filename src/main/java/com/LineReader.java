package com;

import com.line.parser.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineReader<T> { // LineReader는 Parser라는 interface에 의존하게 된다.
    Parser<T> parser; // parser도 제네릭을 넘겨받아야 한다.
    // 어디선가 주입을 받아야 한다.
    boolean isRemoveColumnName = true;

    // Con + Enter : 생성자 생성
    public LineReader(Parser<T> parser) {
        this.parser = parser;
    }

    List<T> readLines(String filename) throws IOException { // <String> 에서 <T>로 변경
        List<T> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // 한줄씩 읽어와야 한다
        String str;

        if(isRemoveColumnName) {
            // 출력 후 첫줄이 이상해서 한줄 날리기
            br.readLine();
        }

        while((str = br.readLine()) != null) { // null이 아닌 동안 whlie문 반복
            result.add(parser.parse(str));
        }
        return result;
    }

    /*
    public static void main(String[] args) throws IOException {
        String filename = "/Users/daon/Downloads/서울시 병의원 위치 정보.csv";
        LineReader lr = new LineReader();
        List<String> lines = lr.readLines(filename);
        System.out.println(lines.size());
        // 19040 라인 출력
    }
    */
}
