package com.line.parser;

import com.line.domain.Hospital;

// alt + Enter : Test Class 만들기
public class HospitalParser implements Parser<Hospital>{
/*
    private String replaceAllQuot(String str) {
        return str.replaceAll("\"","");
    }
*/
    private String getSubdivision(String name)  {
        String[] subdivisions = {"소아과", "피부과", "성형외과", "정형외과", "산부인과", "관절", "안과", "가정의학과", "비뇨기과", "치과", "내과", "외과"};

        for(String subdivision : subdivisions) {
            if(name.contains(subdivision)) {
                // 만약 스캔다하고서도 여기에 안걸린다면
                return subdivision;
            }
        }
        return "";
    }
    @Override
    public Hospital parse(String str) {
        str = str.replaceAll("\"","");
        String[] splitted  = str.split(",");
//        address;
//        district;
//        category;
//        emergencyRoom;
//        name;
//        subdivision;

        // subdivision 파싱을 해서 넣는 것이 좋을 것 같습니다.
        String name = splitted[10];
        String subdivision = getSubdivision(name);

        // HospitalParser에서 새로 만든 생성자를 쓰도록 변경

        return new Hospital(splitted[0],
                            splitted[1],
                            splitted[2],
                            Integer.parseInt(splitted[6]),
                            name,
                            subdivision);
    }
}
/*
Unit Test(유닛 테스트) Integration Test(통합 테스트)
통합 테스트는 배포 하기 전에 합니다.

테스트 코드가 1개밖에 없으면 Unit test, Integration Test가 똑같다.
*/

