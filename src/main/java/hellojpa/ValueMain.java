package hellojpa;

public class ValueMain {

    public static void main(String[] args) {
        int a = 10;
        int b = a;

        a= 20;

        Integer a1 = 10;
        Integer b1 = a1;

//        a1.setValue(20)이 있다고 가정 시 a1, b1 둘다 20이 됨(공유 가능) 하지만 변경 불가능하게 해서 side effect 방지

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("a1 = " + a1);
        System.out.println("b1 = " + b1);

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2" + (address1 == address2));
        // 값타입 비교는 항상 equals 비교 사용
        System.out.println("address1 equals address2" + (address1.equals(address2)));
    }
}
