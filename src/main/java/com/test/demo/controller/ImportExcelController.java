package com.test.demo.controller;

import com.test.demo.common.util.ResponseVoUtil;
import com.test.demo.common.vo.ResponseVo;
import com.test.demo.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/impotr")
public class ImportExcelController {

    @Autowired
    private MaterielService materielService;

    /**
     * 导入excel
     * @param file
     * @return
     */
    @PostMapping("/material")
    public ResponseVo impotr(@RequestParam("file") MultipartFile file) {
        String msg = null;
        try {
            msg = materielService.importExcelInfo(file);
        } catch (Exception e) {
            msg = "异常"+e.getMessage();
        }
        return msg == null ? ResponseVoUtil.successMsg("导入成功") : ResponseVoUtil.failResult("导入失败");
    }
}
