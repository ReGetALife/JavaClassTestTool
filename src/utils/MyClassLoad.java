package utils;

import java.io.*;

public class MyClassLoad extends ClassLoader {
    private String rootPath;

    MyClassLoad(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 根据name来寻找该类
     */
    @Override
    protected Class<?> findClass(String name) {
        Class<?> c = findLoadedClass(name);
        if (c == null) { // 内存堆中还没加载该类
            c = findMyClass(name); // 自己实现加载类
        }
        return c;
    }

    /**
     * 加载该类
     *
     * @param name 类名
     * @return 类
     */
    private Class<?> findMyClass(String name) {
        try {
            byte[] bytes = getData(name);
            if (bytes != null) {
                return this.defineClass(null, bytes, 0, bytes.length); // 调用父类方法，生成具体类
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getData(String className) {
        String path = rootPath + File.separatorChar + className + ".class";
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
