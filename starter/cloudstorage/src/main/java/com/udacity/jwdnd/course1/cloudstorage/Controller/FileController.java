package com.udacity.jwdnd.course1.cloudstorage.Controller;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class FileController {
    private final UserService userService;
    private final FileService fileService;
    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }
    @GetMapping("/downlod")
    public @ResponseBody byte[]
    getFileContent(@Param(value = "filename") String filename, Model model,
                   RedirectAttributes redirectAttributes, HttpServletResponse response, Authentication authentication)
    {
        redirectAttributes.addFlashAttribute("activeTab","files");
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        File file = fileService.getFileByName(filename, userId);
        if(file != null){
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + file.getFileName()
            );
            try{
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(file.getFileData());
                servletOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file.getFileData();
        }
        return null;
    }
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes){
        if(fileUpload.isEmpty()){
            redirectAttributes.addFlashAttribute("fileError",true);
            redirectAttributes.addFlashAttribute("file Error Message","No such file uploaded");
            return "redirect:/home";
        }
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        try{
            File file = this.fileService.createFile(fileUpload,userId);
            if(this.fileService.getFileByName(file.getFileName(), userId)  != null){
                redirectAttributes.addFlashAttribute("fileError", true);
                redirectAttributes.addFlashAttribute("fileErrorMessage","File with this name already exist");
            }
            else{
                this.fileService.addOrEditFile(file);
                redirectAttributes.addFlashAttribute("fileSucess",true);
                redirectAttributes.addFlashAttribute("message","File that name already exist");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("fileError",true);
            redirectAttributes.addFlashAttribute("fileErrorMessage",
                    "Error: " + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("activeTab", "files");
        return "redirect:/home";
    }
    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, RedirectAttributes redirectAttributes){
        this.fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("activeTab", "files");
        return "redirect:/home";
    }
    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<InputStreamSource> getFile(@PathVariable Integer fileId)  {
        File file = this.fileService.getFileById(fileId);
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        MediaType mt = MediaType.parseMediaType(file.getContentType());
        return ResponseEntity.ok().contentType(mt).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"").body(resource);
    }
}