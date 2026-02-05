package com.yunke.admin.modular.system.file.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.Console;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @className FileUploadController
 * @description: 文件上传_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Api(tags = "通用附件接口")
@ApiSupport(author = "tianlei",order = 2)
@OpLogHeader("附件上传")
@RestController
@RequestMapping("/sys/file/")
@RequiredArgsConstructor
@Validated
public class FileUploadController extends BaseController {

    private final FileUploadService fileUploadService;

    /**
     * @description: 根据附件类型定义获取附件类型列表
     * <p></p>
     * @param
     * @param fileType
     * @return ResponseData<List<FileSmallTypeVO>>
     * @auth: tianlei
     * @date: 2026/1/26 13:38
     */
    @GetMapping(value = "smallTypeList")
    public ResponseData<List<FileSmallTypeVO>> smallTypeList(@NotNull(message = "附件类型不能为空，请检查参数fileType") String fileType){
        return new SuccessResponseData<>(fileUploadService.getSmallTypeList(fileType));
    }

    /**
     * @description: 通用附件上传接口
     * <p></p>
     * @param fileUploadParam
     * @return ResponseData<String>
     * @auth: tianlei
     * @date: 2026/1/26 13:41
     */
    @ApiOperation(value = "附件上传",position = 1)
    @OpLog(title = "附件上传",opType = OpLogAnnotionOpTypeEnum.UPLOAD)
    @PostMapping(value = "upload")
    public ResponseData<String> upload(@Validated FileUploadParam fileUploadParam){
        Console.log(fileUploadParam);
        return ResponseData.success(fileUploadService.upload(fileUploadParam));
    }

    /**
     * @description: 根据附件子类型获取业务附件
     * <p></p>
     * @param smallTypeCode
     * @param bigTypeCode
     * @param businessId
     * @return ResponseData<List<FileSmallTypeVO>>
     * @auth: tianlei
     * @date: 2026/1/26 15:41
     */
    @GetMapping(value = "smallFileList")
    public ResponseData<List<FileSmallTypeVO>> smallFileList(@NotEmpty(message = "附件子类型编码不能为空，请检查参数smallTypeCode") String smallTypeCode,
                                      @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode,
                                      @NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId){
        return ResponseData.success(fileUploadService.getFileListBySmall(smallTypeCode,bigTypeCode,businessId));
    }

    /**
     * @description: 根据附件子类型获取业务附件id
     * <p></p>
     * @param smallTypeCode
     * @param bigTypeCode
     * @param businessId
     * @return ResponseData<List<String>>
     * @auth: tianlei
     * @date: 2026/1/26 15:43
     */
    @ApiOperation(value = "根据附件子类型获取业务附件id",position = 1)
    @GetMapping(value = "smallFileIdList")
    public ResponseData<List<String>> smallFileIdList(@NotEmpty(message = "附件子类型编码不能为空，请检查参数smallTypeCode") String smallTypeCode,
                                                             @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode,
                                                             @NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId){
        return ResponseData.success(fileUploadService.getFileIdsBySmall(smallTypeCode,bigTypeCode,businessId));
    }


    /**
     * @description: 根据附件大类获取附件
     * <p></p>
     * @param bigTypeCode
     * @param businessId
     * @return ResponseData<Map<String,List<FileSmallTypeVO>>>
     * @auth: tianlei
     * @date: 2026/1/26 15:42
     */
    @GetMapping(value = "bigFileList")
    public ResponseData<Map<String,List<FileSmallTypeVO>>> bigFileList(@NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode,
                                                        @NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId){
        return ResponseData.success(fileUploadService.getFileListByBigType(bigTypeCode,businessId));
    }

    /**
     * @description: 根据附件大类获取附件id
     * <p></p>
     * @param bigTypeCode
     * @param businessId
     * @return ResponseData<Map<String,List<String>>>
     * @auth: tianlei
     * @date: 2026/1/26 15:45
     */
    @ApiOperation(value = "根据附件大类获取附件id",position = 2)
    @GetMapping(value = "bigFileIdList")
    public ResponseData<Map<String,List<String>>> bigFileIdList(@NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode,
                                                                       @NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId){
        return ResponseData.success(fileUploadService.getFileIdsByBigType(bigTypeCode,businessId));
    }

    /**
     * @description: 根据附件id删除附件
     * <p></p>
     * @param id 附件id
     * @return ResponseData
     * @auth: tianlei
     * @date: 2026/1/26 15:38
     */
    @ApiOperation(value = "根据附件id删除附件",position = 3)
    @OpLog(title = "根据附件id删除附件",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "附件id不能为空，请检查参数id") String id){
        return ResponseData.bool(fileUploadService.deleteById(id));
    }

    @OpLog(title = "根据附件子类型编码删除附件",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @GetMapping(value = "deleteSmall")
    public ResponseData deleteSmall(@NotEmpty(message = "附件子类型编码不能为空，请检查参数smallTypeCode") String smallTypeCode,
                                    @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode,
                                    @NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId){
        return ResponseData.bool(fileUploadService.deleteBySmallType(smallTypeCode,bigTypeCode,businessId));
    }

    @ApiOperation(value = "下载文件",position = 5)
    @OpLog(title = "下载文件",opType = OpLogAnnotionOpTypeEnum.DOWNLOAD)
    @GetMapping(value = "download/{id}")
    public void download(@NotEmpty(message = "附件id不能为空，请检查参数id") @PathVariable("id") String id){
        FileStore fileStore = fileUploadService.getFileStoreById(id);
        if(fileStore == null){
            throw new ServiceException("文件不存在");
        }
        File file = new File(fileUploadService.getUploadDir(),fileStore.getRelativePath());
        if(!file.exists()){
            throw new ServiceException("文件不存在");
        }
        try {
            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            HttpServletResponse response = this.getResponse();
            // 清空response
            response.reset();
            //加这个是为了解决跨域问题
            response.addHeader("Access-Control-Allow-Origin", "*");
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileStore.getOriginalFileName(), "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping(value = "previewImage/{id}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> previewImage(@NotEmpty(message = "附件id不能为空，请检查参数id") @PathVariable("id") String id){
        FileStore fileStore = fileUploadService.getFileStoreById(id);
        if(fileStore == null){
            throw new ServiceException("文件不存在");
        }
        File file = new File(fileUploadService.getUploadDir(),fileStore.getRelativePath());
        if(!file.exists()){
            throw new ServiceException("文件不存在");
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource ipInputStreamResource = new InputStreamResource(fileInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+URLEncoder.encode(fileStore.getOriginalFileName(),"UTF-8"));
            return new ResponseEntity<>(ipInputStreamResource,headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null);
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;


    @GetMapping(value = "previewPdf/{id}",produces = {MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity previewPdf(@NotEmpty(message = "附件id不能为空，请检查参数id") @PathVariable("id") String id){
        FileStore fileStore = fileUploadService.getFileStoreById(id);
        if(fileStore == null){
            throw new ServiceException("文件不存在");
        }
        File file = new File(fileUploadService.getUploadDir(),fileStore.getRelativePath());
        if(!file.exists()){
            throw new ServiceException("文件不存在");
        }
        if(!file.getName().toLowerCase().endsWith(".pdf")){
            throw new ServiceException("要打开的文件不是pdf类型");
        }
        Path path = Paths.get(fileStore.getAbsolutePath());
        Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    /**
     * @description: 预览附件
     * <p></p>
     * @param id
     * @return void
     * @auth: tianlei
     * @date: 2026/1/26 15:48
     */
    @ApiOperation(value = "预览附件",position = 4)
    @GetMapping(value = "previewFile/{id}")
    public void previewFile(@NotEmpty(message = "附件id不能为空，请检查参数id") @PathVariable("id") String id) throws IOException {
        FileStore fileStore = fileUploadService.getFileStoreById(id);
        if(fileStore == null){
            throw new ServiceException("文件不存在");
        }
        File file = new File(fileUploadService.getUploadDir(),fileStore.getRelativePath());
        if(!file.exists()){
            throw new ServiceException("文件不存在");
        }
        HttpServletResponse response = this.getResponse();
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] bs = new byte[1024];
        int len = 0;
        response.reset();
        URL u = new URL("file:///" + file.getAbsolutePath());
        String contentType = u.openConnection().getContentType();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileStore.getOriginalFileName(),"UTF-8"));
        OutputStream out = response.getOutputStream();
        while ((len = br.read(bs)) > 0) {
            out.write(bs, 0, len);
        }
        out.flush();
        out.close();
        br.close();
    }



}
