package com.line.domain;

import static java.lang.String.format;

public class Hospital {
    private String id;          // [0]
    private String address;     // [1]
    private String district;    // [1] 수정
    private String category;    // [2]
    private Integer emergencyRoom;  // [6]
    private String name;        // [10]
    private String subdivision; // [10]수정

    // 매개변수 있는 생성자
/*    public Hospital(String id) {
        this.id = id.replace("\"", "");
        // Constructor에서 Id를 set할 때 “를 빈칸으로
    }*/

    public Hospital(String id, String address, String category, Integer emergencyRoom, String name, String subdivision) {
        this.id = id;
        this.address = address;
        this.category = category;
        this.emergencyRoom = emergencyRoom;
        this.name = name;
        this.subdivision = subdivision;
        // district set하는 방법
        this.setDistrict();
    }

    public String getSqlInsertQuery() {
        String sql = String.format("INSERT INTO `likelion-db`.`seoul_hospital`\n" +
                                  "(`id`,`address`,`district`,`category`,`emergency_room`,`name`,`subdivision`)\n"+
                                  "VALUES\n" +
                                  "(\"%s\",\n" +
                                  "\"%s\",\n" +
                                  "\"%s\",\n" +
                                  "\"%s\",\n" +
                                  "%d,\n" +
                                  "\"%s\",\n" +
                                  "\"%s\");", this.id, this.address, this.district, this.category, this.emergencyRoom, this.name, this.subdivision);
        return sql;
    }

    // Insert Query만드는 로직 튜닝
    public String getTupleString() {
        String sql = String.format(
                "(\"%s\",\"%s\"," +"\"%s\"," +
                        "\"%s\"," +"%d," +"\"%s\"," +"\"%s\");",
                this.id, this.address, this.district, this.category, this.emergencyRoom, this.name, this.subdivision);
        return sql;
    }

    private void setDistrict() {
        String[] splitted = address.split(" ");
        this.district = String.format("%s %s", splitted[0], splitted[1]);
    }

    public String getId() { // id값에 접근 가능
        return id;
    }

    // Getter
    public String getAddress() {
        return address;
    }

    public String getDistrict() { // 19000번 호출되서 속도가 느림
        /*
        String[] splitted = this.address.split(" ");
        return format("%s %s", splitted[0], splitted[1]);
    */
        return district;
    }

    public String getCategory() {
        return category;
    }

    public Integer getEmergencyRoom() {
        return emergencyRoom;
    }

    public String getName() {
        return name;
    }

    public String getSubdivision() {
        return subdivision;
    }
}

