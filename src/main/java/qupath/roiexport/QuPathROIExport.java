package qupath.roiexport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import qupath.lib.io.PathIO;
import qupath.lib.objects.PathObject;
import qupath.lib.roi.interfaces.ROI;

public class QuPathROIExport {

	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.out.println("usage: QuPathROIExport <input qpdata file> <output json file>");
			return;
		}
		new QuPathROIExport().convertToJSON(args[0], args[1]);
	}


	public void convertToJSON(String qpDatafileName, String jsonDatafileName) throws IOException {
		File file = new File(qpDatafileName);
		Collection<PathObject> childObjects = PathIO.readHierarchy(file).getRootObject().getChildObjects();
		if(childObjects.isEmpty()) throw new IllegalArgumentException("no child objects to write");
		try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(jsonDatafileName))) {
			outputStream.write("[".getBytes());
			ObjectMapper om = new ObjectMapper();
			String sep = "";
			for (PathObject pathObject : childObjects) {
				if(pathObject.hasROI()) {
					outputStream.write(sep.getBytes());
					ROI roi = pathObject.getROI(); 
					
					String jsonInString = om.writeValueAsString(roi);
					jsonInString = jsonInString.replaceFirst(Pattern.quote("{"),"{\"pathClass\":\""+roi+"\",");
					outputStream.write(jsonInString.getBytes());
					sep = ",\n";
				}
				
			}
			outputStream.write("]".getBytes());
		}
		
	}
}
