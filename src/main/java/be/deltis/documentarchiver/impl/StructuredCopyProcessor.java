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
package be.deltis.documentarchiver.impl;

import be.deltis.documentarchiver.DocumentModelDetector;
import be.deltis.documentarchiver.Processor;
import be.deltis.documentarchiver.context.Context;
import be.deltis.documentarchiver.exception.DocArchiverException;
import be.deltis.documentarchiver.model.Document;
import be.deltis.documentarchiver.model.DocumentModel;
import be.deltis.documentarchiver.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by benoit on 29/12/16 - 16:50.
 */
public class StructuredCopyProcessor implements Processor {

    private Path targetRootDir;
    private String templateIdDir ;
    private String templateIdFile ;

    @Autowired
    private DocumentModelDetector documentModelDetector ;
    @Autowired
    private TemplateUtil templateUtil;

    public StructuredCopyProcessor(Path targetRootDir, String templateIdDir, String templateIdFile) {
        this.targetRootDir = targetRootDir;
        this.templateIdDir = templateIdDir;
        this.templateIdFile = templateIdFile;
    }

    @Override
    public void processFile(Path filename, Context context) {
        List<DocumentModel> documentModels = documentModelDetector.searchModels(filename, context) ;
        if (documentModels == null || documentModels.isEmpty()) {
            throw new DocArchiverException("No document model found.");
        }
        if (documentModels.size() == 1) {
            processDocumentModel(documentModels.get(0), filename, context);
        } else {
            // TODO Implement document selector
            throw new DocArchiverException("More than one document model found, and selector not yet implemented.");
        }
    }

    private void processDocumentModel(DocumentModel documentModel, Path filename, Context context) {
        processDocument(new Document(documentModel, askForDate()), filename, context);
    }

    private LocalDate askForDate() {
        return LocalDate.now() ;
    }

    private void processDocument(Document document, Path filename, Context context) {
        String dir = templateUtil.process(document, templateIdDir);
        String file = templateUtil.process(document, templateIdFile);
        try {
            Path source = context.getDirectory().resolve(filename);
            Path targetDir = targetRootDir.resolve(dir) ;
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }
            Files.copy(source, targetDir.resolve(file));
        } catch (IOException ioe) {
            throw new DocArchiverException(String.format("Failed to copy file %s", filename), ioe);
        }

    }
}
