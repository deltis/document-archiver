/*
 * Copyright 2016 DELTIS Engineering sprl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.deltis.documentarchiver.util;

import be.deltis.documentarchiver.model.Document;
import be.deltis.documentarchiver.model.DocumentType;
import be.deltis.documentarchiver.model.OriginalFormat;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by benoit on 30/12/16 - 17:03.
 */
@Test
public class TemplateUtilTest {

    private TemplateUtil templateUtil = TemplateUtil.getInstance("src/test/resources/templates");

    @Test(dataProvider = "templates")
    public void process(String templateId, String expectedResult) {
        Document document = new Document();
        document.setDocumentType(DocumentType.INVOICE_PURCHASE);
        document.setOriginalFormat(OriginalFormat.ELECTRONIC);
        document.setName("securex-precompte");
        document.setSupplier("Securex");
        document.setDate(LocalDate.of(2016, Month.DECEMBER, 20));

        String actualResult = templateUtil.process(document, templateId);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "templates")
    public Object[][] templates() {
        return new Object[][]{
                new Object[]{ "dir.ftlh", "/tmp/document-archiver/INVOICE_PURCHASE/Securex/2016"},
                new Object[]{ "file.ftlh", "2016-12-20.pdf"}
        };
    }
}
