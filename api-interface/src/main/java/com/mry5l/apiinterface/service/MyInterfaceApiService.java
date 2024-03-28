package com.mry5l.apiinterface.service;

import com.mry5l.apiclientsdk.common.BaseResponse;
import com.mry5l.apiclientsdk.model.params.AiMessageParams;
import com.mry5l.apiclientsdk.model.params.HoroscopeParams;

/**
 * @author YJL
 * @version 1.0
 */
public interface MyInterfaceApiService {
    /**
     * 根据ip获取ip信息
     * @param ip
     * @return
     */
    String getIpDetailsInfo(String ip);

    /**
     * ChatGPT聊天
     * @param aiMessage
     * @return
     */
    String doChat(AiMessageParams aiMessage);

    /**
     * 获取随机土味情话
     * @return
     */
    String getTalkLove();

    /**
     * 获取随机风景图片
     * @return
     */
    String getRandomScenery();

    /**
     * 获取摸鱼日历
     * @return
     */
    String getMoYu();

    /**
     * 获取随机壁纸
     */
    String getRandomWallpaper();

    /**
     * 获取星座运势
     * @param horoscopeParams
     * @return
     */
    String getHoroscope(HoroscopeParams horoscopeParams);
}
