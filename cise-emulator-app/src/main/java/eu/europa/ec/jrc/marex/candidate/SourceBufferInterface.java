package eu.europa.ec.jrc.marex.candidate;

import java.io.File;
import java.util.List;

public interface SourceBufferInterface {

    StringBuffer getReferenceFileContent(String PayloadUri);

    List<File> getReferenceDirectoryListing(String pathParameter);

}