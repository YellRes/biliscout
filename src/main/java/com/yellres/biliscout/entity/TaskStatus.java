package com.yellres.biliscout.entity;

public enum TaskStatus {
    QUEUED,           // 排队
    REWRITING_QUERY,  // 改写关键词
    SEARCHING,        // 搜索
    COLLECTING,       // 采集
    JUDGING_ADS,      // 判恰饭
    EXTRACTING,       // 提观点
    SUMMARIZING,      // 汇总
    DONE,             // 完成
    FAILED            // 失败
}