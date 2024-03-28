package com.mry5l.apiinterface.controller;

import com.mry5l.apiclientsdk.model.params.AiMessageParams;
import com.mry5l.apiclientsdk.model.params.HoroscopeParams;
import com.mry5l.apiclientsdk.model.params.IpInfoParams;
import com.mry5l.apiinterface.service.MyInterfaceApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 名称 API
 *
 * @author YJL
 * @version 1.0
 */
@RestController()
@RequestMapping(value = "/mry5l", produces = "application/json;charset=utf-8")
public class ApiController {
    @Resource
    MyInterfaceApiService myInterfaceApiService;

    @PostMapping("/getIpInfo")
    public String getUserNameByPost(@RequestBody IpInfoParams ipRequest) {
        return myInterfaceApiService.getIpDetailsInfo(ipRequest.getIp());
    }

    @PostMapping("/doChatMessage")
    public String doChatMessage(@RequestBody AiMessageParams aiMessage) {
        return myInterfaceApiService.doChat(aiMessage);
    }

    @PostMapping("/talkLove")
    public String talkLove() {
        return myInterfaceApiService.getTalkLove();
    }

    @PostMapping("/RandomScenery")
    public String RandomScenery() {
        return myInterfaceApiService.getRandomScenery();
    }

    @PostMapping("/getMoYu")
    public String getMoYu() {
        return myInterfaceApiService.getMoYu();
    }

    @PostMapping("/getRandomWallpaper")
    public String getRandomWallpaper() {
        return myInterfaceApiService.getRandomWallpaper();
    }

    @PostMapping("/getHoroscope")
    public String getHoroscope(@RequestBody HoroscopeParams horoscopeParams) {
        return myInterfaceApiService.getHoroscope(horoscopeParams);
    }
}
