package com.m.livedate.mvp.base.net.downfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import okhttp3.ResponseBody;

/**
 * file util
 * 文件保存
 * 删除
 * 打开
 *
 * @author ch
 */
public class FileUtil {


    public static File saveFile(String filePath, ResponseBody body) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            if (filePath == null) {
                return null;
            }
            file = new File(filePath);
            if (file == null || !file.exists()) {
                file.createNewFile();
            }
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;
            byte[] fileReader = new byte[4096];
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * @param filePath
     * @param start    起始位置
     * @param body
     */
    public static File saveFile(String filePath, long start, ResponseBody body) {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        File file = null;
        try {
            file = new File(filePath);
            raf = new RandomAccessFile(filePath, "rw");
            inputStream = body.byteStream();
            byte[] fileReader = new byte[4096];
            raf.seek(start);
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                raf.write(fileReader, 0, read);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;

    }

    /**
     * 打开apk
     *
     * @param context
     * @param pathName 文件路径
     */
    public static void openFile(Context context, String pathName, String authority) {
        if (pathName == null) {
            return;
        }
        File file = new File(pathName);
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(FileProvider.getUriForFile(context, authority, file), "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        Log.d("SSSSSSS", "delete: " + file.exists());
        if (!file.exists()) {
            Log.d("SSSSSS", "delete: " + "删除文件失败:" + delFile + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
                return deleteDirectory(delFile);
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.d("SSSSSSSSSS", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Log.d("SSSSSSSSSS", "deleteSingleFile: " + "删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            Log.d("SSSSSSSSSSs", "deleteSingleFile: " + "删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.d("SSSSSS", "delete: " + "删除文件失败:不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Log.d("SSSSSS", "delete: " + "删除文件失败:");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            Log.d("SSSSSS", "delete: " + "删除文件失败:");
            return false;
        }
    }

}
