package be.deltis.documentarchiver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import be.deltis.documentarchiver.model.DocumentModel;
import be.deltis.documentarchiver.model.DocumentType;
import be.deltis.documentarchiver.model.OriginalFormat;

public class Serializer {

	@Test
	public void marshal() throws Exception {
		DocumentModel dm = new DocumentModel() ;
		dm.setDocumentType(DocumentType.INVOICE_PURCHASE);
		dm.setOriginalFormat(OriginalFormat.ELECTRONIC);
		dm.setName("securex-precompte");
		dm.setSupplier("Securex");
		marshal(dm);
	}
	
	private void marshal(DocumentModel documentModel) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(DocumentModel.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(documentModel, System.out);
	}
}
