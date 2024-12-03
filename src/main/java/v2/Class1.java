package v2;

public class Class1 {
    private Class2 class2;

    // OR
    public Class1() {
        //
        this.class2 = new Class2(new Class3());
        this.class2.setClass3(new Class3());
    }
}
