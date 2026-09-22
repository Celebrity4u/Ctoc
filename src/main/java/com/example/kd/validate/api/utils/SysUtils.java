package com.example.kd.validate.api.utils;

import java.io.Closeable;
import java.io.File;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.*;

public class SysUtils {
    public SysUtils() {
    }

    public static String runCmd(String cmd, int line) {
        Scanner sc = null;
        StringBuffer sb = new StringBuffer();

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            if(process.getOutputStream()!=null){process.getOutputStream().close();};
            sc = new Scanner(process.getInputStream());
            int i = 0;

            while(sc.hasNextLine()) {
                ++i;
                String str = sc.nextLine();
                if (line <= 0) {
                    sb.append(str).append("\r\n");
                } else if (i == line) {
                    String var7 = str.trim();
                    return var7;
                }
            }

           if( sc!=null){ sc.close();};
            return sb.toString();
        } catch (Exception var11) {
            return sb.toString();
        } finally {
           try {
                IoUtils.close(new Closeable[]{sc});
            }catch(Exception e) {

            }
        }
    }

    public static String runCmd(String cmd, String substr) {
        Scanner sc = null;

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            if(process.getOutputStream()!=null){process.getOutputStream().close();};
            sc = new Scanner(process.getInputStream());

            while(sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str != null && str.contains(substr)) {
                    String var5 = str.trim();
                    return var5;
                }
            }

           if( sc!=null){ sc.close();};
        } catch (Exception var9) {
        } finally {
            try {
                IoUtils.close(new Closeable[]{sc});
            }catch(Exception e) {

            }
        }

        return null;
    }

    public static List<String> getMxyzList() {
        ArrayList<String> list = new ArrayList();
        StringBuilder sb = new StringBuilder();

        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            label59:
            while(en.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface)en.nextElement();
                List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
                Iterator var5 = addrs.iterator();

                while(true) {
                    byte[] mac;
                    do {
                        NetworkInterface network;
                        do {
                            InetAddress ip;
                            do {
                                if (!var5.hasNext()) {
                                    continue label59;
                                }

                                InterfaceAddress addr = (InterfaceAddress)var5.next();
                                ip = addr.getAddress();
                            } while(ip.isLinkLocalAddress());

                            network = NetworkInterface.getByInetAddress(ip);
                        } while(network == null);

                        mac = network.getHardwareAddress();
                    } while(mac == null);

                    sb.delete(0, sb.length());

                    for(int i = 0; i < mac.length; ++i) {
                        sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
                    }

                    if (!list.contains(sb.toString())) {
                        list.add(sb.toString());
                    }
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return list;
    }

    public static String getAEAC() {
        String sysName = System.getProperty("os.name");
        String str;
        if (sysName.contains("Windows")) {
            str = runCmd("wmic cpu get ProcessorId", 2);
            return str;
        } else {
            if (sysName.contains("Linux")) {
                str = runCmd("dmidecode |grep -A16 \"Processor Information$\"", "ID");
                if (str != null) {
                    return str.substring(str.indexOf(':')).trim();
                }
            } else if (sysName.contains("Mac")) {
                str = runCmd("system_profiler SPHardwareDataType", "Serial Number");
                if (str != null) {
                    return str.substring(str.indexOf(':') + 1).trim();
                }
            }

            return "";
        }
    }
    public static String getZAOP() {
        String sysName = System.getProperty("os.name");
        String str="";
        if (sysName.contains("Windows")) {
            String[] windows ={"wmic","cpu","get","ProcessorId"};
            try {
                Process process = Runtime.getRuntime().exec(windows);
                if(process.getOutputStream()!=null){process.getOutputStream().close();};
                Scanner sc = new Scanner(process.getInputStream(),"utf-8");
                sc.next();
                str = sc.next();
            }catch (Exception e)
            {

            }
            return str;
        } else {
            if (sysName.contains("Linux")) {
                str = runCmd("dmidecode |grep -A16 \"Processor Information$\"", "ID");
                if (str != null) {
                    return str.substring(str.indexOf(':')).trim();
                }
            } else if (sysName.contains("Mac")) {
                str = runCmd("system_profiler SPHardwareDataType", "Serial Number");
                if (str != null) {
                    return str.substring(str.indexOf(':') + 1).trim();
                }
            }

            return "";
        }
    }
    public static String getPOIU() {
        String sysName = System.getProperty("os.name");
        String str;
        if (sysName.contains("Windows")) {
            str = runCmd("wmic path win32_physicalmedia get serialnumber", 2);
            return str;
        } else {
            if (sysName.contains("Linux")) {
                str = runCmd("dmidecode |grep -A16 \"System Information$\"", "Serial Number");
                if (str != null) {
                    return str.substring(str.indexOf(':')).trim();
                }
            } else if (sysName.contains("Mac")) {
                str = runCmd("system_profiler SPStorageDataType", "Volume UUID");
                if (str != null) {
                    return str.substring(str.indexOf(':') + 1).trim();
                }
            }

            return "";
        }
    }
    public static String getADGN() {
        String sysName = System.getProperty("os.name");
        String str="";
        if (sysName.contains("Windows")) {
            String[] windows ={"wmic","path","win32_physicalmedia","get","serialnumber"};
            try {
                Process process = Runtime.getRuntime().exec(windows);
                if(process.getOutputStream()!=null){process.getOutputStream().close();};
                Scanner sc = new Scanner(process.getInputStream(),"utf-8");
                sc.next();
                str = sc.next();
            }catch (Exception e)
            {

            }
            return str;
        } else {
            if (sysName.contains("Linux")) {
                str = runCmd("dmidecode |grep -A16 \"System Information$\"", "Serial Number");
                if (str != null) {
                    return str.substring(str.indexOf(':')).trim();
                }
            } else if (sysName.contains("Mac")) {
                str = runCmd("system_profiler SPStorageDataType", "Volume UUID");
                if (str != null) {
                    return str.substring(str.indexOf(':') + 1).trim();
                }
            }

            return "";
        }
    }
    public static String makeM() {
        String mac = getMxyzList().toString();
        String getCPUId = getZAOP();
        String getHardDiskId = getADGN();
        String md5 = MD5Util.MD5(mac+getCPUId+getHardDiskId);
        return md5;
        /*char[] c1 = EncryptUtils.md5(getMacList().toString().toCharArray());
        char[] c2 = EncryptUtils.md5(getCPUId().toCharArray());
        char[] c3 = EncryptUtils.md5(getHardDiskId().toCharArray());
        char[] chars = StrUtils.merger(new char[][]{c1, c2, c3});
        System.out.println(mac +"#"+getCPUId +"#"+getHardDiskId);
        System.out.println(mac +"#"+getCPUSerialNumber +"#"+getHardDiskSerialNumber);
        for(int i = 0; i < chars.length; ++i) {
            chars[i] = Character.toUpperCase(chars[i]);
        }
        return chars;*/
    }

    public static boolean isLinux(){
        if (System.getProperty("os.name").toLowerCase().contains("win")){
            return false;
        }else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return true;
        }
        return false;
    }
   public static Long getSQLLeftCapacity()
   {
       String m = System.getenv("path");
       String[] paths = m.split(";");
       String sqlPath = "";
       for(int i = 0 ;i < paths.length ;i++)
       {
           if(paths[i].toLowerCase().contains("mysql"))
           {
               sqlPath = paths[i];
               break;
           }
       }
       if(StringUtils.isEmpty(sqlPath))
       {
           return 1000000l;
       }

       File cFile = new File(sqlPath.substring(0,2));
       Long totalCapacity = cFile.getTotalSpace();
       Long freeCapacity = cFile.getFreeSpace();
       Double free = (freeCapacity.doubleValue()) / totalCapacity.doubleValue() ;
       Long lef =  freeCapacity /(1024 * 1024);
       return lef;
   }
   public static String getenv(String MODE){
        String flagMODE = System.getProperty(MODE);
        return flagMODE;
    }
}