package samples;

import org.culturegraph.mf.morph.Metamorph;
import org.culturegraph.mf.stream.converter.LineReader;
import org.culturegraph.mf.stream.converter.ObjectTemplate;
import org.culturegraph.mf.stream.converter.StreamToTriples;
import org.culturegraph.mf.stream.converter.bib.PicaDecoder;
import org.culturegraph.mf.stream.pipe.sort.AbstractTripleSort.Compare;
import org.culturegraph.mf.stream.pipe.sort.TripleCount;
import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.culturegraph.mf.stream.source.FileOpener;
import org.culturegraph.mf.types.Triple;
import org.culturegraph.mf.util.FileCompression;

public class Sample4_CountValues {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		PicaDecoder decoder = new PicaDecoder();
		Metamorph morph = new Metamorph("src/test/resources/sample4/count.xml");
		StreamToTriples triples = new StreamToTriples();
		TripleCount count = new TripleCount();
		count.setCountBy(Compare.OBJECT);
		ObjectTemplate<Triple> template = new ObjectTemplate<>("${o} | ${s}");
		ObjectWriter<String> writer = new ObjectWriter<>(
				"src/test/resources/sample4/sample4-out.txt");

		opener.setReceiver(reader)//
				.setReceiver(decoder)//
				.setReceiver(morph)//
				.setReceiver(triples)//
				.setReceiver(count)//
				.setReceiver(template)//
				.setReceiver(writer);

		opener.process("src/test/resources/sample4/bib-data.pica.gz");
		opener.closeStream();
	}
}
