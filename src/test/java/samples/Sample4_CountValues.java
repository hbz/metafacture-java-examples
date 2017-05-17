package samples;

import org.culturegraph.mf.biblio.pica.PicaDecoder;
import org.culturegraph.mf.formatting.ObjectTemplate;
import org.culturegraph.mf.framework.objects.Triple;
import org.culturegraph.mf.io.FileCompression;
import org.culturegraph.mf.io.FileOpener;
import org.culturegraph.mf.io.LineReader;
import org.culturegraph.mf.io.ObjectWriter;
import org.culturegraph.mf.metamorph.Metamorph;
import org.culturegraph.mf.triples.AbstractTripleSort.Compare;
import org.culturegraph.mf.triples.StreamToTriples;
import org.culturegraph.mf.triples.TripleCount;

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
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample4/sample4-out.txt");

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
