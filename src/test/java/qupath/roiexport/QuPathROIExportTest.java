package qupath.roiexport;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class QuPathROIExportTest {

	
	QuPathROIExport export = new QuPathROIExport();
	
	@Test
	public void testConvertToJSON() throws Exception {
		String qpDatafileName = "src/test/resources/test.qpdata";
		String jsonDatafileName = "target/output.json";
		File file = new File(jsonDatafileName);
		if(file.exists()) file.delete();
		export.convertToJSON(qpDatafileName, jsonDatafileName);
		assertTrue(file.exists());
	}

}
