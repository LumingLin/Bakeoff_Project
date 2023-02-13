import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class PlaySound {
//	String input;
	Map<String, String> soundsLibrary;
	public PlaySound() {
//		this.input = input;
		soundsLibrary = new HashMap<>();
		soundsLibrary.put("1", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("2", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("3", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("4", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("5", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("6", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
		soundsLibrary.put("7", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\mixkit-drum-bass-hit-2294.wav");
	}
	
	public void playSound(String input) {
		if(input.equals("neutral") || input.equals("noise")) {
			return;
		}
		
//		soundsLibrary.put("1", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Bass_Drum.wav");
//		soundsLibrary.put("2", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Crash_Cymbal.wav");
//		soundsLibrary.put("3", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Floor_Tom.wav");
//		soundsLibrary.put("4", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Hi_Hat.wav");
//		soundsLibrary.put("5", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\High_Tom.wav");
//		soundsLibrary.put("6", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Ride_Cymbal.wav");
//		soundsLibrary.put("7", "C:\\School\\UCLA\\ECE209AS HCI\\MLBakeoff\\DrumSounds\\Snare.wav");
//		System.out.println(input);
		String fileName = soundsLibrary.get(input);
		System.out.println(input);
		try {
		        // Read the .wav file
				File wavFile = new File(fileName);
			    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
			        
			    // Get the audio format
			    AudioFormat audioFormat = audioInputStream.getFormat();
			        
			    // Get the data line information
			    DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat);
			        
			    // Open the clip
			    Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
			    clip.open(audioInputStream);
			        
			    // Play the clip
			    clip.start();
			        
			    } 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
