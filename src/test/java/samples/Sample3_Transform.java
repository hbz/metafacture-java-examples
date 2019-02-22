package samples;

import org.metafacture.biblio.pica.PicaDecoder;
import org.metafacture.formeta.FormetaEncoder;
import org.metafacture.formeta.formatter.FormatterStyle;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;
import org.metafacture.metamorph.Metamorph;

public class Sample3_Transform {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		PicaDecoder decoder = new PicaDecoder();
		Metamorph morph = new Metamorph("src/test/resources/sample3/simple-transformation.xml");
		FormetaEncoder encoder = new FormetaEncoder();
		encoder.setStyle(FormatterStyle.MULTILINE);
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample3/sample3-out.txt");

		opener.setReceiver(reader)//
				.setReceiver(decoder)//
				.setReceiver(morph)//
				.setReceiver(encoder)//
				.setReceiver(writer);

		opener.process("src/test/resources/sample3/bib-data.pica.gz");
		opener.closeStream();
	}
}
