package ru.urvanov.virtualpets.server.controller.site;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import ru.urvanov.virtualpets.fileinfo.FileInfo;
import ru.urvanov.virtualpets.fileinfo.FileListGenerator;

@Controller
@RequestMapping("site")
public class ClientUpdateController implements ServletContextAware {

    private static final Logger logger = LoggerFactory
            .getLogger(ClientUpdateController.class);
    private ServletContext servletContext;

    @ResponseBody
    @RequestMapping(value = "/update-info", method = RequestMethod.GET)
    public String updateInfo(ModelMap model, HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        try {
            logger.info("updater-update-info started.");
            logger.info("servletContext={}", servletContext);
            URL realPathUrl = servletContext.getResource("/resources/update");
            logger.info("realPathUrl={}", realPathUrl);
            Path realPath = Paths.get(realPathUrl.toURI());
            logger.info("realPath={}", realPath);
            Map<String, FileInfo> map = FileListGenerator
                    .generateFileList(realPath);
            for (Map.Entry<String, FileInfo> entry : map.entrySet()) {
                String strPath = entry.getKey();
                String str1 = strPath.replaceAll(File.pathSeparator, "/");
                result.append(str1);
                result.append("\n");
                result.append(entry.getValue().modified.toMillis());
                result.append("\n");
                result.append(entry.getValue().size);
                result.append("\n");
            }
        } catch (IOException | URISyntaxException ex) {
            logger.error("update-info failed.", ex);
            return "update-info failed.";
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/updater-update-info", method = RequestMethod.GET)
    public String updaterUpdateInfo(ModelMap model, HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        try {
            logger.info("updater-update-info started.");
            logger.info("servletContext={}", servletContext);
            URL realPathUrl = servletContext.getResource("/resources/updater-update");
            logger.info("realPathUrl={}", realPathUrl);
            Path realPath = Paths.get(realPathUrl.toURI());
            logger.info("realPath={}", realPath);
            Map<String, FileInfo> map = FileListGenerator
                    .generateFileList(realPath);
            for (Map.Entry<String, FileInfo> entry : map.entrySet()) {
                String strPath = entry.getKey();
                String str1 = strPath.replaceAll(File.pathSeparator, "/");
                result.append(str1);
                result.append("\n");
                result.append(entry.getValue().modified.toMillis());
                result.append("\n");
                result.append(entry.getValue().size);
                result.append("\n");
            }
        } catch (IOException | URISyntaxException ex) {
            logger.error("updater-update-info failed.", ex);
            return "updater-update-info failed.";
        }
        return result.toString();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        logger.info("servletContext="+servletContext);
        this.servletContext = servletContext;
    }

}
