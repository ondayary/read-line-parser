package com.line.parser;

public interface Parser<T> {
    T parse(String str); // 파일에서 읽어오는 것이라 String
}
