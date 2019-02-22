package samples;

import org.metafacture.biblio.pica.PicaDecoder;
import org.metafacture.formeta.FormetaEncoder;
import org.metafacture.formeta.formatter.FormatterStyle;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;

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
