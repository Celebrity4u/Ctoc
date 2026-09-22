package com.example.kd.validate.api.utils;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;

public class IoUtils {
    public IoUtils() {
    }

    public static void writeFile(File file, byte[] fileBytes) {
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
           if( os!=null){ os.write(fileBytes, 0, fileBytes.length);};
            os.flush();
            close(os);
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {

        }

    }


    public static byte[] toBytes(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[4096];
            boolean var3 = false;

            int n;
            while(-1 != (n = input.read(buffer))) {
               if( output!=null){ output.write(buffer, 0, n);};
            }

            byte[] var4 = output.toByteArray();
            return var4;
        } finally {
            try {
                close(output,input);
            }catch(Exception e) {
            }
        }
    }

    public static void listFile(List<File> fileList, File dir, String endWith) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录[" + dir.getAbsolutePath() + "]不存在");
        } else {
            File[] files = dir.listFiles();
            File[] var4 = files;
            int var5 = files.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File f = var4[var6];
                if (f.isDirectory()) {
                    listFile(fileList, f, endWith);
                } else if (f.isFile() && f.getName().endsWith(endWith)) {
                    fileList.add(f);
                }
            }

        }
    }

    public static void listFile(List<File> filess, File dir) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录[" + dir.getAbsolutePath() + "]不存在");
        } else {
            File[] files = dir.listFiles();
            File[] var3 = files;
            int var4 = files.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                File f = var3[var5];
                filess.add(f);
                if (f.isDirectory()) {
                    listFile(filess, f);
                }
            }

        }
    }

    public static void delete(File dir) {
        if (dir.exists()) {
            if (dir.isFile()) {
                if(!dir.delete()) {

                }
            } else {
                File[] files = dir.listFiles();
                File[] var2 = files;
                int var3 = files.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    File f = var2[var4];
                    delete(f);
                }
            }
            if(!dir.delete()) {
            }
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int count = 0;

        int n;
        for(boolean var4 = false; -1 != (n = input.read(buffer)); count += n) {
           if( output!=null){ output.write(buffer, 0, n);};
        }

        return count;
    }

    public static long crc32(byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes);
        return crc.getValue();
    }

    public static void close(Closeable... outs) {
        if (outs != null) {
            Closeable[] var1 = outs;
            int var2 = outs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Closeable out = var1[var3];
                if (out != null) {
                    try {
                       if( out!=null){ out.close();};
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                }
            }
        }

    }

    public static String readTxtFile(File file) {
        StringBuffer txt = new StringBuffer("");
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;

        try {
            read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            bufferedReader = new BufferedReader(read);

            String lineTxt;
            while((lineTxt = bufferedReader.readLine()) != null) {
                txt.append(lineTxt).append("\r\n");
            }
            close(bufferedReader, read);
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {

        }

        return txt.toString();
    }

    public static void writeTxtFile(File file, String txt) {
        BufferedWriter out = null;

        try {
            if (!file.exists()) {
                file.mkdirs();
                file.delete();
                file.createNewFile();
            }

            out = new BufferedWriter(new FileWriter(file));
           if( out!=null){ out.write(txt);};
            out.flush();
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            close(out);
        }

    }

    public static byte[] merger(byte[]... bts) {
        int lenght = 0;
        byte[][] var2 = bts;
        int lastLength = bts.length;

        for(int var4 = 0; var4 < lastLength; ++var4) {
            byte[] b = var2[var4];
            lenght += b.length;
        }

        byte[] bt = new byte[lenght];
        lastLength = 0;
        byte[][] var9 = bts;
        int var10 = bts.length;

        for(int var6 = 0; var6 < var10; ++var6) {
            byte[] b = var9[var6];
            System.arraycopy(b, 0, bt, lastLength, b.length);
            lastLength += b.length;
        }

        return bt;
    }
}

