package com.test.demo.test.designMode.strategyPattern.service;


import java.io.File;

/**
 * 文件读取，策略接口
 */
public interface FileDisposeService {

    String readFile(File targetFile) throws Exception;
}
