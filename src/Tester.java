import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Tester {
	
	public static void main(String[] args) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		
		File testFile = new File("D:/Music/Aurora/All My Demons Greet Me as a Friend/01-aurora-runaway-3a82cf3c.mp3");
		
		AudioFile f = AudioFileIO.read(testFile);
		Tag tag = f.getTag();
		String artistName = tag.getFirst(FieldKey.ARTIST);
		String songTitle = tag.getFirst(FieldKey.TITLE);
		
		String newName = artistName + " - " + songTitle + ".mp3";
		String pathName = testFile.getParent() + "\\";
		
		System.out.println(pathName + newName);
		System.out.println(testFile.renameTo(new File(pathName + newName)));
		
	}
	
}
