package samples;

import org.metafacture.biblio.pica.PicaDecoder;
import org.metafacture.flowcontrol.CloseSuppressor;
import org.metafacture.formeta.FormetaDecoder;
import org.metafacture.formeta.FormetaEncoder;
import org.metafacture.formeta.formatter.FormatterStyle;
import org.metafacture.framework.objects.Triple;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;
import org.metafacture.metamorph.Metamorph;
import org.metafacture.triples.StreamToTriples;
import org.metafacture.triples.TripleCollect;
import org.metafacture.triples.TripleSort;

public class Sample6_Enrich {
	public static void main(String[] args) {

		String gndLocation = "src/test/resources/sample6/authority-persons.pica.gz";
		String wikiLocation = "src/test/resources/sample6/wiki-persons.foma.gz";
		String wikiMorph = "src/test/resources/sample6/enrich-wiki.xml";

		FileOpener gndOpener = new FileOpener();
		gndOpener.setCompression(FileCompression.GZIP);

		StreamToTriples flow1 = gndOpener//
				.setReceiver(new LineReader())//
				.setReceiver(new PicaDecoder())//
				.setReceiver(new StreamToTriples());

		FileOpener wikiOpener = new FileOpener();
		gndOpener.setCompression(FileCompression.GZIP);
		StreamToTriples streamToTriples = new StreamToTriples();
		streamToTriples.setRedirect(true);

		StreamToTriples flow2 = wikiOpener//
				.setReceiver(new LineReader())//
				.setReceiver(new FormetaDecoder())//
				.setReceiver(new Metamorph(wikiMorph))//
				.setReceiver(streamToTriples);

		CloseSuppressor<Triple> wait = new CloseSuppressor<Triple>(2);
		FormetaEncoder encode = new FormetaEncoder();
		encode.setStyle(FormatterStyle.MULTILINE);
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample6/sample6-out.txt");

		flow1.setReceiver(wait)//
				.setReceiver(new TripleSort())//
				.setReceiver(new TripleCollect())//
				.setReceiver(encode)//
				.setReceiver(writer);

		flow2.setReceiver(wait)//
				.setReceiver(new TripleSort())//
				.setReceiver(new TripleCollect())//
				.setReceiver(encode)//
				.setReceiver(writer);

		gndOpener.process(gndLocation);
		wikiOpener.process(wikiLocation);

		gndOpener.closeStream();
		wikiOpener.closeStream();
	}
}
