package com.example.kd.validate.api.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CmdExecutor {
    public static void main(String[] args) {
        // 要执行的CMD命令（示例：查看系统信息）
        String command = "verify_score.exe 123344.txt";

        try {
            // 执行CMD命令并获取输出
            List<String> output = executeCmd(command);
            // 打印输出结果
            for (String line : output) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> executeCmd(String command) throws IOException {
        return executeCmd(command, 300);
    }

    /**
     * 执行CMD命令并获取输出
     *
     * @param command        命令
     * @param timeoutSeconds 超时秒数,超时返回null
     */
    public static List<String> executeCmd(String command, int timeoutSeconds) throws IOException {
        // 构建进程命令（Windows系统需先启动cmd.exe）
        List<String> cmd = new ArrayList<String>();
        cmd.add("cmd");
        cmd.add("/c");
        cmd.add(command);

        // 创建进程构建器
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        // 重定向错误流到输出流，确保错误信息被捕获
        processBuilder.redirectErrorStream(true);

        // 启动进程
        final Process process = processBuilder.start();

        // 后台线程读取输出：失败单输出可能几百行，若主线程不同时消费，
        // 管道缓冲区写满会阻塞exe本身；同时旧写法"先读行再waitFor"会让超时保护失效
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = process.getInputStream();
                    byte[] buf = new byte[8192];
                    int n;
                    while ((n = in.read(buf)) != -1) {
                        baos.write(buf, 0, n);
                    }
                } catch (Exception e) {
                    // 进程被销毁时读流会抛异常，忽略
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();

        // 主线程等待进程执行完毕，超时则销毁进程
        try {
            boolean finished = process.waitFor(timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                //System.out.println("命令执行超时");
                return null;
            }
            // 进程已退出，等待剩余输出读完
            readerThread.join(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("命令执行被中断", e);
        }

        // 编码自动探测：甲题verify_score.exe输出UTF-8中文，乙题AtCTOC14Main.exe输出GBK，
        // 固定编码读取会导致中文明细乱码（如"鏍煎紡..."），纯ASCII输出不受影响
        String text = decodeSmart(baos.toByteArray());

        // 按行切分（与原readLine行为一致，末尾无多余空行）
        List<String> output = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new StringReader(text));
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (Exception e) {

        }

        return output;
    }

    /**
     * 编码自动探测：先用UTF-8严格解码，字节序列非法（如GBK双字节流）时回退GBK
     */
    private static String decodeSmart(byte[] bytes) {
        CharsetDecoder utf8Decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);
        try {
            CharBuffer charBuffer = utf8Decoder.decode(ByteBuffer.wrap(bytes));
            return charBuffer.toString();
        } catch (CharacterCodingException e) {
            try {
                return new String(bytes, "GBK");
            } catch (UnsupportedEncodingException ex) {
                return new String(bytes, StandardCharsets.UTF_8);
            }
        }
    }
}
