package samples;

import org.metafacture.formatting.StreamLiteralFormatter;
import org.metafacture.formeta.FormetaDecoder;
import org.metafacture.io.FileCompression;
import org.metafacture.io.FileOpener;
import org.metafacture.io.LineReader;
import org.metafacture.io.ObjectWriter;
import org.metafacture.metamorph.Metamorph;
import org.metafacture.monitoring.ObjectLogger;
import org.metafacture.monitoring.StreamLogger;
import org.metafacture.strings.StringFilter;

public class Sample2_Logging {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		StringFilter filter = new StringFilter("geburts");
		filter.setPassMatches(false);
		FormetaDecoder decoder = new FormetaDecoder();
		Metamorph morph1 = new Metamorph("src/test/resources/sample2/mystery-morph-1.xml");
		Metamorph morph2 = new Metamorph("src/test/resources/sample2/mystery-morph-2.xml");
		StreamLiteralFormatter encoder = new StreamLiteralFormatter();
		ObjectWriter<String> writer = new ObjectWriter<>("src/test/resources/sample2/sample2-out.txt");

		opener.setReceiver(reader)//
				.setReceiver(new ObjectLogger<>("Filtering: "))//
				.setReceiver(filter)//
				.setReceiver(new ObjectLogger<>("Decoding: "))//
				.setReceiver(decoder)//
				.setReceiver(new StreamLogger("Morph 1: "))//
				.setReceiver(morph1)//
				.setReceiver(new StreamLogger("Morph 2: "))//
				.setReceiver(morph2)//
				.setReceiver(new StreamLogger("Encoding: "))//
				.setReceiver(encoder)//
				.setReceiver(new ObjectLogger<>("Writing: "))//
				.setReceiver(writer);

		opener.process("src/test/resources/sample2/input.foma.gz");
		opener.closeStream();
	}
}
