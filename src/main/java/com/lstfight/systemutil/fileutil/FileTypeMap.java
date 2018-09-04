package com.lstfight.systemutil.fileutil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>文件类型容器</p>
 *
 * @author 李尚庭
 * @date 2018/8/27 0027 9:44
 */
public class FileTypeMap {
    private static Map<String, String> FILE_MAP = new HashMap<>();

    private FileTypeMap() {}

    /**
     * 添加新文件类型
     *
     * @param fileType 文件类型
     */
    public static void registerFileType(FileType fileType) {

    }
}
