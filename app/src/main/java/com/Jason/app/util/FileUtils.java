package com.Jason.app.util;

import android.os.Environment;
import android.util.Log;

import com.Jason.app.model.FileInfo;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.lzy.okgo.utils.HttpUtils.deleteFile;

/**
 * Created by syd on 2017/6/16.
 */

public class FileUtils {
    private static final String TAG = "SydCamera";
    public static int TYPE_PHOTO = 0;
    public static int TYPE_VIDEO = 1;

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type, String filePath) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_PATH);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "can not get sdcard!");
            return null;
        }
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), filePath);
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath());
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            Log.i(TAG, "mkdirs: " + mediaStorageDir.getPath());
            if (!mediaStorageDir.mkdirs()) {
                Log.e(TAG, "failed to create directory");
                return null;
            }
        } else {
            Log.i(TAG, "mkdirs,文件夹已存在： " + mediaStorageDir.getPath());
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "renlian.jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "renlian.mp4");
        } else {
            return null;
        }
//        try {
//            Log.d(TAG, "mediaFile.createNewFile(): " + mediaFile.createNewFile());
//        } catch (IOException e) {
//            Log.e(TAG, "createNewFile IOException: " + e);
//            e.printStackTrace();
//        }
        return mediaFile;
    }

    /**
     * 获取文件存储文件夹
     *
     * @param filePath
     * @return
     */
    public static String getMediaStorageDir(String filePath) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "can not get sdcard!");
            return null;
        }
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), filePath);
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath());
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            Log.i(TAG, "mkdirs: " + mediaStorageDir.getPath());
            if (!mediaStorageDir.mkdirs()) {
                Log.e(TAG, "failed to create directory");
                return null;
            }
        } else {
            Log.i(TAG, "mkdirs,文件夹已存在： " + mediaStorageDir.getPath());
        }
        return mediaStorageDir.getPath();
    }

    public static ArrayList<FileInfo> ListFilesByTime(String path, int fileType) {
        File[] files;
        if (fileType == TYPE_PHOTO) {
            files = new File(path).listFiles(photoFilter);
        } else {
            files = new File(path).listFiles(videoFilter);
        }

        ArrayList<FileInfo> fileList = new ArrayList<>();//将需要的子文件信息存入到FileInfo里面

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            FileInfo fileInfo = new FileInfo();
            fileInfo.name = file.getName();
            fileInfo.path = file.getPath();
            fileInfo.lastModified = file.lastModified();
            fileList.add(fileInfo);
        }
        Collections.sort(fileList, new FileComparator());//通过重写Comparator的实现类FileComparator来实现按文件创建时间排序。
        return fileList;
    }

    private static FileFilter photoFilter = new FileFilter() {
        public boolean accept(File file) {
            String tmp = file.getName().toLowerCase();
            if (tmp.endsWith(".png") || tmp.endsWith(".jpg")) {
                return true;
            }
            return false;
        }
    };

    private static FileFilter videoFilter = new FileFilter() {
        public boolean accept(File file) {
            String tmp = file.getName().toLowerCase();
            if (tmp.endsWith(".mp4")) {
                return true;
            }
            return false;
        }
    };

    /**
     * 按时间从小到大排序
     */
    private static class FileComparator implements Comparator<FileInfo> {
        public int compare(FileInfo file1, FileInfo file2) {
            if (file1.lastModified < file2.lastModified) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile()) return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /** 删除单个文件
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     * */
    public  static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
