package com.person.hlwan.html2image.controller;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@RestController
public class ImageController {

    private Logger logger= LoggerFactory.getLogger(ImageController.class);

    private String rootPath="/data/server/images/";


    @GetMapping("/generate")
    public String generate(String html) throws Exception{
//        html=new String(html.getBytes("iso-8859-1"),"utf-8");
        logger.info(html);
        return "/image/load/"+output(html);
    }

    @PostMapping("/generate")
    public String generateFromBody(@RequestBody String html) throws Exception{
//        html=new String(html.getBytes("iso-8859-1"),"utf-8");
        return "/image/load/"+output(html);
    }

    @GetMapping("/load/{guid}")
    public void loadImage(@PathVariable String guid, HttpServletResponse response) throws Exception{
        File file=new File(rootPath+"/"+guid+".png");
        IOUtils.copy(new FileInputStream(file),response.getOutputStream());
        response.getOutputStream().flush();
    }

    private  String output(String html) throws Exception{
        String guid= UUID.randomUUID().toString();
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(rootPath+guid+".png");
        return guid;
    }

    public static void main(String[] args)  throws Exception{
        String guid=UUID.randomUUID().toString();
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

        //String htmlstr = HttpTool.get("https://baike.baidu.com/item/c%E8%AF%AD%E8%A8%80/105958?fr=aladdin",String.class,3000,false);
        String htmlstr="";
        imageGenerator.loadHtml(new String(htmlstr.getBytes("iso-8859-1"),"utf-8"));

        imageGenerator.saveAsImage("/Users/0001275/Documents/"+guid+".png");
    }
}
