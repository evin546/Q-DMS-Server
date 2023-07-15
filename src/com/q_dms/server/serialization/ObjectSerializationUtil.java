package com.q_dms.server.serialization;

import java.io.*;

/**
 * 对对象进行序列化和反序列化操作的工具类
 */
public class ObjectSerializationUtil {

    /**
     * 静态方法：对传入的任意对象进行序列化处理
     *
     * @param object 任意对象
     * @return 字节数组
     * @throws IOException 处理失败
     */
    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(object);
        //释放资源
        objectStream.close();
        return byteStream.toByteArray();
    }

    /**
     * 静态方法：对传入的对象序列化后生成的字节数组进行反序列化处理
     *
     * @param data 字节数组
     * @return (使用前必须进行强转 ！)Object类型的对象，
     * @throws IOException            .
     * @throws ClassNotFoundException .
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Object object = objectStream.readObject();
        //释放资源
        objectStream.close();
        return object;
    }


}
