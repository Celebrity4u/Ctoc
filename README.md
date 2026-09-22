# validateTools2026 — CTOC 竞赛验证服务

CTOC 竞赛（2026 赛段）选手提交文件的自动验证服务：接收主服务下发的验证任务 → 下载选手提交文件 → 调用外部校验程序 → 回调主服务上报结果。

## 技术栈

- Spring Boot 2.0.8 + JDK 8
- Maven 构建，默认 HTTPS 端口 8089

## 验证流程

1. 主服务下发任务（携带 `topic_type`：1=甲题，2=乙题）
2. 下载提交文件到 `files/down/`，解压到 `files/data/`
3. 调用外部校验程序：
   - 甲题：`verify_score.exe`（UTF-8 输出）
   - 乙题：`AtCTOC14Main.exe`（GBK 输出，依赖 Qt DLL + AstroData 星历数据，须平铺在工作目录）
4. 回调主服务 `/api/kd_competitions/callback.json`（带 `COMPETITION-KEY` header）

## 构建

```bash
mvn package
```

产物：`target/validateTools-0.0.1-SNAPSHOT.jar`

## 运行

```bash
java -jar validateTools-0.0.1-SNAPSHOT.jar \
  --mainServiceIp=<主服务IP:端口> \
  --main.ssl.enabled=false \
  --server.ssl.enabled=false \
  --encode=true
```

> 注意：`--mainServiceIp` 参数**不能带 `http://` 前缀**。

生产环境配合 `guard.bat` 看护脚本运行（自动重启 + 健康检查假死检测）。

## 目录结构

```
src/main/java/kd/validate/api/
├── control/     # 控制器（ValidateController、HealthController）
├── data/        # 数据模型与回调协议
├── utils/       # 工具类（CmdExecutor、DownLoadUtils、HttpUtils 等）
└── ValidateApplication.java
```
