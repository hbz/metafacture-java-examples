import org.junit.runner.RunWith;
import org.metafacture.metamorph.test.MetamorphTestSuite;
import org.metafacture.metamorph.test.MetamorphTestSuite.TestDefinitions;

@RunWith(MetamorphTestSuite.class)
@TestDefinitions({ //
		"sample2/mystery-morph-1-test.xml", //
		"sample2/mystery-morph-2-test.xml", //
		"sample3/simple-transformation-test.xml", //
		"sample4/count-test.xml", //
		"sample5/count-test.xml", //
		"sample6/enrich-wiki-test.xml", //
		"sample7/morph-bib-test.xml" })
public final class TestMorphs {
	/* bind to xml test */
}