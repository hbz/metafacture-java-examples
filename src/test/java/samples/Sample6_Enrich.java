package samples;

import org.culturegraph.mf.formeta.formatter.FormatterStyle;
import org.culturegraph.mf.morph.Metamorph;
import org.culturegraph.mf.stream.converter.FormetaDecoder;
import org.culturegraph.mf.stream.converter.FormetaEncoder;
import org.culturegraph.mf.stream.converter.LineReader;
import org.culturegraph.mf.stream.converter.StreamToTriples;
import org.culturegraph.mf.stream.converter.bib.PicaDecoder;
import org.culturegraph.mf.stream.pipe.CloseSupressor;
import org.culturegraph.mf.stream.pipe.sort.TripleCollect;
import org.culturegraph.mf.stream.pipe.sort.TripleSort;
import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.culturegraph.mf.stream.source.FileOpener;
import org.culturegraph.mf.types.Triple;
import org.culturegraph.mf.util.FileCompression;

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
		ObjectWriter<String> writer = new ObjectWriter<>(
				"src/test/resources/sample6/sample6-out.txt");

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
