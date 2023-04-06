package study.mvc;

class MyCommandObject {
    private String value1;
    private Integer value2;

    // 반드시 세터 메서드가 있어야 함
    public void setValue1(String value1) { this.value1 = value1; }
    public void setValue2(Integer value2) { this.value2 = value2; }

    @Override
    public String toString() {
        return "MyCommandObject{" +
                "value1='" + value1 + '\'' +
                ", value2=" + value2 +
                '}';
    }
}