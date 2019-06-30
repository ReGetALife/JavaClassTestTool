package utils;

import java.io.File;

import java.lang.Boolean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import jxl.*;
import jxl.write.*;

import java.text.NumberFormat;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ClassTester {

    static private ClassLoader myClassPath;
    static private Class myClass;

    static private Method[] methodsArr;
    static private Method method;
    static private String[] methods;

    static private String[] paramArr;
    static private Field[] fs;
    static private String[] returnArr;

    /**
     * 获取类中方法
     *
     * @param javaPath java文件路径
     * @return 类中方法数组（包括返回类型、方法名、参数类型）
     * @throws Exception 异常
     */
    public static String[] getClassMethods(String javaPath) throws Exception {

        String[] pathArr = javaPath.split("\\\\");
        String[] classArr = Arrays.copyOfRange(pathArr, 0, pathArr.length - 1);
        //获取编译结果存放路径
        String classPath = String.join("\\", classArr);
        String className = pathArr[pathArr.length - 1].split("\\.")[0];

        // 执行编译任务
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = standardFileManager.getJavaFileObjects(javaPath);
        CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, iterable);
        task.call();
        standardFileManager.close();

        //获取方法
        myClassPath = new MyClassLoad(classPath);
        myClass = myClassPath.loadClass(className);
        methodsArr = myClass.getMethods();
        methods = new String[methodsArr.length - 9];

        for (int i = 0; i < methodsArr.length - 9; i++) {
            // 得到方法的返回值类型
            Class<?> returnType = methodsArr[i].getReturnType();
            //获取方法名
            methods[i] = returnType.getName() + " " + methodsArr[i].getName() + "(";
            // 获取参数类型
            Class[] paramTypes = methodsArr[i].getParameterTypes();
            String[] paramArr = new String[paramTypes.length];
            for (int j = 0; j < paramTypes.length; j++) {
                paramArr[j] = paramTypes[j].getName();
            }
            String paramString = String.join(", ", paramArr);
            methods[i] += paramString + ")";
        }/*
        for(int j = 0; j < methods.length; j++){
            System.out.println(methods[j]);
        }
*/
        //返回类中所有方法
        return methods;
    }

    public static String[] getClassMethods(){
        return methods;
    }

    /**
     * 设置方法
     *
     * @param MethodID 方法在数组中的位置
     * @throws Exception 异常
     */
    public static void setCurrentMethod(int MethodID) throws Exception {
        method = methodsArr[MethodID];

        //获取方法参数类型
        Class[] paramTypes = method.getParameterTypes();
        paramArr = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramArr[i] = paramTypes[i].getName();
        }

        //获取方法返回值
        String param = method.getReturnType().getName();
        //返回值为基本数据类型
        if (param.equals("int") || param.equals("java.lang.String") || param.equals("char") || param.equals("byte") || param.equals("short") || param.equals("long") || param.equals("float") || param.equals("double") || param.equals("boolean") || param.equals("void")) {
            returnArr = new String[]{param};
        }
        //返回值为类
        else {
            Class returnClass = myClassPath.loadClass(method.getReturnType().getName());

            fs = returnClass.getFields();
            returnArr = new String[fs.length];
            for (int i = 0; i < fs.length; i++) {
                returnArr[i] = fs[i].getType() + " " + fs[i].getName();
            }

        }
    }

    /**
     * 获取方法参数类型
     *
     * @return 参数类型数组
     */
    public static String[] getMethodParam() {
        return paramArr;
    }

    /**
     * 获取方法返回值类型
     *
     * @return 返回值类型数组
     */
    public static String[] getMethodReturn() {
        return returnArr;
    }

    /**
     * 执行测试
     *
     * @param testFile  测试文件路径
     * @param paramPos  方法参数所在位置
     * @param resultPos 正确结果所在位置
     * @param returnPos 测试结果存放位置
     * @param isTruePos 正误标志存放位置
     * @return 执行结果统计或错误提示信息
     * @throws Exception 异常
     */
    public static String runTest(String testFile, int[] paramPos, int[] resultPos, int[] returnPos, int isTruePos) throws Exception {

        Object o = myClass.newInstance();

        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(new File(testFile));
        } catch (Exception e) {
            return "请确定测试文件是否存在";
        }

        WritableWorkbook resultbook;
        String resultPath = testFile.split("\\.")[0] + "_result.xls";
        try {
            resultbook = Workbook.createWorkbook(new File(resultPath), workbook);
        } catch (Exception e) {
            return "请检查结果文件\"" + resultPath + "\"是否正处于打开状态或被其他程序占用";
        }
        Sheet sheet = workbook.getSheet(0);
        WritableSheet writablesheet = resultbook.getSheet(0);
        int rows = sheet.getRows();
        //int cols = sheet.getColumns();
//        System.out.println("总列数：" + cols);System.out.println("总行数:" + rows);System.out.println("----------------------------");

        int trueNum = 0;
        for (int i = 1; i < rows; i++) {
            Class[] paramTypeArr = new Class[paramArr.length];
            Object[] valueArr = new Object[paramArr.length];

            for (int j = 0; j < paramPos.length; j++) {
                paramTypeArr[j] = typeClass(paramArr[j]);
//                System.out.println(sheet.getCell(paramPos[j], i).getContents());
                switch (paramArr[j]) {
                    case "int":
                        valueArr[j] = Integer.parseInt(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "double":
                        valueArr[j] = Double.parseDouble(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "float":
                        valueArr[j] = Float.parseFloat(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "char":
                        valueArr[j] = sheet.getCell(paramPos[j], i).getContents().charAt(0);
                        break;
                    case "byte":
                        valueArr[j] = Byte.parseByte(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "short":
                        valueArr[j] = Short.parseShort(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "long":
                        valueArr[j] = Long.parseLong(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    case "boolean":
                        valueArr[j] = Boolean.parseBoolean(sheet.getCell(paramPos[j], i).getContents());
                        break;
                    default:
                        valueArr[j] = sheet.getCell(paramPos[j], i).getContents();
                        break;
                }
            }

            Object obj = myClass.getMethod(method.getName(), paramTypeArr).invoke(o, valueArr);

            if (obj == null)
                return "测试方法没有返回值";
            else {
                boolean isTrue = true;
                if (returnArr.length == 1) {
                    writablesheet.addCell(new Label(returnPos[0], i, obj.toString()));
                    if (!sheet.getCell(resultPos[0], i).getContents().equals(obj.toString()))
                        isTrue = false;
                } else {
//                    System.out.println(obj.getClass().getName());
                    for (int j = 0; j < fs.length; j++) {
                        Field f = fs[j];
                        f.setAccessible(true); // 设置些属性是可以访问的
                        Object val = f.get(obj);
                        writablesheet.addCell(new Label(returnPos[j], i, val.toString()));

                        if (!sheet.getCell(resultPos[j], i).getContents().equals(val.toString()))
                            isTrue = false;
//                        System.out.println(f.getName() + " " + val);
                    }
                }

                if (isTrue) {
                    writablesheet.addCell(new Label(isTruePos, i, "true" + ""));
                    trueNum++;
                } else {
                    WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
                    WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
                    writablesheet.addCell(new Label(isTruePos, i, "false", wcfFC));
                }
//                System.out.println(isTrue);
            }
        }
        resultbook.write();
        resultbook.close();

        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);//设置保留几位小数
        int row = rows - 1;
        String truePercent = format.format((double) trueNum / (double) (row));
        return "成功执行了" + row + "条测试用例，正确率为" + truePercent + "，已将结果保存于\"" + resultPath + "\"";
    }


    //获取基本类型对应的类
    private static Class typeClass(String type) {
        switch (type) {
            case "int":
                return int.class;
            case "double":
                return double.class;
            case "float":
                return float.class;
            case "char":
                return char.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "long":
                return long.class;
            case "boolean":
                return boolean.class;
            default:
                return java.lang.String.class;

        }
    }

}
