@echo off
setlocal enabledelayedexpansion
rem ============================================================
rem  validateTools watchdog: auto-restart + hang detection
rem  Usage : run this in C:\kd\work\Validate2026 (double-click or cmd)
rem  Stop  : close this window, then run:  taskkill /F /IM java.exe
rem  NOTE  : taskkill below kills ALL java.exe on this machine.
rem          This cloud PC runs only this one java service, so it is safe.
rem          If you later run other java services here, remove the /IM switch.
rem ============================================================
cd /d C:\kd\work\Validate2026

rem health endpoint of the service (http, because --server.ssl.enabled=false)
set HEALTH_URL=http://127.0.0.1:8089/api/health
rem startup params must stay identical to the manual launch command
set JAVA_OPTS=--mainServiceIp=47.111.130.18:61080 --main.ssl.enabled=false --server.ssl.enabled=false --encode=true

:loop
echo [%date% %time%] === starting validate service ===
start "validateTools" /min jre\bin\java -jar validateTools-0.0.1-SNAPSHOT.jar %JAVA_OPTS%
set FAIL=0

:watch
ping -n 31 127.0.0.1 >nul

rem ---- 1) process dead -> restart in 10s ----
tasklist /FI "IMAGENAME eq java.exe" /NH 2>nul | findstr /I "java.exe" >nul
if errorlevel 1 (
    echo [%date% %time%] java process dead, restarting in 10s ...
    ping -n 11 127.0.0.1 >nul
    goto loop
)

rem ---- 2) process alive but 2 consecutive health checks failed -> treat as hung, force kill + restart ----
curl.exe -s -m 10 %HEALTH_URL% 2>nul | findstr /I "ok" >nul
if errorlevel 1 (
    set /a FAIL+=1
    echo [%date% %time%] health check failed !FAIL!/2
    if !FAIL! GEQ 2 (
        echo [%date% %time%] service hung, killing java ...
        taskkill /F /IM java.exe >nul
        ping -n 6 127.0.0.1 >nul
        goto loop
    )
) else (
    set FAIL=0
)
goto watch
