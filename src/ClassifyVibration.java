import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import processing.sound.AudioIn;
import processing.sound.FFT;
import processing.sound.Sound;
import processing.sound.Waveform;

/* A class with the main function and Processing visualizations to run the demo */

public class ClassifyVibration extends PApplet {

	FFT fft;
	AudioIn in;
	Waveform waveform;
	int bands = 512;
	int nsamples = 1024;
	float[] spectrum = new float[bands];
	float[] fftFeatures = new float[bands];
	String[] classNames = {"neutral", "noise", "3", "4","5","6","7"};
	int classIndex = 0;
	int dataCount = 0;
	
	int counter = 0; 
	int maxCount = 0;
	Map<String, Integer> guessedLabels = new HashMap<>();
	String guessedLabel_stablized = "neutral";
	float volume;
	float[] prev_spec = new float[bands];
	float prev_volume = 0; 
	int training_finished = 0;
	int num_samples = 3;
	float[] sound_samples = new float[num_samples * bands]; 
	
	PlaySound play;

	MLClassifier classifier;
	
	Map<String, List<DataInstance>> trainingData = new HashMap<>();
	{for (String className : classNames){
		trainingData.put(className, new ArrayList<DataInstance>());
	}}
	
	DataInstance captureInstance (String label){
		DataInstance res = new DataInstance();
		res.label = label;
//		res.measurements = fftFeatures.clone();
		res.measurements = sound_samples.clone();
		return res;
	}
	
	public static void main(String[] args) {
		PApplet.main("ClassifyVibration");
	}
	
	public void settings() {
		size(512, 400);
	}

	public void setup() {
		
		/* list all audio devices */
		Sound.list();
		Sound s = new Sound(this);
		  
		/* select microphone device */
		s.inputDevice(6);
		    
		/* create an Input stream which is routed into the FFT analyzer */
		fft = new FFT(this, bands);
		in = new AudioIn(this, 0);
		waveform = new Waveform(this, nsamples);
		waveform.input(in);
		
		/* start the Audio Input */
		in.start();
		  
		/* patch the AudioIn */
		fft.input(in);
	}

	public void draw() {
		background(0);
		fill(0);
		stroke(255);
		
		waveform.analyze();

		beginShape();
		  
		for(int i = 0; i < nsamples; i++)
		{
			vertex(
					map(i, 0, nsamples, 0, width),
					map(waveform.data[i], -1, 1, 0, height)
					);
		}
		
		endShape();

		fft.analyze(spectrum);

		//sound_samples.length = bands(512) * num_samples
		for(int j = 0; j < bands * (num_samples - 1); j++) {
			sound_samples[j] = sound_samples[j + bands]; //remove 0 - 511, move 511-1023 to 0 - 511
		}
		
		for(int i = 0; i < bands; i++){
			volume = 0;
			/* the result of the FFT is normalized */
			/* draw the line for frequency band i scaling it up by 40 to get more amplitude */
			line( i, height, i, height - spectrum[i]*height*40);
//			spectrum[i] -= prev_spec[1];
//			if(training_finished == 1) spectrum[i] -= prev_spec[1]; //eliminating the noise 
			fftFeatures[i] = spectrum[i];
			volume += spectrum[i];
			sound_samples[bands * (num_samples - 1) + i] = spectrum[i];
			volume += spectrum[i];
		} 

		fill(255);
		textSize(30);
		if(classifier != null) {
			String guessedLabel = classifier.classify(captureInstance(null));
			
			int labelCount = guessedLabels.getOrDefault(guessedLabel, 0) + 1;
			if(labelCount > maxCount) maxCount = labelCount;
			guessedLabels.put(guessedLabel, labelCount);
//			println("counter is" + counter);
			counter ++;
			// Yang: add code to stabilize your classification results
			if(counter == 5) {
				for(String name : guessedLabels.keySet()) {
					if(guessedLabels.get(name) == maxCount) {
						guessedLabel_stablized = name;
						break;
					}
				}
				counter = 0;
				maxCount = 0;
				guessedLabels.clear();
				play = new PlaySound();
				play.playSound(guessedLabel_stablized);
			}
			text("classified as: " + guessedLabel_stablized, 20, 30);
			
			
			
		}else {
			text(classNames[classIndex], 20, 30);
			dataCount = trainingData.get(classNames[classIndex]).size();
			text("Data collected: " + dataCount, 20, 60);
		}
	}
	
	public void keyPressed() {
		

		if (key == CODED && keyCode == DOWN) {
			classIndex = (classIndex + 1) % classNames.length;
		}
		
		else if (key == 't') {
			if(classifier == null) {
				println("Start training ...");
				classifier = new MLClassifier();
				classifier.train(trainingData);
				training_finished = 1;
			}else {
				classifier = null;
			}
		}
		
		else if (key == 's') {
			// Yang: add code to save your trained model for later use
		}
		
		else if (key == 'l') {
			// Yang: add code to load your previously trained model
		}
			
		else {
			if(classIndex != 0 && (volume < 5E-6 || volume < prev_volume))
				println("\\");
			else {
				println("//");
				println("Data collected! volume: " + volume);
				trainingData.get(classNames[classIndex]).add(captureInstance(classNames[classIndex]));
			}	
			prev_volume = volume;
		}
	}

}
