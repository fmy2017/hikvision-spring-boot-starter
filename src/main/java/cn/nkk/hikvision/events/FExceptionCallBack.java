package cn.nkk.hikvision.events;

import cn.nkk.hikvision.sdk.HCNetSDK;
import cn.nkk.hikvision.utils.HkUtils;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.nkk.hikvision.sdk.HCNetSDK.EXCEPTION_EXCHANGE;
import static cn.nkk.hikvision.sdk.HCNetSDK.EXCEPTION_PREVIEW;

//接收异常消息的回调函数
public class FExceptionCallBack implements HCNetSDK.FExceptionCallBack {

    private final static Logger log = LoggerFactory.getLogger(HkUtils.class);

    @Override
    public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
        log.info("设备异常：{}",dwType);
        switch(dwType)
        {
            case EXCEPTION_EXCHANGE: //用户交互时异常
                log.error("用户交互时异常:{}",lHandle);
                break;
            case EXCEPTION_PREVIEW:  //网络预览时异常
                log.error("网络预览时网络异常:{}",lHandle);
                break;
            case 0x8015: //预览时重连成功
                log.error("预览时重连成功:{}",lHandle);
                break;
            case 0x8017:
                log.error("用户交互恢复:{}",lHandle);
                break;
            default:
                System.out.println("异常");
                break;
        }
    }
}