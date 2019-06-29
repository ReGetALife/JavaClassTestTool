package classtester;

public class Main {

    //测试例子
    public static void main(String[] args) {
        //声明新对象
        ClassTester classTester = new ClassTester();
        try {
            //获取文件中类的所有方法
            String[] me = classTester.getClassMethods("C:\\Users\\13643\\Desktop\\sales\\src\\sales.java");
            //String[] me = classTester.getClassMethods("C:\\Users\\13643\\Desktop\\classtester\\HelloWorld2.java");
            //输出方法数组
            for(String a : me){
                System.out.println(a);
            }
            //选择方法数组中的第0个方法
            classTester.setCurrentMethod(0);
            //获取选中方法的参数
            String[] mp = classTester.getMethodParam();
            for(String a : mp){
                System.out.println(a);
            }
            //获取选中方法的返回值
            String[] mr = classTester.getMethodReturn();
            for(String a : mr){
                System.out.println(a);
            }
            //执行测试
            //System.out.println(classTester.runTest("C:\\Users\\13643\\Desktop\\sales.xls", new int[]{1},new int[]{5},new int[]{7}, 9));
            System.out.println(classTester.runTest("C:\\Users\\13643\\Desktop\\sales.xls", new int[]{1,2,3},new int[]{5,4},new int[]{7,6}, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
