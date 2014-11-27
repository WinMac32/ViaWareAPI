/*
Copyright 2014 Seth Traverse

This file is part of ViaWare API.

ViaWare API is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ViaWare API is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with ViaWare API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.viaware.api.audio;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioUtils {

	public static void playRaw(AudioInputStream input, AudioFormat format) {
		try{
			SourceDataLine dataLine = AudioSystem.getSourceDataLine(format);

			dataLine.open();
			dataLine.start();

			byte[] buffer = new byte[1024];
			int read;
			try {
				while ((read = input.read(buffer)) != -1) {
						dataLine.write(buffer, 0, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			dataLine.drain();
			dataLine.stop();
			dataLine.close();
		}catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
