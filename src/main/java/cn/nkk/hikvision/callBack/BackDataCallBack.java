package cn.nkk.hikvision.callBack;

import cn.nkk.hikvision.sdk.HCNetSDK;
import com.sun.jna.ptr.ByteByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

/**
 *
 * <p>回放预览回调</p>
 *
 */

@Component
public class BackDataCallBack implements HCNetSDK.FPlayDataCallBack{

    private final static Logger log = LoggerFactory.getLogger(BackDataCallBack.class);

    private PipedOutputStream outputStream;

    public BackDataCallBack(){
    }

    public void setOutputStream(PipedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void invoke(int lPlayHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, int dwUser) {
        if(dwBufSize > 0) {
            ByteBuffer buffers = pBuffer.getPointer().getByteBuffer(0, dwBufSize);
            byte[] bytes = new byte[dwBufSize];
            buffers.rewind();
            buffers.get(bytes);
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                log.error("回放预览回调：{}",e.getMessage());
            }
        }
    }
}