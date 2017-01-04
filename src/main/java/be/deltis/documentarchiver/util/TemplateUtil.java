/*
 *  Copyright 2016-2017 DELTIS Engineering sprl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package be.deltis.documentarchiver.util;

import be.deltis.documentarchiver.exception.DocArchiverException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.StringWriter;

/**
 * Created by benoit on 30/12/16 - 15:27.
 */
public class TemplateUtil {

    private static TemplateUtil instance ;

    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    public static TemplateUtil getInstance(String templateDir) {
        if (instance == null) {
            instance = new TemplateUtil(templateDir);
        }
        return instance ;
    }

    private TemplateUtil(String templateDir) {
        File dir = new File(templateDir) ;
        try {
            cfg.setDirectoryForTemplateLoading(dir);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
        } catch (Exception e) {
            throw new DocArchiverException(String.format("Failed to init freemarker configuration. dir=%s", dir.getAbsolutePath()), e) ;
        }
    }

    public String process(Object dataModel, String  templateId) {
        try {
            StringWriter result = new StringWriter();
            Template template = cfg.getTemplate(templateId);
            template.process(dataModel, result);
            return result.toString();
        } catch (Exception e) {
            throw new DocArchiverException(String.format("Failed to process template %s", templateId), e) ;
        }
    }
}
