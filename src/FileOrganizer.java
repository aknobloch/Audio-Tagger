import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class FileOrganizer {

	static String currentDirectory;
	static ArrayList<File> unalteredFiles = new ArrayList<File>();
	
	
	public static void main(String[] args)  {
		
		File rootDirectory = new File("D:/Music/");
		
		long startTime = System.currentTimeMillis();
		searchSongs(rootDirectory.listFiles());
		long endTime = System.currentTimeMillis();
		System.out.println("\n\n THE FOLLOWING FILES WERE NOT ALTERED: \n\n");
		for(File errorFile : unalteredFiles) {
			System.out.println(errorFile.getPath());
		}
		
		System.out.println("Seconds to complete: " + (endTime - startTime) / 1000);
		
	}
	
	public static void searchSongs(File[] files) {
		
		
		// iterate through every file
		for(File focusFile : files) {
			
			// if file is directory, we must go deeper
			if(focusFile.isDirectory()) {
				searchSongs(focusFile.listFiles());
			}
			
			// if an actual file
			else {
				
				currentDirectory = files[0].getParent() + "\\";
				
				// get file type extension (for music: mp3, m4a, FLAC)
				String fileType = focusFile.getName().substring(focusFile.getName().length() - 4);
				
				// keep track to determine if add to unaltered files
				boolean flacAltered = false;
				
				// check for FLAC
				if(fileType.equalsIgnoreCase("FLAC")) {
					
					renameFile(focusFile, ".flac");
					flacAltered = true;
					
				}
				
				fileType = fileType.substring(1);
				
				// check for mp3
				if(fileType.equalsIgnoreCase("MP3")) {
					renameFile(focusFile, ".mp3");
				}
				// check for m4a
				else if(fileType.equalsIgnoreCase("M4A")) {
					renameFile(focusFile, ".m4a");
				}
				// if none of the above, add to unaltered list
				else if( ! flacAltered) {
					
					unalteredFiles.add(focusFile);
					
				}
				
			}
			
		}
		
	}
	
	public static boolean renameFile(File fileToRename, String extension) {
		
		AudioFile f;
		try {
			
			f = AudioFileIO.read(fileToRename);
			Tag tag = f.getTag();
			String artistName = tag.getFirst(FieldKey.ARTIST);
			String songTitle = tag.getFirst(FieldKey.TITLE);
			
			String newName = artistName + " - " + songTitle + extension;
			
			return fileToRename.renameTo(new File(currentDirectory + newName));
			
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			
			unalteredFiles.add(fileToRename);
			e.printStackTrace();
			return false;
		}
		
	}
	
}
