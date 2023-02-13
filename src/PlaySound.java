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
		soundsLibrary.put("1", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("2", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("3", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("4", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("5", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("6", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
		soundsLibrary.put("7", "C:\\Users\\brand\\Desktop\\New folder (7)\\musicradar-drum-samples\\Drum Kits\\Kit 3 - Acoustic\\CyCdh_K3Snr-06.wav");
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
				AudioInputStream targetInput; 
				
				AudioFormat targetFormat; 
				
				SourceDataLine sourceDataLine; 
				
//				sourceInput = AudioSystem.getAudioInputStream()
		        // Read the .wav file
				File wavFile = new File(fileName);
			    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
			        
			    // Get the audio format
			    AudioFormat audioFormat = audioInputStream.getFormat();
			    
			    targetFormat = new AudioFormat(audioFormat.getEncoding(),
			    		audioFormat.getSampleRate(),
			    		audioFormat.getSampleSizeInBits(),
			    		audioFormat.getChannels(),
			    		audioFormat.getFrameSize(), 
			    		audioFormat.getFrameRate(),
			    		false);
			    
			    targetInput = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
			        
			    // Get the data line information
			    DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, targetFormat);
			        
			    // Open the clip
			    sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			    sourceDataLine.open(targetFormat);
			        
			    // Play the clip
			    sourceDataLine.start();
			       
		} 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
