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

import be.deltis.documentarchiver.model.Document;
import be.deltis.documentarchiver.model.DocumentModel;
import be.deltis.documentarchiver.model.DocumentType;
import be.deltis.documentarchiver.model.OriginalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;

import static be.deltis.documentarchiver.TestConstants.DIR_TEMPLATE_ID;
import static be.deltis.documentarchiver.TestConstants.FILE_TEMPLATE_ID;

/**
 * Created by benoit on 30/12/16 - 17:03.
 */
@Test
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class TemplateUtilTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TemplateUtil templateUtil;

    public TemplateUtilTest() {
    }

    @Test(dataProvider = "templates")
    public void process(String templateId, String expectedResult) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setDocumentType(DocumentType.INVOICE_PURCHASE);
        documentModel.setOriginalFormat(OriginalFormat.ELECTRONIC);
        documentModel.setName("securex-precompte");
        documentModel.setSupplier("Securex");

        Document document = new Document(documentModel, LocalDate.of(2016, Month.DECEMBER, 20));

        String actualResult = templateUtil.process(document, templateId);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "templates")
    public Object[][] templates() {
        return new Object[][]{
                new Object[]{DIR_TEMPLATE_ID, "/tmp/document-archiver/INVOICE_PURCHASE/Securex/2016"},
                new Object[]{FILE_TEMPLATE_ID, "2016-12-20.pdf"}
        };
    }
}
