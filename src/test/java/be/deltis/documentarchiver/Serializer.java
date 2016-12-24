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
package be.deltis.documentarchiver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.testng.annotations.Test;

import be.deltis.documentarchiver.model.DocumentModel;
import be.deltis.documentarchiver.model.DocumentType;
import be.deltis.documentarchiver.model.OriginalFormat;

@SuppressWarnings("restriction")
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
