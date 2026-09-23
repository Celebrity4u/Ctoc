package com.example.kd.validate.api.control;


import cn.hutool.core.text.StrBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.example.kd.validate.api.data.*;
import com.example.kd.validate.api.req.EvaluateREQ;
import com.example.kd.validate.api.resq.EvaluateData;
import com.example.kd.validate.api.resq.EvaluateRESQ;
import com.example.kd.validate.api.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author zl
 * Date 2025/6/27
 * Version 1.0
 */
@Api(description = "竞赛平台模块")
@CrossOrigin
@RestController
@EnableAsync
@RequestMapping(path = "/api")
public class ValidateController {
    private static final ExecutorService cachedThreadPool;
    static{
        int count = Runtime.getRuntime().availableProcessors();
        cachedThreadPool = Executors.newFixedThreadPool(2 * count +1);
    }
    private ParseTask task;
    private CallBackInterface callBackInterface;

    @Value("${file.httpFilePath}")
    private String httpFilePath;

    @Value("${file.fileDataLocalPath}")
    private String fileDataLocalPath;
    @Value("${file.fileDownLocalPath}")
    private String fileDownLocalPath;

    @Value("${file.fileUpLocalPath}")
    private String fileUpLocalPath;

    @ApiOperation(value = "发起评测任务", notes = "发起评测任务接口")
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public SimResult<Boolean> evaluate(@RequestBody @Valid EvaluateREQ req, HttpServletRequest request, HttpServletResponse response) {
        String key = request.getHeader("COMPETITION-KEY");
        LogUtil.logColor("evaluate",req.getFile_url()+"##"+req.getRequest_id()+"##"+key, ColorType.YELLOW.getCode(),true);
        /*if(!req.getFile_url().endsWith(".xml"))
        {
            return ResultData.errorData("下载地址格式不正确，暂时只支持xml格式文件");
        }*/
        // 题型:1=甲题(默认),2=乙题;传其他值直接拒绝
        final String topicType = StringUtils.isEmpty(req.getTopic_type()) ? "1" : req.getTopic_type();
        if(!"1".equals(topicType) && !"2".equals(topicType))
        {
            return ResultData.errorData("topic_type取值不正确，只支持1(甲题)或2(乙题)");
        }
        final String currentPath = System.getProperty("user.dir");
        String dataName = req.getFile_url();
        if(dataName.contains("filename="))
        {
            // 截取 filename= 之后的文件名(含扩展名,兼容甲题.txt/乙题.atk)
            int start = dataName.lastIndexOf("filename=")+9;
            int end = dataName.length();
            int qIndex = dataName.indexOf('"', start);
            if(qIndex > 0)
            {
                end = qIndex;
            }
            int ampIndex = dataName.indexOf('&', start);
            if(ampIndex > 0 && ampIndex < end)
            {
                end = ampIndex;
            }
            dataName = dataName.substring(start, end);
            if(Constants.encode)
            {
                try {
                    dataName = URLDecoder.decode(dataName,"UTF-8");
                }catch (Exception e)
                {

                }
            }
        }else{
            return ResultData.errorData("没有附加filename");
        }
        /*if(StringUtils.isEmpty(key))
        {
            if(req.getFile_url().contains("39.96.176.243") || req.getFile_url().contains("192.168.3.118"))
            {
                key = "B0F4073DC10AD344B1A1D215E24C9C0F";
            }else{
                return ResultData.errorData("请传入正确秘钥");
            }
        }else{
            if(!key.equals("B0F4073DC10AD344B1A1D215E24C9C0F"))
            {
                return ResultData.errorData("请传入正确秘钥");
            }
        }*/
        String fileName = IdWorker.get32UUID()+".txt";
        callBackInterface  = new CallBackInterface() {
            @Override
            public void parse(EvaluateData data) {
                try {
                    //long resultData = HttpUtil.downloadFile(req.getFile_url(),currentPath+ fileDataLocalPath+dataName);
                    //long result = HttpUtil.downloadFile(data.getFile_url(),data.getData_path());
                    // 下载带60秒超时，防止慢URL把线程永久占住
                    HttpResponse dlResp = HttpRequest.get(data.getFile_url()).timeout(60000).execute();
                    dlResp.writeBody(data.getData_path());
                    FileUtil.copyFile(data.getData_path(),data.getDown_path());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                EvaluateRESQ evaluateRESQ = new EvaluateRESQ();
                evaluateRESQ.setCode(0);
                evaluateRESQ.setMessage("请求成功");
                DataBoX dataBoX = new DataBoX();
                dataBoX.setRequest_id(data.getRequest_id());
                ResultBo result = new ResultBo();
                String evaluateRESQStr = "";

                File file = new File(data.getData_path());
                if(file.exists())
                {
                    try {
                        // 甲题走verify_score.exe,乙题走AtCTOC14Main.exe
                        // 文件名可能含空格(平台按"队伍名_编号_时间戳"命名,如"JUST OK_156_xxx.atk")，
                        // 用参数列表方式直接启动exe(不经cmd.exe)，避免cmd引号剥离规则把带空格路径拆断
                        String exeName = "2".equals(topicType) ? "AtCTOC14Main.exe" : "verify_score.exe";
                        // 执行命令并获取输出(超时5分钟,超时时返回null)
                        List<String> output = CmdExecutor.executeCmd(Arrays.asList(exeName, data.getData_path()), 300);
                        System.setProperty("sun.jnu.encoding", "GBK");
                        DataBo dataBo = new DataBo();
                        if(output == null)
                        {
                            result.setStatus("fail");
                            result.setMessage("验证超时,请稍后重试。");
                        }
                        else if("2".equals(topicType))
                        {
                            // 乙题协议:通过→第1行"验证通过"、第2行F1、第3行F2原始值(展示值由后端计算);
                            // 不通过→第1行"验证不通过"、第2行错误说明(如"非可行解...")
                            if(output.size() >= 3 && "验证通过".equals(output.get(0)))
                            {
                                result.setStatus("success");
                                dataBo.setF1(output.get(1));
                                dataBo.setF2(output.get(2));
                            }else if(output.size() == 1 && "Failed to open file".equals(output.get(0))){
                                // exe打不开文件(文件名异常/文件不完整)时明确提示，不要误导成"答案验证不通过"
                                result.setStatus("fail");
                                result.setMessage("验证文件打开失败，请重新提交");
                            }else{
                                result.setStatus("fail");
                                result.setMessage(output.size() >= 2 ? output.get(1) : "验证不通过");
                            }
                        }
                        else
                        {
                            // 甲题协议(与原有逻辑一致):第1行"1"=通过、第2行=得分N;
                            // 否则第2行=错误条数、后续行=错误明细
                            if(output.size() == 2)
                            {
                                result.setStatus("success");
                            }else{
                                result.setStatus("fail");
                            }
                            if(output.size()>0)
                            {
                                String reuslt = output.get(0);

                                if(reuslt.equals("1"))
                                {
                                    String lastSub = output.get(1);
                                    dataBo.setN(lastSub);
                                }else{
                                    Integer errorTotal = Integer.valueOf(output.get(1));
                                    // 明细最多回传前50条：失败单明细可能几百行，全量回传报文过大，
                                    // 主服务处理失败会导致任务一直"验证中"被判超时；错误总条数保持真实值
                                    int maxDetail = Math.min(errorTotal, output.size() - 2);
                                    maxDetail = Math.min(maxDetail, 50);
                                    StrBuilder strBuilder = new StrBuilder();
                                    for(int i = 0 ;i < maxDetail;i++)
                                    {
                                        strBuilder.append(output.get(2+i));
                                        strBuilder.append("\n");
                                    }
                                    if(errorTotal > maxDetail)
                                    {
                                        strBuilder.append("……(共"+errorTotal+"条错误，仅展示前"+maxDetail+"条)");
                                    }
                                    result.setMessage(strBuilder.toString());
                                }
                            }else{
                                result.setStatus("fail");
                                result.setMessage("解析异常。");
                            }
                        }

                        result.setData(dataBo);
                        dataBoX.setResult(result);
                        evaluateRESQ.setData(dataBoX);
                        evaluateRESQStr = JSON.toJSONString(evaluateRESQ);
                        if(Constants.fileDelete)
                        {
                            file.delete();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        // 输出格式异常(如exe崩溃、行数不足)时兜底,保证仍能回调后端
                        e.printStackTrace();
                        result.setStatus("fail");
                        result.setMessage("解析异常。");
                        result.setData(new DataBo());
                        dataBoX.setResult(result);
                        evaluateRESQ.setData(dataBoX);
                        evaluateRESQStr = JSON.toJSONString(evaluateRESQ);
                    }

                }else{
                    result.setStatus("fail");
                    DataBo dataBo = new DataBo();
                    result.setData(dataBo);
                    result.setMessage("文件下载失败，请核实下载链接");
                    dataBoX.setResult(result);
                    evaluateRESQ.setData(dataBoX);
                    evaluateRESQStr = JSON.toJSONString(evaluateRESQ);
                }


                String sendUrl = "http://"+Constants.mainServiceIp+"/api/kd_competitions/callback.json";
                if(Constants.openMainHttps)
                {
                    sendUrl= sendUrl.replaceAll("http://","https://");
                }else
                {
                    sendUrl= sendUrl.replaceAll("https://","http://");
                }

                    /*Map<String,Object> headers = new HashMap<>();
                    headers.put("Content-Type","application/json");
                    headers.put("COMPETITION-KEY",data.getKey());*/
                //HttpUtil.post(sendUrl,headers,evaluateRESQStr);
                HttpRequest HttpRequest = HttpUtil.createPost(sendUrl);
                HttpRequest.body(evaluateRESQStr);
                HttpRequest.header("Content-Type","application/json");
                HttpRequest.header("COMPETITION-KEY",data.getKey());
                LogUtil.logColor("kdCompetitions_send",sendUrl+"###"+evaluateRESQStr+"###"+data.getKey(), ColorType.YELLOW.getCode(),true);
                try {
                    // 回调10秒超时并打印主服务响应，失败一眼可见；异常不再向外抛导致线程挂掉
                    HttpResponse callBackResp = HttpRequest.timeout(10000).execute();
                    LogUtil.logColor("kdCompetitions_response",sendUrl+"###"+callBackResp.getStatus()+"###"+callBackResp.body(), ColorType.GREEN.getCode(),true);
                }catch (Exception e)
                {
                    LogUtil.logColor("kdCompetitions_error",sendUrl+"###"+e.getMessage(), ColorType.RED.getCode(),true);
                }

            }
        };
        EvaluateData evaluateData = new EvaluateData();
        evaluateData.setKey(key);
        evaluateData.setRequest_id(req.getRequest_id());
        evaluateData.setFile_url(req.getFile_url());
        evaluateData.setData_path(currentPath+ fileDataLocalPath +dataName);
        evaluateData.setDown_path(currentPath+ fileDownLocalPath+fileName);
        task = new ParseTask(callBackInterface, evaluateData);
        cachedThreadPool.execute(task);
        return ResultData.successData(true);
    }
    @ApiOperation(value = "返回测评结果(模拟)", notes = "返回测评结果接口(模拟)")
    @RequestMapping(value = "/kd_competitions/callback.json", method = RequestMethod.POST)
    public SimResult<Boolean> kdCompetitions(@RequestBody String req, HttpServletRequest request, HttpServletResponse response) {
        //System.out.println(req);
        LogUtil.logColor("kdCompetitions_key",request.getHeader("COMPETITION-KEY"), ColorType.YELLOW.getCode(),true);
        LogUtil.logColor("kdCompetitions_end",req, ColorType.YELLOW.getCode(),true);
        return ResultData.successData(true);
    }

}

