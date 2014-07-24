package samples;

import org.culturegraph.mf.formeta.formatter.FormatterStyle;
import org.culturegraph.mf.morph.Metamorph;
import org.culturegraph.mf.stream.converter.FormetaEncoder;
import org.culturegraph.mf.stream.converter.LineReader;
import org.culturegraph.mf.stream.converter.StreamToTriples;
import org.culturegraph.mf.stream.converter.bib.PicaDecoder;
import org.culturegraph.mf.stream.pipe.CloseSupressor;
import org.culturegraph.mf.stream.pipe.sort.AbstractTripleSort.Compare;
import org.culturegraph.mf.stream.pipe.sort.TripleCollect;
import org.culturegraph.mf.stream.pipe.sort.TripleCount;
import org.culturegraph.mf.stream.pipe.sort.TripleSort;
import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.culturegraph.mf.stream.source.FileOpener;
import org.culturegraph.mf.types.Triple;
import org.culturegraph.mf.util.FileCompression;

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

		CloseSupressor<Triple> wait = new CloseSupressor<Triple>(2);
		TripleSort sort = new TripleSort();
		sort.setBy(Compare.SUBJECT);
		FormetaEncoder encode = new FormetaEncoder();
		encode.setStyle(FormatterStyle.VERBOSE);
		ObjectWriter<String> writer = new ObjectWriter<>(
				"src/test/resources/sample7/sample7-out.txt");

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
