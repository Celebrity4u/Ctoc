package com.example.kd.validate.api.data;

import com.example.kd.validate.api.resq.EvaluateData;

public class ParseTask implements Runnable {
    private CallBackInterface c;
    private EvaluateData data;

    public ParseTask(CallBackInterface c, EvaluateData data) {
        this.c = c;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            parseData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 数据解析(DM)
     *
     * @param data
     */
    private void parseData(EvaluateData data) {
        this.c.parse(data);
    }



}
