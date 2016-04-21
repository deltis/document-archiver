package be.deltis.documentarchiver.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"supplier", "name", "documentType", "originalFormat", "fileNameRegExp"})
public class DocumentModel {

	private String name ;
	private DocumentType documentType ;
	private String supplier ;
	private OriginalFormat originalFormat ;
	private String fileNameRegExp ;
		
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
