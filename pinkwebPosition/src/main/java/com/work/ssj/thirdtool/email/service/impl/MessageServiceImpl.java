package com.work.ssj.thirdtool.email.service.impl;

import com.work.ssj.common.utils.spring.EnvironmentUtil;
import com.work.ssj.thirdtool.email.service.MessageService;
import com.work.ssj.thirdtool.qywechat.SendWxInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private SendWxInfo sendWxInfo;

    @Override
    public void sendBanSellReason(String materialNo) {

        String toUser = "U18029";
        if (!"prod".equals(EnvironmentUtil.thisSystemParam)) {
            toUser= "U20143";
        }
                String message = "报关物料-待审批通知" + "\n" + "系统：报关系统" + "\n" + "物料编码："+ materialNo + "\n"  +
                "存在不可售原因，需要您审批\n" +
                "请及时处理";
        sendWxInfo.sendMsg(toUser,message);
    }

}
