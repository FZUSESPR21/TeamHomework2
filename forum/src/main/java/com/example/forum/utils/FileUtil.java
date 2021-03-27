package com.example.forum.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class FileUtil
{
    //获取目录下的所有文件。
    public  static List<File> getFiles(String path){
        File root =  new  File(path);
        List<File> files =  new ArrayList<File>();
        if (!root.isDirectory()){
            files.add(root);
        } else {
            File[] subFiles = root.listFiles();
            for (File f : subFiles){
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return  files;
    }
}
