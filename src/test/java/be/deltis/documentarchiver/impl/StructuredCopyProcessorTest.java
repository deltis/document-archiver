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
import be.deltis.documentarchiver.exception.DocArchiverException;
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

    public static final String DIR = "INVOICE_PURCHASE/Securex/2016";

    public static final String FILE = "2016-12-20.pdf";

    public static final String FULL_FILE_PATH = "INVOICE_PURCHASE/Securex/2016/2016-12-20.pdf";

    private Path rootDir = FileHelper.createTempDirectory(this);

    @InjectMocks
    private Processor processor = new StructuredCopyProcessor(rootDir, DIR_TEMPLATE_ID, FILE_TEMPLATE_ID);

    @Mock
    private DocumentModelDetector documentModelDetector;

    @Mock
    private TemplateUtil templateUtil;

    public StructuredCopyProcessorTest() {
    }

    @BeforeMethod
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void copy() throws IOException {
        when(documentModelDetector.searchModels(any(Path.class), any(Context.class))).thenReturn(Collections.singletonList(document()));
        when(templateUtil.process(any(), eq(DIR_TEMPLATE_ID))).thenReturn(DIR);
        when(templateUtil.process(any(), eq(FILE_TEMPLATE_ID))).thenReturn(FILE);

        Path sourceDirectory = FileHelper.createTempDirectory(this);
        Context context = new Context(Source.SCANNER, sourceDirectory);
        Path file = FileHelper.createTempFile(sourceDirectory, this);

        processor.processFile(file.getFileName(), context);

        assertTrue(Files.exists(context.getDirectory().resolve(file.getFileName())));
        assertTrue(Files.exists(rootDir.resolve(FULL_FILE_PATH)));

        // Clean-up
        FileHelper.deleteDir(sourceDirectory, this);
        FileHelper.deleteDir(rootDir, this);
    }

    @Test(expectedExceptions = DocArchiverException.class)
    public void copyNoModel() throws IOException {

        Path sourceDirectory = FileHelper.createTempDirectory(this);
        Context context = new Context(Source.SCANNER, sourceDirectory);

        Path file = FileHelper.createTempFile(sourceDirectory, this);

        when(templateUtil.process(any(), eq(DIR_TEMPLATE_ID))).thenReturn(DIR);
        when(templateUtil.process(any(), eq(FILE_TEMPLATE_ID))).thenReturn(FILE);

        processor.processFile(file.getFileName(), context);

        assertTrue(Files.exists(context.getDirectory().resolve(file.getFileName())));
        assertTrue(Files.exists(rootDir.resolve(FULL_FILE_PATH)));

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
