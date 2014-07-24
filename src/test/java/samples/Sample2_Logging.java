package samples;
import org.culturegraph.mf.morph.Metamorph;
import org.culturegraph.mf.stream.converter.FormetaDecoder;
import org.culturegraph.mf.stream.converter.LineReader;
import org.culturegraph.mf.stream.converter.StreamLiteralFormater;
import org.culturegraph.mf.stream.pipe.ObjectLogger;
import org.culturegraph.mf.stream.pipe.StreamLogger;
import org.culturegraph.mf.stream.pipe.StringFilter;
import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.culturegraph.mf.stream.source.FileOpener;
import org.culturegraph.mf.util.FileCompression;

public class Sample2_Logging {
	public static void main(String[] args) {

		FileOpener opener = new FileOpener();
		opener.setCompression(FileCompression.GZIP);
		LineReader reader = new LineReader();
		StringFilter filter = new StringFilter("geburts");
		filter.setPassMatches(false);
		FormetaDecoder decoder = new FormetaDecoder();
		Metamorph morph1 = new Metamorph(
				"src/test/resources/sample2/mystery-morph-1.xml");
		Metamorph morph2 = new Metamorph(
				"src/test/resources/sample2/mystery-morph-2.xml");
		StreamLiteralFormater encoder = new StreamLiteralFormater();
		ObjectWriter<String> writer = new ObjectWriter<>(
				"src/test/resources/sample2/sample2-out.txt");

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
