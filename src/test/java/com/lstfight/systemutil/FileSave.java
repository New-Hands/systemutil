package com.lstfight.systemutil;

import com.lstfight.systemutil.fileutil.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * 如何方便的进行测试
 */
public class FileSave {

    @Test
    public void saveFile() throws IOException {
        long start = System.currentTimeMillis();
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\doc\\QUI.chm");
        FileUtils.saveFileByNIO(inputStream, new File("C:\\Users\\Administrator\\Desktop\\doc\\doc\\QUI.chm"), 1024);
    }


    @Test
    public void copyFileOneTurn() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                FileUtils.fileCopyOneTurn(new File("C:\\Users\\lst\\Desktop\\NLP\\stanford-chinese-corenlp-2017-06-09-models.jar"), new File("C:\\Users\\lst\\Desktop\\NLP\\ch\\stanford-chinese-corenlp-2017-06-09-models.jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        while (true) {
            Thread.sleep(10000);
            System.out.println("dfd");
        }
    }


    @Test
    public void copyFile() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                File targetFile = new File("C:\\Users\\lst\\Desktop\\NLP\\ch\\stanford-chinese-corenlp-2017-06-09-models.jar");
                FileUtils.fileParentProcessor(targetFile);
                FileUtils.fileProcessor(targetFile);
                File file = new File("C:\\Users\\lst\\Desktop\\NLP\\stanford-chinese-corenlp-2017-06-09-models.jar");
                FileUtils.saveFileByNIO(new FileInputStream(file), targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        while (true) {
            Thread.sleep(10000);
            System.out.println("dfd");
        }
    }


}
