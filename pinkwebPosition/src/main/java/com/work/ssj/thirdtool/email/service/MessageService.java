package com.work.ssj.thirdtool.email.service;

public interface MessageService {
    /**
     * 存在不可售原因审核时需要胡总审核
     */
    void sendBanSellReason(String materialNo);
}
