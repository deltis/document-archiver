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

package be.deltis.documentarchiver.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"supplier", "name", "documentType", "originalFormat", "fileNameRegExp"})
public class DocumentModel {

    private String name;
    private DocumentType documentType;
    private String supplier;
    private OriginalFormat originalFormat;
    private String fileNameRegExp;

    public DocumentModel() {
    }

    /**
     * Copy constructor.
     *
     * @param other the DocumentModel to copy values from
     */
    DocumentModel(DocumentModel other) {
        this.name = other.name;
        this.documentType = other.documentType;
        this.supplier = other.supplier;
        this.originalFormat = other.originalFormat;
        this.fileNameRegExp = other.fileNameRegExp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public OriginalFormat getOriginalFormat() {
        return originalFormat;
    }

    public void setOriginalFormat(OriginalFormat originalFormat) {
        this.originalFormat = originalFormat;
    }

    public String getFileNameRegExp() {
        return fileNameRegExp;
    }

    public void setFileNameRegExp(String fileNameRegExp) {
        this.fileNameRegExp = fileNameRegExp;
    }

}
