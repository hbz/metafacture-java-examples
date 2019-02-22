package samples;

import org.metafacture.biblio.pica.PicaDecoder;
import org.metafacture.formatting.ObjectTemplate;
import org.metafacture.framework.objects.Triple;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;
import org.metafacture.metamorph.Metamorph;
import org.metafacture.triples.AbstractTripleSort.Compare;
import org.metafacture.triples.StreamToTriples;
import org.metafacture.triples.TripleCount;

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
