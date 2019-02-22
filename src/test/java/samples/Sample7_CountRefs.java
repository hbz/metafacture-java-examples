package samples;

import org.metafacture.biblio.pica.PicaDecoder;
import org.metafacture.flowcontrol.CloseSuppressor;
import org.metafacture.formeta.FormetaEncoder;
import org.metafacture.formeta.formatter.FormatterStyle;
import org.metafacture.framework.objects.Triple;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;
import org.metafacture.metamorph.Metamorph;
import org.metafacture.triples.AbstractTripleSort.Compare;
import org.metafacture.triples.StreamToTriples;
import org.metafacture.triples.TripleCollect;
import org.metafacture.triples.TripleCount;
import org.metafacture.triples.TripleSort;

public class Sample7_CountRefs {
	public static void main(String[] args) {

		String gndLocation = "src/test/resources/sample7/authority-persons.pica.gz";
		String bibLocation = "src/test/resources/sample7/bib-data.pica.gz";
		String gndMorph = "src/test/resources/sample7/summarize-authority-persons.xml";
		String bibMorph = "src/test/resources/sample7/morph-bib.xml";

		FileOpener gndOpener = new FileOpener();
		gndOpener.setCompression(FileCompression.GZIP);

		StreamToTriples flow1 = gndOpener//
				.setReceiver(new LineReader())//
				.setReceiver(new PicaDecoder())//
				.setReceiver(new Metamorph(gndMorph))//
				.setReceiver(new StreamToTriples());

		FileOpener bibOpener = new FileOpener();
		gndOpener.setCompression(FileCompression.GZIP);
		StreamToTriples streamToTriples = new StreamToTriples();
		streamToTriples.setRedirect(true);
		TripleCount count = new TripleCount();
		count.setCountBy(Compare.SUBJECT);

		TripleCount flow2 = bibOpener//
				.setReceiver(new LineReader())//
				.setReceiver(new PicaDecoder())//
				.setReceiver(new Metamorph(bibMorph))//
				.setReceiver(streamToTriples)//
				.setReceiver(count);

		CloseSuppressor<Triple> wait = new CloseSuppressor<Triple>(2);
		TripleSort sort = new TripleSort();
		sort.setBy(Compare.SUBJECT);
		FormetaEncoder encode = new FormetaEncoder();
		encode.setStyle(FormatterStyle.VERBOSE);
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample7/sample7-out.txt");

		flow1.setReceiver(wait)//
				.setReceiver(sort)//
				.setReceiver(new TripleCollect())//
				.setReceiver(encode)//
				.setReceiver(writer);

		flow2.setReceiver(wait)//
				.setReceiver(sort)//
				.setReceiver(new TripleCollect())//
				.setReceiver(encode)//
				.setReceiver(writer);

		gndOpener.process(gndLocation);
		bibOpener.process(bibLocation);

		gndOpener.closeStream();
		bibOpener.closeStream();
	}
}
