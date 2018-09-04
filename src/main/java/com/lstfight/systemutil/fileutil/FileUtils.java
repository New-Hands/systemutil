package com.lstfight.systemutil.fileutil;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * <p>文件处理工具</p>
 * <p>应用层与磁盘交互的方式</p>
 * <p>io nio nio文件内存映射</p>
 *
 * @author 李尚庭
 * @date 2018/8/15 0015 9:08
 */
public class FileUtils {


    /**
     * 工具类禁止初始化
     */
    private FileUtils() {
    }

    /**
     * 通过输入流保存文件到指定路径
     *
     * @param in   文件流
     * @param file 文件描述
     * @return 被保存的文件描述
     */
    public static File saveFile(InputStream in, File file) {
        FileOutputStream outFile = null;
        //文件不存在时新增文件
        if (!file.exists()) {
            try {
                outFile = new FileOutputStream(file);
                byte[] buffer = new byte[256];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    outFile.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outFile != null) {
                    try {
                        outFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file;
    }

    /**
     * 从指定路径读取文件
     *
     * @param file 文件描述
     * @return 文件流
     */
    public static InputStream readFile(File file) {
        //判断文件是否存在
        if (Objects.nonNull(file) && !file.exists()) {
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
                return fileInputStream;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过file获取的输入流保存为文件
     *
     * @param in   文件输入流
     * @param file 目标文件描述
     */
    public static void saveFileByNIO(InputStream in, File file) throws IOException {
        saveFileByNIO(in, file, 1024 * 1024);
    }

    /**
     * 通过file获取的输入流保存为文件
     *
     * @param in         输入流
     * @param file       文件描述符
     * @param bufferSize 缓冲区大小
     */
    public static void saveFileByNIO(InputStream in, File file, int bufferSize) throws IOException {
        long start = System.currentTimeMillis();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        byte[] bytes = new byte[bufferSize];
        int len;
        while ((len = in.read(bytes)) > -1) {
            //这里是一个buffer的坑 读写数据的坑 注意指定长度
            byteBuffer.put(bytes, 0, len);
            //修改position和limit
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
            //capacity
            byteBuffer.clear();
        }

        in.close();
        outputStreamChannel.close();

        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * <p>根据文件大小分配缓冲区 一次性拷贝</p>
     * <p>异步 就是采用回调的方式进行编程</p>
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static void fileCopyOneTurn(File sourceFile, File targetFile) throws IOException {
        //定义缓冲区大小为 文件大小
        ByteBuffer allocate = ByteBuffer.allocate((int) sourceFile.length());
        AsynchronousFileChannel fileInChannel = AsynchronousFileChannel.open(sourceFile.toPath(), StandardOpenOption.READ);
        AsynchronousFileChannel fileOutChannel = AsynchronousFileChannel.open(targetFile.toPath(), StandardOpenOption.WRITE);
        asynchronousFileCopy(allocate, 0, fileInChannel, fileOutChannel);
    }

    public static void fileCopy(File sourceFile, File targetFile) throws IOException {
        System.out.println(System.currentTimeMillis());
        ByteBuffer allocate = ByteBuffer.allocate(1024 * 1024 * 10);
        AsynchronousFileChannel fileInChannel = AsynchronousFileChannel.open(sourceFile.toPath(), StandardOpenOption.READ);
        AsynchronousFileChannel fileOutChannel = AsynchronousFileChannel.open(targetFile.toPath(), StandardOpenOption.WRITE);
        asynchronousFileCopy(allocate, 0, fileInChannel, fileOutChannel);
    }

    private static void asynchronousFileCopy(ByteBuffer allocate, long position, AsynchronousFileChannel fileInChannel, AsynchronousFileChannel fileOutChannel) {
        fileInChannel.read(allocate, position, allocate, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                fileOutChannel.write(attachment, position, attachment, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        attachment.clear();
                        //当写入的数据仍为最大值时 继续读取
                        if (result > 0) {
                            asynchronousFileCopy(allocate, position + result, fileInChannel, fileOutChannel);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("hhhh");
                    }
                });
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc.getMessage());
            }
        });
    }

    /**
     * <p>为文件创建目录</p>
     *
     * @param file 文件描述
     */
    public static void fileParentProcessor(File file) throws IOException {
        //创建文件目录
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IOException("文件目录创建不成功！");
            }
        }
    }

    /**
     * <p>创建文件</p>
     *
     * @param file 文件描述
     * @throws IOException 创建文件文件时 无效路径
     */
    public static void fileProcessor(File file) throws IOException {
        //创建文件目录
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("文件创建不成功！");
            }
        }
    }
}
