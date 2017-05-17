package samples;

import org.culturegraph.mf.biblio.pica.PicaDecoder;
import org.culturegraph.mf.formeta.FormetaEncoder;
import org.culturegraph.mf.formeta.formatter.FormatterStyle;
import org.culturegraph.mf.io.FileCompression;
import org.culturegraph.mf.io.FileOpener;
import org.culturegraph.mf.io.LineReader;
import org.culturegraph.mf.io.ObjectWriter;

public class Sample1_PrintData {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		PicaDecoder decoder = new PicaDecoder();
		FormetaEncoder encoder = new FormetaEncoder();
		encoder.setStyle(FormatterStyle.MULTILINE);
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample1/sample1-out.txt");

		opener.setReceiver(reader)//
				.setReceiver(decoder)//
				.setReceiver(encoder)//
				.setReceiver(writer);

		opener.process("src/test/resources/sample1/bib-data.pica.gz");
		opener.closeStream();
	}
}
