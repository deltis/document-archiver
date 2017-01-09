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
import be.deltis.documentarchiver.context.Source;
import be.deltis.documentarchiver.helper.FileHelper;
import be.deltis.documentarchiver.model.Document;
import be.deltis.documentarchiver.model.DocumentModel;
import be.deltis.documentarchiver.model.DocumentType;
import be.deltis.documentarchiver.model.OriginalFormat;
import be.deltis.documentarchiver.util.TemplateUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static be.deltis.documentarchiver.TestConstants.DIR_TEMPLATE_ID;
import static be.deltis.documentarchiver.TestConstants.FILE_TEMPLATE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

/**
 * Created by benoit on 9/01/17 - 15:03.
 */
@Test
public class StructuredCopyProcessorTest {

    private Path rootDir = FileHelper.createTempDirectory(this);

    @InjectMocks
    private Processor processor = new StructuredCopyProcessor(rootDir, DIR_TEMPLATE_ID, FILE_TEMPLATE_ID);

    @Mock
    private DocumentModelDetector documentModelDetector ;

    @Mock
    private TemplateUtil templateUtil;

    @BeforeMethod
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void copy() throws IOException {

        Path sourceDirectory = FileHelper.createTempDirectory(this);
        Context context = new Context(Source.SCANNER, sourceDirectory);

        Path file = FileHelper.createTempFile(sourceDirectory, this);

        when(documentModelDetector.searchModels(any(Path.class), any(Context.class))).thenReturn(Collections.singletonList(document()));
        when(templateUtil.process(any(), eq("dir.ftlh"))).thenReturn("INVOICE_PURCHASE/Securex/2016");
        when(templateUtil.process(any(), eq("file.ftlh"))).thenReturn("2016-12-20.pdf");

        processor.processFile(file.getFileName(), context);

        assertTrue(Files.exists(context.getDirectory().resolve(file.getFileName())));
        assertTrue(Files.exists(rootDir.resolve("INVOICE_PURCHASE/Securex/2016/2016-12-20.pdf")));

        // Clean-up
        FileHelper.deleteDir(sourceDirectory, this);
        FileHelper.deleteDir(rootDir, this);
    }

    private Document document() {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setDocumentType(DocumentType.INVOICE_PURCHASE);
        documentModel.setOriginalFormat(OriginalFormat.ELECTRONIC);
        documentModel.setName("securex-precompte");
        documentModel.setSupplier("Securex");

        return new Document(documentModel, LocalDate.of(2016, Month.DECEMBER, 20));
    }

}
