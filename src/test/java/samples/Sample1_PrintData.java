package samples;

import org.culturegraph.mf.formeta.formatter.FormatterStyle;
import org.culturegraph.mf.stream.converter.FormetaEncoder;
import org.culturegraph.mf.stream.converter.LineReader;
import org.culturegraph.mf.stream.converter.bib.PicaDecoder;
import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.culturegraph.mf.stream.source.FileOpener;
import org.culturegraph.mf.util.FileCompression;

public class Sample1_PrintData {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		PicaDecoder decoder = new PicaDecoder();
		FormetaEncoder encoder = new FormetaEncoder();
		encoder.setStyle(FormatterStyle.MULTILINE);
		ObjectWriter<String> writer = new ObjectWriter<>(
				"src/test/resources/sample1/sample1-out.txt");

		opener.setReceiver(reader)//
				.setReceiver(decoder)//
				.setReceiver(encoder)//
				.setReceiver(writer);

		opener.process("src/test/resources/sample1/bib-data.pica.gz");
		opener.closeStream();
	}
}
